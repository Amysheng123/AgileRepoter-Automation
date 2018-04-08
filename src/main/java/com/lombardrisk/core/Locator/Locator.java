package com.lombardrisk.core.Locator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Locator {
	public WebDriver driver;
	private String yamlFile;
	protected int waitTime = 20;
	private Map<String, Map<String, String>> locators;
	
	public Locator(WebDriver driver) {
		this.driver = driver;
	}
	
	private String getYamlFileName(String id) throws Exception{
		String fileName="";
		String path = System.getProperty("user.dir") + "/src/main/resources/locator";
		File f = new File(path);
		if(!f.exists()) {
			System.err.println(path + " not exists");
		}
		File fa[] = f.listFiles();
		for(int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			String name = fs.getName().replace(".", "#").split("#")[0];
			if(getExpectFileName(name).equalsIgnoreCase(id))
			{
				fileName = name;
				break;
			}
		}
		return path + "/" + fileName + ".yaml";
	}
	
	private String getExpectFileName(String fileName) throws Exception {
		String name = "";
		for(char c:fileName.toCharArray()) {
			if((c>='A')&&(c<='Z')) {
				name = name+c;
			}
		}
		return name;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> loadYamlFile(String fileName) throws Exception{
		File file=new File(fileName);
		return Yaml.loadType(file, HashMap.class);
	}
	
	private By getBy(String type,String value) {
		By by = null;
		if(type.equalsIgnoreCase("id")) {
			by = By.id(value);
		}
		if(type.equalsIgnoreCase("name")) {
			by = By.name(value);
		}
		if(type.equalsIgnoreCase("xpath")) {
			by = By.xpath(value);
		}
		if(type.equalsIgnoreCase("className")) {
			by = By.className(value);
		}
		if(type.equalsIgnoreCase("linkText")) {
			by = By.linkText(value);
		}
		return by;
	}
	
	public WebElement getElement(String idKey, String... replace) {
		String id = idKey.replace(".", "#").split("#")[0];
		String key = idKey.replace(".", "#").split("#")[1];
		try {
			yamlFile = getYamlFileName(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			locators = this.loadYamlFile(yamlFile);
		} catch (Exception e) {
			e.printStackTrace();
			locators = new HashMap<String, Map<String, String>>();
		}
		String type = locators.get(key).get("type");
		String value = locators.get(key).get("value");
		
		return driver.findElement(getBy(type, value));
	}
	
	
}
