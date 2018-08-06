@VacationSettings @Comfort
Feature: As an user I want to set the vacation period for my home so that my thermostat will follow vacation setting on my absence from my home during the vacation days  

@Vacations_VerifyStartAndEndDate			@UIAutomatable
 Scenario Outline: Verify Start and End Date
 Given vacation mode is <settings>
 And user launches and logs in to the Lyric application
 When user navigates to "Vacation" screen from the "Dashboard" screen
 Then user is displayed with start date and end date options based on the <settings> vacation
 
 Examples: 
      | settings | 
      | active   | 
      | InActive | 
  
@Vacations_VerifyGuideMessage			@UIAutomatable
Scenario Outline: Verify Guide Message
Given vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "off" vacation from <card name>
Then user gets cautioned with guide message about vacation turned off for the location
When user "cancels" the guide message
Then user verifies vacation is "on" in <card name>
And user navigates to "Solution" screen from the "Dashboard" screen
When user turns "off" vacation from <card name>
Then user gets cautioned with guide message about vacation turned off for the location
When user "ends" the guide message
Then user verifies vacation is "off" in <card name>
  
Examples: 
      | card name              | 
      | vacation settings card | 
      | solution card          | 
  
  
@Vacations_DefaultVacationTimeForNA			@UIAutomatable
Scenario: As a user I want to verify the date inputs
Given vacation mode is "InActive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then user is displayed with "From" date as "Current Time" nearest to 15
And user is displayed with "To" date as "Week from Current Time" nearest to 15

  
@Vacations_VerifyTimeAndDateBoundaryConditionsForEMEA			@UIAutomatable 
Scenario: As a user I want to verify the time and date boundary conditions
Given vacation mode is "InActive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then user is displayed with "From" date as "Current Time" nearest to 10
And user is displayed with "To" date as "Week from Current Time" nearest to 10

  
@Vacations_VerifyVacationDefaultSetPoints			@UIAutomatable
Scenario: As a user I want to set the vacation set value so that I can put my home with desired temperature on my vacation  
Given vacation mode is "InActive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then user is displayed with default set point value
And user is displayed temperature values within maximum minimum limit

  
@Vacations_VerifyHBBStatsNotPresentOnComfortSettings			@UIAutomatable
Scenario: As a user I want to verify that HBB stats are not present in vacation comfort settings 
Given vacation mode is "InActive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then user provided with option to enter vacation start and end date
And user with HBB is not listed under the review vacation settings in the location

  
@Vacations_MinimumBandwidthTimer			@UIAutomatable
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
When acation mode is "Active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then Minimum bandwidth timer between from and to is "1" hour
  
  
@Vacation_TimerValueIncreamentOf15EMEA			@UIAutomatable
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
When acation mode is "Active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then user should be displayed from and to timer field incremental of "15" minutes


@Vacation_TimerValueIncreamentOf10NA			@UIAutomatable
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
When acation mode is "Active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then user should be displayed from and to timer field incremental of "10" minutes
  
  
@Vacation_EditSetPoints			@UIAutomatable
Scenario: As a user I want to edit set points for individual stat
When acation mode is "Active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user selects the "stat" to edit
Then user should allowed to edit set points

 
@Vacation_Enable/DisbaleIndividulaStat			@UIAutomatable
Scenario Outline: As a user I want to enable/Disable stat vacation individually
#Given: User has multiple stats
When acation mode is "Active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user selects the "stat" to edit
Then user can <Condition> the stat individually
And Vacation mode is <Vacation Status> for the Stat

Examples: 
      | Condition | Vacation Status | 
      | Enable    | Active          | 
      | Disable   | InActive        | 
  
@Vacation_WhenScheduleEnables			@UIAutomatable
Scenario Outline: As a user I want to activate an Vacation settings when Scheduling is active 
Given user thermostat is set to <Schedule Type> schedule
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then Vacation mode is "Active" for the Stat
  
Examples: 
      | Schedule Type | 
      | Time Based    | 
      | Geofence      | 
      | No            | 
  

@Vacation_EditSetPointsFromPrimaryCard			@UIAutomatable
Scenario Outline: As a user I want to edit set points for individual stat
When acation mode is "Active"
And user launches and logs in to the Lyric application
When user edits set point from <Card>
Then user should be displayed with Updated setpoint in <Postcondition>

Examples: 
      | Card				| Postcondition	|
      | Primary card		| Vacation card	|
      | Vacation card	| Primary card	|


#JasperNA
@VacationActiveSwitchingModesNA			@UIAutomatable
Scenario Outline:  To verify when vacation active switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
When user Activates the "Vacation"
Then user should be displayed with  status on "SolutionCard" 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display "Vacation" status on "SolutionCard
When suer change the <UMode> from "OFF" 
Then user should be displayed with displayed with  status on "SolutionCard" 
And user should be displayed with "Vacation" setpoint value 

Examples:
		| Mode	| Umode		| 
		| Auto	| Heat		| 
		| Auto	| Cool		|
		| Auto	| Auto		|
		| Heat	| Cool		|
		| Heat	| Auto		| 
		| Heat	| Heat		|
		| Cool	| Heat		|
		| Cool	| Auto		|
		| Cool	| Cool		|


#JasperEMEA
@VacationActiveSwitchingModesEMEA			@UIAutomatable
Scenario:  To verify geofence schedule switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user launches and logs in to the Lyric application
Then user is set to "Heat" through CHIL
When user Activates the "Vacation"
Then user should be displayed with  status on "SolutionCard" 
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF"  status 
And user should not be display "Vacation" status on "SolutionCard
When suer change the "Heat" from "OFF" 
Then user should be displayed with displayed with  status on "SolutionCard" 
And user should be displayed with "Vacation" setpoint value 


#JasperNA
@VacationTimebaseSolutionCardAfterVacationEndsNA			@NotAutomatable
Scenario Outline:   I want to verify AdhocOVerride status when vacation ends
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Time base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with <AdhocOverride> on "SolutionCard"
When "Vacation" Ends 
Then user should be display with <UAdhocOverride>  on "Solution card"

Examples:
		| Mode				| AdhocOverride				| UAdhocOverride				| 
		| Cool				| Following Schedule			| Following schedule			|
		| Cool				| Temporary					| Temporary					|
		| Cool				| Permanent					| Permanent					| 
		| Cool				| Specific Time				| specific time				| 
		| Heat				| Following Schedule			| Following schedule			| 
		| Heat				| Temporary					| Temporary					|
		| Heat				| Permanent					| Permanent					|
		| Heat				| Specific Time				| Specific time				|
		| Auto				| Following Schedule			| Following Schedule			|
		| Auto				| Temporary					| Temporary					|
		| Auto				| Permanent					| Permanent					|
		| Auto				| Specific Time				| Specific time				|
		| Cool only			| Following Schedule			| Following schedule			|
		| Cool only			| Temporary					| Temporary					|
		| Cool only			| Permanent					| Permanent					|
		| Cool only			| Specific Time				| Specific time				|
		| Heat Only			| Following schedule			| Following schedule			|
		| Heat only			| Temporary					| Temporary					|
		| Heat only			| Permanent					| Permanent					|
		| Heat only			| Specific Time				| Specific Time				|
		#Vacation end in text period (After temporary ends )
		| Cool				| Temporary					| Following Schedule			|
		| Heat				| Temporary					| Following Schedule			|
		| Auto				| Temporary					| Following Schedule			|
		| Cool Only			| Temporary					| Following Schedule			|
		| Heat only			| Temporary					| Following Schedule			|
		| Cool				| Specific Time				| Following schedule			| 
		| Heat				| Specific Time				| Following schedule			| 
		| Auto				| Specific Time				| Following schedule			| 
		| Cool Only			| Specific Time				| Following schedule			| 
		| Heat only			| Specific Time				| Following schedule			| 
		#Vacation ends after permanent hold ends 
		| Cool				| Permanent					| Following schedule			|
		| Heat				| Permanent					| Following schedule			|
		| Auto				| Permanent					| Following schedule			|
		| Cool Only			| Permanent					| Following schedule			|
		| Heat only			| Permanent					| Following schedule			|


#JasperEMEA
@VacationTimebaseSolutionCardAfterVacationEndsEMEA			@NotAutomatable
Scenario Outline:   I want to verify AdhocOVerride status when vacation ends
Given user launches and logs in to the Lyric application
Then user is set to "Heat" through CHIL
And user is set to "Time base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with <AdhocOverride> on "SolutionCard"
When "Vacation" Ends 
Then user should be display with <UAdhocOverride> on "SolutionCard"

Examples:
		| AdhocOverride			| UAdhocOverride				| 
		| Following Schedule 	| Following schedule			|
		| Temporary 				| Temporary					|
		| Permanent				| Permanent					| 
		| Specific Time			| specific time				| 
		#Vacation ends in next period (After temporary ends)/
		| Temporary				| Following Schedule			|
		#Vacation ends after permanent hold ends 
		| Permanent				| Following schedule			|


#JasperNA
@VacationGeofenceSolutionCardAfterVacationEndsNA			@UIAutomatable
Scenario Outline:   I want to verify AdhocOVerride status when vacation ends
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Geofence base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with <AdhocOverride> on "SolutionCard"
When "Vacation" Ends 
Then user should be display with <UAdhocOverride> on "SolutionCard"

Examples:
		| Mode		| AdhocOverride					| UAdhocOverride						| 
		| Cool		| Using while home/away/sleep	| Using while home/away/sleep  		|
		| Cool		| Temporary 						| Temporary							|
		| Heat		| Using while home/away/sleep	| Using while home/away/sleep		| 
		| Heat		| Temporary						| Temporary							|
		| Auto		| Using while home/away/sleep	| Using while home/away/sleep		|
		| Auto		| Temporary						| Temporary							|
		| Cool only	| Using while home/away/sleep	| Using while home/away/sleep		|
		| Cool only	| Temporary						| Temporary							|
		| Heat Only	| Using while home/away/sleep	| Using while home/away/sleep		|
		| Heat only | Temporary						| Temporary							|


#JasperEMEA
@VacationGeofenceSolutionCardAfterVacationEndsEMEA			@UIAutomatable
Scenario Outline:   I want to verify AdhocOVerride status when vacation ends
Given user launches and logs in to the Lyric application
Then user is set to <Mode> through CHIL
And user is set to "Geofence base schedule"
When user navigates to "Solutioncard" screen from "Dashboard" screen
Then user should be displayed with <AdhocOverride> on "SolutionCard"
When "Vacation" Ends 
Then user should be display with <UAdhocOverride> on "SolutionCard"

Examples:
		| AdhocOverride					| UAdhocOverride					| 
		| Using while home/away/sleep	| Using while home/away/sleep	| 
		| Temporary						| Temporary						|
		#Vacation ends after temporary ends 
		| Temporary						| Using while home/away/sleep	|

  
@Vacations_VactionStatusOnDashabord			@UIAutomatable
Scenario: Verify if vacation is active when user is in Dashboard
Given vacation mode is Active
When user launches and logs in to the Lyric application
Then user should be displayed "Vacation Active" on Dashboard header