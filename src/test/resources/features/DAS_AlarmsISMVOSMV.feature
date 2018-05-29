@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:
 Given reset relay as precondition
  Given user is set to "Home" mode through CHIL
#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#And user dismisses all alerts and notification through CHIL


 
      
    @MotionViewer_AwayMode_SwitchingToHomeFromEntryDelay @P1
    Scenario: As a user when motion detected after exit delay I should be able to switch to home from entry delay screen (check clip in activity log)
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     When timer ends on user device
    And user <type> motion viewer detects motion
	 When user selects "Switch to Home" from "Entry delay" screen
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | ISMV motion detected at Away mode|
       | Switched to Home by app |
      And user "closes" activity log
      When user MotionViewer "closed"
      
      @MotionViewer_AwayMode_SwitchingToNightFromPushNotification @P2
      Scenario: As a user when motion detected after exit delay I should be able to switch to night from Push notification  (check clip in activity log)
      Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      # And user mobile screen is locked
      And user "ismv" motion viewer detects motion
      When user selects the "Switch to Night option from ISMV motion detected" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
   #  Then user should be displayed with "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
      When timer ends on user device
	 Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | ISMV motion detected at Away mode|
       | Switched to Night by app |
      
      @MotionViewer_OpenAfterAwayExitDelay_AttentionFromEntryDelay @P2
      Scenario: As a user when ISMV motion is detected in Away mode and attention from entry delay screen is selected on seeing mischeif activity, system should trigger alarm   (check clip in alarm history)
      Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      And user "ismv" motion viewer detects motion
     When user selects "Attention" from "Entry delay" screen
	  Then user should be displayed with the "Alarm" screen
     Then user receives a "Alarm" email
     When user selects "dismiss alarm" from "alarm" screen
     Then user receives a "Alarm cancelled" email
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | ISMV motion detected at Away mode|
       | ISMV motion Alarm|
       | Switched to Home by app |
       
       
       @MotionViewer_OpenAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay @P1
    Scenario: As a user when ISMV motion is detected in Away mode and no action taken from entry delay screen, system should alarm  
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #And user minimizes the application
     #user should be treated as intruder in this scenario
      And user "ismv" motion viewer detects motion
      And timer ends on user device
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
      Then user should be displayed with the "Security Solution card" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
        | ISMV motion detected at Away mode|
         | ISMV motion Alarm|
       | Alarm Dismissed |
       | Switched to Home by app|
     And user "closes" activity log
       
      
     @P2 @MotionViewer_AwayMode_AtDiffExitTimer	
     Scenario: As a user when motion detected after exit delay irrespective of exit timer , I should have clips generated for 30sec (landscape, download)
     Given user sets the entry/exit timer to <timerValue> seconds
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     When  user motion viewer triggered
     Then view video clips generated on 'Alarm history' screen
     When  user motion viewer triggered
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then verify irrespective of exit timer, captured clips play for 30seconds
     
     @MotionViewerAlarm_ClipGenerated_EntryDelay_Alarm  @P1
    Scenario: As a user I should be able to view the clip generated in motion viewer 
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user motion viewer triggered
     And system went into alarm mode
     Then view video clips generated for alarm on Activity history screen
     #only 1 video should be played
     When user selects "dismiss alarm" from alarm screen
     Then view video clips generated for entry delay on Activity log screen
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      Then view video clips generated for entry delay on Activity log screen
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "ISMV" status as "Inactive" on the "Sensor Status"
     # verify alarm history events
     
     @MotionViewerAlarm_Clipgenerated_Offline  @P2
    Scenario: As a user I should be able to view the clip generated in motion viewer during panel offline
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user motion viewer triggered
     Then view video clips generated on 'Alarm history' screen
     #only 1 video should be played
     When user selects "dismiss alarm" from keyfob
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "ISMV" status as "" on the "Sensor Status"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
        | Motion detected IS motion viewer |
        | Alarm due to IS motion viewer |
       | Alarm Dismissed |
       | Switched to Home by Keyfob|
     And user "closes" activity log
     # verify alarm history events
     
     
      @ISMV_OSMV_MotionDetectedInAwayModeAtSameTime @P3 
    Scenario: As a user when motion detected by ISMV and OSMV after exit delay at same time then user should be able to shown ISMV name in entry delay screen
   # Trigger 2 ISMV or OSMV sensor, entry delay screen should show 1st sensor only till entry delay completes
      Given user is set to "Away" mode through CHIL
       And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When user "ISMV sensor1" detects the "Motion"
       When user "ISMV sensor2" detects the "Motion"
      Then user should be displayed with the "Entry delay" screen
      And user should see the "ISMV sensor1" as "Title" on the Entry delay
      Given user is set to "Home" mode through CHIL
      
      
       @MotionDetectedInAwayMode_ByMotionSensorAndMotionViewer_AtSameTime @P2
    Scenario: As a user when motion detected by PIR and MV after exit delay at same time I should be able to switch to night from entry delay screen - waiting screen should not be displayed
    Given user is set to "Away" mode through CHIL
       And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When user "ISMV sensor" detects the "Motion"
       When user "motion sensor" detects the "Motion"
      Then user should be displayed with the "Entry delay" screen
      When user selects "Switch to Night" from "Entry delay" screen
      Then verify no waiting screen is displayed
      
      
       @MotionViewerSensor_TamperDuringNightModeExitDelay @P2
      Scenario: As a user when the MotionViewer is tampered in Night exit delay I should be notified with alarm
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
       #And user minimizes the application
      #And user mobile screen is locked
     When user MotionViewer "Tampered"
      #When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	  When user MotionViewer "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |MotionViewer SENSOR TAMPERED AT NIGHT MODE|
       |MotionViewer SENSOR ALARM AT NIGHT MODE |
       |MotionViewer SENSOR TAMPER CLEARED AT NIGHT MODE|
       
        @MotionViewerSensor_TamperDuringNightModeExitDelay @P2
      Scenario: As a user when the MotionViewer is tampered in Night exit delay I should be notified with alarm
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
       #And user minimizes the application
      #And user mobile screen is locked
     When user MotionViewer "Tampered"
      #When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	  When user MotionViewer "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |MotionViewer SENSOR TAMPERED AT NIGHT MODE|
       |MotionViewer SENSOR ALARM AT NIGHT MODE |
       |MotionViewer SENSOR TAMPER CLEARED AT NIGHT MODE|
       
       
       @AwayMode_MotiondetectedByOSMV_DoorOpened_MotiondetectedByMotionSensor_MotiondetectedByISMV_WindowOpened @P1
       Scenario: As a user when the MotionViewer is tampered in Night exit delay I should be notified with alarm
      Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
      And user clears all push notifications
       
       @AwayMode_MotiondetectedByOSMV_DoorOpened_MotiondetectedByISMV_MotiondetectedByMotionSensor_WindowOpened @P1
       
       @AwayMode_DoorOpened_MotiondetectedByISMV_MotiondetectedByMotionSensor_WindowOpened @P2
       
       @AwayMode_WindowOpened_MotiondetectedByISMV_MotiondetectedByMotionSensor_DoorOpened_MotiondetectedByOSMV @P1
