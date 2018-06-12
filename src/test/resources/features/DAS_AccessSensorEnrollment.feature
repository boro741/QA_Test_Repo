@AccessSensorEnrollment
Feature: Verify Sensor Enrolment Functionally

@DASAccessSensorEnrollmentWithDefaultSensorNameWatchHow-ToVideo
Scenario Outline: a- As a user I should be able to successfully enrol Access Sensor with default sensor name and video should play for assistance in sensor enrolment 
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
When user <Sensor Location> access sensor "enrolled"
And  user selects "Access sensor SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch The How To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
When user selects "Get Started" from "Sensor Overview" screen
Then user should be displayed with the "Locate Sensor" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user should be displayed with the "Name Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects <Sensor Location Area> from "Name Sensor" screen
Then user should be displayed with the "Place Sensor" screen
And user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
When user selects "Wont Fit As shown" from "Place Sensor on location" screen
Then user should be displayed with the "Access sensor Install help" screen
When user navigates to "Place Sensor on location" screen from the "Access sensor Install help" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
When user <Sensor Location> access sensor "Opened"
Then user should see the <Sensor Location> status as <Access Status> on the "Test Access Sensor"
When user <Sensor Location> access sensor "closed"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
And user "should not be displayed" with the "Test sensor screen cancel" option 
And user "should not be displayed" with the "Test sensor screen back" option 
When user selects "Done" from "Test Sensor" screen
Then user should see the <Sensor Location> status as "configured" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor List"
When user navigates to "Security Solution card" screen from the "Sensor List" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor Status"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen 
When user navigates to  <Access Setting screen> screen from the "Dashboard" screen 
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with <Sensor Location> device on the "sensor list" screen

Examples:
|Mode|Sensor Location| Sensor Location Area | Access Status | Access Status Update |Access Setting screen|
|Home | Door | Front Door| Opened | Closed |Door Access settings|
|Home | Window | Living Room Window | Opened | Closed |Window Access settings|
#|OFF | Door | Back Door| Opened | Closed |Door Access settings|
#|OFF | Window |Dining Room Window | Opened | Closed |Window Access settings|

#incaserequired
#|Home | Door | Back Door| Opened | Closed |
#|Home | Door | Side Door| Opened | Closed |
#|Home | Window |Dining Room Window | Opened | Closed |
#|Home | Window | Kitchen Window | Opened | Closed |
#|OFF | Door | Front Door| Opened | Closed |
#|OFF | Door | Side Door| Opened | Closed |
#|OFF | Window | Living Room Window | Opened | Closed |
#|OFF| Window | Kitchen Window | Opened | Closed |


@DASAccessSensorEnrollmentWithCustomSensorName
Scenario Outline: As a user I should be able to successfully enrol Access Sensor with custom sensor name and video should play for assistance in sensor enrolment
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
#When user triggers <Sensor Location> sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user selects "Watch The How To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
When user selects "Get Started" from "Sensor Overview" screen
Then user should be displayed with the "Locate Sensor" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user should be displayed with the "Name Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects "Create Custom Name" from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
Then user edits the <Sensor Location> name to "custom name"
Then user should be displayed with the "Place Sensor" screen
And user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
When user selects "Wont Fit As shown" from "Place Sensor on location" screen
Then user should be displayed with the "Access sensor Install help" screen
When user navigates to "Place Sensor on location" screen from the "Access sensor Install help" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
#When user <Sensor Location> "Opened"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
#When user <Sensor Location> "closed"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
When user selects "Done" from "Test Sensor" screen
#And user should not display with "cancel" button and "Back" button
Then user should see the <Sensor Location> status as "configured" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor List"
When user navigates to "Security Solution card" screen from the "Sensor List" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor Status"
When user navigates to <Access Sensor Settings> screen from the "Security Solution card" screen
When user selects "delete sensor" from <Access Sensor Settings> screen
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
When user selects "delete sensor" from "<Access Sensor Settings> screen
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with <Sensor Location> device on the "sensor list" screen
 
 
Examples:
|Mode|Sensor Location| Sensor Location Area | Access Status | Access Status Update |Access Sensor Settings|
|Home | Door |Front Door| Open | Closed |Door Access Settings|
#|Home | Door |Back Door| Open | Closed |Door Access Settings|
#|Home | Door | Side Door| Open | Closed |Door Access Settings|
#|Home | Window | Living Room Window | Open | Closed |Window Access Settings|
#|Home | Window |Dining Room Window | Open | Closed |Window Access Settings|
#|Home | Window | Kitchen Window | Open | Closed |Window Access Settings|
#|OFF | Door | Front Door| Open | Closed |Door Access Settings|
#|OFF | Door | Back Door| Open | Closed |Door Access Settings|
#|OFF | Door | Side Door| Open | Closed |Door Access Settings|
#|OFF | Window | Living Room Window | Open | Closed |Window Access Settings|
#|OFF | Window |Dining Room Window | Open | Closed |Window Access Settings|
#|OFF| Window | Kitchen Window | Open | Closed |Window Access Settings|


@DASAccessSensorEnrollmentWithDuplicateSensorName
Scenario Outline: As a user I should get a Sensor Name Already Assigned popup when I enrol Access Sensor with duplicate sensor name
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
#When user triggers Door sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user selects "Watch The How To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
When user selects "Get Started" from "Sensor Overview" screen
Then user should be displayed with the "Locate Sensor" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user should be displayed with the "Name Sensor" screen
And user selects "Door" from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects "Create Custom Name" from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
Then user edits the "Duplicate Door" name to "custom name"
Then user should receive a "Sensor Name Already Assigned" popup
Examples:
|Mode |
|Home|
#|OFF|

@DASAccessSensorEnrollmentByCancellingAndRetryingAgainWithCustomSensorName
Scenario Outline: As a user I should be able to successfully enroll Access Sensor with custom sensor name after cancelling the enrollment and retrying again
Given user launches and logs in to the Lyric application
#And user is set to <Mode> through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
#When user triggers <Sensor Location> sensor
And user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user selects "Get Started" from "Sensor Overview" screen
Then user should be displayed with the "Locate Sensor" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user should be displayed with the "Name Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects "Create Custom Name" from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
Then user edits the <Sensor Location> name to <Custom name>
Then user should be displayed with the "Place Sensor" screen
And user selects "cancel" from "Place Sensor" screen
Then user should receive a "Cancel Sensor Setup" popup
When user "accepts" the "Cancel Sensor Setup" popup
Then user should be displayed with the "Set Up Accessories" screen
When user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user selects "Get Started" from "Sensor Overview" screen
Then user should be displayed with the "Locate Sensor" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user should be displayed with the "Name Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects "Create Custom Name" from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
Then user edits the <Sensor Location> name to <Custom name>
Then user should be displayed with the "Place Sensor" screen
When user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
#When user <Sensor Location> "Opened"
Then user should see the <Sensor Location> status as <Access Status> on the "Test Access Sensor"
#When user <Sensor Location> "closed"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
When user selects "Done" from "Test Sensor" screen
#And user should not display with "cancel" button and "Back" button
Then user should see the <Sensor Location> status as "configured" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor List"
When user navigates to "Security Solution card" screen from the "Sensor List" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor Status"
When user navigates to <Access Sensor Settings> screen from the "Security Solution card" screen
When user selects "delete sensor" from <Access Sensor Settings> screen
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
When user selects "delete sensor" from "<Access Sensor Settings> screen
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with <Sensor Location> device on the "sensor list" screen

Examples:
|Mode |Sensor Location| Custom name| Access Status | Access Status Update |Access Sensor Settings|
|Home | Door | Honeywell | Open | Closed |Door Access Settings|
|Home | Window | Honeywell1 | Open | Closed |Window Access Settings|
|OFF | Door | Honeywell2 | Open | Closed |Door Access Settings|
|OFF | Window | Honeywell3 | Open | Closed |Window Access Settings|


@DASAccessSensorEnrollmentWithSensorNotWorkingAndIsWithInRange
Scenario Outline: As a user I should be able to successfully enroll Access Sensor when sensor is not working but is within range
Given user launches and logs in to the Lyric application
And user is set to <Mode> mode through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
#When user triggers <Sensor Location> sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user selects "Get Started" from "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects <Sensor Location Area> from "Name Sensor" screen
Then user should be displayed with the "Place Sensor" screen
When user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
When user selects "Sensor Not Working" from "Test Access Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Get Additional Help" from "Access Sensor Help" screen
#Then user should be displayed with the "Honeywell help web portal" screen
And user navigates to "Access Sensor Help" screen from the "Honeywell Help web portal" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with the "Signal Strength" screen
Then user should see the <Sensor Location> status as <Signal strength> on the "Signal Strength"
And user navigates to "Test Sensor" screen from the "Test Signal Strength" screen
#When user <Sensor Location> "Opened"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
#When user <Sensor Location> "closed"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
And user "should not be displayed" with the "Test sensor screen cancel" option 
And user "should not be displayed" with the "Test sensor screen back" option 
When user selects "Done" from "Test Sensor" screen
Then user should see the <Sensor Location> status as "configured" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor List"
When user navigates to "Security Solution card" screen from the "Sensor List" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor Status"
When user navigates to "Door Access settings" screen from the "Security Solution card" screen 
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with <Sensor Location Area> device on the "sensor list" screen

 
Examples:
|Mode |Sensor Location| Sensor Location Area | Signal strength | Access Status | Access Status Update |
|Home | Door |Front Door| High |  Opened | Closed |
#|Home | Door | Front Door| Medium |Opened | Closed |
#|Home | Door | Front Door| Low |Opened | Closed |
#|Home | Window |Front Door| High | Opened | Closed |
#|Home | Window |Front Door| Medium | Opened | Closed |
#|Home | Window |Front Door| Low | Opened | Closed |
#|OFF | Door |Front Door| High | Opened | Closed |
#| OFF | Door |Front Door| Medium | Opened | Closed |
| OFF | Door | Front Door| Low |Opened | Closed |
#| OFF | Window |Front Door| High | Opened | Closed |
#| OFF | Window |Front Door| Medium | Opened | Closed |
#| OFF | Window |Front Door| Low | Opened | Closed |


@DASAccessSensorEnrollmentWithSensorNotWorkingAndIsOutOfRange
Scenario Outline: As a user I should be able to verify sensor not working functionality when sensor is out of range
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Sensors" screen from the "Dashboard" screen
Then user navigates to "Set Up Accessories" screen from the "Sensors" screen
When user triggers "ACCESS" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects <sensor location area> from "Name Sensor" screen
Then user should be displayed with "Place Sensor" screen
When user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
When user selects "Sensor Not Working?" from "Test Sensor" screen
Then user should be displayed with the "Access Sensor Help" screen
When user selects "Test Signal Strength" from "Access Sensor Help" screen
Then user should be displayed with the "Signal Strength" screen
When user door "is offline"
Then user should receive a "Out Of Range" popup
And user "taps on RETRY in" the "Out Of Range" popup
Then user should receive a "Out Of Range" pop up
And user "taps on OK in" the "Out Of Range" popup
Then user should be displayed with the "Access Sensor Help" screen
And user navigates to "Test Sensor" screen from the "Test Signal Strength" screen
		
Examples:
|Mode |Sensor Location| Sensor Location Area | 
|Home | Door | Front Door|
|Home | Door | Back Door| 
|Home | Door | Side Door| 
|Home | Window | Living Room Window | 
|Home | Window |Dining Room Window | 
|Home | Window | Kitchen Window | 
|OFF | Door | Front Door| 
|OFF | Door | Back Door| 
|OFF | Door | Side Door| 
|OFF | Window | Living Room Window | 
|OFF | Window |Dining Room Window | 
|OFF| Window | Kitchen Window | 


@DASAccessSensorEnrollmentVerifyCancelFunctionality
Scenario Outline: Verify cancel functionality in all screens while enrolling a Access Sensor
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen 
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the <PreScreen> screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Set Up Accessories" screen without displaying any sensor
		
Examples:
|Mode|PreScreen |
|Home|Sensor Overview |
|Home|Locate Sensor |
|Home|Name Sensor Location|
|Home|Name sensor Default Name |
|Home|Name sensor Custom name |
|Home|Place Sensor|
|Home|Place Sensor on Location|
|Home|Test Sensor|
|OFF|Sensor Overview |
| OFF |Locate Sensor |
| OFF |Name Sensor Location|
| OFF |Name sensor Default Name |
| OFF |Name sensor Custom name |
| OFF |Place Sensor|
| OFF |Place Sensor on Location|
| OFF |Test Sensor|


@DASAccessSensorEnrollmentVerifyCancelFunctionalityAfterTestSensor
Scenario Outline: Verify cancel functionality in all screens while enrolling a Access Sensor
Given user launches and logs in to the Lyric application
#And user is set to <Mode> mode through CHIL
When user navigates to "Sensor List Settings" screen from the "Dashboard" screen
Then user selects "Add button" from "Sensor List Settings" screen
#When user door "enrolled"
And user selects "SETUP button" from "Set Up Accessories" screen
When user selects "Get Started" from "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects "Door" from "Name Sensor" screen
And user selects "Front Door" from "Name Sensor" screen
When user navigates to "Place Sensor on location" screen from the "Place Sensor" screen
And user navigates to "Test Sensor" screen from the "Place Sensor on location" screen
And user selects "cancel" from "Test Sensor" screen
Then user should receive a "Cancel Sensor Setup" popup
#When user door "Opened"
#When user door "closed"
Then user "should not be displayed" with the "Cancel Sensor Setup popup" option
Then user should see the "door" status as <Access Status Update> on the "Test Access Sensor"
When user selects "Done" from "Test Sensor" screen
When user selects "Done" from "Set Up Accessories" screen
When user navigates to "Door Access Settings" screen from the "Sensor list" screen
When user selects "delete sensor" from "Door Access Settings" screen
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "Door" device on the "sensor list" screen
 
Examples:
|Mode|Access status | Access Status Update |
|Home|Open | Closed |
 
 
#In case required
#| OFF |Test Sensor | Opened | Closed |



@DASAccessSensorEnrollmentVerifyBackArrowFunctionality
Scenario Outline: Verify Back arrow functionality in all screens while enrolling a Access Sensor
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen
And user selects "Back arrow" from <PreScreen> screen
Then user should be displayed with the <PostScreen> screen

Examples:
|Mode|PreScreen				 |PostScreen |
|Home|Sensor Overview 			| Set up Accessories |
|Home|Locate Sensor 				|Sensor Overview |
|Home|Name Sensor Location		| Location Sensor |
|Home|Name sensor Default Name 	|Name Sensor Location|
|Home|Name sensor Custom name 	| Name sensor Default Name |
|Home|Place Sensor				| Name sensor Default Name |
|Home|Place Sensor				| Name sensor Custom name | # Navigates from sensor Custom name
|Home|Place Sensor on Location		| Place sensor |
|Home|Access Sensor Install Help 	| Place Sensor on Location |	
|Home|Test Sensor				| Place Sensor on Location|# Before verification
|Home|Acces Sensor Help			| Test Sensor |
|Home|Signal Strength			| Access Sensor Help |
|OFF|Sensor Overview 			| Set up Accessories |
| OFF |Locate Sensor 				|Sensor Overview |
| OFF |Name Sensor Location		| Location Sensor |
| OFF |Name sensor Default Name 	|Name Sensor Location|
| OFF |Name sensor Custom name 	| Name sensor Default Name |
| OFF |Place Sensor				| Name sensor Default Name |
| OFF |Place Sensor				| Name sensor Custom name | # Navigates from sensor Custom name
| OFF |Place Sensor on Location		| Place sensor |
| OFF |Access Sensor Install Help 	| Place Sensor on Location |	
| OFF |Test Sensor				| Place Sensor on Location|# Before verification
| OFF |Acces Sensor Help			| Test Sensor |
| OFF |Signal Strength			| Access Sensor Help |

@DASAccessSensorEnrollmentVerifyBackArrowFunctionalityAfterVerificationTestSensor
Scenario Outline: Verify Back arrow functionality in Test Sensor screen after verification
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to the <PreScreen> screen
And user selects "Back arrow" from <PreScreen> screen
Then user should be displayed with <PreScreen> screen
And user should be disabled "Back arrow" button
Examples:
|Mode |PreScreen	|			
|Home|Test Sensor|				
|OFF|Test Sensor|

@DASAccessSensorEnrollmentflowpushnotificationVerification
Scenario: verify push notification while Access Sensor Enrolment
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
And user enrolled the "ISMV" , "OSMV", "MV" , "AccessSensor"
When user navigates to "Sensors" screen from the "Dashboard" screen
Then user navigates to "Set Up Accessories" screen from the "Sensors" screen
When user triggers "ACCESS" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
When user "Triggered" the "Access Sensor"
Then user should not receive "Alerts" and "Push Notifications"

@DASAccessSensorEnrollmentflowpushnotificationVerification
Scenario: verify push notification while Access Sensor Enrolment
Given user launches and logs in to the Lyric application
And user is set to <Mode> through CHIL
When user navigates to "Sensors" screen from the "Dashboard" screen
Then user navigates to "Set Up Accessories" screen from the "Sensors" screen
When user triggers "ACCESS" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
When user "Triggered" the "Access Sensor"
Then user should receive "Time out" pop up
When user "accepts" the "Time out" pop up
Then user should navigate to "Setup Accessories" screen

