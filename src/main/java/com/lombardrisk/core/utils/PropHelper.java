package com.lombardrisk.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;


/**
 * Created by Leo Tu on 4/18/2017.
 */


public class PropHelper{
    private final static Logger logger = LoggerFactory.getLogger(PropHelper.class);
    private static final Properties props = new Properties();
    private static boolean hasLoaded = false;
    public static final String BROWSER = getProperty("browser.default");
    public static final String WAITTIME = getProperty("browser.waitTime");
    public static final String SERVER_INFO = getProperty("server.info");
    public static final String DATE_FORMAT = getProperty("Regional.language");

/*    public static final String DISCRIMINATOR_KEY = getProperty("discriminator.key");
    public static final boolean REMOTE = Boolean.parseBoolean(getProperty("remote"));
    public static final String RESULT_FOLDER = getProperty("path.result.folder");
    public static final String SCREENSHOT_FOLDER = getProperty("path.screenshot.folder");
    public static final String LOG_FOLDER = getProperty("path.log.folder");
    public static final String HAR_FOLDER = getProperty("path.har.folder");
    public static final String DEFAULT_LANG = getProperty("lang.default");
    public static final String DATA_SCHEMA_FILE = getProperty("path.data.schema");
    public static final String LOCATORS_FILE = getProperty("path.locator");
    public static final String FEATURE_DATA_FOLDER = getProperty("path.source.feature.data.folder");
    public static final String SOURCE_SCENARIO_DATA_FOLDER = getProperty("path.source.scenario.data.folder");
    public static final String TARGET_SCENARIO_DATA_FOLDER = getProperty("path.target.scenario.data.folder");
    public static final String DOWNLOAD_FOLDER = getProperty("path.download.folder");
    public static final String MAPS_FOLDER = getProperty("path.maps.folder");
    public static final String REMOTE_ADDRESS = getProperty("remote.address");

    @Deprecated
    public static final String CURRENT_LANG = getProperty("lang.current");
    public static final String FIREFOX_PATH = getProperty("path.chrome");
    public static final String CHROME_WEBDRIVER = getProperty("path.webdriver.chrome");
    public static final String IE_WEBDRIVER = getProperty("path.webdriver.ie");*/

    private static void load(String file) {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(file)) {
            if (is != null) {
                props.load(is);
                is.close();
            } else {
                logger.warn(format("%s was not provided", file));
            }
        } catch (IOException e) {
            logger.error(file, e);
        }
    }

    private static boolean load() {
        load(System.getProperty("test.prop", "test.properties"));
        return true;
    }

    public static String getProperty(String key){
        if(!hasLoaded) {
            hasLoaded = load();
        }
        return System.getProperty(key, props.getProperty(key));
    }

    public static String getServerInfo(String key) {
        String serverInfo = "";

        String jsonfile = getProperty(key);
        try (InputStream is = ClassLoader.getSystemResourceAsStream(jsonfile)) {
            if (null != is) {
                serverInfo = IOUtils.toString(is, "UTF-8");
                is.close();
            } else {
                serverInfo = FileUtils.readFileToString(new File(jsonfile), "UTF-8");
            }
        } catch (IOException e)
        {
            logger.error(jsonfile, e);
        }
        logger.info("server information:\n" + serverInfo);
        return serverInfo;
    }
}