@CameraMembership
Feature: Camera Membership
As a user I want to subscribe to plans offered by Honeywell for my camera clips.

@SubscribeToStandardMothlySubscription
  Scenario: As a user I want to subscibe for a standard monthly subscription plan for my first C1 camera
      When user launches and logs in to the Lyric application
      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
      And user subscribes to a "Monthly" plan
      And user accepts the checkout view
      And user enters payment details and pay
	  And user clicks done button in the subscription complete screen
	  And user logs out of the app
	  
@SubscribeToStandardAnnualSubscription
  Scenario: As a user I want to subscibe for a standard monthly subscription plan for my first C1 camera
      When user launches and logs in to the Lyric application
      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
      And user subscribes to a "Annual" plan
      And user accepts the checkout view
      And user enters payment details and pay
	  And user clicks done button in the subscription complete screen
	  And user logs out of the app