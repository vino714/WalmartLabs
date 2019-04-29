package com.walmartlabs.testutils.seleniumutils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.walmartlabs.testutils.genericutility.Constants;

public class SeleniumWebDriver {

    /**
     * Creating the web driver object to be used
     */
    protected static WebDriver driver;
    WebDriverWait wait;
    Boolean result = true;
    public String returnString = "";

    public SeleniumWebDriver(WebDriver driver) {
	SeleniumWebDriver.driver = driver;
    }

    /**
     * Veriy the presence of a text in the page.
     * 
     * @param driver
     * @param text
     * @return true/false
     * @throws IOException
     */
    public boolean isTextPresent(String text) throws IOException {
	try {
	    result = driver.getPageSource().contains(text);
	}
	catch (Exception e) {
	}

	return result;
    }

    /**
     * Veriy the presence of a element in the page.
     * 
     * @param By
     * @param text
     * @return true/false
     * @throws IOException
     */
    public boolean isElementPresent(By element) throws IOException {
	boolean exists = true;
	try {
	    exists = driver.findElement(element).isDisplayed();
	} catch (Exception e) {
	}
	return exists;
    }

    public boolean waitForElement(final By ajaxElementName, int timeOutValue) throws Exception {
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	wait = new WebDriverWait(driver, timeOutValue);
	try {
	    ExpectedCondition<Boolean> e = new ExpectedCondition<Boolean>() {
		public Boolean apply(WebDriver driver) {
		    return driver.findElement(ajaxElementName).isDisplayed();
		}
	    };
	    wait.until(e);
	    return true;
	} catch (Exception e) {
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    throw new Exception("The Element '" + ajaxElementName + "' did not appear  within " + timeOutValue + " ms."
		    + timeOutValue * 1000);
	}

    }

    public boolean sendKeys(By elementLocator, String value) throws IOException {
	boolean flag;
	try {
	    driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
	    driver.findElement(elementLocator).clear();
	    driver.findElement(elementLocator).sendKeys(value);
	    flag = true;
	} catch (Exception e) {
	    flag = false;
	}
	return flag;
    }

    public boolean click(final By ajaxElementName) throws IOException {
	try {
	    waitForElement(ajaxElementName, Constants.AVG_WAIT_TIME_FOR_ELEMENT);
	    if (driver.findElement(ajaxElementName).isDisplayed() && driver.findElement(ajaxElementName).isEnabled()) {
		driver.findElement(ajaxElementName).click();
	    } else {
		result = false;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return result;
    }

    public boolean clickElementUsingAction(By ajaxElementName) throws IOException {
	try {
	    WebElement element = driver.findElement(ajaxElementName);
	    Actions builder = new Actions(driver);
	    builder.moveToElement(element).click(element);
	    builder.perform();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    public boolean clickElementUsingJavaScript(By ajaxElementName) throws IOException {
	try {
	    WebElement element = driver.findElement(ajaxElementName);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    public void waitForPageToLoad() throws IOException {
	String element = "";
	try {
	    int counter = 0;

	    Thread.sleep(1000);
	    while (true) {
		String ajaxIsComplete = ((JavascriptExecutor) driver).executeScript("return Ajax.activeRequestCount")
			.toString();
		if (Integer.parseInt(ajaxIsComplete) == 0)
		    break;
		if (counter == 20)
		    break;
		Thread.sleep(100);
		counter++;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public String getText(By elementName, int wait) throws IOException {
	try {
	    if (waitForElement(elementName, wait)) {
		returnString = driver.findElement(elementName).getText();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return returnString;
    }

    public String getValue(By elementName) throws IOException {
	try {
	    if (waitForElement(elementName, Constants.AVG_WAIT_TIME_FOR_ELEMENT)) {
		returnString = driver.findElement(elementName).getAttribute("value");
	    } else {
		return "";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return returnString;
    }

    /**
     * The screen shot is captured
     * 
     * @param driver
     * @return
     */
    public static File takeScreenshot(WebDriver driver) {
	return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

}
