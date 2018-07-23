@ScheduleON/OFF
Feature:
As an user 
I want to turn schedule OFF or ON 
So that I can run schedule whenever I want to apply set points automatically 

@ScheduleOFFHB
Scenario Outline:Schedule OFF the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr  
Given user launches and login to application
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
Examples:
|Mode |Schedule         |
|Cool|Geofence schedule|
|Heat|Geofence schedule|
|Auto|Geofence schedule|
|Heat only|Geofence schedule|
|Cool only|Geofence schedule|
|Cool|Time schedule    |
|Cool|Time schedule    |
|Heat|Time schedule    |
|Auto|Time schedule    |
|Heat only|Time schedule    |
|Cool only|Time schedule    |

@ScheduleONHBtimebase
Scenario Outline: Schedule ON the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr 
Given user launches and login to application 
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the "schedule OFF" overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "schedule OFF" overlay disappeared in the schedule screen
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
Scenario Outline:Schedule ON the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
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

@ScheduleON/OFFHBswitchingmodes
Scenario Outline:Schedule ON/OFF status while switching modes to off and from off for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
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