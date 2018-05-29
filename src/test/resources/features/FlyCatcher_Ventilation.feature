@VantilationMode
Feature: As an user 
I want to change Ventialtion settings 
So that my stat will turn on ,Off or autom mode to maintain my comfortable indoor temperature

@ChangeStatVantilationMode
Scenario Outline: To change stat mode based on my indoor temperature for systems
As an user 
I want to change ventialtion mode based on my prefernce
So that my stat will turn on ,Off or autom mode to maintain my comfortable indoor temperature

Given  user has <Mode> Vantilation mode
And user launches and logs in to the Lyric application
And user selects "FlyCatcher device" from the dashboard
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
And user selects "FlyCatcher device" from the dashboard
When user changes Vantilation mode to <To mode>
Then Verify run ventilation is <Run Ventilation>

Examples:
|To mode |Run Ventilation|
|Off  | Enabled     |
|ON  | Disabled   | 
|Auto|Enabled|

@StartVentilationTimer
Scenario Outline: To Set Ventialtion timer for Auto and On mode
As an user 
I want to know if ventilation timer can be started 
so that Ventilation mode wil be enabled during that time period

Given  user has "On" Vantilation mode
And user launches and logs in to the Lyric application
And user selects "FlyCatcher device" from the dashboard
And user changes Vantilation mode to <To mode>
When user navigates to "Ventilation Timer" screen from the "Ventilation Mode" screen
And User Sets the Timer <Mintues>
And User tap on "Save"
And Verify Timer is "displayed" and set to <Mintues>
# And Verify Timer is displayed on the Stat

Examples:
|To mode |Mintues|
|Off  | 20     |
|Auto|20|


@StopVentilationTimer
Scenario Outline: To stop the Ventialtion timer for Auto and On mode
As an user 
I want to know stop ventilation timer
so that Ventilation can be turned off

Given User sets Ventilation Timer <Mintues>
And user launches and logs in to the Lyric application
And user selects "FlyCatcher device" from the dashboard
And user changes Vantilation mode to <To mode> 
And user navigates to "Ventilation Timer" screen from the "Ventilation Mode" screen
Then User tap on "Stop timer"
And Verify Timer is "Stopped"
# And Verify Timer is Stopped on the Stat
Examples:
|To mode |Mintues|
|Off  | 20     |
|Auto|20|


@VentilationTimerIncreamentalof20
Scenario Outline: To veify if Ventilation timer is increamental of 20
As an user 
I want set ventilantion timer and timer is increamental of 20
so on tpaing Incrementing or decrementing timer increases or reduces 20

Given user has "OFF" Vantilation mode
And user launches and logs in to the Lyric application
And user selects "FlyCatcher device" from the dashboard
And user navigates to "Ventilation Timer" screen from the "Ventilation Mode" screen
When user <Control Buttons> the timer <Mintues>
And user taps on "Save"
Then Verify Timer is "displayed" and set to <Mintues>
Examples:
|Control Buttons|Mintues|
|Increment |20     |
|Increment|40|
|Increment|60|
|Increment|80|
|Decrement|60|
|Decrement|40|
|Decrement|20|


