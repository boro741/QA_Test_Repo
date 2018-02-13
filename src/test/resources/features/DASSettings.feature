@DASSettings 
Feature: DAS Settings 
As user I should be able to control my DAS panel settings from the app

  @DeleteBaseStation @UIAutomated
  Scenario: As a user I should be able to delete my DAS panel from my account through the Lyric application 
    Given user is set to "Home" mode through CHIL 
      And user launches and logs in to the Lyric application 
     When user navigates to "Base Station Settings" screen from the "Dashboard" screen 
      And user "deletes DAS device" by clicking on "delete" button 
     Then user should receive a "Delete DAS Confirmation" pop up 
      And user "dismisses" the "Delete DAS Confirmation" popup 
  
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
  
  @RenameDASBaseStation @UIAutomated 
  Scenario: As a user I want to rename my Base station through th application 
    Given user launches and logs in to the Lyric application 
      And user navigates to "Base Station Settings" screen from the "Dashboard" screen 
     When user edits the "DAS Panel" name to "Test Panel Name" 
      And user navigates to "Dashboard" screen from the "Base Station Settings" screen 
     Then user should be displayed with "Test Panel Name" device on dashboard 
      And user reverts back the "DAS device name" through CHIL 
  
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
     |Options|
     |Entry/Exit Delay|
     |Volume|
     |Geofencing|
     When user navigates to "Base Station Settings" screen from the "Security Settings" screen 
     Then the following "Security Settings" options should be disabled:
     |Options|
     |Base Station Wi-Fi|
     
  @VerifyDisplayedSensors
  Scenario: As a user I should be displayed with all my sensors and keyfobs
  Given user launches and logs in to the Lyric application
  When user navigates to "Keyfob" screen from the "Dashboard" screen
  Then user should be displayed with all "keyfob" names configured to his/her account
  When user navigates to "Keyfob Settings" screen from the "Keyfob" screen
  Then user should be displayed with the following "Keyfob Settings" options: 
      | Settings         | 
      | Name             | 
      | Model and Firmware Details |
      | Delete  |
  When user navigates to "Sensor" screen from the "Keyfob Settings" screen
  Then user should be displayed with all "sensor" names configured to his/her account
  Then user should be displayed with the following "Keyfob Settings" options: 
      | Settings         | 
      | Name             | 
      | Status             |
      | Signal Strength and Test            |
      | Battery           |
      | Model and Firmware Details |
      | Delete  |
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
      | Details           | 
      | Model Details     | 
      | Firmware Details  | 
 
 @VerifySensorModelAndFirmwareDetails
 Scenario: As a user I should be able to verify keyfob model and firmware details
 Given user launches and logs in to the Lyric application
 When user navigates to "Sensor Model and Firmware Settings" screen from the "Dashboard" screen
 Then user should be displayed with the following "Sensor Model and Firmware Details" options: 
      | Details           | 
      | Model Details     | 
      | Firmware Details  |
  
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
  
  @VerifyVideoSettings
  Scenario: As a user I should be able to access DAS camera settings
  Given user DAS camera is set to "on" through CHIL
  And user launches and logs in to the Lyric application
  When user navigates to "Video Settings" screen from the "Dashboard" screen
  Then the following "Video Settings" options should be disabled:
  |Options|
     |Motion Detection|
     |Night Vision|
     |Video Quality|
  
  
  @VideoSettingsDisabled
  Scenario: As a user I should not be able to access certain DAS camera settings when my camera is off
  Given user DAS camera is set to "off" through CHIL
  And user launches and logs in to the Lyric application
  When user navigates to "Video Settings" screen from the "Dashboard" screen
 Then user should be displayed with the following "Video Settings" options: 
     |Settings|
     |Motion Detection|
     |Camera On in Home Mode|
     |Camera On in Night Mode|
     |Night Vision|
     |Video Quality|
     
  @VerifyOKSecurityVoiceCommands
  Scenario: As a user I should be able to enable/disable OK Security Voice Commands
  Given user launches and logs in to the Lyric application
  When user navigates to "Video Settings" screen from the "Dashboard" screen
 Then user should be displayed with the following "Video Settings" options: 
     |Settings|
     |Motion Detection|
     |Camera On in Home Mode|
     |Camera On in Night Mode|
     |Night Vision|
     |Video Quality|
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 
    
    
  @CameraMicrophone_On 
  Scenario: As a user I want to verify that I can turning on camera microphone. 
    Given user is in "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
     When user enables "Camera Microphone" 
     Then verify "camera microphone" is "enabled" 
  
  @CameraMicrophone_Off 
  Scenario: As a user I want to verify that I can turning Off camera microphone. 
    Given user is in "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
     When user disables "Camera Microphone" 
     Then verify "camera microphone" is "Disable" 
  
  @CameraNightVision_Menu @UIAutomated 
  Scenario: As a user I want to verify the Night vision setting menu options. 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user clicks on "NIGHT VISION SETTINGS" menu 
     Then verify the display of following: 
      | Elements          | 
      | NIGHT VISION AUTO | 
      | NIGHT VISION ON   | 
      | NIGHT VISION OFF  | 
     When user taps on "NIGHT VISION ON" 
     Then verify "NIGHT VISION ON" is selected 
     When user taps on "NIGHT VISION OFF" 
     Then verify "NIGHT VISION OFF" is selected 
     When user taps on "NIGHT VISION AUTO" 
     Then verify "NIGHT VISION AUTO" is selected 
  
  @CameraNightVision_OnSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of On option for Night vision setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Night vision setting" 
      And user taps on "Night Vision ON" 
     Then verify "Night Vision ON text" is displayed 
      And user taps on "Night vision setting" 
      And verify "Night Vision ON" is "selected" 
  
  @CameraNightVision_OffSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of Off option for Night vision setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Night vision setting" 
      And user taps on "Night Vision OFF" 
     Then verify "Night Vision OFF text" is displayed 
      And user taps on "Night vision setting" 
      And verify "Night Vision OFF" is "selected" 
  
  @CameraNightVision_AutoSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of Auto option for Night vision setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Night vision setting" 
      And user taps on "Night Vision AUTO" 
     Then verify "Night Vision AUTO text" is displayed 
      And user taps on "Night vision setting" 
      And verify "Night Vision AUTO" is "selected" 
  
  @videoQualitySettingMenu @UIAutomated 
  Scenario: As a user I want to verify the Video Quality setting menu options. 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user clicks on "VIDEO QUALITY SETTINGS" menu 
     Then verify the display of following: 
      | Elements           | 
      | VIDEO QUALITY AUTO | 
      | VIDEO QUALITY LOW  | 
      | VIDEO QUALITY HIGH | 
      And user taps on "VIDEO QUALITY LOW" 
     Then verify "VIDEO QUALITY LOW" is selected 
     When user taps on "VIDEO QUALITY HIGH" 
     Then verify "VIDEO QUALITY HIGH" is selected 
     When user taps on "VIDEO QUALITY AUTO" 
     Then verify "VIDEO QUALITY AUTO" is selected 
  
  @videoQualitySettingAutoSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of Auto option for Video Quality setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Video Quality setting" 
      And user taps on "Video Quality AUTO" 
     Then verify "Video Quality AUTO" is "selected" 
     Then verify "Video Quality Auto text" is displayed 
  
  @videoQualitySettingLowSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of Low option for Video Quality setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Video Quality setting" 
      And user taps on "Video Quality LOW" 
     Then verify "Video Quality LOW" is "selected" 
     Then verify "Video Quality Low text" is displayed 
  
  @videoQualitySettingHighSelected @UIAutomated 
  Scenario: 
  As a user I want to verify the selection of High option for Video Quality setting 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Video Quality setting" 
      And user taps on "Video Quality HIGH" 
     Then verify "Video Quality HIGH" is "selected" 
     Then verify "Video Quality High text" is displayed 
  
  @motionDetectionSettings 
  Scenario: 
  As a user i want to verify if motion detection is disabled when user disables motion detection 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user taps on "MOTION DETECTION" 
     When user disables "Motion Detection button" 
     Then verify "Motion Detection" button is "Disabled" 
     When user enables "Motion Detection button" 
     Then verify "Motion Detection" button is "enabled" 
  #And verify "motion detection value off" is displayed
  
  @motionDetection_verifyZoneSensitivityLevel 
  Scenario: 
  As a user i want to verify Each zones has LOW,Normal,High sensitivity Level 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user taps on "MOTION DETECTION" 
     When user enables "Motion Detection button" 
     When user taps on "ZONE1" 
     Then verify "ZONE1" is displayed "Low" 
  # various zones and all sensitivity option need to be added.
  
  @motionDetection_chooseZones 
  Scenario: As a user i want to verify if user can choose between the zone 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user taps on "MOTION DETECTION" 
     When user enables "Motion Detection button" 
     When user taps on "ZONE1" 
     Then verify "ZONE1" is "selected" 
     When user taps on "ZONE2" 
     Then verify "ZONE2" is "selected"" 
     When user taps on "ZONE3" 
     Then verify "ZONE3" is "selected" 
     When user taps on "ZONE4" 
     Then verify "ZONE4" is "selected" 
  
  @motionDetection_retainZoneSelected 
  Scenario: 
  As a user i want to verify if values are retained when user chnages the settings in Motion detection 
    Given "camera Privacy off" as precondition 
     When user clicks on "HAMBURGER MENU" button 
      And user clicks on "DAS DEVICE" menu 
      And user clicks on "BASE STATION SETTINGS" menu 
      And user clicks on "CAMERA SETTINGS" menu 
      And user taps on "MOTION DETECTION" 
     When user enables "Motion Detection button" 
     When user taps on "ZONE2" 
      And user taps on "ENABLE DETECTION ZONE" 
      And verify "ENABLE DETECTION ZONE" is "selected" 
      And user taps on "BACK" 
      And user taps on "MOTION DETECTION" 
      And user taps on "ZONE2" 
     Then verify "ENABLE DETECTION ZONE" is "selected" 
  
  @motionDetection_verifyOtherZonesSensitivity 
  Scenario Outline: 
  As user i want to verify if user can enable detection zone for Zone2 ,Zone3 ,Zone3 and Adjust detection zone 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user taps on "Motion Detection" 
     When user taps on <Zones> 
     Then verify <Zones> is "selected" 
      And verify  <Zones> "Detection Zone" is "Adjustable" 
      And user force kill and relaunches the app 
    Examples: 
      | Zones | 
      | Zone1 | 
      | Zone2 | 
      | zone3 | 
      | Zone4 | 
  
  @motionDetection_verifyDetectionZoneHighlight 
  Scenario: 
  As user i want to verify if Detection Zone is highlight in RED when multiple zone overlap 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user "enabled" "Motion Detection" 
      And user taps on "Zone1" 
      And user "Adjusts" "Zone1" 
      And user taps on "Zone2" 
     When user "Overlaps zone2" on "zone1" 
     Then verify "Detection Zone highlight in RED" 
  
  @motionDetection_validateErrorOnMultipleZoneOverlaps 
  Scenario: 
  As user i want to verify if user get an error message when multiple zone overlaped and user navigates back 
    Given user is "Das Device Settings" 
     When user clicks on "Base station settings" 
      And user clicks on "Camera settings" 
      And user enables "Motion Detection button" 
      And user taps on "Zone1" 
      And user "Adjusts" "Zone1" 
      And user taps on "Zone2" 
     When user "Overlaps zone2" on "zone1" 
      And User taps on "Back" 
     Then verify "Please Arrange detection zone properly" is displayed 
      And verify "OK button" is displayed 
  
  @sensorSettings 
  Scenario: As a user I want to view my sensor settings 
     When user clicks on "Hamburger menu" button 
      And user clicks on "DAS Device" menu 
      And user clicks on "Sensors list" menu 
     Then verify "Appropriate sensor type with sensor" is displayed 
  # Then verify Each sensor are listed in Appropriate sensor type
      And Verify sensor status is displayed algined with snesor name 
  
  @viewEnrolledSensorsSettings 
  Scenario: 
  As a user I want to view number of sensors enrolled in Das device setting 
    Given user is in "Das Device Settings" 
     When user has enrolled few sensors 
     Then Verify enrolled sensor count is displayed 
  
  @viewAccessSensorDetails 
  Scenario: 
  As a user I want to sensor details so that i can review enrolled sensors 
    Given user is in "Das Device Settings" 
      And user click on "Sensors settings" 
     When user clicks on "Access sensor" 
     Then verify the display of following: 
      | Elements                   | 
      | Sensor Type image          | 
      | Sensor Name                | 
      | ON/Off toggle              | 
      | Sensor Status              | 
      | Sensor signla Strength     | 
      | Battery                    | 
      | Select Chime               | 
      | Model and Firmware Details | 
      | Delete                     | 
  
  @viewMotionSensorDetails 
  Scenario: 
  As a user I want to sensor details so that i can review enrolled sensors 
    Given user is in "Das Device Settings" 
      And user click on "Sensors settings" 
     When user clicks on "Motion Viewer" 
     Then verify the display of following: 
      | Elements                   | 
      | Sensor Type image          | 
      | Sensor Name                | 
      | ON/Off toggle              | 
      | Sensor Status              | 
      | Sensor signla Strength     | 
      | Battery                    | 
      | Select Chime               | 
      | Model and Firmware Details | 
      | Delete                     | 
      | Motion Sensitivity         | 
  
  @renameSensor 
  Scenario Outline: As a user i want to rename my sensor using Sensor settings 
    Given user is in "Das Device Settings" 
      And user click on "Sensors settings" 
      And user clicks on <Sensor> 
     When user clicks on "Sensor text feild" 
     Then verify user can clear text 
     When user renames the sensor 
     Then verify renamed Base station name persists 
    Examples: 
      | Sensor        | 
      | Access sensor | 
      | Motion Viewer | 
  
  @deleteSensor 
  Scenario Outline: As a user i want delete my sensor from sensors setting 
    Given user is "Das Device Settings" 
     When user clicks on "Sensors settings" 
      And user clicks on <Sensor> 
      And user clicks on "Delete" 
     Then verify Sensor is delete from the User account 
    Examples: 
      | Sensor        | 
      | Access sensor | 
      | Motion Viewer | 
  
  @verifySensorSignal 
  Scenario Outline: 
  As a user I want to know what the optimatal signal strength is to place my sensor 
    Given user is "Das Device Settings" 
     When user clicks on "Sensors settings" 
      And user clicks on <Sensor> 
     Then user should be displayed with sensor signal strength 
     When user moves the sensor away-from/towards the das device 
     Then signal strength should update on the application 
    Examples: 
      | Sensor        | 
      | Access sensor | 
      | Motion Viewer | 
  
  @SensorMotionSensitivityLevel 
  Scenario: 
  As a user i want to verify motion sensor has LOW,Normal,High sensitivity Level 
    Given user is "Das Device Settings" 
     When user clicks on "Sensors settings" 
      And user clicks on "Motion Viewer" 
     Then verify "Motion Viewer" is displayed "Low Normal High" 
  
  @EnableDisableSensor 
  Scenario Outline:  As a user i want to enable/disable sensor on my prefernce 
    Given user is "Das Device Settings" 
     When user clicks on "Sensors settings" 
      And user clicks on <sensor> 
     Then verify user can "Enable" 
      And verify approriate Status is displayed 
    Examples: 
      | Sensor        | 
      | Access sensor | 
      | Motion Viewer | 
  
  @AlertWhenSensorDisabled 
  Scenario Outline: 
  As a user i want to view alert message when user disbales the sensor 
    Given user is "Das Device Settings" 
     When user clicks on "Sensors settings" 
      And user clicks on <sensor> 
     Then verify user can "disable" 
      And verify an alert message is displayed 
     When user clicks on "OK" 
     Then verify sensor is disabled 
      And verify approriate Status is displayed in detail view 
    Examples: 
      | Sensor        | 
      | Access sensor | 
      | Motion Viewer | 
  
