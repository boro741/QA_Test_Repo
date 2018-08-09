@DutyCycleWithOptOutableTrue @comfort 
Feature: Duty Cycle with Opt Out able true 
	As a user, I want to opt out of DR when my duty cycle is enabled

@VerifySavingEventScheduleMessagenew @UIAutomatable 
Scenario: Verify Saving Event Schedule Message 
	As a user, I should receive a saving event schedule message on the primary card, Activity History screen
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "duty cycle" and "is" opt-out able for "10" minutes and "2" minutes from now 
	When user launches and logs in to the Lyric application 
	And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	Then "saving event schedule" message pop up is displayed on the primary card 
	And user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	Then  user receives and views a "saving event schedule" message on the "ACTIVITY HISTORY" screen 
	#And user logs out of the application
	
	
@VerifySavingEventStartMessageDutyCycle @UIAutomatable 
Scenario: Verify Saving Event Start Message 
	As a user, I should receive a saving event start message on the primary card,  Activity History screen
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "duty cycle" and "is" opt-out able for "10" minutes and "2" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	Then user "SHOULD BE DIPLAYED" with the "DR event label on Dashboard" option 
	And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	Then user "SHOULD BE DIPLAYED" with the "DR event label on primary card" option 
	And user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	Then  user receives and views a "saving event started" message on the "ACTIVITY HISTORY" screen 
	And user navigates to "Dashboard" screen from the "Activity History" screen 
	And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	And user selects "MAX SET TEMPERATURE BY TAPING ON UP STEPPER" from "Primary Card" screen 
	And user selects "MIN SET TEMPERATURE BY TAPING ON DOWN STEPPER" from "Primary Card" screen 
	#  And user logs out of the application
	
@VerifySavingEventEndMessage @UIAutomatable 
Scenario: Verify Saving Event End Message 
	As a user, I should receive a saving event end message on Activity History screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "duty cycle" and "is" opt-out able for "15" minutes and "2" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	And DR event has ended on user device 
	Then user "SHOULD NOT BE DISPLAYED" with the "DR event label on Dashboard" option 
	And  user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	Then user "SHOULD NOT BE DISPLAYED" with the "DR event label on primary card" option 
	When user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event ended" message on the "ACTIVITY HISTORY" screen 
	#  And user logs out of the application
	
@VerifySavingEventCancelByUtilityMessage @UIAutomatable 
Scenario: Verify Saving Event Cancel By Utility Message 
	As a user, I should be able to cancel a DR Event and receive a saving event cancel message on aActivity History screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "duty cycle" and "is" opt-out able for "10" minutes and "2" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	And DR event is cancelled by the utility provider 
	Then user "SHOULD NOT BE DISPLAYED" with the "DR event label on Dashboard" option 
	And  user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	Then user "SHOULD NOT BE DISPLAYED" with the "DR event label on primary card" option 
	When user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event canceled by utility" message on the "ACTIVITY HISTORY" screen 
	#  And user logs out of the application      
	
@VerifySavingEventCancelByUserMessageDutyCycle @UIAutomatable 
Scenario: Verify Saving Event Cancel By User Message 
	As a user, I should be able to cancel a DR Event and receive a saving event cancel message on Activity History screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "duty cycle" and "is" opt-out able for "10" minutes and "1" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	Then user "SHOULD BE DISPLAYED" with the "DR event label on Dashboard" option 
	When user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	Then user "SHOULD BE DISPLAYED" with the "DR event label on primary card" option 
	And user selects "DR Event Label" from "Primary Card" screen
	Then "cancel saving event message with a Yes and No" message pop up is displayed on the primary card
	When user "DISMISSES" the "DR CANCEL" popup
	Then user "SHOULD BE DIPLAYED" with the "DR event label on primary card" option
	And user should be displayed with "DR" setpoint value in solution card
	When user selects "DR Event Label" from "Primary Card" screen
	Then "cancel saving event message with a Yes and No" message pop up is displayed on the primary card
	When user "ACCEPTS" the "DR CANCEL" popup
	Then user "should not be displayed" with the "DR event label on primary card" option
	When user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen
	Then user "should not be displayed" with the "DR event label on dashboard" option
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event canceled by user" message on the "ACTIVITY HISTORY" screen 
	#And user logs out of the application 
		
		@VerifyAdHocStatusAfterDREventEndsTimeBassedScheduling @UIAutomatable 
		Scenario Outline: Verify Ad Hoc Status After DR Event Ends 
			As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
			Given user has "time based schedule" 
			And user puts schedule on <ad hoc status> 
			And user has thermostat enrolled with DR 
			And user has triggered DR event with "duty cycle" and "is" opt-out able 
			And DR event has started on the user device 
			When user logs in to the Lyric Application 
			Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
			And user should be displayed with a "saving event until" green label on the "primary card" screen 
			When DR event has ended 
			Then user should receive a <Ad Hoc status> status on primary card 
			And user setpoints should be reverted back 
			And user logs out of the application 
			
			Examples: 
				| Ad Hoc status  | 
				| Permanent Hold | 
				| Temporary Hold |
				#Both JpaserNA and HB
				| Schedule Off   |
				
				@VerifyAdHocStatusAfterDREventEndsGeofenceBasedScheduling @UIAutomatable 
				Scenario Outline: Verify Ad Hoc Status After DR Event Ends 
					As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
					Given user has "geofence based schedule" 
					And user puts schedule on "temporary hold" 
					And user has thermostat enrolled with DR 
					And user has triggered DR event with "duty cycle" and "is" opt-out able 
					And DR event has started on the user device 
					When user logs in to the Lyric Application 
					Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
					And user should be displayed with a "saving event until" green label on the "primary card" screen 
					When DR event has ended 
					Then user should receive a "temporary hold" status on primary card 
					And user setpoints should be reverted back 
					And user logs out of the application 
					
					
					
					
					
				@VerifyVacationStatusAfterDREventEnds @UIAutomatable 
				Scenario: Verify Vacation Status After DR Event Ends 
					As a user, I should receive a vacation ad hoc message after DR Ends
					Given vacation is "scheduled" 
					And vacation is "running" 
					And user has thermostat enrolled with DR 
					And user has triggered DR event with "duty cycle" and "is" opt-out able 
					And DR event has started on the user device 
					When user logs in to the Lyric Application 
					Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
					And user should be displayed with a "saving event until" green label on the "primary card" screen 
					When DR event has ended 
					Then user should receive a "vacation status" on primary card 
					And user logs out of the application 
					
				@VerifyTimeScheduleAdHocStatusAfterDREnds @UIAutomatable 
				Scenario Outline: Verify Time Schedule Ad Hoc Status After DR Ends 
					As a user, I should receive a following schedule message after DR Ends in next period of time schedule
					Given user has "time based schedule" with "Temporary Hold" 
					And user has <Thermostat> enrolled with DR 
					And user has triggered DR event with "duty cycle" and "is" opt-out able 
					And DR event has started on the user device 
					When user logs in to the Lyric Application 
					Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
					And user should be displayed with a "saving event until" green label on the "primary card" screen 
					And DR event ends on next period of the schedule 
					And user should be displayed with "Following Schedule" label on the primary card 
					And user setpoints should be reverted back 
					And user logs out of the application 
					Examples: 
						|HB|
						|JasperNA|
						
					@VerifyGeofenceScheduleAdHocStatusAfterDREnds @NotAutomatable 
					Scenario Outline: Verify Geofence Schedule Ad Hoc Status After DR Ends 
						As a user, I should receive a geofence until schedule message after DR Ends in geofence crossed location
						Given user is <geofence location> 
						Given user has "geofence schedule" with "Temporary Hold" 
						And user has <Thermostat> enrolled with DR 
						And user has triggered DR event with "duty cycle" and "is" opt-out able 
						And DR event has started on the user device 
						When user logs in to the Lyric Application 
						Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
						And user should be displayed with a "saving event until" green label on the "primary card" screen 
						When user is <geofence crossed location> 
						And DR event ends 
						Then user should be displayed with "geofence until schedule" label on the primary card 
						And user logs out of the application 
						
						Examples: 
							|Thermostat| geofence location | geofence crossed location | 
							|HB       | Home              | Away                      | 
							|HB       | Away              | Home                      | 
							|JasperNA | Home              | Away                      | 
							|JasperNA | Away              | Home                      | 
							
							
							
						@VerifyDRStatusAfterVacationStarts @UIAutomatable 
						Scenario: Verify DR Status After Vacation Starts for Jasper Thermostats 
							As a user, I should receive a DR message after vacation starts
							Given vacation is "scheduled" 
							And user has thermostat enrolled with DR 
							And user has triggered DR event with "duty cycle" and "is" opt-out able 
							And DR event has started on the user device 
							When user logs in to the Lyric Application 
							Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
							And user should be displayed with a "saving event until" green label on the "primary card" screen 
							When user vacation "starts" 
							Then user should be displayed with a "saving event until" green label on the "primary card" screen 
							Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
							When DR event has ended 
							Then user nacviages to "Primarycard" screen 
							And user should receive a "vacation status" on primary card 
							And user logs out of the application 
							
						@VerifyDRStatusWhenChangingSystemMode @UIAutomatable 
						Scenario Outline: Verify DR Status When Chaning System Mode 
							As a user, I should receive a DR Lable on Cool/Heat system mode and No Lable should be present on OFF mode
							Given user has <Thermostat> enrolled with DR 
							And user has triggered DR event with "duty cycle" and "is" opt-out able 
							And User device is in "cool" mode 
							And DR event has started on the user device 
							When user logs in to the Lyric Application 
							Then user should be displayed with a "saving event until" green label on the "Dashboard" screen 
							And user should be displayed with a "saving event until" green label on the "primary card" screen 
							And user setpoints should be "dr cool" setpoints 
							When user changes system to "heat" 
							Then user should be displayed with a "saving event until" green label on the "primary card" screen 
							And user setpoints should be "dr heat" setpoints 
							When user changes system to "off" 
							Then user should not be displayed with a "saving event until" green label on the "primary card" screen 
							And user should not be dipalyed with a "saving event until" green label on the "Dashboard" screen 
							When user changes system to "heat" 
							Then user should be displayed with a "saving event until" green label on the "primary card" screen 
							And user setpoints should be "dr heat" setpoints 
							When user changes system to "cool" 
							Then user should be displayed with a "saving event until" green label on the "primary card" screen 
							And user setpoints should be "dr cool" setpoints 
							When user changes system to "off" 
							Then user should not be displayed with a "saving event until" green label on the "primary card" screen 
							And user should not be dipalyed with a "saving event until" green label on the "Dashboard" screen 
							And user naviages to "Primary card" screen 
							When user changes system to "cool" 
							Then user should be displayed with a "saving event until" green label on the "primary card" screen 
							And user setpoints should be "dr cool" setpoints 
							And user logs out of the application 
							Examples: 
								|Thermostat |
								|HB|  
								
	  