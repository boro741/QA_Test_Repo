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
Scenario Outline: To verify following base switching  mode is changed for Heat system with auto changeover enabled
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
Scenario:  To verify following base switching  mode is changed for Heat system with auto changeover enabled
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
Scenario Outline:  To verify geofence switching modes is Heat system with auto changeover enabled
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
| scheduling					 	| Schedule status		| Geofence     | Period	|
#| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|

@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA			@Automated
Scenario Outline:  To verify using schedule switching modes is changed for Heat and off system with auto changeover enabled
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



 

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleduplicateEMEA			@Automated
Scenario Outline:  To verify following base switching  mode is changed for Heat system with auto changeover enabled
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
#|Away|UserDeparted| Using Away Settings |

@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing_EMEA  		@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for Heat system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set "Home" with <Geofence>
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using Sleep Settings" on the "PRIMARY CARD" screen
When user deletes the <Period> 
Then verify the "USING HOME SETTINGS" on the "PRIMARY CARD" screen

Examples:
| Mode	| scheduling			| Schedule status		| Geofence     | Period		|
#| Heat | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|

@AdhocOverrideCreateTimebasescheduleFollowingscheduleEMEA			@Rework
Scenario Outline:  To verify create time base schedule when mode is changed for Heat system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following schedule" on the "PRIMARY CARD" screen
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

@AdhocOverrideCreateTimebasescheduleOFFModeNormalflowEMEA		@Rework 
Scenario Outline: To Verify create time base schedule in off mode  
Given user has <Mode> system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the <Schedule status> on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user creates "Same Every Day" schedule following specific <Period> time
And verify the "Following schedule NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
And verify the "Following schedule" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| Mode		| CurrentSchedule	| NEWSchedule 			|
| HEAT		| time base 			| Time base schedule 	|
#| Heat only	| time base 			| Time base schedule 	|
#| HEAT		| geofence based  	| Time base schedule 	|
#| Heat only	| geofence based  	| Time base schedule 	|


@AdhocOverrideCreateGeofencebasechedulethroughFollowingScheduleEMEA		@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "FLLOWING SCHEDULE" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user thermostat set <Period> with <Geofence>
And user creates "Geofence based" schedule following specific <Sleep period> time
And verify the <Schedule status> on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| Mode	| Period		| Geofence		| Schedule status		| Sleep period | 
| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#|HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#|HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#|HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#|HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#|HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#|HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |

@AdhocOverrideCreateGeofencebasescheduleOFFthroughFollowingSchedule 			@Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "FLLOWING SCHEDULE" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user thermostat set <Period> with <Geofence>
And user creates "Geofence based" scheduling with default values <Sleep Period> sleep settings
And user changes system mode to <Mode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period		| Geofence		| Schedule status		| Sleep period | 
| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#|HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#|HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#|HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#|HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#|HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#|HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |


#TemporaryHold (Time and geofence)

@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA			@Automated
Scenario Outline:  I want to verify time Temporary override Schedule from solution card  for systems Heat with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <CPeriod> and <NPeriod> current and next period "time based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When verify next schedule period activated
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| CPeriod | NPeriod |
| P1| P3 |
#| P1| P2|
#| P1| P4 |
#| P3 | P1 |
#| P3 | P2 |
#| P3 | P4 |
#| P4 | P3 |
#| P4 | P2 |
#| P4 | P1 |
#| P2 | P1 |
#| P2 | P3 |
#| P2| P4 |

@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboardEMEA			@Automated
Scenario:  I want to verify time Temporary override Schedule from solution card for systems Heat with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen


@AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordSetpointChangeAndNEXTPeriodResumeEMEA				@Automated
Scenario Outline: Verify time temporary hold setpoint dashboard and next period resumefor systems Heat cool,Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user thermostat has <CPeriod> and <NPeriod> current and next period "time based" schedule
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen
When verify next schedule period activated
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And verify respective <NPeriod> period setpoint values
Then user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
And verify respective <NPeriod> period setpoint values
Examples:
| CPeriod | NPeriod |
| P1| P3 |
#| P1| P2|
#| P1| P4 |
#| P3 | P1 |
#| P3 | P2 |
#| P3 | P4 |
#| P4 | P3 |
#| P4 | P2 |
#| P4 | P1 |
#| P2 | P1 |
#| P2 | P3 |
#| P2| P4 |

@AdhocOverrideGeofencebaseScheduleTempHoldSolCardSetpointChangeAndNEXTPeriodResumeEMEA			@Automated
Scenario Outline: Verify geofence temporary hold setpoint solution card and next period resume for systems Heat cool, Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user thermostat set <NPeriod> with <NGeofence>
Then verify the <AfterCrossTemporary> on the "PRIMARY CARD" screen
And verify respective <NPeriod> period setpoint values

Examples:
| scheduling 					| Period				| Geofence		| NPeriod 		| NGeofence				| AfterCrossTemporary	|
| Without sleep geofence based 	| HOME				| UserArrived	| Away			| UserDeparted			| USING AWAY SETTINGS	|
#| Without sleep geofence based 	| AWAY				| UserDeparted  	| HOME			| UserArrived  			| USING HOME SETTINGS	|
#| geofence based 				| AWAY 				| UserDeparted	| Sleep			| UserArrived			| USING SLEEP SETTINGS	|
#| geofence based					| Sleep 				| UserArrived	| AWAY			| UserDeparted			| USING AWAY SETTINGS 	|

@AdhocOverrideGeofencebaseScheduleTemporaryHoldDashboardSetpointChangeAndNEXTPeriodResumeEMEA			@Automated
Scenario Outline: I want to verify geofence repoint change and resume in next period on systems Heat cool, Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen
Then user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
And user thermostat set <NPeriod> with <NGeofence>
#And verify the respective <NPeriod> setpoint value from "Dashboard"
And user should be displayed with "respective period" setpoint value
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And verify the <AfterCrossTemporary> on the "PRIMARY CARD" screen
And verify respective <NPeriod> period setpoint values

Examples:
| scheduling 					| Period				| Geofence		| NPeriod 		| NGeofence				| AfterCrossTemporary	|
| Without sleep geofence based 	| HOME				| UserArrived	| Away			| UserDeparted			| USING AWAY SETTINGS	|
#| Without sleep geofence based 	| AWAY				| UserDeparted  	| HOME			| UserArrived  			| USING HOME SETTINGS	|
#| geofence based 				| AWAY 				| UserDeparted	| Sleep			| UserArrived			| USING SLEEP SETTINGS	|
#| geofence based					| Sleep 				| UserArrived	| AWAY			| UserDeparted			| USING AWAY SETTINGS 	|



@AdhocOverrideTimeSchedulingChangeSameModeHeatCoolAutoOFFTemporaryHoldEMEA		@Automated
Scenario Outline:  To verify time base schedule switching  mode is changed for Heat , auto ,cool and off system with auto changeover enabled
Given user has "HEAT" system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to "HEAT"
And  user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

Examples:
| Period		| 
|P1|
#|P2|
#|P3|
#|P4|

@AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHoldEMEA				@Automated
Scenario Outline:  To verify geofence switching modes is Heat system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Period| scheduling 					| Geofence 		|
| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Away 	| geofence based 				| UserDeparted 	|
#| Sleep | Without sleep geofence based 	| UserArrived	|

@AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold_EMEA			@Automated
Scenario Outline:  To verify geofence schedule switching modes is changed for Heat system with auto changeover enabled
Given user has "Home" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NO"T DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to "Heat"
When user has "Temporary" status
#And user should be displayed with adhocoverride <Period> setpoint value
And user should be displayed with "respective period" setpoint value

Examples:
| Period| scheduling 					| Geofence		|
#| Home 	| Without sleep geofence based 	| UserArrived 	|
| Away 	| geofence based					| UserDeparted 	|
#| Sleep | Without sleep geofence based 	| UserArrived 	|

@AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold_EMEA  @AutomatedOnAndroid
Scenario Outline:  To verify delete current period and remove hold  when mode is changed for Heat system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <PPeriod> as previous period <CPeriod> as current period and <NPeriod> as next period of "time based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user deletes the <CPeriod> time period
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user selects "Remove hold" from adhoc
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And verify respective <PPeriod> period setpoint values
Examples:
|Mode | PPeriod | CPeriod | NPeriod |
|Heat| P1 | P2| P3 |


@AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold_EMEA @Automated
Scenario Outline:  To verify delete Next period and remove hold  when mode is changed for Heat system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <CPeriod> as current period <NPeriod> as next period and <NNPeriod> as next period of "time based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user deletes the <NPeriod> time period
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user selects "Remove hold" from adhoc
Then verify the "Following schedule" on the "PRIMARY CARD" screen
#And verify the setpoint value of <CPeriod> 

Examples:
|Mode| CPeriod | NPeriod | NNPeriod |
|Heat| P1 | P2| P3 |


@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for Heat system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set "Home" with <Geofence>
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using Sleep Settings" on the "PRIMARY CARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user deletes the <Period> 
Then verify the "USING HOME SETTINGS" on the "PRIMARY CARD" screen

Examples:
| scheduling			| Schedule status		| Geofence     | Period		|
| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|


@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHoldEMEA				@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for Heat, auto, cool system with auto changeover enabled by removing hold from adhoc
Given user has "Heat" system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using Sleep Settings" on the "PRIMARY CARD" screen
When user has "Temporary" status
#Then verify the "Temporary" on the "PRIMARY CARD" screen
When user selects "Remove hold" from adhoc
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| scheduling			| Schedule status		| Geofence     | Period		|
#| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|
#| Without sleep geofence based 		| Using Away Settings 	| UserDeparted	  | Away		|
| Without sleep geofence based 		| Using Home Settings 	| UserArrived  | Home		|


@AdhocOverrideCreateTimebasescheduleTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify create time base schedule when mode is changed for Heat system with auto changeover enabled
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
#| HEAT		| geofence based  	| Time base schedule 	|


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
#| HEAT		| time base 			| Time base schedule 	|
| HEAT		| geofence based  	| Time base schedule 	|


@AdhocOverrideCreateGeofencebasecheduleTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heatsystem with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user thermostat set <Period> with <Geofence>
And user creates "Geofence based" scheduling with default values <Sleep> sleep settings
And verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period		| Geofence			| NEW Schedule				| Schedule status		| Sleep 		|
#| HEAT	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
| HEAT	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| 
#| Heat	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| 

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
| Mode	| Period		| Geofence		| Schedule status		| Sleep period | 
#| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |


@AdhocOverrideTimeschedulingChangemodeHeatOffTemporaryHoldEMEA			@Automated
Scenario Outline:  To verify time base switching  mode is changed for Heat, Off system with auto changeover enabled
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
#| Heat	| P2 		|
#| Heat	| P3 		|
#| Heat	| P4 		|

#Specific Time 

@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA			@Automated
Scenario Outline: To Verify create geofence schedule in off mode
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user thermostat set <Period> with <Geofence>
And user creates "Geofence based" scheduling with default values <Sleep Period> sleep settings
And user changes system mode to "Heat"
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value
Examples:
| Mode	| Period		| Geofence		| Schedule status		| Sleep period | 
| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |


@AdhocOverrideCreateGeofencebasescheduleAspecifictimeEMEA			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heat system with auto changeover enabled
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
| Period		| Geofence		| Schedule status		| Sleep period | 
| Home		| UserArrived		| Using Home Settings	| Without |
#| Away		| UserDeparted		| Using Away Settings	| Without |
#| Sleep		| UserArrived		| Using Sleep Settings	| With |


@AdhocOverrideCreateTimebasescheduleOFFModeAspecifictimeEMEA		@Automated
Scenario Outline: To Verify create time base schedule in off mode  
Given user has "Heat" system mode
And user thermostat is set to <Current schedule> schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
#And user creates "Same Every Day" schedule with default schedule value
Then user creates "Same Every Day" schedule following specific <Period> time
And user changes system mode to "Heat"
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the "Heat" option 
And user should be displayed with "respective period" setpoint value
Examples:
|Current schedule |Period|
#| time based |P1|
| geofence based |P1|
#|P2|
#|P3|
#|P4|


@AdhocOverrideCreateTimebasescheduleAspecifictime			@Automated
Scenario Outline:  To verify create time base schedule when mode is changed for Heat system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
#And user creates "Same Every Day" schedule with default schedule value
Then user creates "Same Every Day" schedule following specific <Period> time
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value
Examples:
|Current schedule |Period|
| time based |P1|
#| geofence based |P1|
#|P2|
#|P3|
#|P4|


@AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA			@Automated
Scenario Outline:   I want to verify remove hold for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode
Then user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Remove hold" from adhoc
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
|Period|
|P1|

@AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA			@Automated
Scenario Outline:   I want to verify specific time to permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode
Then user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" adhoc status 
And verify the "PERMANENT" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictimeEMEA		@Automated
Scenario Outline:  To verify switching modes Heat and off system with auto changeover enabled
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
Then user has "no" adhoc status 
When user changes system mode to "Heat"
Then user has "temporary" adhoc status 
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

Examples:
| Period |
#| P1 |
#| P2|
| P3|
#| P4|

@AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatautoEMEA			@Automated
Scenario Outline:   I want to verify switching modes Heat and off with temperature scale celciusfahrenheit and with time format 12 24hr 
Given user has "Heat" system mode
Then user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
| Period |
#| P1 |
| P2|
#| P3|
#| P4|

@AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint_EMEA @Automated
Scenario:   I want to verify setpoint change in dashboard for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user launches and logs in to the Lyric application
Given user has "Heat" system mode
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "thermostat Dashboard" screen from the "thermostat solution card" screen
And user has "Temporary Dashboard" status
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card




#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint_EMEA @Automated
Scenario:  I want to verify setpoint change in solution card for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user launches and logs in to the Lyric application
Given user has "Heat" system mode
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "thermostat Dashboard" screen from the "thermostat solution card" screen
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card

#Permanent hold (Time base Schedule)

@AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetEMEA @Automated
Scenario Outline: I want to verify action sheet view  for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user selects "Adhoc Status" from adhoc
And user should be displayed with the following "Action Sheet" options:
|Action Sheet|
|Permanent |
|A Specific Time|
|Remove Hold|
|Cancel |


@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityEMEA @Automated
Scenario Outline: I want to verify cancel functionality for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has <AdhocStatus> status
Then verify the <AdhocStatus> on the "PRIMARY CARD" screen
And user selects "Cancel" from adhoc
Then verify the <AdhocStatus> on the "PRIMARY CARD" screen
Examples:
| AdhocStatus |
#| PERMANENT | 
| Temporary | 

@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardEMEA @Automated
Scenario: I want to verify permanent hold status for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen


@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchangeEMEA  @Automated
Scenario: I want to verify setpoint change solution card  for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user taps on "UP STEPPER"
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

@AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchangeEMEA  @Automated
Scenario: I want to verify setpoint change dashboard for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
When user taps on "UP STEPPER"
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And verify the "PERMANENT" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value


@AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHoldEMEA	@Automated
Scenario:   I want to verify permanent hold to specific time and resume for systems Heat with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user has "Heat" system mode
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" adhoc status
And user holds the schedule until time "greater than 12 hours" from current time
And user should be displayed with time reverted back to 12hours gap from present time
And user holds the schedule until time "default" from current time
Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When verify next schedule period activated
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value


@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHoldEMEA			@Automated
Scenario Outline: To verify change modes for Heat and off system with auto changeover enabled
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to "Heat"
And user should be displayed with "OVERRIDE SETPOINT" setpoint value
Examples:
|Period|
#|P1|
#|P2|
#|P3|
|P4|

@AdhocOverrideTimebaseSchedulePermanentRemoveHoldEMEA			@Automated
Scenario Outline: I want to verify override Permanent schedule - Remove Hold  with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has "Heat" system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user selects "Remove Hold" from adhoc
Then verify the "FOLLOWING SCHEDULE" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
|Period|
|P1|
#|P2|
#|P3|
#|P4|



@AdhocOverrideCreateTimebaseschedulePermanentHoldEMEA @Automated
Scenario Outline: To Verify create time base schedule in off mode  
Given user has "Heat" system mode
And user thermostat is set to <Currentschedule> schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user creates "Same Every Day" schedule following specific <Period> time
And verify the "Following schedule" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| Currentschedule |Period |
| time based | P1 | 
#| time based | P2 |
#| time based | P3 | 
#| time based | P4 |  

@AdhocOverrideCreateTimebasescheduleOFFModePermanentHoldEMEA @Automated 
Scenario Outline: To Verify create time base schedule in off mode when PermanentHold
Given user has "Heat" system mode
And user thermostat is set to <Currentschedule> schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user creates "Same Every Day" schedule following specific <Period> time
And verify the "Following schedule NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
And verify the "Following schedule" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| Currentschedule |Period |
| time based | P1 | 
#| time based | P2 |
#| time based | P3 | 
#| time based | P4 |  



@AdhocOverrideCreateGeofencebaseschedulePermanentHoldEMEA @Automated
Scenario Outline: To Verify create geofence schedule when permanentHold
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user thermostat set <Period> with <Geofence>
And user creates "Geofence based" schedule following specific <Sleep period> time
And verify the <Schedule status> on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values

Examples:
| Period		| Geofence		| Schedule status		| Sleep period | 
| Home		| UserArrived		| Using Home Settings	| Without |
#| Away		| UserDeparted		| Using Away Settings	| Without |
#| Sleep		| UserArrived		| Using Sleep Settings	| With |




@AdhocOverrideCreateGeofencebasescheduleOFFPermanentHoldEMEA @Automated
Scenario Outline: To Verify create geofence schedule in off mode when permanentHold
Given user has "Heat" system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user changes system mode to "Off"
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user thermostat set <Period> with <Geofence>
And user creates "Geofence based" scheduling with default values <Sleep Period> sleep settings
And user changes system mode to <Mode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value

Examples:
| Period		| Geofence		| Schedule status		| Sleep period | 
| Home		| UserArrived		| Using Home Settings	| Without |
#| Away		| UserDeparted		| Using Away Settings	| Without |
#| Sleep		| UserArrived		| Using Sleep Settings	| With |