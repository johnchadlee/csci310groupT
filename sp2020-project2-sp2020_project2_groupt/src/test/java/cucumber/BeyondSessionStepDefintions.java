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

import com.mysql.cj.xdevapi.Statement;

import csci310.DBConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class BeyondSessionStepDefintions extends DBConnection{
	private static final String ROOT_URL = "http://localhost:8080";
	private static final String LOGIN_URL = "http://localhost:8080/login.jsp";
	Connection con;
	String query;
    ResultSet rs;
	java.sql.Statement stmt;
	public final WebDriver driver = new ChromeDriver();

//	@Given("I am on the login page")
//	public void i_am_on_the_login_page() {
//		driver.get(LOGIN_URL);
//	}
//	@When("I input email:{string} and password:{string}")
//	public void i_input_form(String email, String password) {
//		WebElement emailInput = driver.findElement(By.id("exampleInputEmail1"));
//		WebElement passwordInput = driver.findElement(By.id("exampleInputPassword1"));
//		emailInput.sendKeys(email); 
//		passwordInput.sendKeys(password); 
//	}
//	@Then("I click {string}")
//	public void i_click_activity_icon(String activity) {
//		driver.findElement(By.name(activity)).click();
//	}
	@Then("I should redirect to activity planning page")
	public void i_should_redirect_to_activity_planning_page() {
	    assertEquals(driver.getCurrentUrl(), "http://localhost:8080/activityPlanning.jsp");
	}
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
//	@Then("I click {string}")
//	public void i_click_logout_button(String logout) {
//		driver.findElement(By.name(logout)).click();
//	}
    @When("I run the select query")
    public void i_run_the_select_query() throws Throwable {
        query = "select * from SearchHistory where search=?;";
        stmt = con.createStatement();   
        rs = stmt.executeQuery(query);
    }   
    @Then("I should see the result")
    public void i_should_see_the_result_as(String name) throws Throwable {
        boolean flag = true;
    	if (rs.next()){
        	flag = true;
        }
    	else {
    		flag = false;
    	}
    	assertTrue(flag);
    }
	
}
