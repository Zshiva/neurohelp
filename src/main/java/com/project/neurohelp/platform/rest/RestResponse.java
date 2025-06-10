package com.project.neurohelp.platform.rest;

public record RestResponse<T>(String code, String message, T data) {
}
