Feature: Zoo Administration

  Scenario: Register new animals in the zoo
    Given the user is on the animal registration feature
    When the user submits the following animals for registration
      | name  | species         | age |
      | Lion  | Panthera leo    | 10  |
      | Tiger | Panthera tigris | 19  |
    Then the system should return a confirmation with the details of the registered animals
      | name  | species         | age |
      | Lion  | Panthera leo    | 10  |
      | Tiger | Panthera tigris | 19  |

  Scenario: List all registered animals
    Given the zoo has the following animals registered
      | name  | species         | age |
      | Lion  | Panthera leo    | 10  |
      | Tiger | Panthera tigris | 19  |
    When the user requests the list of all animals
    Then the system should display the complete list of registered animals
      | name  | species         | age |
      | Lion  | Panthera leo    | 10  |
      | Tiger | Panthera tigris | 19  |