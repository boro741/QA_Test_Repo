@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:

#Given relay is reset
And user dismisses all alerts and notification through CHIL

	@Attention_FromCamera @--xrayid:ATER-6142
	Scenario: As a user I can initiate the alarms from camera on seeing any mischief acts
	Given "Home" mode as precondition
    Given user sets the entry/exit timer to "15" seconds
    Given "Away" mode as precondition
    Given user launches and logs in to the Lyric application
    And user taps on "ATTENTION FROM CAMERA SOLUTION CARD"
	Then user taps on "dismiss alarm popup"
	
	
	
