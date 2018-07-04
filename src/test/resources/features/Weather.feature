{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf200
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww33400\viewh20520\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 @Weatherforecast\
Feature:Weather forecast, As user I want to view the outdoor temperature from weather forecast so that I will aware of my outdoor temperature\
\
#Dashboard weather \
\
#Requirements: Single location with or with out solution \
@GenralWeatherforecastwithorwithoutsolutionDashboard\
Scenario : As a user I want to verify the weather forecast for the location with temper scale celsius/Fahrenheit on dashboard with or with out solution \
Given user launches and logs in to the Lyric Application\
Then user navigates to \'93Dashboard\'94 screen \
And Verify the \'93Weather icon and Tempr value\'94 based on the location zipcode  #respective temper scale \
\
\
#Weather screen \
\
#Requirements: Single location with or with out solution \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 @GenralWeatherforecastwithorwithoutsolutionWeatherScreen\
Scenario : As a user I wanted to verify weather forecast screen for the location with temper scale celsius/Fahrenheit on Weather screen with or with out solution  and for time format 24/12hr\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 Given user launches and logs in to the Lyric Application\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 Then user navigates to \'93Forecast\'94 screen \
And verify the current weather forecast for the location with temperature and weather status \
And Verify for three more forecast with interval of 6 hrs from current time \
And Verify the time format in app is same as mobile device\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 \
#Weather screen switching temper scale celsius and Fahrenheit \
\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 #Requirements: Single location with or with out solution US location\
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUSLocation\
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, US Location\
Given user launches and logs in to the Lyric Application\
Then user navigates to \'93Forecast\'94 screen \
And verify the temper scale selected on \'93Fahrenheit\'94\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 When user switches to \'93Celsius\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Celsius\'94\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93Celsius\'94 \
When user switches to \'93Fahrenheit\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Fahrenheit\'94\
And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93Fahrenheit\'94 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 #Weather screen switching temper scale celsius and Fahrenheit, US location\
\
#Requirements: Single location with or with out solution US location\
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUSLocation\
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, US Location\
Given user launches and logs in to the Lyric Application\
Then user navigates to \'93Forecast\'94 screen \
And verify the temper scale selected as \'93Fahrenheit\'94\
When user switches to \'93Celsius\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Celsius\'94\
And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93Celsius\'94 \
When user switches to \'93Fahrenheit\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Fahrenheit\'94\
And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93Fahrenheit\'94 \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 \
\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 #Weather screen switching temper scale celsius and Fahrenheit, EMEA Location \
\
#Requirements: Single location with or with out solution UK location\
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitchwithUKLocation\
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on weather screen with or with out solution  and for time format 24/12hr, UK Location\
Given user launches and logs in to the Lyric Application\
Then user navigates to \'93Forecast\'94 screen \
And verify the temper scale selected as \'93celsius\'94\
When user switches to \'93Fahrenheit\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Fahrenheit\'94\
And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93Fahrenheit\'94 \
When user switches to \'93celsius\'94 on bottom of the screen \
Then verify the current weather forecast for the location with temperature displayed with the \'93Fahrenheit\'94\
And Verify for three more forecast with interval of 6 hrs from current time\
And verify for three more forecast with \'93celsius\'94     \
\
#Dashboard weather switching temper  switching scale celsius and Fahrenheit, EMEA Location \
\
#Requirements: Single location with or with out solution \
@GenralWeatherforecastwithorwithoutsolutionWeathertemperscaleswitch\
Scenario : As a user I wanted to verify weather forecast screen temper scale switch with celsius/Fahrenheit on Dashboard with or with out solution\
Given user launches and logs in to the Lyric Application\
And user set to temper scale \'93Celsius\'94 \
And verify the temper scale \'93Celsius\'94 for weather\
Then user navigates to \'93Forecast\'94 screen \
When user switches to \'93Fahrenheit\'94 on bottom of the screen \
Then user navigates to \'93Dashboard\'94 screen from \'93Forecast\'94 screen\
And verify the temper scale \'93Fahrenheit\'94 for weather\
\
                             \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 @GenralWeatherforecast\
Scenario :Fetch Weather forecast for systems with mobile time format 24/12hr\
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 Given user launches and logs in to the Lyric Application\
Then user navigates to \'93Forecast\'94 screen from \'93Dashboard\'94 screen \
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0
\cf0 And Verify the current weather forecast for the location with temperature and weather status\
And Verify for three more forecast with interval of 6 hrs from current time\
And Verify the time format in app is same as mobile device\
\
\
\
@ErrormessageWeatherforecast\
Scenario Outline:To get error messages on system unavailability to fetch weather forecast As an user I want to get error message if app failed to fetch weather forecast for the location  So that I will get notified on system unavailability\
Given With the <Condition>\
When click the weather forecast   \
Then Verify the error message\
Examples:\
|Condition                       |\
|Mobile internet(3G/wifi) is down|\
|Mobile in Airplane mode         |\
|Mobile with low signal(3G/wifi) |\
|CHIL down                       |\
|Switching between the network   |\
|Smart network switch            |}