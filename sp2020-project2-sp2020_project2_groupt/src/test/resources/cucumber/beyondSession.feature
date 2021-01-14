Feature: Maintain users info beyond session

  Scenario: Information stored when logged in
    Given I am on login page
    When I input email:"testemail@gmail.com" and password:"wrongpassword"
    And I click "loginButton"
    Then I click activity icon
    And I should redirected to activity planning page
    When I activity search with inputs 'swimming' '10' 'Los Angeles' '100'
    Then I click logout button
    When I run select query
    Then I should see result