@ScheduleON_OFF
Feature: As an user I want to turn schedule OFF or ON so that I can run schedule whenever I want to apply set points automatically 


@ScheduleOFFONNA @Automated --LYR-29388
Scenario Outline:Schedule OFF ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
#As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule  
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
Then user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen

Examples:
|Mode | scheduling |
|Cool|geofence based|
|Heat|geofence based|
#|Auto|geofence based|
#|Cool|time based    |
#|Heat|time based    |
#|Auto|time based    |

#incaserequired
#|Heat only|geofence based|
#|Cool only|geofence based|
#|Heat only|time based    |
#|Cool only|time based    |



@ScheduleOFFONNAtimebase  @Automated --	LYR-29389
Scenario Outline:Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
#As an user I want to turn schedule ON while running time base schedule   
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
Then user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
When user navigates to "PRIMARY CARD" screen from the "SCHEDULING" screen
Then Verify the <Schedule status> on the "PRIMARY CARD" screen

Examples:
|Mode | scheduling | Schedule status |
|Cool|time based |following schedule |
#|Heat|time based | following schedule |
#|Auto|time based  | following schedule |

#incaserequired
#|Heat only|time based  | following schedule |
#|Cool only|time based  | following schedule |

@ScheduleONOFFNAgeofencebase
Scenario Outline:Schedule ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
#As an user I want to turn schedule ON from OFF So that schedule will be turned back to geofence based 
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
Then user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
When user navigates to "PRIMARY CARD" screen from the "SCHEDULING" screen
Then Verify the <Schedule status> on the "PRIMARY CARD" screen

Examples:
|MODE|scheduel|Schedule status | 
|Heat|geofence based|Using Home Settings|
|Heat|geofence based|Using Away Settings|
|Heat|geofence based|Using Sleep Settings |
|Auto|geofence based|Using Home Settings |
|Auto|geofence based|Using Away Settings |
|Auto|geofence based|Using Sleep Settings |

#Incaserequried
|Heat only|geofence based|Using Home Settings |
|Heat only|geofence based|Using Away Settings |
|Heat only|geofence based|Using Sleep Settings |
|Cool only|geofence based|Using Home Settings|
|Cool only|geofence based|Using Away Settings |
|Cool only|geofence based|Using Sleep Settings |