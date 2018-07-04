@GenralGlobalDrawerUnderScenarios
Feature: Genral global drawer under scenarios 
	As a user I want to verify under global drawer scenaios 
	
	
#Requirments : single location with out any solution 
@GenralGlobalDrawerWithsolutionVerification
Scenario Outline: As a user i want to view the under global drawer scenarios  
Given user launches and logs in to the Lyic Application
When user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer" options:
|Global Drawer |
|Home | #Header 
|Activity history |
|Add Users|
|Home Address | 
|Account | #Header 
|Honeywell Membership |
|Edit Account |
|About the app |
|FAQs |#for UK location
|Logout |


#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
@GenralGlobalDrawerWithsolutionexpectWLDVerification
Scenario Outline: As a user i want to view the under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
Given user launches and logs in to the Lyic Application
And user configured the <Solution>
When user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer" options:
|Global Drawer |
|Automation| #Header
|Geofence |
|Home | #Header 
|Activity history |
|Add Users|
|Home Address | 
|Account | #Header 
|Honeywell Membership |
|Edit Account |
|About the app |
|FAQs| #for UK location
|Logout |
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|
 
 
#Requirments : single location with WLD
@GenralGlobalDrawerWithWLDVerification
Scenario Outline: As a user i want to view the under global drawer scenarios  with WLD
Given user launches and logs in to the Lyic Application
And user configured the <Solution>
When user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
Then user should be displayed with the following "Global Drawer" options:
|Global Drawer |
|Home | #Header 
|Activity history |
|Add Users|
|Home Address | 
|Account | #Header 
|Honeywell Membership |
|Edit Account |
|About the app |
|Logout |
Examples:
|Solution|
|WLD|

#Geofence 

# Geofence this location disabled and enabled

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIY
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence option under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
And user configured the <Solution> with out geofence 
And user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be displayed with following "Geofence this location" options :
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

#Geofence this lcoatio ndisabled and enabled when location serivce off 

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationserviceoff
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence option when location service off under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
And user turn off the "Location service" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be dispalyed with "Location services disabled" pop up 
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

# Geofence this location Permission pop up Dismiss

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffDismiss
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be dispalyed with "Update Location Permissions" pop up
When user "Dismiss" the  "Update Location Permissions" pop up
Then user should be dsipalyed with disabled "Geofence this Location" option 
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|

# Geofence this location Permission pop up show me how

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffshowmehow
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off -- show me how , under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be dispalyed with "Update Location Permissions" pop up
When user selects the "Show me how" button 
Then user should be dispalyed  with the "Question" screen
# YES 
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
# No When user selects any one "Questions"
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
When user selects the "Contact us" button
Then user should be dispalyed with "New Conversation" screen 
And user enters the "email" and "Your name"
And user enters the text in "Home can we help" field
And user attach the "Screen shot" 
And user attached file should be displayed in "How can we halp" field
When user selects the "Send" option 
Then user should be dispalyed with "Thanks for contacting us" pop up 
And user should be dispalyed with disabled "Geofence this location" option
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|

# Geofence this location Permission pop up Go to settings

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefnecedisabledEnablewhileDIYwhenlocationpermissionoffGoToSettings
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence option when location permission off -- Go to settings , under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
And user turn off the "locaiton permission" on the phone
And user configured the <Solution> with out geofence 
And user Navigates to "Glogal Drawer" screen from the "Dashboard" screen 
When user selects "Geofence" button 
Then user navigates to "Geofence option" screen
And user disaplyed with disabled "Geofence this Location" option 
When user enables the "Geofence this Location" toggle button 
Then user should be dispalyed with "Update Location Permissions" pop up
When user selects the "Go to Settings" option 
Then user naivgates to "Location persmissions" screen
When user enables the "Location persmissions" 
Then user anviagtes to "Geofence option" screen
And should be dispalyed with enabled "Geofence this location" option
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
				 -- GeofenceRadiusUpdate.feature
				 -- GeofenceManager.feature
				 -- GeofenceSet.feature
				 

				 
# Geofence Alert 

#Requirments : single location with jasperNA or JapserEMEA or C1 or C2 or DAS  or all the solucations  with out Geofence 
@GenralGlobalDrawerGoefneceAlertdisabledEnablewhileDIY
Scenaio outline:  As a user I wnat to verify the disabled and enabled geofence alert option, under global drawer when configued solution with out geofence enable 
Given user launches and logs in to the Lyic Application
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
 
#Requirments : single location with out any solution 
@GenralGlobalDrawerActivityHistorywithoutsolutionVerification
Scenario Outline: As a user i want to Verify Activity History under gloabl drawer with no solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Activity History" screen from the "Dashboard" screen 
Then user should be displayed with "No Message" text 
And user should not displayed with "Edit" option on top right corner 
And user should naivgates to "Global Drawer" screen from " Activity History" screen 

#Activity history with all solution 

#Requirments : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solucations  and trigged all the events 
@GenralGlobalDrawerWithsolutionActivityHistoryVerification
Scenario Outline: As a user i want to view the under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
Given user launches and logs in to the Lyic Application
And user configured the <Solution>
When user Navigates to "Activity History" screen from the "Dashboard" screen 
Then user should be dispalyed with All "triggered" event for all the <Solution>
When user selects the any "Message"
Then user should be naviagtes to respective "Message" screen 
And user naviagtes back to "Activity History" screen from "Message" screen 
When user selects the "Video Message" of "C1 or C2 or DAS -ISMV or OSMV"
Then user should be navigates to "Video CLip" screen 
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

#Requirments : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solucations  and trigged all the events 
@GenralGlobalDrawerWithsolutionActivityHistoryEditselectedVerification
Scenario Outline: As a user i want to Activity history edit selected  option under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
Given user launches and logs in to the Lyic Application
And user configured the <Solution>
When user Navigates to "Activity History" screen from the "Dashboard" screen 
Then user should be dispalyed with All "triggered" event for all the <Solution>
When user selects the "Edit" option
Then user should be dispalyed with enabled "Selet All" button and disabled "Delte" button 
When user selects the and "Message" 
Then user user should be displayed with enabled "Delete" button
And user able to select multiple "Message"
And user should be dispalyed "Tick" mark with selected "message" 
When user selects "Delete" option 
Then user should not be displayed with selected "message"
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|
|WLD| 

#Activity history Edit select all message  with all solutions

#Requirments : single location with jasperNA or JapserEMEA or WLD or C1 or C2 or DAS or all the solucations  and trigged all the events 
@GenralGlobalDrawerWithsolutionActivityHistoryEditselecteallVerification
Scenario Outline: As a user i want to Activity history edit selecte all option under global drawer scenarios  with jasperNA or JapserEMEA or C1 or C2 or DAS or all the solucations  
Given user launches and logs in to the Lyic Application
And user configured the <Solution>
When user Navigates to "Activity History" screen from the "Dashboard" screen 
Then user should be dispalyed with All "triggered" event for all the <Solution>
When user selects the "Edit" option
Then user should be dispalyed with enabled "Selet All" button and disabled "Delte" button 
When user selects the and "Select All" 
Then user should be displayed with enabled "Delete" button
And user should be displayed with all "Message" selected 
And user should be dispalyed "Tick" mark with all selected "message" 
When user selects "Delete" option 
Then user should not be displayed with selected "message"
And user should be displayed with "No Message" text 
Examples:
|Solution|
|JasperNA|
|JasperEMEA|
|C1|
|C2|
|DAS|
|WLD| 




#InviteUsers

#Requirments : single location with and with out any solution 
@GenralGlobalDrawerAddUsersWithandwithoutsolutionVerification
Scenario Outline: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Add Users" screen from the "Dashboard" screen 
Then user should be displayed with "No Invited Users" text with "Add Users" button
When user navigates to "Invite Users" screen from "Add Users" screen
Then user should dispalyed with disabled "Send" button 
When user enters the <Email> 
And user should dispalyed with enabled "Send" button
When user  Selects the "Send" button   
Then user should naivgtes to "Add users" screen from "Invite Users" screen 
And user should displayed with "Invited" <Email>
And user <Email> should receive Email "Email Nitifciation" 
And user should naivgates to "Global Drawer" screen from " Activity History" screen
When user Logged in to the "Invited" <Email>
Then user should able to access with "Invited location"
Examples:
|Email|
|ure@grr.la|
|sys@grr.la|


#Requirments : single location with and with out any solution and user should be invited 
@GenralGlobalDrawerAddUsersWithsameaccountErrorWithandwithoutsolutionVerification
Scenario: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Invite Users" screen from the "Dashboard" screen 
Then user enters the "Email" of logged account 
And user should be displayed with "Error, The requested user was already added to this account" pop up

#Requirments : single location with and with out any solution and user should be invited 
@GenralGlobalDrawerAddUsersWithalreadyinvitedemailErrorWithandwithoutsolutionVerification
Scenario Outline: As a user i want to Verify Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyic Application
And user invited <Email>
When user Navigates to "Invite Users" screen from the "Dashboard" screen 
Then user enters the <Email> 
And user should be displayed with "Error, The requested user was already added to this account" pop up
Examples:
|Email|
|ure@grr.la|


#Requirments : single location with and with out any solution and user should be invited 
@GenralGlobalDrawerRemoveAddUsersWithithandwithoutsolutionVerification
Scenario Outline: As a user i want to Verify Remove Add Users under gloabl drawer with and without solution 
Given user launches and logs in to the Lyic Application
And user should be "Invited" <Email>
When user Navigates to "Add Users" screen from the "Dashboard" screen 
#Android
Then user "TAP on Hold" on "Invited" <Email> '
#iOS
Then user selects "Cancel(x)" button 
And user should be displayed with "Confirm Access Removal" pop up 
When user "Dismiss" the "Confirm Access Removal" pop up
Then user should be displayed with "Invited Users" <Email>
When user "accepts" the "Confirm Access Removal" pop up
Then user should not be dispalyed with "Invited" <Email> 
Examples:
|Email| 
|ure@grr.la|

#HomeAddress

#Requirments : single location with and with out any solution
@GenralGlobalDrawerHomeAddressEditWithoutsavingWithandwithoutsolutionVerification
Scenario: As a user i want to Verify with out saving Edit existing address under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Home Address" screen from the "Dashboard" screen
Then user should be displayed with "Exisitng Address" details 
When user navigates to "Edit Address" screen from "Home Address" screen 
And user should displayed with following field "Exisiting Address" details of "Edit Address" options :
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
|Country Change?| 
And user should displayed with disabled "SAVE" button
When user enter the following "Edit Address" fields: 
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
And user should be dispalyed with enabled "SAVE"  button
When user selectes " BACK" button 
Then user should dispalyed with "Cancel Location Changes?" pop up 
When user "dismiss" the "Cancel Location Changes?" pop up 
Then When user selectes " BACK" button 
Then user should dispalyed with "Cancel Location Changes?" pop up 
When user "Accepts" the "Cancel Location Changes?" pop up 
Then user should navigates to "Home Address" screen
And user "Exisitng Address" should not be updated


#Requirments : single location with and with out any solution
@GenralGlobalDrawerHomeAddressEditWithsavingWithandwithoutsolutionVerification
Scenario: As a user i want to Verify with saving Edit existing address under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Home Address" screen from the "Dashboard" screen
Then user should be displayed with "Exisiting Address" details 
When user navigates to "Edit Address" screen from "Home Address" screen 
And user should be displayed with following field "Exisiting Address" details of "Edit Address" options :
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
|Country Change?| 
And user should displayed with disabled "SAVE" button
When user Edits the following "Edit Address" fields: 
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
And user should be dispalyed with enabled "SAVE"  button
When user selectes "SAVE" button 
Then user should navigates to "Home Address" screen from "Edit Address" screen 
And user should be dispalyed with updated "Exisiting Address" details 


#Requirments : single location with and with out any solution
@GenralGlobalDrawerHomeAddressWithoutEditWithandwithoutsolutionVerification
Scenario: As a user i want to Verify Cancel Location Changes? with out Edit existing address under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Home Address" screen from the "Dashboard" screen
Then user should be displayed with "Exisitng Address" details 
When user navigates to "Edit Address" screen from "Home Address" screen 
And user should displayed with following field "Exisiting Address" details of "Edit Address" options :
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
|Country Change?| 
Then user should navigates to "Home Address" screen
And user should be displayed with "Exisitng Address" should not be updated

#Requirments : single location with and with out any solution
@GenralGlobalDrawerHomeAddressEditwithchangecountryWithandwithoutsolutionVerification
Scenario: As a user i want to Verify Edit existing address with change country under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "Home Address" screen from the "Dashboard" screen
Then user should be displayed with "Exisitng Address" details 
When user navigates to "Edit Address" screen from "Home Address" screen 
And user should be displayed with following field "Exisiting Address" details of "Edit Address" options :
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode| 
|Country Change?| 
Then user should navigates to "Home Address" screen
And user should be displayed with disabled "SAVE" button
When user selects "Change Country?" button 
Then user should navgiates to "Please confirm your country" screen 
When  user selects the <New Country> 
Then user navigates to "Edit Address" screen with following "Edit Address" fields "Empty"
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box stat|
|Edit Box Zipcode|
And user should be dispalyed updated with <New Country> above "Change country" button 
And user should displayed with disabled "SAVE" button
When user Edits the following "Edit Address" fields: 
|Edit Address|
|Location Name|
|Edit Box Location name |
|Address|
|Edit Box Address |
|Edit Box City |
|Edit Box <State>|
|Edit Box <Zipcode>| 
And user should be dispalyed with enabled "SAVE"  button
When user selectes "SAVE" button 
Then user should navigates to "Home Address" screen from "Edit Address" screen 
And user should be dispalyed with updated "Exisiting Address" details 
Examples:
|New Country| State | ZipCode |
|Argentina|
|Australia|
|Austria|
|Belgium|
|Brazil|
|Bulgaria|
|Canada|
|Chile|
|Chine|
|Colombia|



#Requirments : single location with and with out any solution
@GenralGlobalDrawerHomeAddressDeleteLocationWithandwithoutsolutionVerification
Scenario: As a user i want to Verify delete location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "Home Address" screen from the "Dashboard" screen
When user selects "Delete Location" button 
Then user should dispaly with "Delete Location?" pop up 
When user "dismiss" the "Delete Location?" pop up
Then user should be displayed with "HomeAddress" screen 
When user selects "Delete Location" button 
Then user should dispaly with "Delete Location?" pop up 
When user "accepts" the "Delete Location?" pop up
Then user navigates to "ADD New Device" screen 

#Requirments : Two location with and with out any solution
@GenralGlobalDrawerHomeAddressDeleteLocationWithandwithoutsolutionVerification
Scenario: As a user i want to Verify delete location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "Home Address" screen from the "Dashboard" screen of "Location1" 
When user selects "Delete Location" button 
Then user should dispaly with "Delete Location?" pop up 
When user "dismiss" the "Delete Location?" pop up
Then user should be displayed with "HomeAddress" screen 
When user selects "Delete Location" button 
Then user should dispaly with "Delete Location?" pop up 
When user "accepts" the "Delete Location?" pop up
Then user navigates to "Dashboard" screen of  "Location2" 

#Edit Account 


# Edit first name last name
 
 #Requirments : single location with and with out any solution
@GenralGlobaldrawerEditAccountFirstandlastnameupdate
  Scenario: As a user i want to Verify Edit first name and last name in Edit account  under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "Edit Account" screen from "Dashboard" screen 
 And user should be displayed with "Edit box" field with "Exists" details 
 And user should be displayed with Disabled "SAVE" button 
 When the user edits the <first name> and <last name>
 Then user should displayed with enabled "SAVE button
 When user selects "SAVE" button
Then user navigates to "Global Drawer" screen
When user anviagtes to "Edit Account" screen from "Global Drawer" screen 
Then the first name and last name should get updated 
   Example:
      | first name | last name  | 
      | giri	 | 	THEJ | 
      | sami | krishna | 
      | vijay | Govda | 
      | anju | sweets | 

#Edit first name last name with error

#Requirments : single location with and with out any solution
@GenralGlobaldrawerEditAccountFirstandlastnameupdatewitherrror
  Scenario outline: As a user i want to Verify save with error first name and last name in Edit account  under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "Edit Account" screen from "Dashboard" screen 
 And user should be displayed with "Edit box" field with "Exists" details 
 And user should be displayed with Disabled "SAVE" button 
 When the user edits the <first name> and <last name>
 Then user should displayed with enabled "SAVE button
 When user selects "SAVE" button
Then user should be displayed with respective "Error" pop up 
   Example:
      | first name | last name  | 
      | Valid name | Valid name | 
      | Blank name | blank name | 
      | Valid name | Blank name | 
      | Blank name | Valid name | 
	  

	  
#Change Password

#Requirments : single location with and with out any solution
@GenralGlobaldrawerEditAccountChangepassword
  Scenario outline: As a user i want to Verify change password  under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "change password" screen from "Dashboard" screen 
 When user navagites "Change password" screen from "Edit Account" screen 
  Then user should be displayed with disabled "SAVE" button
When user eneters the <Old password> < New password> <Verify New password> edit fields 	  
Then user should be dispalyed with enabled "SAVE" button 
And user should navigates to "Login" screen
And user should "login" with <Verify New password>
Examples:
| Old password     | New password           | Verify new password    | 
| Valid password   | Valid password         | Valid password         | 

#change password with error

#Requirments : single location with and with out any solution
@GenralGlobaldrawerEditAccountChangepasswordwitherror
  Scenario outline: As a user i want to Verify change password  with error under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "change password" screen from "Dashboard" screen 
 When user navagites "Change password" screen from "Edit Account" screen 
  Then user should be displayed with disabled "SAVE" button
When user eneters the <Old password> < New password> <Verify New password> edit fields 	  
Then user should be dispalyed with enabled "SAVE" button 
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

#Requirments : single location with out solution 
@GenralGlobaldrawerEditAccountdeleteaccount
  Scenario : As a user i want to Verify delete account  under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "Edit Account" screen from "Dashboard" screen 
When user selects "Delete Account" button 
Then user should be dispalyed with "We're sorry to see you Go." pop up 
When user selects the "NO" button
Then user should be dispaly with  "Edit Account" screen
When user selects "Delete Account" button 
Then user should be dispalyed with "We're sorry to see you Go." pop up 
When user selects the "YES" button
Then user navigates to "Login" screen

#Delete account with learn more, with solucation 
  
#Requirments : single location with solution 
@GenralGlobaldrawerEditAccountdeleteaccountwith solucation
Scenario : As a user i want to Verify delete account  under  gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
Then navigates to "Edit Account" screen from "Dashboard" screen 
When user selects "Delete Account" button 
Then user should be dispalyed with "We're sorry to see you Go." pop up 
When user selects the "OK" button
Then user should be dispaly with  "Edit Account" screen
When user selects "Delete Account" button 
Then user should be dispalyed with "We're sorry to see you Go." pop up 
When user selects the "Learn more" button
Then user navigates to "HELP" screen 
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
Then user should be dispaly with  "Edit Account" screen

# PIN implementaion pending 


#About the app

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappUSLocationVerification
Scenario: As a user i want to Verify about the app with US Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "About the app" screen from the "Dashboard" screen
Then user should be displayed with following "About the app" options:
|About the app|
|Get Help|
|Rate the app |
|Privacy Policy &EULA|
|Acknowledgements|
|Version|

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerAbouttheappUKLocationVerification
Scenario: As a user i want to Verify about the app with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "About the app" screen from the "Dashboard" screen
Then user should be displayed with following "About the app" options:
|About the app|
|Privacy Policy &EULA|
|Acknowledgements|
|Version|

#GET Help

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappGetHelpUSLocationVerification
Scenario: As a user i want to Verify GetHelp US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "GET HELP" option
Then user should open with "www.yourhome.honeywell.com/support" portal on browser 

#Rate the app only for android  

#Rate the app close option

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappRatetheappClosestarUSLocationVerification
Scenario: As a user i want to Verify Rate the app with close option,  US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Rate the app" option
Then user should be displayed with "What do you think of honeywell home app?" pop up 
When user selects "Close" button
Then user should be displayed with "About the app" option

#Rate the app below 3 stars 

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappRatetheappbelow3starUSLocationVerification
Scenario Outline: As a user i want to Verify Rate the app with below 3 starts,  US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Rate the app" option
Then user should be displayed with "What do you think of honeywell home app?" pop up 
When user selects <star> button
Then user should be displayed with "App Feedback" screen
And user should be dispalyed with the disabled "SEND FEEDBACK" and "Toggle" button
When user enter the "Feedback" text in "Feedback Edit Box"
Then user should be dsipalyed with enabled "SEND FEEDBACK" button
And user enables the "Toggle" button 
When user selects the "SEND FEEDBACK" button
Then user should be dispalyed with "About the app" screen 
Examples:
|Star|
|1|
|2|
|3|


#Rate the app below 3 stars and Feedback screen back button

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappRatetheappFeedbackscreenbackUSLocationVerification
Scenario Outline: As a user i want to Verify Feedback back button,  US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Rate the app" option
Then user should be displayed with "What do you think of honeywell home app?" pop up 
When user selects <star> button
Then user should be displayed with "App Feedback" screen
And user should be dispalyed with the disabled "SEND FEEDBACK" and "Toggle" button
When user enter the "Feedback" text in "Feedback Edit Box"
Then user should be dsipalyed with enabled "SEND FEEDBACK" button
And user enables the "Toggle" button 
When user selects the "Back" button 
Then user should be dispalyed with "About the app" screen  with out any error
Examples:
|Star|
|1|
|2|
|3|

#Rate the app below 4-5 stars with close 

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappRatetheapp4-5starcloseUSLocationVerification
Scenario: As a user i want to Verify Rate the app with 4-5 starts and verify close option,  US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Rate the app" option
Then user should be displayed with "What do you think of honeywell home app?" pop up 
When user selects <star> button
Then user should be displayed with "Thanks for your rating" pop up
When user select the "Close" option 
Then user should be dispalyed with "About the app" screen 
Examples:
|Star|
|4|
|5|

#Rate the app below 4-5 stars with continue 

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappRatetheapp4-5starcontinueUSLocationVerification
Scenario: As a user i want to Verify Rate the app with 4-5 starts and verify continue option,  US Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Rate the app" option
Then user should be displayed with "What do you think of honeywell home app?" pop up 
When user selects <star> button
Then user should be displayed with "Thanks for your rating" pop up
When user select the "Continue" option 
Then user should naviagtes to "Playstore" app 
Examples:
|Star|
|4|
|5|

#Minimize and maximize or logout and login for 3 times Rate my pop app up should be dispaly -- 1-3 star
#Minimize and maximize or logout and login for 3 times Rate my pop app up should be dispaly -- 4-5 star
#Minimize and maximize or logout and login for 3 times Rate my pop app up should be dispaly -- dismiss 
#Minimize and maximize or logout and login for 3 times Rate my pop app up should be dispaly,  dismiss the pop up and Minimize and maximize or logout and login for 15 times Rate my pop up should be dispaly
#User provided rating under global drawer after login, Rate my app pop up should not be display for every 
#Minimize and maximize or logout and login for 3 times Rate my pop app up should be dispaly, dismiss the pop up and provide the feed back from global drawer, Rate my app pop up should not be display for every 
#user login and provided feedback from global drawer , Rate my app pop up should not be display for every 

#Privacy Policy & EULA

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAboutthePrivacyPolicy&EULALocationVerification
Scenario: As a user i want to Verify Privacy policy and EULA under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects the "Privacy Policy & EULA" option
Then user should be display with "Privacy Policy & EULA" screen
And user navigates "About the app" screen from "Privacy Policy & EULA" screen

#App version

#Requirments : single location with and with out any solution , US location 
@GenralGlobalDrawerAbouttheappVersionLocationVerification
Scenario: As a user i want to Verify version Location under About the app with and with out solution 
Given user launches and logs in to the Lyic Application
When user Navigates to "About the app" screen from the "Dashboard" screen
Then user should be displayed with current app "Versioin x.x.x(xx)" 

#FAQs

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerFAQsUKLocationVerification
Scenario: As a user i want to Verify FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects "FAQs" option 
Then user should be navigates to "FAQs" screen 
And user should be displayed with following "FAQs" list:
|FAQs|
|General|
|Thermostat|
|Water leak detector|
|Camera|  

#FAQs Genral 

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerFAQsGenralUKLocationVerification
Scenario: As a user i want to Verify Genral FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects "FAQs" option 
Then user should be navigates to "FAQs" screen 
And user should be displayed with following "FAQs" list:
|FAQs|
|General|
|Thermostat|
|Water leak detector|
|Camera|  
When user selects "General" optoin 
Then user should be displayed with "Genral" screen with respective "Questions"
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
And suer navigates to "Genral" screen from " Questions" screen

#FAQs Thermostat 

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerFAQsThermostatUKLocationVerification
Scenario: As a user i want to Verify Thermostat FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects "FAQs" option 
Then user should be navigates to "FAQs" screen 
And user should be displayed with following "FAQs" list:
|FAQs|
|General|
|Thermostat|
|Water leak detector|
|Camera|  
When user selects "Thermostat" optoin 
Then user should be displayed with "Genral" screen with respective "Questions"
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
And suer navigates to "Genral" screen from " Questions" screen

#FAQs WLD 

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerWaterleakdetectorFAQsUKLocationVerification
Scenario: As a user i want to Verify WLD FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects "FAQs" option 
Then user should be navigates to "FAQs" screen 
And user should be displayed with following "FAQs" list:
|FAQs|
|General|
|Thermostat|
|Water leak detector|
|Camera|  
When user selects "Water leak detector" optoin 
Then user should be displayed with "Genral" screen with respective "Questions"
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
And suer navigates to "Genral" screen from " Questions" screen

#FAQs CAMERA 

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerCameraFAQsUKLocationVerification
Scenario: As a user i want to Verify camera FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "About the app" screen from the "Dashboard" screen
When user selects "FAQs" option 
Then user should be navigates to "FAQs" screen 
And user should be displayed with following "FAQs" list:
|FAQs|
|General|
|Thermostat|
|Water leak detector|
|Camera|  
When user selects "camera" optoin 
Then user should be displayed with "Genral" screen with respective "Questions"
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "YES" button 
Then user should be displayed with "You found this helpful" text  
And user should not displayed with the "Options" 
When user selects any one "Questions"
Then user navigates to respectvie "Questions" screen
When user selects "NO" button 
Then user should be displayed with "You didn't find this helpful" text 
And user should not displayed with the "Options" 
And suer navigates to "Genral" screen from " Questions" screen

#FAQs search box 

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerFAQsThermostatUKLocationVerification
Scenario: As a user i want to Verify Thermostat FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "FAQs" screen from the "Dashboard" screen
When user enters the text in "Search" edit box 
Then user should be display with respective "Questions"

#FAQs search box with no FAQs

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerNoFAQsUKLocationVerification
Scenario: As a user i want to Verify No FAQs with UK Location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "FAQs" screen from the "Dashboard" screen
When user enters the <text> text in "Search" edit box 
Then user should be displayed with "No FAQs Found" text
Examples"
|Text|
|acbd|

#Logout

#Requirments : single location with and with out any solution , UK location 
@GenralGlobalDrawerlogoutwithlocationVerification
Scenario: As a user i want to Verify logout with location under gloabl drawer with and with out solution 
Given user launches and logs in to the Lyic Application
And user Navigates to "Global Drawer" screen from the "Dashboard" screen
When user selects "Logout" options
Then user should be displayed with "Login" Screen
Then user should be displayed with "No FAQs Found" text
Examples"
|Text|
|acbd|
