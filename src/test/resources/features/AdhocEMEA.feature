@AdhocOverride @Comfort
Feature:   
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.


#Following schedule and using settings

 @AdhocOverrideTimebaseSchedulefollowingfromsolutioncardEMEA
 Scenario Outline :   I want to verify time follwoing Schedule from solution card  for systems Heat  with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" Through CHIL
And user is set to "Time base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "Following schedule" 



 @AdhocOverrideGeofencebaseScheduleusingEMEA
 Scenario Outline :   I want to verify geofence using for systems Heat  with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" Through CHIL
When user is set to "Geofence base schedule"
Then user navigates to "Solutioncard" screen from "Dashboard" screen
And user should be displayed with "USING" <PERIOD> "SETTINGS" 
Examples :
|Mode | PERIOD | 
|Heat | HOME |
|Heat | AWAY |
|HEAT | SLEEP |



@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleEMEA
Scenario Outline:  To verify following base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" Through CHIL
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "Following schedule" 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And  user should not be display with "Following schedule"  
When user change the <Mode> from "OFF"
Then user should be displayed with "Following schedule"  
And user should be displayed with respective <Period> setpoint value


Examples:
| Period |
|HOME |
|AWAY |
|SLEEP|
|Wake|


@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA
Scenario Outline:  To verify using schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" Through CHIL
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "USING" <PERIOD> "SETTINGS" 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And  user should not be display with "USING" <PERIOD> "SETTINGS" 
When user change the <Mode> from "OFF"
Then user should be displayed with "USING" <PERIOD> "SETTINGS" 
And user should be displayed with respective <Period> setpoint value


Examples:
| Period |
|HOME |
|AWAY |
|SLEEP|


@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodusingEMEA
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to "HEAT" Through CHIL
And user is in "SLEEP"
And user should be displayed with "USING SLEEP SETTINGS"
When user deletes the "Sleep"
Then user should be displayed with "USING HOME SETTINGS" 




#TemporaryHold (Time and geofence)


 #JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardEMEA
 Scenario Outline :   I want to verify time Temporary override Schedule from solution card  for systems   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples :
|Mode | 
|Heat | 


#JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsetpointchangeEMEA
 Scenario Outline :   I want to verify time Temporary override from solution card and  resume in next period for systems Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
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
Examples :
|Mode | 
|Heat | 



#JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordEMEATemporaryHold 
 Scenario Outline :   I want to verify time Temporary override from dashboard and  resume in next period for systems   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples :
|Mode | 
|Heat | 

 #JasperEMEA
 @AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordsetpointEMEA
 Scenario Outline :   I want to verify setpoint change on dashboard when   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx UNTIL XX:XX" 
Examples :
|Mode | 
|Heat | 


 #JasperEMEA
 @AdhocOverrideGeofencebaseScheduleTemporaryHoldSolutionCardEMEATemporaryHoldEMEA 
 Scenario Outline :   I want to verify setpoint change on solution card when   Heat with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Geofence base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When next schedule period activated or crossed fense 
Then user should be displayed with "USING" <NPERIOD> "SETTINGS"
Examples :
|Mode | PERIOD | NPERIOD |
|Heat | HOME |AWAY OR SLEEP |
|Heat | AWAY |HOME OR SLEEP |
|HEAT | SLEEP |HOME OR AWAY |


#JasperEMEA
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatOFFTemporaryHoldEMEA 
Scenario Outline:  To verify override retained even mode is changed for "Heat OFF" system  
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
Then user should be displayed with "HOLD XX WHILE SLEEP” override with set temperature till next schedule 
When user deletes the “Sleep period”
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



#JasperEMEA
@AdhocOverridetimebaseschedulingdeleteNextperiodEMEATemporaryHoldEMEA 
Scenario Outline:  To verify delete Next period and remove hold mode is changed for "Heat " system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user deletes the "Next period"
Then user should be displayed with "HOLD xx UNTIL" following period time "XX:XX" 
When user selects the "Remove hold" through "AdhocOverried Action sheet"
Then user should be displayed with "Following schedule" with following period 
And suer should be displayed with following period setpoint value 

Examples:
|Mode| Current PERIOD | 
|P1|
|P2|
|P3|
|P4|
|P5|
|P6|



#JasperEMEA
@AdhocOverrideCreateTimebasescheduleEMEATemporaryHoldEMEA
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify creates time base schedule even mode is changed for "Heat " system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user creates the <NEW Schedule>
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
| Current PERIOD | NEW Schedule |
|Time base schedule |Time base schedule |
|Geofence base schedule |Time base schedule |


#JasperEMEA
@AdhocOverridCreateTimebasescheduleOFFModeEMEATemporaryHoldEMEA 
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify override retained even mode is changed for "OFF" 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user change "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user change the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
| Current PERIOD | NEW Schedule |
|Time base schedule |Time base schedule|
|Geofence base schedule |Time base schedule |




#JasperEMEA
@AdhocOverrideCreateGeofencebasescheduleTemporaryHoldEMEA
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify override retained even mode is changed for "Heat , auto ,cool" system  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <current PERIOD> override
When user creates the “Geofence schedule”
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 
Examples:
| Current PERIOD | PERIOD |
|Geofence base schedule | Home | 
|Time base schedule |Home |  
|Geofence base schedule |AWAY | 
|Time base schedule |AWAY |  
|Geofence base schedule |SLEEP | 
|Time base schedule |SLEEP | 


#JasperEMEA
@AdhocOverridetimebaseschedulingdeleteCreateGeofencebasescheduleOFFEMEATemporaryHoldEMEA 
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify override retained even mode is changed for "OFF"
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" < current PERIOD> override
When user changes "OFF" mode from <Mode>
When user creates the “Geofence schedule”
When user changes “Heat” from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And   should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 
Examples:
| schedule | PERIOD |
|Geofence base schedule | Home | 
|Time base schedule |Home |  
|Geofence base schedule |AWAY | 
|Time base schedule |AWAY |  
|Geofence base schedule |SLEEP | 
|Time base schedule |SLEEP | 



 #Permanent hold (Time base Schedule)

 @AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetEMEA
 Scenario Outline :   I want to verify action sheet view  for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user navigates to "Adhoc Override action sheet"
And user should be displayed with following options:
|Action Sheet|
|Next period xx:xx|
|Permanent |
|A Specific Time|
|Remove Hold|
|Cancel |


 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityEMEA
 Scenario Outline :   I want to verify cancel functionality for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user navigates to "Adhoc Override action sheet"
When user selects "Cancel" option 
Then user should dismiss the "Adhoc Override action sheet"



 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardEMEA
 Scenario :   I want to verify permanent hold status for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user navigates to "Adhoc Override action sheet"
When user selects the "Permanently"
Then user should be displayed with "HOLD XX PERMANENTLY"



#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchangeEMEA
 Scenario :   I want to verify setpoint change solution card  for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is "PermanentHold"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "SolutionCard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx PERMANENTLY" 



#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchangeEMEA
 Scenario :   I want to verify setpoint change dashboard for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "PermanentHold"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx PERMANENTLY" on "SolucationCard"



#Requirements : Thermsotat should be set in permanent 
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHoldEMEA
Scenario:  To verify change modes for "Heat , auto ,cool and off " system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat” through CHIL
When user is in "PermanentHold"
Then user should be displayed with adhoc override "HOLD xx PERMANENTLY" 
When user change the "OFF" from “Heat”
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx PERMANENTLY"  override 
When user change the “Heat” from "OFF" 
Then user should be displayed with "HOLD xx PERMANENTLY" override 
And user should be displayed with Perviously adjusted setpoint value


#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentRemoveHoldEMEA
 Scenario :   I want to verify override Permanent schedule - Remove Hold  with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "PermanentHold"
When user selects the "PermanentHold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Remove Hold" option 
Then user should be displayed with the "Following schedule"
And user should be displayed with respective <Mode> period setpoint value 



@AdhocOverrideCreateTimebaseschedulePermanentHoldEMEA 
Scenario:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in”time base schedule”
And user set to “Permanent Hold>
When user creates the “Time base schedule” 
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 


@AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHoldEMEA
Scenario: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “Time base schedule”
And user set to “PermanentHold”
When user change "OFF" mode from <Mode>
Then user creates the “Time base schedule “
When user change the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 



#JasperNA
@AdhocOverrideCreateGeofencebaseschedulePermanentHoldEMEA
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “Time base schedule”
And user set to “PermanentHold”
When user creates the “geofence schedule”
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
| PERIOD |
| Home | 
| AWAY | 
| SLEEP | 


@AdhocOverrideCreateGeofencebasescheduleOFFPermanentHoldEMEA
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “time base schedule”
And user set to “PermanentHold”
When user changes "OFF" mode from <Mode>
Then user creates the “geofence schedule”
When user changes “Heat” from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
| PERIOD |
| Home | 
| AWAY | 
| SLEEP | 








#A Specific Time

#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpointEMEA
 Scenario :   I want to verify setpoint change in solution card for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "A specific time" status
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "SolutionCard"
Then verify setpoint in"HOLD xx UNTIL XX:XX" override status on "solutionCard"
And verify adjusted setpoint should be updated on "Dashboard"



#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpointEMEA
 Scenario  :   I want to verify setpoint change in dashboard for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "A specific time" status
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then verify setpoint in"HOLD xx UNTIL XX:XX" override status on "solutionCard"
And verify adjusted setpoint should be updated on "SolutionCard"


#JasperNA
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictimeEMEA
Scenario Outline:  To verify switching modes "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "A specific time" status
When user change the "OFF" from “Heat”
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx UNTIL XX:XX"  override 
When user changes “Heat” from "OFF"
Then user should be displayed with the "HOLD XX UNTIL {Specific time}" 
And user should be displayed with "A SPECIFIC time" setpoint value


#Requirements : Thermostat should be set to Temporary Hold 
 @AdhocOverrideTimebaseScheduleTemporaryHoldspecifictimeEMEA
 Scenario :   I want to verify temporary to specific time and resume for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "Temporary Hold" status
When user selects the "Temporary Hold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "A Specific Time" option
Then user should be displayed with the "Select Time & Days" 
And user should be displayed with 10mints time interval 
When user set to greater than 12hours from present time 
Then user should displayed with "Please select proper time" pop up 
And user should be displayed with time reverted back to 12hours gap from present time
When user set to less than 12hours 
Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When "A specific time" set time completed 
Then user should be displayed with "Following schedule" 
And user should be displayed with respective <Mode> period setpoint value



#Requirements : Thermostat should be set to Permanent Hold 
 @AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHoldEMEA
 Scenario :   I want to verify permanent hold to specific time and resume for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "Permanent Hold" status
When user selects the "Permanent Hold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "A Specific Time" option
Then user should be displayed with the "Select Time & Days" 
And user should be displayed with 10mints time interval 
When user set to greater than 12hours from present time 
Then user should displayed with "Please select proper time" pop up 
And user should be displayed with time reverted back to 12hours gap from present time
When user set to less than 12hours 
Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When "A specific time" set time completed 
Then user should be displayed with "Following schedule" 
And user should be displayed with respective <Mode> period setpoint value


#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA
 Scenario :   I want to verify specific time to permanent hold status for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "A specific time" status
When user selects the "A specific time" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Permanently" option
Then user should be displayed "HOLD XX PERMANENTLY" 


#Requirements : Thermostat should be set to A specific time
 @AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA
 Scenari :   I want to verify remove hold for systems Heat    with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in "A specific time" status
When user selects the "A specific time" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Remove hold" option
Then user should be displayed "Following Schedule"
And user should be displayed with respective period setpoint 



@AdhocOverrideCreateTimebasescheduleAspecifictimeEMEA
Scenario:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in”time base schedule”
And user set to “specifictime”
When user creates the “Time base schedule”
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 



@AdhocOverrideCreateTimebasescheduleOFFModeAspecifictimeEMEA
Scenario: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “Time base schedule”
And user set to “Aspecifictime”
When user change "OFF" mode from <Mode>
Then user creates the “Time base schedule”
When user chagne the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 



#JasperNA
@AdhocOverrideCreateGeofencebasescheduleAspecifictimeEMEA
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “Time base schedule”
And user set to “A specific time”
When user creates the  “Geofence schedule”
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
| PERIOD |
| Home | 
| AWAY | 
| SLEEP | 


@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
Then user is set to “Heat”
And user is in “time base schedule”
And user set to “A Specific time”
When user changes "OFF" mode from <Mode>
Then user creates the  “Geofence schedule”
When user changes <Mode> from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
| PERIOD |
| Home | 
| AWAY | 
| SLEEP | 













