@AdhocOverride
Feature:   
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.

#Following schedule and using settings

 #JasperNA
 @AdhocOverrideTimebaseSchedulefollowingfromsolutioncard
 Scenario Outline :   I want to verify time follwoing Schedule from solution card  for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "Following schedule" 
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


 @AdhocOverrideGeofencebaseScheduleusing
 Scenario Outline :   I want to verify geofence using for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user is set to "Geofence base schedule"
Then user navigates to "Solutioncard" screen from "Dashboard" screen
And user should be displayed with "USING" <PERIOD> "SETTINGS" 
Examples :
|Mode | PERIOD | 
|Cool | HOME | 
|Cool | AWAY | 
|COOL | SLEEP |
|Heat | HOME |
|Heat | AWAY |
|HEAT | SLEEP |
|Auto | HOME |
|Auto | AWAY |
|AUTO | SLEEP|
|Cool only| HOME | 
|Cool only| AWAY |
|Cool only| SLEEP |
|Heat only| HOME | 
|Heat only| AWAY |
|Heat only|SLEEP |

#JasperNA
@AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollwoing
Scenario Outline:  To verify following base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "Following schedule"
When user change the <UMode> from <Mode>
Then user should be displayed with "Following schedule"
And user should be displayed with respective <Period> setpoint value
When user change the <Mode> from <UMode>
Then user should be displayed with "Following schedule"
And user should be displayed with respective <Period> setpoint value

Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |


#JasperNA
@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule
Scenario Outline:  To verify following base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with "Following schedule"
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And  user should not be display with "following schedule"
When user change the <Mode> from "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with respective <Period> setpoint value

Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |
|Cool only |Cool only       |SLEEP |
|Cool only |Cool only       |WAKE |
|Cool only |Cool only       |HOME |
|Cool only |Cool only       |AWAY |
|Heat only | Heat only       |SLEEP |
| Heat only | Heat only       |WAKE |
| Heat only | Heat only       |HOME |
| Heat only | Heat only       |AWAY |


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousing
Scenario Outline:  To verify geofence switching modes is "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
And user should be displayed with "USING" <PERIOD> "SETTINGS"
When suer change the <UMode> from <Mode> 
Then user should be displayed with be displayed with "USING" <PERIOD> "SETTINGS"
And user should be displayed with respective <Period> setpoint value
When suer change the <Mode> from <UMode> 
Then user should be displayed with "USING" <PERIOD> "SETTINGS"
And user should be displayed with respective <Period> setpoint value

Examples:
|Mode|Umode|  Period |
|Auto|Heat        | Home |
|Auto|Heat        | Away |
|Auto|Heat        | Sleep |
|Auto|Cool        |Home |
|Auto|Cool        |Away |
|Auto|Cool        |Sleep |
|Heat|Cool        |Home |
|Heat|Cool        |Away |
|Heat|Cool        |Sleep |
|Heat|Auto        | Home |
|Heat|Auto        | Away |
|Heat|Auto        | Sleep |
|Cool|Heat        |Home |
|Cool|Heat        |Away |
|Cool|Heat        |Sleep |
|Cool|Auto        |Home |
|Cool|Auto        |Away |
|Cool|Auto        |Sleep |


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusing
Scenario Outline:  To verify using schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
Then user should be displayed with "USING" <PERIOD> "SETTINGS"
And user should be displayed with respective <Period> setpoint value
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "USING" <PERIOD> "SETTINGS"
When suer change the <UMode> from "OFF" 
Then user should be displayed with "USING" <PERIOD> "SETTINGS"
And user should be displayed with respective <Period> setpoint value

Examples:
|Mode|Umode|  Period |
|Auto|Heat        | Home |
|Auto|Heat        | Away |
|Auto|Heat        | Sleep |
|Auto|Cool        |Home |
|Auto|Cool        |Away |
|Auto|Cool        |Sleep |
|Auto| Auto        |Home |
|Auto| Auto        |Away |
|Auto|Auto        |Sleep |
|Heat|Cool        |Home |
|Heat|Cool        |Away |
|Heat|Cool        |Sleep |
|Heat|Auto        | Home |
|Heat|Auto        | Away |
|Heat|Auto        | Sleep |
|Heat|Heat        | Home |
|Heat|Heat        | Away |
|Heat|Heat        | Sleep |
|Cool|Heat        |Home |
|Cool|Heat        |Away |
|Cool|Heat        |Sleep |
|Cool|Auto        |Home |
|Cool|Auto        |Away |
|Cool|Auto        |Sleep |
|Cool|Cool        |Home |
|Cool| Cool        |Away |
|Cool| Cool        |Sleep |

#JasperNA
@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodusing
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user should be displayed with "USING SLEEP SETTINGS"
When user deletes the <Current Period> 
Then user should be displayed with "USING HOME SETTINGS" 

Examples:
|Mode|
|Cool| 
|HEAT|
|AUTO|
|Heat only|
|Cool only|

#TemporaryHold (Time and geofence)


 #JasperNA
 @AdhocOverrideTimebaseScheduleTemporaryHoldstatusfromsolutioncard
 Scenario Outline :   I want to verify time Temporary override Schedule from solution card  for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
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
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

 @AdhocOverrideTimebaseScheduleTemporaryHoldstatusfromDashboard
 Scenario Outline :   I want to verify time Temporary override Schedule from dashboard card for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with updated setpoint in adhoc override "HOLD xx UNTIL XX:XX" 
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

 #JasperNA
 @AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsepointchangeandNEXTperiodresume
 Scenario Outline :   I want to verify time Temporary override resume in next period for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

 #JasperNA
 @AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordsetpointchangeandNEXTperiodresume
 Scenario Outline :   I want to verify time temporary hold setpoint dashboard and next period resumefor systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx UNTIL XX:XX" ON "Solutioncard"
When next schedule period activated 
Then user should be displayed with "Following Schedule"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

 #JasperNA
 @AdhocOverrideGeofencebaseScheduleTemporaryHoldSolutionCardsetpintchangeandNEXTperiodresume
 Scenario Outline :   I want to verify geofence temporary hold setpoint solution cardand next period resume for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
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
|Cool | HOME | AWAY OR SLEEP |
|Cool | AWAY | HOME OR SLEEP |
|COOL | SLEEP | HOME OR AWAY |
|Heat | HOME |AWAY OR SLEEP |
|Heat | AWAY |HOME OR SLEEP |
|HEAT | SLEEP |HOME OR AWAY |
|Auto | HOME | AWAY OR SLEEP |
|Auto | AWAY |HOME OR SLEEP |
|AUTO | SLEEP|HOME OR AWAY |
|Cool only| HOME | AWAY OR SLEEP |
|Cool only| AWAY |HOME OR SLEEP |
|Cool only| SLEEP |HOME OR AWAY |
|Heat only| HOME | AWAY OR SLEEP |
|Heat only| AWAY |HOME OR SLEEP |
|Heat only|SLEEP |HOME OR AWAY |


 #JasperNA
 @AdhocOverrideGeofencebaseScheduleTemporaryHoldDashboardsetpointchangeandNEXTperiodresume 
 Scenario Outline :   I want to verify geofence  repoint change and resume in next period on systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Geofence base schedule"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When next schedule period activated or crossed fense 
Then user should be displayed with "USING" <NPERIOD> "SETTINGS"
Examples :
|Mode | PERIOD | NPERIOD |
|Cool | HOME | AWAY OR SLEEP |
|Cool | AWAY | HOME OR SLEEP |
|COOL | SLEEP | HOME OR AWAY |
|Heat | HOME |AWAY OR SLEEP |
|Heat | AWAY |HOME OR SLEEP |
|HEAT | SLEEP |HOME OR AWAY |
|Auto | HOME | AWAY OR SLEEP |
|Auto | AWAY |HOME OR SLEEP |
|AUTO | SLEEP|HOME OR AWAY |
|Cool only| HOME | AWAY OR SLEEP |
|Cool only| AWAY |HOME OR SLEEP |
|Cool only| SLEEP |HOME OR AWAY |
|Heat only| HOME | AWAY OR SLEEP |
|Heat only| AWAY |HOME OR SLEEP |
|Heat only|SLEEP |HOME OR AWAY |

#JasperNA
@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoTemporaryHold 
Scenario Outline:  To verify time base switching  mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user should be displayed with respective <Period> setpoint value
When user change the <UMode> from <Mode>
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user should be displayed with "TemporaryHold" setpoint value

Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |


#JasperNA
@AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFTemporaryHold 
Scenario Outline:  To verify time base schedule switching  mode is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx UNTIL XX:XX"  override 
When suer change the <UMode> from "OFF" 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user should be displayed with Perviously adjusted setpoint value

Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |
|Cool only |Cool only       |SLEEP |
|Cool only |Cool only       |WAKE |
|Cool only |Cool only       |HOME |
|Cool only |Cool only       |AWAY |
|Heat only | Heat only       |SLEEP |
| Heat only | Heat only       |WAKE |
| Heat only | Heat only       |HOME |
| Heat only | Heat only       |AWAY |


#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoTemporaryHold 
Scenario Outline:  To verify geofence switching modes is "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When suer change the <UMode> from <Mode> 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
And user should be displayed with respective <Period> setpoint value
When suer change the <Mode> from <UMode> 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
And user should be displayed with previously adjusted setpoint value

Examples:
|Mode|Umode|  Period |
|Auto|Heat        | Home |
|Auto|Heat        | Away |
|Auto|Heat        | Sleep |
|Auto|Cool        |Home |
|Auto|Cool        |Away |
|Auto|Cool        |Sleep |
|Heat|Cool        |Home |
|Heat|Cool        |Away |
|Heat|Cool        |Sleep |
|Heat|Auto        | Home |
|Heat|Auto        | Away |
|Heat|Auto        | Sleep |
|Cool|Heat        |Home |
|Cool|Heat        |Away |
|Cool|Heat        |Sleep |
|Cool|Auto        |Home |
|Cool|Auto        |Away |
|Cool|Auto        |Sleep |

#JasperNA
@AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFTemporaryHold 
Scenario Outline:  To verify geofence schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD XX WHILE" <PERIOD>  override 
When suer change the <UMode> from "OFF" 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
And user should be displayed with Perviously adjusted setpoint value

Examples:
|Mode|Umode|  Period |
|Auto|Heat        | Home |
|Auto|Heat        | Away |
|Auto|Heat        | Sleep |
|Auto|Cool        |Home |
|Auto|Cool        |Away |
|Auto|Cool        |Sleep |
|Auto| Auto        |Home |
|Auto| Auto        |Away |
|Auto|Auto        |Sleep |
|Heat|Cool        |Home |
|Heat|Cool        |Away |
|Heat|Cool        |Sleep |
|Heat|Auto        | Home |
|Heat|Auto        | Away |
|Heat|Auto        | Sleep |
|Heat|Heat        | Home |
|Heat|Heat        | Away |
|Heat|Heat        | Sleep |
|Cool|Heat        |Home |
|Cool|Heat        |Away |
|Cool|Heat        |Sleep |
|Cool|Auto        |Home |
|Cool|Auto        |Away |
|Cool|Auto        |Sleep |
|Cool|Cool        |Home |
|Cool| Cool        |Away |
|Cool| Cool        |Sleep |


#JasperNA
@AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold 
Scenario Outline:  To verify delete current period and remove hold  when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user deletes the <Current Period> 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user selects the "Remove hold" through "AdhocOverride Action sheet"
Then user should be displayed with "Following schedule" with previous period 
And user should be displayed with previous period setpoint value 

Examples:
|Mode| Current PERIOD | 
|Cool| WAKE |
|HEAT|WAKE|
|AUTO|WAKE|
|Heat only| WAKE|
|Cool only |WAKE |
|Cool| AWAY |
|HEAT|AWAY|
|AUTO|AWAY|
|Heat only| AWAY|
|Cool only |AWAY |
|Cool| SLEEP |
|HEAT|SLEEP|
|AUTO|SLEEP|
|Heat only| SLEEP|
|Cool only |SLEEP |
|Cool| HOME |
|HEAT|HOME|
|AUTO|HOME|
|Heat only| HOME|
|Cool only |HOME |

#JasperNA
@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodTemporaryHold 
Scenario Outline:  To verify geofence schedule delete current sleep period when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <PERIOD> override with set temperature till next schedule 
When user deletes the <Current Period> 
Then user should be displayed with "USING HOME SETTINGS" 

Examples:
|Mode| Current PERIOD | 
|Cool| Sleep |
|HEAT|Sleep|
|AUTO|Sleep|
|Heat only|Sleep|
|Cool only|Sleep|



#JasperNA
@AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodTemporaryHold 
Scenario Outline:  To verify geofence schedule sleep period delete and remove hold when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <Current PERIOD> override 
When user selects the "Remove hold" through "AdhocOverride Action sheet"
Then user should be displayed with "USING" <CURRENT PERIOD> "SETTINGS"

Examples:
|Mode| Current PERIOD | 
|Cool| Sleep |
|HEAT|Sleep|
|AUTO|Sleep|
|Heat only|Sleep|
|Cool only|Sleep|
|Cool| AWAY |
|HEAT|AWAY|
|AUTO|AWAY|
|Heat only|AWAY|
|Cool only|AWAY|
|Cool| HOME |
|HEAT|HOME|
|AUTO|HOME|
|Heat only|HOME|
|Cool only|HOME|



#JasperNA
@AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold 
Scenario Outline:  To verify delete Next period and remove hold  when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user deletes the "Next period"
Then user should be displayed with "HOLD xx UNTIL" following period time "XX:XX" 
When user selects the "Remove hold" through "AdhocOverride Action sheet"
Then user should be displayed with "Following schedule" with following period 
And suer should be displayed with following period setpoint value 

Examples:
|Mode| Current PERIOD | 
|Cool| WAKE | 
|HEAT|WAKE|
|AUTO|WAKE|
|Heat only|WAKE|
|Cool only|WAKE|
|Cool| AWAY |
|HEAT|AWAY|
|AUTO|AWAY|
|Heat only|AWAY|
|Cool only|AWAY|
|Cool| SLEEP |
|HEAT|SLEEP|
|AUTO|SLEEP|
|Heat only|Sleep|
|Cool only|Sleep|
|Cool| HOME |
|HEAT|HOME|
|AUTO|HOME|
|Heat only|HOME|
|Cool only|HOME|


#JasperNA
@AdhocOverrideCreateTimebasescheduleTemporaryHold 
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user creates the <NEW Schedule>
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
|Mode| Current PERIOD | NEW Schedule |
|Cool| Time base schedule | Time base schedule |
|HEAT|Time base schedule |Time base schedule|
|AUTO|Time base schedule |Time base schedule |
|Heat only|Time base schedule |Time base schedule |
|Cool only|Time base schedule |Time base schedule |
|Cool| Geofence base schedule | Time base schedule |
|HEAT|Geofence base schedule |Time base schedule |
|AUTO|Geofence base schedule |Time base schedule |
|Heat only|Geofence base schedule |Time base schedule |
|Cool only|Geofence base schedule |Time base schedule |

#JasperNA
@AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHold 
Scenario Outline: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user change "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user chagne the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
|Mode| scheduling |Current PERIOD | NEW Schedule |
|Cool| Time base schedule | Time base schedule |
|HEAT|Time base schedule |Time base schedule|
|AUTO|Time base schedule |Time base schedule |
|Heat only|Time base schedule |Time base schedule |
|Cool only|Time base schedule |Time base schedule |
|Cool| Geofence base schedule | Time base schedule |
|HEAT|Geofence base schedule |Time base schedule |
|AUTO|Geofence base schedule |Time base schedule |
|Heat only|Geofence base schedule |Time base schedule |
|Cool only|Geofence base schedule |Time base schedule |



#JasperNA
@AdhocOverrideCreateGeofencebasescheduleTemporaryHold 
Scenario Outline: To Verify override retained when creates time base schedule 
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <current PERIOD> override
When user creates the <NEW Schedule>
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 
Examples:
|Mode| Scheduling |Current PERIOD |NEW Schedule | PERIOD |
|Cool| Geofence base schedule | Geofence base schedule| Home | 
|HEAT|Geofence base schedule |Geofence base schedule| Home | 
|AUTO|Geofence base schedule |Geofence base schedule| Home |
|Heat only|Geofence base schedule |Geofence base schedule| HOME | 
|Cool only|Geofence base schedule |Geofence base schedule| HOME |
|Cool| Time base schedule | Geofence base schedule| Home | 
|HEAT|Time base schedule |Geofence base schedule| Home | 
|AUTO|Time base schedule |Geofence base schedule| Home | 
|Heat only|Time base schedule |Geofence base schedule| HOME | 
|Cool only|Time base schedule |Geofence base schedule| HOME |
|Cool| Geofence base schedule | Geofence base schedule| AWAY | 
|HEAT|Geofence base schedule |Geofence base schedule| AWAY | 
|AUTO|Geofence base schedule |Geofence base schedule| AWAY | 
|Heat only|Geofence base schedule |Geofence base schedule| Away | 
|Cool only|Geofence base schedule |Geofence base schedule| Away |
|Cool| Time base schedule | Geofence base schedule| AWAY | 
|HEAT|Time base schedule |Geofence base schedule| AWAY | 
|AUTO|Time base schedule |Geofence base schedule| AWAY | 
|Heat only|Time base schedule |Geofence base schedule| AWAY | 
|Cool only|Time base schedule |Geofence base schedule| AWAY |
|Cool| Geofence base schedule | Geofence base schedule| SLEEP | 
|HEAT|Geofence base schedule |Geofence base schedule| SLEEP | 
|AUTO|Geofence base schedule |Geofence base schedule| SLEEP | 
|Heat only|Geofence base schedule |Geofence base schedule| SLEEP | 
|Cool only|Geofence base schedule |Geofence base schedule| SLEEP |
|Cool| Time base schedule | Geofence base schedule| SLEEP | 
|HEAT|Time base schedule |Geofence base schedule| SLEEP | 
|AUTO|Time base schedule |Geofence base schedule| SLEEP | 
|Heat only|Time base schedule |Geofence base schedule| SLEEP | 
|Cool only|Time base schedule |Geofence base schedule| SLEEP | 

#JasperNA
@AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHold 
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Scheduling>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD XX WHILE" <current PERIOD> override
When user changes "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user changes <Mode> from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 
Examples:
|Mode| Scheduling | Current PERIOD | NEW Schedule | PERIOD |
|Cool| Geofence base schedule | Geofence base schedule| Home | 
|HEAT|Geofence base schedule |Geofence base schedule| Home | 
|AUTO|Geofence base schedule |Geofence base schedule| Home |
|Heat only|Geofence base schedule |Geofence base schedule| HOME | 
|Cool only|Geofence base schedule |Geofence base schedule| HOME |
|Cool| Time base schedule | Geofence base schedule| Home | 
|HEAT|Time base schedule |Geofence base schedule| Home | 
|AUTO|Time base schedule |Geofence base schedule| Home | 
|Heat only|Time base schedule |Geofence base schedule| HOME | 
|Cool only|Time base schedule |Geofence base schedule| HOME |
|Cool| Geofence base schedule | Geofence base schedule| AWAY | 
|HEAT|Geofence base schedule |Geofence base schedule| AWAY | 
|AUTO|Geofence base schedule |Geofence base schedule| AWAY | 
|Heat only|Geofence base schedule |Geofence base schedule| Away | 
|Cool only|Geofence base schedule |Geofence base schedule| Away |
|Cool| Time base schedule | Geofence base schedule| AWAY | 
|HEAT|Time base schedule |Geofence base schedule| AWAY | 
|AUTO|Time base schedule |Geofence base schedule| AWAY | 
|Heat only|Time base schedule |Geofence base schedule| AWAY | 
|Cool only|Time base schedule |Geofence base schedule| AWAY |
|Cool| Geofence base schedule | Geofence base schedule| SLEEP | 
|HEAT|Geofence base schedule |Geofence base schedule| SLEEP | 
|AUTO|Geofence base schedule |Geofence base schedule| SLEEP | 
|Heat only|Geofence base schedule |Geofence base schedule| SLEEP | 
|Cool only|Geofence base schedule |Geofence base schedule| SLEEP |
|Cool| Time base schedule | Geofence base schedule| SLEEP | 
|HEAT|Time base schedule |Geofence base schedule| SLEEP | 
|AUTO|Time base schedule |Geofence base schedule| SLEEP | 
|Heat only|Time base schedule |Geofence base schedule| SLEEP | 
|Cool only|Time base schedule |Geofence base schedule| SLEEP | 


#JasperNA
@AdhocOverridetimebaseschedulingdeleteALLPERIODSTemporaryHold 
Scenario Outline:  To verify delete all periods when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in <Current PERIOD>
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
When user deletes the "All Periods"
Then user should be displayed with "No schedule" on "SolutionCard"
And user should be displayed with <Mode>
And user should be displayed with Last period setpoint value 

Examples:
|Mode| Current PERIOD | 
|Cool| Sleep |
|HEAT|Sleep|
|AUTO|Sleep|
|Heat only|Sleep|
|Cool only|Sleep|
|Cool| AWAY |
|HEAT|AWAY|
|AUTO|AWAY|
|Heat only|AWAY|
|Cool only|AWAY|
|Cool| HOME |
|HEAT|HOME|
|AUTO|HOME|
|Heat only|HOME|
|Cool only|HOME|


 #Permanent hold (Time base Schedule)



 #JasperNA

 @AdhocOverrideTimebaseScheduleAdhocOverrideActionSheet
 Scenario Outline :   I want to verify action sheet view  for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
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
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionality
 Scenario Outline :   I want to verify cancel functionality for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user navigates to "Adhoc Override action sheet"
When user selects "Cancel" option 
Then user should dismiss the "Adhoc Override action sheet"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 



 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCard
 Scenario Outline :   I want to verify permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
And user navigates to "Solutioncard" screen from "Dashboard" screen
When user "Increase" or "Decrease" the temperature through TAPing on stepper 
Then user should be displayed with "HOLD xx UNTIL XX:XX" override with set temperature till next schedule 
And user navigates to "Adhoc Override action sheet"
When user selects the "Permanently"
Then user should be displayed with "HOLD XX PERMANENTLY"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchange
 Scenario Outline :   I want to verify setpoint change solution card  for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is "PermanentHold"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "SolutionCard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx PERMANENTLY" 
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchange
 Scenario Outline :   I want to verify setpoint change dashboard for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "PermanentHold"
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then user should be displayed with updated setpoint in adhoc override "HOLD xx PERMANENTLY" on "SolucationCard"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoTemporaryHold 
Scenario Outline:  To verify change modes for "Heat , auto ,cool," system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user is in "PermanentHold"
Then user should be displayed with dhoc override "HOLD xx PERMANENTLY" 
When user change the <UMode> from <Mode>
Then user should be displayed with dhoc override "HOLD xx PERMANENTLY" 
And user should be displayed with respective <Period> setpoint value 
When suer change the <Mode> from <UMode>
Then user should be displayed with "HOLD xx PERMANENTLY" override 
And user should be displayed with Perviously adjusted setpoint value


Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |


#Requirements : Thermsotat should be set in permanent 
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHold 
Scenario Outline:  To verify change modes for "Heat , auto ,cool and off " system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user is in "PermanentHold"
Then user should be displayed with adhoc override "HOLD xx PERMANENTLY" 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx PERMANENTLY"  override 
When user change the <UMode> from "OFF" 
Then user should be displayed with "HOLD xx PERMANENTLY" override 
And user should be displayed with Perviously adjusted setpoint value

Examples:
|Mode|Umode      |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |

#Requirement : Thermostat should be set in permanent 
 @AdhocOverrideTimebaseSchedulePermanentRemoveHold
 Scenario Outline :   I want to verify override Permanent schedule - Remove Hold  with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "PermanentHold"
When user selects the "PermanentHold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Remove Hold" option 
Then user should be displayed with the "Following schedule"
And user should be displayed with respective <Mode> period setpoint value 
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only|

#Requirements : Thermostat should be set in permanent 
@AdhocOverridetimebaseschedulingdeleteALLPERIODSPermanentHold 
Scenario Outline: To Verify override retained when delete all periods
Scenario Outline:  To verify delete all periods and no schedule status for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is "PermanentHold"
When user deletes the "All Periods"
Then user should be displayed with "No schedule" on "SolutionCard"
And user should be displayed with <Mode>
And user should be displayed with Last period setpoint value 

Examples:
|Mode| Current PERIOD | 
|Cool| Sleep |
|HEAT|Sleep|
|AUTO|Sleep|
|Heat only|Sleep|
|Cool only|Sleep|
|Cool| AWAY |
|HEAT|AWAY|
|AUTO|AWAY|
|Heat only|AWAY|
|Cool only|AWAY|
|Cool| HOME |
|HEAT|HOME|
|AUTO|HOME|
|Heat only|HOME|
|Cool only|HOME|


@AdhocOverrideCreateTimebaseschedulePermanentHold 
Scenario Outline:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in”time base schedule”
And user set to “Permanent Hold>
When user creates the <NEW Schedule>
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
|Mode| NEW Schedule |
|Cool| Time base schedule | 
|HEAT|Time base schedule |
|AUTO|Time base schedule |
|Heat only|Time base schedule |
|Cool only|Time base schedule |

@AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHold 
Scenario Outline: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “Time base schedule”
And user set to “PermanentHold”
When user change "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user chagne the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
|Mode| NEW Schedule |
|Cool| Time base schedule | 
|HEAT|Time base schedule |
|AUTO|Time base schedule |
|Heat only|Time base schedule |
|Cool only|Time base schedule |

#JasperNA
@AdhocOverrideCreateGeofencebaseschedulePermanentHold 
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “Time base schedule”
And user set to “PermanentHold”
When user creates the <NEW Schedule>
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
|Mode| NEW Schedule | PERIOD |
|Cool|   Geofence base schedule| Home | 
|HEAT| Geofence base schedule| Home | 
|AUTO| Geofence base schedule| Home | 
|Heat only| Geofence base schedule| HOME | 
|Cool only| Geofence base schedule| HOME |
|Cool|   Geofence base schedule| AWAY | 
|HEAT| Geofence base schedule| AWAY | 
|AUTO| Geofence base schedule| AWAY | 
|Heat only| Geofence base schedule| AWAY | 
|Cool only| Geofence base schedule| AWAY |
|Cool|   Geofence base schedule| SLEEP | 
|HEAT| Geofence base schedule| SLEEP | 
|AUTO| Geofence base schedule| SLEEP | 
|Heat only| Geofence base schedule| SLEEP | 
|Cool only| Geofence base schedule| SLEEP | 

@AdhocOverrideCreateGeofencebasescheduleOFFPermanentHold
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “time base schedule”
And user set to “PermanentHold”
When user changes "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user changes <Mode> from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
|Mode| NEW Schedule | PERIOD |
|Cool|   Geofence base schedule| Home | 
|HEAT| Geofence base schedule| Home | 
|AUTO| Geofence base schedule| Home | 
|Heat only| Geofence base schedule| HOME | 
|Cool only| Geofence base schedule| HOME |
|Cool|   Geofence base schedule| AWAY | 
|HEAT| Geofence base schedule| AWAY | 
|AUTO| Geofence base schedule| AWAY | 
|Heat only| Geofence base schedule| AWAY | 
|Cool only| Geofence base schedule| AWAY |
|Cool|   Geofence base schedule| SLEEP | 
|HEAT| Geofence base schedule| SLEEP | 
|AUTO| Geofence base schedule| SLEEP | 
|Heat only| Geofence base schedule| SLEEP | 
|Cool only| Geofence base schedule| SLEEP | 


#A Specific Time

#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint
 Scenario Outline :   I want to verify setpoint change in solution card for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "SolutionCard"
Then verify setpoint in"HOLD xx UNTIL XX:XX" override status on "solutionCard"
And verify adjusted setpoint should be updated on "Dashboard"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint
 Scenario Outline :   I want to verify setpoint change in dashboard for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user "Increase" or "Decrease" the temperature through TAPing on stepper on "Dashboard"
Then verify setpoint in"HOLD xx UNTIL XX:XX" override status on "solutionCard"
And verify adjusted setpoint should be updated on "SolutionCard"
Examples :
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatauto
 Scenario Outline :   I want to verify switching modes "Heat , auto ,cool and off" with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user change the <UMode> from <Mode>
Then user should be displayed with "HOLD xx UNTIL XX:XX"  status 
And user should be displayed with respective <Mode> <period> setpoint value
When user changes <Mode> from <UMode>
Then user should be displayed with the "HOLD XX UNTIL {Specific time}" 
And user should be displayed with "A SPECIFIC time" setpoint value

Examples :
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |


#JasperNA
@AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictime
Scenario Outline:  To verify switching modes "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display with "HOLD xx UNTIL XX:XX"  override 
When user changes <UMode> from "OFF"
Then user should be displayed with the "HOLD XX UNTIL {Specific time}" 
And user should be displayed with "A SPECIFIC time" setpoint value

Examples:
|Mode|UMode	  |   PERIOD |
|Auto|Heat        | WAKE |
|Auto|Cool        |WAKE |
|Auto|Auto        | WAKE |
|Heat|Cool        |WAKE |
|Heat|Auto        | WAKE |
|Heat|HEAT        | WAKE |
|Cool|Heat        |WAKE |
|Cool|Auto        |WAKE |
|Cool|Cool        |WAKE |
|Auto|Heat        | AWAY |
|Auto|Cool        |AWAY |
|Auto|Auto        | AWAY |
|Heat|Cool        |AWAY |
|Heat|Auto        | AWAY |
|Heat|HEAT        | AWAY |
|Cool|Heat        |AWAY |
|Cool|Auto        |AWAY |
|Cool|Cool        |AWAY |
|Auto|Heat        | HOME |
|Auto|Cool        |HOME |
|Auto|Auto        | HOME |
|Heat|Cool        |HOME |
|Heat|Auto        | HOME |
|Heat|HEAT        | HOME |
|Cool|Heat        |HOME |
|Cool|Auto        |HOME |
|Cool|Cool        |HOME |
|Auto|Heat        | SLEEP |
|Auto|Cool        |SLEEP |
|Auto|Auto        | SLEEP |
|Heat|Cool        |SLEEP |
|Heat|Auto        | SLEEP |
|Heat|HEAT        | SLEEP |
|Cool|Heat        |SLEEP |
|Cool|Auto        |SLEEP |
|Cool|Cool        |SLEEP |
|Cool only |Cool only       |SLEEP |
|Cool only |Cool only       |WAKE |
|Cool only |Cool only       |HOME |
|Cool only |Cool only       |AWAY |
|Heat only | Heat only       |SLEEP |
| Heat only | Heat only       |WAKE |
| Heat only | Heat only       |HOME |
| Heat only | Heat only       |AWAY |


#Requirements : Thermostat should be set to Temporary Hold 
 @AdhocOverrideTimebaseScheduleTemporaryHoldspecifictime
 Scenario Outline :   I want to verify temporary to specific time and resume for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "Temporary Hold" status
When user selects the "Temporary Hold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "A Specific Time" option
Then user should be displayed with the "Select Time & Days" 
And user should be displayed with 15mints time interval 
When user set to greater than 12hours from present time 
Then user should displayed with "Please select proper time" pop up 
And user should be displayed with time reverted back to 12hours gap from present time
When user set to less than 12hours 
Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When "A specific time" set time completed 
Then user should be displayed with "Following schedule" 
And user should be displayed with respective <Mode> period setpoint value
Examples:
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 


#Requirements : Thermostat should be set to Permanent Hold 
 @AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHold
 Scenario Outline :   I want to verify permanent hold to specific time and resume for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "Permanent Hold" status
When user selects the "Permanent Hold" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "A Specific Time" option
Then user should be displayed with the "Select Time & Days" 
And user should be displayed with 15mints time interval 
When user set to greater than 12hours from present time 
Then user should displayed with "Please select proper time" pop up 
And user should be displayed with time reverted back to 12hours gap from present time
When user set to less than 12hours 
Then user should be displayed with "HOLD XX UNTIL XX:XX" adhoc override on "SolutionCard"
When "A specific time" set time completed 
Then user should be displayed with "Following schedule" 
And user should be displayed with respective <Mode> period setpoint value
Examples:
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseSchedulespecifictimetoPermanentHold
 Scenario Outline :   I want to verify specific time to permanent hold status for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user selects the "A specific time" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Permanently" option
Then user should be displayed "HOLD XX PERMANENTLY" 

Examples:
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseSchedulespecifictimeRemoveHold
 Scenario Outline :   I want to verify remove hold for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user selects the "A specific time" status 
Then user should be displayed with "Adhoc override action sheet"
When user selects the "Remove hold" option
Then user should be displayed "Following Schedule"
And user should be displayed with respective period setpoint 

Examples:
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 

#Requirements : Thermostat should be set to A specific time 
 @AdhocOverrideTimebaseSchedulespecifictimedeleteallperiods
 Scenario Outline :   I want to verify delete all period and no schedule status for systems Heat cool,Heat and Cool with temperature scale celcius/fahrenheit and with time format 12/24hr 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in "A specific time" status
When user deletes the "All Period"
Then user should be displayed with "No Schedule"
And user should be displayed with last period setpoint value 

Examples:
|Mode | 
|Cool | 
|Heat | 
|Auto | 
|Cool only| 
|Heat only| 



@AdhocOverrideCreateTimebasescheduleAspecifictime 
Scenario Outline:  To verify create time base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in”time base schedule”
And user set to “specifictime”
When user creates the <NEW Schedule>
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

Examples:
|Mode| NEW Schedule |
|Cool| Time base schedule | 
|HEAT|Time base schedule |
|AUTO|Time base schedule |
|Heat only|Time base schedule |
|Cool only|Time base schedule |

@AdhocOverrideCreateTimebasescheduleOFFModeAspecifictime
Scenario Outline: To Verify create time base schedule in off mode  
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “Time base schedule”
And user set to “Aspecifictime”
When user change "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user chagne the <Mode> From "OFF"
Then user should be displayed with "Following schedule"
And user should be displayed with <Mode>
And user should be displayed with updated "Following schedule" setpoint value 

|Mode| NEW Schedule | PERIOD |
|Cool|   Geofence base schedule| Home | 
|HEAT| Geofence base schedule| Home | 
|AUTO| Geofence base schedule| Home | 
|Heat only| Geofence base schedule| HOME | 
|Cool only| Geofence base schedule| HOME |
|Cool|   Geofence base schedule| AWAY | 
|HEAT| Geofence base schedule| AWAY | 
|AUTO| Geofence base schedule| AWAY | 
|Heat only| Geofence base schedule| AWAY | 
|Cool only| Geofence base schedule| AWAY |
|Cool|   Geofence base schedule| SLEEP | 
|HEAT| Geofence base schedule| SLEEP | 
|AUTO| Geofence base schedule| SLEEP | 
|Heat only| Geofence base schedule| SLEEP | 
|Cool only| Geofence base schedule| SLEEP | 

#JasperNA
@AdhocOverrideCreateGeofencebasescheduleAspecifictime
Scenario Outline:  To verify creates geofence base schedule when mode is changed for "Heat , auto ,cool" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “Time base schedule”
And user set to “A specific time”
When user creates the <NEW Schedule>
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
|Mode| NEW Schedule | PERIOD |
|Cool|   Geofence base schedule| Home | 
|HEAT| Geofence base schedule| Home | 
|AUTO| Geofence base schedule| Home | 
|Heat only| Geofence base schedule| HOME | 
|Cool only| Geofence base schedule| HOME |
|Cool|   Geofence base schedule| AWAY | 
|HEAT| Geofence base schedule| AWAY | 
|AUTO| Geofence base schedule| AWAY | 
|Heat only| Geofence base schedule| AWAY | 
|Cool only| Geofence base schedule| AWAY |
|Cool|   Geofence base schedule| SLEEP | 
|HEAT| Geofence base schedule| SLEEP | 
|AUTO| Geofence base schedule| SLEEP | 
|Heat only| Geofence base schedule| SLEEP | 
|Cool only| Geofence base schedule| SLEEP | 

@AdhocOverrideCreateGeofencebasescheduleOFFAspecifictime
Scenario Outline: To Verify create geofence schedule in off mode
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is in “time base schedule”
And user set to “A Specific time”
When user changes "OFF" mode from <Mode>
Then user creates the <NEW Schedule>
When user changes <Mode> from "OFF"
Then user should be displayed with "USING" <Period> "Settings" 
And user should be displayed with <Mode>
And user should be displayed with updated <Period> setpoint value 

Examples:
|Mode| NEW Schedule | PERIOD |
|Cool|   Geofence base schedule| Home | 
|HEAT| Geofence base schedule| Home | 
|AUTO| Geofence base schedule| Home | 
|Heat only| Geofence base schedule| HOME | 
|Cool only| Geofence base schedule| HOME |
|Cool|   Geofence base schedule| AWAY | 
|HEAT| Geofence base schedule| AWAY | 
|AUTO| Geofence base schedule| AWAY | 
|Heat only| Geofence base schedule| AWAY | 
|Cool only| Geofence base schedule| AWAY |
|Cool|   Geofence base schedule| SLEEP | 
|HEAT| Geofence base schedule| SLEEP | 
|AUTO| Geofence base schedule| SLEEP | 
|Heat only| Geofence base schedule| SLEEP | 
|Cool only| Geofence base schedule| SLEEP | 
