Feature: a User can be added, searched for, and updated

  Scenario: Client adds a new User successfully
    Given the User bobsmith@mailinator.com does not exist
    When the Client puts /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 200
    And the Client receives body parameter firstName of Bob
    And the Client receives body parameter lastName of Smith
    And the Client receives body parameter email of bobsmith@mailinator.com

  Scenario: Client adds a new User that already exists
    Given the User bobsmith@mailinator.com already exists
    When the Client puts /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Provided email is already in use.

  Scenario: Client cannot add a User without an email
    Given the User bobsmith@mailinator.com does not exist
    When the Client puts /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Email is required.

  Scenario: Client can get a User that exists
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client gets /user/bobsmith@mailinator.com
    Then the Client receives status code of 200
    And the Client receives body parameter firstName of Bob
    And the Client receives body parameter lastName of Smith
    And the Client receives body parameter email of bobsmith@mailinator.com

  Scenario: Client will get an error trying to find a User that does not exist
    Given the User bobsmith@mailinator.com does not exist
    When the Client gets /user/bobsmith@mailinator.com
    Then the Client receives status code of 400
    And the Client receives an error of Could not find user.

  Scenario: Client can update a User who exists
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user with a body like
    """
    {
        "firstName": "Alice",
        "lastName": "Doe",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 200
    And the Client receives body parameter firstName of Alice
    And the Client receives body parameter lastName of Doe
    And the Client receives body parameter email of bobsmith@mailinator.com

  Scenario: Client can update a User with the same parameters
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 200
    And the Client receives body parameter firstName of Bob
    And the Client receives body parameter lastName of Smith
    And the Client receives body parameter email of bobsmith@mailinator.com

  Scenario: Client can update a User who exists and their email
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user/bobsmith@mailinator.com with a body like
    """
    {
        "firstName": "Alice",
        "lastName": "Doe",
        "email": "alicedoe@mailinator.com"
    }
    """
    Then the Client receives status code of 200
    And the Client receives body parameter firstName of Alice
    And the Client receives body parameter lastName of Doe
    And the Client receives body parameter email of alicedoe@mailinator.com

  Scenario: Client cannot update a User email if it does not exist
    Given the User bobsmith@mailinator.com already exists
    And the User alicesmith@mailinator.com does not exist
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user with a body like
    """
    {
        "firstName": "Alice",
        "lastName": "Doe",
        "email": "alicesmith@mailinator.com"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Could not find user.

  Scenario: Client cannot update a User email if it does not exist in the path
    Given the User bobsmith@mailinator.com does not exist
    And the User alicesmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user/bobsmith@mailinator.com with a body like
    """
    {
        "firstName": "Alice",
        "lastName": "Doe",
        "email": "alicesmith@mailinator.com"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Could not find user.

  Scenario: Client cannot update a User email to one that is already used
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    And the User alicesmith@mailinator.com already exists
    When the Client posts /user/bobsmith@mailinator.com with a body like
    """
    {
        "firstName": "Alice",
        "lastName": "Doe",
        "email": "alicesmith@mailinator.com"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Provided email is already in use.

  Scenario: Client cannot update a User without an email in the body or path
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client posts /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Email is required to find a user.

  Scenario: Client can delete a User that exists
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client deletes /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 200

  Scenario: Client cannot delete a User that does not exists
    Given the User bobsmith@mailinator.com does not exist
    When the Client deletes /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith",
        "email": "bobsmith@mailinator.com"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Could not find user.

  Scenario: Client cannot delete a User without an email provided
    Given the User bobsmith@mailinator.com already exists
    And the User has first name of Bob
    And the User has last name of Smith
    When the Client deletes /user with a body like
    """
    {
        "firstName": "Bob",
        "lastName": "Smith"
    }
    """
    Then the Client receives status code of 400
    And the Client receives an error of Email is required to find a user.
