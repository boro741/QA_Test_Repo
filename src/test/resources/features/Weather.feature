@Weatherforecast
Feature:Weather forecast, As user I want to view the outdoor temperature from weather forecast so that I will aware of my outdoor temperature

#Dashboard weather 

#Requirements: Single location with or with out solution 
@GenralWeatherforecastwithorwithoutsolutionDashboard          @Automatable
Scenario : As a user I want to verify the weather forecast for the location with temper scale celsius/Fahrenheit on dashboard with or with out solution 
Given user launches and logs in to the Lyric Application
Then user navigates to “Dashboard” screen 
And Verify the “Weather icon and Tempr value” based on the location zipcode  #respective temper scale 


#Weather screen 

#Requirements: Single location with or with out solution 
@GenralWeatherforecastwithorwithoutsolutionWeatherScreen          @NotAutomatable
Scenario : As a user I wanted to verify weather forecast screen for the location with temper scale celsius/Fahrenheit on Weather screen with or with out solution  and for time format 24/12hr
Given user launches and logs in to the Lyric Application
Then user navigates to “Forecast” screen 
And verify the current weather forecast for the location with temperature and weather status 
And Verify for three more forecast with interval of 6 hrs from current time 
And Verify the time format in app is same as mobile device

#Weather screen switching temper scale celsius and Fahrenheit 

#Requirements: Single location with or with out solution US location          @Automatable
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUSLocation
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, US Location
Given user launches and logs in to the Lyric Application
Then user navigates to “Forecast” screen 
And verify the temper scale selected on “Fahrenheit”
When user switches to “Celsius” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Celsius”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “Celsius” 
When user switches to “Fahrenheit” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Fahrenheit”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “Fahrenheit” 

#Weather screen switching temper scale celsius and Fahrenheit, US location

#Requirements: Single location with or with out solution US location          @Automatable
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUSLocation
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, US Location
Given user launches and logs in to the Lyric Application
Then user navigates to “Forecast” screen 
And verify the temper scale selected as “Fahrenheit”
When user switches to “Celsius” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Celsius”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “Celsius” 
When user switches to “Fahrenheit” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Fahrenheit”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “Fahrenheit” 


#Weather screen switching temper scale celsius and Fahrenheit, EMEA Location 

#Requirements: Single location with or with out solution UK location          @Automatable
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUKLocation
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, UK Location
Given user launches and logs in to the Lyric Application
Then user navigates to “Forecast” screen 
And verify the temper scale selected as “celsius”
When user switches to “Fahrenheit” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Fahrenheit”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “Fahrenheit” 
When user switches to “celsius” on bottom of the screen 
Then verify the current weather forecast for the location with temperature displayed with the “Fahrenheit”
And Verify for three more forecast with interval of 6 hrs from current time
And verify for three more forecast with “celsius”     

#Dashboard weather switching temper  switching scale celsius and Fahrenheit, EMEA Location 

#Requirements: Single location with or with out solution           @Automatable
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitch
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on Dashboard with or with out solution
Given user launches and logs in to the Lyric Application
And user set to temper scale “Celsius” 
And verify the temper scale “Celsius” for weather
Then user navigates to “Forecast” screen 
When user switches to “Fahrenheit” on bottom of the screen 
Then user navigates to “Dashboard” screen from “Forecast” screen
And verify the temper scale “Fahrenheit” for weather

                             
@GenralWeatherforecast          @NotAutomatable
Scenario :Fetch Weather forecast for systems with mobile time format 24/12hr
Given user launches and logs in to the Lyric Application
Then user navigates to “Forecast” screen from “Dashboard” screen 
And Verify the current weather forecast for the location with temperature and weather status
And Verify for three more forecast with interval of 6 hrs from current time
And Verify the time format in app is same as mobile device



@ErrormessageWeatherforecast          @NotAutomatable
Scenario Outline:To get error messages on system unavailability to fetch weather forecast As an user I want to get error message if app failed to fetch weather forecast for the location  So that I will get notified on system unavailability
Given With the <Condition>
When click the weather forecast   
Then Verify the error message
Examples:
|Condition                       |
|Mobile internet(3G/wifi) is down|
|Mobile in Airplane mode         |
|Mobile with low signal(3G/wifi) |
|CHIL down                       |
|Switching between the network   |
|Smart network switch            |

#Requirment : Multiple location with different zipcode
@GenralchangelocationWeatherupdate          @Automatable
Scenario: As a user i wanted to verify the weather update with lcoation change 
Given user launches and logs in to the Lyric application
When user see the wheather status of location1
Then user changes the location2
And user should be displayed updated weather based on the location2 zipcode
When user changes the location1
Then user should be displayed with weather based on the location1 zipcode

#Requirment: Single location with or without solution
@Genralweatherchangebasedonlocationzipcodeudpate          @Automatable
Given user launches and logs in to the Lyric application
When user see the weather status of locatoin
Then user navigates to "Location settings" screen 
When user update the location 
Then user navigates to dashboard
And user should be displayed with udpated weather based on the location zipcode

#Requirment : Mulitple location with different Tempr scale
@Genralweathertempscalevalidationmultilocation          @Automatable
Given user launches and logs in to the Lyric application
When user navigates to weather screen
Then user changes to tempr scale to C
When user navigates to location2
Then user navigates to weather screen
When user changes the tempr scale to F
Then user navigates to location1
And user should be displayed with C
When user navigates to location2
Then user should be displayed with F
