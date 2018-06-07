@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:
Given reset relay as precondition
Given user is set to "Home" mode through CHIL
#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#And user dismisses all alerts and notification through CHIL


    @Attention_FromCamera @P1 
  Scenario Outline: As a user when I see any mischief acts in the live streaming I should be able to initiate the alarm from camera card 
    Given user is set to <System> mode through CHIL
      And user DAS camera is set to "ON" through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user "DAS camera" detects "Motion"  
     When user selects the "MOTION DETECTED" push notification
     Then user should be displayed with the "Camera Solution Card" screen
      And user camera is "Live streaming"
     When user selects "confirms attention" from "Camera Solution Card" screen
     Then user should be displayed with the "Alarm" screen  
      And view the "Alarm Live streaming and progress" in full screen
     When user selects "dismiss alarm" from "alarm" screen
     Then user should be displayed with the "Camera Solution Card" screen
      And user camera is "Live streaming"
     Then user should see the "Camera" status as "ON" on the "Camera Solution Card"
     When user selects "cancels attention" from "Camera Solution Card" screen
     Then user should be displayed with the "Camera Solution Card" screen
     Then user should see the "Camera" status as "ON" on the "Camera Solution Card"
     When user navigates to "Security Solution card" screen from "Camera Solution Card" screen
      And user "opens" activity log
     Then verify the following activity log:
      | Elements                               | 
      | Motion Detected                        | 
      | Attention siren sounded by actual user | 
      | Alarm                                  | 
      | Alarm Dismissed                        | 
    Examples: 
      | System | 
      | Away   | 
  #   |Night |
  #   |Home  |
  #   |OFF   |

@Doorsensor_NoAlarmInAwayExitDelay @P2
  Scenario: As a user when I open and close the door during away exit delay then i should not get alarm  
  Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     And user door "opened" 
    Then user should be displayed with the "No Entry delay" screen
    When user door "closed"
    Then user should be displayed with the "No Entry delay" screen
    When timer ends on user device
    Then user should be displayed with the "No Entry delay" screen
     Then user should be displayed with the "No Alarm" screen
     Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
 
@Doorsensor_OpenAfterAwayExitDelay_SwitchingToHomeFromPushNotification_withDoorOpen @P1
    Scenario: As a user when I open the door and left open after away exit delay I should be able to switch to home from push notification on my arrival to home and should be notified the door status
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     When timer ends on user device
    And user "opens door with app" in background
      When user selects the "Switch to Home from Door Open" push notification
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
    
@Doorsensor_OpenAfterAwayExitDelay_SwitchingToHomeFromEntryDelay_withDoorClosed @P2
    Scenario: As a user when I open and close the door after away exit delay I should be able to switch to home from entry delay screen on my arrival to home
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     When timer ends on user device
    And user "opens door with app" in background
      When user selects the "Door Opened" push notification
      And user door "closed"
       When user selects "Switch to Home" from "Entry delay" screen
       When user navigates to "Security Solution card" screen from the "Dashboard" screen
       Then user status should be set to "Home"
         Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 |
       | Door opened at Away mode|
       | Switched to Home by app |
      And user "closes" activity log
      When user door "closed"
 
@Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm @P2
      Scenario: As a user when I open the door I should be able to switch to Night from door open push notification on my arrival to home after closing the door
       Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      When user "opens door with app" in background
      And user door "closed"
      When user selects the "Switch to Night from Door open" push notification
      ##### When user navigates to "Security Solution card" screen from the "Dashboard" screen
     When timer ends on user device
       Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 |
       | Door opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |
 
@Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm  @P2
    Scenario: As a user when I open the door in away mode I should be able to switch to Night from Entry Delay but failed to close the door in entry delay waiting should be taken to alarm
   Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     And timer ends on user device
     And  user door "opened"
     Then user selects the "Door Opened" push notification
     When user selects "Switch to Night" from "Entry delay" screen
       Then user should be displayed with the "Waiting to close" screen
       When user door "is not closed"
      And user should be displayed with the "Alarm" screen
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
      
@Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInWaiting_NoAlarm
@P1
    Scenario: As a user when I open the door in away mode I should be able to switch to Night from door open push notification and close the door in entry delay waiting should be shown no alarm
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      And user "opens door with app" in background
      And user door "closed"
      When user selects the "Switch to Night from Door open" push notification
      When timer ends on user device
      When user navigates to "Security Solution card" screen from the "Dashboard" screen
      Then user status should be set to "Night"
      When user "opens" activity log
      Then verify the following activity log:
       | Elements                 |
       | Door Opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |
 
@Doorsensor_OpenAfterAwayExitDelay_AttentionFromEntryDelay
@P2
    Scenario: As a user when the door is opened in away mode I should be able to initiate attention alarm from entry delay screen on observing intruder in premises
     Given user sets the entry/exit timer to "60" seconds
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     And user "opens door with app" in background
     When user selects the "Door Opened" push notification
     When user selects "Attention" from "Entry Delay" screen
     Then user should be displayed with the "Alarm" screen
And user navigates to "Alarm history" screen from the “Alarm” screen
     Then verify the following alarm history:
       | Elements                    |
       | SIREN SOUNDED BY ACTUAL USER|
       | ALARM AT AWAY MODE          |
     Then user receives a "Alarm" email
     When user selects "dismiss alarm" from "alarm" screen
     Then user receives a "Alarm cancelled" email
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "opened" on the "Sensor Status"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 |
       | SIREN SOUNDED BY ACTUAL USER|
       | ALARM AT AWAY MODE |
       | Alarm Dismissed |
       | Switched to Home by app|
     And user "closes" activity log
    When user door "closed"
   
       
@Doorsensor_OpenAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay
@P2
    Scenario: As a user when the door is opened in away mode and no commands issued from entry delay screen then system should go into alarm mode
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     #user should be treated as intruder in this scenario
      When user "opens door with app" in background
     Then user selects the "Door Opened" push notification
     When user door "is not closed"
      And timer ends on user device
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
    #  Then user should be displayed with the "Security Solution card" screen
    When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 |
       | Alarm Dismissed |
       | Switched to Home by app|
     And user "closes" activity log
     
     
     ############ set 2############
   @Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToHomeFromEntryDelay@P1
    Scenario: As a user when when I left my door open during away exit delay , I should be able to switch to home from entry delay screen to avoid the alarm
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
      When user "opens door with app" in background
     Then user selects the "Door Opened" push notification
      And user door "closed"
       When user selects "Switch to Home" from "Entry delay" screen
       When user navigates to "Security Solution card" screen from the "Dashboard" screen
       Then user status should be set to "Home"
         Then user should see the "sensor" status as "no issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "closed" on the "Sensor Status"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 |
       | Door opened at Away mode|
       | Switched to Home by app |
      And user "closes" activity log
      #When user door "closed"
 
@Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm@P2
      Scenario: As a user when I left my door open in away mode exit delay I should get Push notification of door open during entry delay 
      # and in which i can select switch to Night and close the door within delay to avoid alarm
      Given user sets the entry/exit timer to "60" seconds
      Given user launches and logs in to the Lyric application
      And user clears all push notifications
      When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
       When user "opens door with app" in background
      And timer lapse "60" seconds
      When user selects the "Switch to Night from Door Open" push notification
      And user door "closed"
      And timer ends on user device
       Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 |
       | Door opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |
 
@Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm@P2
    Scenario: As a user when I left my door open in away mode exit delay , I should be able to select switch to Night from Entry delay screen
    # But if I didn’t not close the door on time, it should get into  alarm
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
     And  user door "opened"
      And timer lapse "60" seconds
     #verify the entry delay status on dashboard
     When user selects "Switch to Night" from "Entry delay" screen
       Then user should be displayed with the "Waiting to close" screen
       When user door "is not closed"
      And user should be displayed with the "Alarm" screen
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
 
@Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm @P2
    Scenario: As a user when I left my door open in away mode exit delay and left door open , I should be able to get Push notification of door open and on clicking it should take to Entry delay 
    #where i can select switch to Night and close the door in entry delay waiting on my arrival to home with no alarm
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      #And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When  user door "opened"
      And timer ends on user device
       And user door "closed"
      When user selects "Switch to Night" from "Entry delay" screen
      When timer ends on user device
      When user navigates to "Security Solution card" screen from the "Dashboard" screen
      Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 |
       | Door Opened at Away mode|
       | Door Closed at Away mode |
       | Switched to Night by app |
 
    @Doorsensor_LeftOpenDuringAwayExitDelay_AttentionFromEntryDelay @P1
    Scenario: As a user when the door is opened in exit delay of away mode and failed to close door, then intruder entered, I should be able to initiate attention alarm from entry delay screen 
    #on observing intruder in premises
     Given user sets the entry/exit timer to "60" seconds
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When user door "opened"
     When user navigates to "Dashboard" screen from the "Security Solution card" screen
     Then user should see the "Security" status as "Entry Delay" on the "Dashboard"
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     When user selects "Attention" from "Entry Delay" screen
     Then user should be displayed with the "Alarm" screen
     And user navigates to "alarm history" screen from the "alarm" screen
     Then verify the following alarm history:
       | Elements                 |
       | ALARM AT AWAY MODE |
     And user navigates to "alarm" screen from the "alarm history" screen
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
 
@Doorsensor_LeftOpenDuringAwayExitDelay_AlarmWhenNoActionOnEntryDelay@P1
    Scenario: As a user when the door is opened in away mode exit delay and forgot to close in time then user should receive Entry delay and if user does not perform any action
    # from entry delay screen then system should go into alarm mode
     Given user launches and logs in to the Lyric application
     And user clears all push notifications
     And user is set to "Away" mode through CHIL
     When user "opens door with app" in background
     And timer ends on user device
     When user door "is not closed"
     When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     
     
     
     @Doorsensor_NoAlarmInNightExitDelay @P1
  Scenario: As a user when I open and close the door during Night exit delay then i should not get alarm 
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
     
     @Doorsensor_OpenAfterNightExitDelay_SwitchingToHomeFromPushNotification_withDoorOpen @P1
    Scenario: As a user when I open the door and left open after Night exit delay I should be able to switch to home from push notification on my arrival to home and should be notified the door status
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
     When timer ends on user device
    And user "opens door with app" in background 
      When user selects the "Switch to Home from Door Open" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Night mode|
       | Switched to Home by app |
      And user "closes" activity log
      When user door "closed"
      
      @Doorsensor_OpenAfterNightExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm @P1
      Scenario: As a user when I open the door I should be able to switch to Night from door open push notification on my arrival to home after closing the door
       Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Night"
      When timer ends on user device
      When user "opens door with app" in background
      And user door "closed"
      When user selects the "Switch to Night option from Door opened" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
     When timer ends on user device
	 Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Night mode|
       | Door Closed at Night mode |
       | Switched to Night by app |
      
      @Doorsensor_OpenAfterNightExitDelay_SwitchingToHomeFromEntryDelay_withDoorClosed @P1
  Scenario:As a user when I open and close the door after Night exit delay I should be able to switch to home from entry delay screen on my arrival to home
  Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
     When timer ends on user device
       When user "opens door with app" in background 
      When user selects the "Switch to Home from Door Open" push notification
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
      
      
       @Doorsensor_OpenAfterNightExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm @P1
    Scenario: As a user when I open in Night mode I should be able to switch to Night from door open push notification and close the door in entry delay waiting on my arrival to home with no alarm
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
      When  user door "opened"
      Then user should be displayed with the "Entry delay" screen
      And user door "closed"
      When user selects "Switch to Night" from "Entry delay" screen
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
       |Door Closed at Night mode|
       |Switched to Night by app|
       
     @P1 @Doorsensor_OpenAfterNightExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm 
    Scenario: As a user when I open the door in Night mode I should be able to switch to Night from Entry Delay but failed to close the door in entry delay waiting should be taken to alarm
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When  user door "opened"
     Then user selects the "Door Opened" push notification
     When user selects "Switch to Night" from "Entry delay" screen
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
      
      
     @Doorsensor_OpenAfterNightExitDelay_AttentionFromEntryDelay @P1
    Scenario: As a user when the door is opened in Night mode I should be able to initiate attention alarm from entry delay screen on observing intruder in premises
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
 
   @Doorsensor_OpenAfterNightExitDelay_AlarmWhenNoActionOnEntryDelay @P2
    Scenario: As a user when the door is opened in Night mode and no commands issued from entry delay screen then system should go into alarm mode
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
     
      @Doorsensor_OpenAfterNightExitDelay_SwitchingToNightFromPushNotification_DoorClosedInWaiting_NoAlarm @P1
  Scenario: As a user when I open the door in Night mode I should be able to switch to Night from door open push notification and I closes the door within entry delay
     Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      Then user should be displayed with the "Entry delay" screen
      And user door "closed"
       When user selects the "Switch to Night from Door Open" push notification
	 When timer ends on user device
	  When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Door Opened at Night mode|
        |Door Closed at Night mode|
       |Switched to Night by app|
       
       
       #DURING NIGHT MODE
       
        @Doorsensor_OpenDuringNightExitDelay_SwitchingToHomeFromPushNotification_withDoorOpen @P1
    Scenario: As a user when I open the door during night exit delay , I should be able to switch to home from entry delay screen to avoid the alarm
    Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
    And user "opens door with app" in background 
      When user selects the "Switch to Home from Door Open" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Home"
	  Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "door" status as "open" on the "Sensor Status"
     And user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Night mode|
       | Switched to Home by app |
      And user "closes" activity log
      When user door "closed"
      
      
      @Doorsensor_OpenDuringNightExitDelay_SwitchingToHomeFromEntryDelay_withDoorClosed @P1
  Scenario:As a user when I open and close the door During Night exit delay I should be able to switch to home from entry delay screen on my arrival to home
  Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
     When timer ends on user device
       When user "opens door with app" in background 
      When user selects the "Switch to Home from Door Open" push notification
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
      
      @Doorsensor_LeftOpenDuringNightExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm @P2
      Scenario: As a user when I left my door open in Night mode exit delay , I should get Push notification of door open during entry delay and in which i can select switch to Night and close the door within  delay to avoid alarm
      Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      And user door "closed"
      When user selects the "Switch to Night from Door Open" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
	 Then user status should be set to "Night"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       | Door opened at Night mode|
       | Door Closed at Night mode |
       | Switched to Night by app |

    @Doorsensor_LeftOpenDuringNightExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm @P2
    Scenario: As a user when I left my door open in Night mode exit delay , I should be able to select switch to Night from Entry delay screen But if I didn’t not close the door on time, it should get into  alarm
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Night"
     And timer ends on user device
     And  user door "opened"
     #verify the entry delay status on dashboard
     Then user selects the "Door Opened" push notification
     When user selects "Switch to Night" from "Entry delay" screen
	 Then user should be displayed with the "Waiting to close" screen
	 When user door "is not closed"
      And user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
      When user "opens" activity log
       Then verify the following activity log:
       | Elements                 | 
       | DOOR SENSOR ALARM AT Night MODE|
       | ALARM AT Night MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
      When user "closes" activity log
      And user door "closed"
      
      @Doorsensor_LeftOpenDuringNightExitDelay_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm @P2
    Scenario: As a user when I left my door open in Night mode exit delay and left door open , I should be able to get Push notification of door open and on clicking it should take to Entry delay where i can select switch to Night and close the door in entry delay waiting on my arrival to home with no alarm 
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Night"
      When timer ends on user device
      When  user door "opened"
      And user door "closed"
      When user selects "Switch to Night" from "Entry delay" screen
   When timer ends on user device
	Then user status should be set to "Night"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | Door Opened at Night mode|
       | Door Closed at Night mode |
       | Switched to Night by app |
       
       @Doorsensor_LeftOpenDuringNightExitDelay_AttentionFromEntryDelay @P1
    Scenario: As a user when the door is opened in exit delay of Night mode and failed to close door, then intruder entered, I should be able to initiate attention alarm from entry delay screen on observing intruder in premises
    Given user sets the entry/exit timer to "60" seconds
     Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
    When  user door "opened"
     When user navigates to "Dashboard" screen from the "Security Solution card" screen
     Then user should see the "Security" status as "Entry Delay" on the "Dashboard"
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
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
       | ALARM AT Night MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
     And user "closes" activity log
    When user door "closed"
     And user navigates to "Activity history" screen from the "Dashboard" screen
     Then verify the following activity history:
      | Elements                 | 
       | SIREN SOUNDED BY ACTUAL USER|
       | ALARM AT Night MODE |
       | Alarm Dismissed |
       |Switched to Home by app|
     
     
     @Doorsensor_LeftOpenDuringNightExitDelay_AlarmWhenNoActionOnEntryDelay @P1
    Scenario: As a user when the door is opened in Night mode exit delay and forgot to close in time then user should receive Entry delay and if user does not perform any action from entry delay screen then system should go into alarm mode
     Given user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When  user door "opened"
     Then user receives a "Door Opened" push notification
     When user door "is not closed"
     When user selects the "Alarm" push notification
     Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to "Home"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       | Alarm Dismissed |
       | Switched to Home by app|
     And user "closes" activity log
       
       #END OF DURING NIGHT MODE
	  
    @WindowSensor_OpenDuringAwayModeExitDelay @P2
      Scenario: As a user when the window is opened in Away exit delay I should be notified with alarm 
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And user switches from "Home" to "Away"
     When user window "opened"
   	 Then user should be displayed with the "Alarm" screen
   	 #And user camera is "Live streaming"
   	 #view in full screen
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
      
      
      @WindowSensor_OpenAfterAwayModeExitDelay @P1
      Scenario: As a user when intruder breaches the premises through window after away exit delay I should be notified with alarm
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      And user switches from "Home" to "Away"
      And timer ends on user device
       And user "opens window with app" in background
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
     
     @WindowSensor_OpenDuringNightModeExitDelay  @P2
     Scenario: As a user when intruder breaches the premises through window during night mode exit delay I should be notified with alarm 
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      And user switches from "Home" to "Night"
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
     
     @WindowSensor_OpenAfterNightModeExitDelay @P1
     Scenario: As a user when intruder breaches the premises through window after night mode exit delay I should be notified with alarm
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And user switches from "Home" to "Night"
      And timer ends on user device
     When user window "opened"
     When user selects the "Alarm" push notification
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
     
     @DoorSensor_TamperDuringAwayModeExitDelay @P1
      Scenario: As a user when the DOOR is tampered in Away exit delay I should be notified with alarm 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user door "Tampered"
   	 Then user should be displayed with the "Alarm" screen
      When user selects "dismiss alarm" from "alarm" screen
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
      When user door "Tamper Restored"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT AWAY MODE|
       |DOOR SENSOR ALARM AT AWAY MODE |
       |DOOR SENSOR TAMPER CLEARED AT AWAY MODE|
     And user "closes" activity log
     
     
     @DoorSensor_TamperDuringNightModeExitDelay @P2
      Scenario: As a user when the door is tampered in Night exit delay I should be notified with alarm
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
      When user "door tampered with app" in background
      When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	  When user selects "dismiss alarm" from "alarm" screen
   	 And user navigates to "Security Solution card" screen from the "Dashboard" screen
   	 When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
   	  When user door "Tamper Restored"
    When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT NIGHT MODE|
       |DOOR SENSOR ALARM AT NIGHT MODE |
       |DOOR SENSOR TAMPER CLEARED AT NIGHT MODE|
      
       @DoorSensor_TamperAfterAwayModeExitDelay @P1
      Scenario: As a user when the DOOR is tampered After Away exit delay I should be notified with alarm 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When timer ends on user device
     When user "door tampered with app" in background
      When user selects the "Alarm" push notification
   	 Then user should be displayed with the "Alarm" screen
   	 When user selects "dismiss alarm" from "alarm" screen
   	  And user navigates to "Security Solution card" screen from the "Dashboard" screen
   	  When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     When user door "Tamper Restored"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT AWAY MODE|
       |DOOR SENSOR ALARM AT AWAY MODE |
       |DOOR SENSOR TAMPER CLEARED AT AWAY MODE|
     And user "closes" activity log
     
     
     @DoorSensor_TamperAfterNightModeExitDelay @P2
      Scenario: As a user when the door is tampered after Night exit delay I should be notified with alarm
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
     And user launches and logs in to the Lyric application
     When user door "Tampered"
   	 Then user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
       When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
       When user door "Tamper Restored"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT NIGHT MODE|
       |DOOR SENSOR ALARM AT NIGHT MODE |
       |DOOR SENSOR TAMPER CLEARED AT NIGHT MODE|
      
       @WindowSensor_TamperDuringAwayModeExitDelay @P2
      Scenario: As a user when the window is tampered in Away exit delay I should be notified with alarm 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
      When user window "Tampered"
   	 Then user should be displayed with the "Alarm" screen
   	 # When user navigates to "Dashboard" screen from "Dashboard Alarm" screen
   	 #Then user should see the "Security" status as "attention" on the "Dashboard"
   	 When user selects "dismiss alarm" from "alarm" screen
   	  And user navigates to "Security Solution card" screen from the "Dashboard" screen
   	   When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
      When user window "Tamper Restored"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT AWAY MODE|
       |WINDOW SENSOR ALARM AT AWAY MODE |
       |WINDOW SENSOR TAMPER CLEARED AT AWAY MODE|
       And user "closes" activity log
       
        @WindowSensor_TamperDuringNightModeExitDelay @P1
      Scenario: As a user when the window is tampered in Night exit delay I should be notified with alarm 
      Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      When user "window tampered with app" in background
      When user selects the "Alarm" push notification
   	  Then user should be displayed with the "Alarm" screen
      When user selects "dismiss alarm" from "alarm" screen
      When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
      When user window "Tamper Restored"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT NIGHT MODE|
       |WINDOW SENSOR ALARM AT NIGHT MODE |
       |WINDOW SENSOR TAMPER CLEARED AT NIGHT MODE|
       
      @WindowSensor_TamperAfterAwayModeExitDelay @P1
      Scenario: As a user when the window is tampered after Away exit delay I should be notified with alarm 
     Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
       When timer ends on user device
      When user "window tampered with app" in background
      When user selects the "Alarm" push notification
   	  Then user should be displayed with the "Alarm" screen
      When user selects "dismiss alarm" from "alarm" screen
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
      When user window "Tamper Restored"
     When user "opens" activity log
      Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT AWAY MODE|
       |WINDOW SENSOR ALARM AT AWAY MODE |
       |WINDOW SENSOR TAMPER CLEARED AT AWAY MODE|
       And user "closes" activity log
       
        @WindowSensor_TamperAfterNightModeExitDelay @P2
      Scenario: As a user when the window is tampered after Night exit delay I should be notified with alarm 
      Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
        And user launches and logs in to the Lyric application
        When timer ends on user device
      When user window "Tampered"
   	 Then user should be displayed with the "Alarm" screen
   	 When user selects "dismiss alarm" from "alarm" screen
   	  And user navigates to "Security Solution card" screen from the "Dashboard" screen
   	   When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
      When user window "Tamper Restored"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |WINDOW SENSOR TAMPERED AT NIGHT MODE|
       |WINDOW SENSOR ALARM AT NIGHT MODE |
       |WINDOW SENSOR TAMPER CLEARED AT NIGHT MODE|
       And user "closes" activity log
      
      @MotionSensor_TamperDuringAwayModeExitDelay @P1
      Scenario: As a user when the motion sensor is tampered in Away exit delay I should be notified with alarm 
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
     Then user should see the "sensor" status as "issue" on the "Security Solution Card"
     When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
     Then user should see the "motion sensor" status as "cover tampered" on the "Sensor Status"
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |DOOR SENSOR TAMPERED AT AWAY MODE|
       |DOOR SENSOR ALARM AT AWAY MODE |
       |DOOR SENSOR TAMPER CLEARED AT AWAY MODE|
     And user "closes" activity log
     
     @MotionSensor_TamperAfterAwayModeExitDelay @P2
      Scenario: As a user when the motion sensor is tampered after Away exit delay I should be notified with alarm 
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
     
      @Motionsensor_MotionDetectedAfterAwayExitDelay_SwitchingToHomeFromEntryDelay @P1
    Scenario: As a user when motion is detected in Away mode and switch to home is given by user from entry delay screen, system should go to home mode 
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
     
     
     @Motionsensor_OpenAfterAwayExitDelay_SwitchingToNightFromEntryDelay @P2
    Scenario: As a user when motion is detected in Away mode and switch to night is given by user from entry delay screen, system should go to night mode 
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
       #verify the icon 
       |Switched to Night by app |
     When user "closes" activity log
     
     @Motionsensor_OpenAfterAwayExitDelay_AttentionFromEntryDelay @--xrayid:ATER-6147
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
       #verify the icon
       |Alarm Dismissed|
     When user "closes" activity log
     
     @Motionsensor_MotionDetectedAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay @P1
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
	        
     @Motionsensor_MotionDetectedPushNotification_SwitchingToHomeFromEntryDelay @P1
     Scenario: As a user when motion is detected in Away mode , user selects push notification and switch to home from entry delay screen, system should go to home mode 
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     And timer ends on user device
     When user "sensor" detects the "Motion"
     When user selects the "Switch to home from Motion Detected" push notification
     Then user should be displayed with the "Entry Delay" screen
	 When user selects "Switch to Home" from "Entry delay" screen
	 Then user status should be set to "Home"
	 
	 @MotionSensor_TamperAfterAwayModeExitDelay @P1
      Scenario: As a user when the Motion is tampered after Away exit delay I should be notified with alarm 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user Motion "Tampered"
   	 Then user should be displayed with the "Alarm" screen
   	  When user navigates to "Dashboard" screen from "Security Solution Card Alarm" screen
      Then user should see the "Security" status as "attention" on the "Dashboard"
   	  When user Motion "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Motion SENSOR TAMPERED AT AWAY MODE|
       |Motion SENSOR ALARM AT AWAY MODE |
       |Motion SENSOR TAMPER CLEARED AT AWAY MODE|
       And user "closes" activity log
       
       @MotionSensor_TamperDuringAwayModeExitDelay @P2
      Scenario: As a user when the Motion is tampered in Away exit delay I should be notified with alarm 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user Motion "Tampered"
   	 Then user should be displayed with the "Alarm" screen
   	  When user navigates to "Dashboard" screen from "Security Solution Card Alarm" screen
      Then user should see the "Security" status as "attention" on the "Dashboard"
   	  When user Motion "Tamper Restored"
     When user selects "dismiss alarm" from "alarm" screen
     When user "opens" activity log
     Then verify the following activity log:
       | Elements                 | 
       |Motion SENSOR TAMPERED AT AWAY MODE|
       |Motion SENSOR ALARM AT AWAY MODE |
       |Motion SENSOR TAMPER CLEARED AT AWAY MODE|
       And user "closes" activity log
    
     @DoorsensorOpenAfterAwayExitDelay_windowopen @P2
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
       
       @DoorsensorOpenInAwayModeExitDelay_windowopen @P2
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
       
        @DoorsensorOpenAfterNightExitDelay_windowopen @P2
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
    
       @DoorsensorOpenInNightModeExitDelay_windowopen @P3
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
      
       @AwayMode_MotionDetectedWindowOpenDoorOpen @P1
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
      
      @AwayMode_WindowOpenMotionDetectedDoorOpen @P2
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
      
      
     @NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen @P1
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
       
       
        @Alarm_Navigation_Otherscreen_Settings @P2
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
      
   
    @Entrydelay_Navigation_Otherscreen @P2
    Scenario: As an user I should not be navigated to any screen in lyric app from entry delay screen  
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
       
       
       
      @Alarm_Call @P1
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
       
       
     @AlarmOnlogin @P3
    Scenario: As an user I should be shown with alarm screen on login to account when panel in Alarm state
     Given user is set to "Away" mode through CHIL
       When user window "Tampered"
       And user launches and logs in to the Lyric application
       Then user should be displayed with the "Alarm" screen
      # Repeat for door motion ismviewer osmviewer
      
       @EntrydelayOnlogin @P3
    Scenario: As an user I should be shown with entry delay screen on login to account when panel in exntry delay state
    Given user is set to "Away" mode through CHIL
       When user window "Tampered"
       And user launches and logs in to the Lyric application
       Then user should be displayed with the "entry delay" screen
        
     
    @Alarm_History @P1
    Scenario: As a user I should get events in alarm history during alarm period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And timer ends on user device
      When user door "opened"
      And user "sensor" detects the "Motion"
      Then user should be displayed with the "Entry Delay" screen
      When user selects "no options" from "Entry delay" screen
      And user should be displayed with the "Alarm" screen
      When user window "opened" 
      And user door "Tampered"
      And user window "Tampered"
      And user motion sensor "Tampered"     
      When user navigates to "Alarm history" screen from the "Alarm" screen
      And verify the following alarm history:
      |Elements|
      |DOOR OPENED AT NIGHT MODE|
      |SENSOR MOTION DETECTED AT NIGHT MODE|
      |ALARM AT NIGHT MODE|
      |Window Opened At NIGHT mode|
      |Door SENSOR Tampered At NIGHT mode|
      |Window SENSOR Tampered At NIGHT mode|
      |Motion Sensor Tampered At NIGHT mode|
      

      
       @Basestation_DisplacedDuringExitDelayModes @P2
      Scenario: As a user when someone tried to displace or tamper the basestation being in transition of away or night modes I should be notified with alarm 
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
      
      @Basestation_DisplacedAfterExitDelayModes @P3
      Scenario: As a user when someone tried to displace or tamper the basestation being in away or night modes I should be notified with alarm 
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
      #make the alarm dismissed
      #make the panel offline
      #generate the sensor alarm
      #dismiss using keyfob
      #make panel online
      #check the activity log for sensor alarm details  
    
     
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

  
       @AlarmDismissedViaKeyfobDiffModes @P2
    Scenario Outline: As a user I should be able to dismiss alarm through keyfob
     Given user is set to "Away" mode through CHIL
     And user launches and logs in to the Lyric application
     And user clears all push notifications
     When  user window "opened"
     When user selects <Mode> from keyfob
     #validate email content
     And user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user status should be set to <Mode>
     When user "opens" activity log
     Then verify the following activity log:
       | Elements  | 
       | Alarm Dismissed |
       | Switched to Home by Keyfob|
     And user "closes" activity log
     Examples:
     |Mode|
     |Home|
     |Off |
     
     
      @P3	@Alarm_MultiLocation
     Scenario:	As a user when a das is in alarm state in one location, I should be able to access other locations without any problem
     Given user is set to "Away" mode through CHIL
     When user window "opened"
     Given user launches and logs in to the Lyric application
   	 Then user should be displayed with the "Alarm" screen
   	 Then user navigates to "global drawer" from "dashboard alarm" screen
   	  Then user should be displayed with the "Global drawer menu" screen
   	  And user "put app" in background
   	  And user navigates to "other location" screen from the "Dashboard alarm" screen
   	  Then user should be displayed with the "Alarm" screen
      When user selects "dismiss alarm" from "alarm" screen
    
     
      
   @Livestreaming_Alarm  @P1
    Scenario: As an user I should be able to pause and resume streaming in alarm screen
      Given user is set to "Away" mode through CHIL
      When user window "opened"
      And user launches and logs in to the Lyric application
       Then user should be displayed with the "Alarm" screen
       And alarm screen is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with the "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then alarm screen is "Live streaming"
       #check in ipad
       #minimize and launch the aoo, check live stream
       #close the shutter and open in mid of alarm or entry delay, check live stream
       #cover attention alarm
       
       @Livestreaming_Entrydelay  @P1
    Scenario: As an user I should be able to pause and resume streaming in entry delay screen
      Given user is set to "Away" mode through CHIL
       When user door "opened"
        And user launches and logs in to the Lyric application
        Then user should be displayed with the "Entry delay" screen
        And entry delay camera is "Live streaming"
       When user selects "Pause" from "Entry delay" screen
       Then user should be displayed with the "Paused streaming" 
      When user selects "Resume" from "Entry delay" screen
      And launch from background check live stream
       #close the shutter and open in mid of alarm or entry delay, check live stream
       #cover attention alarm
     
     
     
     @EntryDelayScreenValidation @P1
    Scenario: As a user when entry delay is shown I should be able to view the details of entry delay screen.
      Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
      And timer ends on user device
     And  user door "opened"
      And user should be displayed with the following "EntryDelay" options:
      | Elements                               | 
      |  entry delay with door open title      |
      |  entry delay with sensor name          |
      |  entry delay subtitle as location name |
      |  entry delay live stream               |
      |  entry delay alarm in secs text        |
      |  entry delay alarm in secs counter     |
    And user is set to "home" mode through CHIL

 @AlarmScreenValidation  @P1
    Scenario: As a user when alarm is shown I should be able to view the details of alarm screen
     Given user is set to "Away" mode through CHIL
     And user window "opened"
      And user launches and logs in to the Lyric application
      And user should be displayed with the "Alarm" screen
      And user should be displayed with the following "Alarm" options:
      | Elements                    |
      |  alarm title                |
      |  alarm subtitle             |
      |  alarm live stream          |
      |  alarm navigate back button |
      |  call                       |
     When user selects "dismiss alarm" from "alarm" screen
     
     #Repeat screen validation for 
      #  And user force closes the application
     #   And user lyric app screen is locked
      
         @MobileLocked_Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm @P2
      Scenario: As a user with mobile locked when I open the door I should be able to switch to Night from door open push notification on my arrival to home after closing the door
       Given user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
      When user switches from "Home" to "Away"
      When timer ends on user device
      And user mobile screen is locked
      When  user door "opened"
      And user door "closed"
      When user selects the "Switch to Night option from Door opened" push notification
	 When user navigates to "Security Solution card" screen from the "Dashboard" screen
     Then user should be displayed with "Mobile locked" screen
     When user enters "Mobile Passcode" 
     When timer ends on user device
	 Then user status should be set to "Night"
     When user "opens" activity log
     
     @After24hrs_Doorsensor_OpenAfterAwayExitDelay_PotraitLandscapeView_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm  @P2
    Scenario: As a user with app not used for 24 hrs when I open the door in away mode I should be able to switch to Night from Entry Delay but failed to close the door in entry delay waiting should be taken to alarm
   Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
      And user clears all push notifications
     When user navigates to "Security Solution card" screen from the "Dashboard" screen
    And user switches from "Home" to "Away"
    And user force closes the application
   And user lyric app screen is locked
     And timer ends on user device
     And  user door "opened"
     Then user selects the "Door Opened" push notification
     #view the entry delay screen in landscape mode
     When user selects "Switch to Night" from "Entry delay" screen
    Then user should be displayed with "Lyric app locked" screen
    When user enters "Lyric Passcode" 
	 Then user should be displayed with the "Waiting to close" screen
	 When user door "is not closed"
      And user should be displayed with the "Alarm" screen
     When user selects "dismiss alarm" from "alarm" screen
      
   # @MultiDoorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromEntryDelay
    
   # @AttentionPopup_DoorSensorOpened obsolete
    
   # @MotionDetectedAttentionCreated_verifyNoframedrop
    
   # @AlarmScreen_SmoothTransitionNFR
   
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
   
     