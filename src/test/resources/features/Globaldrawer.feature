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
When user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
When user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
When user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  with out Geofence 
@GeneralGlobalDrawerGeofenceDisabledEnableWhileDIY				@Automatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
#And user configured the <Solution> with out geofence 
And user Navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" from "Global Drawer" screen
Then user should be displayed with the "Geofence Settings" screen
And the following "Geofence Settings" options should be disabled:
| Options					|
| Geofence this Location		|
When user changes the "Geofence this locaiton toggle" to "on"
Then user should be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|
When user changes the "Geofence this locaiton toggle" to "off" 
#Then user should dispaly with "Disabling geofencing" pop up 
Then user should not be displayed with the following "Geofence this location" options:
| GeofenceThisLocation		|
| Geofence Radius			|
| Location Status			|
| Geofence Alert				|

Examples:
|Solution|
#|JasperNA|
#|JasperEMEA|
#|C1|
#|C2|
|DAS|


#Geofence this location disabled and enabled when location serivce off 
#Requirements : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solutions  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationserviceoff				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence option when location service off under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "Location service" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Global Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with "Location services disabled" pop up 
When user enables the "Locatiton service" on the phone 
Then user enables the "Geofence this Location" toggle button 
And user should be displayed with following "Geofence this location" options :
|Geofence this location |
|Geofence Radius |
|Location Status |
|Geofence Alert |
When user disabled the "Goefnece this location" 
Then user should dispaly with "Disabling geofencing" pop up 
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
And user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
And user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
And user Navigates to "Global Drawer" screen from the "Dashboard" screen 
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
@GenralGlobalDrawerGoefneceAlertdisabledEnablewhileDIY				@NotAutomatable
Scenario Outline:  As a user I wnat to verify the disabled and enabled geofence alert option, under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyric Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Geofence option" screen from the "Dashboard" screen 
When user enables the "Geofence Alerts" 
Then user should receive "Push notification" when th house is empty or someone is at home
When user disables the "Geofence Alerts" 
Then user should not receive "Push notification" when th house is empty or someone is at home

Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|


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
When user Navigates to "Activity History" screen from the "Dashboard" screen 
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
@GeneralGlobalDrawerWithSolutionActivityHistoryDeleteAMessage				@Automatable
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


#Activity history Edit select all message  with all solutions
#Requirements : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solutions  and trigged all the events 
@GenralGlobalDrawerWithSolutionActivityHistoryDeleteAllMessages				@Automatable
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


#InviteUsers
#Requirements : single location with and with out any solution 
@GenralGlobalDrawerAddUsersWithandwithoutsolutionVerification
Scenario Outline: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyric Application
When user Navigates to "Add Users" screen from the "Dashboard" screen 
Then user should be displayed with "No Invited Users" text with "Add Users" button
When user navigates to "Invite Users" screen from "Add Users" screen
Then user should displayed with disabled "Send" button 
Then user should be displayed with "Invite User" Header
When user enters the <Email> 
And user should displayed with enabled "Send" button
When user  Selects the "Send" button   
Then user should naivgtes to "Add users" screen from "Invite Users" screen 
And user should displayed with "Invited" <Email>
#And user <Email> should receive Email "Email Nitifciation" 
When user logged out from the user1
Then user logged in to the "user2"
And user should naivgates to "Activity history" screen from "dashboard" screen
And user should be displayed with the "Invited message"
Then user should able to access with "Invited location"
When user logged out from the user2
Then user logged in to the "user1"
Then remvoe invited user
#Android
And user "Tap on Hold" on "Invited" <Email> '
#iOS
Then user selects "Cancel(x)" button 
And user should be displayed with "Confirm Access Removal" pop up 
When user "Dismiss" the "Confirm Access Removal" pop up
Then user should be displayed with "Invited Users" <Email>
When user "accepts" the "Confirm Access Removal" pop up
Then user should not be displayed with "Invited" <Email> 
Examples:
|Email|
|ure@grr.la|
|sys@grr.la|


#Requirements : single location with and with out any solution and user should be invited 
@GenralGlobalDrawerAddUsersWithsameaccountErrorWithandwithoutsolutionVerification
Scenario: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyric Application
When user Navigates to "Invite Users" screen from the "Dashboard" screen 
Then user enters the "Email" of logged account 
And user should be displayed with "Error, The requested user was already added to this account" pop up

#Requirements : single location with and with out any solution and user should be invited 
@GenralGlobalDrawerAddUsersWithalreadyinvitedemailErrorWithandwithoutsolutionVerification
Scenario Outline: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyric Application
And user invited <Email>
When user Navigates to "Invite Users" screen from the "Dashboard" screen 
Then user enters the <Email> 
And user should be displayed with "Error, The requested user was already added to this account" pop up
Examples:
|Email|
|ure@grr.la|
