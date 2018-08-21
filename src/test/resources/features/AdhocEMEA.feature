@AdhocOverride @Comfort
Feature: As a user  
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.


#Following schedule and using settings

@AdhocOverrideTimebaseSchedulefollowingfromsolutioncardEMEA @Automated
Scenario: I want to verify time follwoing Schedule from solution card  for systems Heat  with temperature scale celcius orfahrenheit and with time format 12 or24hr 
Given user launches and logs in to the Lyric application
When user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen 


 @AdhocOverrideGeofencebaseScheduleusingEMEA @Automated
Scenario Outline:I want to verify geofence using for systems Heat  with temperature scale celcius or fahrenheit and with time format 12 or 24hr 
Given user launches and logs in to the Lyric application
And user has "Heat" system mode
Then user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the <Schedule status> on the "PRIMARY CARD" screen
Examples:
 |scheduling					 |Schedule status	   |Geofence     |Period|
#|Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|Without sleep geofence based |Using Away Settings  |UserDeparted |Away|
#|Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
#|Without sleep geofence based |Using Away Settings  |UserDeparted |Away|
#|geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
#|Without sleep geofence based |Using Away Settings  |UserDeparted |Away|

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowingEMEA		@Automated
Scenario Outline: To verify following base switching  mode is changed for Heat, auto, cool system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user thermostat has <Period> currently following in "Time Based" schedule
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
| Period |
| WAKE   |
| AWAY   |
| HOME   |
| SLEEP  |

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleEMEA		@Automated
Scenario:  To verify following base switching  mode is changed for "Heat, auto, cool" system with auto changeover enabled
Given user has "Heat" system mode
When user launches and logs in to the Lyric application
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "System Is Off" on the "PRIMARY CARD" screen
And verify the "Following Schedule Not Displayed" on the "PRIMARY CARD" screen
When user changes system mode to "Heat"
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card


@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA @Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
And user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
And user changes system mode to "Heat"
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the "Heat" option 
And user should be displayed with "respective period" setpoint value in solution card

Examples:
| Period	| Geofence			| NEW Schedule				| Schedule status		|
| Home		| UserArrived		| Geofence base schedule	| Using Home Settings	| 
#| Home		| UserArrived		| Geofence base schedule	| Using Away Settings	| 
#| Home		| UserArrived		| Geofence base schedule	| Using Sleep Settings	|

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleEMEA@Automated
Scenario Outline:  To verify following base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
And user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the "Heat" option 
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Period|Geofence| Schedule status |
|Home|UserArrived| Using Home Settings | 
|Away|UserDeparted| Using Away Settings |


@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA @Automated
Scenario Outline:  To verify using schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has "Heat" system mode
When user launches and logs in to the Lyric application
And user thermostat set <Period> with <Geofence>
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the <Schedule status> on the "PRIMARY CARD" screen
Then user should be displayed with "respective period" setpoint value in solution card
When user changes system mode to "Off"
And verify the "System Is Off" on the "PRIMARY CARD" screen
When user changes system mode to "Heat"
And verify the <Schedule status> on the "PRIMARY CARD" screen
Then user should be displayed with "respective period" setpoint value in solution card
Examples:
|ChangeMode	|scheduling					 	|Schedule status	   	|Geofence     |Period|
| Off		| Without sleep geofence based 	|Using Home Settings  	|UserArrived  |Home|
#| Off		| Without sleep geofence based 	|Using Away Settings  	|UserDeparted |Away|
#| Off		| geofence based 				|Using Sleep Settings 	|UserArrived  |Home|

@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodusingEMEA @UIAutomatable
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
When user launches and logs in to the Lyric application
And user thermostat set <Period> with <Geofence>
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the <Schedule status> on the "PRIMARY CARD" screen
When user is in <Current PERIOD>
Then user should be displayed with "USING SLEEP SETTINGS"
When user deletes the <Current Period> 
Then user should be displayed with "USING HOME SETTINGS" 

Examples:
|scheduling						|Schedule status			|Geofence     |Period	|
|Without sleep geofence based	|Using Home Settings		|UserArrived  |Home		|
#|geofence based              	|Using Sleep Settings 		|UserArrived  |Sleep	|
#|Without sleep geofence based 	|Using Away Settings  		|UserDeparted |Away		|	


@AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA			@Automated
Scenario:   I want to verify remove hold for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Remove hold" from adhoc
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card

@AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA			@Automated
Scenario:   I want to verify specific time to permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" status 

 #JasperEMEA
@AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardEMEA
Scenario Outline:   I want to verify time Temporary override Schedule from solution card  for systems   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples:
|Mode | 
|Heat | 


#JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsetpointchangeEMEA
 Scenario Outline:   I want to verify time Temporary override from solution card and  resume in next period for systems Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with updated setpoint in adhoc override "HOLD xx UNTIL XX:XX" on "Solutioncard"
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples:
|Mode | 
|Heat | 



#JasperEMEA
@AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordEMEATemporaryHold 
 Scenario Outline:   I want to verify time Temporary override from dashboard and  resume in next period for systems   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples:
|Mode | 
|Heat | 

 #JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordsetpointEMEA
 Scenario Outline:   I want to verify setpoint change on dashboard when   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx UNTIL XX:XX" 
Examples:
|Mode | 
|Heat | 


 #JasperEMEA
 @AdhocOverrideGeofencebaseScheduleTemporaryHoldSolutionCardEMEATemporaryHoldEMEA 
 Scenario Outline:   I want to verify setpoint change on solution card when   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Geofence base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When next schedule period activated or crossed fense 
Then user should be displayed with "USING" <NPERIOD> "SETTINGS"
Examples:
|Mode | PERIOD | NPERIOD |
|Heat | HOME |AWAY OR SLEEP |
|Heat | AWAY |HOME OR SLEEP |
|HEAT | SLEEP |HOME OR AWAY |


#JasperEMEA
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatOFFTemporaryHoldEMEA 
Scenario:  To verify override retained even mode is changed for "Heat OFF" system  
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX"  override with set temperature till next schedule 
When user change the "OFF" from "HEAT"
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx UNTIL XX:XX"  override 
When suer change the "HEAT" from "OFF"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user should be displayed with Perviously adjusted setpoint value


#JasperEMEA
@AdhocOverrideScheduletemperatureGeofencebaseSchedulingChangemodeHeatOFFTemporaryHoldEMEA
Scenario Outline:  To verify override retained even mode is changed for "Heat , OFF" system  
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When user change the "OFF" from "HEAT"
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD XX WHILE" <PERIOD> override 
When suer change the "HEAT" from "OFF"
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
And user should be displayed with Perviously adjusted setpoint value

Examples:
| Period |
|HOME |
|AWAY |
|SLEEP|


#JasperEMEA
@AdhocOverridetimebaseschedulingdeletecurrentperiodEMEATemporaryHoldEMEA
Scenario Outline:  To verify delete current period and remove hold  in "Heat" system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user deletes the <Current Period> 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user selects the "Remove hold" through "AdhocOverried Action sheet"
Then user should be displayed with "Following schedule" with previous period 
And suer should be displayed with previous period setpoint value 

Examples:
| Current PERIOD | 
|P1|
|P2|
|P3|
|P4|
|P5|
|P6|

#JasperEMEA
@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodEMEATemporaryHoldEMEA 
Scenario:  To verify delete current period mode in "Heat" system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “Sleep Period”
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE SLEEP" override with set temperature till next schedule 
When user deletes the "Sleep period"
Then user should be displayed with "USING HOME SETTINGS" 


#JasperEMEA
@AdhocOverridegeofencebaseschedulingremoveholdEMEATemporaryHoldEMEA
Scenario Outline:  To verify remove hold in  "Heat" system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <current PERIOD> override 
When user selects the "Remove hold" through "AdhocOverried Action sheet"
Then user should be displayed with "USING" <current PERIOD> "SETTINGS"

Examples:
| Current PERIOD | 
|Sleep|
|AWAY|
|HOME|



