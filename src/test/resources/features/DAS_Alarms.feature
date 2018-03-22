@DAS_Alarms
Feature: DAS Alarms
As a user I want to be notified when my sensors and system are intruded

Background:

#Given "ENABLE MODE PUSH NOTIFICATION" as precondition
#Given "ENABLE CAMERA STATUS PUSH NOTIFICATION" as precondition
Given relay is reset
And user dismisses all alerts and notification through CHIL


   @Attention_FromCamera @P1 
   Scenario Outline: As a user I should be able to initiate the alarm from camera card on seeing any mischief acts in the premises
   Given user "enables" the "Camera Mode" through CHIL
     And user is set to <System> mode through CHIL
     And user launches and logs in to the Lyric application
    When user "DAS camera" detects "Motion"  
    Then user receives a "Motion" push notification
    When user selects the "Motion" push notification
    Then user should be displayed with "Camera Solution Card" screen
     And user DAS camera is "Live streaming" 
    When user "confirms" the camera attention 
    Then user should be displayed with "Alarm" screen  
    When user "dismisses" the alarm
	Then user should be displayed with "Camera Solution Card" screen
    When user "cancels" the camera attention
    Then user should be displayed with "Camera Solution Card" screen
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
     |Night |
     |Home  |
     |OFF   |


  @AwayMode_doorsensorNoAlarm @P1
  Scenario: As a user i should not get alarm when i opens and closes the door during exit delay
    Given user is set to "Home" mode through CHIL
      And user launches and logs in to the Lyric application
     When user navigates to "Security Solution card" screen from "Dashboard" screen
      And user set to "Away" mode from "Home" mode
      And user "opens" the door "in" Exit delay  
     Then user should not be displayed with "Entry delay" screen
     When user "closes" the door sensor "in" Exit delay
     Then user should not be displayed with "Entry delay" screen
     When user waits till "Exit" timer expires
     Then user receives a "Door opened at Away mode" in activity log
      And user receives a "Door closed at Away mode" activity log
     When user navigates to "Alert" screen from "Activity Log" screen
     Then user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
   
      
    @AwayMode_doorsensorOpenAfterExitDelay_Switchtohome_withdooropen 
    Scenario: As a user I should be able to switch to home from away mode entry delay screen on my arrival to home without closing the door
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
	 Then user should be displayed with "Entry delay" screen
	 When user selects "Switch to Home" from "Entry delay" screen
	 Then user should be displayed with "Home" mode
      And user should be displayed "with" issue in sensor status 
     When user navigates to "Sensor Status" screen from "Security Solution Card" screen
     Then user should be displayed with "door" sensor in "open" status
     When user navigates to "Activity log" screen from "Sensor Status" screen
	 Then user receives a "Door opened at Away mode" activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Switched to Home" alert
      
	  
	  @AwayMode_doorsensorOpenAfterExitDelay_Switchtohome @AF_Mode @--xrayid:ATER-6149
    Scenario: As a user I should be able to switch to home from door sensor open push notification on my arrival to home after closing the door
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
     When user "opens" the door "after" Exit delay
      And user "closes" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Door Closed" push notification
     When user selects the "Switch to Home" push notification
	 Then user should be displayed with "Home" mode
      And user should be displayed "without" issue in sensor status
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Door Closed at Away mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Home" alert
      
      @AwayMode_doorsensorOpenAfterExitDelay_Switchtonight @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from door open push notification in Away mode on my arrival to home after closing the door
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the door "after" Exit delay
      And user "closes" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Door Closed" push notification
     When user selects the "Switch to Night" push notification
     Then user should be displayed with "Mobile locked" screen
     When user enters "Mobile Passcode" 
	 Then user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Door Closed at Away mode" in activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Night" alert
     
    @AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm in away mode if i fails to close the door after entry delay period
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
      And user lyric app screen is locked
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Switch to Night" push notification
     Then user should be displayed with "Lyric app locked" screen
     When user enters "Lyric Passcode" 
	 Then user should be displayed with "Waiting to close" screen
      And user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_Doorclose @--xrayid:ATER-6150
    Scenario: As a user I should be able to enter mobile passcode and switch to Night from entry delay screen on my arrival to home after closing the door in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
     Then user should be displayed with "Mobile locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
      And user selects the "Switch to Night"
	 Then user should be displayed with "Waiting to close" screen
      And user "closes" the door "in" Entry delay
      And user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Door Closed at Away mode" in activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Night" alert
      
      
    @AwayMode_doorsensorOpenAfterExitDelay_AttentionAlarm @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from away mode's entry delay screen on observing intruder in premises
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     #user should be treated as intruder in this scenario
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Attention" from "Entry Delay" screen
     Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Attention Alarm" alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
     
    @AwayMode_doorsensorOpenAfterExitDelay_Alarm @--xrayid:ATER-6150
    Scenario: As a user in away mode I should get alarm from door on breach of intruder
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
    #user should be treated as intruder in this scenario 
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Alarm" push notification
     When user selects the "Alarm" push notification
     Then user should be displayed with "Alarm" screen
     When user navigates to "Alarm History" screen from "Alarm" screen
	 Then user receives a "Door Opened" in Alarm history
     When user navigates to "Alarm" screen from "Alarm History" screen
      And user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution card" screen
     Then user should be displayed with "Grouped Alarm Events" 
     
     
     @AwayMode_doorsensorOpeninExitDelay_Switchtohome_withdooropen @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to home from entry delay screen on my leaving the premises with door left open when the system is in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
	 Then user should be displayed with "Entry delay" screen
	 When user selects "Switch to Home" from "Entry delay" screen
	 Then user should be displayed with "Home" mode
      And user should be displayed "with" issue in sensor status 
     When user navigates to "Sensor Status" screen
     Then user should be displayed with "door" sensor in "open" status
     When user navigates to "Activity log" screen from "Sensor Status" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Switched To Home" alert
      
	  
	  @AwayMode_doorsensorOpeninExitDelay_Switchtohome @AF_Mode @--xrayid:ATER-6149
    Scenario: As a user I should be able to switch to home from door open push notification on leaving door left open and by closing the door when the system is in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the door "in" Exit delay
     Then user receives a "Door Opened" push notification
     When user "closes" the door "after" Exit delay
     Then user receives a "Door Closed" push notification
     When user selects the "Switch to Home" push notification
      And user enters "Mobile Passcode"
	 Then user should be displayed with "Home" mode
      And user should be displayed with sensor status "without" issue
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Door Closed at Away mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Home" alert
      

     
    @AwayMode_doorsensorOpeninExitDelay_Switchtonight_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm in away mode if i fails to close the door within entry delay period
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Switch to Night" 
     Then user should be displayed with "Waiting to close" screen
      And user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Sensor Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Sensor Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @AwayMode_doorsensorOpeninExitDelay_Switchtonight_Doorclose @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from entry delay screen on my arrival to home after closing the door in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
   	 Then user should be displayed with "Entry Delay" screen
      And user should be displayed with "Waiting to close" screen
     When user "closes" the door "in" Entry delay
      And user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" in activity log
      And user receives a "Door Closed at Away mode" in activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Night" alert
      
      
    @AwayMode_doorsensorOpeninExitDelay_AttentionAlarm @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from entry delay screen on observing intruder in premises in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     # user should be treated as intruder in this scenario
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
      And user DAS camera is "Live Streaming"
     When user selects the "Attention" from "Entry Delay" screen
     Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Attention Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert     
     
     
    @AwayMode_doorsensorOpeninExitDelay_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should get alarm from door on leaving the door left open in away mode
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the door "in" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Alarm" push notification
     When user selects the "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Alarm" screen
	 Then user should be displayed with "Alarm history" 
      And user receives a "Door opened" in activity log
      And user receives a "Door Alarm" in activity log
      And user receives a "Alarm" in activity log

 
      @NightMode_doorsensorOpenAfterExitDelay_Switchtohome_withdooropen @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to home from night mode entry delay screen on my arrival to home without closing the door
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Switch to Home" push notification
     Then user should be displayed with "Mobile Entry" screen
     When user enters "Mobile Passcode"
	 Then user should be displayed with "Home" mode
      And user should be displayed "with" issue in sensor status 
     When user navigates to "Sensor Status" screen from "Security Solution Card" screen
     Then user should be displayed with "door" sensor in "open" status
     When user navigates to "Activity log" screen from "Sensor Status" screen
	 Then user receives a "Door opened at Night mode" activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Switched to Home" alert
      
	  
	  @NightMode_doorsensorOpenAfterExitDelay_Switchtohome @AF_Mode @--xrayid:ATER-6149
    Scenario: As a user I should be able to switch to home from door open push notification on my arrival to home after closing the door
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
      And user mobile screen is locked
     When user "opens" the door "after" Exit delay
      And user "closes" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Door Closed" push notification
     When user selects the "Door Opened" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Switch to Home"
	 Then user should be displayed with "Home" mode
      And user should be displayed "without" issue in sensor status
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Night mode" in activity log
      And user receives a "Door Closed at Night mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Home" alert
      
      
      @NightMode_doorsensorOpenAfterExitDelay_Switchtonight @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from door open push notification in night mode on my arrival to home after closing the door
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
     When user "opens" the door "after" Exit delay
      And user "closes" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Door Closed" push notification
     When user selects the "Switch to Night" push notification
	 Then user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Night mode" in activity log
      And user receives a "Door Closed at Night mode" in activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Night" alert
     
    @NightMode_doorsensorOpenAfterExitDelay_Switchtonight_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm after night mode if i fails to close the door within entry delay period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Switch to Night" push notification
	 Then user should be displayed with "Waiting to close" screen
      And user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Door Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
        
    @NightMode_doorsensorOpenAfterExitDelay_AttentionAlarm @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from entry delay screen on observing intruder in premises in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
     #user should be treated as intruder in this scenario
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
     Then user should be displayed with "Entry Delay" screen
     When user selects the "attention" from "Entry Delay" screen
     Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Attention Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
     
    @NightMode_doorsensorOpenAfterExitDelay_Alarm @--xrayid:ATER-6150
    Scenario: As a user in night mode I should get alarm from door on breach of intruder
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
      And user mobile screen is locked
    #user should be treated as intruder in this scenario 
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
      And user receives a "Alarm" push notification
     When user selects the "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Alarm" screen
     When user navigates to "Alarm History" screen from "Alarm" screen
	 Then user receives a "Door Opened" in Alarm history
     When user navigates to "Alarm" screen from "Alarm History" screen
      And user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution card" screen
     Then user should be displayed with "Grouped Alarm Events" 
     
     
     @NightMode_doorsensorOpeninExitDelay_Switchtohome_withdooropen @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to home from entry delay screen on my leaving the premises with door left open when the system is in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user force closes the application
      And user mobile screen is locked
     When user "opens" the door "in" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
	 Then user should be displayed with "Entry delay" screen
	 When user selects "Switch to Home" from "Entry delay" screen
	 Then user should be displayed with "Home" mode
      And user should be displayed "with" issue in sensor status 
     When user navigates to "Sensor Status" screen
     Then user should be displayed with "door" sensor in "open" status
     When user navigates to "Activity log" screen from "Sensor Status" screen
	 Then user receives a "Door Opened at Night mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Switched To Home" alert
      
	  
	  @NightMode_doorsensorOpeninExitDelay_Switchtohome @AF_Mode @--xrayid:ATER-6149
    Scenario: As a user I should be able to switch to home from door open push notification on leaving door left open and by closing the door when the system is in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
     When user "closes" the door "after" Exit delay
      And user selects the "Switch to Home" 
	 Then user should be displayed with "Home" mode
      And user should be displayed with sensor status "without" issue
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Night mode" in activity log
      And user receives a "Door Closed at Night mode" in activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Home" alert
      

     
    @NightMode_doorsensorOpeninExitDelay_Switchtonight_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should be notified with alarm during night mode if i fails to close the door within entry delay period
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Switch to Night" 
     Then user should be displayed with "Waiting to close" screen
      And user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Sensor Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Sensor Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @NightMode_doorsensorOpeninExitDelay_Switchtonight_Doorclose @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from entry delay screen on my arrival to home after closing the door in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
   	 Then user should be displayed with "Entry Delay" screen
      And user should be displayed with "Waiting to close" screen
     When user "closes" the door "in" Entry delay
      And user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Night mode" in activity log
      And user receives a "Door Closed at Night mode" in activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Door Closed" alert
      And user receives a "Switched to Night" alert
      
      
    @NightMode_doorsensorOpeninExitDelay_AttentionAlarm @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from entry delay screen on observing intruder in premises
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     # user should be treated as intruder in this scenario
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
      And user DAS camera is "Live Streaming"
     When user selects the "Attention" from "Entry Delay" screen
     Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Attention Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert     
     
     
    @NightMode_doorsensorOpeninExitDelay_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should get alarm from door on leaving the door left open in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
     Then user should be displayed with "Entry Delay" screen
      And user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Alarm" screen
	 Then user should be displayed with "Alarm history" 
     When user navigates to "Alarm" screen from "Alarm Activity Log" screen
      And user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution card" screen
     Then user should be displayed with "Alarm history"
     
      
      @AwayMode_doorsensorOpenAfterExitDelay_Alarm @APassed @--xrayid:ATER-6161
      Scenario: As a user I should be shown with entry delay screen with all required information 
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "After" Exit delay
   	 Then user should be displayed with "Entry Delay" screen
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
	 Then user should be displayed with "Alarm" screen
      And user should be displayed with following:
      | Elements                    |
      |  alarm title 	   			|
      |  alarm subtitle			    |
      |  alarm live stream 			|
      |  alarm navigate back button |
      |  call                       |
    
  
         
      @AwayMode_MotionsensorAfterExitDelay_Switchtohome @--xrayid:ATER-6147
    Scenario: As a user I should be able to switch to home from entry delay screen on my arrival to home on my motion sensor detection
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "sensor" detects the "Motion"
     Then user receives a "Motion Detected" push notification
     When user selects the "Motion Detected" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Switch to Home"
	 Then user should be displayed with "Home" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Detected at Away mode" activity log
      And user receives a "Switched to Home via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Motion Detected" alert
      And user receives a "Switched to Home" alert
      
	  
	        
      @AwayMode_MotionsensorAfterExitDelay_Switchtonight @--xrayid:ATER-6150
    Scenario: As a user I should be able to switch to Night from motion detection push notification 
    Given user is set to "Away" mode through CHIL
      And user minimizes the application
      And user mobile screen is locked
     When user "sensor" detects the "Motion"
     Then user receives a "Motion Detected" push notification
     When user selects the "Motion Detected" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Switch to Night"
	 Then user should be displayed with "Night" mode
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Detected at Away mode" activity log
      And user receives a "Switched to Night via app" in activity log
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Motion Detected" alert
      And user receives a "Switched to Night" alert
     
       
        
    @AwayMode_doorsensorOpenAfterExitDelay_AttentionAlarm @--xrayid:ATER-6150
    Scenario: As a user I should be able to initiate attention alarm from entry delay screen on observing intruder in premises by entering mobile passcode when required
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     #user should be treated as intruder in this scenario
     When user "sensor" detects the "Motion"
     Then user receives a "Motion Detected" push notification
     When user selects the "Motion Detected" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
     When user selects the "Attention" from "Entry Delay" screen
     Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Attention Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
     
    @AwayMode_MotionsensorAfterExitDelay_Alarm @--xrayid:ATER-6150
    Scenario: As a user I should get notified with alarm from motion sensor on breach of intruder
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
    #user should be treated as intruder in this scenario 
     When user "sensor" detects the "Motion"
     Then user receives a "Motion Detected" push notification
     When user selects the "Motion Detected" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
     Then user should be displayed with "Entry Delay" screen
      And user should be displayed with "Alarm" screen
     When user navigates to "Alarm History" screen from "Alarm" screen
	 Then user receives a "Motion Detected" in Alarm history
     When user navigates to "Alarm" screen from "Alarm History" screen
      And user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity log" screen from "Security Solution card" screen
     Then user should be displayed with "Grouped Alarm Events" 
     
     
      
	   @AwayMode_windowsensorOpenDuringExitDelay_DismissAlarm @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm when intruder breaches the premises through window 
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the window "in" Exit delay
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
      @AwayMode_windowsensorOpenAfterExitDelay_DismissAlarm @--xrayid:ATER-6191
      Scenario: As a user i should be notified with alarm on intruder breach through window while system is in away
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the window "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
     
      
      @NightMode_windowsensorOpenDuringExitDelay_DismissAlarm @--xrayid:ATER-6194
      Scenario: As a user I want to be shown the alarms when window access sensors was open during exit delay
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "opens" the window "in" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @NightMode_windowsensorOpenAfterExitDelay_DismissAlarm @--xrayid:ATER-6195
    Scenario: As a user i should be notified with alarm on intruder breach through window while system is in night mode
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "opens" the window "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Attention Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @AwayMode_doorsensorOpenAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises from door
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door opened at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      
      
       @AwayMode_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with motion detection
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user "Sensor" detects the "Motion" "after" Exit delay
     Then user receives a "Motion Detected" push notification
     When user selects the "Motion Detected" push notification
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Detected at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Motion Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Motion Alarm" alert 
      
     @AwayMode_Windowopen_MotionsensorAfterExitDelay_Dooropen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on door open by intruder while entry delay in progress  after intruder breaches the premises with window open and motion detection
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user "opens" the window "after" Exit delay
     Then user receives a "Window Opened" push notification
     When user selects the "Window Opened" push notification 
     Then user should be displayed with "Alarm" screen
     When user "Sensor" detects the "Motion" "after" Exit delay 
      And user "opens" the door "after" Exit delay
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Window Opened at Away mode" activity log
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Motion Detected at Away mode" activity log
      And user receives a "Door Opened at Away Mode"
      And user receives a "Motion Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Door Alarm" alert
      And user receives a "Motion Alarm" alert 
      
      
     @AwayMode_Dooropen_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In away mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with door open and motion detection
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application 
     When user "opens" the door "after" Exit delay
     Then user receives a "Door Opened" push notification
     When user selects the "Door Opened" push notification 
     When user "Sensor" detects the "Motion" "after" Exit delay
     Then user receives a "Motion Detected" push notification
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" activity log
      And user receives a "Motion Detected at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log
      And user receives a "Motion Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      And user receives a "Motion Alarm" alert 
      
      
    @AwayMode_doorsensorOpenAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Away Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door opened at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      
    @NightMode_doorsensorOpenAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Night mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises from door
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "after" Exit delay
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door opened at Night mode" activity log
      And user receives a "Window Opened at Night Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      
      
       @NightMode_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In Night mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with motion detection
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Sensor" detects the "Motion" "after" Exit delay
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Detected at Night mode" activity log
      And user receives a "Window Opened at Night Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Motion Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Motion Alarm" alert 
      
     @NightMode_Windowopen_MotionsensorAfterExitDelay_Dooropen @--xrayid:ATER-6147
    Scenario: In night mode As an user I should get alarm immediately on door open by intruder while entry delay in progress  after intruder breaches the premises with window open and motion detection
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the window "after" Exit delay
     Then user should be displayed with "Alarm" screen
     When user "Sensor" detects the "Motion" "after" Exit delay 
      And user "opens" the door "after" Exit delay
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Window Opened at Night mode" in activity log
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Motion Detected at Night mode"in activity log
      And user receives a "Door Opened at Night Mode" in activity log
      And user receives a "Motion Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Door Alarm" alert
      And user receives a "Motion Alarm" alert 
      
      
     @NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In night mode As an user I should get alarm immediately on window open by intruder while entry delay in progress  after intruder breaches the premises with door open and motion detection
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "after" Exit delay
     Then user should be displayed with "Entry Delay" screen
     When user "Sensor" detects the "Motion" "after" Exit delay
     Then user receives a "Motion Detected" push notification
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door Opened at Away mode" activity log
      And user receives a "Motion Detected at Away mode" activity log
      And user receives a "Window Opened at Away Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log
      And user receives a "Motion Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Motion Detected" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      And user receives a "Motion Alarm" alert 
      
      
    @NightMode_doorsensorOpenAfterExitDelay_windowopen @--xrayid:ATER-6147
    Scenario: In night Mode as an user I should get immediate alarm for door open when system in entry delay due to user left the door open
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "opens" the door "in" Exit delay
	 Then user should be displayed with "Entry delay" screen
	 When user "opens"the window "in" Entry delay
	 Then user should be displayed with "Alarm" screen
     When user navigates to "Activity log" screen from "Security Solution Card" screen
	 Then user receives a "Door opened at Night mode" activity log
      And user receives a "Window Opened at Night Mode"
      And user receives a "Window Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Door Alarm" in activity log 
     Then user navigates to "Alert" screen from "Activity Log" screen
      And user receives a "Door Opened" alert
      And user receives a "Window Opened" alert
      And user receives a "Alarm" alert
      And user receives a "Window Alarm" alert
      And user receives a "Door Alarm" alert
      
      
	  @DismissAlarm_Navigation @--xrayid:ATER-6218
      Scenario Outline: As an user I should be navigated to screen previous to alarm status on dismissing alarm
      Given user is set to "Away" mode through CHIL
        And user launches and logs in to the Lyric application
        And user navigates to <Current> screen from <Previous> screen
       When user "opens" the window "after" Exit delay
   	   Then user should be displayed with "Alarm" screen
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
       When user "opens" the window "after" Exit delay
   	   Then user should be displayed with "Alarm" screen
       #When user navigates to <Current> screen from <Previous> screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous |Current   |
       |Alarm    |Dashboard |
       |Dashboard|Camera Solution Card|
       |Camera Solution Card|Security Solution Card|Geofence radius  |
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
       When user "opens" the door "after" Exit delay
   	   Then user should be displayed with "Entry Delay" screen
       When user navigates to <Current> screen from <Previous> screen
       Then user should be displayed with "Alarm" screen
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
       When user "opens" the door "after" Exit delay
   	   Then user should be displayed with "Entry Delay" screen
      # When user navigates to <Current> screen from <Previous> screen
      Then user should be displayed with "Grayed settings" screen
       Then user should be displayed with "Alarm" screen
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
       When user "opens" the window "after" Exit delay
   	   Then user should be displayed with "Alarm" screen
      # When user navigates to <Current> screen from <Previous> screen
      Then user should be displayed with "Grayed settings" screen
       When user "dismisses" the alarm
       Then user should be displayed with "Dashboard" screen
       Examples:
       |Previous |Current   |
       |Alarm    |Dashboard |
       |Dashboard|Camera Solution Card|
       |Camera Solution Card|Security Solution Card|Geofence radius  |
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
   	   Then user should be displayed with "Alarm" screen

       
      @Entrydelay_Login @--xrayid:ATER-6147
    Scenario Outline: As an user I should be shown with entry delay screen on login to lyric app during panel in entry delay state
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
       When user "opens" the door "after" Exit delay
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with "Entry delay" screen
       
       
      @Alarm_Call @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to call police from alarm screen on confirming intruder in premises
      Given user is set to "Away" mode through CHIL
       When user "opens" the window "after" Exit delay
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with "Alarm" screen
       When user selects "Call" from "Alarm" screen
       Then user should be displayed with "Call" screen from "Alarm" screen
       
       
      @Alarm_Livestreaming  @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to pause and resume streaming in alarm screen
      Given user is set to "Away" mode through CHIL
       When user "opens" the window "after" Exit delay
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with "Alarm" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then user camera is "Live streaming"
       
      @Entrydelay_Livestreaming  @--xrayid:ATER-6147
    Scenario Outline: As an user I should be able to pause and resume streaming in entry delay screen
      Given user sets the entry/exit timer to "60" seconds
        And user is set to "Away" mode through CHIL
       When user "opens" the window "after" Exit delay
        And user launches and logs in to the Lyric application
   	   Then user should be displayed with "Alarm" screen
        And user camera is "Live streaming"
       When user selects "Pause" from "Alarm" screen
       Then user should be displayed with "Paused streaming" 
       When user selects "Resume" from "Alarm" screen
       Then user camera is "Live streaming"
       
       
   @AwayMode_DoortamperDuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on door tamper during away exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the door "in" Exit delay
   	 Then user should be displayed with "Alarm" screen
     When user "Tamper Restored" the door
      And user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" in activity log
     And user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" in activity log
     And user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" in activity log
     And user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" in activity log
     And user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
     When user "Tampered" the window "in" Exit delay
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" in activity log
     And user receives a "Window Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
      @AwayMode_WindowtamperAfterExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on window tamper after away mode exit delay
    Given user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the window "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" in activity log
     And user receives a "Window Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert 
      
    @NightMode_WindowtamperDuringExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on window tamper during night exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
     When user "Tampered" the window "in" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Door Tampered" in activity log
     And user receives a "Door Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
      
      
    @NightMode_WindowtamperAfterExitDelay @--xrayid:ATER-6190
 Scenario: As a user I should be notified with alarm on window tamper after exit delay
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
      And user minimizes the application
      And user mobile screen is locked
     When user "Tampered" the window "after" Exit delay
     Then user receives a "Alarm" push notification
     When user selects "Alarm" push notification
     Then user should be displayed with "Mobile Locked" screen
     When user enters "Mobile Passcode"
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Window Tampered" in activity log
     And user receives a "Window Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
    When user navigates to "Alert" screen from "Activity Log" screen
    Then user receives a "Window Tampered" alert
     And user receives a "Window Alarm " alert
     And user receives a "Alarm" alert
     And user receives a "Alarm Dismissed" alert
     And user receives a "Switched to Home" alert
     
     
    @AwayMode_MotiontamperDuringExitDelay @--xrayid:ATER-6190
      Scenario: As a user I should be notified with alarm on motion sensor tamper during exit delay 
    Given user sets the entry/exit timer to "60" seconds
      And user is set to "Away" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the motion sensor "in" Exit delay
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Tampered" in activity log
     And user receives a "Motion Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Motion Tampered" in activity log
     And user receives a "Motion Alarm" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
      And user receives a "Door Opened" in activity log
      And user receives a "Door Alarm" in activity log
      And user receives a "Alarm" in activity log
      And user receives a "Motion Detected" in activity log
      And user receives a "Window Opened" in activity log
      And user receives a "Door Tampered" in activity log
      And user receives a "Window Tampered" in activity log
      And user receives a "Motion Tampered" in activity log
      
      

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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Basestation Displaced" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
   	 Then user should be displayed with "Alarm" screen
     When user "dismisses" the alarm
     Then user should be displayed with "Home" Mode
     When user navigates to "Activity Log" screen from "Security Solution Card" screen
	 Then user receives a "Basestation Displaced" in activity log
     And user receives a "Alarm" in activity log
     And user receives a "Alarm Dismissed" in activity log
     And user receives a "Switched to Home via app" in activity log
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
	 Then user "should not" receives a "Basestation Displaced" in activity log
      And user receives a "Switched to Night via app" in activity log
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
	 Then user "should not" receives a "Basestation Displaced" in activity log
      And user receives a "Switched to Night via app" in activity log
     When user navigates to "Alert" screen from "Activity Log" screen
     Then user receives a "Switched to Night" alert
      And user "should not" receives a "Alarm Dismissed" alert
      
      
   @Alarm_Offline @--xrayid:ATER-6190
 Scenario: As a user I should be shown with help message after panel offline so that i will be guided to take necessary actions
    Given user is set to "Night" mode through CHIL
      And user launches and logs in to the Lyric application
     When user "Tampered" the window "after" Exit delay
   	 Then user should be displayed with "Alarm" screen
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
      And user "Tampered" the window "after" Exit delay
   	 Then user should be displayed with "Alarm" screen
 Examples:|Zwave operation|
          |Replace        | 
          |Fix All        |
          |ALL ON         |
          |ALL OFF        |
          |Controller Factory Reset|
          |Inclusion      |
          |Exclusion      |
          |Learn          |

   
        
