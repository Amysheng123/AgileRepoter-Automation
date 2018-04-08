package com.lombardrisk.core.WebDriverUtils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


public class RemoteLogWebDriver extends RemoteWebDriver {
    private Logger logger;

   public RemoteLogWebDriver(URL url, Capabilities caps, Class<?> clazz){
       super(url,caps);
       logger = LoggerFactory.getLogger(clazz);
   }

    @Override
    public WebElement findElementByLinkText(String using) {

        try {
            WebElement element = super.findElementByLinkText(using);
            logger.info(using + "定位已找到元素");
            return element;
        } catch (NoSuchElementException e) {
            logger.error(using + "定位未找到元素");
            throw e;
        }

    }
    @Override
    public WebElement findElementByXPath(String using) {
        try {
            WebElement element = super.findElementByXPath(using);
            logger.info(using+"定位已找到元素");
            return element;
        } catch (NoSuchElementException e) {
            logger.error(using+"定位未找到元素");
            throw e;
        }
    }

    @Override
    public WebElement findElementByName(String using) {
        try {
            WebElement element = super.findElementByName(using);
            logger.info(using+"定位已找到元素");
            return element;
        } catch (NoSuchElementException e) {
            logger.error(using+"定位未找到元素");
            throw e;
        }
    }
}
