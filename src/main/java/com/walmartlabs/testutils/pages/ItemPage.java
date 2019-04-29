package com.walmartlabs.testutils.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.walmartlabs.testutils.seleniumutils.SeleniumWebDriver;

public class ItemPage extends SeleniumWebDriver {

    protected By btnAddToCart = By.cssSelector("[data-automation='cta-button']");
    protected By btnIncreaseQuantity = By.cssSelector("[data-automation='increase-qty']");
    protected By btnDecreaseQuantity = By.cssSelector("[data-automation='decrease-qty']");
    protected By txtQuantity = By.cssSelector("[data-automation='quantity'] > input");
    protected By btnHeart = By.cssSelector("[data-automation='heart-icon']");
    protected By btnSignInFromRegistry = By.xpath("//button[@data-automation='heart-icon']/../*//button[text()='Sign in']");
    protected By btnCreateRegistry = By.xpath("//button[text()='Create registry']");
    protected By lblPrice = By.cssSelector("[data-automation='buybox-price']");
    protected By popUpAddToCart = By.id("atc-root");

    /***
     * Call to super constructor
     */
    public ItemPage(WebDriver driver) {
	super(driver);
    }

    /***
     * increase quantity
     * 
     * @throws Exception
     * @throws IOException
     */
    public void clickIncreaseItemQuantity() throws IOException {
	try {
	    boolean found = true;
	    while (found) {
		if(isElementPresent(btnIncreaseQuantity))
		{
		    break;
		}
	    }
	    click(btnIncreaseQuantity);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * decrease quantity
     * 
     * @throws Exception
     * @throws IOException
     */
    public void clickDecreaseItemQuantity() throws IOException {
	try {
	    click(btnDecreaseQuantity);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * get quantity of item
     * 
     * @throws Exception
     * @throws IOException
     */
    public String getQuantityOfItem() throws IOException {
	try {
	    boolean found = true;
	    while (found) {
		if(isElementPresent(txtQuantity))
		{
		    break;
		}
	    }
	    return getValue(txtQuantity);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * get price of item
     * 
     * @throws Exception
     * @throws IOException
     */
    public String getPriceOfItem() throws IOException {
	try {
	    //waitForPageToLoad();
	    isElementPresent(txtQuantity);
	    return getText(lblPrice, 5);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * click Favorite button(heart icon)
     * 
     * @throws Exception
     * @throws IOException
     */
    public void clickFavoritebutton() throws IOException {
	try {
	    isElementPresent(btnHeart);
	    clickElementUsingAction(btnHeart);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * verify if Sign In and Create Registry link is present
     * 
     * @throws Exception
     * @throws IOException
     */
    public boolean verifySignInAndCreateRegistryLink() throws IOException {
	try {
	    if (isElementPresent(btnSignInFromRegistry) && isElementPresent(btnCreateRegistry))
		return true;
	    else
		return false;
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * verify if item is added to cart has proper price and quantity
     * 
     * @throws Exception
     * @throws IOException
     */
    public boolean verifyQuantityAndPriceInCartPopup(String price, String quantity) throws IOException {
	try {
	    String popText = getText(popUpAddToCart, 10);
	    if (popText.contains(price) && popText.contains(quantity))
		return true;
	    else
		return false;
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

    /***
     * clickAddToCart
     * 
     * @throws Exception
     * @throws IOException
     */
    public void clickAddToCart() throws IOException {
	try {
	    clickElementUsingAction(btnAddToCart);
	} catch (Exception e) {
	    throw new IOException(e);
	}
    }

}
