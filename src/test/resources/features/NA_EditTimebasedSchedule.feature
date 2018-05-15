@EditNATimeschedule
Feature: Edit Time schedule
As an user 
I want to Edit Time schedule
so that my home temperature will get set automatically all days based on the new schedule settings for my status in home like arrival time,leaving time,sleep time and wake time   

@ViewTimeschedulePrimarycardNA @FlyAutomated @--xrayid:ATER-7585
  Scenario: To view schedule state in primary card if time schedule is available for stat with systems Heat cool,Cool,Heat and Temperture scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule 
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
    Then user verifies the following on the primary card:
      | Features               | 
      | Scheduling Icon        |
      | Following schedule      | 
    And user logs out of the app
  
  @ViewTimescheduledefaultNA @FlyAutomated @--xrayid:ATER-7586
  Scenario Outline: To view the Time schedule for stat with systems Heat cool,Cool,Heat and Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
     Given user thermostat is set to "time based" schedule 
    And user launches and logs in to the Lyric application
     When user selects "Jasper device" from the dashboard
      And user selects view by "Individual Days" 
      And user edits the schedule periods of <Days>
      And user selects "Jasper device" from the dashboard
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
  
  @ViewTimescheduleIndividualdaysNA @FlyAutomated @RunManual @--xrayid:ATER-7587
  Scenario: To view the Time schedule for stat with systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr in individual days
    Given user thermostat is set to "time based" schedule 
    And user launches and logs in to the Lyric application
     When user selects "Jasper device" from the dashboard
      And user selects view by "Individual Days"
     Then verify user shown with Time schedule list in Individual days view
     And user logs out of the app
  
  @OfflineSchedulescreenNA @NotAutomatable
  Scenario Outline:To get error message when Stat is Offline and try to fetch schedule information in solution card and in schedule screen
    Given With the <Condition>
     When User try to fetch schedule information in primary card 
      But User try to fetch schedule information in schedule screen 
     Then Verify for the error message
    Examples: 
      | Condition    | 
      | LCC/TCC down | 
      | Stat offline | 
  
  @NetworkdownSchedulescreenNA @NotAutomatable
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
  
  @EditTimescheduleTemperatureNA @FlyAutomated @--xrayid:ATER-7588
  Scenario Outline: To Edit time schedule by setting up with new temperature value for stat with systems Heat cool,Cool,Heat and Temperature scale Celsius or Fahrenheit 
   Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
     And user selects view by <Type>
     When user edit "Time" schedule by changing temperature to <Temperature> value
     And user selects "Jasper device" from the dashboard
     Then verify edited period temperature is set within the maximum and minimum range
      And verify edited period temperature is incremental by 1F for fahrenheit and 0.5C for celsius
      And user selects "Jasper device" from the dashboard
      And verify "Time" schedule successfully gets edited
      And user logs out of the app
    Examples: 
      | Type            | Temperature   | 
      | Grouped days    | Above Maximum | 
      | Grouped days    | Below Minimum | 
      | Grouped days    | At Maximum    | 
      | Grouped days    | At Minimum    | 
      | Grouped days    | within range  | 
      | Individual days | Above Maximum | 
      | Individual days | Below Minimum | 
      | Individual days | At Maximum    | 
      | Individual days | At Minimum    | 
      | Individual days | within range  | 
  
  @EditTimescheduleTemperatureEverydayAutochangeoverEnabledNA @FlyAutomated @--xrayid:ATER-7589
  Scenario Outline: To edit time schedule by setting up with new temp value for systems Heat cool for Temp scale c F 
  #and for time format 24/12hr with autochangeover enabled
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
     And user selects view by <Type>
     When user edit "Time" schedule by changing temperature to <Temperature> value
     And user selects "Jasper device" from the dashboard
     Then verify edited period temperature is set within the maximum and minimum range
      And verify edited period temperature is incremental by 1F for fahrenheit and 0.5C for celsius
      And user selects "Jasper device" from the dashboard
      And verify cool set point is always greater than or equal to heat set point
      And user selects "Jasper device" from the dashboard
      And verify "Time" schedule successfully gets edited
      And user logs out of the app
    Examples: 
      | Type            | Temperature   | 
      | Grouped days    | Above Maximum | 
#      | Grouped days    | Below Minimum | 
#      | Grouped days    | At Maximum    | 
#      | Grouped days    | At Minimum    | 
#      | Grouped days    | within range  | 
#      | Individual days | Above Maximum | 
#      | Individual days | Below Minimum | 
#      | Individual days | At Maximum    | 
#      | Individual days | At Minimum    | 
#      | Individual days | within range  | 
  
  @EditTimescheduleTimeformatNA @NOTFlyAutomated @--xrayid:ATER-7590
  Scenario Outline: To Edit Time schedule by setting up with new time value for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
    And user selects view by <Type>
     When user edit Time schedule by changing with new time value
     And user selects "Jasper device" from the dashboard
     Then verify "Time" schedule successfully gets edited 
#     And user selects "Jasper device" from the dashboard
#      And verify the time fields can be set with increments of "15 minutes"
      And user logs out of the app
      Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  @EditTimescheduleOverlapTwoPeriodsNA @FlyAutomated @--xrayid:ATER-7591
  Scenario Outline: The Time schedule removed if setting up with time value as same as it for new time schedule for systems Heat cool,Cool,Heat 
  #for Temperature scale C or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
    And user selects view by <Type>
     When user edit "Wake" period by changing time value to "06 00 AM"
     And user selects "Jasper device" from the dashboard
     And user edit "Away" period by changing time value to "06 00 AM"
     And user selects "Jasper device" from the dashboard
     Then verify "Time" schedule successfully gets edited 
     And user selects "Jasper device" from the dashboard
      And Verify if "Wake" period time value is displayed as "Tap to set"
      And user logs out of the app
      Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  @CancellingDeleteperiodNA @FlyAutomated @--xrayid:ATER-7592
  Scenario Outline: To verify the confirmation message for deleting period for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
    And user selects view by <Type>
     When user edit Time schedule by deleting "Atleast 1 period"
     Then verify the dialog box message for period deletion
     And verify the period is "not deleted" on "canceling" the period deletion
     And user logs out of the app
     Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 
  
  @EditTimescheduleDeletingperiodNA @FlyAutomated @--xrayid:ATER-7593
  Scenario Outline: To edit Time schedule by deleting period for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
    When user selects view by <Type>
     And user edit Time schedule by deleting <Period> on confirming the period deletion
     And user selects "Jasper device" from the dashboard
     Then verify "Time" schedule successfully gets edited
     And user logs out of the app
    Examples: 
      | Type            | Period           | 
      #| Grouped days    | Atleast 1 period | 
      | Grouped days    | Atleast 2 period | 
      #| Grouped days    | Atleast 3 period | 
      #| Individual days | Atleast 1 period | 
      #| Individual days | Atleast 2 period | 
      #| Individual days | Atleast 3 period | 
  
  @EditTimescheduleDeletingallperiodNA @FlyAutomated @--xrayid:ATER-7594
  Scenario Outline: To edit Time schedule by deleting all period for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24 or 12hr
    Given user thermostat is set to "time based" schedule
     And user launches and logs in to the Lyric application
     And user selects "Jasper device" from the dashboard
    And user selects view by <Type>
     When user edit Time schedule by deleting "All 4 periods" on confirming the period deletion
     And user selects "Jasper device" from the dashboard
     Then verify "No Schedule" screen is shown in view schedule screen
     And user logs out of the app
     Examples:
      | Type            | 
      | Grouped days    | 
      #| Individual days | 