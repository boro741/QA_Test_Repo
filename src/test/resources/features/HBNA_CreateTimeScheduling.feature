@NATimeBasedScheduling
Feature: Jasper,Sprouce and Flycatcher Scheduling
	As a user I want to create an time based scheduling


@JasperNA_CreateNAScheduleSinglestatwithDefaultvalue @Automated
Scenario Outline: As a user I want to create an Time based schedule with default schedule value for systems 
Given user thermostat is set to <scheduling> schedule  
And user launches and logs in to the Lyric application
And user navigates to "SCHEDULING" screen from the "Dashboard" screen
When user creates <ScheduleType> schedule with default schedule value
Then <ScheduleType> scheduling gets activated
#And user navigates to "Primary card" screen from the "Scheduling" screen
#Then user is displayed with "Following scheduling"

    Examples: 
      | scheduling| ScheduleType |
#      | no        | Same Every Day|
      | time based        | Same Every Day|
      | no| Different On Weekdays|
#      | time based| Different On Weekdays|
      
@JasperNA_CancelToRetainExisitngscheduling @Automated
Scenario Outline: As a user i want to be prompted with an option to Cancel overriding Geofence Schedule
Given user thermostat is set to "geofence based" schedule
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user selects "Cancel" option while creating <ScheduleType> schedule with default schedule value
Then "Geofence" scheduling is retained

Examples: 
| ScheduleType |
| Same Every Day|
| Different On Weekdays|

@JasperNA_ConfirmToCreateNewSchedule @Automated
Scenario Outline: As a user i want to be prompted with an option to Confirm and Create new Schedule
Given user thermostat is set to "geofence based" schedule
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen 
When user selects "Confirm" option while creating <ScheduleType> schedule with default schedule value
Then <ScheduleType> scheduling gets activated

Examples: 
| ScheduleType |
| Same Every Day|
| Different On Weekdays|

@JasperNA_TempretureBandwidthforEachPeriod @Automated
Scenario Outline: As a user i want to verify Tempreture bandwidth limit for each period
Above Maximum: Above 90, Below Minimum : below 50,At Maximum : max 90, At Minimum : min 50 ,within range : between 50-90
Given user thermostat is set to "time based" schedule
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates <ScheduleType> schedule by setting temperature value to <Temperature>
Then user displayed temperature within the maximum and minimum range

Examples: 
|ScheduleType       | Temperature   | 
|Same Every Day     | Above Maximum |
#|Same Every Day     | Below Minimum | 
#|Same Every Day     | At Maximum    | 
#|Same Every Day     | At Minimum    | 
#|Same Every Day     | within range  |
|Different On Weekdays     | Above Maximum | 
#|Different On Weekdays     | Below Minimum | 
#|Different On Weekdays     | At Maximum    | 
#|Different On Weekdays     | At Minimum    | 
#|Different On Weekdays     | within range  | 


@JasperNA_TimerClockIsInCrementalOf10mins
Scenario: As a user i want to verify if Timer clock in Each period is incremental of 10mins
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Same Every Day" schedule by changing the time values
Then user should be displayed "Same Every Day" schedule with timer field incremental of "10 minutes"
  

@JasperNA_DeletingDefaultPeriodSameEveryDay @Automated
Scenario Outline: As a user I want to delete period in Same Every Day Time based schedule
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Same Every Day" schedule by deleting <Periods> from the default schedule values 	
Then <Period Value> scheduling gets activated

Examples: 
| Periods |Period Value|
| Atleast 1 period|Same Every Day|
#| Atleast 2 period|Same Every Day|
#| Atleast 3 period|Same Every Day|
#| All periods     |No|

@JasperNA_DeletingDefaultPeriodDifferentOnWeekdays @Automated
Scenario Outline: As a user i want to delete periods in Different On Weekdays schedule so that only those periods are deleted
Given user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Different On Weekdays" schedule by deleting <Periods> from the default schedule values
Then <Period Value> scheduling gets activated

Examples: 
| Periods |Period Value|
| Atleast 1 period|Different On Weekdays|
#| Atleast 2 period|Different On Weekdays|
#| Atleast 3 period|Different On Weekdays|
#| All periods     |No|

@JapserNA_CopyScheduleToMulitpleStat
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

@JapserNA_CopyScheduleWhenStatOffline
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

@JasperNA_CreateTimeBasedScheduleInOffMode @Automated
Scenario Outline: As a user I want to create an Time based schedule with default schedule value when System is in Off Mode 
Given user thermostat is set to "geofence based" schedule
And  user has "Off" system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates <ScheduleType> schedule with default schedule value
Then  <ScheduleType> scheduling gets activated
#And user navigates to "Primary card" screen from the "Scheduling" screen
#Then user is displayed with "System is Off" on the screen

    Examples: 
      | scheduling| ScheduleType |
      | no        | Same Every Day|
#      | time based        | Same Every Day|
#      | Geofence |Same Every Day|
#      | no| Different On Weekdays|
      | time based| Different On Weekdays|
#      |Geofence|Different On Weekdays|
      
@JasperNA_WhenHeat/CoolOnly
Scenario Outline: As a user I want to create an Time based schedule with default schedule value when System is in Off Mode 
Given  user thermostat is  configures with "Modes"system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Time Based" schedule with default schedule value
Then  "Time Based" scheduling gets activated
And user is displayed with <Modes> in Schedule

Examples: 
|Modes|
|Heat Only|
|Cool Only|

@NA_CreateGeofenceScheduleInOffMode
Scenario: As a user I want to create an Geofence schedule with default schedule value when System is in Off Mode 
Given user thermostat is set to "Geofence" schedule
And  user has "Off" system mode
And user launches and logs in to the Lyric application
And user navigates to "Scheduling" screen from the "Dashboard" screen
When user creates "Time Based" schedule with default schedule value
Then <scheduling> scheduling gets activated on Tapping Confirm
And user navigates to "Primary card" screen from the "Scheduling" screen
Then user is displayed with "System is Off" on the screen