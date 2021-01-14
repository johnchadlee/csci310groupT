package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebElement;

public class logoutStepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080";
	private static final String LOGIN_URL = "http://localhost:8080/login.jsp";
	private static final String HOME_URL = "http://localhost:8080/home.jsp";
	
	public final WebDriver driver = new ChromeDriver();

//	@Given("I am on the login page")
//	public void i_am_on_the_login_page() {
//		driver.get(LOGIN_URL);
//	}
//	
//	@When("I input email:{string} and password:{string}")
//	public void i_input_form(String email, String password) {
//		WebElement emailInput = driver.findElement(By.id("exampleInputEmail1"));
//		WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
//		emailInput.sendKeys(email); 
//		passwordInput.sendKeys(password); 
//	}
//
//	@When("I click {string}")
//	public void i_click_button(String buttonName) {
//		driver.findElement(By.name(buttonName)).click(); 
//	}
//	
//	@Then("I should redirect to authenticate page")
//	public void i_should_redirect_to_authenticate_page() {
//	    assertEquals(driver.getCurrentUrl(), "http://localhost:8080/authentication.html");
//	}
//	
//	@When("I input passcode:{string}")
//	public void i_input_passcode(String string) {
//		WebElement passcodeInput = driver.findElement(By.name("passcode"));
//		passcodeInput.sendKeys(string);
//	}
//	
//	@When("I click authenticate")
//	public void i_click_authenticate() {
//		driver.findElement(By.id("auth")).click();
//	}
//	
//	@Then("I should redirect to main-search page")
//	public void i_should_redirect_to_main_search_page() {
//		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/main-search.jsp");
//	}
//	
	@When("I click home icon")
	public void i_click_home_icon() {
		driver.findElement(By.id("home")).click();
	}
	
	@Then("I should redirect to home page")
	public void i_should_redirect_to_home_page() {
		driver.get(HOME_URL);
	}
	
	@Then("I should not see my username")
	public void i_should_not_see_my_username() {
		String text = driver.findElement(By.name("useremail")).getText();
		boolean flag = text.indexOf("Logged in as: testemail@gmail.com") != -1? true: false;
		assertTrue(flag);
	}
	
	@After()
	public void after() {
		driver.quit();
	}
}
