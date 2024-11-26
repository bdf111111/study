package com.hgf.tool.normal;

import com.hgf.tool.model.exception.NormalIOException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class IOUtil {

    /**
     * 默认缓存大小 8192
     */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * 数据流末尾
     */
    private static final int EOF = -1;

    private IOUtil(){}

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in  输入流
     * @param out 输出流
     * @return 传输的byte数
     */
    public static long copy(InputStream in, OutputStream out) {

        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 拷贝流，拷贝后不关闭流
     *
     * @param in         输入流
     * @param out        输出流
     * @param bufferSize 缓存大小
     * @return 传输的byte数
     */
    public static long copy(InputStream in, OutputStream out, int bufferSize) {

        long size = 0;

        if (bufferSize <= 0) {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }

        byte[] buffer = new byte[bufferSize];

        try {
            for (int readSize; (readSize = in.read(buffer)) != EOF; ) {
                out.write(buffer, 0, readSize);
                size += readSize;
                out.flush();
            }
        } catch (IOException e) {
            throw new NormalIOException("拷贝流失败", e);
        }

        return size;
    }

    /**
     * 字节数组转为流
     *
     * @param bytes 字节数组
     * @return 字节流
     */
    public static ByteArrayInputStream toStream(byte[] bytes) {
        if (null == bytes) {
            return null;
        }

        return new ByteArrayInputStream(bytes);
    }
}

