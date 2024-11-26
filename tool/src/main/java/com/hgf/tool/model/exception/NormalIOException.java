package com.hgf.tool.model.exception;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class NormalIOException  extends RuntimeException{

    public NormalIOException() {
        super();
    }

    public NormalIOException(String message) {
        super(message);
    }

    public NormalIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
