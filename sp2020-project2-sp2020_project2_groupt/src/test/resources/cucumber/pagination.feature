Feature: Pagniation display

  Scenario: Vacation planning page pagination
    Given I am on the Vacation Planning page
    When I vacation search with inputs '0' '100' '75' '5' 'Los Angeles' '1000'
    Then I should not see pagination

  Scenario: Activity planning page pagination
    Given I am on 'Activity Planning' page
    When I activity search with inputs 'swimming' '10' 'Los Angeles' '100'
    Then I should see a link to next page
    

