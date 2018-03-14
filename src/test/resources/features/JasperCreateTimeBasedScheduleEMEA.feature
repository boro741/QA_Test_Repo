@CreateEMEATimeschedule
Feature: Create Time schedule
As an user 
I want to create Time schedule [post installation] 
so that my home temperature will get set automatically all days based on the time and time window is configured for my status in home like arrival time,leaving time,sleep time and wake time   

@CreateEMEAEverydayscheduleSinglestatDefaultvalue  @--xrayid:ATER-7431
  Scenario Outline: To create EMEA Everyday schedule with default schedule value
  As an user
  I want to create Everyday schedule with default schedule value with time format 24or12hr
  So that my temperature will get set automatically based on the time
    Given user thermostat is set to <scheduling> schedule  
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "Everyday" schedule with default schedule value
     Then verify "Everyday" schedule gets created successfully
#     And user logs out of the app 
     Examples: 
      | scheduling          | 
      | no       |
      #| time based    | 
      | geofence based |
  
  @CreateEMEAWeekdayandWeekendTimebasedscheduleSinglestatDefaultvalue  @--xrayid:ATER-7432
  Scenario Outline: To create EMEA Weekday and Weekend schedule with default schedule value
  As an user
  I want to create Weekday and Weekend schedule with default schedule value with time format 24or12hr
  so that my temperature will get set automatically based on the time
    Given user thermostat is set to <scheduling> schedule 
     And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "weekday and weekend" schedule with default schedule value
     Then verify "weekday and weekend" schedule gets created successfully 
#     And user logs out of the app
    Examples: 
      | scheduling          | 
      #| no scheduling       |
      | time based    | 
      #| geofence based |
  
  @CautionmessageTochangeGeofencescheduleFromWeekdayandWeekendscheduleCancels  @--xrayid:ATER-7433
  Scenario: Caution message cancelled to change EMEA Weekday and Weekend schedule From Geofence schedule 
  As an user
  I should get caution message to convey i will be turned to Weekday and Weekend schedule over Geofence schedule with time format 24or12hr and by cancelling geofence schedule retained 
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "cancel" option while creating "weekday and weekend" schedule with default schedule value
     Then verify "Geofence" schedule is retained
#      And user logs out of the app
  
  #@CautionmessageTochangeGeofencescheduleFromWeekdayandWeekendscheduleConfirms  @--xrayid:ATER-7434
  Scenario: Caution message confirmed to change EMEA Weekday and Weekend schedule From Geofence schedule 
  As an user I should get caution message to convey i will be turned to Weekday and Weekend schedule over Geofence schedule with time format 24or12hr
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "confirm" option while creating "weekday and weekend" schedule with default schedule value
     Then verify "weekday and weekend" schedule gets created successfully
#     And user logs out of the app
  
  #@CautionmessageTochangeGeofencescheduleFromEverydayscheduleCancels  @--xrayid:ATER-7435
  Scenario: Caution message cancelled to change EMEA Everyday schedule From Geofence schedule 
  As an user
  I should get caution message to convey i will be turned to Everyday schedule over Geofence schedule with time format 24or12hr and by cancelling geofence schedule retained 
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "cancel" option while creating "everyday" schedule with default schedule value
     Then verify "geofence" schedule is retained
#      And user logs out of the app
  
  @CautionmessageTochangeGeofencescheduleFromEverydayscheduleConfirms  @--xrayid:ATER-7436
  Scenario: Caution message confirmed to change EMEA Everyday schedule From Geofence schedule 
  As an user
  I should get caution message to convey i will be turned to Everyday schedule over Geofence schedule with time format 24or12hr
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user selects "confirm" option while creating "everyday" schedule with default schedule value
     Then verify "everyday" schedule gets created successfully
#      And user logs out of the app
  
  @CreateEMEAEverydayscheduleTemperatureRange  @--xrayid:ATER-7437
  Scenario Outline: To create EMEA Everyday schedule by setting up with new temperature value
  As an user
  I want to create Everyday schedule by setting up with new temperature value from default schedule value
  so that my temperature will get set automatically based on arrival or exit home
    Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "everyday" schedule by setting temperature value to <temperature value>
     Then verify "everyday" schedule gets created successfully 
      And verify temperature is set within the maximum and minimum range
      And user logs out of the app
    Examples: 
      | temperature value | 
      #| Above Maximum     | 
      #| Below Minimum     | 
      #| At Maximum        | 
      #| At Minimum        | 
      | Within range      |
  
  @CreateEMEAWeekdayandWeekendscheduleTemperatureRange  @--xrayid:ATER-7438
  Scenario Outline: To configure EMEA Weekday and Weekend schedule by setting up with new temperature value
  As an user
  I want to create Weekday and Weekend schedule by setting up with new temperature value from default schedule value
  so that my temperature will get set automatically based on arrival or exit home
    Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "weekday and weekend" schedule by setting temperature value to <temperature value>
     Then verify "weekday and weekend" schedule gets created successfully 
      And verify temperature is set within the maximum and minimum range
      And user logs out of the app
   Examples: 
      | temperature value | 
      #| Above Maximum     | 
      #| Below Minimum     | 
      #| At Maximum        | 
      | At Minimum        | 
      #| Within range      |
  
  @CreateEMEAEverydayscheduleTimeformat  @--xrayid:ATER-7439
  Scenario: To create EMEA Everyday Time schedule by setting up with new time value for both time format
  As an user
  I want to create Everyday schedule by setting up with new time value for both format
   Given user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "everyday" schedule by changing with new time values
     Then verify "everyday" schedule gets created successfully 
      And verify the time fields can be set with increments of "10 minutes"
#      And user logs out of the app
  
  @CreateEMEAEverydayscheduleAddingperiod  @--xrayid:ATER-7440
  Scenario: To create EMEA Everyday schedule by setting up with new time value for both time format
  As an user
  I want to create Everyday schedule by adding new period for both time format
    Given user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "everyday" schedule by adding period to the default schedule values
     Then verify "everyday" schedule gets created successfully
#      And user logs out of the app
#  
  @CreateEMEAEverydayscheduleDeletingperiod  @--xrayid:ATER-7441
  Scenario: To create EMEA Everyday schedule by setting up with new time value for both time format
  As an user
  I want to create Everyday schedule by deleting new period for both time format
    Given user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "everyday" schedule by deleting period from the default schedule values
     Then verify "everyday" schedule gets created successfully
#      And user logs out of the app
  
  #@CreateEMEAWeekedayandWeekendscheduleAddingperiod  @--xrayid:ATER-7442
  Scenario: To create EMEA Weekeday and Weekend schedule by setting up with new time value for time format 12hr
  As an user
  I want to create Weekeday and Weekend schedule by adding new period for both time format
  so that my temperature will get set automatically based on scheduled time
    Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "weekday and weekend" schedule by adding period to the default schedule values
     Then verify "weekday and weekend" schedule gets created successfully
#     And user logs out of the app
  
  @CreateEMEAWeekedayandWeekendscheduleDeletingperiod  @--xrayid:ATER-7443
  Scenario: To create EMEA Weekeday and Weekend schedule by setting up with new time value for time format 12hr
  As an user
  I want to create Weekeday and Weekend schedule by deleting new period for both time format
  so that my temperature will get set automatically based on scheduled time
    Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user creates "weekday and weekend" schedule by deleting period from the default schedule values
     Then verify "weekday and weekend" schedule gets created successfully
#      And user logs out of the app
  
  @CreateEMEAWeekdayandWeekendScheduleTimeformat @Automated @--xrayid:ATER-7444
  Scenario: To configure EMEA Weekday and Weekend schedule by setting up with new time value for both format
  As an user
  I want to create Weekday and Weekend schedule by setting up with new time value for both format
    Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
    When user creates "Weekday and Weekend" schedule by changing with new time values
     Then verify "weekday and weekend" schedule gets created successfully 
      And verify the time fields can be set with increments of "10 minutes"
#      And user logs out of the app
  
  @CreateEMEAEverydayscheduleMultistatCopyschedule @Automated @--xrayid:ATER-7445
  Scenario Outline: Create EMEA Everyday Schedule MultiStat Copy
  As an user
  I want to create Everyday schedule for stat in my location 
  So that my temperature will get set automatically for all stat in my location based on the time
   Given user thermostat is set to "no" schedule
      And user launches and logs in to the Lyric application
     When user creates default "Everyday" schedule value <copying> stats
	 Then verify "Everyday" schedule gets created successfully
      And verify "Everyday" schedule is <verify copy> stats
#      And user logs out of the app
     Examples: 
      | copying                           | verify copy         | 
      | without copying schedule to other | not copied to other | 
      #| by copying schedule to all        | copied to all other | 
      #| by copying schedule to selected   | copied to selected  | 
  
  @CreateEMEAWeekdayandWeekendscheduleMultistatCopyschedule @Automated @--xrayid:ATER-7446
  Scenario Outline: Create EMEA Weekday and Weekend Schedule MultiStat Copy
  As an user
  I want to create Weekday and Weekend schedule for stat in my location 
  So that my temperature will get set automatically for all stat in my location based on the time
    Given user thermostat is set to "no" schedule
   And user launches and logs in to the Lyric application
     When user creates default "weekday and weekend" schedule value <copying> stats
     Then verify "weekday and weekend" schedule gets created successfully
      And verify "weekday and weekend" schedule is <verify copy> stats
#      And user logs out of the app
     Examples: 
      | copying                           | verify copy         | 
      #| without copying schedule to other | not copied to other | 
      | by copying schedule to all        | copied to all other | 
      #| by copying schedule to selected   | copied to selected  | 
  
  @ErrormessageCreateEverydayscheduleEMEA @NotAutomatable
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
  
  @ErrormessageConfigureWeekdayandWeekdayscheduleEMEA @NotAutomatable
  Scenario Outline:To get error messages on system unavailability
  As an user
  I want to get error messages on system unavailability to create Weekday and Weekday schedule with time format 24or12hr  
  So that I will get notified on system unavailability
    Given With the <condition>
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
  
