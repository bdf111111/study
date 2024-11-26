package com.hgf.tool.model.exception;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class FileException extends RuntimeException{

    public FileException() {
        super();
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

}
