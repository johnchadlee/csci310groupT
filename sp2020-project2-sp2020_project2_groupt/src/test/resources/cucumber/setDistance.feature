Feature: Set Distance Radius

	  Scenario: Clicking the 'Find My Vacation Spot' button returns results that match distance criteria
	    Given I am on the vacation page using distance criteria
	    When I vacation search with inputs '0' '100' '75' '10' 'Los Angeles' '13000'
	    Then I should see the cities that are no farther than '13000'

	  Scenario: Clicking the 'Find My Activity Spot' button returns results that match distance criteria
	    Given I am on the activity page using distance criteria
	    When I activity search with inputs 'swimming' '10' 'Los Angeles' '13000'
	    Then I should see the cities that are no farther than '13000'

   