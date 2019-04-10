@DashboardScenarios         @Platform
Feature: Dashboard
As a user I want to verify the newly implemented dashboard status area in the dashboard screen


@DashboardCoachMarksWithLocationWithoutAnyDevice
Scenario:   To verify the dashboard coach marks after successfully sign into the app with location anad without any device
Given user launches and logs in to the Lyric application with user account with location and without any device and without closing the coach marks
Then user should be displayed with coach marks on top of the dashboard screen
When user navigates back and forth in "Coach Marks in Dashboard" screen
Then user should be displayed with "Weather status" in Dashboard status area


@DashboardCoachMarksByMinimizingAndMaximizingTheApp
Scenario:   To verify the dashboard coach marks after successfully sign into the app and by minmizing and maximizing the app
Given user launches and logs in to the Lyric application with user account with location and with any device and without closing the coach marks
Then user should be displayed with coach marks on top of the dashboard screen
When user minimizes and maximizes the app
Then user should be displayed with coach marks on top of the dashboard screen
When user "navigates to Dashboard screen" by clicking on "Coach Marks Done" button
Then user should be displayed with "Weather status" in Dashboard status area


@DashboardCoachMarksByKillAndRelaunchTheApp
Scenario:   To verify the dashboard coach marks after successfully sign into the app and by killing and relaunching the app
Given user launches and logs in to the Lyric application with user account with location and with any device and without closing the coach marks
Then user should be displayed with coach marks on top of the dashboard screen
When user kills and relaunches the app
Then user should be displayed with coach marks on top of the dashboard screen
When user "navigates to Dashboard screen" by clicking on "Coach Marks Done" button
Then user should be displayed with "Weather status" in Dashboard status area


@DashboardCoachMarksWhenAppIsInstalledForTheFirstTimeOnly
Scenario:   To verify the dashboard coach marks after successfully sign into the app for the first time after the app is installed
Given user launches and logs in to the Lyric application with user account with location and with any device and without closing the coach marks
Then user should be displayed with coach marks on top of the dashboard screen
When user "navigates to Dashboard screen" by clicking on "Coach Marks Done" button
Then user should be displayed with "Weather status" in Dashboard status area
When user logs out and logs in to the Lyric Application with "logged in users account"
Then user should not be displayed with coach marks on top of the dashboard screen


@DashboardCoachMarksBasedOnTheDeviceRegistered
Scenario:   To verify the dashboard coach marks based on the device registered after successfully sign into the app
Given user launches and logs in to the Lyric application with user account with location and with any device and without closing the coach marks
Then user should be displayed with coach marks on top of the dashboard screen
When user "navigates to Dashboard screen" by clicking on "Coach Marks Done" button
Then user should be displayed with "Weather status" in Dashboard status area


@DashboardStatusAreaWithLocationWithoutAnyDevice
Scenario:   To verify the dashboard status area in dashboard screen when there are no devices for a location
Given user launches and logs in to the Lyric application with user account with location
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Add New Device button should be displayed below Dashboard status area


@DashboardStatusAreaWithLocationWithAnyDevice
Scenario:   To verify the dashboard screen when there is a device for a location
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@DashboardStatusAreaWithWeatherFetchingStatus
Scenario:   To verify the dashboard screen while fetching the weather status
Given user launches and logs in to the Lyric application
When app is fetching the weather data
Then user should be displayed with the following "Weather" options:
| WeatherOptions				                        |
| Today's Forecast Header                               |
| Fetching Weather label                                |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@DashboardStatusAreaWithWeatherUnavailableStatus
Scenario:   To verify the dashboard screen when weather is unavailable
Given user launches and logs in to the Lyric application
When weather data is unavailable
Then user should be displayed with the following "Weather" options:
| WeatherOptions				                        |
| Today's Forecast Header                               |
| Weather Unavailable label                             |
| Check Back Later label                                |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyIfUserIsAbleToScrollVerticallyWhenAddNewDeviceIsVisible
Scenario:   To verify if user is able to scroll vertically when add new device icon is visible in the screen
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
And user should not be able to scroll vertically


@VerifyNavigationToWeatherScreenFromDashboardStatusArea
Scenario:   To verify navigation to Weather screen from dashboard status area
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user selects "Weather Status in Dashboard Status area" in "Dashboard" screen
Then user should be displayed with the "Weather Forecast" screen
And verify the current weather forecast for the location with temperature and weather status 
And Verify for three more forecast with interval of 6 hrs from current time 
And Verify the time format in app is same as mobile device
When user selects "Back button" from "Weather Forecast" screen
Then user should be displayed with the "Dashboard" screen
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Title                                |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyWeatherStatusInDashboardWhenTempUnitIsChangedInWeatherForecastScreen
Scenario:   To verify Weather status in Dashboard screen when temperature unit is changed in Weather Forecast screen
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
Then user should be displayed with "Celsius" temperature scale in "Add New Device Dashboard" screen
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user selects "Weather Status in Dashboard Status area" in "Dashboard" screen
Then user should be displayed with the "Weather Forecast" screen
And verify the current weather forecast for the location with temperature and weather status 
And Verify for three more forecast with interval of 6 hrs from current time 
And Verify the time format in app is same as mobile device
When user selects "Farenheit" from "Weather Forecast" screen 
Then user selects "Back button" from "Weather Forecast" screen
Then user should be displayed with the "Dashboard" screen
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Title                                |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
Then user should be displayed with "Farenheit" temperature scale in "Add New Device Dashboard" screen
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyIfDashboardStatusAreaDisplaysWhenANewLocationIsCreated
Scenario Outline:   To verify if dashboard status screen is displayed when user creates a new location
Given user launches and logs in to the Lyric application with user account without any location
Then user should be displayed with the "Add New Device" screen
And user changes the country to <Country>
When user selects "Smart Home Security" from "Add New Device" screen
Then user should be displayed with the "What To Expect" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects <Default Location> from "Choose Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <valid zip code>
Then user should be displayed with the "Name Your Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Title                                |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Add New Device button should be displayed below Dashboard status area

Examples: 
      | Country         | Default Location		| Default Device Name		| valid zip code        |
      | United States   | Home					| Living Room				| 90001                 |
      

@VerifyWeatherStatusInDashboardWhenLocationIsSelectedFromListOfLocationsDropDown
Scenario Outline:   To verify Weather status in Dashboard screen when user selects location from location list drop down in Dashboard screen
Given user launches and logs in to the Lyric application
When user selects "Smart Home Security" from "Add New Device" screen
Then user should be displayed with the "What To Expect" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user inputs <first location name> in the "Create Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <valid first locations zip code>
Then user should be displayed with the "Name Your Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device" screen
Then user clicks on the back arrow in the <Current Screen> screen
Then user should be displayed with the <Previous Screen> screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Add New Device button should be displayed below Dashboard status area
Then user navigates to "Weather Forecast" screen from the "Add New Device Dashboard" screen
Then user selects "Celsius" from "Weather Forecast" screen
Then user selects "Back button" from "Weather Forecast" screen
Then user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
When user selects "Smart Home Security" from "Add New Device" screen
Then user should be displayed with the "What To Expect" screen
When user navigates to "Choose Location" screen from the "What To Expect" screen
And user selects "Create New Location" from "Choose Location" screen
Then user should be displayed with the "Create Location" screen
When user inputs <second location name> in the "Create Location" screen
Then user should be displayed with the "Confirm Your ZIP Code" screen
When user inputs <valid second locations zip code>
Then user should be displayed with the "Name Your Base Station" screen
When user "cancels the set up" by clicking on "cancel" button
Then user should receive a "Cancel Setup" popup
When user "accepts" the "Cancel Setup" popup
Then user should be displayed with the "Add New Device" screen
Then user clicks on the back arrow in the <Current Screen> screen
Then user should be displayed with the <Previous Screen> screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Add New Device button should be displayed below Dashboard status area
Then user navigates to "Weather Forecast" screen from the "Add New Device Dashboard" screen
Then user selects "Farenheit" from "Weather Forecast" screen
Then user selects "Back button" from "Weather Forecast" screen
Then user selects <first location name> from "Add New Device Dashboard" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Add New Device button should be displayed below Dashboard status area
And user "deletes location details" by clicking on "delete" button
Then user should be displayed with the "Dashboard" screen
And user "deletes the existing location details" by clicking on "delete" button
Then user should be displayed with the "Add New Device Dashboard" screen

Examples: 
| first location name | valid first locations zip code | second location name   | valid second locations zip code  | Current Screen              | Previous Screen   |
| California          | 90001                          |  Texas                 | 75457                            | Add New Device Dashboard    | Dashboard         |


@DashboardStatusAreaWhenGeofenceThisLocationIsTurnedOFF
Scenario:   To verify the dashboard status area in dashboard screen when Geofence this location is turned off for a device
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "off"
Then the following "Geofence Settings" options should be disabled:
| Options					|
| Geofence this Location	|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyDasbhoardStatusAreaVerticalScrollWhenMultipleDevicesAreRegisteredAndGeofenceThisLocationIsTurnedOFF
Scenario:   To verify veritical scroll in dashboard screen when multiple devices are registered to the logged users account and geofence this location is turned off
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
When user scrolls to the top of the screen
Then devices should scroll over the top of the Dashboard status area
And the screen header along with Global drawer button should be visible
And Add New Device button should be displayed below the devices
When user scrolls to the bottom of the screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area


@FetchingGeofenceStatusInDashboardStatusAreaWhenGeofenceThisLocationIsTurnedON
Scenario:   To verify if fetching geofence status in dashboard status area is displayed when Geofence this location is turned on for a device
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
When app is fetching the Geofence status
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Fetching Geofence label               |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When Geofence status is fetched
Then user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Geofence status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@FailedToFetchGeofenceStatusInDashboardStatusAreaWhenGeofenceThisLocationIsTurnedON
Scenario:   To verify the geofence status in dashboard status area when geofence status is  when Geofence this location is turned on for a device
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
When app is fetching the Geofence status
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Fetching Geofence label               |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When app fails to fetch current geofence status
Then user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Geofence unavailable label            |
| Check Back Later label                |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Geofence status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@DashboardStatusAreaWhenGeofenceThisLocationIsTurnedON
Scenario:   To verify the dashboard status area in dashboard screen when Geofence this location is turned on for a device
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Geofence status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user crosses geofence location
Then user should be displayed "Away" status in "Geofence Status Area" in "Dashboard" screen
When user returns home
Then user should be displayed "Home" status in "Geofence Status Area" in "Dashboard" screen


@VerifyDasbhoardStatusAreaVerticalScrollWhenMultipleDevicesAreRegisteredAndGeofenceThisLocationIsTurnedON
Scenario:   To verify veritical scroll in dashboard screen when multiple devices are registered to the logged users account and geofence this location is turned on
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
When user scrolls to the top of the screen
Then devices should scroll over the top of the Dashboard status area
And the screen header along with Global drawer button should be visible
And Add New Device button should be displayed below the devices
When user scrolls to the bottom of the screen
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
When user crosses geofence location
Then user should be displayed "Away" status in "Geofence Status Area" in "Dashboard" screen
When user returns home
Then user should be displayed "Home" status in "Geofence Status Area" in "Dashboard" screen


@DashboardStatusAreaWhenGeofenceThisLocationIsTurnedOFFAndTurnedON
Scenario:   To verify the dashboard status area in dashboard screen when Geofence this location is turned off for a device and then turned on
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "off"
Then the following "Geofence Settings" options should be disabled:
| Options					|
| Geofence this Location	|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Geofence status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyNavigationToGeofenceScreenFromDashboardStatusArea
Scenario:   To verify navigation to Geofence screen from dashboard status area
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user selects "Geofence in Dashboard Status area" in "Dashboard" screen
Then user should be displayed with the "Geofence Settings" screen
And user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user selects "Back button" from "Geofence Settings" screen
Then user should be displayed with the "Dashboard" screen
And user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Right" on "Dashboard Status Area"
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyIfGeofenceStatusIsStillDisplayedWhenUserTurnsOffGeofenceFromDashboard
Scenario:   To verify if Geofence status in Dashboard status area is still displayed when user turns off Geofence this location by navigating from Dashboard status area
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user selects "Geofence in Dashboard Status area" in "Dashboard" screen
Then user should be displayed with the "Geofence Settings" screen
And user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user changes the "Geofence this locaiton toggle" to "off"
Then the following "Geofence Settings" options should be disabled:
| Options					|
| Geofence this Location	|
And user should not be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user selects "Back button" from "Geofence Settings" screen
Then user should be displayed with the "Dashboard" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyGeofenceStatusWhenGeofenceIsEnabledAndPhoneLocationServicesIsTurnedOff
Scenario:   To verify Geofence status in Dashboard status area when geofence is enabled and phone location services is turned off
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user turns off the mobile device location
And user maximizes the app
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Skip button in" the "Turn on Location Services" popup
Then user should not receive a "Turn On Location Services" popup
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Alert icon                            |
| Location Services Disabled label      |
| Enable in <OS> Settings label         |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user selects "Location Services Disabled" in "Dashboard" screen
Then user should be displayed with the "Device Settings Location Services" screen
When user turns on the mobile device location
And user maximizes the app
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyGeofenceStatusWhenGeofenceIsEnabledAndAppLocationServicesIsTurnedOffInPhoneSettings
Scenario:   To verify Geofence status in Dashboard status area when geofence is enabled and App location services is turned off
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user turns off the HH App location services present in phone settings
And user maximizes the app
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Skip button in" the "Turn on Location Services" popup
Then user should not receive a "Turn On Location Services" popup
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Alert icon                            |
| Location Services Disabled label      |
| Enable in <OS> Settings label         |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user turns on the mobile device location
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@VerifyIfGeofenceStatusIsDisplayedWhenTheDeviceIsDeleted
Scenario:  Verify if Geofence status in Dashboard Status area is displayed when user deletes device
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to registered device settings and deletes the device
And user navigates to "Dashboard" screen from the "Settings" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@DashboardStatusAreaWithWLDDevice
Scenario:   To verify the dashboard screen when there is a WLD device registered for a location
Given user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


@DashboardStatusAreaWithDASDeviceWhenUserChangesSecurityCardModeFromHomeToOtherModes
Scenario Outline:   To verify the dashboard screen when there is a DAS device registered for a location
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
Then user is set to <Mode> mode through CHIL
And user navigates to “Dashboard” screen 
When user switches from <Mode> to <UMode>
Then user should be displayed with a switching to <UMode> text
Then timer ends on user device
And user status should be set to <UMode>
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And user should be displayed with "Everyone is Home" in the "Geofence status in Dashboard" screen
And user should be displayed with "Using Away Settings" in the "Geofence status in Dashboard" screen   
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples: 
| Timer | Mode  | UMode  |
| 15    | Home  | Away   |
| 15    | Home  | Night  |
| 15    | Home  | Off    |


@DashboardStatusAreaWithDASDeviceWhenUserChangesSecurityCardModeFromAwayToOtherModes
Scenario Outline:   To verify the dashboard screen when there is a DAS device registered for a location
Given user sets the entry/exit timer to <Timer> seconds
When user launches and logs in to the Lyric application
Then user is set to <Mode> mode through CHIL
And user navigates to “Dashboard” screen 
When user switches from <Mode> to <UMode>
Then user should be displayed with a switching to <UMode> text
Then timer ends on user device
And user status should be set to <UMode>
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And user should be displayed with "Security set to Home" in the "Geofence status in Dashboard" screen
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples: 
| Timer | Mode  | UMode  |
| 15    | Away  | Night  |
| 15    | Away  | Off    |
| 15    | Away  | Home   |


@DashboardStatusAreaWithJASPERHBBDeviceWhenUserChangesSecurityCardModeFromHomeToSleep
Scenario Outline:   To verify the dashboard screen when there is a Jasper/HB device registered for a location
Given user has <Mode> system mode
And user thermostat is set to <scheduling> schedule
And user thermostat set "Home" with <Geofence>
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And user should be displayed with "Using Sleep Settings" in the "Geofence status in Dashboard" screen
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
And verify the "Using SLEEP SETTINGS" on the "PRIMARY CARD" screen
When user deletes the <Period> 
Then verify the "USING HOME SETTINGS" on the "PRIMARY CARD" screen
And verify respective <UPeriod> period setpoint values
When user selects "Back button" from "Weather Forecast" screen
Then user should be displayed with the "Dashboard" screen
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Title                                |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And user should be displayed with "Using Sleep Settings" in the "Geofence status in Dashboard" screen
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples:
 | Mode	| scheduling	 | Geofence     | Period |UPeriod |
 | Cool	| geofence based | UserArrived  | Sleep	 | Home |
#| Heat | geofence based | UserArrived  | Sleep	 |Home |
#| Auto | geofence based | UserArrived  | Sleep	 |Home |


# JASPER NA, EMEA, HBB, FlyCatcher, Camera and DAS
@VerifyIfGeofenceStatusDisplayedWhenUserSkipsGeofenceWhileDoingDIY
Scenario:  Verify if Geofence status displayed in Dashboard status area when user skips geofence while doing DIY
Given user launches and logs in to the Lyric application
When user registers a device by skipping geofence
Then user should be displayed with the "Dashboard" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


# JASPER NA, EMEA, HBB, FlyCatcher, Camera and DAS
@VerifyIfGeofenceStatusDisplayedWhenUserSetsUpGeofenceRadiusWhileDoingDIY
Scenario:  Verify if Geofence status displayed in Dashboard status area when user sets up geofence radius while doing DIY
Given user launches and logs in to the Lyric application
When user registers a device by setting up geofence radius
Then user should be displayed with the "Dashboard" screen
And user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Title                                |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


# JASPER NA, EMEA, HBB, FlyCatcher, Camera and DAS
@VerifyDashboardStatusAreaWhenGeofenceIsEnabledForExistingDeviceAndUserSkipsGeofenceWhileDoingDIYForAnotherDevice
Scenario:  Verify if Geofence status displayed in Dashboard status area when user skips geofence while doing DIY for another device
Given user launches and logs in to the Lyric application
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user registers a device by skipping geofence
Then user should be displayed with the "Dashboard" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device


#JASPER NA, HBB, Fly Catcher
@VerifyDashboardStatusAreaWhenScheduleIsChangedFromTimeBasedScheduleToNoSchedule
Scenario Outline:  To verify if Geofence status is displayed in Dashboard status area when Time based Schedule is changed to No Schedule
Given user has <Mode> system mode 
Then user thermostat is set to "time based" schedule 
When user launches and logs in to the Lyric application
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
Then user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen
When user has "PERMANENT" status
Then verify the "PERMANENT" on the "PRIMARY CARD" screen
And user navigates to "Scheduling" screen from the "Primary card" screen
And user selects "Grouped days" view
When user edit Time schedule by deleting "All 4 Periods" on confirming the period deletion
Then verify the "No Schedule" on the "PRIMARY CARD" screen
Then user navigates to "PRIMARY CARD" screen from the "Scheduling" screen
When user changes system mode to <UMode>
Then verify the "No Schedule" on the "PRIMARY CARD" screen
When user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples:
 |Mode	|UMode	| 
 |Heat	|Cool 	|
#|Cool	|Heat 	|
#|Heat	|Cool 	|
#|Cool	|Heat  	|
#|Heat	|Cool 	|
#|Cool	|Heat 	|
#|Heat	|Cool 	|
#|Cool	|Heat 	|
#|Cool	|Auto 	|
#|Auto	|Heat 	|	
#|Auto	|Cool 	|
#|Heat	|Auto 	|
#|Auto	|Heat 	|
#|Auto	|Cool 	|
#|Cool	|Auto 	|
#|Heat	|Auto 	|
#|Cool	|Auto 	|
#|Heat	|Auto 	|
#|Auto	|Heat	|
#|Auto	|Cool	|
#|Heat	|Auto 	|
#|Auto	|Heat 	|
#|Auto	|Cool	|
#|Cool	|Auto	|


#JASPER NA, HBB, Fly Catcher
@VerifyDashboardStatusAreaWhenScheduleIsChangedFromGeofenceBasedScheduleToNoSchedule
Scenario Outline:  To verify if Geofence status is displayed in Dashboard status area when Geofence based Schedule is changed to No Schedule
Given user has <Mode> system mode
And user thermostat is set to "time based" schedule
When user launches and logs in to the Lyric application
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Weather status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "thermostat solution card" screen from the "thermostat Dashboard" screen
Then verify the "FOLLOWING SCHEDULE" on the "PRIMARY CARD" screen
When user navigates to "scheduling" screen from the "PRIMARY CARD" screen
Then user thermostat set <Period> with <Geofence>
And user creates "Geofence based" schedule following specific <Sleep period> time
And verify the <Schedule status> on the "PRIMARY CARD" screen
And verify respective <Period> period setpoint values
When user navigates to "THERMOSTAT DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
Then user should be displayed with "Weather status" in Dashboard status area
And user should be displayed with the following "Weather" options:
| WeatherOptions				                        | 
| Today's Forecast Header                               |
| Weather icon                                          |
| Current Outdoor Temperature and Weather condition     |
| Today's High and Low Temperature                      |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples:
 | Mode	| Period	| Geofence		    | Schedule status		| Sleep period | 
 | HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Home		| UserArrived		| Using Home Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Away		| UserDeparted		| Using Away Settings	| Without |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#| HEAT	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#| Cool	| Home		| UserArrived		| Using Home Settings	| Without |
#| Cool	| Home		| UserArrived		| Using Home Settings	| Without |
#| Cool	| Home		| UserArrived		| Using Home Settings	| Without |
#| Cool	| Away		| UserDeparted		| Using Away Settings	| Without |
#| Cool	| Away		| UserDeparted		| Using Away Settings	| Without |
#| Cool	| Away		| UserDeparted		| Using Away Settings	| Without |
#| Cool	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#| Cool	| Sleep		| UserArrived		| Using Sleep Settings	| With |
#| Cool	| Sleep		| UserArrived		| Using Sleep Settings	| With |


#JASPER NA, HBB, Fly Catcher
@VerifyDashboardStatusAreaForGeofenceBasedSchedule
Scenario Outline:  To verify if Geofence status is displayed in Dashboard status area when Geofence based Schedule is enabled
Given user has <Mode> system mode
And user thermostat is set to "geofence based" schedule
And user thermostat set <Period> with <Geofence>
When user launches and logs in to the Lyric application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device

Examples:
| Mode  |
| Heat  |
| Cool  |
| Auto  |


@DashboardStatusAreaWhenUserCrossesFenceAndReturnsBack
Scenario:   To verify the dashboard status area in dashboard screen when Geofence is crossed and returned back when Multiple devices are registered
Given user launches and logs in to the Lyric Application
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user selects "Geofence" from "Global Drawer" screen
And user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert			|
When user navigates to "Dashboard" screen from "Global Drawer" screen
Then user should be displayed with "Weather status" in Dashboard status area
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then user should be displayed with "Geofence status" in Dashboard status area
And user should be displayed with the following "Geofence status" options:
| GeofenceStatusOptions				    |
| Geofencing Title                      |
| Geofence icon                         |
| Current state of geofencing           |
| What settings are being used          |
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user swipes "Left" on "Dashboard Status Area"
Then Dashboard status area should move in the direction of the swipe to a certain point, spring back and display "Geofence status"
And Device should be displayed below Dashboard status area
And Add New Device button should be displayed below the device
When user crosses geofence location
Then user should be displayed "Away" status in "Geofence Status Area" in "Dashboard" screen
When user returns home
Then user should be displayed "Home" status in "Geofence Status Area" in "Dashboard" screen