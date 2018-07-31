@VacationSettings
Feature: As an user I want to set the vacation period for my home so that my thermostat will follow vacation setting on my absence from my home during the vacation days  
#
@VacationsVerifyStartAndEndDate
Scenario Outline: Verify Start and End Date
 Given vacation mode is <settings>
 And user launches and logs in to the Lyric application
 When user navigates to "Vacation" screen from the "Dashboard" screen
 Then user is displayed with start date and end date options based on the <settings> vacation
 And user logs out of the app
 Examples: 
| settings | 
| active   | 
| InActive | 