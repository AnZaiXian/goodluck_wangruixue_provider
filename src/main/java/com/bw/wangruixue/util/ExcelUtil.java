package com.bw.wangruixue.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * 
 * @Action Excel工具类
 * @author 周孟禹
 * @date 2017年6月19日
 */
public class ExcelUtil {

	public static void excelOut(List list,Workbook wb,String[] str) throws Exception{
		int row=0;//第一行
		Sheet sheet = wb.createSheet("导出sheet");
		Row createRow = sheet.createRow(row);
		for (int i = 0; i < str.length; i++) {
			//将传来的数组设置给下标为i的单元格
			createRow.createCell(i).setCellValue(str[i]);;
		}
		for (int j = 0; j < list.size(); j++) {//遍历list
			System.out.println(row);
			createRow=sheet.createRow(++row);//并创建新行
			for (int i = 0; i < str.length; i++) {//根据数组的长度循环赋值
				String string = list.get(j).toString();//得到list的每个值
				String[] split = string.split(",");//逗号分隔
				for (int k = 0; k < split.length; k++) {
					System.out.println(split[k]);
					//将分隔好的数组赋值给索引为k的单元格
					createRow.createCell(k).setCellValue(split[k]);
				}
			}
		}
	}
	public static void export(HttpServletResponse response,Workbook wb,String fileName)throws Exception{
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
		response.setContentType("application/ynd.ms-excel;charset=UTF-8");//
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.flush();
		out.close();
	}
	/*
	 * 模板
	 */
	public static Workbook excelOutModel(List list,String str) throws Exception{
		//读取模板
		InputStream is = ExcelUtil.class.getResourceAsStream("/com/poi/model/"+str);
		POIFSFileSystem ps=new POIFSFileSystem(is);//读取流
		Workbook wb=new HSSFWorkbook(ps);//将读取到的流创建工作薄
		
		Sheet sheet = wb.getSheetAt(0);//得到第一个工作薄
		//获取列数
		int lastCellNum = sheet.getRow(0).getLastCellNum();
		int row=1;//第一行
		Row createRow = sheet.createRow(row);
		for (int j = 0; j < list.size(); j++) {//遍历查询到的list
			createRow=sheet.createRow(row++);//并创建新行
			for (int i = 0; i < lastCellNum; i++) {//根据列数循环赋值
				String string = list.get(j).toString();//得到list的每个值
				String[] split = string.split(",");//逗号分隔
				for (int k = 0; k < split.length; k++) {
					System.out.println(split[k]);
					//将分隔好的数组赋值给索引为k的单元格
					createRow.createCell(k).setCellValue(split[k]);
				}
			}
		}
		return wb;
	}
	/*
	 * 类型
	 */
	public static String formatCell(HSSFCell hssfCell){
		if(hssfCell==null){
			return "";
		}else{
			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(hssfCell.getBooleanCellValue());
			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
	}
}
