package com.lombardrisk.bus.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
	ListPage listPage = null;

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public ListPage logon() throws Exception {
		l.getElement("hp.userName").sendKeys(userName);
		l.getElement("hp.password").sendKeys(password);
		l.getElement("hp.login").click();
		return new ListPage(driver);
	}
/*
	public void typeUsername(String username) throws Exception {
		l.getElement("hp.userName").sendKeys(username);
	}

	public void typePassword(String password) throws Exception {
		l.getElement("hp.password").sendKeys(password);
	}

	public ListPage submitLogin() throws Exception {
		l.getElement("hp.login").click();
	}*/

	public ListPage loginAs(String username, String password) throws Exception
	{
		logger.info("Login RP with user[" + username + "]");
		l.getElement("hp.userName").sendKeys(username);
		l.getElement("hp.password").sendKeys(password);
		l.getElement("hp.login").click();
		return new ListPage(driver);
/*
		String currFormat = getFormatFromDB(username);
		String expectedLang = PropHelper.getProperty("Regional.language").trim();
		logger.info("Expected language is:" + expectedLang);
		if (currFormat == null || !currFormat.equalsIgnoreCase(expectedLang))
		{
			logger.info("update user language and format");
			PreferencePage preferencePage = listPage.enterPreferencePage();
			preferencePage.selectLanguageByValue(expectedLang);
		}
		return listPage;*/
	}
}
