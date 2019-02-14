@GenralAdditionScenarios @Platform
Feature: As a user i wanted to verify addition scenarios

      
#Requirment : Sinale location with or with out solution
@GeneralinappratinginsolutionDIYflow        @NotAutomatable
 Scenario Outline: As a user i wanted to verify in app rating should not display in DIY flow
 Given user launches and logs in to the Lyric application
 When user configure the <Solution> with new location
 Then user should not displayed "inapprating" pop up during DIY flow
 Examples: 
      | Solutions		| 
      | JasperNA		| 
      | JasperEMEA		| 
      | HB              |
      | C1              |
      | C2              |
      | D6              |
      | DAS             |
      
#Requirement: Single location with any solution
@GeneralInAppRatingNotNowiOS         @Automatable
Scenario: As a user I want to verify In App Rating with "Not Now" option
Given user launches and logs in to the Lyric application
Then user should receive a "Enjoying Honeywell" popup
And user should be displayed with following "inapprating" options:
|Options|
|Enjoying Honeywell?|
|Tap a star to rate it on the app store.|
|5 stars|
|Not Now |
When user selects "Not Now" option
Then user should not be displayed with "Enjoying Honeywell?" pop up

#Requirement: Single location with any solution
@GeneralInAppRatingStarSelectiOS         @Automatable
Scenario Outline: As a user I want to verify In App Rating with star selection
Given user launches and logs in to the Lyric application
Then user should be displayed "Enjoying Honeywell?" pop up
When user selects the <stars> 
Then user should be displayed with "inapprating" options:
|Options|
|Enjoying Honeywell?|
|Tap a star to rate it on the app store.|
|Cancel | 
|Submit |
And user should be dispalyed with selected <stars>
Examples:
|stars |
|1 |
|2|
|3|
|4|
|5|

#Requirement: Single location with any solution
@GeneralInAppRatingCanceliOS     @Automatable
Scenario Outline: As a user I want to verify In App Rating with cancel option functionlity
Given user launches and logs in to the Lyric application
Then user should be displayed "Enjoying Honeywell?" pop up
When user selects the <stars> 
Then user should be displayed with "inapprating" options:
|Options|
|Enjoying Honeywell?|
|Tap a star to rate it on the app store.|
|Cancel | 
|Submit |
And user should be dispalyed with selected <stars>
When user selects "Cancel" option
Then user should not be displayed with "Enjoying Honeywell?" pop up
Examples:
|stars |
|1 |
|2|
|3|
|4|
|5|

#Requirement: Single location with any solution
@GeneralInAppRatingSubmitiOS     @Automatable
Scenario Outline: As a user I want to verify In App Rating with submit option functionlity
Given user launches and logs in to the Lyric application
Then user should be displayed "Enjoying Honeywell?" pop up
When user selects the <stars> 
Then user should be displayed with "inapprating" options:
|Options|
|Enjoying Honeywell?|
|Tap a star to rate it on the app store.|
|Cancel | 
|Submit |
And user should be dispalyed with selected <stars>
When user selects "Submit" option
Then user should navigates to "AppStore"
Examples:
|stars |
|1 |
|2|
|3|
|4|
|5|

#Geofence Location Status
# JaseprNA, JasperEMEA, C1, C2, DAS,
@GenralGlobalDrawerVerifyLocationStatus         @Automatable                         
Scenario Outline:  As a user I want to verify Location status when user cross the fence home and away with solutions
Given user has <Mode> system mode
And user thermostat is set to "geofence based" schedule
When user launches and logs in to the Lyric application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
And user thermostat set <Period> with <Geofence>
And user should be updated with Location status <Period>
And user thermostat set <UPeriod> with <Geofence>
And user should be updated with Location status <UPeriod>
Examples:
| Mode | Period                    | Geofence           | UPeriod            | UGeofence          |
| Heat | Home               | UserArrived        | Away               | UserDeparted |
| Cool | UserArrived        | Home               | Away               | UserDeparted |


#Invite User
#Requirements : single location with and with out any solution
@GeneralGlobalDrawerInviteUserEmailConfirmation     @NotAutomatable
Scenario: As a user i want to Verify Email notification for invited user
Given user launches and logs in to the Lyric Application
When user navigates to "Invite User" screen from the "Dashboard" screen
And user inputs <invite users email address> in "Email Text Field" in the "Invite User" screen
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
And user login to "das_stage5@grr.la" email account
And user should be displayed with "email invitation"

#Activity History
#Requirements : single location with DAS/JASPER/CAMERA/HB any solution
@GeneralGlobalDrawerGefoencenotificaqtioninactivityHistory          @Automatable
Scenario Outline: As a user i want to Verify Geofence messages in activity history
Given user launches and logs in to the Lyric Application
When user Cross the fence "home" to "Away"
Then user should receive "Push Notification"
When user navigtes to "Activity History" screen
Then user should be displayed with "Geofence cross" message
And user selects "Geofence cross" message
And user shold be displayed with "Geofence cross description" message
When user Cross the fence "Away" to "Home"
Then user should receive "Push Notification"
When user navigtes to "Activity History" screen
Then user should be displayed with "Geofence cross" message
And user selects "Geofence cross" message
And user shold be displayed with "Geofence cross description" message
Examples:
| Mode | Period                    | Geofence           | UPeriod            | UGeofence          |
| Heat | Home               | UserArrived        | Away               | UserDeparted |
| Cool | UserArrived        | Home               | Away               | UserDeparted |


#Server Down
#LCC/ CHIL Down error popup
@Serverdownerrorpopupverficiation       @NotAutomatable
Scenario: As a user i wnat to verify the error pop up when server is down
Given user launches and logs in to the Lyric Application
Then user server "LCC/TCC/CHIL" down
And user receives the pop up "The HOneywell Serviceis temporarily unavailable, but dont worry -- this wont effect your  device"
And user should be displayed with "RETRY" option
When user selcts the "RETRY" option
Then user should receives the pop up "The HOneywell Serviceis temporarily unavailable, but dont worry -- this wont effect your  device"
When server "LCC/TCC/CHIL" up
Then user select the "RETRY" option
And user pop up should be disappear 


#Activity History
#Requirments: Single location with solutions and with alters
@GenralGlobalDrawerActivityHistorycheckanduncheckandcheckscnearios          @Automatable
Scenario: As a user i want to Verify select all the messages in activity history
Given user launches and logs in to the Lyric Application
When user navigates to "Activity history" screen
Then user selects the "Selects all" option
And user "Uncheck" the two "Messages"
When user selects the "Selects all" option
Then user should be selects all the messages
When user selects the "Delete" option
Then user should not dispalyed any messages 

#Requirements : single location with and with out any solution
@GeneralGlobalDrawerInviteUserdeleteaccountmultiuser            @Automatable
Scenario: As a user i want to Verify Email notification for invited user
Given user launches and logs in to the Lyric Application
When user invites "User1" to "User2"
Then user deletes the "User2"
And user login with "user1"
And user should be displayed with "Add new device" screen

#Requirements : Single location with solution and push notifications
@GeneralPushnotificationafterlogout             @Automatable
Scenario: As a user i want to verify push notifcation clear after user logged out 
Given user launches and logs in to the Lyric Application
When user generates push notifications
Then user logged out from the app
And push notification should be disappear 

#Permission pop up 
#Location service off on the phone device
#Cannot Automate in iOS
@GeneralLocationServicesOnPopupCancelFunctionality          @Automated			@LimitationOnInvokingSettingsAppOnIOS
Scenario: As a user I want to verify turn on location service popup cancel functionality
Given user launches the Lyric application without closing the popup
Then user turns off the mobile device location
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Skip button in" the "Turn on Location Services" popup
Then user should not receive a "Turn On Location Services" popup

#Requirements : Location service off on the phone device
#Cannot Automate in iOS
@GeneralLocationServicesOnPopupSettingsFunctionality          @Automated			@LimitationOnInvokingSettingsAppOnIOS
Scenario: As a user I want to verify turn on location service pop up cancel functionality
Given user launches the Lyric application without closing the popup
Then user turns off the mobile device location
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Settings button in" the "Turn on Location Services" popup
Then user should be displayed with the "Mobile device Location settings" screen
When user selects "Enable Location" from "Mobile device Location settings" screen
Then user launches the Lyric application after turning on the device location
Then user should not receive a "Turn On Location Services" popup


#Cannot Automate in iOS
@GeneralLocationPermissionDenyFunctionality          @Automated
Scenario: As a user I want to verify Deny option in location permission popup
Given user launches the Lyric application without closing the popup
Then user turns off the mobile device location
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Settings button in" the "Turn on Location Services" popup
Then user should be displayed with the "Mobile device Location settings" screen
When user selects "Enable Location" from "Mobile device Location settings" screen
Then user launches and logs in to the Lyric application without closing the popup
When user should receive a "Allow honeywell to access this devices location" popup
When user "Clicks on Deny button in" the "Allow honeywell to access this devices location" popup
Then user should not receive a "Allow honeywell to access this devices location" popup
#And user navigates to "Hoenywell permission" settings            ------- Cannot automate the next 2 steps on Android relating to Device Settings
#And user should be displayed with "location permission deny"


#Cannot Automate in iOS
@GeneralLocationPermissionAllowFunctionality          @Automated
Scenario: As a user I want to verify Allow option in location permission pop up 
Given user launches the Lyric application without closing the popup
Then user turns off the mobile device location
Then user should receive a "Turn On Location services" popup
Then user "Clicks on Settings button in" the "Turn on Location Services" popup
Then user should be displayed with the "Mobile device Location settings" screen
When user selects "Enable Location" from "Mobile device Location settings" screen
Then user launches and logs in to the Lyric application without closing the popup
When user should receive a "Allow honeywell to access this devices location" popup
When user "Clicks on Allow button in" the "Allow honeywell to access this devices location" popup
Then user should not receive a "Allow honeywell to access this devices location" popup
#And  user navigates to "Hoenywell permission" settings		      ------- Cannot automate the next 2 steps on Android relating to Device Settings
#And user should be displayed with "location permission Allow"


#iOS 
@GeneralLocationPermissionDontAllowFunctionality          @Automatable
Scenario: As a user I want to verify Never option in location permission pop up 
Given user launches and logs in to the Lyric Application
When user should be displayed with "Allow honeywell to access your location?" pop up
Then suer should be displayed with "Geofencing will not work unless..." description
And user should be displayed with "Only while using the app" and "Always allow" and "Don't Allow" option
When user selects "Don't Allow" option
Then user should not be displayed "Allow honeywell to access this devices location?" pop up
#And  user navigates to "Hoenywell permission" settings	     ------- Cannot automate the next 2 steps on ios relating to Device Settings
#And user should be displayed with "Never"

@GeneralLocationpermissionAlwaysAllowfunctionality          @Automatable
Scenario: As a user i want to verify Always option in nlcoation permission pop up 
Given user launches and logs in to the Lyric Application
When user should be displayed with "Allow honeywell to access your location?" pop up
Then suer should be displayed with "Geofencing will not work unless..." description
And user should be displayed with "Only while using the app" and "Always allow" and "Don't Allow" option
When user selects "Always Allow" option
Then user should not be displayed "Allow honeywell to access this devices location?" pop up
#And  user navigates to "Hoenywell permission" settings	    ------- Cannot automate the next 2 steps on ios relating to Device Settings
#And user should be displayed with "Always"

@GeneralLocationpermissionWhileUsingtheAppfunctionality          @Automatable
Scenario: As a user i want to verify While using the app option in locationpermission permission pop up 
Given user launches and logs in to the Lyric Application
When user should be displayed with "Allow honeywell to access your location?" pop up
Then suer should be displayed with "Geofencing will not work unless..." description
And user should be displayed with "Only while using the app" and "Always allow" and "Don't Allow" option
When user selects "While Using the App" option
Then user should not be displayed "Allow honeywell to access this devices location?" pop up
#And  user navigates to "Hoenywell permission" settings    ------- Cannot automate the next 2 steps on ios relating to Device Settings
#And user should be displayed with "While Using the App"

@GeneralNotificationpermissionAllowfunctionlity          @Automatable
Scenario: As a user i want to verify Deny option in nlcoation permission pop up 
Given user launches and logs in to the Lyric Application
When user should be displayed with "Honeywell would like to send you notifications" pop up
Then suer should be displayed with "Notifications may include alerts ... " description
And user should be displayed with "Don't allow" and "Allow" option
When user selects "Allow option"
Then user should not be displayed "Honeywell would like to send you notifications" pop up
#And  user navigates to "Hoenywell Notification permission" settings  ------- Cannot automate the next 2 steps on ios relating to Device Settings
#And user should be displayed with "Allow notifcations" enabled


@GeneralNotificationpermissionDontallowunctionlity          @Automatable
Scenario: As a user i want to verify Deny option in nlcoation permission pop up 
Given user launches and logs in to the Lyric Application
When user should be displayed with "Honeywell would like to send you notifications" pop up
Then suer should be displayed with "Notifications may include alerts ... " description
And user should be displayed with "Don't allow" and "Allow" option
When user selects "Dont Allow" option
Then user should not be displayed "Honeywell would like to send you notifications" pop up
#And  user navigates to "Hoenywell Notification permission" settings ------- Cannot automate the next 2 steps on ios relating to Device Settings
#And user should be displayed with "Allow notifcations" disabled 
