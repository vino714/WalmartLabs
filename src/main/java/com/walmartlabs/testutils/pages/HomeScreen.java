package com.walmartlabs.testutils.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.walmartlabs.testutils.seleniumutils.SeleniumWebDriver;

public class HomeScreen extends SeleniumWebDriver {

    protected By txtSearchbox = By.id("global-search");
    protected By btnSearch = By.xpath("//button[@data-automation='search-form-submit']");

    /***
     * Call to super constructor
     */
    public HomeScreen(WebDriver driver) {
	super(driver);
    }

    /***
     * type into search text box
     * 
     * @throws Exception
     * @throws IOException
     */
    public void typeSearchItem(String item) throws IOException {
	try {
	    sendKeys(txtSearchbox, item);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * clickSearch
     * 
     * @throws Exception
     * @throws IOException
     */
    public SearchResultPage clickSearch() throws IOException {
	try {
	    clickElementUsingAction(btnSearch);
	    return new SearchResultPage(driver);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

}
