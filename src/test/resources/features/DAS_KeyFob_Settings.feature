#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobStatusVerification 
  Scenario: As a user I want to view that all KeyFob settings 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Motion Viewer settings" screen from the "Dashboard" screen 
     Then user should be displayed with the following <Settings> options: 
      | Settings           |
      | Name               |  
      | Model and Firmware Details |
	  | DELETE|

#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobRenameVerification 
Scenario Outline:AS a user I want to rename my KeyFob sensor through the application
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "KeyFob settings" screen from the "Dashboard"
When user edits the "KeyFob" name to "KeyFob Name"
Then user navigates to "Base station configuration" screen from the "KeyFob" screen
And user navigates to "Motion Viewer settings" screen from the "Base station configuration" screen
Then user should be displayed with "KeyFob Name" sensor name on the "Motion Screen settings" screen
And user reverts back the "KeyFob" Sensor name through CHIL
Examples:
|Mode|
|Home|
|OFF|

#Requirement :One DAS Panel and two KeyFob should be configured
@DASKeyFobDuplicatenameVerification
Scenario Outline:AS a user I want to verify duplicate name my KeyFob sensor through the application
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "KeyFob settings" screen from the "Dashboard"
When user edits the "KeyFob1" for first keyfob
Then user edits the "KeyFob1" for second keyfob
And user navigates to "Sensors" screen from the "KeyFob" screen
Then user should receive with "Sensor Name Already Assigned, Pleases Pleases give different name" pop up 
And user should not displayed with "KeyFob1" name for second keyfob
And user reverts back the "KeyFob" Sensor name through CHIL
Examples:
|Mode|
|Home|
|OFF|

#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobRenamePopUpVerification 
Scenario Outline:AS a user I want to rename my KeyFob sensor through the application when in night, away, offline mode
Given user launches and logs in to the Lyric application 
And user is set to <Mode> through CHIL
Then user navigates to "Motion Viewer settings" screen from the "Dashboard"
When user selects the "KeyFob" name
Then user should display the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Mode|
|Night|
|Away|
|Offline|


#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobModelAndFirmwareDetailsVerification 
Scenario Outline: Verify Model details and Firmware details in KeyFob
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Model and Firmware Details" screen from the "Sensor Settings" screen
Then user should be displayed with the "Model Details" and "Firmware Details"  of the KeyFob
And user selects "Back" button
Then user should navigates to "KeyFob" screen
Examples:
|Mode|
|Home|
|Away|
|Night|
|OFF|
|OFFLINE|

#Requirement :One DAS Panel and one KeyFob should be configured
@DASDeleteKeyFobVerification
Scenario: As a user I should be able to delete KeyFob configured to my DAS panel from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to "Motion Viewer settings" screen from the "Dashboard" screen 
And user "deletes KeyFob" by clicking on "delete" button
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "KeyFob" on the "sensors" screen
Then user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user should not  displayed with the "Aware and Deter" and "Outdoor Motion Viewers On in Home Mode" options
Examples:
|Mode|
|Home|
|OFF|

#Requirement :One DAS Panel and one KeyFob should be configured
@DASDeleteKeyFobPopupVerificationNightAwayOffline 
Scenario: As a user I should be able to delete KeyFob configured to my DAS panel from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL 
When user navigates to "Motion Viewer settings" screen from the "Dashboard" screen 
And user "deletes KeyFob" by clicking on "delete" button
And user "deletes sensor" by clicking on "delete" button
Then user should be displayed the "you can perform this action only in Home or Off mode" pop up 
Examples:
|Mode|
|Night|
|Away|
|Offline|



