package com.lombardrisk.core.utils.fileService;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelutils {
	public static Object[][] getExcelData(String filename) {
		Object[][] objs = null;
		InputStream is = ReadExcelutils.class.getClassLoader().getResourceAsStream(filename);
		Workbook wb = null;

		try {
			wb = new XSSFWorkbook(is);
			Sheet sheet = wb.getSheet("Sheet1");
			int lastRowNum = sheet.getLastRowNum();// rownum 是从零开始
			Row titlerow = sheet.getRow(0);
			int lastCellNum = titlerow.getLastCellNum();// col是从1开始的
			objs = new Object[lastRowNum][lastCellNum];
			for (int i = 0; i < objs.length; i++) {
				Row row = sheet.getRow(i + 1);
				for (int j = 0; j < objs[i].length; j++) {
					objs[i][j] = getObject(row.getCell(j));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return objs;
	}


	private static Object getObject(Cell cell) {
		Object obj = null;
		switch (cell.getCellTypeEnum()) {
			case BOOLEAN:
				obj = cell.getBooleanCellValue();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					obj = cell.getDateCellValue();
				} else {
					obj = DataParse .double2int(cell.getNumericCellValue());
				}
				break;
			case STRING:
				obj = cell.getStringCellValue();
				break;
			default:
				obj = null;
		}
		return obj;
	}

}
