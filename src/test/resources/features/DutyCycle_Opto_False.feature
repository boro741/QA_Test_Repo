@DutyCycleWithOptOutableFalse
Feature: Duty Cycle with OptOutable set to false
As a user, I should not be able to opt out of DR when my duty cycle is enabled

@VerifySavingEventCancelByUserMessage
  Scenario Outline: Verify Saving Event Cancel By User Message
  As a user, I should be able to cancel a DR Event and receive a saving event cancel message on Activity History screen for HBB and Jasper Devices
    Given user <Thermostat> is enrolled with DR
      And user has triggered DR event with "duty cycle" and "is not" optoutable
      And DR event has started on the user device
     When user logs in to the Lyric Application
     Then user should be displayed with a "Saving event until" green label on the Dashboard
     When user naviagtes to "Primary card" screen 
     Then user should be displayed with a "saving event until" green label on the primary card
      And user cancels saving event by tapping on the green label
     Then user "receives" a cancel saving event message with a "OK" option
     When user taps on the "OK" option
     Then user should be displayed with a "saving event until" green label on the primary card
      And set point value should not change
      And user logs out of the application
      Examples:
     |Thermostat|
     |HB|
     |JasperNA|  