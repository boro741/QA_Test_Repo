
@ScheduleON/OFF
Feature: 
As an user 
I want to turn schedule OFF or ON 
So that I can run schedule whenever I want to apply set points automatically 


@ScheduleOFFONEMEA
Scenario Outline:Schedule OFF ON the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius and for time format 24 12hr
#As an user I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule  
Given user has “Heat: system mode
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
| scheduling |
|time based |
|geofence based |


@ScheduleOFFONEMEAtimebase
Scenario Outline:Schedule OFF the stat with systems Heat cool,Cool,Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
#As an user I want to turn schedule ON while running time base schedule   
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
Scenario Outline:Schedule ON the stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 2412hr
As an user 
I want to turn schedule ON from OFF
So that schedule will be turned back to follow schedule 
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



@ScheduleOFFAdhocOverrideNAtimebase
Scenario Outline:Schedule OFF the stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr  
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
|Heat|Geofence schedule| Temporary |
|Heat|Geofence schedule| Permanent |


@ScheduleONNAadhocoverride
Scenario Outline:Schedule ON the stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr
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
|Heat| Time schedule| Temporary |
|Heat| Time schedule| Permanent |






@ScheduleOFFVacationEMEA
Scenario Outline:Schedule OFF for stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
As an user 
I want to turn schedule OFF while vacation is active 
So that I will be able to turned off schedule whenever I don't want to run schedule    
  Given user launches and login to application 
Then user set to “HEAT”
And user Stat with "Vacation"
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
And Verify the "Schedule off" status on the solution card 




@ScheduleONNAVacationEMEA
Scenario Outline:Schedule ON the stat with systems Heat for Temperture scale Celsius Fahrenheit and for time format 24 12hr 
As an user 
I want to turn schedule ON 
So that my vaction will be back   
  Given user launches and login to application 
Then user set to “Heat”
And user Stat with "Vacation"
When User "turns schedule off" the schedule from schedule screen
Then Verify the schedule OFF overlay in the schedule screen
When user TAP on the "Schedule OFF" overlay 
Then user should displayed with disappeared “Schedule OFF” overlay on “Schedule screen
And Verify the "Vacation" status on "SolutionCard"







@ScheduleON/OFFEMEAswitchingmodes
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

Examples :
| Adocoverride |
| Temporary |
| Permanent | 
 | Vacation |
