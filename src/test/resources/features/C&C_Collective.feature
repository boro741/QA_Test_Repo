@DASCommandAndControl 
Feature: DAS Command And Control 
As a user I want to change the status of my DAS device

Background:
Given reset relay as precondition

@SwitchingSystemModeOption	@UIAutomated
Scenario: As a user I want to see all modes in my security solution card 
Given user launches and logs in to the Lyric application
When user is set to "Home" mode through CHIL
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

@CommandControlDashbaordModeToHomeChangeActivitlyLogAndPushNotificaiton  @UIAutomated
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
Scenario Outline: As a user i want to have expected sensors status when my security panel is in Away mode and Night mode
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
When user navigates to "Security Solution card" screen from the "Dashboard" screen
And timer ends on user device
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
And user is set to <Mode> mode through CHIL
When user <Sensor> access sensor "tampered"
Given user launches and logs in to the Lyric Application
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "Front Door Cover Tampered" description
When user navigates to "SENSOR STATUS" screen from the "SECURITY SOLUTION CARD" screen
Then user should be displayed with the "Cover Tampered" description
When user select <Sensor> that is "Tampered" from "Sensor Status" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
When user <Sensor> access sensor "Tamper Restored"
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
Then user navigates to "Security Solution card" screen from the "Sensor Status" screen
And user "opens" activity log
Then verify the following activity log:
      |Elements|
      |DOOR SENSOR TAMPERED AT HOME MODE| 
      |DOOR SENSOR TAMPER CLEARED AT HOME MODE|
      |WINDOW SENSOR TAMPERED AT HOME MODE|
      |WINDOW SENSOR TAMPER CLEARED AT HOME MODE|
And user "closes" activity log
Examples:
|Mode|Sensor|
|Home|Door Sensor|
|Home|Window Sensor|		
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
When user navigates to  "Security Solution card" screen from the "Sensor Status" screen
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
When user navigates to "Sensor Status" screen from the "Dashboard" screen
Then user should see the "door" status as "open" on the "Sensor Status"
When user navigates to "Security Solution Card" screen from the "Sensor Status" screen
When user <Sensor> access sensor "closed"
And user "opens" activity log
Then verify the following activity log:
      |Elements|
      |DOOR OPENED AT HOME MODE|
      |DOOR CLOSED AT HOME MODE|
And user "closes" activity log
Examples:
|Mode|Sensor|
|Home|Door Sensor|
|Home|Window Sensor|

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
Then verify the following activity log:
      |Elements|
      |DOOR OPENED AT HOME MODE|
      |DOOR CLOSED AT HOME MODE|
And user "closes" activity log
Examples:
|Mode|Sensor|
|Off|Door Sensor|
|Off|Window Sensor| 


@ContactSensorstatusPriority @P2    @UIAutomated
Scenario Outline: As a user I want to shown with sensors status based on priority when sensor with multiple status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
#And user creates "Low battery" at the <Sensor>
When user <Sensor> access sensor "tampered"
#And user creates "Offline" at the <Sensor>
When user is set to "Sensor Alert Enabled" mode through CHIL 
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description
When user <Sensor> access sensor "opened"
When user navigates to "Sensor Status" screen from the "Security Solution card" screen
#Then user should be displayed with the "Offline Status" description
#When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user select <Sensor> that is "Tampered" from "Sensor Status" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
When user <Sensor> access sensor "Tamper Restored"
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
Then user navigates to "Security Solution card" screen from the "Sensor Status" screen
When user <Sensor> access sensor "opened"
And user should be displayed with the "FRONT DOOR OPEN" description
When user <Sensor> access sensor "closed"
Then user should be displayed with the "SensorNoIssue" description 
#And user should be displayed with the "Low battery" description
#When user navigates to "Sensor List" screen from "Security card" screen
#Then user should be displayed with the "Low battery" description
#When user clears "Low battery" at the <Sensor>
And user "opens" activity log
Then verify the following activity log:
      |Elements|
      |DOOR SENSOR TAMPERED AT HOME MODE| 
      |DOOR SENSOR TAMPER CLEARED AT HOME MODE|
      |WINDOW SENSOR TAMPERED AT HOME MODE|
      |WINDOW SENSOR TAMPER CLEARED AT HOME MODE|
      |DOOR OPENED AT HOME MODE|
      |DOOR CLOSED AT HOME MODE|
      |WINDOW OPENED AT HOME MODE|
      |WINDOW CLOSED AT HOME MODE
And user "closes" activity log
Examples:
|Mode|Sensor| 
|Home|Door Sensor| 
|Home|Window Sensor| 

@MotionSensorstatusMotionSensorPriority @P2   @UIAutomated
Scenario Outline: As a user i want to shown with sensors status based on priority when sensor with multiple status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
#And user creates "Low battery" at the <Sensor>
When user <Sensor> access sensor "tampered"
#And user creates "Offline" at the <Sensor>
When user is set to "Sensor Alert Enabled" mode through CHIL
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsIssue" description
When user <Sensor> access sensor "opened"
When user navigates to "Sensor Status" screen from the "Security Solution card" screen
#Then user should be displayed with the "Offline Status" description
#When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user select <Sensor> that is "Tampered" from "Sensor Status" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
When user <Sensor> access sensor "Tamper Restored"
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
Then user navigates to "Security Solution card" screen from the "Sensor Status" screen
#And user should be displayed with the "Low battery" description
#When user navigates to "Sensor List" screen from "Security card" screen
#Then user should be displayed with the "Low battery" description
#When user clears "Low battery" at the <Sensor>
Then user should be displayed with the "SensorNoIssue" description 
And user "opens" activity log
Then verify the following activity log:
      |Elements|
      |DOOR SENSOR TAMPERED AT HOME MODE| 
      |DOOR SENSOR TAMPER CLEARED AT HOME MODE|
And user "closes" activity log
Examples:
|Mode|Sensor|
|Home|Motion Sensor|


@MotionSensorstatusISMVandOSMVSensorPriority   @P2 @UIAutomated
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
When user navigates to "Sensor Status" screen from "Security Solution card" screen
Then user should be displayed with the "Offline Status" description
When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user navigates to "Sensor List" screen from "Security Solution card" screen
When user navigates to "Sensor Status" screen from the "Security Solution card" screen
Then user should be displayed with the "Cover Tampered" description
When user selects tampered <Sensor> "Sensors Status" screen
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
When user navigates to  "Security Solution card" screen from the "Sensor List" screen 
Then user should be displayed with the "Open status" description
When user clears "Open status" at the <Sensor>
Then user should be displayed with the "SensorNoIssue" description 
And user "opens" activity log
Then user receives a "ActivityOpen" activity log
And user "closes" activity log
Examples:
|Mode|Sensor| ActivityOpen|
|Home|ISMV Sensor|ISMV Sensor Low battery at Home mode|
|Home|OSMV Sensor|OSMV Sensor cover tampered at Home mode|



@BasestationSensorenrollment @P3  @NotAutomatable
Scenario Outline: I should be shown with update in progress when panel in sensor enrollment state
Given user panel in "Sensor enrollment state" enabled (write a code)
And user launches and logs in to the Lyric Application
And user is set to <Mode> mode through CHIL
 Then user should be displayed with "Updating status" in "Dashboard" screen
 When user navigates to "Security Solution card" screen from the "Dashboard" screen
 Then user should be displayed with "Updating status" in "Security card" screen
 Given user panel in "Sensor enrollment state"  disabled (write a code)
 Then user should not be displayed with "Updating status" in "Dashboard" screen
 When user navigates to "Security card" screen from "Dashboard" screen
 Then user should not be displayed with "Updating status" in "Security card" screen
 Examples:
 |Mode|
 |Home|
 |Off |
 
 
@CloseandLaunchApp    @UIAutomated
Scenario: As a user I want to close and relaunch the app
Given user launches and logs in to the Lyric Application
And user force close and launch the Lyric application


@CommandControlfromkeyfobwhensensorfault   @UIAutomated
Scenario Outline: As a user I should not be allowed to switch to Away or Night mode through keyfob when sensor is fault
Given user sets the entry/exit timer to "60" seconds
And user is set to <Mode> mode through CHIL
When user <Sensor> access sensor "opened"
When user launches and logs in to the Lyric application
And user press "away" key from keyfob
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the <Mode> screen
When user is set to <Mode> mode through CHIL
When user <Sensor> access sensor "opened"
When user launches and logs in to the Lyric application
And user press "night" key from keyfob
Then user should be displayed with the <Mode> screen
Examples:
|Mode|Sensor|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|
 
@CommandControlfromkeyfobwhenContactsensortrouble    @UIAutomated
Scenario Outline: As a user I should not be allowed to switch to Away or Night mode through keyfob when sensor is trouble
Given user sets the entry/exit timer to "60" seconds
And user is set to <Mode> mode through CHIL
When user <Sensor> access sensor "tampered"
When user launches and logs in to the Lyric application
And user press "away" key from keyfob
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the <Mode> screen
When user is set to <Mode> through CHIL
When user <Sensor> access sensor "tampered"
When user launches and logs in to the Lyric application
And user press "night" key from keyfob
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user should be displayed with the <Mode> screen
Examples:
|Status|Sensor|
|Home|Door Sensor|
|OFF |Window Sensor|
|Home|Motion Sensor|
|Home|ISMV Sensor|
|OFF |OSMV Sensor|
 
 
@CommandControlfromkeyfobwhenMotionsensortrouble   @UIAutomated
Scenario Outline: As a user I should not be allowed to switch to Away/Night mode through keyfob when sensor is trouble
Given user sets the entry/exit timer to "60" seconds
And user is set to <Mode> through CHIL
And user "Cover Tamper" the <Sensor type>
When user launches and logs in to the Lyric application
And user "Activates Away mode" from keyfob
Then user should not be displayed with the "Away Status"
When user is set to <Mode> through CHIL
And user "Cover Tamper" the <Sensor type>
When user launches and logs in to the Lyric application
And user "Activates Night mode" from keyfob
Then user should be displayed with the "Night Status"
Examples:
|Status|Sensor type|
|Home|Motion Sensor|
|Home|ISMV Sensor|
|Home|OSMV Sensor|
|Off |Motion Sensor|
|Off |ISMV Sensor|
|Off |OSMV Sensor|


@CommandControlfromvoicecommandsAwaymode  @NotAutomatable
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
 
@CommandControlfromvoicecommandsNightmode   @NotAutomatable
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
 
@CommandControlfromvoicecommandswhensensorfault   @NotAutomatable
Scenario Outline: As a user I shouldnot be allowed to switch to Away/Night mode through voice commands when contact sensor is fault
Given user sets the entry/exit timer to "60" seconds
And user is set to <status> mode through CHIL
And user "opens" the <Sensor type>
When user launches and logs in to the Lyric application
And user "Activates Away mode" from Voice commands
Then user should not be displayed with the "Away Status"
When user is set to <status> mode through CHIL
And user "opens" the <Sensor type>
When user launches and logs in to the Lyric application
And user "Activates Away mode" from Voice commands
Then user should not be displayed with the "Away Status"
Examples:
|Status|Sensor type|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|
 
@CommandControlfromvoicecommandswhensensortrouble  @NotAutomatable
Scenario Outline: As a user I shouldnot be allowed to switch to Away/Night mode through voice commands when sensor is trouble
And user is set to <status> mode through CHIL
And user "Cover Tamper" the <Sensor type>
When user launches and logs in to the Lyric application
And user "Activates Away mode" from Voice commands
Then user should be not displayed with the "Away Status"
When user launches and logs in to the Lyric application
And user "Activates Night mode" from Voice commands
Then user should be not displayed with the "Night Status"
Examples:
|Status|Sensor type|
|Home|Door Sensor|
|Home|Window Sensor|
|Home|ISMV Sensor|
 
 
@commandandcontrolmotionsensorfault    @UIAutomated
Scenario Outline: As a user i should not get alarm from motionsensor detection other than away mode
Given user sets the entry/exit timer to "60" seconds
And user is set to "Home" mode through CHIL
When user <Sensor> access sensor "tampered"
When user launches and logs in to the Lyric application
And user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away"
Then user "Accepts" the "Switch To Away" popup
Then user should be displayed with a "Switching to Away" text
And user <Sensor> detects "Motion"
Then user should not be displayed with Alarm screen
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night"
Then user should be displayed with a "Switching to Night" text
And user <Sensor> detects "Motion"
Then user should not be displayed with Alarm screen
When user is set to "Home" mode through CHIL
And user switches from "Home" to "Night"
Then user should be displayed with a "Switching to Night" text
When "Switching timer" ends on user device
Then user should be displayed with the "Night" screen
And user <Sensor> detects "Motion"
Then user should not be displayed with Alarm screen
Examples:
|Sensor       |
|Motion Sensor|
|ISMV Sensor  |
 
@commandandcontrolExitdelayFault   @UIAutomated
Scenario Outline: As a user should not be get alarm during exit delay and my contact sensor is faulted
Given user sets the entry/exit timer to "60" seconds
And user is set to <Status> mode through CHIL
When user <Sensor> access sensor "tampered"
When user launches and logs in to the Lyric application
And user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Status> to "Away"
Then user "Accepts" the "Switch To Away" popup
Then user should be displayed with a "Switching to Away" text
When user <Sensor> access sensor "opened"
Then user should not be displayed with Alarm screen
When user is set to <Status> mode through CHIL
And user switches from <Status> to "Night"
Then user should be displayed with a "Switching to Night" text
When user <Sensor> access sensor "opened"
Then user should not be displayed with Alarm screen
Examples:
|Status|Sensor|
|Home|Door|
|Home|Window|
|Off|Door|
|Off|Window|
 
@commandandcontrolmultiplesensorwithfault   @UIAutomated
Scenario: As a user I want to switch to different states in my DAS device when multiple sensors are in fault condition
Given user sets the entry/exit timer to "45" seconds
And user is set to <Mode> mode through CHIL
When user launches and logs in to the Lyric application
And user navigates to "security solution cards" screen from "Dashboard" screen
When user <Sensor> access sensor "opened"
And user "Sensor" detects "Motion"
And user switches from <Mode> to "Away"
Then user "Accepts" the "Switch To Away" popup
Then user should be displayed with a "Switching to Away" popup
When user "Accepts" the "Switch to Away with the sensor fault" popup
When "Switching timer" ends on user device
Then user should be displayed with the "Away" screen
When user navigates to "Sensor List" from the "security solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status |Mode|
| Door Sensor   | OFF       |Home|
| Window Sensor | OFF       |Home|
| Motion Sensor | OFF       |Home|
| OSMV Sensor   | Active    |Home|
| ISMV Sensor   | Active    |Home|
When user is set to <Mode> mode through CHIL
And user switches from <Mode> to "Night"
Then user should be displayed with a "Switch to Night" popup
When user user "Accepts" the "SWITCH TO NIGHT WITH MULTILPLE SENSOR FAULT" popup
And "Switching timer" ends on user device
Then user should be displayed with the "Night" screen
When user navigates to "Sensors List"  from "security solution cards" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status|Mode|
| Door Sensor   | OFF       |Home|
| Window Sensor | OFF       |Home|
| Motion Sensor | OFF       |Home|
| OSMV Sensor   | Active    |Home|
| ISMV Sensor   | Active    |Home|

 
 
#Backoptionwhileswitchingmodes
@UserpressesbackbuttonWhileSwitchingModes             @UIAutomated
Scenario: As a user I want to go back to dashboard when user press back button while switching modes
Given user sets the entry/exit timer to "60" seconds
When user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from "Home" to "Away"
Then user should be displayed with a "Switching to Away" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Away" text
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


@CancelSwitchingModeAway    @UIAutomated
Scenario Outline: As a user I should be able to cancel the switching away request
Given user sets the entry/exit timer to "60" seconds 
When user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Mode> to "Away"
Then user should be displayed with a "Switching to Away" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Away" text
And user should be displayed with a switching timer
And user should be displayed with cancel icon
When user taps on "Cancel"
Then user should be displayed with the <Mode> screen
When user switches from <Mode> to "Away"
Then user should be displayed with a "Switching to Away" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Away" text in the Dashboard screen
And user should be displayed with a switching timer
And user should be displayed with cancel icon
When user taps on "Cancel"
Then user should be displayed with the <Mode> screen
Examples:
|Mode|
|Home|
|Off |


@CancelSwitchingModeNight   @UIAutomated
Scenario Outline: As a user I should be able to cancel the switching away request
Given user sets the entry/exit timer to "60" seconds 
When user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user switches from <Mode> to "Night"
Then user should be displayed with a "Switching to Night" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Night" text in the Dashboard screen
When user navigates to "Security Solution Card" screen from the "Dashboard" screen
Then user should be displayed with a "Switching to Night" text
And user should be displayed with a switching timer
And user should be displayed with cancel icon
When user taps on "Cancel"
Then user should be displayed with the <Mode> screen
When user switches from <Mode> to "Night"
Then user should be displayed with a "Switching to Night" text
And user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user should be displayed with a "Switching to Night" text in the Dashboard screen
And user should be displayed with a switching timer
And user should be displayed with cancel icon
When user taps on "Cancel"
Then user should be displayed with the <Mode> screen
Examples:
|Mode|
|Home|
|Off |


@OutdoorMotionViewer_VideoClip   @NotAutomatable
Scenario Outline: As a user when motion detected after exit delay irrespective of exit timer, I should have clips generated for 30sec (landscape, download)
Given user sets the entry/exit timer to <timerValue> seconds
And user is set to "Outdoor Motion Viewers On in Home Mode" through CHIL
And user is set to <Mode> through CHIL
And user launches and logs in to the Lyric application
When user outdoor motion viewer detected
Then user should be recieved with Motion event:
|Event Name | Sensor Name |Device Icon |Thumbnail|
|Motion Detected|Sensor Name|OSMV Icon |Clip|
And user should be displayed with intermediate screen on loading clip
And user should be displayed with thumbnail with play icon
When user plays clip
Then user should be played with video for 30 seconds
And user should be played with video of 5 seconds prealert
 

@LowBatterySensorstatus @P2    @UIAutomated
Scenario Outline: As a user i should be displayed with low battery sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Security card" screen from the "Dashboard" screen
Then user should be displayed with the "SENSORSNOISSUE" description
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
#And user creates "Low battery" at the <Sensor> 
Then user should be displayed with the "Low battery" description
When user selects <Sensor> that is "Low Battery" from "Sensor Status" screen
When user navigates to  "Security Solution Card" screen from "Sensor Status" screen
Then user should be displayed with the "Low battery" description
When user clears "Low battery" at the <Sensor>
Then user should be displayed with the "SensorsNoIssue" description
And user "opens" activity log
Then user receives a <ActivityLowBattery> activity log
Then user receives a <ActivityLowBatteryRestore> activity log
And user "closes" activity log
Examples:
|Mode|Sensor|ActivityLowBattery|ActivityLowBatteryRestore|
|Away|Door Sensor|Low battery at Away mode|Low battery restored at Away Mode|
|Away|Window Sensor|Low battery at Away mode|Low battery restored at Away Mode|	
|Away|Motion Sensor|Low battery at Away mode|Low battery restored at Away Mode|
|Away|ISMV Sensor|Low battery at Away mode|Low battery restored at Away Mode|
|Away|OSMV Sensor|Low battery at Away mode|Low battery restored at Away Mode|
|Night|Door Sensor|Low battery at Night mode|Low battery restored at Night Mode|		
|Night|Window Sensor|Low battery at Night mode|Low battery restored at Night Mode|		
|Night|Motion Sensor|Low battery at Night mode|Low battery restored at Night Mode|
|Night|ISMV Sensor|Low battery at Night mode|Low battery restored at Night Mode|
|Night|OSMV Sensor|Low battery at Night mode|Low battery restored at Night Mode|
|Home|Door Sensor|Low battery at Home mode|Low battery restored at Home Mode|		
|Home|Window Sensor|Low battery at Home mode|Low battery restored at Home Mode|		
|Home|Motion Sensor|Low battery at Home mode|Low battery restored at Home Mode|	
|Home|ISMV Sensor|Low battery at Home mode|Low battery restored at Home Mode|	
|Home|OSMV Sensor|Low battery at Home mode|Low battery restored at Home Mode|	
|Off |Door Sensor|Low battery at Off mode|Low battery restored at Off Mode|			
|Off |Window Sensor|	Low battery at Off mode|Low battery restored at Off Mode|
|Off |Motion Sensor|Low battery at Off mode|Low battery restored at Off Mode|
|Off |ISMV Sensor|Low battery at Off mode|Low battery restored at Off Mode|
|Off |OSMV Sensor|Low battery at Off mode|Low battery restored at Off Mode|


@MultipleSensorwithstatusscrolling @P2     @UIAutomated
Scenario: As a user i should get all the sensors status in security solution card with all status scrolling 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
 And user is set to "Home" mode through CHIL
 And user is "Enabled" Doors and Windows alert through CHIL 
When user navigates to "Security card" screen from the "Dashboard" screen
Then user should be displayed with the "SensorsNoIssue" description
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
And user "opens" activity log
Then user receives a "Door Sensor Opened at Home Mode" activity log
Then user receives a "Window Sensor Low Battery at Home Mode" activity log
Then user receives a "Motion Sensor Cover Tamper at Home Mode" activity log
Then user receives a "ISMV Sensor Opened at Home Mode" activity log
And user "closes" activity log


@SwitchingSystemModeOffSensorstatus   @UIAutomated
Scenario Outline: As a user i want to switch off mode with different sensor status
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
When user switches from <Mode> to "OFF"
And user "Dismiss" the "Set to Off" popup
Then user should be displayed with the <Mode> screen
When user switches from <Mode> to "OFF"
And user "Accepts" the "Set to Off" popup
Then user should be displayed with the "OFF" screen
When user should be displayed with a "Switching To Off" text
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor Status"
Examples:
|Mode| Sensor Status |Sensors |
|Home| Offline | Door sensor | 
|Home| Cover Tampered | Window sensor |
|Home| Low Battery | OSMV sensor | 
|Away| Offline | Motion sensor | 
|Away| Low Battery | ISMV sensor |
|Night| Offline |OSMV sensor |
|Night| Cover Tampered | ISMV sensor | 
|Night| Low Battery | Door sensor |


@SwitchingSystemModeHomeSensorstatus     @UIAutomated
Scenario Outline: As a user i want to switch Home mode with different sensor status
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
When user switches from <Mode> to "Home"
Then user should be displayed with the "Home" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor Status"
Examples:
|Mode| Sensor Status |Sensors |
|Off | Offline | Door sensor | 
|Off | Cover Tampered | Window sensor |
|Off | Low Battery | OSMV sensor | 
|Away| Offline | Motion sensor | 
|Away| Low Battery | ISMV sensor |
|Night| Offline |OSMV sensor |
|Night| Cover Tampered | ISMV sensor | 
|Night| Low Battery | Door sensor |


@SwitchingSystemModeNightMotionSensorstatus     @UIAutomated
Scenario Outline: As a user i want to switch Night mode with different sensor status
And user is set to <Mode> through CHIL
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
When user switches from <Mode> to "Night"
Then user should be displayed with the "Night" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor Status"
Examples:
|Mode| Sensor Status |Sensors |
|Off | Offline | Door sensor | 
|Off | Cover Tampered | Window sensor |
|Off | Low Battery | OSMV sensor | 
|Away| Offline | Motion sensor | 
|Away| Low Battery | ISMV sensor |
|Night| Offline |OSMV sensor |
|Night| Cover Tampered | ISMV sensor | 
|Night| Low Battery | Door sensor |


@SwitchingSystemModeNightContactSensorstatus   @UIAutomated
Scenario Outline: As a user i want to switch Night mode with different sensor status
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
When user switches from <Mode> to "Night"
And user "Dismiss" the "Switch to Night" popup
Then user should be displayed with the <Mode> screen
When user switches from <Mode> to "Night"
And user "Accepts" the "Switch to Night" popup
Then user should be displayed with the "Night" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor Status"
Examples:
|Mode| Sensor Status |Sensors |
|Home| Offline | Door sensor | 
|Home| Cover Tampered | Window sensor |
|Home| Low Battery | OSMV sensor | 
|Away| Offline | Motion sensor | 
|Away| Low Battery | ISMV sensor |
|Night| Offline |OSMV sensor |
|Night| Cover Tampered | ISMV sensor | 
|Night| Low Battery | Door sensor | 


@SwitchingSystemModeAwaySensorstatus   @UIAutomated
Scenario Outline: As a user i want to switch Away mode with different sensor status
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
And user "Door Sennsor" access sensor "Opened"
And user "Window Sennsor" access sensor "Low battery"
And user "Motion Sennsor" access sensor "Cover Tamper"
And user "ISMV Sennsor" access sensor "Offline"
When user switches from <Mode> to "Away"
And user "Dismiss" the "Switch to Away" popup
Then user should be displayed with the <Mode> screen
When user switches from <Mode> to "Away"
And user "Accepts" the "Switch to Away" popup
Then user should be displayed with the "Away" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor Status"
Examples:
|Mode| Sensor Status |Sensors |
|Home| Offline | Door sensor | 
|Home| Cover Tampered | Window sensor |
|Home| Low Battery | OSMV sensor | 
|Away| Offline | Motion sensor | 
|Away| Low Battery | ISMV sensor |
|Night| Offline |OSMV sensor |
|Night| Cover Tampered | ISMV sensor | 
|Night| Low Battery | Door sensor |


@CommandControlViewswitchingmodewhenDASPanelinstandbymode   @NotAutomatable
Scenario Outline:As a user I want to see the error pop up on switch the mode when base station in stand by 
Given user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user set to "Stand-by" mode Base station
Then user switch the mode to <Mode>
And user should receive "Unable to switch" error pop up
Examples:
|Mode|
|Away|
|Night|
|Off|


#Requirements : Configured one DAS panel and user has to install new application and login or after enroll the DAS panel 
@DASPanelDashboardandsecuritycardcoach-markverification   @UIAutomated
Scenario Outline: AS a user I want to verify Dashboard & Security card coach-mark
Given user set to <Mode> through CHIL
And user launches and logs in to the lyric application 
Then user verifies the "Access More Information" coach marks
And user taps on "Next" 
Then user verifies the "Quick Controls" coach marks
And user taps on "Back" 
Then user verifies the "Access More Information" coach marks
And user taps on "Next" 
Then user verifies the "Quick Controls" coach marks
And user taps on "Next" 
And user taps on "Done"
And user "Should Not Be Displayed" with the "Coach Mark" option
When user navigates to "Security Solution card" screen from the "Dashboard" screen
Then user verifies the "Home Mode" coach marks
And user taps on "Next"
Then user verifies the "Away Mode" coach marks
And user taps on "Next"
Then user verifies the "Night Mode" coach marks
And user taps on "Next"
Then user verifies the "Off Mode" coach marks
And user taps on "Back"
Then user verifies the "Night Mode" coach marks
And user taps on "Back"
Then user verifies the "Away Mode" coach marks
And user taps on "Back" 
Then user verifies the "Home Mode" coach marks
And user taps on "Back"
Then user verifies the "Home Mode" coach marks
And user taps on "Next"
Then user verifies the "Away Mode" coach marks
And user taps on "Next"
Then user verifies the "Night Mode" coach marks
And user taps on "Next"
Then user verifies the "Off Mode" coach marks
And user taps on "Next"
And user "Should Not Be Displayed" with the "Coach Mark" option
Examples:
|Mode|
|Home|
|Away|
|Night|
|Off|
