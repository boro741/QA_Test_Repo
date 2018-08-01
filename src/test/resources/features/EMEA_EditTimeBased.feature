@EditEMEATimeschedule
Feature: Edit Time Based schedule
As an user 
I want to Edit Time schedule
so that i can change Time and Set points of individual periods and Days 


@EMEA_AtleastOnePeriodNotDeletableInGroupDay
Scenario: Verify User should have atleast one schedule period in set of grouped days 
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application 
     And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
     And user selects "Grouped Days" view
     When user tries to delete "All Periods" in EMEA schedule screen
     And user navigates to "Scheduling" screen from the "DASHBOARD" screen
     Then verify user should have atleast "One" schedule period in "Grouped days" view


@EMEA_AtleastTwoPeriodInIndividualDay
Scenario: Verify User should have atleast two schedule period in set of individual days 
    Given user has "time schedule" with "1 Period"
     And user launches and logs in to the Lyric application
     And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
     And user selects "SINGLE DAY" view
     When user tries to delete one of the schedule period of the last two schedule period
     And user navigates to "Scheduling" screen from the "DASHBOARD" screen
     Then verify user should have atleast "Two" schedule period in "Single day" view

@EMEA_EndtimeTimeschedulePeriod
Scenario Outline:Verify User should not be allowed to edit end time of last period in a day 
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application 
     And user navigates to "Scheduling" screen from the "Dashboard" screen
     And user selects <Type> view
     Then verify user should not be allowed to edit end time with same as start time
      Examples:
      | Type            | 
      | Grouped days    | 
      
@EMEA_EditingEndtime
 Scenario Outline:Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects view by <Type>
      When user selects any schedule period in a day
     Then user should not be allowed to edit end time with same as start time
      And End time is atleast "10 min" after the start time
      
      Examples:
      | Type            | 
      | Grouped days    | 
      | Individual days | 
      
@EMEA_EditingStartTime
  Scenario Outline: Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects view by <Type>
      When user selects any schedule period in a day
     Then user should be allowed to edit start time with all possible values for both time formats
     Examples:
      | Type            | 
      | Grouped days    | 
      | Individual days |
      

@EMEA_GroupEditedDays
  Scenario Outline: As a User i want to Edit my Individual days so that those days are grouped
    Given user thermostat is set to "Time Based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects "SINGLE DAY" view
     When user edits the schedule periods of <EditedDays>
      And user selects "GROUPD DAY" view
     Then <EditedDays> are grouped separately
  
    Examples: 
      | EditedDays | 
      | One day    | 
      | Two days   | 
      | Three days | 
      | Four days  | 
      | Five days  | 
      | Six days   | 
      
@EMEA_ViewTimescheduleIndividualdaysEMEA
  Scenario: To view the Time schedule for EMEA stat
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      When user selects view by "Individual Days"
     Then  user shown with Time schedule list in Individual days view
     
     
 @EMEA_EditTempratureInSchedule
  Scenario Outline: As a User i want to Edit Temperature value for period and those days are grouped
    Given user thermostat is set to "Time Based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates <ScheduleType> schedule by setting temperature value to <Temperature>
  
    Examples: 
      | ScheduleType          | Temperature   | 
      | Same Every Day        | Above Maximum | 
      | Same Every Day        | Below Minimum | 
      | Same Every Day        | At Maximum    | 
      | Same Every Day        | At Minimum    | 
      | Same Every Day        | within range  | 
      | Different On Weekdays | Above Maximum | 
      | Different On Weekdays | Below Minimum | 
      | Different On Weekdays | At Maximum    | 
      | Different On Weekdays | At Minimum    | 
      | Different On Weekdays | within range  | 


@CreateEMEA_ScheduleOptions
  Scenario Outline: As a user i want an Options to Create,Pause/Off and Switch Schdeule
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user creates "Different On Weekdays" schedule with default schedule value
     When user selects <schedule Options> from "Scheduling" screen
     Then user should be displayed with "Expected" screen
  
    Examples: 
      | schedule Options        | Expected            | 
      | Switch to Geofencing    | Geofencing schedule | 
      | Create new time Scedule | Time based Scedule  | 
      | Turn oFF Schedule       | Tap on Resume       | 
      
@EMEA_ResumeSchedule
  Scenario: As a user i want an Options to resume Schdeule
    Given user thermostat is set to "Time Based" schedule  
      And user selects "Turn OFF Schedule " option on "Scheduling" 
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user is displayed with "Tap on Resume" screen
     When user selects "Tap on Resume" from "Scheduling" screen
     Then user should be displayed with "Scheduling" screen
     