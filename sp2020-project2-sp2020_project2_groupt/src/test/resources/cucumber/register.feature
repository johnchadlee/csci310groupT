Feature: Register

  Scenario: Register with existing email
    Given I am on the register page
    When I input email:"testemail@gmail.com" and password:"1234" and confirm:"1234"
    When I click register
    Then I should redirect to login page
    And I should see already exist notice
   
    
  Scenario: Register with new email and correct password
    Given I am on the register page
    And I input email:"pleasedeleteme@gmail.com" and password:"pass" and confirm:"pass"
    When I click register
    Then I should redirect to login page
    And I should see completion notice
    