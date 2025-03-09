package com.cx.visionvibe.service;

import com.cx.visionvibe.dto.request.OrderCreationRequest;
import com.cx.visionvibe.dto.response.OrderDetailResponse;
import com.cx.visionvibe.dto.response.OrderResponse;
import com.cx.visionvibe.entity.Order;
import com.cx.visionvibe.entity.OrderDetail;
import com.cx.visionvibe.entity.ProductDetail;
import com.cx.visionvibe.enums.OrderStatus;
import com.cx.visionvibe.exception.AppException;
import com.cx.visionvibe.exception.ErrorCode;
import com.cx.visionvibe.mapper.OrderDetailMapper;
import com.cx.visionvibe.mapper.OrderMapper;
import com.cx.visionvibe.mapper.PaymentMethodMapper;
import com.cx.visionvibe.repository.OrderRepository;
import com.cx.visionvibe.repository.PaymentMethodRepository;
import com.cx.visionvibe.repository.ProductDetailRepository;
import com.cx.visionvibe.utility.MailContent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    PaymentMethodRepository paymentMethodRepository;
    ProductDetailRepository productDetailRepository;

    OrderMapper orderMapper;
    OrderDetailMapper orderDetailMapper;
    PaymentMethodMapper paymentMethodMapper;

    NotificationService notificationService;
    ProductDetailService productDetailService;
    PaypalService paypalService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR','SALE')")
    public Page<OrderResponse> findAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findAll(pageable).map(this::toOrderResponse);

    }

    public List<OrderResponse> findAllOrdersInCurrentMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return orderRepository.findOrderInMonth(startOfMonth, endOfMonth).stream().map(this::toOrderResponse).toList();
    }

    public OrderResponse findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        return toOrderResponse(order);
    }

    @Transactional
    public OrderResponse addNewOrder(OrderCreationRequest request) throws IOException {
        Order rawOrder = orderMapper.toOrder(request);
        rawOrder.setCreatedAt(LocalDateTime.now());
        rawOrder.setPaymentMethod(paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_ID_NOT_FOUND)));

        List<ProductDetail> updatedProductDetails = new ArrayList<>();
        List<OrderDetail> orderDetailList = request.getOrderDetailCreationDtoList().stream().map(dto -> {
            ProductDetail productDetail = productDetailRepository.findById(dto.getProductDetailId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_ID_NOT_FOUND));

            if (productDetail.getStock() < dto.getQuantity())
                throw new AppException(ErrorCode.OUT_OF_STOCK, productDetail.getProduct().getProductName());

            updatedProductDetails.add(productDetail);

            return OrderDetail.builder()
                    .productDetail(productDetail)
                    .amount(dto.getQuantity())
                    .order(rawOrder)
                    .build();

        }).toList();

        productDetailRepository.saveAll(updatedProductDetails);
        rawOrder.setOrderDetails(orderDetailList);

        if (request.getPaymentMethodId() == 1) {
            rawOrder.setOrderStatus(OrderStatus.PENDING);

            var order = orderRepository.save(rawOrder);

            notificationService.createNewNotificationWhenCreateNewOrder(order);
            notificationService.sendOrderMail(order.getEmail(), MailContent.pendingSubject, MailContent.htmlPendingContent, order.getId());
            notificationService.sendNotificationByWebSocket(order);

            return toOrderResponse(order);
        }

        if (request.getPaymentMethodId() == 2) {
            rawOrder.setOrderStatus(OrderStatus.AWAITING_PAYMENT);

            var order = orderRepository.save(rawOrder);
            notificationService.createNewNotificationWhenCreateNewOrder(order);
            notificationService.sendNotificationByWebSocket(order);

            double totalPrice = 0D;
            for (OrderDetail orderDetail : orderDetailList)
                totalPrice = totalPrice + orderDetail.getProductDetail().getProduct().getPrice() * orderDetail.getAmount();

            var orderResponse = toOrderResponse(order);
            orderResponse.setPaymentUrl(paypalService.createOrder(totalPrice, order.getId()));

            return orderResponse;
        }
        return null;
    }

    public OrderResponse setPendingAfterPaidById(Long id, String token) {
        if (paypalService.checkPaymentStatus(token)) {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
            order.setOrderStatus((OrderStatus.PENDING));

            notificationService.sendOrderMail(order.getEmail(), MailContent.pendingSubject, MailContent.htmlPendingContent, order.getId());
            notificationService.sendNotificationByWebSocket(order);
            notificationService.createNewNotificationWhenCreateNewOrder(order);

            return toOrderResponse(orderRepository.save(order));
        }
        throw new AppException(ErrorCode.PAYPAL_TOKEN_DENIED);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SALE')")
    public OrderResponse confirmOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        order.setOrderStatus(OrderStatus.CONFIRMED);

        List<ProductDetail> updatedProductDetails = order.getOrderDetails().stream()
                .map(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    if (productDetail.getStock() < orderDetail.getAmount())
                        throw new AppException(ErrorCode.OUT_OF_STOCK, productDetail.getProduct().getProductName());

                    productDetail.setStock(productDetail.getStock() - orderDetail.getAmount());
                    return productDetail;
                }).toList();
        productDetailRepository.saveAll(updatedProductDetails);

        notificationService.sendOrderMail(order.getEmail(), MailContent.confirmedSubject, MailContent.htmlConfirmContent, order.getId());
        notificationService.sendNotificationByWebSocket(order);
        notificationService.createNewNotificationWhenCreateNewOrder(order);

        return toOrderResponse(orderRepository.save(order));
    }

    @PreAuthorize("hasAnyRole('ADMIN','SALE')")
    public OrderResponse denyOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ID_NOT_FOUND));
        order.setOrderStatus(OrderStatus.DENIED);

        notificationService.sendOrderMail(order.getEmail(), MailContent.deniedSubject, MailContent.htmlDeniedContent, order.getId());
        notificationService.sendNotificationByWebSocket(order);
        notificationService.createNewNotificationWhenCreateNewOrder(order);

        return toOrderResponse(orderRepository.save(order));
    }


    private OrderResponse toOrderResponse(Order order) {
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);
        orderResponse.setOrderStatus(order.getOrderStatus().toString());
        orderResponse.setPaymentMethodResponse(paymentMethodMapper.toPaymentMethodResponse(order.getPaymentMethod()));
        orderResponse.setOrderDetailResponseList(order.getOrderDetails().stream().map(this::toOrderDetailResponse).toList());
        return orderResponse;
    }

    private OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        OrderDetailResponse orderDetailResponse = orderDetailMapper.toOrderDetailResponse(orderDetail);
        orderDetailResponse.setProductDetailResponse(productDetailService.toProductDetailResponse(orderDetail.getProductDetail()));
        return orderDetailResponse;
    }
}
