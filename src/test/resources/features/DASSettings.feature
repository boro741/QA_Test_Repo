@DASSettings 
Feature: DAS Settings 
  As user I should be able to control my DAS panel settings from the app
  
  #LYDAS-7075,LYDAS-4088,LYDAS-4058,LYDAS-4011,LYDAS-3294,LYDAS-3271,LYDAS-3116,LYDAS-2982,LYDAS-2808,LYDAS-2610,LYDAS-2563,LYDAS-2497,LYDAS-2408,LYDAS-2404,LYDAS-2231,LYDAS-2167
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @DeleteBaseStation @UIAutomated
  Scenario: As a user I should be able to delete my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
      And user "deletes DAS device" by clicking on "delete" button
     Then user should receive a "Delete DAS Confirmation" popup
      And user "dismisses" the "Delete DAS Confirmation" popup
  
  #LYDAS-3398,LYDAS-3270,LYDAS-2770
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyDASSettings @UIAutomated
  Scenario: As a user I want to verify that all DAS Settings options are available to me 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Security Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Security Settings" options: 
      | Settings                   | 
      | Alert Settings             |
      | Amazon Alexa               | 
      | Geofencing                 |
      | Video Settings             |
      | OK Security Voice Commands | 
      | Entry/Exit Delay           | 
      | About Security Modes       | 
      | Key Fob                    | 
      | Sensors                    |
      | Volume                     |  
      | Base Station Wi-Fi         | 
      | Base Station Configuration | 
  
  #LYDAS-4216,LYDAS-3376,LYDAS-3244,LYDAS-2660,LYDAS-2403,LYDAS-2380,LYDAS-2360,LYDAS-2149,LYDAS-3440
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @DASEntryExitDelaySettings @UIAutomated 
  Scenario: As user I want to verify if entry exit delay time displayed on settings and user can update the value 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Entry-Exit Delay" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Entry-Exit Delay" options: 
      | Delays | 
      | 15     | 
      | 30     | 
      | 45     | 
      | 60     | 
     When user selects "15" from "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "15" on "Entry-Exit Delay" screen 
     When user navigates to "Security Settings" screen from the "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "15" on "Security Settings" screen 
     When user navigates to "Entry-Exit Delay" screen from the "Security Settings" screen 
     Then user should be displayed with the following "Entry-Exit Delay" options: 
      | Delays | 
      | 15     | 
      | 30     | 
      | 45     | 
      | 60     | 
     When user selects "30" from "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "30" on "Entry-Exit Delay" screen 
     When user navigates to "Security Settings" screen from the "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "30" on "Security Settings" screen 
     When user navigates to "Entry-Exit Delay" screen from the "Security Settings" screen 
     Then user should be displayed with the following "Entry-Exit Delay" options: 
      | Delays | 
      | 15     | 
      | 30     | 
      | 45     | 
      | 60     | 
     When user selects "45" from "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "45" on "Entry-Exit Delay" screen 
     When user navigates to "Security Settings" screen from the "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "45" on "Security Settings" screen 
     When user navigates to "Entry-Exit Delay" screen from the "Security Settings" screen 
     Then user should be displayed with the following "Entry-Exit Delay" options: 
      | Delays | 
      | 15     | 
      | 30     | 
      | 45     | 
      | 60     | 
     When user selects "60" from "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "60" on "Entry-Exit Delay" screen 
     When user navigates to "Security Settings" screen from the "Entry-Exit Delay" screen 
     Then "Entry-Exit Delay" value should be updated to "60" on "Security Settings" screen 
  
  #LYDAS-7040,LYDAS-2508,LYDAS-2337
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @RenameDASBaseStation @UIAutomated
  Scenario: As a user I want to rename my Base station through th application 
    Given user launches and logs in to the Lyric application 
      And user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
     When user edits the "DAS Panel" name to "Test Panel Name" 
      And user navigates to "Dashboard" screen from the "Base Station Configuration" screen 
     Then user should be displayed with "Test Panel Name" device on the "dashboard" screen 
      And user reverts back the "DAS device name" through CHIL
  
  #LYDAS-6941
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @ResetWiFi @NotAutomatable
  Scenario Outline: As a user I want to reset my DAS Panel WiFi 
    Given user launches and logs in to the Lyric application 
      And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
     When user "resets WiFi" by clicking on "Reset WiFi" button  
     Then user should be displayed with the "Reset WiFi Instructions" screen
     When user presses the context button for 5 seconds on the DAS Panel
     Then the DAS panel should start flashing blue
     When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
      And user adds a <Network Type> WiFi network with WiFi SSID as <WiFi SSID> and WiFi Password as <WiFi Password>
     Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
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
  
  @ResetWiFiByAddingNetwork @NotAutomatable
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
      And user inputs <WiFi Password> as the WiFi Password
     Then user "wifi name" should be updated to <WiFi SSID> in the "Base Station WiFi" screen.
    Examples: 
      | WiFi SSID | WiFi Password | 
      | abcd      | abcd          | 
  
  @ResetWiFiIncorrectPassword @NotAutomatable
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
  
  #LYDAS-4099,LYDAS-3633,LYDAS-3469,LYDAS-3419,LYDAS-2411
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyDASPanelModelAndFirmwareDetails @UIAutomated
  Scenario: As a user I want to view that all model, firmware and panel details 
    Given user launches and logs in to the Lyric application 
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
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @DASSettingsDisabled @UIAutomated
  Scenario: As a user I should not be allowed to change DAS settings when I am not home 
    Given user sets the entry/exit timer to "15" seconds 
      And user is set to "Away" mode through CHIL 
     When user launches and logs in to the Lyric application 
      And user navigates to "Security Settings" screen from the "Dashboard" screen
     Then the following "Security Settings" options should be disabled:
      | Options            | 
      | Geofencing         |
      | Entry/Exit Delay   | 
      | Volume             |
      | Base Station Wi-Fi | 
  
  #LYDAS-3196
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyNoKeyfobsAndSensors @UIAutomated
  Scenario: As a user I should not be displayed with Keyfobs or Sensors if I have not configured any keyfobs to my account
    Given user launches and logs in to the Lyric application
     When user navigates to "keyfob" screen from the "Dashboard" screen
     Then user should not be displayed with "keyfobs" on the "Keyfob" screen 
     When user navigates to "Sensors" screen from the "Keyfob" screen
     Then user should not be displayed with "sensors" on the "sensors" screen
  
  #Requirements: Single Location Single DAS Device, Atleast 1 Sensor & Atleast 1 Keyfob Required
  @VerifyDisplayedKeyfobsAndSensors @UIAutomated
  Scenario: As a user I should be displayed with all my sensors and keyfobs
    Given user launches and logs in to the Lyric application
     When user navigates to "Keyfob" screen from the "Dashboard" screen
     Then user should be displayed with all "keyfob" names configured to his account
     When user navigates to "Keyfob Settings" screen from the "Keyfob" screen
     Then user should be displayed with the following "Keyfob Settings" options: 
      | Settings                   | 
      | Name                       | 
      | Model and Firmware Details |  
      When user navigates to "Model and Firmware Details" screen from the "Keyfob Settings" screen
      Then user should be displayed with the following "Keyfob Model and Firmware Details" options: 
      | Settings         | 
      | Model Details    | 
      | Firmware Details |
     When user navigates to "Sensors" screen from the "Keyfob Model and Firmware Details" screen
     Then user should be displayed with all "sensor" names configured to his account
     When user navigates to "Sensor Settings" screen from the "Sensor" screen
     Then user should be displayed with the following "Sensor Settings" options: 
      | Settings                   | 
      | Name                       | 
      | Status                     | 
      | Signal Strength and Test   | 
      | Battery                    | 
      | Model and Firmware Details |
      When user navigates to "Model and Firmware Details" screen from the "Sensor Settings" screen
      Then user should be displayed with the following "Sensor Model and Firmware Details" options: 
      | Settings         | 
      | Model Details    | 
      | Firmware Details | 
  
  #Requirements: Single Location Single DAS Device, 1 Sensor Required
  @RenameSensors @UIAutomated
  Scenario: As a user I should be able to rename the sensors configured on my account
    Given user launches and logs in to the Lyric application
     When user navigates to "Sensor Settings" screen from the "Dashboard" screen
     When user edits the "sensor" name to "Test Sensor Name" 
      And user navigates to "Sensor" screen from the "Sensor Settings" screen
     Then user should be displayed with "Test Sensor Name" device on the "Sensor" screen
      And user reverts back the "Sensor Name" through CHIL
  
  #Requirements: Single Location Single DAS Device, 1 Sensor Required
  @DeleteSensor @UIAutomated
  Scenario: As a user I should be able to delete sensors configured to my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Sensor Settings" screen from the "Dashboard" screen 
      And user "deletes sensor" by clicking on "delete" button
     Then user should receive a "Delete Sensor Confirmation" popup
     And user "dismisses" the "Delete Sensor Confirmation" popup
  
  #Requirements: Single Location Single DAS Device, 1 Keyfob Required
  @RenameKeyfob @UIAutomated
  Scenario: As a user I should be able to rename the keyfobs configured on my account
    Given user launches and logs in to the Lyric application
     When user navigates to "Keyfob Settings" screen from the "Dashboard" screen
     When user edits the "keyfob" name to "Test Keyfob Name"
      And user navigates to "Keyfob" screen from the "Keyfob Settings" screen
     Then user should be displayed with "Test Keyfob Name" device on the "Keyfob" screen
      And user reverts back the "Keyfob Name" through CHIL
  
  #Requirements: Single Location Single DAS Device, 1 Keyfob Required
  @DeleteKeyfob
  Scenario: As a user I should be able to delete Keyfob configured to my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Keyfob Settings" screen from the "Dashboard" screen 
      And user "deletes keyfob" by clicking on "delete" button 
     Then user should receive a "Delete keyfob Confirmation" pop up 
     When user "accepts" the "Delete Keyfob Confirmation" popup
     Then user should not be displayed with the deleted "keyfob" on the "Keyfob Settings" screen
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @ChangeBaseStationVolume
  Scenario: As a user I should be able to change the base station volume
    Given user launches and logs in to the Lyric application
     When user navigates to "Security Settings" screen from the "Dashboard" screen
      And user changes the "Base Station Volume" to "0%"
     Then "Base Station Volume" value should be updated to "0" on "Security Settings" screen
     When user changes the "Base Station Volume" to "50%"
     Then "Base Station Volume" value should be updated to "50" on "Security Settings" screen
     When user changes the "Base Station Volume" to "99%"
     Then "Base Station Volume" value should be updated to "99" on "Security Settings" screen
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @ConfigureAmazonAlexa @NotAutomatable
  Scenario: As a user I should be able to configure Amazon Alexa to my DAS Panel
    Given user launches and logs in to the Lyric application
     When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
      And user "sets up Amazon Alexa" by clicking on "set up" button
     Then user should be displayed with the "Amazon Sign-in" screen
     When user signs in to "Amazon" using "<amazon emailID>" and "amazon password"
     Then user should be displayed with "Alexa Voice Service T&C" screen
     When user "allows Alexa T&C" by clicking on "Allow" button
     Then user should be displayed with the "Feature Setup Complete" screen
     When user completes "Alexa Configuration" by clicking on "Done" button
     Then user verifies that the DAS Panel "reponds" to "Amazon Alexa Voice Commands"
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @AmazonAlexaLogoff
  Scenario: As a user I should be able to log off Amazon Alexa from my DAS account
    Given user launches and logs in to the Lyric application
     When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
      And user "signs out of Amazon Alexa" by clicking on "sign out" button
     Then user should receive a "Confirm Amazon Alexa Logout" pop up
     When user "accepts" the "Confirm Amazon Alexa Logout" popup 
     Then user verifies that the DAS Panel "does not repond" to "Amazon Alexa Voice Commands"
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @AmazonAlexaAppDownload
  Scenario: As a user I should be able to download the Amazon Alexa App
    Given user launches and logs in to the Lyric application
     When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
      And user "downloads the Alexa app" by clicking on "Alexa app" button
     Then user should be navigated to the "Alexa app" download page
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @EnableDisableGeofencing
  Scenario: As a user I should be able to enable/disable geofencing on my DAS Panel account   
    Given geofencing is "enabled" on the user account through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Security Settings" screen from the "Dashboard" screen
      And user changes the "Geofencing" to "ON"
     Then "Geofencing" value should be updated to "ON" on "Security Settings" screen
     When user changes the "Geofencing" to "OFF"
     Then "Geofencing" value should be updated to "OFF" on "Security Settings" screen
  
  #LYDAS-6820,LYDAS-6890,LYDAS-3596
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VideoSettingsDisabled
  Scenario: As a user I should be able to access DAS camera settings
    Given user DAS camera is set to "off" through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Video Settings" screen from the "Dashboard" screen
     Then the following "Video Settings" options should be disabled:
      | Options          | 
      | Motion Detection | 
      | Night Vision     | 
      | Video Quality    | 
     When user selects the "Motion Detection" option 
     Then user receives a "Ensure camera is turned on" toast message
     When user selects the "Night Vision" option 
     Then user receives a "Ensure camera is turned on" toast message
     When user selects the "Video Quality" option 
     Then user receives a "Ensure camera is turned on" toast message
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyVideoSettings
  Scenario: As a user I should not be able to access certain DAS camera settings when my camera is off
    Given user DAS camera is set to "on" through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Video Settings" screen from the "Dashboard" screen
     Then user should be displayed with the following "Video Settings" options: 
      | Settings                | 
      | Motion Detection        | 
      | Camera On in Home Mode  | 
      | Camera On in Night Mode | 
      | Night Vision            | 
      | Video Quality           | 
  
  #LYDAS-6643
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyOKSecurityVoiceCommands @NotAutomatable
  Scenario: As a user I should be able to enable/disable OK Security Voice Commands
    Given user launches and logs in to the Lyric application
     When user navigates to "Voice Commands" screen from the "Dashboard" screen
      And user changes the "OK Security Voice Commands" to "ON"
     Then "OK Security Voice Commands" value should be updated to "ON" on "Voice Commands" screen
      And user verifies that the DAS Panel "reponds" to "OK Security Voice Commands"
      And user changes the "OK Security Voice Commands" to "OFF"
     Then "OK Security Voice Commands" value should be updated to "OFF" on "Voice Commands" screen
      And user verifies that the DAS Panel "does not respond" to "OK Security Voice Commands"
     Then user should be displayed with the following "Voice Commands" options: 
      | Commands          | 
      | Trigger Phrase    | 
      | Security Commands | 
      | Camera Commands   | 
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyCameraOnInHomeMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "ON"
     Then "Camera ON in Home Mode" value should be updated to "ON" on "Video Settings" screen
     When user navigates to "Camera Solution Card" screen from the "Video Settings" screen
     Then user camera is "live streaming"
  
  #LYDAS-3148,LYDAS-2634
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyCameraOffInHomeMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "Off"
     Then "Camera ON in Home Mode" value should be updated to "Off" on "Video Settings" screen
     When user navigates to "Camera Solution Card" screen from the "Video Settings" screen
     Then user camera is "not live streaming"
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyCameraOnInNightMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Night Mode" to "ON"
     Then "Camera ON in Night Mode" value should be updated to "ON" on "Video Settings" screen
     When user navigates to "Security Solution Card" screen from the "Video Settings" screen
      And user switches to "Night" mode
     When user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
     Then user camera is "live streaming"
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyCameraOffInNightMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Night Mode" to "OFF"
     Then "Camera ON in Night Mode" value should be updated to "OFF" on "Video Settings" screen
     When user navigates to "Security Solution Card" screen from the "Video Settings" screen
      And user switches to "Night" mode
      And user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
     Then user camera is "not live streaming"
  
  #LYDAS-2584
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyCameraOnInAwayMode
  Scenario: As a user I want verify that my camera is streaming when I am Away
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Camera Solution Card" screen from the "Dashboard" screen
     Then user camera is "live streaming"  
  
  #LYDAS-6939
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyNightVisionSettings
  Scenario: As a user I should be able to update my Night Vision Settings
    Given user launches and logs in to the Lyric application 
     When user navigates to "Night Vision Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "Auto" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Auto" on "Night Vision Settings" screen 
     When user navigates to "Video Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Auto" on "Video Settings" screen
     When user navigates to "Night Vision Settings" screen from the "Video Settings" screen
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "On" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "On" on "Night Vision Settings" screen 
     When user navigates to "Video Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "On" on "Video Settings" screen
     When user navigates to "Night Vision Settings" screen from the "Video Settings" screen
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "Off" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Off" on "Night Vision Settings" screen 
     When user navigates to "Video Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Off" on "Video Settings" screen
  
  #Requirements: Single Location Single DAS Device, No Sensors Required
  @VerifyVideoQualitySettings
  Scenario: As a user I should be able to update my Video Quality Settings
    Given user launches and logs in to the Lyric application 
     When user navigates to "Video Quality Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "Auto" from "Video Quality Settings" screen
     Then "Video Quality" value should be updated to "Auto" on "Video Quality Settings" screen 
     When user navigates to "Video Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "Auto" on "Video Settings" screen
     When user navigates to "Video Quality Settings" screen from the "Video Settings" screen
     Then user should be displayed with the following "Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "Low" from "Night Vision Settings" screen 
     Then "Video Quality" value should be updated to "Low" on "Video Quality Settings" screen 
     When user navigates to "Video Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "Low" on "Video Settings" screen
     When user navigates to "Video Quality Settings" screen from the "Video Settings" screen
     Then user should be displayed with the following "Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "High" from "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "High" on "Video Quality Settings" screen 
     When user navigates to "Video Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "High" on "Video Settings" screen
     
     @EnableDisableMotionDetection
     Scenario: As a user I should be able to enable or disable motion detection
     Given user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
     When user launches and logs in to the Lyric application 
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     And user changes the "Motion Detection" to "Off"
     Then "Motion Detection" value should be updated to "Off" on "Motion Detection Settings" screen
  	When user navigates to "Video Settings" screen from the "Motion Detection Settings" screen
  	Then "Motion Detection" value should be updated to "Off" on "Video Settings" screen
  	When user navigates to "Motion Detection Settings" screen from the "Video Settings" screen
     And user changes the "Motion Detection" to "On"
     Then "Motion Detection" value should be updated to "On" on "Motion Detection Settings" screen
  	When user navigates to "Video Settings" screen from the "Motion Detection Settings" screen
  	Then "Motion Detection" value should be updated to "On" on "Video Settings" screen
  	
  	@ChooseAllZones
  	Scenario: As a user I should be able to select and draw all the 4 zones
  	Given user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
     When user launches and logs in to the Lyric application 
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     Then user should be displayed with "zone 1" as the default zone
     And user verifies that the entire snapshot is selected as the motion detection zone
     When user selects "zone 2" from the "Motion Detection Settings" screen
     And user "enables" detection in "zone 2"
     Then user "should be able" to draw on "zone 2"
     When user selects "zone 3" from the "Motion Detection Settings" screen
     And user "enables" detection in "zone 3"
     Then user "should be able" to draw on "zone 3"
     When user selects "zone 4" from the "Motion Detection Settings" screen
     And user "enables" detection in "zone 4"
     Then user "should be able" to draw on "zone 4"
     
     @VerifyMotionSensitivitySettings
     Scenario: As a user I should be able to set motion sensitivity on my DAS panel to Off,High,Low, Normal and High
     Given user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
     When user launches and logs in to the Lyric application 
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     Then user should be displayed with the following "Motion Sensitivity Settings" options:
     | Settings | 
     | Off      | 
     | Low      |
     | Medium   | 
     | High     |
     When user changes the "Motion Sensitivity" to "Off"
     Then "Motion Sensitivity" value should be updated to "Off" on "Motion Detection Settings" screen
     And "no" motion detection changes should be reported
     When user changes the "Motion Sensitivity" to "Low"
     Then "Motion Sensitivity" value should be updated to "Low" on "Motion Detection Settings" screen
     And "low" motion detection changes should be reported
     When user changes the "Motion Sensitivity" to "medium"
     Then "Motion Sensitivity" value should be updated to "medium" on "Motion Detection Settings" screen
     And "medium" motion detection changes should be reported
     When user changes the "Motion Sensitivity" to "high"
     Then "Motion Sensitivity" value should be updated to "high" on "Motion Detection Settings" screen
     And "high" motion detection changes should be reported
     
     @VerifySensitivityOnZones
     Scenario Outline: As a user I want to verify sensitivity on all the zones
     Given user DAS camera is set to "on" through CHIL
      And "motion detection" is "enabled" on user DAS panel through CHIL
      And "motion sensitivity" is "high" on user DAS panel through CHIL
     When user launches and logs in to the Lyric application 
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     When user selects <zones> from the "Motion Detection Settings" screen
     And user "enables" detection in <zones>
     And user selects an area on the detection zone
     Then the selected area should be highlighted in red
     And user panel should report motion detection changes on the selected area
     Examples:
     |zones|
     |zone2|
     |zone3|
     |zone4|
     
     @VerifyMultipleZonesOverlapError
     Scenario: As a user I want to verify sensitivity area on my zones should not overlap
     Given user DAS camera is set to "on" through CHIL
      And "motion detection" is "enabled" on user DAS panel through CHIL
      And "motion sensitivity" is "high" on user DAS panel through CHIL
     When user launches and logs in to the Lyric application
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     And user ovelaps motion detection area across multiple zones
     And user navigates to "Video Settings" screen from the "Motion Detection Settings" screen
     Then user should be displayed with "Error-Zones overlap" popup