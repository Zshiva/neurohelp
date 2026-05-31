package com.project.neurohelp.platform.rest;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record RestResponse<T>(String code, String message, T data) {

    @SuppressWarnings("unchecked")
    public static <T> RestResponse<T> success(){
        return (RestResponse<T>) (Object) RestResponseBuilder.builder()
                .code("0")
                .message("SUCCESS")
                .data(null)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> RestResponse<T> success(T data) {
        return (RestResponse<T>) (Object) RestResponseBuilder.builder()
                .code("0")
                .message("SUCCESS")
                .data(data)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> RestResponse<T> error(String code, String message) {
        return (RestResponse<T>) (Object) RestResponseBuilder.builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> RestResponse<T> error(String message) {
        return (RestResponse<T>) (Object) RestResponseBuilder.builder()
                .code("400")
                .message(message)
                .data(null)
                .build();
    }
}
