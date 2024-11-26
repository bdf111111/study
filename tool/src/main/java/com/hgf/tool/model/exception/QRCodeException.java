package com.hgf.tool.model.exception;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class QRCodeException extends RuntimeException{

    public QRCodeException() {
        super();
    }

    public QRCodeException(String message) {
        super(message);
    }

    public QRCodeException(String message, Throwable cause) {
        super(message, cause);
    }

}