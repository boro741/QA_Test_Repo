@CameraSolution
Feature:  
As a user,i should be able to get live streaming of the camera and to get motion alert on motion detection 

   @CameraStatusDashboardDIY @P2
   Scenario Outline: As an user I should be shown with camera in the dashboard after DIY registration
   Given user logs in to Lyric application
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
     And user navigates to "Dashboard" screen from "Camera card Solution"
    Then user should be displayed with camera icon as On in dashboard
     And user should be displayed with "Camera card Solution" screen
     And user should be shown with No Clips available
   
   
   @CameraStatusDashboard @P2
   Scenario Outline: I should be shown with latest clip as thumbnail in the dashboard
   Given user logs in to Lyric application
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
     And user navigates to "Dashboard" screen from "Camera card Solution"
    Then user should be displayed with camera icon as On in dashboard
     And user DAS camera detects "Motion detection"
    Then user receives "Motion detection" push notifications
    When user selects "Motion detection" push notifications
    Then user should be displayed with "Camera card Solution" screen
    When user navigates to "Activity log" screen from "Camera card Solution" screen
    Then user receives a "Motion Detection" in activity log
    When user navigates to "Dashboard" screen from "Activity log"
    Then user should be displayed with latest clip as thumbnail in dashboard
    
    
   @CameraStatusPrivacyRingClosed @P2
   Scenario Outline: I should be shown with privacy ring closed status in the dashboard and camera solution card
   Given user logs in to Lyric application
    When user closes the DAS shutter
    Then user should be displayed with shutter close icon in dashboard
     And user should be displayed with shutter close status in dashboard
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
    Then user should be displayed with "Privacy Ring Closed" in "Camera card Solution" screen
     And user should be not displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user DAS camera is set to "on"
    Then user should not have any impact in shutter close status
     And user should not receive "Motion detected" event
     
     
   @CameraStatusOff @P2
   Scenario Outline: I should be shown with off status in the dashboard and camera solution card
   Given user logs in to Lyric application
    When user DAS camera is set to "Off" through CHIL
    Then user should be displayed with off icon in dashboard
     And user should be displayed with off status in dashboard
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
    Then user should be displayed with "off" in "Camera card Solution" screen
     And user should be displayed with toggle button as "off"
     And user should not be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    
    
   @CameraStatusOffline @P2
   Scenario Outline: I should be shown with offline status in the dashboard and camera solution card
   Given user DAS camera is in <State>
     And user logs in to Lyric application
    Then user should be displayed with <State> in dashboard
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
    Then user should be displayed with <State> in "Camera card Solution" screen
     And user should not be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    And user should be displayed with toggle button as "displayed"
   When user selects "Get Help" 
   Then user should be displayed with "Help" screen
    Examples:
     |State  |
     |Offline|
     |Upgrade|
        
     
   @CameraLivestreamingstatusError @P2
   Scenario Outline: I should be shown with error message on livestreaming status in primary card
   Given user logs in to Lyric application
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
     And user navigates to "Dashboard" screen from "Camera card Solution"
    Then user should be displayed with "Loading live feed" till livestreaming
    When user DAS Camera not streaming with <Status>
    Then user should be displayed with "Unable to stream live feed"
    #The message is "Unable to stream live feed,please try again. If the problem persists,please call customer care"
    When user retry to play 
    Then user should be displayed with "Unable to connect, please try again"
     And user should be displayed with play icon
     And user should be displayed with options disabled:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    Examples:
    |Status                |
    |Camera network is low |
    |Cloud is not available|
    |Mobile network is low |
    
     
  @CameraLivestreamingstatus @P2
   Scenario: I should be shown with livestreaming status in primary card
   Given user logs in to Lyric application
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
    Then user should be displayed with "Loading live feed" till livestreaming
     And user should be displayed with "Live streaming"
     And user should be displayed with "Streaming Progress status"
     And user should be Live streamed for 90 seconds
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user changes his mobile view to "Landscape" from "potrait"
    Then user should be displayed with "Live streaming" in full screen
    When user changes his mobile view to "Landscape" from "potrait"
     And user should be displayed with "Streaming Progress status"
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    #User should be shown with live streaming for 90 seconds and stopped
    Then user should be displayed with "Tap to continue live feed"
     And user should be displayed with play icon
     And user should not be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user restarts "Live streaming"
    Then user should be displayed with "Live streaming"
    When user pauses "Live streaming"
    Then user should be displayed with "Tap to continue live feed" 
    When user click full icon during live streaming
    Then user should be displayed with "Live streaming" in full screen
   

  @Snapshot_WhenCameraOnlineCameraservicesEnabled  @P2
   Scenario: As a user i should be able to take snapshot from live stream when my camera is online
    Given user logs in to Lyric app
     And user enabled the camera services
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
    Then user should be displayed with "Live streaming"
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user selects snap shot 
    Then user should be displayed with "saved snapshot message" in "Camera Solution card" screen
   # And verify "snapshot" is available in gallery
   
    
   @Snapshot_WhenCameraOnlineCameraservicesDisabled @P4
   Scenario: As a user i should not be able to take snapshot from live stream when my camera is online
    Given user logs in to Lyric app
     And user disabled the camera services
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
    Then user should be displayed with "Live streaming"
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user selects snap shot 
    Then user should be displayed with "Need to enable Phone settings" popup
    
    
   @Talk_WhenCameraOnlineMicrophoneservicesEnabled @P2
   Scenario: As a user i should be able to push to talk from mobile to family members in home via DAS panel 
    Given user logs in to Lyric app
     And user enabled the microphone services
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
    Then user should be displayed with "Live streaming"
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user taps on "push to talk button"
  	Then user should be displayed with "Push to talk" popup
    When user "cancels" the "Push to talk" popup 
 	Then user should be displayed with "Live streaming"
 	When user taps on "push to talk button"
 	Then user should be displayed with "Push to talk" popup
    When user press to talk button on press hold
    Then user should be able to send voice message to DAS for announcement
    
   
   @Talk_WhenCameraOnlineMicrophoneservicesDisabled  @P2
   Scenario: As a user i should not be able to push to talk from mobile when microphone service is disabled
    Given user logs in to Lyric app
     And user disabled the microphone services
    When user navigates to "Camera card Solution" screen from "Dashboard" screen
     And user DAS camera is set to "on"
    Then user should be displayed with "Live streaming"
     And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user taps on "push to talk button"
  	Then user should be displayed with "Need to enable Microphone services" popup
    
    
    @Attention_WhenCameraOnline  @P1
   Scenario: As a user i should be able to initiate alarm from camera solution card
    Given user logs in to Lyric app
     When user navigates to "Camera card Solution" screen from "Dashboard" screen
      And user DAS camera is set to "on"
     Then user should be displayed with "Live streaming"
      And user should be displayed with options:
    |Icons    |
    |Snapshot |
    |Talk     |
    |Attention|
    When user taps on "Attention"
  	Then user should be displayed with "Attention" popup
    When user "cancels" the "Attention" popup 
 	Then user should be displayed with "Live streaming"
 	When user taps on "Attention"
 	Then user should be displayed with "Alarm" screen
   

  @MotionDetectionCameraOn  @P1
  Scenario Outline: As a user I should receive motion detection with camera on when panel is in all states 
    Given user DAS panel is set to <Command> through CHIL
      And user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
    Examples:
      |Command|
      |Home   |
      |Off    |
      |Night  |
      |Away   |
      |Alarm  |
      |Enrollment |
      |Entry Delay|
      
      
  @MotionDetectionCameraOn  @P4
  Scenario Outline: As a user I should not struck in camera clip view on various panel state
    Given user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user selects clip
     Then user should be played with clip automatically one after one
     When user DAS panel is set to <Command> through CHIL
     Then user receives <Command> push notifications
     When user selects <Command> push notifications
     Then user should not be struck in camera clip view
    Examples:
      |Command|
      |Home   |
      |Off    |
      |Night  |
      |Away   |
      |Alarm  |
      |Enrollment |
      |Entry Delay|    
      
      
  @MotionDetectionPre-eventClip @P2
  Scenario Outline: As a user I should receive motion detection with camera  
    Given user DAS camera is set to "on" through CHIL
      And motion detection is "enabled" on user DAS panel through CHIL
      And user launches and logs in to the Lyric application
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should play clip with 5 sec pre event 
      
  
  @NightVisionOnMode  @P2
  Scenario: As a user I should be able to livestream camera with night vision mode enabled
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "on" through CHIL
      And user selects "On" from "Night Vision Settings" screen
      And user DAS camera in night time
     When user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with IR specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip in IR specification
     # streaming & clip generated with IR light inensity 
     
     
   @NightVisionOffMode @P2
   Scenario: As a user I should be able to livestream camera with location light intensity
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "On" through CHIL
      And user selects "Off" from "Night Vision Settings" screen
      And user DAS camera in night time
     When user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" based on room light brightness
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip based on room light brightness 
     
  
  @NightVisionAutoMode @P2
  Scenario: As a user I should be able to livestream camera by switching night vision mode automatically based on my room light brightness 
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "On" through CHIL
      And user selects "Auto" from "Night Vision Settings" screen
      And user DAS camera in night time
      # Location light lux level thresold is "" to turn on IR night vision mode
     Then user DAS camera IR vision is turned on automatically
     When user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with IR specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip in IR specification
     # Location light lux level thresold is "" to turn off IR night vision mode
     Then user DAS camera IR vision is turned off automatically
     When user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with room light brightness
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip

  
  @VideoQualityLow @P2
  Scenario Outline: As a user I should be able to livestream camera with low video quality based on the network bandwidth
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "on" through CHIL
      And user selects "Low" from "Video Quality Settings" screen
      And user DAS camera with <Network>
     When user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with low resolution specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip 
     # The clip should not have any impact over clip resolution 
     Examples:
     |Network|
     |<1mbps |
     |<2mbps |
     |<3mbps |
      
   
   @VideoQualityHigh @P2
  Scenario: As a user I should be able to livestream camera with high video quality based on the network bandwidth
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "on" through CHIL
      And user selects "High" from "Video Quality Settings" screen
     When user DAS camera with "Low bandwidth" Network
      And user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Unable to stream live feed" 
     When user DAS camera with "High bandwidth" Network
      And user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with High resolution specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip
      
     
   @VideoQualityAuto  @P2
   Scenario Outline: As a user I should be able to livestream camera with automatically video quality based on the network bandwidth
    Given user launches and logs in to the Lyric application
      And user DAS camera is set to "on" through CHIL
      And user selects "High" from "Video Quality Settings" screen
     When user DAS camera with "Low bandwidth" Network
      And user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with Low resolution specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip 
     # The clip should not have any impact over clip resolution 
     When user DAS camera with "High bandwidth" Network
      And user navigates to "Camera Solution card" screen  
     Then user should be displayed with "Live streaming" with High resolution specification
     When user DAS camera detects "Motion detection"
     Then user receives "Motion detection" push notifications
     When user selects "Motion detection" push notifications
     Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
     Then user receives a "Motion Detection" in activity log
     When user play the "Motion Detection"
     Then user should be played with clip  
 
     
   @MotionDetectionMotionSensitivity @P2
   Scenario Outline: As a user I should get motion detected for various motion sensitivity settings Off,Low, Normal and High
     Given user DAS camera is set to "on" through CHIL
       And motion detection is "enabled" on user DAS panel through CHIL
      When user launches and logs in to the Lyric application 
       And user navigates to "Motion Detection Settings" screen from the "Dashboard" screen
      When user selects <Zone> from the "Motion Detection Settings" screen 
       And user changes the "Motion Sensitivity" to "Off"
      Then "no" motion detection changes should be reported
      When user changes the "Motion Sensitivity" to "Low"
       And user DAS camera detects "Motion detection"
      Then user receives "Motion detection" push notifications
      When user selects "Motion detection" push notifications
      Then user should be displayed with "Camera card Solution" screen
      When user navigates to "Activity log" screen from "Camera card Solution" screen
      Then user receives a "Motion Detection" in activity log
      When user play the "Motion Detection"
      Then user should be played with clip
      #Only detects motion like walking
      When user changes the "Motion Sensitivity" to "normal"
       And user DAS camera detects "Motion detection"
      Then user receives "Motion detection" push notifications
      When user selects "Motion detection" push notifications
      Then user should be displayed with "Camera card Solution" screen
      When user navigates to "Activity log" screen from "Camera card Solution" screen
      Then user receives a "Motion Detection" in activity log
      When user play the "Motion Detection"
      Then user should be played with clip
      #Only detects motion like door opening or closing
      When user changes the "Motion Sensitivity" to "high"
       And user DAS camera detects "Motion detection"
      Then user receives "Motion detection" push notifications
      When user selects "Motion detection" push notifications
      Then user should be displayed with "Camera card Solution" screen
      When user navigates to "Activity log" screen from "Camera card Solution" screen
      Then user receives a "Motion Detection" in activity log
      When user play the "Motion Detection"
      Then user should be played with clip
      #Detects all movement in the zone
     Examples:
      |Zone   |
      |Zone1  |
      |Zone2  |
      |Zone3  |
      |Zone4  |
    
      
    @ActivitylogClipList @P2
	Scenario: As a user I should be displayed with activity log  
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be displayed with "Activity log"
	  And user should be displayed with "all clip count" equals to the "list of all clips"
	  And user should be displayed with "recent clip" at top
	  And user should be displayed with "clip thumbnail" for each clip


	@ActivitylogStatus @P2
	Scenario: As a user I should be displayed with activity log with status 
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	  And user should be displayed with "clip thumbnail" for each clip
	  And user selects clip 
     Then user should be displayed with "Viewed" status
     When user selects clip
     Then user should be displayed with clips in playing state
	 When user selects clip 
      And user download the clip
     Then user should be displayed with "Download progress"
      And user should be displayed with "Saved" status
	
	
   @ActivitylogClipactions  @P2
    Scenario: As a user I should be able to play the clips from activity log 
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	  And user should be displayed with "clip thumbnail"
     Then user should be displayed with "clip details"
      And user should be displayed with "Clip Loading in Progress"
      And user should be played with clip 
      And user clip length with 30 seconds 
     When user changes his mobile view to "Landscape" from "potrait"
     Then user should be displayed with "Clip view" in full screen
     When user changes his mobile view to "potrait" from "Landscape"
     Then user should not be impacted due to orientation
     When user selects the playing clip
     Then user should be displayed with "Pause icon"
      And user should be displayed with "Clip seek bar with progress"
     When user clicks pause icon
     Then user should be displayed with "Paused clip"
      And user should be displayed with "Play icon"
     When user clicks play icon
     Then user should be resumed the clip from paused state
     When user clicks "Full view icon"
     Then user should be displayed with "Clip view" in full screen
     When user clicks "Full view icon"
     Then user should be displayed with "Clip view" in normal screen
     
   @ActivitylogPlayClipusingSeekbar  @P2
    Scenario: As a user I should be able to play the clips from any point of timeline using seekbar 
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	  And user should be played with clips 
      And user should be displayed with "Clip seek bar with progress"
     When user scrolls the seek bar
     Then user should be played with clips from scrolled point
     When user selects the playing clip
     Then user should be displayed with "Paused clip"
      And user should be displayed with "Play icon"
     When user scrolls the seek bar
     Then user should be played with clips from scrolled point 
 
 
   @ActivitylogClipFetchingStatus @P2
    Scenario: As a user I should be shown with status on fetching the clips
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be displayed with "Clip Loading in Progress" in thumbail space
     When user clip is older than 24 hours 
     Then user should be displayed with "Clip expired" in thumbail space
     #Edimax cloud is down
     When user should be displayed with "Failed to load" in thumbail space
   

   @ActivitylogAutoplay @P2 
    Scenario: As a user I should be auto played all the recorded clips one after one
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be played with clips
      And user should be played automatically one after one till last clip
      
    @DeleteBaseStationClipPlaying @P2
    Scenario: As a user I should be auto played all the recorded clips one after one
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be played with clips
      And user should be played automatically one after one till last clip
     When user delete basestation from other mobile during clip playing 
      
      
   @ActivitylogMaximumClip @P3
    Scenario: As a user I should be shown with maximum 50 clips
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be displayed with maximum 50 clips
     When user deletes any clip from list
     Then user should be displayed with updated 50 clips list by including old clip
     
     
    @ActivitylogDeleteClip @P2
    Scenario: As a user I should be able to delete clip from the clip view
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be played with clip 
     When user deletes the clip
     Then user should be displayed with "Delete" popup
     When user "cancels" the delete popup
     Then user should be displayed with "Clip" in current status
     When user "confirms" the delete popup
     Then user should be displayed with "Delete progress"
      And user should be displayed with "Deletion success" popup
     Then user should be displayed without deleted clip
     
    @ActivitylogDownloadClip @P2
    Scenario: As a user I should be able to download my clip in mobile
	Given user logs in to Lyric app
	 When user navigates to "Camera card Solution" screen from "Dashboard" screen
	 Then user should be displayed with "Camera card Solution" screen
     When user navigates to "Activity log" screen from "Camera card Solution" screen
	 Then user should be played with clip
     When user download the clip
     Then user should be displayed with "Download progress"
     When user selects clicks cancel 
     Then user should be displayed with "Download cancel" popup
     When user "cancels" the download cancel popup
     Then user should be displayed with "Download progress"
     Then user should be displayed with download clip in mobile 
     When user download the clip
     Then user should be displayed with "Download progress"
     When user selects clicks cancel 
     Then user should be displayed with "Download cancel" popup
     When user "confirms" the download cancel popup
     Then user should be displayed with clip view
      And user should be cancelled with download request
      
    @NoMotionDetectionCameraOffInHomeMode @P3
    Scenario Outline: As a user I should not get motion detection in Home mode if camera is turned off
    Given user is set to <Command> mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "OFF"
     Then "Camera ON in Home Mode" value should be updated to "OFF" on "Camera Settings" screen
     When user navigates to "Security Solution Card" screen from the "Camera Settings" screen
      And user switches to "Home" mode
      And user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
     Then user camera is "not live streaming"
     When user DAS camera detects "Motion detection"
     Then user should not receives "Motion detection" push notifications
    Examples:
    |Command|
    |Off    |
    |Home   |  
    
    @NoMotionDetectionCameraOffInNightMode @P3
  Scenario Outline: As a user I should not get motion detection in Night mode if camera is turned off
    Given user is set to <Command> mode through CHIL
      And user launches and logs in to the Lyric application 
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
      And user changes the "Camera ON in Home Mode" to "OFF"
     Then "Camera ON in Home Mode" value should be updated to "OFF" on "Camera Settings" screen
     When user navigates to "Security Solution Card" screen from the "Camera Settings" screen
      And user switches to "Night" mode
      And user navigates to "Camera Solution Card" screen from the "Security Solution Card" screen
     Then user camera is "not live streaming"
     When user DAS camera detects "Motion detection"
     Then user should not receives "Motion detection" push notifications
    Examples:
    |Command|
    |Off    |
    |Home   |
    
    
  @CameraSolutionCardCoachMarksDIY @P2
  Scenario: As a user i should be shown with coach marks for first time user 
    Given user launches and logs in to the Lyric application 
     When user performed DAS DIY
     Then user should be displayed with "Dashboard" screen
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
     Then user should be displayed with "Camera Solution Card" screen with coach marks
     
  @CameraSolutionCardCoachMarksAppInstallation @P2
  Scenario: As a user i should be shown with coach marks for first time user 
     When user installed app
      And user launches and logs in to the Lyric application
      #login account with DAS camera
     Then user should be displayed with "Dashboard" screen
     When user navigates to "Camera Settings" screen from the "Dashboard" screen
     Then user should be displayed with "Camera Solution Card" screen with coach marks   
     

     