@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:

#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#Given "ENABLE CAMERA STATUS PUSH NOTIFICATION" as precondition
#Given relay is reset
#And user dismisses all alerts and notification through CHIL


 @AwayMode_windowsensorOpenDuringExitDelay_OpenAfterExitDelay_and_NightMode_windowsensorOpenDuringExitDelay_OpenAfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm when intruder breaches the premises through window 
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And user switches from "Home" to "Away"
     When user window "opened"
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     Then user status should be set to "Home"
      And user "opens" activity log
	 Then user receives a "Window Alarm At Away Mode" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
      And user "closes" activity log
      And user window "closed"
      And user switches from "Home" to "Away"
     # And user minimizes the application
     # And user mobile screen is locked
     When user window "opened"
      When user selects the "Alarm" push notification
     #Then user should be displayed with "Mobile Locked" screen
     #When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     And user "opens" activity log
     Then user receives a "Attention Alarm" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
     And user window "closed"
      And user switches from "Home" to "Night"
     #And user minimizes the application
     When user window "opened"
     When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     And user "opens" activity log
     Then user receives a "Attention Alarm" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
     And user window "closed"
     And user switches from "Home" to "Night"
     # And user minimizes the application
     # And user mobile screen is locked
     When user window "opened"
     When user selects the "Alarm" push notification
     #Then user should be displayed with "Mobile Locked" screen
     #When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     And user "opens" activity log
     Then user receives a "Attention Alarm" activity log
     And user receives a "ALARM AT Night MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log 
     And user window "closed"
      
   @Attention_FromCamera @P1 
   Scenario Outline: As a user I should be able to initiate the alarm from camera card on seeing any mischief acts in the premises
   Given user is set to <System> mode through CHIL
     And user DAS camera is set to "ON" through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
    When user "DAS camera" detects "Motion"  
    #Then user receives a "Motion" push notification
    When user selects the "Motion" push notification
    Then user should be displayed with the "Camera Solution Card" screen
    # And user DAS camera is "Live streaming" 
    When user selects "confirms attention" from "Camera Solution Card" screen
    Then user should be displayed with the "Alarm" screen  
    When user selects "dismiss alarm" from "alarm" screen
	Then user should be displayed with the "Camera Solution Card" screen
    When user selects "cancels attention" from "Camera Solution Card" screen
    Then user should be displayed with the "Camera Solution Card" screen
      Examples:
     |System|
     |Away  | 
     
     @PendingAwayMode_doorsensorNoAlarm @P1
  Scenario Outline: As a user I should be able to initiate the alarm from camera card on seeing any mischief acts in the premises
    When user navigates to "Activity log" screen from "Camera Solution Card" screen
	Then user receives a "Motion Detected" in activity log
     And user receives a "Attention siren sounded by actual user" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Motion Detected" alert
     And user receives a "Attention Alarm" alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     Examples:
     |System|
     |Away  |
   #  |Night |
   #  |Home  |
   #  |OFF   |


  @AwayMode_doorsensor_NoAlarmInExitDelay_EntryDelayAfterExitDelay_SwitchingToHome_SwitchToNight @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     And user door "opened"  
    Then user should be displayed with the "No Entry delay" screen
    When user "closes" the door "in Exit" delay
    Then user should be displayed with the "No Entry delay" screen
    When timer ends on user device
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     And user "opens" activity log
   Then user receives a "Door opened at Away mode" activity log
    And user receives a "Door closed at Away mode" activity log
    And user "closes" activity log
       And user "opens door with app" in background 
      When user selects the "Door Opened" push notification
	 When user selects "Switch to Home" from "Entry delay" screen
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
	 Then user receives a "Door opened at Away mode" activity log
      And user receives a "Switched to Home by app" activity log
      And user "closes" activity log
      When user "closes" the door "after Exit" delay
   # And user mobile screen is locked
      When user switches from "Home" to "Away"
      When timer ends on user device
      When  user door "opened"
      And user "closes" the door "after Exit" delay
      When user selects the "Door Opened" push notification
	 When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
	 Then user status should be set to "Night"
     When user "opens" activity log
	 Then user receives a "Door Opened at Away mode" activity log
      And user receives a "Door Closed at Away mode" activity log
      And user receives a "Switched to Night by app" activity log
      
	 
      
    @AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_ClosedDoorNoAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm in away mode if i fails to close the door after entry delay period
   Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
   #  And user force closes the application
   #   And user lyric app screen is locked
     And timer ends on user device
     And  user door "opened"
     Then user selects the "Door Opened" push notification
      And user should be displayed with following:
      | Elements                          | 
      |  entry delay with door open title |
      |  entry delay subtitle             |
      |  entry delay live stream          |
      |  entry delay alarm in secs text   |
      |  entry delay alarm in secs counter|
      |  entry delay switch to home button|
      |  entry delay switch to night button|
      |  entry delay panic button         |
      |  entry delay navigate back button |
     When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with "Lyric app locked" screen
   #  When user enters "Lyric Passcode" 
	 Then user should be displayed with the "Waiting to close" screen
	 When user "does not close" the door "after Exit" delay
      And user should be displayed with the "Alarm" screen
      And user should be displayed with following:
      | Elements                    |
      |  alarm title 	   			|
      |  alarm subtitle			    |
      |  alarm live stream 			|
      |  alarm navigate back button |
      |  call                       |
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      When user "opens" activity log
	 Then user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
      When user "closes" activity log
      And user "closes" the door "after Exit" delay
      When user switches from "Home" to "Away"
      When timer ends on user device
      When  user door "opened"
      And user "closes" the door "after Exit" delay
      When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with the "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
	 Then user receives a "Door Opened at Away mode" activity log
      And user receives a "Door Closed at Away mode" activity log
      And user receives a "Switched to Night by app" activity log
      
    @AwayMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay_AlarmWhenNoActionTaken @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from away mode's entry delay screen on observing intruder in premises
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #And user minimizes the application
     #user should be treated as intruder in this scenario
    When  user door "opened"
     When user selects the "Door Opened" push notification
     When user selects "Attention" from "Entry Delay" screen
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
	 Then user receives a "Attention Alarm" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
    When user "closes" the door "after Exit" delay
     Given user is set to "Away" mode through CHIL
     When  user door "opened"
     Then user receives a "Door Opened" push notification
     When user "does not close" the door "after Exit" delay
     When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
    # When user navigates to "Alarm History" screen from "Alarm" screen
	# Then user receives a "Door Alarm" in alarm history
    # And user receives a "ALARM AT AWAY MODE" in alarm history
    # When user navigates to "Alarm" screen from "Alarm History" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
     

     @NightMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_ClosedDoorNoAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm in Night mode if i fails to close the door after entry delay period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
   #  And user force closes the application
   #   And user lyric app screen is locked
     When  user door "opened"
     Then user receives a "Door Opened" push notification
     When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with "Lyric app locked" screen
   #  When user enters "Lyric Passcode" 
	 Then user should be displayed with the "Waiting to close" screen
	 When user "does not close" the door "after Exit" delay
      And user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      When user "opens" activity log
	 Then user receives a "DOOR SENSOR ALARM AT Night MODE" activity log
     And user receives a "ALARM AT Night MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
      When user "closes" activity log
      And user "closes" the door "after Exit" delay
#    When user navigates to "Alert" screen from "Activity Log" screen
 #   Then user receives a "Door Alarm " alert
#     And user receives a "Alarm" alert
#     And user receives a "Alarm Dismissed" alert
 #    And user receives a "Switched to Home" alert
      When user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      And user "closes" the door "after Exit" delay
      When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with the "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
	 Then user receives a "Door Opened at Night mode" activity log
      And user receives a "Door Closed at Night mode" activity log
      And user receives a "Switched to Night by app" activity log
   #  Then user navigates to "Alert" screen from "Activity Log" screen
   #   And user receives a "Door Opened" alert
   #   And user receives a "Door Closed" alert
  #    And user receives a "Switched to Night" alert
     
      
      
      @NightMode_doorsensor_NoAlarmInExitDelay_EntryDelayAfterExitDelay_SwitchingToHome_SwitchToNight @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
    And user door "opened"  
   Then user should be displayed with the "No Entry delay" screen
   When user "closes" the door "in Exit" delay
   Then user should be displayed with the "No Entry delay" screen
    When timer ends on user device
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     And user "opens" activity log
   Then user receives a "Door opened at Night mode" activity log
    And user receives a "Door closed at Night mode" activity log
    And user "closes" activity log
       When user "opens door with app" in background 
      And user selects the "Door Opened" push notification
	 And user selects "Switch to Home" from "Entry delay" screen
	 And user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
	 Then user receives a "Door opened at Night mode" activity log
      And user receives a "Switched to Home by app" activity log
      And user "closes" activity log
      And user "closes" the door "after Exit" delay
   # And user mobile screen is locked
      When user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      And user "closes" the door "after Exit" delay
       And user selects the "Door Opened" push notification
	 And user selects "Switch to Night" from "Entry delay" screen
	 When timer ends on user device
	  When user navigates to "Security Solution card" screen from the "Dashboard" screen
   #  Then user should be displayed with "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
	 Then user status should be set to "Night"
     When user "opens" activity log
	 Then user receives a "Door Opened at Night mode" activity log
      And user receives a "Door Closed at Night mode" activity log
      And user receives a "Switched to Night by app" activity log
	  
    @NightMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay_AlarmWhenNoActionTaken @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from Night mode's entry delay screen on observing intruder in premises
     Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #And user minimizes the application
     #user should be treated as intruder in this scenario
    When  user door "opened"
     When user selects the "Door Opened" push notification
     When user selects "Attention" from "Entry Delay" screen
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
	 Then user receives a "Attention Alarm" activity log
     And user receives a "ALARM AT Night MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
    When user "closes" the door "after Exit" delay
    When user switches from "Home" to "Night"
And timer ends on user device
     When  user door "opened"
     Then user receives a "Door Opened" push notification
     When user "does not close" the door "after Exit" delay
     When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
    # When user navigates to "Alarm History" screen from "Alarm" screen
	# Then user receives a "Door Alarm" in alarm history
    # And user receives a "ALARM AT Night MODE" in alarm history
    # When user navigates to "Alarm" screen from "Alarm History" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
     And user "closes" activity log
     
     
     
     
     
      
      @DoorsensorOpenAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Away Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
    Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
    When  user door "opened"
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then user receives a "Door opened at Away mode" activity log
      And user receives a "Window Opened at Away Mode" activity log
      And user receives a "Window Alarm At Away Mode" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log 
      Given user is set to "Night" mode through CHIL
     And user clears all push notifications
     And timer ends on user device
    When  user door "opened"
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then user receives a "Door opened at Night mode" activity log
      And user receives a "Window Opened at Night Mode" activity log
      And user receives a "Window Alarm At Away Mode" activity log
      And user receives a "ALARM AT Night MODE" activity log
      And user receives a "DOOR SENSOR ALARM AT Night MODE" activity log 
    
    
    @DoorsensorOpenInExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Away Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
    Given user launches and logs in to the Lyric application
    And user clears all push notifications
     And user is set to "Away" mode through CHIL
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
    When user door "opened"
    When user selects the "Door Opened" push notification
	 Then user should be displayed with the "Entry delay" screen
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then user receives a "Door opened at Away mode" activity log
      And user receives a "Window Opened at Away Mode" activity log
      And user receives a "Window Alarm At Away Mode" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log 
      Given user is set to "Night" mode through CHIL
     When user door "opened"
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then user receives a "Door opened at Night mode" activity log
      And user receives a "Window Opened at Night Mode" activity log
      And user receives a "Window Alarm At Away Mode" activity log
      And user receives a "ALARM AT Night MODE" activity log
      And user receives a "DOOR SENSOR ALARM AT Night MODE" activity log 
      
    
      @Motionsensor_Switchtohome_Switchtonight_Attention_Alarm @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to home from entry delay screen on my arrival to home on my motion sensor detection
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     # And user minimizes the application
     # And user mobile screen is locked
     When user "sensor" detects the "Motion"
     #When user selects the "Motion Detected" push notification
     #Then user should be displayed with "Mobile Locked" screen
     #When user enters "Mobile Passcode"
     Then user should be displayed with the "Entry Delay" screen
	 When user selects "Switch to Home" from "Entry delay" screen
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
     When user "opens" activity log
	 Then user receives a "Sensor Motion Detected at Away mode" activity log
     And user receives a "Switched to Home by app" activity log
     When user "closes" activity log
      Given user is set to "Away" mode through CHIL
	  When user "sensor" detects the "Motion"
	  Then user should be displayed with the "Entry Delay" screen
     When user selects "Switch to Night" from "Entry delay" screen
     And timer ends on user device
	 Then user status should be set to "Night"
     When user "opens" activity log
	 Then user receives a "Sensor Motion Detected at Away mode" activity log
     And user receives a "Switched to Night by app" activity log
     When user "closes" activity log
     Given user is set to "Away" mode through CHIL
	  When user "sensor" detects the "Motion"
     When user selects "Attention" from "Entry delay" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
	 Then user receives a "Sensor Motion Detected at Away mode" activity log
	 Then user receives a "ATTENTION SIREN SOUNDED BY ACTUAL USER" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     When user "closes" activity log
     Given user is set to "Away" mode through CHIL
	  When user "sensor" detects the "Motion"
	  When user selects "no options" from "Entry delay" screen
     When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
    # When user navigates to "Alarm History" screen from "Alarm" screen
	# Then user receives a "Door Alarm" in alarm history
    # And user receives a "ALARM AT Night MODE" in alarm history
    # When user navigates to "Alarm" screen from "Alarm History" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
	 Then user receives a "Sensor Motion Detected at Away mode" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     When user "closes" activity log
	        
        
       @AwayMode_MotionDetectedWindowOpenDoorOpen_WindowOpenMotionDetectedDoorOpen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with motion detection
   # Given user is set to "Home" mode through CHIL
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      #And user minimizes the application 
     When user "sensor" detects the "Motion"
     #And user selects the "Motion Detected" push notification
	 Then user should be displayed with the "Entry delay" screen
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	 When  user door "opened"
	 And user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
	 Then user receives a "Sensor Motion Detected at Away mode" activity log
      And user receives a "Window Opened at Away Mode" activity log
      And user receives a "Window Alarm At Away Mode" activity log
      Then user receives a "Door Opened at Away mode" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user receives a "Motion Alarm" activity log 
      And user "closes" activity log
      When user window "closed"
      When user door "closed"
      Given user is set to "Away" mode through CHIL
      When user "sensor" detects the "Motion"
      And timer ends on user device
      Then user should be displayed with the "Alarm" screen
       And user selects "dismiss alarm" from "alarm" screen
      When user "opens" activity log
      And user receives a "Sensor Motion Detected at Away mode" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user receives a "Motion Alarm" activity log
      And user "closes" activity log
      
      
     @NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In night mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with door open and motion detection
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When  user door "opened"
     Then user should be displayed with the "Entry Delay" screen
     When user "sensor" detects the "Motion"
	  When user window "opened"
	 Then user should be displayed with the "Alarm" screen
     When user "opens" activity log
     Then user receives a "Door Opened at Away mode" activity log
      And user receives a "Sensor Motion Detected at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm At Away Mode" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user "closes" activity log 
      
      
	  @DismissAlarm_Navigation @--xrayid:ATER-6218
      Scenario Outline: As an user I should be navigated to screen previous to alarm status on dismissing alarm
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
        And user navigates to <Current> screen from <Previous> screen
       When user "opens" the window
   	   Then user should be displayed with the "Alarm" screen
       When user "dismisses" the alarm
       Then user should be displayed with <Current> screen
       Examples:
       |Previous |Current   |
       |Dashborad|Geofence  |
       |Dashboard|Geofence Radius|
       |Dashboard|Alert|
       |Dashboard|Security Solution Card|
       |Dashboard|Camera Solution Card|
       |Dashboard|Camera Activity Log|
       |Dashboard|Comfort Solution Card|
       |Dashboard|WLD Solution Card|
       |Dashboard|Alerts & Notification|
       |Dashboard|My Account|
       |Dashboard|App Settings & Info|
       |Dashboard|Add New Device|
       |Dashboard|Camera Settings|
       |Dashboard|WLD Settings|
       |Dashboard|Comfort Settings|
       |Dashboard|Basestation Settings |
       |Dashboard|DAS Camera Settings|
       |Dashboard|Vacation|
       |Dashboard|Location Details|
       |Dashboard|Manage user    |
       |Dashboard|Zwave settings |
       |Dashboard|Zwave inclusion|
       |Dashboard|Zwave exclusion|
        	  
        @Alarm_Navigation_Otherscreen @--xrayid:ATER-6147
    Scenario Outline: As an user I should be navigated to any screen in lyric app from alarm screen during panel in alarm state
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
       When user "opens" the window
   	   Then user should be displayed with the "Alarm" screen
       #When user navigates to <Current> screen from <Previous> screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous |Current   |
       |Alarm    |Dashboard |
       |Dashboard|Camera Solution Card|
       |Camera Solution Card|Security Solution Card|
       |Geofence radius|Geofence radius|
       |Geofence radius|Alerts & Notification|
       |Alerts & Notification|Security Solution Card|
       |Security Solution Card|Comfort Solution Card|
       |Comfort Solution Card|WLD Solution Card|
       |WLD Solution Crad|My Account|
       |My Account|App Settings & Info|
       |App Settings & Info|Add New Device|
       |Add New Device|Camera Settings|
       |Camera Settings|WLD Settings|
       |WLD Settings|Comfort Settings|
       |Comfort Settings|Basestation Settings |
       |Basestation Settings|DAS Camera Settings|
       |DAS Camera Settings |Vacation|
       |Vacation|Location Details|
       |Location Details|Manage user|
       |Manage user|Zwave settings|
       |Zwave settings|Zwave inclusion|
       |Zwave inclusion|Zwave exclusion|
       |Zwave exclusion|Alarm|
       
       
   
    @Entrydelay_Navigation_Otherscreen @--xrayid:ATER-6147
    Scenario Outline: As an user I should be navigated to any screen in lyric app from entry delay screen 
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
       When  user door "opened"
   	   Then user should be displayed with "Entry Delay" screen
       When user navigates to <Current> screen from <Previous> screen
       Then user should be displayed with the "Alarm" screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous  |Current  |
       |Entry Delay|Dashboard |
       |Dashboard|Camera Solution Card|
       |Camera Solution Card|Entry Delay|
       
       
       
    @Entrydelay_Navigation_Otherscreen_Settings @--xrayid:ATER-6147
    Scenario Outline: As an user I should be allowed to access DAS settings in lyric app in entry delay panel state
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
       When  user door "opened"
   	   Then user should be displayed with "Entry Delay" screen
      # When user navigates to <Current> screen from <Previous> screen
      Then user should be displayed with "Grayed settings" screen
       Then user should be displayed with the "Alarm" screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous |Current  |
       |Entry Delay|Dashboard |
       |Dashboard|DAS Camera Settings|
       |DAS Camera Settings|Basestation Settings|
       |Basestation Settings|Dashboard|     
      
     
     @Alarm_Navigation_Otherscreen_Settings @--xrayid:ATER-6147
    Scenario Outline: As an user I should be not allowed to edit settings during panel in alarm state
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
       When user "opens" the window
   	   Then user should be displayed with the "Alarm" screen
      # When user navigates to <Current> screen from <Previous> screen
      Then user should be displayed with "Grayed settings" screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous |Current   |
       |Alarm    |Dashboard |
       |Dashboard|Camera Solution Card|
       |Camera Solution Card|Security Solution Card|
       |Geofence radius|Geofence radius|
       |Geofence radius|Alerts & Notification|
       |Alerts & Notification|Security Solution Card|
       |Security Solution Card|Comfort Solution Card|
       |Comfort Solution Card|WLD Solution Card|
       |WLD Solution Crad|My Account|
       |My Account|App Settings & Info|
       |App Settings & Info|Add New Device|
       |Add New Device|Camera Settings|
       |Camera Settings|WLD Settings|
       |WLD Settings|Comfort Settings|
       |Comfort Settings|Basestation Settings |
       |Basestation Settings|DAS Camera Settings|
       |DAS Camera Settings |Vacation|
       |Vacation|Location Details|
       |Location Details|Manage user|
       |Manage user|Zwave settings|
       |Zwave settings|Zwave inclusion|
       |Zwave inclusion|Zwave exclusion|
       |Zwave exclusion|Alarm|
       

       @Alarm_Login @--xrayid:ATER-6147
    Scenario Outline: As an user I should be shown with alarm screen on login to lyric app during panel in alarm state
      Given user is set to "Away" mode through CHIL
       When user "opens" the window"in" Exit delay
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with the "Alarm" screen

       
      @Entrydelay_Login @--xrayid:ATER-6147
    Scenario Outline: As an user I should be shown with entry delay screen on login to lyric app during panel in entry delay state
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
       When  user door "opened"
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with "Entry delay" screen
       
       
      @Alarm_Call @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to call police from alarm screen on confirming intruder in premises
      Given user is set to "Away" mode through CHIL
       When user "opens" the window
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with the "Alarm" screen
       When user selects "Call" from "Alarm" screen
       Then user should be displayed with "Call" screen from "Alarm" screen
       
       
      @Alarm_Livestreaming  @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to pause and resume streaming in alarm screen
      Given user is set to "Away" mode through CHIL
       When user "opens" the window
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with the "Alarm" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then user camera is "Live streaming"
       
      @Entrydelay_Livestreaming  @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to pause and resume streaming in entry delay screen
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
       When user "opens" the window
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with the "Alarm" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then user camera is "Live streaming"
       
       
   @Doortamper_AwayMode @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on door tamper during away exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the door "in Exit" delay
   	 Then user should be displayed with the "Alarm" screen
     When user "Tamper Restored" the door
      And user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" activity log
     And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Tampered" alert
     And user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
      @AwayMode_DoortamperAfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on door tamper after exit delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the door "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" activity log
     And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Tampered" alert
     And user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
    @NightMode_DoortamperDuringExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on door tamper during night mode exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "Tampered" the door "in" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" activity log
     And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Tampered" alert
     And user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @NightMode_DoortamperAfterExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on door tamper after night mode exit delay
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the door "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" activity log
     And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Tampered" alert
     And user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
        @AwayMode_WindowtamperDuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on window tamper during away exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user window is "Tampered" 
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" activity log
     And user receives a "Window Alarm At Away Mode" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm At Away Mode " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
      @AwayMode_WindowtamperAfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on window tamper after away mode exit delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the window
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" activity log
     And user receives a "Window Alarm At Away Mode" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm At Away Mode " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
    @NightMode_WindowtamperDuringExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on window tamper during night exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "Tampered" the window
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" activity log
     And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm At Night Mode " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @NightMode_WindowtamperAfterExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on window tamper after exit delay
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the window
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" activity log
     And user receives a "Window Alarm At Night Mode" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm At Night Mode " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
    @AwayMode_MotiontamperDuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on motion sensor tamper during exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the motion sensor "in" Exit delay
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Tampered" activity log
     And user receives a "Motion Alarm" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Motion Tampered" alert
     And user receives a "Motion Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
      @AwayMode_MotiontamperAfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on motion sensor tamper after exit delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the motion sensor "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Tampered" activity log
     And user receives a "Motion Alarm" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Motion Tampered" alert
     And user receives a "Motion Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
     
     
    @Alarm_History @--xrayid:ATER-6150
    Scenario: As a user I should get events in alarm history during alarm period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
      And user "sensor" detects the "Motion"
     Then user should be displayed with "Entry Delay" screen
      And user should be displayed with "Alarm" screen
     When user "opens" the window "after" Entry delay
      And user "Tampered" the door sensor "after" Entry delay
      And user "Tampered" the window sensor "after" Entry delay
      And user "Tampered" the motion sensor "after" Entry delay      
     When user navigates to "Activity log" screen from "Alarm" screen
	 Then user should be displayed with "Alarm history"
      And user receives a "Door Opened" activity log
      And user receives a "DOOR SENSOR ALARM AT AWAY MODE" activity log
      And user receives a "ALARM AT AWAY MODE" activity log
      And user receives a "Motion Detected" activity log
      And user receives a "Window Opened" activity log
      And user receives a "Door Tampered" activity log
      And user receives a "Window Tampered" activity log
      And user receives a "Motion Tampered" activity log
      
      

      @AwayMode_Basestation_Displaced_DuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on basestation displaced during after delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Displaced" the Basestation "in" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Basestation Displaced" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Basestation Displaced" alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
     
     
   @AwayMode_Basestation_Displaced_AfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on basestation displaced after exit delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Displaced" the Basestation "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user "dismisses" the alarm
     Then user status should be set to "Home"
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Basestation Displaced" activity log
     And user receives a "ALARM AT AWAY MODE" activity log
     And user receives a "Alarm Dismissed" activity log
     And user receives a "Switched to Home by app" activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Basestation Displaced" alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
    
     
    @NightMode_Basestation_Displaced_DuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should not get alarm on basestation displaced when panel in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Displaced" the Basestation "in" Exit delay
     Then user should be displayed with "Night" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user "should not" receives a "Basestation Displaced" activity log
      And user receives a "Switched to Night by app" activity log
     When user navigates to "Alert" screen from "Activity Log" screen
     Then user receives a "Switched to Night" alert
      And user "should not" receives a "Alarm Dismissed" alert

          
   @AwayMode_Basestation_Displaced_AfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should not get alarm on basestation displaced when panel in away mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Displaced" the Basestation "after" Exit delay
     Then user should be displayed with "Night" Mode
     When user "Displaced" the Basestation "in" Exit delay
     Then user should be displayed with "Night" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user "should not" receives a "Basestation Displaced" activity log
      And user receives a "Switched to Night by app" activity log
     When user navigates to "Alert" screen from "Activity Log" screen
     Then user receives a "Switched to Night" alert
      And user "should not" receives a "Alarm Dismissed" alert
      
      
   @Alarm_Offline @--xrayid:ATER-6190
 Scenario: As a user I should be shown with help message after panel offline so that i will be guided to take necessary actions
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the window
   	 Then user should be displayed with the "Alarm" screen
     When user DAS panel went to "offline"
     Then user "should not" "dismisses" the alarm
     Then user should be displayed with "Offline" state
      And user should be displayed with "Help" message
      
      
     @ZwaveOperations_Alarm @--xrayid:ATER-6190
 Scenario Outline: As a user I should get alarm when i am performing any Zwave operation
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
	  And user selects <Zwave operation> from "Z-Wave Utilities" screen
      And user "Tampered" the window
   	 Then user should be displayed with the "Alarm" screen
 Examples:|Zwave operation|
          |Replace        | 
          |Fix All        |
          |ALL ON         |
          |ALL OFF        |
          |Controller Factory Reset|
          |Inclusion      |
          |Exclusion      |
          |Learn          |

   
        