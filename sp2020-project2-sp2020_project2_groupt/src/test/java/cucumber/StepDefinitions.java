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

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	static final String ROOT_URL = "http://localhost:8080/";

	public final WebDriver driver = null;// = new ChromeDriver();

	@Given("I am on the index page")
	public void i_am_on_the_index_page() {
		driver.get(ROOT_URL);
	}

	@When("I click the link")
	public void i_click_the_link() {
		driver.findElement(By.tagName("a")).click(); 
	}

	@Then("I should see header {string}")
	public void i_should_see_header(String string) {
		assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(string));
	}

	@After()
	public void after() {
//		driver.quit();
	}
//	
//	@Then("I should see text box {string}")
//	public void i_should_see_text_box(String string) {
//		System.out.println(driver.findElement(By.tagName("input")));
//		assertTrue(true);
//	}
	
}
