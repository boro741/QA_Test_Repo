@EditEMEATimeschedule @EMEA_Schedule @Comfort
Feature: Edit Time Based schedule
As an user 
I want to Edit Time schedule
so that i can change Time and Set points of individual periods and Days 


@EMEA_AtleastOnePeriodNotDeletableInGroupDay  @Automated
Scenario: Verify User should have atleast one schedule period in set of grouped days 
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application 
     And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
     And user selects "Grouped Days" view
     When user tries to delete "All Periods" in EMEA schedule screen
     And user navigates to "Scheduling" screen from the "DASHBOARD" screen
     Then verify user should have atleast "One" schedule period in "Grouped days" view


@EMEA_AtleastTwoPeriodInIndividualDay  @Automated
Scenario: Verify User should have atleast two schedule period in set of individual days 
    Given user has "time schedule" with "1 Period"
     And user launches and logs in to the Lyric application
     And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
     And user selects "SINGLE DAY" view
     When user tries to delete one of the schedule period of the last two schedule period
     And user navigates to "Scheduling" screen from the "DASHBOARD" screen
     Then verify user should have atleast "Two" schedule period in "Single day" view

@EMEA_EndtimeTimeschedulePeriod  @Automated
Scenario Outline:Verify User should not be allowed to edit end time of last period in a day 
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application 
     And user navigates to "Scheduling" screen from the "Dashboard" screen
     And user selects <Type> view
     Then verify user should not be allowed to edit end time with same as start time
      Examples:
      | Type            | 
      | Grouped days    | 
      
@EMEA_EditingEndtime @Automated
 Scenario Outline:Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects <Type> view
     Then User edits end time value atleast 10 min less than next day period start value
      
      Examples:
      | Type            | 
      | Grouped days    | 
   #   | Individual days | 
      
@EMEA_EditingStartTime @Automated
  Scenario Outline: Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects <Type> view
      Then User edits start time value atleast 10 min more than next day period start value
     Examples:
      | Type            | 
      | Grouped days    | 
    #  | Individual days |
      

   @EMEA_GroupEditedDays @UIAutomatable
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
      
@EMEA_ViewTimescheduleIndividualdaysEMEA @AutomatedAndroid
  Scenario: To view the Time schedule for EMEA stat
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
      And user selects "SINGLE DAY" view
     Then verify user shown with Time schedule list in Individual days view
     
     

@EMEA_ScheduleOptions  @Automated
  Scenario Outline: As a user i want an Options to Create,Pause, Off and Switch Schdeule
     Given user thermostat is set to "Time based" schedule
    And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user selects <schedule Options> from Scheduling screen
     Then user should be displayed with the <Expected> screen
  
    Examples: 
      | schedule Options        | Expected            | 
      | Switch to Geofencing    | Geofencing schedule | 
      | Create new time schedule | Time based schedule  | 
      | Turn Schedule Off   | Tap on Resume       | 
      
@EMEA_ResumeSchedule @Automated
  Scenario: As a user i want an Options to resume Schdeule
      Given user thermostat is set to "Time Based" schedule  
      And user selects "Pause" option on Scheduling 
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user selects "Tap On Resume" from Scheduling screen
     Then "Same Every Day" scheduling is retained
     