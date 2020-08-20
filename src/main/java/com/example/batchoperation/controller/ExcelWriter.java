package com.example.batchoperation.controller;

import com.example.batchoperation.entity.Weather;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author hou
 * @date 2020/8/18
 */
public class ExcelWriter {
    private static List<String> CELL_HEADS;
    //列头

    static{
        // 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
        CELL_HEADS = new ArrayList<>();
        CELL_HEADS.add("风速");
        CELL_HEADS.add("雨量");
        CELL_HEADS.add("大气温度");
        CELL_HEADS.add("雨量积累");
        CELL_HEADS.add("大气压力");
        CELL_HEADS.add("风向");
        CELL_HEADS.add("大气湿度");
        CELL_HEADS.add("噪声");
        CELL_HEADS.add("照度");
        CELL_HEADS.add("pm10");
        CELL_HEADS.add("pm25");
        CELL_HEADS.add("创建时间");
    }

    /**
     * 生成Excel并写入数据信息
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
    public static Workbook exportData(List<Weather> dataList){
        // 生成xlsx的Excel
        Workbook workbook = new SXSSFWorkbook();

        // 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls
        //Workbook workbook = new HSSFWorkbook();

        // 生成Sheet表，写入第一行的列头
        Sheet sheet = buildDataSheet(workbook);
        //构建每行的数据内容
        int rowNum = 1;
        for (Iterator<Weather> it = dataList.iterator(); it.hasNext(); ) {
            Weather data = it.next();
            if (data == null) {
                continue;
            }
            //输出行数据
            Row row = sheet.createRow(rowNum++);
            convertDataToRow(data, row);
        }
        return workbook;
    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    private static Sheet buildDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        // 设置列头宽度
        for (int i=0; i<CELL_HEADS.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(CELL_HEADS.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置

        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 右边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 将数据转换成行
     * @param data 源数据
     * @param row 行对象
     * @return
     */
    private static void convertDataToRow(Weather data, Row row){
        int cellNum = 0;
        Cell cell;

        //风速
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getWindSpeed());
        //雨量
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getRainfall());
        //大气温度
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getAtmosphericTemperature());
        //雨量积累
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getAccumulation());
        //大气压力
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getPressure());
        //风向
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getWindDirection());
        //大气湿度
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getAirHumidity());
        //噪声
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getNoise());
        //照度
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getIllumination());
        //pm10
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getPm10());
        //pm25
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getPm25());
        //创建时间
        cell = row.createCell(cellNum++);
        cell.setCellValue(data.getCreateTime());

    }
}
