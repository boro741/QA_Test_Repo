@DAS_Sensor
Feature: DAS Sensor
As a user I want to check the manage and view the status of security accessories

Background:
Given reset relay as precondition


@DASSecuritySettingsSensorstatus	@P2		@Automatable
Scenario Outline: As a user i should be displayed with security accessories in Security Settings 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 2 Keyfob
Given user launches and logs in to the Lyric Application
 And user is set to "Home" through CHIL
When user navigates to "Security Settings" screen from "Dashboard" screen
Then user should be displayed with Security Accessories:
|Security Accessories|Accessories Count|Issue Count|
|Key Fob|(2)|Null|
|Sensors|(5)|Null|
When user is set <Sensors> to <Sensor status>
Then user should be displayed with Security Accessories:
|Security Accessories|Accessories Count|Issue Count|
|Key Fob|(2)|Null|
|Sensors|(5)|4 Issue(s)|

Examples:
| Sensor Status |Sensors |
| Cover Tampered|Motion sensor|
| Low Battery   |ISMV sensor  | 
| Offline       |OSMV sensor  | 
| Open          |Door sensor  |
| Closed        |Window sensor|


#Requirement :One DAS Panel and one Door Sensor should be configured
@DASDoorSensorRenameVerification 		@UIAutomated			@Automatable
Scenario Outline: As a user I want to rename my Door Sensor through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "Door Sensor settings" screen from the "Dashboard" screen
Then user edits the "Access Sensor" name to "new name"
#And user reverts back the "Access Sensor" Sensor name through CHIL
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASWindowSensorRenameVerification 		@UIAutomated			@Automatable
Scenario Outline: As a user I want to rename my Window Sensor sensor through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "Window Sensor settings" screen from the "Dashboard" screen
Then user edits the "Access Sensor" name to "new name"
#And user reverts back the "Access Sensor" Sensor name through CHIL
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Motion Sensor should be configured
@DASMotionRenameVerification			@UIAutomated			@Automatable
Scenario Outline: As a user I want to rename my Motion Sensor through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "Motion Sensor settings" screen from the "Dashboard" screen
Then user edits the "Motion Sensor" name to "new name"
#And user reverts back the "Motion Sensor" Sensor name through CHIL
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one ISMV Sensor should be configured
@DASISMVSensorRenameVerification			@UIAutomated			@Automatable
Scenario Outline: As a user I want to rename my Window Sensor sensor through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "ISMV Sensor settings" screen from the "Dashboard" screen
Then user edits the "Motion Viewer" name to "new name"
#And user reverts back the "Motion Viewer" Sensor name through CHIL
Examples:
|Mode|Sensor status|
|Home|Open|
|Home|Low battery|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one ISMV Sensor should be configured
@DASOSMVSensorRenameVerification		@UIAutomated			@Automatable
Scenario Outline: As a user I want to rename my OSMV Sensor sensor through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "OSMV Sensor settings" screen from the "Dashboard" screen
Then user edits the "Motion Viewer" name to "new name"
#And user reverts back the "Motion Viewer" Sensor name through CHIL
Examples:
|Mode|Sensor status|
|Home|Open|
|Home|Low battery|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASSensorRenamePopUpVerification		@UIAutomated			@Automatable
Scenario Outline: As a user i should be shown with message that I cannot rename my Access Sensor sensor through the application when panel is in below states 
Given user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to <Sensor Settings> settings screen from the "Dashboard" screen 
Then the following <Sensor Settings> options should be disabled:
|Options|
|Name field|
#Then user should display the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Sensor Settings|Mode|
|Door Sensor|Night|
|Window Sensor|Away|
|Motion Sensor|Offline|
|ISMV Sensor  |Alarm|
|OSMV Sensor  |Night|


#Requirement :One DAS Panel and one ISMV Sensor should be configured
@DASKeyfobRenameVerification			@UIAutomated				@Automatable
Scenario Outline: As a user I want to rename my keyfob through the application
And user is set to <Mode> mode through CHIL
And user is set to <Sensor status> 
And user launches and logs in to the Lyric application 
When user navigates to "Key Fob settings" screen from the "Dashboard" screen
Then user edits the "Key Fob" name to "new name"
#And user reverts back the "Motion Viewer" Sensor name through CHIL
Examples:
|Mode|
|Home|
|Off |
|Sensor Enrollment |


#Requirement :One DAS Panel and one Key fob should be configured
@DASKeyfobRenamePopUpVerification		@UIAutomated			@Automatable
Scenario Outline: As a user i should be shown with message that I cannot rename my keyfob through the application when panel is in below states 
Given user is set to <Mode> mode through CHIL
And user launches and logs in to the Lyric application 
When user navigates to "Key Fob Settings" screen from the "Dashboard" screen 
Then the following "Key Fob Settings" options should be disabled:
|Options|
|Name field|
#Then user should display the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Mode|
|Night|
|Away|
|Offline|
|Alarm|
|Night|


#Requirement :One DAS Panel and all sensor type should be configured
@DASSensorStatusOFFWithAwayMode			@UIAutomated				@Automatable
Scenario Outline: As a user I want to switch to different states in my DAS device when multiple sensors are in fault condition
Given user sets the entry/exit timer to "45" seconds
And user is set to <Status> mode through CHIL
When user launches and logs in to the Lyric application
And user navigates to "security solution cards" screen from "Dashboard" screen
And user "Cover Tampered" the Door sensor
And user "Cover Tampered" the Window sensor
And user "Cover Tampered" the Motion sensor
And user switches from <Status>to "Away"
Then user should be displayed with a "Switch to Away" popup
When user "confirms" the "Switch to Away " popup
When "Switching timer" ends on user device
Then user should be displayed with the "Away Status"
When user navigates to "Sensor List"  from "security solution cards" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status |
| Door Sensor   | OFF       |
| Window Sensor | OFF       |
| Motion Sensor | OFF       |
Examples:
|Mode|
|Home|
|OFF |


@DASSensorStatusOFFWithNightMode		@UIAutomated				@Automatable
Scenario Outline: As a user I want to switch to different states in my DAS device when multiple sensors are in fault condition
Given user sets the entry/exit timer to "45" seconds
And user is set to <Status> mode through CHIL
When user launches and logs in to the Lyric application
And user navigates to "security solution cards" screen from "Dashboard" screen
And user "Cover Tampered" the Door sensor
And user "Cover Tampered" the Window sensor
And user "Cover Tampered" the Motion sensor
And user switches from <Status> to "Night"
Then user should be displayed with a "Switch to Night" popup
When user user "confirms" the "Switch to Night " popup
And "Switching timer" ends on user device
Then user should be displayed with the "Night Status"
When user navigates to "Sensors List"  from "security solution cards" screen
Then user should see the <Sensors> status as <Sensor Status> on the "Sensor List"
| Sensors       | Sensor Status |
| Door Sensor   | OFF       |
| Window Sensor | OFF       |
| Motion Sensor | Active    |
When user navigates to "Sensor settings" screen
Then user should be displayed with "OFF" screen
Examples:
|Mode|
|Home|
|OFF |


@DASSensorCoverTamperStatus			@P2			@Automatable
Scenario Outline: As a user i should be displayed with Cover Tamper sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
And user is set to <Mode> through CHIL
When user navigates to "Security Settings " screen from "Dashboard" screen
And user navigates to "Sensor list" screen from "Security Settings" screen
And user creates "Cover Tamper" at the <Sensor> 
Then user should be displayed with the "Cover Tamper status"
When user selects the "Cover Tamper status"
Then user should be displayed with the "Cover Tamper description" Screen
When user navigates to "Sensor list" screen from "Cover Tamper description" Screen
And user clears "Cover Tamper" at the <Sensor>
And user selects the <Sensor>
Then user should be displayed with the "Cover Tamper description" Screen
When user selects the "Clear Tamper"
And user navigates to "Security Settings" screen from "Dashboard" screen
And user navigates to "Sensor list" screen from "Security Settings"
Then user should not be displayed with "Cover Tamper status"
Examples:
|Mode|Sensor|
|Home|Door Sensor|
|Home|Window Sensor|		
|Home|Motion Sensor|
|Off |Door Sensor|			
|Off |Window Sensor|		
|Off |Motion Sensor|


@DASSensorLowBatteryStatus			@P2			@NotAutomatable		
Scenario Outline: As a user i should be displayed with low battery sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
 And user is set to <Mode> through CHIL
When user navigates to "Security Settings " screen from "Dashboard" screen
 And user navigates to "Sensor list" screen from "Security Settings" screen
 And user creates "Low Battery" at the <Sensor> 
Then user should be displayed with the "Low Battery status"
When user selects the "Low Battery status"
Then user should be displayed with the "Low Battery description" Screen
When user navigates to "Sensor list" screen from "Low Battery description" Screen
Then user should not be displayed with "Low Battery status"
Examples:
|Mode|Sensor| 
|Away|Door Sensor|			
|Away|Window Sensor|		
|Away|Motion Sensor|
|Away|ISMV Sensor|
|Away|OSMV Sensor|
|Night|Door Sensor|			
|Night|Window Sensor|		
|Night|Motion Sensor|
|Night|ISMV Sensor|
|Night|OSMV Sensor|
|Home|Door Sensor|			
|Home|Window Sensor|		
|Home|Motion Sensor|
|Home|ISMV Sensor|
|Home|OSMV Sensor|
|Off |Door Sensor|			
|Off |Window Sensor|		
|Off |Motion Sensor|
|Off |ISMV Sensor|
|Off |OSMV Sensor|


@DASSensorOfflineStatus			@P2			@NotAutomatable
Scenario Outline: As a user i should be displayed with offline sensors status 
#DAS with sensors Door Contact Window Contact ISMV OSMV Motion Sensor 
Given user launches and logs in to the Lyric Application
 And user is set to <Mode> through CHIL
When user navigates to "Security Settings " screen from "Dashboard" screen
 And user navigates to "Sensor list" screen from "Security Settings" screen
 And user creates "Offline" at the <Sensor> 
Then user should be displayed with the "Offline status"
When user selects the "Offline status"
Then user should be displayed with the "Offline description" Screen
When user navigates to "Sensor list" screen from "Offline description" Screen
Then user should not be displayed with "Offline status"
Examples:
|Mode|Sensor| 
|Away|Door Sensor|			
|Away|Window Sensor|		
|Away|Motion Sensor|
|Away|ISMV Sensor|
|Away|OSMV Sensor|
|Night|Door Sensor|			
|Night|Window Sensor|		
|Night|Motion Sensor|
|Night|ISMV Sensor|
|Night|OSMV Sensor|
|Home|Door Sensor|			
|Home|Window Sensor|		
|Home|Motion Sensor|
|Home|ISMV Sensor|
|Home|OSMV Sensor|
|Off |Door Sensor|			
|Off |Window Sensor|		
|Off |Motion Sensor|
|Off |ISMV Sensor|
|Off |OSMV Sensor|


#Requirement :One DAS Panel and one Access Sensor should be configured
@DASSensorModelAndFirmwareDetailsVerification			@UIAutomated				@Automatable
Scenario Outline:Verify Model details and Firmware details in Access Sensor
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Door Sensor settings" screen from the "Dashboard" screen
And user selects "Model and Firmware Details" from "Door Sensor settings" screen
Then user should be displayed with the "Model and Firmware Details" screen
When user navigates to "Window Sensor settings" screen from the "Model and Firmware Details" screen
And user selects "Model and Firmware Details" from "Window Sensor settings" screen
Then user should be displayed with the "Model and Firmware Details" screen
When user navigates to "Motion Sensor settings" screen from the "Model and Firmware Details" screen
And user selects "Model and Firmware Details" from "Motion Sensor settings" screen
Then user should be displayed with the "Model and Firmware Details" screen
When user navigates to "ISMV Sensor settings" screen from the "Model and Firmware Details" screen
And user selects "Model and Firmware Details" from "ISMV Sensor settings" screen
Then user should be displayed with the "Model and Firmware Details" screen
When user navigates to "OSMV Sensor settings" screen from the "Model and Firmware Details" screen
And user selects "Model and Firmware Details" from "OSMV Sensor settings" screen
Then user should be displayed with the "Model and Firmware Details" screen
When user navigates to "Key fob settings" screen from the "Model and Firmware Details" screen
And user selects "Model and Firmware Details" from "Key fob settings" screen
Then user should be displayed with the "Model and Firmware Details" screen

Examples:
|Mode|
|Home|
|Away|
|Night|
|OFF|
|OFFLINE|


#Requirement :One DAS Panel and one Door Access Sensor should be configured
@DASDoorSensorSignalStrengthWithSensor			@Automatable
Scenario Outline: As a user I should be able to verify the signal strength and test Door Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Door Access settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Get additional help" from "Access Sensor Help" screen
Then user should be navigated to "Web page help" screen 
When user selects "back" button
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with "Signal Strength" screen 
When user navigates to "Dashboard" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Door Access Sensor should be configured
@DASWindowSensorSignalStrengthWithSensor			@Automatable
Scenario Outline: As a user I should be able to verify the signal strength and test window Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Window Access settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Get additional help" from "Access Sensor Help" screen
Then user should be navigated to "Web page help" screen 
When user selects "back" button
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with "Signal Strength" screen 
When user navigates to "Dashboard" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Motion Sensor should be configured
@DASMotionSensorSignalStrengthWithSensor			@Automatable
Scenario Outline: As a user I should be able to verify the signal strength and test Motion Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Motion Sensor settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Motion Sensor Help" screen
When user selects "Get additional help" from "Motion Sensor Help" screen
Then user should be navigated to "Web page help" screen 
When user selects "back" button
Then user should be displayed with the "Motion Sensor Help" screen
When user selects "Test Signal Strength" from "Motion Sensor Help" screen
Then user should be displayed with "Signal Strength" screen 
When user navigates to "Dashboard" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one ISMV Sensor should be configured
@DASISMVSensorSignalStrengthWithSensor			@Automatable
Scenario Outline: As a user I should be able to verify the signal strength and test ISMV Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Motion Viewer settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Motion Viewer Help" screen
When user selects "Get additional help" from "Motion Viewer Help" screen
Then user should be navigated to "Web page help" screen 
When user selects "back" button
Then user should be displayed with the "Motion Viewer Help" screen
When user selects "Test Signal Strength" from "Motion Viewer Help" screen
Then user should be displayed with "Signal Strength" screen 
When user navigates to "Dashboard" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one OSMV Sensor should be configured
@DASOSMVSensorSignalStrengthWithSensor		@Automatable
Scenario Outline: As a user I should be able to verify the signal strength and test OSMV Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Motion Viewer settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Motion Viewer Help" screen
When user selects "Get additional help" from "Motion Viewer Help" screen
Then user should be navigated to "Web page help" screen 
When user selects "back" button
Then user should be displayed with the "Motion Viewer Help" screen
When user selects "Test Signal Strength" from "Motion Viewer Help" screen
Then user should be displayed with "Signal Strength" screen 
When user navigates to "Dashboard" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|


#Requirement :One DAS Panel and one Door Access Sensor should be configured
@DASDoorSensorSignalStrengthWithSensorNotWorkingAndIsOutOfRange			@NotAutomatable
Scenario Outline: As a user I should be able to verify the signal strength and test Door Sensor in Home and OFF mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Door Access settings" settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Test Sensor" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with "Signal Strength" screen 
Then user should receive a "Out Of Range" popup
And user "taps on RETRY in" the "Out Of Range" popup
Then user should receive a "Out Of Range" pop up
And user "taps on OK in" the "Out Of Range" popup
Then user should be displayed with the "Access Sensor Help" screen
And user navigates to "Test Sensor" screen from the "Test Signal Strength" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|


#Requirement :One DAS Panel and one Sensor should be configured and sensor status should be offline
@DASSensorSignalStrengthWhenSensorOffline			@NotAutomatable
Scenario Outline: As a user I should be shown with message that I cannot check signal strength for panel accessories through the application when sensor offline
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to <Sensor Settings> settings screen from the "Dashboard"
When user selects the "Signal Strength and Test" button
Then user should be displayed with "Sensor is offline" pop up
Examples:
|Sensor Settings|Status|
|Door sensosr   |Offline|
|Motion sensor  |Offline|
|Window sensor  |Offline|
|ISMV sensor    |Offline|
|OSMV sensor    |Offline|


@DASSignalStrengthErrorpopupVerification				@NotAutomatable
Scenario Outline: As a user I should be able to verify delete error pop up from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to <Sensor Settings> Settings screen from the "Dashboard" screen 
 And user selects the "Signal Strength and Test" button
#Deletion failed(Put panel to offline,server not responding ) 
Then user should be displayed with "Signal Strength and Test " pop up 
When user selects the "Retry" button
Then user should be displayed with "Signal Strength and Test " pop up 
When user selects the "Cancel" button
Then user display with <Sensor Settings> Settings screen
Examples:
|Mode|Sensor Settings|
|Home|Door Sensor  |
|Home|Window Sensor|
|Home|Motion Sensor|
|Home|ISMV Sensor|
|Home|OSMV Sensor|
|Off |Door Sensor  |
|Off |Window Sensor|
|Off |Motion Sensor|
|Off |ISMV Sensor|
|Off |OSMV Sensor|


@DASSignalStrengthSensorDisabled				@NotAutomatable
Scenario Outline: As a user I should be shown with message that I cannot check signal strength for panel accessories through the application when panel is in below states
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to <Sensor Settings> screen from the "Dashboard" screen 
Then the following <Sensor Settings> Settings options should be disabled:
|Options|
|Signal Strength and test|
#Then user should display the "you can perform this action only in Home or Off mode" pop up
Examples:
|Sensor Settings|Mode|
|Door sensosr   |Night|
|Motion sensor  |Away|
|Window sensor  |Alarm|
|ISMV sensor    |Away|
|OSMV sensor    |Night|


#Requirement :One DAS Panel and one Door Access Sensor should be configured
@DASDeleteDoorAccessSensor				@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "Door Sensor settings" screen from the "Dashboard" screen
And user navigates to "Door Sensor settings" screen from the "Dashboard" screen
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "Door Sensor settings" screen
When user navigates to "Sensor list" screen from the "Door Sensor settings" screen 
Then user should not be displayed with "Door Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one Window Access Sensor should be configured
@DASDeleteWindowAccessSensor			@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "Window Sensor settings" screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "Window Sensor settings" screen
When user navigates to "Sensor list" screen from the "Window Sensor settings" screen 
Then user should not be displayed with "Window Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Open|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Open|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Open|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one Motion Sensor should be configured
@DASDeleteMotionSensor			@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "Motion Sensor settings" screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "Motion Sensor settings" screen
When user navigates to "Sensor list" screen from the "Motion Sensor settings" screen 
Then user should not be displayed with "Motion Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Standby|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Standby|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Standby|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one Motion Sensor should be configured
@DASDeleteMotionSensor			@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "Motion Sensor settings" screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "Motion Sensor settings" screen
When user navigates to "Sensor list" screen from the "Motion Sensor settings" screen 
Then user should not be displayed with "Motion Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Standby|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Standby|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Standby|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one ISMV Sensor should be configured
@DASDeleteISMVSensor				@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "ISMV Sensor settings" screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "ISMV Sensor settings" screen
When user navigates to "Sensor list" screen from the "ISMV Sensor settings" screen 
Then user should not be displayed with "ISMV Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Standby|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Standby|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Standby|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one OSMV Sensor should be configured
@DASDeleteOSMVSensor				@Automatable
Scenario Outline: As a user I should be able to delete sensor from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user is set to <Sensor status>
When user navigates to "OSMV Sensor settings" screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "Cancels" the "Delete Sensor Confirmation" popup
Then user display with "Door Sensor settings" screen
When user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user selects the "OK" button
Then user display with "OSMV Sensor settings" screen
When user navigates to "Sensor list" screen from the "OSMV Sensor settings" screen 
Then user should not be displayed with "OSMV Sensor" in the "Sensor list" screen
Examples:
|Mode|Sensor status|
|Home|Cover Tampered|
|Home|Standby|
|Home|Low battery|
|Home|Offline|
|Off |Cover Tampered|
|Off |Standby|
|Off |Low battery|
|Off|Offline|
|Sensor Enrollment |Cover Tampered|
|Sensor Enrollment |Standby|
|Sensor Enrollment |Low battery|
|Sensor Enrollment |Offline|


#Requirement :One DAS Panel and one Keyfob should be configured
@DASDeleteKeyfob				@Automatable
Scenario Outline: As a user I should be able to delete keyfob from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Keyfob settings" screen from the "Dashboard" screen 
And user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup
When user "Cancels" the "Delete keyfob Confirmation" popup
Then user display with "Keyfob settings" screen
When user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup
When user selects the "OK" button
Then user display with "Keyfob settings" screen
When user navigates to "Sensor list" screen from the "keyfob settings" screen 
Then user should not be displayed with "Keyfob" in the "keyfob list" screen
Examples:
|Mode|
|Home|
|Off |


@DASDeleteSensorDisabled				@NotAutomatable
Scenario Outline: As a user I should be shown with message that I cannot delete panel accessories through the application when panel is in below states
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to <Sensor Settings> screen from the "Dashboard" screen 
Then the following <Sensor Settings> Settings options should be disabled:
|Options|
|Delete field|
#Then user should display the "you can perform this action only in Home or Off mode" pop up
Examples:
|Sensor Settings|Mode|
|Door sensosr   |Night|
|Motion sensor  |Away|
|Window sensor  |Offline|
|ISMV sensor    |Alarm|
|OSMV sensor    |Night|
|Keyfob         |Away|


@DASDeleteSensorErrorpopupVerification			@NotAutomatable
Scenario Outline: As a user I should be able to verify delete error pop up from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to <Sensor Settings>screen from the "Dashboard" screen 
And user "deletes Sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
When user "ok" the "Delete Sensor Confirmation" popup
#Deletion failed(Put panel to offline,server not responding ) 
Then user should be displayed with "Unable to delete sensor" pop up 
When user selects the "OK" button
Then user display with <Sensor Settings> Settings screen
 And user should be displayed with <Sensor> in the "Sensor list" screen
Examples:
|Mode|Sensor Settings|Sensor|
|Home|Door Sensor  |Door Sensor|
|Home|Window Sensor|Window Sensor|
|Home|Motion Sensor|Motion Sensor|
|Home|ISMV Sensor|ISMV Sensor|
|Home|OSMV Sensor|OSMV Sensor|
|Off |Door Sensor  |Door Sensor|
|Off |Window Sensor|Window Sensor|
|Off |Motion Sensor|Motion Sensor|
|Off |ISMV Sensor|ISMV Sensor|
|Off |OSMV Sensor|OSMV Sensor|