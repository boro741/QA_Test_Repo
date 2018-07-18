@DashbboardandSolutionCard
Feature: As an user I want to verify the Dashboard and primary card for JapserNA , JasperEMEA, HB and Spruce 

#Dashboard view 

#HB, Spruce and JasperNA
@ViewDashboard @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the Dashboard view with respective system modes 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user should be displayed with the "Thermostat Dashboard" screen
Then user should be displayed with "Thermostat name" with "XX INSIDE" temperature 
Then the following "Thermostat" options should be enabled:
 |Options|
 |Up Stepper|
 |Down Stepper|
And user "should be displayed" with the "respective setpoint value" option
####And user should be displayed with respective <Mode> Color 
Examples:
|Mode|
|Cool| 
|Heat|
|Auto|

#incaserequired 
#|Cool only|
#|Heat Only|

#JasperEMEA
@ViewDashboardEMEA @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the Dashboard view with system modes 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user should be displayed with the "Thermostat Dashboard" screen
Then user should be displayed with "Thermostat name" with "XX INSIDE" temperature 
Then the following "Thermostat" options should be enabled:
 |Options|
 |Up Stepper|
 |Down Stepper|
And user "should be displayed" with the "respective setpoint value" option
####And user should be displayed with respective <Mode> Color 
Examples:
|Mode|
|Heat |

@ViewDashboardOFF @DashbboardandSolutionCard_P1
Scenario Outline: As a user I want to verify the Dashboard view with "OFF" mode 
Given user launches and logs in to the Lyric application
#Then user has <Mode> system mode
When user should be displayed with the "thermostat Dashboard" screen 
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should not be displayed" with the "respective setpoint value" option
And user should see the "Inside temperature" status as "OFF" on the "thermostat dashboard" screen  
Examples:
|Mode |
|OFF |

#SolutionCard view 

#JasperNA
@ViewSolutionCard
Scenario Outline: Aa an user I want to verify the SolutionCard view with respective system modes 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "SolutionCard" screen
Then user should displayed with "Current" indoor tempr value 
And user should be displayed with "UP stepper" and "Down stepper"
And user should be displayed with respective setpoint value 
And user Should be displayed with enabled "FAN mode " "System mode" and "Schedule" icons
Examples:
|Mode |
|Cool| 
|Heat |
|Auto |
|Cool only|
|Heat Only|

#HB, Spruce
@ViewSolutionCard
Scenario Outline: Aa an user I want to verify the SolutionCard view with respective system modes 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "SolutionCard" screen
Then user should displayed with "Current" indoor tempr value 
And user should be displayed with "HUMIDITY XX" tempr value 
And user should be displayed with "UP stepper" and "Down stepper"
And user should be displayed with respective setpoint value 
And user Should be displayed with enabled "FAN mode " "System mode" and "Schedule" icons
Examples:
|Mode |
|Cool| 
|Heat |
|Auto |
|Cool only|
|Heat Only|

#JasperEMEA
@ViewSolutionCardEMEA
Scenario Outline: Aa an user I want to verify the SolutionCard view with system modes 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "SolutionCard" screen
Then user should displayed with "greyed out" indoor tempr value 
And user should be displayed with "Thermostat is OFF" status 
And user should be displayed with disabled "UP stepper" and "Down stepper"
And user should be displayed with setpoint value "--" 
And user Should be displayed with enabled "System mode" and "Schedule" icons 
Examples:
|Mode |
|Heat |

#HB, Spruce, JasperNA, JasperEMEA
@ViewSolutionCardOFF
Scenario Outline: Aa an user I want to verify the SolutionCard view with "OFF" mode 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "SolutionCard" screen 
Then user should displayed with "greyed out" indoor tempr value 
And user should be displayed with "Thermostat is OFF" status 
And user should be displayed with disabled "UP stepper" and "Down stepper"
And user should be displayed with setpoint value "--" 
And user Should be displayed with enabled "System mode" and "Schedule" icons 
Examples:
|Mode |
|OFF |

#Offline

#HB, Spruce, JasperNA, JasperEMEA
@Offlineverficationdashbaordsolutioncard
Scenario Outline: As an user I want to verify the SolutionCard and dashboard with offline 
Given user launches and "Offline" 
Then user launches and logs in to the lyric application 
When user navigates to "Dashboard" 
Then user should be displayed with "OFFLINE" status 
And user should be displayed with "THERMSTAT IS OFFLINE" below the Tstat name
When user navigates to "SolutionCard"
Then user should be displayed with "Greyed out" indoor tempr
And user should be displayed with "THERMOSTAT IS OFFLINE" Status
And user should be displayed with disabled "System mode", "Schedule" icon 
#EMEA
And user should be displayed with disabled "Fan mode"
And user should not receivce any "Push notification" of stat 



# systemmode

#System mode cancel functionality 

#Requirements : Auto mode should be disabled 
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithCoolandHeatMode
Scenario Outline: As an user I want to verify the Systemode info option when both cool and heat configured 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
When user selects "Info" from "Change mode" screen
Then user should be displayed with the "Mode Info" screen
And user should be displayed with the following "Mode Info" options:
|Options|
|HEAT - HEAT TO REACH TARGET TEMPERATURE|
|COOL - COOL TO REACH TARGET TEMPERATURE|
|OFF - TURN SYSTEM OFF|
When user selects "BACK" from "mode info" screen
Then user should be displayed with the "Change mode" screen
Examples:
|Mode | 
|Cool |
|Heat |
|OFF |

#Requirements : Auto mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled
Scenario Outline: As an user I want to verify the Systemode info option when both cool and heat configured with auto mode enabled
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
When user selects "Info" from "Change mode" screen
Then user should be displayed with the "Mode Info" screen
And user should be displayed with the following "Mode Info" options:
|Options|
|AUTO - COOL OR HEAT AS NEEDED TO REACH TARGET TEMPERATURE|
|HEAT - HEAT TO REACH TARGET TEMPERATURE|
|COOL - COOL TO REACH TARGET TEMPERATURE|
|OFF  - TURN SYSTEM OFF|
When user selects "BACK" from "Mode Info" screen
Then user should be displayed with the "Change mode" screen 
Examples:
|Mode | 
|Cool |
|Heat |
|Auto | 
|OFF |

#Requirements : Cool only mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithCoolOnly
Scenario Outline: As an user I want to verify the Systemmode info option when cool only configured 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
When user selects "Info" from "Change mode" screen
Then user should be displayed with the "Mode Info" screen
And user should be displayed with the following "Mode Info" options:
|Options|
|COOL - COOL TO REACH TARGET TEMPERATURE|
|OFF  - TURN SYSTEM OFF|
When user selects "BACK" from "Mode Info" screen
Then user should be displayed with the "Change mode" screen 
Examples:
|Mode | 
|Cool only| 
|OFF|


#Requirements : Heat only mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithHeatOnly
Scenario Outline: As an user I want to verify the Systemmode info option when heat only configured 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
When user selects "Info" from "Change mode" screen
Then user should be displayed with the "Mode Info" screen
And user should be displayed with the following "Mode Info" options:
|Options|
|HEAT - HEAT TO REACH TARGET TEMPERATURE|
|OFF  - TURN SYSTEM OFF|
When user selects "BACK" from "Mode Info" screen
Then user should be displayed with the "Change mode" screen 
Examples:
|Mode | 
|Heat only |
|OFF|

#JasperEMEA
@SystemModeInfoscreenwithHeatOnlyEMEA
Scenario Outline: As an user I want to verify the Systemmode info option when heat  configured 
Given user launches and logs in to the Lyric application
Then user has <Mode> system mode
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
When user selects "Info" from "Change mode" screen
Then user should be displayed with the "Mode Info" screen
And user should be displayed with the following "Mode Info" options:
|Options|
|HEAT - HEAT TO REACH TARGET TEMPERATURE|
|OFF  - TURN SYSTEM OFF|
When user selects "BACK" from "Mode Info" screen
Then user should be displayed with the "Change mode" screen 
Examples:
|Mode | 
|Heat |
|OFF|

#System mode cancel functionality 

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithbothcoolandheatCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch between Cool , heat, off 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "system mode" screen 
And user should be displayed with "Heat" "Cool" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <SystemMode>
When user navigates to "Dashboard" screen
Then user should be displayed with <Mode>
Examples:
|Mode| systemmode | 
|Cool| Heat | 
|Cool | Cool| 
|Cool | OFF| 
|Heat| Heat | 
|Heat | Cool| 
|Heat | OFF| 
|OFF| Heat | 
|OFF | Cool| 
|OFF | OFF| 

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithbothcoolandheatandautoenabledCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch between Cool , heat, off , auto
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "system mode" screen 
And user should be displayed with "Heat" "Cool" "OFF" and "Auto" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <SystemMode>
When user navigates to "Dashboard" screen
Then user should be displayed with <Mode>
Examples:
|Mode| systemmode | 
|Cool| Heat | 
|Cool| Auto | 
|Cool | Cool| 
|Cool | OFF| 
|Heat| Heat | 
|Heat| Auto | 
|Heat | Cool| 
|Heat | OFF| 
|OFF| Heat | 
|OFF | Cool| 
|OFF | OFF| 
|OFF| auto | 
|Auto | Cool| 
|Auto | OFF| 
|Auto | auto| 
|Auto | Heat| 

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch Heat only 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "system mode" screen 
And user should be displayed with "Heat"  and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <SystemMode>
When user navigates to "Dashboard" screen
Then user should be displayed with <Mode>
Examples:
|Mode| systemmode | 
|Heat| Heat | 
|Heat | OFF| 
|OFF| Heat | 
|OFF | OFF| 

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithcoolonlyCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch between cool only
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "system mode" screen 
And user should be displayed with "Cool" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <SystemMode>
When user navigates to "Dashboard" screen
Then user should be displayed with <Mode>
Examples:
|Mode| systemmode | 
|Cool | Cool| 
|Cool | OFF| 
|OFF | Cool| 
|OFF | OFF| 

#JasperEMEA
@SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionalityEMEA
Scenario Outline: As an user I want to verify the system mode when cancel option while switch Heat only 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "system mode" screen 
And user should be displayed with "Heat"  and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <SystemMode>
When user navigates to "Dashboard" screen
Then user should be displayed with <Mode>
Examples:
|Mode| systemmode | 
|Heat| Heat | 
|Heat | OFF| 
|OFF| Heat | 
|OFF | OFF| 

#SYSTEM MODE SAVE

#HB, Spruce, JasperNA
@SystemModeswitchSAVEfunctionbothcoolandheat
Scenario Outline: As an user I want to verify the system mode save option while switch between cool, heat and off
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "System Mode" screen 
And user should be displayed with "Cool" "Heat" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <SystemMode> "icon"
And user should be displayed with respective setpoint 
When user navigates to "Dashboard" screen
Then user should be displayed with <SystemMode>
And user should be displayed with respective setpoint 
Examples:
|Mode | SystemMode| 
|Cool | Cool |
|Cool |Heat |
|Cool | OFF |
|Heat | Cool |
|Heat |Heat |
|Heat | OFF |
|OFF | Cool |
|OFF |Heat |
|OFF | OFF |

#HB, Spruce, JasperNA
@SystemModeswitchSAVEfunctionbothcoolandheatandauto
Scenario Outline: As an user I want to verify the system mode save option while switch between cool, heat, off, auto
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "System Mode" screen 
And user should be displayed with "Auto" Cool" "Heat" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selects the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <SystemMode> "icon"
And user should be displayed with respective setpoint 
When user navigates to "Dashboard" screen
Then user should be displayed with <SystemMode>
And user should be displayed with respective setpoint 
Examples:
|Mode | SystemMode| 
|Cool | Cool |
|Cool |Heat |
|Cool | OFF |
|Cool | auto |
|Heat | Cool |
|Heat |Heat |
|Heat | OFF |
|Heat | auto |
|OFF | Cool |
|OFF |Heat |
|OFF | OFF |
|OFF | auto |
|auto | Cool |
|auto |Heat |
|auto | OFF |
|auto | auto |

#HB, Spruce, JasperNA
@SystemModeswitchSAVEfunctioncoolonly
Scenario Outline: As an user I want to verify the system mode save option while switch between cool, off
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "System Mode" screen 
And user should be displayed with "Cool" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user selecte the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <SystemMode> "icon"
And user should be displayed with respective setpoint 
When user navigates to "Dashboard" screen
Then user should be displayed with <SystemMode>
And user should be displayed with respective setpoint 
Examples:
|Mode | SystemMode| 
|Cool | Cool |
|Cool | OFF |
|OFF | Cool |
|OFF | OFF |

#HB, Spruce, JasperNA
@SystemModeswitchSAVEfunctionHeatonly
Scenario Outline: As an user I want to verify the system mode save option while switch between heat, off
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "System Mode" screen 
And user should be displayed with "Heat" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user select the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <SystemMode> "icon"
And user should be displayed with respective setpoint 
When user navigates to "Dashboard" screen
Then user should be displayed with <SystemMode>
And user should be displayed with respective setpoint 
Examples:
|Mode | SystemMode| 
|Heat | Heat |
|Heat | OFF |
|OFF | Heat |
|OFF | OFF |


#JasperEMEA
@SystemModeswitchSAVEfunctionEMEA
Scenario Outline: As an user I want to verify the system mode save option while switch between heat, off
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "System Mode" screen 
And user should be displayed with "Heat" and "OFF" options
And user should be displayed with "Blue Tick" mark on selected option
When user selects the <SystemMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <SystemMode> description 
When user select the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <SystemMode> "icon"
And user should be displayed with respective setpoint 
When user navigates to "Dashboard" screen
Then user should be displayed with <SystemMode>
And user should be displayed with respective setpoint 
Examples:
|Mode | SystemMode| 
|Heat | Heat |
|Heat | OFF |
|OFF | Heat |
|OFF | OFF |

#Fan mode 

#HB, Spruce, JasperNA
@FanOptionInfoOption
Scenario Outline: As an user I want to verify the Fan mode info option 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Fan Mode" screen 
When user selects the "Info" button on top right corner 
Then user should navigates to "Fan Info" screen 
And user should be displayed with following description :
|AUTO -FAN RUNS ONLY WHEN THER HEATING OR COOLING SYSTEM IS ON |
|CIRCULATE = FAN RUNS RANDOMLY, ABOUT 35% OF THE TIME|
|ON - FAN IS ALWAY ON |
When user selects "BACK" button 
Then user should be navigates to "Fan Mode" screen 
Examples:
|Mode | 
|Cool |
|Heat |
|Heat only |
|Cool only| 
|Auto | 
|OFF |


#HB, Spruce, JasperNA
@FanModeSwitchcancelfunction
Scenario Outline: As an user I want to verify the Fan mode cancel option while switch between Auto, circulate and ON
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Fan Mode" screen 
When user selects the <FanMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <FanMode> description 
When user selects 'X' button 
Then user should be navigates to "SolutionCard" with out update of <FanMode>
And user should be display with <Mode>
Examples:
|Mode |  FanMode | 
|Cool | Auto |
|Cool | Circulate |
|Cool | ON | 
|Heat | Auto |
|Heat | Circulate |
|Heat | ON | 
|Heat only | Auto |
|Heat only | Circulate |
|Heat only | ON | 
|Cool only | Auto |
|Cool only | Circulate |
|Cool only | ON | 
|Auto | Auto |
|Auto | Circulate |
|Auto | ON | 
|OFF | Auto |
|OFF | Circulate |
|OFF | ON | 

#HB, Spruce, JasperNA
@FanModeSwitchSAVEfunction
Scenario Outline: As an user I want to verify the Fan mode save option while switch between Auto, circulate and ON
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Fan Mode" screen 
When user selects the <FanMode> 
Then user should be displayed with "Blue Tick" mark on selected option 
And user should be displayed with respective <FanMode> description 
When user selecte the "SAVE" button 
Then user should be navigates to "SolutionCard" with updated of <FanMode> "icon"
Examples:
|Mode |  FanMode | 
|Cool | Auto |
|Cool | Circulate |
|Cool | ON | 
|Heat | Auto |
|Heat | Circulate |
|Heat | ON | 
|Heat only | Auto |
|Heat only | Circulate |
|Heat only | ON | 
|Cool only | Auto |
|Cool only | Circulate |
|Cool only | ON | 
|Auto | Auto |
|Auto | Circulate |
|Auto | ON | 
|OFF | Auto |
|OFF | Circulate |
|OFF | ON | 

#JasperEMEA
@FaModeOptionONEMEA
Scenario Outline: As an user I want to verify the Fan mode option for JasperEMEA
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates "SolutionCard"
And user should not be displayed with "FAN" icon 
Examples: 
|Mode| 
|Heat |
|OFF |

#Setpoint values SolutionCard

#HB, Spruce, JasperNA
@SetTemperatureSolutionCardMAXandMIN
Scenario Outline: As an user I want to verify the Max qnd Min temper throguh TAP on stepper
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "SolutionCard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MIN" set temperater on "Dashboard"
Examples:
|Mode|
|Cool|
|Heat|
|Cool only|
|Heat only|

#HB, Spruce, JasperNA
#requirment : Should be in NO schedule stat
@SetTemperatureSolutionCardMAXandMINWhenNoschedule
Scenario Outline: As an user I want to verify the Max and Min temper throguh TAP on stepper when no schedule
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "SolutionCard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MIN" set temperater on "Dashboard"
Examples:
|Mode|
|Cool|
|Heat|
|Cool only|
|Heat only|

#JasperEMEA
@SetTemperatureSolutionCardMAXandMINEMEA
Scenario Outline: As an user I want to verify the Max and Min temper through TAP on stepper
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "SolutionCard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
When user navigates to "Dashboard"
Then user should be displayed with "MIN" set temperater on "Dashboard"
Examples:
|Mode|
|Heat|

#Setpoint value Dashboard

#HB, Spruce, JasperNA
@SetTemperatureDashboardMAXandMIN
Scenario Outline: As an user I want to verify the Max and Min temper through TAP on stepper
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Dashboard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
Examples:
|Mode|
|Cool|
|Heat|
|Cool only|
|Heat only|

#HB, Spruce, JasperNA
#Requirement : When no schedule stat
@SetTemperatureDashboardMAXandMINWhenNoschedule
Scenario Outline: As an user I want to verify the Max and Min temper through TAP on stepper when no schedudle
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "Dashboard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
Examples:
|Mode|
|Cool|
|Heat|

|Cool only|
|Heat only|

#JasperEMEA
@SetTemperatureDashboardMAXandMINEMEA
Scenario Outline: As an user I want to verify the Max and Min temper through TAP on stepper
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user navigates to "SolutionCard" screen 
When user selects the "MAX"  set temperate through taping on "UP stepper"
Then user should be displayed with "MAX" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MAX" set temperater on "SolutionCard"
When user navigates to "Dashboard" screen 
When user selects the "MIN"  set temperate through taping on "Down stepper"
Then user should be displayed with "MIN" set temperater on "Dashboard"
When user navigates to "SolutionCard"
Then user should be displayed with "MIN" set temperater on "SolutionCard"
Examples:
|Mode|
|Heat|

#OFF Mode Dashboard and primary card 

@HB, Spruce
@SetTemperatiureOFFMode
Scenario Outline: As an user I want to verify the setpoint value on OFF mode 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "Dashboard" screen 
Then user should be displayed with disabled "UP stepper" and "Down stepper"
And suer should be displayed with setpoint value "--" 
And suer should be displayed with "xx INSIDE" tempr with "OFF" status
When user navigates to "SolutionCard" screen
Then user should displayed with "greyed out" indoor tempr value 
And user should be displayed with "Thermostat is OFF" status 
And user should be displayed with disabled "UP stepper" and "Down stepper"
And suer should be displayed with setpoint value "--" 
And user should be displayed with "Humidity xx" above "Thermostat is OFF" status
Examples:
|Mode| 
|OFF|

@JAsperNA, JasperEMEA 
@SetTemperatiureOFFMode
Scenario Outline: As an user I want to verify the setpoint value on OFF mode 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user navigates to "Dashboard" screen 
Then user should be displayed with disabled "UP stepper" and "Down stepper"
And suer should be displayed with setpoint value "--" 
And suer should be displayed with "xx INSIDE" tempr with "OFF" status
When user navigates to "SolutionCard" screen
Then user should displayed with "greyed out" indoor tempr value 
And user should be displayed with "Thermostat is OFF" status 
And user should be displayed with disabled "UP stepper" and "Down stepper"
And suer should be displayed with setpoint value "--" 
Examples:
|Mode| 
|OFF|



#Multistats


@SetTemperatureSolutionCardFromJasperNA
Scenario Outline:To set temperature for location with multistat(Jasper NA,HBB)systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to set temperature
So that my comfortable indoor temperature maintained   
Given Jasper NA "stat1" with <Mode>
Then user navigates to "SolutionCard"
When User set the temperature from Jasper NA "stat1" in app
But User set the temperature from Jasper NA "stat1" in app from other mobile
But User set the temperature from Jasper NA "stat1"
Then Verify the Jasper NA "stat1" status on the primary card for set temperature
And Verify the Jasper NA "stat1" schedule temperature is override with set temperature
And Verify maximum and minimum set values is followed
And Verify the Jasper NA "stat1" widget on the location dashboard for set temperature
Examples:
|Mode |
|Heat | 
|Cool |
|Auto |
|Heat only|
|Cool only|

@SetTemperatureFromHBB 
Scenario Outline:To set temperature for location with multistat(Jasper NA,HBB)systems Heat cool,Cool,Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr
As an user 
I want to set temperature
So that my comfortable indoor temperature maintained   
Given HBB "stat1" with <Mode>
When User set the temperature from HBB "stat1" in app
But User set the temperature from HBB "stat1" in app from other mobile
But User set the temperature from HBB "stat1"
Then Verify the HBB "stat1" status on the primary card for set temperature
And Verify the HBB "stat1" schedule temperature is override with set temperature
And Verify maximum and minimum set values is followed
And Verify the HBB "stat1" widget on the location dashboard for set temperature
Examples:
|Mode |
|Heat | 
|Cool |
|Auto |

@SetTemperatureFromEMEA
Scenario Outline:To set temperature for location with multistat with time format 24/12hr 
As an user 
I want to set temperature 
So that my stat will maintain my comfortable indoor temperature  
Given Stat1 with "Heat" mode
When User set the temperature from "Stat1" in app
But User set the temperature from "Stat1" in app from other device
But User set the temperature from "Stat1"
Then Verify the "Stat1" status on the primary card for set temperature
And Verify the "Stat1" schedule temperature is override with set temperature 
And Verify maximum and minimum set values is followed
And Verify the "Stat1" widget on the location dashboard for set temperature


#network error

#HB, Spruce, JasperNA, JasperEMEA
@NetworkdownSolutionCard&SolutionCard
Scenario Outline:To get error messages on network down in primary card
As an user
I want to get error message in primary card 
Given With the <Condition>
Then Verify the error message
Examples:
|Condition                       |
|Mobile internet(3G/wifi) is down|
|Mobile in Airplane mode         |
|Mobile with low signal(3G/wifi) |
|CHIL down                       |
|Switching between the network   |
|Smart network switch            |



#HB, Spruce, JasperNA, JasperEMEA
@NetworkdownDashboard&SolutionCard
Scenario Outline:To get error messages on network down in location Dashboard
As an user
I want to get error message in location Dashboard 
So that I will get notified on network down
Given With the <Condition>
Then Verify the error message
Examples:
|Condition                       |
|Mobile internet(3G/wifi) is down|
|Mobile in Airplane mode         |
|Mobile with low signal(3G/wifi) |
|CHIL down                       |
|Switching between the network   |
|Smart network switch            |


#Feature: As an user I want to change the AutoChangeover option for JapserNA , JasperEMEA, HB and Spruce
#AutoChangeover

#HB, Spruce, JaperNA
@DashboardandsolutioncardAutochangeover
Scenario:As an user  i want to view the option for automode 
Given Stat with Heat Cool system
And Autochangeover enabled in stat
When user selects mode icon in solution card 
Then verify user provided with auto mode option in set mode screen

#HB, Spruce, JaperNA - negative case
@DashboardandsolutioncardAutoModeNegative
Scenario Outline: As an user i should not shown with the option for automode 
Given Stat with <system>
And Autochangeover enabled in stat
When user selects mode icon in solution card 
Then verify user not provided with auto mode option in set mode screen
Examples:
|system|
|Heat  |
|Cool  |

#HB, Spruce, JaperNA // negative case
@DashboardandsolutioncardAutoModeNegative1
Scenario Outline: As an user i should not shown with the option for automode
Given Stat with <system>
And Autochangeover disabled in stat
When user selects mode icon in solution card 
Then verify user not provided with auto mode option in set mode screen
Examples:
|system|
|Heat  |
|Cool  |
|Heat Cool|

#HB, Spruce, JaperNA
@DashboardandsolutioncardAutoModewithMutliOS
Scenario Outline:As an user i want to set the stat with automode 
Given Stat with Heat Cool system
And Autochangeover enabled in stat
When <user> selects auto mode in <mobile device> 
Then verify stat set to auto mode
And verify app logged with <user> in <mobile device> mode icon changed to auto mode
Examples:
|user  |mobile device|
|Actual|android      |
|Actual|iOS          |
|Invite|android      |
|Invite|iOS          |

#HB, Spruce, JaperNA
@DashboardandsolutioncardCheckSetpointInSchedule
Scenario Outline:As an user i want the Heat setpoint should be always less than the cool setpoint in configure scheduling screens  
Given Stat with Heat Cool system
And Autochangeover enabled in stat
When user selects  time based scheduling 
And selects following <schedule option>
|schedule option        |
|Time based schedule    |
|Geofence based schedule|
And selects following <schedule type>
|schedule type|
|Everyday     |
|Weekday      |
And user <configure> schedule points
|Configure|
|Create   |
|Edit     | 
And set points within maximum and minimum range                               
Then verify Heat setpoint should be always less than the cool setpoint

#HB, Spruce, JaperNA
@DashboardandsolutioncardCheckSetpointInVacationSettings
Scenario Outline:As an user i want the Heat setpoint should be always less than the cool setpoint in vacation settings  
Given Stat with Heat Cool system
And Autochangeover enabled in stat
When user selects set points within maximum and minimum range                               
Then verify Heat setpoint should be always less than the cool setpoint


#CoachMark

#JasperNA, HB, Spruce
@Dashboardandsolutioncardoach-markverification
Scenario Outline: AS a user I want to verify Dashboard & Solutioncard coach-mark
Given user set to <Mode> through CHIL
And user launches and logs in to the lyric application 
And user navigates to "Dashboard"
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Quick Controls" coach-mark
And user selects the "Back" button 
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Quick Controls" coach-mark
And user selects the "Got It" button 
Then user should displayed with "OutSide Temperature" coach-mark
And user selects the "Back" button 
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Outside Temperature" coach-mark
And user selects the "Got It" button 
And user should not display "coach-mark"
When user navigates to "SolutionCard" screen from "Dashboard" screen
Then user should displayed with "Indoor Temperature Reading" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Fan" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Schedule" coach-mark
And user Selects the "Back" button 
Then user should displayed with "Fan" coach-mark
And user Selects the "Back" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Back" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Back" button
Then user should displayed with "Indoor Temperature Reading" coach-mark 
And user Selects the "Got It" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Fan" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Schedule" coach-mark
And user Selects the "Got It" button 
And user should not display "coach-mark"
Examples:
|Mode|
|Cool|
|Heat|
|Auto|
|DR|
|Offline|

#JasperEMEA
@Dashboardandsolutioncardoach-markverificationEMEA
Scenario Outline: AS a user I want to verify Dashboard & Solutioncard coach-mark
Given user set to <Mode> through CHIL
And user launches and logs in to the lyric application 
And user navigates to "Dashboard"
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Quick Controls" coach-mark
And user selects the "Back" button 
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Quick Controls" coach-mark
And user selects the "Got It" button 
Then user should displayed with "OutSide Temperature" coach-mark
And user selects the "Back" button 
Then user should displayed with "Access More Information" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Outside Temperature" coach-mark
And user selects the "Got It" button 
And user should not display "coach-mark"
When user navigates to "SolutionCard" screen from "Dashboard" screen
Then user should displayed with "Indoor Temperature Reading" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Fan" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Schedule" coach-mark
And user Selects the "Back" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Back" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Back" button
Then user should displayed with "Indoor Temperature Reading" coach-mark 
And user Selects the "Got It" button 
Then user should displayed with "Temp stepper" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Mode" coach-mark
And user Selects the "Got It" button 
Then user should displayed with "Schedule" coach-mark
And user Selects the "Got It" button 
And user should not display "coach-mark"
Examples:
|Mode|
|OFF |
|Heat|
|Offline|

#Dashboard order 
@Dashboardorderwithallsolutions
Scenario Outline:  user configured with C1, C2 , JasperNA, JasperEMEA, WLD, DAS
Given user launches and logs in to the lyric application
Then user  navigates to "Dashboard"
When user should displayed with "alphanumeric order"

#JasperNA, HB, Spruce
#Emergencyheat should be enabled 
@SolutionCardEmergencyHeatbothcoolandheat
Scenario Outline: As an user I want to verify Emergency heat on solution card when both heat and cool  mode configured
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user enables the "Emergency Heat" under settings
Then user should be displayed with "Heat" Mode
And user should be displayed with "Emergencey heat" on "Solutioncard"
When user switch the <SystemMode> 
Then user should not be displayed "Emergency heat" on "SolutionCard"
And user should be displayed with disabled "Emergency heat" option under settings
Examples:
|Mode | systemMode|
|Cool | Cool |
|Cool | Auto | 
|Cool | OFF  |
|Cool | Cool |
|Heat | Cool |
|Heat | Auto |
|Heat | OFF |
|Heat |Heat|
|Auto | Cool |
|Auto | Heat | 
|Auto | OFF |
|Auto |Auto |
|OFF | Cool |
|OFF | Heat | 
|OFF | OFF |
|OFF | auto|

#JasperNA, HB, Spruce
#Emergencyheat should be enabled 
@SolutionCardEmergencyHeatHeatonly
Scenario Outline: As an user I want to verify Emergency heat on solution card when heat only mode configured
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user enables the "Emergency Heat" under settings
Then user should be displayed with "Heat" Mode
And user should be displayed with "Emergencey heat" on "Solutioncard"
When user switch the <SystemMode> 
Then user should not be displayed "Emergency heat" on "SolutionCard"
And user should be displayed with disabled "Emergency heat" option under settings
Examples:
|Mode | systemMode|
|Heat only | OFF |
|Heat only | Heat only  | 
|OFF | OFF |
|OFF| Heat only  | 

#JasperNA, HB, Spruce
#Emergencyheat should be enabled 
@SolutionCardEmergencyHeatCoolonly
Scenario Outline: As an user I want to verify Emergency heat on solution card when cool only mode configured
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user should not displayed with "Emergency Heat" under settings
And user should not  displayed with "Emergencey heat" on "Solutioncard"
Examples:
|Mode | 
|Cool only |
|OFF| 


#JasperEMEA
#Emergencyheat should be enabled 
@SolutionCardEmergencyHeatHeatonly
Scenario Outline: As an user I want to verify Emergency heat on solution card 
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user should not displayed with "Emergency Heat" under settings
And user should not  displayed with "Emergencey heat" on "Solutioncard"
Examples:
|Mode | 
|Heat only |
|OFF| 



