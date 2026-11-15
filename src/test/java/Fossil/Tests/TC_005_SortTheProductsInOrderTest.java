package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class TC_005_SortTheProductsInOrderTest extends BaseClass {
	
	
	@Test
public void sortingProductsTest() {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map = ExcelUtil.getTestData("Sheet1", "TC_005");
		String categoryName = map.get("Category_Name");
	HomePage hp=new HomePage(driver);
		
		hp.HandleOkPopup(driver);
		
		//Navigating to specified Category
		PlpPage plp=new PlpPage(driver);
		plp.NavigateToCategory(driver, categoryName);
		
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, plp.getSearchResult());
		Assert.assertTrue(categoryName.contains(plp.getSearchResult().getText()));
		
		
		//Sort Products
		String SortByValue=map.get("SortBy_Value");
		wdUtil.scrollToElement(driver, plp.getSortDropDown());
		wdUtil.ExplicitWaitForElementToBeClickable(driver, plp.getSortDropDown());
		
	
		
		wdUtil.HandleDropDown(SortByValue,plp.getSortDropDown() );
		
		//Validating Sorting Order
		
		List<WebElement> prices = driver.findElements(By.xpath("//span[@class='price']"));

		for (int i = 0; i < prices.size() - 1; i++) {

		    int a = Integer.parseInt(prices.get(i).getText().replace("₹", "").trim());
		    int b = Integer.parseInt(prices.get(i + 1).getText().replace("₹", "").trim());
		   
		    // Print values being compared
		    System.out.println("Comparing: " + a + " and " + b);
		    
		    if (a > b) {
		        System.out.println("Not in ascending order");
		        return;
		    }
		   
		}

		System.out.println("In ascending order");
		
	
}

}
