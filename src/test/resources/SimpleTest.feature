Feature: Testing User saving flow

  Scenario Outline: Searching for clients but service returns single result
    Given Service has only one user with name <name> and id <id>
    When Client make a call to API to get all users
    Then API returns only one single user with name <name> and id <id>

    Examples:
      | name  | id |
      | Peter | 13 |
      | John  | 77 |
#
#
#  Scenario: Using API with existent user and successfully find all of them.
#    Given Service has following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#    When Client make a call to API to get all users
#    Then API returns following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#
#  Scenario: Adding user with valid name and id when api already have users
#    Given Service has following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#    When Client make a call to API and add user with name William and id 777
#    And Client make a call to API to get all users
#    Then API returns following users
#      | name    | id  |
#      | john    | 1   |
#      | peter   | 2   |
#      | William | 777 |
#
#  Scenario: Trying to add user without id and failing to do it
#    Given Service has following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#    When Client make a call to API and add user with name William and empty id
#    Then Api respond with bad request and message contains id
#    And Client make a call to API to get all users
#    Then API returns following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#
#  Scenario: Trying to add existent users and fail to do it
#    Given Service has following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |
#    When Client make a call to API and add user with name peter and id 2
#    Then Api respond with conflict response and message contains already exists
#    And Client make a call to API to get all users
#    Then API returns following users
#      | name  | id |
#      | john  | 1  |
#      | peter | 2  |