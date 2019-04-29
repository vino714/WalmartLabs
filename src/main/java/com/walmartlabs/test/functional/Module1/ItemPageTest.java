package com.walmartlabs.test.functional.Module1;

import org.apache.commons.collections.map.HashedMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.walmartlabs.testutils.baseclass.TestBaseClass;
import com.walmartlabs.testutils.pages.HomeScreen;
import com.walmartlabs.testutils.pages.ItemPage;
import com.walmartlabs.testutils.pages.SearchResultPage;
import com.walmartlabs.testutils.testdatareader.ExcelReader;

public class ItemPageTest extends TestBaseClass {

    @Test(groups = { "Exercise" })
    public void increaseDecreaseQuantityTest() throws Exception {
	try {

	    HomeScreen homeScreen = new HomeScreen(driver);
	    HashedMap testData = ExcelReader.getTestDataByTestCaseId("TC_001", ItemPageTest.class.getSimpleName());
	    homeScreen.typeSearchItem(testData.get("Item").toString());
	    SearchResultPage searchResultPage = homeScreen.clickSearch();
	    ItemPage productPage = searchResultPage.selectNthProductOnPage(1);
	    // verify if default quantity is 1
	    String quantity = productPage.getQuantityOfItem();
	    Assert.assertTrue(quantity.equals("1"), "Dafault item quantity is not 1");
	    // verify if clicking the increase quantity button should increase the quantity
	    // by 1
	    productPage.clickIncreaseItemQuantity();
	    quantity = productPage.getQuantityOfItem();
	    Assert.assertTrue(quantity.equals("2"), "Item quantity is not increased by 1");
	    // verify if clicking the decrease quantity button should decrease the quantity
	    // by 1
	    productPage.clickDecreaseItemQuantity();
	    quantity = productPage.getQuantityOfItem();
	    Assert.assertTrue(quantity.equals("1"), "Item quantity is not decreased by 1");
	    System.out.println("Quantity test : Pass");
	} catch (Exception e) {
	    new Exception(e);
	}

    }

    @Test(groups= {"Exercise"})
    public void addItemToFavoriteTest() throws Exception {
	try {

	    HomeScreen homeScreen = new HomeScreen(driver);
	    HashedMap testData = ExcelReader.getTestDataByTestCaseId("TC_001", ItemPageTest.class.getSimpleName());
	    homeScreen.typeSearchItem(testData.get("Item").toString());
	    SearchResultPage searchResultPage = homeScreen.clickSearch();
	    ItemPage productPage = searchResultPage.selectNthProductOnPage(1);
	    // Click on heart icon to add to favorite and verify Sign In and Create Registry
	    // link
	    productPage.clickFavoritebutton();
	    Assert.assertTrue(productPage.verifySignInAndCreateRegistryLink(),
		    "Sign In and Create Registry button is not present");
	    System.out.println("Add item to favorites : Pass");
	} catch (Exception e) {
	    new Exception(e);
	}

    }

    @Test(groups= {"Exercise"})
    public void verifyAddToCartTest() throws Exception {
	try {

	    HomeScreen homeScreen = new HomeScreen(driver);
	    HashedMap testData = ExcelReader.getTestDataByTestCaseId("TC_001", ItemPageTest.class.getSimpleName());
	    homeScreen.typeSearchItem(testData.get("Item").toString());
	    SearchResultPage searchResultPage = homeScreen.clickSearch();
	    ItemPage productPage = searchResultPage.selectNthProductOnPage(1);
	    // Increasing the quantity
	    productPage.clickIncreaseItemQuantity();
	    productPage.getQuantityOfItem();
	    // Get the Price value
	    String price = productPage.getPriceOfItem();
	    price = price.replace("$", "");
	    float finalPrice = Float.parseFloat(price) * 2;
	    productPage.clickAddToCart();
	    Assert.assertTrue(productPage.verifyQuantityAndPriceInCartPopup("Subtotal: $" + finalPrice, "2 items"),
		    "Item details are not proper in the Add To cart Pop up");
	    System.out.println("Add to Cart : Pass");
	} catch (Exception e) {
	    new Exception(e);
	}

    }
}
