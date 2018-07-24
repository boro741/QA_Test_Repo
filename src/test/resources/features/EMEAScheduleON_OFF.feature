@ScheduleON_OFF
Feature: As an user I want to turn schedule OFF or ON So that I can run schedule whenever I want to apply set points automatically 


@ScheduleOFFONEMEA @Automated --LYR-29402
Scenario Outline:Schedule OFF ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius and for time format 24 12hr
#As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule  
Given user has "Heat" system mode
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
|scheduling |
|time based |
|geofence based |


@ScheduleOFFONEMEAtimebase @Automated --LYR-29401
Scenario Outline:Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
#As an user I want to turn schedule ON while running time base schedule   
Given user has "Heat" system mode
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
| scheduling | Schedule status |
|time based | following schedule |

@ScheduleONOFFEMEAgeofencebase
Scenario Outline:Schedule ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
#As an user I want to turn schedule ON from OFF So that schedule will be turned back to geofence based 
Given user has “Heat” system mode
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
| scheduling | Schedule status |
|geofence based|Using Home Settings|
|geofence based|Using Away Settings|
|geofence based|Using Sleep Settings|

#JapserEMEA
@ScheduleONEMEAgeofencebase
Scenario Outline:Schedule ON the stat with systems Heat for Temperture scale Celsius and for time format 2412hr
#As an user I want to turn schedule ON from OFFSo that schedule will be turned back to follow schedule 
Given user launches and login to application 
Then user set to “Heat”
And user Stat with “Geofence schedule”
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then Verify the "USING" <PERIOD> SETTINGS" status on the solution card 

Examples:
|Period|
|Home|
|Away|
|Sleep|



@ScheduleOFFAdhocOverridetimebase
Scenario Outline:Schedule OFF the stat with systems Heat for Temperture scale Celsius and for time format 24 12hr  
#As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule  
Given user launches and login to application 
Then user set to <Mode>
And user stat with <scheduling> 
And user Stat with <AdhocOverride>
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 
Examples:
|Mode|scheduling | Ahocoverride    |
|Heat| time base| Temporary |
|Heat| time base| Permanent |
|Heat|geofence based | Temporary |


@ScheduleONNAadhocoverride
Scenario Outline:Schedule ON the stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
#As an user I want to turn schedule ON from OFFSo that schedule will be turned back to follow schedule 
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
|Heat| time base| Temporary |
|Heat| time base| Permanent |

@ScheduleOFFVacationEMEA @Automated --LYR-29407
Scenario Outline:As an user I want to turn schedule OFF while vacation is active So that I will be able to turned off schedule whenever I don't want to run schedule
# Schedule OFF for stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
Given user has "Heat" system mode
Then user thermostat is set to <scheduling> schedule 
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And vacation mode is EMEA "active"
And Verify the "Vacation status EMEA" on the "PRIMARY CARD" screen
Then user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user selects "Schedule OFF overlay" from "Scheduling" screen
Then Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
And user navigates to "primary card" screen from the "SCHEDULING" screen
And vacation mode is "disable"
Examples:
| scheduling |
|time based|
#|geofence based | 



@ScheduleOFFONVacationEMEA @Automated --LYR-29406
Scenario: As an user I want to turn schedule ON So that my vaction will be back   
#Schedule ON the stat with systems Heat for Temperture scale Celsius and for time format 24 12hr 
Given user has "Heat" system mode
Then user thermostat is set to <scheduling> schedule
And user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When vacation mode is EMEA "active"
Then Verify the "Vacation status EMEA" on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "PRIMARY CARD" screen
When user selects "Option" from "Scheduling" screen
Then user selects "schedule off" from "Option" screen
And verify the "schedule off overlay" on the "Scheduling" screen
When user navigates to "primary card" screen from the "SCHEDULING" screen
Then verify the "Vacation status EMEA" on the "PRIMARY CARD" screen 
And vacation mode is "disable"
Then verify the "Schedule off Status" on the "PRIMARY CARD" screen
When user navigates to "SCHEDULING" screen from the "PRIMARY CARD" screen
Then user selects "Schedule OFF overlay" from "Scheduling" screen
And Verify the "Schedule OFF overlay disabled" on the "Scheduling" screen
Examples:
| scheduling |
| time based |
#| geofence based | 

@ScheduleONOFFEMEAswitchingmodes
Scenario Outline:Schedule ON OFF status while switching modes to off and from off for Temperture scale CelsiusFahrenheit and for time format 2412hr
Given user launches and login to application 
Then user set to “HEAT”
And user Stat with <AdhocOverride>
And user should be displayed with <Adhocoverride> status
When user changes the "OFF" from “HEAT”
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be displayed with <Adhocoverride> status
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 
When use changes the “HEAT” from "OFF"
Then user should display with "Shedule OFF"
When user changes the "OFF" from “HEAT”
Then user TAP on the "Schedule OFF" overlay on "Schedule" screen
When user changes the “HEAT”from "OFF"
Then user should be displayed with <Adhocoverride> status on "SolutionCard"
Examples:
| Adocoverride |
| Temporary |
| Permanent | 
| Vacation |
