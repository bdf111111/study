package com.hgf.tool.easy.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hgf.tool.json.JsonUtil;
import com.hgf.tool.normal.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExcelTools {

    /**
     * 处理图片
     *
     * @param sheet    excel表
     * @param callBack 回调处理
     */
    public static void handleExcelImage(Sheet sheet, ImageCallBack callBack) throws Exception {
        if (Objects.nonNull(callBack) && Objects.nonNull(sheet.getDrawingPatriarch())) {
            for (Shape shape : sheet.getDrawingPatriarch()) {
                ClientAnchor anchor = (ClientAnchor) shape.getAnchor();
                int row1 = anchor.getRow1();   // 获取行编号: getRow2():获取图片右下角行号 getRow1():获取图片左上角行号
                short col1 = anchor.getCol1();// 获取列编号: getCol2():获取图片右下角列号 getCol1():获取图片左上角列号

                if (row1 > sheet.getLastRowNum()) {
                    throw new Exception("图片位置错误(" + row1 + "," + col1 + ")");
                }
                if (shape instanceof Picture) {
                    Picture picture = (Picture) shape;
                    PictureData pictureData = picture.getPictureData();
                    String url = callBack.imgCallback(pictureData); // 调用回调处理

                    Row row = Objects.isNull(sheet.getRow(row1)) ? sheet.createRow(row1) : sheet.getRow(row1);
                    Cell cell = Objects.isNull(row.getCell(col1)) ? row.createCell(col1) : row.getCell(col1);
                    List<String> urls;
                    if (StringUtil.isNotEmpty(cell.getStringCellValue())) {
                        try {
                            urls = JsonUtil.parseArray(cell.getStringCellValue(), String.class);
                        } catch (Exception e) {
                            urls = new ArrayList<>();
                        }
                    } else {
                        urls = new ArrayList<>();
                    }
                    urls.add(url);
                    cell.setCellValue(JsonUtil.toJsonString(urls));
                }
            }
        }
    }

    /**
     * 保存并返回数据（對於多行頭不理解的可以先看下EasyExcel的多行頭）
     *
     * @param headRowCount 表头的行数(比如有些表格存在多行的头,T为bean对象时无效,为Map时生效), (当T为Map时, headRowCount不传時默认为1,第一行是表头; 当T为bean对象时根据ExcelProperty配置字段计算)
     * @param newHeads     默认null，重新设置表头，且clazz的表头失效，只对T为bean对象生效，(特别注意: heads的表头顺序与clazz的字段顺序必须保持一致，否则bean赋值可能出错)
     */
    public static <T> List<T> saveRowData(Sheet sheet, Class<T> clazz, Integer headRowCount, List<List<String>> newHeads) throws Exception {
        List<T> list = new ArrayList<>();
        Map<Integer, List<String>> cellHeadMap = new HashMap<>(); // 列-头
        Map<String, Field> headFieldMap = getHeadFieldMap(clazz, newHeads); // 头-字段

        // 多行头数(存在多行头)
        int headCount;
        if (Objects.isNull(headFieldMap)) {
            headCount = Objects.isNull(headRowCount) ? 1 : headRowCount;
        } else {
            headCount = headFieldMap.keySet().stream().map(k -> JsonUtil.parseArray(k, String.class)).filter(Objects::nonNull).map(List::size).reduce(Integer::max).orElse(1);
        }

        // 分析每一行
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (Objects.isNull(row)) continue;

            T t = null; // 声明对象
            boolean existContent = false; // 存在内容
            int rowNum = row.getRowNum();  // 从0开始

            // headCount行以内都是表头
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext() && rowNum < headCount) {
                Cell cell = cellIterator.next();
                DataFormatter dataFormatter = new DataFormatter();
                String val = dataFormatter.formatCellValue(cell);
                List<String> heads = cellHeadMap.getOrDefault(cell.getColumnIndex(), new ArrayList<>());
                heads.add(val);
                cellHeadMap.put(cell.getColumnIndex(), heads);
            }

            if (rowNum >= headCount) {
                int hs = cellHeadMap.size(); // 头数量
                for (int cn = 0; cn < hs; cn++) {
                    t = Objects.isNull(t) ? clazz.getDeclaredConstructor().newInstance() : t;

                    Cell cell = row.getCell(cn, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    List<String> heads = cellHeadMap.get(cell.getColumnIndex()); // 获取表头字段名称

                    DataFormatter dataFormatter = new DataFormatter();
                    String val = dataFormatter.formatCellValue(cell);
                    if (StringUtil.isEmpty(val)) {
                        continue;
                    }

                    // bean
                    if (Objects.nonNull(headFieldMap)) {
                        Field field1 = headFieldMap.get(JsonUtil.toJsonString(heads)); // 为bean时获取表头对应的bean字段
                        if (Objects.nonNull(field1)) {
                            field1.set(t, val); // 为字段赋值
                            existContent = StringUtil.isNotEmpty(val) || existContent; // 内容是否存在
                        }
                    }

                    // map
                    if (Map.class.isAssignableFrom(clazz)) {
                        Method put = clazz.getMethod("put", Object.class, Object.class);
                        put.invoke(t, String.join(",", heads), val);
                        existContent = StringUtil.isNotEmpty(val) || existContent; // 内容是否存在
                    }
                }
            }

            // 收集数据
            if (Objects.nonNull(t)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 获取map<excel头，字段>
     */
    private static <T> Map<String, Field> getHeadFieldMap(Class<T> clazz, List<List<String>> heads) {
        if (Map.class.isAssignableFrom(clazz)) {
            return null;
        }
        // 存储注解位置-字段
        Map<String, Field> indexMap = new HashMap<>(); // 字段-对象属性
        Field[] fields = clazz.getDeclaredFields();

        // 新表头
        if (!CollectionUtils.isEmpty(heads)) {
            // 取表头行数
            int max = heads.stream().map(List::size).reduce(Integer::max).orElse(0);

            for (int i = 0; i < heads.size(); i++) {
                List<Field> fs = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(ExcelProperty.class)).collect(Collectors.toList());

                List<String> value = new ArrayList<>(heads.get(i));
                // 不足时最后一个往后填充
                while (value.size() < max) {
                    value.add(value.get(value.size() - 1));
                }
                if (i < fs.size()) {
                    Field field = fs.get(i);
                    if (field.isAnnotationPresent(ExcelProperty.class)) {
                        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                        System.out.println(excelProperty);
                        field.setAccessible(true); // 设置私有字段可赋值
                        indexMap.put(JsonUtil.toJsonString(value), field);
                    }
                }
            }
        } else {
            // 取表头行数
            int max = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(ExcelProperty.class))
                    .map(field -> field.getAnnotation(ExcelProperty.class).value().length).reduce(Integer::max).orElse(0);

            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelProperty.class)) {
                    field.setAccessible(true); // 设置私有字段可赋值
                    ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                    // 没设置值或者设了一个空串时按字段名称处理
                    int vl = excelProperty.value().length;
                    List<String> value = vl == 0 || vl == 1 && "".equals(excelProperty.value()[0]) ? Stream.of(field.getName()).collect(Collectors.toList())
                            : Stream.of(excelProperty.value()).collect(Collectors.toList());
                    // 不足时最后一个往后填充
                    while (value.size() < max) {
                        value.add(value.get(value.size() - 1));
                    }
                    indexMap.put(JsonUtil.toJsonString(value), field);
                }
            }
        }
        return indexMap;
    }
}
