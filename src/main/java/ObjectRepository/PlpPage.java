package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericUtilities.WebDriverUtility;

public class PlpPage {
	@FindBy(xpath = "//h1[.='Search' and contains(@class,'plp-page-title')]")
	private WebElement SearchTxt;

	@FindBy(xpath = "//p[@class='results-for-text']/span")
	private WebElement InvalidSearchMsg;

	@FindBy(xpath = "//ol[contains(@class,'plp-breadcrumb')]/descendant::a")
	private WebElement SearchResult;

	@FindBy(xpath = "//span[.='Category']")
	private WebElement CategoryBtn;

	@FindBy(xpath = "//section[contains(@class,'plp-mobile-chips')]/descendant::span[@class='plp-chip reval-filter-chip']")
	private WebElement SelectedFilter;

	@FindBy(xpath = "//span[.='Strap Color']")
	private WebElement ColorFilter;

	@FindBy(xpath = "//button[.='Price']")
	private WebElement PriceText;

	@FindBy(xpath = "//div[@class='plp-price-inputs']/descendant::input")
	private WebElement MinPriceInput;

	@FindBy(xpath = "//span[.='to']/following-sibling::label/input")
	private WebElement MaxPriceInput;

	@FindBy(xpath = "//select[contains(@class,'plp-sort-select')]")
	private WebElement SortDropDown;

	@FindBy(xpath = "//span[contains(@class,'regularPriceValue')]")
	private WebElement ProductsPrices;

	@FindBy(xpath = "//span[.='Now']/following-sibling::span")
	private WebElement priceNow;

	public WebElement getPriceNow() {
		return priceNow;
	}

	public WebElement getSortDropDown() {
		return SortDropDown;
	}

	public WebElement getProductsPrices() {
		return ProductsPrices;
	}

	public WebElement getPriceText() {
		return PriceText;
	}

	public WebElement getMinPriceInput() {
		return MinPriceInput;
	}

	public WebElement getMaxPriceInput() {
		return MaxPriceInput;
	}

	public WebElement getColorFilter() {
		return ColorFilter;
	}

	public WebElement getSelectedFilter() {
		return SelectedFilter;
	}

	public WebElement getCategoryBtn() {
		return CategoryBtn;
	}

	public WebElement getSearchResult() {
		return SearchResult;
	}

	public WebElement getInvalidSearchMsg() {
		return InvalidSearchMsg;
	}

	public WebElement getSearchTxt() {
		return SearchTxt;
	}

	// Constructor

	public PlpPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Business library
	/**
	 * To Navigate to Specified Category
	 * 
	 * @param driver
	 * @param categoryName
	 */

	public void NavigateToCategory(WebDriver driver, String categoryName) {

		driver.findElement(By.xpath("//a[.='" + categoryName + "']")).click();

	}

	public WebElement scrollUntilProductLink(WebDriver driver, String product_Name) {
		PlpPage plp = new PlpPage(driver);
		WebDriverUtility wdUtil = new WebDriverUtility();
		wdUtil.ExplicitWaits(driver);
		By productLocator = By.xpath("//a[.='" + product_Name + "']");
		wdUtil.ExplicitWaitUntilElementIsVisible(driver, productLocator);
		WebElement product_link = driver.findElement(productLocator);
		wdUtil.ExplicitWaits(driver);
		wdUtil.scrollByAmountAction(driver, 0, 300);

		wdUtil.scrollToElementAction(driver, product_link);
		return product_link;
	}

}
