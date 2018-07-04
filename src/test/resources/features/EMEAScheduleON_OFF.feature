{\rtf1\ansi\ansicpg1252\cocoartf1561\cocoasubrtf200
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww32760\viewh19600\viewkind0
\pard\tx566\tx1133\tx1700\tx2267\tx2834\tx3401\tx3968\tx4535\tx5102\tx5669\tx6236\tx6803\pardirnatural\partightenfactor0

\f0\fs24 \cf0 \
@ScheduleON/OFF\
Feature: \
As an user \
I want to turn schedule OFF or ON \
So that I can run schedule whenever I want to apply set points automatically \
\
\
#JapserNA\
@ScheduleOFFNA \
Scenario Outline:Schedule OFF the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr\
As an user \
I want to turn schedule OFF So that I will be able to turned off schedule whenever I don't want to run schedule   \
Given user launches and login to application\
Then user set to \'93Heat\'94\
And user Stat with <Schedule>\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
And Verify the "Schedule off" status on the solution card \
Examples:\
 |Schedule         |\
|Geofence schedule|\
|Time schedule    |\
\
\
\
\
#JapserNA\
@ScheduleONEMEAtimebase\
Scenario:Schedule ON the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr\
As an user \
I want to turn schedule ON from OFF\
So that schedule will be turned back to follow schedule \
  Given user launches and login to application \
Then user set to \'93Heat\'94\
And user Stat with \'93time base schedule\'94\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the "schedule OFF" overlay in the schedule screen\
When user TAP on the "Schedule OFF" overlay \
Then Verify the "schedule OFF" overlay disappeared in the schedule screen\
And Verify the "Following schedule" status on the solution card \
Examples:\
|Schedule         |\
|Time schedule    |\
\
\
#JapserEMEA\
@ScheduleONEMEAgeofencebase\
Scenario Outline:Schedule ON the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr\
As an user \
I want to turn schedule ON from OFF\
So that schedule will be turned back to follow schedule \
Given user launches and login to application \
Then user set to \'93Heat\'94\
And user Stat with \'93Geofence schedule\'94\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
When user TAP on the "Schedule OFF" overlay \
Then Verify the "USING" <PERIOD> SETTINGS" status on the solution card \
\
Examples:\
|Period|\
|Home|\
|Away|\
|Sleep|\
\
\
\
@ScheduleOFFAdhocOverrideNAtimebase\
Scenario Outline:Schedule OFF the stat  with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr  \
As an user \
I want to turn schedule OFF \
So that I will be able to turned off schedule whenever I don't want to run schedule  \
  Given user launches and login to application \
Then user set to <Mode>\
And user stat with <schedule> \
And user Stat with <AdhocOverride>\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
And Verify the "Schedule off" status on the solution card \
Examples:\
|Mode|Schedule | Ahocoverride    |\
|Heat| Time schedule| Temporary |\
|Heat| Time schedule| Permanent |\
|Heat|Geofence schedule| Temporary |\
|Heat|Geofence schedule| Permanent |\
\
\
@ScheduleONNAadhocoverride\
Scenario Outline:Schedule ON the stat   with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr\
As an user \
I want to turn schedule ON from OFF\
So that schedule will be turned back to follow schedule \
  Given user launches and login to application \
Then user set to <Mode>\
And user Stat with <Schedule>\
And user Stat with <AdhocOverride>\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
When user TAP on the "Schedule OFF" overlay \
Then Verify the <AhocOverride> status on "SolutionCard"\
\
Examples:\
|Mode|Schedule | Ahocoverride    |\
|Heat|Geofence schedule| Temporary |\
|Heat|Geofence schedule| Permanent |\
|Heat| Time schedule| Temporary |\
|Heat| Time schedule| Permanent |\
\
\
\
\
\
\
@ScheduleOFFVacationEMEA\
Scenario Outline:Schedule OFF for stat  with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr \
As an user \
I want to turn schedule OFF while vacation is active \
So that I will be able to turned off schedule whenever I don't want to run schedule    \
  Given user launches and login to application \
Then user set to \'93HEAT\'94\
And user Stat with "Vacation"\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
And Verify the "Schedule off" status on the solution card \
\
\
\
\
@ScheduleONNAVacationEMEA\
Scenario Outline:Schedule ON the stat  with systems Heat for Temperture scale Celsius/Fahrenheit and for time format 24/12hr \
As an user \
I want to turn schedule ON \
So that my vaction will be back   \
  Given user launches and login to application \
Then user set to \'93Heat\'94\
And user Stat with "Vacation"\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
When user TAP on the "Schedule OFF" overlay \
Then user should displayed with disappeared \'93Schedule OFF\'94 overlay on \'93Schedule screen\
And Verify the "Vacation" status on "SolutionCard"\
\
\
\
\
\
\
\
@ScheduleON/OFFEMEAswitchingmodes\
Scenario Outline:Schedule ON/OFF status while switching modes to off and from off for Temperture scale Celsius/Fahrenheit and for time format 24/12hr\
Given user launches and login to application \
Then user set to \'93HEAT\'94\
And user Stat with <AdhocOverride>\
And user should be displayed with <Adhocoverride> status\
When user changes the "OFF" from \'93HEAT\'94\
Then user should be displayed with "SYSTEM IS OFF"  status \
And user should not be displayed with <Adhocoverride> status\
When User "turns schedule off" the schedule from schedule screen\
Then Verify the schedule OFF overlay in the schedule screen\
And Verify the "Schedule off" status on the solution card \
When use changes the \'93HEAT\'94 from "OFF"\
Then user should display with "Shedule OFF"\
When user changes the "OFF" from \'93HEAT\'94\
Then user TAP on the "Schedule OFF" overlay on "Schedule" screen\
When user changes the \'93HEAT\'94from "OFF"\
Then user should be displayed with <Adhocoverride> status on "SolutionCard"\
\
Examples :\
| Adocoverride |\
| Temporary |\
| Permanent | \
 | Vacation |\
}