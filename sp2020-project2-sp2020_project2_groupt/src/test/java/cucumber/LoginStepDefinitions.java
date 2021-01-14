package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebElement;

/**
 * Step definitions for Cucumber tests.
 */

public class LoginStepDefinitions {
	private static final String ROOT_URL = "https://localhost:8080";
	private static final String LOGIN_URL = "https://localhost:8080/login.jsp";

	WebDriver driver;
	
	@Before	
	public void setDriver() {
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("--ignore-certificate-errors");
	    driver = new ChromeDriver(options);
	}


	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
		
		driver.get(LOGIN_URL);
	}
	
	@When("I input email:{string} and password:{string}")
	public void i_input_form(String email, String password) {
		WebElement emailInput = driver.findElement(By.id("exampleInputEmail1"));
		WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
		emailInput.sendKeys(email); 
		passwordInput.sendKeys(password); 
	}

	@When("I click {string}")
	public void i_click_button(String buttonName) {
		driver.findElement(By.name(buttonName)).click(); 
	}

	@Then("I should see invalid error")
	public void i_should_see_invalid_error() {
		String text = driver.findElement(By.id("message")).getText();
		boolean flag = text.indexOf("Invalid email/password") != -1? true: false;
		assertTrue(flag);
	}
	
	@Then("I should redirect to authenticate page")
	public void i_should_redirect_to_authenticate_page() {
	    assertEquals(driver.getCurrentUrl(), "https://localhost:8080/authentication.html");
	}
	
	@When("I input passcode:{string}")
	public void i_input_passcode(String string) {
		WebElement passcodeInput = driver.findElement(By.name("passcode"));
		passcodeInput.sendKeys(string);
	}
	
	@When("I click authenticate")
	public void i_click_authenticate() {
		driver.findElement(By.id("auth")).click();
	}
	
	@Then("I should redirect to main-search page")
	public void i_should_redirect_to_main_search_page() {
		assertEquals(driver.getCurrentUrl(), "https://localhost:8080/main-search.jsp");
	}
	
	@Then("I should see my username")
	public void i_should_see_my_username() {
		String text = driver.findElement(By.name("useremail")).getText();
		boolean flag = text.indexOf("Logged in as: testemail@gmail.com") != -1? true: false;
		assertTrue(flag);
	}
	@Then("I should see login warning")
	public void i_should_see_login_warning() {
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		boolean flag = text.indexOf("Please sign in to continue") != -1? true: false;
		assertTrue(flag);
	}


	@After()
	public void after() {
		driver.quit();
	}
}