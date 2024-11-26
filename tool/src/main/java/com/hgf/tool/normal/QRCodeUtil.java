package com.hgf.tool.normal;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hgf.tool.model.exception.QRCodeException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class QRCodeUtil {

    private static final Map<EncodeHintType, Object> HINTS = new EnumMap<>(EncodeHintType.class);

    static {
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        HINTS.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8);
        HINTS.put(EncodeHintType.MARGIN, 1);
    }

    private QRCodeUtil(){}

    /**
     * 生成二维码，默认黑白
     *
     * @param content    内容
     * @param width      宽（像素）
     * @param height     高（像素）
     * @param formatName 二维码图片格式
     * @return 二维码字节数组
     */
    public static byte[] generate(String content, int width, int height, String formatName) {

        return generate(content, width, height, 0xFF000000, 0x00FFFFFF, formatName);
    }

    /**
     * 生成二维码，默认黑白
     *
     * @param content    内容
     * @param width      宽（像素）
     * @param height     高（像素）
     * @param onColor    前景色（ARGB）
     * @param offColor   背景色（ARGB）
     * @param formatName 二维码图片格式
     * @return 二维码字节数组
     */
    public static byte[] generate(String content, int width, int height, int onColor, int offColor, String formatName) {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix;
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, HINTS);
        } catch (WriterException e) {
            throw new QRCodeException("生成二维码失败", e);
        }

        int imgWidth = bitMatrix.getWidth();
        int imgHeight = bitMatrix.getHeight();

        BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? onColor : offColor);
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, formatName, byteArrayOutputStream);
        } catch (IOException e) {
            throw new QRCodeException("生成二维码失败", e);
        }

        return byteArrayOutputStream.toByteArray();

    }

}
