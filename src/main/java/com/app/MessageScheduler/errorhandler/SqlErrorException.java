package com.app.MessageScheduler.errorhandler;

public class SqlErrorException extends Exception{

    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    private final int errorCode = 10003;

    public SqlErrorException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
