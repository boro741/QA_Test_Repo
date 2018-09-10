  @CameraSettings 
  Feature: DAS Camera Settings 
  As user I should be able to configure camera settings from the app
  
  @CameraSettingsPartialDisabled @P4 @NotAutomatable
  Scenario Outline: As a user I should be having limited access to configure camera settings when my camera is in offline or in upgrade state 
    Given user camera is in <State> 
      And user launches and logs in to the Lyric application
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
     Then the following "DAS Camera Settings" options should be disabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
      | Camera On in Home mode |
      | Camera On in Night mode|
      And the following "Camera Settings" options should be enabled:
      | Options             | 
      | Manage Alerts       |
      | People Detection    |
     Examples:
      |State  |
      |Offline|
      |Upgrade|
          
     
  @DAS_CameraSettingsCameraOff @P3 @Automated
  Scenario: As a user I should be having limited access to configure camera settings when my camera is in off state 
     Given  user is set to "Home" mode through CHIL
	Then user launches and logs in to the Lyric application
      And user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is set to "OFF"
      When user navigates to "Camera Settings" screen from the "Camera Solution Card" screen
     Then the following "DAS Camera Settings" options should be disabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
      And the following "DAS Camera Settings" options should be enabled:
      | Options             |
      | Manage Alerts       |
      | People detection    |
      | Camera On Home mode |
      | Camera On Night mode|  
      
  @DAS_CameraSettingsCameraOn @P1 @Automated
  Scenario: As a user I should be able to configure camera settings when my camera is in on state 
      Given  user is set to "Home" mode through CHIL
	Then user launches and logs in to the Lyric application
      Then user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is set to "ON"
     When user navigates to "Camera Settings" screen from the "Camera Solution Card" screen
     Then the following "DAS Camera Settings" options should be enabled:
      | Options             | 
      | Manage alerts       |
      | Motion Detection   | 
      | People detection	|
      | Night Vision        | 
      | Video Quality       |
      | Camera On in Home Mode |
      | Camera On in Night Mode|  

   @CameraSettingsCameraOffPanelArmed @P3 @Automated
  Scenario Outline: As a user I should be having limited access to configure camera settings when my camera is in off state 
    Given  user is set to <State> mode through CHIL
	Then user launches and logs in to the Lyric application
      Then user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is set to "OFF"
       When user navigates to "Camera Settings" screen from the "Camera Solution Card" screen
     Then the following "DAS Camera Settings" options should be disabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
      And the following "DAS Camera Settings" options should be enabled:
      | Options             |
      | Manage Alerts       |
      | People detection    |
      | Camera On in Home Mode |
      | Camera On in Night Mode|  
     Examples:
      |State|
      |Away |
      |Night| 
      
  @CameraSettingsCameraOnPanelArmed @P3 @Automated
  Scenario Outline: As a user I should be able to configure camera settings when my camera is in on state 
    Given  user is set to <State> mode through CHIL
	Then user launches and logs in to the Lyric application
      Then user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is set to "ON"
       When user navigates to "Camera Settings" screen from the "Camera Solution Card" screen
     Then the following "DAS Camera Settings" options should be enabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
      | Manage Alerts       |
      | People detection    |
      And the following "DAS Camera Settings" options should be disabled:
      | Camera On in Home Mode |
      | Camera On in Night Mode|  
      Examples:
      |State|
      |Away |
      |Night|
  
 
 
 @CameraSettingsCameraModeGeofenceAway @P2 @Automatable
  Scenario: As a user I should be able to set to geofencing mode so that my camera turns on when i am away and off when i am in home automatically without manual intervention 
    Given user launches and logs in to the Lyric application
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
      And user selects camera mode as "Geofencing" 
      And user location with status "Geofence Away"
     Then the following "Camera Settings" options should be disabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
      | Manage Alerts       |
      | People detection    |
     And the following "Camera Settings" options should be enabled:
      | Options             |
      | Camera On Home mode |
      | Camera On Night mode| 

         
  @CameraSettingsCameraModeGeofenceHome @P2 @Automatable
  Scenario: As a user I should be able to set to geofencing mode so that my camera turns on when i am away and off when i am in home automatically without manual intervention 
    Given user launches and logs in to the Lyric application
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
      And user selects camera mode as "Geofencing" 
      And user location with status "Geofence Home"
     Then the following "Camera Settings" options should be enabled:
      | Options             | 
      | Manage Alerts       |
      | People detection    |
      | Camera On Home mode |
      | Camera On Night mode|
     And the following "Camera Settings" options should be disabled:
      | Options             | 
      | Motion Detection    | 
      | Night Vision        | 
      | Video Quality       |
     
 
   @CameraSettingsManageAlertsDisabled @P2 @Automatable
   Scenario Outline: As a user I should be able to disable alert of camera status, sound event and motion event on my demand to get alerts in app or in email on alerts detection 
    Given user launches and logs in to the Lyric application
    Then user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is in <State> 
     When user navigates to "Manage Alerts" screen from the "Dashboard" screen
      And user changes the "Camera Face Detection alerts" to "OFF"
      When user navigates back and forth in "Manage Alerts" screen
     Then "People Detection Alerts" value should be updated to "Off" on "Manage Alerts" screen 
     When user disables "Motion Event Alerts" 
     Then user should not be displayed with "email" option
      And "Motion Event Alerts" value should be updated to "Off" on "Manage Alerts" screen     
     
     Examples:
      |State  |
      |On     |
      |Off    |
      #|Offline|
      #|Upgrade|
      
     
   @EnableDisableMotionDetection @P2  @Automatable
   Scenario: As a user I should be able to enable or disable motion detection
   Given user camera is in "on" 
     And motion detection is "enabled" on user camera through CHIL
    When user launches and logs in to the Lyric application 
     And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     And user changes the "Motion Detection" to "Off"
    Then "Motion Detection" value should be updated to "Off" on "Motion Detection Settings" screen
     And user should be displayed with the following "Motion Detection" options disabled:
     | Settings | 
     | Motion Detection Zone| 
     | Motion Sensitivity   |
  	When user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
  	Then "Motion Detection" value should be updated to "Off" on "Camera Settings" screen
  	When user navigates to "Motion Detection Settings" screen from the "Camera Settings" screen
     And user changes the "Motion Detection" to "On"
    Then "Motion Detection" value should be updated to "On" on "Motion Detection Settings" screen
  	When user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
  	Then "Motion Detection" value should be updated to "On" on "Camera Settings" screen
     And user should be displayed with the following "Motion Detection" options enabled:
     | Settings | 
     | Motion Detection Zone| 
     | Motion Sensitivity   |
    #login with different mobiles for the status of configured options to verify the settings as user account level 
  	
  	@ChooseMotionDetectionZones @P3 @Automatable
  	Scenario: As a user I should be able to select and draw all the 4 zones so that i can set different sensitivity for each zones in the camera frame based on my requirement  
  	Given user camera is set to "on" through CHIL
      And motion detection is "enabled" on user camera through CHIL
     When user launches and logs in to the Lyric application 
      And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     Then user should be displayed with "zone 1" as the default zone
      And user verifies that the entire snapshot is selected as the motion detection zone
     Then user "should be able" to draw on "zone 1"
     When user selects "zone 2" from the "Motion Detection Settings" screen
      And user "enables" detection in "zone 2"
     Then user "should be able" to draw on "zone 2"
     When user selects "zone 3" from the "Motion Detection Settings" screen
      And user "enables" detection in "zone 3"
     Then user "should be able" to draw on "zone 3"
     When user selects "zone 4" from the "Motion Detection Settings" screen
      And user "enables" detection in "zone 4"
     Then user "should be able" to draw on "zone 4"
     #login with different mobiles for the status of configured options to verify the settings as user account level
           
     @ChooseMotionDetectionZoneError @P3 @Automatable
  	Scenario: As a user I should be shown with popup message on failure to load snapshot to select and draw all the 2 zones 
  	Given user camera is set to "on" through CHIL
      And motion detection is "enabled" on user camera through CHIL
     When user launches and logs in to the Lyric application 
      And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
     When user selects <Zone> from the "Motion Detection Settings" screen
     #Fail to load screenshot
     Then user should be shown with "Loading spinner"
      And user should be shown with "Retry"
     When user selects "Retry"
     Then user should be shown with "Unable to take the snapshot"
     
     
     @VerifyMotionSensitivitySettings @P2 @Automatable
     Scenario Outline: As a user I should be able to set motion sensitivity on DAS camera to Off,Low, Normal and High
     Given user camera is set to "on" through CHIL
       And motion detection is "enabled" on user camera through CHIL
      When user launches and logs in to the Lyric application 
       And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
      When user selects <Zone> from the "Motion Detection Settings" screen 
      Then user should be displayed with the following "Motion Sensitivity Settings" options:
      | Settings | 
      | Off      | 
      | Low      |
      | Normal   | 
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
     Examples:
      |Zone   |
      |Zone1  |
      |Zone2  |
      |Zone3  |
      |Zone4  |
  
    
     @VerifyMultipleZonesOverlapError  @P3  @Notautomatable
     Scenario: As a user I want to verify sensitivity area on my zones should not overlap
     Given user camera is set to "on" through CHIL
      And "motion detection" is "enabled" on user camera through CHIL
      And "motion sensitivity" is "high" on user camera through CHIL
     When user launches and logs in to the Lyric application
      And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
      And user ovelaps motion detection area across multiple zones
      And user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
     Then user should be displayed with "Error-Zones overlap" popup
     
     
     @VerifyOutsideZonesWarningMessage  @P3 @Automatable
     Scenario: As a user I should be shown with warning message if any area on my zones is not covered for my confirmation
     Given user camera is set to "on" through CHIL
      And "motion detection" is "enabled" on camera through CHIL
      And "motion sensitivity" is "high" on camera through CHIL
     When user launches and logs in to the Lyric application
      And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
      And user sets motion detection area for multiple zones without covering few area of frame
      And user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
     Then user should be displayed with "Warning" popup
     When user "cancels" outside zone "warning" popup
     Then user should be retained in "Motion Detection" screen
     When user "confirms" outside zone "warning" popup
     Then user should be displayed with "Camera settings" screen
     
      
  @VerifyNightVisionSettings @P2 @Automatable
  Scenario: As a user I should be able to set my Night Vision Settings so that my camera works even in night 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Night Vision Settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "Auto" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Auto" on "Night Vision Settings" screen 
     When user navigates to "Camera Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Auto" on "Camera Settings" screen
     When user navigates to "Night Vision Settings" screen from the "Camera Settings" screen
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "On" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "On" on "Night Vision Settings" screen 
     When user navigates to "Camera Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "On" on "Camera Settings" screen
     When user navigates to "Night Vision Settings" screen from the "Camera Settings" screen
     Then user should be displayed with the following "Night Vision Settings" options: 
      | Settings | 
      | Auto     | 
      | On       | 
      | Off      | 
     When user selects "Off" from "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Off" on "Night Vision Settings" screen 
     When user navigates to "Camera Settings" screen from the "Night Vision Settings" screen 
     Then "Night Vision" value should be updated to "Off" on "Camera Settings" screen
     #login with different mobiles for the status of configured options to verify the settings as user account level
  
  
   @DAS_VerifyVideoQualitySettings @P2 @Automated
   Scenario: As a user I should be able to set my Video Quality Settings based on my network connection
   Given  user is set to "Home" mode through CHIL
	Then user launches and logs in to the Lyric application
      And user navigates to "DAS Camera Solution Card" screen from the "Dashboard" screen
      And user camera is set to "ON"
	When user navigates to "Camera Settings" screen from the "Camera Solution Card" screen
      Then user navigates to "Video Quality Settings" screen from the "Camera Settings" screen
     And user should be displayed with the following "DAS Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "Auto" from "DAS Video Quality Settings" screen
     Then "Video Quality" value should be updated to "Auto" on "Video Quality Settings" screen 
     When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "Auto" on "Camera Settings" screen
     When user navigates to "Video Quality Settings" screen from the "Camera Settings" screen
     Then user should be displayed with the following "DAS Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "Low" from "DAS Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "Low" on "Video Quality Settings" screen 
     When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "Low" on "Camera Settings" screen
     When user navigates to "Video Quality Settings" screen from the "Camera Settings" screen
     Then user should be displayed with the following "DAS Video Quality Settings" options: 
      | Settings | 
      | Auto     | 
      | Low      | 
      | High     | 
     When user selects "High" from "DAS Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "High" on "Video Quality Settings" screen 
     When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
     Then "Video Quality" value should be updated to "High" on "Camera Settings" screen
     #login with different mobiles for the status of configured options to verify the settings as user account level
     
      
   @DAS_EditCameraName @P2 @Automatable
    Scenario Outline: As a user I should be able to edit the camera name
    Given user launches and logs in to the Lyric application 
      And user camera is in <State> 
     When user navigates to "Camera Configuration" screen from the "Dashboard" screen
      And user edits "Camera name"
      #verify the all valid naming criterias
     Then user should be displayed with "edited name" 
     Examples:
      |State  |
      |On     |
      |Off    |
      |Offline|
      |Upgrade| 

     
     @DAS_VerifyErrorMessagesCameraSettings @P4 @NotAutomable 
     Scenario: As a user I should be shown with error messages on failure to set any values in camera settings screen
     Given user <Camera> is set to "on" through CHIL
      When user launches and logs in to the Lyric application
       And user navigates to "Camera Settings" screen from the "Dashboard" screen
       #Cloud and camera is not responding for any camera settings set value
      Then user should be displayed with "Error" popup 





