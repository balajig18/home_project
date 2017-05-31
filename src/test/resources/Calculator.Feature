Feature: Addition

  Scenario Outline: Add Positive Number
    Given User is trying to add "<Number1>" and "<Number2>"
    When Adding Two Positive Number "<Number1>" and "<Number2>"
    Then Result should be equal To "<Result>"

    Examples: 
      | Number1 | Number2 | Result |
      |       1 |       3 |      4 |
      |    1000 |     150 |   1150 |
      |    1200 |     150 |   1350 |