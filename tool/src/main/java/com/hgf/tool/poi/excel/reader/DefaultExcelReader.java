package com.hgf.tool.poi.excel.reader;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author huanggf
 * @date 2024/11/5
 */
public class DefaultExcelReader extends AbstractExcelReader {

    private final Logger log = LoggerFactory.getLogger(DefaultExcelReader.class);

    private final InputStream inputStream;

    public DefaultExcelReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * 读取工作簿信息
     * @return 返回工作簿实例
     */
    @Override
    protected Workbook readWorkbook() {
        try {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            log.error("读取工作簿信息异常: " + e.getMessage(), e);
            return null;
        }
    }
}

