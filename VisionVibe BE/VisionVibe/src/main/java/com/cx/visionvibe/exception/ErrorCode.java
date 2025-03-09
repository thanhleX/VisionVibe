package com.cx.visionvibe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CATEGORY_ID_NOT_FOUND(1001, "Category ID not found", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_NOT_FOUND(1002, "Category name not found", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_DUPLICATED(1003, "Category name is duplicated", HttpStatus.BAD_REQUEST),

    PRODUCT_ID_NOT_FOUND(1004, "Product ID not found", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_FOUND(1005, "Product name not found", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_DUPLICATED(1006, "Product name is duplicated", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_ID_NOT_FOUND(1007, "Product detail ID not found", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_DUPLICATED(1008, "Product detail is duplicated", HttpStatus.BAD_REQUEST),
    OUT_OF_STOCK(1009, "%s is out of stock", HttpStatus.BAD_REQUEST),

    IMAGE_ID_NOT_FOUND(1010, "Image ID not found", HttpStatus.BAD_REQUEST),
    IMAGE_UPLOAD_FAILED(1011, "Image upload failed", HttpStatus.BAD_REQUEST),

    USER_ID_NOT_FOUND(1012, "User ID not found", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND(1013, "Username not found", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATED(1014, "Username is duplicated", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1015, "Email not found", HttpStatus.BAD_REQUEST),
    EMAIL_DUPLICATED(1016, "Email is duplicated", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(1017, "Password and confirmation do not match", HttpStatus.BAD_REQUEST),

    ROLE_ID_NOT_FOUND(1018, "Role ID not found", HttpStatus.BAD_REQUEST),
    ROLE_NAME_DUPLICATED(1019, "Role name is duplicated", HttpStatus.BAD_REQUEST),

    PERMISSION_ID_NOT_FOUND(1020, "Permission ID not found", HttpStatus.BAD_REQUEST),
    PERMISSION_NAME_DUPLICATED(1021, "Permission name is duplicated", HttpStatus.BAD_REQUEST),

    ORDER_ID_NOT_FOUND(1022, "Order ID not found", HttpStatus.BAD_REQUEST),

    PAYMENT_METHOD_ID_NOT_FOUND(1023, "Payment method ID not found", HttpStatus.BAD_REQUEST),
    PAYPAL_TOKEN_NOT_VALID(1024, "PayPal token is invalid", HttpStatus.BAD_REQUEST),
    PAYPAL_TOKEN_DENIED(1025, "PayPal token was not approved", HttpStatus.BAD_REQUEST),

    NOTIFICATION_ID_NOT_FOUND(1026, "Notification ID not found", HttpStatus.BAD_REQUEST),

    IO_ERROR(1027, "I/O error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    CANT_SEND_MAIL(1028, "Unable to send email", HttpStatus.BAD_REQUEST),

    JSON_PARSE_ERROR(1029, "Invalid JSON format", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1030, "Authentication required", HttpStatus.UNAUTHORIZED),
    FRAME_COLOR_ID_NOT_FOUND(1031, "Frame color id is not found", HttpStatus.BAD_REQUEST),
    FRAME_MATERIAL_ID_NOT_FOUND(1032, "Frame material id is not found", HttpStatus.BAD_REQUEST),
    LENS_COLOR_ID_NOT_FOUND(1033, "Lens color id is not found", HttpStatus.BAD_REQUEST),
    LENS_MATERIAL_ID_NOT_FOUND(1034, "Lens material id is not found", HttpStatus.BAD_REQUEST),
    BRAND_ID_NOT_FOUND(1035, "Brand id is not found", HttpStatus.BAD_REQUEST),
    PROMOTION_ID_NOT_FOUND(1036, "Promotion id is not found", HttpStatus.BAD_REQUEST),
    PRODUCT_CATEGORY_ID_NOT_FOUND(1037, "Product category id is not found", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}