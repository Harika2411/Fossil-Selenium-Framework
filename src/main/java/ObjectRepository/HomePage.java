package ObjectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import GenericUtilities.WebDriverUtility;

public class HomePage {
	
	@FindBy(xpath="//button[@aria-label='Open search' and contains(@class,'search-form')]")
	private WebElement searchIcon;
	
	@FindBy(xpath="//input[@class='header-search-input']")
	private WebElement searchTextField;
	
	@FindBy(xpath="//button[.='Search']")
	private WebElement SearchButton;
	
	@FindBy(xpath="//button[.='Ok']")
	private WebElement OkPopup;
	
	@FindBy(xpath="//button[.='Sign in']")
	private WebElement SignInButton;
	
	@FindBy(id="phoneInput")
	private WebElement MobileNumberTextField;
	
	@FindBy(xpath="//h1[.='Login or Signup']")
	private WebElement loginPopup;
	
	@FindBy(xpath="//button[.='CONTINUE']")
	private WebElement continueButton;
	
	@FindBy(xpath="//div[@role=\"alert\"]")
	private WebElement AlertMsg;
	
	public WebElement getAlertMsg() {
		return AlertMsg;
	}



	public WebElement getContinueButton() {
		return continueButton;
	}



	public WebElement getLoginPopup() {
		return loginPopup;
	}



	public WebElement getSignInButton() {
		return SignInButton;
	}



	public WebElement getMobileNumberTextField() {
		return MobileNumberTextField;
	}



	public WebElement getOkPopup() {
		return OkPopup;
	}

	

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getSearchIcon() {
		return searchIcon;
	}

	public WebElement getSearchTextField() {
		return searchTextField;
	}

	public WebElement getSearchButton() {
		return SearchButton;
	}
	
	//Business Library
	public void SearchProduct(String productName,WebDriver driver) {
		WebDriverUtility wdUtil=new WebDriverUtility();
		
		wdUtil.ExplicitWaitForElementToBeClickable(driver, searchIcon);
		searchIcon.click();
		wdUtil.ExplicitWaitForElementToBeClickable(driver, searchTextField);
		searchTextField.sendKeys(productName);
		SearchButton.click();
	}
	
	//Enter Mobile Number to SignIn
	
	public void SignWithInvalidNumber(String n,WebDriver driver) {
		WebDriverUtility wdUtil=new WebDriverUtility();
		wdUtil.ExplicitWaitForElementThatIsVisible(driver, getSignInButton());
	    getSignInButton().click();
	    
	    wdUtil.ExplicitWaitForElementToBeClickable(driver, MobileNumberTextField);
	    getMobileNumberTextField().sendKeys(n);
		
	}
	
	public void HandleOkPopup(WebDriver driver) {
		
	    HomePage hp = new HomePage(driver);
	    WebDriverUtility wdutil=new WebDriverUtility();

	    List<WebElement> popup = driver.findElements(By.xpath("//button[.='Ok']"));

	    if (popup.size() > 0 && popup.get(0).isDisplayed()) {

	        wdutil.ExplicitWaitForElementToBeClickable(driver, popup.get(0));
	        popup.get(0).click();

	        System.out.println("OK Popup is displayed and clicked");

	    } else {

	        Assert.fail("OK Popup is not displayed");

	    }
	}

}
