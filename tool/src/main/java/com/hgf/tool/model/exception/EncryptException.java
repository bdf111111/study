package com.hgf.tool.model.exception;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class EncryptException extends RuntimeException{

    public EncryptException() {
        super();
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

}
