@GenralGlobalDrawerUnderScenarios @Platform
Feature: Genral global drawer under scenarios 
	As a user I want to verify under global drawer scenaios 
	
	
#Single location with out any solution for UK Location
@GeneralGlobalDrawerWithoutSolutionVerificationForUKLocation				@Automated
Scenario: As a user i want to view the options displayed in global drawer without a solution for UK location
Given user launches and logs in to the Lyric application
When user navigates to "Global Drawer" screen from the "Dashboard" screen
Then user should be displayed with the following "Global Drawer Without Solution For UK Location" options:
| GlobalDrawerWithoutSolutionORWithWLDSolutionForUKLocation	|
| Without Automation Header									|
| Without Geofence option									|
| Home Header												|
| Activity history											|
| Add Users													|
| Home Address												| 
| Account Header												|
| Edit Account												|
| About the app												|
| FAQs														|
| Logout														|
#And user logs out of the app


#Single location with out any solution for US Location
@GeneralGlobalDrawerWithoutSolutionVerificationForUSLocation				@Automated
Scenario: As a user i want to view the options displayed in global drawer without a solution for US location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer Without Solution For US Location" options:
| GlobalDrawerWithoutSolutionORWithWLDSolutionForUSLocation	|
| Without Automation Header									|
| Without Geofence option									|
| Home Header												|
| Activity history											|
| Add Users													|
| Home Address												| 
| Account Header												|
| Edit Account												|
| About the app												|
| No FAQs For US location									|
| Logout														|
#And user logs out of the app


#WLD Solution for UK Location
@GeneralGlobalDrawerWithWLDSolutionVerificationForUKLocation				@Automated
Scenario: As a user i want to view the options displayed in global drawer with WLD solution for UK location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With WLD Solution For UK Location" options:
| GlobalDrawerWithoutSolutionORWithWLDSolutionForUKLocation	|
| Without Automation Header									|
| Without Geofence option									|
| Home Header												|
| Activity history											|
| Add Users													|
| Home Address												| 
| Account Header												|
| Edit Account												|
| About the app												|
| FAQs														|
| Logout														|
#And user logs out of the app


#WLD Solution for US Location
@GeneralGlobalDrawerWithWLDSolutionVerificationForUSLocation				@Automated
Scenario: As a user i want to view the options displayed in global drawer with WLD solution for US location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With WLD Solution For US Location" options:
| GlobalDrawerWithoutSolutionORWithWLDSolutionForUSLocation	|
| Without Automation Header									|
| Without Geofence option									|
| Home Header												|
| Activity history											|
| Add Users													|
| Home Address												| 
| Account Header												|
| Edit Account												|
| About the app												|
| No FAQs For US location									|
| Logout														|
#And user logs out of the app


#DAS, C1, C2 Solution for UK Location
@GeneralGlobalDrawerWithSolutionForDASC1C2VerificationForUKLocation			@Automated
Scenario: As a user i want to view the options in global drawer scenarios for DAS or C1 or C2 solutions for UK location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With DAS C1 C2 Solution For UK Location" options:
| GlobalDrawerWithDASC1C2SolutionForUKLocation		|
| Automation Header									|
| Geofence 											|
| Home Header										| 
| Activity history									|
| Add Users											|
| Home Address										| 
| Account Header										|
| Honeywell Membership For Android					|
| Edit Account										|
| About the app										|
| FAQs												|
| Logout 											|
#And user logs out of the app


#DAS, C1, C2 for US Location
@GeneralGlobalDrawerWithSolutionForDASC1C2VerificationForUSLocation			@Automated
Scenario: As a user i want to view the options in global drawer scenarios for DAS or C1 or C2 solutions for US location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With DAS C1 C2 Solution For US Location" options:
| GlobalDrawerWithDASC1C2SolutionForUSLocation		|
| Automation Header									|
| Geofence 											|
| Home Header										| 
| Activity history									|
| Add Users											|
| Home Address										| 
| Account Header										|
| Honeywell Membership For Android					|
| Edit Account										|
| About the app										|
| No FAQs For US location							|
| Logout 											|
#And user logs out of the app


#JASPEREMEA for UK Location
@GeneralGlobalDrawerWithSolutionForJASPEREMEAVerificationForUKLocation			@Automated
Scenario: As a user i want to view the options in global drawer scenarios for Jasper EMEA solution for UK location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With JASPER EMEA Solution For UK Location" options:
| GlobalDrawerWithJasperEMEASolutionForUKLocation		|
| Automation Header									|
| Geofence 											| 
| Vacation											|
| Home Header										|
| Activity history									|
| Add Users											|
| Home Address										| 
| Account Header										|
| Edit Account										|
| About the app										|
| FAQs												|
| Logout 											|
#And user logs out of the app


#JasperNA for US Location
@GeneralGlobalDrawerWithSolutionForJASPERNAVerificationForUSLocation			@Automated
Scenario: As a user i want to view the options in global drawer scenarios for Jasper NA solution for US location
Given user launches and logs in to the Lyric Application
When user navigates to "Global Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer With JASPER NA Solution For US Location" options:
| GlobalDrawerWithJasperNASolutionForUSLocation		|
| Automation Header									|
| Geofence 											|
| Vacation											|
| Home Header										| 
| Activity history									|
| Add Users											|
| Home Address										| 
| Account Header										|
| Edit Account										|
| About the app										|
| No FAQs For US location							|
| Logout 											|
#And user logs out of the app


#Geofence 
#Geofence this location disabled and enabled
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions
@GeneralGlobalDrawerEnableAndDisableGeofence					@Automated
Scenario:  As a user I want to verify the enable and disable geofence option under global drawer when a solution is present
Given user launches and logs in to the Lyric Application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user navigates to "Geofence Radius" screen from the "Geofence Settings" screen
And user selects "Update Geofence Center" from "Geofence Radius" screen
Then user should receive a "Update Geofence Center" popup
And user "Cancels" the "Update Geofence Center" popup
When user selects "Update Geofence Center" from "Geofence Radius" screen
Then user should receive a "Update Geofence Center" popup
And user "Clicks on UPDATE in" the "Update Geofence Center" popup
When user selects "Save button" from "Geofence Radius" screen
Then user should be displayed with the "Geofence Settings" screen
And "Geofencing" value should be updated to "ON" on "Geofence Settings" screen
And user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user changes the "Geofence this locaiton toggle" to "off"
Then the following "Geofence Settings" options should be disabled:
| Options					|
| Geofence this Location		|
And user should not be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|


#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions
@GeneralGlobalDrawerUpdateGeofenceCenterAndTapOnBackButtonInGeofenceRadius     @Automated
Scenario: As a user I want to verify if cancel geofence changes popup appears when YES, Update button is selected in Update Geofence Center popup and tap on back button in Geofence Radius screen
Given user launches and logs in to the Lyric Application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user navigates to "Geofence Radius" screen from the "Geofence Settings" screen
And user selects "Update Geofence Center" from "Geofence Radius" screen
Then user should receive a "Update Geofence Center" popup
And user "Clicks on UPDATE in" the "Update Geofence Center" popup
When user selects "Back button" from "Geofence Radius" screen
Then user should receive a "Cancel Geofence Changes" popup
And user "Cancels" the "Cancel Geofence Changes" popup
When user selects "Back button" from "Geofence Radius" screen
Then user should receive a "Cancel Geofence Changes" popup
And user "Accepts" the "Cancel Geofence Changes" popup
Then user should be displayed with the "Geofence Settings" screen
And "Geofencing" value should be updated to "ON" on "Geofence Settings" screen
And user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|


#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions
@GeneralGlobalDrawerTapOnBackButtonInGeofenceRadiusWithoutUpdatingGeofenceCenter    @Automated
Scenario: As a user I want to verify if Geofence Settings screen is displayed when tapped on back button in Geofence Radius screen
Given user launches and logs in to the Lyric Application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user navigates to "Geofence Radius" screen from the "Geofence Settings" screen
And user selects "Back button" from "Geofence Radius" screen
Then user should be displayed with the "Geofence Settings" screen
And "Geofencing" value should be updated to "ON" on "Geofence Settings" screen
And user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|


#Geofence this location disabled and enabled when location serivce off 
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationserviceoff				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option when location service off under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "Location service" on the phone
And user configured the <Solution> with out geofence 
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with "Location services disabled" popup 
When user enables the "Locatiton service" on the phone 
Then user enables the "Geofence this Location" toggle button 
And user should be displayed with following "Geofence this location" options :
|Geofence this location |
|Geofence Radius |
|Location Status |
|Geofence Alert |
When user disabled the "Goefnece this location" 
Then user should dispaly with "Disabling geofencing" popup 
And user should not be displayed with following "Geofence this location" options :
|Geofence this location |
|Geofence Radius |
|Location Status |
|Geofence Alert |

Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|


#Geofence this location Permission pop up Dismiss
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solutions  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffDismiss				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with "Update Location Permissions" pop up
When user "Dismiss" the  "Update Location Permissions" pop up
Then user should be dsipalyed with disabled "Geofence this Location" option

Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|


#Geofence this location Permission pop up show me how
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solutions  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffshowmehow				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off -- show me how , under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with "Update Location Permissions" pop up
When user selects the "Show me how" button 
Then user should be displayed  with the "Question" screen
# YES 
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
# No When user selects any one "Questions"
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
When user selects the "Contact us" button
Then user should be displayed with "New Conversation" screen 
And user enters the "email" and "Your name"
And user enters the text in "Home can we help" field
And user attach the "Screen shot" 
And user attached file should be displayed in "How can we halp" field
When user selects the "Send" option 
Then user should be displayed with "Thanks for contacting us" pop up 
And user should be displayed with disabled "Geofence this location" option

Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|


#Geofence this location Permission pop up Go to settings
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solutions  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffGoToSettings				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off -- Go to settings , under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with "Update Location Permissions" pop up
When user selects the "Go to Settings" option 
Then user naivgates to "Location persmissions" screen
When user enables the "Location persmissions" 
Then user anviagtes to "Geofence option" screen
And should be displayed with enabled "Geofence this location" option
And user should be displayed with following "Geofence this location" options :
|Geofence this location |
|Geofence Radius |
|Location Status |
|Geofence Alert |

Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|


#Geofence radius -- FeofenceCenter.feature 
#				 -- GeofenceRadiusUpdate.feature
#				 -- GeofenceManager.feature
#				 -- GeofenceSet.feature	 
#Geofence Alert 
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solutions  with out Geofence 
@GenralGlobalDrawerVerifyPushNotificationWhenGeofenceAlertIsEnabledAndDisabled				@Automated
Scenario Outline:  As a user I want to verify if push notification is received when geofence alert toggle is enabled 
Given user has <Mode> system mode
And user thermostat is set to "geofence based" schedule
When user launches and logs in to the Lyric application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence this locaiton toggle" to "on"
When user changes the "Geofence Alert toggle" to "on"
And user thermostat set <Period> with <Geofence>
Then user receives a "Geofence crossed Home" push notification
And user thermostat set <UPeriod> with <UGeofence>
Then user receives a "Geofence crossed Away" push notification
When user clears all push notifications
Then user navigates to "Global Drawer" screen from the "Dashboard" screen 
And user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
When user changes the "Geofence Alert toggle" to "off"
And user thermostat set <Period> with <Geofence>
Then user should not receive a "Geofence crossed Home" push notification
And user thermostat set <UPeriod> with <UGeofence>
Then user should not receive a "Geofence crossed Away" push notification


Examples:
| Mode	| Period			| Geofence		| UPeriod		| UGeofence		|
| Heat	| Home			| UserArrived  	| Away			| UserDeparted	|
#| Cool	| UserArrived  	| Home			| Away			| UserDeparted	|


@GeneralGlobalDrawerLoginWithProductionEnv			@Automated
Scenario: Verify Geofence Screen after login
Given user launches and logs in to the Lyric Application
And user navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen

#ActivityHistory
#Requirements : single location with out any solution 
@GeneralGlobalDrawerWithoutSolutionActivityHistoryNoMsgsLabel				@Automated
Scenario: As a user i want to Verify No messages label in Activity History screen when no solution is configured for the logged in account 
Given user launches and logs in to the Lyric Application
When user navigates to "Activity History" screen from the "Dashboard" screen
Then user should be displayed with "No Messages label in Activity History screen"
And user should not be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Edit						|
Then user navigates to "Global Drawer" screen from the "Activity History" screen
#And user logs out of the app


#Activity history with all solution 
#Requirements : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solucations  and trigged all the events 
@GeneralGlobalDrawerWithSolutionActivityHistoryVerification				@NotAutomatable
Scenario Outline: As a user i want to view the under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
Given user launches and logs in to the Lyric Application
And user configured the <Solution>
When user navigates to "Activity History" screen from the "Dashboard" screen 
Then user should be displayed with All "triggered" event for all the <Solution>
When user selects the any "Message"
Then user should be navigated to respective "Message" screen 
And user navigates back to "Activity History" screen from "Message" screen 
When user selects the "Video Message" of "C1 or C2 or DAS -ISMV or OSMV"
Then user should be navigates to "Video Clip" screen 
And user should be displayed with one "Video Clip"
And user able to "Play the video clip"
And user should display the "Video Play" option once video completed
When user select the "Download" option 
Then user should "Download" the "video Clip"
And user should display with "Download success" pop up 
When user "Deletes" the clip"
Then user navigates to "Activity History" screen
When user selects "older video message" more than 24 hours of "C1 or C2 or DAS -ISMV or OSMV"
Then user should be navigates to "Video CLip" screen 
And user should be displayed with "Error" pop up as "Clip has expired"
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|
|WLD| 

#Activity history Edit selected message  with all solutions
#Requirements : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solutions  and triggered all the events 
@GeneralGlobalDrawerWithSolutionActivityHistoryDeleteAMessage				@Automated
Scenario: As a user i want to delete selected Activity history message with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  
Given user launches and logs in to the Lyric Application
When user navigates to "Activity History" screen from the "Dashboard" screen
When user selects "Edit" from "Activity History" screen
And the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Select All					|
Then the following "Activity History" options should be disabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "a message" from "Activity History" screen
Then the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "Cancel" from "Activity History" screen
Then user should not be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Select All					|
| Delete						|
When user selects "Edit" from "Activity History" screen
And the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Select All					|
Then the following "Activity History" options should be disabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "a message" from "Activity History" screen
Then the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "Delete" from "Activity History" screen 
Then "deleted message" should not be displayed in the "Activity History" screen
#And user logs out of the app

#Activity history Edit select all message  with all solutions
#Requirements : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solutions  and trigged all the events 
@GenralGlobalDrawerWithSolutionActivityHistoryDeleteAllMessages				@Automated
Scenario: As a user i want to delete all messages from Activity history with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  
Given user launches and logs in to the Lyric Application
When user navigates to "Activity History" screen from the "Dashboard" screen
When user selects "Edit" from "Activity History" screen
And the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Select All					|
Then the following "Activity History" options should be disabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "all messages" from "Activity History" screen
Then the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "Cancel" from "Activity History" screen
Then user should not be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Select All					|
| Delete						|
When user selects "Edit" from "Activity History" screen
And the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Select All					|
Then the following "Activity History" options should be disabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "all messages" from "Activity History" screen
Then the following "Activity History" options should be enabled:
| ActivityHistoryOptions		|
| Delete						|
And user should be displayed with the following "Activity History" options:
| ActivityHistoryOptions		|
| Cancel						|
When user selects "Delete" from "Activity History" screen 
Then user should be displayed with "No Messages label in Activity History screen"
#And user logs out of the app

#InviteUsers
#Requirements : single location with and with out any solution 
@GenralGlobalDrawerAddDeleteUsersFromInviteList				@Automated
Scenario Outline: As a user i want to Verify invite user functionality by adding and removing a user from invite list
Given user launches and logs in to the Lyric Application
When user navigates to "Invite User" screen from the "Dashboard" screen
And user inputs <invite users email address> in "Email Text Field" in the "Invite User" screen
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
When user logs out and logs in to the Lyric Application with <invite users email address>
Then user navigates to "Add Users" screen from the "Dashboard" screen
And user should not be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList						|
| User who invited the logged in user	|
#And user logs out of the app
#When user launches and logs in to the Lyric Application
When user logs out and logs in to the Lyric Application with "logged in users account"
Then user navigates to "Add Users" screen from the "Dashboard" screen
And user should not be displayed with the following "Invited Users" options:
| InvitedUsersList	|
| Logged in user		|
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
When user deletes the <invite users email address> from "Add Users" screen
Then user should receive a "Confirm Access Removal" popup
And user "Clicks on Remove in" the "Confirm Access Removal" popup
Then user should not be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
And user should be displayed with "No Invited Users label"
When user logs out and logs in to the Lyric Application with <invite users email address>
Then user navigates to "Add Users" screen from the "Dashboard" screen
And user should be displayed with "No Invited Users label"
#And user logs out of the app

Examples:
| invite users email address		|
| das_stage5@grr.la				|


#Requirements : single location with and with out any solution and user should be invited 
@GeneralGlobalDrawerInviteUserWithLoggedInUserEmail				@Automated
Scenario: As a user i want to Verify if error message displays when logged in users email address in Add Users
Given user launches and logs in to the Lyric Application
When user navigates to "Add Users" screen from the "Dashboard" screen
Then user should not be displayed with the following "Invited Users" options:
| InvitedUsersList	|
| Logged in user		|
When user navigates to "Invite User" screen from the "Add Users" screen
Then user inputs "Logged in users email address" in "Email Text Field" in the "Invite User" screen
And user should receive a "User already added to this account error" popup
Then user "Clicks on OK in" the "User already added to this account error" popup
When user navigates to "Add Users" screen from the "Invite User" screen
Then user should not be displayed with the following "Invited Users" options:
| InvitedUsersList	|
| Logged in user		|
#And user logs out of the app


#Requirements : single location with and with out any solution and user should be invited 
@GeneralGlobalDrawerInviteUserWithAlreadyInvitiedUsersEmail					@Automated
Scenario Outline: As a user i want to Verify if error message displays when already existing invited users email address is entered in Add Users 
Given user launches and logs in to the Lyric Application
When user navigates to "Invite User" screen from the "Dashboard" screen
And user inputs <invite users email address> in "Email Text Field" in the "Invite User" screen
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
When user navigates to "Invite User" screen from the "Add Users" screen
And user inputs <invite users email address> in "Email Text Field" in the "Invite User" screen
Then user should receive a "User already added to this account error" popup
And user "Clicks on OK in" the "User already added to this account error" popup
When user navigates to "Add Users" screen from the "Invite User" screen
And user deletes the <invite users email address> from "Add Users" screen
Then user should receive a "Confirm Access Removal" popup
And user "Cancels" the "Confirm Access Removal" popup
Then user should be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
When user deletes the <invite users email address> from "Add Users" screen
Then user should receive a "Confirm Access Removal" popup
And user "Clicks on Remove in" the "Confirm Access Removal" popup
Then user should not be displayed with the following "Invited Users" options:
| InvitedUsersList		|
| das_stage5@grr.la		|
#And user logs out of the app

Examples:
| invite users email address		|
| das_stage5@grr.la				|
