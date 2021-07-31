package com.why.transportsecurity_finally.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO excel文件读取
 * @Author why
 * @Date 2021/7/22 14:44
 * Version 1.0
 **/
public class ExcelUtils {
    /**
     * 读取excel
     * @param path
     * @return
     */
    public static List<List<String>> readExcel(String path){
        List<List<String>> list=new ArrayList<List<String>>();
        try {
            Workbook wb;
            InputStream is=null;
            try{
                is=new FileInputStream(path);
                //读取2007版Excel
                wb=new XSSFWorkbook(is);
            }catch(Exception e){
                //防止异常导致输入流关闭
                is=new FileInputStream(path);
                //读取2003版Excel
                wb=new HSSFWorkbook(is);
            }
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                //读取Sheet
                Sheet sheet=wb.getSheetAt(i);
                if(sheet==null){
                    continue;
                }
                //处理当前页，循环每一行
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    //得到当前行
                    Row row=sheet.getRow(j);
                    //当前行第一个单元格
                    int minCells=row.getFirstCellNum();
                    //当前行最后一个单元格
                    int maxCells=row.getLastCellNum();
                    List<String> sl=new ArrayList<String>();
                    for (int k = minCells; k < maxCells; k++) {
                        //每一个单元格
                        Cell cell=row.getCell(k);
                        if(cell==null){
                            continue;
                        }
                        sl.add(cell.toString());
                    }
                    list.add(sl);
                }
            }
            if(is!=null){
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
