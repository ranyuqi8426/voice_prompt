package com.wsct.util.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {
	private static HSSFWorkbook workbook = null;  
	
	
	
	public static HSSFWorkbook createExcel(String[][] excel, String sheetName,String titleName,int columnNum) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();//设置边框及对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// sheet创建一个工作页
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow rowheader = sheet.createRow(0);
		rowheader.setHeightInPoints(25);
		HSSFCell cellheader = rowheader.createCell(0);
		cellheader.setCellValue(titleName);
		cellheader.setCellStyle(style);
		
		
		sheet.setDefaultColumnWidth((short) 12);
		CellRangeAddress region = new CellRangeAddress(0, // first row
		        0, // last row
		        0, // first column
		        columnNum // last column
		);
		sheet.addMergedRegion(region);
		
		
		for (short i = 1; i < excel.length; i++) {
			// HSSFRow,对应一行
			HSSFRow row = sheet.createRow(i);
			row.setHeightInPoints(20);
			for (short j = 0; j < excel[i].length; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(excel[i][j]);
				cell.setCellStyle(style);
			}
		}
		return wb;
	}
	private static HSSFSheet sheetss = null;
	public static boolean createExcelForTitle(String sheetName) {
		workbook = new HSSFWorkbook();
		// sheet创建一个工作页
		sheetss = workbook.createSheet(sheetName);
		return true;
	}
	
	
	
	public static HSSFWorkbook createExcel(String[][] excel,String titleName,int rowNum) {
		
		HSSFCellStyle style = workbook.createCellStyle();//设置边框及对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

		HSSFRow rowheader = sheetss.createRow(rowNum);
		rowheader.setHeightInPoints(25);
		HSSFCell cellheader = rowheader.createCell(0);
		cellheader.setCellValue(titleName);
		cellheader.setCellStyle(style);
		
		
		sheetss.setDefaultColumnWidth((short) 12);
		CellRangeAddress region = new CellRangeAddress(rowNum, // first row
				rowNum, // last row
		        0, // first column
		        5 // last column
		);
		sheetss.addMergedRegion(region);
		
		
		for (int i = 1; i < excel.length; i++) {
			// HSSFRow,对应一行
			HSSFRow row = sheetss.createRow(i+rowNum);
			row.setHeightInPoints(20);
			for (int j = 0; j < excel[i].length; j++) {
				// HSSFCell对应一格
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(excel[i][j]);
				cell.setCellStyle(style);
			}
		}
		return workbook;
	}
	
	
	/** 
     * 创建新excel. 
     * @param fileDir  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     */  
    public static HSSFWorkbook createExcel(String sheetName,String titleRow[],HttpServletResponse response){  
        //创建workbook  
        workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
            //添加标题  
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //创建第一行 标题    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }  
//            out = new FileOutputStream(fileDir);  
//            workbook.write(out);  
            
            OutputStream os = null;
    		String report_name = "导出文件";
    			response.setContentType("application/msexcel");
    			response.reset();
    			response.setHeader("content-disposition", "attachment; filename=" + new String(report_name.getBytes("gb2312"), "ISO-8859-1") + ".xls");
    			System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
    			os = response.getOutputStream();
    			workbook.write(os);
        } catch (Exception e) {  
				e.printStackTrace();
        } finally {    
//            try {    
////                out.close();    
//            } catch (IOException e) {    
//                e.printStackTrace();  
//            }    
        }
		return workbook;    
    }  
    /** 
     * 往excel中写入(已存在的数据无法写入). 
     * @param fileDir    文件路径 
     * @param sheetName  表格索引 
     * @param object 
     * @throws Exception 
     */  
//    public static void writeToExcel(String fileDir,String sheetName,List<Map> mapList) throws Exception{  
        //创建workbook  
//        File file = new File(fileDir);  
//        try {  
//            workbook = new HSSFWorkbook(new FileInputStream(file));  
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
        //流  
//        FileOutputStream out = null;  
//        HSSFSheet sheet = workbook.getSheet(sheetName);  
//        // 获取表格的总行数  
//        // int rowCount = sheet.getLastRowNum() + 1; // 需要加一  
//        // 获取表头的列数  
//        int columnCount = sheet.getRow(0).getLastCellNum()+1;  
//        try {  
//            // 获得表头行对象  
//            HSSFRow titleRow = sheet.getRow(0);  
//            if(titleRow!=null){ 
//                for(int rowId=0;rowId<mapList.size();rowId++){
//                    Map map = mapList.get(rowId);
//                    HSSFRow newRow=sheet.createRow(rowId+1);
//                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //遍历表头  
//                        String mapKey = titleRow.getCell(columnIndex).toString().trim().toString().trim();  
//                        HSSFCell cell = newRow.createCell(columnIndex);  
//                        cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());  
//                    } 
//                }
//            }  
//  
//            out = new FileOutputStream(fileDir);  
//            workbook.write(out);  
//        } catch (Exception e) {  
//            throw e;
//        } finally {    
//            try {    
//                out.close();    
//            } catch (IOException e) {    
//                e.printStackTrace();  
//            }    
//        }    
//    }  
//    
}
