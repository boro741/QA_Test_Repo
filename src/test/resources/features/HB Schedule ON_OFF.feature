@ScheduleON_OFF
Feature:As an user I want to turn schedule OFF or ON So that I can run schedule whenever I want to apply set points automatically 

@ScheduleONOFFHB
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

@ScheduleONHBtimebase
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
Examples:
|Mode |Schedule|
|Cool|Time schedule    |
|Cool|Time schedule    |
|Heat|Time schedule    |
|Auto|Time schedule    |
|Heat only|Time schedule    |
|Cool only|Time schedule    |
|Cool||Time schedule    |


@ScheduleONHBgeofencebase
Scenario Outline:Schedule ON the stat   with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user launches and login to application 
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "schedule OFF" overlay disappeared in the schedule screen

Examples:
|Cool|Geofence schedule|period | 
|Heat|Geofence schedule|Home|
|Heat|Geofence schedule|sleep|
|Heat|Geofence schedule|away |

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