package com.lombardrisk.bus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.lombardrisk.core.utils.PropHelper;
import com.lombardrisk.core.Locator.Locator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lombardrisk.core.test.TestTemplate;


public class AbstractPage {
		protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	//public static String dateFormat;
		public static String userName, password, connectedDB, format;
		public static boolean setOriginalName;
		public WebDriver driver;
		Locator l = new Locator(driver);


	   public AbstractPage (WebDriver driver) {
		   this.driver = driver;
		   TestTemplate testTemplate =  new TestTemplate();
		   userName = testTemplate.getUserName();
		   password = testTemplate.getPassword();
	   }



	   protected void selectDate2 (String date)throws Exception
	   {
	      String day, month, year;
	      Select select = null;
	      if ("en_GB".equalsIgnoreCase(PropHelper.DATE_FORMAT))
	      {
	         month = date.substring(3, 5);
	         day = date.substring(0, 2);
	         year = date.substring(6, 10);
	      }
	      else if ("en_US".equalsIgnoreCase(PropHelper.DATE_FORMAT))
	      {
	         day = date.substring(3, 5);
	         month = date.substring(0, 2);
	         year = date.substring(6, 10);
	      }
	      else if ("zh_CN".equalsIgnoreCase(PropHelper.DATE_FORMAT))
	      {
	         month = date.substring(5, 7);
	         day = date.substring(8, 10);
	         year = date.substring(0, 4);
	      }
	      else
	      {
	         month = date.substring(3, 5);
	         day = date.substring(0, 2);
	         year = date.substring(6, 10);
	      }
	      if (day.startsWith("0"))
	      {
	         day = day.substring(1);
	      }
	      switch (month)
	      {
	         case "01":
	            month = "Jan";
	            break;
	         case "02":
	            month = "Feb";
	            break;
	         case "03":
	            month = "Mar";
	            break;
	         case "04":
	            month = "Apr";
	            break;
	         case "05":
	            month = "May";
	            break;
	         case "06":
	            month = "Jun";
	            break;
	         case "07":
	            month = "Jul";
	            break;
	         case "08":
	            month = "Aug";
	            break;
	         case "09":
	            month = "Sep";
	            break;
	         case "10":
	            month = "Oct";
	            break;
	         case "11":
	            month = "Nov";
	            break;
	         case "12":
	            month = "Dec";
	            break;
	      }
	      select = new Select(l.getElement("rcp.calendarMonth"));
	      select.selectByVisibleText(month);
	      Thread.sleep(300);
	      new Select(l.getElement("rcp.calendarYear")).selectByVisibleText(year);
	      Thread.sleep(300);
	      //$(l.getElement("rcp.calendarDay3")).click();
	      l.getElement("rcp.calendarDay2",day).click();

	   }



	   protected void setCalendarDate(String processDate) throws Exception
	   {

	      String Day = processDate.replace("/","#").split("#")[0];
	      String Year = processDate.replace("/","#").split("#")[2];
	      String Month = processDate.replace("/","#").split("#")[1];
//	      if (Month.startsWith("0"))
//	      {
//	          Month = Month.substring(1);
//	      }



	       //$(l.getElement("rcp.calendarMonth")).selectOptionContainingText(Month);
	      new Select(l.getElement("rcp.calendarMonth")).selectByVisibleText(Month);
	      new Select(l.getElement("rcp.calendarYear")).selectByVisibleText(Year);

	      //String[] list = { Day, Day };
	      //$(l.getElement("rcp.calendarDay",list)).click();


	      //Use contains() in xpath - way1
	      l.getElement("rcp.calendarDay2",Day).click();


	      //$(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody//a[contains()]");

	      //Use contains() in xpath - way2 - doesn't work
	      //$(By.xpath("//*[@id='ui-datepicker-div']//a[contains(text(),Day)]")).click();


	      //Use CSS Selector - way1
	      //$("#ui-datepicker-div>table>tbody>tr>td>a.ui-state-default.ui-state-active").click();

	      //Use CSS Selector - way2
	      //$(l.getElement("fcp.calendarDay3")).click();

	   }

}
