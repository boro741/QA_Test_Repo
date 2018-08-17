@AdhocOverride @Comfort
Feature: I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.

#Following schedule and using settings

#JasperNA
@AdhocOverrideTimebaseSchedulefollowingfromsolutioncard			@Automated
Scenario Outline:   I want to verify time Following Schedule from solution card  for systems Heat cool,Heat and Cool with temperature scale celcius (OR) fahrenheit and with time format 12 (OR) 24hr 
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen 
Examples:
|Mode | 
|Cool |
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only|


@AdhocOverrideGeofencebaseScheduleusing			@Automated
Scenario Outline: I want to verify geofence using for systems Heat cool,Heat and Cool with temperature scale celcius (OR) fahrenheit and with time format 12 (OR) 24hr 
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
Then user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the <Schedule status> on the "PRIMARY CARD" screen
Examples:
 |Mode |scheduling					 |Schedule status	   |Geofence     |Period|
#|Cool |Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
 |Cool |geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|Cool |Without sleep geofence based |Using Away Settings  |UserDeparted |Away|
#|Heat |Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
#|Heat |Without sleep geofence based |Using Away Settings  |UserDeparted |Away|
#|Heat |geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|Auto |geofence based               |Using Sleep Settings |UserArrived  |Sleep|
#|Auto |Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
#|Auto |Without sleep geofence based |Using Away Settings  |UserDeparted |Away|


@AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowing			@Automated
Scenario Outline: To verify following base switching  mode is changed for Heat, auto, cool system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
Then user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
|Mode  | ChangeMode | Period |
|Heat  |Cool        | WAKE   |
#|Heat |Cool        | AWAY   |
#|Heat |Cool        | HOME   |
#|Heat |Cool        | SLEEP  |
#|Cool |Heat        | WAKE   |
#|Cool |Heat        | AWAY   |
#|Cool |Heat        | HOME   |
#|Cool |Heat        | SLEEP  |


@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule		@Automated
Scenario Outline:  To verify following base switching  mode is changed for "Heat, auto, cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "System Is Off" on the "PRIMARY CARD" screen
And verify the "Following Schedule Not Displayed" on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
 |Mode  | ChangeMode |
 |Heat  | Cool       |
#|Cool  | Auto       |
#|Heat  | Cool       |
#|Heat  | Cool       |
#|Cool  | Heat       |
#|Cool  | Heat       |
#|Cool  | Heat       |
#|Cool  | Heat       |


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousing			@Automated
Scenario Outline:  To verify geofence switching modes is "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule 
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the <Schedule status> on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with respective <Period> setpoint value
When user changes system mode to <Mode>
And user should be displayed with respective <Period> setpoint value

Examples:
|Mode |ChangeMode	|scheduling					 |Schedule status	   |Geofence     |Period|
|Heat | Cool		| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Heat | Cool	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Heat | Cool	| geofence based |Using Sleep Settings  |UserArrived  |Home|
|Heat | Auto	| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Heat | Auto	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Heat | Auto	| geofence based |Using Sleep Settings  |UserArrived  |Home|
|Cool | Heat	| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Cool | Heat	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Cool | Heat	| geofence based |Using Sleep Settings  |UserArrived  |Home|
|Auto | Heat	| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Auto | Heat	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Auto | Heat	| geofence based |Using Sleep Settings  |UserArrived  |Home|
|Auto | Heat	| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Auto | Heat	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Auto | Heat	| geofence based |Using Sleep Settings  |UserArrived  |Home|
|Auto | Cool	| Without sleep geofence based |Using Home Settings  |UserArrived  |Home|
|Auto | Cool	| Without sleep geofence based |Using Away Settings  |UserDeparted  |Away|
|Auto | Cool	| geofence based |Using Sleep Settings  |UserArrived  |Home|


#JasperNA
@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictime			@Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
And user changes system mode to <Mode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value in solution card

Examples:
| Mode	| Period		| Geofence			| NEW Schedule			| Schedule status		|
| HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Home Settings	| 
#|HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Away Settings	| 
#|HEAT	| Home		| UserArrived		| Geofence base schedule	| Using Sleep Settings	| 


#JasperNA
@AdhocOverrideCreateGeofencebasescheduleAspecifictime			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Mode|Period|Geofence| Schedule status |
|HEAT|Home|UserArrived| Using Home Settings | 
|Cool|Away|UserDeparted| Using Away Settings |
#|AUTO| Geofence base schedule| Using Home Settings | 
#|Heat only| Geofence base schedule| HOME | 
#|Cool only| Geofence base schedule| HOME |
#|Cool|   Geofence base schedule| AWAY | 
#|HEAT| Geofence base schedule| AWAY | 
#|AUTO| Geofence base schedule| AWAY | 
#|Heat only| Geofence base schedule| AWAY | 
#|Cool only| Geofence base schedule| AWAY |
#|Cool|   Geofence base schedule| SLEEP | 
#|HEAT| Geofence base schedule| SLEEP | 
#|AUTO| Geofence base schedule| SLEEP | 
#|Heat only| Geofence base schedule| SLEEP | 
#|Cool only| Geofence base schedule| SLEEP | 

@AdhocOverrideCreateTimebasescheduleOFFModeAspecifictime		@Automated
Scenario Outline: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
And user changes system mode to <Mode>
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value in solution card
Examples:
|Mode|
|HEAT|
|Cool|
#|AUTO| Geofence base schedule| Using Home Settings | 
#|Heat only| Geofence base schedule| HOME | 
#|Cool only| Geofence base schedule| HOME |
#|Cool|   Geofence base schedule| AWAY | 
#|HEAT| Geofence base schedule| AWAY | 
#|AUTO| Geofence base schedule| AWAY | 
#|Heat only| Geofence base schedule| AWAY | 
#|Cool only| Geofence base schedule| AWAY |
#|Cool|   Geofence base schedule| SLEEP | 
#|HEAT| Geofence base schedule| SLEEP | 
#|AUTO| Geofence base schedule| SLEEP | 
#|Heat only| Geofence base schedule| SLEEP | 
#|Cool only| Geofence base schedule| SLEEP | 


@AdhocOverrideCreateTimebasescheduleAspecifictime			@Automated
Scenario Outline:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
And user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Mode| NEW Schedule |
|Cool| Time base schedule | 
|HEAT|Time base schedule |
|AUTO|Time base schedule |
|Heat only|Time base schedule |
|Cool only|Time base schedule |


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseSchedulespecifictimedeleteallperiods		@Automated
Scenario Outline:   I want to verify delete all period and no schedule status for systems Heat cool,Heat and Cool
 #with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user launches and logs in to the Lyric application
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user navigates to "Scheduling" screen from the "Primary card" screen
And user selects "Grouped days" view
When user edit Time schedule by deleting "All 4 Periods" on confirming the period deletion
When user navigates to "Scheduling" screen from the "Dashboard" screen
Then verify "No Schedule" screen is shown in view schedule screen
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseSchedulespecifictimeRemoveHold			@Automated
Scenario Outline:   I want to verify remove hold for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user launches and logs in to the Lyric application
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Remove hold" from adhoc
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only|


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseSchedulespecifictimetoPermanentHold			@Automated
Scenario Outline:   I want to verify specific time to permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user launches and logs in to the Lyric application
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" status 

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only|


#Requirements : Thermostat should be set to Permanent Hold 
@AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHold @InProgress
Scenario Outline:   I want to verify permanent hold to specific time and resume for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user launches and logs in to the Lyric application
And user thermostat is set to "time based" schedule
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" status
And user holds the schedule until time "greater than 12 hours" from current time
#Then user should displayed with "Please select proper time" pop up 
#And user should be displayed with time reverted back to 12hours gap from present time
And user holds the schedule until time "default" from current time
#Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
#When "A specific time" set time completed 
#Then user should be displayed with "Following schedule" 
#And user should be displayed with respective <Mode> period setpoint value
Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 
