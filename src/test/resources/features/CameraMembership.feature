@CameraMembership
Feature: Camera Membership
As a user I want to subscribe to plans offered by Honeywell for my camera clips.

@Membersip_StandardMonthly_FromHoneywellMembershipScreen
Scenario: As a user I should be able to purchase a standard monthly subscription from Membership Menu
		Given user launches and logs in to the Lyric application
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		And user selects "Monthly" plan from the "Honeywell Membership" screen and selects "Start Membership"
		And user selects "Proceed To Checkout" option from the "Membership Details" screen
		And user enters "valid" payment details and selects pay now option
		And user selects "Done" option from the "Summary" screen
		Then user navigates to "Honeywell Membership" screen from the "dashboard" screen
		#And user is able to view an active "Monthly" subscription selected

@Membersip_StandardAnnaul_FromHoneywellMembershipScreen
Scenario: As a user I should be able to purchase a standard Annaul subscription from Membership Menu
		Given user launches and logs in to the Lyric application
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		And user selects "Annual" plan from the "Honeywell Membership" screen and selects "Start Membership" 
		And user selects "Proceed To Checkout" option from the "Membership Details" screen
		And user enters "valid" payment details and selects pay now option
		And user selects "Done" option from the "Summary" screen
		Then user navigates to "Honeywell Membership" screen from the "dashboard" screen
		#And user is able to view the an active "Annual" subscription selected

	