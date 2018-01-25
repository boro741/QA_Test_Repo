@DASCommandAndControl
Feature: DAS Command And Control
As a user I want to change the status of my DAS device

  @CommandControl @--xrayid:ATER-6257
  Scenario: As a user I want to switch to different states in my DAS device
    Given user sets the entry/exit timer to "45" seconds
     When user launches and logs in to the Lyric application
      And user selects "DAS device" from the dashboard
      And user switches from "Home" to "Away"
     Then user should be displayed with a "Switching to Away" text
      And user should be displayed with a switching timer
     When timer ends on user device
     Then user status should be set to "Away"
      And user should be displayed with the correct time stamp
     When user switches from "Away" to "Home"
  #Then user should be displayed with a "Switching to Home" text
     Then user status should be set to "Home"
      And user should be displayed with the correct time stamp
     When user switches from "Home" to "Night"
     Then user should be displayed with a "Switching to Night" text
      And user should be displayed with a switching timer
     When timer ends on user device
     Then user status should be set to "Night"
      And user should be displayed with the correct time stamp
     When user switches from "Night" to "Away"
     Then user should be displayed with a "Switching to Away" text 
      And user should be displayed with a switching timer
     When timer ends on user device
     Then user status should be set to "Away"
      And user should be displayed with the correct time stamp
     When user switches from "Away" to "Night"
     Then user should be displayed with a "Switching to Night" text
      And user should be displayed with a switching timer
     When timer ends on user device
     Then user status should be set to "Night"
      And user should be displayed with the correct time stamp
     When user switches from "Night" to "Home"
  #Then user should be displayed with a "Switching to Home" text
     Then user status should be set to "Home"
      And user should be displayed with the correct time stamp
