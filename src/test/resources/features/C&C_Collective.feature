@DASCommandAndControl 
Feature: DAS Command And Control 
As a user I want to change the status of my DAS device

Background:
Given reset relay as precondition

@CommandControlviewSecuritysolutioncard	@UIAutomated
Scenario: As a user I want to see all modes in my security solution card 
Given user launches and logs in to the Lyric application
#When user is set to "Home" mode through CHIL
Then user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user should be displayed with the following "SECURITY MODE" options:
| securityState		|
| Home				|
| Away				|
| Night				|
| Off				|


@CommandControlSecuritycardModeFromHomeandOFFChangeActivitlyLogAndPushNotificaiton	@UIAutomated
Scenario Outline: As a user I want to switch to different system modes from OFf and Home in my DAS device and verify activity log screen and push notification 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Mode> to <UMode>
And user should be displayed with a switching to <UMode> text
And user should be displayed with a switching timer
And timer ends on user device
And user status should be set to "<UMode>"
Then user should be displayed with the correct time stamp
And user receives a <Push Notification> push notification
And user selects the <Push Notification> push notification
When user "opens" activity log
Then user receives a <expectedActivity> activity log
And user "closes" activity log

Examples:
|Timer| Mode | UMode| Push Notification |expectedActivity|
|15| Home | Away |SET TO AWAY |switched to Away by app|
|15| Night| Away | SET TO AWAY | switched to Away by app|
|15| Home | Night |SET TO NIGHT |switched to Night by app|
|15| Away | Night |SET TO NIGHT |switched to Night by app|
|15| OFF | Away | SET TO AWAY |switched to Away by app|

#Incaserequired
#|30| Home | Away | SET TO AWAY |switched to Away by app|
#|30| Night| Away | SET TO AWAY |switched to Away by app|
#|30| Home | Night |SET TO NIGHT |switched to Night by app|
#|30| Away | Night |SET TO NIGHT |switched to Night by app|
#|30| OFF | Away | SET TO AWAY |switched to Away by app|
#|30| OFF| Night |SET TO NIGHT |switched to Night by app|
#|45| Home | Away | SET TO AWAY |switched to Away by app|
#|45| Night| Away | SET TO AWAY |switched to Away by app|
#|45| Home | Night |SET TO NIGHT |switched to Night by app|
#|45| Away | Night |SET TO NIGHT |switched to Night by app|
#|45| OFF | Away | SET TO AWAY |switched to Away by app|
#|45| OFF| Night |SET TO NIGHT |switched to Night by app|
#|60| Home | Away |SET TO AWAY | switched to Away by app|
#|60| Night| Away |SET TO AWAY | switched to Away by app|
#|60| Home | Night |SET TO NIGHT |switched to Night by app|
#|60| Away | Night |SET TO NIGHT |switched to Night by app|
#|60| OFF | Away |SET TO AWAY |switched to Away by app|
#|60| OFF| Night |SET TO NIGHT |switched to Night by app|

@CommandControlSecuritycardModetoOFFChangeActivitlyLogAndPushNotificaiton	 @UIAutomated
Scenario Outline: As a user I want to switch to different system modes to OFf in my DAS device and verify activity log screen and push notification 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user switches from <Mode> to "Off" 
Then user should receive a "Set to Off" popup
When user "dismisses" the "Set to Off" popup
Then user status should be set to <UMode> 
When user switches from <UMode> to "Off" 
Then user receives a "Set to Off" popup
When user "accepts" the "Set to Off" popup
Then user status should be set to "Off"
And user receives a <Push Notification> push notification
And user selects the <Push Notification> push notification
When user "opens" activity log
Then user receives a <expectedActivity> activity log
And user "closes" activity log
Examples:
|Timer| Mode |expectedActivity|
|15| Home | switched to OFF by app|
|15| Night | switched to Away by app|
|15| Away |switched to Night by app|

#Incaserequired
#|30| Home | switched to OFF by app|
#|30| Night | switched to Away by app|
#|30| Away |switched to Night by app|
#|45| Home | switched to OFF by app|
#|45| Night | switched to Away by app|
#|45| Away |switched to Night by app|
#|60| Home | switched to OFF by app|
#|60| Night | switched to Away by app|
#|60| Away |switched to Night by app|


@CommandControlSecuritycardModeToHomeChangeActivitlyLogAndPushNotificaiton	 @UIAutomated
Scenario Outline: As a user I want to switch to different system modes To Home in my DAS device and verify activity log screen and push notification 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Mode> to "Home"
And user should be displayed with a switching to <UMode> text
And user status should be set to "Home"
Then user should be displayed with the correct time stamp
And user receives a <Push Notification> push notification
And user selects the <Push Notification> push notification
When user "opens" activity log
Then user receives a <expectedActivity> activity log
And user "closes" activity log

Examples:
|Timer| Mode | UMode|expectedActivity|
|15| Home | Home | switched to Home by app|
|15| Away| Home | switched to Home by app|
|15| Night | Home |switched to Home by app|
|15| OFF | Home |switched to Home by app|

#incaserequired 
#|15| Home  | switched to Home by app|
#|15| Away| switched to Home by app|
#|15| Night  |switched to Home by app|
#|15| OFF  |switched to Home by app|
#|30| Home | switched to Home by app|
#|30| Away|  switched to Home by app|
#|30| Night | switched to Home by app|
#|30| OFF | switched to Home by app|
#|60| Home |  switched to Home by app|
#|60| Away|  switched to Home by app|
#|60| Night | switched to Home by app|
#|60| OFF | switched to Home by app|

@CommandControlDasboardModetoHomeAwayAndNightChangePushNotificaiton @UIAutomated
Scenario Outline: As a user I want to verify Switching modes home , away, night on Dashboard 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
Then user is set to <Mode> mode through CHIL
And user navigates to “Dashboard” screen 
When user switches from <Mode> to <UMode>
Then user should be displayed with a switching to <UMode> text
Then user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to <UMode>
Then user should be displayed with the correct time stamp
And user receives a <Push Notification> push notification

Examples:
|Timer| Mode | UMode|
|15| Home | Away | 
|15| Night| Away | 
|15| Home | Night |
|15| Away | Night |
|15| OFF| Away |
|15| OFF| Night |

#Incase required
#|30| Home | Away | 
#|30| Night| Away | 
#|30| Home | Night |
#|30| Away | Night |
#|30 | OFF| Away |
#|30 | OFF| Night |
#|45| Home | Away | 
#|45| Night| Away | 
#|45| Home | Night |
#|45| Away | Night |
#|45 | OFF| Away |
#|45 | OFF| Night |
#|60| Home | Away | 
#|60| Night| Away | 
#|60| Home | Night |
#|60| Away | Night |
#|60| OFF| Away |
#|60| OFF| Night |

@CommandControlDasboardOFFModeStatusAndPushNotificaiton  @UIAutomated
Scenario Outline: As a user I want to verify OFF mode status in dashboard screen 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
Then user is set to “OFF” mode through CHIL
When user navigates to “Dashboard”
Then user status should be set to "OFF" in the "Dashboard" Screen
And user should be displayed with the correct time stamp

@CommandControlDashbaordModeToHomeChangeActivitlyLogAndPushNotificaiton
Scenario Outline: As a user I want to switch to different system modes To Home in my DAS device on dashbaord and verify activity log screen and push notification 
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to  "Dashboard" screen
And user switches from <Mode> to "Home"
And user should be displayed with a switching to <UMode> text
And user status should be set to "Home"
Then user should be displayed with the correct time stamp
And user receives a <Push Notification> push notification

Examples:
|Timer| Mode |
|15| Home | 
|15| Away| 
|15| Night | 
|15| OFF | 

#incaserequired 
#|15| Home  | 
#|15| Away| 
#|15| Night  |
#|15| OFF  |
#|30| Home | 
#|30| Away|  
#|30| Night | 
#|30| OFF | 
#|60| Home |  
#|60| Away|  
#|60| Night |
#|60| OFF | 

@CommandControlDasboardModefromSolutionCardChangePushNotificaiton  @UIAutomated
Scenario Outline: As a user I want to verify different states in dashboard screen by updating states in solutions card screen
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Mode> to <UMode>
When user navigates to "Dashboard" Screen from the "Security Solution Card" screen
Then  user should be displayed with a switching to <UMode> text in the "Dashboard" screen
Then user should be displayed with a switching timer
Then timer ends on user device
And user status should be set to <UMode>
Then user should be displayed with the correct time stamp
And  user receives a <Push Notification> push notification

Examples:
|Timer| Mode | UMode|
|15| Home | Away | 
|15| Night| Away | 
|15| Home | Night |
|15| Away | Night |
|15| OFF| Away |
|15| OFF| Night |

#Incaserequired
#|30| Home | Away | 
#|30| Night| Away | 
#|30| Home | Night |
#|30| Away | Night |
#|30| OFF| Away |
#|30| OFF| Night |
#|45| Home | Away | 
#|45| Night| Away | 
#|45| Home | Night |
#|45| Away | Night |
#|45| OFF| Away |
#|45| OFF| Night |
#|60| Home | Away | 
#|60| Night| Away | 
#|60| Home | Night |
#|60| Away | Night |
#|60| OFF| Away |
#|60| OFF| Night |


@SensorstatusHomemode @P3     @UIAutomated
Scenario Outline: As a user I want to have expected sensors status when my security panel is in Home mode and Off mode
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description  
When user navigates to "SENSOR STATUS" screen from the "SECURITY SOLUTION CARD" screen
Then user should see the <Sensors> status as <Sensor State> on the "SENSOR STATUS"
Examples:
| Sensors       | Sensor State |Mode|
| Door Sensor   | Closed       |OFF |
| Window Sensor | Closed       |Home|
| Motion Sensor | Standby      |Home|
| OSMV Sensor   | Standby      |Home|
| ISMV Sensor   | Standby      |Home|

@SensorstatusArmmode @P3   @UIAutomated
Scenario Outline: As a user i want to have expected sensors status when my security panel is in Away mode Night mode
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
#And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description  
When user navigates to "SENSOR STATUS" screen from the "SECURITY SOLUTION CARD" screen
Then user should see the <Sensors> status as <Sensor State> on the "SENSOR STATUS"
Examples:
| Sensors       | Sensor State |Mode|
| Door Sensor   | Closed       |Away|
| Window Sensor | Closed       |Night|
| Motion Sensor | Active       |Away|
| OSMV Sensor   | Active       |Night|
| ISMV Sensor   | Active       |Away|

 
@CoverTamperSensorstatus @P2 @UIAutomated
Scenario Outline: As a user I want to get offline sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
When user <Sensor> access sensor "tampered"
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "Front Door Cover Tampered" description
When user navigates to "SENSOR STATUS" screen from the "SECURITY SOLUTION CARD" screen
Then user should be displayed with the "Cover Tampered" description
When user selects tampered <Sensor> from "Sensors Status" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
#Then user <Sensor> access sensor "Tamper CLEARED"
#When user taps on "Clear Tamper"
Then user navigates to "Security Solution card" screen from the "Sensor Status" screen
And user "opens" activity log
Then verify the following activity log:
       |Elements                 |
       |Living Room tamper cleared at Home Mode| 
And user "closes" activity log
Examples:
|Mode|Sensor| 
|Home|Door Sensor|			
#|Home|Window Sensor|		
#|Home|Motion Sensor|
#|Away|ISMV Sensor|
#|Away|OSMV Sensor|
#|Home|Door Sensor|			
#|Home|Window Sensor|		
#|Home|Motion Sensor|
#|Night|ISMV Sensor|
#|Night|OSMV Sensor|
#|Home|Door Sensor|			
#|Home|Window Sensor|		
#|Home|Motion Sensor|
#|Home|ISMV Sensor|
#|Home|OSMV Sensor|
#|Off |Door Sensor|			
#|Off |Window Sensor|		
#|Off |Motion Sensor|
#|Off |ISMV Sensor|
#|Off |OSMV Sensor|


@OfflineSensorstatus @P2      @UIAutomated
Scenario Outline: As a user i want to get offline sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
#And user creates "Offline" at the <Sensor> 
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "Solution Card Offline status" description
When user navigates to "SENSOR STATUS" screen from the "SECURITY SOLUTION CARD" screen
Then user should be displayed with the "Offline status" description
When user navigates to  "Security Solution card" screen from the "Sensors Status" screen
And user "opens" activity log
Then verify the following activity log:
       |Elements                 |
       |Living Room Offline at Home Mode| 
And user "closes" activity log
Examples:
|Mode|Sensor| 
|Away|Door Sensor|			
|Away|Window Sensor|		
|Away|Motion Sensor|
|Away|ISMV Sensor|
|Away|OSMV Sensor|
|Night|Door Sensor|			
|Night|Window Sensor|		
|Night|Motion Sensor|
|Night|ISMV Sensor|
|Night|OSMV Sensor|
|Home|Door Sensor|			
|Home|Window Sensor|		
|Home|Motion Sensor|
|Home|ISMV Sensor|
|Home|OSMV Sensor|
|Off |Door Sensor|			
|Off |Window Sensor|		
|Off |Motion Sensor|
|Off |ISMV Sensor|
|Off |OSMV Sensor|

 
@OpenSensorstatusHome @P2  @UIAutomated
Scenario Outline: As a user I want to get open sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user is set to "Sensor Alert Enabled" mode through CHIL
When user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
And user minimizes the app
When user <Sensor> access sensor "opened"
Then user selects the "Door Opened" push notification
And user should be displayed with the "Front Door Open" description  
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the "door" status as "open" on the "Sensor Status"
When user navigates to "Security Solution Card" screen from the "Sensor Status" screen
And user "opens" activity log
Then user receives a "ActivityOpen" activity log
And user "closes" activity log
Examples:
|Mode|Sensor|ActivityOpen|
|Home|Door Sensor| Front Door opened at Home mode|
|Home|Window Sensor| Window opened at Home mode|

@OpenSensorstatusOff @P2  @UIAutomated
Scenario Outline: As a user i want to get open sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
When user is set to "Sensor Alert Enabled" mode through CHIL
And user minimizes the app
When user <Sensor> access sensor "opened"
Then user selects the "Door Opened" push notification
Then user should be displayed with the "Front Door Open" description 
When user "door" access sensor "closed"
Then user should be displayed with the "SensorsNoIssue" description
And user "opens" activity log
Then user receives a "ActivityOpen" activity log
Then user receives a "RestoreActivity" activity log
And user "closes" activity log
Examples:
|Mode|Sensor| ActivityOpen|RestoreActivity|
|Home|Door Sensor| Front Door opened at Home mode|Front Door opened at HOME MODE|	
|Home|Door Sensor| Front Door closed at Home mode|Front Door closed at HOME MODE|


@ContactSensorstatusPriority @P2    @UIAutomated
Scenario Outline: As a user I want to shown with sensors status based on priority when sensor with multiple status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
And user creates "Low battery" at the <Sensor>
And user creates "Cover tamper" at the <Sensor> 
And user creates "Offline" at the <Sensor>
When user is set to "Sensor Alert Enabled" mode through CHIL 
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description
When user <Sensor> access sensor "opened"
When user navigates to "Sensor List" screen from "Security Solution card" screen
Then user should be displayed with the "Offline Status" description
When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user navigates to "Sensor Status" screen from the "Security Solution card" screen
Then user should be displayed with the "Cover Tampered" description
When user selects tampered <Sensor> from "Sensors List" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
#Then user should be displayed with the "SENSOR COVER TAMPER" description
#When user taps on "Clear Tamper"
Then user navigates to  "Security Solution card" screen from "Sensor List" screen
And user should be displayed with the "Low battery" description
When user navigates to "Sensor List" screen from "Security card" screen
Then user should be displayed with the "Low battery" description
When user clears "Low battery" at the <Sensor>
Then user should be displayed with the "Open status" description
When user navigates to  "Security card" screen from "Sensor List" screen 
Then user should be displayed with the "Open status" description
When user clears "Open status" at the <Sensor>
Then user should be displayed with the "SensorNoIssue" description 
And user "opens" activity log
Then user receives a "ActivityOpen" activity log
And user "closes" activity log
Examples:
|Mode|Sensor| ActivityOpen|
|Home|Door Sensor| Door opened at Home mode|	
|Home|Window Sensor| Low battery at Home mode|
|Home|Window Sensor|cover tampered at Home mode|
|Home|Window Sensor|Offline at Home mode|
|Home|Window Sensor|Offline restored at Home mode|
|Home|Window Sensor|Cover tamper at Home mode|
|Home|Window Sensor|Low battery restored at Home mode|


@MotionSensorstatusPriority @P2   @UIAutomated
Scenario Outline: As a user i want to shown with sensors status based on priority when sensor with multiple status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
And user creates "Low battery" at the <Sensor>
And user creates "Cover tamper" at the <Sensor> 
And user creates "Offline" at the <Sensor>
When user is set to "Sensor Alert Enabled" mode through CHIL
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description
When user <Sensor> access sensor "opened"
When user navigates to "Sensor List" screen from "Security Solution card" screen
Then user should be displayed with the "Offline Status" description
When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user navigates to "Sensor List" screen from "Security Solution card" screen
When user navigates to "Sensor Status" screen from the "Security Solution card" screen
Then user should be displayed with the "Cover Tampered" description
When user selects tampered <Sensor> from "Sensors List" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
#Then user should be displayed with the "SENSOR COVER TAMPER" description
#When user taps on "Clear Tamper"
Then user navigates to  "Security Solution card" screen from "Sensor List" screen
And user should be displayed with the "Low battery" description
When user navigates to "Sensor List" screen from "Security card" screen
Then user should be displayed with the "Low battery" description
When user clears "Low battery" at the <Sensor>
Then user should be displayed with the "Open status" description
When user navigates to  "Security card" screen from "Sensor List" screen 
Then user should be displayed with the "Open status" description
When user clears "Open status" at the <Sensor>
Then user should be displayed with the "SensorNoIssue" description 
And user "opens" activity log
Then user receives a "ActivityOpen" activity log
And user "closes" activity log
Examples:
|Mode|Sensor| ActivityOpen|
|Home|Motion Sensor| Door opened at Home mode|	
|Home|ISMV Sensor| Low battery at Home mode|
|Home|OSMV Sensor|cover tampered at Home mode|
|Home|Motion Sensor|Offline at Home mode|
|Home|ISMV Sensor|Offline restored at Home mode|
|Home|OSMV Sensor|Cover tamper at Home mode|
|Home|Motion Sensor|Low battery restored at Home mode|



@BasestationSensorenrollment @P3  @UIAutomating
Scenario Outline: I should be shown with update in progress when panel in sensor enrollment state
Given user panel in "Sensor enrollment state" enabled (write a code)
And user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
 Then user should be displayed with "Updating status" in "Dashboard" screen
 When user navigates to "Security card" screen from "Dashboard" screen
 Then user should be displayed with "Updating status" in "Security card" screen
 Given user panel in "Sensor enrollment state"  disabled (write a code)
 Then user should not be displayed with "Updating status" in "Dashboard" screen
 When user navigates to "Security card" screen from "Dashboard" screen
 Then user should not be displayed with "Updating status" in "Security card" screen
 Examples:
 |Mode|
 |Home|
 |Off |
 
 
@CloseandLaunchApp
Scenario: As a user I want to close and relaunch the app
Given user launches and logs in to the Lyric Application
And user force close and launch the Lyric application
