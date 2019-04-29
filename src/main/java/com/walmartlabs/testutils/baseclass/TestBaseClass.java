package com.walmartlabs.testutils.baseclass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.walmartlabs.testutils.genericutility.DateTimeUtility;
import com.walmartlabs.testutils.genericutility.FileUtility;
import com.walmartlabs.testutils.seleniumutils.SeleniumWebDriver;

public class TestBaseClass extends Assert {

    /**
     * For Core Selenium2 functionality
     */
    protected WebDriver driver = null;
    protected WebDriver driver1 = null;
    protected WebDriverWait wait;
    /**
     * Standard log4j logger.
     */
    protected final Logger log = Logger.getLogger(getClass().getSimpleName());

    /**
     * Getting the base path of screen shot
     */
    String screenshotBasePath;
    String logBasePath;
    String logFile;
    /**
     * Global retry count for the tests failed because of TimedOut Exceptions
     */
    /**
     * Instantiating the driver path
     */
    private final String IE_FILE_PATH = "/src/main/resources/extensions/IEDriverServer.exe";
    private final String CHROME_FILE_PATH = "/src/main/resources/extensions/chromedriver.exe";

    public enum BrowserType {
	FIREFOX("firefox"), IE("iexplore"), SAFARI("SAFARI"), CHROME("CHROME");
	private String label;

	private BrowserType(String label) {
	    this.label = label;
	}

	public String Value() {
	    return label;
	}
    }

    /**
     * Displaying the environment details
     * 
     * @throws IOException
     */
    public TestBaseClass() {
	// Getting the properties
	try {
	    PropertyConfigurator.configure(new File(".").getCanonicalPath() + File.separator + "src" + File.separator
		    + "main" + File.separator + "resources" + File.separator + "log4j.properties");

	    // Location for screenshot
	    screenshotBasePath = new File(".").getCanonicalPath() + File.separator + "test-output" + File.separator
		    + "screenshots";

	    // Location for logs
	    logBasePath = new File(".").getCanonicalPath() + File.separator + "test-output" + File.separator + "logs";

	    // Instantiating logger
	    logFile = new File(".").getCanonicalPath() + File.separator + "test-output" + File.separator + "temp.log";

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Set up logger info
     * 
     * @throws IOException
     * 
     * @throws Exception
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "webSite", "browser", "seleniumHost", "seleniumPort" })
    public final void genericSetUp(String webSite, String browser, String seleniumHost, String seleniumPort)
	    throws IOException {
	// Instantiating Logger
	driver = loadURL(webSite, browser, seleniumHost, seleniumPort);
	Layout layout = new PatternLayout("%d{dd-MMM-yyyy HH:mm:ss:SSS} %-5p %c{1}:%L - %m%n");
	log.removeAllAppenders();
	FileAppender appender = new FileAppender(layout, logFile, false);
	log.addAppender(appender);
    }

    /**
     * Returning the driver based on the browser
     * 
     * @param browser
     * @return
     * @throws IOException
     */
    public WebDriver loadURL(String webSite, String browser, String seleniumHost, String seleniumPort)
	    throws IOException {

	// Instantiating the browser
	driver = getWebDriver(browser);
	wait = new WebDriverWait(driver, 30);
	driver.get(webSite);

	// Maximize the window
	driver.manage().window().maximize();

	return driver;

    }

    /**
     * Returning the driver based on the browser
     * 
     * @param browser
     * @return
     * @throws IOException
     */
    public WebDriver getWebDriver(String browser) throws IOException {
	switch (BrowserType.valueOf(browser)) {
	case FIREFOX:
	    // Firefox invoke code goes here
	case IE:
	    // IE Invoke code goes here
	case CHROME:
	    File chromeFile = new File(new java.io.File(".").getCanonicalPath() + CHROME_FILE_PATH);
	    System.setProperty("webdriver.chrome.driver", chromeFile.getAbsolutePath());
	    return new ChromeDriver();

	default:
	    throw new RuntimeException("Browser type unsupported");
	}
    }

    /**
     * Capturing screenshot, Setting test result and creating log files after each
     * test run
     * 
     * @param result
     * @throws IOException
     */
    @AfterMethod(alwaysRun = true)
    public final void tearDown(ITestResult result) throws IOException {

	String dateTimeStamp = DateTimeUtility.getCurrentDateAndTimeInLoggerFormat();
	String status = "PASS";

	// Capture screen shot in case test has failed.
	try {
	    if (!result.isSuccess()) {
		String destFile = screenshotBasePath + File.separator + result.getName() + " " + dateTimeStamp + ".png";
		log.info("Captured Screenshot : " + destFile);
		status = "FAIL";
		File scrFile = SeleniumWebDriver.takeScreenshot(driver);
		FileUtility.copyFile(scrFile, new File(destFile));
	    }
	} catch (Exception e) {
	    log.error("The following error has occured while capturing a screen shot : " + e.getMessage());
	} finally {

	    // Closing the browser and closing driver
	    driver.quit();
	}
    }

    /**
     * This method returns the current date and time in format HH-mm-ss.SSS
     * 
     * @return time - in the above mentioned format
     */
    protected static String getCurrentDateAndTime() {
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
	Date date = new Date();
	String time = sdf.format(date);
	return time;
    }

}
