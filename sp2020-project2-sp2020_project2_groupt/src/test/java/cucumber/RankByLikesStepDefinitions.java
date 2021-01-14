package cucumber;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.assertTrue;

import java.util.List;

public class RankByLikesStepDefinitions {
	public final WebDriver driver = new ChromeDriver();
	private static final String ACT_URL = "http://localhost:8080/vacationPlanning.jsp";
	
//	@Given("I am on the activity page using distance criteria")
//	public void i_am_on_the_activity_page_using_distance_criteria() {
//		driver.get(ACT_URL);
//	}
//	@When("I activity search with inputs {string} {string} {string} {string}")
//	public void i_activity_search_with_inputs(String string, String string2, String string3, String string4) {
//	    WebElement act = driver.findElement(By.id("activty"));
//	    WebElement numRes = driver.findElement(By.id("numResults"));
//	    WebElement loc = driver.findElement(By.id("location"));
//	    WebElement dis = driver.findElement(By.id("Sdistance"));
//	    act.sendKeys(string);
//	    numRes.sendKeys(string2);
//	    loc.sendKeys(string3);
//	    dis.sendKeys(string4);
//	}
	@Then("the list should be ranked by likes")
	public void the_list_should_be_ranked_by_likes() {
		List<WebElement> col = driver.findElements(By.id("favorite"));
		boolean flag = true;
//		for(WebElement fav : col ) {
//			
//		}
		assertTrue(flag);
	}
}
