package Fossil.Tests;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class TC_002_Apply_FiltersTest extends BaseClass {

	@Test
	public void checkFiltersTest() throws InterruptedException {

		// Retrieve test data from Excel
		Map<String, String> map = ExcelUtil.getTestData("Sheet1", "TC_002");

		System.out.println("Excel Data: " + map);

		String SectionName = map.get("Category_Name");
		String filterTitle = map.get("Filter_Title");
		String filterName = map.get("Color_Name");

		wdUtil.ExplicitWaits(driver);

		HomePage hp = new HomePage(driver);

		hp.HandleOkPopup(driver);

		// Locate category
		By SectionLinks = By.xpath("//a[normalize-space(.)='" + SectionName + "' and @class='navLink']");

		wdUtil.ExplicitWaitForElementToBeClickable(driver, driver.findElement(SectionLinks));
		WebElement section = driver.findElement(SectionLinks);

		section.click();

		// Verify category
		PlpPage plp = new PlpPage(driver);
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, plp.getSearchResult());
		String ExpectedResult = plp.getSearchResult().getText();
		Assert.assertTrue(ExpectedResult.contains(SectionName), "Category verification failed");

		// Scroll to filter

		wdUtil.scrollToElement(driver,
				driver.findElement(By.xpath("//span[@class='filterTitle' and .='" + filterTitle + "']")));

		// Locate filter checkbox

		By FilterCheckBox = By.xpath(
				"//label[@class='plp-filter-item filter-item']" + "/descendant::div[contains(.,'" + filterName + "')]");
		WebElement colorCheckBox = driver.findElement(FilterCheckBox);
		wdUtil.ExplicitWaitForElementToBeClickable(driver, colorCheckBox);

		colorCheckBox.click();

		// Validaing The Filter Applied

		wdUtil.ExplicitWaits(driver);
		filterName = filterName.toLowerCase();
		wdUtil.WaitUntilUrlContains(filterName, driver);
		Assert.assertTrue(driver.getCurrentUrl().contains(filterName));

		System.out.println("Category and filter applied successfully");
	}
}