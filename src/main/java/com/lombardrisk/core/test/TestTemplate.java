package com.lombardrisk.core.test;

import com.lombardrisk.pojo.ServerManager;
import com.lombardrisk.pojo.TestCapability;
import com.lombardrisk.pojo.TestEnvironment;
import com.lombardrisk.bus.pages.*;
import com.lombardrisk.core.WebDriverUtils.WebDriverUtils;
import com.lombardrisk.core.utils.DBQuery;
import com.lombardrisk.core.utils.PropHelper;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * Created by amy sheng on 4/3/2018.
 */
public class TestTemplate {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String downloadFile;//last download file name by relative path
    private String defaultDownloadFileName;//default name of download file
    private String transactionName;//unique http archive file name
    private String suiteName;//testng test suite name
    private String testName;//testng test name

    private String scenarioId;
    private String featureId;
    private boolean skipTest;//whether to skip next execution of left test methods
    private boolean prepareToDownload;
    private boolean recycleTestEnvironment;
    private Map<String, String> testMap;
/*    private Subject subject;
    private IWebDriverWrapper webDriverWrapper;
    private ITestDataManager testDataManager;
    private IPageManager pageManager;
    private TimestampWriter timestampWriter;
    private SoftAssertions softAssertions;
    private ProxyWrapper proxyWrapper;*/
    private TestCapability testCapability;
    private Charset downloadFileCharset;
    private FileFormat downloadFileFormat;

    protected static String targetLogFolder = System.getProperty("user.dir") + "/target/result/logs/";
    protected static String testDataFolderName = PropHelper.getProperty("data.type").trim();
    protected static boolean startService = Boolean.parseBoolean(PropHelper.getProperty("test.startService").trim());
    protected static boolean setOriginalName = Boolean.parseBoolean(PropHelper.getProperty("getOriginalName").trim());
    protected static String envPath = PropHelper.getProperty("test.environment.path").trim();
    protected static boolean httpDownload = Boolean.parseBoolean(PropHelper.getProperty("download.enable").trim());
    protected static String testData_admin = null;
    protected static String testData_DeleteReturn = null;
    protected static String testData_edition = null;
    protected static String testData_editForm = null;
    protected static String testData_highlight = null;
    protected static String testData_Utility = null;
    protected static String testData_Workflow = null;
    protected static File editFormLogData = null;
    protected static String jobData = null;
    protected static String testData_General = null;
    protected static File testRstFile = null;
    protected static String testData_updateForm = null;
    protected static String testData_OtherModule = null;
    protected static String testData_FormVariable = null;
    protected static String testData_RowLimit = null;
    protected static String testData_Threshold = null;
    protected static String testData_DropDown = null;
    protected static String testData_GridWithinGrid = null;
    protected static String testData_Contextual = null;
    protected static String testData_Calendar = null;
    protected static String testData_ReturnList = null;
    protected static String testData_importExportFormat = null;
    protected static String testData_Export_External = null;
    protected static String testData_BatchRun = null;
    protected static String parentPath = null;
    protected static String testData_ExportForm2 = null;

    protected static String format = "";
    protected static String userName = "";
    protected static String AR_DBName = "";
    protected static String T_DBName = "";
    protected static String AR_DBType = "";
    protected static String T_DBType = "";
    protected static String AR_Server = "";
    protected static String T_Server = "";
    protected static String AR_IP = "";
    protected static String T_IP = "";
    protected static String AR_SID = "";
    protected static String T_SID = "";
    protected static String ConnectDBType = "";
    protected static String password = "password";
    static File nameFile = null;
    static TestEnvironment testEnvironment;
    protected Module m;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
    String curDate = sdf.format(new Date());
    WebDriver driver = null;

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getFeatureId() {
        return featureId;
    }

    public TestCapability getTestCapability() {
        return testCapability;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public void setTestCapability(TestCapability testCapability) {
        this.testCapability = testCapability;
    }

/*    public static TestEnvironment getTestEnvironment() {
        return testEnvironment;
    }*/

    public static void setTestEnvironment(TestEnvironment environment) {
        TestTemplate.testEnvironment = environment;
    }

/*    public void setUpTest() throws Exception {
        setUpTest(true);
    }*/

    /**
     * set test data
     *
     * @throws Exception
     */
    @BeforeSuite
    protected void beforeSuite() throws Exception
    {
        if (startService)
        {
            envPath = envPath + "/bin/start.bat";
            if (new File(envPath).exists())
            {
                logger.info("Begin start ar service");
                logger.info("Test env path is: " + envPath);
                Runtime.getRuntime().exec("cmd /c start " + envPath);
                logger.info("Starting ...waiting 90s");
                Thread.sleep(1000 * 90);
            }
        }

        nameFile = new File("target/result/Names.txt");
        if (nameFile.exists())
        {
            nameFile.delete();
        }
        nameFile.createNewFile();

        if (testDataFolderName.equalsIgnoreCase("ar"))
        {
            ConnectDBType = "ar";
            testDataFolderName = "data_ar";
        }
        else if (testDataFolderName.equalsIgnoreCase("toolSet"))
        {
            testDataFolderName = "data_toolset";
            ConnectDBType = "toolSet";
        }
        else if (testDataFolderName.equalsIgnoreCase("toolsetNull"))
        {
            testDataFolderName = "data_toolset_allownull";
            ConnectDBType = "toolSet";
        }

        testData_admin = System.getProperty("user.dir") + "/" + testDataFolderName + "/Admin/Admin.xml";
        testData_DeleteReturn = System.getProperty("user.dir") + "/" + testDataFolderName + "/DeleteReturn/DeleteReturn.xml";
        testData_edition = System.getProperty("user.dir") + "/" + testDataFolderName + "/Edition/Edition.xml";
        testData_editForm = System.getProperty("user.dir") + "/" + testDataFolderName + "/EditForm/EditForm.xml";
        editFormLogData = new File(testData_editForm.replace("EditForm.xml", "EditForm_Data_Log.xlsx"));
        testData_highlight = System.getProperty("user.dir") + "/" + testDataFolderName + "/HighLight/HighLight.xml";
        testData_Utility = System.getProperty("user.dir") + "/" + testDataFolderName + "/Utility/Utility.xml";
        testData_Workflow = System.getProperty("user.dir") + "/" + testDataFolderName + "/Workflow/Workflow.xml";
        testData_General = System.getProperty("user.dir") + "/" + testDataFolderName + "/GeneralFunction/GeneralFunction.xml";
        jobData = System.getProperty("user.dir") + "/" + testDataFolderName + "/Job/Job.xml";
        testData_updateForm = System.getProperty("user.dir") + "/" + testDataFolderName + "/UpdateForm/UpdateForm.xml";
        testData_OtherModule = System.getProperty("user.dir") + "/" + testDataFolderName + "/OtherModule/OtherModule.xml";
        testData_FormVariable = System.getProperty("user.dir") + "/" + testDataFolderName + "/Admin/FormVariable.xml";
        testData_RowLimit = System.getProperty("user.dir") + "/" + testDataFolderName + "/RowLimit/RowLimit.xml";
        testData_Threshold = System.getProperty("user.dir") + "/" + testDataFolderName + "/Threshold/Threshold.xml";
        testData_DropDown = System.getProperty("user.dir") + "/" + testDataFolderName + "/DropDown/DropDown.xml";
        testData_GridWithinGrid = System.getProperty("user.dir") + "/" + testDataFolderName + "/GridWithinGrid/GridWithinGrid.xml";
        testData_Contextual = System.getProperty("user.dir") + "/" + testDataFolderName + "/Contextual/Contextual.xml";
        testData_Calendar = System.getProperty("user.dir") + "/" + testDataFolderName + "/Admin/Calendar.xml";
        testData_ReturnList = System.getProperty("user.dir") + "/" + testDataFolderName + "/ReturnList/ReturnList.xml";
        testData_importExportFormat = System.getProperty("user.dir") + "/" + testDataFolderName + "/ImportExportFormat/ImportExportFormat.xml";
        testData_Export_External = System.getProperty("user.dir") + "/" + testDataFolderName + "/ExportForm_External/ExportForm_External.xml";
        testData_BatchRun = System.getProperty("user.dir") + "/" + testDataFolderName + "/BatchRun/BatchRun.xml";
        testData_ExportForm2 = System.getProperty("user.dir") + "/" + testDataFolderName + "/ExportForm2/ExportForm2.xml";

        parentPath = new File(new File(System.getProperty("user.dir")).getParent()).getParent().toString();

        File testRstFolder = new File("target/TestResult");
        if (!testRstFolder.exists())
            testRstFolder.mkdir();

        File logFolder = new File(targetLogFolder);
        if (!logFolder.exists())
            logFolder.mkdir();

    }

/*
    @BeforeClass(dependsOnMethods=
            { "beforeClass" })
    protected void setUpClass() throws Exception
    {
    }
*/

    @BeforeMethod
    protected void setMethod() throws Exception
    {
        logger.info("set up  before method");
        try
        {
            setFeatureId(this.getClass().getSimpleName().toLowerCase());
            setScenarioId(getFeatureId());

            //testEnvironment = ServerManager.getTestEnvironments();
            userName = ServerManager.TEST_ENVIRONMENTS.get(0).getApplicationServers().get(0).getUsername();
            password = TEST_ENVIRONMENTS.getApplicationServer(0).getPassword();

            AR_DBName = TEST_ENVIRONMENTS.getDatabaseServer(0).getSchema();
            AR_DBType = TEST_ENVIRONMENTS.getDatabaseServer(0).getDriver();
            AR_Server = TEST_ENVIRONMENTS.getDatabaseServer(0).getHost();
            if (AR_DBType.equalsIgnoreCase("oracle"))
            {
                AR_IP = AR_Server.split("@")[0];
                AR_SID = AR_Server.split("@")[1];
            }
            try
            {
                T_DBName = getTestEnvironment().getDatabaseServer(1).getSchema();
                T_DBType = getTestEnvironment().getDatabaseServer(1).getDriver();
                T_Server = getTestEnvironment().getDatabaseServer(1).getHost();
                if (AR_DBType.equalsIgnoreCase("oracle"))
                {
                    T_IP = AR_Server.split("@")[0];
                    T_SID = AR_Server.split("@")[1];
                }
            }
            catch (Exception e)
            {
                //
            }
            driver = WebDriverUtils.getDriver(PropHelper.BROWSER, this.getClass());
            String wt = PropHelper.WAITTIME;
            driver.manage().timeouts().implicitlyWait(Long.parseLong(PropHelper.WAITTIME), TimeUnit.SECONDS);
            driver.navigate().to(getTestEnvironment().getApplicationServer(0).getUrl());
            report(Helper.getTestReportStyle(getTestEnvironment().getApplicationServer(0).getUrl(), "open test server url"));

            m = new Module(this);
            m.homePage.logon();

        }
        catch (Exception e)
        {
            logger.error("error", e);
        }

    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod(ITestContext testContext, Method method, ITestResult testResult) throws Exception
    {
        if (testResult.getThrowable() != null)
            logger.error(method.getName(), testResult.getThrowable());

        logger.info("tearDown after method");
        tearDownTest();
    }

    public void tearDownTest() throws Exception {
        logger.info("teardown test after finishing feature id {}, scenario id {}", featureId, scenarioId);
        if (recycleTestEnvironment) {
            ServerManager.offerTestEnvironment(testEnvironment);
            recycleTestEnvironment = false;
        }

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
                logger.error(ignored.getMessage(), ignored);
            }
        }
    }

    @AfterSuite
    public void SyncQC() throws Exception
    {
        WebDriverUtils.stopService();
    }



    public void report(String s) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String now = df.format(new Date());
        Reporter.log(now + " " + this.getClass().getName() + " " + s + "<br>");
    }

    /**
     * close formInstancePage
     *
     * @throws Exception
     */
    public void closeFormInstance() throws Exception
    {
        FormInstancePage formInstancePage = m.formInstancePage;
        try
        {
            formInstancePage.closeFormInstance();
        }
        catch (Exception e)
        {
            driver.navigate().back();
        }
    }

    /**
     * Logout
     *
     * @throws Exception
     */
    public void logout() throws Exception
    {
        closeFormInstance();
        try
        {
            ListPage listPage = new ListPage(driver);
            listPage.logout();
        }
        catch (Exception e)
        {
            logger.warn("warn", e);
        }
    }


    public ListPage loginAsOtherUser(String userName, String password) throws Exception
    {
        ListPage listPage = m.listPage;
        HomePage homePage = listPage.logout();
        homePage.loginAs(userName, password);
        String SQL = "SELECT MAX(\"ID\") FROM \"USR_PREFERENCE\" WHERE \"USER_ID\"='" + userName.toLowerCase() + "' and \"PREFERENCE_NAME\"='LANGUAGE'";
        String id = DBQuery.queryRecord(SQL);
        SQL = "SELECT \"PREFERENCE_CODE\" FROM \"USR_PREFERENCE\" WHERE \"USER_ID\"='" + userName.toLowerCase() + "' and \"ID\"=" + id;
        format = DBQuery.queryRecord(SQL);
        logger.info("Note: The language is:" + format);

        String expectedLang = PropHelper.getProperty("Regional.language").trim();
        logger.info("Expected language is:" + expectedLang);
        if (format == null || !format.equalsIgnoreCase(expectedLang))
        {
            m.listPage.enterPreferencePage();
            m.preferencePage.selectLanguageByValue(expectedLang);
            format = expectedLang;
        }

        return m.listPage;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public String connetcedDB()
    {
        return ConnectDBType;
    }

    public String getFormat()
    {
        return format;
    }

    public boolean isSetOriginalName()
    {
        return setOriginalName;
    }


    /**
     * Assert list1 contains the value of list2
     */
    public boolean checkListContainsAnotherList(List<String> listLong, List<String> listShort)
    {
        for (String aListShort : listShort)
        {
            if (!listLong.contains(aListShort))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * refresh page
     *
     * @throws Exception
     */
    public void refreshPage() throws Exception
    {
        logger.info("Refresh page");
        driver.navigate().refresh();
        Thread.sleep(3000);
    }

    public String addQuotesInPath(String path) throws Exception
    {
        String pathWithQuotes = "";
        if (path.contains(" "))
        {
            path = path.replace("/", "~");
            for (String part : path.split("~"))
            {
                if (part.contains(" "))
                {
                    part = "\"" + part + "\"";
                }
                pathWithQuotes = pathWithQuotes + part + "/";
            }
            pathWithQuotes = pathWithQuotes.substring(0, pathWithQuotes.length() - 1);
        }
        else
            pathWithQuotes = path;
        return pathWithQuotes;
    }

    public class Module
    {
        public AboutPage aboutPage;
        public AbstractPage abstractPage;
        public AdminPage adminPage;
        public ChangePasswordPage changePasswordPage;
        public EntityPage entityPage;
        public HomePage homePage;
        public ListPage listPage;
        public FormInstancePage formInstancePage;
        public PreferencePage preferencePage;
        public Module(TestTemplate testCase)
        {
            aboutPage = new AboutPage(driver);
            abstractPage = new AbstractPage(driver);
            adminPage = new AdminPage(driver);
            changePasswordPage = new ChangePasswordPage(driver);
            entityPage = new EntityPage(driver);
            homePage = new HomePage(driver);
            listPage = new ListPage(driver);
            formInstancePage = new FormInstancePage(driver);
            preferencePage = new PreferencePage(driver);
        }

    }




}
