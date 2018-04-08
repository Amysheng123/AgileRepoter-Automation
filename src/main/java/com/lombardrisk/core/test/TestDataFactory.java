package com.lombardrisk.core.test;

import java.util.Properties;

import com.lombardrisk.core.utils.fileService.ReadCSVUtils;
import com.lombardrisk.core.utils.fileService.ReadDatabaseUtils;
import com.lombardrisk.core.utils.fileService.ReadExcelutils;
import org.testng.annotations.DataProvider;

public class TestDataFactory {

	@DataProvider(name="calc_test_data")
	public static Object[][] getCalcTestData(){
		Object[][] objs = new Object[][] {
			{10,20,30},
			{30,20,10},
			{30,30,60}};
		return objs;
	}
	
	@DataProvider(name="calc_excel_data")
	public static Object[][] getCalcexcelData(){
		return ReadExcelutils.getExcelData("calcTestData.xlsx");
	}
	
	@DataProvider(name="calc_CSV_data")
	public static Object[][] getecshopCSVData(){
		Properties props = System.getProperties();
		Object[][] objs = ReadCSVUtils.getPlainCSVData("ecshopLoginTestData.csv");
		for(int i = 0; i<objs.length; i++) {
			objs[i][1] = objs[i][1] + "";
			System.out.println(objs[i][2]);
		}
		return objs;
	}
	
	@DataProvider(name="calc_mysql_data")
	public static Object[][] getCalcMysqlData(){
		String url = "jdbc:mysql:///cloud5?characterEncoding=UTF8&serverTimezone=CST";
		String username = "root";
		String password = "";
		String tableName = "calctestdata";
		return ReadDatabaseUtils.getDataFromDB(url, username, password, tableName);
	}
/*	@DataProvider
	public static Objects[][] providerMethod(Method method){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List l = XMLutil.getXmlData();
		for(int i = 0; i < l.size(); i++){
			Map m = (Map) l.get(i);
			if(m.containsKey(method.getName())){
				Map<String, String> dm = (Map<String, String>) m.get(method.getName());
				result.add(dm);
			}
		}
		Objects[][] files = new Objects[result.size()][];
		for(int i = 0; i<result.size(); i++){
			files[i] = new Objects[]{result.get(i)};
		}
		return files;
	}*/
}
