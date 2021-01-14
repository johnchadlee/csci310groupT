Feature: logout 

  Scenario: Logout without login
    Given I am on the login page
    When I clicked "logout"
    Then I should stay on the same page

  Scenario: Logout when logged in
    Given I am on the login page
    When I input email:"testemail@gmail.com" and password:"wrongpassword"
    And I click "loginButton"
    Then I should redirect to authenticate page
    When I input passcode:"000000"
    And I click authenticate 
    Then I should redirect to main-search page
    When I click home icon
    Then I should redirect to home page
    When I click "logout"
    Then I should redirect to main-search page
    And I should not see my username

