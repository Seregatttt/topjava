package ru.javawebinar.topjava.util.exception;

import java.util.List;

public class ErrorInfo {
    private  String url;
    private  ErrorType type;
    private  String[] detail;

    public ErrorInfo(String url, ErrorType type, String... detail) {
        this.url = url;
        this.type = type;
        this.detail = detail;
    }
}