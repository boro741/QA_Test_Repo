@EditNAGeofenceschedule
Feature: Edit geofence based scheduling
As an user 
I want to edit geofence schedule [post installation] 
so that my home temperature will get set automatically based on edited geofence schedule settings

@EditNAGeofencescheduleWithSleepsettings1 @FlyAutomated @--xrayid:ATER-7577
  Scenario Outline: To Edit geofence schedule for systems with all possible formats
  As an user
  I want to edit geofence schedule value
    Given "geofence" schedule "With" sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule by editing <Values>
     Then verify "geofence" schedule successfully gets edited
     And user logs out of the app
    Examples: 
      | Values         | 
      | Home settings  | 
      | Sleep settings | 
#      | Away settings  | 
  
  @EditNAGeofencescheduleWithSleepsettings @FlyAutomated @--xrayid:ATER-7578
  Scenario Outline:To Edit geofence schedule without sleep settings for systems Heat cool,Cool,Heat for Temperature scale Celsius (or) Fahrenheit and for time format 24 (or) 12hr
  As an user
  I want to edit geofence schedule 
  So that my temperature will get set automatically based on new geofence scheduling settings
    Given "geofence" schedule "Without" sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule by editing <Values>
     Then verify "geofence" schedule successfully gets edited
     And user logs out of the app
    Examples: 
      | Values        | 
      | Home settings | 
      #| Away settings | 
  
  @EditNAGeofenceschedulevaluesleepsettings @FlyAutomated @--xrayid:ATER-7579
  Scenario Outline:To Edit geofence schedule sleep settings
  As an user
  I want to edit geofence schedule 
  So that my temperature will get set automatically based on new geofence scheduling settings
    Given "geofence" schedule <Condition> sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edits "geofence" schedule by <Values>
     Then verify "geofence" schedule successfully gets edited
     And user logs out of the app
    Examples: 
      | Condition | Values   | 
      | With      | Deleting | 
      | Without   | Adding   | 
  
  @EditNAGeofencescheduleTemperatureRange @FlyAutomated @--xrayid:ATER-7580
  Scenario Outline: To edit Geofence schedule by setting up with new temperature value for systems with all possible formats
  As an user
  I want to edit Geofence schedule by editing with new temperature value
    Given "geofence" schedule "With" sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule by changing temperature to <Temperature> value 
     Then verify "geofence" schedule successfully gets edited
     And user selects "Jasper device" from the dashboard
      And verify temperature is set within the maximum and minimum range
      And verify temperature is incremental by 1F for fahrenheit and 0.5C for celsius
       And user logs out of the app
    Examples: 
      | Temperature   | 
      #| Above Maximum | 
      | Below Minimum | 
      #| At Maximum    | 
      #| At Minimum    | 
      #| Within range  | 
  
  @EditNAGeofencescheduleSinglestatTemperatureAutochangeoverEnabled @FlyAutomated @--xrayid:ATER-7581
  Scenario Outline: To edit geofence schedule by setting up with new temperature value for systems with all possible formats
  As an user
  I want to edit geofence schedule by editing with new temperature value
   	Given "geofence" schedule "With" sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
    When user edit "geofence" schedule by changing temperature to <Temperature> value
    Then verify "geofence" schedule successfully gets edited
    And user selects "Jasper device" from the dashboard
    And verify temperature is set within the maximum and minimum range
    And verify cool set point is always greater than or equal to heat set point
    And user logs out of the app
    Examples: 
      | Temperature   | 
      #| Above Maximum | 
      #| Below Minimum | 
      #| At Maximum    | 
      #| At Minimum    | 
      | Within range  | 
  
  @EditNAGeofencescheduleTimeformat @FlyAutomated @--xrayid:ATER-7582
  Scenario: To edit geofence schedule by setting up with new time for sleep settings
  As an user
  I want to edit geofence schedule by setting up with new time value for any time format
  	Given "geofence" schedule "With" sleep settings
  	And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule with new sleep time values
     Then verify "geofence" schedule successfully gets edited
     And user selects "Jasper device" from the dashboard
      And verify the time fields can be set with increments of "15 minutes"
      And user logs out of the app
  
  @EditNAGeofencescheduleAddingSleepsettingsTimeformat @FlyAutomated @--xrayid:ATER-7583
  Scenario: To edit geofence schedule by adding sleep settings to geofence schedule with any time format
  As an user
  I want to edit geofence schedule by adding sleep settings for any time format
  	Given "geofence" schedule "Without" sleep settings
  	And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edits "geofence" schedule by "Adding"
     And user edit "geofence" schedule with new sleep time values
     Then verify "geofence" schedule successfully gets edited
     And user selects "Jasper device" from the dashboard
      And verify the time fields can be set with increments of "15 minutes"
      And user logs out of the app
  
  @ErrormessageEditGeofencescheduleNA @NotAutomatable
  Scenario Outline:To get error messages on system unavailability to edit schedule
  As an user
  I want to get error messages on system unavailability to edit schedule   
  So that I will get notified on system unavailability
    Given With the <Condition>
     When User edit "Geofence" schedule   
     Then Verify the error message
    Examples: 
      | Condition                        | 
      | Mobile internet(3G/wifi) is down | 
      | Mobile in Airplane mode          | 
      | Mobile with low signal(3G/wifi)  | 
      | CHIL down                        | 
      | LCC Down                         | 
      | Stat offline                     | 
  
  @GuidemessagToTurnONLocationservicesNA @NotAutomatable
  Scenario Outline:To get guide message to turn ON location services to edit geofence schedule by editing home settings or by editing sleep settings or by editing away settings   
  As an user
  As an user
  I want to get guide message to turn ON location services   
  So that I will enable the service for using Geofence schedule
    Given With the <Below condition>
     When User edit "Geofence" schedule <Condition> sleep settings   
     Then Verify the guide message to turn ON location services
    Examples: 
      | Below condition                                 | Condition | 
      | Location services is disabled in mobile device  | With      | 
      | Location services is disabled for the lyric app | With      | 
      | Location services is disabled in mobile device  | Without   | 
      | Location services is disabled for the lyric app | Without   | 
  
  @GuidemessagToTurnONLocationservicesNALocationGeofenceOFF @NotAutomatable @--xrayid:ATER-7584
  Scenario Outline:To edit geofence schedule eventhough geofence is set to OFF for location by editing home settings or by editing sleep settings or by editing away settings   
  As an user
  I should be allowed to edit my geofence scheduling eventhough geofence is set to OFF for location  
  So that scheduling updated with edit changes and location is set to ON automatically
    Given With the Geofence for location is set to OFF 
     When User edit "Geofence" schedule <Condition> sleep settings  
     Then Verify "Geofence" schedule successfully gets edited
      And Verify Geofence for location is set to ON automatically
    Examples: 
      | Condition | 
      | With      | 
      | Without   |