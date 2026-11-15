package ObjectRepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	
	@FindBy(xpath="//h1[@class='cart-title']")
	private WebElement CartTitle;
	
	@FindBy(xpath="//h5[contains(@class,'product-title')]/a")
	private WebElement productTitle;
	
	@FindBy(xpath="//span[contains(@class,'price-current')]")
	private WebElement productPrice;
	
	@FindBy(xpath="//button[@class='qty-btn']/img[@alt='Plus']")
	private WebElement increaseQtyBtn;
	
	@FindBy(xpath="//button[@class='qty-btn']/img[@alt='Minus']")
	private WebElement decreaseQtyBtn;
	
	@FindBy(xpath="//p[contains(.,'Total:')]/span")
	private WebElement TotalPrice;
	
	@FindBy(xpath="//span[.='ESTIMATED TOTAL']/following-sibling::span[@class='estimated-total-value']")
	private WebElement EstimatedTotal;
	
	@FindBy(xpath="//span[@class='qty-display']")
	private WebElement QuantityValue;
	
	@FindBy(xpath="//h2[@id='remove-modal-title']")
	private WebElement RemovePopupText;
	
	@FindBy(xpath="//button[.='Yes']")
	private WebElement RemoveYesBtn;
	
	@FindBy(xpath="//button[.='Sign In']")
	private WebElement SignIn;
	
	public WebElement getSignIn() {
		return SignIn;
	}


	public WebElement getRemoveYesBtn() {
		return RemoveYesBtn;
	}


	public WebElement getRemovePopupText() {
		return RemovePopupText;
	}


	public WebElement getQuantityValue() {
		return QuantityValue;
	}


	public WebElement getEstimatedTotal() {
		return EstimatedTotal;
	}

	public WebElement getTotalPrice() {
		return TotalPrice;
	}



	public CartPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		
	}

	public WebElement getCartTitle() {
		return CartTitle;
	}



	public WebElement getProductTitle() {
		return productTitle;
	}


	public WebElement getProductPrice() {
		return productPrice;
	}



	public WebElement getIncreaseQtyBtn() {
		return increaseQtyBtn;
	}

	

	public WebElement getDecreaseQtyBtn() {
		return decreaseQtyBtn;
	}

	
	
	

}
