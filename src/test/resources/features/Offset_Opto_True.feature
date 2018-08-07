@OffsetWithOptOutableTrue @Comfort
Feature: Offset with Opt Out able true 
	As a user, I want to opt out of DR when my offset is enabled

@VerifySavingEventScheduleMessageOffset @AutomatedonAndroid 
Scenario: Verify Saving Event Schedule Message with DR Event Offset 
	As a user, I should receive a saving event schedule message on the primary card, alerts screen and messages screen
	#Given user thermostat is enrolled with DR 
	#And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "3" minutes from now 
	When user launches and logs in to the Lyric application 
	#And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	#Then "saving event schedule" message pop up is displayed on the primary card 
	#And user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	#And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event schedule" message on the "ACTIVITY HISTORY" screen 
	#Then user logs out of the app
	
@VerifySavingEventStartMessageOffset @AutomatedonAndroid 
Scenario: Verify Saving Event Start Message with DR Event Offset 
	As a user, I should receive a saving event start message on the primary card, alerts screen and messages screen
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	And user navigates to "THERMOSTAT SOLUTION CARD" screen from the "THERMOSTAT DASHBOARD" screen 
	And user navigates to "DASHBOARD" screen from the "THERMOSTAT SOLUTION CARD" screen 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	Then user receives and views a "saving event started" message on the "ACTIVITY HISTORY" screen 
	And user logs out of the app 
	
@VerifySavingEventEndMessageOffset  @AutomatedonAndroid
Scenario: Verify Saving Event End Message Offset 
	As a user, I should receive a saving event end message on alerts screen and messages screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "offset" and "is" opt-out able for "3" minutes and "1" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	And DR event has ended on user device 
	Then user should not be displayed with a DR event label on the primary card 
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event ended" message on the "ACTIVITY HISTORY" screen 
	And user logs out of the app 
	
	
@VerifySavingEventCancelByUtilityMessageOffset  @AutomatedonAndroid
Scenario: Verify Saving Event Cancel By Utility Message Offset 
	As a user, I should be able to cancel a DR Event and receive a saving event cancel message on alerts screen and messages screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
	And DR event has started on the user device 
	When user launches and logs in to the Lyric application 
	And DR event is cancelled by the utility provider 
	#Then user should not be displayed with a DR event label on the primary card
	And user navigates to "ACTIVITY HISTORY" screen from the "Dashboard" screen 
	And user receives and views a "saving event canceled by utility" message on the "ACTIVITY HISTORY" screen 
	#And user logs out of the app 
	
@VerifySavingEventCancelByUserMessageOffset  @UIAutomatable 
Scenario: Verify Saving Event Cancel By User Message Offset 
	As a user, I should be able to cancel a DR Event and receive a saving event cancel message on alerts screen and messages screen for HBB and Jasper Devices
	Given user thermostat is enrolled with DR 
	And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
	And DR event has started on the user device 
	And user has "cool" system mode 
	When user logs in to Lyric app 
	Then user should be displayed with a "saving event until green" label on the primary card 
	When user "decreases" the dialer set points 
	Then user "receives" a cancel saving event message with a "Yes and No" option 
	When user taps on the "No" option 
	Then user should be displayed with a "saving event until green" label on the primary card 
	And user set points should be "DR Cool" set points 
	When user changes system mode to "heat" 
	And user "increases" the dialer set points 
	Then user "receives" a cancel saving event message with a "Yes and No" option 
	When user taps on the "Yes" option 
	Then user should not be displayed with a DR event label on the primary card 
	And user receives a "saving event cancelled by user" alert on the alert screen 
	And user receives and views a "saving event cancelled by user" message on the messages screen 
	And user logs out of the app 

	
@VerifyAdHocStatusAfterDREventEndsTimeBassedSchedulingOffset  @UIAutomatable 
Scenario Outline: Verify Ad Hoc Status After DR Event Ends Time Based Offset 
	As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
	Given user thermostat is enrolled with DR 
	And user has "time based" schedule 
	And user puts schedule on <ad hoc status> 
	And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
	And DR event has started on the user device 
	And DR event has started on the user device 
	When user logs in to Lyric app 
	Then user should be displayed with a "saving event until green" label on the primary card 
	When DR event has ended on user device 
	Then user should receive a <ad hoc status> status on primary card 
	And user set points should be <ad hoc status> set points 
	And user logs out of the app 
	
	Examples: 
		| ad hoc status  | 
		| permanent hold | 
		| temporary hold |
		#| Schedule Off   |
		
		@VerifyAdHocStatusAfterDREventEndsGeofenceBasedSchedulingOffset  @UIAutomatable
		Scenario: Verify Ad Hoc Status After DR Event Ends Geofence Offset 
			As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
			Given user thermostat is enrolled with DR 
			And user has "geofence based" schedule 
			And user puts schedule on "temporary hold" 
			And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
			And DR event has started on the user device 
			When user logs in to Lyric app 
			Then user should be displayed with a "saving event until green" label on the primary card 
			When DR event has ended on user device 
			Then user should receive a "temporary hold" status on primary card 
			And user set points should be "temporary hold" set points 
			And user logs out of the app 
			
		@VerifyAdHocStatusAfterDREventEndsNoScheduleOffset  @UIAutomatable
		Scenario: Verify Ad Hoc Status After DR Event Ends No Schedule Offset 
			As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
			Given user thermostat is enrolled with DR 
			And user has "no" schedule 
			And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
			And DR event has started on the user device 
			When user logs in to Lyric app 
			Then user should be displayed with a "saving event until green" label on the primary card 
			When DR event has ended on user device 
			Then user should receive a "no schedule" status on primary card 
			And user set points should be "no schedule" set points 
			And user logs out of the app 
			
		@VerifyVacationStatusAfterDREventEndsOffset  @UIAutomatable
		Scenario: Verify Vacation Status After DR Event Ends Vacation Offset 
			As a user, I should receive a vacation ad hoc message after DR Ends
			Given user thermostat is enrolled with DR 
			And vacation mode is "active" 
			And vacation is running on user device 
			And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
			And DR event has started on the user device 
			When user logs in to Lyric app 
			Then user should be displayed with a "saving event until green" label on the primary card 
			When DR event has ended on user device 
			Then user should receive a "vacation" status on primary card 
			And user set points should be "vacation" set points 
			And user logs out of the app 
			
		@VerifyTimeScheduleAdHocStatusAfterDREndsOffset  @UIAutomatable
		Scenario: 
			Verify Time Schedule Ad Hoc Status After DR Ends In The Next Time Period Offset 
			As a user, I should receive a following schedule message after DR Ends in next period of time schedule
			Given user thermostat is enrolled with DR 
			And user has time based schedule with 15 minute interval between periods 
			And user puts schedule on "temporary hold" 
			And user has triggered DR event with "offset" and "is" opt-out able for "15" minutes and "1" minutes from now 
			And DR event has started on the user device 
			When user logs in to Lyric app 
			Then user should be displayed with a "saving event until green" label on the primary card 
			And DR event ends on next period of the schedule 
			And user should be displayed with a "following schedule" label on the primary card 
			And user logs out of the app 
			
		@VerifyGeofenceScheduleAdHocStatusAfterDREndsOffset @UIAutomatable
		Scenario Outline: Verify Geofence Schedule Ad Hoc Status After DR Ends Offset 
			As a user, I should receive a geofence until schedule message after DR Ends in geofence crossed location
			Given user thermostat is enrolled with DR 
			And user has "geofence based without sleep" schedule 
			And user puts schedule on "temporary hold" 
			And user is <geofence location> 
			And user has triggered DR event with "offset" and "is" opt-out able for "10" minutes and "1" minutes from now 
			And DR event has started on the user device 
			When user logs in to Lyric app 
			Then user should be displayed with a "saving event until green" label on the primary card 
			When user is <geofence crossed location> 
			And DR event has ended on user device 
			Then user should be displayed with a <geofence status> label on the primary card 
			And user logs out of the app 
			
			Examples: 
				| geofence location | geofence crossed location | geofence status     |
				| Home              | Away                      | using away settings |
				| Away              | Home                      | using home settings |
				
				@VerifyDRStatusAfterVacationStartsOffset @UIAutomatable 
				Scenario: 
					Verify DR Status After Vacation Starts for Jasper Thermostats Offset 
					As a user, I should receive a DR message after vacation starts
					Given user thermostat is enrolled with DR 
					And vacation mode is "active" 
					And user has triggered DR event with "offset" and "is" opt-out able for "15" minutes and "1" minutes from now 
					And DR event has started on the user device 
					When user logs in to Lyric app 
					Then user should be displayed with a "saving event until green" label on the primary card 
					When vacation is running on user device 
					Then user should be displayed with a "saving event until green" label on the primary card 
					When DR event has ended on user device 
					Then user should receive a "vacation" status on primary card 
					And vacation mode is "yet to set" 
					And user logs out of the app 
					
				@VerifyDRStatusWhenChangingSystemModeOffset  @UIAutomatable
				Scenario: Verify DR Status When Changing System Mode 
					As a user, I should receive a DR Label on Cool/Heat system mode and No Label should be present on OFF mode
					Given user thermostat is enrolled with DR 
					And user has triggered DR event with "offset" and "is" opt-out able for "15" minutes and "1" minutes from now 
					And user has "cool" system mode 
					And DR event has started on the user device 
					When user logs in to Lyric app 
					Then user should be displayed with a "saving event until green" label on the primary card 
					And user set points should be "DR Cool" set points 
					When user changes system mode to "heat" 
					Then user should be displayed with a "saving event until green" label on the primary card 
					And user set points should be "DR Heat" set points 
					When user changes system mode to "off" 
					Then user should not be displayed with a DR event label on the primary card 
					When user changes system mode to "heat" 
					Then user should be displayed with a "saving event until green" label on the primary card 
					And user set points should be "DR Heat" set points 
					When user changes system mode to "cool" 
					Then user should be displayed with a "saving event until green" label on the primary card 
					And user set points should be "DR Cool" set points 
					When user changes system mode to "off" 
					Then user should not be displayed with a DR event label on the primary card 
					When user changes system mode to "cool" 
					Then user should be displayed with a "saving event until green" label on the primary card 
					And user set points should be "DR Cool" set points 
					And user logs out of the app