@EditNATimeschedule @NA_Schedule @Comfort
Feature: Edit Time Based schedule
As an user 
I want to Edit Time schedule
so that i can change Time and Set points of individual periods and Days 

@NA_GroupEditedDays @automated
  Scenario Outline: As a User i want to Edit my Individual days so that those days are grouped
    Given user thermostat is set to "Time Based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects "SINGLE DAY" view
     When user edits schedule periods of <EditedDays>
      And user selects "Grouped days" view
     Then <EditedDays> are grouped separately
  
    Examples: 
      | EditedDays | 
      | One day    | 
  #    | Two days   | 
  #    | Three days | 
   #   | Four days  | 
   #   | Five days  | 
  #    | Six days   | 
  
  @NA_EditTempratureInSchedule @automated
  Scenario Outline: As a User i want to Edit Temperature value for period and those days are grouped
    Given user thermostat is set to "Time Based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates <ScheduleType> schedule by setting temperature value to <Temperature>
     Then verify temperature is incremental by 1F for fahrenheit and 0.5C for celsius
  
    Examples: 
      | ScheduleType          | Temperature   | 
      | Same Every Day        | Above Maximum | 
   #   | Same Every Day        | Below Minimum | 
   #   | Same Every Day        | At Maximum    | 
   #   | Same Every Day        | At Minimum    | 
    #  | Same Every Day        | within range  | 
   #   | Different On Weekdays | Above Maximum | 
   #   | Different On Weekdays | Below Minimum | 
   #   | Different On Weekdays | At Maximum    | 
    #  | Different On Weekdays | At Minimum    | 
   #   | Different On Weekdays | within range  | 
  
  @NA_EditSetpointsWhenAutoChnageOverEnabled @automated
  Scenario Outline: As a User i want to Edit set point when Auto chnage over is enabled so that my 
  cool set point is always greater than or equal to heat set point
    Given user thermostat is set to "Time Based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates <ScheduleType> schedule with default schedule value
     Then Periods cool set point is always greater than or equal to heat set point
      And <ScheduleType> scheduling gets activated
  
    Examples: 
      | ScheduleType          | 
      | Same Every Day        | 
   #   | Different On Weekdays |  
      
  @NA_PeriodTimeShouldNotOverlap @automated
  Scenario: As a User i want to edit my period so that each period is independent,On overlap Schule period is removed
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Same Every Day" schedule with default schedule value
      And user selects "SINGLE DAY" view
     When user edit "Wake" period by changing time value to "06 00 AM"
      And user edit "Away" period by changing time value to "06 00 AM"
     Then user should be displayed "Wake" time as "Tap to set"
  
  @NA_DeletePerioConfirmdAlert @automated
  Scenario: As a User i want be shown with confirm pop so that i will be alerted of deleting period 
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Same Every Day" schedule with default schedule value
      And user selects "SINGLE DAY" view
     When user edit Time schedule by deleting "Atleast 1 period"
     Then user should be displayed with confirm pop for period deletion
      And Period is "not deleted" on "canceling" Dialog box
  
  @NA_DeletingDefaultPeriodDifferentOnWeekdays @automated
  Scenario Outline: As a user i want to delete periods in Different On Weekdays schedule so that only those periods are deleted
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Different On Weekdays" schedule with default schedule value
      And user selects "SINGLE DAY" view
     When user edit Time schedule by deleting <Periods>
     Then verify "Time" schedule successfully gets edited
#      And "EditedDays" are grouped separately
  
    Examples: 
      | Periods          | Period Value |EditedDays |
   #   | Atleast 1 period | Tap to set   |One days |
      | Atleast 2 period | Tap to set   |Two days |
    #  | Atleast 3 period | Tap to set   |Three days|

 @NA_DeletingAllPeriod @Automated
  Scenario Outline: To edit Time schedule by deleting all period for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "Time Based" schedule
    And user launches and logs in to the Lyric application
    And user navigates to "Scheduling" screen from the "Dashboard" screen
    And user selects <Type> view
     When user edit Time schedule by deleting "All 4 periods" on confirming the period deletion
     Then verify "No Schedule" screen is shown in view schedule screen
      Examples:
      | Type            | 
      | Grouped days    | 
#      | SINGLE DAY | 
        
  @NA_ScheduleOptions @Automated
  Scenario Outline: As a user i want an Options to Create,Pause_Off and Switch Schdeule
    Given user thermostat is set to "Time based" schedule
    And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user selects <schedule Options> from Scheduling screen
     Then user should be displayed with the <Expected> screen
  
    Examples: 
      | schedule Options        | Expected            | 
      | Switch to Geofencing    | Geofencing schedule | 
   #   | Create new time schedule | Time based schedule  | 
   #   | Turn Schedule Off   | Tap on Resume       | 
  
  @NA_ResumeSchedule @Automated
  Scenario: As a user i want an Options to resume Schdeule
    Given user thermostat is set to "Time Based" schedule  
      And user selects "Pause" option on Scheduling 
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user selects "Tap On Resume" from Scheduling screen
     Then "Same Every Day" scheduling is retained
  
  @NA_DeletingCurrentPeriodDifferentOnWeekdays @Automatable
  Scenario: As a user i want to delete periods in Different On Weekdays schedule so that only those periods are deleted
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Different On Weekdays" schedule with default schedule value
      And user selects view by "SINGLE DAY"
     When user deletes "Current Period" from the Schdeule
     Then user is displayed with "Tap to Set" on deleted period
     
     
@NA_EditAway/HomeSettingsGeofence @Automatable
Scenario Outline: As a user i want to edit away/home settings in Geofence Schedule
Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Geofence" schedule with default schedule value
      When user edits <Geofence Status> settings by changing "Temprature" 
      Then user should be displayed "Geofence" schedule with <Geofence Status> settings with "Temprature"
      
Examples:
|Geofence Status|
|Home|
#|Away|
#|Sleep|
 
  
