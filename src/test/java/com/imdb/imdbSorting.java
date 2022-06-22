package com.imdb;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import jxl.Sheet;
import jxl.Workbook;

public class imdbSorting {
	Sheet s;	
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
		  System.setProperty("webdriver.chrome.driver", "D:\\eclipse workplace\\server\\chromedriver.exe");
	    driver = new ChromeDriver();
	    baseUrl = "http://www.imdb.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testIMDBSorting() throws Exception {
	    driver.get(baseUrl + "chart/top");
	    FileInputStream fi = new FileInputStream("resources/SortType.xls"); 
	    Workbook w = Workbook.getWorkbook(fi); 
	    s = w.getSheet(0); 
	    for(int row=1; row < s.getRows();row++) { 
	    	String sortBy = s.getCell(0, row).getContents(); 
	    	
	        try {
	            assertTrue(isElementPresent(By.name("sort")));
	          } catch (Throwable e) {
	            verificationErrors.append(e.toString());
	          }
	          
	          new Select(driver.findElement(By.name("sort"))).selectByVisibleText(sortBy);
	          System.out.println("Sorting option "+sortBy); 
	          try {
	            assertTrue(isElementPresent(By.cssSelector("td.titleColumn")));
	            WebElement elem = driver.findElement(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr[1]/td[2]/a"));
	  	        String title = elem.getText();
	  	        System.out.println("Passed: Top title by " + sortBy + " is " + title + "\n");
	            
	          } catch (Throwable e) {
	        	System.out.println("Failed: No Titles Found");
	            verificationErrors.append(e.toString());
	          }
	    }
	  }

	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }

}
