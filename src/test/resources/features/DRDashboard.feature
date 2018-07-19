@DRDashBoard
Feature: Verify DR status on Dashboard
As a user, I want to verify DR status on the dashboard

  @VerifyDREventOnMultiStatAccount
  Scenario: Verify Saving Event Schedule Message
  As a user, I should be able to identify thermostats with DR events on my multistat account dashboard
    Given user <Thermostat> is enrolled with DR for "thermostat1"
      And user has triggered DR event for "thermostat1"
     When user logs in to the Lyric application
     Then "saving event schedule" message pop up is not displayed on the dashboard
	 And "saving event schedule" message pop up is displayed on the primary card of "thermostat1"
     When DR event has started on the user device for "thermostat1" 
     Then "thermostat1" will "have" a "Saving event until" green label on the dashboard
	 When DR event has ended on the user device for "thermostat1"
	 Then "thermostat1" will "not have" a "Saving event until" green label on the dashboard
     Then user logs out of the application
     Examples"
     |HB|
     |Thermostat|
	 