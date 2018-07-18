@JasperNAEMEAHBBSettings 
Feature: DAS Settings 
  As user I should be able to control my Japer NA settings from the app


#JasperNA, 
@VerifyJasperNASettings		@P1		@UIAutomatable		@--xrayid:ATER-44516
Scenario: As a user I want to verify that all JasperNA Settings options are available to me 
Given user launches and logs in to the Lyric application 
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen 
Then user should be displayed with the following "Thermostat Settings" options: 
      | ThermostatSettings			|
      | Manage Alerts				|
      | Set Filter Reminder			|
      |	Set up HomeKit & Siri		|
      | Adaptive Recovery			|	
      | Set filter reminder			|
      |	Set Up Homekit & Siri		|
      | Adaptive recovery			|	
      | Ventilation					|
      | Emergency heat				| 
      | Auto changeover				|
      | Auto chnageover				|
      | Reset Wi-Fi					| 
      | Thermostat Configuration 	| 

#JasperEMEA
@VerifyJasperNAEMEASpruceSettings		@P1		@UIAutomatable		@--xrayid:ATER-44517
Scenario: As a user I want to verify that all JasperNA Settings options are available to me 
Given user launches and logs in to the Lyric application 
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen 
Then user should be displayed with the following "Thermostat Settings" options: 
      | ThermostatSettings			|
      | Manage Alerts				|
      | Set Filter Reminder			|
      |	Set up HomeKit & Siri		|
      | Optimise						|
      | Set filter reminder			|
      |	Set Up Homekit & Siri		|
      | Optimizer					|
      | Emergency heat				|
      | Reset Wi-Fi					| 
      | Thermostat Configuration 	| 


#HB_Spruce
@VerifyHBBSettings		@P1		@UIAutomatable		@--xrayid:ATER-44518
Scenario: As a user I want to verify that all HBB Settings options are available to me 
Given user launches and logs in to the Lyric application 
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen 
Then user should be displayed with the following "Thermostat Settings" options: 
      | ThermostatSettings			|
      | Manage Alerts				|
      | Fine Tune					|
      | Set Filter Reminder			|
      | Set filter reminder			|
      | Frost Protection Mode		|
      | Humidification				|
      | Dehumidification				|
      | Adaptive Recovery			|	
      | Adaptive recovery			|	
      | Ventilation					|
      | Emergency heat				| 
      | Auto changeover				|
      | Auto chnageover				|
      | Sleep Brightness Mode		|
      | Sound						|
      | Reset Wi-Fi					| 
      | Thermostat Configuration 	|

#Manage Alerts 

#JasperNA, JasperEMEA, HB_Spruce
@EnableDisableIndoorTemperatureAlert		@P1		@UIAutomatable		@--xrayid:ATER-44519
Scenario: As a user I should be able to enable or disable Indoor Temperature Alert on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alert" to "ON"
Then "Indoor Temperature Alert" value should be updated to "ON" on "Manage Alerts" screen
And user should be displayed with the following "Indoor Temperature Alert" options:
	| IndoorTempAlertOptions			| 
	| Alert for this range			|
When user changes the "Indoor Temperature Alert" to "OFF"
Then "Indoor Temperature Alert" value should be updated to "OFF" on "Manage Alerts" screen
      
 
#InvalidScenario
#JasperNA, JasperEMEA, HB_Spruce     
@EnableDisableEmailForEnabledAlerts		@P3		@UIAutomatable
Scenario: As a user I should be able to enable or disable Email For Enabled Alerts on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Temperature Alert" to "ON"
And user changes the "Email For Enabled Alerts" to "ON"
Then "Email For Enabled Alerts" value should be updated to "ON" on "Manage Alerts" screen
When user changes the "Indoor Temperature Alert" to "OFF"
Then "Indoor Temperature Alert" value should be updated to "OFF" on "Manage Alerts" screen

#JasperNA, JasperEMEA, HB_Spruce   
@ChangeAlertForThisRangeforTemperature		@P1		@UIAutomatable		@--xrayid:ATER-44520
Scenario: As a user I should be able to change Alert For This Range on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
Then user changes the "Indoor Temperature Alert" to "ON"
And "Indoor Temperature Alert" value should be updated to "ON" on "Manage Alerts" screen
And user should be displayed with the following "Indoor Temperature Alert" options:
	| IndoorTempAlertOptions			| 
	| Alert for this range			|
When user selects "Alert for this range" from "Manage Alerts" screen
Then user should be displayed with the following "Alert for this range" options:
	| AlertTempRangeOptions		| 
	| Below 						| 
	| Above						|
And user taps on "Below Range"
Then user should be able to select "Below Temperature Range"
And user receives a "Below Temperature Range" push notification
And user taps on "Above Range"
Then user should be able to select "Above Temperature Range"
And user receives a "Above Temperature Range" push notification
And user should be displayed with "Indoor Temperature" message on "Activity History" history screen


#InvalidScenario
#HB_Srpuce
@HBBEnableDisableEmailForEnabledAlertsforHumidity		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Email For Enabled Alerts for Humidity on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
And user changes the "Email For Enabled Alerts" to "ON"
Then "Email For Enabled Alerts" value should be updated to "ON" on "Manage Alerts" screen
When user changes the "Email For Enabled Alerts" to "OFF"
Then "Email For Enabled Alerts" value should be updated to "OFF" on "Manage Alerts" screen



#HB_Srpuce
@HBBEnableDisableIndoorHumidityAlert		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Indoor Humidity Alert on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
Then "Indoor Humidity Alert" value should be updated to "ON" on "Thermostat Settings" screen
And following "Indoor Humidity Alert" option should be displayed
When user changes the "Indoor Humidity Alert" to "OFF"
Then "Indoor Humidity Alert" value should be updated to "OFF" on "Manage Alerts" screen
	  | Options						| 
      | Email for enabled alerts		| 
      | Alert for this range			|

#HB_Srpuce
@ChangeAlertForThisRangeforHumidity		@P1		@UIAutomatable		@--xrayid:ATER-44521
Scenario: As a user I should be able to change Alert For This Range on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Manage Alerts" screen from the "Dashboard" screen
And user changes the "Indoor Humidity Alert" to "ON"
And user clicks on " Alert for this Range" option
Then user should be displayed with Following Options
And when user Clicks on "Below Range"
Then user should be able to select "Below Humidity Range"
And user should be able to receive push notification for below range
And when user Clicks on "Above Range"
And user should be displayed with "Indoor Humidity" message on "Activity History" history screen

| Options	| 
| Below 		| 
| Above		|


#Set Filter Reminder 

#JasperNA, HB-Spruce
@EnableDisableSetFilterReminder		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Set Filter Reminder option on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Set Filter Reminder" screen from the "Dashboard" screen
And user changes the "Set Filter Reminder" to "ON"
Then "Set Filter Reminder" value should be updated to "ON" on "Set Filter Reminder" screen
When user changes the "Set Filter Reminder" to  "OFF"
Then "Set Filter Reminder" value should be updated to "OFF" on "Set Filter Reminder" screen
     
@VerifySetFilterReminderOptions		@P2		@UIAutomatable
Scenario: As a user I should be able to view options for Set Filter Reminder
Given user launches and logs in to the Lyric application
When user navigates to "Set Filter Reminder" screen from the "Dashboard" screen
And user changes the "Set Filter Reminder" to "ON"
Then the following "Set Filter Reminder" options should be disabled
		| Options   					| 
	    |Replace Filter Every 		| 
    		|Filter Last Replaced  		| 
      	|Next Scheduled Reminder 	| 
 

 #JasperNA, HB-Spruce
@ChangeReplacefilterandFilterLastReplacedOptions		@P2		@NotAutomatable
Scenario: As a user I should be able to set options Replace filter and Filter Last Replaced
Given user launches and logs in to the Lyric application
When user navigates to "Set Filter Reminder" screen from the "Dashboard" screen
And user changes the "Set Filter Reminder" to "ON"
And user selects <Replace filter value as month> from "Set Filter Reminder" screen
Then "Replace Filter Every" value should be updated to <replace filter Every value> on "Set Filter Reminder" screen
When user selects "Filter Last Replaced " from "set filter reminder" screen
Then user should be able to "Selecet new date"
And user should be able to "View Next scheduled Reminder"
And user should be able to receive push notification for filter reminder once
|7 month|
|8 month|
|9 month|
|10 month|
|11month|


#Homekit -- only for iOS , spruce , NA, EMEA

@SetupHomekitHB		@P2		@UIAutomatable
 Scenario: As a user i should verify Homekit option should not display for HB
 Given user launches and logs in to the Lyric application 
 When user navigates to "HB settings" Screen
Then user should not display "Setup Homekit & Siri" option 


#JasperNA, JasperEMEA, HB_Spruce
@SetupHomekitAndSiriiOS		@P2		@NotAutomatable
 Scenario Outline: As a user i should be able to set homekit and siri using Custom location.
 Given user launches and logs in to the Lyric application 
 When user navigates to "Setup Homekit & Siri" screen from the "Dashboard" screen 
 Then user should be displayed with the "Looking for Device" Spinner
 And user should be displayed with the "Identify Thermostat" Spinner
 When user clicks on "Identify Thermostat" option
 Then user should be displayed with "LCD blinking" on the device
 When user navigates to "Find Homekit Setup Guide" screen from the "Identify Thermostat" screen
 And user click son "Next"
 Then user should be displayed with the " Connecting to device" Spinner
 And user should be displayed with the "Thermostat Room" Screen
 When user inputs <Custom Location> in "Custom Field"
 Then user should be displayed with the " Thermostat Configuration" Screen
 And user should be displayed with the "Identify" Option 
 
 Examples:
 		|Custom Location		|
      	| My Room			|
        
 #JasperNA, JasperEMEA, HB_Spruce
 @SetupHomekitAndSiriiOS		@P1		@NotAutomatable
 Scenario Outline: As a user i should be able to set homekit and siri using default location.
 Given user launches and logs in to the Lyric application 
 When user navigates to "Setup Homekit & Siri" screen from the "Dashboard" screen 
 Then user should be displayed with the "Looking for Device" Spinner
 And user should be displayed with the "Identify Thermostat" Spinner
 When user clicks on "Identify Thermostat" option
 Then user should be displayed with "LCD blinking" on the device
 When user navigates to "Find Homekit Setup Guide" screen from the "Identify Thermostat" screen
 And user click son "Next"
 Then user should be displayed with the " Connecting to device" Spinner
 Examples:
 		|Default Location	|
      	| Default Room		|
     	| Basement			|
      	| Bedroom			| 
      	| Guest Bedroom		|	
      	| Hallway			|
      	| Living Room 		|

#Add HomeKit error pop up 
@Homekitpopupverification		@P2		@NotAutomatable
 Scenario: As a user i should be able to get homekit error pop up when my device is unavailable.
 Given user launches and logs in to the Lyric application 
 When user navigates to "Setup Homekit & Siri" screen from the "Dashboard" screen 
 Then user should be displayed with the "Looking for Device" Spinner
 And user should be displayed with the "Identify Thermostat" Spinner
 When user navigates to "Find Homekit Setup Guide" screen from the "Identify Themostat" screen
 And user doesn't receives any response from the device
 Then user should be displayed with the "Error" Popup
 Then user should be displayed with the " Error" Popup
When user taps on "Learn how to setup homekit"
 Then user should be displayed with "Homekit Web portal"
 And when user navigates back to lyric app
 When user taps on "Try Again"
 Then user should be displayed with the "Looking for Device" Spinner 
 And user should be displayed with the "Identify Thermostat" Spinner
 When user clicks on "Identify Themostat" option
  And user doesn't receives any response from the device
 Then user should be displayed with the " Error" Popup
 When user taps on "Cancel"
 Then user should be displayed with the "Setup Homekit & Siri" screen


#LYR-24321
#HomeKitMultilocationaccuont

# Comfort settings 

#HB_Spruce
@HBBEnableDisableFineTune		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Fine Tune option on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Fine Tune" to "ON"
Then "Fine Tune" value should be updated to "ON" on "Set Filter Reminder" screen
When user changes the "Fine Tune" to  "OFF"
Then "Fine Tune" value should be updated to "OFF" on "Set Filter Reminder" screen 

#HB_Spruce, JasperNA
@EnableDisableAdaptiverecovery		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Adaptive recovery on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Adaptive Recovery" to "ON"
Then "Adaptive Recovery" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "Adaptive Recovery" to "OFF"
Then "Adaptive Recovery" value should be updated to "OFF" on "Thermostat Settings" screen

#JasperEMEA—optimise
@EnableDisableAOptimiseValue		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable optimise value on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Optimise" to "ON"
Then "Optimise" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "Optimise" to "OFF"
Then "Optimise" value should be updated to "OFF" on "Thermostat Settings" screen

#JasperNA, HB_Spruce
@EnableDisableEmergencyHeat		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Emergency Heat on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Emergency Heat" to "ON"
Then "Emergency Heat" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "Emergency Heat" to "OFF"
Then "Emergency Heat" value should be updated to "OFF" on "Thermostat Settings" screen

#JasperNA, HB_Spruce
@Autochangeoverwhenheatonlyoracoolonlyenabled		@P2		@UIAutomatable
Scenario Outline: As a user I should not be able view Auto changeover when heatvonly or coolvonly enabled on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user should not be displayed with "Auto Changeover" when <Modes> Are enabled in device

Examples:
|Modes		|
|Heat Only	|
|Cool Only	|

#JasperNA, HB_Spruce
@EmergencyHeatwhencoolonlyenabled		@P2		@UIAutomatable
Scenario Outline: As a user I should not be able view Auto changeover when cool only enabled on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user should not be displayed with "Emergency Heat" when <Mode> is enabled in device
And user should not be displayed with "EmergencyHeat" when <Mode> is enabled in device

Examples:
|Modes		|
|Cool Only	|

#JasperNA, HB_Spruce
@EmergencyHeatwhenHeatonlyenabled		@P2		@UIAutomatable
Scenario Outline: As a user I should not be able view Auto changeover when Heat only enabled on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user should be displayed with "Emergency Heat" when <Mode> is enabled in device
And user should be displayed with "EmergencyHeat" when <Mode> is enabled in device

Examples:
|Modes		|
|Heat Only	|

#HB_Spruce
@HBBSpruceEnableFrostProtectionMode		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Frost Protection Mode on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Frost Protection Mode" to "ON"
Then "Frost Protection Mode" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "Frost Protection Mode" to "OFF"
Then "Frost Protection Mode" value should be updated to "OFF" on "Thermostat Settings" screen
#Hardware settings 


#HB_Spruce
@HBBSpruceEnableHumidification		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable Humidification on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "Humidification" to "ON"
Then "Humidification" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "Humidification" to "OFF"
Then "Humidification" value should be updated to "OFF" on "Thermostat Settings" screen

#HB_Spruce
@HBBSpruceEnableDeHumidification		@P2		@UIAutomatable
Scenario: As a user I should be able to enable or disable DeHumidification on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes the "DeHumidification" to "ON"
Then "DeHumidification" value should be updated to "ON" on "Thermostat Settings" screen
When user changes the "DeHumidification" to "OFF"
Then "DeHumidification" value should be updated to "OFF" on "Thermostat Settings" screen


#HB_Spruce
@HBBIncreaseDecreaseSleepBrightnessMode 		@P2		@UIAutomatable 
Scenario: As a user I should be able to increase or decrease Sleep Brightness Mode option on my thermostat
Given user launches and logs in to the Lyric application
When user navigates to "Sleep Brightness Mode" screen from the "Dashboard" screen
And user changes the "Sleep Brightness Mode" to "50%"
Then "Sleep Brightness Mode" value should be updated to "50%" on "Sleep Brightness Mode" screen
And "Sleep Brightness Mode" value should be updated to "50%" on "Thermostat Settings" screen
When user changes the "Sleep Brightness Mode" to  "OFF"
Then "Sleep Brightness Mode" value should be updated to "OFF" on "Sleep Brightness Mode" screen  
And "Sleep Brightness Mode" value should be updated to "OFF" on "Thermostat Settings" screen

#HB_Spruce
@HBBverifySoundOptions		@P2		@UIAutomatable
Scenario: As a user I should be able to view options for Sound
Given user launches and logs in to the Lyric application
When user navigates to "Sound" screen from the "Dashboard" screen
Then the following "Sound" options should be displayed
		| Options	| 
	    | Off 		| 
    		| Low   		| 
      	| Normal  	| 

#HB_Spruce	  
 @HBBchangeSoundsettings		@P2		@UIAutomatable
 Scenario: As a user I should be able to set options for Sound
 Given user launches and logs in to the Lyric application
 When user navigates to "Sound" screen from the "Dashboard" screen
 And user changes the "Sound" to "OFF"
 And user navigates to "Thermostat Settings" Screen from "Sound" Screen
 Then "Sound" value should be updated to "OFF" on "Thermostat Settings" screen 
 When user navigates to "Sound" screen from the "Dashboard" screen
 And user changes the "Sound" to "Low"
 And user navigates to "Thermostat Settings" Screen from "Sound" Screen
 Then "Sound" value should be updated to "Low" on "Thermostat Settings" screen 
 When user navigates to "Sound" screen from the "Dashboard" screen
 And user changes the "Sound" to "Normal"
 And user navigates to "Thermostat Settings" Screen from "Sound" Screen
 Then "Sound" value should be updated to "Normal" on "Thermostat Settings" screen

#Rest wi-fi


#JasperNA, JAsperEMEA
@ResetWiFiNAEMEA		@P2		@NotAutomatable
Scenario: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with" PIN Screen"
      | open                | abcd      | none          | 
      | WEP_SHARED          | abcd      | abcd          | 
      | WPA_PERSONAL_TKIP   | abcd      | abcd          | 
      | WPA_PERSONAL_AES    | abcd      | abcd          | 
      | WPA2_PERSONAL_AES   | abcd      | abcd          | 
      | WPA2_PERSONAL_TKIP  | abcd      | abcd          | 
      | WPA2_PERSONAL_MIXED | abcd      | abcd          | 
      | WPA2                | abcd      | abcd          | 

#JasperNA, JAsperEMEA
@ResetWiFiByAddingNetworkNAEMEA		@P2		@NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with" PIN Screen"
Then user should be displayed with "Select WiFi" screen
When user selects <WiFi SSID> from the available WiFi list
And user inputs <WiFi Password> as the WiFi Password
Then user "wifi name" should be updated to <WiFi SSID> in the "Thermostat WiFi" screen.

Examples: 
      | WiFi SSID | WiFi Password | 
      | abcd      | abcd          | 
 
#JasperNA, JAsperEMEA
@ResetWiFiIncorrectPasswordNAEMEA	@P2		@NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with" PIN Screen"
When user enters the Proper PIN
Then user should be displayed with "Select WiFi" screen
And user selects <WiFi SSID> from the available WiFi list
And user inputs <Incorrect WiFi Password> as the WiFi Password
Then user should receive a "Incorrect WiFi Password" pop up

Examples: 
      | WiFi SSID | Incorrect WiFi Password | 
      | abcd      | abcd                    | 

@ResetWiFiHB			@P2		@NotAutomatable
Scenario: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with "Select WiFi" screen
      | open                | abcd      | none          | 
      | WEP_SHARED          | abcd      | abcd          | 
      | WPA_PERSONAL_TKIP   | abcd      | abcd          | 
      | WPA_PERSONAL_AES    | abcd      | abcd          | 
      | WPA2_PERSONAL_AES   | abcd      | abcd          | 
      | WPA2_PERSONAL_TKIP  | abcd      | abcd          | 
      | WPA2_PERSONAL_MIXED | abcd      | abcd          | 
      | WPA2                | abcd      | abcd          | 


@ResetWiFiByAddingNetworkHB			@P2		@NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with "Select WiFi" screen
When user selects <WiFi SSID> from the available WiFi list
And user inputs <WiFi Password> as the WiFi Password
Then user "wifi name" should be updated to <WiFi SSID> in the "Thermostat WiFi" screen.

Examples: 
      | WiFi SSID | WiFi Password | 
      | abcd      | abcd          | 


@ResetWiFiIncorrectPasswordHB		@P2		@NotAutomatable
Scenario Outline: As a user I want to reset my DAS Panel WiFi 
Given user launches and logs in to the Lyric application 
And user navigates to "Base Station WiFi" screen from the "Dashboard" screen 
When user "resets WiFi" by clicking on "Reset WiFi" button  
Then user should be displayed with the "Reset WiFi Instructions" screen
When user presses the center temperature for 5 seconds on the Thermostat
Then the Thermostat should start wifi network
When user navigates to the "Select WiFi" screen from the "Reset WiFi Instruction" screen
And user connects to thermostat network
Then user should be displayed with "Select WiFi" screen
Examples: 
      | WiFi SSID | Incorrect WiFi Password | 
      | abcd      | abcd                    | 




#Thermostat Configuration 

#JasperNA, JAsperEMEA, HB_Spruce
@RenameJasperNAEMEAHBdevice			@P2			@UIAutomatable
Scenario: As a user I want to rename my thermostat through the application 
Given user launches and logs in to the Lyric application 
And user navigates to "Thermostat Configuration" screen from the "Dashboard" screen 
When user edits the "Thermostat" name to "Test Thermostat Name" 
And user navigates to "Dashboard" screen from the "Thermostat Configuration" screen 
Then user should be displayed with "Test Thermostat Name" device on the "dashboard" screen 
And user reverts back the "Thermostat device name" through CHIL

#JasperNA, JAsperEMEA, HB_Spruce
@RenameThermostatwithDuplicatename			@P3			@UIAutomatable
Scenario: As a user I want to get a error message when i eneter a duplicate name for my thermostat
Given user launches and logs in to the Lyric application 
And user navigates to "Thermostat Configuration" screen from the "Dashboard" screen 
When user edits the "Thermostat 1" name to "Test Thermostat Name" 
And user navigates to "Dashboard" screen from the "Thermostat Configuration" screen 
Then user should be displayed with "Test Thermostat Name" device on the "dashboard" screen
When user navigates to "Thermostat Configuration" screen from the "Dashboard" screen 
And user edits the "Thermostat 1" name to "Test Thermostat Name" 
Then user should be displayed with "Duplicate name error" Popup


 #JasperNA, JAsperEMEA, HB_Spruce
@ViewThermostatconfigurationOptions			@P2			@UIAutomatable
#Precondition: User has set Heating System, Heating Stages, Cooling Stages in Thermostat
Scenario: As a user I want to View my thermostat Configuration details
Given user launches and logs in to the Lyric application 
And user navigates to "Thermostat Configuration" screen from the "Dashboard" screen 
Then user should be displayed with "Thermostat Name"
And user should be displayed with "Firmware Details"
And user should be displayed with "Heating System"
And user should be displayed with "Heating Stages"
And user should be displayed with "Cooling Stages"
 

#JasperNA, JAsperEMEA, HB_Spruce
@DismissDeletePopupJasperNAEMEAHB			@P3			@UIAutomatable
Scenario: As a user I should be able to dismiss the delete poup for my Japer NA device from my account through the Lyric application
Given user launches and logs in to the Lyric application 
When user navigates to "Thermostat Configuration" screen from the "Dashboard" screen 
And user "deletes thermostat" by clicking on "delete" button
Then user should receive a "Delete Device Confirmation" popup
And user "dismisses" the "Delete Device Confirmation" popup


#Offline

#JasperNA
@JasperNASettingsDisabled			@P2		@NotAutomatable
Scenario: As a user I should not be allowed to change jasper settings when I am offline 
Given user device is offline
      And user launches and logs in to the Lyric application 
      And user navigates to "Thermostat Settings" screen from the "Dashboard" screen
     Then the following "Thermostat Settings" options should be disabled:
      | Options				| 
      | Adaptive recovery	|	
      | Ventilation			|
      | Emergency heat		| 
      | Auto chnageover		|

#HB_Spruce
@HBBSettingsDisabled			@P2		@NotAutomatable
Scenario: As a user I should not be allowed to change HBB settings when I am offline 
Given user device is offline
And user launches and logs in to the Lyric application 
And user navigates to "Thermostat Settings" screen from the "Dashboard" screen
Then the following "Thermostat Settings" options should be disabled:
      | Options					| 
      | Adaptive recovery		|	
      | Ventilation				|
      | Emergency heat			| 
      | Auto chnageover			|
      | Fine tune				|
      | Frost Protection Mode	|
      | Humidification			|
      | Dehumdification			|
      | Sleep Brightness Mode	|
      | Sound					|

#JasperEMEA
@JasperEmeaSettingsDisabled			@P2		@NotAutomatable
Scenario: As a user I should not be allowed to change Jasper EMEA settings when I am offline 
Given user device is offline
And user launches and logs in to the Lyric application 
And user navigates to "Thermostat Settings" screen from the "Dashboard" screen
Then the following "Thermostat Settings" options should be disabled:
      |  Options			| 
      | Optimise			|	
      | Ventilation		|
     

#JasperNA, JasperEMEA, HB_Spruce  
@JasperSettingsEnabledwhenOffline 		@P2		@NotAutomatable
Scenario: As a user I should be allowed to change Thermostat common settings when I am offline 
Given user device is offline
And user launches and logs in to the Lyric application 
And user navigates to "Thermostat Settings" screen from the "Dashboard" screen
Then the following "Thermostat Settings" options should be Enabled:
      | Options					| 
      | Manage Alerts			|	
      | Reset WIFI				|
      | Thermostat Configuration| 

#JaseprNA, HB_Spruce
@TogglebetweenEmergencyheatandAutoChangeOver			@P2			@UIAutomatable
Scenario: As a user I should be able to Toggle between Emergency heat and Auto ChangeOver
Given user launches and logs in to the Lyric application
When user navigates to "Thermostat Settings" screen from the "Dashboard" screen
And user changes has "Emergency heat" Enabled
Then user should be displyed with "Auto Change Over disabled"
When user changes has "Auto Change Over" to "Enabled"
When user changes has "Auto Change Over" to  "Enabled"
Then user should be displyed with "Emergency heat disabled" 