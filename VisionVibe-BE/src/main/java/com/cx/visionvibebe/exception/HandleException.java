package com.cx.visionvibebe.exception;

import com.cx.visionvibebe.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class HandleException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        String message = e.getPlaceHolder() != null
                ? String.format(errorCode.getMessage(), e.getPlaceHolder())
                : errorCode.getMessage();

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<?>> handleIOException(IOException e) {
        ErrorCode errorCode = ErrorCode.IO_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleJsonParseError(HttpMessageNotReadableException ex) {
        ErrorCode errorCode = ErrorCode.JSON_PARSE_ERROR;
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }
}
