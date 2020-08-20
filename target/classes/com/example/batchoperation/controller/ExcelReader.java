package com.example.batchoperation.controller;

import com.example.batchoperation.entity.Weather;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Description: 读取Excel内容
 *
 * @author 学无止境~冲
 */
public class ExcelReader {

    private static Logger logger = Logger.getLogger(ExcelReader.class.getName());
    // 日志打印类

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     *
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Weather> readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<Weather> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 读取Excel文件内容
     *
     * @param file 上传的Excel文件
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Weather> readExcel(MultipartFile file) {

        Workbook workbook = null;

        try {
            // 获取Excel后缀名
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty() || fileName.lastIndexOf(".") < 0) {
                logger.warning("解析Excel失败，因为获取到的Excel文件名非法！");
                return null;
            }
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

            // 获取Excel工作簿
            workbook = getWorkbook(file.getInputStream(), fileType);


            // 读取excel中的数据
            List<Weather> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + file.getOriginalFilename() + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Weather> parseExcel(Workbook workbook) {
        List<Weather> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                Weather resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }

    /**
     * 将单元格内容转换为double
     *
     * @param cell
     * @return
     */
    private static double convertCellValueToDouble(Cell cell) {
        if (cell == null) {
            return 0;
        }

        return cell.getNumericCellValue();
    }


    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
//    private static String convertCellValueToString(Cell cell) {
//        if(cell==null){
//            return null;
//        }
//        String returnValue = null;
//        switch (cell.getCellType()) {
//            case NUMERIC:   //数字
//                Double doubleValue = cell.getNumericCellValue();
//
//                // 格式化科学计数法，取一位整数
//                DecimalFormat df = new DecimalFormat("0");
//                returnValue = df.format(doubleValue);
//                break;
//            case STRING:    //字符串
//                returnValue = cell.getStringCellValue();
//                break;
//            case BOOLEAN:   //布尔
//                Boolean booleanValue = cell.getBooleanCellValue();
//                returnValue = booleanValue.toString();
//                break;
//            case BLANK:     // 空值
//                break;
//            case FORMULA:   // 公式
//                returnValue = cell.getCellFormula();
//                break;
//            case ERROR:     // 故障
//                break;
//            default:
//                break;
//        }
//        return returnValue;
//    }

    /**
     * 判断是否为null或空串
     *
     * @param cell
     * @return
     */
    public static Date convertCellValueToDate(Cell cell) {
        if (cell == null || cell.toString().trim().equals("")) {
            return null;
        }
        Date cellValue = new Date();
        if (cell.getCellType() == CellType.NUMERIC) {
            // 数字
            short format = cell.getCellStyle().getDataFormat();
            if (DateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = null;
                if (format == 20 || format == 32) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                } else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
                try {
                    cellValue = cell.getDateCellValue();
                } catch (Exception e) {
                    try {
                        throw new Exception("exception on get date data !".concat(e.toString()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } finally {
                    sdf = null;
                }
            }
        }
        return cellValue;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static Weather convertRowToData(Row row) {
        Weather weather = new Weather();

        Cell cell;
        int cellNum = 0;

        // 获取风速
        cell = row.getCell(cellNum++);
        double windSpeed = convertCellValueToDouble(cell);
        weather.setWindSpeed(windSpeed);

        // 获取降雨量
        cell = row.getCell(cellNum++);
        double rainfall = convertCellValueToDouble(cell);
        weather.setRainfall(rainfall);

        // 获取大气温度
        cell = row.getCell(cellNum++);
        double atmosphericTemperature = convertCellValueToDouble(cell);
        weather.setAtmosphericTemperature(atmosphericTemperature);

        //获取雨量积累
        cell = row.getCell(cellNum++);
        double accumulation = convertCellValueToDouble(cell);
        weather.setAccumulation(accumulation);

        //获取大气压力
        cell = row.getCell(cellNum++);
        double pressure = convertCellValueToDouble(cell);
        weather.setPressure(pressure);

        //获取风向
        cell = row.getCell(cellNum++);
        double windDirection = convertCellValueToDouble(cell);
        weather.setWindDirection(windDirection);

        //获取大气温度
        cell = row.getCell(cellNum++);
        double airHumidity = convertCellValueToDouble(cell);
        weather.setAirHumidity(airHumidity);

        //获取噪声
        cell = row.getCell(cellNum++);
        double noise = convertCellValueToDouble(cell);
        weather.setNoise(noise);

        //获取照度
        cell = row.getCell(cellNum++);
        double illumination = convertCellValueToDouble(cell);
        weather.setIllumination(illumination);

        //获取pm10
        cell = row.getCell(cellNum++);
        double pm10 = convertCellValueToDouble(cell);
        weather.setPm10(pm10);

        //获取pm25
        cell = row.getCell(cellNum++);
        double pm25 = convertCellValueToDouble(cell);
        weather.setPm25(pm25);

        //获取创建时间
        cell = row.getCell(cellNum++);
        Date createTime = convertCellValueToDate(cell);
        weather.setCreateTime(createTime);

        return weather;
    }


}
