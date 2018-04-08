package com.lombardrisk.bus.pages;


import org.openqa.selenium.WebDriver;

public class ListPage extends AbstractPage {

	public ListPage(WebDriver driver) {
		super(driver);
	}


	/**
	 * logout
	 *
	 * @return HomePage
	 * @throws Exception
	 */
	public HomePage logout() throws Exception
	{
		if (element("lp.message").isDisplayed())
			waitThat("lp.message").toBeInvisible();
		element("lp.uMenu").click();
		waitStatusDlg();
		element("lp.Logout").click();
		return new HomePage(getWebDriverWrapper());
	}

	/**
	 * enter preferencePage
	 *
	 * @return PreferencePage
	 * @throws Exception
	 */
	public PreferencePage enterPreferencePage() throws Exception
	{
		element("lp.uMenu").click();
		waitStatusDlg();
		element("lp.Preferences").click();
		waitStatusDlg();
		return new PreferencePage(getWebDriverWrapper());
	}

}
