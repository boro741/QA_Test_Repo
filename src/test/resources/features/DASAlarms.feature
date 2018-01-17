@DASAlarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

  Background:  
    Given user dismisses all alerts and notification through CHIL
  
  @AttentionFromCamera @--xrayid:ATER-6142
  Scenario: As a user I can initiate the alarms from camera on seeing any mischief acts
    Given user is set to "Home" mode through CHIL
      And user sets the entry/exit timer to "15" seconds
      And user is set to "Away" mode through CHIL
     When user launches and logs in to the Lyric application
      And user taps on "ATTENTION FROM CAMERA SOLUTION CARD"
     Then user taps on "dismiss alarm popup"
     