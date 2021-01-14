Feature: Hello
  Scenario: Open hello page from index page
    Given I am on the index page
    When I click the link
    Then I should see header 'Hello world!'
