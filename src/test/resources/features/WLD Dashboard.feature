@WLDDashboard
Feature: WLD Dashboard
  As user I should be able to View dashboard of WLD from the app


@viewDashboard
Scenario: As a user I should be able view dashboard of WLD through the Lyric application 
Given user launches and logs in to the Lyric application 
When user lands on "Dashboard" screen 
Then user should be able to see "WLD device status"
And user should be able to see "Last updated time"
And user should be able to see "Current temperature percentage"
And user should be able to see "Current humidity percentage"

@ViewDashboardTemperatureunit
Scenario: As a user i should be able to view the change in temperature unit on my dashboard
Given user launches and logs in to the Lyric application
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
And temperature unit is set to "F"
And user navigates to "Dashboard" Screen from "Leak Detector  Settings" screen
Then user should be displayed with "Current temperature" in  degree faranheit in "dashboard" screen
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
And temperature unit is set to "C"
And user navigates to "Dashboard" Screen from "Leak Detector  Settings" screen
Then user should be displayed with "Current temperature" in  degree Celcius in "dashboard" screen


@landingToSolutionCard
Scenario: As a user i should be able to land on solution card from dashboard
Given user launches and logs in to the Lyric application
When user Taps on "Dashboard"
Then user should be displayed with "Solution Card" Screen