@GeofenceRadius
Feature: GeofenceRadius Setup
As an user I want to set my geofence radius so that geofence trigger happens and setpoint changes when I cross/enter the radius

@GeofenceThisLocation_ToggleON
  Scenario: Verify user is able to turn ON 'Geofence This Location' toggle
Given user login to user account with single stat
	And 'Geofence This Location' toggle is OFF
  When user turn ON 'Geofence This Location' toggle 
  Then verify the 'Geofence This Location' toggle is ON 
  	And Geofence radius & Location status options are dispayed
  	And user logout and login to verify the value is retained
    And user log out of the app

@GeofenceThisLocation_ToggleOff
  Scenario: Verify user is able to turn off 'Geofence This Location' toggle
Given user login to user account with single stat
	And 'Geofence This Location' toggle is ON
  When user turn OFF 'Geofence This Location' toggle 
  Then Verify Disabling Geofence notification popup is displayed with OK button
  	And verify the popup is dismissed on tapping OK button & 'Geofence This Location' toggle is OFF
  	And Geofence radius & Location status options are not dispayed
  	And user logout and login to verify the value is retained
    And user log out of the app

@GeofenceRadius_Update_Singlestat
  Scenario: Update geofence radius based on the geofence center for user account with single stat 
Given user login to user account with single stat
	And Geofence center is set
  When user navigate to geofence radius screen
  	And user update the Geofence radius
 	And user save the geofence Radius 
  Then verify the updated geofence radius is saved & the updated values retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
    
@GeofenceRadius_Update_MultiStat
  Scenario: Update geofence radius based on the geofence center for user account with multiple stats
Given user login to user account with multiple stats
	And Geofence center is set
  When user navigate to geofence radius screen
  	And user update the Geofence radius
 	And user save the geofence Radius 
  Then verify the updated geofence radius is saved & the updated values retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
    
@GeofenceRadius_UpdateAndCancel_SingleStat
  Scenario: Login to user account with single stat update geofence radius and cancel without saving 
Given user login to user account with single stat
	And Geofence center is set
  When user navigate to geofence radius screen
  	And user update the Geofence radius
 	And user cancel the geofence radius setting screen 
  Then verify the updated geofence radius is not saved & the old value is retained
    And user log out of the app

@GeofenceRadius_UpdateAndCancel_MultiStat
  Scenario: Login to user account with multiple stats update geofence center and cancel without saving
Given user login to user account with multiple stats
	And Geofence center is set
  When user navigate to geofence radius screen
  	And user update the Geofence radius
 	And user cancel the geofence radius setting screen 
  Then verify the updated geofence radius is not saved & the old value is retained
    And user log out of the app
    
@GeofenceRadius_MobileLocation_PopupVerification
  Scenario: Verify Location service popup is displayed when mobile device location is turned off
Given user login to lyric app
	And Geofence center is set
	And mobile location service is turned off
  When user navigate to geofence radius screen
  	Then verify location service popup is displayed with Settings & cancel button
    And user cancel the popup 
    And user log out of the app
    
@GeofenceRadius_APPLocationPermission_PopupVerification
  Scenario: Verify Location service popup is displayed when app location permission is turned off
Given user login to lyric app
	And Geofence center is set
	And app location permission is turned off
  When user navigate to geofence radius screen
  	Then verify location service popup is displayed with Settings & cancel button
    And user cancel the popup 
    And user log out of the app
    
@GeofenceRadius_MobileLocationTurnON
  Scenario: Verify user is able to turn on mobile Location service and update the geofence radius
Given user login to user account 
	And Geofence center is set
	And mobile location service is turned off
  When user navigate to geofence radius screen
    And user goto location settings and turn on location
    And user update the Geofence radius
    And user save the geofence radius 
  Then verify the updated geofence radius is saved & the updated values retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
	    
@GeofenceRadius_AppLocation_PermissionTurnON
  Scenario: Verify user is able to turn on app Location permission and update the geofence radius
Given user login to user account 
	And Geofence center is set
	And app location permission is turned off
  When user navigate to geofence radius screen
    And user goto app location permission and turn on permission
    And user update the Geofence radius
    And user save the geofence radius 
  Then verify the updated geofence radius is saved & the updated values retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
    
@GeofenceRadius_Update_MaximumAndMinimum
  Scenario Outline: verify user is able to update geofence radius to maximum/minimum limit
Given user login to lyric app
	And Geofence center is set
  When user navigate to geofence radius screen
  	And user update the Geofence radius <Allowed> limit
 	And user save the geofence Radius 
  Then verify the updated geofence radius is saved & the updated values retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
    
 Examples:
|Allowed            |
|Maximum-500Miles   |
|Minimum-500Meter   |


@Geofence_LocationStatus
  Scenario Outline: verify Location status is updated based on the user position
Given user login to lyric app
	And Geofence center is set
    And Geofence Radius is set
  When user mobile location move <Status>
  Then verify the Location status is updated to Home/Away based on the user geofence position
    And user log out of the app
    
 Examples:
|Status         |
|Within Radius  |
|Outside Radius |

@GeofenceTirggerVerification_ToggleOff
  Scenario Outline: Verify the geofence trigger Home/Away doesn't happen when the 'Geofence This Location' toggle is OFF
Given user login to user account with single stat
	And Geofence center is set
    And Geofence Radius is set
	And 'Geofence This Location' toggle is OFF
  When user mobile location move <Status>
  Then verify geofence trigger Home/Away doesn't happen & push notification is not received
    And user log out of the app

 Examples:
|Status         |
|Within Radius  |
|Outside Radius |

@GeofenceRadius_Update_MultiLocation
  Scenario: Verify the geofence radius update is location dependent
Given user login to user account with multilocation
	And Geofence center is set for Location1
    And Geofence center is set for Location2
  When user navigate to geofence radius screen of Location1
  	And user update the Geofence radius of Location1
 	And user save the geofence Radius of Location1
  Then verify the updated geofence radius is saved & the updated values retain for Location1
  	And verify the Location2 geofence radius is not updated & old radius retain
    And user log out of the app

@GeofenceCenterUpdate_NewlyConfiguredStat
  Scenario: Verify user is able to updated geofence center for newly congifured stat
Given user configured new Jasper/HB stat by skipping geofence
	And Geofence center is set
  When user navigate to 'Geofence this Location' screen
    And user turn ON 'Geofence this Location'
    And user update the geofence radius 
  Then verify the updated geofence radius is saved & the updated radius retain
  	And user logout and login to verify the updated geofence radius is retained
    And user log out of the app
