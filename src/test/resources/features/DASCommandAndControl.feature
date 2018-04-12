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

@CommandControlfromHomemode		@UIAutomated
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
Then user receives a "switched to Away by app" activity log
And user "closes" activity log
When user switches from "Away" to "Night" 
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night" 
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "switched to Night by app" activity log
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
Then user receives a "switched to Off by app" activity log
And user "closes" activity log
	
@CommandControlfromAwaymode		@UIAutomated
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
	
@CommandControlfromNightmodemode		@UIAutomated
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

@CommandControlfromOffmode		@UIAutomated
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

@CommandAndControlSecurityStatesWhenNavigatedBackToDashboard		@UIAutomated
Scenario: As a user I want to verify different states in dashboard screen by updating states in solutions card screen
Given user sets the entry/exit timer to "15" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
Then user status should be set to "Away" in the dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user switches from "Away" to "Night"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Night" text in the Dashboard screen
And user status should be set to "Night" in the dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Night" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Off" in the dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Off" to "Home"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Home" in the dashboard screen

@CommandAndControlSecurityStatesUpdateInDashboard			@UIAutomated
Scenario: As a user I want to set security states in dashboard screen
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user switches from "Home" to "Away" in the dashboard screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
When user "opens" activity log
Then user receives a "switched to Away by app" activity log
And user "closes" activity log
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Away" in the dashboard screen
When user switches from "Away" to "Night" in the dashboard screen
Then user should be displayed with a "Switching to Night" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night"
When user "opens" activity log
Then user receives a "switched to Night by app" activity log
And user "closes" activity log
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Night" in the dashboard screen
When user switches from "Night" to "Home" in the dashboard screen
Then user should be displayed with a "Switching to Home" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user status should be set to "Home"
When user "opens" activity log
Then user receives a "switched to Home by app" activity log
And user "closes" activity log
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Home" in the dashboard screen

@PushNotificationWhenSwitchedToDiffSecurityStates			@UIAutomated
Scenario: As a user I want to receive push notifications while switching between security states
Given user sets the entry/exit timer to "15" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user clears all push notifications
When user "Trigers Away Mode" in background
Then user receives a "set to Away" push notification
And user clears all push notifications
When user "Trigers Night Mode" in background
Then user receives a "set to Night" push notification
And user clears all push notifications
When user "Trigers Off Mode" in background
Then user receives a "set to Off" push notification
And user clears all push notifications
When user "Trigers Home Mode" in background
Then user receives a "set to Home" push notification

@UserpressesbackbuttonWhileSwitchingModes
Scenario: As a user I want to go back to dashboard when user press back button while switching modes 
Given user sets the entry/exit timer to "45" seconds 
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away"
Then user should be displayed with a "Switching to Away" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user status should be set to "Away" in the dashboard screen
And user is set to "Home" mode through CHIL 
When user switches from "Home" to "Away" in the dashboard screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
When user minimizes and maximizes the app 
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"

@CommandControlWhenwindow/DoorSensorfault 
Scenario Outline:
As a user I want to switch to different states in my DAS device when there is a sensor fault 
Given user sets the entry/exit timer to "45" seconds 
And user is set to <Status> mode through CHIL 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user "opens" the <Sensor type> 
And user switches from <Status>to "Away" 
Then user should be displayed with a "Switch to Away with the sensor fault" popup 
When user "confirms" the "Switch to Away with the sensor fault" popup 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
And user should be displayed with the "correct time stamp" 
When user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "OFF" on the "Sensor LIST" 
And user is set to <Status> mode through CHIL 
When user switches from <Status> to "Night" 
Then user should be displayed with a "Switch to Night with the sensor fault" popup 
When user user "confirms" the "Switch to Night with the sensor fault" popup 
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Night Status" 
And user should be displayed with the "correct time stamp" 
When user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "OFF" on the "Sensor List" 
And user is set to <Status> mode through CHIL 
When user switches from <Status> to "Off" 
Then user should receive a "Set to Off" popup 
When user "confirms" the "Set to Off" popup 
Then user should be displayed with a "Switching to Off" text 
And user should be displayed with the "Off Status" 
	
Examples: 
|Status|Sensor type|
|Home|Door|
|Home|Window|
|off|Door|
|off|Window|