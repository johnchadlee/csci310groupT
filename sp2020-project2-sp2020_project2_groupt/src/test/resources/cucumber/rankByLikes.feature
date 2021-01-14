Feature: Results ranked by likes

  Scenario: Activity page ranked by likes
    Given I am on 'Activity Planning' page
    When I activity search with inputs 'swimming' '10' 'Los Angeles' '100'
	  Then the list should be ranked by likes

