@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:
 Given reset relay as precondition
  Given user is set to "Home" mode through CHIL
#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#And user dismisses all alerts and notification through CHIL


 
      
   @Attention_FromCamera @P1 
   Scenario Outline: As a user I should be able to initiate the alarm from camera card on seeing any mischief acts in the premises
   Given user is set to <System> mode through CHIL
     And user DAS camera is set to "ON" through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
    When user "DAS camera" detects "Motion"  
    #Then user receives a "MOTION DETECTED" push notification
    When user selects the "MOTION DETECTED" push notification
    Then user should be displayed with the "Camera Solution Card" screen
    # And user DAS camera is "Live streaming" 
    # check full screen view
    #check status bar of live streaming
    When user selects "confirms attention" from "Camera Solution Card" screen
    Then user should be displayed with the "Alarm" screen  
    #When user receives a "Alarm" push notification
    When user selects "dismiss alarm" from "alarm" screen
	Then user should be displayed with the "Camera Solution Card" screen
	# And user DAS camera is "Live streaming" 
    When user selects "cancels attention" from "Camera Solution Card" screen
    Then user should be displayed with the "Camera Solution Card" screen
    When user navigates to "Security Solution card" screen from "Camera Solution Card" screen
     And user "opens" activity log
     Then verify the following activity log:
      | Elements                 | 
      |Motion Detected|
      |Attention siren sounded by actual user|
      |Alarm|
      |Alarm Dismissed|
     Examples:
     |System|
     |Away  |
   #  |Night |
   #  |Home  |
   #  |OFF   |

@AwayMode_doorsensor_NoAlarmInExitDelay @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
      Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     And user door "opened"  
    Then user should be displayed with the "No Entry delay" screen
    When user door "closed"
    Then user should be displayed with the "No Entry delay" screen
    When timer ends on user device
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     
     @AwayMode_doorsensor_EntryDelayAfterExitDelay_SwitchingToHome
    Scenario: As a user I should be able to switch to home from entry delay screen on my arrival to home without closing the door
       Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     When timer ends on user device
    And user "opens door with app" in background 
      When user selects the "Door Opened" push notification
	 When user selects "Switch to Home" from "Entry delay" screen
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Away mode|
       | Switched to Home by app |
      And user "closes" activity log
      When user door "closed"
      
      @AwayMode_doorsensor_EntryDelayAfterExitDelay_SwitchToNight
      Scenario: As a user I should be able to switch to Night from door open push notification on my arrival to home after closing the door
 Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      # And user mobile screen is locked
      When  user door "opened"
      And user door "closed"
      When user selects the "Door Opened" push notification
	 When user selects "Switch to Night" from "Entry delay" screen
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
   #  Then user should be displayed with "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
	 Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |

@AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
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
  #    And user should be displayed with following:
   #   | Elements                          | 
   #   |  entry delay with door open title |
   #   |  entry delay subtitle             |
   #   |  entry delay live stream          |
   #   |  entry delay alarm in secs text   |
   #   |  entry delay alarm in secs counter|
   #   |  entry delay navigate back button |
     When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with "Lyric app locked" screen
   #  When user enters "Lyric Passcode" 
	 Then user should be displayed with the "Waiting to close" screen
	 When user door "is not closed"
      And user should be displayed with the "Alarm" screen
   #   And user should be displayed with following:
   #   | Elements                    |
   #   |  alarm title 	   			|
   #   |  alarm subtitle			    |
   #   |  alarm live stream 			|
   #   |  alarm navigate back button |
   #   |  call                       |
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      When user "opens" activity log
       Then verify the following activity log:
       | Elements                 | 
       | DOOR SENSOR ALARM AT AWAY MODE|
       | ALARM AT AWAY MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
      When user "closes" activity log
      And user door "closed"
      
      @AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_ClosedDoorNoAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from entry delay screen on my arrival to home after closing the door
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      When  user door "opened"
      And user door "closed"
      When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with the "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | Door Opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |
       
       @AwayMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from away mode's entry delay screen on observing intruder in premises
    Given user sets the entry/exit timer to "60" seconds
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #And user minimizes the application
     #user should be treated as intruder in this scenario
    When  user door "opened"
    # When user selects the "Door Opened" push notification
     When user selects "Attention" from "Entry Delay" screen
     Then user should be displayed with the "Alarm" screen
     Then user receives a "Alarm" email
     When user selects "dismiss alarm" from "alarm" screen
     Then user receives a "Alarm cancelled" email
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | SIREN SOUNDED BY ACTUAL USER|
       | ALARM AT AWAY MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
     And user "closes" activity log
    When user door "closed"
     And user navigates to "Activity history" screen from the "Dashboard" screen
     Then verify the following activity history:
      | Elements                 | 
    #   | SIREN SOUNDED BY ACTUAL USER|
       | ALARM AT AWAY MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
     
     
     @AwayMode_doorsensorOpenAfterExitDelay_AlarmWhenNoActionTaken @--xrayid:ATER-6150
    Scenario: Scenario: As a user I should get alarm from door on breach of intruder
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #And user minimizes the application
     #user should be treated as intruder in this scenario
     When  user door "opened"
     Then user receives a "Door Opened" push notification
     When user door "is not closed"
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
     Then verify the following activity log:
       | Elements                 | 
       | Alarm Dismissed |
       | Switched to Home by app|
     And user "closes" activity log
     
     
      @NightMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm in Night mode if i fails to close the door after entry delay period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
   #  And user force closes the application
   #   And user lyric app screen is locked
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When  user door "opened"
     Then user selects the "Door Opened" push notification
     When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with "Lyric app locked" screen
   #  When user enters "Lyric Passcode" 
	 Then user should be displayed with the "Waiting to close" screen
	 When user door "is not closed"
      And user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR ALARM AT Night MODE|
       |ALARM AT Night MODE|
       | Alarm Dismissed |
       | Switched to Home by app|
      When user "closes" activity log
      And user door "closed"
      
      
      @NightMode_doorsensorOpenAfterExitDelay_Switchtonight_ClosedDoorNoAlarm_FromEntryDelayScreen @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to night when door opened in Night mode after entry delay period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
   #  And user force closes the application
   #   And user lyric app screen is locked
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When  user door "opened"
      Then user should be displayed with the "Entry delay" screen
      And user door "closed"
      When user selects "Switch to Night" from "Entry delay" screen
   #  Then user should be displayed with the "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
       |Door Closed at Night mode|
       |Switched to Night by app|
 
      @NightMode_doorsensor_NoAlarmInExitDelay @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
    And user door "opened"  
   Then user should be displayed with the "No Entry delay" screen
   When user door "closed"
   Then user should be displayed with the "No Entry delay" screen
    When timer ends on user device
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     And user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
       |Door Closed at Night mode|
    And user "closes" activity log
    
    @NightMode_doorsensor_SwitchingToHome @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
  Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
     When timer ends on user device
       When user "opens door with app" in background 
      And user selects the "Door Opened" push notification
	 And user selects "Switch to Home" from "Entry delay" screen
	 And user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
       |Switched to Home by app|
      And user "closes" activity log
      And user door "closed"
      
      @NightMode_doorsensor_SwitchToNight @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
   # And user mobile screen is locked
     Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      Then user should be displayed with the "Entry delay" screen
      And user door "closed"
       And user selects the "Door Opened" push notification
	 And user selects "Switch to Night" from "Entry delay" screen
	 When timer ends on user device
	  When user navigates to "Security Solution card" screen from the "Dashboard" screen
   #  Then user should be displayed with "Mobile locked" screen
   #  When user enters "Mobile Passcode" 
	 Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
        |Door Closed at Night mode|
       |Switched to Night by app|
	  
    @NightMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay @--xrayid:ATER-6150
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
      Then verify the following activity log:
       | Elements                 | 
       |SIREN SOUNDED BY ACTUAL USER|
        |ALARM AT Night MODE|
       |Alarm Dismissed|
       |Switched to Home by app |
     And user "closes" activity log
    When user door "closed"
    
    @NightMode_doorsensorOpenAfterExitDelay_AlarmWhenNoActionTaken @--xrayid:ATER-6150
    Scenario: As a user I should be able to see alarm from Night mode when no action taken
     Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When  user door "opened"
     Then user selects the "Door Opened" push notification
     When user door "is not closed"
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
      Then verify the following activity log:
       | Elements                 | 
       |Alarm Dismissed|
       |Switched to Home by app |
     And user "closes" activity log
     
       
    @AwayMode_windowsensorOpenDuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm in away exit delay when intruder breaches the premises through window 
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And user switches from "Home" to "Away"
     When user window "opened"
   	 Then user should be displayed with the "Alarm" screen
   	 #And user camera is "Live streaming"
     When user selects "dismiss alarm" from "alarm" screen
     Then user status should be set to "Home"
      And user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Window Alarm At Away Mode|
       | ALARM AT AWAY MODE       |
       | Alarm Dismissed          |
       | Switched to Home by app  |
      And user "closes" activity log
      And user window "closed"
      
      
      @AwayMode_windowsensorOpenAfterExitDelay
      Scenario: As a user I should be notified with alarm in away when intruder breaches the premises through window after exit delay
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      And user switches from "Home" to "Away"
      And timer ends on user device
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
     Then verify the following activity log:
      | Elements                 | 
      | ALARM AT AWAY MODE       |
      | Alarm Dismissed          |
      | Switched to Home by app  |
     And user "closes" activity log
     And user window "closed"
     
     @NightMode_windowsensorOpenDuringExitDelay
     Scenario: As a user I should be notified with alarm in night exit delay when intruder breaches the premises through window 
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      And user switches from "Home" to "Night"
     #And user minimizes the application
     When user window "opened"
     When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     And user "opens" activity log
     Then verify the following activity log:
     | Elements                 | 
      | ALARM AT AWAY MODE       |
      | Alarm Dismissed          |
      | Switched to Home by app  |
     And user "closes" activity log
     And user window "closed"
     
     @NightMode_windowsensorOpenAfterExitDelay
     Scenario: As a user I should be notified with alarm in night when intruder breaches the premises through window after exit delay
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And user switches from "Home" to "Night"
      And timer ends on user device
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
      Then verify the following activity log:
     | Elements                 | 
      | ALARM AT Night MODE       |
      | Alarm Dismissed          |
      | Switched to Home by app  |
     And user "closes" activity log 
     And user window "closed"
     
  
       ###########
      
     #Door Tamepered scenarios
     
     @Doortamper_AwayMode @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on door tamper during exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
       #And user minimizes the application
      #And user mobile screen is locked
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     When user door "Tampered"
     #When user selects "Alarm" push notification
     #Then user should be displayed with "Mobile Locked" screen
     #When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
     When user door "Tamper Restored"
      When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT AWAY MODE|
       |DOOR SENSOR ALARM AT AWAY MODE |
       |DOOR SENSOR TAMPER CLEARED AT AWAY MODE|
     And user "closes" activity log
     
     @Doortamper_NightMode @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on door tamper during night exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
       #And user minimizes the application
      #And user mobile screen is locked
     When user door "Tampered"
      #When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	  When user door "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT NIGHT MODE|
       |DOOR SENSOR ALARM AT NIGHT MODE |
       |DOOR SENSOR TAMPER CLEARED AT NIGHT MODE|
      
      
     
       @Windowtamper_AwayMode @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on Window tamper during exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
       #And user minimizes the application
      #And user mobile screen is locked
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user window "Tampered"
     # When user selects the "Alarm" push notification
     # Then user should be displayed with "Mobile Locked" screen
     # When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
   	  When user window "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT AWAY MODE|
       |WINDOW SENSOR ALARM AT AWAY MODE |
       |WINDOW SENSOR TAMPER CLEARED AT AWAY MODE|
       And user "closes" activity log
       
        @Windowtamper_NightMode @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on Window tamper during exit delay 
      And user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      #And user minimizes the application
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     When user window "Tampered"
      #When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	  When user window "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT NIGHT MODE|
       |WINDOW SENSOR ALARM AT NIGHT MODE |
       |WINDOW SENSOR TAMPER CLEARED AT NIGHT MODE|
      
      
      @DoorsensorOpenAfterAwayExitDelay_windowopen @--xrayid:ATER-6147
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
     Then verify the following activity log:
       | Elements                 | 
       |Door opened at Away mode|
       |Window Opened at Away Mode |
       |Window Alarm At Away Mode|
       |ALARM AT AWAY MODE|
       
        @DoorsensorOpenAfterNightExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Night Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
      Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
    When  user door "opened"
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door opened at Night mode|
       |Window Opened at Night Mode |
       |Window Alarm At Night Mode|
       |ALARM AT Night MODE|
    
    
    @DoorsensorOpenInAwayModeExitDelay_windowopen @--xrayid:ATER-6147
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
      Then verify the following activity log:
       | Elements                 | 
       |Door opened at Away mode|
       |Window Opened at Away Mode |
       |Window Alarm At Away Mode|
       |ALARM AT Away MODE|
       
       @DoorsensorOpenInNightModeExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Night Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
    Given user launches and logs in to the Lyric application
    And user clears all push notifications
    Given user is set to "Night" mode through CHIL
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     When user door "opened"
	 When user window "opened"
	 Then user should be displayed with the "Alarm" screen
	  When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door opened at Night mode|
       |Window Opened at Night Mode |
       |Window Alarm At Night Mode|
       |ALARM AT Night MODE| 
      
    
      @Motionsensor_Switchtohome @--xrayid:ATER-6147
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
	 Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Sensor Motion Detected at Away mode|
       |Switched to Home by app |
     When user "closes" activity log
     
     
     @Motionsensor_Switchtonight @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to night from entry delay screen on my arrival to home on my motion sensor detection
      Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
	  When user "sensor" detects the "Motion"
	  Then user should be displayed with the "Entry Delay" screen
     When user selects "Switch to Night" from "Entry delay" screen
     And timer ends on user device
	 Then user status should be set to "Night"
     When user "opens" activity log
	Then verify the following activity log:
       | Elements                 | 
       |Sensor Motion Detected at Away mode|
       |Switched to Night by app |
     When user "closes" activity log
     
     @Motionsensor_Attention @--xrayid:ATER-6147
     Scenario: As a user I should be able to initiate panic alarm from entry delay screen on seeing suspects in my motion sensor detection video
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
	  When user "sensor" detects the "Motion"
     When user selects "Attention" from "Entry delay" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Sensor Motion Detected at Away mode|
       |SIREN SOUNDED BY ACTUAL USER |
       |ALARM AT AWAY MODE|
       |Alarm Dismissed|
     When user "closes" activity log
     
     @Motionsensor_Alarm @--xrayid:ATER-6147
    Scenario: As a user I should be able to alarm when no action taken from entry delay screen on seeing motion detection
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
	  When user "sensor" detects the "Motion"
	  When user selects "no options" from "Entry delay" screen
     #When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
    # When user navigates to "Alarm History" screen from "Alarm" screen
	# Then user receives a "Door Alarm" in alarm history
    # And user receives a "ALARM AT Night MODE" in alarm history
    # When user navigates to "Alarm" screen from "Alarm History" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Sensor Motion Detected at Away mode|
       |ALARM AT AWAY MODE|
       |Alarm Dismissed|
     When user "closes" activity log
	        
        
       @AwayMode_MotionDetectedWindowOpenDoorOpen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with motion detection
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
      Then verify the following activity log:
       | Elements                 | 
       |Sensor Motion Detected at Away mode|
       |Window Opened at Away Mode|
       |Door Opened at Away mode|
       |ALARM AT AWAY MODE|
       |Motion Alarm|
       |Alarm Dismissed|
      And user "closes" activity log
      When user window "closed"
      When user door "closed"
      
      @AwayMode_WindowOpenMotionDetectedDoorOpen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with motion detection
    Given user is set to "Away" mode through CHIL
       And user launches and logs in to the Lyric application
       And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When user window "opened"
      Then user should be displayed with the "Alarm" screen
      When user "sensor" detects the "Motion"
       When  user door "opened"
       And user selects "dismiss alarm" from "alarm" screen
      When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |Window Opened at Away Mode|
       |Sensor Motion Detected at Away mode|
       |Door Opened at Away mode|
      And user "closes" activity log
      
      
     @NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In night mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with door open and motion detection
    Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When user door "opened"
     Then user should be displayed with the "Entry Delay" screen
     When user "sensor" detects the "Motion"
	  When user window "opened"
	  And user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
        |Door Opened at Away mode|
          |Sensor Motion Detected at Away mode|
       |Window Opened at Away Mode|
      And user "closes" activity log 
      
      
	  @DismissAlarm_Navigation @--xrayid:ATER-6218
      Scenario: As an user I should be navigated to screen previous to alarm status on dismissing alarm
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
        And user can dismiss the alarm in following screen:
		|Elements   |
      |Geofence setting  |
      |Geofence Radius|
      |Add Users|
      |Location Details|
      |Edit Account|
      |About the app|
       #  |Add New Device|
     # |Alert|
     # |Comfort Solution Card|
     # |WLD Solution Card|
     # |Alerts & Notification|
    #  |Camera Settings|
    #  |WLD Settings|
     # |Comfort Settings|
    #  |Basestation Settings |
    #  |DAS Camera Settings|
    #  |Vacation|
   #   |Zwave settings |
   #   |Zwave inclusion|
   #   |Zwave exclusion|
    #  |Camera Activity Log|
        	  
        @Alarm_Navigation_Otherscreen @--xrayid:ATER-6147
    Scenario: As an user I should be navigated to any screen in lyric app from alarm screen during panel in alarm state
       Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
        And user can navigate from the alarm in following screen:
		|Elements   |
      |Geofence setting  |
      |Geofence Radius|
      |Add Users|
      |Location Details|
      |Edit Account|
      |About the app|
     # |Add New Device|
     # |Alert|
     # |Comfort Solution Card|
     # |WLD Solution Card|
     # |Alerts & Notification|
    #  |Camera Settings|
    #  |WLD Settings|
     # |Comfort Settings|
    #  |Basestation Settings |
    #  |DAS Camera Settings|
    #  |Vacation|
   #   |Zwave settings |
   #   |Zwave inclusion|
   #   |Zwave exclusion|
    #  |Camera Activity Log|
    |Sensor enrollment|
    |OD event triggered|
       |ID event triggered|
       
       
        @Alarm_Navigation_Otherscreen_Settings @--xrayid:ATER-6147
    Scenario Outline: As an user I should be not allowed to edit settings during panel in alarm state
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
       When user "opens" the window
   	   Then user should be displayed with the "Alarm" screen
       When user navigates to <Expected> screen from "Alarm" screen
      Then user should be displayed with "Grayed settings" screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Expected   |
       |Security Solution Card Settings|
     #  |Camera Settings|
     #  |Geofence radius|Geofence radius|
     #  |Geofence radius|Alerts & Notification|
      # |Alerts & Notification|Security Solution Card|
      # |Security Solution Card|Comfort Solution Card|
      # |Comfort Solution Card|WLD Solution Card|
      # |WLD Solution Crad|My Account|
      # |My Account|App Settings & Info|
     #  |App Settings & Info|Add New Device|
     #  |Add New Device|Camera Settings|
     #  |Camera Settings|WLD Settings|
      # |WLD Settings|Comfort Settings|
      # |Comfort Settings|Basestation Settings |
     #  |Basestation Settings|DAS Camera Settings|
     #  |DAS Camera Settings |Vacation|
     #  |Vacation|Location Details|
      # |Location Details|Manage user|
     #  |Manage user|Zwave settings|
      
   
    @Entrydelay_Navigation_Otherscreen @--xrayid:ATER-6147
    Scenario: As an user I should be navigated to any screen in lyric app from entry delay screen 
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
        When  user door "opened"
        Then user should be displayed with "Entry Delay" screen
        And user can navigate from the entry exit delay in following screen:
       |Elements   |
      |Geofence setting  |
      |Geofence Radius|
      |Add Users|
      |Location Details|
      |Edit Account|
      |About the app|
      |Add New Device|
       
       
       
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
       |my account and select the passcode| Dashboard| 
      
     
      @Alarm_Call @--xrayid:ATER-6147
    Scenario: As an user I should be able to call police from alarm screen on confirming intruder in premises
      Given user is set to "Away" mode through CHIL
       And user launches and logs in to the Lyric application
        When user window "Tampered"
       Then user should be displayed with the "Alarm" screen
       When user selects "Call" from "Alarm" screen
       Then user should be displayed with the "Call" screen
       When user selects "Cancel" from "Call" screen
       Then user should be displayed with the "Alarm" screen
        When user selects "Call" from "Alarm" screen
       When user selects "Call the police" from "Call" screen
      # Then user should be displayed with the “Phone” screen
       
       
    @Motiontampered @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on motion sensor tamper during exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      #And user minimizes the application
      #And user mobile screen is locked
     When user motion sensor "Tampered"
     When user selects the "Alarm" push notification
     #Then user should be displayed with "Mobile Locked" screen
     #When user enters "Mobile Passcode"
   	 Then user should be displayed with the "Alarm" screen
   	 When user motion sensor "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | MOTION SENSOR TAMEPERED AT AWAY MODE|
       | ALARM AT AWAY MODE |
       | Alarm Dismissed |
       | MOTION SENSOR TAMEPER CLEARED AT AWAY MODE|
       |Switched to Home by app|
     And user "closes" activity log
     
    @Alarm_History @--xrayid:ATER-6150
    Scenario: As a user I should get events in alarm history during alarm period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And timer ends on user device
      When user door "tampered"
      And user "sensor" detects the "Motion"
      Then user should be displayed with the "Entry Delay" screen
      When user selects "no options" from "Entry delay" screen
      And user should be displayed with the "Alarm" screen
      When user window "opened" 
      And user "Tampered" the door sensor "after" Entry delay
      And user "Tampered" the window sensor "after" Entry delay
      And user "Tampered" the motion sensor "after" Entry delay      
      When user navigates to "Alarm history" screen from the "Alarm" screen
      Then user should be displayed with the "Alarm history" screen
      And verify the following alarm history:
      |Elements|
      |DOOR OPENED AT NIGHT MODE|
      |ALARM AT NIGHT MODE|
      |SENSOR MOTION DETECTED AT NIGHT MODE|
      |Window Opened At NIGHT mode|
      |Door Tampered At NIGHT mode|
      |Window Tampered At NIGHT mode|
      |Motion Sensor Tampered At NIGHT mode|
      

     @ZwaveOperations_Alarm @--xrayid:ATER-6190
 Scenario Outline: As a user I should get alarm when i am performing any Zwave operation
    Given reset relay as precondition
    And user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "ZWAVE UTILITIES" screen from the "Dashboard" screen
      And user navigates to <Zwave operation> screen from the "ZWAVE UTILITIES" screen
      Then user should be displayed with the <Expected> screen
      And user window "opened"
   	 Then user should be displayed with the "Alarm" screen
   	  When user selects "DISMISS ALARM OVERLAY WITH ZWAVE ACTION IN PROGRESS" from "alarm" screen
    Examples:
    |Zwave operation                             | Expected|
    |ZWAVE DEVICE THROUGH GENERAL INCLUSION      |Activate ZWAVE Device|
    |ZWAVE DEVICE THROUGH GENERAL Exclusion      |Exclusion Mode Active|

   
   @Livestreaming_Alarm_Entrydelay  @NotAutomated @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to pause and resume streaming in alarm screen
      Given user is set to "Away" mode through CHIL
       When user "opens" the door
        And user launches and logs in to the Lyric application
        Then user should be displayed with the "Entry delay" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Entry delay" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Entry delay" screen
       Then user camera is "Live streaming"
   	   Then user should be displayed with the "Alarm" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then user camera is "Live streaming"
       
    @Alarm_Offline @--xrayid:ATER-6190 @SetupRequired
 Scenario: As a user I should be shown with help message after panel offline so that i will be guided to take necessary actions
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the window
   	 Then user should be displayed with the "Alarm" screen
     When user DAS panel went to "offline"
     Then user "should not" "dismisses" the alarm
     Then user should be displayed with "Offline" state
      And user should be displayed with "Help" message
      
      
       @Basestation_Displaced @--xrayid:ATER-6190 @SetupRequired
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
    Given user is set to "Night" mode through CHIL
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
      
      
      
    @AlarmDismissedViaKeyfob @--xrayid:ATER-6150
    Scenario: Scenario: As a user I should be able to dismiss alarm through keyfob
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user window "opened"
     When user selects "dismiss alarm" from keyfob
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | Alarm Dismissed |
       | Switched to Home by Keyfob|
     And user "closes" activity log
     
     
     @ViewIDgeneratedClipsInAlarm @--xrayid:ATER-6150
    Scenario: Scenario: As a user I should be able to view ID generated clips during alarm 
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user window "opened"
     Then view video clips generated on 'Alarm' screen
     And user is set to "Home" mode through CHIL
     
     @MotionViewerAlarm @--xrayid:ATER-6150
    Scenario: Scenario: As a user I should be able to dismiss alarm through keyfob
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user motion viewer triggered
     When user selects "dismiss alarm" from keyfob
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
        | Alarm due to motion viewer |
       | Alarm Dismissed |
       | Switched to Home by Keyfob|
     And user "closes" activity log
     # verify alarm history events
