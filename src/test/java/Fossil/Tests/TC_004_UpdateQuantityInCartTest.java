package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.CartPage;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;
import ObjectRepository.PlpPage;

public class TC_004_UpdateQuantityInCartTest extends BaseClass {

	@Test
	public void checkQuantity() {
		// Retrieving Data From Excel
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = ExcelUtil.getTestData("Sheet1", "TC_004");
		String product_Name = map.get("Product_Name");
		// Implicit Wait
		wdUtil.ImplicitlyWaits(driver);

		HomePage hp = new HomePage(driver);

		String PageTitle = driver.getTitle();
		Assert.assertEquals(PageTitle, "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");
		hp.HandleOkPopup(driver);
		// Searching the Product
		hp.SearchProduct(product_Name, driver);

		// Navigating from PLP to PDP
		PlpPage plp = new PlpPage(driver);
		By productLocator = By.xpath("//a[.='" + product_Name + "']");

		WebElement product_link=plp.scrollUntilProductLink(driver, product_Name);

		
		// Getting Product Price From PLP and Click on Product Link
		By priceLocator = By.xpath("//a[.='" + product_Name
				+ "']/../following-sibling::div[contains(@class,'plp-price-wishlist')]/descendant::span[contains(@class,'regularPriceValue')]");

		// Clicking Product
		wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
		String priceInPLP = driver.findElement(priceLocator).getText();
		product_link.click();
		

		// Performing Validations In PDP
		PdpPage pdp = new PdpPage(driver);

		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProductTitle());

		String pdpProdName = pdp.getTitleOfProduct();

		Assert.assertTrue(pdpProdName.contains(product_Name));

		Assert.assertEquals(priceInPLP, pdp.getpriceOfProd());
		

		// Scrolling To Add To Cart
		wdUtil.scrollToElementAction(driver, pdp.getAddToCartButton());
		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getAddToCartButton());
		pdp.getAddToCartButton().click();

		// Validate Checkout Popup
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProdTtleInCheckoutPopUp());

		String title = pdp.getProdTtleInCheckoutPopUp().getText();
		Assert.assertTrue(product_Name.equalsIgnoreCase(title));

		String EstimatedPrice = pdp.getEstimatedPrice().getText();
		Assert.assertEquals(priceInPLP, EstimatedPrice);

		// close checkout popup
		pdp.getCloseCheckoutPopup().click();

		// Go to Cart

		wdUtil.ExplicitWaitForElementToBeClickable(driver, pdp.getGoToCartBtn());

		pdp.getGoToCartBtn().click();
		CartPage cart = new CartPage(driver);
		String cartTitle = cart.getCartTitle().getText();
		Assert.assertTrue(cartTitle.contains("Shopping Cart"));
		String expected = cart.getProductTitle().getText();
		Assert.assertTrue(expected.equalsIgnoreCase(product_Name));

		wdUtil.ExplicitWaitForElementThatIsVisible(driver, cart.getIncreaseQtyBtn());

		String priceBefore = cart.getProductPrice().getText();

		Assert.assertEquals(priceBefore, priceInPLP);
		int before = Integer.parseInt(priceBefore.replaceAll("[^0-9]|\\.0+$", ""));

		// Increase Quantity to count

		String count = map.get("Quantity");
		int n = Integer.parseInt(count);
		int currentQty = Integer.parseInt(cart.getQuantityValue().getText());

		while (currentQty < n) {

			cart.getIncreaseQtyBtn().click();

			int previousQty = currentQty;

			currentQty = Integer.parseInt(cart.getQuantityValue().getText());
		}

		String priceAfter = cart.getTotalPrice().getText();
		int after = Integer.parseInt(priceAfter.replaceAll("[^0-9]|\\.0+$", ""));

		Assert.assertTrue(after > before, "Price did not increase after increasing quantity");

		// validating quantity *price
		Assert.assertEquals(before * n, after);

		System.out.println("Price increased successfully: " + before + " -> " + after);

		// Validating Estimated Total with actual price
		String EstimatedTotal = cart.getEstimatedTotal().getText();
		System.out.println(map);
		ExcelUtil.writeBackData("Sheet1", "TC_004", "Updated_Total", EstimatedTotal);
		int EstimatedActual = Integer.parseInt(EstimatedTotal.replaceAll("[^0-9]|\\.0+$", ""));
		Assert.assertEquals(EstimatedActual, after);

	}

}
