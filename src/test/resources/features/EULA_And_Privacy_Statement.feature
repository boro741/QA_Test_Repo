@EULAScenarios @Platform
Feature: General global drawer under scenarios 
As a user I want to verify under global drawer scenarios 

#create account                        

@VerifyEULAAndPrivacyStatementScreensAfterSelectingCountryInCreateAccount			@Automated			@--xrayid:ATER-81480
Scenario Outline: As a user wanted to verify privacy statement and End user License agreement screens after selecting country in create account screen
Given user launches the Lyric application
When user selects "Create Account" from "Honeywell Home" screen
Then user should be displayed with the "Create Account" screen
When user selects "Country" from "Create Account" screen
Then user should be displayed with the "Please confirm your country" screen
When user inputs <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "Create Account" screen
When user selects "Privacy Statement" from "Create Account" screen
Then user should be displayed with the "Privacy Statement" screen
When user selects "Back" from "Privacy Statement" screen
Then user should be displayed with the "Create Account" screen
When user selects "END USER License Agreement" from "Create Account" screen
Then user should be displayed with the "END USER License Agreement" screen
When user selects "Back" from "END USER License Agreement" screen
Then user should be displayed with the "Create Account" screen

Examples: 
      | Country						| 
      | Argentina                    |
      | Australia                    | 
      | Austria                      | 
      | Belgium                      | 
      | Brazil                       | 
      | British Virgin Islands       | 
      | Bulgaria                     | 
      | Canada                       | 
      | Chile                        | 
      | China                        | 
      | Colombia                     | 
      | Costa Rica                   | 
      | Czech Republic               | 
      | Denmark                      | 
      | Dominican Republic           | 
      | Egypt                        | 
      | Estonia                      | 
      | Finland                      | 
      | France                       | 
      | Germany                      | 
      | Greece                       | 
      | Hungary                      | 
      | India                        | 
      | Ireland                      | 
      | Italy                        | 
      | Japan                        | 
      | Kenya                        | 
      | Korea, Republic of           | 
      | Latvia                       | 
      | Lithuania                    | 
      | Malaysia                     | 
      | Mexico                       | 
      | Minor Outlying Islands, U.S. | 
      | Morocco                      | 
      | Netherlands                  | 
      | New Zealand                  | 
      | Norway                       | 
      | Panama                       | 
      | Peru                         | 
      | Poland                       | 
      | Portugal                     | 
      | Puerto Rico                  | 
      | Romania                      | 
      | Saudi Arabia                 | 
      | Sinapore                     | 
      | Slovakia                     | 
      | South Africa                 | 
      | South Korea                  | 
      | Spain                        | 
      | Sweden                       | 
      | Switzerland                  | 
      | Tunisia                      | 
      | Turkey                       | 
      | U.S.Outlying Islands         | 
      | U.S.Virgin Islands           | 
      | Ukraine                      | 
      | United Arab Emirates         | 
      | United Kingdom               | 
      | United States                | 
      | Virgin Islands, British      |
     

#AddNewDevice
  
#With out location
@VerifyEULAAndPrivacyStatementScreensAfterSelectingCountryInAddNewDevice			@Automated			@--xrayid:ATER-81701
Scenario Outline: As a user wanted to verify privacy statement and End user License agreement screens after selecting country in Add New Device screen
Given user launches and logs in to the Lyric application with user account without any location
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Cancel" from "New Agreement" screen
Then user should be displayed with the "Please confirm your country" screen
And user should be displayed with "default" country selected in the "Please confirm your country" screen
When user selects "default country" from "Please confirm your country" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Default Country" in the "Add New Device Dashboard" screen
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
When user selects "Close Button" from "Add New Device" screen
Then user should receive a "Exit Honeywell Home" popup
When user "Clicks on Delete Account button in" the "Exit Honeywell Home" popup
Then user should receive a "Sorry to see you go" popup
When user "Clicks on Yes button in" the "Sorry to see you go" popup
Then user should receive a "Your Account and Data is deleted" popup
And user "Accepts" the "Your Account and Data is deleted" popup
Then user should be displayed with the "Honeywell Home" screen
When user logs in to the Lyric Application with "deleted account credentials"
Then user should receive a "Email or Password incorrect" popup
And user "Accepts" the "Email or Password incorrect" popup
Then create the deleted user account through CHIL

Examples: 
      | Country						| 
      | Argentina                    |
      | Australia                    | 
      | Austria                      | 
      | Belgium                      | 
      | Brazil                       | 
      | British Virgin Islands       | 
      | Bulgaria                     | 
      | Canada                       | 
      | Chile                        | 
      | China                        | 
      | Colombia                     | 
      | Costa Rica                   | 
      | Czech Republic               | 
      | Denmark                      | 
      | Dominican Republic           | 
      | Egypt                        | 
      | Estonia                      | 
      | Finland                      | 
      | France                       | 
      | Germany                      | 
      | Greece                       | 
      | Hungary                      | 
      | India                        | 
      | Ireland                      | 
      | Italy                        | 
      | Japan                        | 
      | Kenya                        | 
      | Korea, Republic of           | 
      | Latvia                       | 
      | Lithuania                    | 
      | Malaysia                     | 
      | Mexico                       | 
      | Minor Outlying Islands, U.S. | 
      | Morocco                      | 
      | Netherlands                  | 
      | New Zealand                  | 
      | Norway                       | 
      | Panama                       | 
      | Peru                         | 
      | Poland                       | 
      | Portugal                     | 
      | Puerto Rico                  | 
      | Romania                      | 
      | Saudi Arabia                 | 
      | Sinapore                     | 
      | Slovakia                     | 
      | South Africa                 | 
      | South Korea                  | 
      | Spain                        | 
      | Sweden                       | 
      | Switzerland                  | 
      | Tunisia                      | 
      | Turkey                       | 
      | U.S.Outlying Islands         | 
      | U.S.Virgin Islands           | 
      | Ukraine                      | 
      | United Arab Emirates         | 
      | United Kingdom               | 
      | United States                | 
      | Virgin Islands, British      |

 
#With out location
@VerifyEULAAndPrivacyStatementScreensForEULAAcceptedCountryInAddNewDevice			@Automated			@--xrayid:ATER-81702
Scenario Outline: As a user wanted to verify privacy statement and End user License agreement screens for EULA accepted countries in Add New Device screen
Given user launches and logs in to the Lyric application with user account without any location
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen

      Examples: 
      | Country						 | 
      | Argentina                    |
      | Australia                    | 
      | Austria                      | 
      | Belgium                      | 
      | Brazil                       | 
      | British Virgin Islands       | 
      | Bulgaria                     | 
      | Canada                       | 
      | Chile                        | 
      | China                        | 
      | Colombia                     | 
      | Costa Rica                   | 
      | Czech Republic               | 
      | Denmark                      | 
      | Dominican Republic           | 
      | Egypt                        | 
      | Estonia                      | 
      | Finland                      | 
      | France                       | 
      | Germany                      | 
      | Greece                       | 
      | Hungary                      | 
      | India                        | 
      | Ireland                      | 
      | Italy                        | 
      | Japan                        | 
      | Kenya                        | 
      | Korea, Republic of           | 
      | Latvia                       | 
      | Lithuania                    | 
      | Malaysia                     | 
      | Mexico                       | 
      | Minor Outlying Islands, U.S. | 
      | Morocco                      | 
      | Netherlands                  | 
      | New Zealand                  | 
      | Norway                       | 
      | Panama                       | 
      | Peru                         | 
      | Poland                       | 
      | Portugal                     | 
      | Puerto Rico                  | 
      | Romania                      | 
      | Saudi Arabia                 | 
      | Sinapore                     | 
      | Slovakia                     | 
      | South Africa                 | 
      | South Korea                  | 
      | Spain                        | 
      | Sweden                       | 
      | Switzerland                  | 
      | Tunisia                      | 
      | Turkey                       | 
      | U.S.Outlying Islands         | 
      | U.S.Virgin Islands           | 
      | Ukraine                      | 
      | United Arab Emirates         | 
      | United Kingdom               | 
      | United States                | 
      | Virgin Islands, British      | 
  

#With Location
@VerifyEULAAndPrivacyStatementScreensAfterSelectingCountryInEditLocationScreenWithLocation			@Automated			@--xrayid:ATER-81703
Scenario Outline: As a user wanted to verify privacy statement and End user License agreement screens after selecting country in Edit Location screen
Given user launches and logs in to the Lyric application with user account with location
When user navigates to "Address" screen from the "Dashboard" screen
Then user should be displayed with the "Address" screen
And user should be displayed with "Default Country" in the "Address" screen
When user navigates to "Edit Address" screen from the "Address" screen
Then user should be displayed with "Default Country" in the "Edit Address" screen
When user selects "Change Country" from "Edit Address" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Cancel" from "New Agreement" screen
Then user should be displayed with the "Please confirm your country" screen
And user should be displayed with "current" country selected in the "Please confirm your country" screen
When user selects "default country" from "Please confirm your country" screen
And user should be displayed with the "Edit Address" screen
Then user should be displayed with "Default Country" in the "Edit Address" screen
When user selects "Change Country" from "Edit Address" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
And user should be displayed with "Updated Country in Privacy Policy and EULA link" in the "New Agreement" screen
When user selects "Privacy Policy and EULA link" from "New Agreement" screen
Then user should be displayed with the "Privacy Policy and EULA For Selected Country" screen
When user selects "back" from "Privacy Policy and EULA For Selected Country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
Then user should be displayed with the "Edit Address" screen
And user should be displayed with "Updated Country" in the "Edit Address" screen
When user navigates to "Edit Account" screen from the "Edit Address" screen
When user selects "Delete Account" from "Edit Account" screen
Then user should be displayed with the "Delete Account Without Solution" screen
And user should be displayed with the following "Delete Account" options:
| DeleteAccountOptions				| 
| We are sorry to see you go			|
When user selects "Delete Account button" from "Delete Account" screen
Then user should receive a "Your Account and Data is deleted" popup
And user "Accepts" the "Your Account and Data is deleted" popup
Then user should be displayed with the "Honeywell Home" screen
When user logs in to the Lyric Application with "deleted account credentials"
Then user should receive a "Email or Password incorrect" popup
And user "Accepts" the "Email or Password incorrect" popup
Then create the deleted user account through CHIL
When user logs in to the Lyric Application with "deleted account credentials"
Then user should be displayed with the "Add New Device Dashboard" screen
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters "United States" in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
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

Examples: 
      | Default Location		| Default Device Name		| valid zip code        | Country					  |
      | Home					| Living Room				| 90001                 | Argentina					  | 
      | Home					| Living Room				| 90001                 | Australia                    | 
      | Home					| Living Room				| 90001                 | Austria                      | 
      | Home					| Living Room				| 90001                 | Belgium                      | 
      | Home					| Living Room				| 90001                 | Brazil                       | 
      | Home					| Living Room				| 90001                 | British Virgin Islands       | 
      | Home					| Living Room				| 90001                 | Bulgaria                     | 
      | Home					| Living Room				| 90001                 | Canada                       | 
      | Home					| Living Room				| 90001                 | Chile                        | 
      | Home					| Living Room				| 90001                 | China                        | 
      | Home					| Living Room				| 90001                 | Colombia                     | 
      | Home					| Living Room				| 90001                 | Costa Rica                   | 
      | Home					| Living Room				| 90001                 | Czech Republic               | 
      | Home					| Living Room				| 90001                 | Denmark                      | 
      | Home					| Living Room				| 90001                 | Dominican Republic           | 
      | Home					| Living Room				| 90001                 | Egypt                        | 
      | Home					| Living Room				| 90001                 | Estonia                      | 
      | Home					| Living Room				| 90001                 | Finland                      | 
      | Home					| Living Room				| 90001                 | France                       | 
      | Home					| Living Room				| 90001                 | Germany                      | 
      | Home					| Living Room				| 90001                 | Greece                       | 
      | Home					| Living Room				| 90001                 | Hungary                      | 
      | Home					| Living Room				| 90001                 | India                        | 
      | Home					| Living Room				| 90001                 | Ireland                      | 
      | Home					| Living Room				| 90001                 | Italy                        | 
      | Home					| Living Room				| 90001                 | Japan                        | 
      | Home					| Living Room				| 90001                 | Kenya                        | 
      | Home					| Living Room				| 90001                 | Korea, Republic of           | 
      | Home					| Living Room				| 90001                 | Latvia                       | 
      | Home					| Living Room				| 90001                 | Lithuania                    | 
      | Home					| Living Room				| 90001                 | Malaysia                     | 
      | Home					| Living Room				| 90001                 | Mexico                       | 
      | Home					| Living Room				| 90001                 | Minor Outlying Islands, U.S. | 
      | Home					| Living Room				| 90001                 | Morocco                      | 
      | Home					| Living Room				| 90001                 | Netherlands                  | 
      | Home					| Living Room				| 90001                 | New Zealand                  | 
      | Home					| Living Room				| 90001                 | Norway                       | 
      | Home					| Living Room				| 90001                 | Panama                       | 
      | Home					| Living Room				| 90001                 | Peru                         | 
      | Home					| Living Room				| 90001                 | Poland                       | 
      | Home					| Living Room				| 90001                 | Portugal                     | 
      | Home					| Living Room				| 90001                 | Puerto Rico                  | 
      | Home					| Living Room				| 90001                 | Romania                      | 
      | Home					| Living Room				| 90001                 | Saudi Arabia                 | 
      | Home					| Living Room				| 90001                 | Sinapore                     | 
      | Home					| Living Room				| 90001                 | Slovakia                     | 
      | Home					| Living Room				| 90001                 | South Africa                 | 
      | Home					| Living Room				| 90001                 | South Korea                  | 
      | Home					| Living Room				| 90001                 | Spain                        | 
      | Home					| Living Room				| 90001                 | Sweden                       | 
      | Home					| Living Room				| 90001                 | Switzerland                  | 
      | Home					| Living Room				| 90001                 | Tunisia                      | 
      | Home					| Living Room				| 90001                 | Turkey                       | 
      | Home					| Living Room				| 90001                 | U.S.Outlying Islands         | 
      | Home					| Living Room				| 90001                 | U.S.Virgin Islands           | 
      | Home					| Living Room				| 90001                 | Ukraine                      | 
      | Home					| Living Room				| 90001                 | United Arab Emirates         | 
      | Home					| Living Room				| 90001                 | United Kingdom               | 
#      | Home					| Living Room				| 90001                 | United States                | 
      | Home					| Living Room				| 90001                 | Virgin Islands, British      |
			
  
#With location
@VerifyEULAAndPrivacyStatementScreensForEULAAcceptedCountryInEditLocation          @Automated			@--xrayid:ATER-81704
Scenario Outline: As a user wanted to verify privacy statement and End user License agreement screens for EULA accepted countries in Edit Location screen
Given user launches and logs in to the Lyric application with user account with location
When user navigates to "Address" screen from the "Dashboard" screen
Then user should be displayed with the "Address" screen
And user should be displayed with "Default Country" in the "Address" screen
When user navigates to "Edit Address" screen from the "Address" screen
Then user should be displayed with "Default Country" in the "Edit Address" screen
When user selects "Change Country" from "Edit Address" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "Edit Address" screen
And user should be displayed with "Updated Country" in the "Edit Address" screen

Examples: 
      | Country                      | 
      | Argentina                    | 
      | Australia                    | 
      | Austria                      | 
      | Belgium                      | 
      | Brazil                       | 
      | British Virgin Islands       | 
      | Bulgaria                     | 
      | Canada                       | 
      | Chile                        | 
      | China                        | 
      | Colombia                     | 
      | Costa Rica                   | 
      | Czech Republic               | 
      | Denmark                      | 
      | Dominican Republic           | 
      | Egypt                        | 
      | Estonia                      | 
      | Finland                      | 
      | France                       | 
      | Germany                      | 
      | Greece                       | 
      | Hungary                      | 
      | India                        | 
      | Ireland                      | 
      | Italy                        | 
      | Japan                        | 
      | Kenya                        | 
      | Korea, Republic of           | 
      | Latvia                       | 
      | Lithuania                    | 
      | Malaysia                     | 
      | Mexico                       | 
      | Minor Outlying Islands, U.S. | 
      | Morocco                      | 
      | Netherlands                  | 
      | New Zealand                  | 
      | Norway                       | 
      | Panama                       | 
      | Peru                         | 
      | Poland                       | 
      | Portugal                     | 
      | Puerto Rico                  | 
      | Romania                      | 
      | Saudi Arabia                 | 
      | Sinapore                     | 
      | Slovakia                     | 
      | South Africa                 | 
      | South Korea                  | 
      | Spain                        | 
      | Sweden                       | 
      | Switzerland                  | 
      | Tunisia                      | 
      | Turkey                       | 
      | U.S.Outlying Islands         | 
      | U.S.Virgin Islands           | 
      | Ukraine                      | 
      | United Arab Emirates         | 
      | United Kingdom               | 
      | United States                | 
      | Virgin Islands, British      |
      
  
#Last accepted EULA

@VerifyEULAAndPrivacyStatementScreensAfterSelectingCountryInAddNewDeviceAndLogOutAndLogInToTheApp			@Automated			@--xrayid:ATER-81705
Scenario Outline: Verify if privacy statement and End user License agreement screens are displayed after selecting country in Add New Device screen and log out and login to the app
Given user launches and logs in to the Lyric application with user account without any location
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Cancel" from "New Agreement" screen
Then user should be displayed with the "Please confirm your country" screen
And user should be displayed with "default" country selected in the "Please confirm your country" screen
When user selects "default country" from "Please confirm your country" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Default Country" in the "Add New Device Dashboard" screen
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
And user should be displayed with "Updated Country in Privacy Policy and EULA link" in the "New Agreement" screen
When user selects "Privacy Policy and EULA link" from "New Agreement" screen
Then user should be displayed with the "Privacy Policy and EULA For Selected Country" screen
When user selects "back" from "Privacy Policy and EULA For Selected Country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
When user selects "Close Button" from "Add New Device" screen
Then user should receive a "Exit Honeywell Home" popup
When user "Clicks on Sign Out button in" the "Exit Honeywell Home" popup
Then user should be displayed with the "Honeywell Home" Screen
And user should be able to login to the app with user account without any location after sign out
Then user should be displayed with the "Add New Device Dashboard" screen
And user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
When user selects "Close Button" from "Add New Device" screen
Then user should receive a "Exit Honeywell Home" popup
When user "Clicks on Delete Account button in" the "Exit Honeywell Home" popup
Then user should receive a "Sorry to see you go" popup
When user "Clicks on Yes button in" the "Sorry to see you go" popup
Then user should receive a "Your Account and Data is deleted" popup
And user "Accepts" the "Your Account and Data is deleted" popup
Then user should be displayed with the "Honeywell Home" screen
When user logs in to the Lyric Application with "deleted account credentials"
Then user should receive a "Email or Password incorrect" popup
And user "Accepts" the "Email or Password incorrect" popup
Then create the deleted user account through CHIL

Examples: 
      | Country                      | 
      | Argentina                    | 
      | Australia                    | 
      | Austria                      | 
      | Belgium                      | 
      | Brazil                       | 
      | British Virgin Islands       | 
      | Bulgaria                     | 
      | Canada                       | 
      | Chile                        | 
      | China                        | 
      | Colombia                     | 
      | Costa Rica                   | 
      | Czech Republic               | 
      | Denmark                      | 
      | Dominican Republic           | 
      | Egypt                        | 
      | Estonia                      | 
      | Finland                      | 
      | France                       | 
      | Germany                      | 
      | Greece                       | 
      | Hungary                      | 
      | India                        | 
      | Ireland                      | 
      | Italy                        | 
      | Japan                        | 
      | Kenya                        | 
      | Korea, Republic of           | 
      | Latvia                       | 
      | Lithuania                    | 
      | Malaysia                     | 
      | Mexico                       | 
      | Minor Outlying Islands, U.S. | 
      | Morocco                      | 
      | Netherlands                  | 
      | New Zealand                  | 
      | Norway                       | 
      | Panama                       | 
      | Peru                         | 
      | Poland                       | 
      | Portugal                     | 
      | Puerto Rico                  | 
      | Romania                      | 
      | Saudi Arabia                 | 
      | Sinapore                     | 
      | Slovakia                     | 
      | South Africa                 | 
      | South Korea                  | 
      | Spain                        | 
      | Sweden                       | 
      | Switzerland                  | 
      | Tunisia                      | 
      | Turkey                       | 
      | U.S.Outlying Islands         | 
      | U.S.Virgin Islands           | 
      | Ukraine                      | 
      | United Arab Emirates         | 
      | United Kingdom               | 
      | United States                | 
      | Virgin Islands, British      |

 
#With location

@VerifyCountryDisplayedInAddNewDeviceScreenAfterUpdatingInEditLocationScreen          @Automated			@--xrayid:ATER-81706
Scenario Outline: Verify if country selected in Edit Location screen is displayed in Add New Device screen
Given user launches and logs in to the Lyric application with user account with location
When user navigates to "Address" screen from the "Dashboard" screen
Then user should be displayed with the "Address" screen
And user should be displayed with "Default Country" in the "Address" screen
When user navigates to "Edit Address" screen from the "Address" screen
Then user should be displayed with "Default Country" in the "Edit Address" screen
When user selects "Change Country" from "Edit Address" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Cancel" from "New Agreement" screen
Then user should be displayed with the "Please confirm your country" screen
And user should be displayed with "current" country selected in the "Please confirm your country" screen
When user selects "default country" from "Please confirm your country" screen
And user should be displayed with the "Edit Address" screen
Then user should be displayed with "Default Country" in the "Edit Address" screen
When user selects "Change Country" from "Edit Address" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters <Country> in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
And user should be displayed with "Updated Country in Privacy Policy and EULA link" in the "New Agreement" screen
When user selects "Privacy Policy and EULA link" from "New Agreement" screen
Then user should be displayed with the "Privacy Policy and EULA For Selected Country" screen
When user selects "back" from "Privacy Policy and EULA For Selected Country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
Then user should be displayed with the "Edit Address" screen
And user should be displayed with "Updated Country" in the "Edit Address" screen
When user navigates to "Dashboard" screen from the "Edit Address" screen
And user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
When user navigates to "Dashboard" screen from the "Add New Device Dashboard" screen
When user navigates to "Edit Account" screen from the "Dashboard" screen
When user selects "Delete Account" from "Edit Account" screen
Then user should be displayed with the "Delete Account Without Solution" screen
And user should be displayed with the following "Delete Account" options:
| DeleteAccountOptions				| 
| We are sorry to see you go			|
When user selects "Delete Account button" from "Delete Account" screen
Then user should receive a "Your Account and Data is deleted" popup
And user "Accepts" the "Your Account and Data is deleted" popup
Then user should be displayed with the "Honeywell Home" screen
When user logs in to the Lyric Application with "deleted account credentials"
Then user should receive a "Email or Password incorrect" popup
And user "Accepts" the "Email or Password incorrect" popup
Then create the deleted user account through CHIL
When user logs in to the Lyric Application with "deleted account credentials"
Then user should be displayed with the "Add New Device Dashboard" screen
When user selects "Change Country" from "Add New Device Dashboard" screen
Then user should be displayed with the "Please confirm your country" screen
When user enters "United States" in "Search Text Field" in the "Please confirm your country" screen
Then user should be displayed with the "New Agreement" screen
When user selects "Accept" from "New Agreement" screen
And user should be displayed with the "Add New Device Dashboard" screen
Then user should be displayed with "Updated Country" in the "Add New Device Dashboard" screen
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

Examples: 
	  | Default Location		| Default Device Name		| valid zip code        | Country					  |
      | Home					| Living Room				| 90001                 | Argentina					  | 
      | Home					| Living Room				| 90001                 | Australia                    | 
      | Home					| Living Room				| 90001                 | Austria                      | 
      | Home					| Living Room				| 90001                 | Belgium                      | 
      | Home					| Living Room				| 90001                 | Brazil                       | 
      | Home					| Living Room				| 90001                 | British Virgin Islands       | 
      | Home					| Living Room				| 90001                 | Bulgaria                     | 
      | Home					| Living Room				| 90001                 | Canada                       | 
      | Home					| Living Room				| 90001                 | Chile                        | 
      | Home					| Living Room				| 90001                 | China                        | 
      | Home					| Living Room				| 90001                 | Colombia                     | 
      | Home					| Living Room				| 90001                 | Costa Rica                   | 
      | Home					| Living Room				| 90001                 | Czech Republic               | 
      | Home					| Living Room				| 90001                 | Denmark                      | 
      | Home					| Living Room				| 90001                 | Dominican Republic           | 
      | Home					| Living Room				| 90001                 | Egypt                        | 
      | Home					| Living Room				| 90001                 | Estonia                      | 
      | Home					| Living Room				| 90001                 | Finland                      | 
      | Home					| Living Room				| 90001                 | France                       | 
      | Home					| Living Room				| 90001                 | Germany                      | 
      | Home					| Living Room				| 90001                 | Greece                       | 
      | Home					| Living Room				| 90001                 | Hungary                      | 
      | Home					| Living Room				| 90001                 | India                        | 
      | Home					| Living Room				| 90001                 | Ireland                      | 
      | Home					| Living Room				| 90001                 | Italy                        | 
      | Home					| Living Room				| 90001                 | Japan                        | 
      | Home					| Living Room				| 90001                 | Kenya                        | 
      | Home					| Living Room				| 90001                 | Korea, Republic of           | 
      | Home					| Living Room				| 90001                 | Latvia                       | 
      | Home					| Living Room				| 90001                 | Lithuania                    | 
      | Home					| Living Room				| 90001                 | Malaysia                     | 
      | Home					| Living Room				| 90001                 | Mexico                       | 
      | Home					| Living Room				| 90001                 | Minor Outlying Islands, U.S. | 
      | Home					| Living Room				| 90001                 | Morocco                      | 
      | Home					| Living Room				| 90001                 | Netherlands                  | 
      | Home					| Living Room				| 90001                 | New Zealand                  | 
      | Home					| Living Room				| 90001                 | Norway                       | 
      | Home					| Living Room				| 90001                 | Panama                       | 
      | Home					| Living Room				| 90001                 | Peru                         | 
      | Home					| Living Room				| 90001                 | Poland                       | 
      | Home					| Living Room				| 90001                 | Portugal                     | 
      | Home					| Living Room				| 90001                 | Puerto Rico                  | 
      | Home					| Living Room				| 90001                 | Romania                      | 
      | Home					| Living Room				| 90001                 | Saudi Arabia                 | 
      | Home					| Living Room				| 90001                 | Sinapore                     | 
      | Home					| Living Room				| 90001                 | Slovakia                     | 
      | Home					| Living Room				| 90001                 | South Africa                 | 
      | Home					| Living Room				| 90001                 | South Korea                  | 
      | Home					| Living Room				| 90001                 | Spain                        | 
      | Home					| Living Room				| 90001                 | Sweden                       | 
      | Home					| Living Room				| 90001                 | Switzerland                  | 
      | Home					| Living Room				| 90001                 | Tunisia                      | 
      | Home					| Living Room				| 90001                 | Turkey                       | 
      | Home					| Living Room				| 90001                 | U.S.Outlying Islands         | 
      | Home					| Living Room				| 90001                 | U.S.Virgin Islands           | 
      | Home					| Living Room				| 90001                 | Ukraine                      | 
      | Home					| Living Room				| 90001                 | United Arab Emirates         | 
      | Home					| Living Room				| 90001                 | United Kingdom               | 
#      | Home					| Living Room				| 90001                 | United States                | 
      | Home					| Living Room				| 90001                 | Virgin Islands, British      |
 
  
  #EULAUpdate scenarios
  
  #With or with out location 
  @UpdateacceptedEULAcancelfunctionality          @NotAutomatable
  Scenario: As a user wanted to verify accepted update EULA cancel functionality and privacy policy & EULA for the updated EULA countries    
    Given user updated the accepted EULA from CHIL
     When user launches and login to the application 
     Then user should be displayed with "New Agreement" header screen
      And user should be displayed with "Welcome back!" Description
     Then user selects "Privacy Policy & EULA:" with <Countries>
      And user should be displayed with respective "Privacy Policy & EULA" screen
     When user select "Done" option
     Then user should be displayed with "New Agreement" header screen
     When user selects "Cancel" option 
     Then user should be displayed with "Are you sure you want to cancel and logout?" Pop up 
     When user "dismiss" the pop up 
     Then user should be displayed with "New Agreement" header screen
     When user "Accept" the pop up 
     Then user should be navigates to "Login" Screen
  
  #with location
  @UpdateacceptedEULAAcceptfunctionalityWithLocation          @NotAutomatable
  Scenario: As a user wanted to verify accepted update EULA accept functionality and privacy policy & EULA for the updated EULA countries    
    Given user updated the accepted EULA from CHIL
     When user launches and login to the application 
     Then user should be displayed with "New Agreement" header screen
      And user should be displayed with "Welcome back!" Description
     Then user selects "Privacy Policy & EULA:" with <Countries>
      And user should be displayed with respective "Privacy Policy & EULA" screen
     When user select "Done" option
     Then user should be displayed with "New Agreement" header screen
     When user selects "Accept" option 
     Then user should be navigates to "Dashboard" screen
  
  #with out location          
  @UpdateacceptedEULAAcceptfunctionalityWithOutAnyLocation		@NotAutomatable
  Scenario: As a user wanted to verify accepted update EULA accept functionality and privacy policy & EULA for the updated EULA countries    
    Given user updated the accepted EULA from CHIL
     When user launches and login to the application 
     Then user should be displayed with "New Agreement" header screen
      And user should be displayed with "Welcome back!" Description
     Then user selects "Privacy Policy & EULA:" with <Countries>
      And user should be displayed with respective "Privacy Policy & EULA" screen
     When user select "Done" option
     Then user should be displayed with "New Agreement" header screen
     When user selects "Accept" option 
     Then user should be navigates to "Add New Device" screen
