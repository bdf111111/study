package com.hgf.tool.normal;

import com.hgf.tool.model.exception.FileException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author huanggf
 * @date 2024/11/26
 */
public class ImageCompressorUtil {

    /**
     * 图片格式
     */
    private static final String IMAGE_FORMAT = "jpg";

    /**
     * 默认大小 2m
     */
    private static final Long DEFAULT_SIZE = 2 * 1024 * 1024L;


    private ImageCompressorUtil() {
    }

    /**
     * 压缩图片
     *
     * @param inputStream 图片流
     * @return 压缩后文件流
     */
    public static InputStream compressImage(InputStream inputStream) {

        return compressImageToSize(inputStream, DEFAULT_SIZE);
    }

    /**
     * 压缩图片至指定大小
     *
     * @param inputStream 图片流
     * @param targetSize  压缩目标大小
     * @return 压缩后文件流
     */
    public static InputStream compressImageToSize(InputStream inputStream, long targetSize) {

        try {
            BufferedImage image = ImageIO.read(inputStream);

            long initialQuality = 100;
            long qualityStep = 5;
            long currentQuality = initialQuality;

            while (true) {

                ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();

                // 压缩图像并保存到compressedStream中
                compressImageWithQuality(image, compressedStream);
                byte[] compressedData = compressedStream.toByteArray();
                if (compressedData.length <= targetSize) {
                    // 如果压缩后的文件大小满足要求，则保存图像并退出循环
                    inputStream = new ByteArrayInputStream(compressedData);
                    break;
                }

                if (currentQuality <= 0) {
                    break;
                }

                // 如果无法继续减小质量因子，则降低质量步长继续进行尝试
                qualityStep /= 2;

                // 根据文件大小和目标文件大小之间的比例调整质量
                long ratio = compressedData.length * 100L / targetSize;

                if (ratio == 0) {
                    ratio = 1;
                }

                long qualityReduction = qualityStep * ratio;
                currentQuality -= qualityReduction;
            }
        } catch (Exception e) {
            throw new FileException("压缩图片文件失败");
        }
        return inputStream;
    }

    /**
     * 压缩图像并保存到compressedStream中
     *
     * @param image        图片文件
     * @param outputStream 输出流
     */
    private static void compressImageWithQuality(BufferedImage image, ByteArrayOutputStream outputStream) throws IOException {

        // 创建新的BufferedImage来保存压缩后的图像
        BufferedImage compressedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 获取图像的Graphics2D对象
        Graphics2D graphics = compressedImage.createGraphics();

        // 设置渲染质量为高质量
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制压缩后的图像
        graphics.drawImage(image, 0, 0, compressedImage.getWidth(), compressedImage.getHeight(), null);
        graphics.dispose();

        // 将压缩后的图像保存到输出流中
        ImageIO.write(compressedImage, IMAGE_FORMAT, outputStream);
    }
}
