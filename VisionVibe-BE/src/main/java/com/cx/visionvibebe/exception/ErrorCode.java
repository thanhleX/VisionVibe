package com.cx.visionvibebe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CATEGORY_ID_NOT_FOUND(1001, "Category ID not found.", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_NOT_FOUND(1002, "Category name not found.", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_DUPLICATED(1003, "Category name is already in use.", HttpStatus.BAD_REQUEST),

    PRODUCT_ID_NOT_FOUND(1004, "Product ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_DUPLICATED(1005, "Product name is already in use.", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE(1006, "Invalid image format.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    IMAGE_ID_NOT_FOUND(1008, "Image ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_ID_NOT_FOUND(1009, "Product image ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_SUB_CATEGORY_ID_NOT_FOUND(1031, "Product sub-category ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_COLOR_ID_NOT_FOUND(1032, "Product color ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_MATERIAL_ID_NOT_FOUND(1033, "Product material ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_NOT_FOUND(1035, "Product price not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_DUPLICATED(1036, "Product detail is already in use.", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_ID_NOT_FOUND(1037, "Product detail ID not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_FOUND(1038, "Product name not found.", HttpStatus.BAD_REQUEST),
    PRODUCT_CATEGORY_ID_NOT_FOUND(1043, "Product category ID not found.", HttpStatus.BAD_REQUEST),

    USER_ID_NOT_FOUND(1010, "User ID not found.", HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATED(1011, "Email is already in use.", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1027, "Email not found.", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATED(1041, "Username is already in use.", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(1042, "Username not found.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1016, "Password and confirmation do not match.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(1015, "User is already deleted.", HttpStatus.BAD_REQUEST),
    USER_NOT_DELETED(1013, "Failed to delete user.", HttpStatus.BAD_REQUEST),

    ROLE_ID_NOT_FOUND(1012, "Role ID not found.", HttpStatus.BAD_REQUEST),
    ROLE_NAME_DUPLICATED(1014, "Role name is already in use.", HttpStatus.BAD_REQUEST),

    PERMISSION_NAME_DUPLICATED(1039, "Permission name is already in use.", HttpStatus.BAD_REQUEST),
    PERMISSION_ID_NOT_FOUND(1040, "Permission ID not found.", HttpStatus.BAD_REQUEST),

    ORDER_ID_NOT_FOUND(1017, "Order ID not found.", HttpStatus.BAD_REQUEST),
    ORDER_ALREADY_DELETED(1018, "Order is already deleted.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_DELETED(1019, "Failed to delete order.", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_ID_NOT_FOUND(1020, "Order detail ID not found.", HttpStatus.BAD_REQUEST),
    ADD_NEW_ORDER_DETAIL_NOT_CONSISTENT(1021, "Order ID mismatch in order details.", HttpStatus.BAD_REQUEST),

    PAYMENT_METHOD_ID_NOT_FOUND(1045, "Payment method ID not found.", HttpStatus.BAD_REQUEST),
    PAYPAL_TOKEN_NOT_VALID(1048, "Invalid PayPal token.", HttpStatus.BAD_REQUEST),
    PAYPAL_TOKEN_DENIED(1049, "PayPal token not approved.", HttpStatus.BAD_REQUEST),

    OUT_OF_STOCK(1046, "%s is out of stock.", HttpStatus.BAD_REQUEST),

    BLOG_CATEGORY_ID_NOT_FOUND(1028, "Blog category ID not found.", HttpStatus.BAD_REQUEST),
    BLOG_ID_NOT_FOUND(1029, "Blog ID not found.", HttpStatus.BAD_REQUEST),
    BLOG_CAN_NOT_COMPARE(1030, "Unable to compare blogs.", HttpStatus.BAD_REQUEST),

    NOTIFICATION_ID_NOT_FOUND(1050, "Notification ID not found.", HttpStatus.BAD_REQUEST),

    RABBITMQ_SEND_FAILED(1022, "Failed to send message to RabbitMQ.", HttpStatus.BAD_REQUEST),
    IMAGE_UPLOAD_FAILED(1023, "Failed to upload image.", HttpStatus.BAD_REQUEST),
    CREATE_FOLDER_FAILED(1024, "Failed to create folder.", HttpStatus.BAD_REQUEST),
    JSON_PARSE_ERROR(1025, "Failed to parse JSON data.", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1026, "Authentication required.", HttpStatus.UNAUTHORIZED),
    CANT_SEND_MAIL(1047, "Failed to send email.", HttpStatus.BAD_REQUEST),
    IO_ERROR(1007, "Internal I/O error occurred.", HttpStatus.INTERNAL_SERVER_ERROR),
    PROMOTION_ID_NOT_FOUND(1099, "Promotion ID not found", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
