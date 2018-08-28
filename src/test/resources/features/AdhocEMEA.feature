@AdhocOverride @Comfort
Feature: As a user  
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.


#Following schedule and using settings

@AdhocOverrideTimebaseSchedulefollowingfromsolutioncardEMEA			@Automated
Scenario: I want to verify time follwoing Schedule from solution card  for systems Heat  with temperature scale celcius orfahrenheit and with time format 12 or24hr 
Given user launches and logs in to the Lyric application
When user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen 


 @AdhocOverrideGeofencebaseScheduleusingEMEA			@Automated
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
When user thermostat has <Period> currently following in "Time Based" schedule
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
| Period |
| P1 |
#| P2|
#| P3|
#| P4|

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
And user should be displayed with "respective period" setpoint value


@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousingEMEA		@Automated
Scenario Outline:  To verify geofence switching modes is "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the <Schedule status> on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
When user changes system mode to <Mode>
And user should be displayed with "respective period" setpoint value

Examples:
| Mode 	| ChangeMode		| scheduling					 	| Schedule status		| Geofence     | Period	|
| Heat 	| Cool			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
| Heat 	| Cool			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
| Heat 	| Cool			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|
#| Heat | Auto			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Heat | Auto			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Heat | Auto			| geofence based 				| Using Sleep Settings	| UserArrived  | Home	|
#| Cool | Heat			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Cool | Heat			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Cool | Heat			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto | Heat			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Auto | Heat			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Auto | Heat			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto | Heat			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Auto | Heat			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Auto | Heat			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto | Cool			| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Auto | Cool			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Auto | Cool			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|


@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA			@Automated
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


@AdhocOverrideCreateGeofencebasescheduleAspecifictimeEMEA			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat" system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the "Heat" option 
And user should be displayed with "respective period" setpoint value

Examples:
|Period|Geofence| Schedule status |
|Home|UserArrived| Using Home Settings | 
|Away|UserDeparted| Using Away Settings |
#|Home| Geofence base schedule| Using Home Settings | 
#|Away| Geofence base schedule| Using Away Settings | 
#|Heat only| Geofence base schedule| Using Away Settings | 
#|HEAT| Geofence base schedule| Using Sleep Settings | 
#|Heat only| Geofence base schedule| Using Sleep Settings |
 

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleduplicateEMEA			@Automated
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


@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA			@Automated
Scenario Outline:  To verify using schedule switching modes is changed for Heat , auto ,cool and off system with auto changeover enabled
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


@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat" system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set "Home" with <Geofence>
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using Sleep Settings" on the "PRIMARY CARD" screen
When user has "Temporary" status
#Then verify the "Temporary" on the "PRIMARY CARD" screen
When user deletes the <Period> 
Then verify the "USING HOME SETTINGS" on the "PRIMARY CARD" screen

Examples:
| scheduling			| Schedule status		| Geofence     | Period		|
| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|
| geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|
| geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|


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


#TemporaryHold (Time and geofence)


#JasperEMEA
@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA			@ToBeAutomated
Scenario Outline:   I want to verify time Temporary override Schedule from solution card for systems Heat with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
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


@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboardEMEA			@Automated
Scenario:  I want to verify time Temporary override Schedule from solution card for systems Heat with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen


#JasperEMEA
@AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsetpointchangeEMEA		@ToBeAutomated
Scenario Outline:   I want to verify time Temporary override from solution card and resume in next period for systems Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
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


@AdhocOverrideTimeschedulingChangemodeHeatOffTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify time base switching  mode is changed for "Heat, Off" system with auto changeover enabled
Given user has <Mode> system mode 
And user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period 	|
| Heat	| P1 		|
| Heat	| P2 		|
| Heat	| P3 		|
| Heat	| P4 		|


@AdhocOverrideCreateTimebasescheduleTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify create time base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value

Examples:
| Mode		| CurrentSchedule	| NEWSchedule 			|
| HEAT		| time base 			| Time base schedule 	|
#| Heat only	| time base 			| Time base schedule 	|
#| HEAT		| geofence based  	| Time base schedule 	|
#| Heat only	| geofence based  	| Time base schedule 	|


@AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHoldEMEA			@Automated 
Scenario Outline: To Verify create time base schedule in off mode  
Given user has <Mode> system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
Then verify the "Following schedule NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode		| CurrentSchedule	| NEWSchedule 			|
| HEAT		| time base 			| Time base schedule 	|
#| Heat only	| time base 			| Time base schedule 	|
#| HEAT		| geofence based  	| Time base schedule 	|
#| Heat only	| geofence based  	| Time base schedule 	|


@AdhocOverrideCreateGeofencebasecheduleTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values <Sleep> sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period		| Geofence			| NEW Schedule				| Schedule status		| Sleep 		|
| HEAT	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
#| HEAT	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| #Start and end 
#| Heat	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable


@AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHoldEMEA			@Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
And user thermostat set <Period> with <Geofence>
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values <Sleep> sleep settings
When user changes system mode to <Mode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen

Examples:
| Mode	| Period		| Geofence			| NEW Schedule				| Schedule status		| Sleep 		|
| HEAT	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
#| HEAT	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| #Start and end
#| Heat	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable


@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA			@Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
And user changes system mode to <Mode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period		| Geofence			| NEW Schedule			| Schedule status		|
| HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Home Settings	| 
#|HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Away Settings	| 
#|HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Sleep Settings	| 


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

#JasperNA
@AdhocOverrideCreateGeofencebasecheduleTemporaryHold			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values <Sleep> sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period		| Geofence			| NEW Schedule				| Schedule status		| Sleep 		|
| HEAT	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
#| HEAT	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| #Start and end 
#| Cool	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
#| Cool	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| #Start and end 
#| Heat	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable
#| Cool	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable


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



