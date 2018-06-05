@KeyFobEnrollment
Feature: Verify Key Fob Enrolment Functionally

@DASKeyFobEnrollment
Scenario Outline: As a user I should be able to successfully enrol Key Fob and Assistance video 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "KEYFOB INSTALLATION" screen from the "Dashboard" screen
Then user navigates to "Set Up Accessories" screen from the "Sensors" screen
When user triggers "KEYFOB" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Name Key Fob" screen from the "Sensor Overview" screen
And user inputs <Custom name> in the "Name Key Fob" screen
Then user should be displayed with the "Key Fob" screen
Then user navigates to "Set Up Accessories Configured" screen from the "Key Fob" screen
When user navigates to "Dashboard" screen from the "Set Up Accessories Configured" screen
Then user should be displayed with "Security" device on the "dashboard" screen
When user navigates to "Keyfob Settings" screen from the "Dashboard" screen 
And user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup 
And user "dismisses" the "Delete Keyfob Confirmation" popup
When user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup 
And user "accepts" the "Delete Keyfob Confirmation" popup
Then user should not be displayed with "Keyfobs" on the "Keyfob Settings" screen

Example :
|Custom name|
|Honeywell1|


@DASKeyFobEnrollmentVerifyCancelFunctionality
Scenario Outline: Verify cancel functionality in all screens while enrolling a Key Fob
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen 
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the <PreScreen> screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Set Up Accessories" screen without displaying any sensor
		
Example:
|PreScreen |
|Sensor Overview |
|Name Key Fob |
|Key Fob|


@DASKeyFobEnrollmentVerifyBackArrowFunctionality
Scenario Outline : Verify Back arrow functionality in all screens while enrolling a Key Fob
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen
And user selects "Back arrow" from <PreScreen> screen
Then user should be displayed with the <PostScreen> screen

Example:
|PreScreen				 |PostScreen |
|Sensor Overview 			| Set up Accessories |
|Name Key Fob				| Sensor Overview |
|Key Fob					| Name Key Fob |