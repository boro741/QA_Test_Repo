@CameraMembership
Feature: Camera Membership
As a user I want to subscribe to plans offered by Honeywell for my camera clips.

#@SubscribeToStandardMothlySubscription
#  Scenario: As a user I want to subscibe for a standard monthly subscription plan for my first C1 camera
#      When user launches and logs in to the Lyric application
#      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
#      And user subscribes to a "Monthly" plan
#      And user accepts the checkout view
#      And user enters payment details and pay
#	  And user clicks done button in the subscription complete screen
#	  And user logs out of the app
#	  
#@SubscribeToStandardAnnualSubscription
#  Scenario: As a user I want to subscibe for a standard annual subscription plan for my first C1 camera
#      When user launches and logs in to the Lyric application
#      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
#      And user subscribes to a "Annual" plan
#      And user accepts the checkout view
#      And user enters payment details and pay
#	  And user clicks done button in the subscription complete screen
#	  And user logs out of the app
#	  
#@SubscribeToStandardMothlySubscription
#  Scenario: As a user I want to subscibe for a standard monthly subscription plan for my first C1 camera
#      When user launches and logs in to the Lyric application
#      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
#      And user subscribes to a "Monthly" plan
#      And user accepts the checkout view
#      And user enters payment details and pay
#	  And user clicks done button in the subscription complete screen
#	  And user logs out of the app
#	  
#@SubscribeToPremiumAnnualSubscription
#  Scenario: As a user I want to subscibe for a standard annual subscription plan for my one plus C1/C2 cameras
#      When user launches and logs in to the Lyric application
#      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
#      And user subscribes to a "Annual" plan
#      And user accepts the checkout view
#      And user enters payment details and pay
#	  And user clicks done button in the subscription complete screen
#	  And user logs out of the app
#	  
#@SubscribeToPremiumAnnualSubscription
#  Scenario: As a user I want to subscibe for a standard monthly subscription plan for my one plus C1/C2 cameras
#      When user launches and logs in to the Lyric application
#      When user navigates to "HONEYWELL MEMBERSHIP" screen from the "DASHBOARD" screen
#      And user subscribes to a "Monthly" plan
#      And user accepts the checkout view
#      And user enters payment details and pay
#	  And user clicks done button in the subscription complete screen
#	  And user logs out of the app

@Membersip_StandardMonthly_FromHoneywellMembershipScreen
Scenario: As a user I should be able to purchase a standard monthly subscription from Membership Menu
		#Given user have an account with a camera and no active subscription
		Given user launches and logs in to the Lyric application
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		And user selects "Monthly" plan from the "Honeywell Membership" screen and selects "Start Membership"
		#Then user navigates to "Membership Details" screen from the "Honeywell Membership" screen
		When user selects "Proceed To Checkout" option from the "Membership Details" screen
		#Then user navigates to "Checkout" screen from the "Membership Details" screen
		When user enters "valid" payment details and selects pay now option
		#Then user navigates to the "Summary" screen from the "Checkout" screen with "Purchase complete" Message
		When user selects "Done" option from the "Summary" screen
		#Then user is navigated to the "Dashboard" screen
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		#Then user is able to view an active "Monthly" subscription selected

@Membersip_StandardAnnaul_FromHoneywellMembershipScreen
Scenario: As a user I should be able to purchase a standard Annaul subscription from Membership Menu
		Given user have an account with a camera and no active subscription
		And user launches and logs in to the Lyric application
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		And user selects "Annual" plan from the "Honeywell Membership" screen
		And user selects "Start Membership" 
		Then user navigates to "Membership Details" screen from the "Honeywell Membership" screen
		When user selects "Proceed To Checkout" option from the "Membership Details" screen
		Then user navigates to "Checkout" screen from the "Membership Details" screen
		When user enters "valid" payment details and selects "PAY NOW" option
		Then user navigates to the "Summary" screen from the "Checkout" screen with "Purchase complete" Message
		When user selects "Done" option from the "Summary" screen
		Then user is navigated to the "Dashboard" screen
		When user navigates to "Honeywell Membership" screen from the "dashboard" screen
		Then user is able to view the an active "Annual" subscription selected

	