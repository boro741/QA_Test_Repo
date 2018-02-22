@CreateNATimeschedule
Feature: Create Time schedule
As an user 
I want to create Time schedule [post installation] 
so that my home temperature will get set automatically all days based on the time and time window is configured for my status in home like arrival time,leaving time,sleep time and wake time   

@CreateNAEverydayscheduleSinglestatDefaultvalue @Automated @--xrayid:ATER-7491
  Scenario Outline: To create Everyday schedule with default schedule value for systems
  As an user
  I want to create Everyday schedule with default schedule value with time format 24or12hr
    Given user thermostat is set to <scheduling> schedule  
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "Everyday" schedule with default schedule value
     Then verify "Everyday" schedule gets created successfully
     #And user logs out of the app 
    Examples: 
      | scheduling      | 
      | no              | 
      #| Time scheduling | 
  
  @CreateNAWeekdayandWeekendTimebasedscheduleSinglestatDefaultvalue @Automated @--xrayid:ATER-7492
  Scenario Outline: To create Weekday and Weekend schedule with default schedule value for systems
  As an user
  I want to create Weekday and Weekend schedule with default schedule value 
    Given user thermostat is set to <scheduling> schedule  
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "Weekday and Weekend" schedule with default schedule value
     Then verify "Weekday and Weekend" schedule gets created successfully
#     And user logs out of the app 
    Examples: 
      | scheduling      | 
      #| No scheduling   | 
      | Time scheduling | 
  
  @CautionmessageNATochangeGeofencescheduleFromWeekdayandWeekendscheduleCancels @Automated @--xrayid:ATER-7493
  Scenario: Caution message cancelled to change Weekday and Weekend schedule From Geofence schedule for systems
  As an user
  I should get caution message to convey i will be turned to Weekday and Weekend schedule over Geofence schedule by cancelling geofence schedule retained 
    Given user thermostat is set to "geofence based" schedule
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "Cancel" option while creating "Weekday and Weekend" schedule with default schedule value
     Then verify "Geofence" schedule is retained
#     And user logs out of the app
  
  @CautionmessageNATochangeGeofencescheduleFromWeekdayandWeekendscheduleConfirms @Automated @--xrayid:ATER-7494
  Scenario: Caution message confirmed to change Weekday and Weekend schedule From Geofence schedule for systems
  As an user
  I should get caution message to convey i will be turned to Weekday and Weekend schedule over Geofence schedule 
    Given user thermostat is set to "geofence based" schedule
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "Confirm" option while creating "Weekday and Weekend" schedule with default schedule value
     Then verify "Weekday and Weekend" schedule gets created successfully
#     And user logs out of the app
  
  @CautionmessageNATochangeGeofencescheduleFromEverydayscheduleCancels @Automated @--xrayid:ATER-7495
  Scenario: Caution message cancelled to change Everyday schedule From Geofence schedule for systems
  As an user
  I should get caution message to convey i will be turned to Everyday schedule over Geofence schedule and by cancelling geofence schedule retained 
    Given user thermostat is set to "geofence based" schedule
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "Cancel" option while creating "Everyday" schedule with default schedule value
     Then verify "Geofence" schedule is retained
#     And user logs out of the app
  
  @CautionmessageNATochangeGeofencescheduleFromEverydayscheduleConfirms @Automated @--xrayid:ATER-7496
  Scenario: Caution message confirmed to change Everyday schedule From Geofence schedule for systems
  As an user
  I should get caution message to convey i will be turned to Everyday schedule over Geofence schedule with time format 24or12hr
    Given user thermostat is set to "geofence based" schedule
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "Confirm" option while creating "Everyday" schedule with default schedule value
     Then verify "Everyday" schedule gets created successfully
#     And user logs out of the app
  
  @CreateNAEverydayscheduleTemperatureRange @Automated @--xrayid:ATER-7497
  Scenario Outline: To create Everyday schedule by setting up with new temperature value for systems 
  As an user
  I want to create Everyday schedule by setting up with new temperature value from default schedule value
  	Given user launches and logs in to the Lyric application
  	And user selects "Jasper device" from the dashboard
     When user creates "Everyday" schedule by setting temperature value to <Temperature>
     Then verify "Everyday" schedule gets created successfully 
      And verify temperature is set within the maximum and minimum range
#      And user logs out of the app
    Examples: 
      | Temperature   | 
      | Above Maximum | 
      | Below Minimum | 
      | At Maximum    | 
      | At Minimum    | 
      | within range  | 
  
  @CreateNAWeekdayandWeekendscheduleTemperatureRange @Automated @--xrayid:ATER-7498
  Scenario Outline: To configure Weekday and Weekend schedule by setting up with new temperature value for systems 
  I want to create Weekday and Weekend schedule by setting up with new temperature value from default schedule value
  	Given user launches and logs in to the Lyric application
  	And user selects "Jasper device" from the dashboard
     When user creates "Weekday and Weekend" schedule by setting temperature value to <Temperature>
     Then verify "Weekday and Weekend" schedule gets created successfully 
      And verify temperature is set within the maximum and minimum range
#      And user logs out of the app
    Examples: 
      | Temperature   | 
#      | Above Maximum | 
#      | Below Minimum | 
#      | At Maximum    | 
#      | At Minimum    | 
      | within range  | 
  
  @CreateNAEverydayscheduleTimeformat @Automated @--xrayid:ATER-7499
  Scenario: To create Everyday Time schedule by setting up with new time value for systems
  As an user
  I want to create Everyday schedule by setting up with new time value for both format
  	Given user logs in to Lyric app
     When user creates "Everyday" schedule by changing with new time values
     Then verify "Everyday" schedule gets created successfully 
      And verify the time fields can be set with increments of "15 minutes"
      And user logs out of the app
  
  @CreateNAEverydayscheduleDeletingperiod @Automated  @--xrayid:ATER-7500
  Scenario Outline: To create Everyday schedule by deleting period for systems with all possible modes and time formats
  As an user
  I want to create Everyday schedule by deleting new period
  	Given user logs in to Lyric app
     When user creates "Everyday" schedule by deleting <Period> from the default schedule values
     Then verify "Everyday" schedule gets created successfully
     And user logs out of the app
    Examples: 
      | Period           | 
      #| Atleast 1 period | 
      #| Atleast 2 period | 
      | Atleast 3 period | 
  
  @CreateNAEverydayscheduleDeletingAllperiod @Automated @--xrayid:ATER-7501
  Scenario: To verify by deleting all periods for systems with all possible modes and time formats
  As an user
  I want to redirected to scheduling selection screen
  	Given user logs in to Lyric app 
     When user creates "Everyday" schedule by deleting "All 4 periods" from the default schedule values
     Then verify app redirected to scheduling selection screen
     And user logs out of the app
  
  @CreateNAWeekdayandWeekendscheduleDeletingperiod @Automated @--xrayid:ATER-7502
  Scenario Outline: To create Everyday schedule by deleting period for systems
  As an user
  I want to create Weekday and Weekend schedule by deleting new period
  	Given user logs in to Lyric app
     When user creates "Weekday and Weekend" schedule by deleting <Period> from the default schedule values
     Then verify "Weekday and Weekend" schedule gets created successfully
     And user logs out of the app
    Examples: 
      | Period           | 
      | Atleast 1 period | 
      | Atleast 2 period | 
      #| Atleast 3 period | 
  
  @CreateNAWeekdayandWeekendscheduleDeletingAllperiod @Automated @--xrayid:ATER-7503
  Scenario: To verify by deleting all periods for systems
  As an user
  I want to redirected to scheduling selection screen
  	Given user logs in to Lyric app
     When user creates "Weekday and Weekend" schedule by deleting "All 4 periods" from the default schedule values
     Then verify app redirected to scheduling selection screen
     And user logs out of the app
  
  @CreateNAWeekdayandWeekendscheduleTimeformat @Automated @--xrayid:ATER-7504
  Scenario: To configure Weekday and Weekend schedule by setting up with new time value for systems Heat cool,Cool,Heat for Temperature scale Celsius or Fahrenheit and for time format 24or12hr
  As an user
  I want to create Weekday and Weekend schedule by setting up with new time value for all time format
  	Given user logs in to Lyric app
     When user creates "Weekday and Weekend" schedule by changing with new time values
     Then verify "Weekday and Weekend" schedule gets created successfully 
      And verify the time fields can be set with increments of "15 minutes"
      And user logs out of the app
  
  @ErrormessageCreateEverydayscheduleNA @NotAutomatable
  Scenario Outline:To get error messages on system unavailability
  As an user
  I want to get error messages on system unavailability to create schedule with time format 24or12hr 
  So that i will get notified on system unavailability
    Given With the <Condition>
     When User creates "Everyday" schedule with default schedule value  
     Then Verify the error message
    Examples: 
      | Condition                        | 
      | Mobile internet(3G/wifi) is down | 
      | Mobile in Airplane mode          | 
      | Mobile with low signal(3G/wifi)  | 
      | CHIL down                        | 
      | LCC Down                         | 
      | Stat offline                     | 
  
  @ErrormessageCreateWeekday&WeekdayscheduleNA @NotAutomatable
  Scenario Outline:To get error messages on system unavailability
  As an user
  I want to get error messages on system unavailability to create Weekday and Weekday schedule with time format 24or12hr  
  So that I will get notified on system unavailability
    Given With the <Condition>
     When User Creates "Weekday and Weekday" schedule with default schedule value  
     Then Verify the error message
    Examples: 
      | Condition                        | 
      | Mobile internet(3G/wifi) is down | 
      | Mobile in Airplane mode          | 
      | Mobile with low signal(3G/wifi)  | 
      | CHIL down                        | 
      | LCC Down                         | 
      | Stat offline                     | 
  
  @CreateNAEverydayscheduleMultistatCopyschedule @Automated @--xrayid:ATER-7505
  Scenario Outline: Copying schedule to stats in location for systems with all possible modes and time formats
  As an user
  I want to create Everyday schedule for stat in my location
  	Given user logs in to Lyric app
     When user creates default "Everyday" schedule value <Copying> stats
     Then verify "Everyday" schedule gets created successfully
      And verify "Everyday" schedule is <Verifycopy> stats
      And user logs out of the app
    Examples: 
      | Copying                            | Verifycopy           | 
      | without copying to other           | not copied to other  | 
      #| by copying schedule to all         | copied to all other  | 
      #| by copying schedule to selected    | copied to selected   | 
  
  @CreateNAWeekdayandWeekendscheduleMultistatCopyschedule @Automated @--xrayid:ATER-7506
  Scenario Outline: Copying schedule to stats in location for systems with all possible modes and time formats
  As an user
  I want to create Weekday and Weekend schedule for stat in my location
  	Given user logs in to Lyric app
     When user creates default "Weekday and Weekend" schedule value <Copying> stats
     Then verify "Weekday and Weekend" schedule gets created successfully 
      And verify "Weekday and Weekend" schedule is <Verifycopy> stats
      And user logs out of the app
    Examples: 
      | Copying                            | Verifycopy           | 
      #| without copying to other           | not copied to other  | 
      | by copying schedule to all         | copied to all other  | 
      #| by copying schedule to selected    | copied to selected   | 
