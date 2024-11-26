package com.hgf.tool.easy.excel;

import org.apache.poi.ss.usermodel.PictureData;

/**
 * 图片回调
 */
public interface ImageCallBack {
    /**
     * 导入或上传或读excel时处理：图片回调
     *
     * @param pictureData 图片数据
     * @return 图片处理后的路径
     */
    String imgCallback(PictureData pictureData) throws Exception;
}
