@ScheduleON/OFF
Feature: 
As an user 
I want to turn schedule OFF or ON 
So that I can run schedule whenever I want to apply set points automatically 


#JapserNA
@ScheduleOFFNA 
Scenario Outline:Schedule OFF the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule   
Given user launches and login to application
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 
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
|Cool||Time schedule    |


#JapserNA
@ScheduleONNAtimebase
Scenario Outline:Schedule ON the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to turn schedule ON from OFF
So that schedule will be turned back to follow schedule 
  Given user launches and login to application 
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the "schedule OFF" overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "schedule OFF" overlay disappeared in the schedule screen
And Verify the "Following schedule" status on the solution card 
Examples:
|Mode |Schedule         |
|Cool|Time schedule    |
|Cool|Time schedule    |
|Heat|Time schedule    |
|Auto|Time schedule    |
|Heat only|Time schedule    |
|Cool only|Time schedule    |
|Cool||Time schedule    |

#JapserNA
@ScheduleONNAgeofencebase
Scenario Outline:Schedule ON the stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to turn schedule ON from OFF
So that schedule will be turned back to follow schedule 
  Given user launches and login to application 
Then user set to <Mode>
And user Stat with <Schedule>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "USING" <PERIOD> SETTINGS" status on the solution card 

Examples:
|MODE|scheduel|period | 
|Heat|Geofence schedule|Home|
|Heat|Geofence schedule|sleep|
|Heat|Geofence schedule|away |
|Auto|Geofence schedule|Home |
|Auto|Geofence schedule|sleep |
|Auto|Geofence schedule|way |
|Heat only|Geofence schedule|Home/ |
|Heat only|Geofence schedule|sleep |
|Heat only|Geofence schedule|way |
|Cool only|Geofence schedule|Home|
|Cool only|Geofence schedule|sleepy |
|Cool only|Geofence schedule|away |


@ScheduleOFFAdhocOverrideNAtimebase
Scenario Outline:Schedule OFF the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr  
As an user 
I want to turn schedule OFF 
So that I will be able to turned off schedule whenever I don't want to run schedule  
  Given user launches and login to application 
Then user set to <Mode>
And user stat with <schedule> 
And user Stat with <AdhocOverride>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 
Examples:
|Mode|Schedule | Ahocoverride    |
|Heat| Time schedule| Temporary |
|Heat| Time schedule| Permanent |
|Cool| Time schedule| Temporary |
|Cool| Time schedule| Permanent |
|auto| Time schedule| Temporary |
|auto| Time schedule| Permanent |
|Cool only| Time schedule| Temporary |
|Cool only | Time schedule| Permanent |
|Heat only| Time schedule| Temporary |
|Heat only | Time schedule| Permanent |
|Heat|Geofence schedule| Temporary |
|Heat|Geofence schedule| Permanent |
|Cool|Geofence schedule| Temporary |
|Cool|Geofence schedule| Permanent |
|auto|Geofence schedule| Temporary |
|auto|Geofence schedule| Permanent |
|Cool only|Geofence schedule| Temporary |
|Cool only |Geofence schedule| Permanent |
|Heat only|Geofence schedule| Temporary |
|Heat only |Geofence schedule| Permanent |

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
|Heat|Geofence schedule| Temporary |
|Heat|Geofence schedule| Permanent |
|Cool|Geofence schedule| Temporary |
|Cool|Geofence schedule| Permanent |
|auto|Geofence schedule| Temporary |
|auto|Geofence schedule| Permanent |
|Cool only|Geofence schedule| Temporary |
|Cool only |Geofence schedule| Permanent |
|Heat only|Geofence schedule| Temporary |
|Heat only |Geofence schedule| Permanent |
|Heat| Time schedule| Temporary |
|Heat| Time schedule| Permanent |
|Cool| Time schedule| Temporary |
|Cool| Time schedule| Permanent |
|auto| Time schedule| Temporary |
|auto| Time schedule| Permanent |
|Cool only| Time schedule| Temporary |
|Cool only | Time schedule| Permanent |
|Heat only| Time schedule| Temporary |
|Heat only | Time schedule| Permanent |





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
Scenario Outline:Schedule ON the stat  with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr 
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
Scenario Outline:Schedule ON/OFF status while switching modes to off and from off for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
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

#Negative scenario
@ScheduleON/OFFEMEANA 
Scenario Outline:Schedule ON/OFF when there is no schedule for stat   with systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
Given Stat with <Condition>
Then Verify there is no option to Schedule ON/OFF
Examples:
|Condition   |
|No schedule |
|Stat Offline|



@ScheduleONMultistatEMEANA 
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
|Time schedule    |
|Geofence schedule| 


@ScheduleOFFMultistatEMEANA 
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
|Time schedule    |
|Geofence schedule| 


@ErrormessageScheduleON/OFFEMEANA
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

Schedule on/off specific time , 


