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

@UserpressesbackbuttonWhileSwitchingModes		@UIAutomated
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

@CommandControlWhenDoorSensorIsInFault		@UIAutomated
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a door sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user door <Fault Type>
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
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
And user door <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	| 
|	door				|	opened				|	open						|	off				|	closed				|
|	door				|	tampered				|	cover tampered			|	off				|	tamper restored		|

@CommandControlWhenWindowSensorIsInFault		@UIAutomated
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a window sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user window <Fault Type>
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
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
And user window <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	|
|	window			|	opened				|	open						|	off				|	closed				|
|	window			|	tampered				|	cover tampered			|	off				|	tamper restored		|

@CommandControlWhenMotionSensorIsInFault		@UIAutomated
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a motion sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user motion sensor <Fault Type>
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
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night"
Then user should be displayed with a "Switching to Night" text
Then timer ends on user device
And user status should be set to "Night"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user is set to "Home" mode through CHIL 
And user switches from "Home" to "Off" 
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Home"
Then user should be displayed with a "Switching to Home" text
And user status should be set to "Home"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
And user motion sensor <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	| 
|	motion sensor	|	tampered				|	cover tampered			|	off				|	tamper restored		|

@CommandControlWhenSensorIsOffline 		@CannotAutomate
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a sensor trouble/fault and in Home mode 
Given user sets the entry/exit timer to "45" seconds 
And user is set to Home mode through CHIL 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user <sensor mode> the <Sensor type> 
And user switches from Home to "Away" 
Then user should be displayed with a "Switch to Away with the sensor fault" popup 
When user "confirms" the "Switch to Away with the sensor fault" popup 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
And user should be displayed with the "correct time stamp" 
When user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "OFF" on the "Sensor LIST" 
When user navigates to "security solution cards" screen  from "sensors list" 
And user is set to Home mode through CHIL 
And user <sensor mode> the <Sensor type> 
And user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "OFFLINE" on the "Sensor LIST" 
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
|Sensor type|Sensor mode|
|Door|cover tamper|
|Door|Offline|
|Window|cover tamper|  
|Window|Offline|

#Covered in CommandControlWhenDoorSensorIsInFault scenario
@CommandControlWhenSensorisbypassed
Scenario Outline: As a user I want to switch to different states in my DAS device when there is a sensor bypass 
Given user sets the entry/exit timer to "45" seconds 
And user is set to Home mode through CHIL 
And there is a user <sensor mode> the <Sensor type> 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from "Home" to "Away" 
And user "confirms" the "Switch to Away with the sensor fault" popup 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
When user switches from "Away" to "Night" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Night Status" 
When user switches from "Night" to "Away" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
When user navigates to "activity log" screen from "security solution cards" screen 
Then user should receive "sensor bypassed via app with time stamp by actual user" in activity log 

Examples: 
|Sensor type|Sensor mode|
|Door|Open|
|Door|Offline|
|Door|cover tamper|
|Window|Open|
|Window|Offline|
|Window|cover tamper|

#Covered in CommandControlWhenDoorSensorIsInFault scenario
@CommandControlUsercancelsbypass 
Scenario: As a user I want to cancel the sensor bypass popup when switching states 
Given user sets the entry/exit timer to "45" seconds 
And user is set to "Home" mode through CHIL 
And there is a fault/trouble in any of the sensor 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from "Home" to "Away" 
Then user should be displayed with a "Switch to Away with the sensor fault" popup 
When user user "dismiss" the "Switch to Away with the sensor fault" popup 
Then user should be navigates back to "primarycard with current state" 
And user switches from "Away" to "Home" 
When user switches from "Home" to "Night" 
Then user should be displayed with a "Switch to Night with the sensor fault" popup 
When user user "dismiss" the "Switch to Night with the sensor fault" popup 
Then user should be navigates back to "primarycard with current state".

@CommandControlsensorlowbattery 		@CannotAutomate
Scenario Outline: As a user I shouldn't be allowed to bypass a sensor with Low battery 
Given user sets the entry/exit timer to "45" seconds 
And user is set to "Home" mode through CHIL 
And user <Sensor type> is in "Low Battery" 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from "Home" to "Away" 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
And user should be displayed with the "correct time stamp" 
When user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "LOW BATTERY" on the "Sensor LIST" 
And user is set to "Home" mode through CHIL 
When user switches from "Home" to "Night" 
Then user should be displayed with a "Switching to Night" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Night Status" 
And user should be displayed with the "correct time stamp" 
When user navigates to "sensors list"  from "security solution cards" screen 
Then user should see the "Sensor Satus" status as "LOW BATTERY" on the "Sensor List" 

Examples: 
|Sensor type|
|Door|
|Window|
|Motion Sensor|

#Covered in the UserpressesbackbuttonWhileSwitchingModes scenario
@CommandControlUserpressbackbutton 
Scenario: As a user I want to go back to dashboard when user press back button while switching modes 
Given user sets the entry/exit timer to "60" seconds 
And user is set to "Home" mode through CHIL 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from "Home" to "Away" 
Then user should be displayed with the "Switching to Away" text 
And user navigates to "Dashboard" screen from "security solution cards" screen 
When user navigates to "security solution cards" screen from "Dashboard" screen 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
And user should be displayed with the "correct time stamp" 
And user is set to "Home" mode through CHIL 
When user switches from "Home" to "Away" 
Then user should be displayed with the "Switching to Away" text 
And user navigates to "Dashboard" screen from "security solution cards" screen 
When user navigates to "backround" and returns "foreground" 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Away Status" 
And user should be displayed with the "correct time stamp"

@CommandControlwhenpanelisinbatterymode			@CannotAutomate 
Scenario: As a user I want to get a error message when my panel is disconnected from power 
Given user turns off the panel 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
Then User should be displayed with "POWER DISCONNECTED RUNNING ON BATTERY"  text 

@CommandControlWhenDASPanelIsNotReachable		@UIAutomated
Scenario: As a user I want to get a error message when my DAS panel is not reachable
Given user sets the entry/exit timer to "15" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user DAS device with ADB ID "9c48da88" is deregistered and booted
When user switches from "Home" to "Away"
Then user should receive a "Unable to connect to base station" popup
When user "clicks on OK in" the "Unable to connect to base station" popup
Then user status should be set to "Home"

#@CommandControlafterpanelrestoringfromofflinestate
@CommandControlWhenDASPanelIsRebooted		@UIAutomated
Scenario: As a user I want to switch to different security state after DAS panel is rebooted
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
When user switches from "Home" to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
Then user should be displayed with the correct time stamp

@CommandControlofflinemode		@CannotAutomate		
Scenario: As a user I want see offline status in my app when my DAS panel goes offline 
Given user turns off the panel 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
Then user should be displayed with "Offline status" 
And user should be displayed with the "correct time stamp"

@CommandControlgeofencecrossedAwaymode		@CannotAutomate
Scenario: As a user I want to switch to Away mode in my DAS device from geofence 
Given user sets the entry/exit timer to "15" seconds 
And user is set to "Home" mode through CHIL 
When user launches and logs in to the Lyric application 
And user user minimizes the application 
And user  "Moves out" from the geofence radius through CHIL 
Then user should receive "Switched to away" push notifications 
When user selects  the "Switched to away" Push notification 
Then user should be displayed with the "Away Status" 
When user is set to "Home" mode through CHIL 
When user navigates to "activity log" screen from "security solution cards" screen 
And user should receive "switched to Away status via app with time stamp by actual user" in activity log 
When user navigates to "alerts" screen from "activity log" screen 
Then user should receive "switched to Away" alert   Given user sets the entry/exit timer to "45" seconds 

@CommandControlgeofencecrossedHomemode		@CannotAutomate
Scenario: As a user I want to switch to Home mode in my DAS device from geofence 
Given user sets the entry/exit timer to "15" seconds 
And user is set to "Home" mode through CHIL 
When user launches and logs in to the Lyric application 
And user user minimizes the application 
And user  "Moves in" from the geofence radius through CHIL 
Then user should receive "Switched to Home" push notifications 
When user selects  the "Switched to Home" Push notification 
Then user should be displayed with the "Home Status" 
When user is set to "Home" mode through CHIL 
When user navigates to "activity log" screen from "security solution cards" screen 
And user should receive "switched to Home status via app with time stamp by actual user" in activity log 
When user navigates to "alerts" screen from "activity log" screen 
Then user should receive "switched to Home" alert

@CommandControlFromKeyFob		@UIAutomated
Scenario: As a user I want to want to switch different modes in my DAS device from keyfob 
Given user sets the entry/exit timer to "45" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
Then user clears all push notifications
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away" using keyfob
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Away"
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "SWITCHED TO AWAY BY KEYFOB" activity log
And user "closes" activity log
Then user receives a "set to Away by keyfob" push notification
When user is set to "Home" mode through CHIL
Then user clears all push notifications
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away" using keyfob
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to "Night"
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "SWITCHED TO NIGHT BY KEYFOB" activity log
And user "closes" activity log
Then user receives a "set to Night by keyfob" push notification
And user is set to "Home" mode through CHIL
Then user clears all push notifications
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away" using keyfob
Then user status should be set to "Off"
Then user should be displayed with the correct time stamp
When user "opens" activity log
Then user receives a "SWITCHED TO OFF BY KEYFOB" activity log
And user "closes" activity log
Then user receives a "set to Off by keyfob" push notification
And user clears all push notifications

@CommandControlFromKeyFobAndPanelWiFiIsDown		@CannotAutomate
Scenario: As a user I want to switch to different states in my DAS device from keyfob when panel wifi down 
Given user is set to "Home" mode through CHIL 
And user turn off the panel wifi 
When user launches and logs in to the Lyric application 
And user "Activates Off mode" from keyfob 
Then user should be displayed with the "Off Status" in panel 
When user "Activates Away mode" from keyfob 
Then user should be displayed with the "Away Status" in panel 
When user "Activates Night mode" from keyfob 
Then user should be displayed with the "Night Status" in panel 
When user "Activates Home mode" from keyfob 
Then user should be displayed with the "Home Status" in panel

@CommandControlFromKeyFobWhenDoorSensorIsInFault		@UIAutomated
Scenario Outline: As a user I should not be allowed to switch to Away/Night mode through keyfob when there is a door sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Off" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user door <Fault Type>
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Away" using keyfob
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Night" using keyfob
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user door <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Update Fault Type	|
|	door				|	opened				|	open						|	closed				|
|	door				|	tampered				|	cover tampered			|	tamper restored		|

@CommandControlFromKeyFobWhenWindowSensorIsInFault		@UIAutomated
Scenario Outline: As a user I should not be allowed to switch to Away/Night mode through keyfob when there is a door sensor fault 
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to "Off" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user door <Fault Type>
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Away" using keyfob
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
When user switches from "Off" to "Night" using keyfob
Then user status should be set to "Off"
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Type> status as <Sensor Issue Type> on the "Sensor Status"
And user door <Update Fault Type>

Examples:
|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Update Fault Type	|
|	window			|	opened				|	open						|	closed				|
|	window			|	tampered				|	cover tampered			|	tamper restored		|

#Covered in the the above scenario
@CommandControlfromkeyfobwhensensortrouble
Scenario Outline:
As a user I should not be allowed to switch to Away/Night mode through keyfob when sensor is trouble 
Given user sets the entry/exit timer to "60" seconds 
And user is set to "Off" mode through CHIL 
And user "Cover Tamper" the <Sensor type> 
When user launches and logs in to the Lyric application 
And user "Activates Away mode" from keyfob 
Then user should be displayed with the "Off Status" 
When user "Activates Night mode" from keyfob 
Then user should be displayed with the "Off Status" 

Examples: 
|Status|Sensor type|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|

@CommandControlfromvoicecommandsAwaymode		@SystemTeamToAutomate
Scenario: As a user I should be able to switch to Away mode through voice commands 
Given user sets the entry/exit timer to "45" seconds 
And user is set to "Home" mode through CHIL 
When user launches and logs in to the Lyric application 
And user user minimizes the application 
And user "Activates Away mode" from Voice commands 
Then user should receive "Switched to away" push notifications 
When user selects  the "Switched to away" Push notification 
Then user should be displayed with the "Away Status" 
When user is set to "Home" mode through CHIL 
When user navigates to "activity log" screen from "security solution cards" screen 
And user should receive "switched to Away status via app with time stamp by actual user" in activity log 

@CommandControlfromvoicecommandsNightmode		@SystemTeamToAutomate
Scenario: As a user I should be able to switch to Night mode through voice commands 
Given user sets the entry/exit timer to "45" seconds 
And user is set to "Home" mode through CHIL 
When user launches and logs in to the Lyric application 
And user user minimizes the application 
And user "Activates Night mode" from Voice commands 
Then user should receive "Switched to Night" push notifications 
When user selects  the "Switched to Night" Push notification 
Then user should be displayed with the "Night Status" 
When user navigates to "activity log" screen from "security solution cards" screen 
And user should receive "switched to Night status via app with time stamp by actual user" in activity log 
And user should receive "switched to Night" push notification 

@CommandControlfromvoicecommandswhensensorfault		@SystemTeamToAutomate
Scenario Outline: As a user I shouldnot be allowed to switch to Away/Night mode through voice commands when sensor is fault 
Given user sets the entry/exit timer to "60" seconds 
And user is set to <status> mode through CHIL 
And user "opens" the <Sensor type> 
When user launches and logs in to the Lyric application 
And user "Activates Away mode" from Voice commands 
Then user should be displayed with the "Off Status" 
When user "Activates Night mode" from Voice commands 
Then user should be displayed with the "Off Status" 

Examples: 
|Status|Sensor type|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|

@CommandControlfromvoicecommandswhensensortrouble		@SystemTeamToAutomate
Scenario Outline: As a user I shouldnot be allowed to switch to Away/Night mode through voice commands when sensor is trouble 
And user is set to <status> mode through CHIL 
And user "Cover Tamper" the <Sensor type> 
When user launches and logs in to the Lyric application 
And user "Activates Away mode" from Voice commands 
Then user should be displayed with the "Off Status" 
When user "Activates Night mode" from Voice commands 
Then user should be displayed with the "Off Status" 

Examples: 
|Status|Sensor type|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|

@CommandControlChangeInDoorSensorStateDuringExitDelay		@UIAutomated 
Scenario Outline: As a user I should not be alarmed during exit delay time and door sensor is in fault
Given user sets the entry/exit timer to "60" seconds
When user launches and logs in to the Lyric application
And user is set to <Security Status> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Security Status> to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
When user door <Fault Type>
And user door <Update Fault Type>
Then user should not be displayed with Alarm screen
And user status should be set to "Away"
Then user should be displayed with the correct time stamp
When user is set to <Security Status> mode through CHIL
And user switches from <Security Status> to "Night"
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
When user door <Fault Type>
And user door <Update Fault Type>
Then user should not be displayed with Alarm screen
And user status should be set to "Night"

Examples:
|	Security Status		|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Update Fault Type	|
|	Home					|	door				|	opened				|	open						|	closed				|
|	Off					|	door				|	opened				|	open						|	closed				|

@CommandControlChangeInWindowSensorStateDuringExitDelay		@UIAutomated
Scenario Outline: As a user I should not be alarmed during exit delay time and window sensor is in fault
Given user sets the entry/exit timer to "60" seconds
When user launches and logs in to the Lyric application
And user is set to <Security Status> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Security Status> to "Away"
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
When user window <Fault Type>
And user window <Update Fault Type>
Then user should not be displayed with Alarm screen
And user status should be set to "Away"
Then user should be displayed with the correct time stamp
When user is set to <Security Status> mode through CHIL
And user switches from <Security Status> to "Night"
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
When user window <Fault Type>
And user window <Update Fault Type>
Then user should not be displayed with Alarm screen
And user status should be set to "Night"

Examples:
|	Security Status		|	Sensor Type		|	Fault Type			|	Sensor Issue Type		|	Update Fault Type	|
|	Home					|	window			|	opened				|	open						|	closed				|
|	Off					|	window			|	opened				|	open						|	closed				|

@CommandControlChangeInMotionSensorStateDuringExitDelay		@UIAutomated
Scenario: As a user I should not be alarmed during exit delay time and window sensor is in fault
Given user sets the entry/exit timer to "60" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Night"
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
When user "sensor" detects the "Motion"
Then user should not be displayed with Alarm screen
And user status should be set to "Night"
Then user should be displayed with the correct time stamp
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Off"
Then user should receive a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
Then user should be displayed with the correct time stamp
When user "sensor" detects the "Motion"
Then user should not be displayed with Alarm screen

#Covered in the previous scenario
@CommandControlMultipleSensorAndMotionSensorFault 
Scenario: As a user I want to view the motionsensor status when i have all types of sensor enrolled 
Given user sets the entry/exit timer to "60" seconds 
And user is set to Home mode through CHIL 
And user has one sensor of each type enrolled 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from "Home" to "Away" 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
And user user "Sensor" detects "Motion" 
Then user should not be displayed with Alarm screen 
When user is set to Home mode through CHIL 
And user switches from "Home" to "Night" 
Then user should be displayed with the "Switching to Night" text 
And user should be displayed with the "switching timer" 
And user "Sensor" detects "Motion" 
Then user should not be displayed with Alarm screen 
When user is set to Home mode through CHIL 
And user switches from "Home" to "Night" 
Then user should be displayed with the "Switching to Night" text 
And user should be displayed with the "switching timer" 
When "Switching timer" ends on user device 
Then user should be displayed with the "Night Status" 
And user "Sensor" detects "Motion" 
Then user should not be displayed with Alarm screen

#Covered in CommandControlChangeInDoorSensorStateDuringExitDelay and CommandControlChangeInWindowSensorStateDuringExitDelay scenarios
@CommandControlMultipleSensorWithDifferentStateAndOneSensorIsInFault 
Scenario Outline: As a user should not be alarmed during exit delay and my sensor is faulted 
Given user sets the entry/exit timer to "60" seconds 
And user is set to <Status> mode through CHIL 
And user has one sensor of each type enrolled 
When user launches and logs in to the Lyric application 
And user navigates to "security solution cards" screen from "Dashboard" screen 
And user switches from <Status> to "Away" 
Then user should be displayed with the "Switching to Away" text 
And user should be displayed with the "switching timer" 
And user "opens" the <Sensor type> 
Then user should not be displayed with Alarm screen 
When user is set to <Status> mode through CHIL 
And user switches from <Status> to "Night" 
Then user should be displayed with the "Switching to Night" text 
And user should be displayed with the "switching timer" 
And user "opens" the <Sensor type> 
Then user should not be displayed with Alarm screen 

Examples: 
|Status|Sensor type|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|

@CommandControlMultipleSensorsWithFault		@UIAutomated
Scenario Outline: As a user I want to switch to different states in my DAS device when multiple sensors are in fault condition
Given user sets the entry/exit timer to "30" seconds
When user launches and logs in to the Lyric application
And user is set to <Security Status> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user door <Fault Type>
And user window <Fault Type>
And user "sensor" detects the "Motion"
And user switches from <Security Status> to "Away"
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
When user is set to <Security Status> mode through CHIL
And user switches from <Security Status> to "Night"
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
And user door <Update Fault Type>
And user window <Update Fault Type>

Examples: 
|	Security Status		|	Fault Type			|	Sensor Issue Type		|	Off Status		|	Update Fault Type	| 
|	Home					|	opened				|	open						|	off				|	closed				|
|	Off					|	opened				|	open						|	off				|	closed				|