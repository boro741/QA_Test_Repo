@EditEMEAGeofenceschedule
Feature: Edit geofence based scheduling
As an user 
I want to edit geofence schedule [post installation] 
so that my home temperature will get set automatically based on edited geofence schedule settings

@EditEMEAGeofencescheduleWithSleepsettings @Automated @--xrayid:ATER-7519
  Scenario Outline: To Edit geofence schedule 
  As an user
  I want to edit geofence schedule value
  So that my temperature will get set automatically based on my new geofence schedule settings
    Given "geofence" schedule "With" sleep settings
     And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule by editing <Values>
     Then verify "geofence" schedule successfully gets edited
     And user logs out of the app
    Examples: 
      | Values         | 
      #| Home settings  | 
      | Sleep settings | 
      #| Away settings  | 
  
  @EditEMEAGeofenceschedulevalueWithoutsleepsettings @Automated @--xrayid:ATER-7520
  Scenario Outline:To Edit geofence schedule without sleep settings
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
      | Away settings | 
  
  @EditEMEAGeofenceschedulevaluesleepsettings @Automated @--xrayid:ATER-7521
  Scenario Outline:To Edit geofence schedule without sleep settings
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
  
  @EditEMEAGeofencescheduleTemperatureRange @Automated @--xrayid:ATER-7522
  Scenario Outline: To configure Geofence schedule by setting up with new temperature value
  As an user
  I want to edit Geofence schedule by editing with new temperature value
    Given "geofence" schedule "With" sleep settings
    And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule by changing temperature to <Temperature> value 
     Then verify "geofence" schedule successfully gets edited
      And verify temperature is set within the maximum and minimum range
      And user logs out of the app
    Examples: 
      | Temperature   | 
      #| Above Maximum | 
      #| Below Minimum | 
      #| At Maximum    | 
      #| At Minimum    | 
      | Within range  | 
  
  @EditEMEAGeofencescheduleTimeformat @Automated @--xrayid:ATER-7523
  Scenario: To edit geofence schedule by setting up with new time for sleep settings with any time formats
  As an user
  I want to edit geofence schedule by setting up with new time value for any time format
    Given "geofence" schedule "With" sleep settings
  	 And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edit "geofence" schedule with new sleep time values
     Then verify "geofence" schedule successfully gets edited
     And user selects "Jasper device" from the dashboard
      And verify the time fields can be set with increments of "10 minutes"
      And user logs out of the app
  
  @EditEMEAGeofencescheduleAddingSleepsettingsTimeformat @Automated @--xrayid:ATER-7524
  Scenario: To edit geofence schedule by adding sleep settings to geofence schedule with any time formats
  As an user
  I want to edit geofence schedule by adding sleep settings for any time format
    Given "geofence" schedule "Without" sleep settings
  	 And user launches and logs in to the Lyric application
    And user selects "Jasper device" from the dashboard
     When user edits "geofence" schedule by "Adding"
     And user edit "geofence" schedule with new sleep time values
     Then verify "geofence" schedule successfully gets edited
     And user selects "Jasper device" from the dashboard
      And verify the time fields can be set with increments of "10 minutes"
      And user logs out of the app
  
  @ErrormessageEditGeofencescheduleEMEA @NotAutomatable
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
  
  @GuidemessagToTurnONLocationservicesEMEA @NotAutomatable
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
  
  @GuidemessagToTurnONLocationservicesEMEALocationGeofenceOFF @PendingToAutomate @--xrayid:ATER-7525
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
  
  