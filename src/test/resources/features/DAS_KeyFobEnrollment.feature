@KeyFobEnrollment
Feature: Verify KeyFob Enrollment Functionally

@DAS_KeyFobEnrollment
Scenario Outline: As a user I should be able to successfully enrol Key Fob and Assistance video 
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "KEYFOB list Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Keyfob List Settings" screen
When user press "enrollment" key from keyfob
And user selects "Keyfob SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Keyfob Overview" Screen 
When user selects "WATCH THE HOW TO VIDEO" from "Keyfob Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Keyfob Overview" screen from the "Video clip" screen
When user selects "Get Started" from "Keyfob Overview" screen
Then user should be displayed with the "NAME KEYFOB" screen
Then user edits the "Keyfob Sensor" name to <Custom name>
When user selects "Done" from "Configuration Success" screen
Then user should see the "Keyfob" status as "Assigned" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should be displayed with "keyfob" device on the "Keyfob list" screen
When user navigates to "Keyfob Settings" screen from the "Keyfob list" screen 
When user selects "delete sensor" from "Keyfob Settings" screen
Then user should receive a "Delete keyfob Confirmation" popup
And user "accepts" the "Delete Keyfob Confirmation" popup
Then user should not be displayed with "Keyfob" device on the "Keyfob list" screen

Examples:
|Mode |Custom name|
|Home |Keyfob|

#incaseerequied
#|OFF |Keyfob|

@DASKeyFobEnrollmentVerifyCancelFunctionality
Scenario Outline: Verify cancel functionality in all screens while enrolling a Key Fob
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "KEYFOB list Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Keyfob List Settings" screen
When user press "enrollment" key from keyfob
When user navigates to the <PreScreen> screen 
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the <PreScreen> screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Set Up Accessories" screen without displaying any sensor
		
Examples:
|Mode|PreScreen |
|Home|Sensor Overview |
|Home|Name Key Fob |
|Home|Key Fob|

#incaserequired 
|OFF|Sensor Overview |
| OFF |Name Key Fob |
| OFF |Key Fob|

@DASKeyFobEnrollmentVerifyBackArrowFunctionality
Scenario Outline: Verify Back arrow functionality in all screens while enrolling a Key Fob
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen
And user selects "Back arrow" from <PreScreen> screen
Then user should be displayed with the <PostScreen> screen

Examples:
|Mode |PreScreen				 |PostScreen |
|Home |Sensor Overview 			| Set up Accessories |
|Home |Name Key Fob				| Sensor Overview |
|Home |Key Fob				| Name Key Fob |

#incaserequired
|OFF |Sensor Overview 			| Set up Accessories |
| OFF |Name Key Fob				| Sensor Overview |
| OFF |Key Fob				| Name Key Fob |