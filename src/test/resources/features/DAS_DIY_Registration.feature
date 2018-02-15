@DASDIY
Feature: DAS DIY Registration
As a user I want to register a DAS device using the Lyric application


@DIYSmartHomeSecurityOptionInAddNewDevice
Scenario: As a user I should be able to see Smart Home Security option in Add New Device screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen form the "Dashboard" screen
Then user should be able to view "Smart Home Security" option
And user navigates to "Add new device(global drawer)" screen form the "Global drawer" screen
Then user should be able to view "Smart Home Security" option

@DIYCancelSetUp
Scenario: User should be able to cancel the set up from choose location and name your base station screens
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen form the "Dashboard" screen
And user selects “Smart Home Security” from “Add device” screen
Then user should be displayed with the "Choose Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" pop up
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Choose Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" pop up
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Add New Device Dashboard" screen from "Choose Location" screen
Then user selects “Smart Home Security” from “Add device” screen
Then user should be displayed with the "Choose Location" screen
When user "selects a Location" by clicking on "Home" button
Then user should be displayed with the "Name Your Base Station" screen
And user clicks on "cancel" button
Then user should receive a "Cancel Setup" pop up
When user "dismiss" the "Cancel Setup" popup
Then user should be displayed with the "Choose Location" screen
And user clicks on "cancel" button
Then user should receive a "Cancel Setup" pop up
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Choose Location" screen from "Name Your Base Station" screen

@DIYConfirmYourAddressZipCode
Scenario Outline: As a user I should be navigated to zip code screen for the entered custom location when location services are enabled
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen form the "Dashboard" screen
Then user selects “Smart Home Security” from “Add device” screen
And user should be displayed with the "Choose Location" screen
Then user inputs <location name> as the custom location
Then user should be displayed with the "Confirm your address ZIP Code" screen
And user inputs "invalid ZIP code" as address ZIP code
Then user should receive a "ZIP code Validation Error" pop up
When user "accepts" the "ZIP code Validation Error" pop up
And user inputs  "valid ZIP code" as address ZIP code
Then user should be displayed with the "Name Your Base Station" screen
And user clicks on "Back arrow" button
Then user should be displayed with the "Choose Location" screen
And <location name> "should" be displayed in the list of locations
And user clicks on "Back arrow" button
Then user should be displayed with the "Select a device to install" screen
And user clicks on "Back arrow" button
Then user should be displayed with the "Add New Device Dashboard" screen
And <location name> "should" be displayed in the header section
When user navigates to "Global drawer" screen form the "Add New Device Dashboard" screen
Then user should be displayed with the "Location details" option
And user clicks on "Location details" button
Then user should be displayed with the "Location details" screen
When user updates <location name> from <updated location name>
And user navigates to "Choose Location" screen form the "Location details(global drawer)" screen
Then <updated location name> should be displayed in the list of locations
And user clicks on "Back arrow" button
Then user should be displayed with the "Select a device to install" screen
And user clicks on "Back arrow" button
Then user should be displayed with the "Add New Device Dashboard" screen
And <updated location name> "should" be displayed in the header section
When user navigates to "Global drawer" screen form the "Add New Device Dashboard" screen
Then user should be displayed with the "Location details" option
And user clicks on "Location details" button
Then user should be displayed with the "Location details" screen
When user deletes the location
Then user should be displayed with the "Add New Device Dashboard" screen
And <updated location name> "should not" be displayed in the header section
When user selects “Smart Home Security” from “Add device” screen
Then user should be displayed with the "Choose Location" screen
And <updated location name> "should not" be displayed in the list of locations

Examples:
    |  location name  |  updated location name  |
    |  Office         |  Gym                    |

@DIYDenyAppAccessToLocationServices
Scenario: As a user I should be prompted with Location services popup when location services access is denied after installation
Given user denies location services access while launching the Lyric app after installation and then logs in to the Lyric app
When user navigates to "Add New Device Dashboard" screen form the "Dashboard" screen
And user selects “Smart Home Security” from “Add device” screen
Then user should be displayed with the "Choose Location" screen
And user clicks on "Location" button
Then user should be displayed with the "Name Your Base Station" screen
And user clicks on "base station name" button
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user clicks on "Next" button
Then user should receive a "Location services" pop up
And user "denies access" in "Location services" pop up
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should receive a "Location services" pop up
And user "allows access" in "Location services" pop up
Then user should be displayed with the "Looking for Base Station" screen
When user turns "off" location services for Lyric app in mobile settings
And user navigates to "Power Base Station Instructions" screen from "Power Base Station" screen
Then user should receive a "Location services" pop up
And user "allows access" in "Location services" pop up
Then user should be displayed with the "Looking for Base Station" screen 

@DIYWhenNoBaseStationsAreAvailable
Scenario: As a user I should be prompted with Base Station Not Found popup when there are no base stations available
Given No base stations are available for pairing
And user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
Then user should receive a "Base Station Not Found" pop up
And user "clicks on OK" in "Base Station Not Found" pop up
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
And user should receive a "Base Station Not Found" pop up
Then user "retries base station pairing" in "Base Station Not Found" pop up
And user should be displayed with the "Looking for Base Station" screen

@DIYCancelSetUpInRegisterBaseStation
Scenario: As a user I should be able to cancel set up in Register Base Station screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user clicks on "Back arrow" button
Then user should receive a "Cancel Setup" pop up
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Register Base Station" screen
When user clicks on "Back arrow" button
Then user should receive a "Cancel Setup" pop up
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

@DIYWhenQRCodeIsNotScanned
Scenario: As a user I should be prompted with Scanning Failure screen when QR code is not scanned
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user does not scan QR code for "2" minutes
Then user should receive a "scanning failure" pop up
When user clicks on "RETRY" button
Then user should be displayed with the "Register Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen

@DIYWhenInvalidQRCodeIsScanned
Scenario: As a user my DAS device should not be configured when invalid QR code is scanned
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans "invalid" QR code
Then user should receive a "scanning failure" pop up
When user clicks on "RETRY" button
Then user should be displayed with the "Register Base Station" screen
And user scans "valid" QR code
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Refresh" button
Then "Connect to Network" screen should refresh and update the "WiFi list"

@DIYRefreshBaseStationsList
Scenario: As a user I should be able to refresh the base stations list
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When there are multiple base stations available
Then user should be displayed with the "Select Base Station" screen with list of Mac IDs for each base station
And user clicks on "Refresh" button
When there is one base station available
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen

@DIYDisconnectDASDevice
Scenario: As a user I should be prompted with Bluetooth disconnected pop up when DAS device is disconnected
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
When user disconnects the DAS device
Then user should receive a "Bluetooth Disconnected" pop up
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
And user should be displayed with the "Register Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user disconnects the DAS device
Then user should receive a "Bluetooth Disconnected" pop up
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

@DIYTimeoutInDASDevice
Scenario: As a user I should be prompted with Bluetooth disconnected pop up when timeout happens in DAS device
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user waits until timeout happens in DAS device
Then user should receive a "Bluetooth Disconnected" pop up
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

@DIYTurnOffMobileDeviceBluetooth
Scenario: As a user I should be prompted with Bluetooth is off popup when mobile device Bluetooth is off
Given user launches and logs in to the Lyric application
When And user turns "off" mobile device Bluetooth
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
And user clicks on "Next" button
Then user should receive a "Bluetooth is Off" pop up
And user "clicks on OK" in "Bluetooth is Off" pop up
Then user should be displayed with the "Power Base Station" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user turns "off" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should receive a "Bluetooth is Off" pop up
And user "clicks on SETTINGS" in "Bluetooth is Off" pop up
Then user should be displaeyd with the "Device Settings" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
Then user should be displayed with "Register Base Station" screen
When user turns "off" mobile device Bluetooth
Then user should receive a "Bluetooth Disconnected" pop up
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
Then user should be displayed with "Register Base Station" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user turns "off" mobile device Bluetooth
Then user should receive a "Bluetooth Disconnected" pop up
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen

@DIYCancelSetUpInConnectToNetworkScreen
Scenario: As a user I should be able to cancel set up in Connect to Network screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Add A Network" button
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" pop up
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" pop up
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

@DIYMoveAwayFromDASDeviceAfterScanningQRCode
Scenario: As a user I should be prompted with Bluetooth Disconnected pop up when I move away from DAS device after scanning the QR code
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user should receive a "Bluetooth Disconnected" pop up
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user clicks on "Next" button
Then user should be displayed with the "Looking for Base Station" screen
When there is one base station available
And user should be displayed with the "Register Base Station" screen 
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user moves away from DAS Device
Then user should receive a "Bluetooth Disconnected" pop up
And user taps on "OK" button
Then user should be displayed with the "Power Base Station" screen

@DIYAddAWiFiNetwork
Scenario: As a user I should be able to add a new network
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Add A Network" button
Then user should be displayed with the "Connect to Network" screen
And user clicks on "security type" button
When user enters "invalid" new network credentials and tries to join the network
Then user should receive a "WiFi Connection timeout" pop up
And user clicks on "RETRY" button
Then user should receive a "WiFi Connection timeout" pop up
And user clicks on "OK" button
Then user should be displayed with the "Connect to Network" screen
When user enters "valid" new network credentials and tries to join the network
Then added network should be displayed in the list of networks in "Connect to Network" screen

@DIYInvalidWiFiPassword
Scenario: As a user I should not be able to connect to a Wi-Fi network with invalid password
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Invalid password" as the WiFi Password
And user clicks on "Next" button
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should receive a "Wi-Fi Connection Failed" pop up
And user clicks on "OK" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" pop up
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" pop up
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

@DIYRegistrationWhenSingleBaseStationIsAvailable
Scenario Outline: As a user I want to register a DAS device using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user navigates to "Connect to Network" screen from the "Register Base Station" screen
Then user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user logs out of the Lyric app and logs into the Lyric app again
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard 
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYRegistrationWithSensorsGeoFencingOnAndAlexaConnect
Scenario Outline: As a user I want to register a DAS device by adding sensor and enabling geofencing and alexa using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user logs out of the Lyric app and logs into the Lyric app again
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard 
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      | Office                                  | Scrum Room                      |
      | abcdefghijklmnopqrstuvwxyzabcd          | abcdefghijklmnopqrstuvwxyzefgh  |

@DIYMultipleDASRegistrationsForTheSameAccount
Scenario Outline: As a user I want to register multiple DAS devices for a single account using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with both "Security" devices on dashboard
And user should be displayed with <device name> device on dashboard
When user logs out of the Lyric app and logs into the Lyric app again
Then user should be displayed with both "Security" devices on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |


@DIYRegistrationByReceivingCallsAndMsgs
Scenario Outline: As a user I want to register a DAS device using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
And user scans the QR code by showing it to the base station camera
When user receives a message/notification/call
Then user should be able to attend the message/notification/call
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
When user receives a message/notification/call
Then user should be able to attend the message/notification/call
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user logs out of the Lyric app and logs into the Lyric app again
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard 
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      | Office                                  | Scrum Room                      |
      | abcdefghijklmnopqrstuvwxyzabcd          | abcdefghijklmnopqrstuvwxyzefgh  |

@DIYAddAWiFiNetworkWithInvalidPwdAndTryReconnectingWithAvailableNetwork
Scenario Outline: As a user I want to register a DAS device by connecting to available network after trying connecting to a invalid Wi-Fi network 
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user clicks on "security type" button
When user enters "invalid" new network credentials and tries to join the network
Then user should receive a "WiFi Connection timeout" pop up
And user clicks on "OK" button
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Smart Home Security" screen
When user clicks on "No" button
Then user should be displayed with the "Enable Geofencing" screen
And user clicks on "SKIP" button
Then user should be displayed with the "Enable Amazon Alexa" screen
And user clicks on "SKIP" button
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYConnectingToOpenWiFiNetwork
Scenario Outline: As a user I should not be able to connect to a open Wi-Fi network and able to perform DAS registration
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Open Wi-Fi Network"
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Smart Home Security" screen
When user clicks on "No" button
Then user should be displayed with the "Enable Geofencing" screen
And user clicks on "SKIP" button
Then user should be displayed with the "Enable Amazon Alexa" screen
And user clicks on "SKIP" button
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYConnectingToWiFiNetworkWithSecurityDisabled
Scenario Outline: As a user I should not be able to connect to a Wi-Fi network with security disbaled and able to perform DAS registration
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user clicks on "Network with security disabled" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Smart Home Security" screen
When user clicks on "No" button
Then user should be displayed with the "Enable Geofencing" screen
And user clicks on "SKIP" button
Then user should be displayed with the "Enable Amazon Alexa" screen
And user clicks on "SKIP" button
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYRegistrationByNavigatingToOtherApps
Scenario Outline: As a user I want to register a DAS device using the Lyric application by navigating to other apps intermittently
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
When user navigates to other apps and navigates back to Lyric app
Then user should be displayed with the "Connecting Smart Home Security" screen
When user navigates to other apps and navigates back to Lyric app
And user should be displayed with the "Almost Done" screen
When user navigates to other apps and navigates back to Lyric app
Then user should be displayed with the "Smart Home Security" screen
When user navigates to other apps and navigates back to Lyric app
When user clicks on "No" button
Then user should be displayed with the "Enable Geofencing" screen
When user navigates to other apps and navigates back to Lyric app
And user clicks on "SKIP" button
Then user should be displayed with the "Enable Amazon Alexa" screen
When user navigates to other apps and navigates back to Lyric app
And user clicks on "SKIP" button
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
When user navigates to other apps and navigates back to Lyric app
And user should be displayed with <device name> device on dashboard
When user navigates to other apps and navigates back to Lyric app
And user deletes <device name> "DAS" from <location name> through the application
When user navigates to other apps and navigates back to Lyric app

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYTryToReRegisterDAS
Scenario Outline: As a user I should be prompted with device already registered pop up when I try to reregister DAS using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Device already registered" screen
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYTryToReRegisterDASAfterPerformingFactorySettingsOnDAS
Scenario Outline: As a user I should be prompted with device already registered pop up when I try to reregister DAS after performing factory settings on the registered DAS
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user performs factory settings on the registered DAS
And user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user clicks on "Available Network" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user inputs "Valid password" as the WiFi Password
And user clicks on "Next" button
Then user should be displayed with the "Connecting Smart Home Security" screen
And user should be displayed with the "Almost Done" screen
Then user should be displayed with the "Device already registered" screen
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYTryToReRegisterDASWhenDASIsOffline
Scenario: As a user I should not see the DAS device in the list of base stations when I try to reregister DAS after turning off the DAS
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user switches off/unplugs the registered DAS
And user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user should not see the registered DAS device in the list of base stations

@DIYDeleteExistingDASAndRegisterIt
Scenario Outline: As a user I want to register a deleted DAS device using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
And user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
Then user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
And user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
Then user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
And user creates a passcode if required
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
And user deletes <device name> "DAS" from <location name> through the application

Examples:
      | location name | device name  | 
      | Home          | Living Room  |