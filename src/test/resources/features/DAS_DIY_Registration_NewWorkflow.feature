@DASDIY
Feature: DAS DIY Registration
As a user I want to register a DAS device using the Lyric application

@DASDIYWhatToExpectScreen	@P2			@UIAutomated
Scenario: User should be validate What To Expect and Watch How-To Video screens
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Watch How-To video" screen from the "What To Expect" screen
Then user should be displayed with the "Video clip" screen
#And User rotates the device
#Then User should be able to see the video in landscape mode also 
When user navigates to "What To Expect" screen from the "Video clip" screen
Then user navigates to "Choose Location" screen from the "What To Expect" screen

#There is no Cancel button in Create Location, Create Base Station screens in iOS
@DIYCancelSetUp		@P2			@UIAutomated
Scenario Outline: User should be able to cancel the set up from choose location, Create Location, name your base station and Name your device screens
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Choose Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen
When user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
Then user navigates to "Choose Location" screen from the "What To Expect" screen
When user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Create Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen
When user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
Then user navigates to "Choose Location" screen from the "What To Expect" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Name Your Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen
When user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
Then user navigates to "Choose Location" screen from the "What To Expect" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects "Create New Base Station" from "Name Your Base Station" screen
Then user should be displayed with the "Create New Base Station" screen
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Create New Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
      | location name   |
      | Home            |
      

@DIYRegistrationWhenExistingLocationAndBaseStationNamesAreEntered			@P2		@UIAutomated
Scenario Outline: As a user I want to verify if error popup displays when existing location and base station names are entered again
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user clicks on done button without entering any custom name
Then user should receive a "Custom Name should not be empty" popup
When user "clicks on OK in" the "Custom Name should not be empty" popup
Then user should be displayed with the "Create Location" screen
When user inputs <Existing location name> in the "Create Location" screen
Then user should receive a "Existing Location Error" popup
When user "clicks on OK in" the "Existing Location Error" popup
Then user should be displayed with the "Create Location" screen
When user navigates to "Choose Location" screen from the "Create Location" screen
When user selects <Existing location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects "Create New Base Station" from "Name Your Base Station" screen
Then user should be displayed with the "Create New Base Station" screen
When user clicks on done button without entering any custom name
Then user should receive a "Custom Name should not be empty" popup
When user "clicks on OK in" the "Custom Name should not be empty" popup
Then user should be displayed with the "Create New Base Station" screen
When user inputs <Existing device name> in the "Create New Base Station" screen
Then user should receive a "Existing Base Station Error" popup
When user "clicks on OK in" the "Existing Base Station Error" popup
Then user should be displayed with the "Create New Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
      | Existing location name      | Existing device name  |
      | Home                        | Living Room           |
      
@DIYWhenUserEntersMaxCharactersInCustomLocationAndBaseStationName		@P2		@UIAutomated
Scenario Outline: As a user I want to enter max characters in new location and base station name
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user inputs <max charcters> in the "Name Your Location" screen
Then user should not be allowed to enter more than "30" charcters in "Name Your Location" screen
When user navigates to "Choose Location" screen from the "Create Location" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects "Create New Base Station" from "Name Your Base Station" screen
Then user should be displayed with the "Create New Base Station" screen
And user inputs <max charcters> in the "Name Your Device" screen
Then user should not be allowed to enter more than "30" charcters in "Name Your Device" screen

Examples: 
      | max charcters                    | location name		|
      | This is to test max character	| Home				|
      | This is to test max characters	| Home				|
      | This is to test max characterss	| Home				|
      

@DIYRegistrationWithNewCustomLocationAndBaseStationName		@P1			@UIAutomated
Scenario Outline: As a user I want to register a DAS device with new location and base station name using the Lyric application
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user inputs <new location name> in the "Create Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <invalid zip code>
Then user should receive a "Invalid zip code" popup
When user "dismisses" the "Invalid zip code" popup
When user inputs <valid zip code>
Then user should be displayed with the "Name Your Base Station" screen
When user selects "Create New Base Station" from "Name Your Base Station" screen
Then user should be displayed with the "Create New Base Station" screen
When user inputs <new device name> in the "Create New Base Station" screen
Then user should be displayed with the "Power Base Station" screen
And user selects "Not Pulsing Blue" from "Power Base station" screen
Then user should be displayed with the "Base Station Help" screen
#And user should be displayed with Customer Care number on the bottom of the screen
When user navigates to "Power Base station" screen from the "Base station Help" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <new device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <new device name> device on the "dashboard" screen
Then user "deletes location details" by clicking on "delete" button

Examples: 
      | new location name           | new device name       | invalid zip code             | valid zip code        |
      | California                  | Scrum Room            | 555555                       | 90001                 |
      | Texas                       | War Room              | 555555                       | 73301                 |
#     | Texas#$%                    | Ball Room             | 555555                       | 73301                 |


@DIYRegistrationWithAvailableDefaultLocationAndBaseStationName		@P2		@UIAutomated
Scenario Outline: As a user I want to verify default location name and default base station name
#Given user DAS device with ADB ID "9c48da88" is deregistered and booted
Given user launches and logs in to the Lyric application
When user selects "Smart Home Security" from "Add New Device" screen
Then user should be displayed with the "What To Expect" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <Default Location> from "Choose Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <invalid zip code>
Then user should receive a "Invalid zip code" popup
When user "dismisses" the "Invalid zip code" popup
When user inputs <valid zip code>
Then user should be displayed with the "Name Your Base Station" screen
And user selects <Default Device Name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
And user selects "Not Pulsing Blue" from "Power Base station" screen
Then user should be displayed with the "Base Station Help" screen
#And user should be displayed with Customer Care number on the bottom of the screen
When user navigates to "Power Base station" screen from the "Base station Help" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <Default Device Name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <Default Device Name> device on the "dashboard" screen
Then user "deletes default location details" by clicking on "delete" button

Examples: 
      | Default Location		| Default Device Name		| invalid zip code             | valid zip code        |
#      | Home					| Living Room				| 555555                       | 90001                 |		
#      | Vacation Home		| Kitchen					| 555555                       | 90001                 |
      | Office				| Entry Way					| 555555                       | 90001                 |
#      | Lake House			| Dining Room				| 555555                       | 90001                 |
#      | Cabin				| Bedroom					| 555555                       | 90001                 |
#      | Pool House			| First Floor				| 555555                       | 90001                 |

#CannotAutomate
@DIYDenyAppAccessToLocationServices		@P2
Scenario Outline: As a user I should be prompted with Location services popup when location services access is denied after installation
Given user denies location services access while launching the Lyric app after installation and then logs in to the Lyric app
When user navigates to "Add New Device Dashboard" screen form the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects the "Get Started" from "What To Expect" screen
Then user should be displayed with the "Choose Location" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user clicks on "Connect" button
Then user should receive a "Location services" popup
And user "denies access" in "Location services" popup
Then user should be displayed with the "Power Base Station" screen
When user clicks on "Connect" button
Then user should receive a "Location services" popup
And user "allows access" in "Location services" popup
Then user should be displayed with the "Looking for Base Station" screen
When user turns "off" location services for Lyric app in mobile device settings
Then user should receive a "Location services" popup
And user "allows access" in "Location services" popup
Then user should be displayed with the "Looking for Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYWhenNoBaseStationsAreAvailable	 		@Doesn'tRequireAnyBaseStationsForExecution		@P2				@UIAutomated
Scenario Outline: As a user I should be prompted with Base Station Not Found popup when there are no base stations available
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user DAS device with ADB ID "9c48da88" is deregistered and booted
Then user navigates to "Looking for Base Station" screen from the "Power Base Station" screen
Then user should receive a "Base Station Not Found" popup
And user "clicks on OK in" the "Base Station Not Found" popup
Then user should be displayed with the "Power Base Station" screen
When user DAS device with ADB ID "9c48da88" is deregistered and booted
Then user navigates to "Looking for Base Station" screen from the "Power Base Station" screen
And user should receive a "Base Station Not Found" popup
Then user "retries base station pairing in" the "Base Station Not Found" popup
Then user should receive a "Base Station Not Found" popup
And user "clicks on OK in" the "Base Station Not Found" popup
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYWhenQRCodeIsNotScannedAndThenScanned	 		@P3			@UIAutomated
Scenario Outline: As a user I should be prompted with Scanning Failure screen when QR code is not scanned
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When QR code is not scanned for "2" minutes
Then user should receive a "scanning failure" popup
When user "accepts" the "scanning failure" popup
Then user should be displayed with the "Register Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYWhenInvalidQRCodeIsScannedFirstAndThenScanAValidQRCode 	@P3				@UIAutomated
Scenario Outline: As a user my DAS device should not be configured when invalid QR code is scanned
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans an invalid QR code
Then user should receive a "scanning failure" popup
When user "accepts" the "scanning failure" popup
Then user should be displayed with the "Register Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      

#CannotAutomate
@DIYTapOnCancelMultipleTimesInRegisterBaseStationScreen		@P2
Scenario Outline: As a user I should be able to tap on Cancel multiple times in Register Base Station screen
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user "cancels the set up" by clicking on "cancel" button multiple times
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Register Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Dashboard" screen from the "Register Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYCancelInRegisterBaseStation	 	@P2			@UIAutomated
Scenario Outline: As a user I should be able to Cancel the setup in Register Base Station screen
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Register Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Add New Device Dashboard" screen from the "Register Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      

@DIYCancelInSelectBaseStationsListScreen		@RequiresMultipleBaseStationsForExecution		@P2			@UIAutomated
Scenario Outline: As a user I should be able to cancel the setp up in select base station screen when multiple base stations are displayed
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user DAS device with ADB ID "9c43dac2" is deregistered and booted
When user launches and logs in to the Lyric application
And user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Select Base Station" screen from the "Power Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Select Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Add New Device Dashboard" screen from the "Select Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


#CannotAutomate
@DIYDisconnectDASDevice		@P3
Scenario Outline: As a user I should be prompted with Bluetooth disconnected popup when DAS device is disconnected
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user disconnects the DAS device
Then user should receive a "Bluetooth Disconnected" popup
When user clicks on "OK" button
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
Then user should be displayed with the "Connect to Network" screen
When user disconnects the DAS device
Then user should receive a "Bluetooth Disconnected" popup
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

#CannotAutomate
@DIYTimeoutInDASDevice		@P3
Scenario Outline: As a user I should be prompted with Bluetooth disconnected popup when timeout happens in DAS device
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user waits until timeout happens in DAS device
Then user should receive a "Bluetooth Disconnected" popup
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     | 

#CannotAutomate
@DIYTurnOffMobileDeviceBluetooth		@P2
Scenario: As a user I should be prompted with Bluetooth is off popup when mobile device Bluetooth is off
Given user launches and logs in to the Lyric application
When user turns "off" mobile device Bluetooth
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
When user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
And user clicks on "Next" button
Then user should receive a "Bluetooth is Off" popup
And user "clicks on OK" in "Bluetooth is Off" popup
Then user should be displayed with the "Power Base Station" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Power Base Station" screen
And user turns "off" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should receive a "Bluetooth is Off" popup
And user "clicks on SETTINGS" in "Bluetooth is Off" popup
Then user should be displaeyd with the "Device Settings" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and clicks on "Next" button
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
Then user should be displayed with "Register Base Station" screen
When user turns "off" mobile device Bluetooth
Then user should receive a "Bluetooth Disconnected" popup
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Power Base Station" screen
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
Then user should be displayed with "Register Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user turns "off" mobile device Bluetooth
Then user should receive a "Bluetooth Disconnected" popup
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen


@DIYCancelSetUpInConnectToNetworkScreen	 	@P2			@UIAutomated
Scenario Outline:: As a user I should be able to cancel set up in Connect to Network screen
#Given user DAS device with ADB ID "9c48da88" is deregistered and booted
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen 
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user "cancels the connect to wifi network" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user "cancels the connect to network" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


#CannotAutoamte
@DIYMoveAwayFromDASDeviceAfterScanningQRCode		@P3
Scenario Outline: As a user I should be prompted with Bluetooth Disconnected popup when I move away from DAS device after scanning the QR code
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
Then user should receive a "Bluetooth Disconnected" popup
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Connect" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
And user should be displayed with the "Register Base Station" screen 
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user moves away from DAS Device
Then user should receive a "Bluetooth Disconnected" popup
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYAddAWiFiNetworkAndRegisterDAS    @Setuprequired		@P2
Scenario Outline: As a user I should be able to add a new network
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
And user "views connect to network screen" by clicking on "Refresh" button
When user clicks on "Add A Network" button
Then user should be displayed with the "Connect to Network" screen
And user clicks on "security type" button
When user enters valid SSID and navigates to Password screen
And user clicks on Back button
Then user should be displayed with the SSID screen
When user inputs <invalid SSID>
And user inputs "vibex888" as the WiFi Password
Then user should receive a "WiFi Connection timeout" popup
And user clicks on "RETRY" button
Then user should receive a "WiFi Connection timeout" popup
And user clicks on "OK" button
Then user should be displayed with the "Connect to Network" screen
When user enters "valid" new network credentials and tries to join the network
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | invalid SSID        |
      | Home                                    | Living Room                     | thisisinvalidssid   |


@DIYCancelAddANetworkAndTryConnectingWithAvailableNetwork  	@P2			@UIAutomated
Scenario Outline: As a user I want to register a DAS device by connecting to available network after cancelling add a network
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Add A Network" from "Connect to Network" screen
And user selects <Network Type> from "Add A Network" screen
Then user should be displayed with the "Enter Network Credentials" screen
When user "Cancels the enter SSID" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Enter Network Credentials" screen
When user "Cancels the enter SSID" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen
When user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
Then user navigates to "Choose Location" screen from the "What To Expect" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name | device name  | Network Type	|
      | Home          | Living Room  | WEP PSK		|

@DIYRegistrationWhenSingleBaseStationIsAvailable	 	@P1			@UIAutomated
Scenario Outline: As a user I want to register a DAS device using the Lyric application by disabling geofencing and ignorning alexa setup
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      | Home                                    | Kitchen                         |
      
@DIYRegistrationWithSkipGeoFenceAndSetUpAlexa	 @P2			@UIAutomated
Scenario Outline: As a user I want to register a DAS device using the Lyric application by skiping geofencing and setting up alexa
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
When user "cancels enabling geofence" by clicking on "Cancel" button
Then user should receive a "Cancel Geofence" popup
When user "dismisses" the "Cancel Geofence" popup
Then user should be displayed with the "Geofence" screen
When user "cancels enabling geofence" by clicking on "Cancel" button
Then user should receive a "Cancel Geofence" popup
When user "accepts" the "Cancel Geofence" popup
Then user should be displayed with the "Enable Geofencing" screen
When user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
And user enables Amazon Alexa with <Amazon username> and <Amazon password>
Then user should be displayed with the "Dashboard" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
Then user should be displayed with "Sign Out" button on the "Amazon Alexa" screen
When user navigates to "Base Station Configuration" screen from the "Amazon Alexa Settings" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | Amazon username				| Amazon password		|
      | Home                                    | Living Room                     | xyx.xyx@xyx.com				| xyxxyxxyx				|


@DIYRegistrationWithGeoFenceEnabledAndSetUpAlexa	 	@P1			@UIAutomated
Scenario Outline: As a user I want to register a DAS device using the Lyric application by enabling geofencing and setting up alexa
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
When user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user enables Amazon Alexa with <Amazon username> and <Amazon password>
Then user should be displayed with the "Dashboard" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
Then user should be displayed with "Sign Out" button on the "Amazon Alexa" screen
When user navigates to "Base Station Configuration" screen from the "Amazon Alexa Settings" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | Amazon username				| Amazon password		|
      | Home                                    | Living Room                     | xyx.xyx@xyx.com				| xyxyxxyx				|
      
      
@DIYRegistrationWhenFirmwareIsNotUpToDate		@FirmwareWithPreviousVersionRequired			@P2				@UIAutomated
Scenario Outline: As a user I want to register a DAS device using the Lyric application when firmware update pops up
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user should be displayed with the "Smart Home Security Success" screen
When user receives "Firmware update" by clicking on "Yes" button
And user "accepts" the "Firmware update" popup
Then user should be displayed with the "Enable Geofencing" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |	
      | Home                                    | Kitchen                         |

      
@DIYRegistrationByMinimizingAndMaximizingTheApp		@P3			@UIAutomated
Scenario Outline: As a user I want to register a DAS device using the Lyric application by navigating to other apps intermittently
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user minimizes and maximizes the app
And user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
When user minimizes and maximizes the app
Then user should be displayed with the "Smart Home Security Success" screen
When user minimizes and maximizes the app
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
When user minimizes and maximizes the app
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user minimizes and maximizes the app
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
When user minimizes and maximizes the app
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
And user minimizes and maximizes the app
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYRegistrationWithInvalidNetworkPwdAndTryReconnectingWithValidPwd	 	@P2			@UIAutomated
Scenario Outline: As a user I want to register a DAS device by connecting to available network after trying connecting to a invalid Wi-Fi network
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex444" as the WiFi Password 
Then user should receive a "Wi-Fi Connection Failed" popup
When user "dismisses" the "Wi-Fi Connection Failed" popup
Then user should be displayed with the "Enter your Wi-Fi password" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYRegistrationByConnectingToOpenWiFiNetwork     @SetUpRequired		@P2
Scenario Outline: As a user I should not be able to connect to a open Wi-Fi network and able to perform DAS registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
When user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user selects "Open Wi-Fi Network"
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Smart Home Security" screen
When user clicks on "No" button
Then user should be displayed with the "Enable Geofencing" screen
And user clicks on "SKIP" button
Then user should be displayed with the "Enable Amazon Alexa" screen
And user clicks on "SKIP" button
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |
      

@DIYRegistrationWhenNetworkConnectivityIsLow 		@P3
Scenario Outline: As a user I want to register a DAS device using the Lyric application when network connectivity is low
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
When connected internet is low or there is no internet connection
Then user should receive a "No Internet Connection" popup
When user "dismisses" the "No Internet Connection" popup
Then user should be displayed with "Connect to Network" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |


@DIYAddAWiFiNetworkWithInvalidPwdAndTryReconnectingWithAvailableNetwork	 	@P2			@UIAutomated
Scenario Outline: As a user I want to register a DAS device by connecting to available network after trying connecting to a invalid Wi-Fi network 
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Any WiFi name" from "Connect to Network" screen
And user inputs "Vibe444" as the WiFi Password
Then user should receive a "Wi-Fi Connection Failed" popup
When user "dismisses" the "Wi-Fi Connection Failed" popup
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user navigates to "Connect to Network" screen from the "Enter your Wi-Fi password" screen
Then user selects "Lenovo VIBE X3" from "Connect to Network" screen
When user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYTryToReRegisterDAS		 	@P2			@UIAutomated
Scenario Outline: As a user I should be prompted with base station not found popup when I try to reregister DAS using the Lyric application
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <first device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <first device name> device on the "dashboard" screen
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <second device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Looking for Base Station" screen from the "Power Base Station" screen
Then user should receive a "Base Station Not Found" popup
When user "retries base station pairing in" the "Base Station Not Found" popup
Then user should receive a "Base Station Not Found" popup
When user "clicks on OK in" the "Base Station Not Found" popup
Then user should be displayed with the "Power Base Station" screen
When user navigates to "dashboard" screen from the "Power Base Station" screen
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <first device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <first device name> device on the "dashboard" screen

Examples: 
      | location name | first device name	| second device name		|
      | Home          | Living Room  		| Kitchen				|
      

@DIYDeleteExistingDASAndRegisterIt		@P2			@UIAutomated
Scenario Outline: As a user I want to register a deleted DAS device using the Lyric application
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
      | location name | device name  | 
      | Home          | Living Room  |
      

@DIYMultipleDASRegistrationsForTheSameAccount		@P2			@UIAutomated
Scenario Outline: As a user I want to register multiple DAS devices for a single account using the Lyric application
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Select Base Station" screen from the "Power Base Station" screen
When user selects <Base Station MAC ID> from "Select Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <first device name> device on the "dashboard" screen
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <second device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with <> device on the "dashboard" screen
And user should be displayed with <second device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <first device name> device on the "dashboard" screen
And user should not be displayed with <second device name> device on the "dashboard" screen

Examples: 
      |	location name	|	first device name	|	second device name	|	Base Station MAC ID		| first location name in dashboard     | first device name in dashboard       | second location name in dashboard     | second device name in dashboard       |
      |	Home			|	Living Room			|	Kitchen				|	B8:2C:A0:00:07:D8		| Home Security                        | Living Room Camera                   | Home Security                         | Kitchen Camera                        | 


@DIYTryToReRegisterDASAfterPerformingFactorySettingsOnDAS			@P2
Scenario Outline: As a user I should be prompted with device already registered popup when I try to reregister DAS after performing factory settings on the registered DAS
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Select Base Station" screen from the "Power Base Station" screen
When user selects <Base Station MAC ID> from "Select Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user performs factory settings on the registered DAS
Then user should be displayed with panel status as Offline in Dashboard
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Select Base Station" screen from the "Power Base Station" screen
When user selects <Base Station MAC ID> from "Select Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen
Then user should be displayed with das panel status and time

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |


@DASDIYConfigurationVerifyBackArrowFunctionality		@P2
Scenario Outline: Verify Back arrow functionality while registering DAS
#Given user DAS device with ADB ID "9c48da88" is deregistered and booted
Given user launches and logs in to the Lyric application
When user navigates to the <Current Screen> screen
And user clicks on the back arrow in the <Current Screen> screen
Then user should be displayed with the <Previous Screen> screen

Examples:
| Current Screen				| Previous Screen				|
| What To Expect				| Add New Device					|
| Choose Location			| What To Expect					|
| Create Location			| Choose Location				|
| Name Your Base Station		| Choose Location				|
| Power Base Station			| Name Your Base Station			|
#| Enter your Wi-Fi password	| Connect to Network				|
#| Set Up accessories         | Smart Home Security Success	|

@DIYRegistrationWithAccessSensorEnrollmentWithDefaultName		@P2
Scenario Outline: As a user I should be able to successfully enrol Access Sensor with default sensor name through DIY registration
#Given user DAS device with ADB ID "9c48da88" is deregistered and booted
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user should be displayed with the "Smart Home Security Success" screen
When user navigates to "Set up Accessories" screen from the "Smart Home Security Success" screen
#And user <Sensor Location> access sensor "enrolled"
When user selects "Access Sensor SETUP Button" from "Set Up Accessories" screen
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
#When user <Sensor Location> access sensor "Opened"
Then user should see the <Sensor Location> status as <Access Status> on the "Test Access Sensor"
#When user <Sensor Location> access sensor "closed"
Then user should see the <Sensor Location> status as <Access Status Update> on the "Test Access Sensor"
And user "should not be displayed" with the "Test sensor screen cancel" option 
And user "should not be displayed" with the "Test sensor screen back" option 
When user selects "Done" from "Test Sensor" screen
Then user should see the <Sensor Location> status as "configured" on the "Set Up Accessories"
When user selects "Done" from "Set Up Accessories" screen
Then user should be displayed with the "Enable Geofencing" screen
When user navigates to "Geofence" screen from the "Enable Geofencing" screen
Then user navigates to "Geofence Enabled" screen from the "Geofence" screen
When user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user enables Amazon Alexa with <Amazon username> and <Amazon password>
Then user should be displayed with the "Dashboard" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Amazon Alexa Settings" screen from the "Dashboard" screen
Then user should be displayed with "Sign Out" button on the "Amazon Alexa" screen
When user navigates to "Sensor List Settings" screen from the "Amazon Alexa Settings" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor List"
When user navigates to "Security Solution card" screen from the "Sensor List" screen
When user navigates to "Sensor Status" screen from the "Security Solution Card" screen
Then user should see the <Sensor Location> status as "closed" on the "Sensor Status"
When user navigates to "Dashboard" screen from the "Security Solution Card" screen
Then user navigates to <Access Setting screen> screen from the "Dashboard" screen
When user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with <Sensor Location> device on the "sensor list" screen
When user navigates to "Base Station Configuration" screen from the "sensor list" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "dismisses" the "Delete DAS Confirmation" popup
Then user "deletes DAS device" by clicking on "delete" button
And user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|Sensor Location	| Sensor Location Area	| Access Status	| Access Status Update	| Access Setting screen		| Amazon username				| Amazon password		|
#| Home			| Living Room		| Door			| Front Door				| Open			| Closed					| Door Access Settings		| midhun.gollapalli@gmail.com	| xyxyxxyx				|
| Home			| Living Room		| Window 		| Living Room Window 	| Open 			| Closed 				| Window Access Settings		| midhun.gollapalli@gmail.com	| prabha@08				|
#incaserequired
#| Home			| Living Room		| Door			| Back Door				| Opened			| Closed					| Door Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Door			| Side Door				| Opened 		| Closed 				| Door Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window 		| Living Room Window 	| Opened 		| Closed 				| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window			| Dining Room Window 	| Opened			| Closed 				| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window			| Kitchen Window 		| Opened			| Closed 				| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Door			| Front Door				| Opened 		| Closed 				| Door Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Door			| Back Door				| Opened 		| Closed 				| Door Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Door			| Side Door				| Opened 		| Closed 				| Door Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window			| Living Room Window 	| Opened			| Closed					| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window			| Dining Room Window 	| Opened 		| Closed 				| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|
#| Home			| Living Room		| Window			| Kitchen Window 		| Opened 		| Closed 				| Window Access Settings		| xyx.xyx@xyx.com				| xyxyxxyx				|

@DIYRegistrationWithAccessSensorEnrollmentWithCustomName		@P3
Scenario Outline: As a user I should be able to successfully enrol Access Sensor with custom sensor name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
And user triggers "ACCESS" sensor
Then user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
Then user navigates to "Name Sensor" screen from the "Locate Sensor" screen
When user navigates to "Custom Name Sensor" screen from the "Name Sensor" screen
And user inputs <Custom name> in the "Custom Name Sensor" screen
Then user should be displayed with the "Place Sensor" screen
And user navigates to "Place Sensor on location" screen from "Place Sensor" screen
When user selects "Won't Fit As shown?" from "Place Sensor on location" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Place Sensor on location" screen from the "Video clip" screen
And user navigates to "Test Sensor" screen from "Place Sensor on location" screen
Then user <Sensor Location> <Access Status>
And user <Sensor Location> <Access Status Update>
When user navigates to "Set Up Accessories Configured" screen from the "Test Sensor" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|Sensor Location | Sensor Location Area | Access Status | Access Status Update  | Custom name    |
| Home			| Living Room		| Door           | Front Door           | Opened        | Closed                | Honeywell      |
| Home			| Living Room		| Door           | Back Door            | Opened        | Closed                | Honeywell1     |
| Home			| Living Room		| Door           | Side Door            | Opened        | Closed                | Honeywell12    |


@DIYRegistrationWithAccessSensorEnrollmentByNavigatingToNotificationScreen		@P3     #Improvement
Scenario Outline: As an user I should not get any push notifications during DIY Flow for the DAS panel in DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
And user triggers "ACCESS" sensor
Then user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects <sensor location area> from "Name Sensor" screen
Then user should be displayed with the "Place Sensor" screen
And user navigates to "Place Sensor on location" screen from "Place Sensor" screen
When user selects "Won't Fit As shown?" from "Place Sensor on location" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Place Sensor on location" screen from the "Video clip" screen
And user navigates to "Test Sensor" screen from "Place Sensor on location" screen
Then user <Sensor Location> <Access Status>
And user <Sensor Location> <Access Status Update>
When user receives a "Sensor Close" push notification
Then user navigates to "Set Up Accessories Configured" screen from the "Test Sensor" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|Sensor Location | Sensor Location Area | Access Status | Access Status Update  |
| Home			| Living Room		| Door           | Front Door           | Opened        | Closed                |

@DIYRegistrationByCancellingAccessSensorEnrollmentAndSkipGeofencingAndEnableAlexa		@P3
Scenario Outline: As a user I should be able to successfully complete DIY registration by cancelling access sensor enrollment and skip geofencing but enabling alexa
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
And user triggers "ACCESS" sensor
When user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Sensor Overview" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Set Up Accessories" screen
When user clicks on back button
Then user should be displayed with the "Set Up Accessories Configured" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user enables "Amazon Alexa"
Then user should be displayed with the "Dashboard" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "dashboard" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|Sensor Location | Sensor Location Area | Access Status | Access Status Update  |
| Home			| Living Room		| Door           | Front Door           | Opened        | Closed                |


@DIYRegistrationWithAccessSensorEnrollmentWhenFirmwareIsNotUpToDate		@P3
Scenario Outline: As a user I should be able to successfully enrol Access Sensor with default sensor name through DIY registration when firmware is not upto date
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
When user receives a "Firmware update" popup
Then user "accepts" the "Firmware update" popup
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
And user triggers "ACCESS" sensor
Then user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects <Sensor Location> from "Name Sensor" screen
Then user should be displayed with the "Name Sensor" screen
When user selects <sensor location area> from "Name Sensor" screen
Then user should be displayed with the "Place Sensor" screen
And user navigates to "Place Sensor on location" screen from "Place Sensor" screen
When user selects "Won't Fit As shown?" from "Place Sensor on location" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Place Sensor on location" screen from the "Video clip" screen
And user navigates to "Test Sensor" screen from "Place Sensor on location" screen
Then user <Sensor Location> <Access Status>
And user <Sensor Location> <Access Status Update>
When user navigates to "Set Up Accessories Configured" screen from the "Test Sensor" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|Sensor Location | Sensor Location Area | Access Status | Access Status Update  |
| Home			| Living Room		| Door           | Front Door           | Opened        | Closed                |
| Home			| Living Room		| Door           | Back Door            | Opened        | Closed                |
| Home			| Living Room		| Door           | Side Door            | Opened        | Closed                |
| Home			| Living Room		| Window         | Living Room Window   | Opened        | Closed                |
| Home			| Living Room		| Window         | Dining Room Window   | Opened        | Closed                |
| Home			| Living Room		| Window         | Kitchen Window       | Opened        | Closed                |
| Home			| Living Room		| Door           | Front Door           | Opened        | Closed                |
| Home			| Living Room		| Door           | Back Door            | Opened        | Closed                |
| Home			| Living Room		| Door           | Side Door            | Opened        | Closed                |
| Home			| Living Room		| Window         | Living Room Window   | Opened        | Closed                |
| Home			| Living Room		| Window         |Dining Room Window    | Opened        | Closed                |
| Home			| Living Room		| Window         | Kitchen Window       | Opened        | Closed                |

@DIYRegistrationWithMotionViewerEnrollmentWithDefaultSensorName		@P2
Scenario Outline: As a user I should be able to successfully enrol Motion Viewer with default name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
When user triggers "MOTION" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
And user selects <Sensor Name> from "Name Sensor" screen
Then user should be displayed with the "Mount Sensor" screen
When user selects <Mount Sensor Name> from "Mount Sensor" screen
Then user should be displayed with the <Place Sensor> screen
And user navigates to "Test Sensor" screen from <Place Sensor> screen
Then user <Sensor Name> <Motion Status>
And user <Sensor Name> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Sensor" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Sensor Name    | Mount Sensor Name | Place Sensor      | Motion Status         | Motion Status Update |
| Home			| Living Room		| Front Hall     | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Front Hall     | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Hall      | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Hall      | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Living Room    | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Living Room    | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Front Hall     | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Front Hall     | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Hall      | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Hall      | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Living Room    | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Living Room    | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |

@DIYRegistrationWithMotionViewerEnrollmentWithCustomSensorName		@P3
Scenario Outline: As a user I should be able to successfully enrol Motion Viewer with custom name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
When user triggers "MOTION" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Locate Sensor" screen from the "Sensor Overview" screen
When user navigates to "Name Sensor" screen from the "Locate Sensor" screen
Then user navigates to "Custom Name Sensor" screen from the "Name Sensor" screen
When user inputs <Custom name> in the "Custom Name Sensor" screen
Then user should be displayed with the "Mount Sensor" screen
When user selects <Mount Sensor Name> from "Mount Sensor" screen
Then user should be displayed with the <Place Sensor> screen
And user navigates to "Test Sensor" screen from the <Place Sensor> screen
Then user <Sensor Name> <Motion Status>
And user <Sensor Name> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Sensor" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Custom Name    | Mount Sensor Name | Place Sensor      | Motion Status         | Motion Status Update |
| Home			| Living Room		| Honeywell      | In a Wall Corner  | Mount in a Corner | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Honeywell1     | Flat on a Wall    | Mount on the Wall | NO MOTION DETECTED    | MOTION DETECTED      |


@DIYRegistrationWithKeyFobEnrollment		@P2
Scenario Outline: As a user I should be able to successfully enrol Key Fob through DIY
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security" screen
When user triggers "KEYFOB" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Sensor Overview" Screen 
When user selects "Watch How-To video" from "Sensor Overview" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Sensor Overview" screen from the "Video clip" screen
Then user navigates to "Name Key Fob" screen from the "Sensor Overview" screen
And user inputs <Custom name> in the "Name Key Fob" screen
Then user should be displayed with the "Key Fob" screen
Then user navigates to "Set Up Accessories Configured" screen from the "Key Fob" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Keyfob" screen from the "Dashboard" screen
And user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup
And user "dismisses" the "Delete keyfob Confirmation" popup
And user "deletes keyfob" by clicking on "delete" button
Then user should receive a "Delete keyfob Confirmation" popup
And user "accepts" the "Delete keyfob Confirmation" popup
Then user should not be displayed with "keyfobs" on the "keyfob" screen
When user navigates to "Base Station Configuration" screen from the "keyfob" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Custom name   |
| Home			| Living Room		| Honeywell     |

@DIYRegistrationWithISMVEnrollmentWithDefaultSensorName		@P2
Scenario Outline: As a user I should be able to successfully enrol ISMV with default sensor name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security" screen
When user triggers "ISMV" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Locate Viewer" Screen 
When user selects "Watch How-To video" from "Locate Viewer" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Locate Viewer" screen from the "Video clip" screen
Then user navigates to "Name Viewer" screen from the "Locate Viewer" screen
And user selects <Viewer Location> from "Name Viewer" screen
Then user should be displayed with the "Place Viewer" screen
When user selects <Place Viewer Area> from "Place Viewer" screen
Then user should be displayed with the "Place Viewer On Location" screen
And user navigates to "Test Motion Viewer" screen from "Place Sensor on location" screen
Then user <Viewer Location> <Motion Status>
And user <Viewer Location> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Motion Viewer" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Viewer Location    | Place Viewer Area             | Motion Status         | Motion Status Update  |
| Home			| Living Room		| Living Room        | On a Shelf                    | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Living Room        | In a Corner                   | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Living Room        | Flat on a Wall with Adhesive  | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Living Room        | Flat on a Wall with Screws    | NO MOTION DETECTED    | MOTION DETECTED       |
#Incaserequired
| Home			| Living Room		| Front Hall         | On a Shelf                   | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Front Hall         | In a Corner                  | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Front Hall         | Flat on a Wall with Adhesive | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Front Hall         | Flat on a Wall with Screws   | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Back Hall          | On a Shelf                   | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Back Hall          | In a Corner                  | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Back Hall          | Flat on a Wall with Adhesive | NO MOTION DETECTED |MOTION DETECTED |
| Home			| Living Room		| Back Hall          | Flat on a Wall with Screws   | NO MOTION DETECTED |MOTION DETECTED |


@DIYRegistrationWithISMVEnrollmentWithCustomSensorName		@P3
Scenario Outline: As a user I should be able to successfully enrol ISMV with custom sensor name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security" screen
When user triggers "ISMV" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Locate Viewer" Screen 
When user selects "Watch How-To video" from "Locate Viewer" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Locate Viewer" screen from the "Video clip" screen
Then user navigates to "Name Viewer" screen from the "Locate Viewer" screen
When user navigates to "Custom Name Sensor" screen from the "Name Viewer" screen
And user inputs <Custom name> in the "Custom Name Sensor" screen
Then user should be displayed with the "Place Viewer" screen
When user selects <Place Viewer Area> from "Place Viewer" screen
Then user should be displayed with the "Place Viewer On Location" screen
And user navigates to "Test Motion Viewer" screen from "Place Sensor on location" screen
Then user <Custom Name> <Motion Status>
And user <Custom Name> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Motion Viewer" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Custom name        | Place Viewer Area             | Motion Status         | Motion Status Update  |
| Home			| Living Room		| Honeywell          | On a Shelf                    | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Honeywell1         | In a Corner                   | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Honeywell12        | Flat on a Wall with Adhesive  | NO MOTION DETECTED    | MOTION DETECTED       |
| Home			| Living Room		| Honeywell123       | Flat on a Wall with Screws    | NO MOTION DETECTED    | MOTION DETECTED       |

@DIYRegistrationWithOSMVEnrollmentWithDefaultSensorName		@P2
Scenario Outline: As a user I should be able to successfully enrol OSMV with default sensor name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security" screen
When user triggers "OSMV" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Locate Viewer" Screen 
When user selects "Watch How-To video" from "Locate Viewer" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Locate Viewer" screen from the "Video clip" screen
Then user navigates to "Name Viewer" screen from the "Locate Viewer" screen
And user selects <Viewer Location> from "Name Viewer" screen
Then user should be displayed with the "Name Viewer Placement" screen
When user navigates to "Place viewer Wall" screen from the "Name Viewer Placement" screen
Then user navigates to "Place viewer Arm" screen from the "Place viewer Wall" screen
Then user navigates to "Place viewer motion viewer" screen from the "Place viewer Arm" screen
Then user navigates to "Place viewer Adjust viewer" screen from the "Place viewer motion viewer" screen
And user navigates to "Test Motion Viewer" screen from "Place viewer Adjust viewer" screen
Then user <Viewer Location> <Motion Status>
And user <Viewer Location> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Motion Viewer" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Viewer Location    | Motion Status         | Motion Status Update |
| Home			| Living Room		| Front Porch        | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Porch         | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Driveway           | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Garage             | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Patio              | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Front Porch        | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Back Porch         | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Driveway           | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Garage             | NO MOTION DETECTED    | MOTION DETECTED      |
| Home			| Living Room		| Patio              | NO MOTION DETECTED    | MOTION DETECTED      |


@DIYRegistrationWithOSMVEnrollmentWithCustomSensorName		@P3
Scenario Outline: As a user I should be able to successfully enrol OSMV with custom sensor name through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security" screen
When user triggers "OSMV" sensor
And  user selects "SETUP button" from "Set Up Accessories" screen
Then user should be displayed with the "Locate Viewer" Screen 
When user selects "Watch How-To video" from "Locate Viewer" screen
Then user should be displayed with the "Video clip" screen
When user navigates to "Locate Viewer" screen from the "Video clip" screen
When user navigates to "Custom Name Sensor" screen from the "Name Viewer" screen
And user inputs <Custom name> in the "Custom Name Sensor" screen
Then user should be displayed with the "Name Viewer Placement" screen
When user navigates to "Place viewer Wall" screen from the "Name Viewer Placement" screen
Then user navigates to "Place viewer Arm" screen from the "Place viewer Wall" screen
Then user navigates to "Place viewer motion viewer" screen from the "Place viewer Arm" screen
Then user navigates to "Place viewer Adjust viewer" screen from the "Place viewer motion viewer" screen
And user navigates to "Test Motion Viewer" screen from "Place viewer Adjust viewer" screen
Then user <Viewer Location> <Motion Status>
And user <Viewer Location> <Motion Status Update>
Then user navigates to "Set Up Accessories Configured" screen from the "Test Motion Viewer" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		| Custom name    | Motion Status         | Motion Status Update |
| Home			| Living Room		| Honeywell      | NO MOTION DETECTED    | MOTION DETECTED      |


@DIYRegistrationWithSensorBulkEnrollment    @P1
Scenario Outline: As a user I should be able to successfully enrol various types of sensors through DIY registration
Given user DAS device with ADB ID "9c48da88" is deregistered and booted
And user launches and logs in to the Lyric application
When user navigates to "Smart Home Security Success" screen after registering the base station
And user navigates to "Set up Accessories" screen from "Smart Home Security Success" screen
When user triggers "ACCESS" sensor
And user adds the access sensor
Then user should be displayed with "Set Up Accessories Configured" screen
Then user navigates to "Set up Accessories" screen from "Set Up Accessories configured" screen
When user triggers "MOTION" sensor
And user adds the motion sensor
Then user should be displayed with "Set Up Accessories Configured" screen
Then user navigates to "Set up Accessories" screen from "Set Up Accessories configured" screen
When user triggers "KEYFOB" sensor
And user adds the keyfob sensor
Then user should be displayed with "Set Up Accessories Configured" screen
Then user navigates to "Set up Accessories" screen from "Set Up Accessories configured" screen
When user triggers "ISMV" sensor
And user adds the ISMV sensor
Then user should be displayed with "Set Up Accessories Configured" screen
Then user navigates to "Set up Accessories" screen from "Set Up Accessories configured" screen
When user triggers "OSMV" sensor
And user adds the OSMV sensor
Then user should be displayed with "Set Up Accessories Configured" screen
And user navigates to "Enable Geofencing" screen from the "Set Up Accessories configured" screen
Then user navigates to "Geofence" screen from the "Enable Geofencing" screen
And user navigates to "Geofence Enabled" screen from the "Geofence" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Geofence Enabled" screen
And user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Sensor Settings" screen from the "Dashboard" screen
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "dismisses" the "Delete Sensor Confirmation" popup
And user "deletes sensor" by clicking on "delete" button
Then user should receive a "Delete Sensor Confirmation" popup
And user "accepts" the "Delete Sensor Confirmation" popup
Then user should not be displayed with "sensors" on the "sensors" screen
When user navigates to "Base Station Configuration" screen from the "Sensor" screen
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
When user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on the "dashboard" screen
And user should not be displayed with <device name> device on the "dashboard" screen

Examples:
| location name	| device name		|
| Home			| Living Room		|