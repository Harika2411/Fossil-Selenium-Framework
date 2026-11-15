package Fossil.Tests;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class TC_003_SortProductsByPriceRangeTest extends BaseClass {

	@Test
	public void sortProductsTest() {

		wdUtil.ImplicitlyWaits(driver);

		// Retrieve test data from Excel
		Map<String, String> map = ExcelUtil.getTestData("Sheet1", "TC_003");

		String sectionName = map.get("Category_Name");

		int minPrice = Integer.parseInt(map.get("Min_Price"));
		int maxPrice = Integer.parseInt(map.get("Max_Price"));
		wdUtil.ExplicitWaits(driver);

		HomePage hp = new HomePage(driver);

		hp.HandleOkPopup(driver);

		// Navigate to category
		By sectionLinks = By.xpath("//a[normalize-space(.)='" + sectionName + "' and @class='navLink']");

		wdUtil.ExplicitWaitForElementToBeClickable(driver, driver.findElement(sectionLinks));
		WebElement section = driver.findElement(sectionLinks);

		section.click();

		// Verify category
		PlpPage plp = new PlpPage(driver);

		wdUtil.ExplicitWaitForElementThatIsVisible(driver, plp.getSearchResult());

		String expectedResult = plp.getSearchResult().getText();

		Assert.assertTrue(expectedResult.contains(sectionName), "Category verification failed");

		// Enter price range(Min and Max Price)

		wdUtil.scrollToElement(driver, plp.getPriceText());
		wdUtil.ExplicitWaitForElementToBeClickable(driver, plp.getMinPriceInput());
		plp.getMinPriceInput().sendKeys(Keys.CONTROL + "a");
		plp.getMinPriceInput().sendKeys(Keys.BACK_SPACE);
		plp.getMinPriceInput().sendKeys(String.valueOf(minPrice));

		plp.getMaxPriceInput().click();
		plp.getMaxPriceInput().sendKeys(Keys.CONTROL + "a");
		plp.getMaxPriceInput().sendKeys(Keys.BACK_SPACE);
		wdUtil.ExplicitWaits(driver);
		plp.getMaxPriceInput().sendKeys(String.valueOf(maxPrice));
		wdUtil.PressEnterKey(driver);

		// Wait for product prices
		By priceLocator = By.xpath("//span[.='Now']/following-sibling::span");

		wdUtil.ExplicitWaitForElementThatIsVisible(driver, driver.findElement(priceLocator));

		List<WebElement> list = driver.findElements(priceLocator);

		Assert.assertTrue(list.size() > 0, "No products found");

		// Verify every product price
		for (int i = 0; i < list.size(); i++) {

			String price = list.get(i).getText();

			int amount = Integer.parseInt(price.replaceAll("\\.\\d+$|[^0-9]", ""));

			System.out.println("Product Price: " + amount);

			Assert.assertTrue(amount >= minPrice && amount <= maxPrice,
					"Price out of range: " + amount + " | Expected range: " + minPrice + " - " + maxPrice);

			System.out.println(amount + " - PASS");
		}

		System.out.println("All product prices are within the given range");
	}
}