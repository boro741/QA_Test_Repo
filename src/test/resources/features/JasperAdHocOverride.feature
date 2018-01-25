@Adhoc
Feature: As a user I want to set the vacation period for my home
so that my stat will follow vacation setting on my absence in my home during the vacation days  

  @AdhocOverrideScheduleTemperature @Automated @--xrayid:ATER-7386
  Scenario Outline: Override schedule set points 
  As a user I want to override schedule set points for systems Heat and Cool with temperature scale Celsius / Fahrenheit and with time format 12/24hr 
    Given user thermostat is set to <scheduling> schedule
      And user launches and logs in to the Lyric application
     When user sets the temperature by tapping or rotating the set wheel in the solution card
     Then verify the schedule temperature is overridden with set temperature
      And verify the override status on the solution card is overridden with set temperature <status>
      And user logs out of the app
  
    Examples: 
      | scheduling          | status                        | 
      | time based          | till next schedule period     | 
      | geofence based      | while current schedule period | 
  
  @AdhocOverrideScheduleTemperatureMultistat @Automated @--xrayid:ATER-7387
  Scenario Outline: Verify overridding schedule set points on one stat does not affect other stats in the location
    Given user has <scheduling> mode for "stat1"
      And setpoints are not overridden on "stat2"
      And user logs in to Lyric app
     When user sets the temperature by tapping or rotating the set wheel in the solution card for "stat1"
     Then verify the schedule temperature is overridden with set temperature for "stat1"
      And verify the override status on the solution card is overridden with set temperature <status> for "stat1"
      And verify the status of stat2 in the location not affected by adhoc override of stat1
      And user logs out of the app
  
    Examples: 
      | scheduling          | status                        | 
      | time scheduling     | till next schedule period     | 
      | geofence scheduling | while current schedule period | 
  
  @AdhocOverrideScheduleTemperatureGeofenceschedulingChangemodeHeatcool @Automated @--xrayid:ATER-7388
  Scenario Outline: Verify Override set points are retained after mode change through Geofence Schedule
  As a user I want to verify override set points are retained for change in modes for "Heat cool" system with auto changeover enabled
    Given user has "geofence scheduling" mode
      And user has <current mode> system mode
      And user logs in to Lyric app
     When user overrides the "geofence scheduling" set temperature
      And user changes system mode to <mode>
     Then verify override status is retained
      And verify the override status on the solution card is overridden with set temperature "while current schedule period"
      And user logs out of the app
  
    Examples: 
      | current mode | mode | 
      | Heat         | Auto | 
      #| Cool         | Auto | 
      #| Cool         | Heat | 
      #| Auto         | Heat | 
      #| Heat         | Cool | 
      #| Auto         | Cool | 
  
  @AdhocOverrideScheduleTemperatureGeofenceschedulingResume @Automated @--xrayid:ATER-7389
  Scenario: Resume Geofence scheduling
  As a user I want to resume geofence schedule
    Given user has "geofence scheduling" mode
      And user logs in to Lyric app
     When user overrides the "geofence scheduling" set temperature
      And user resumes the schedule
     Then verify that schedule has resumed by checking setpoints
      And verify the "geofence scheduling" status on the solution card
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureGeofenceschedulingResumeMultistat @Automated @--xrayid:ATER-7390
  Scenario: Verify Resuming Schedule on one stat does not affect another stat in the location
  As a user scheduling should not resume from override for other stat in the location other than the stat selected for resume
    Given user has "geofence scheduling" mode for "stat1"
      And user has "geofence scheduling" mode for "stat2"
      And user logs in to Lyric app
     When user overrides the "geofence scheduling" set temperature for "stat1"
      And user overrides the "geofence scheduling" set temperature for "stat2"
      And user resumes the schedule for "stat1"
     Then verify that schedule has resumed by checking setpoints for "stat1"
      And verify the "geofence scheduling" status on the solution card for "stat1"
      And verify the schedule has not resumed for "stat2"
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingChangemodeHeatcool @Automated @--xrayid:ATER-7391
  Scenario Outline: Verify Override set points are retained after mode change through Time Schedule
    Given user has "time scheduling" mode
      And user has <current mode> system mode
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature
      And user changes system mode to <mode>
     Then verify override status is retained
      And verify the override status on the solution card is overridden with set temperature "till next schedule period"
      And user logs out of the app 
  
    Examples: 
      | current mode | mode | 
      | Heat         | Auto | 
      | Cool         | Auto | 
      | Cool         | Heat | 
      | Auto         | Heat | 
      | Heat         | Cool | 
      | Auto         | Cool | 
  
  @AdhocOverrideScheduleTemperatureTimeschedulingPermanently @Automated @--xrayid:ATER-7392
  Scenario: Override schedule temperature permanently
    Given user has "time scheduling" mode
      And setpoints are not overridden
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature
      And user puts schedule on permanent hold
     Then verify the override status on the solution card is overridden with set temperature "permanently"
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingPermanentlyMultistat @Automated @--xrayid:ATER-7393
  Scenario: Override schedule temperature permanently MultiStat 
  Verify override schedule set points permanently for stat should not affect other stats in the location
    Given user has "time scheduling" mode for "stat1"
      And user has "time scheduling" mode for "stat2"
      And setpoints are not overridden on "stat2"
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature for "stat1"
      And user puts schedule on permanent hold for "stat1"
     Then verify the override status on the solution card is overridden with set temperature "permanently" for "stat1"
      And verify the status of stat2 in the location not affected by adhoc override of stat1
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingHolduntil @Automated @--xrayid:ATER-7394
  Scenario: Override Schedule Temperature through Hold Until
  To override schedule temperature till required time with maximum of 12hours for 24hr/12hr format
    Given user has "time scheduling" mode 
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature
      And user holds the schedule until time "greater than 12 hours" from current time
     Then verify hold until time is not set to time greater than 12 hours
     When user holds the schedule until time "lesser than 12 hours" from current time
     Then verify hold until time is set to time lesser than 12 hours
      And verify the override status on the solution card is overridden with set temperature "till defined hold time"
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingHolduntilMultistat @Automated @--xrayid:ATER-7395
  Scenario: Override Schedule Temperature through Hold Until MultiStat
  To Verify override schedule set points by hold until for stat should not affect other stats in the location
    Given user has "time scheduling" mode for "stat1"
      And user has "time scheduling" mode for "stat2"
      And setpoints are not overridden on "stat2"
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature for "stat1"
      And user holds the schedule until time "greater than 12 hours" from current time for "stat1"
     Then verify hold until time is not set to time greater than 12 hours
     When user holds the schedule until time "lesser than 12 hours" from current time for "stat1"
     Then verify hold until time is set to time lesser than 12 hours
      And verify the override status on the solution card is overridden with set temperature "till defined hold time" for "stat1"
      And verify the status of stat2 in the location not affected by adhoc override of stat1
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterPermanentHold @Automated @--xrayid:ATER-7396
  Scenario: Resume scheduling from override from Time Scheduling
    Given user has "time scheduling" mode 
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature
      And user puts schedule on permanent hold
      And user resumes the schedule
     Then verify that schedule has resumed by checking setpoints
      And verify the "time scheduling" status on the solution card
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterPermanentHoldMultiStat @Automated @--xrayid:ATER-7397
  Scenario: Resume scheduling from override from Time Scheduling MultiStat
    Given user has "time scheduling" mode for "stat1"
      And user has "time scheduling" mode for "stat2"
      And setpoints are not overridden on "stat2"
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature for "stat1"
      And user puts schedule on permanent hold for "stat1"
      And user resumes the schedule for "stat1"
     Then verify that schedule has resumed by checking setpoints for "stat1"
      And verify the "time scheduling" status on the solution card for "stat1"
      And verify the status of stat2 in the location not affected by adhoc override of stat1
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterHoldUntil @Automated @--xrayid:ATER-7398
  Scenario: Resume scheduling from override from Time Scheduling
    Given user has "time scheduling" mode 
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature
      And user holds the schedule until time "lesser than 12 hours" from current time
     Then verify hold until time is set to time lesser than 12 hours
     When user resumes the schedule
     Then verify that schedule has resumed by checking setpoints
      And verify the "time scheduling" status on the solution card
      And user logs out of the app
  
  @AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterHoldUntilMultiStat @Automated @--xrayid:ATER-7399
  Scenario: Resume scheduling from override from Time Scheduling MultiStat
    Given user has "time scheduling" mode for "stat1"
      And user has "time scheduling" mode for "stat2"
      And setpoints are not overridden on "stat2"
      And user logs in to Lyric app
     When user overrides the "time scheduling" set temperature for "stat1"
      And user holds the schedule until time "lesser than 12 hours" from current time
     Then verify hold until time is set to time lesser than 12 hours
     When user resumes the schedule for "stat1"
     Then verify that schedule has resumed by checking setpoints for "stat1"
      And verify the "time scheduling" status on the solution card for "stat1"
      And verify the status of stat2 in the location not affected by adhoc override of stat1
      And user logs out of the app
  
  @OverrideTemperatureStatoffmode @Automated @--xrayid:ATER-7400
  Scenario Outline: Override temperature for the conditions like stat is in off mode
  As an user I won't be able to override temperature for the conditions like stat is in off mode
    Given user has <scheduling> mode 
      And user logs in to Lyric app
     When user changes system mode to "off"
     Then verify adhoc status is not present on solution card 
      And user logs out of the app
  
    Examples: 
      | scheduling          | 
      | geofence scheduling | 
      | time scheduling     | 
  
  @VerifyVacationSetPointsAfterEditing @Automated @--xrayid:ATER-7401
  Scenario: As a user I want to edit the vacation settings so that I can set the new vacation period settings for stat  
    Given vacation mode is "active"
     When user logs in to Lyric app
      And user edits the set values under "vacation settings"
     Then verify the edited set values are updated on both vacation settings card and solutions card
     When user edits the set values under "set wheel"
     Then verify the edited set values are updated on both vacation settings card and solutions card
      And user logs out of the app
  
  @VerifyVacationStatusOnDevicecard @Automated @--xrayid:ATER-7402
  Scenario Outline: View the vacation status in device card
  As a user  I want to view the vacation status in device card so that I can know vacation settings are applied in the device 
     When vacation mode is "<vacation mode>"
      And user logs in to Lyric app
     Then user verifies vacation is <status> in "dashboard"
  	  And user logs out of the app
    Examples: 
      | vacation mode | status | 
      | active        | on     | 
      | yet to set    | off    | 
      
	@AdhocOverrideScheduletemperatureGeofenceschedulingSChangeGeofencestatus @PendingToAutomate @--xrayid:ATER-7403
    Scenario Outline: To verify scheduling should resume from override if geofence status changed for the location
	Given "Geofence scheduling"  is available
	And Set temperature is overrided by Adhoc temperature
	And Location with Geofence <Status> 
	When Geofence <Status changed> for the location in which stat is connected
	Then Verify the Geofence scheduling is resumed due to change in geofence status
	Examples:
	|Status|Status changed|
	|Away  |Home          |
	|Home  |Away          |
	
	@AdhocOverrideScheduletemperatureTimeschedulingShouldnotResume @NotAutomatable
	Scenario Outline: To verify scheduling should not resume from override if next configured time scheduling time clocked in the stat
	Given "Time scheduling"  is available
	And Temperature is override with set temperature <Till>
	When Next configured time scheduling time clocked in the stat
	Then Verify the time scheduling is not resumed 
	Examples:
	|Till       |  
	|Permanently|
	|Hold Until |
	
	@OverrideTemperatureStatoffline @PendingToAutomate @--xrayid:ATER-7404
	Scenario Outline:To Override Temperature when stat is in offline
	As an user
	I won't be able to override temperature for the conditions like stat is in offline 
	Given <Scheduling>  is available 
	When Stat is in " Offline"
	Then Verify user won't be allowed to override scheduling temperature with set wheel 
	Examples:
	|Scheduling         | 
	|Time scheduling    |
	|Geofence scheduling|
	
