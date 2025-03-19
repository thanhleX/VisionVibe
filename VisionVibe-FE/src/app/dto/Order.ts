import { ProductDetail } from "./ProductDetail";

export interface Order {
    id: number;
    customerName: string;
    email: string;
    phoneNumber: string;
    address: string;
    note: string;
    createdAt: Date;
    status: string;
    paymentMethodResponse: PaymentMethod;
    paymentUrl: string | null;
    orderDetailResponseList: OrderDetail[];
}

export interface PaymentMethod {
    id: number;
    name: string;
}

export interface OrderDetail {
    id: number;
    productDetailResponse: ProductDetail;
    amount: number;
}