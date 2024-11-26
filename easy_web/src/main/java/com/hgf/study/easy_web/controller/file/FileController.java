package com.hgf.study.easy_web.controller.file;

import cn.hutool.json.JSONUtil;
import com.hgf.tool.csv.reader.DefaultCsvReader;
import com.hgf.tool.csv.writer.CsvExportData;
import com.hgf.tool.csv.writer.DefaultCsvWriter;
import com.hgf.tool.easy.excel.ExcelUtils;
import com.hgf.tool.poi.excel.export.DefaultExcelExportTools;
import com.hgf.tool.poi.excel.export.ExcelExportData;
import com.hgf.tool.poi.excel.reader.DefaultExcelReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huanggf
 * @date 2024/11/5
 */
@RestController
@RequestMapping("/test/file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        List<StudentRow> rows = new ArrayList<>();
        for (int i = 0; i <= 5000; i++) {
            StudentRow studentRow = new StudentRow();
            studentRow.setName("名称" + i);
            studentRow.setAge("18");
            rows.add(studentRow);
        }
        ExcelExportData<StudentRow> excelExportData = new ExcelExportData<>("学生.xlsx", "学生", StudentRow.class, rows);
        Workbook workbook = new DefaultExcelExportTools().exportData(excelExportData);
        try (OutputStream outputStream = response.getOutputStream()) {
            // 设置响应头
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            // 对文件名进行URL编码，处理中文文件名
            String encodedFileName = URLEncoder.encode("学生 学生", StandardCharsets.UTF_8.name())
                    // encode后空格会变加号，这里重新替换
                    .replaceAll("\\+", "%20");

            // 设置文件下载头
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + encodedFileName + ".xlsx");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");

            // poi
//            workbook.write(outputStream);
//            outputStream.flush();
            // easy excel
            ExcelUtils.downLoad(outputStream, StudentRow.class, rows, "學生");
        } catch (IOException e) {
            log.error("Export excel failed: ", e);
            throw new RuntimeException("导出Excel失败", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("Close workbook failed: ", e);
            }
        }
    }

    @PostMapping("/import/student")
    public void importStudent(MultipartFile file) {
        try(InputStream inputStream = file.getInputStream()){
            // poi
//            DefaultExcelReader reader = new DefaultExcelReader(inputStream);
//            List<StudentRow> rows = reader.readDataFormExcel(StudentRow.class, 5);
            // easy excel
//            List<StudentRow> rows = ExcelUtils.excelRead(inputStream, StudentRow.class, 1);
            List<StudentRow> rows = ExcelUtils.excelImageRead(inputStream, StudentRow.class, null, null, pictureData -> {
                // 实际可以对pictureData上传并返回url new ByteArrayInputStream(pictureData.getData())
                System.out.println(Arrays.toString(pictureData.getData()));
                return "hello";
            });
            System.out.println(JSONUtil.toJsonStr(rows));
        } catch (Exception e) {
            log.error("Import student failed: ", e);
        }
    }

    @GetMapping("/csv/export")
    public void csvExport(HttpServletResponse response) throws IOException {
        List<StudentRow> rows = new ArrayList<>();
        for (int i = 0; i <= 5000; i++) {
            StudentRow studentRow = new StudentRow();
            studentRow.setName("名称" + i);
            studentRow.setAge("18");
            rows.add(studentRow);
        }
        CsvExportData<StudentRow> excelExportData = new CsvExportData<>(StudentRow.class, rows);
        File file = DefaultCsvWriter.export("学生.csv", excelExportData);
        try (OutputStream outputStream = response.getOutputStream()) {
            // 设置响应头
            response.setContentType("text/csv");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            // 对文件名进行URL编码，处理中文文件名
            String encodedFileName = URLEncoder.encode("学生 学生", StandardCharsets.UTF_8.name())
                    // encode后空格会变加号，这里重新替换
                    .replaceAll("\\+", "%20");

            // 设置文件下载头
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + encodedFileName + ".csv");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");

            // 读取文件并写入响应
            try (FileInputStream inStream = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("Export csv failed: ", e);
            throw new RuntimeException("导出Csv失败", e);
        }
    }

    @PostMapping("/csv/import/student")
    public void csvImportStudent(MultipartFile file) {
        try(InputStream inputStream = file.getInputStream()){
            DefaultCsvReader reader = new DefaultCsvReader(inputStream);
            List<StudentRow> rows = reader.readDataFormCsv(StudentRow.class);
            System.out.println(JSONUtil.toJsonStr(rows));
        } catch (Exception e) {
            log.error("Import student failed: ", e);
        }
    }

}
