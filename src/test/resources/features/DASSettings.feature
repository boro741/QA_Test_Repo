@DASSettings 
Feature: DAS Settings 
As user I should be able to control my DAS panel settings from the app


#Requirements: Single Location Single DAS Device, No Sensors Required
@VerifyDASSettings @Automated
Scenario Outline: As a user I want to verify that all DAS Settings options are available to me 
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen
Then the following "DAS Security Settings" options should be enabled:
| Settings                   | 
| Manage Alerts              |
| Amazon Alexa               | 
| Geofencing                 |
| OK Security Voice Commands | 
| Entry/Exit Delay           | 
| About Security Modes       | 
| Key Fob                    | 
| Sensors                    |
| Z-Wave Devices 	         |
| Base Station Volume        |  
| Reset Wi-Fi                | 
| Base Station Configuration | 
Examples:
|Mode|
|Home|
#|OFF|

#Requirements: Single Location Single DAS Device, OSMV required
@VerifyDASSettingsOSMV @Automated
Scenario Outline: As a user I want to verify that all DAS Settings options are available to me when OSMV configured
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen 
Then the following "DAS Security Settings" options should be enabled:
| Settings                   | 
| Manage Alerts           |
| Amazon Alexa               | 
| Geofencing                 |
| OK Security Voice Commands | 
| Enhanced Deterrence		 |
| Outdoor motion viewers on in Home Mode           | 
| Entry/Exit Delay           |
| About Security Modes       | 
| Key Fob                    | 
| Sensors                    |
| Z-Wave Devices 	         |
| Base Station Volume        |  
| Reset Wi-Fi                | 
| Base Station Configuration | 
Examples:
|Mode|
|Home|
|OFF|

#Requirements: Single Location Single DAS Device OSMV required
@VerifyDASSettingswhenmodechange @Automated
Scenario Outline: As a user I want to verify that all DAS Settings options on away and night mode
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen 
Then the following "DAS Security Settings" options should be enabled:
| Settings                   | 
| Manage Alerts           |
| Amazon Alexa               | 
| OK Security Voice Commands |
| About Security Modes       | 
| Key Fob                    | 
| Sensors                    |
| Z-Wave Devices 	         | 
| Base Station Configuration | 
When user navigates to "Dashboard" screen from the "DAS Security Settings" screen
Then user navigates to "Security Settings" screen from the "Dashboard" screen
Then the following "DAS Security Settings" options should be disabled:
|Settings 					|
| Geofencing                 |
| Entry/Exit Delay           | 
| Base Station Volume        |  
| Reset Wi-Fi                |
Examples:
|Mode|
|Away|
|Night|

#Requirements: Single Location Single DAS Device OSMV required
@VerifyDASSettingswhenmodechangeOSMV @Automated
Scenario Outline: As a user I want to verify that all DAS Settings options on away and night mode
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen 
Then the following "DAS Security Settings" options should be enabled:
| Settings                   | 
| Manage Alerts           |
| Amazon Alexa               | 
| OK Security Voice Commands | 
| About Security Modes       | 
| Key Fob                    | 
| Sensors                    |
| Z-Wave Devices 	         | 
| Base Station Configuration | 
When user navigates to "Dashboard" screen from the "DAS Security Settings" screen
Then user navigates to "Security Settings" screen from the "Dashboard" screen 
Then the following "DAS Security Settings" options should be disabled:
|Settings 					 |
| Geofencing                 |
| Enhanced Deterrence		 |
| Outdoor motion viewers on in Home Mode           |
| Entry/Exit Delay           | 
| Base Station Volume        |  
| Reset Wi-Fi                |
Examples:
|Mode|
|Away|
|Night|


#ManageAlert 

#Requirements: DAS panel configured newly, No Sensors Required
@VerifyManageAlertsScreen @Automated
Scenario Outline: As a user I should be verify default Manage Alerts Screen
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen
When user navigates to "Manage alerts" screen from the "Security Settings" screen
Then the following "Security Manage Alerts" options should be enabled:
|Alerts|
|Security Mode change |
And user "SHOULD NOT BE DISPLAYED" with the "Doors and Windows" option
Examples:
|Mode|
|Home|
|Night|
|Away |
|OFF|


#Requirements: DAS panel configured newly, Access sensor
@VerifyManageAlertsScreenwithAccessSensor @Automated
Scenario Outline: As a user I should be verify default Manage Alerts Screen
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
When user navigates to "Security Settings" screen from the "Dashboard" screen
Then user navigates to "Manage alerts" screen from the "Security Settings" screen
Then user should be display with the following "Alerts" Enabled:
And the following "Security Manage Alerts" options should be enabled:
|Alerts|
|Security Mode change |
|Doors and Windows |
Examples:
|Mode|
|Home|
|Night|
|OFF|


#Requirements: DAS panel configured newly, No Sensors Required
@VerifyManageAlertsScreenEnableDisableSecurityModeChange @Automatable
Scenario Outline: As a user I should be verify security mode change enable and disable option in Manage alerts screen
Given user is set to <Mode> mode through CHIL
Then user launches and logs in to the Lyric application
Then user navigates to "Security settings" screen from "Dashboard"
When user navigates to "Manage alerts" screen from "Security settings" screen
Then user changes the "Security mode change" to "Off"
When user navigates to "Security Solution Card" screen from "Manage alerts" screen
Then user switches from <Mode> to <UMode>
And user should be displayed with a switching to <UMode> text
And user should be displayed with a switching timer
And timer ends on user device
And user status should be set to <UMode>
And user should be displayed with the correct time stamp
#And user not receives a <Push Notification> push notification
When user navigates to "Manage alerts" screen from "Security Solution Card" screen
Then user changes the "Security mode change" to "ON"
When user navigates to "Security Solution Card" screen from "Manage alerts" screen
Then user switches from <UMode> to <Mode>
And user should be displayed with a switching to <Mode> text
And user should be displayed with a switching timer
And timer ends on user device
And user status should be set to <UMode>
And user should be displayed with the correct time stamp
And user receives a <UPush Notification> push notification

Examples:
|Mode| UMode | Push Notification | |UPush Notification |
|Home| Away | Set to Away | Set to Home |
|Home|Night| Set to Night |Set to Home |
|Home| Off| Set to Off | Set to Home |
|OFF| Away |Set to Away |Set to Off | 
|OFF| Night |Set to Night |Set to Off | 
|OFF| Home | Set to Home |Set to Off | 
|Night|Home | Set to Home |Set to Night |
|Night|Away |Set to Away |Set to Night |
|Night| Off|Set to Off | Set to Night |
|Away|Home | Set to Home |Set to Away |
|Away|Night |Set to Night |Set to Away |
|Away| Off|Set to Off | Set to Away |

#Requirements: DAS panel configured newly, One access sensor
@VerifyManageAlertsScreenEnableDisableDoorsAndWindowsModeChange @Automatable
Scenario Outline: As a user I should be verify Doors and Windows change enable and disable option in Manage alerts screen
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
When user selects "Manage alerts" option from the "Security Settings" screen
Then user navigates to "Manage alerts" screen from "Security settings" screen
When user disables the "Doors and windows" option
Then user navigates to "Security settings" screen from " Manage Alerts" screen
Then user trigger the <Events>
Then user should not receive "Alerts" and "Push Notification"
When user selects "Manage alerts" option from the "Security Settings" screen
Then user navigates to "Manage alerts" screen from "Security settings" screen
When user Enables the "Doors and windows" option
Then user navigates to "Security settings" screen from " Manage Alerts" screen
Then user trigger the <Events>
Then user should receive "Alerts" and "Push Notification"
Examples:
|Mode| Events |
|Home| Open |
|Home|Close|
|OFF| Open |
|OFF| Close |
|Night|Open |
|Night|Close |



#Geofence

#Requirements: DAS panel configured newly, No Sensors Required
@VerifySecuritySettingsGeofenceoptionAwayNightOffline @Automatable
Scenario Outline: As a user I should be verify geofence stgatus on home or off modeScreen
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
When user selects "Geofencing" option from the "Security Settings" screen
Then user should display with "You can perform this action only in home or off mode" pop up
Examples:
|Mode|
|Away|
|Offline|
|Night|


#Requirements: DAS panel configured , No Sensors Required
@VerifySecuritySettingsGeofenceoptionHomeOff @Automatable
Scenario Outline: As a user I should be verify geofence status in home and off mode
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
When user enables "Geofencing" option from the "Security Settings" screen
Then user cross the geofence <Gmode> to <GMode update>
Then user DAS panel should update <GMode update> status
And user camera should update <Gmode update> status
And user should receive geofence "Push notifications"
When user disables the "Geofencing" option from the "Security settings" screen
Then user cross the geofence <Gmode> to <GMode update>
Then user DAS panel should not update <GMode update> status
And user camera should not update <Gmode update> status
And user should not receive geofence "Push notifications"
Examples:
|Mode| Gmode| Gmode update |
|Home| Home | Away |
|Home| Away | Home | 



#Requirements: DAS panel configured, No Sensors Required and "geofence this location" should be disabled under global drawer
@VerifySecuritySettingsGeofenceoptionHomeOffDisbaledgeofencethislocation @Automatable
Scenario Outline: As a user I should be verify default Manage Alerts Screen on home and off mode 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
When user enables "Geofencing" option from the "Security Settings" screen
Then user should receive a "Geofencing" pop up
And user "Dismiss" the "Geofencing" pop up
When user enables the "Geofencing" pop up 
And user "Accepts" the "Geofencing" pop up
Then user should navigates to "Geofence" screen under "Global drawer"
And user enabled the "Geofence this location" option
And user selects "BACK" button
Then user should navigates to "Security settings" screen
And user should display with enabled "Geofencing" option
Examples:
|Mode|
|Home|
|Off|



#Requirements: DAS panel configured with out Amazon Alexa
@VerifySetUpAmazonAlexa @NotAutomatable
Scenario Outline: As a user I should be verify Amazon Alexa setup
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user navigates to "Enable Amazon Alexa" screen from "Dashboard"
And user Navigates to "Sign in to Amazon" screen from "Enable Amazon Alexa" screen
And user enter the <amazon Email ID> and <amazon Password>
When user select the "Sign In" button 
Then user should be displayed with "Alexa Voice Service T&C" screen
When user "Allows Alexa T&C" by clicking on "Allow" button
Then user should  be displayed with the "Feature Setup Completed" screen 
When user completes "Alexa Configuration" by clicking on "Done" button
Then user should navigates to "Security settings" screen
Then user verifies that the DAS Panel "responds" to "Amazon Alexa Voice Commands"
Examples:
|Mode| amazon Email ID | amazon Password | 
|Home| mdsdas@grr.la | Honeywell@1234 | 
|OFF |mdsdas@grr.la | Honeywell@1234 | 
|Away |mdsdas@grr.la | Honeywell@1234 | 
|Night|mdsdas@grr.la | Honeywell@1234 | 


#Requirements: Single Location Single DAS Device, No Sensors Required
@AmazonAlexaLogoff   @NotAutomatable
Scenario: As a user I should be able to log off Amazon Alexa from my DAS account
Given user launches and logs in to the Lyric application
When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
And user "signs out of Amazon Alexa" by clicking on "sign out" button
Then user should receive a "Confirm Amazon Alexa Logout" pop up
When user "accepts" the "Confirm Amazon Alexa Logout" popup 
Then user verifies that the DAS Panel "does not respond" to "Amazon Alexa Voice Commands"



#Requirements: DAS panel configured with Amazon Alexa
@VerifySignoutAmazonAlexa @NotAutomatable
Scenario Outline: As a user I should be verify Amazon Alexa sign out
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user navigates to "Amazon Alexa Voice commands" screen from "Dashboard"
And user selects the "Sign Out" option
Then user should receive a "Confirm Amazon Alexa Logout" pop up
When user "accepts" the "Confirm Amazon Alexa Logout" popup 
Then user should navigates to "Security settings" screen 
Then user verifies that the DAS Panel "does not respond" to "Amazon Alexa Voice Commands"
Examples:
|Mode|
|Home|
|OFF|
|Night|	


# Need to check the HUE Amazon Alexa flow for offline 

#Requirements DAS panel configured, No Sensors Required and "geofence this location" should be disabled under global drawer and one sensor should be in <Error>
@VerifyGeofencecrossinsensorfault @NotAutomatable
Scenario Outline: As a user I should be verify geofence cross when sensor has <error>
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user enables "Geofencing" option from the "Security Settings" screen
Then user DAS panel <Sensors> has <Error>
Then user cross the geofence <Gmode> to <GMode update>
And user DAS Panel and Camera should not update with <GMode update> status

Examples:
|Mode|Sensors|Error | Gmode | Gmode update|
|Home|AccessSensor | Motion | Home | Away |
|Home|AccessSensor | Cover Tampered |Home | Away |
|Home|AccessSensor | Low |Home | Away |
|Home|AccessSensor | Offline |Home | Away |
|Home|MotionViewer | Motion |Home | Away |
|Home|MotionViewer | Cover Tampered |Home | Away |
|Home|MotionViewer | Low |Home | Away |
|Home|MotionViewer | Offline |Home | Away |
|Home|ISMV | Motion |Home | Away |
|Home|ISMV | Cover Tampered |Home | Away |
|Home|ISMV | Low |Home | Away |
|Home|ISMV | Offline |Home | Away |



#VoiceCommands


#LYDAS-6643
#Requirements: Single Location Single DAS Device, No Sensors Required
@VerifyOKSecurityVoiceCommandsHomeOFFNight   #NotAutomatable
Scenario Outline: As a user I should be able to enable disable OK Security Voice Commands in offline and away mode
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
When user navigates to "Voice Commands" screen from "Security settings" screen
Then user should display with "OK security voice commands" and with the following "Voice commands" options
| Commands          | 
| Trigger Phrase    | 
| Security Commands | 
| Camera Commands   |
Then user enables the "OK security Voice Commands" button
And user receive DAS Panel "Responds" to "OK security voice commands"
Then user disables the "OK security Voice Commands" button
And user should display with "OK security voice commands" and with the following "Voice commands" options
| Commands          | 
| Trigger Phrase    | 
| Security Commands | 
| Camera Commands   |
And user should not receive DAS Panel "Responds" to "OK security voice commands"
Examples:
|Mode|
|Home|
|OFF|
|Night|


#Requirements: Single Location Single DAS Device, No Sensors Required
@VerifyOKSecurityVoiceCommandsOfflineAway  @NotAutomatable
Scenario Outline: As a user I should be able to enable disable OK Security Voice Commands in offline and away mode 
Given user launches and logs in to the Lyric application
When user navigates to "Voice Commands" screen from the "Dashboard" screen
And user is set to <Mode> through CHIL
Then user Navigates to " Security settings" screen from " dashboard"
And user navigates to "Voice Commands" screen from "Security settings" screen
And user should display with disabled "OK Security voice commands" and with the following "Voice commands" options
| Commands          | 
| Trigger Phrase    | 
| Security Commands | 
| Camera Commands   |
When user selects the "OK Security voice commands" 
Then user should display the "This Feature is not available in <Mode> mode" pop up
Examples:
|Mode|
|Offline|
|Away|


#Entry exit delay 
#LYDAS-4216,LYDAS-3376,LYDAS-3244,LYDAS-2660,LYDAS-2403,LYDAS-2380,LYDAS-2360,LYDAS-2149,LYDAS-3440
#Requirements: Single Location Single DAS Device, No Sensors Required
@DASEntryExitDelaySettingsHomeOff @Automatable
Scenario Outline: As user I want to verify if entry exit delay time displayed on settings and user can update the value in home and off mode
Given user launches and logs in to the Lyric application
And user is set to <Mode> Through CHIL 
Then user navigates to "Entry-Exit Delay" screen from the "Dashboard" screen 
And user should be displayed with <Delays> value
And user should be display with default "60" selected
When user selects <Delays> from "Entry-Exit Delay" screen
Then "Entry-Exit Delay" value should be updated to <Delays> on "Entry-Exit Delay" screen
When user navigates to "Security Settings" screen from the "Entry-Exit Delay" screen 
Then "Entry-Exit Delay" value should be updated to <Delays> on "Security Settings" screen 
Then user navigates to "Dashboard" from "Security Settings" screen
When user changes the "system modes" should be delay with <Delays>
Examples: 
|Mode| Delays|
|Home|15|
|Home|30|
|Home|45|
|Home|60|
|Off|15|
|Off|30|
|Off|45|
|Off|60|




#Requirements Single Location Single DAS Device, No Sensors Required
@DASEntryExitDelaySettingsNightAway @Automatable
Scenario Outline: As user I want to verify if entry exit delay time displayed on settings and user can update the value in home and off mode
Given user launches and logs in to the Lyric application
And user is set to <Mode> Through CHIL 
Then user navigates to "Security Settings" screen from the "Dashboard" screen
When user selects the "Entry/Exit Delay" option
Then user should be displayed with "you can perform this action only in Home or Off mode" pop up
Examples: 
|Mode| 
|Away|
|Night|
|Offline|

#Requirements Single Location Single DAS Device, No Sensors Required
@DASAboutSecurityModesVerification  @Automatable
Scenario Outline: As user I want to verify About Security modes screen
Given user launches and logs in to the Lyric application
And user is set to <Mode> Through CHIL 
Then user navigates to "Security Settings" screen from the "Dashboard" screen
When user selects the "About SecurityModes" option
Then user should navigates "Security modes" screen with system mode explications
Examples:
|Mode|
|Home|
|Away|
|Night|
|Off|
|Offline|


#Requirements Single Location Single DAS Device, No Sensors Required
@ChangeBaseStationVolumeHomeOff @Automatable
Scenario Outline: As a user I should be able to change the base station volume in Home and off mode
Given user launches and logs in to the Lyric application
And User is set to <Mode> through CHIL
When user navigates to "Security Settings" screen from the "Dashboard" screen
And user changes the "Base Station Volume" to "~0%"
Then "Base Station Volume" value should be updated to "~0%" on "Security Settings" screen
When user changes the "Base Station Volume" to "~50%"
Then "Base Station Volume" value should be updated to "~50%" on "Security Settings" screen
When user changes the "Base Station Volume" to "~99%"
Then "Base Station Volume" value should be updated to "~99%" on "Security Settings" screen
Examples:
|Mode|
|Home|
|Off|

#Requirements Single Location Single DAS Device, No Sensors Required
@ChangeBaseStationVolumeNightAwayOffline @Automatable
Scenario Outline: As a user I should be able to change the base station volume in away, night, offline mode
Given user launches and logs in to the Lyric application
And User is set to <Mode> through CHIL
When user navigates to "Security Settings" screen from the "Dashboard" screen
Then user selects the "Base station volume" option
And user displayed with "You can perform this action only in Home and Off mode" pop up
Examples:
|Mode|
|Home|
|Off|



#LYDAS-6941
#Requirements: Single Location Single DAS Device, No Sensors Required
@ResetWiFi   @NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Security Settings" screen from the "Dashboard" screen	
And user should display with configured "Wifi network" name
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the context button for 5 seconds on the DAS Panel
Then the DAS panel should start flashing blue
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user adds a <Network Type> WiFi network with WiFi SSID as <WiFi SSID> and WiFi Password as <WiFi Password>
Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
And user should be display base station and camera "online"
Examples: 
| Network Type        | WiFi SSID | WiFi Password | 
| open                | abcd      | none          | 
| WEP_SHARED          | abcd      | abcd          | 
| WPA_PERSONAL_TKIP   | abcd      | abcd          | 
| WPA_PERSONAL_AES    | abcd      | abcd          | 
| WPA2_PERSONAL_AES   | abcd      | abcd          | 
| WPA2_PERSONAL_TKIP  | abcd      | abcd          | 
| WPA2_PERSONAL_MIXED | abcd      | abcd          | 
| WPA2                | abcd      | abcd          | 

#Requirements: Single Location Single DAS Device, No Sensors Required	  
@ResetWiFiByAddingNetwork  @NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the context button for 5 seconds on the DAS Panel
Then the DAS panel should start flashing blue
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user selects <WiFi SSID> from the available WiFi list
And user inputs <WiFi Password> as the WiFi Password
Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
Examples: 
| WiFi SSID | WiFi Password | 
| abcd      | abcd          | 

#Only for android OS above 6
#Requirements: Single Location Single DAS Device, No Sensors Required, and location service should OFF on the Phone device
@VerifyLocationservicePopupWhenLocationServiceOFFonPhone @NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
And user should be displayed with "Turn on Location service" pop up 
When user "Dismiss" the "Turn on Location Service" pop up
Then user should be displayed with the "Reset WiFi Instructions" screen
When user selects the "NEXT" button 
Then user should be displayed with "Turn on Location service" pop up 
When user selects the "Settings" option on the pop up 
Then user should navigates to "Location Service" screen on the phone
And user enables the "location service" option  on "Location service" screen
And user navigates to "Reset WiFi Instructions" screen
When user selects the "NEXT" button
Then user navigates to "Select wifi" screen from the "Reset WiFi Instruction" screen
And user selects <WiFi SSID> from the available WiFi list
And user inputs <WiFi Password> as the WiFi Password
Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
Examples: 
| WiFi SSID | WiFi Password | 
| abcd      | abcd          | 


#Requirements Single Location Single DAS Device, No Sensors Required
@ResetWifiFailedPopupverification @NotAutomatable
Scenario: As a user I want to verify Reset wifi failed pop up 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user selects the "NEXT" button on the "Reset Wifi Instructions" screen
#none of the panel should not be in broadcast mode
Then user received "Reset WiiFi Failed" pop up 
And user selects the "OK" option
When user selects the "NEXT" button on the "Reset Wifi Instructions" screen
#none of the panel should not be in broadcast mode
Then user received "Reset WiiFi Failed" pop up 
And user selects the "Cancel" option
Then user Should navigates to "Base Station WiFi" screen


@ResetWiFiByAddingNetworkOpenNetwork  @NotAutomatable
#Requirements: Single Location Single DAS Device, No Sensors Required
Scenario Outline: As a user I want to reset my DAS Panel WiFi with open network
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the context button for 5 seconds on the DAS Panel
Then the DAS panel should start flashing blue
And user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
When user selects "ADD Network" from "Select wifi" screen
Then user selects "None" option from "wifi connect" screen
And user selects <None Open network> from the available WiFi list
Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
Examples: 
| None Open network|
|Abcd |	  

@ResetWiFiIncorrectPassword  @NotAutomatable
#Requirements: Single Location Single DAS Device, No Sensors Required
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the context button for 5 seconds on the DAS Panel
Then the DAS panel should start flashing blue
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user selects <WiFi SSID> from the available WiFi list
And user inputs <Incorrect WiFi Password> as the WiFi Password
Then user should receive a "Incorrect WiFi Password" pop up
Examples: 
| WiFi SSID | Incorrect WiFi Password | 
| abcd      | abcd                    | 

#LYDAS-7040,LYDAS-2508,LYDAS-2337
#Requirements: Single Location Single DAS Device, No Sensors Required
@RenameDASBaseStationHomeOFF @Automatable
Scenario Outline: As a user I want to rename my Base station through the application 
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
And user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
When user edits the "DAS Panel" name to "Test Panel Name" 
And user navigates to "Dashboard" screen from the "Base Station Configuration" screen 
Then user should be displayed with "Test Panel Name" device on the "dashboard" screen 
And user reverts back the "DAS device name" through CHIL
Examples:
|Mode|
|Home|
|OFF|


@RenameDASBaseStationAwayNightOffline @Automatable
Scenario Outline: As a user I want to rename my Base station through the application 
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
And user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
When user selects the "base Station" name
Then user should display the "you can perform this action only in Home or Off mode' pop up
Examples:
|Mode|
|Away|
|Night|
|Offline|


#LYDAS-4099,LYDAS-3633,LYDAS-3469,LYDAS-3419,LYDAS-2411
#Requirements: Single Location Single DAS Device, No Sensors Required
@VerifyDASPanelModelAndFirmwareDetails @Automatable
Scenario Outline: As a user I want to view that all model, firmware and panel details 
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
Then user should be displayed with the following "Base Station Configuration" options: 
| Settings           |
| Name               |  
| Battery            | 
| Model and Firmware Details |
When user navigates to "Model and Firmware Details" screen from the "Base Station Configuration" screen
Then user should be displayed with the following "Panel Model and Firmware Details" options: 
| Settings         | 
| Model Details    | 
| Firmware Details | 
Examples:
|Mode|
|Home|
|Away|
|Night|
|Off|
|Offline|


# Battery status 

#Requirements: Single Location Single DAS Device, No Sensors Required
@DASSettingsBasestationBatterystatus @Automatable
Scenario Outline: As a user I should verify the base station battery status 
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
Then user should be display with the "battery status"
Examples:
|Mode|
|Home|
|Away|
|Night|
|OFF|
|Offline|


#LYDAS-7075,LYDAS-4088,LYDAS-4058,LYDAS-4011,LYDAS-3294,LYDAS-3271,LYDAS-3116,LYDAS-2982,LYDAS-2808,LYDAS-2610,LYDAS-2563,LYDAS-2497,LYDAS-2408,LYDAS-2404,LYDAS-2231,LYDAS-2167
#Requirements: Single Location Single DAS Device, No Sensors Required
@DeleteBaseStation @NotAutomatable
Scenario Outline: As a user I should be able to delete my DAS panel from my account through the Lyric application 
Given user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete base station" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
When user selects the "delete base station" button on "Base station configuration" screen
Then user should receive a "Delete DAS Confirmation" popup
When user accepts the "Delete DAS confirmation" pop up
Then user should be navigates to "Dashboard" screen
And user should receive "Push Notification"
Examples:
|Mode|
|Home|
|Away|
|OFF|
|Night|
|Offline|