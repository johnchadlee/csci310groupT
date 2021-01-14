package cucumber;
import io.cucumber.datatable.DataTable;
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
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.Map;

public class PaginationStepDefinitions {
	
	private static final String ROOT_URL = "http://localhost:8080";
	private static final String VAC_URL = "http://localhost:8080/activityPlanning.jsp";
	private static final String ACT_URL = "http://localhost:8080/vacationPlanning.jsp";
	public final WebDriver driver = new ChromeDriver();
	
    @Given("I am on the Vacation Planning page")
    public void i_am_the_vacation_planning_page(){
    	driver.get(VAC_URL);
    }
    @When("I am on the Activity Planning page")
    public void i_am_on_the_activity_planning_page(){
    	 driver.get(ACT_URL);
    }
    @Then ("I should not see pagination")
    public void i_should_see_a_disabled_pagination_link_to() {
    	boolean flag = true;
    	if(driver.findElement(By.id("results_paginate") ) == null) {
    		flag = true;
    	}
    	else {
    		flag = false;
    	}
    	assertTrue(flag);
    }
//	@When("I vacation search with inputs {string} {string} {string} {string} {string} {string}")
//	public void i_vacation_search_with_inputs(String string, String string1 ,String string2, String string3, String string4, String string5) {
//	    WebElement tempLow = driver.findElement(By.id("tempRangeLow"));
//	    WebElement tempHi = driver.findElement(By.id("tempRangeHigh"));
//	    WebElement precip = driver.findElement(By.id("precipitation"));
//	    WebElement numRes = driver.findElement(By.id("numResults"));
//	    WebElement loc = driver.findElement(By.id("location"));
//	    WebElement dis = driver.findElement(By.id("Sdistance"));
//	    tempLow.sendKeys(string);
//	    tempHi.sendKeys(string1);
//	    precip.sendKeys(string2);
//	    numRes.sendKeys(string3);
//	    loc.sendKeys(string4);
//	    dis.sendKeys(string5);
//	    dis.submit();
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
	@Then("I should see a link to next page")
	public void i_should_see_a_link_to_next_page() {
		boolean flag = true;
		if(driver.findElement(By.id("results_next") ) != null) {
			flag = true;
		}
		else {
			flag = false;
		}
		assertTrue(flag);
	}
}
