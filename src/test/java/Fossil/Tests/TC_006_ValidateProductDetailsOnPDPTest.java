package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PdpPage;
import ObjectRepository.PlpPage;

public class TC_006_ValidateProductDetailsOnPDPTest extends BaseClass {
	
	@Test
	public void navigateFromPlpToPDPTest() {
		
		// Retrieving Data From Excel
				Map<String, String> map = new LinkedHashMap<String, String>();
				map = ExcelUtil.getTestData("Sheet1", "TC_006");

				String product_Name = map.get("Product_Name");

				wdUtil.ImplicitlyWaits(driver);
				HomePage hp=new HomePage(driver);
				
				
				String PageTitle = driver.getTitle();

				Assert.assertEquals(PageTitle, "Fossil India - The Official Site for Fossil Watches, Bags, Jewelry and More");
				hp.HandleOkPopup(driver);

				// Searching the Product
				hp.SearchProduct(product_Name, driver);
			

				// Navigating from PLP to PDP
				PlpPage plp = new PlpPage(driver);
				By productLocator = By.xpath("//a[.='" + product_Name + "']");

				wdUtil.ExplicitWaits(driver);
				
				By priceLocator = By.xpath("//a[.='" + product_Name
						+ "']/../following-sibling::div[contains(@class,'plp-price-wishlist')]/descendant::span[contains(@class,'regularPriceValue')]");
				

				WebElement product_link=plp.scrollUntilProductLink(driver, product_Name);
				// Getting Product Price From PLP
				
				
				// Clicking Product
				wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
				// Getting Product Price From PLP
				String priceInPLP = driver.findElement(priceLocator).getText();

				// Clicking Product
				wdUtil.ExplicitWaitForElementToBeClickable(driver, product_link);
				product_link.click();

				// Performing Validations In PDP
				PdpPage pdp = new PdpPage(driver);

				wdUtil.ExplicitWaitForElementThatIsVisible(driver, pdp.getProductTitle());

				String pdpProdName = pdp.getTitleOfProduct();

				Assert.assertTrue(pdpProdName.contains(product_Name));

				Assert.assertEquals(priceInPLP, pdp.getpriceOfProd());

	}

}
