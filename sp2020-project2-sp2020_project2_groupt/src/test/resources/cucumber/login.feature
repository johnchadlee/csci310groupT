Feature: Login

  Scenario: Login with invalid email
    Given I am on the login page
    When I input email:"idontexist@gmail.com" and password:"pass"
    And I click "loginButton"
    Then I should see invalid error
    
  Scenario: Login with invalid password
    Given I am on the login page
    When I input email:"testemail@gmail.com" and password:"wrongpassword"
    And I click "loginButton"
    Then I should see invalid error
      
  Scenario: Login with correct email and password
    Given I am on the login page
    When I input email:"testemail@gmail.com" and password:"1234"
    And I click "loginButton"
    Then I should redirect to authenticate page
    When I input passcode:"000000"
    And I click authenticate 
    Then I should redirect to main-search page
    And I should see my username
    
   Scenario: Navigate to main-page without Login
    Given I am on the login page
    When I click "mainbutton"
    Then I should see login warning 
   
   Scenario: Navigate to activity-page without Login
    Given I am on the login page
    When I click "activitybutton"
    Then I should see login warning 
   
   Scenario: Navigate to analysis-page without Login
   Given I am on the login page
    When I click "analysisbutton"
    Then I should see login warning 
   
   Scenario: Navigate to analysis-page without Login
   Given I am on the login page
    When I click "vacationbutton"
    Then I should see login warning 
    
    