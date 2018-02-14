@DASSettings 
Feature: DAS Settings 
As user I should be able to control my DAS panel settings from the app



LYDAS-3196 - No Sensors or No Keyfobs should come instead of empty list in sensor and keyfob settings

#LYDAS-7075,	LYDAS-4088,LYDAS-4058,LYDAS-4011,LYDAS-3294,LYDAS-3271,LYDAS-3116,LYDAS-2982,LYDAS-2808,LYDAS-2610,LYDAS-2563,LYDAS-2497,LYDAS-2408,LYDAS-2404,LYDAS-2231,LYDAS-2167		
  @DeleteBaseStation @UIAutomated
  Scenario: As a user I should be able to delete my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Base Station Settings" screen from the "Dashboard" screen 
      And user "deletes DAS device" by clicking on "delete" button 
     Then user should receive a "Delete DAS Confirmation" pop up 
      And user "dismisses" the "Delete DAS Confirmation" popup 
  
  #LYDAS-3398,LYDAS-3270,LYDAS-2770
  @VerifyDASSettings @UIAutomated
  Scenario: As a user I want to verify that all DAS Settings options are available to me 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Security Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Security Settings" options: 
      | Settings                   | 
      | Camera Settings            | 
      | Amazon Alexa               | 
      | OK Security Voice Commands | 
      | Entry/Exit Delay           | 
      | Volume                     | 
      | Geofencing                 | 
      | Key Fob                    | 
      | Sensors                    | 
      | Base Station Settings      | 
      | Know your Security Modes   | 
  
  #LYDAS-4216,LYDAS-3376,LYDAS-3244,LYDAS-2660,LYDAS-2403,LYDAS-2380,LYDAS-2360,LYDAS-2149,LYDAS-3440
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
  @RenameDASBaseStation @UIAutomated 
  Scenario: As a user I want to rename my Base station through th application 
    Given user launches and logs in to the Lyric application 
      And user navigates to "Base Station Settings" screen from the "Dashboard" screen 
     When user edits the "DAS Panel" name to "Test Panel Name" 
      And user navigates to "Dashboard" screen from the "Base Station Settings" screen 
     Then user should be displayed with "Test Panel Name" device on dashboard 
      And user reverts back the "DAS device name" through CHIL 
  
  #LYDAS-6941
  @ResetWiFi
  Scenario: As a user I want to reset my DAS Panel WiFi 
    Given user launches and logs in to the Lyric application 
      And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
     When user "resets WiFi" by clicking on "Reset WiFi" button  
     Then user should be displayed with the "Reset WiFi Instructions" screen
     When user presses the context button for 5 seconds on the DAS Panel
     Then the DAS panel should start flashing blue
     When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
  # Put in DIY steps here
     Then user "wifi name" should be updated to "<WiFi Name>" in the "Base Station WiFi" screen.
  
  #LYDAS-4099,LYDAS-3633,LYDAS-3469,LYDAS-3419,LYDAS-2411
  @VerifyDASPanelModelAndFirmwareDetails
  Scenario: As a user I want to view that all model, firmware and panel details 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Base Station Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Panel Settings" options: 
      | Settings           | 
      | Base Station Wi-Fi | 
      | Battery            | 
     When user navigates to "Model and Firmware Details" screen from the "Base Station Settings" screen 
     Then user should be displayed with the following "Panel Model and Firware Details" options: 
      | Settings         | 
      | Model Details    | 
      | Firmware Details | 
  
  @DASSettingsDisabled 
  Scenario: As a user I should not be allowed to change DAS settings when I am not home 
    Given user sets the entry/exit timer to "15" seconds 
      And user is set to "Away" mode through CHIL 
     When user launches and logs in to the Lyric application 
      And user navigates to "Security Settings" screen from the "Dashboard" screen
     Then the following "Security Settings" options should be disabled:
      | Options          | 
      | Entry/Exit Delay | 
      | Volume           | 
      | Geofencing       | 
     When user navigates to "Base Station Settings" screen from the "Security Settings" screen 
     Then the following "Security Settings" options should be disabled:
      | Options            | 
      | Base Station Wi-Fi | 
  
  @VerifyDisplayedSensors
  Scenario: As a user I should be displayed with all my sensors and keyfobs
    Given user launches and logs in to the Lyric application
     When user navigates to "Keyfob" screen from the "Dashboard" screen
     Then user should be displayed with all "keyfob" names configured to his/her account
     When user navigates to "Keyfob Settings" screen from the "Keyfob" screen
     Then user should be displayed with the following "Keyfob Settings" options: 
      | Settings                   | 
      | Name                       | 
      | Model and Firmware Details | 
      | Delete                     | 
     When user navigates to "Sensor" screen from the "Keyfob Settings" screen
     Then user should be displayed with all "sensor" names configured to his/her account
     Then user should be displayed with the following "Keyfob Settings" options: 
      | Settings                   | 
      | Name                       | 
      | Status                     | 
      | Signal Strength and Test   | 
      | Battery                    | 
      | Model and Firmware Details | 
      | Delete                     | 
 
  @RenameSensors
  Scenario: As a user I should be able to rename the sensors configured on my account
    Given user launches and logs in to the Lyric application
     When user navigates to "Sensor Settings" screen from the "Dashboard" screen
     When user edits the "sensor" name to "Test Sensor Name" 
      And user navigates to "Sensor" screen from the "Sensor Settings" screen
     Then user should be displayed with "Test Sensor Name" device on the "Sensor Settings" screen
      And user reverts back the "Sensor Name" through CHIL
  
  @RenameKeyfob
  Scenario: As a user I should be able to rename the keyfobs configured on my account
    Given user launches and logs in to the Lyric application
     When user navigates to "Keyfob Settings" screen from the "Dashboard" screen
     When user edits the "keyfob" name to "Test Keyfob Name"  
      And user navigates to "Keyfob" screen from the "Keyfob Settings" screen
     Then user should be displayed with "Test Keyfob Name" device on the "Keyfob Settings" screen
      And user reverts back the "Keyfob Name" through CHIL
  
  @VerifyKeyfobModelAndFirmwareDetails
  Scenario: As a user I should be able to verify keyfob model and firmware details
    Given user launches and logs in to the Lyric application
     When user navigates to "Keyfob Model and Firmware Settings" screen from the "Dashboard" screen
     Then user should be displayed with the following "Keyfob Model and Firmware Details" options: 
      | Details          | 
      | Model Details    | 
      | Firmware Details | 
  
  @VerifySensorModelAndFirmwareDetails
  Scenario: As a user I should be able to verify keyfob model and firmware details
    Given user launches and logs in to the Lyric application
     When user navigates to "Sensor Model and Firmware Settings" screen from the "Dashboard" screen
     Then user should be displayed with the following "Sensor Model and Firmware Details" options: 
      | Details          | 
      | Model Details    | 
      | Firmware Details | 
  
  @DeleteSensor
  Scenario: As a user I should be able to delete sensors configured to my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Sensor Settings" screen from the "Dashboard" screen 
      And user "deletes sensor" by clicking on "delete" button 
     Then user should receive a "Delete Sensor Confirmation" pop up 
     When user "accepts" the "Delete Sensor Confirmation" popup
     Then user should not be displayed with the deleted "sensor" on the "Sensor Settings" screen 
  
  @DeleteKeyfob
  Scenario: As a user I should be able to delete Keyfob configured to my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Keyfob Settings" screen from the "Dashboard" screen 
      And user "deletes keyfob" by clicking on "delete" button 
     Then user should receive a "Delete keyfob Confirmation" pop up 
     When user "accepts" the "Delete Keyfob Confirmation" popup
     Then user should not be displayed with the deleted "keyfob" on the "Keyfob Settings" screen
  
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
  
  @ConfigureAmazonAlexa
  Scenario: As a user I should be able to configure Amazon Alexa to my DAS Panel
    Given user launches and logs in to the Lyric application
     When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
      And user "sets up Amazon Alexa" by clicking on "set up" button
     Then user should be displayed with the "Amazon Sign-in" screen
     When user signs in to "Amazon" using "<amazon emailID>" and "amazon password"
     Then .....
  
  @EnableDisableGeofencing
  Scenario: As a user I should be able to enable/disable geofencing on my DAS Panel account   
    Given Geofencing is "disabled" on the user account
      And user launches and logs in to the Lyric application
     When user navigates to "Security Settings" screen from the "Dashboard" screen
      And user changes the "Geofencing" to "ON"
     Then "Geofencing" value should be updated to "ON" on "Security Settings" screen
     When user changes the "Geofencing" to "OFF"
     Then "Geofencing" value should be updated to "OFF" on "Security Settings" screen
  
  #LYDAS-6820,LYDAS-6890,LYDAS-3596
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
  
  #
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
  @VerifyOKSecurityVoiceCommands
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
  
  @VerifyCameraOnInHomeMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "ON"
     Then "Camera ON in Home Mode" value should be updated to "ON" on "Video Settings" screen
     When user navigates to "Camera Solution Card" screen from the "Video Settings" screen
  #Change the below statement
     Then user camera should be "on"
  
  #LYDAS-3148,LYDAS-2634
  @VerifyCameraOffInHomeMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "Off"
     Then "Camera ON in Home Mode" value should be updated to "Off" on "Video Settings" screen
     When user navigates to "Camera Solution Card" screen from the "Video Settings" screen
  #Change the below statement
     Then user camera should be "off"
  
  @VerifyCameraOnInNightMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Night Mode" to "ON"
     Then "Camera ON in Night Mode" value should be updated to "ON" on "Video Settings" screen
     When user navigates to "Security Solution Card" screen from the "Video Settings" screen
  #Put Command Control steps to switch to NIght
     When user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
  #Change the below statement
     Then user camera should be "on"
  
  @VerifyCameraOffInNightMode
  Scenario: As a user I should be able to enable/disable Camera Settings in Home Mode
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Video Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Night Mode" to "OFF"
     Then "Camera ON in Night Mode" value should be updated to "OFF" on "Video Settings" screen
     When user navigates to "Security Solution Card" screen from the "Video Settings" screen
  #Put Command Control steps to switch to NIght
     When user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
  #Change the below statement
     Then user camera should be "off"
  
  #LYDAS-2584
  @VerifyCameraOnInAwayMode
  Scenario: As a user I want verify that my camera is streaming when I am Away
    Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
    When user navigates to "Camera Solution Card" screen from the "Dashboard" screen
    Then user camera should be "on"  
  
  #LYDAS-6939
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
    
     
 