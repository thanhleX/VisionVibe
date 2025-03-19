package com.cx.visionvibebe.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String placeHolder;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.placeHolder = null;
    }

    public AppException(ErrorCode errorCode, String placeHolder) {
        super(placeHolder != null ? String.format(errorCode.getMessage(), placeHolder) : errorCode.getMessage());
        this.errorCode = errorCode;
        this.placeHolder = placeHolder;
    }
}
