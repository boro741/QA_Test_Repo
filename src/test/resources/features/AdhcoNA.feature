@AdhocOverride @Comfort
Feature:   
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.

#Following schedule and using settings

 #JasperNA
@AdhocOverrideTimebaseSchedulefollowingfromsolutioncard @Automated
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

@AdhocOverrideGeofencebaseScheduleusing @Automated
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

@AdhocOverrideTimeschedulingChangemodeHeatcoolfollwoing @Automated
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

@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule @Automated
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
