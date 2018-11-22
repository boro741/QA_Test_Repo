@GenralAddNewDevice @Platform
Feature: Add New Device scenarios when user selects respective countries  
As a user I want to verify Respective solution displayed on add device screen

#Requirement : Account with out location
@GeneralListOfSolutionsInAddNewDevicesScreenForSupportedCountries             @Automatable		@--xrayid:ATER-69152
  Scenario Outline: As a user i want to verify list of solutions displayed in Add Devices screen for supported countries
    Given user launches and logs in to the Lyric application
    When user navigates to "Add New Device Dashboard" screen from the "Dashboard" screen
    Then user selects "Change Country" from "Add New Device Dashboard" screen
    Then user should be displayed with "Confirm your country" screen
     When user navigates to "Please confirm your country" screen from "Add New Device" screen
     Then user selects <Country> from "Please confirm your country" screen
      And user should be displayed "SELECT A DEVICE TO INSTALL" text
      And user should be displayed "Smart Home Security " text
      And user should be displayed <DAS> solution
      And user should be displayed <HB> solution
      And user should be displayed <D6> solution
      And user should be displayed <T5> solution
      And user should be displayed <T6 Pro> solution
      And user should be displayed <WLD> solution
      And user should be displayed <C1> solution
      And user should be displayed <C2> solution
      And user should be displayed <T6> solution
      And user should be displayed <T6R> solution
      And user should be displayed "Showing devices for" with <Country>
    Examples: 
      | Country                      | DAS   | HB   | D6   | T5   | T6 Pro   | WLD   | C1   | C2   | T6   | T6R   | 
      | Argentina                    | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Australia                    | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Austria                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Belgium                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Brazil                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | British Virgin Islands       | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | N C1 | N C2 | Y T6 | Y T6R | 
      | Bulgaria                     | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Canada                       | Y DAS | Y HB | Y D6 | Y T5 | Y T6 Pro | Y WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Chile                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | China                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Colombia                     | N DAS | Y HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Costa Rica                   | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Czech Republic               | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Denmark                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Dominican Republic           | N DAS | Y HB | Y D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Egypt                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Estonia                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Finland                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | France                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Germany                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Greece                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Hungary                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | India                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Ireland                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Italy                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Japan                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Kenya                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Korea, Republic of           | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Latvia                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Lithuania                    | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Malaysia                     | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Mexico                       | N DAS | Y HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Minor Outlying Islands, U.S. | N DAS | Y HB | N D6 | Y T5 | Y T6 Pro | N WLD | N C1 | N C2 | N T6 | N T6R | 
      | Morocco                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Netherlands                  | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | New Zealand                  | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Norway                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Panama                       | N DAS | Y HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Peru                         | N DAS | Y HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Poland                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Portugal                     | Y DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Puerto Rico                  | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | N C1 | N C2 | Y T6 | Y T6R | 
      | Romania                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Saudi Arabia                 | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Sinapore                     | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Slovakia                     | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | South Africa                 | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | South Korea                  | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Spain                        | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Sweden                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Switzerland                  | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | Tunisia                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Turkey                       | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | U.S.Outlying Islands         | N DAS | N HB | N D6 | Y T5 | Y T6 Pro | N WLD | N C1 | N C2 | N T6 | N T6R | 
      | U.S.Virgin Islands           | Y DAS | N HB | N D6 | Y T5 | Y T6 Pro | N WLD | N C1 | N C2 | N T6 | N T6R | 
      | Ukraine                      | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | United Arab Emirates         | N DAS | N HB | N D6 | N T5 | N T6 Pro | N WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | United Kingdom               | N DAS | N HB | N D6 | N T5 | N T6 Pro | Y WLD | Y C1 | Y C2 | Y T6 | Y T6R | 
      | United States                | Y DAS | Y HB | Y D6 | Y T5 | Y T6 Pro | Y WLD | Y C1 | Y C2 | N T6 | N T6R | 
      | Virgin Islands, British      | N DAS | Y HB | N D6 | Y T5 | Y T6 Pro | N WLD | N C1 | N C2 | Y T6 | Y T6R | 
  
