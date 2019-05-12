Feature: Add artist name and related albums titles as comments to blog
  As a user
  I want to be able to post artist name and related albums titles from chinook database as Blog comments
  So that the frontend can manage the blog

  Scenario: Transfer artist name and related album titles to Blog as comments
    Given That chinook database artist name with albums and Blog exist
    When I transfer artist name and album titles to blog as comment
    Then Blog comment should be added successfully
    And artist and related albums should be deleted
