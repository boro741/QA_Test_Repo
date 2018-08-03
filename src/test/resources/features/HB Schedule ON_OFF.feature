@ScheduleON_OFF
Feature:As an user I want to turn schedule OFF or ON So that I can run schedule whenever I want to apply set points automatically 

@ScheduleONOFFHB @Automated -- 	LYR-29410
Scenario Outline:Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr  
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
Then user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
Examples:
|Mode | scheduling |
|Cool|geofence based|
#|Heat|geofence based|
#|Auto|geofence based|
#|Cool|time based    |
#|Heat|time based    |
#|Auto|time based    |

#incaserequired
#|Heat only|geofence based|
#|Cool only|geofence based|
#|Heat only|time based    |
#|Cool only|time based    |

@ScheduleONOFFHBtimebase @Automated --LYR-29409
Scenario Outline:Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr time base schedule
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
Then user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status not displayed" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
When user navigates to "PRIMARY CARD" screen from the "SCHEDULING" screen
Then Verify the "Schedule status not displayed" on the "PRIMARY CARD" screen
Examples:
|Mode |scheduling	|
|Cool|time based     |
#|Heat|time based     |
#|Auto|time based     |

#Incaserequried
#|Heat only|time based   |
#|Cool only|time based    |



@ScheduleONOFFHBgeofencebase @Automated --LYR-29388
Scenario Outline: Schedule ON the stat   with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And Verify the "Schedule status not displayed" on the "PRIMARY CARD" screen
Then user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status not displayed" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
When user navigates to "PRIMARY CARD" screen from the "SCHEDULING" screen
Then Verify the "Schedule status not displayed" on the "PRIMARY CARD" screen
Examples:
|Mode|scheduling	|Geofence   |Period |
|Cool|Without sleep geofence based|UserArrived | Home|
|Cool|geofence based|UserArrived |Sleep|
|Cool|Without sleep geofence based|UserDeparted |Away|
#|Heat|Without sleep geofence based|UserArrived |Home|
#|Heat|Without sleep geofence based|UserDeparted |Away|
#|Heat|geofence based|UserArrived | Sleep|
#|Auto|geofence based|UserArrived |Sleep|
#|Auto|Without sleep geofence based|UserArrived |Home|
#|Auto|Without sleep geofence based|UserDeparted |Away|

#Incaserequried
#|Heat only|Without sleep geofence based|UserArrived |Home|
#|Heat only|Without sleep geofence based|UserDeparted |Away|
#|Heat only|geofence based|UserArrived |Sleep|
#|Cool only|Without sleep geofence based|UserArrived |Home|
#|Cool only|Without sleep geofence based|UserDeparted |Away|
#|Cool only|geofence based|UserArrived |Sleep|



@ScheduleONOFFHBswitchingmodes
Scenario Outline:Schedule ON OFF status while switching modes to off and from off for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user launches and login to application 
Then user set to <Mode>
When user changes the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When use changes the <UMode> from "OFF"
Then user should display with "Shedule OFF" overlay in the schedule screen 
When user changes the "OFF" from <UMode>
Then user TAP on the "Schedule OFF" overlay on "Schedule" screen
And Verify the "schedule OFF" overlay disappeared in the schedule screen
When user changes the <UMode> from "OFF"
And Verify the "schedule OFF" overlay disappeared in the schedule screen

Examples:
|Mode| Adocoverride | UMode | 
|Cool | Temporary | Cool |
|Cool | Temporary | Heat |
|Cool | Temporary | Auto |
|Cool | Permanent | Cool |
|Cool | Permanent | Heat |
|Cool | Permanent | Auto |
|Cool | Vacation | Cool |
|Cool | Vacation | Heat |
|Cool | Vacation | Auto |
| Heat | Temporary | Cool |
| Heat | Temporary | Heat |
| Heat | Temporary | Auto |
| Heat | Permanent | Cool |
| Heat | Permanent | Heat |
| Heat | Permanent | Auto |
| Heat | Vacation | Cool |
| Heat | Vacation | Heat |
| Heat | Vacation | Auto |
| Auto | Temporary | Cool |
| Auto | Temporary | Heat |
| Auto | Temporary | Auto |
| Auto | Permanent | Cool |
| Auto | Permanent | Heat |
| Auto | Permanent | Auto |
| Auto | Vacation | Cool |
| Auto | Vacation | Heat |