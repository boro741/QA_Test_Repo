@DASCommandAndControl 
Feature: DAS Command And Control 
	As a user I want to change the status of my DAS device


@CommandControlviewSecuritysolutioncard		@UIAutomated
Scenario: As a user I want to see all modes in my securitysolution card 
Given user launches and logs in to the Lyric application
When user is set to "Home" mode through CHIL
And user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with the following "security mode" options:
| SecurityStates		|
| Home				|
| Away				|
| Night				|
| Off				|

@CommandControlfromHomemode
Scenario: As a user I want to switch to different states in my DAS device from Home mode 
Given user sets the entry/exit timer to "45" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "switched to Away via the app" activity log
#Then user gets location time
And user "closes" activity log
When user switches from "Away" to "Night" 
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night" 
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "switched to Night via the app" activity log
##Then user gets location time
And user "closes" activity log
When user switches from "Night" to "Off" 
Then user should receive a "Set to Off" popup
When user "dismisses" the "Set to Off" popup
Then user status should be set to "Night" 
When user switches from "Night" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "switched to Off via the app" activity log
###Then user gets location time
And user "closes" activity log
#When user navigates to "alerts" screen from "security solution cards" screen 
#Then user should receive "switched to Home" alert 
#And user should receive "switched to Away" alert 
#And user should receive "switched to Night" alert 
#And user should receive "switched to Off" alert 
#And user navigates to "security solution cards" screen from "activity log" screen 
	
@CommandControlfromAwaymode 
Scenario: As a user I want to switch to different states in my DAS device from Away mode 
Given user sets the entry/exit timer to "45" seconds 
When user launches and logs in to the Lyric application 
And user is set to "Away" mode through CHIL
Then user navigates to "Security Solution Card" screen from the "Dashboard" screen
When timer ends on user device
And user switches from "Away" to "Home" 
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
Then user should be displayed with the correct time stamp
When user switches from "Home" to "Night" 
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night" 
Then user should be displayed with the correct time stamp
When user switches from "Night" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
Then user should be displayed with the correct time stamp
	
@CommandControlfromNightmodemode 
Scenario: As a user I want to switch to different states in my DAS device from Night mode 
Given user sets the entry/exit timer to "45" seconds 
When user launches and logs in to the Lyric application 
And user is set to "Night" mode through CHIL
Then user navigates to "Security Solution Card" screen from the "Dashboard" screen
When timer ends on user device
When user switches from "Night" to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
When user switches from "Away" to "Off"
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
Then user should be displayed with the correct time stamp
Then user should be displayed with the correct time stamp
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
Then user should be displayed with the correct time stamp

@CommandControlfromOffmode 
Scenario: As a user I want to switch to different states in my DAS device from Off mode 
Given user sets the entry/exit timer to "45" seconds 
When user launches and logs in to the Lyric application 
And user is set to "Off" mode through CHIL
Then user navigates to "Security Solution Card" screen from the "Dashboard" screen
When user switches from "Off" to "Night"
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night" 
Then user should be displayed with the correct time stamp
When user switches from "Night" to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
When user switches from "Away" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
Then user should be displayed with the correct time stamp

@PushNotificationWhenSwitchedToDiffSecurityStates
Scenario: As a user I want to receive push notifications while switching between security states
Given user sets the entry/exit timer to "15" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user clears all push notifications
When "Away Mode" is triggered in the background
Then user should receive a "set to Away" push notification
And user clears all push notifications
When "Night Mode" is triggered in the background
Then user should receive a "set to Night" push notification
And user clears all push notifications
When "Off Mode" is triggered in the background
Then user should receive a "set to Off" push notification
And user clears all push notifications
When "Home Mode" is triggered in the background
Then user should receive a "set to Home" push notification