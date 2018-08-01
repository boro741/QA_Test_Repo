@AdhocOverride
Feature:   
I want to override my running schedule so that i can set my comfortable temperature manually 
I should able to hold the override temperature temporarily for maximum of 12 hrs.
I should able to permanently override scheduling set value from the thermostat primary card. 
I should be able to cancel overrides and resume scheduling.

#Following schedule and using settings

 #JasperNA
 @AdhocOverrideTimebaseSchedulefollowingfromsolutioncard
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