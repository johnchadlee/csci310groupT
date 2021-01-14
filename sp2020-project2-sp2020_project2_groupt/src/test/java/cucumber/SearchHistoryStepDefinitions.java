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
import static org.junit.Assert.assertFalse;
import org.openqa.selenium.WebElement;

public class SearchHistoryStepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080";
	//URL needs to be updated
	private static final String SEARCH_URL = "http://localhost:8080/search-history.html";
	private static final String MAIN_URL = "http://localhost:8080/main-search.jsp";
	private static final String LOGIN_URL = "http://localhost:8080/login.jsp";
	public final WebDriver driver = new ChromeDriver();
	
//	@Given("I am on main search page")
//	public void i_am_on_main_search_page() {
//		driver.get(MAIN_URL);
//	}
	@When("I redirect to search history page")
	public void i_redirect_to_search_history_page() {
		//URL needs to be updated
		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/search-history.html");
	}
	@Then("The page does not exist")
	public void the_page_does_not_exist() {
//		assertFalse(driver.findElement(By.name("searchHistory")).click());	
	}
	
//	@Given("I am on login page")
//	public void i_am_on_login_page() {
//		driver.get(LOGIN_URL);
//	}
//	@When("I input email:{string} and password:{string}")
//	public void i_input_form(String email, String password) {
//		WebElement emailInput = driver.findElement(By.id("exampleInputEmail1"));
//		WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
//		emailInput.sendKeys(email); 
//		passwordInput.sendKeys(password); 
//	}
	@When("I click search history")
	public void i_click_search_history() {
		driver.findElement(By.name("searchHistory")).click(); //name needs to updated
	}
	@Then("I am on search history page")
	public void i_am_on_search_history_page() {
		driver.get(SEARCH_URL);
	}
	@Then("I should be redirected to main search page")
	public void i_should_be_redirected_to_main_search_page() {
		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/main_search.jsp");
	}
	@When("I main search with input {string}")
	public void i_main_search_with_inputs(String string) {
	    WebElement search = driver.findElement(By.id("homepage-search"));
	    search.sendKeys(string);
	}
	@Then("The search should appear in my search history")
	public void the_search_should_appear_in_my_search_history() {
		//Need to be implemented
	}
	@After()
	public void after() {
		driver.quit();
	}
	
}
