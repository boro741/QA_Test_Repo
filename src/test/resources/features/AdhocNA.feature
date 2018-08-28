@AdhocOverride @Comfort
Feature: I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.

#Following schedule and using settings

#JasperNA
@AdhocOverrideTimebaseSchedulefollowingfromsolutioncard			@Automated
Scenario Outline:   I want to verify time Following Schedule from solution card  for systems Heat cool,Heat and Cool with temperature scale celcius (OR) fahrenheit and with time format 12 (OR) 24hr 
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen 
Examples:
| Mode 		| 
| Cool 		|
#| Heat		| 
#| Auto 		| 
#| Cool only	| 
#| Heat only	|


@AdhocOverrideGeofencebaseScheduleusing			@Automated
Scenario Outline: I want to verify geofence using for systems Heat cool,Heat and Cool with temperature scale celcius (OR) fahrenheit and with time format 12 (OR) 24hr 
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
When user launches and logs in to the Lyric application
And user thermostat set <Period> with <Geofence>
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the <Schedule status> on the "PRIMARY CARD" screen
Examples:
| Mode 	| scheduling					 	| Schedule status		| Geofence     | Period	|
| Cool 	| Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
| Cool 	| geofence based               	| Using Sleep Settings 	| UserArrived  |Sleep	|
#| Cool | Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Heat | Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Heat | Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Heat | geofence based               	| Using Sleep Settings 	| UserArrived  | Sleep	|
#| Auto | geofence based               	| Using Sleep Settings 	| UserArrived  | Sleep	|
#| Auto | Without sleep geofence based 	| Using Home Settings  	| UserArrived  | Home	|
#| Auto | Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|


@AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowing			@Automated
Scenario Outline: To verify following base switching mode is changed for Heat, auto, cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user thermostat has <Period> currently following in "Time Based" schedule
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
| Mode  | ChangeMode		| Period |
| Heat  | Cool        	| WAKE   |
#| Heat | Cool        	| AWAY   |
#| Heat | Cool        	| HOME   |
#| Heat | Cool        	| SLEEP  |
#| Cool | Heat        	| WAKE   |
#| Cool | Heat        	| AWAY   |
#| Cool | Heat       	| HOME   |
#| Cool | Heat        	| SLEEP  |


@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule		@Automated
Scenario Outline:  To verify following base switching  mode is changed for "Heat, auto, cool" system with auto changeover enabled
Given user has <Mode> system mode
When user launches and logs in to the Lyric application
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
And verify the "Following Schedule Not Displayed" on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
 | Mode  | ChangeMode |
 | Heat  | Cool       |
#| Cool  | Auto       |
#| Heat  | Cool       |
#| Heat  | Cool       |
#| Cool  | Heat       |
#| Cool  | Heat       |
#| Cool  | Heat       |
#| Cool  | Heat       |


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousing			@Automated
Scenario Outline:  To verify geofence switching modes is Heat , auto ,cool system with auto changeover enabled
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
#| Heat 	| Cool			| Without sleep geofence based 	| Using Away Settings  	| UserDeparted | Away	|
#| Heat 	| Cool			| geofence based 				| Using Sleep Settings  	| UserArrived  | Home	|
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


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusing			@Automated
Scenario Outline:  To verify using schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
And user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the <Schedule status> on the "PRIMARY CARD" screen
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <ChangeMode>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode		| ChangeMode	| Schedule status		| Geofence     | Period	|
| Heat 		| Cool		| Using Home Settings  	| UserArrived  | Home	|
#| Heat 		| Cool		| Using Away Settings  	| UserDeparted | Away	|
#| Heat 		| Cool		| Using Sleep Settings	| UserArrived  | Home	|
#| Heat 		| Auto		| Using Home Settings  	| UserArrived  | Home	|
#| Heat 		| Auto		| Using Away Settings  	| UserDeparted | Away	|
#| Heat 		| Auto		| Using Sleep Settings  	| UserArrived  | Home	|
#| Cool 		| Heat		| Using Home Settings  	| UserArrived  | Home	|
#| Cool 		| Heat		| Using Away Settings  	| UserDeparted | Away	|
#| Cool 		| Heat		| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto 		| Heat		| Using Home Settings  	| UserArrived  | Home	|
#| Auto 		| Heat		| Using Away Settings  	| UserDeparted | Away	|
#| Auto 		| Heat		| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto 		| Heat		| Using Home Settings  	| UserArrived  | Home	|
#| Auto 		| Heat		| Using Away Settings  	| UserDeparted | Away	|
#| Auto 		| Heat		| Using Sleep Settings  	| UserArrived  | Home	|
#| Auto 		| Cool		| Using Home Settings  	| UserArrived  | Home	|
#| Auto 		| Cool		| Using Away Settings  	| UserDeparted | Away	|
#| Auto 		| Cool		| Using Sleep Settings  	| UserArrived  | Home	|
	

#JasperNA
@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing   		@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
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
| Cool	| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|
#| Heat | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|
#| Auto | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|


#TemporaryHold (Time and geofence)

#JasperNA
@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCard			@Automated
Scenario Outline:  I want to verify time Temporary override Schedule from solution card  for systems Heat cool,Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has <Mode> system mode 
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
|Mode | CPeriod | NPeriod |
#|Cool | Home| Wake |
|Cool| Home| Away |
#|Cool | Home | SLEEP |
#|Cool | Wake | Home |
#|Cool | Wake | Away |
#|Cool | Wake | SLEEP |
#|Cool | SLEEP | Wake |
#|Cool | SLEEP | Away |
#|Cool | SLEEP | Home |
#|Cool | Away | Home |
#|Cool | Away | Wake |
#|Cool | Away | SLEEP |
#|Heat | Home | Wake |
#|Heat | Home | Away |
#|Heat | Home | SLEEP |
#|Heat | Wake | Home |
#|Heat | Wake | Away |
#|Heat | Wake | SLEEP |
#|Heat | SLEEP | Wake |
#|Heat | SLEEP | Away |
#|Heat | SLEEP | Home |
#|Heat | Away | Home |
#|Heat | Away | Wake |
#|Heat | Away | SLEEP |
#|Auto | Home | Wake |
#|Auto | Home | Away |
#|Auto | Home | SLEEP |
#|Auto | Wake | Home |
#|Auto | Wake | Away |
#|Auto | Wake | SLEEP |
#|Auto | SLEEP | Wake |
#|Auto | SLEEP | Away |
#|Auto | SLEEP | Home |
#|Auto | Away | Home |
#|Auto | Away | Wake |
#|Auto | Away | SLEEP |
#|Cool Only | Home | Away |
#|Cool Only  | Home | SLEEP |
#|Cool Only  | Wake | Home |
#|Cool Only  | Wake | Away |
#|Cool Only  | Wake | SLEEP |
#|Cool Only  | SLEEP | Wake |
#|Cool Only  | SLEEP | Away |
#|Cool Only  | SLEEP | Home |
#|Cool Only  | Away | Home |
#|Cool Only  | Away | Wake |
#|Cool Only  | Away | SLEEP |
#|Heat Only | Home | Away |
#|Heat Only| Home | SLEEP |
#|Heat Only| Wake | Home |
#|Heat Only| Wake | Away |
#|Heat Only| Wake | SLEEP |
#|Heat Only| SLEEP | Wake |
#|Heat Only| SLEEP | Away |
#|Heat Only| SLEEP | Home |
#|Heat Only| Away | Home |
#|Heat Only| Away | Wake |
#|Heat Only| Away | SLEEP |


#JasperNA
@AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboard			@Automated
Scenario Outline:  I want to verify time Temporary override Schedule from solution card for systems Heat, cool, Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen

Examples:
| Mode 			| 
| Cool 			| 
#| Heat 			| 
#| Auto 			| 
#| Cool only		| 
#| Heat only		|


#JasperNA
@AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordSetpointChangeAndNEXTPeriodResume					@Automated
Scenario Outline: Verify time temporary hold setpoint dashboard and next period resumefor systems Heat cool,Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user thermostat has <CPeriod> and <NPeriod> current and next period "time based" schedule
When user launches and logs in to the Lyric application
And user has "Temporary Dashboard" status
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
Then verify the "Temporary" on the "PRIMARY CARD" screen
When verify next schedule period activated
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Then user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
|Mode | CPeriod | NPeriod |
#|Cool | Home| Wake |
|Cool| Home| Away |
#|Cool | Home | SLEEP |
#|Cool | Wake | Home |
#|Cool | Wake | Away |
#|Cool | Wake | SLEEP |
#|Cool | SLEEP | Wake |
#|Cool | SLEEP | Away |
#|Cool | SLEEP | Home |
#|Cool | Away | Home |
#|Cool | Away | Wake |
#|Cool | Away | SLEEP |
#|Heat | Home | Wake |
#|Heat | Home | Away |
#|Heat | Home | SLEEP |
#|Heat | Wake | Home |
#|Heat | Wake | Away |
#|Heat | Wake | SLEEP |
#|Heat | SLEEP | Wake |
#|Heat | SLEEP | Away |
#|Heat | SLEEP | Home |
#|Heat | Away | Home |
#|Heat | Away | Wake |
#|Heat | Away | SLEEP |
#|Auto | Home | Wake |
#|Auto | Home | Away |
#|Auto | Home | SLEEP |
#|Auto | Wake | Home |
#|Auto | Wake | Away |
#|Auto | Wake | SLEEP |
#|Auto | SLEEP | Wake |
#|Auto | SLEEP | Away |
#|Auto | SLEEP | Home |
#|Auto | Away | Home |
#|Auto | Away | Wake |
#|Auto | Away | SLEEP |
#|Cool Only | Home | Away |
#|Cool Only  | Home | SLEEP |
#|Cool Only  | Wake | Home |
#|Cool Only  | Wake | Away |
#|Cool Only  | Wake | SLEEP |
#|Cool Only  | SLEEP | Wake |
#|Cool Only  | SLEEP | Away |
#|Cool Only  | SLEEP | Home |
#|Cool Only  | Away | Home |
#|Cool Only  | Away | Wake |
#|Cool Only  | Away | SLEEP |
#|Heat Only | Home | Away |
#|Heat Only| Home | SLEEP |
#|Heat Only| Wake | Home |
#|Heat Only| Wake | Away |
#|Heat Only| Wake | SLEEP |
#|Heat Only| SLEEP | Wake |
#|Heat Only| SLEEP | Away |
#|Heat Only| SLEEP | Home |
#|Heat Only| Away | Home |
#|Heat Only| Away | Wake |
#|Heat Only| Away | SLEEP |


#JasperNA
@AdhocOverrideGeofencebaseScheduleTempHoldSolCardSetpointChangeAndNEXTPeriodResume			@Automated
Scenario Outline: Verify geofence temporary hold setpoint solution card and next period resume for systems Heat cool, Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user thermostat set <NPeriod> with <NGeofence>
Then verify the <AfterCrossTemporary> on the "PRIMARY CARD" screen

Examples:
| Mode 	| scheduling 					| Period				| Geofence		| NPeriod 		| NGeofence				| AfterCrossTemporary	|
| Cool 	| Without sleep geofence based 	| HOME 				| UserArrived	| Away 			| UserDeparted 			| USING AWAY SETTINGS	|
| Cool 	| Without sleep geofence based 	| AWAY 				| UserDeparted  	| HOME			| UserArrived  			| USING HOME SETTINGS	|
#| Cool 	| geofence based 				| AWAY 				| UserDeparted	| Sleep			| UserArrived			| USING SLEEP SETTINGS	|
#| Cool 	| geofence based 	            	| Sleep		 		| UserArrived	| AWAY			| UserDeparted			| USING AWAY SETTINGS	|
#| Heat | Without sleep geofence based 	| HOME				| UserArrived	| Away			| UserDeparted			| USING AWAY SETTINGS	|
#| Heat | Without sleep geofence based 	| AWAY				| UserDeparted  	| HOME			| UserArrived  			| USING HOME SETTINGS	|
#| Heat | geofence based 				| AWAY 				| UserDeparted	| Sleep			| UserArrived			| USING SLEEP SETTINGS	|
#| Heat | geofence based					| Sleep 				| UserArrived	| AWAY			| UserDeparted			| USING AWAY SETTINGS 	|
#| Auto | Without sleep geofence based 	| HOME 				| UserArrived 	| Away 			| UserDeparted 			| USING AWAY SETTINGS  	|
#| Auto | Without sleep geofence based 	| AWAY 				| UserDeparted	| HOME			| UserArrived  			| USING HOME SETTINGS  	|
#| Auto | geofence based 				| AWAY 				| UserDeparted 	| Sleep			| UserArrived  			| USING SLEEP SETTINGS  	|
#| Auto | geofence based 	            	| Sleep 				| UserArrived  	| AWAY			| UserDeparted			| USING AWAY SETTINGS  	|


#JasperNA
@AdhocOverrideGeofencebaseScheduleTemporaryHoldDashboardSetpointChangeAndNEXTPeriodResume			@Automated
Scenario Outline: I want to verify geofence repoint change and resume in next period on systems Heat cool, Heat and Cool with temperature scale celcius or fahrenheit and with time format 12hr or 24hr 
Given user has <Mode> system mode
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
Then verify the <AfterCrossTemporary> on the "PRIMARY CARD" screen

Examples:
| Mode 	| scheduling 					| Period		| Geofence		| NPeriod 	| NGeofence 		| AfterCrossTemporary	|
| Cool 	| Without sleep geofence based 	| HOME 		| UserArrived 	| Away 		| UserDeparted 	| USING AWAY SETTINGS  	|
| Cool 	| Without sleep geofence based 	| AWAY 		| UserDeparted	| HOME		| UserArrived  	| USING HOME SETTINGS  	|
| Cool 	| geofence based 				| AWAY 		| UserDeparted 	| Sleep		| UserArrived  	| USING SLEEP SETTINGS  	|
| Cool 	| geofence based 	            	| Sleep		| UserArrived  	| AWAY 		| UserDeparted  	| USING AWAY SETTINGS  	|
#| Heat | Without sleep geofence based 	| HOME 		| UserArrived 	| Away 		| UserDeparted 	| USING AWAY SETTINGS  	|
#| Heat | Without sleep geofence based 	| AWAY 		| UserDeparted  	| HOME		| UserArrived  	| USING HOME SETTINGS  	|
#| Heat | geofence based 				| AWAY 		| UserDeparted 	| Sleep		| UserArrived  	| USING SLEEP SETTINGS  	|
#| Heat | geofence based					| Sleep 		| UserArrived  	| AWAY 		| UserDeparted  	| USING AWAY SETTINGS  	|
#| Auto | Without sleep geofence based 	| HOME 		| UserArrived 	| Away 		| UserDeparted 	| USING AWAY SETTINGS  	|
#| Auto | Without sleep geofence based 	| AWAY 		| UserDeparted  	| HOME		| UserArrived  	| USING HOME SETTINGS  	|
#| Auto | geofence based 				| AWAY 		| UserDeparted 	| Sleep		| UserArrived  	| USING SLEEP SETTINGS  	|
#| Auto | geofence based 	            	| Sleep 		| UserArrived  	| AWAY 		| UserDeparted  	| USING AWAY SETTINGS  	|


#JasperNA
@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoTemporaryHold			@Automated
Scenario Outline:  To verify time base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode 
And user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
|Mode|UMode	  	  | Period |
|Heat|Cool        |WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Cool        |WAKE |
#|Heat|Cool        |AWAY |
#|Heat|HEAT        | AWAY |
#|Cool|Heat        |AWAY |
#|Cool|Cool        |AWAY |
#|Heat|Cool        |HOME |
#|Heat|HEAT        | HOME |
#|Cool|Heat        |HOME |
#|Cool|Cool        |HOME |
#|Heat|Cool        |SLEEP |
#|Heat|HEAT        | SLEEP |
#|Cool|Heat        |SLEEP |
#|Cool|Cool        |SLEEP |
#|Auto|Heat        | WAKE |
#|Auto|Cool        |WAKE |
#|Auto|Auto        | WAKE |
#|Heat|Auto        | WAKE |
#|Cool|Auto        |WAKE |
#|Auto|Heat        | AWAY |
#|Auto|Cool        |AWAY |
#|Auto|Auto        | AWAY |
#|Heat|Auto        | AWAY |
#|Cool|Auto        |AWAY |
#|Auto|Heat        | HOME |
#|Auto|Cool        |HOME |
#|Auto|Auto        | HOME |
#|Heat|Auto        | HOME |
#|Cool|Auto        |HOME |
#|Auto|Heat        | SLEEP |
#|Auto|Cool        |SLEEP |
#|Auto|Auto        | SLEEP |
#|Heat|Auto        | SLEEP |
#|Cool|Auto        |SLEEP |


#JasperNA
@AdhocOverrideTimeSchedulingChangeDifferentModeHeatCoolAutoOFFTemporaryHold 				@Automated
Scenario Outline:  To verify time base schedule switching  mode is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
And user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| UMode	| Period	|
| Heat	| Cool 	| WAKE 	|
#| Cool	| Heat	| WAKE 	|
#| Heat	| Cool	| AWAY 	|
#| Cool	| Heat	| AWAY 	|
#| Heat	| Cool	| HOME 	|
#| Heat	| Auto	| HOME 	|
#| Cool	| Heat	| HOME 	|
#| Cool	| Auto 	| HOME 	|
#| Heat	| Cool	| SLEEP	|
#| Cool	| Heat	| SLEEP	|
#incaserequired
#| Auto	| Heat	| WAKE 	|
#| Auto	| Cool	| WAKE 	|
#| Auto	| Heat	| AWAY 	|
#| Auto	| Cool	| AWAY 	|
#| Auto	| Heat	| SLEEP	|
#| Auto	| Cool	| SLEEP	|
#| Auto	| Heat	| HOME	|
#| Auto	| Cool	| HOME	|
#| Cool	| Auto	| SLEEP	|
#| Heat	| Auto	| SLEEP	|
#| Heat	| Auto	| AWAY 	|
#| Cool	| Auto	| AWAY 	|
#| Cool	| Auto	| WAKE 	|
#| Heat	| Auto	| WAKE 	|


#JasperNA
@AdhocOverrideTimeSchedulingChangeSameModeHeatCoolAutoOFFTemporaryHold			@Automated
Scenario Outline:  To verify time base schedule switching  mode is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
And  user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
#And user should be displayed with adhocoverride <Period> setpoint value
And user should be displayed with "respective period" setpoint value

Examples:
| Mode			| Period		| 
| Cool			| WAKE 		|
| Heat			| WAKE 		|
| Heat 			| AWAY 		|
| Auto			| WAKE 		|
#| Auto			| AWAY 		|
#| Cool			| AWAY 		|
#| Auto			| HOME 		|
#| Heat			| HOME 		|
#| Cool			| HOME 		|
#| Auto			| SLEEP 		|
#| Heat			| SLEEP 		|
#| Cool			| SLEEP 		|
#| Cool only		| SLEEP 		|
#| Cool only		| WAKE 		|
#| Cool only		| HOME 		|
#| Cool only	 	| AWAY 		|
#| Heat only		| SLEEP 		|
#| Heat only		| WAKE 		|
#| Heat only 	| HOME 		|
#| Heat only 	| AWAY 		|


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHold				@Automated
Scenario Outline:  To verify geofence switching modes is Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
When user has "Temporary" status
And user should be displayed with "respective period" setpoint value
When user changes system mode to <Mode>
When user has "Temporary" status
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| UMode	| Period	| scheduling 					| Geofence 		|
| Heat	| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Heat	| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Heat	| Cool	| Sleep | Without sleep geofence based 	| UserArrived	|
#| Cool	| Heat	| Home 	| Without sleep geofence based 	| UserArrived	|
#| Cool	| Heat	| Away 	| geofence based 				| UserDeparted 	|
#| Cool	| Heat	| Sleep 	| Without sleep geofence based 	| UserArrived 	|
#incaserequired 
#| Auto	| Heat	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Heat 	| Away 	| geofence based 				| UserDeparted 	|
#| Auto	| Heat	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Auto	| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Auto	| Cool	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Cool	| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Auto	| Away 	| geofence based 				| UserDeparted 	|
#| Cool	| Auto	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Heat	| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Heat	| Auto	| Away 	| geofence based 				| UserDeparted 	|
#| Heat	| Auto	| Sleep 	| Without sleep geofence based 	| UserArrived 	|


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold			@Automated
Scenario Outline:  To verify geofence schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <Mode>
When user has "Temporary" status
#And user should be displayed with adhocoverride <Period> setpoint value
And user should be displayed with "respective period" setpoint value

Examples:
| Mode	| Period| scheduling 					| Geofence		|
| Heat  | Home 	| Without sleep geofence based 	| UserArrived 	|
| Heat 	| Away 	| geofence based					| UserDeparted 	|
#| Heat	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Heat	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Heat	| Away	| geofence based 				| UserDeparted 	|
#| Heat	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Cool	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Cool	| Sleep | Without sleep geofence based 	| UserArrived 	|
#incaserequired 
#| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto 	| Away 	| geofence based 				| UserDeparted 	|
#| Auto 	| Sleep | Without sleep geofence based 	| UserArrived 	|
#| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Away 	| geofence based 				| UserDeparted 	|
#| Auto	| Sleep | Without sleep geofence based 	| UserArrived 	|


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangeModeWithDifferentModesHeatCoolAutoOFFTemporaryHold		@Automated
Scenario Outline:  To verify geofence schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
When user has "Temporary" status
#And user should be displayed with respective <Period> setpoint value
And user should be displayed with "respective period" setpoint value
Examples:
| Mode	| UMode	| Period	| scheduling 					| Geofence 		|
| Heat	| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
| Heat	| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Heat	| Cool	| Sleep 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Heat	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	 |Heat	| Away	| geofence based 				| UserDeparted 	|
#| Cool	| Heat	| Sleep 	| Without sleep geofence based 	| UserArrived 	|
#incaserequired 
#| Auto	| Heat	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Heat	| Away 	| geofence based 				| UserDeparted 	|
#| Auto	| Heat	| Sleep 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Cool	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Auto	| Cool	| Away 	| geofence based 				| UserDeparted 	|
#| Auto	| Cool	| Sleep 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Cool	| Auto 	| Away 	| geofence based 				| UserDeparted 	|
#| Cool	| Auto	| Sleep	| Without sleep geofence based 	| UserArrived 	|
#| Heat	| Auto	| Home 	| Without sleep geofence based 	| UserArrived 	|
#| Heat	| Auto	| Away 	| geofence based 				| UserDeparted 	|
#| Heat	| Auto	| Sleep 	| Without sleep geofence based 	| UserArrived 	|


#JasperNA
@AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold  @AutomatedOnAndroid
Scenario Outline:  To verify delete current period and remove hold  when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
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
#And verify the setpoint value of <PPeriod> 
Examples:
|Mode | PPeriod | CPeriod | NPeriod |
#|Cool |Away| Home| Wake |
#|Cool |Sleep| Home| Wake |
|Cool| Wake | Home| Away |
#|Cool| Sleep| Home| Away |
#|Heat |Away| Home| Wake |
#|Heat |Sleep| Home| Wake |
#|Heat| Wake | Home| Away |
#|Heat| Sleep| Home| Away |


#JasperNA
@AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold @Automated
Scenario Outline:  To verify delete Next period and remove hold  when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
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
#|Cool |Away| Home| Wake |
#|Cool |Sleep| Home| Wake |
|Cool| Wake | Home| Away |
#|Cool| Sleep| Home| Away |
#|Heat |Away| Home| Wake |
#|Heat |Sleep| Home| Wake |
#|Heat| Wake | Home| Away |
#|Heat| Sleep| Home| Away |


#JasperNA
@AdhocOverrideCreateTimebasescheduleTemporaryHold			@Automated
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
| Cool		| time base 			| Time base schedule 	|
#| HEAT		| time base 			| Time base schedule 	|
#| AUTO		| time base 			| Time base schedule 	|
#| Heat only	| time base 			| Time base schedule 	|
#| Cool only	| time base 			| Time base schedule 	|
#| Cool		| geofence based 	| Time base schedule 	|
#| HEAT		| geofence based  	| Time base schedule 	|
#| AUTO		| geofence based  	| Time base schedule 	|
#| Heat only	| geofence based  	| Time base schedule 	|
#| Cool only	| geofence based 	| Time base schedule 	|


#JasperNA
@AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHold		@Automated 
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
| Mode		| CurrentSchedule	| NEW Schedule		|
| Cool		| time base 			| Time base schedule	|
#| HEAT		| time base 			| Time base schedule	|
#| AUTO		| time base 			| Time base schedule	|
#| Heat only	| time base 			| Time base schedule	|
#| Cool only	| time base 			| Time base schedule	|
#| Cool		| geofence based 	| Time base schedule	|
#| HEAT		| geofence based  	| Time base schedule	|
#| AUTO		| geofence based  	| Time base schedule	|
#| Heat only	| geofence based  	| Time base schedule	|
#| Cool only	| geofence based 	| Time base schedule	|


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


#JasperNA
@AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHold 			@Automated
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
#| Cool	| Home		| UserArrived		| Geofence base schedule		| Using Home Settings	| Without	|
#| Cool	| Sleep		| UserArrived		| Geofence base schedule		| Using Sleep Settings	| With		| #Start and end 
#| Heat	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable
#| Cool	| Away		| UserDeparted		| Geofence base schedule		| Using Sleep Settings	| With		| #Not automatable


#JasperNA
@AdhocOverridetimebaseschedulingdeleteALLPERIODSTemporaryHold				@Automated
Scenario Outline:  To verify delete all periods when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user has "Temporary" status
Then verify the "Temporary" on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "Primary card" screen
And user selects "Grouped days" view
When user edit Time schedule by deleting "All 4 Periods" on confirming the period deletion
When user navigates to "Scheduling" screen from the "Dashboard" screen
Then verify "No Schedule" screen is shown in view schedule screen
And user should be displayed with "respective period" setpoint value

Examples:
|Mode| Period | 
|Cool| Sleep |
#|HEAT|Sleep|
#|AUTO|Sleep|
#|Heat only|Sleep|
#|Cool only|Sleep|
#|Cool| AWAY |
#|HEAT|AWAY|
#|AUTO|AWAY|
#|Heat only|AWAY|
#|Cool only|AWAY|
#|Cool| HOME |
#|HEAT|HOME|
#|AUTO|HOME|
#|Heat only|HOME|
#|Cool only|HOME|
#|Cool| Wake |
#|HEAT|Wake|
#|AUTO|Wake|
#|Heat only|Wake|
#|Cool only|Wake|


#JasperNA
@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictime			@Automated
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


#JasperNA
@AdhocOverrideCreateGeofencebasescheduleAspecifictime			@Automated
Scenario Outline:  To verify creates geofence base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Geofence based" scheduling with default values "Without" sleep settings
And user thermostat set <Period> with <Geofence>
Then verify the <Schedule status> on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value

Examples:
|Mode|Period|Geofence| Schedule status |
|HEAT|Home|UserArrived| Using Home Settings | 
|Cool|Away|UserDeparted| Using Away Settings |
#|AUTO| Geofence base schedule| Using Home Settings | 
#|Heat only| Geofence base schedule| Using Home Settings | 
#|Cool only| Geofence base schedule| Using Home Settings |
#|Cool|   Geofence base schedule| Using Away Settings | 
#|HEAT| Geofence base schedule| Using Away Settings | 
#|AUTO| Geofence base schedule| Using Away Settings | 
#|Heat only| Geofence base schedule| Using Away Settings | 
#|Cool only| Geofence base schedule| Using Away Settings |
#|Cool|  Geofence base schedule| Using Sleep Settings | 
#|HEAT| Geofence base schedule| Using Sleep Settings | 
#|AUTO| Geofence base schedule| Using Sleep Settings | 
#|Heat only| Geofence base schedule| Using Sleep Settings | 
#|Cool only| Geofence base schedule| Using Sleep Settings | 


@AdhocOverrideCreateTimebasescheduleOFFModeAspecifictime		@Automated
Scenario Outline: To Verify create time base schedule in off mode  
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
And user changes system mode to <Mode>
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value
Examples:
|Mode|
#|HEAT|
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
Scenario Outline:  To verify create time base schedule when mode is changed for Heat , auto ,cool system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <CurrentSchedule> schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
And user creates "Same Every Day" schedule with default schedule value
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user "should be updated" with the <Mode> option 
And user should be displayed with "respective period" setpoint value
Examples:
|Mode| CurrentSchedule | NEW Schedule |
|Cool| time base | Time base schedule |
#|HEAT|time base |Time base schedule |
#|AUTO|time base |Time base schedule |
#|Heat only|time base |Time base schedule |
#|Cool only|time base |Time base schedule |
#|Cool| geofence based | Time base schedule |
#|HEAT|geofence based  |Time base schedule |
#|AUTO|geofence based  |Time base schedule |
#|Heat only|geofence based  |Time base schedule |
#|Cool only|geofence based |Time base schedule |


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseSchedulespecifictimedeleteallperiods		@Automated
Scenario Outline:   I want to verify delete all period and no schedule status for systems Heat cool,Heat and Cool
 #with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user navigates to "Scheduling" screen from the "Primary card" screen
And user selects "Grouped days" view
When user edit Time schedule by deleting "All 4 Periods" on confirming the period deletion
When user navigates to "Scheduling" screen from the "Dashboard" screen
Then verify "No Schedule" screen is shown in view schedule screen
And user should be displayed with "respective period" setpoint value

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
Given user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Remove hold" from adhoc
Then verify the "Following Schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value

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
Given user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" adhoc status 

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only


#JasperNA
@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHold			@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode
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
| Mode	| scheduling			| Schedule status		| Geofence     | Period		|
| Cool	| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|
#| Heat | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|
#| Auto | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|


#JasperNA
@AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHold				@Automated
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set "Home" with <Geofence>
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using Sleep Settings" on the "PRIMARY CARD" screen
When user has "Temporary" status
#Then verify the "Temporary" on the "PRIMARY CARD" screen
When user selects "Remove hold" from adhoc
Then verify the "USING SLEEP SETTINGS" on the "PRIMARY CARD" screen

Examples:
| Mode	| scheduling			| Schedule status		| Geofence     | Period		|
| Cool	| geofence based		| Using Sleep Settings 	| UserArrived  | Sleep		|
| Heat | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|
#| Auto | geofence based		| Using Sleep Settings	| UserArrived  | Sleep		|



#Requirements : Thermostat should be set to Permanent Hold 
@AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHold		@Automated
Scenario Outline:   I want to verify permanent hold to specific time and resume for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user has <Mode> system mode
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
And user selects "Permanent hold" from adhoc
Then user has "PERMANENT" adhoc status
And user holds the schedule until time "greater than 12 hours" from current time
#And user should be displayed with time reverted back to 12hours gap from present time
And user holds the schedule until time "default" from current time
#Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When specific time hold completed 
Then verify the "Following schedule" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value
Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 


#JasperNA
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictime		@Automated
Scenario Outline:  To verify switching modes Heat , auto ,cool and off system with auto changeover enabled
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user changes system mode to "Off"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
Then user has "no" adhoc status 
When user changes system mode to <UMode>
Then user has "temporary" adhoc status 
And user should be displayed with "respective period" setpoint value

Examples:
|Mode|UMode	  |   Period |
|Heat|Cool        |WAKE |
#|Cool|Heat        |AWAY |
#|Heat|Cool        |HOME |
#|Cool|Heat        |SLEEP |

#|Auto|Heat        | WAKE |
#|Auto|Cool        |WAKE |
#|Auto|Auto        | WAKE |
#|Heat|Auto        | WAKE |
#|Heat|HEAT        | WAKE |
#|Cool|Heat        |WAKE |
#|Cool|Auto        |WAKE |
#|Cool|Cool        |WAKE |
#|Auto|Heat        | AWAY |
#|Auto|Cool        |AWAY |
#|Auto|Auto        | AWAY |
#|Heat|Cool        |AWAY |
#|Heat|Auto        | AWAY |
#|Heat|HEAT        | AWAY |
#|Cool|Auto        |AWAY |
#|Cool|Cool        |AWAY |
#|Auto|Heat        | HOME |
#|Auto|Cool        |HOME |
#|Auto|Auto        | HOME |
#|Heat|Auto        | HOME |
#|Heat|HEAT        | HOME |
#|Cool|Heat        |HOME |
#|Cool|Auto        |HOME |
#|Cool|Cool        |HOME |
#|Auto|Heat        | SLEEP |
#|Auto|Cool        |SLEEP |
#|Auto|Auto        | SLEEP |
#|Heat|Cool        |SLEEP |
#|Heat|Auto        | SLEEP |
#|Heat|HEAT        | SLEEP |
#|Cool|Auto        |SLEEP |
#|Cool|Cool        |SLEEP |
#|Cool only |Cool only       |SLEEP |
#|Cool only |Cool only       |WAKE |
#|Cool only |Cool only       |HOME |
#|Cool only |Cool only       |AWAY |
#|Heat only | Heat only       |SLEEP |
#| Heat only | Heat only       |WAKE |
#| Heat only | Heat only       |HOME |
#| Heat only | Heat only       |AWAY |


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatauto			@Automated
Scenario Outline:   I want to verify switching modes Heat , auto ,cool and off with temperature scale celciusfahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user thermostat has <Period> currently following in "Time Based" schedule
Given user launches and logs in to the Lyric application
Given user has <Mode> system mode
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user changes system mode to <UMode>
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
When user changes system mode to <Mode>
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card

Examples:
|Mode|UMode	  |   Period |
|Auto|Heat        | WAKE |
#|Auto|Cool        |WAKE |
#|Auto|Auto        | WAKE |
#|Heat|Cool        |WAKE |
#|Heat|Auto        | WAKE |
#|Heat|HEAT        | WAKE |
#|Cool|Heat        |WAKE |
#|Cool|Auto        |WAKE |
#|Cool|Cool        |WAKE |
#|Auto|Heat        | AWAY |
#|Auto|Cool        |AWAY |
#|Auto|Auto        | AWAY |
#|Heat|Cool        |AWAY |
#|Heat|Auto        | AWAY |
#|Heat|HEAT        | AWAY |
#|Cool|Heat        |AWAY |
#|Cool|Auto        |AWAY |
#|Cool|Cool        |AWAY |
#|Auto|Heat        | HOME |
#|Auto|Cool        |HOME |
#|Auto|Auto        | HOME |
#|Heat|Cool        |HOME |
#|Heat|Auto        | HOME |
#|Heat|HEAT        | HOME |
#|Cool|Heat        |HOME |
#|Cool|Auto        |HOME |
#|Cool|Cool        |HOME |
#|Auto|Heat        | SLEEP |
#|Auto|Cool        |SLEEP |
#|Auto|Auto        | SLEEP |
#|Heat|Cool        |SLEEP |
#|Heat|Auto        | SLEEP |
#|Heat|HEAT        | SLEEP |
#|Cool|Heat        |SLEEP |
#|Cool|Auto        |SLEEP |
#|Cool|Cool        |SLEEP |


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint @Automated
Scenario Outline:   I want to verify setpoint change in dashboard for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user launches and logs in to the Lyric application
Given user has <Mode> system mode
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "thermostat Dashboard" screen from the "thermostat solution card" screen
And user has "Temporary Dashboard" status
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 


#Requirements : Thermostat should be set to A specific time 
@AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint @Automated
Scenario Outline:   I want to verify setpoint change in solution card for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user thermostat is set to "time based" schedule
Given user launches and logs in to the Lyric application
Given user has <Mode> system mode
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And user holds the schedule until time "lesser than 12 hours" from current time
When user navigates to "thermostat Dashboard" screen from the "thermostat solution card" screen
And user edits set point from "Primary card"
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "TEMPORARY" on the "PRIMARY CARD" screen
And user should be displayed with "respective period" setpoint value in solution card
Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only|

#Permanent hold (Time base Schedule)

#JasperNA
@AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetNA @Automated
Scenario Outline: I want to verify action sheet view  for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
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

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 

@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityNA @Automated
Scenario Outline: I want to verify cancel functionality for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has <AdhocStatus> status
Then verify the <AdhocStatus> on the "PRIMARY CARD" screen
And user selects "Cancel" from adhoc
Then verify the <AdhocStatus> on the "PRIMARY CARD" screen
Examples:
|Mode | AdhocStatus |
|Cool | PERMANENT | 
|Cool | Temporary | 
#|Heat | PERMANENT | 
#|Heat | Temporary | 
#|Auto | PERMANENT | 
#|Auto | Temporary | 
#|Cool only|  PERMANENT | 
#|Cool only|  Temporary | 
#|Heat only|  PERMANENT | 
#|Heat only|  Temporary | 

@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCard @Automated
Scenario Outline: I want to verify permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
Examples:
|Mode | 
|Cool | 
|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 


@AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchange  @Automated
Scenario Outline: I want to verify setpoint change solution card  for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user taps on "UP STEPPER"
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value
Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 

@AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchange  @Automated
Scenario Outline: I want to verify setpoint change dashboard for systems Heat cool,Heat and Cool with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
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

Examples:
|Mode | 
|Cool | 
#|Heat | 
#|Auto | 
#|Cool only| 
#|Heat only| 


@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoTemporaryHold @Automated
Scenario Outline: To verify change modes for "Heat , auto ,cool," system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values
When user changes system mode to <Mode>
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

Examples:
|Mode|UMode	  |   Period |
|Heat|Cool        |WAKE |
#|Cool|Heat        |WAKE |
#|Heat|Cool        |AWAY |
|Cool|Heat        |AWAY |
#|Heat|Cool        |HOME |
|Cool|Heat        |HOME |
|Heat|Cool        |SLEEP |
#|Cool|Heat        |SLEEP |
#|Cool|Auto        |SLEEP |
#|Auto|Heat        | WAKE |
#|Auto|Cool        |WAKE |
#|Heat|Auto        | SLEEP |
#|Auto|Heat        | SLEEP |
#|Auto|Cool        |SLEEP |
#|Cool|Auto        |HOME |
#|Heat|Auto        | WAKE |
#|Cool|Auto        |WAKE |
#|Heat|Auto        | AWAY |
#|Auto|Heat        | AWAY |
#|Auto|Cool        |AWAY |
#|Heat|Auto        | HOME |
#|Auto|Heat        | HOME |
#|Auto|Cool        |HOME |
#|Cool|Auto        |AWAY |

@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHold @Automated
Scenario Outline: To verify change modes for "Heat , auto ,cool and off " system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
And user thermostat has <Period> currently following in "Time Based" schedule
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
When user changes system mode to "OFF"
Then verify the "ADHOCOVERRIDE NOT DISPLAYED" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values
When user changes system mode to <Mode>
And user should be displayed with "OVERRIDE SETPOINT" setpoint value

Examples:
|Mode|UMode	  |   Period |
|Heat|Cool        |WAKE |
#|Cool|Heat        |WAKE |
#|Heat|Cool        |AWAY |
#|Cool|Heat        |AWAY |
#|Heat|Cool        |HOME |
#|Cool|Heat        |HOME |
#|Heat|Cool        |SLEEP |
#|Cool|Heat        |SLEEP |
#|Cool|Auto        |SLEEP |
#|Auto|Heat        | WAKE |
#|Auto|Cool        |WAKE |
#|Heat|Auto        | SLEEP |
#|Auto|Heat        | SLEEP |
#|Auto|Cool        |SLEEP |
#|Cool|Auto        |HOME |
#|Heat|Auto        | WAKE |
#|Cool|Auto        |WAKE |
#|Heat|Auto        | AWAY |
#|Auto|Heat        | AWAY |
#|Auto|Cool        |AWAY |
#|Heat|Auto        | HOME |
#|Auto|Heat        | HOME |
#|Auto|Cool        |HOME |
#|Cool|Auto        |AWAY |

@AdhocOverrideTimebaseSchedulePermanentRemoveHold  @Automated
Scenario Outline: I want to verify override Permanent schedule - Remove Hold  with temperature scale celcius fahrenheit and with time format 12 24hr 
Given user has <Mode> system mode 
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
|Mode | Period |
|Cool | WAKE |
#|Heat | WAKE |
#|Auto | WAKE |
#|Cool only| WAKE |
#|Heat only|WAKE |
#|Cool | AWAY |
#|Heat | AWAY |
#|Auto | AWAY |
#|Cool only| AWAY |
#|Heat only|AWAY |
#|Cool | SLEEP |
#|Heat | SLEEP |
#|Auto | SLEEP |
#|Cool only| SLEEP |
#|Heat only|SLEEP |
#|Cool | HOME |
#|Heat | HOME |
#|Auto | HOME |
#|Cool only| HOME |
#|Heat only|HOME |


@AdhocOverridetimebaseschedulingdeleteALLPERIODSPermanentHold @Automated
Scenario Outline:  To verify delete all periods and no schedule status for "Heat , auto ,cool" system with auto changeover enabled
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "Primary card" screen
And user selects "Grouped days" view
When user edit Time schedule by deleting "All 4 Periods" on confirming the period deletion
When user navigates to "Scheduling" screen from the "Dashboard" screen
Then verify the "No Schedule" on the "PRIMARY CARD" screen
When user changes system mode to <UMode>
Then verify the "No Schedule" on the "PRIMARY CARD" screen

Examples:
|Mode|UMode	| 
|Heat|Cool |
#|Cool|Heat |
#|Heat|Cool |
|Cool|Heat  |
#|Heat|Cool |
#|Cool|Heat |
#|Heat|Cool |
#|Cool|Heat |
#|Cool|Auto |
#|Auto|Heat |
#|Auto|Cool |
#|Heat|Auto |
#|Auto|Heat |
#|Auto|Cool |
#|Cool|Auto |
#|Heat|Auto |
#|Cool|Auto |
#|Heat|Auto |
#|Auto|Heat|
#|Auto|Cool|
#|Heat|Auto |
#|Auto|Heat |
#|Auto|Cool|
#|Cool|Auto|