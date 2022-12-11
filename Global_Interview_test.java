package main.java.org.GlobalInterview;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

// Importing Packages for TestNG
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

//=========================================
// User Story
// Description of the test
//=========================================
/**
  * Steps: 
  * 1. Open site https://magento.softwaretestingboard.com/
  * 2. Add to cart 4 - Gwyn Endurance Tee Medium Green
  * 3. Address should United Kingdom
  * 4. Check cart total is $92.00 (discount applied)
  * 5. Update the Quantity of  Gwyn Endurance Tee Medium Green to 3
  * 6. Add to cart 1 - Gwyn Endurance Tee Small Yellow
  * 7. Add to cart 1 Quest Lumaflex™ Band
  * 8. Check cart total is $116.00
*/

//======================================================
// Author : Mounika Kesireddy 
// Date   : 09/12/2022
// Project: Magento Shopping cart Value. 
//======================================================
public class Global_Interview_test {
	// Chrome Driver
	ChromeDriver obj   =    new ChromeDriver(); // Chrome Driver
	SoftAssert   sa    =    new SoftAssert(); // Soft Assertion
	String       url   =    "https://magento.softwaretestingboard.com/";

	// ===========================================
	// Open Browser and Launch URL
	// ===========================================
	@BeforeTest
	public void launchBrowser() {
		System.out.println("launching Chrome browser");
		System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		
		// Opening Link
		obj.get(url);
		
		// Maximize Window
		obj.manage().window().maximize();
		
		// Adding implicit timeout
		obj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	// ===========================================
	// To Search Elements
	// ===========================================
	public void SearchBox(String SearchElement) {
		WebElement searchBox = obj.findElement(By.id("search"));
		searchBox.sendKeys(SearchElement);
		searchBox.submit();
	}

	@Test
	public void Additems_Check_Cartval() throws InterruptedException {
		// Search For "Gwyn Endurance Tee" and submit
		SearchBox("Gwyn Endurance Tee");
		// ===========================================================
		//  Item: "Gwyn Endurance Tee", Colour: Green, Size: M, Qty: 4
		// ===========================================================
		// Select "Gwyn Endurance Tee" from search Results
		obj.findElementByLinkText("Gwyn Endurance Tee").click();
		// Select Colour - Green
		obj.findElementById("option-label-size-143-item-168").click(); // Green Color
		// Select Size - Medium
		obj.findElementById("option-label-color-93-item-53").click(); // Medium Size
		// Before entering quantity of items, clearing the quantity
		obj.findElementById("qty").clear();
		// Quantity of items - 4
		obj.findElementById("qty").sendKeys("4");
		// Adding 4 items to the Cart
		obj.findElementByCssSelector(".action.tocart.primary").click();

		// Implicit wait time for 10 Seconds
		obj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Click cart
		obj.findElementByLinkText("shopping cart").click();

		// ===========================================================
		// Expand Summary and set Shipping address to UK
		// ===========================================================
		obj.findElementByXPath("//*[@id=\"block-shipping-heading\"]").click();
		// Select dropdown to choose country
		WebElement testDropDown = obj.findElement(By.name("country_id"));
		Select dropdown = new Select(testDropDown);

		dropdown.selectByValue("GB"); // setting shipping address to UniTed Kingdom

		obj.findElementByName("postcode").clear(); // Setting Postcode to Harrow
		obj.findElementByName("postcode").sendKeys("HA2 0UR");

		obj.findElementByXPath("//*[@id=\"block-shipping-heading\"]").click(); // Minimize Summary options

		// Implicit wait time for 10 Seconds
		obj.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Explict wait time to check Cart value
		// WebDriverWait wait = new WebDriverWait(obj,20);
		// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div#cart-totals
		// strong > span"), "$92.00"));
		Thread.sleep(5000);
		String amount = obj.findElementByCssSelector("div#cart-totals strong > span").getText();
		System.out.println(
				"===============================================================================================");
		System.out.println(
				"Total products[4 Gwyn Green M shirst] cart value, Shipping: UK, Expected = $92, Actual = " + amount);
		System.out.println(
				"===============================================================================================");

		// Assertion - check for value $92.00 in the cart
		sa.assertEquals(obj.findElementByCssSelector("div#cart-totals strong > span").getText(), "$92.00");

		// ===================================================================
		//  Update Cart: "Gwyn Endurance Tee", Colour: Green, Size: M, Qty: 3
		// ===================================================================
		obj.findElementByCssSelector("table#shopping-cart-table input").clear();
		obj.findElementByCssSelector("table#shopping-cart-table input").sendKeys("3");
		obj.findElementByName("update_cart_action").submit();

		// Wait for cart is updated for three items and check price (shipping:UK)
		// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div#cart-totals
		// strong > span"), "$87.00"));
		// sa.assertEquals(obj.findElementByCssSelector("div#cart-totals strong >
		// span").getText(), "$87.00");

		Thread.sleep(5000);
		
		// ============================================================
		//  Item: "Gwyn Endurance Tee", Colour: Yellow, Size: S, Qty: 1
		// ============================================================	
		// search for GWYN Tee Shirt, to add Yellow colour and small size
		SearchBox("Gwyn Endurance Tee");

		// Adding Gwyn Tee shirt to cart (Color: Yellow, Size:Small)
		// Select "Gwyn Endurance Tee" from search Results
		obj.findElementByLinkText("Gwyn Endurance Tee").click();
		// Select Color - Yellow
		obj.findElementById("option-label-size-143-item-167").click();
		// Select Size - Small
		obj.findElementById("option-label-color-93-item-60").click();
		// Before entering quantity of items, clearing the quantity
		obj.findElementById("qty").clear();
		// Quantity of items - 4
		obj.findElementById("qty").sendKeys("1");
		// Add Item to the Cart
		obj.findElementByCssSelector(".action.tocart.primary").click();

		obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// ============================================================
		//  Item: "Quest Lumaflex", Qty: 1
		// ============================================================	
		// Search for Quest Lumaflex Band
		SearchBox("Quest Lumaflex");

		obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Add Quest Lumaflex Band to CART
		obj.findElementByXPath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[2]/div[2]/ol/li/div/div/strong/a").click();
		obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		obj.findElementById("qty").clear();
		obj.findElementById("qty").sendKeys("1");
		obj.findElementByCssSelector(".action.tocart.primary").click();

		// Visit shopping cart
		obj.findElementByLinkText("shopping cart").click();

		obj.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Wait for cart is updated for all items and check order total value
		// (shipping:UK)
		Thread.sleep(10000);

		amount = obj.findElementByCssSelector("div#cart-totals strong > span").getText();
		System.out.println("========================================================================================");
		System.out
				.println("Checking Cart Order Total Value: Expected Cart Value - $116, Actual Cart Value - " + amount);
		System.out.println("========================================================================================");

		// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div#cart-totals
		// strong > span"), "$116.00"));
		sa.assertEquals(obj.findElementByCssSelector("div#cart-totals strong > span").getText(), "$116.00");

		// Check and Fire Assertion
		sa.assertAll();
	}

	@AfterTest
	public void CloseBrowser() {
		System.out.println("launching Chrome browser");
		obj.close();
	}

}
