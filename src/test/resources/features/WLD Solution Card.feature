@WLDSolutionCard
Feature: WLD Solution Card
  As user I should be able to View Solution card of WLD from the app

@viewwldSolutionCard @Automated
Scenario: As a user I should be able view Contents of my WLD Solution card through the Lyric application 
Given user launches and logs in to the Lyric application  
When user selects "WLD device" from the dashboard
Then user should be displayed with the following "WLDSolutionTemperature" options:
|WLD Solution options|
|Current Temperature|
|Last Updated Time|
#|Temperature Graph| #remove comment if account is 48 hours passed
|Battery percentage|
|Next Update Time|
#And user should be displayed with the "Settings" description
When user navigates to "humidity graph" screen from the "temperature graph" screen
Then user should be displayed with the following "WLDSolutionHumidity" options:
|WLD Humidity Solution options|
|Current Humidity|
|Last Updated Time|
#|Humidity Graph| #remove comment if account is 48 hours passed

@ViewSolutionCardTemperatureunit
Scenario: As a user i should be able to view the change in temperature unit on my Solution Card
Given user launches and logs in to the Lyric application
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
And temperature unit is set to "F"
And user navigates to "Dashboard" Screen from "Leak Detector  Settings" screen
Then user should be displayed with "Current temperature" in  degree faranheit in "dashboard" screen
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
And temperature unit is set to "C"
And user navigates to "Dashboard" Screen from "Leak Detector  Settings" screen
Then user should be displayed with "Current temperature" in  degree Celcius in "dashboard" screen

@ViewSolutionCardNextupdateTime
Scenario: As a user i should be able to view the change in Update frequency on my Solution Card
Given user launches and logs in to the Lyric application
When user navigates to "Update Frequency" screen from the "Dashboard" screen 
And Sets the "Update frequency" to "Twice Daily"
Then user should be displayed with the "updated new time after 24 hours " in "Solution Card" Screen
When user navigates to "Update Frequency" screen from the "Dashboard" screen 
And Sets the "Update frequency" to "Three Times Daily"
Then user should be displayed with the "updated new time after 24 hours " in "Solution Card" Screen

@TemperatureAndHumidityGrapghAfterDIY
Scenario: As a user i should be able to view a message on my temperature graph of solution card after DIY
Given user launches and logs in to the Lyric application
When user navigates to "Solution Card" screen from the "Dashboard" screen
Then user should be able to see a "Message set up complete and data will be displayed after 48 hours"
When user navigates to "Humidity" Screen
Then user should be able to see a "Message set up complete and data will be displayed after 48 hours"

@TemperatureAndHumidityGraphAftertwodaysDIY
Scenario: As a user i should be able to view temperature and humidity temperature graph of my WLD after two days of DIY
Given user launches and logs in to the Lyric application
When user navigates to "Solution Card" screen from the "Dashboard" screen
Then user should be displayed with "Two Day Graph"
And user should be displyed with the "Dates of which trend is recorded"
And user should be displayed with the " No of days trend"
And user should be diaplayed with the " Maximum and minimum temperature trend"
When user navigates to "Humidity" Screen
Then user should be displayed with "Two Day Graph"
And user should be displyed with the "Dates of which trend is recorded"
And user should be displayed with the " No of days trend"
And user should be diaplayed with the " Maximum and minimum humidity trend"

@TemperatureAndHumidityGraphAftertwodaysDIY
Scenario: As a user i should be able to view temperature and humidity temperature graph of my WLD after 5 to 30 days
Given user launches and logs in to the Lyric application
When user navigates to "Solution Card" screen from the "Dashboard" screen
Then user should be displayed with "5 - 30 days graph"
And user should be displyed with the "Dates of which trend is recorded"
And user should be displayed with the " No of days trend"
And user should be diaplayed with the " Maximum and minimum temperature trend"
And user should be able to see maximun "5 dates"
When user navigates to "Humidity" Screen
Then user should be displayed with "Two Day Graph"
And user should be displyed with the "Dates of which trend is recorded"
And user should be displayed with the " No of days trend"
And user should be diaplayed with the " Maximum and minimum humidity trend"
And user should be able to see maximun "5 dates"








