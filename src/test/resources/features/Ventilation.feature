@ChangeVantilationMode
Feature: As an user 
I want to change Ventialtion settings 
So that my stat will turn on ,Off or autom mode to maintain my comfortable indoor temperature

@ChangeStatVantilationModeNA
Scenario Outline: To change stat mode based on my indoor temperature for systems
As an user 
I want to change ventialtion mode based on my prefernce
So that my stat will turn on ,Off or autom mode to maintain my comfortable indoor temperature

Given  user has <Mode> Vantilation mode
And user launches and logs in to the Lyric application
And user selects "Jasper device" from the dashboard
When user changes Vantilation mode to <To mode>
Then verify Vantilation mode is changed to <To mode>
And user logs out of the app
Examples:
|Mode |To mode|
|Off  |On     |
|Off  |Auto   | 
|On   |Auto   |
|On   |Off    |
|Auto |On     |
|Auto |Off    |


@RunVentilationWhenAuto-On
Scenario Outline: To verify Run Ventilation mode is enable in Auto/Off mode
As an user 
I want to know if Run ventilation is enable during auto/off mode 
so that i can start my timer 

Given user launches and logs in to the Lyric application
And user selects "Jasper device" from the dashboard
When user changes Vantilation mode to <To mode>
Then Verify run ventilation is <Run Ventilation>

Examples:
|To mode |Run Ventilation|
|Off  | Enabled     |
|ON  | Disabled   | 
|Auto|Enabled|

#@StartVentilationTimer
#Scenario Outline: To verify Ventialtion timer for Auto and On mode
#As an user 
#I want to know if ventilation timer can be started 
#so that Ventilation mode wil be enabled during that time period
#
#Given  user has "On" Vantilation mode
#And user launches and logs in to the Lyric application
#And user selects "Jasper device" from the dashboard
#When user changes Vantilation mode to <To mode>
#And 