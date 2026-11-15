package Fossil.Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class TC_007_NavigateToEachCategoryTest extends BaseClass {

    static boolean isFirstIteration = true;

    @Test(dataProvider = "getData")
    public void navigateToSectionsTest(String CategoryName) throws InterruptedException {

        wdUtil.ImplicitlyWaits(driver);

        HomePage hp = new HomePage(driver);
        // Check popup only in the first iteration
        if (isFirstIteration) {
            hp.HandleOkPopup(driver);
            isFirstIteration = false;
        }

        // Navigate to each category
        By CategoryLinks = By.xpath(
                "//a[normalize-space(.)='" + CategoryName + "' and @class='navLink']");

        WebElement category = driver.findElement(CategoryLinks);

        wdUtil.ExplicitWaitForElementToBeClickable(driver, category);
        category.click();

        // Validate search result
     // Validate search result
        PlpPage plp = new PlpPage(driver);
        wdUtil.ExplicitWaitForElementThatIsVisible(driver, plp.getSearchResult());

        // Wait until the expected category text is displayed
        wdUtil.ExplicitWaitForText(driver, plp.getSearchResult(),
                CategoryName.equals("New") ? "New Arrivals" : CategoryName);

        String actualResult = plp.getSearchResult().getText();

        System.out.println("Expected Category: " + CategoryName);
        System.out.println("Actual Result: " + actualResult);

        Assert.assertEquals(
                actualResult.trim().toLowerCase(),
                (CategoryName.equals("New") ? "New Arrivals" : CategoryName).toLowerCase(),
                "Category verification failed: " + CategoryName);

        System.out.println(CategoryName + " - PASS");
        
    }
    
    @DataProvider
    public Object[][] getData() {

        String[] categories = { "Womens", "Jewelry", "Mens", "Bags", "New", "Wallets" };

        Object[][] data = new Object[categories.length][1];

        for (int i = 0; i < categories.length; i++) {
            data[i][0] = categories[i];
        }

        return data;
    }
}