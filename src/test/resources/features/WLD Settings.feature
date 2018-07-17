@WLDSettings 
Feature: WLD Settings 
  As user I should be able to control my WLD settings from the app

@VerifyWLDSettings
Scenario: As a user I want to verify that all WLD Settings options are available to me 
Given user launches and logs in to the Lyric application 
When user navigates to "Leak Detector  Settings" screen from the "Dashboard" screen 
Then user should be displayed with the following "Leak Detector  Settings" options: 
      | Settings					| 
      | Manage Alerts				|
      | Battery Status				|
      |	Temperature Unit			|
      | Update Frequency			|					 
      | Leak Detector Configuration 		| 


#Manage Alerts 

@EnableDisableIndoorTemperatureAlertInWLDSettings
Scenario: As a user I should be able to enable or disable Indoor Temperature Alert on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alert" to "ON"
Then "Indoor Temperature Alert" value should be updated to "ON" on "Leak Detector Settings" screen
And following "Indoor Temperature Alert" option should be displayed
| Options          		 | 
| Email for enabled alerts | 
| Alert for this range     |
When user changes the "Indoor Temperature Alert" to "OFF"
Then "Indoor Temperature Alert" value should be updated to "OFF" on "Manage Alerts" screen
	  
     
@EnableDisableEmailNotifications
Scenario: As a user I should be able to enable or disable EmailForEnabledAlerts on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alert" to "ON"
And user changes the "Email Notifications" to "ON"
Then "Email Notifications" value should be updated to "ON" on "Manage Alerts" screen
When user creates "Alerts"
Then user should receive "Email Alert"
When user changes the "Email Notifications" to "OFF"
Then "Email Notifications" value should be updated to "OFF" on "Manage Alerts" screen
When user creates "Alerts"
Then user should not receive "Email Alert"
  
@ChangeAlertForThisRangeforTemperature
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





@EnableDisableIndoorHumidityAlert
Scenario: As a user I should be able to enable or disable Indoor Humidity Alert on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
Then "Indoor Humidity Alert" value should be updated to "ON" on "Thermostat Settings" screen
And following "Indoor Humidity Alert" option should be displayed
When user changes the "Indoor Humidity Alert" to "OFF"
Then "Indoor Humidity Alert" value should be updated to "OFF" on "Manage Alerts" screen
	  | Options					 | 
      | Email for enabled alerts | 
      | Alert for this range     |


@EnableDisableEmailForEnabledAlertsforHumidity
Scenario: As a user I should be able to enable or disable EmailForEnabledAlerts for Humidity on my WLD
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
And user changes the "Email Notifications" to "ON"
When user creates "Alerts"
Then user should receive "Email Alert"
Then "Email Notifications" value should be updated to "ON" on "Manage Alerts" screen
When user changes the "Email Notifications" to "OFF"
Then "Email Notifications" value should be updated to "OFF" on "Manage Alerts" screen
When user creates "Alerts"
Then user should not receive "Email Alert"

@ChangeAlertForThisRangeforHumididty
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


@SetEmailContacts
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
 




@ChangeTemperatureUnit
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

@SetUpdateFrequency
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

@RenameWLDdevice
Scenario: As a user I want to rename my thermostat through the application 
Given user launches and logs in to the Lyric application 
And user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
When user edits the "Leak Detector" name to "Test WLD Name" 
And user navigates to "Dashboard" screen from the "Leak Detector Configuration" screen 
Then user should be displayed with "Test WLD Name" device on the "dashboard" screen 
And user reverts back the "WLD device name" through CHIL

@RenameThermostatwithDuplicatename
Scenario: As a user I want to get a error message when i eneter a duplicate name for my thermostat
Given user launches and logs in to the Lyric application 
And user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
When user edits the "WLD 1" name to "Test WLD Name" 
And user navigates to "Dashboard" screen from the "Leak Detector Configuration" screen 
Then user should be displayed with "Test WLD Name" device on the "dashboard" screen
When user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
And user edits the "WLD 2" name to "Test WLD Name" 
Then user should be displayed with "Duplicate name error" Popup


@ViewLeakDetectorconfigurationOptions
#Precondition: User has set Heating System, Heating Stages, Cooling Stages in Thermostat
Scenario: As a user I want to View my thermostat Configuration details
Given user launches and logs in to the Lyric application 
And user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
Then user should be displayed with "Leak Detector Name"
And user should be displayed with "Firmware Details"
And user should be displayed with "Delete Leak Detector"

@DeleteLeakDetector
Scenario: As a user I should be able to delete my Japer NA device from my account through the Lyric application 
Given user launches and logs in to the Lyric application 
When user navigates to "Leak Detector Configuration" screen from the "Dashboard" screen 
And user "Deletes Leak Detector" by clicking on "Delete" button
And user should receive a "Delete Device Confirmation" popup
Then user "Dismisses" the "Delete Device Confirmation" popup
When user "Deletes Leak Detector" by clicking on "Delete" button
And user should receive a "Delete Device Confirmation" popup
And user "Accepts" the "Delete Device Confirmation" popup
Then WLD should be deleted
And user should be displayed with "Dashboard" With "Add new Device" Option



 