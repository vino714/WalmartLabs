package com.walmartlabs.testutils.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.walmartlabs.testutils.seleniumutils.SeleniumWebDriver;

public class SearchResultPage extends SeleniumWebDriver {

    protected By lnkNavigateToAcccount = By.id("nav-link-yourAccount");
    protected By txtSearchbox = By.id("twotabsearchtextbox");
    protected By lnkSearchResults = By.cssSelector("h2.thumb-header");

    /***
     * Call to super constructor
     */
    public SearchResultPage(WebDriver driver) {
	super(driver);
    }

    /***
     * selects the nth product on the page
     * 
     * @throws Exception
     * @throws IOException
     */
    public ItemPage selectNthProductOnPage(int nthProduct) throws IOException {
	try {
	    List<WebElement> searchResults = driver.findElements(lnkSearchResults);
	    searchResults.get(nthProduct - 1).click();
	    return new ItemPage(driver);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

}
