@ScheduleON_OFF
Feature: As an user I want to turn schedule OFF or ON so that I can run schedule whenever I want to apply set points automatically 


@ScheduleOFFONNA @Automated --LYR-29388
Scenario Outline: As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule 
#Schedule OFF ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
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
Scenario Outline:As an user I want to turn schedule ON while running time base schedule   
#Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
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

@ScheduleONOFFNAgeofencebase @Automated -- LYR-29390
Scenario Outline:As an user I want to turn schedule ON from OFF So that schedule will be turned back to geofence based 
#Schedule ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And Verify the <Schedule status> on the "PRIMARY CARD" screen
Then user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
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
|Mode|scheduling	|Schedule status	 |Geofence   |Period |
|Cool|Without sleep geofence based|Using Home Settings|UserArrived | Home|
|Cool|geofence based|Using Sleep Settings |UserArrived |Sleep|
|Cool|Without sleep geofence based|Using Away Settings|UserDeparted |Away|
#|Heat|Without sleep geofence based|Using Home Settings|UserArrived |Home|
#|Heat|Without sleep geofence based|Using Away Settings|UserDeparted |Away|
#|Heat|geofence based|Using Sleep Settings |UserArrived | Sleep|
#|Auto|geofence based|Using Sleep Settings |UserArrived |Sleep|
#|Auto|Without sleep geofence based|Using Home Settings |UserArrived |Home|
#|Auto|Without sleep geofence based|Using Away Settings |UserDeparted |Away|

#Incaserequried
#|Heat only|Without sleep geofence based|Using Home Settings |UserArrived |Home|
#|Heat only|Without sleep geofence based|Using Away Settings |UserDeparted |Away|
#|Heat only|geofence based|Using Sleep Settings |UserArrived |Sleep|
#|Cool only|Without sleep geofence based|Using Home Settings|UserArrived |Home|
#|Cool only|Without sleep geofence based|Using Away Settings |UserDeparted |Away|
#|Cool only|geofence based|Using Sleep Settings |UserArrived |Sleep|


@ScheduleOFFAdhocOverrideNAtimebaseGeofence @Automated --LYR-29397
Scenario Outline:As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule  
#Schedule OFF the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr  
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
When user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then user has <Adhocoverride> status
And Verify the <Adhocoverride> on the "PRIMARY CARD" screen
Then user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen 
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
Examples:
|Mode|scheduling | Adhocoverride    |
|Heat| time based| Temporary |
|Heat| time based| Permanent |
|Heat|geofence based| Temporary |
#|Cool| time based| Temporary |
#|Cool| time based| Permanent |
#|Cool|geofence based| Temporary |
#|auto| time based| Temporary |
#|auto| time based| Permanent |
#|auto|geofence based| Temporary |

#incaserequired
#|Cool only| time based| Temporary |
#|Cool only | time based| Permanent |
#|Heat only| time based| Temporary |
#|Heat only | time based| Permanent |
#|Cool only|geofence based| Temporary |
#|Heat only|geofence based| Temporary |

@ScheduleONOFFNNAadhocoverrideTimebase @Automated -- LYR-29392
Scenario Outline: As an user I want to turn schedule ON from OFFSo that schedule will be turned back to follow schedule 
#Schedule ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
When user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And user has <Adhocoverride> status
And Verify the <Adhocoverride> on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
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
|Mode|scheduling | Adhocoverride   | Schedule status |
|Heat| time based| Temporary | following schedule |
|Heat| time based| Permanent | following schedule |
|Cool| time based| Permanent | following schedule |
|Cool| time based| Temporary | following schedule |
#|auto| time based| Temporary | following schedule |
#|auto| time based| Permanent | following schedule |

#incaserequired
#|Cool only| time based| Permanent |following schedule |
#|Heat only| time based| Temporary |following schedule |
#|Cool only| time based| Permanent |following schedule |
#|Heat only| time based| Temporary |following schedule |


@ScheduleONOFFNAadhocoverrideGeofence @Automated -- LYR-29391
Scenario Outline:As an user I want to turn schedule ON from OFF So that schedule will be turned back to geofence schedule 
#Schedule ON the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
And user launches and logs in to the Lyric application
When user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then Verify the <Schedule status> on the "PRIMARY CARD" screen
When user has <Adhocoverride> status
Then Verify the <Adhocoverride> on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
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
|Mode | scheduling | Adhocoverride | Schedule status |Geofence | Period |
|Heat|Without sleep geofence based| Temporary | Using Home Settings| UserArrived |Home|
|Heat|Without sleep geofence based| Temporary | Using Away Settings| UserDeparted |Away|
|Heat|geofence based| Temporary | Using Sleep Settings|UserArrived |Sleep|
#|Cool|Without sleep geofence based| Temporary |Using Home Settings| UserArrived |Home|
#|Cool|Without sleep geofence based| Temporary |Using Away Settings| UserDeparted |Away|
#|Cool|geofence based| Temporary |Using Sleep Settings|UserArrived |Sleep|
#|auto|Without sleep geofence based| Temporary |Using Home Settings| UserArrived |Home|
#|auto|Without sleep geofence based| Temporary |Using Away Settings|UserDeparted |Away|
#|auto|geofence based| Temporary |Using Sleep Settings|UserArrived |Sleep|

#Incaserequired
#|Cool only|Without sleep geofence based| Temporary |Using Home Settings|Home|
#|Cool only|Without sleep geofence based| Temporary |Using Away Settings|Away|
#|Cool only|geofence based| Temporary |Using Sleep Settings|Sleep|
#|Heat only|Without sleep geofence based| Temporary |Using Home Settings|Home|
#|Heat only|Without sleep geofence based| Temporary |Using Away Settings|Away|
#|Heat only|geofence based| Temporary |Using Sleep Settings|Sleep|


@ScheduleOFFVacationNA @Automated -- LYR-29393
Scenario Outline: As an user  I want to turn schedule OFF while vacation is active  So that I will be able to turned off schedule whenever I don't want to run schedule
#Schedule OFF for stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr     
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And vacation mode is "active"
And Verify the "Vacation status" on the "PRIMARY CARD" screen
Then user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user selects "Schedule OFF overlay" from "Scheduling" screen
Then Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
And user navigates to "primary card" screen from the "SCHEDULING" screen
And vacation mode is "disable"
Examples:
|Mode| scheduling | 
|Cool| time based |
#|Heat| time base |
#|Auto| time based |
#|Cool|geofence based |
#|Heat| geofence based |
#|Auto| geofence based |

#incaserequired
#|Cool only| time based |
#|Heat Only|time based |
#|Cool only| geofence based |
#|Heat Only| geofence based |



@ScheduleONOFFNAVacationNA @Automated --LYR-29394
Scenario Outline:As an user I want to turn schedule ON So that my vaction will be back  
#Schedule ON the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When vacation mode is "active"
Then Verify the "Vacation status" on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Vacation status" on the "PRIMARY CARD" screen 
And vacation mode is "disable"
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen
When user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
Examples:
|Mode| scheduling | 
|Cool| time based |
#|Heat| time based |
#|Auto| time based |
#|Cool|geofence based |
#|Heat| geofence based |
#|Auto| geofence based |

#incaserequired
#|Cool only| time based |
#|Heat Only| time based |
#|Cool only| geofence based |
#|Heat Only| geofence based |


@ScheduleONOFFNAswitchingmodes @Automatable
Scenario Outline: Schedule ON OFF status while switching modes to off and from off for Temperture scale Celsius Fahrenheit and for time format 24 12hr
Given user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user Stat with <AdhocOverride>
And user launches and logs in to the Lyric application
And Verify the <AdhocOverrideStatus> on the "PRIMARY CARD" screen
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
| Auto | Vacation | Auto |


#Negative scenario
@ScheduleON/OFFEMEANA  @NotAutomatable
Scenario Outline:Schedule ON/OFF when there is no schedule for stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
Given Stat with <Condition>
Then Verify there is no option to Schedule ON/OFF
Examples:
|Condition   |
|No schedule |
|Stat Offline|



@ScheduleONMultistatEMEANAHB @Automatable
Scenario Outline:Schedule ON in the stat1 doesnot affect other stats schedule OFF status in the location with Multi stat(Jasper EMEA) or with Multi stat(Jasper NA,HBB) for time format 24/12hr 
As an user 
I want to turn schedule ON from OFF 
So that I can run schedule whenever I want to apply set automatically   
Given Stat1 with <Schedule>
When User turns schedule ON in the stat1 from schedule screen 
Then Verify the schedule OFF overlay disappeared in the schedule screen for stat1
And Verify the status on the solution card for following schedule for stat1
And Verify schedule ON in the stat1 doesnot affect other stats schedule OFF status in the location
Examples:
|Schedule         |
|time based    |
|geofence based| 


@ScheduleOFFMultistatEMEANAHB @Automatable
Scenario Outline:Schedule OFF in the stat1 doesnot affect other stats schedule ON status in the location with Multi stat(Jasper EMEA) or with Multi stat(HBB,Jasper NA) for systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to turn schedule OFF from ON 
So that I can run schedule whenever I want to apply set automatically   
Given Stat1 with <Schedule>
When User turns schedule OFF in the stat1 from schedule screen 
Then Verify the schedule OFF overlay in the schedule screen for stat1
And Verify the status on the solution card for schedule OFF for stat1
And Verify schedule OFF in the stat1 doesnot affect other stats schedule ON status in the location
Examples:
|Schedule         |
|time based    |
|geofence based| 


@ErrormessageScheduleON/OFFEMEANA @NotAutomatable
Scenario Outline:To get error messages on Schedule ON/OFF for stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user
I want to get error message while attempt to Schedule ON/OFF  
So that I will get notified on system unavailability
Given With the <Condition>
When Schedule ON/OFF from schedule screen   
Then Verify the error message
Examples:
|Condition                       |
|Mobile internet(3G/wifi) is down|
|Mobile in Airplane mode         |
|Mobile with low signal(3G/wifi) |
|CHIL down                       |
|Switching between the network   |
|Smart network switch            |