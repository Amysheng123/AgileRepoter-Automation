package com.lombardrisk.bus.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by amy sheng on 4/3/2018.
 */
public class PreferencePage extends AbstractPage {

    public PreferencePage(WebDriver driver) {
        super(driver);
    }

    public ListPage selectLanguageByValue(String language) throws Exception
    {
        element("prp.LC").click();
        waitStatusDlg();
        element("prp.LS").selectByValue(language);
        Thread.sleep(500);
        element("prp.confirm").click();
        waitStatusDlg();
        Thread.sleep(2000);
        return new ListPage(driver);
    }
}
