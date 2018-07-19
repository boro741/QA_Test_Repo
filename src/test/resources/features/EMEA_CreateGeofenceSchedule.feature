@EMEA_GeofenceSchedule
Feature: Edit Geofence based scheduling
As an user i want to Create and Edit Geofence Schedule 
so that my home temperature will get set automatically based on geofence settings

@EMEA_CreateGeofenceSchedule
  Scenario: As a user i want to create an Geofence schdeule with Defualt values(Home/Away settings)
    Given user thermostat is set to "No" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule with "Use Geofence"
     Then "Geofence based" scheduling gets activated
      And user navigates to "Primary card" screen from the "Scheduling" screen
      And user is displayed with "Using Away/Home settings"

      @EMEA_CreateNewGeofenceSchedulewithExistingGeofence
  Scenario: As a user i want to create New Geofence schdeule when user already running on Geofence Schedule 
    Given user thermostat is set to "Geofence" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule with "Use Geofence"
     Then "Geofence based" scheduling gets activated
      And user navigates to "Primary card" screen from the "Scheduling" screen
      And user is displayed with "Using Away/Home settings"
  
  @EMEA_SleepSettings
  Scenario Outline: As a user i want to Add/Edit Geofence Sleeo Settings 
    Given "Geofence" Schedule <Sleep Option> sleep Settings
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user <Condition> sleep settings in Geofence Schedule
     Then user is displayed with <Edit Sleep Value> sleep settings
  
    Examples: 
      | Sleep Option | Condition | 
      | With         | Edit      | 
      | With Out     | New       | 
      | With         | Delete    | 
  
  @EMEA_EditGeofenceWithTemperature
  Scenario Outline: As a user i want to verify Tempreture bandwidth limit for  Home,Sleep and Away settings
  Above Maximum: Above 90, Below Minimum : below 50,At Maximum : max 90, At Minimum : min 50 ,within range : between 50-90
    Given user thermostat is set to <ScheduleType> schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule by setting temperature value to <Temperature>
     Then user should be displayed "Geofence based" schedule with temperature value incremental by 1F for fahrenheit and 0.5C for celsius
  
    Examples: 
      | Temperature   | 
      | Above Maximum | 
      | Below Minimum | 
      | At Maximum    | 
      | At Minimum    | 
      | within range  | 
  
  @EMEA_EditGeofenceSetpointsWhenAutoChnageOverEnabled
  Scenario Outline: As a User i want to Edit set point when Auto change over is enabled so that my 
  cool set point is always greater than or equal to heat set point
  
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule by setting temperature value to <Temperature>
      And user selects <Geofence Periods> settings to edit
     Then Periods cool set point is always greater than or equal to heat set point
  
    Examples: 
      | Geofence Periods | 
      | Home             | 
      | Sleep            | 
      | Away             | 
  
  @JasperEMEA_TimerClockIsInCrementalOf15mins
  Scenario Outline: As a user i want to verify if Sleep settings timer is incremental of 15mins
    Given "Geofence" Schedule "With" sleep Settings
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user <Condition> sleep settings in Geofence Schedule
     Then user should be displayed sleep setting timer with increments of "15 minutes"
  
    Examples: 
      | Condition   | 
      | Edit        | 
      | Creates new | 
  
  @GuidemessagToTurnONLocationservicesNA @NotAutomatable
  Scenario Outline:To get guide message to turn ON location services to edit geofence schedule by editing home settings 
  or by editing sleep settings or by editing away settings   
  I want to get guide message to turn ON location services   
  So that I will enable the service for using Geofence schedule
    Given With the <Below condition>
     When User edit "Geofence" schedule <Condition> sleep settings   
     Then User is displayed with guide message to turn ON location services
    Examples: 
      | Below condition                                 | Condition | 
      | Location services is disabled in mobile device  | With      | 
      | Location services is disabled for the lyric app | With      | 
      | Location services is disabled in mobile device  | Without   | 
      | Location services is disabled for the lyric app | Without   | 
  
  @EMEA_GeofenceScheduleOptions
  Scenario Outline: As a user i want an Options to Pause/Off and Switch Geofence Schdeule
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Geofence" schedule with default schedule value
     When user selects <schedule Options> from "Scheduling" screen
     Then user should be displayed with "Expected" screen
  
    Examples: 
      | schedule Options     | Expected            | 
      | Switch to Time based | Time based schedule | 
      | Turn  Schedule oFF   | Tap on Resume       | 

@EMEA_CreateGeofenceScheduleInOffMode
Scenario Outline: As a user I want to create an Geofence schedule with default schedule value when System is in Off Mode 
Given user thermostat is set to "Geofence" schedule
And  user has "Off" system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates <scheduling> schedule with default schedule value
Then <scheduling> scheduling gets activated
And user navigates to "Primary card" screen from the "Scheduling" screen
Then user is displayed with "System is Off" on the screen

    Examples: 
      | scheduling| 
      | time based|
      | Geofence |
      |No|

@EMEA_CreateGeonceScheduleInLearnMode
Scenario: As a user i want to create an Geofence schdeule from Learn More
    Given user thermostat is set to "No" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule with "Learn More"
     Then <Geofence based> scheduling gets activated
 And user navigates to "Primary card" screen from the "Scheduling" screen
      And user is displayed with "Using Away/Home settings"
      
      @EMEA_CreateGeonceScheduleInLearnModeWhenUserSkipsGeofence
Scenario: As a user i want to create an Geofence schdeule from Learn More
    Given user Skips Geofence while DIY and Configures a stat
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Geofence based" schedule with "Learn More"
     Then <Geofence based> scheduling gets activated
 And user navigates to "Primary card" screen from the "Scheduling" screen
      And user is displayed with "Using Away/Home settings"

     
     @EMEA_CopyScheduleToMulitpleStat
# Given Account has a Location with Multiple Stats
Scenario Outline: As a user i want to copy my New schedule to other stats as well
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "CopyStatst" stats while creating "Geofence" schedule with default schedule value
Then "Geofence" scheduling gets activated in <CopyStatst> stats
  
  Examples: 
 |CopyStatst |
| None|
|All|
| Selected|

@EMEA_CopyScheduleWhenStatOffline
# Given Account has a Location with Multiple Stats and Offline stats
Scenario: As a user i want to verify that offline Stats are not displayed in the Copystat pop ups
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "CopyStatst" stats while creating "Geofence" schedule with default schedule value
Then Offline Stat should't be displayed

@EMEA_CreateGeofenceWithEdiotingHome_Sleep_AwaySettings
  Scenario Outline: As a user i want to create an Geofence schdeule with Editing Home,Sleep and Away setpoint in Geofence
    Given user thermostat is set to "No" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     And user creates "Geofence based" schedule with "Use Geofence"
     When user edits "Condition" Settings
    Then "Geofence based" scheduling gets activated
    And user is displayed with "Condition" settings
Examples: 
|Condition|
|Sleep|
|Home|
|Away|

@EMEA_EditGeofenceScheduleSettingsAndBackNavigation
  Scenario Outline: As a user i want to create an Geofence schdeule with Editing Home,Sleep and Away setpoint in Geofence and Verify if Values are saved navigating Back 
    Given user thermostat is set to "No" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     And user creates "Geofence based" schedule with "Use Geofence"
     When user edits "Condition" Settings
     And user Navigates taps on "Back" button
     Then user should still be displayed with edited set points
     Examples: 
|Condition|
|Sleep|
|Home|
|Away|

@EMEA_CreateGeofenceScheduleInOffMode
Scenario: As a user I want to create an Geofence schedule with default schedule value when System is in Off Mode 
Given user thermostat is set to "Geofence" schedule
And  user has "Off" system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Time Based" schedule with default schedule value
Then <scheduling> scheduling gets activated on Tapping Confirm
And user navigates to "Primary card" screen from the "Scheduling" screen
Then user is displayed with "System is Off" on the screen