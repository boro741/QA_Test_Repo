@EditEMEATimeschedule
Feature: Edit Time schedule
As an user 
I want to Edit Time schedule
so that my home temperature will get set automatically all days based on the new schedule settings for my status in home like arrival time,leaving time,sleep time and wake time   

@ViewTimeschedulePrimarycardEMEA @PendingToAutomateForIOS @--xrayid:ATER-7526
  Scenario: To view schedule state in primary card if time schedule is available for EMEA stat for time format 24 and 12hr
    Given user has "time scheduling" mode
    And user logs in to Lyric app
     Then user verifies the following on the primary card:
      | Features               | 
      | Scheduling Icon        |
      | Following schedule      | 
    And user logs out of the app
  
  @AddTimeschedulePeriodEMEA @PendingToAutomateForIOS @--xrayid:ATER-7527
  Scenario Outline:To view the option to add schedule period for EMEA stat 
     Given user has "time schedule" with <Periods>
     And user logs in to Lyric app
     When user selects schedule icon from primary card
     And user selects view by "Grouped Days" 
     Then verify user is shown with an option to add period for a day to accommodate maximum of six periods
     And user logs out of the app
    Examples: 
      | Periods   | 
      #| 1 period  | 
      | 2 periods | 
      #| 3 periods | 
      #| 4 periods | 
      #| 5 periods | 
      #| 6 periods | 
  
  @DeleteTimeschedulePeriodGroupedDaysEMEA @PendingToAutomateForIOS @--xrayid:ATER-7528
  Scenario: Verify User should have atleast one schedule period in set of grouped days 
    Given user has "time scheduling" mode
     And user logs in to Lyric app 
     When user selects schedule icon from primary card
      And user selects view by "Grouped Days"
      And user tries to delete "All Periods" in EMEA schedule screen
     Then Verify user should have atleast "One" schedule period in "Grouped days" view
      And user logs out of the app
  
  @DeleteTimeschedulePeriodIndividualDaysEMEA @PendingToAutomateForIOS @--xrayid:ATER-7529
  Scenario: Verify User should have atleast two schedule period in set of individual days 
    Given user has "time schedule" with "1 Period"
     And user logs in to Lyric app
     When user selects schedule icon from primary card
     And user selects view by "Individual days"
      And user tries to delete one of the schedule period of the last two schedule period
     Then verify user should have atleast "Two" schedule period in "Individual days" view
     And user logs out of the app
  
  @EditEndtimeTimeschedulePeriodEMEA @PendingToAutomateForIOS @--xrayid:ATER-7530
  Scenario Outline:Verify User should not be allowed to edit end time of last period in a day 
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
      And user selects view by <Type>
      And user selects the last schedule period of a day
     Then verify user should not be allowed to edit end time
     And user logs out of the app
      Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  @EditEndtimeEMEA @PendingToAutomateForIOS @--xrayid:ATER-7531
  Scenario Outline:Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
      And user selects view by <Type>
      And user selects any schedule period in a day
     Then verify user should not be allowed to edit end time with same as start time
      #And verify end time is atleast "10 min" after the start time
      And user logs out of the app
     Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  #Not an use case 
  @EditStarttimeForSingleperiodEMEA @PendingToAutomate 
  Scenario: Verify User edit start time with atleast 10 min less than next day period start value which is the end time of last period
    Given "Time schedule" is available 
     When User is in view schedule screen
      And User selects View by <Days>
      And User selects the last schedule period in a day
      And User sets time value atleast 10 min less than next day period start value which is the end time of last period 
     Then Verify day with single period having the same value for start time and end time as entered
      | Type            | 
      | Grouped days    | 
      | Individual days | 
  
  #Not an use case 
  @EditEndtimeForchangeperiodEMEA @PendingToAutomate 
  Scenario: Verify User edit end time such that it engulf some schedule period 
    Given "Time schedule" is available 
     When User is in view schedule screen
      And User selects View by <Days>
      And User edit end time such that it engulf some schedule period 
     Then Verify period inbetween the set time value gets engulfed by edited period
      And the next period start date updated with end date of edited schedule period
      | Type            | 
      | Grouped days    | 
      | Individual days | 
  
  @EditStarttimeEMEA @PendingToAutomate @--xrayid:ATER-7532
  Scenario Outline: Verify User should not be allowed to edit start time and end time with same time value for both time format
    Given user has "time scheduling" mode
     And user logs in to Lyric app 
     When user selects schedule icon from primary card
      And user selects view by <Type>
      And user selects any schedule period in a day
     Then verify user should be allowed to edit start time with all possible values for both time formats
     And user logs out of the app
     Examples:
      | Type            | 
      | Grouped days    | 
      | Individual days |
  
  #Not an use case 
  @AddscheduleperiodEMEA @PendingToAutomate 
  Scenario: Verify User add schedule by editting end time such that it engulf some schedule period 
    Given "Time schedule" is available 
     When User is in view schedule screen
      And User clicks "Add Period"
      And User sets time value by editting end time such that it engulf some schedule period 
     Then Verify period inbetween the set time value engulfed
      And The next period start date updated with end date of edited schedule period
      | Type            | 
      | Grouped days    | 
      | Individual days | 
  
  @ViewTimescheduleGroupedDaysEMEA @PendingToAutomateForIOS @--xrayid:ATER-7533
  Scenario Outline:To view the options in schedule screen for EMEA stat 
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
      And user selects view by "Individual Days" 
      And edit the schedule periods of <Days>
      And user selects view by "Grouped Days" 
     Then verify Time schedule grouped with edited <Days> separately and remaining separately
     And user logs out of the app
    Examples: 
      | Days       | 
      | One day    | 
      | Two days   | 
      | Three days | 
      | Four days  | 
      | Five days  | 
      | Six days   | 
  
  @ViewTimescheduleIndividualdaysEMEA @PendingToAutomateForIOS @--xrayid:ATER-7534
  Scenario: To view the Time schedule for EMEA stat
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
      And user selects view by "Individual Days"
     Then verify user shown with Time schedule list in Individual days view
     And user logs out of the app
  
  @OfflineSchedulescreenEMEA @NotAutomatable
  Scenario Outline:To get error message when Stat is Offline and try to fetch schedule information in solution card and in schedule screen
    Given With the <Condition>
     When User try to fetch schedule information in primary card 
      But User try to fetch schedule information in schedule screen 
     Then Verify for the error message
    Examples: 
      | Condition    | 
      | LCC/TCC down | 
      | Stat offline | 
  
  @NetworkdownSchedulescreenEMEA @NotAutomatable
  Scenario Outline:To get error messages on network down 
    Given With the <Condition>
     When User try to fetch schedule information in primary card 
      But User try to fetch schedule information in schedule screen
     Then Verify the error message
    Examples: 
      | Condition                        | 
      | Mobile internet(3G/wifi) is down | 
      | Mobile in Airplane mode          | 
      | Mobile with low signal(3G/wifi)  | 
      | CHIL down                        | 
      | Switching between the network    | 
      | Smart network switch             | 
  
  @EditTimescheduleTemperatureEMEA @PendingToAutomateForIOS @--xrayid:ATER-7535
  Scenario Outline: To Edit time schedule period by setting up with new temperature value for stat 
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
     And user selects view by <Type> 
    When user edit "Time" schedule by changing temperature to <Temperature> value
     Then verify edited period temperature is set within the maximum and minimum range
      And verify temperature is incremental by 0.5C for celsius
      And verify "Time" schedule successfully gets edited
      And user logs out of the app 
    Examples: 
      | Type            | Temperature   | 
      #| Grouped days    | Above Maximum | 
      #| Grouped days    | Below Minimum | 
      #| Grouped days    | At Maximum    | 
      #| Grouped days    | At Minimum    | 
      | Grouped days    | within range  | 
      #| Individual days | Above Maximum | 
      #| Individual days | Below Minimum | 
      #| Individual days | At Maximum    | 
      #| Individual days | At Minimum    | 
      #| Individual days | within range  | 
  
  @EditTimescheduleTimeformatEMEA @PendingToAutomateForIOS @--xrayid:ATER-7536
  Scenario Outline: To Edit Time schedule by setting up with new time value for time format 24 and 12hr
    Given user has "time scheduling" mode
     And user logs in to Lyric app
     When user selects schedule icon from primary card
      And user selects view by <Type>
     When user edit Time schedule by changing with new time value
     Then verify "Time" schedule successfully gets edited 
      And verify the time fields can be set with increments of "10 minutes"
      And user logs out of the app
      Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  @CancellingDeleteperiodEMEA @Automated @--xrayid:ATER-7537
  Scenario Outline: To verify the confirmation message for deleting period for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user has "time scheduling" mode
    And user logs in to Lyric app
    When user selects schedule icon from primary card
    And user selects view by <Type>
     When user edit Time schedule by deleting "Atleast 1 period"
     Then verify the dialog box message for period deletion
     And verify the period is "not deleted" on "canceling" the period deletion
     And user logs out of the app
     Examples:
      | Type            | 
      | Grouped days    | 
      | Individual days | 
  
  