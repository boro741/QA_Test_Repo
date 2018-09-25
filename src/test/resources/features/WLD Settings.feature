@WLDSettings 
Feature: WLD Settings 
  As user I should be able to control my WLD settings from the app

@VerifyWLDSettings @Automated
Scenario: As a user I want to verify that all WLD Settings options are available to me 
Given user launches and logs in to the Lyric application  
And user navigates to "WLD Settings" screen from the "Dashboard" screen
Then user should be displayed with the following "Leak Detector Settings" options: 
      | Settings					| 
      | Manage Alerts				|
      | Battery Status				|
      |	Temperature Unit			|
      | Update Frequency			|					 
      | Leak Detector Configuration |

#Manage Alerts 

@EnableDisableIndoorTemperatureAlertInWLDSettings @Automated
Scenario: As a user I should be able to enable or disable Indoor Temperature Alert on my WLD
Given user launches and logs in to the Lyric application  
When user navigates to "WLD Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alerts" to "ON"
Then "WLD Indoor Temperature Alert" value should be updated to "ON" on "Manage Alerts" screen
And user should be displayed with the following "WLD Indoor Temperature Alert" options: 
| WLD Manage Alerts Options| 
| Email for enabled alerts | 
| Alert for this range     |
When user changes the "Indoor Temperature Alerts" to "OFF"
Then "WLD Indoor Temperature Alert" value should be updated to "OFF" on "Manage Alerts" screen
  
@EnableDisableEmailNotifications @AutomateLast
Scenario: As a user I should be able to enable or disable EmailForEnabledAlerts on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alerts" to "ON"
And user changes the "Email Notifications" to "ON"
Then "Email Notifications" value should be updated to "ON" on "Manage Alerts" screen
When user creates "Alerts"
Then user should receive "Email Alert"
When user changes the "Email Notifications" to "OFF"
Then "Email Notifications" value should be updated to "OFF" on "Manage Alerts" screen
When user creates "Alerts"
Then user should not receive "Email Alert"
  
@WLDChangeAlertForThisRangeforTemperature @Automating
Scenario: As a user I should be able to change Alert For This Range on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alert" to "ON"
And user clicks on " Alert for this Range" option
Then user should be displayed with Following Options
| Options	| 
| Below 	| 
| Above		|
And when user Clicks on "Below Range"
Then user should be able to select "Below temperature Range"

And user should be able to receive push notification for below range
And when user Clicks on "Above Range"
Then user should be able to select "Above temperature Range"
And user should be able to receive push notification for Above range
When user selects the "Push notification"
Then user should be navigates to "SolutionCard"

@EnableDisableIndoorHumidityAlert  @Automated
Scenario: As a user I should be able to enable or disable Indoor Humidity Alert on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "WLD Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
Then "WLD Indoor Humidity Alert" value should be updated to "ON" on "Manage Alerts" screen
And user should be displayed with the following "WLD Indoor Humidity Alert" options: 
| WLD Manage Alerts Options| 
| Email for enabled alerts | 
| Alert for this range     |
When user changes the "Indoor Humidity Alert" to "OFF"
Then "WLD Indoor Humidity Alert" value should be updated to "OFF" on "Manage Alerts" screen

@EnableDisableEmailForEnabledAlertsforHumidity @AutomateLast
Scenario: As a user I should be able to enable or disable EmailForEnabledAlerts for Humidity on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
And user changes the "Email Notifications" to "ON"
#navigation back
When user creates "Alerts"
Then user should receive "Email Alert"
Then "Email Notifications" value should be updated to "ON" on "Manage Alerts" screen
When user changes the "Email Notifications" to "OFF"
Then "Email Notifications" value should be updated to "OFF" on "Manage Alerts" screen
When user creates "Alerts"
Then user should not receive "Email Alert"

@ChangeAlertForThisRangeforHumididty @Automatable
Scenario: As a user I should be able to change Alert For This Range on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
And user clicks on " Alert for this Range" option
Then user should be displayed with Following Options
| Options	| 
| Below 	| 
| Above		|
And when user Clicks on "Below Range"
Then user should be able to select "Below Humidity Range"
And user should be able to receive push notification for below range
And when user Clicks on "Above Range"
Then user should be able to select "Above Humidity Range"
And user should be able to receive push notification for Above range
When user selects the "Push notification"
Then user should be navigates to "SolutionCard"

@SetEmailContacts @Automatable
Scenario Outline: As a user I should be able set email contacts on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Email Contacts" screen from the "Dashboard" screen
And user inputs<Email Id>
And user clicks on " Done " option
Then user should be displayed with <Email Id> in the List
When user clicks on "Add" Icon
Then user should be able to add email id from contact list
Examples:
 		| Email ID	 |
      	| Test@grr.la|

#First time when user selects (+) option, should receive "Permission" pop up on android 
#Status of accept "Pending accept" 
#add remove email ID with select option 

@ChangeTemperatureUnit @Automatable
Scenario: As a user I should be able to set Temperture Unit contacts on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
And temperature unit is set to "F"
And user taps on "Temperature Unit"
Then user should be displayed with "C" in "Leak Detector  Settings" screen
When temperature unit is set to "C"
And user taps on "Temperature Unit"
Then user should be displayed with "F" in "Leak Detector  Settings" screen

#please add steps solution card and dashboard (Tempr and humidity )

@SetUpdateFrequency @Automatable
Scenario: As a user I should be able to set Update frequency on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Update Frequency " screen from the "Dashboard" screen
And user changes the "Update Frequency" to "Daily"
Then "Update Frequency" value should be updated to "Daily" on "Leak Detector  Settings" screen
When user changes the "Update Frequency" to "Twice Daily"
Then "Update Frequency" value should be updated to "Twice Daily" on "Leak Detector  Settings" screen
When user changes the "Update Frequency" to "Three Times Daily"
Then "Update Frequency" value should be updated to "Three Times" on "Leak Detector  Settings" screen

#Please add after update and selects back button > pop up validation  " Changes may take up to 24 hours to effect"

#Leak Detector Configuration 

@RenameWLDdevice  @Automated
Scenario: As a user I want to rename my wld through the application 
Given user launches and logs in to the Lyric application 
When user selects "WLD device" from the dashboard
And user navigates to "Leak Detector Configuration" screen from the "WLD Solution card" screen
When user edits the "Water Leak Detector" name to "Test WLD Name" 
And user navigates to "Dashboard" screen from the "Leak Detector Configuration" screen 
Then verify the Edited name on "WLD Dashboard" screen
#user reverts back to Original device name
When user selects "WLD Renamed device" from the dashboard
And user navigates to "Leak Detector Configuration" screen from the "WLD Solution card" screen
When user edits the "Water Leak Detector" name to "Original Name" 
And user navigates to "Dashboard" screen from the "Leak Detector Configuration" screen 
Then verify the Edited name on "WLD Dashboard" screen

@RenameWLDWithDuplicatename @Automated
#Account should have two WLDs with one named as "Test WLD Name" and other named as "Test"
Scenario: As a user I want to get a error message when i enter a duplicate name for my wld
Given user launches and logs in to the Lyric application
When user selects "WLD device" from the dashboard
And user navigates to "Leak Detector Configuration" screen from the "WLD Solution card" screen
When user edits the "Water Leak Detector" name to "Test WLD Name" 
And user navigates to the "Previous" screen 
Then user should receive a "DUPLICATE NAME ERROR" popup

@ViewLeakDetectorconfigurationOptions @Automated
#Precondition: User has set Heating System, Heating Stages, Cooling Stages in Thermostat
Scenario: As a user I want to View my wld Configuration details
Given user launches and logs in to the Lyric application 
And user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
Then user should be displayed with the following "Leak Detector Configuration" options:
|Configuration|
|Leak Detector Name|
|Firmware Details|
|Delete Leak Detector|

@DeleteLeakDetector @NotAutomatable
Scenario: As a user I should be able to delete my WLD device from my account through the Homes application 
Given user launches and logs in to the Homes application 
When user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
And user "Deletes Leak Detector" by clicking on "Delete" button
And user should receive a "Delete Device Confirmation" popup
Then user "Dismisses" the "Delete Device Confirmation" popup
When user "Deletes Leak Detector" by clicking on "Delete" button
And user should receive a "Delete Device Confirmation" popup
And user "Accepts" the "Delete Device Confirmation" popup
Then WLD should be deleted
And user should be displayed with "Dashboard" With "Add new Device" Option