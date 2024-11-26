package com.hgf.tool.csv.reader;

import com.csvreader.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 默認CSV讀取工具
 */
public class DefaultCsvReader extends AbstractCsvReader {

    private final Logger log = LoggerFactory.getLogger(DefaultCsvReader.class);

    private final InputStream inputStream;

    private Charset charset = StandardCharsets.UTF_8;;

    public DefaultCsvReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public DefaultCsvReader(InputStream inputStream, Charset charset) {
        this.inputStream = inputStream;
        this.charset = charset;
    }

    @Override
    protected CsvReader readCsv() {
        try {
            return new CsvReader(inputStream, ',', charset);
        } catch (Exception e) {
            log.error("讀取CSV文件失敗，異常信息：" + e.getMessage(), e);
            return null;
        }
    }
}
