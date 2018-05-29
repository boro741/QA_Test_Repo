@DAS_AccessSensor
Feature: DAS Access Sensor
As a user I want to check the functioning of Access sensor 


@DASAccessSensorStatusVerification @UIAutomatedAndroid
  Scenario: As a user I want to view that all Access Sensor settings
  Given user is set to <Mode> mode through CHIL
    Given user launches and logs in to the Lyric application 
     When user navigates to "Door Access settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following "Sensor Settings" options: 
      | Settings                   |
      | Name                       | 
      |Status 	                   | 
      | Battery                    | 
      | Signal Strength and Test   |
      | Model and Firmware Details |
      | DELETE                     |


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorRenameVerification @UIAutomatedAndroid
Scenario Outline: As a user I want to rename my Access Sensor sensor through the application
And user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to "Door Access settings" screen from the "Dashboard" screen
Then user edits the "Access Sensor" name to "new name"
#And user reverts back the "Access Sensor" Sensor name through CHIL
Examples:
|Mode|
|Home|
|OFF|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorRenamePopUpVerification @UIAutomatedAndroid
Scenario Outline: As a user I cannot rename my Access Sensor sensor through the application when its in 
Given user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to "Door Access settings" screen from the "Dashboard" screen 
Then the following "sensor settings" options should be disabled:
|Options|
|Name field|
#Then user should display the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Mode|
|Night|
|Away|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorSignalStrengthAndTestVerificationWithAwayNightOffline @UIAutomatedAndroid
Scenario Outline: As a user I want to Verify Access Sensor SignalStrengthAndTest in Away, Night and offline mode
Given user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to "Door Access settings" screen from the "Dashboard" screen 
Then the following "sensor settings" options should be disabled:
|Options|
|Signal strength and test|
#Then user should display the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Mode|
|Away|
|Night|

#Requirement :One DAS Panel and one Access Sensor should be configured
@DASDeleteAccessSensorPopupVerification @UIAutomatedAndroid
Scenario Outline: As a user I should be able to delete Access Sensor configured to my DAS panel from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL 
When user navigates to "Door Access settings" screen from the "Dashboard" screen 
#Then user should be displayed the "you can perform this action only in Home or Off mode" pop up 
Then the following "sensor settings" options should be disabled:
|Options|
|Delete|
Examples:
|Mode|
|Night|
|Away|


#Requirement: One DAS Panel and one Access Sensor should be configured

@DASAccessSensorStatusOFFVerificationWithHomeOffMode
Scenario Outline: As a user I want to Verify Access Sensor Battery status OFF 
#Given user is set to "Home" through CHIL
And user launches and logs in to the Lyric application
And user navigates to "Security Solution card" screen from the "Dashboard" screen 
When user switches from "Home" to <Switch to Mode>
Then user navigates to "Door Access settings" screen from the "Security Solution card" screen
Then user should see the "door sensor" status as <Sensor status> on the "Access Sensor Settings"
And user navigates to "Security Solution card" screen from the "Access Sensor Settings" screen 
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the "door" status as <Sensor status> on the "Sensor Status"
Examples:
|Switch to Mode | Sensor status|
#|Home|Open|
|Off| Open|

#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorStatusOFFVerificationWithAwayNightMode
Scenario Outline:AS a user I want to Verify Access Sensor Battery status OFF 
#And user is set to "Home" through CHIL
Given user launches and logs in to the Lyric application 
And user navigates to "Security Solution card" screen from the "Dashboard" screen 
When user switches from "Home" to <Mode>
Then user should receive a <Switch to Mode> popup
When user "accepts" the <Switch to Mode> popup
Then user navigates to "Door Access settings" screen from the "Security Solution card" screen
Then user should see the "door sensor" status as <Sensor status> on the "Access Sensor Settings"
Then user navigates to "Access Sensor OFF" screen from the "Access sensor settings" screen
And user navigates to "Security Solution card" screen from the "Access Sensor Settings" screen 
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the "door" status as "off" on the "Sensor Status"
Examples:
|Mode|Switch to Mode | Sensor status|
|Night|Switch to Night |Off|
|Away|Switch to Away |Off|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorSignalStrengthAndTestVerificationGetAdditionlHelp
Scenario Outline: As a user I want to Verify Access Sensor SignalStrengthAndTest in Home or OFF mode and assistance link
Given user launches and logs in to the Lyric application 
And user door "opened"
And user is set to <Mode> mode through CHIL
Then user navigates to "Door Access settings" screen from the "Dashboard" screen
When user selects "Signal Strength and Test" from "Door Access settings" screen
Then user should be displayed with the "Test Access Sensor" screen
Then user should see the "door sensor" status as <Access Status> on the "Test Access Sensor"
When user selects "Sensor Not Working" from "Test Access Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
And user should be displayed with the "Get Additional Help on Access Sensor Help" screen
When user selects "Get Additional Help" from "Access Sensor Help" screen
#Then user should be displayed with the "Honeywell help web portal" screen
And user navigates to "Access Sensor Help" screen from the "Honeywell Help web portal" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with the "Signal Strength" screen
Then user should see the "door sensor" status as <Signal strength> on the "Signal Strength"
And user navigates to "Access Sensor Help" screen from the "Test Signal Strength" screen
Then user navigates to "Test Access Sensor" screen from the "Access Sensor Help" screen
Then user door "closed"
Then user should see the "door sensor" status as <Access Status Update> on the "Test Access Sensor"
Then user navigates to "Access sensor Settings" screen from the "Test Access Sensor" screen
#Then user should see the "door sensor" status as <Access Status Update> on the "Access sensor Settings"
Examples:
|Mode |Signal strength | Access Status | Access Status Update |
|Home | High |  Open | Closed |
#|Home | Medium |Opened | Closed |
#|Home | Low |Opened | Closed |
|OFF | High | Opened | Closed |
#| OFF | Medium | Opened | Closed |
#| OFF | Low |Opened | Closed |

#Requirement :One DAS Panel and one Access Sensor should be configured and Access Sensor status cover tampered status
@DASAccessSensorStatusCoverTamperedVerificationHome
Scenario Outline:AS a user I want to Verify Access Sensor Cover Tamper and Tamper restored status When base station status in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> mode through CHIL
And user door <Status>
And user door "tampered"
Then user navigates to "Door Access settings" screen from the "Dashboard" screen
When user selects "Sensor Cover Tampered" from "Door Access settings" screen
Then user should be displayed with the "Sensor Cover Tamper" screen
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
Then user should receive a "Sensor Tamper" popup 
When user "OK" the "Sensor Tamper" popup 
Then user should be displayed with the "Sensor Cover Tamper" screen
When user selects "Clear Tamper" from "Sensor Cover Tamper" screen
And user should receive a "Sensor Tamper" popup 
When user "RETRY" the "Sensor Tamper" popup
Then user should be displayed with the "Sensor Cover Tamper" screen
And user selects "Clear Tamper" from "Sensor Cover Tamper" screen
Then user should receive a "Sensor Tamper" popup 
And user door "tamper cleared"
When user "RETRY" the "Sensor Tamper" popup
#Then user should navigates to "Access sensor Settings" screen
And user should see the "door sensor" status as <Status> on the "Access sensor Settings"
Examples:
|Mode| Status |
|Home| Closed |
#|Home| Open |

#Requirement :One DAS Panel and one Access Sensor should be configured and Access Sensor  should be in offline
@DASAccessSensorStatusVerificationoffline
  Scenario: As a user I want to view that all door access sensor settings 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Door Access settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following <Settings> options: 
      | Settings           |
	#Disabled
      | Name               |  
      | Status 		   |
      | Battery            | 
      | Signal Strength and Test |
	#Enabled
      | Model and Firmware Details |
      | DELETE|

#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorSensorStatusVerificationWithHomeAwayNightOffMode
Scenario Outline:AS a user I want to Verify Access Sensor Sensor status
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Access Sensor settings" screen from the "Dashboard"
And user should display the status <Sensor Status>
Examples:
|Mode| Sensor Status |
|Home| Closed |
|Home| Open |
|Home| Low battery|
|Home| OFF |
|Home|Offline| 
|Home| Cover Tampered|
|Night| Closed |
|Night| Open |
|Night| Low battery|
| Night |Offline|
|Night | Cover Tampered|
|Away| OFF |
|Away| Closed |
|Away| Open |
|Away| Low battery|
|Away|OFF |
| Away |Offline|
|Away |Cover Tampered|
|Away| OFF |
|OFF| Closed |
|OFF| Open |
|OFF| Low battery|
|OFF|OFF |
|OFF |Cover Tampered|
| OFF |Offline|






#Requirement :One DAS Panel and one Access Sensor should be configured and Access Sensor status cover tampered status
@DASAccessSensorStatusCoverTamperedVerificationNightOffoffline
Scenario Outline:AS a user I want to Verify Access Sensor Cover Taper status When base station status in Away, Night, OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
#Access Sensor back plate not closed
Then user navigates to "Door Access settings" screen from the "Dashboard"
When user selects "Status Covered Tampered" 
Then user should display the "you can perform this action only in Home mode" pop up 
Examples:
|Mode|
|Night|
|OFF|
|Offline|

#Requirement :One DAS Panel and one Access Sensor should be configured and Access Sensor status cover tampered status
@DASAccessSensorStatusCoverTamperedVerificationHomeawaynightoff
Scenario Outline:AS a user I want to Verify Access Sensor Cover Taper status When base station status in Away, Night, OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
#Access Sensor back plate not closed
And user set to "Sensor" "Offline"
Then user navigates to "Door Access settings" screen from the "Dashboard"
Then user should not display "Cleared Tampered" option 
Examples:
|Mode|
|Home|
|Away|
|Night|
|OFF|



#Requirement :One DAS Panel and one Access Sensor should be configured and Access Sensor status cover tampered status
@DASAccessSensorStatusCoverTamperedVerificationOffline
Scenario Outline: AS a user I want to Verify Access Sensor Cover Taper status When base station status in offline
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
#Access Sensor back plate not closed
Then user navigates to "Door Access settings" screen from the "Dashboard"
When user selects "Status Covered Tampered" 
Then user should navigates to "Access Sensor Cover tampered" screen
And user should not display the "Cover Tampered" button
Then user should display the "you can perform this action only in Home or Off mode" pop up 
When user selects the "BACK" button 
Then user should be displayed with "Motion Viewer settins" screen
Examples:
|Mode|
|Offline|

#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorBatteryStatusVerificationWithHomeAwayNightOffOfflineMode
Scenario Outline: AS a user I want to Verify Access Sensor Battery status
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Door Access settings" screen from the "Dashboard"
And user should display the Battery status <Battery Status>
Examples:
|Mode| Battery Status |
|Home| Good |
|Home| OFFLINE|
|Home| Low|
|Home| OFF |
|Night| Good |
|Night| OFFLINE |
|Night| Low|
|Away| OFF |
|Away| Good |
|Away| OFFLINE |
|Away| Low|
|Away| OFF |

#Requirement :One DAS Panel and one Access Sensor should be configured and battery status should be in Low and OFFLINE
@DASAccessSensorBatteryLowofflineStatusLowHelpScreenVerification @NotAutomatable
Scenario Outline: AS a user I want to Verify Access Sensor Low and Offline Battery Help screen
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Access Sensor settings" screen from the "Dashboard"
When user selects the "Battery status" button
Then user should be displayed with "Low_Offline Battery Help" Screen
When user selects "BACK" button
Then user should be displayed with "Access Sensor settings" screen
Examples:
|Mode|
|Home|
|Away|
|Night|
|OFF|
|OFFLINE|





#Requirement :One DAS Panel and one Access Sensor should be configured
@DASAccessSensorSignalStrengthWithSensorNotWorkingAndIsOutOfRange @NotAutomatable
Scenario Outline: As a user I should be able to verify Access Sensor not working functionality when sensor is out of range in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Door Access settings" screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Access Sensor" screen
When user selects "Viewer Not Working?" from "Test Access Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with "Signal Strength" screen 
Then user should receive a "Out Of Range" popup
And user "taps on RETRY in" the "Out Of Range" popup
Then user should receive a "Out Of Range" pop up
And user "taps on OK in" the "Out Of Range" popup
Then user should be displayed with the "Access Sensor Help" screen
And user navigates to "Test Access Sensor" screen from the "Test Signal Strength" screen
Examples:
|Mode|
|Home|
|OFF|




#Requirement :One DAS Panel and one Access Sensor should be configured and sensor status should be offline
@DASAccessSensorSignalStrengthWhenSensorOffline @NotAutomatable
Scenario Outline: As a user I should be able to verify Access Sensor Signal strength functionality when sensor offline
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Door Access settings" screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Sensor is offline" pop up
Examples:
|Mode|
|Home|
|OFF|


 


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASDeleteAccessSensorVerification
Scenario Outline: As a user I should be able to delete Access Sensor configured to my DAS panel from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to "Door Access settings" screen from the "Dashboard" screen 
And user "deletes Access Sensor" by clicking on "delete" button
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
#And user "accepts" the "Delete Sensor Confirmation" popup
#Then user should not be displayed with "Access Sensor" on the "sensors" screen
Examples:
|Mode|
|Home|
|OFF|

#Requirement :One DAS Panel and one Access Sensor should be configured
@DASDeleteAccessSensorErrorpopupVerification @NotAutomatable
Scenario Outline: As a user I should be able to verify delete error pop up from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to "Door Access settings" screen from the "Dashboard" screen 
And user "deletes Access Sensor" by clicking on "delete" button
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should be displayed with "Unable to delete sensor" pop up 
When user selects the "OK" button
Then user display with "Door Access settings" screen
Then user should be displayed with "Access Sensor" on the "sensors" screen
Examples:
|Mode|
|Home|
|OFF|





