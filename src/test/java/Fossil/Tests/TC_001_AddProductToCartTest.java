
package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import GenericUtilities.ListenersImplementation;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;
import ObjectRepository.PlpPage;

@Listeners(ListenersImplementation.class)

public class TC_001_AddProductToCartTest extends BaseClass {

	@Test
	public void addToCartTest() throws InterruptedException {

		// Retrieving Data From Excel
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = ExcelUtil.getTestData("Sheet1", "TC_001");
		String product_Name = map.get("Product_Name");
		wdUtil.ImplicitlyWaits(driver);
		HomePage Hp = new HomePage(driver);
		Hp.HandleOkPopup(driver);
		String PageTitle = driver.getTitle();
		Assert.assertEquals(PageTitle, "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");

		// Searching the Product
		Hp.SearchProduct(product_Name, driver);
		PlpPage plp = new PlpPage(driver);
		By productLocator = By.xpath("//a[.='" + product_Name + "']");
      WebElement product_link= plp.scrollUntilProductLink(driver, product_Name);

		// Getting Product Price From PLP and Click on Product Link
		By priceLocator = By.xpath("//a[.='" + product_Name
				+ "']/../following-sibling::div[contains(@class,'plp-price-wishlist')]/descendant::span[contains(@class,'regularPriceValue')]");

		wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
		String priceInPLP = driver.findElement(priceLocator).getText();
		ExcelUtil.writeBackData("Sheet1", "TC_001", "Product_Price", priceInPLP);
		wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
		product_link.click();

		// Performing Validations In PDP
		PdpPage pdp = new PdpPage(driver);
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProductTitle());
		String pdpProdName = pdp.getTitleOfProduct();
		Assert.assertTrue(pdpProdName.contains(product_Name));
		Assert.assertEquals(priceInPLP, pdp.getProductPrice().getText());
		System.out.println("===========Product Details Validated in PDP Page==========");

		// Scrolling and Add To Cart
		wdUtil.scrollToElementAction(driver, pdp.getAddToCartButton());
		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getAddToCartButton());
		pdp.getAddToCartButton().click();

		// Validate Data in Checkout
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProdTtleInCheckoutPopUp());

		String title = pdp.getProdTtleInCheckoutPopUp().getText();
		Assert.assertTrue(product_Name.equalsIgnoreCase(title));

		String EstimatedPrice = pdp.getEstimatedPrice().getText();
		Assert.assertEquals(priceInPLP, EstimatedPrice);
		
		System.out.println("=============Product Details Validated in Checkout Popup=============");

		
		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getCheckoutButton());
		pdp.getCheckoutButton().click();

		// Validate Login Popup
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getLoginPopup());

		String LoginText = pdp.getLoginPopup().getText();

		Assert.assertTrue(LoginText.contains("Login"));
	}
}
