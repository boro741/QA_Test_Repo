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
Scenario Outline: As a user I want to verify the Dashboard view with OFF mode
Given user has <Mode> system mode
Given user launches and logs in to the Lyric application
When user should be displayed with the "thermostat Dashboard" screen
And the following "Thermostat" options should be disabled:
|Options|
|UP stepper|
|Down stepper|
Then user "should be displayed" with the "--" option
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
Scenario Outline: As an user I want to verify the SolutionCard view with OFF mode 
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



