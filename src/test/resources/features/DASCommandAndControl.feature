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

@CommandControlWhenDoorSensorIsInFault
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a door sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
#And user door <Fault Type>
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Home" to "Away"
Then user should receive a "Switch to Away" popup
When user "dismisses" the "Switch to Away" popup
Then user status should be set to "Home" 
When user switches from "Home" to "Away" 
Then user should receive a "Switch to Away" popup
When user "accepts" the "Switch to Away" popup
Then user should be displayed with a "Switching to Away" text
Then timer ends on user device
And user status should be set to "Away"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Off Status> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night" 
Then user should receive a "Switch to Night" popup
When user "dismisses" the "Switch to Night" popup
Then user status should be set to "Home" 
When user switches from "Home" to "Night" 
Then user should receive a "Switch to Night" popup
When user "accepts" the "Switch to Night" popup
Then user should be displayed with a "Switching to Night" text
Then timer ends on user device
And user status should be set to "Night"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Off Status> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
#And user door <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	| 
|	door				|	opened				|	open						|	off				|	closed				|
|	door				|	tampered				|	cover tampered			|	off				|	tamper restored		|

@CommandControlWhenWindowSensorIsInFault
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a window sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
#And user window <Fault Type>
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Home" to "Away"
Then user should receive a "Switch to Away" popup
When user "dismisses" the "Switch to Away" popup
Then user status should be set to "Home" 
When user switches from "Home" to "Away" 
Then user should receive a "Switch to Away" popup
When user "accepts" the "Switch to Away" popup
Then user should be displayed with a "Switching to Away" text
Then timer ends on user device
And user status should be set to "Away"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Off Status> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night" 
Then user should receive a "Switch to Night" popup
When user "dismisses" the "Switch to Night" popup
Then user status should be set to "Home" 
When user switches from "Home" to "Night" 
Then user should receive a "Switch to Night" popup
When user "accepts" the "Switch to Night" popup
Then user should be displayed with a "Switching to Night" text
Then timer ends on user device
And user status should be set to "Night"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Off Status> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
#And user door <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	|
|	window			|	opened				|	open						|	off				|	closed				|
|	window			|	tampered				|	cover tampered			|	off				|	tamper restored		|

@CommandControlWhenMotionSensorIsInFault
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a motion sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
#And user motion sensor <Fault Type>
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Home" to "Away"
Then user should receive a "Switch to Away" popup
When user "dismisses" the "Switch to Away" popup
Then user status should be set to "Home" 
When user switches from "Home" to "Away" 
Then user should receive a "Switch to Away" popup
When user "accepts" the "Switch to Away" popup
Then user should be displayed with a "Switching to Away" text
Then timer ends on user device
And user status should be set to "Away"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Off Status> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night"
Then user should be displayed with a "Switching to Night" text
Then timer ends on user device
And user status should be set to "Night"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
#And user motion sensor <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	| 
|	motion sensor	|	tampered				|	cover tampered			|	off				|	tamper restored		|