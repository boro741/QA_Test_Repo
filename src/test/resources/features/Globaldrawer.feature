@GenralGlobalDrawerUnderScenarios @Platform
Feature: Genral global drawer under scenarios 
	As a user I want to verify under global drawer scenaios 
	
	
#Single location with out any solution for UK Location
@GeneralGlobalDrawerWithoutSolutionVerificationForUKLocation			@Automated			@--xrayid:ATER-67850	
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
@GeneralGlobalDrawerWithoutSolutionVerificationForUSLocation				@Automated			@--xrayid:ATER-67851
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
@GeneralGlobalDrawerWithWLDSolutionVerificationForUKLocation				@Automated			@--xrayid:ATER-67852
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
@GeneralGlobalDrawerWithWLDSolutionVerificationForUSLocation				@Automated			@--xrayid:ATER-67853
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
@GeneralGlobalDrawerWithSolutionForDASC1C2VerificationForUKLocation			@Automated			@--xrayid:ATER-67854
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
@GeneralGlobalDrawerWithSolutionForDASC1C2VerificationForUSLocation			@Automated			@--xrayid:ATER-67855
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
@GeneralGlobalDrawerWithSolutionForJASPEREMEAVerificationForUKLocation			@Automated			@--xrayid:ATER-67856
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
@GeneralGlobalDrawerWithSolutionForJASPERNAVerificationForUSLocation			@Automated			@--xrayid:ATER-67857
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
@GeneralGlobalDrawerEnableAndDisableGeofence					@Automated			@--xrayid:ATER-67858
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
@GeneralGlobalDrawerUpdateGeofenceCenterAndTapOnBackButtonInGeofenceRadius     @Automated			@--xrayid:ATER-67859
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
@GeneralGlobalDrawerTapOnBackButtonInGeofenceRadiusWithoutUpdatingGeofenceCenter    @Automated			@--xrayid:ATER-67860
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
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationserviceoff				@NotAutomatable			@--xrayid:ATER-68224
Scenario Outline: As a user I want to verify the disabled and enabled geofence option when location service off under global drawer when configued solution with out geofence enable
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
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffDismiss				@NotAutomatable			@--xrayid:ATER-68225
Scenario Outline: As a user I want to verify the disabled and enabled geofence option when location permission off under global drawer when configued solution with out geofence enable
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
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffshowmehow				@NotAutomatable			@--xrayid:ATER-68226
Scenario Outline: As a user I want to verify the disabled and enabled geofence option when location permission off -- show me how , under global drawer when configued solution with out geofence enable
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
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffGoToSettings				@NotAutomatable			@--xrayid:ATER-68227
Scenario Outline: As a user I want to verify the disabled and enabled geofence option when location permission off -- Go to settings , under global drawer when configued solution with out geofence enable
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
@GenralGlobalDrawerVerifyPushNotificationWhenGeofenceAlertIsEnabledAndDisabled				@Automated			@--xrayid:ATER-67861
Scenario Outline: As a user I want to verify if push notification is received when geofence alert toggle is enabled
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
@GeneralGlobalDrawerWithoutSolutionActivityHistoryNoMsgsLabel				@Automated			@--xrayid:ATER-67862
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
@GeneralGlobalDrawerWithSolutionActivityHistoryVerification				@NotAutomatable			@--xrayid:ATER-68228
Scenario Outline: As a user i want to view the under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  
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
@GeneralGlobalDrawerWithSolutionActivityHistoryDeleteAMessage				@Automated			@--xrayid:ATER-67863
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
@GenralGlobalDrawerWithSolutionActivityHistoryDeleteAllMessages				@Automated			@--xrayid:ATER-67864
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
@GenralGlobalDrawerAddDeleteUsersFromInviteList				@Automated			@--xrayid:ATER-67865
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
@GeneralGlobalDrawerInviteUserWithLoggedInUserEmail				@Automated			@--xrayid:ATER-67866
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
@GeneralGlobalDrawerInviteUserWithAlreadyInvitiedUsersEmail					@Automated			@--xrayid:ATER-67867
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

      #Requirements : Single location with and with out any solution
      @GeneralGlobalDrawerInvitNewUser				@NotAutomatable			@--xrayid:ATER-68240
      Scenario Outline: As a user i want to verifry invite new user and with out activation the account user should not receive any alerts
       Given user launches and logs in to the Lyric Application
     When user navigates to "Invite User" screen from the "Dashboard" screen
      Then user inputs <invite users email address> in "Email Text Field" in the "Invite User" screen
     And user should be displayed with the following "Invited Users" options:
      | InvitedUsersList  | 
      | new account|
      When user navigates to "New user" Eamil account
      Then user trigger the events 
      And new user should not be receive events mail
      Then user should be displayed with invited mail 
      When new user downlaod the app or open the app
      Then user Activated accunt
      And user should be displayed with invited location details
      Examples:
      |invite users email address |
      |New account |

  
  #HomeAddress
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressfieldsclearthetextandvalidation             @Automatable			@--xrayid:ATER-68241
  Scenario: As a user i want to Verify the empty field text validation
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with “Home Address” header with location name
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
      And user should displayed with following field "Existing Address" details of "Edit Address" options :
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box state         | 
      | Edit Box Zipcode       | 
      | Country Change?        | 
      And user should displayed with disabled "SAVE" button
     When user clear the following "Edit Address" fields: 
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box state         | 
      | Edit Box Zipcode       | 
      And user should be displayed with in location edit box “Example : Home, cabin, office” text
      And user should be displayed with in Address edit box “Address” text
      And user should be displayed with in City edit box “City” text
      And user should be displayed with in State edit box “state” text
      And user should be displayed with in zip code edit box “zip code” text
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressEditWithoutsavingWithandwithoutsolutionVerification             @Automatable			@--xrayid:ATER-68242
  Scenario: As a user i want to Verify with out saving Edit existing address under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with “Home Address” header with location name
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
      And user should displayed with following field "Existing Address" details of "Edit Address" options :
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      | Country Change?        | 
      And user should displayed with disabled "SAVE" button
     When user enter the following "Edit Address" fields: 
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      And user should be displayed with enabled "SAVE"  button
     When user selects " BACK" button 
     Then user should displayed with "Cancel Location Changes?" pop up 
     When user "dismiss" the "Cancel Location Changes?" pop up 
     Then user selects " BACK" button 
     Then user should displayed with "Cancel Location Changes?" pop up 
     When user "Accepts" the "Cancel Location Changes?" pop up 
     Then user should navigates to "Home Address" screen
      And user "Existing Address" should not be updated
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressEditWithsavingWithandwithoutsolutionVerification            @Automatable			@--xrayid:ATER-68244
  Scenario: As a user i want to Verify with saving Edit existing address under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Exisiting Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
      And user should be displayed with following field "Exisiting Address" details of "Edit Address" options :
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      | Country Change?        | 
      And user should displayed with disabled "SAVE" button
     When user Edits the following "Edit Address" fields: 
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      And user should be displayed with enabled "SAVE"  button
     When user selects "SAVE" button 
     Then user should navigates to "Home Address" screen from "Edit Address" screen 
      And user should be displayed with updated "Existing Address" details 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressWithoutEditWithandwithoutsolutionVerification             @Automatable			@--xrayid:ATER-68246
  Scenario: As a user i want to Verify Cancel Location Changes? with out Edit existing address under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
      And user should displayed with following field "Existing Address" details of "Edit Address" options :
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      | Country Change?        | 
     Then user should navigates to "Home Address" screen
      And user should be displayed with "Existing Address" should not be updated
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressEditwithchangecountryWithandwithoutsolutionVerification             @Automatable			@--xrayid:ATER-68247
  Scenario Outline: As a user i want to Verify Edit existing address with change country under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Exisitng Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
      And user should be displayed with following field "Exisiting Address" details of "Edit Address" options :
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      | Country Change?        | 
     Then user should navigates to "Home Address" screen
      And user should be displayed with disabled "SAVE" button
     When user selects "Change Country?" button 
     Then user should navigates to "Please confirm your country" screen 
     When  user selects the <New Country> 
     Then user navigates to "Edit Address" screen with following "Edit Address" fields "Empty"
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box stat          | 
      | Edit Box Zipcode       | 
      And user should be displayed updated with <New Country> above "Change country" button 
      And user should displayed with disabled "SAVE" button
     When user Edits the following "Edit Address" fields: 
      | Edit Address           | 
      | Location Name          | 
      | Edit Box Location name | 
      | Address                | 
      | Edit Box Address       | 
      | Edit Box City          | 
      | Edit Box <State>       | 
      | Edit Box <Zipcode>     | 
      And user should be displayed with enabled "SAVE"  button
     When user selects "SAVE" button 
     Then user should navigates to "Home Address" screen from "Edit Address" screen 
      And user should be displayed with updated "Existing Address" details 
    Examples: 
      | New Country | State | ZipCode | 
      | Argentina   |       |         | 
      | Australia   |       |         | 
      | Austria     |       |         | 
      | Belgium     |       |         | 
      | Brazil      |       |         | 
      | Bulgaria    |       |         | 
      | Canada      |       |         | 
      | Chile       |       |         | 
      | Chine       |       |         | 
      | Colombia    | NY    | 11787   | 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerHomeAddressEditwithmaxalphanumericwithLocationname             @Automatable			@--xrayid:ATER-68248
  Scenario: As a user i want to Verify Edit existing address with change country under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
     Then “Location Name” should allow to enter max 30 alphanumeric
     When user selects "SAVE" button 
     Then user should navigates to "Home Address" screen from "Edit Address" screen 
      And user should be displayed with updated "Existing Address" details 
  
  @GenralGlobalDrawerHomeAddressEditwithspecialcharactorlocationname             @Automatable			@--xrayid:ATER-69066
  Scenario: As a user i want to Verify Edit existing address with special characters under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
     Then “Location Name” should not allow to enter special characters
     When user selects "SAVE" button 
     Then user should navigates to "Home Address" screen from "Edit Address" screen 
      And user should be displayed with updated "Existing Address" details 
  
  @GenralGlobalDrawerHomeaddressEditwithoutstartandendwithalphabeticorumeric             @Automatable			@--xrayid:ATER-69067
  Scenario: As a user i want to Verify Edit existing address with social characters under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "Home Address" screen from the "Dashboard" screen
     Then user should be displayed with "Existing Address" details 
     When user navigates to "Edit Address" screen from "Home Address" screen 
     Then “Location Name” should be edits <Update>
     When user selects "SAVE" button 
     Then user should be displayed with “Name must start and end with a letter or number” error pop up
     When user selects “OK” option
     Then user should be navigates to “Edit Address” screen 
  
  #@GenralGlobalDrawerHomeaddressfieldvalidationwitherror
  #@GenralGlobalDrawerCityfieldvalidatinmaxcharecters
  #@GenralGlobalDrawerCityfieldvalidatinwitherror
  #@GenralGlobalDrawerStatefieldvalidation
  #@GenralGlobalDrawerStatefieldvalidationerror
  #@GenralGlobalDrawerZipCodevalication
  #@GenralGlobalDrawerZipCodevalicationwitherror
  
  #Requirements : single location with out any solution             @Automatable
  @GenralGlobalDrawerHomeAddressDeleteLocationWithandwithoutsolutionVerification            @Automatable		@--xrayid:ATER-69069
  Scenario: As a user i want to Verify delete location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "Home Address" screen from the "Dashboard" screen
     When user selects "Delete Location" button 
     Then user should dispaly with "Delete Location?" pop up 
     When user "dismiss" the "Delete Location?" pop up
     Then user should be displayed with "HomeAddress" screen 
     When user selects "Delete Location" button 
     Then user should dispaly with "Delete Location?" pop up 
     When user "accepts" the "Delete Location?" pop up
     Then user navigates to "ADD New Device" screen 
  
  #Requirements : Two location with out any solution             @Automatable
  @GenralGlobalDrawerHomeAddressDeleteLocationWithandwithoutsolutionVerification            @Automatable		@--xrayid:ATER-69070
  Scenario: As a user i want to Verify delete location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "Home Address" screen from the "Dashboard" screen of "Location1" 
     When user selects "Delete Location" button 
     Then user should dispaly with "Delete Location?" pop up 
     When user "dismiss" the "Delete Location?" pop up
     Then user should be displayed with "HomeAddress" screen 
     When user selects "Delete Location" button 
     Then user should dispaly with "Delete Location?" pop up 
     When user "accepts" the "Delete Location?" pop up
     Then user navigates to "Dashboard" screen of  "Location2" 
  
  #@GenralGlobalDrawerWithsolutiondeleteoption
  
  #Edit Account 
  
  # Edit first name last name
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdate             @Automatable		@--xrayid:ATER-69076
  Scenario Outline: As a user i want to Verify Edit first name and last name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user edits the <first name> and <last name>
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user navigates to "Global Drawer" screen
     When user navigates to "Edit Account" screen from "Global Drawer" screen 
     Then the first name and last name should get updated 
    Examples: 
      | first name | last name | 
      | giri       | THEJ      | 
      | sami       | krishna   | 
      | vijay      | Govda     | 
      | anju       | sweets    | 
  
  #Edit first name last name with error
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69077
  Scenario: As a user i want to Verify save with error first name and last name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user clears the “first name” and “last name”
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user should be displayed with “The First and Last Name field is required” error pop up
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69079
  Scenario: As a user i want to Verify save with error first name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user clear the “First name”
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user should be displayed with “The First Name field is required” error pop up 
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69080
  Scenario: As a user i want to Verify save with error last name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user clear the “Last name”
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user should be displayed with “The Last Name field is required” error pop up 
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69081
  Scenario: As a user i want to Verify save with error first name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user clear the “First name”
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user should be displayed with “The First Name field is required” error pop up 
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69082
  Scenario: As a user i want to Verify save with error first name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user edits the “First name” and “Last name”
     Then “First Name” and “Last Name” should allow to edit max 40 characters.
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user navigates to "Global Drawer" screen
     When user navigates to "Edit Account" screen from "Global Drawer" screen 
     Then the first name and last name should get updated
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror             @Automatable		@--xrayid:ATER-69083
  Scenario: As a user i want to Verify save with error first name in Edit account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
      And user should be displayed with "Edit box" field with "Exists" details 
      And user should be displayed with Disabled "SAVE" button 
     When the user edits the “First name” and “Last name”
     Then “First Name” and “Last Name” should allow to edit max 40 characters
      And “First Name” and “Last Name” should allow to enter special characters
     Then user should displayed with enabled "SAVE button"
     When user selects "SAVE" button
     Then user navigates to "Global Drawer" screen
     When user navigates to "Edit Account" screen from "Global Drawer" screen 
     Then the first name and last name should get updated
  
  #Password change
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountChangepassword             @Automatable		@--xrayid:ATER-69084
  Scenario Outline: As a user i want to Verify change password  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "change password" screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user should be displayed with “Change Password” header with “Edit account” text
     Then user should be displayed with disabled "SAVE" button
     When user enters the <Old password> < New password> <Verify New password> edit fields
     Then user should be displayed with enabled "SAVE" button 
      And user should navigates to "Login" screen
      And user should "login" with <Verify New password>
    Examples: 
      | Old password   | New password   | Verify new password | 
      | Valid password | Valid password | Valid password      | 
  
  #change password with error
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerChangepasswordwithoutenteringSAVEoption             @Automatable		@--xrayid:ATER-69085
  Scenario: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user selects "SAVE" button
      And user should be displayed with below “Old password” edit box text “You must enter your password” with red color
      And user should be displayed with below “New Password” edit box text “You must enter your new password” with red color
      And user should be displayed with “SAVE” option disabled
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerChangepasswordwitholdpasswordwrong             @Automatable		@--xrayid:ATER-69086
  Scenario: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user enter the wrong “Old Password” 
      And user enters with “Valid New Password” and “Verify New Password” 
     When user selects the “SAVE” option
     Then user should be displayed with “Old password is invalid” text below “Old password” edit box with red color
      And user should be displayed with “SAVE” option disabled
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerChangepasswordwitholdpasswordInvalid             @Automatable		@--xrayid:ATER-69087
  Scenario: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user enter the invalid “Old Password” 
      And user enters with “Valid New Password” and “Verify New Password” 
     When user selects the “SAVE” option
     Then user should be displayed with “Invalid Password Format” text below “Old password” edit box with red color
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerChangepasswordwithinvalidNewpasswordandverifyPassword             @Automatable		@--xrayid:ATER-69088
  Scenario: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user enter the valid “Old Password” 
      And user enters with “invalid New Password” and “valid Verify New Password” 
     When user selects the “SAVE” option
     Then user should be displayed with “Invalid Password Format” text below “New Password” edit box with red color
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerChangepasswordwithNewpasswordandverifyPassworddontmatch             @Automatable		@--xrayid:ATER-69089
  Scenario Outline: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user enter the valid “Old Password” 
      And user enters with <New Password> and <Verify New Password> 
     When user selects the “SAVE” option
     Then user should be displayed with “Passwords don’t match” text below “New Password” edit box with red color
    Examples: 
      | New Password | Verify New Password | 
      | Valid        | Invalid             | 
      | Invalid      | Valid               | 
  
  #Requirements : single location with and with out any solution
  @GenralGlobaldrawerEditAccountChangepasswordwitherror             @Automatable		@--xrayid:ATER-69090
  Scenario Outline: As a user i want to Verify change password  with error under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to “Edit Account” screen from "Dashboard" screen 
     When user navigates "Change password" screen from "Edit Account" screen 
     Then user should be displayed with disabled "SAVE" button
     When user enters the <Old password> < New password> <Verify New password> edit fields
     Then user should be displayed with enabled "SAVE" button 
      And Password mismatch alert message should be displayed if New password and Verify new password are not the same
      And an alert message should be displayed if the password length is less then 8 characters 
      And an alert message should be displayed if the password and verify new password is not as per the defined regular expression
    Examples: 
      | Old password     | New password           | Verify new password    | 
      | Valid password   | Invalid/blank password | Invalid/blank password | 
      | Valid password   | Valid password         | Invalid/blank password | 
      | Valid password   | Invalid/blank password | Valid password         | 
      | Invalid password | Valid password         | Valid password         | 
      | Invalid password | Invalid/blank password | Invalid/blank password | 
      | Invalid password | Valid password         | Invalid/blank password | 
      | Invalid password | Invalid/blank password | Valid password         | 
  
  #Delete account with oK
  
  #Requirements : single location with out solution 
  @GenralGlobaldrawerEditAccountdeleteaccount             @Automatable		@--xrayid:ATER-69091
  Scenario: As a user i want to Verify delete account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
     When user selects "Delete Account" button 
     Then user should be displayed with "We're sorry to see you Go." pop up 
     When user selects the "NO" button
     Then user should be display with  "Edit Account" screen
     When user selects "Delete Account" button 
     Then user should be displayed with "We're sorry to see you Go." pop up 
     When user selects the "YES" button
     Then user navigates to "Login" screen
  
  #Delete account with learn more, with solution 
  
  #Requirements : single location with solution 
  @GenralGlobaldrawerEditAccountdeleteaccountwithSolution             @Automatable		@--xrayid:ATER-69092
  Scenario: As a user i want to Verify delete account  under  global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then navigates to "Edit Account" screen from "Dashboard" screen 
     When user selects "Delete Account" button 
     Then user should be displayed with "We're sorry to see you Go." pop up 
     When user selects the "OK" button
     Then user should be display with  "Edit Account" screen
     When user selects "Delete Account" button 
     Then user should be displayed with "We're sorry to see you Go." pop up 
     When user selects the "Learn more" button
     Then user navigates to "HELP" screen 
     When user selects "YES" button 
     Then user should be displayed with "You found this helpful" text  
      And user should not displayed with the "Options" 
     Then user should be display with  "Edit Account" screen
  
  # PIN implementaion pending 
  
  #@GenralEnaleDisableTogglebutton
  #@GenalMandetorypopup
  #@GenralSecuritypopup
  #@GenalwrongPINvalidation
  #@GenralWrongFingerPrintValication
  #@GenralcorrectPINValication
  #@GenralCorrectFingerPrintvalication
  #@GenralEnableDisableTogglebutton
  #@GenralEnableFingerPrintwithoutphonesecurity
  #@GernalForgetPIN
  #@GenralCreatePINwithsecurity
  #@GenralCreatePINWithoutsecurity
  
  #About the app
  
  #Requirements : single location with and with out any solution , US location 
  @GenralGlobalDrawerAbouttheappUSLocationVerification             @Automatable		@--xrayid:ATER-69093
  Scenario: As a user i want to Verify about the app with US Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "About the app" screen from the "Dashboard" screen
     Then user should be displayed with following "About the app" options:
      | About the app        | 
      | Get Help             | 
      | Rate the app         | 
      | Privacy Policy &EULA | 
      | Acknowledgements     | 
      | Version              | 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerAbouttheappUKLocationVerification             @Automatable		@--xrayid:ATER-69094
  Scenario: As a user i want to Verify about the app with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "About the app" screen from the "Dashboard" screen
     Then user should be displayed with following "About the app" options:
      | About the app        | 
      | Privacy Policy &EULA | 
      | Acknowledgements     | 
      | Version              | 
  
  #Privacy Policy & EULA
  
  #Requirements : single location with and with out any solution , US location 
  @GenralGlobalDrawerAboutthePrivacyPolicy&EULALocationVerification             @Automatable		@--xrayid:ATER-69095
  Scenario: As a user i want to Verify Privacy policy and EULA under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Privacy Policy & EULA" option
     Then user should be display with "Privacy Policy & EULA" screen
      And user navigates "About the app" screen from "Privacy Policy & EULA" screen
  
  #Requirements : single location with and with out any solution , US location 
  @GenralGlobalDrawerAbouttheappAcknowledgements             @Automatable		@--xrayid:ATER-69096
  Scenario: As a user i want to Verify Privacy policy and EULA under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Acknowledgements" option
     Then user should be display with "Acknowledgements" screen
      And user navigates "About the app" screen from "Privacy Policy & EULA" screen
  
  #App version
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappVersionLocationVerification             @Automatable		@--xrayid:ATER-69097
  Scenario: As a user i want to Verify version Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
     When user Navigates to "About the app" screen from the "Dashboard" screen
     Then user should be displayed with current app "Versioin x.x.x(xx)" 
  
  #GetHelp
  
  #Requirements : single location with and with out any solution , US location 
  @GenralGlobalDrawerAbouttheappGetHelpUSLocationVerification             @Automatable		@--xrayid:ATER-69098
  Scenario: As a user i want to Verify GetHelp US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "GET HELP" option
     Then user should open with "www.yourhome.honeywell.com/support" portal on browser 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerAbouttheappGetHelpUKLocationVerification             @Automatable		@--xrayid:ATER-69099
  Scenario: As a user i want to Verify GetHelp US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
     Then user Navigates to "About the app" screen from the "Dashboard" screen
      And user should not displayed "GET HELP" option
  
  #Rate the app only for android  
  
  #Rate the app close option
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheappClosestarUSLocationVerification             @Automatable		@--xrayid:ATER-69100
  Scenario: As a user i want to Verify Rate the app with close option,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects "Close" button
     Then user should be displayed with "About the app" option
  
  @GenralGlobalDrawerAbouttheappRatetheappbelow3starLocationVerificationwithfeedback             @Automatable		@--xrayid:ATER-69101
  Scenario Outline: As a user i want to Verify Rate the app with below 3 starts,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should allow to enter max 50 character with alphanumeric 
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Rate the app below 3 stars 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheappbelow3starLocationVerificationwithfeedback             @Automatable		@--xrayid:ATER-69102
  Scenario Outline: As a user i want to Verify Rate the app with below 3 starts,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
      And user minimise and maximise the app for three times
      And user should not be displayed "What do you think of Honeywell home app?" pop up
  #Helpshift portal to check feedback
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  @GenralGlobalDrawerAbouttheappRatetheappbelow3starVerificationwithoutfeedback
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheappbelow3starLocationVerificationwithoutfeedback             @Automatable		@--xrayid:ATER-69103
  Scenario Outline: As a user i want to Verify Rate the app with below 3 starts,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user disabled the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
      And user minimise and maximise the app for three times
      And user should not be displayed "What do you think of Honeywell home app?" pop up
  #Helpshift portal to check feedback with any account and device information
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheappFeedbackscreenbackVerification             @Automatable		@--xrayid:ATER-69104
  Scenario Outline: As a user i want to Verify Feedback back button,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user disable the "Toggle" button 
     When user selects the "Back" button 
     Then user should be displayed with "About the app" screen  with out any error
      And user minimise and maximise the app for three times
      And user should be displayed "What do you think of Honeywell home app?" pop up
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Rate the app below 4-5 stars with close 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheapp4-5starcloseVerification             @Automatable		@--xrayid:ATER-69105
  Scenario Outline: As a user i want to Verify Rate the app with 4-5 starts and verify close option,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Close" option 
     Then user should be displayed with "About the app" screen 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Rate the app below 4-5 stars with continue 
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerAbouttheappRatetheapp4-5starcontinueVerification             @Automatable		@--xrayid:ATER-69106
  Scenario Outline: As a user i want to Verify Rate the app with 4-5 starts and verify continue option,  US Location under About the app with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects the "Rate the app" option
     Then user should be displayed with "What do you think of Honeywell home app?" pop up 
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Continue" option 
     Then user should navigates to "Play-store" app 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesminimzeandmaximizebelow3star             @Automatable		@--xrayid:ATER-69107
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Minimise and Maximise the app for three times
      And user should be displayed with “"What do you think of Honeywell home app?" pop up
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
  #Helpshift portal to check feedback
    Examples: 
      | star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesminimzeandmaximize4-5star             @Automatable		@--xrayid:ATER-69108
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Minimise and Maximise the app for three times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Continue" option 
     Then user should navigates to "Play-store" app 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesLogoutandloginbelow3star             @Automatable		@--xrayid:ATER-69109
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
  #Helpshift portal to check feedback
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesLogoutandlogin4-5starcontinueoption             @Automatable		@--xrayid:ATER-69110
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for three times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Continue" option 
     Then user should navigates to "Play-store" app 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesLogoutandlogin4-5starcloseoption             @Automatable		@--xrayid:ATER-69111
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for three times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the “Close” option 
     Then user should navigates to “About the app” screen
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesminimzeandmaximizebelow3star             @Automatable		@--xrayid:ATER-69112
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Minimise and Maximise the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
  #Helpshift portal to check feedback
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesminimzeandmaximize4-5starcontinueoption             @Automatable		@--xrayid:ATER-69113
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Minimise and Maximise the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Continue" option 
     Then user should navigates to "Play-store" app 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesminimzeandmaximize4-5starcloseoption             @Automatable		@--xrayid:ATER-69114
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Minimise and Maximise the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the “Close” option 
     Then user should navigates to “About the app” screen 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup3timesLoginandlogoutbelow3star             @Automatable		@--xrayid:ATER-69115
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
  #Helpshift portal to check feedback
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesloginandlogout4-5starcontinueoption             @Automatable		@--xrayid:ATER-69116
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the "Continue" option 
     Then user should navigates to "Play-store" app 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesLogoutandlogin4-5starcloseoption             @Automatable		@--xrayid:ATER-69119
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise the app for 3 times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and Login the app for three times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 12 times
     When user selects <star> button
     Then user should be displayed with "Thanks for your rating" pop up
     When user select the “Close” option 
     Then user should navigates to “About the app” screen 
    Examples: 
      | Star | 
      | 4    | 
      | 5    | 
  
  #Requirements : single location with and with out any solution
  @GenralRatetheapppopup15timesLoginandlogoutandmaximiseandminise             @Automatable		@--xrayid:ATER-69120
  Scenario Outline: As a user I want to verify the pop up if app minimise and maximise , and logout and login the app 15times 
    Given user launches and logs in to the Lyric Application
     Then user Logout and login to the app for two times
      And user manimze and maximise the app for 1 times
      And user should be displayed with "What do you think of Honeywell home app?" pop up
     When user selects “Dismiss” option
     Then user should not be displayed with "What do you think of Honeywell home app?" pop up
     Then user Minimise and Maximise the app for 11 times
     Then user Logout and login to the app for 1 times
     When user selects <star> button
     Then user should be displayed with "App Feedback" screen
      And user should be displayed with the disabled "SEND FEEDBACK" and "Toggle" button
     When user enter the "Feedback" text in "Feedback Edit Box"
     Then user should be displayed with enabled "SEND FEEDBACK" button
      And user enables the "Toggle" button 
     When user selects the "SEND FEEDBACK" button
     Then user should be displayed with "About the app" screen 
  #Helpshift portal to check feedback
    Examples: 
      | Star | 
      | 1    | 
      | 2    | 
      | 3    | 
  
  #FAQs
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerFAQsUKLocationVerification             @Automatable		@--xrayid:ATER-69125
  Scenario: As a user i want to Verify FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects "FAQs" option 
     Then user should be navigates to "FAQs" screen 
      And user should be displayed with following "FAQs" list:
      | FAQs                | 
      | General             | 
      | Thermostat          | 
      | Water leak detector | 
      | Camera              | 
  
  #FAQs General 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerFAQsGenralUKLocationVerification             @Automatable		@--xrayid:ATER-69126
  Scenario: As a user i want to Verify General FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects "FAQs" option 
     Then user should be navigates to "FAQs" screen 
      And user should be displayed with following "FAQs" list:
      | FAQs                | 
      | General             | 
      | Thermostat          | 
      | Water leak detector | 
      | Camera              | 
     When user selects "General" option 
     Then user should be displayed with "General" screen with respective "Questions"
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "YES" button 
     Then user should be displayed with "You found this helpful" text  
      And user should not displayed with the "Options" 
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "NO" button 
     Then user should be displayed with "You didn't find this helpful" text 
      And user should not displayed with the "Options" 
      And suer navigates to "General" screen from " Questions" screen
  
  #FAQs Thermostat 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerFAQsThermostatUKLocationVerification             @Automatable		@--xrayid:ATER-69127
  Scenario: As a user i want to Verify Thermostat FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects "FAQs" option 
     Then user should be navigates to "FAQs" screen 
      And user should be displayed with following "FAQs" list:
      | FAQs                | 
      | General             | 
      | Thermostat          | 
      | Water leak detector | 
      | Camera              | 
     When user selects "Thermostat" option 
     Then user should be displayed with "General" screen with respective "Questions"
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "YES" button 
     Then user should be displayed with "You found this helpful" text  
      And user should not displayed with the "Options" 
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "NO" button 
     Then user should be displayed with "You didn't find this helpful" text 
      And user should not displayed with the "Options" 
      And suer navigates to "General" screen from " Questions" screen
  
  #FAQs WLD 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerWaterleakdetectorFAQsUKLocationVerification             @Automatable		@--xrayid:ATER-69129
  Scenario: As a user i want to Verify WLD FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects "FAQs" option 
     Then user should be navigates to "FAQs" screen 
      And user should be displayed with following "FAQs" list:
      | FAQs                | 
      | General             | 
      | Thermostat          | 
      | Water leak detector | 
      | Camera              | 
     When user selects "Water leak detector" option 
     Then user should be displayed with "General" screen with respective "Questions"
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "YES" button 
     Then user should be displayed with "You found this helpful" text  
      And user should not displayed with the "Options" 
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "NO" button 
     Then user should be displayed with "You didn't find this helpful" text 
      And user should not displayed with the "Options" 
      And suer navigates to "General" screen from " Questions" screen
  
  #FAQs CAMERA 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerCameraFAQsUKLocationVerification             @Automatable		@--xrayid:ATER-69131
  Scenario: As a user i want to Verify camera FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "About the app" screen from the "Dashboard" screen
     When user selects "FAQs" option 
     Then user should be navigates to "FAQs" screen 
      And user should be displayed with following "FAQs" list:
      | FAQs                | 
      | General             | 
      | Thermostat          | 
      | Water leak detector | 
      | Camera              | 
     When user selects "camera" option 
     Then user should be displayed with "General" screen with respective "Questions"
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "YES" button 
     Then user should be displayed with "You found this helpful" text  
      And user should not displayed with the "Options" 
     When user selects any one "Questions"
     Then user navigates to respective "Questions" screen
     When user selects "NO" button 
     Then user should be displayed with "You didn't find this helpful" text 
      And user should not displayed with the "Options" 
      And user navigates to "General" screen from " Questions" screen
  
  #FAQs search box 
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerFAQsThermostatUKLocationVerification             @Automatable		@--xrayid:ATER-69132
  Scenario: As a user i want to Verify Thermostat FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "FAQs" screen from the "Dashboard" screen
     When user enters the text in "Search" edit box 
     Then user should be display with respective "Questions"
  
  #FAQs search box with no FAQs
  
  #Requirements : single location with and with out any solution , UK location 
  @GenralGlobalDrawerNoFAQsUKLocationVerification             @Automatable		@--xrayid:ATER-69133
  Scenario Outline: As a user i want to Verify No FAQs with UK Location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "FAQs" screen from the "Dashboard" screen
     When user enters the <text> text in "Search" edit box 
     Then user should be displayed with "No FAQs Found" text
    Examples: 
      | Text | 
      | acbd | 
  
  #Logout
  
  #Requirements : single location with and with out any solution
  @GenralGlobalDrawerlogoutwithlocationVerification             @Automatable		@--xrayid:ATER-69134
  Scenario: As a user i want to Verify logout with location under global drawer with and with out solution 
    Given user launches and logs in to the Lyric Application
      And user Navigates to "Global Drawer" screen from the "Dashboard" screen
     When user selects "Logout" options
     Then user should be displayed with "Login" Screen
  
  #Withoutlocation
  #Requirements : No location
  @GenralGlobalDrawerlogoutwithnolocationVerification             @Automatable		@--xrayid:ATER-69135
  Scenario: As a user I want to verify logout with out location
    Given user launches and logs in to the Lyric Application
     Then user selects “Back/Cancel” option from the “Add new Device” screen
      And user should be displayed with “Exit Honeywell Home?” Pop up
     When user select “Cancel” option from “Exit Honeywell Home?” Pop up
     Then user should be displayed with “Add new Device” screen
     When user selects “Back/Cancel” option from the “Add new Device” screen
     Then user should be displayed with “Exit Honeywell Home?” Pop up
     When user selects the “Sign out” option
     Then user should be displayed with “login” screen
  
  #Delete account
  #Feature: As an user, I want to delete my account from the app. @LYR-22282
  @SingleUserDeleteAccountWithNoLocationNoDeviceNoMembership             @Automatable 		@--xrayid:ATER-69136  
  Scenario: To verify user is able to delete his account if there are no devices, no locations and no Membership linked to the account
    Given app is launched
      And user creates account and email got verified.	
     When user lands in “Add New Device” screen
      And user clicks on BACK button
     Then verify user should receive the Pop up screen with “Exit Honeywell Home?” Pop up.
     When user Clicks on “DELETE ACCOUNT” link     
     Then verify user should receive a “Delete Account-Sorry to see you go” Page/Pop up.
     When user clicks of NO button
     Then verify user should navigate back to “Add New Device” screen
     When user clicks on BACK button
     Then verify user should receive the Pop up screen with Exit Honeywell Pop up.
     When user Clicks on “DELETE ACCOUNT” button     
     Then verify user should receive a ““DELETE ACCOUNT”-Sorry to see you go” ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user Clicks on YES button
     Then verify user should navigate to Login screen
      And verify user should receive a pop-up saying “Your Account & Data is Deleted”.
      And verify user is unable to login with same credentials
  
  @SingleUserDeleteAccountWithNoDeviceNoMembership             @Automatable		@--xrayid:ATER-69137
  Scenario: To verify user is able to delete his account if there are no devices in any locations any no Membership linked to the account 
    Given app is launched
      And user creates account and email got verified.
      And user created a location only without any device.
     When user navigates to “Edit Account” screen from “Dashboard” screen
      And user clicks on “DELETE ACCOUNT” link.
     Then verify user should receive a ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user clicks of NO button
     Then verify user should navigate back to “Edit Account” screen
     When user clicks on “DELETE ACCOUNT” link
     Then verify user should receive a ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user clicks on YES button
     Then verify user should receive a pop-up saying “Your Account & Data is Deleted”.
      And verify user should navigate to Login screen. 
      And verify user is unable to login with same credentials
  
  @SingleUserWithUnsharedDeviceWithCameraSubscriptions             @Automatable		@--xrayid:ATER-69138
  Scenario Outline: To verify user is not able to delete his account when he has a unshared device or a Camera Subscription in it
    Given app is launched
      And user taps on LOGIN
      And user should have <DEVICE> in one LOCATION
      And user should not have any CAMERA SUBSCRIPTIONS
     When user navigates to “Edit Account” screen from “Dashboard” screen
      And user clicks on “DELETE ACCOUNT” link
     Then verify user should receive a pop up saying “Actions required before deleting your account”
     Then verify user can click and navigate to the the “Delete All Devices” FAQ Page from the link.
     When user clicks of BACK or OK button
     Then verify user should navigate back to Edit Account screen.
  Examples: 
      | DEVICE                              | 
      | Smart Home Security                 | 
      | Lyric Round Wi-Fi Thermostat        | 
      | D6 Pro Wifi Ductless Controller     | 
      | T5 Wifi Thermostat                  | 
      | T6 Pro Wifi Thermostat              | 
      | Wifi Water Leak and Freeze Detector | 
      | C1 Wifi Security Camera             | 
      | C2 Wifi Security Camera             | 
      | Lyric Smart Controller              | 
  
  @DeleteAccountWithLocationHavingNoDeviceWithCameraSubscription             @Automatable		@--xrayid:ATER-69139
  Scenario: To verify user is unable to delete his account when he has a unshared device or a Camera Subscription in it
    Given app is launched
      And user creates account and email got verified.
      And user created a location only without any device.
      And user should have a valid CAMERA SUBSCRIPTION
     When user navigates to “Edit Account” screen from “Dashboard” screen
      And user clicks on “DELETE ACCOUNT” link
     Then verify user should receive a pop up saying “Actions required before deleting your account ”
     Then verify user can click and navigate to the the “Cancel Your Honeywell Membership” FAQ Page from the link
     When user clicks of BACK or OK button
     Then verify user should navigate back to Edit Account screen.
  
  @DeleteAccountWithLocationHavingDeviceWithsubscription             @Automatable		@--xrayid:ATER-69140
  Scenario Outline: To verify user is unable to delete his account when he has a unshared device or a Camera Subscription in it
    Given app is launched
      And user taps on LOGIN
      And user should have <DEVICE> in one LOCATION
      And user should have a valid CAMERA SUBSCRIPTION
     When user navigates to “Edit Account” screen from “Dashboard” screen
      And user clicks on “DELETE ACCOUNT” link
     Then verify user should receive a pop up saying “Actions required before deleting your account ”
      And verify user can click and navigate to the the “Delete All Devices” FAQ Page from the link.
      And verify user can click and navigate to the the “Cancel Your Honeywell Membership” FAQ Page from the link
     When user clicks of BACK or OK button
     Then verify user should navigate back to Edit Account screen.
  Examples: 
      | DEVICE                              | 
      | Smart Home Security                 | 
      | Lyric Round Wi-Fi Thermostat        | 
      | D6 Pro Wifi Ductless Controller     | 
      | T5 Wifi Thermostat                  | 
      | T6 Pro Wifi Thermostat              | 
      | Wifi Water Leak and Freeze Detector | 
      | C1 Wifi Security Camera             | 
      | C2 Wifi Security Camera             | 
      | Lyric Smart Controller              | 
  
  @MultipleDeviceDeleteSameAccountRestAllDeviceLogsOut             @Automatable		@--xrayid:ATER-69141
  Scenario Outline: To verify all logged in devices should log out if account is deleted
    Given app is launched in two devices
      And user taps on LOGIN in both the devices with same account.
      And user should have <DEVICE> in one LOCATION
     When user navigates to “Edit Account” screen from “Dashboard” screen in one device
      And user clicks on “DELETE ACCOUNT” link in one device.
     Then verify user should receive a pop up saying “Actions required before deleting your account”
      And verify user can click and navigate to the the “Delete All Devices” FAQ Page from the link.
      And Click on OK/BACK button.
     When user deletes the <DEVICE> from dashboard.
      And user navigates to “Edit Account” screen from “Dashboard” screen in one device
      And user clicks on “DELETE ACCOUNT” link in one device.
     When user clicks on YES button
     Then verify user should receive a pop-up saying “Your Account & Data is Deleted”.
      And verify both users should navigate to Login screen. 
      And verify both users are unable to login with same credentials
     When user clicks of NO button
     Then verify user should navigate back to “Edit Account” screen.
  
  Examples: 
      | DEVICE                              | 
      | Smart Home Security                 | 
      | Lyric Round Wi-Fi Thermostat        | 
      | D6 Pro Wifi Ductless Controller     | 
      | T5 Wifi Thermostat                  | 
      | T6 Pro Wifi Thermostat              | 
      | Wifi Water Leak and Freeze Detector | 
      | C1 Wifi Security Camera             | 
      | C2 Wifi Security Camera             | 
      | Lyric Smart Controller              | 
  
  @SingleAccountMultipleLoginsDeleteLocationRestAllDeviceAddDeviceScreen             @NotAutomatable		@--xrayid:ATER-69142
  Scenario: To verify all logged in devices should navigate to add device screen when a location is deleted
    Given app is launched in two devices 
      And user creates account and email got verified.
      And user created a location only without any device.
     When any device user navigates to “Home Address” screen from “Dashboard” screen in one device
      And any device user clicks on “DELETE LOCATION” link in any device
      And Clicks on DELETE in Confirmation page.
     Then both the devices should navigate to Add Device Screen.
     When any user clicks on BACK button
     Then verify user should receive the Pop up screen with “Exit Honeywell Home?” Pop up.
     When user Clicks on “DELETE ACCOUNT” link         
     Then verify user should receive a ““DELETE ACCOUNT”-Sorry to see you go” ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user Clicks on YES button
     Then verify both device should navigate to Login screen
      And verify user should receive a pop-up saying “Your Account & Data is Deleted” in one device.
      And verify user is unable to login with same credentials in any device.
  
  
  @MultipleAccountSharedLocationDeletedInOneAccount             @Automatable		@--xrayid:ATER-69143
  Scenario Outline: To verify user is navigated to add device screen when a shared location is deleted and if account is deleted
    Given app is launched in two devices
      And both users taps on LOGIN in with different accounts
      And both users should have <DEVICE> in one LOCATION
      And first user must have shared his location with second user account
     When second user navigates to “Home Address” screen from “Dashboard” screen in one device
      And second user clicks on “Delete Address” link in second device
     Then verify second user should be able to delete first users location
      And verify first user should not be able to see that deleted location and device.
      And verify second user can still have access to its own location.
     When first user navigates to “Edit Account” screen from “Dashboard” screen in one device
      And first user clicks on “DELETE ACCOUNT” link in one device
     Then verify first user should receive a ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user clicks of NO button
     Then verify user should navigate back to “Edit Account” screen
     When first user clicks on YES button
     Then verify user should receive a pop-up saying “Your Account & Data is Deleted”
      And verify both users should navigate to Login screen. 
      And verify both users are unable to login with same credentials
  
  Examples: 
      | DEVICE                              | 
      | Smart Home Security                 | 
      | Lyric Round Wi-Fi Thermostat        | 
      | D6 Pro Wifi Ductless Controller     | 
      | T5 Wifi Thermostat                  | 
      | T6 Pro Wifi Thermostat              | 
      | Wifi Water Leak and Freeze Detector | 
      | C1 Wifi Security Camera             | 
      | C2 Wifi Security Camera             | 
      | Lyric Smart Controller              | 
  
  @InvitedUserHaveAccessToDeletedPrimaryUserLocationAndDevice             @Automatable		@--xrayid:ATER-69144
  Scenario Outline: To verify invited user is able to access the primary user shared location and device even if primary user deleted its account
    Given app is launched in two devices
      And both users taps on LOGIN in with different accounts
      And both users should have <DEVICE> in one LOCATION
      And primary user must have shared his location with invited user.
     When primary user navigates to “Edit Account” screen from “Dashboard” screen in one device
      And primary user clicks on “DELETE ACCOUNT” link in one device
     Then verify user should receive a ““DELETE ACCOUNT”-Sorry to see you go” ““DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user Clicks on YES button
     Then verify primary user should navigate to Login screen. 
      And verify primary user should receive a pop-up saying “Your Account & Data is Deleted”.
      And verify primary user is unable to login with same credentials
      And verify invited user is still able to access the primary location and device.
  Examples: 
      | DEVICE                              | 
      | Smart Home Security                 | 
      | Lyric Round Wi-Fi Thermostat        | 
      | D6 Pro Wifi Ductless Controller     | 
      | T5 Wifi Thermostat                  | 
      | T6 Pro Wifi Thermostat              | 
      | Wifi Water Leak and Freeze Detector | 
      | C1 Wifi Security Camera             | 
      | C2 Wifi Security Camera             | 
      | Lyric Smart Controller              | 
  
  @DeleteAllLocationsAndThenDeleteAccount             @Automatable		@--xrayid:ATER-69147
  Scenario: To Verify user is navigated to add device screen when all locations are deleted and account is deleted when user clicks on “DELETE ACCOUNT” with appropriate pop up
    Given app is launched
      And user creates account and email got verified
      And user creates Location1 without any device
      And user creates Location2 without any device
     When user navigates to “Home Address” screen from “Dashboard” screen
      And user clicks on “DELETE LOCATION” link in Location2
      And Clicks on DELETE in Confirmation page.
     Then user should be directed to Location1 Dashboard.
     When user navigates to “Home Address” screen from “Dashboard” screen
      And user clicks on “DELETE LOCATION” link in Location1
      And Clicks on DELETE in Confirmation page.
     Then user should be directed to “Add New Device” screen
      And user clicks on BACK button
     Then verify user should receive the Pop up screen with “Exit Honeywell Home?” Pop up
     When user Clicks on “DELETE ACCOUNT” button     
     Then verify user should receive a “DELETE ACCOUNT”-Sorry to see you go” “DELETE ACCOUNT”-Sorry to see you go” Page/Pop up.
     When user Clicks on YES button
     Then verify user should navigate to Login screen
      And verify user should receive a pop-up saying “Your Account & Data is Deleted”.
      And verify user is unable to login with same credentials
  
  #Feature: User should be blocked from using app if device is having invalid date and time @LYR23886/22361
  @InvalidmobiledeviceDateOrTimeAndAppisLoggedout             @NotAutomatable		@--xrayid:ATER-69148
  Scenario: To verify the user has blocked using app with device time invalid  
  #Precondition user have invalid date or time or both
    Given app is launched 
     When user taps on LOGIN with valid credential
     Then user should get critical alert ‘The date & time of your mobile device is incorrect. To continue, correct the date and time in your mobile device’s settings’ with ‘Close’ button 
     When ‘Close’ is clicked
     Then user should be in login page 
  #invalid time :  +/- 31 min to current time Invalid date : Any dates other than current date
     When user correct the date/time
     Then user should be allowed to login to the app with valid credential
  
  @InvalidmobiledeviceDateOrTimeAndAppisLoggedin             @NotAutomatable		@--xrayid:ATER-69150
  Scenario: To verify the user has blocked using app with device time invalid  
  #Precondition user have invalid date or time or both
      And app is launched 
    Given app got session time out(60 min) or force closed 
  #No session API called during DIY, so no session timeout holds good while DIY
     When user tries to access the app again
     Then  Then user should get critical alert ‘The date & time of your mobile device is incorrect. To continue, correct the date and       time in your mobile device’s settings’ with ‘Retry’ button 
     When user clicks on ‘Retry’ user should still see the popup
     When user correct the date/time
     Then user should be allowed to access the app 
  
  @InvalidmobiledeviceDateOrTimeAndCreateAccount             @NotAutomatable		@--xrayid:ATER-69151
  Scenario: To verify the user has blocked using app with device time invalid  
  #Precondition user have invalid date or time or both
    Given app is launched 
     When user tries to Create account
     Then user should get critical alert ‘The date & time of your mobile device is incorrect. To continue, correct the date and       time in your mobile device’s settings’ with ‘Retry’ button
     When user correct the date/time
     Then user should be allowed to access the app 
