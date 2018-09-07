@DASCommandAndControl 
Feature: DAS Command And Control 
As a user I want to change the status of my DAS device

@CommandControlviewSecuritysolutioncard	
Scenario: As a user I want to see all modes in my security solution card 
Given user launches and logs in to the Lyric application
#When user is set to "Home" mode through CHIL
Then user navigates to "Security Solution Card" screen from the "Dashboard" screen
And user should be displayed with the following "SECURITY MODE" options:
| SECURITY MODE		|
| Home				|
| Away				|
| Night				|
| Off				|


@CommandControlSecuritycardModeFromHomeandOFFChangeActivitlyLogAndPushNotificaiton	@Automated
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

@CommandControlSecuritycardModetoOFFChangeActivitlyLogAndPushNotificaiton	
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


@CommandControlSecuritycardModeToHomeChangeActivitlyLogAndPushNotificaiton	
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

@CommandControlDasboardModetoHomeAwayAndNightChangePushNotificaiton
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

@CommandControlDasboardOFFModeStatusAndPushNotificaiton
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

@CommandControlDasboardModefromSolutionCardChangePushNotificaiton
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


@SensorstatusHomemode @P3  @Automated
Scenario Outline: As a user i want to have expected sensors status when my security panel is in Home mode Off mode
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Sensors" screen from "Dashboard" screen
Then user should be displayed with the "Sensors" description
When user navigates to "Sensors Status" screen from "Security card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status |
| Door Sensor   | Closed        |
| Window Sensor | Closed        |
| Motion Sensor | Standby       |
| OSMV Sensor   | Standby       |
| ISMV Sensor   | Standby       |
Examples:
|Mode|
|Home|
|OFF |
 
 
@SensorstatusArmmode @P3   @Automated
Scenario Outline: As a user i want to have expected sensors status when my security panel is in Away mode Night mode
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Sensors" screen from "Dashboard" screen
Then user should be displayed with the "Sensors" description
When user navigates to "Sensors Status" screen from "Security card" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status |
| Door Sensor   | Closed        |
| Window Sensor | Closed        |
| Motion Sensor | Active        |
| OSMV Sensor   | Active        |
| ISMV Sensor   | Active        |
Examples:
|Mode |
|Away |
|Night|
 
 
@CoverTamperSensorstatus @P2     @UIAutomating
Scenario Outline: As a user i want to get offline sensors status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Security Solution Card" screen from "Dashboard" screen
Then user should be displayed with the "Sensors" description
When user navigates to "Sensor List" screen from "Security Solution Card" screen
#And user creates "Cover Tamper" at the <Sensor>
Then user should be displayed with the "Cover Tampered" description
When user selects tampered <Sensor> from "Sensors List" screen
Then user should be displayed with the "Cover Tamper" description
When user navigates to  "Security card" screen from "Cover Tamper" screen
Then user should be displayed with the "Cover Tamper" description
When user clears "Cover Tamper" at the <Sensor>
And user selects the <Sensor>
Then user should be displayed with the "Cover Tamper description" Screen
When user selects the "Clear Tamper"
Then user navigates to  "Security card" screen from "Sensor List" screen
When user navigates to  "Security card" screen from "Cover Tamper description" screen
Then user should be displayed with the "Cover Tamper description"
When user navigates to "Activity Log" Screen from "Security crad" screen
Then user should be displayed with the "{Sensor name} Cover Tamper " <mode> status
And user should be displayed with "{Sensor name} Cover Tamper Restored " <mode> status
When user navigates to "Sensor list" screen from "Security card"
Then user should not be displayed with "Cover Tamper status"
Examples:
|Mode|Sensor|
|Away|OSMV Sensor|
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


@OfflineSensorstatus @P2    @UIAutomating
Scenario Outline: As a user i want to get offline sensors status
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Security Solution Card" screen from "Dashboard" screen
Then user should be displayed with the "Sensors" description
When user navigates to "Sensor List" screen from "Security Solution Card" screen
And user creates "Offline" at the <Sensor>
Then user should be displayed with the"Offline status"
When user selects the <Sensor>
Then user should be displayed with the "Offline description" Screen
When user navigates to  "Security card" screen from "Offline description" screen
Then user should be displayed with the "Offline description"
When user clears "Offline" at the <Sensor>
Then user should be displayed with the "Sensors description"
When user navigates to "Activity Log" Screen from "Security crad" screen
Then user should be displayed with the "{Sensor name} Offline " <mode> status
And user should be displayed with "{Sensor name} Offline Restored " <mode> status
When user navigates to "Sensor list" screen from "Security card"
Then user should not be displayed with "Offline status"
Examples:
|Status|Sensor|
|Away|Door Sensor|                                    
|Away|Window Sensor|                             
|Away|Motion Sensor|
|Away|ISMV Sensor|
|Away|OSMV Sensor
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
 

