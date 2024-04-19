Feature: Membership

  Scenario Outline:

    Given I am on the website for the form and I use "<browser>"
    And I enter my first name "<firstName>"
    And Next I enter my last name "<lastName>"
    And Then I enter my password "<password>"
    And Then I enter the password confirmation "<passConfirm>"
    And I enter my the role best suited for me which is "<role>"
    And I accept the "<termsAndConditions>" for the form
    When I click submit
    Then I should get a "<message>" or be redirected to a confirmation webpage

    Examples:
      | browser | firstName        | lastName | password          | passConfirm      | role   | termsAndConditions | message                            |
      | edge    | Domingo          | Chavez   | WatchYourSix1!    | WatchYourSix1!   | player | accept             | confirmation                       |
      | chrome  | Alistair Stanley |          | 123Hejsan#        | 123Hejsan#       | coach  | accept             | Missing last name                  |
      | edge    | Jack             | Ryan     | PresidentRyan123# | PresidentRyan123 | fan    | accept             | Password did not match            |
      | chrome  | John             | Clark    | Rainbow6#         | Rainbow6#        | coach  | do not accept      | Didn't accept terms and conditions |