@DashbboardandSolutionCard
Feature: As an user I want to verify the Dashboard and primary card for JapserNA , JasperEMEA, HB and Spruce 

#Dashboard view 

#HB, Spruce and JasperNA
@ViewDashboard @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the Dashboard view with respective system modes 
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
And user should be displayed with the "Thermostat Dashboard" screen
Then user should be displayed with "Thermostat name" with "XX INSIDE" temperature 
Then the following "Thermostat" options should be enabled:
 |Options|
 |Up Stepper|
 |Down Stepper|
And user "should be displayed" with the "respective setpoint value" option
#And user should be displayed with respective <Mode> Color 
Examples:
|Mode|
|Cool| 
#|Heat|
#|Auto|

#incaserequired 
#|Cool only|
#|Heat Only|

#JasperEMEA
@ViewDashboardEMEA @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the Dashboard view with system modes 
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
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
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
When user should be displayed with the "thermostat Dashboard" screen 
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
<<<<<<< HEAD
Then user "should be displayed" with the "--" option
=======
#Then user "should be displayed" with the "--" option
>>>>>>> a1ac7f20dcd50a57123ae8d3d477d7842a6557d7
And user should see the "Inside temperature" status as "OFF" on the "thermostat dashboard" 
Examples:
|Mode |
|OFF |


#HB, Spruce and JasperNA
@ViewSolutionCard @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the SolutionCard view with respective system modes 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
And the following "Thermostat" options should be enabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should be displayed" with the "respective setpoint value" option
And the following "Thermostat icons" options should be enabled:
|Options|
|mode| 
|Schedule| 
Examples:
|Mode |
|Cool| 
|Heat |
#|Auto |
#|Cool only|
#|Heat Only|


#JasperEMEA. #sangeetha
@ViewSolutionCardEMEA @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the SolutionCard view with system modes 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
And the following "Thermostat" options should be enabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should be displayed" with the "respective setpoint value" option
And the following "Thermostat icons" options should be enabled:
|Options|
|mode| 
|Schedule| 
Examples:
|Mode |
|Heat |

#HB, Spruce, JasperNA, JasperEMEA.
@ViewSolutionCardOFF @DashbboardandSolutionCard_P1
Scenario Outline: As an user I want to verify the SolutionCard view with "OFF" mode 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should not be displayed" with the "respective setpoint value" option
And user should see the "Inside temperature" status as "OFF" on the "thermostat solution card" screen  
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

Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
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
#|Heat |
#|OFF |

#Requirements : Auto mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled
Scenario Outline: As an user I want to verify the Systemode info option when both cool and heat configured with auto mode enabled
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
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
#|Cool |
#|Heat |
|Auto | 
#|OFF |

#in case required
#Requirements : Cool only mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithCoolOnly
Scenario Outline: As an user I want to verify the Systemmode info option when cool only configured 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
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

#in case required
#Requirements : Heat only mode should enabled
#HB, Spruce, JasperNA
@SystemModeInfoscreenwithHeatOnly
Scenario Outline: As an user I want to verify the Systemmode info option when heat only configured
Given user has <Mode> system mode 
Given user launches and logs in to the Lyric application
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
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
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
#|OFF|



#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithbothcoolandheatandautoenabledCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch between Cool , heat, off , auto
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "X" from "Change Mode" Screen
Then user "should not be updated" with <SystemMode> option
Examples:
|Mode|SystemMode|
|Cool|Off|

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch Heat only 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "X" from "Change Mode" Screen
Then user "should not be updated" with <SystemMode> option
Examples:
|Mode| systemmode | 
|Heat| Heat | 
|Heat | OFF| 
|OFF| Heat | 
|OFF | OFF| 

#HB, Spruce, JasperNA
@SystemModeswitchSystemmodescreenwithcoolonlyCancelfunctionality
Scenario Outline: As an user I want to verify the system mode when cancel option while switch between cool only
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "X" from "Change Mode" Screen
Then user "should not be updated" with <SystemMode> option
Examples:
|Mode| systemmode | 
|Cool | Cool| 
|Cool | OFF| 
|OFF | Cool| 
|OFF | OFF| 

#JasperEMEA
@SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionalityEMEA
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "X" from "Change Mode" Screen
Then user "should not be updated" with <SystemMode> option
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
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "save" from "Change Mode" Screen
Then user "should not be updated" with <SystemMode> option
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
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "save" from "Change Mode" Screen
Then user "should be updated" with <SystemMode> option
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
@SystemModeswitchSAVEfunctioncoolonly  #sangeetha 
Scenario Outline: As an user I want to verify the system mode save option while switch between cool, off
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "save" from "Change Mode" Screen
Then user "should be updated" with <SystemMode> option
Examples:
|Mode | SystemMode| 
|Cool | Cool |
|Cool | OFF |
|OFF | Cool |
|OFF | OFF |

#HB, Spruce, JasperNA
@SystemModeswitchSAVEfunctionHeatonly  #sangeetha
Scenario Outline: As an user I want to verify the system mode save option while switch between heat, off
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "save" from "Change Mode" Screen
Then user "should be updated" with <SystemMode> option
Examples:
|Mode | SystemMode| 
|Heat | Heat |
|Heat | OFF |
|OFF | Heat |
|OFF | OFF |


#JasperEMEA
@SystemModeswitchSAVEfunctionEMEA #sangeetha
Scenario Outline: As an user I want to verify the system mode save option while switch between heat, off
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
When user selects "Mode" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change mode" screen 
And user should be displayed with the following "Mode" options: 
|Options|
|Heat|
|Cool|
|Off|
Then user "should be displayed" with the "Blue Tick mark on selected mode" option
And user selects <SystemMode> from "change Mode" Screen
Then user "should be displayed" with the "Blue Tick mark on new selected mode" option
And user should be displayed with the <SystemMode> description 
When user selects "save" from "Change Mode" Screen
Then user "should be updated" with <SystemMode> option
Examples:
|Mode | SystemMode| 
|Heat | Heat |
|Heat | OFF |
|OFF | Heat |
|OFF | OFF |




###lavanya
#HB, Spruce, JasperNA
@FanOptionInfoOption
Scenario Outline: As an user I want to verify the Fan mode info option
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Fan" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change Fan" screen 
When user selects "Info" from "Change Fan" screen
Then user should be displayed with the "Fan Info" screen
And user should be displayed with the following "Fan Info" options:
|Options|
|AUTO - FAN RUNS WHILE HEATING OR COOLING|
|CIRCULATE - FAN RUNS INTERMITTENTLY TO CIRCULATE AIR|
|ON - FAN RUNS CONTINUOUSLY|
When user selects "BACK" from "Fan info" screen
Then user should be displayed with the "Change fan" screen
Examples:
|Mode | 
#|Cool |
|Heat | 
#|Auto | 
#|OFF |
#in case required
#|Heat only |
#|Cool only|


#HB, Spruce, JasperNA
@FanModeSwitchcancelfunction #lavanya
Scenario Outline: As an user I want to verify the Fan mode cancel option while switch between Auto, circulate and ON
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Fan" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change Fan" screen 
When user selects <FanMode> from "Change Fan" screen
Then user "should be displayed" with the "Blue Tick mark on selected Fan" option 
And user should be displayed with the <FanMode> description 
When user selects "X" from "Change Fan" screen
Then user should be displayed with the "Thermostat Solution Card" screen
And user "should not be updated" with the <FanMode> option
Examples:
|Mode |  FanMode | 
|Cool | Auto Fan|
#|Cool | Circulate |
#|Cool | ON | 
|Heat | Auto Fan|
#|Heat | Circulate |
|Heat | ON |
#|Auto | Auto Fan|
#|Auto | Circulate |
#|Auto | ON | 
#|OFF | Auto Fan|
#|OFF | Circulate |
#|OFF | ON |  

#in case required
#|Heat only | Auto Fan|
#|Heat only | Circulate |
#|Heat only | ON | 
#|Cool only | Auto Fan|
#|Cool only | Circulate |
#|Cool only | ON | 

#HB, Spruce, JasperNA
@FanModeSwitchSAVEfunction #lavanya
Scenario Outline: As an user I want to verify the Fan mode save option while switch between Auto, circulate and ON
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user selects "Fan" from "Thermostat Solution Card" screen
Then user should be displayed with the "Change Fan" screen 
When user selects <FanMode> from "Change Fan" screen
Then user "should be displayed" with the "Blue Tick mark on selected Fan" option 
And user should be displayed with the <FanMode> description 
When user selects "SAVE" from "Change Fan" screen
Then user should be displayed with the "Thermostat Solution Card" screen
And user "should be updated" with the <FanMode> option
Examples:
|Mode |  FanMode | 
#|Cool | Auto Fan|
#|Cool | Circulate |
#|Cool | ON | 
|Heat | Auto Fan|
#|Heat | Circulate |
|Heat | ON | 
#|Auto | Auto Fan|
#|Auto | Circulate |
#|Auto | ON | 
#|OFF | Auto Fan|
|OFF | Circulate |
#|OFF | ON | 

#in case required
#|Heat only | Auto Fan|
#|Heat only | Circulate |
#|Heat only | ON | 
#|Cool only | Auto Fan|
#|Cool only | Circulate |
#|Cool only | ON | 

#JasperEMEA
@FanModeOptionONEMEA
Scenario Outline: As an user I want to verify the Fan mode option for JasperEMEA
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
And user "should not be displayed" with the "FAN" option
Examples: 
|Mode| 
|Heat |
#|OFF |

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
@SetTemperatiureOFFModeHB
Scenario Outline: As an user I want to verify the setpoint value on OFF mode 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
When user should be displayed with the "thermostat Dashboard" screen 
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should be displayed" with the "--" option
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should not be displayed" with the "respective setpoint value" option
And user should see the "Inside temperature" status as "OFF" on the "thermostat solution card" screen  
Examples:
|Mode| 
|OFF|

@JAsperNA, JasperEMEA 
@SetTemperatiureOFFModeNA
Scenario Outline: As an user I want to verify the setpoint value on OFF mode 
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
When user should be displayed with the "thermostat Dashboard" screen 
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should be displayed" with the "--" option
Given user has <Mode> system mode
Then user launches and logs in to the Lyric application
And user should be displayed with the "thermostat Dashboard" Screen
Then user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
When user should be displayed with the "thermostat Solution Card" screen
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should not be displayed" with the "respective setpoint value" option
And user should see the "Inside temperature" status as "OFF" on the "thermostat solution card" screen  
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
#Requirement : One account with  Auto mode enabled
Scenario:As an user  i want to view the option for automode 
Given Stat with Heat Cool system
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



