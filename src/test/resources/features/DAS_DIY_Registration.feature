@DASDIY
Feature: DAS DIY Registration
As a user I want to register a DAS device using the Lyric application


@DIYSmartHomeSecurityOptionInAddNewDevice
Scenario: As a user I should be able to see Smart Home Security option in Add New Device screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user should be displayed with "Smart Home Security" option
When user navigates to "Add New Device in Global Drawer" screen from the "Dashboard" screen
Then user should be displayed with "Smart Home Security" option

@DIYCancelSetUp
Scenario: User should be able to cancel the set up from choose location and name your base station screens
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
Then user should be displayed with the "Choose Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Choose Location" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user navigates to "Add New Device Dashboard" screen from the "Choose Location" screen

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
Then user should receive a "Location services" popup
And user "denies access" in "Location services" popup
Then user should be displayed with the "Power Base Station" screen
And user clicks on "Next" button
Then user should receive a "Location services" popup
And user "allows access" in "Location services" popup
Then user should be displayed with the "Looking for Base Station" screen
When user turns "off" location services for Lyric app in mobile settings
And user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user should receive a "Location services" popup
And user "allows access" in "Location services" popup
Then user should be displayed with the "Looking for Base Station" screen 

@DIYWhenNoBaseStationsAreAvailable
Scenario Outline: As a user I should be prompted with Base Station Not Found popup when there are no base stations available
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Looking for Base Station" screen from the "Power Base Station Instructions" screen
Then user should receive a "Base Station Not Found" popup
And user "clicks on OK in" the "Base Station Not Found" popup
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Looking for Base Station" screen from the "Power Base Station Instructions" screen
And user should receive a "Base Station Not Found" popup
Then user "retries base station pairing in" the "Base Station Not Found" popup
Then user should receive a "Base Station Not Found" popup
And user "clicks on OK in" the "Base Station Not Found" popup

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYCancelSetUpInRegisterBaseStation
Scenario Outline: As a user I should be able to cancel set up in Register Base Station screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user "views cancel setup" by clicking on "Back arrow" button
And user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Register Base Station" screen
When user "views cancel setup" by clicking on "Back arrow" button
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYWhenQRCodeIsNotScanned
Scenario Outline: As a user I should be prompted with Scanning Failure screen when QR code is not scanned
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When QR code is not scanned for "2" minutes
Then user should receive a "scanning failure" popup
When user "accepts" the "scanning failure" popup
Then user should be displayed with the "Register Base Station" screen
And user scans the QR code by showing it to the base station camera
Then user should be displayed with the "Connect to Network" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYWhenInvalidQRCodeIsScanned
Scenario Outline: As a user my DAS device should not be configured when invalid QR code is scanned
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans an invalid QR code
Then user should receive a "scanning failure" popup
When user "accepts" the "scanning failure" popup
Then user should be displayed with the "Register Base Station" screen
And user scans the QR code by showing it to the base station camera

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYRefreshBaseStationsList
Scenario Outline: As a user I should be able to refresh the base stations list when multiple base stations are displayed
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Select Base Station" screen from the "Power Base Station Instructions" screen
And user "views select base station screen" by clicking on "Refresh" button

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYDisconnectDASDevice
Scenario: As a user I should be prompted with Bluetooth disconnected popup when DAS device is disconnected
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
When user disconnects the DAS device
Then user should receive a "Bluetooth Disconnected" popup
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
Then user should receive a "Bluetooth Disconnected" popup
When user clicks on "OK" button
Then user should be displayed with the "Power Base Station" screen

@DIYTimeoutInDASDevice
Scenario: As a user I should be prompted with Bluetooth disconnected popup when timeout happens in DAS device
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
Then user should receive a "Bluetooth Disconnected" popup
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
Then user should receive a "Bluetooth is Off" popup
And user "clicks on OK" in "Bluetooth is Off" popup
Then user should be displayed with the "Power Base Station" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
And user turns "off" mobile device Bluetooth
When user opens the DAS app and cicks on "Next" button
Then user should receive a "Bluetooth is Off" popup
And user "clicks on SETTINGS" in "Bluetooth is Off" popup
Then user should be displaeyd with the "Device Settings" screen
And user turns "on" mobile device Bluetooth
When user opens the DAS app and clicks on "Next" button
Then user should be displayed with the "Power Base Station Instructions" screen
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
Then user should be displayed with the "Power Base Station Instructions" screen
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

@DIYCancelSetUpInConnectToNetworkScreen
Scenario Outline:: As a user I should be able to cancel set up in Connect to Network screen
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user "adds a Network" by clicking on "Add A Network" button
And user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Add a Network" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |

@DIYMoveAwayFromDASDeviceAfterScanningQRCode
Scenario: As a user I should be prompted with Bluetooth Disconnected popup when I move away from DAS device after scanning the QR code
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user should receive a "Bluetooth Disconnected" popup
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
Then user should receive a "Bluetooth Disconnected" popup
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
Then user should receive a "WiFi Connection timeout" popup
And user clicks on "RETRY" button
Then user should receive a "WiFi Connection timeout" popup
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
And user should receive a "Wi-Fi Connection Failed" popup
And user clicks on "OK" button
Then user should be displayed with the "Enter your Wi-Fi password" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" popup
When user "dismisses" the "Cancel Setup" popup
Then user should be displayed with the "Connect to Network" screen
When user clicks on "Cancel" button
Then user should receive a "Cancel Setup" popup
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
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password 
Then user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
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
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

Examples: 
      | location name                           | device name                     | 
      | Home                                    | Living Room                     |
      
@DIYRegistrationWithNewLocationAndBaseStationName
Scenario Outline: As a user I want to register a DAS device with new location and base station name using the Lyric application
Given user launches and logs in to the Lyric application
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <invalid zip code>
Then user should receive a "Invalid zip code" popup
When user "dismisses" the "Invalid zip code" popup
When user inputs <valid zip code>
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
When user scans the QR code by showing it to the base station camera
Then user navigates to "Connect to Network" screen from the "Register Base Station" screen
When user selects "Lenovo VIBE X3" from "Connect to Network" screen
And user inputs "vibex888" as the WiFi Password
Then user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
#And user disables the passcode through CHIL
Then user should be displayed with "Security" device on dashboard
And user should be displayed with <device name> device on dashboard
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard
Then user "deletes location details" by clicking on "delete" button

Examples: 
      | location name                           | device name						| invalid zip code			| valid zip code				|
      | California                              | Scrum Room						| 55555						| 90001						|

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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

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
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Select Base Station" screen from the "Power Base Station Instructions" screen
When user selects a base station with MAC ID "B8:2C:A0:00:07:D8"
When user navigates to "Connect to Network" screen from the "Register Base Station" screen
Then user selects "Lenovo VIBE X3" from "Connect to Network" screen
When user inputs "vibex888" as the WiFi Password 
Then user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
When user selects <location name> from "Choose Location" screen
Then user should be displayed with the "Name Your Base Station" screen
When user selects <device name> from "Name Your Base Station" screen
Then user should be displayed with the "Power Base Station" screen
When user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
Then user navigates to "Select Base Station" screen from the "Power Base Station Instructions" screen
When user selects a base station with MAC ID "B8:2C:A0:00:07:D8"
When user navigates to "Connect to Network" screen from the "Register Base Station" screen
Then user selects "Lenovo VIBE X3" from "Connect to Network" screen
When user inputs "vibex888" as the WiFi Password 
Then user navigates to "Smart Home Security Success" screen from the "Connect to Network" screen
When user navigates to "Enable Geofencing" screen from the "Smart Home Security Success" screen
Then user navigates to "Enable Amazon Alexa" screen from the "Enable Geofencing" screen
When user navigates to "Dashboard" screen from the "Enable Amazon Alexa" screen
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

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
Then user should receive a "WiFi Connection timeout" popup
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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to other apps and navigates back to Lyric app
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

@DIYTryToReRegisterDAS
Scenario Outline: As a user I should be prompted with device already registered popup when I try to reregister DAS using the Lyric application
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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
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
Scenario Outline: As a user I should be prompted with device already registered popup when I try to reregister DAS after performing factory settings on the registered DAS
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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
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
Scenario Outline: As a user I should not see the DAS device in the list of base stations when I try to reregister DAS after turning off the DAS
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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user switches off/unplugs the registered DAS
And user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user navigates to "Smart Home Security" screen from the "Add New Device Dashboard" screen
And user navigates to "Choose Location" screen from the "Smart Home Security" screen
Then user navigates to "Name Your Base Station" screen from the "Choose Location" screen
And user navigates to "Power Base Station" screen from the "Name Your Base Station" screen
Then user navigates to "Power Base Station Instructions" screen from the "Power Base Station" screen
And user navigates to "Register Base Station" screen from the "Power Base Station Instructions" screen
Then user should not see the registered DAS device in the list of base stations

Examples: 
      | location name | device name  | 
      | Home          | Living Room  |

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
#And user creates a passcode if required
And user disables the passcode through CHIL
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to other apps and navigates back to Lyric app
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard
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
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to other apps and navigates back to Lyric app
Then user should be displayed with "Security" device on the "dashboard" screen
And user should be displayed with <device name> device on the "dashboard" screen
When user navigates to "Base Station Configuration" screen from the "Dashboard" screen 
And user "deletes DAS device" by clicking on "delete" button
Then user should receive a "Delete DAS Confirmation" popup
And user "accepts" the "Delete DAS Confirmation" popup
Then user should not be displayed with "Security" device on dashboard
And user should not be displayed with <device name> device on dashboard

Examples:
      | location name | device name  | 
      | Home          | Living Room  |