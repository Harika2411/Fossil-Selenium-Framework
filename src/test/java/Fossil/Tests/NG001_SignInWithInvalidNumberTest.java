package Fossil.Tests;

import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import GenericUtilities.BaseClass;
import ObjectRepository.HomePage;
import ObjectRepository.PlpPage;

public class NG001_SignInWithInvalidNumberTest extends BaseClass {
	@Test
	public void invalidProductSearchTest() {
		
		//Retrieving Data From Excel
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		map = ExcelUtil.getTestData("Sheet1", "NG001");
		String mobileNumber = map.get("Mobile_Number");
		String ExpectedInvalidMsg=map.get("Invalid_Msg");
		
		HomePage hp=new HomePage(driver);
		hp.HandleOkPopup(driver);
		hp.SignWithInvalidNumber(mobileNumber, driver);
		
		Assert.assertTrue(hp.getLoginPopup().isDisplayed());
		
		hp.getContinueButton().click();
		
		
		wdUtil.ExplicitWaitForElementToBeClickable(driver, hp.getAlertMsg());
		String Invalid_Msg=hp.getAlertMsg().getText();
		
		Assert.assertTrue(Invalid_Msg.contains(ExpectedInvalidMsg));
		
		
		System.out.println(" Mobile Number Invalid Message Validated");
	
		
	
		
	}

}
