 @CameraSettings
 Feature: C1 Camera Settings 
 As user I should be able to configure camera settings from the app
  
 @CameraSettingsPartialDisabled    @P4        @UIAutomatable
 Scenario Outline: As a user I should be having limited access to configure camera settings when my camera is in offline or in upgrade state 
 Given user camera is in <State> 
 And user launches and logs in to the Lyric application
 When user navigates to "Camera Settings" screen from the "Dashboard" screen
 Then the following "Camera Settings" options should be disabled:
| Options             | 
| Camera Mode         |
| Motion Detection    |
| Sound Detection     |
| Night Vision        | 
| Video Quality       |
| Camera LED          |
| Camera Microphone   | 
 And the following "Camera Settings" options should be enabled:
 | Options             | 
 | Manage Alerts       |
 | Camera Configuration| 
 
 Examples:
 		|State  |
 		|Offline|
 		|Upgrade|
          
     
@CameraSettingsCameraOff      @P3        @UIAutomatable
Scenario: As a user I should be having limited access to configure camera settings when my camera is in off state 
Given user Camera is in Off 
And user launches and logs in to the Lyric application
When user navigates to "Camera Settings" screen from the "Dashboard" screen
Then the following "Camera Settings" options should be disabled:
| Options             | 
| Motion Detection    |
| Sound Detection     |
| Night Vision        | 
| Video Quality       |
| Camera LED          |
| Camera Microphone   | 
And the following "Camera Settings" options should be enabled:
| Options             |
| Camera mode         |
| Manage Alerts       |
| Camera Configuration| 
      
      
@CameraSettingsCameraOn   @P1        @UIAutomatable
Scenario: As a user I should be able to configure camera settings when my camera is in on state 
#Given user Camera is in On
Given user DAS camera is set to "on" through CHIL
And user launches and logs in to the Lyric application
When user navigates to "Camera Settings" screen from the "Dashboard" screen
Then the following "Camera Settings" options should be enabled:
| Options             | 
| Camera mode         |
| Manage Alerts       |
| Motion Detection    |
| Sound Detection     |
| Night Vision        | 
| Video Quality       |
| Camera LED          |
| Camera Microphone   | 
| Camera Configuration|    
 
 
@CameraSettingsCameraModeGeofenceAway      @P2        @NotAutomatable
Scenario: As a user I should be able to set to geofencing mode so that my camera turns on when i am away and off when i am in home automatically without manual intervention 
Given user launches and logs in to the Lyric application
When user navigates to "Camera Settings" screen from the "Dashboard" screen
And user selects camera mode as "Geofencing" 
And user location with status "Geofence Away"
Then the following "Camera Settings" options should be disabled:
| Options             | 
| Motion Detection    |
| Sound Detection     |
| Night Vision        | 
| Video Quality       |
| Camera LED          |
| Camera Microphone   | 
And the following "Camera Settings" options should be enabled:
| Options             |
| Camera mode         |
| Manage Alerts       |
| Camera Configuration|
  
      
@CameraSettingsCameraModeGeofenceHome     @P2        @NotAutomatable
Scenario: As a user I should be able to set to geofencing mode so that my camera turns on when i am away and off when i am in home automatically without manual intervention 
Given user launches and logs in to the Lyric application
When user navigates to "Camera Settings" screen from the "Dashboard" screen
And user selects camera mode as "Geofencing" 
And user location with status "Geofence Home"
Then the following "Camera Settings" options should be enabled:
| Options             | 
| Camera mode         |
| Manage Alerts       |
| Motion Detection    |
| Sound Detection     |
| Night Vision        | 
| Video Quality       |
| Camera LED          |
| Camera Microphone   | 
| Camera Configuration|
      
 
@CameraSettingsManageAlertsDisabled      @P2     @UIAutomatable
Scenario Outline: As a user I should be able to disable alert of camera status, sound event and motion event on my demand to get alerts in app or in email on alerts detection 
Given user launches and logs in to the Lyric application
And user camera is set to <State>
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
#And user disables "Camera Status ON/OFF Alerts"
And user changes the "Camera Status Alerts" to "OFF"
#Then user should not be displayed with "email" option
Then user should not be displayed with "Email Notifications" option
When user navigates back and forth in "Manage Alerts" screen
Then user should not be displayed with "Email Notifications" option
#And user disables "Motion Event Alerts"
When user changes the "Sound Event Alerts" to "OFF"
#Then user should not be displayed with "email" option
Then user should not be displayed with "Email Notifications" option
When user navigates back and forth in "Manage Alerts" screen
Then user should not be displayed with "Email Notifications" option
#When user disables "Sound Event Alerts"
When user changes the "Motion Event Alerts" to "OFF"
#Then user should not be displayed with "email" option
Then user should not be displayed with "Email Notifications" option
When user navigates back and forth in "Manage Alerts" screen
Then user should not be displayed with "Email Notifications" option
#login with different mobiles for the status of configured options to verify the settings as user account level

Examples:
		|State  |
		|On     |
		|Off    |
#		|Offline|
#		|Upgrade|
      
      
@CameraSettingsManageAlertsEnableAndDisable      @P3        @UIAutomatable
Scenario Outline: As a user I should be able to disable alert for email notification of camera status, sound event and motion event on alerts detection 
Given user launches and logs in to the Lyric application
And user camera is in <State> 
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user enables "Camera Status ON/OFF Alerts" 
Then user should be displayed with "email" option
When user changes the "Camera Status ON/OFF email" to "Off"
Then "Camera Status ON/OFF email" value should be updated to "Off" on "Manage Alerts" screen
When user enables "Motion Event Alerts" 
Then user should be displayed with "email" option
When user changes the "Motion Event email" to "Off"
Then "Motion Event email" value should be updated to "Off" on "Manage Alerts" screen
When user enables "Motion Event Alerts" 
Then user should be displayed with "email" option
When user changes the "Motion Event email" to "Off"
Then "Motion Event email" value should be updated to "Off" on "Manage Alerts" screen
#login with different mobiles for the status of configured options to verify the settings as user account level
Examples:
		|State  |
		|On     |
		|Off    |
		|Offline|
		|Upgrade| 
      
      
@CameraSettingsEnableDisableMotionDetection        @P2         @UIAutomatable 
Scenario: As a user I should be able to enable or disable motion detection
#Given user camera is in "on" 
#And motion detection is "enabled" on user camera through CHIL
#When user launches and logs in to the Lyric application
Given user launches and logs in to the Lyric application
And user camera is set to "on"
When user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
And user changes the "Motion Detection" to "Off"
Then "Motion Detection" value should be updated to "Off" on "Motion Detection Settings" screen
And the following "Motion Detection" options should be disabled:
| Options 					| 
| Motion Detection Zone		| 
| Motion Sensitivity   		|
When user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
Then "Motion Detection" value should be updated to "Off" on "Camera Settings" screen
When user navigates to "Motion Detection Settings" screen from the "Camera Settings" screen
Then the following "Motion Detection" options should be disabled:
| Options 					| 
| Motion Detection Zone		| 
| Motion Sensitivity   		|
When user changes the "Motion Detection" to "On"
Then "Motion Detection" value should be updated to "On" on "Motion Detection Settings" screen
And the following "Motion Detection" options should be enabled:
| Options 					| 
| Motion Detection Zone		| 
| Motion Sensitivity   		|
When user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
Then "Motion Detection" value should be updated to "On" on "Camera Settings" screen
When user navigates to "Motion Detection Settings" screen from the "Camera Settings" screen
Then the following "Motion Detection" options should be enabled:
| Options 					| 
| Motion Detection Zone		| 
| Motion Sensitivity   		|
#login with different mobiles for the status of configured options to verify the settings as user account level 
  	
  	
@ChooseMotionDetectionZones      @P3        @NotAutomatable
Scenario: As a user I should be able to select and draw all the 2 zones so that i can set different sensitivity for each zones in the camera frame based on my requirement  
Given user camera is set to "on" through CHIL
And motion detection is "enabled" on user camera through CHIL
When user launches and logs in to the Lyric application 
And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Then user should be displayed with "zone 1" as the default zone
And user verifies that the entire snapshot is selected as the motion detection zone
Then user "should be able" to draw on "zone 1"
When user selects "zone 2" from "Motion Detection Settings" screen
And user "enables" detection in "zone 2"
Then user "should be able" to draw on "zone 2"
#login with different mobiles for the status of configured options to verify the settings as user account level
       
           
@ChooseMotionDetectionZoneError    @P3        @NotAutomatable
Scenario: As a user I should be shown with popup message on failure to load snapshot to select and draw all the 2 zones 
Given user camera is set to "on" through CHIL
And motion detection is "enabled" on user camera through CHIL
When user launches and logs in to the Lyric application 
And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
When user selects <Zone> from "Motion Detection Settings" screen
#Fail to load screenshot
Then user should be shown with "Loading spinner"
And user should be shown with "Retry"
When user selects "Retry"
Then user should be shown with "Unable to take the snapshot"
     
     
#Applicable only to C1
@VerifyCameraMotionSensitivitySettingsC1     @P2        @UIAutomatable
Scenario Outline: As a user I should be able to set motion sensitivity on camera C1 to Off,Low, Normal and High
#Given user camera is set to "on" through CHIL
#And motion detection is "enabled" on user camera through CHIL
#When user launches and logs in to the Lyric application 
#And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Given user launches and logs in to the Lyric application
And user camera is set to "on"
When user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Then user changes the "Motion Detection" to "On"
When user selects <Zone> from "Motion Detection Settings" screen 
Then user should be displayed with the following "Motion Sensitivity Settings" options:
| MotionSensitivityOptionsSettings	| 
| Off      							| 
| Low      							|
| Normal   							| 
| High     							|
When user changes the "Motion Sensitivity" to "Off"
Then "Motion Sensitivity" value should be updated to "Off" on "Motion Detection Settings" screen
#And "no" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "Low"
Then "Motion Sensitivity" value should be updated to "Low" on "Motion Detection Settings" screen
#And "low" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "medium"
Then "Motion Sensitivity" value should be updated to "medium" on "Motion Detection Settings" screen
#And "medium" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "high"
Then "Motion Sensitivity" value should be updated to "high" on "Motion Detection Settings" screen
#And "high" motion detection changes should be reported
Examples:
		| Zone		|
		| Zone 1		|
		| Zone 2		|
      
      
#Applicable only to C2
@VerifyCameraMotionSensitivitySettingsC2        @P2        @UIAutomatable
Scenario Outline: As a user I should be able to set motion sensitivity on camera C2 to Off,Low, Normal and High
#Given user camera is set to "on" through CHIL
#And motion detection is "enabled" on user camera through CHIL
#When user launches and logs in to the Lyric application 
#And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Given user launches and logs in to the Lyric application
And user camera is set to "on"
When user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Then user changes the "Motion Detection" to "On"
When user selects <Zone> from "Motion Detection Settings" screen 
Then user should be displayed with the following "Motion Sensitivity Settings" options:
| MotionSensitivityOptionsSettings	| 
| Off      							| 
| Low      							|
| Normal   							| 
| High     							|
When user changes the "Motion Sensitivity" to "Off"
Then "Motion Sensitivity" value should be updated to "Off" on "Motion Detection Settings" screen
#And "no" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "Low"
Then "Motion Sensitivity" value should be updated to "Low" on "Motion Detection Settings" screen
#And "low" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "medium"
Then "Motion Sensitivity" value should be updated to "medium" on "Motion Detection Settings" screen
#And "medium" motion detection changes should be reported
When user changes the "Motion Sensitivity" to "high"
Then "Motion Sensitivity" value should be updated to "high" on "Motion Detection Settings" screen
#And "high" motion detection changes should be reported
Examples:
		| Zone		|
 		| Zone 1		|
		| Zone 2		|
		| Zone 3		|
		| Zone 4		|
  
    
@VerifyMultipleZonesOverlapError  @P3        @NotAutomatable
Scenario: As a user I want to verify sensitivity area on my zones should not overlap
Given user camera is set to "on" through CHIL
And "Motion Detection" is "enabled" on user camera through CHIL
And "motion sensitivity" is "high" on user camera through CHIL
When user launches and logs in to the Lyric application
And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
And user ovelaps motion detection area across multiple zones
And user navigates to "Camera Settings" screen from the "Motion Detection Settings" screen
Then user should be displayed with "Error-Zones overlap" popup
     
     
@VerifyOutsideZonesWarningMessage      @P3        @NotAutomatable
Scenario: As a user I should be shown with warning message if any area on my zones is not covered for my confirmation
Given user camera is set to "on" through CHIL
And "Motion Detection" is "enabled" on camera through CHIL
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
     

@CameraSettingsEnableDisableSoundDetection     @P2        @UIAutomatable 
Scenario: As a user I should be able to enable or sound detection so that i restrict events on sound detection on demand basics
#Given user camera is in "on" 
#And motion detection is "enabled" on user camera through CHIL
#When user launches and logs in to the Lyric application
Given user launches and logs in to the Lyric application
And user camera is set to "on"
When user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
Then user changes the "Motion Detection" to "On"
And user navigates to "Sound Detection Settings" screen from the "Motion Detection Settings" screen
And user changes the "Sound Detection" to "Off"
Then "Sound Detection" value should be updated to "Off" on "Sound Detection Settings" screen
And the following "Sound Detection" options should be disabled:
| Options				| 
| Sound Sensitivity		|
When user navigates to "Camera Settings" screen from the "Sound Detection Settings" screen
Then "Sound Detection" value should be updated to "Off" on "Camera Settings" screen
When user navigates to "Sound Detection Settings" screen from the "Camera Settings" screen
Then the following "Sound Detection" options should be disabled:
| Options				|
| Sound Sensitivity		|
When user changes the "Sound Detection" to "On"
Then "Sound Detection" value should be updated to "On" on "Sound Detection Settings" screen
And the following "Sound Detection" options should be enabled:
| Options				| 
| Sound Sensitivity		|
When user navigates to "Camera Settings" screen from the "Sound Detection Settings" screen
Then "Sound Detection" value should be updated to "On" on "Camera Settings" screen
When user navigates to "Sound Detection Settings" screen from the "Camera Settings" screen
Then the following "Sound Detection" options should be enabled:
| Options				|
| Sound Sensitivity		|
#login with different mobiles for the status of configured options to verify the settings as user account level 
  	
       
@VerifySoundSensitivitySettings      @P3        @UIAutomatable
Scenario: As a user I should be able to set sound sensitivity on my camera to Low, Normal and High
Given user camera is set to "on" through CHIL
And sound detection is "enabled" on user camera through CHIL
When user launches and logs in to the Lyric application 
And user navigates to "Sound Detection Settings" screen from the "Dashboard" screen
Then user should be displayed with the following "Sound Sensitivity Settings" options:
| Settings | 
| Low      |
| Normal   | 
| High     |
When user changes the "Sound Sensitivity" to "Low"
Then "Sound Sensitivity" value should be updated to "Low" on "Sound Detection Settings" screen
And "low" sound detection changes should be reported
When user changes the "Sound Sensitivity" to "medium"
Then "Sound Sensitivity" value should be updated to "medium" on "Sound Detection Settings" screen
And "medium" sound detection changes should be reported
When user changes the "Sound Sensitivity" to "high"
Then "Sound Sensitivity" value should be updated to "high" on "Sound Detection Settings" screen
And "high" sound detection changes should be reported
#login with different mobiles for the status of configured options to verify the settings as user account level
      
      
 @SoundSettingsEnableCameraMicrophone      @P4        @UIAutomatable
Scenario: As a user I should be able to retain sound settings on turning on microphone from off
Given user camera is set to "on" through CHIL
When user launches and logs in to the Lyric application
And user changes the "Sound Sensitivity" to "Low"
And user navigates to "Camera Settings" screen from the "Dashboard" screen
And user disables the "Camera Microphone"
Then user should be displayed with "Warning" popup
When user "confirms" the "warning" popup
Then user should be displayed with "Camera Microphone" disabled
And user should be displayed with "Sound Detection" grayed out
When user enables the "Camera Microphone"
Then user should be displayed with "Sound Sensitivity" to "Low"
      
        
@VerifyNightVisionSettings        @P2        @UIAutomatable
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
  
  
@VerifyVideoQualitySettings      @P2        @UIAutomatable
Scenario: As a user I should be able to set my Video Quality Settings based on my network connection
Given user launches and logs in to the Lyric application 
When user navigates to "Video Quality Settings" screen from the "Dashboard" screen 
Then user should be displayed with the following "Video Quality Settings" options: 
| Settings | 
| Auto     | 
| Low      | 
| High     | 
When user selects "Auto" from "Video Quality Settings" screen
Then "Video Quality" value should be updated to "Auto" on "Video Quality Settings" screen 
When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
Then "Video Quality" value should be updated to "Auto" on "Camera Settings" screen
When user navigates to "Video Quality Settings" screen from the "Camera Settings" screen
Then user should be displayed with the following "Video Quality Settings" options: 
| Settings | 
| Auto     | 
| Low      | 
| High     | 
When user selects "Low" from "Night Vision Settings" screen 
Then "Video Quality" value should be updated to "Low" on "Video Quality Settings" screen 
When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
Then "Video Quality" value should be updated to "Low" on "Camera Settings" screen
When user navigates to "Video Quality Settings" screen from the "Camera Settings" screen
Then user should be displayed with the following "Video Quality Settings" options: 
| Settings | 
| Auto     | 
| Low      | 
| High     | 
When user selects "High" from "Video Quality Settings" screen 
Then "Video Quality" value should be updated to "High" on "Video Quality Settings" screen 
When user navigates to "Camera Settings" screen from the "Video Quality Settings" screen 
Then "Video Quality" value should be updated to "High" on "Camera Settings" screen
#login with different mobiles for the status of configured options to verify the settings as user account level
     
      
@EnableDisableCameraMicrophone     @P2        @UIAutomatable
Scenario: As a user I should be able to turn off microphone on my demand to not listen the happenings in my premise and also shown with warning message on turning microphone off disables sound alert
Given user camera is set to "on" through CHIL
When user launches and logs in to the Lyric application
And user navigates to "Camera Settings" screen from the "Dashboard" screen
And user disables the "Camera Microphone"
Then user should be displayed with "Warning" popup
When user "cancels" the "warning" popup
Then user should be retained in "Camera Settings" screen
When user "confirms" the "warning" popup
Then user should be displayed with "Camera Microphone" disabled
And user should be displayed with "Sound Detection" grayed out

  
@EnableDisableCameraLED     @P2        @NotAutomatable
Scenario: As a user I should be able to turn off the Camera LED so that the camera on status wont be know to the intruder
Given user launches and logs in to the Lyric application 
When user navigates to "Camera Settings" screen from the "Dashboard" screen
And user disables the "Camera LED" 
Then user "Camera LED" should be turned "off" 
When user camera network is disconnected
Then user "Camera LED" should be turned "RED"
When user camera is in bluetooth mode
Then user "Camera LED" should be turned "Blinking Blue"
When user enables the "Camera LED"
Then user "Camera LED" should be turned "GREEN"
     
     
@CameraConfiguration        @P1        @UIAutomatable
Scenario Outline: As a user I should be able to get the details of camera 
Given user launches and logs in to the Lyric application 
And user camera is in <State> 
When user navigates to "Camera Configuration" screen from the "Dashboard" screen
Then user should be displayed with the following "Camera Configuration" options:
| Details        | 
| Camera Firmware|
| Camera Name    | 
| Model Details  |
#login with different mobiles for the status of configured options to verify the settings as user account level
Examples:
		|State  |
		|On     |
		|Off    |
		|Offline|
		|Upgrade|

      
@EditCameraName     @P2        @UIAutomatable
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
      
      
@DeleteCameraNoBilling      @P2        @UIAutomatable
Scenario Outline: As a user I should be able to delete camera on demand basis
Given user launches and logs in to the Lyric application 
And user camera is in <State> 
When user navigates to "Camera Configuration" screen from the "Dashboard" screen
And user deletes Camera 
Then user should be displayed with "Warning" popup
When user "confirms" the "warning" popup
Then user should be shown with location dashboard without camera 

Examples:
		|State  |
		|On     |
		|Off    |
		|Offline|
		|Upgrade|
      
      
@DeleteCameraWithBilling       @P2        @UIAutomatable
Scenario Outline: As a user I should be able to delete camera on demand basis with caution message about subscription
Given user launches and logs in to the Lyric application 
And user camera is with subscription
And user camera is in <State> 
When user navigates to "Camera Configuration" screen from the "Dashboard" screen
And user deletes Camera 
Then user should be displayed with "Warning" popup
When user "confirms" the "warning" popup 
Then user should be shown with location dashboard without camera 

Examples:
		|State  |
		|On     |
		|Off    |
		|Offline|
		|Upgrade| 
     
     
@VerifyErrorMessagesCameraSettings     @P4        @NotAutomatable
Scenario: As a user I should be shown with error messages on failure to set any values in camera settings screen
Given user <Camera> is set to "on" through CHIL
When user launches and logs in to the Lyric application
And user navigates to "Camera Settings" screen from the "Dashboard" screen
#Cloud and camera is not responding for any camera settings set value
Then user should be displayed with "Error" popup 