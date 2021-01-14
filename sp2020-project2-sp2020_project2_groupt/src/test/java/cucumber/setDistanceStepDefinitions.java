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

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebElement;

public class setDistanceStepDefinitions {

	private static final String ROOT_URL = "http://localhost:8080";
	private static final String VAC_URL = "http://localhost:8080/activityPlanning.jsp";
	private static final String ACT_URL = "http://localhost:8080/vacationPlanning.jsp";
	public final WebDriver driver = new ChromeDriver();
	
	@Given("I am on the vacation page using distance criteria")
	public void i_am_on_the_vacation_page_using_distance_criteria() {
		driver.get(VAC_URL);
	    throw new io.cucumber.java.PendingException();
	}

	@When("I vacation search with inputs {string} {string} {string} {string} {string} {string}")
	public void i_vacation_search_with_inputs(String string, String string1 ,String string2, String string3, String string4, String string5) {
	    WebElement tempLow = driver.findElement(By.id("tempRangeLow"));
	    WebElement tempHi = driver.findElement(By.id("tempRangeHigh"));
	    WebElement precip = driver.findElement(By.id("precipitation"));
	    WebElement numRes = driver.findElement(By.id("numResults"));
	    WebElement loc = driver.findElement(By.id("location"));
	    WebElement dis = driver.findElement(By.id("Sdistance"));
	    tempLow.sendKeys(string);
	    tempHi.sendKeys(string1);
	    precip.sendKeys(string2);
	    numRes.sendKeys(string3);
	    loc.sendKeys(string4);
	    dis.sendKeys(string5);
	    dis.submit();
	}

	@Given("I am on the activity page using distance criteria")
	public void i_am_on_the_activity_page_using_distance_criteria() {
		driver.get(ACT_URL);
	}

	@When("I activity search with inputs {string} {string} {string} {string}")
	public void i_activity_search_with_inputs(String string, String string2, String string3, String string4) {
	    WebElement act = driver.findElement(By.id("activty"));
	    WebElement numRes = driver.findElement(By.id("numResults"));
	    WebElement loc = driver.findElement(By.id("location"));
	    WebElement dis = driver.findElement(By.id("Sdistance"));
	    act.sendKeys(string);
	    numRes.sendKeys(string2);
	    loc.sendKeys(string3);
	    dis.sendKeys(string4);
	}

	@Then("I should see the cities that are no farther than {string}")
	public void i_should_see_the_cities_that_are_no_farther_than(String dist) {
	    int distance = Integer.parseInt(dist);
		List<WebElement> col = driver.findElements(By.id("distance"));
		boolean flag = true;
		for(WebElement radius : col ) {
			int d = Integer.parseInt(radius.getAttribute("innerHTML"));
			if(distance < d) {
				flag = false;
			}
		}
		assertTrue(flag);
	}
	@After()
	public void after() {
		driver.quit();
	}
}
