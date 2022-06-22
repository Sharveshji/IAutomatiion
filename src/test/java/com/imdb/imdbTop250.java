package com.imdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

public class imdbTop250 {
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
public void testIMDBTop250() throws Exception {
  driver.get(baseUrl + "chart/top");
  
  
  try {
    assertEquals("Top 250 Movies", driver.findElement(By.xpath("//span[contains(text(),'Top 250 Movies')]")).getText());
  } catch (Throwable e) {
    verificationErrors.append(e.toString());
  }
  driver.findElement(By.xpath("//span[contains(text(),'Top 250 Movies')]")).click();
  try {
    assertTrue(isElementPresent(By.cssSelector("td.titleColumn")));
    WebElement elem = driver.findElement(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr[1]/td[2]/a"));
    String title = elem.getText();
    System.out.println("Passed: The current top movie is " + title + "\n");
  } catch (Throwable e) {
    System.out.println("Failed: No Titles Found");
    verificationErrors.append(e.toString());
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
