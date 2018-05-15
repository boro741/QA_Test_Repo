@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:
 Given reset relay as precondition
  Given user is set to "Home" mode through CHIL
#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#And user dismisses all alerts and notification through CHIL


 
      
   @MotionViewerLoginWithEntrydelay @--xrayid:ATER-6147
    Scenario Outline: As an user when my ISMV OSMV motion detected I should be shown with entry delay screen on login
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
       When user <type> motion viewer detects motion
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with the "Entry delay" screen
   	   Examples:
   	   |type|
   	   |IS|
   	   |OS|
      
       @MotionViewer_AwayMode_SwitchingToHomeFromEntryDelay
    Scenario: As a user when motion detected by ISMV sensor after away exit delay I should be able to switch to home from entry delay screen
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
      When user door "closed"
      
      @MotionViewer_AwayMode_SwitchingToNightFromPushNotification
      Scenario: As a user when motion detected by ISMV sensor after away exit delay I should be able to switch to home from entry delay screen
       Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      # And user mobile screen is locked
      And user <type> motion viewer detects motion
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
      
      
      @SensorNameOnEntryDelay_MultipleMotionViewer
    Scenario: Trigger 2 ISMV or OSMV sensor, entry delay screen should show 1st sensor only till entry delay completes
      Given user is set to "Away" mode through CHIL
       And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When user "ISMV sensor1" detects the "Motion"
       When user "ISMV sensor2" detects the "Motion"
      Then user should be displayed with the "Entry delay" screen
      And user should see the "ISMV sensor1" as "Title" on the Entry delay
      Given user is set to "Home" mode through CHIL
      
      
      
     
     @MotionViewerAlarm_Clipgenerated 
    Scenario: Scenario: As a user I should be able to dismiss alarm through keyfob
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
