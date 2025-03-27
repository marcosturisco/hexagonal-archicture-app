Feature: Zoo Administration

  Scenario: List all Animals
    Given user registered an "Lion" and "Tiger" animals
    When user request the listAllAnimals feature
    Then the system returns the "Lion" and "Tiger" animals