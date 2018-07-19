@OffsetWithOptOutableTrue
Feature: Offset with Opt Out able true
As a user, I want to opt out of DR when my offset is enabled

@VerifySavingEventScheduleMessage
  Scenario Outline: Verify Saving Event Schedule Message
  As a user, I should receive a saving event schedule message on the primary card, Activity History screen
    Given user <Thermostat> is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
     When user logs in to the Lyric application
     Then user should be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then "saving event schedule" message pop up is displayed on the primary card
      And user receives and views a "saving event schedule" message on the "Activity History" screen
      And user can view the DR event details by tapping on the "Message" in Activity History" screen
     Then user logs out of the application
     Examples:
     |Thermostat|
     |HB|
     |JasperNA| 
  
  @VerifySavingEventStartMessage
  Scenario Outline: Verify Saving Event Start Message
  As a user, I should receive a saving event start message on the primary card, Activity History screen
    Given user <Thermostat> is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
      Then user should be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then user should be displayed with a "saving event until" green label on the primary card
      And user receives and views a "saving event started" message on the "Activity History" screen
      And user can view the DR event details by tapping on the "Message" in Activity History" screen
      And user should be allowed to change the dialer set points
      And user logs out of the application
       Examples:
     |Thermostat|
     |HB|
     |JasperNA|  
  
  @VerifySavingEventEndMessage
  Scenario: Verify Saving Event End Message
  As a user, I should receive a saving event end message on Activity History screen for HBB and Jasper Devices
    Given user thermostat is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
      And DR event has ended
     Then user should not be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then user should not be displayed with a "Saving event until" green DR event label on the primary card
      And user receives a "saving event ended" message on the "Activity; History" screen
      And user can view the DR event details by tapping on the "Message" in "Activity History" screen
      And user logs out of the application
       Examples:
     |Thermostat|
     |HB|
     |JasperNA| 
  
 @VerifySavingEventCancelByUserforheat
  Scenario Outline: Verify Saving Event Cancel By User in Heat mode 
  As a user, I should be able to cancel a DR Event and receive a saving event cancel message on Activity History screen for HBB and Jasper Devices in heat mode when delta value is passed 
    Given user thermostat is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able and dalta value for heat mode 
      And DR event has started on the user device
     When user logs in to the Lyric Application
     Then user should not be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then user should not be displayed with a "Saving event until" green DR event label on the primary card
     And decreases the set point below the heat set value 
     Then user would be allowed to set a temperature below the set value 
     When user increases heat set value upto the delta vlaue 
     Then user should be allowed to set temperature upto the heat delta set value
      And user increases the set point above the heat set value 
     Then user "receives" a cancel saving event message with a "Yes and No" option
     When user taps on the "No" option
     Then user should be displayed with a "saving event until" green label on the primary card
      And set point value should not change
     When user cancels saving event by tapping the  steper button above the heat set value 
     Then user "receives" a cancel saving event message with a "Yes and No" option
     When user taps on the "Yes" option
     Then user should not be displayed with a "saving event until" greenDR event label on the primary card
	   And user setpoints should be reverted back
      And user receives a "saving event ended" message on the "Activity; History" screen
      And user can view the DR event details by tapping on the "Message" in "Activity History" screen
      And user logs out of the application
      |Thermostat|
     |HB|
     |JasperNA| 

      
       @VerifySavingEventCancelByUserforcool
  Scenario Outline: Verify Saving Event Cancel By User in cool mode 
  As a user, I should be able to cancel a DR Event and receive a saving event cancel message on  Activity History screen for HBB and Jasper Devices in cool mode when delta value is passed 
    Given user thermostat is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able and dalta value for cool mode 
      And DR event has started on the user device
     When user logs in to the Lyric Application
     Then user should not be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then user should not be displayed with a "Saving event until" green DR event label on the primary card
     And increase the set point above the cool set value 
     Then user would be allowed to set a temperature above the cool set value
      When user increases cool set value upto the cool set delta vlaue 
     Then user should be allowed to set temperature upto the cool delta set value
      And user decreases the set point below the cool set value 
     Then user "receives" a cancel saving event message with a "Yes and No" option
     When user taps on the "No" option
     Then user should be displayed with a "saving event until" green label on the primary card
      And set point value should not change
     When user cancels saving event by rotating the dialer above the cool set value 
     Then user "receives" a cancel saving event message with a "Yes and No" option
     When user taps on the "Yes" option
     Then user should not be displayed with a "saving event until" greenDR event label on the primary card
     And user setpoints should be reverted back
      And user receives a "saving event ended" message on the "Activity; History" screen
      And user can view the DR event details by tapping on the "Message" in "Activity History" screen
      And user logs out of the application
      |Thermostat|
     |HB|
     |JasperNA| 
      
  
  @VerifySavingEventCancelByUtilityMessage
  Scenario: Verify Saving Event Cancel By Utility Message
  As a user, I should be able to cancel a DR Event and receive a saving event cancel message on Activity History screen for HBB and Jasper Devices
    Given user thermostat is enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
      And DR event is cancelled by the utility provider
     Then user should not be displayed with a "saving event until" green DR event label on the "Dashboard" screen 
      And user should not be displayed with a "saving event until" green DR event label on the "primary card" screen
      And user setpoints should be reverted back
      And user receives a "saving event cancelled by utility" message on the "Activity History" screen
      And user can view the DR event details by tapping on the "Message" in "Activity History" screen
      And user logs out of the application
       Examples:
     |Thermostat|
     |HB|
     |JasperNA| 


  
  @VerifyAdHocStatusAfterDREventEndsTimeBassedScheduling
  Scenario Outline: Verify Ad Hoc Status After DR Event Ends
  As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
    Given user has "time based schedule"
      And user puts schedule on <ad hoc status>
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
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
  
  @VerifyAdHocStatusAfterDREventEndsGeofenceBasedScheduling
  Scenario Outline: Verify Ad Hoc Status After DR Event Ends
  As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
    Given user has "geofence based schedule"
      And user puts schedule on "temporary hold"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
     Then user should be displayed with a "saving event until" green label on the "Dashboard" screen
     And user should be displayed with a "saving event until" green label on the "primary card" screen
     When DR event has ended
     Then user should receive a "temporary hold" status on primary card
      And user setpoints should be reverted back
      And user shoudl not be displayed with a "Saving event until" green DR label on the "Dashboard" screen
      And user logs out of the application
  
  @VerifyAdHocStatusAfterDREventEndsNoSchedule
  Scenario Outline: Verify Ad Hoc Status After DR Event Ends
  As a user, I should receive a ad hoc message after DR Ends for Jasper Thermostats
    Given user <Thermostat> is enrolled with DR
      And user has "no schedule"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
      Then user should be displayed with a "saving event until" green label on the "Dashboard" screen
     And user should be displayed with a "saving event until" green label on the "primary card" screen
     When DR event has ended
     Then user should receive a "no schedule" status on primary card
     And user setpoints should be reverted back
      And user logs out of the application
      Examples:
      |HB|
      |JasperNA|

  
  @VerifyVacationStatusAfterDREventEnds
  Scenario: Verify Vacation Status After DR Event Ends
  As a user, I should receive a vacation ad hoc message after DR Ends
    Given vacation is "scheduled"
      And vacation is "running"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
      Then user should be displayed with a "saving event until" green label on the "Dashboard" screen
     And user should be displayed with a "saving event until" green label on the "primary card" screen
     When DR event has ended
     Then user should receive a "vacation status" on primary card
      And user logs out of the application
  
  @VerifyTimeScheduleAdHocStatusAfterDREnds
  Scenario: Verify Time Schedule Ad Hoc Status After DR Ends
  As a user, I should receive a following schedule message after DR Ends in next period of time schedule
    Given user has "time based schedule" with "Temporary Hold"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
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
  
  @VerifyGeofenceScheduleAdHocStatusAfterDREnds
  Scenario Outline: Verify Geofence Schedule Ad Hoc Status After DR Ends
  As a user, I should receive a geofence until schedule message after DR Ends in geofence crossed location
    Given user is <geofence location>
    Given user has "geofence schedule" with "Temporary Hold"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
      And DR event has started on the user device
     When user logs in to the Lyric Application
       Then user should be displayed with a "saving event until" green label on the "Dashboard" screen
     And user should be displayed with a "saving event until" green label on the "primary card" screen
     When user is <geofence crossed location>
      And DR event ends
     Then user should be displayed with "geofence until schedule" label on the primary card
     And user setpoints should be reverted back
      And user logs out of the application
  
    Examples: 
      |Thermostat| geofence location | geofence crossed location | 
      |HB       | Home              | Away                      | 
      |HB       | Away              | Home                      | 
      |JasperNA | Home              | Away                      | 
      |JasperNA | Away              | Home                      | 


  @VerifyDRStatusAfterVacationStarts
  Scenario: Verify DR Status After Vacation Starts for Jasper Thermostats
  As a user, I should receive a DR message after vacation starts
    Given vacation is "scheduled"
      And user has thermostat enrolled with DR
      And user has triggered DR event with "offset" and "is" opt-out able
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
  
  @VerifyDRStatusWhenChangingSystemMode
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
     hen user should not be displayed with a "saving event until" green label on the "primary card" screen
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
      Examples :
      |Thermostat |
      |HB|  


      
