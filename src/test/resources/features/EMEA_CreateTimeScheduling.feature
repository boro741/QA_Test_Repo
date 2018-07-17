@EMEATimeBasedScheduling
Feature: Jasper,Sprouce and Flycatcher Scheduling
As a user I want to create an time based scheduling

@JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue @JasperEmeaScheduleP1 @--xrayid:ATER-44515
  Scenario Outline: As a user I want to create an Time based schedule with default schedule value for systems 
    Given user thermostat is set to <scheduling> schedule  
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates <ScheduleType> schedule with default schedule value
     Then  <ScheduleType> scheduling gets activated
     Then user verifies the following on the primary card:
      | Elements                               | 
     |Following schedule|

    Examples: 
      | scheduling | ScheduleType          | 
      | no         | Same Every Day        | 
 #     | time based | Same Every Day        | 
 #     | no         | Different On Weekdays | 
 #     | time based | Different On Weekdays | 
  
  @JasperEMEA_CancelToRetainExisitngscheduling @JasperEmeaScheduleP3
  Scenario Outline: As a user i want to be prompted with an option to Cancel overriding Geofence Schedule
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user selects "Cancel" option while creating <ScheduleType> schedule with default schedule value
     Then "Geofence" scheduling is retained
  
    Examples: 
      | ScheduleType          | 
      | Same Every Day        | 
      | Different On Weekdays | 
  
  @JasperEMEA_ConfirmToCreateNewSchedule @JasperEmeaScheduleP2
  Scenario Outline: As a user i want to be prompted with an option to Confirm and Create new Schedule
    Given user thermostat is set to "geofence based" schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen 
     When user selects "Confirm" option while creating <ScheduleType> schedule with default schedule value
     Then <ScheduleType> scheduling gets activated
  
    Examples: 
      | ScheduleType          | 
      | Same Every Day        | 
      | Different On Weekdays | 
  
  @JasperEMEA_TempretureBandwidthforEachPeriod @JasperEmeaScheduleP3
  Scenario Outline: As a user i want to verify Tempreture bandwidth limit for each period
  Above Maximum: Above 90, Below Minimum : below 50,At Maximum : max 90, At Minimum : min 50 ,within range : between 50-90
    Given user thermostat is set to <ScheduleType> schedule
      And user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates <ScheduleType> schedule by setting temperature value to <Temperature>
     Then user should be displayed with <ScheduleType> with <Temperature> Range
  
    Examples: 
      | ScheduleType          | Temperature   | 
      | Same Every Day        | Above Maximum | 
      | Same Every Day        | Below Minimum | 
      | Same Every Day        | At Maximum    | 
      | Same Every Day        | At Minimum    | 
      | Same Every Day        | within range  | 
      | Different On Weekdays | Above Maximum | 
      | Different On Weekdays | Below Minimum | 
      | Different On Weekdays | At Maximum    | 
      | Different On Weekdays | At Minimum    | 
      | Different On Weekdays | within range  | 
  
  @JasperEMEA_TimerClockIsInCrementalOf15mins @JasperEmeaScheduleP2
  Scenario: As a user i want to verify if Timer clock in Each period is incremental of 15mins
    Given user launches and logs in to the Lyric application
      And user navigates to "Scheduling" screen from the "Dashboard" screen
     When user creates "Same Every Day" schedule by changing the time values
     Then user should be displayed "Same Every Day" schedule with timer field incremental of "10 minutes"
  
  @JasperEMEA_CreateEMEAEverydayscheduleAddingperiod @JasperEmeaScheduleP1
  Scenario Outline: To create EMEA schedule by setting up with new period with new time value for both time format
  As an user
  I want to create Everyday schedule by adding new period for both time format
    Given user launches and logs in to the Lyric application
      And user selects "Jasper device" from the dashboard
     When user creates <ScheduleType> schedule by adding New period to the default schedule values
     Then <ScheduleType> scheduling gets activated
  
    Examples: 
      | ScheduleType          | 
      | Same Every Day        | 
      | Different On Weekdays | 
      
@JasperEMEA_DeletingDefaultPeriodDifferentOnWeekdays @JasperEmeaScheduleP4
Scenario Outline: As a user i want to delete periods in Different On Weekdays schedule so that only those periods are deleted
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
And user creates "Different On Weekdays" schedule with default schedule value
When user deletes <Periods> from the Schdeule
Then Periods are Rearranged in Chronology order 

Examples: 
| Periods |
| Atleast 1 period|
| Atleast 2 period|

@JasperEMEA_CanCreateMaximumOfSixPeriods @JasperEmeaScheduleP4
  Scenario Outline: To create EMEA schedule with Maximum of 6 periods
  As an user
  I want to create Everyday schedule by adding new period for both time format
    Given user launches and logs in to the Lyric application
      And user selects "Jasper device" from the dashboard
     When user creates <ScheduleType> schedule by adding <Periods> to the default schedule values
     Then <ScheduleType> scheduling gets activated
     And User is displayed with <Periods>
     
Examples: 
| Periods |
|  5th period|
|  6th period|

@JasperEMEA_CreateTimeBasedScheduleInOffMode @JasperEmeaScheduleP3
Scenario Outline: As a user I want to create an Time based schedule with default schedule value when System is in Off Mode 
Given user thermostat is set to "Geofence" schedule
And  user has "Off" system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates <ScheduleType> schedule with default schedule value
Then  <ScheduleType> scheduling gets activated
And user navigates to "Primary card" screen from the "Scheduling" screen
Then user is displayed with "System is Off" on the screen

    Examples: 
      | scheduling| ScheduleType |
      | no        | Same Every Day|
      | time based        | Same Every Day|
      | no| Different On Weekdays|
      | time based| Different On Weekdays|

@JasperEMEA_CreateTimeBasedScheduleInOffMode @JasperEmeaScheduleP4
# Given Account has a Location with Multiple Stats and Offline stats
Scenario Outline: As a user i want to verify that offline Stats are not displayed in the Copystat pop ups
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "CopyStatst" stats while creating <ScheduleType> schedule with default schedule value
Then Offline Stat should't be displayed
Examples: 
|ScheduleType       |  
|Same Every Day     | 
|Same Every Day     | 
|Same Every Day     | 
|Different On Weekdays     |
|Different On Weekdays     |
|Different On Weekdays     |

@JasperEMEA_CopyScheduleToMulitpleStat @JasperEmeaScheduleP2
# Given Account has a Location with Multiple Stats
Scenario Outline: As a user i want to copy my New schedule to other stats as well
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "CopyStatst" stats while creating <ScheduleType> schedule with default schedule value
Then <ScheduleType> scheduling gets activated in <CopyStatst> stats

Examples: 
|ScheduleType       |  CopyStatst |
|Same Every Day     | None|
|Same Every Day     | All|
|Same Every Day     | Selected|
|Different On Weekdays     |None |
|Different On Weekdays     |All |
|Different On Weekdays     |Selected|