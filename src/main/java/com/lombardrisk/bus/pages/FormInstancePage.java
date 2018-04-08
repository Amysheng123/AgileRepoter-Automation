package com.lombardrisk.bus.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by amy sheng on 4/3/2018.
 */
public class FormInstancePage extends AbstractPage {

    public EntityPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Close formInstancePage
     *
     * @return ListPage
     * @throws Exception
     */
    public ListPage closeFormInstance() throws Exception
    {
        if (element("fp.form").isDisplayed())
        {
            if (element("fp.message").isDisplayed())
                waitThat("fp.message").toBeInvisible();
            if (element("fp.importDlgmodal").isDisplayed())
                waitThat("fp.importDlgmodal").toBeInvisible();

            if (element("fp.close").isDisplayed())
            {
                logger.info("Close form");
                Thread.sleep(2000);
                element("fp.close").click();
                waitThat("fp.close").toBeInvisible();
            }
        }

        return new ListPage(driver);
    }
}
