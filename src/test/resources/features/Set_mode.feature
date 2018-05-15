@ChangeStatmode
Feature: As an user 
I want to change mode based on my indoor temperature
So that my stat will turn on cool system or heat system to maintain my comfortable indoor temperature 

#Background:
#Given user logs in to Lyric app

@ChangeStatmodeNANoschedule @Automated @--xrayid:ATER-7603
Scenario Outline: To change stat mode based on my indoor temperature for systems
As an user 
I want to change mode based on my indoor temperature
So that my stat will turn on cool system or heat system to maintain my comfortable indoor temperature
Given user thermostat is set to "no" schedule
And user has <Mode> system mode
And user launches and logs in to the Lyric application
And user selects "Jasper device" from the dashboard
When user changes system mode to <To mode>
Then verify system mode is changed to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
|Off  |Cool   | 
|Cool |Off    |
|Off  |Heat   |
|Heat |Cool   | 
|Cool |Heat   |
|Heat |Off    |


@ChangeStatModeNAGofenceschedule @Automated @--xrayid:ATER-7604
Scenario Outline: To change stat mode based on my indoor temperature for systems
As an user 
I want to change stat mode based on my indoor temperature
So that my stat will turn on cool system or heat system to maintain my comfortable indoor temperature  
Given user thermostat is set to "geofence" schedule
And user has <Mode> system mode
And user launches and logs in to the Lyric application
And user selects "Jasper device" from the dashboard
When user changes system mode to <To mode>
Then verify system mode is changed to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
|Off  |Cool   |
|Cool |Off    |
|Off  |Heat   |
|Heat |Cool   | 
|Cool |Heat   |
|Heat |Off    |


@ChangeStatModeNATimeschedule @Automated @--xrayid:ATER-7605
Scenario Outline: To change stat mode based on my indoor temperature for systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
As an user 
I want to change stat mode based on my indoor temperature
So that my stat will turn on cool system or heat system to maintain my comfortable indoor temperature  
Given user thermostat is set to "time" schedule
And user has <Mode> system mode
And user launches and logs in to the Lyric application
And user selects "Jasper device" from the dashboard
When user changes system mode to <To mode>
Then verify system mode is changed to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
#|Off  |Cool   | 
|Cool |Off    |
#|Off  |Heat   |
#|Heat |Cool   | 
#|Cool |Heat   |
#|Heat |Off    |


@ChangeStatModeNAMultistat @Automated @--xrayid:ATER-7606
Scenario Outline: To change stat mode based on my indoor temperature for systems
As an user 
I want to change stat mode based on my indoor temperature
So that my stat will turn on cool system or heat system to maintain my comfortable indoor temperature 
Given user has "No scheduling" mode for "stat1" 
And user has <Mode> system mode for "stat1"
And user has <Mode> system mode for "stat2"
And user logs in to Lyric app
When user changes system mode for "stat1" to <To mode> 
Then verify system mode is changed to <To mode>
And Verify "stat1" widget on the dashboard is "updated" to <To mode>
And Verify "stat2" widget on the dashboard is "not updated" to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
#|Off  |Cool   | 
#|Cool |Off    |
#|Off  |Heat   |
|Heat |Cool   | 
#|Cool |Heat   |
#|Heat |Off    |


@ChangeStatModeNAAutochangeover @Automated @--xrayid:ATER-7607
Scenario Outline: To Change stat mode for Heat cool system with auto changeover enabled with Temperture scale Celsius Fahrenheit and for time format 24 12hr 
As an user 
I want to change stat mode based on my indoor temperature
So that my stat will turn on cool system or heat system automatically to maintain my comfortable indoor temperature  
Given user has <Mode> system mode
And user logs in to Lyric app
When user changes system mode to <To mode>
Then verify system mode is changed to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
|Off  |Auto   | 
#|Auto |Cool   |
|Cool |Auto   |
#|Auto |Heat   | 
|Heat |Auto   |
#|Auto |Off    |

             
@ChangeStatModeEMEA @Automated @--xrayid:ATER-7608
Scenario Outline: To Change stat mode for time format  
As an user 
I want to change stat mode based on my indoor temperature
So that my stat will turn on cool system or heat system automatically to maintain my comfortable indoor temperature  
#Given Stat with <Schedule> and <Mode>
Given user has <Schedule> mode
And user has <Mode> system mode
And user logs in to Lyric app
When user changes system mode to <To mode>
Then verify system mode is changed to <To mode>
And user logs out of the app
Examples:
|Schedule         |Mode |To mode|
#|No scheduling      |Off  |Heat   | 
#|No scheduling      |Heat |Off    |
|Time scheduling    |Off  |Heat   | 
#|Time scheduling    |Heat |Off    |
#|Geofence scheduling|Off  |Heat   | 
#|Geofence scheduling|Heat |Off    |



@ChangeStatModeEMEAMultistat @Automated @--xrayid:ATER-7609
Scenario Outline: To Change stat mode with time format 
As an user 
I want to change mode based on my indoor temperature
So that my stat will turn on cool system or heat system automatically to maintain my comfortable indoor temperature  
Given user has <Schedule> mode for "stat1"
And user has <Mode> system mode for "stat1"
And user has <Mode> system mode for "stat2"
And user logs in to Lyric app
When user changes system mode for "stat1" to <To mode>
Then verify system mode is changed to <To mode>
And Verify "stat1" widget on the dashboard is "updated" to <To mode>
And Verify "stat2" widget on the dashboard is "not updated" to <To mode>
And user logs out of the app
Examples:
|Schedule         |Mode |To mode|
|No scheduling      |Off  |Heat   | 
|No scheduling      |Heat |Off    |
|Time scheduling    |Off  |Heat   | 
|Time scheduling    |Heat |Off    |
|Geofence scheduling|Off  |Heat   | 
|Geofence scheduling|Heat |Off    |


@ErrormessageChangeStatmode @NotAutomatable
Scenario Outline:To get error messages on system unavailability on change mode
As an user
I want to get error message while attempt to change mode  
So that I will get notified on system unavailability
Given With the <Condition>
When User changes mode   
Then Verify the error message
Examples:
|Condition                       |
|Mobile internet(3G/wifi) is down|
|Mobile in Airplane mode         |
|Mobile with low signal(3G/wifi) |
|CHIL down                       |
|Switching between the network   |
|Smart network switch            |
|LCC/TCC down                    |
|Stat offline                    |
