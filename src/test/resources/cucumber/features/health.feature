
Feature: the application health can be retrieved
  Scenario: Client makes request of type GET to /actuator/health
    When the Client gets /actuator/health
    Then the Client receives status code of 200
    And the Client receives body parameter status of UP

  Scenario: Client gets /actuator/health
    When the Client gets /actuator/health
    Then the Client receives status code of 200
    And the Client receives a body like
    """
    {
        "status": "UP"
    }
    """