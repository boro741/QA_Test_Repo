@VacationSettings @Comfort
Feature: As an user I want to set the vacation period for my home so that my thermostat will follow vacation setting on my absence from my home during the vacation days  

@Vacations_VerifyStartAndEndDate			@Automated
 Scenario Outline: Verify Start and End Date
 Given vacation mode is <settings>
 And user launches and logs in to the Lyric application
 When user navigates to "Vacation" screen from the "Dashboard" screen
 Then user is displayed with start date and end date options based on the <settings> vacation
 
 Examples: 
      | settings		| 
      | active		| 
 #     | inactive		| 
  
@Vacations_VerifyGuideMessage			@Automated
Scenario Outline: Verify guide Message when vacation is either turned off or on
Given vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to <Vacation Option> screen from the "Dashboard" screen
When user changes the <Option> to "Off"
#Then user gets cautioned with guide message about vacation turned off for the location
Then user should receive a "End Vacation Mode Confirmation" popup
#When user "cancels" the guide message
When user "dismisses" the "End Vacation Mode Confirmation" popup
Then user verifies vacation is "on" in <Vacation Option>
When user changes the <Option> to "Off"
#Then user gets cautioned with guide message about vacation turned off for the location
Then user should receive a "End Vacation Mode Confirmation" popup
#When user "cancels" the guide message
When user "accepts" the "End Vacation Mode Confirmation" popup
Then user verifies vacation is "off" in <Vacation Option>

Examples:
		| Vacation Option		| Option				|
		| Vacation				| Vacation			|
	#	| solution card			| Vacation Until		|
		

@Vacations_DefaultVacationTimeForNA			@Automated
Scenario: As a user I want to verify the date inputs
Given vacation mode is "inactive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user changes the "Vacation" to "On"
#When user turns "on" vacation from "vacation settings card"
Then user is displayed with "From" date as "Current Time" nearest to "15"
And user is displayed with "To" date as "Week from Current Time" nearest to "15"

  
@Vacations_VerifyTimeAndDateBoundaryConditionsForEMEA			@Automated
Scenario: As a user I want to verify the time and date boundary conditions
Given vacation mode is "inactive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user turns "on" vacation from "vacation settings card"
Then user is displayed with "From" date as "Current Time" nearest to "10"
And user is displayed with "To" date as "Week from Current Time" nearest to "10"

  
@Vacations_VerifyVacationDefaultSetPoints			@Automated
Scenario: As a user I want to set the vacation set value so that I can put my home with desired temperature on my vacation  
Given vacation mode is "inactive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user changes the "Vacation" to "On"
And user navigates back and forth in "Vacation" screen
Then user should be displayed with default set point value
And user should be displayed with temperature values within maximum minimum limit


@Vacations_VerifyHBBStatsNotPresentOnComfortSettings			@Automated
Scenario: As a user I want to verify that HBB stats are not present in vacation comfort settings 
When vacation mode is "inactive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user changes the "Vacation" to "On"
And user navigates back and forth in "Vacation" screen
Then user should be provided with option to enter vacation start and end date
#And user with HBB is not listed under the review vacation settings in the location
And HBB device should not be listed under the review vacation settings section in Vacation screen

  
@Vacations_MinimumBandwidthTimer			@Automated
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
Given vacation mode is "inactive"
When vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then Minimum bandwidth timer between from and to is "1" hour
  

@Vacation_TimerValueIncreamentOf10EMEA			@Automated
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
Given vacation mode is "inactive"
When vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then user is displayed with "From" date as "Current Time" nearest to "10"
#Then user should be displayed from and to timer field incremental of "10" minutes


@Vacation_TimerValueIncreamentOf15NA				@Automated
Scenario: As a user I want to verify the minimum Bandwidth Limit for vacation from and To 
Given vacation mode is "inactive"
When vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user edits Vacation Timer 
Then user is displayed with "From" date as "Current Time" nearest to "15"
#Then user should be displayed from and to timer field incremental of "15" minutes
  
  
@Vacation_EditSetPoints			@Automated
Scenario: As a user I want to edit set points for individual stat in Stats screen
When vacation mode is "active"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user selects the stat to edit
Then user should be able to edit set points in Stats screen

@Vacation_EnableDisableIndividualStat			@Automated
Scenario Outline: As a user I want to enable or disable stat vacation individually
#Given: User has multiple stats
When vacation mode is "inactive"
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user selects the stat to edit
And user changes the "Vacation Hold Switch In Stat Screen" to <Condition>
When user navigates to "Vacation" screen from the "Stats" screen
Then user is displayed with stat status <Stats Value In Vacation> in the vacation screen

Examples: 
      | Condition	| Stats Value In Vacation	| 
      | On			| active					| 
   #   | Off			| No Settings				| 
      
@Vacation_Enable_DisableMultiStat			@Automated
Scenario Outline: As a user I want to enable disable multi stat vacation
Given vacation mode is "inactive for multistat"
And user launches and logs in to the Lyric application
And user navigates to "MULTISTAT LOCATION" screen from the "Dashboard" screen
And user navigates to "Vacation" screen from the "Dashboard" screen
When user changes the "Vacation" to "On"
Then user <Condition> the "stat2" individually
And verify Vacation mode is <Vacation Status> for the "stat2"
And verify Vacation mode is "Active" for the "stat1"

Examples: 
      | Condition | Vacation Status	| 
  #    | Enable    | Active			| 
      | Disable   | Inactive			|

@Vacation_WhenScheduleEnables			@Automated
Scenario Outline: As a user I want to activate an Vacation settings when Scheduling is active 
Given user thermostat is set to <Schedule Type> schedule
And user launches and logs in to the Lyric application
And user navigates to "Vacation" screen from the "Dashboard" screen
When user changes the "Vacation" to "On"
Then Vacation mode is "active" for the Stat
  
Examples: 
      | Schedule Type 		| 
      | Time Based    		| 
   #   | geofence based		| 
  #    | No					| 
  

@Vacation_EditSetPointsFromPrimaryCard			@Automated
Scenario Outline: As a user I want to edit set points for individual stat from primary card screen
When vacation mode is "active"
And user launches and logs in to the Lyric application
When user edits set point from <Card>
Then user should be displayed with Updated setpoint in <Postcondition>

Examples: 
		| Card				| Postcondition	|
		| Primary card		| Vacation card	|
		| Vacation card		| Primary card	|


#JasperNA
@VacationActiveSwitchingModesNA			@Automated
Scenario Outline:  To verify when vacation active switching modes is changed for "Heat , auto ,cool and off" system with auto changeover enabled
Given user has <Mode> system mode
And vacation mode is "active"
When user launches and logs in to the Lyric application
Then user navigates to "solution card" screen from the "Dashboard" screen
Then user verifies vacation is "on" in "solution card"
When user change the "OFF" from <Mode>
Then user should be displayed with "SYSTEM IS OFF" status on "solution card"
And user verifies vacation is "off" in "solution card"
When user change the <UMode> from "OFF" 
Then user verifies vacation is "on" in "solution card"
And user should be displayed with "Vacation" setpoint value in the solution card screen

Examples:
		| Mode	| UMode		| 
#		| Auto	| Heat		| 
#		| Auto	| Cool		|
#		| Heat	| Cool		|
#		| Heat	| Auto		| 
		| Cool	| Heat		|
#		| Cool	| Auto		|


#JasperEMEA
@VacationActiveSwitchingModesEMEA			@Automated
Scenario:  To verify geofence schedule switching modes is changed for Heat Cool and Off system with auto changeover enabled
Given user has "Heat" system mode
And vacation mode is "active"
When user launches and logs in to the Lyric application
Then user navigates to "solution card" screen from the "Dashboard" screen
Then user verifies vacation is "on" in "solution card"
When user changes system mode to "Cool" 
Then user verifies vacation is "on" in "solution card"
When user changes system mode to "Off"
And user verifies vacation is "off" in "solution card"


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
#		| Cool				| Temporary					| Temporary					|
#		| Cool				| Permanent					| Permanent					| 
#		| Cool				| Specific Time				| specific time				| 
#		| Heat				| Following Schedule			| Following schedule			| 
#		| Heat				| Temporary					| Temporary					|
#		| Heat				| Permanent					| Permanent					|
#		| Heat				| Specific Time				| Specific time				|
#		| Auto				| Following Schedule			| Following Schedule			|
#		| Auto				| Temporary					| Temporary					|
#		| Auto				| Permanent					| Permanent					|
#		| Auto				| Specific Time				| Specific time				|
#		| Cool only			| Following Schedule			| Following schedule			|
#		| Cool only			| Temporary					| Temporary					|
#		| Cool only			| Permanent					| Permanent					|
#		| Cool only			| Specific Time				| Specific time				|
#		| Heat Only			| Following schedule			| Following schedule			|
#		| Heat only			| Temporary					| Temporary					|
#		| Heat only			| Permanent					| Permanent					|
#		| Heat only			| Specific Time				| Specific Time				|
		#Vacation end in text period (After temporary ends )
#		| Cool				| Temporary					| Following Schedule			|
#		| Heat				| Temporary					| Following Schedule			|
#		| Auto				| Temporary					| Following Schedule			|
#		| Cool Only			| Temporary					| Following Schedule			|
#		| Heat only			| Temporary					| Following Schedule			|
#		| Cool				| Specific Time				| Following schedule			| 
#		| Heat				| Specific Time				| Following schedule			| 
#		| Auto				| Specific Time				| Following schedule			| 
#		| Cool Only			| Specific Time				| Following schedule			| 
#		| Heat only			| Specific Time				| Following schedule			| 
		#Vacation ends after permanent hold ends 
#		| Cool				| Permanent					| Following schedule			|
#		| Heat				| Permanent					| Following schedule			|
#		| Auto				| Permanent					| Following schedule			|
#		| Cool Only			| Permanent					| Following schedule			|
#		| Heat only			| Permanent					| Following schedule			|

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
#		| Specific Time			| specific time				| 
		#Vacation ends in next period (After temporary ends)/
#		| Temporary				| Following Schedule			|
		#Vacation ends after permanent hold ends 
#		| Permanent				| Following schedule			|


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
		| Mode		| AdhocOverride					| UAdhocOverride					| 
		| Cool		| Using while home/away/sleep	| Using while home/away/sleep  		|
#		| Cool		| Temporary 					| Temporary							|
#		| Heat		| Using while home/away/sleep	| Using while home/away/sleep		| 
#		| Heat		| Temporary						| Temporary							|
#		| Auto		| Using while home/away/sleep	| Using while home/away/sleep		|
#		| Auto		| Temporary						| Temporary							|
#		| Cool only	| Using while home/away/sleep	| Using while home/away/sleep		|
#		| Cool only	| Temporary						| Temporary							|
#		| Heat Only	| Using while home/away/sleep	| Using while home/away/sleep		|
#		| Heat only | Temporary						| Temporary							|


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

  
@Vacations_VactionStatusOnDashabord			@Automated
Scenario: Verify if vacation is active when user is in Dashboard
Given vacation mode is "active"
When user launches and logs in to the Lyric application
#Then user should be displayed "Vacation Active" on Dashboard header
Then user verifies vacation is "on" in "dashboard"