package com.why.transportsecurity_finally.utils;

import com.why.transportsecurity_finally.entity.InfoExcelModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Description TODO 文件下载
 * @Author why
 * @Date 2021/7/22 11:06
 * Version 1.0
 **/
public class OutputFileUtils {
    public static void outputFile(List<InfoExcelModel> list, HttpServletResponse response){
        //新建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //列名数组
        String[] columnNames = {"车主","车牌号","车辆类型","驾驶员损伤情况","后排乘员损伤情况","日期","时间","经度","纬度","地址","碰撞方向","安全气囊是否弹开","横向加速度","纵向加速度"};

        //新建sheet
        XSSFSheet sheet = workbook.createSheet();
        //新建字体
        Font font = workbook.createFont();
        font.setFontName("simsun");
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.index);

        //新建样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        titleStyle.setFont(font);

        //创建一行
        Row titleRow = sheet.createRow(0);
        //设置一行的单元格的值
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }

        String number = null;
        //添加数据
        int lastRowNum = 0;
        for (int i = 0; i < list.size(); i++) {
            InfoExcelModel infoExcelModel = list.get(i);
            lastRowNum = lastRowNum + 1;
            //创建新行
            Row dataRow = sheet.createRow(lastRowNum);
            //创建单元格添加数据
            //"车主","车牌号","车辆类型","损伤情况","日期","时间","经度","纬度","地址","碰撞方向","安全气囊是否弹开","横向加速度","纵向加速度"
            dataRow.createCell(0).setCellValue(infoExcelModel.getVName());
            number = infoExcelModel.getVNumber();
            dataRow.createCell(1).setCellValue(infoExcelModel.getVNumber());
            dataRow.createCell(2).setCellValue(infoExcelModel.getVType());
            dataRow.createCell(3).setCellValue(infoExcelModel.getDriverDegreeOfDamage());
            dataRow.createCell(4).setCellValue(infoExcelModel.getPassengerDegreeOfDamage());
            dataRow.createCell(5).setCellValue(infoExcelModel.getDate());
            dataRow.createCell(6).setCellValue(infoExcelModel.getTime());
            dataRow.createCell(7).setCellValue(infoExcelModel.getLng());
            dataRow.createCell(8).setCellValue(infoExcelModel.getLat());
            dataRow.createCell(9).setCellValue(infoExcelModel.getAddress());
            dataRow.createCell(10).setCellValue(infoExcelModel.getCollisionDirection());
            dataRow.createCell(11).setCellValue(infoExcelModel.getIsBounce());
            dataRow.createCell(12).setCellValue(infoExcelModel.getAdRAcceleration().get(0));
            dataRow.createCell(13).setCellValue(infoExcelModel.getAdCAcceleration().get(0));
        }
        if (list != null && list.size() > 0 ){
            InfoExcelModel infoExcelModel = list.get(0);
            int max = 0;
            boolean flag = true;
            if (infoExcelModel.getAdRAcceleration().size() >= infoExcelModel.getAdCAcceleration().size()){
                max = infoExcelModel.getAdRAcceleration().size();
                flag = true;
            }else {
                max = infoExcelModel.getAdCAcceleration().size();
                flag = false;
            }
            int lastRowNumR = 1;
            for (int i = 0; i < max-1; i++) {
                Row dataRow = sheet.createRow(lastRowNumR + 1);
                double ax = 0.000;
                double ay = 0.000;
                if (flag){
                    if (i > infoExcelModel.getAdCAcceleration().size() - 2){
                        ay = 0.000;
                    }else {
                        ay = infoExcelModel.getAdCAcceleration().get(i+1);
                    }
                    ax = ax = infoExcelModel.getAdRAcceleration().get(i+1);
                }else {
                    if (i > infoExcelModel.getAdRAcceleration().size() -2){
                        ax = 0.000;
                    }else {
                        ax = infoExcelModel.getAdRAcceleration().get(i+1);
                    }
                    ay = infoExcelModel.getAdCAcceleration().get(i+1);
                }
                dataRow.createCell(12).setCellValue(ax);
                dataRow.createCell(13).setCellValue(ay);
                lastRowNumR++;
            }

        }

        try {
            response.setContentType("application/vnd.ms-excel");
            if (number != null){
                response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(number+".xlsx", "utf-8"));
            }else {
                response.setHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode("data.xlsx", "utf-8"));
            }
            response.setHeader("Access-Control-Expose-Headers", "content-Disposition");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
