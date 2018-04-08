package com.lombardrisk.testng;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

/**
 * Created by amy sheng on 2/28/2018.
 */
public class EcshopTestListner extends TestListenerAdapter {
	private final static Logger logger = LoggerFactory.getLogger(EcshopTestListner.class);
	WebDriver driver = null;

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		// logger.error("<label style='color:read'>" + tr.getMethod().getMethodName() +
		// " Failure，详情请查看日志,截图存放在screenshot目录</lable>");
		takeScreenShot(tr);
		Reporter.log("<label style='color:read'>" + tr.getMethod().getMethodName()
				+ "执行失败，详情请查看日志,截图存放在screenshot目录</lable>");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		logger.warn(tr.getName() + " Skipped");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		logger.info(tr.getName() + " Success");

	}

	@Override
	public void onTestStart(ITestResult tr) {
		super.onTestStart(tr);
		logger.info(tr.getName() + " Start");
	}
	
	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);

		// List of test results which we will delete later
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		
		
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			logger.info("PassedTests = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}	
		
		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			logger.info("failedTest = " + failedTest.getName());
			// id = class + method + dataprovider
			int failedTestId = getId(failedTest);

			// if we saw this test as a failed test before we mark as to be deleted
			// or delete this failed test if there is at least one passed version
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}
		
		List<ITestResult> skipedToBeRemoved = new ArrayList<>();
		Set<Integer> skippedTestIds = new HashSet<Integer>();
		for (ITestResult skipedTest : testContext.getSkippedTests().getAllResults()) {
			logger.info("skipTest = " + skipedTest.getName());
			// id = class + method + dataprovider
			int skipedTestId = getId(skipedTest);

			// if we saw this test as a failed test before we mark as to be deleted
			// or delete this failed test if there is at least one passed version
			if (failedTestIds.contains(skipedTestId) || passedTestIds.contains(skipedTestId) || skippedTestIds.contains(skipedTestId)) {
				skipedToBeRemoved.add(skipedTest);
			} else {
				skippedTestIds.add(skipedTestId);
			}
		}	

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator();
				iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				logger.info("Remove repeat Fail Test: " + testResult.getName());
				iterator.remove();
			}
		}
		
		for (Iterator<ITestResult> iterator = testContext.getSkippedTests().getAllResults().iterator();
				iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (skipedToBeRemoved.contains(testResult)) {
				logger.info("Remove repeat skip Test: " + testResult.getName());
				iterator.remove();
			}
		}

	}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

	
	private void takeScreenShot(ITestResult tr) {
		String name = tr.getMethod().getMethodName();
		Object obj = tr.getInstance();
		Class<?> clazz = obj.getClass();
		// System.out.println(clazz.getName());
		try {
			Field field = clazz.getField("driver");
			driver = (WebDriver) field.get(obj);
			System.out.println(driver);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		// 处理截图
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = "screenshot";
		SimpleDateFormat df = new SimpleDateFormat(("yyyy_MM_dd_HH_mm_ss"));
		String filename = clazz.getName() + "." + name + "_" + df.format(new Date()) + ".png";
		File pathfile = new File(path, filename);
		screenshot.renameTo(pathfile);
	}
}
