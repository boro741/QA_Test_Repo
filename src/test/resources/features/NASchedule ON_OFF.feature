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


@ScheduleOFFAdhocOverrideNAtimebase
Scenario Outline:Schedule OFF the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr  
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
When user navigates to "PRIMARY CARD" screen from the "SCHEDULING" screen
Then Verify the <Schedule status> on the "PRIMARY CARD" screen
Examples:
|Mode|Schedule | Ahocoverride    |
|Heat| time based| Temporary |
|Heat| time based| Permanent |
|Cool| time based| Temporary |
|Cool| time based| Permanent |
|auto| time based| Temporary |
|auto| time based| Permanent |
|Cool only| time based| Temporary |
|Cool only | time based| Permanent |
|Heat only| time based| Temporary |
|Heat only | time based| Permanent |
|Heat|geofence based| Temporary |
|Heat|geofence based| Permanent |
|Cool|geofence based| Temporary |
|Cool|geofence based| Permanent |
|auto|geofence based| Temporary |
|auto|geofence based| Permanent |
|Cool only|geofence based| Temporary |
|Cool only |geofence based| Permanent |
|Heat only|geofence based| Temporary |
|Heat only |geofence based| Permanent |

@ScheduleONNAadhocoverride
Scenario Outline:Schedule ON the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to turn schedule ON from OFF
So that schedule will be turned back to follow schedule 
  Given user launches and login to application 
Then user set to <Mode>
And user Stat with <Schedule>
And user Stat with <AdhocOverride>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the <AhocOverride> status on "SolutionCard"

Examples:
|Mode|Schedule | Ahocoverride    |
|Heat|geofence based| Temporary |
|Heat|geofence based| Permanent |
|Cool|geofence based| Temporary |
|Cool|geofence based| Permanent |
|auto|geofence based| Temporary |
|auto|geofence based| Permanent |
|Cool only|geofence based| Temporary |
|Cool only |geofence based| Permanent |
|Heat only|geofence based| Temporary |
|Heat only |geofence based| Permanent |
|Heat| time based| Temporary |
|Heat| time based| Permanent |
|Cool| time based| Temporary |
|Cool| time based| Permanent |
|auto| time based| Temporary |
|auto| time based| Permanent |
|Cool only| time based| Temporary |
|Cool only | time based| Permanent |
|Heat only| time based| Temporary |
|Heat only | time based| Permanent |





@ScheduleOFFVacationNA 
Scenario Outline:Schedule OFF for stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr 
As an user 
I want to turn schedule OFF while vacation is active 
So that I will be able to turned off schedule whenever I don't want to run schedule    
  Given user launches and login to application 
Then user set to <Mode>
And user Stat with "Vacation"
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 

Examples:
|Mode|
|Cool|
|Heat|
|Auto|
|Cool only|
|Heat Only|



@ScheduleONNAVacationNA 
Scenario Outline:Schedule ON the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
As an user 
I want to turn schedule ON 
So that my vaction will be back   
Given user launches and login to application 
Then user set to <Mode>
And user Stat with "Vacation"
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "Vacation" status on "SolutionCard"

Examples:
|Mode|
|Cool|
|Heat|
|Auto|
|Cool only|
|Heat Only|


@ScheduleON/OFFNAswitchingmodes
Scenario Outline:Schedule ON/OFF status while switching modes to off and from off for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user launches and login to application 
Then user set to <Mode>
And user Stat with <AdhocOverride>
And user should be displayed with <Adhocoverride> status
When user changes the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be displayed with <Adhocoverride> status
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 
When use changes the <UMode> from "OFF"
Then user should display with "Shedule OFF"
When user changes the "OFF" from <UMode>
Then user TAP on the "Schedule OFF" overlay on "Schedule" screen
When user changes the <UMode> from "OFF"
Then user should be displayed with <Adhocoverride> status on "SolutionCard"

Examples :
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
| Auto | Vacation | Auto |
