@KeyfobSettings
Feature: Test the enrollment and settings of keyfob

@DAS_KeyFobEnrollment
Scenario: 1- As a user I should be able to successfully enroll KeyFob and Assistance video 
Given user launches and logs in to the Lyric application
And user is set to "Home" mode through CHIL
When user navigates to "KEYFOB list Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Keyfob List Settings" screen
When user press "enrollment" key from keyfob
And user selects "Keyfob SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Keyfob Overview" Screen 
When user selects "WATCH THE HOW TO VIDEO" from "Keyfob Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Keyfob Overview" screen from the "Video clip" screen
When user selects "Get Started" from "Keyfob Overview" screen
Then user should be displayed with the "NAME KEYFOB" screen
Then user edits the "Keyfob Sensor" name to "Keyfob"
When user selects "Done" from "Configuration Success" screen
Then user should see the "Keyfob" status as "Assigned" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should be displayed with "keyfob" device on the "Keyfob list" screen


#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobStatusVerification 
  Scenario: 2- As a user I want to view that all KeyFob settings 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Keyfob Settings" screen from the "Dashboard" screen
     Then user should be displayed with the following "Sensor Settings" options: 
      | Settings                   |
      | Name                       | 
      | Model and Firmware Details |
      | DELETE                     |

#Requirement :One DAS Panel and one KeyFob should be configured
@DASKeyFobRenameVerification 
Scenario Outline:AS a user I want to rename my KeyFob sensor through the application
Given user launches and logs in to the Lyric application 
And user is set to <Mode> mode through CHIL
 When user navigates to "Keyfob Settings" screen from the "Dashboard" screen
Then user edits the "keyfob" name to "new name"
Then user navigates to "Base station configuration" screen from the "KeyFob" screen
And user navigates to "Motion Viewer settings" screen from the "Base station configuration" screen
Then user should be displayed with "KeyFob Name" sensor name on the "Motion Screen settings" screen
And user reverts back the "KeyFob" Sensor name through CHIL
Examples:
|Mode|
|Home|
|OFF|

#Requirement :One DAS Panel and one KeyFob should be configured
@DASDeleteKeyFobVerification
Scenario Outline: 5- As a user I should be able to delete KeyFob configured to my DAS panel from my account through the Lyric application 
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL 
When user navigates to "Keyfob Settings" screen from the "Dashboard" screen 
When user selects "delete sensor" from "Keyfob Settings" screen
Then user should receive a "Delete keyfob Confirmation" popup
And user "dismisses" the "Delete keyfob Confirmation" popup
When user selects "delete sensor" from "Keyfob Settings" screen
Then user should receive a "Delete Keyfob Confirmation" popup
And user "accepts" the "Delete Keyfob Confirmation" popup
Then user should not be displayed with "Keyfob" device on the "Keyfob list" screen
Examples:
|Mode|
|Home|
#|OFF|




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
@DASDeleteKeyFobPopupVerificationNightAwayOffline 
Scenario Outline: As a user I should be able to delete KeyFob configured to my DAS panel from my account through the Lyric application 
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



