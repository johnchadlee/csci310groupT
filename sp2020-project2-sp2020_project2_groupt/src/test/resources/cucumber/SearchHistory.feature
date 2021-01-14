
Feature: Search History

  Scenario: Search history is only accessible when logged in
  	Given I am on main search page
  	When I redirect to search history page
  	Then The page does not exist

  Scenario: Search history page accessible
    Given I am on login page
    When I input email:"testemail@gmail.com" and password:"1234"
    And I click search history
    Then I am on search history page
  
  Scenario: Test search history element
  	Given I am on login page
  	When I input email:"testemail@gmail.com" and password:"1234"
  	Then I should be redirected to main search page
  	When I main search with input 'los angeles'
  	Then I click search history
  	And The search should appear in my search history