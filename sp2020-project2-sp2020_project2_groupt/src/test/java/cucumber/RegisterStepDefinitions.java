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

public class RegisterStepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080";
	private static final String REGISTER_URL = "http://localhost:8080/register.html";

	public final WebDriver driver = new ChromeDriver();
	
	@Given("I am on the register page")
	public void i_am_on_the_register_page() {
		driver.get(REGISTER_URL);
	}
	
	@When("I input email:{string} and password:{string} and confirm:{string}")
	public void i_input_email_and_password_and_confirm(String email, String password, String confirm) {
		WebElement emailInput = driver.findElement(By.id("exampleInputEmail1"));
		WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
		WebElement comfirmInput = driver.findElement(By.id("exampleInputPassword2"));
		emailInput.sendKeys(email); 
		passwordInput.sendKeys(password); 
		comfirmInput.sendKeys(confirm);
	}

	@When("I click register")
	public void i_click_register() {
	    driver.findElement(By.id("submitButton")).click();
	}
	
	@Then("I should redirect to login page")
	public void i_should_redirect_to_login_page() {
	    String URL = driver.getCurrentUrl();
	    assertEquals(URL, "http://localhost:8080/login.jsp");
	}
	@Then("I should see already exist notice")
	public void i_should_see_already_exist_notice() {
	    WebElement message = driver.findElement(By.id("message"));
	    String text = message.getText();
	    boolean isExists = text.indexOf("already exist") != -1? true: false;
	    assertTrue(isExists);
	}
	
	
	@Then("I should see completion notice")
	public void i_should_see_completion_notice() {
		WebElement message = driver.findElement(By.id("message"));
		String text = message.getText();
		boolean isExists = text.indexOf("User created sucessfully") != -1? true: false;
		assertTrue(isExists);
	}
	@After()
	public void after() {
		driver.quit();
	}

}