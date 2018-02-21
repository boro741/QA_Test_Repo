@DASZWAVE
Feature: DAS ZWAVE
As a user I want to control all devices using ZWave technology

@NoZwaveOption @IOSCOMPLETED
  Scenario: (ZwaveTC1) As a user i should not be shown with Zwave options when there is no DAS panel available in the location
    Given user launches and logs in to the Lyric application 
     When user navigates to "Add new device dashboard" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device from Add device list" 
     When user navigates to "Global drawer" screen from the "Add new device dashboard" screen
     Then user should not be able to configure "Z-Wave Device from Global drawer menu"
     When user navigates to "Add new device global drawer" screen from the "Global drawer" screen
     Then user should not be able to configure "Z-Wave Device from Add device list" 
  
  @ZwaveOptionfromultipleDAS
  Scenario: (ZwaveTC2) As a user i should be listed with available DAS panel(more than 1) in that location to add Zwave devices
  #DAS1 panel online and DAS2 panel offline 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Add new device(dashboard)" screen from the "Dashboard" screen
     Then user should be able to configure "Z-Wave Device" 
     When user navigates to "Global drawer" screen from the "Add new device(dashboard)" screen
     Then user should be displayed with the "Z-Wave Device" option
     When user navigates to "Add new device(global drawer)" screen from the "Global drawer" screen
     Then user should be able to configure "Z-Wave Device"
      And user selects "Z-Wave Device" from "install device" screen
     Then user should be displayed with the "DAS panel list on Zwave" screen
     Then user should be displayed with the "DAS1 online on Zwave" screen
     Then user should be displayed with the "DAS1 offline on Zwave" screen
  
  @AddNewDeviceInclusionNoDeviceFound @Reviewed
  Scenario: (ZwaveTC3) As a user I should be displayed no device popup when there is no zwave device available to add 
  #atleast 1 das
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #timeout
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "dismisses" the "Inclusion Device not found" popup
     Then user should be displayed with the "Add new device" screen
  #timeout
      And user selects "Z-Wave Device" from "install device" screen
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Retries the inclusion on" the "Inclusion Device not found" popup
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #timeout
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Tries Exclusion on" the "Inclusion Device not found" popup 
  #timeout
     When user "does not activate" the "switch" function key
     Then user should be displayed with the "Add new device" screen
  
  @DeviceIncludedOnRetry @Reviewed @LYDAS-6172
  Scenario: (ZwaveTC4) As a user I should be able to retry the zwave inclusion from No device found popup
    Given user launches and logs in to the Lyric application 
    Given user has no "Switch1"
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "does not activate" the "switch" function key
     #timeout
     Then user should receive a "Inclusion Device not found" popup
     When user "Retries the inclusion on" the "Inclusion Device not found" popup
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
     When user names the "switch" to "Switch1"
  #null, string length with 14, only alpha numeric char}
     Then user should be displayed with "Switch1" device on dashboard
  
  @DeviceFoundAfterExclude @Reviewed 
  Scenario: (ZwaveTC5) As a user I can exclude my zwave device from No device found popup and can include
  #switch already configured
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #timeout
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Tries Exclusion on" the "Inclusion Device not found" popup 
  #timeout
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "Adds device NOW" the "Device Excluded" popup 
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
     When user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
  
  @AddNewDeviceIncludeZwaveSwitch @Corrected @LYDAS-5209 @LYDAS-6587
  Scenario: (ZwaveTC6) As a user my I want to include a zwave switch through the Add new device in application
    Given user launches and logs in to the Lyric application 
    Given user has no "switch1"
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
     When user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
  # Then user receives a "Zwave device added" activity log
  
  @ToggleZwaveSwitchThroughPrimaryCard @corrected @LYDAS-4594
  Scenario: (ZwaveTC7) As a user my I want to toggle my zwave switch to different states through the primary card
      And user launches and logs in to the Lyric application
    Given user turns "off" the "Switch" through the "Z-Wave device function key"
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen
     Then user should see the "Switch" status as "off" on the "Dashboard"
  #action on Primary card - verify pc, dashboard and device
  When user navigates to "Switch Primary card" screen from the "Dashboard" screen
     When user turns "on" the "Switch" through the "Switch Primary card"
     Then user should see the "Switch" status as "on" on the "Switch Primary card"
  #  Then user should see the "Timer" status as "Current time" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen
  #  Then user should see the "Switch" status as "on" on the "Dashboard"
      And user should see the "Switch" status as "on" on the "Z-Wave device"
  #action on Primary card - verify pc, dashboard and device
   When user navigates to "Switch Primary card" screen from the "Dashboard" screen
     When user turns "off" the "Switch" through the "Switch Primary card"
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen
  #    Then user should see the "Switch" status as "Off" on the "Dashboard"
      And user should see the "Switch" status as "off" on the "Z-Wave device"
  #action on device - verify pc and device
     When user turns "on" the "Switch" through the "Z-Wave device function key"
     Then user should see the "Switch" status as "on" on the "Z-Wave device"
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen
     Then user should see the "Switch" status as "on" on the "Switch Primary card"
  #action on device - verify pc and device
     When user turns "off" the "Switch" through the "Z-Wave device function key"
     Then user should see the "Switch" status as "off" on the "Z-Wave device"
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
  
  @StatusChangeOfSwitchFromSettings
  Scenario: (ZwaveTC8) As a user my I want to toggle my zwave switch to different states through the settings
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user turns "on" the "Switch" through the "Switch settings"
     When user navigates to "Switch Primary card" screen from the "Switch settings" screen 
     Then user should see the "Switch" status as "on" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user turns "off" the "Switch" through the "Switch settings"
     When user navigates to "Switch Primary card" screen from the "Switch settings" screen 
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
  
  @ReIncludeZwaveSwitch @Corrected @LYDAS-5209 @LYDAS-6587
  Scenario: (ZwaveTC9) As a user my I want to include a zwave switch through the Add new device in application
    Given user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
     When user names the "switch" to "Switch2"
     Then user should be displayed with "Switch2" device on dashboard
  # Then user receives a "Zwave device added" activity log
  
  @ZwaveSwitchRename @Reviewed @LYDAS-5395
  Scenario: (ZwaveTC17) As a user my I want to rename my zwave switch
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user edits the "Switch" name to "Switch2"
     When user navigates to "Dashboard" screen from the "Switch settings" screen
     Then user should be displayed with "Switch2" device on dashboard
    #  And user reverts back the "Switch" name through CHIL
  
  
  @DeleteZwaveSwitchFromSettings 
  Scenario: (ZwaveTC7) As a user my I want to delete my zwave switch
  #switch configured and online
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user selects "Delete" from "Switch settings" screen
     Then user should receive a "Remove Device" popup
     When user "confirms" the "Deletion on Remove Device" popup
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "confirms" the "Device Excluded" popup
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Switch" device on dashboard
  
  @GeneralIncludeZwaveSwitch @Reviewed @LYDAS-5507
  Scenario: (ZwaveTC9) As a user my I want to exclude a zwave switch through General Inclusion in the application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device through General inclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
     When user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
  
  @GeneralExcludeZwaveSwitch @Reviewed
  Scenario: (ZwaveTC10) As a user my I want to exclude a zwave switch through Genera exclusion in the application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "dismisses" the "Further Exclusion" popup 
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from "Z-Wave Utilities" screen
     Then user should not be displayed with "Switch" device on dashboard
  
  @GeneralExcludeSwitchOnNoDeviceFound @corrected
  Scenario: (ZwaveTC11) As a user my I want to exclude a zwave switch through the General exclusion in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activates" the "switch" function key
     Then user should receive a "Device not found" popup
     When user "dismisses" the "Further Exclusion" popup 
     When user navigates to "Z-Wave device(General Exclusion)" from "Z-Wave Utilities" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activates" the "switch" function key
     Then user should receive a "Device not found" popup
     When user "Retries the exclusion on" the "Device not found" popup
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Switch" device on dashboard
  
  @AddNewDeviceIncludeZwaveLock @LYDAS-5002
  Scenario: (ZwaveTC7) As a user my I want to include a zwave lock through the Add new device in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "lock" function key
     When user names the "lock" as "Lock1"
     When user navigates to "Lock settings" screen from the "Z-wave utilies" screen
     Then user should be displayed with the "Lock details" screen #check details
  
  @AddNewDeviceIncludeZwaveDimmer @Corrected
  Scenario: (ZwaveTC8 As a user my I want to include a zwave dimmer through the the Add new device in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "dimmer" function key
     When user names the "dimmer" as "Dimmer1"
     Then user should be displayed with "Dimmer1" device on dashboard	
     Then user receives a "Zwave device added" activity log
     
  @GeneralExcludeZwaveDimmer @corrected
  Scenario: (ZwaveTC12) As a user my I want to exclude a zwave dimmer through the General exclusion in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "dimmer" function key
     Then user should receive a "Dimmer Excluded Successfully" popup
     When user "dismisses" the "Further Exclusion" popup  
     Then user should not be displayed with the "Further Exclusion" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer" device on dashboard
  
  @StatusChangeOfDimmerFromSettings
  Scenario: (ZwaveTC15) As a user my I want to toggle my zwave dimmer to different states through the settings
      And user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user turns "on" the "Dimmer" through the "Dimmer settings"
     When user navigates to "Dimmer Primary card" screen from the "Dimmer settings" screen 
     Then user should see the "Dimmer" status as "on" on the "Dimmer Primary card"
     When user navigates to "Dashboard" screen from the "Dimmer Primary card" screen 
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user turns "off" the "Dimmer" through the "Dimmer settings"
     When user navigates to "Dimmer Primary card" screen from the "Dimmer settings" screen 
     Then user should see the "Dimmer" status as "off" on the "Dimmer Primary card"
  
  @StatusChangeOfUnknownFromSettings
  Scenario: (ZwaveTC116) As a user my I want to toggle my zwave unknown device to different states through the settings
      And user launches and logs in to the Lyric application
     When user navigates to "Unknown settings" screen from the "Dashboard" screen
     When user turns "on" the "Unknown" through the "Unknown settings"
     When user navigates to "Z-Wave Utilities" screen from the "Unknown settings" screen
     Then user should see the "Unknown" status as "on" on the "Z-Wave Utilities"
     When user navigates to "Unknown settings" screen from the "Z-Wave Utilities" screen
     When user turns "off" the "Unknown" through the "Unknown settings"
     When user navigates to "Z-Wave Utilities" screen from the "Unknown settings" screen
     Then user should see the "Unknown" status as "off" on the "Z-Wave Utilities"
  
  @ZwaveDimmerRename @Reviewed
  Scenario: (ZwaveTC18) As a user my I want to rename my zwave dimmer
      And user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user edits the "Dimmer" name to "Dimmer2"
     When user navigates to "Dashboard" screen from the "Dimmer settings" screen
     Then user should be displayed with "Dimmer2" device on dashboard
      And user reverts back the "Dimmer" name through CHIL
  
  @OfflineZwaveSwitchFromSettings
  Scenario: (ZwaveTC19) As a user my I want to be infromed when my switch device goes offline
  # switch configured but offline
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user turns "on" the "Switch" through the "Switch settings"
     When user turns "off" the "Switch" through the "Switch settings"
     Then user should see the "Switch" status as "offline" on the "Switch settings"
     When user navigates to "Zwave utilities" screen from the "Switch settings" screen
     Then user should see the "All ON" status as "inactive" on the "Zwave utilities"
     Then user should see the "All OFF" status as "inactive" on the "Zwave utilities"
     Then user should see the "Fix All" status as "active" on the "Zwave utilities"
     When user navigates to "Dashboard" screen from the "Zwave utilities" screen 
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen 
     Then user should see the "Switch" status as "offline" on the "Switch Primary card"
  
  @OnlineZwaveSwitchFromSettings @LYDAS-6370
  Scenario: (ZwaveTC20) As a user my I want to be infromed when my switch device goes offline
  # switch configured but offline
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user turns "on" the "Switch" through the "Switch settings"
     When user turns "off" the "Switch" through the "Switch settings"
     Then user should see the "Switch" status as "offline" on the "Switch settings"
     When user powers the "switch"
     Then user should see the "Switch" status as "online" on the "Switch settings"
  
  @OfflineZwaveDimmerFromSettings
  Scenario: (ZwaveTC21) As a user my I want to be infromed when my dimmer device goes offline
  # switch configured but offline
      And user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user turns "on" the "Dimmer" through the "Dimmer settings"
     When user turns "off" the "Dimmer" through the "Dimmer settings"
     Then user should see the "Dimmer" status as "offline" on the "Dimmer settings"
     When user navigates to "Dashboard" screen from the "Dimmer settings" screen 
     When user navigates to "Dimmer Primary card" screen from the "Dashboard" screen 
     Then user should see the "Dimmer" status as "offline" on the "Dimmer Primary card"
  
  @ReplaceZwaveTimeout @LYDAS-6569/LYDAS-5427
  Scenario: (ZwaveTC22) As a user my I want to delete my zwave switch
  # switch configured but offline
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "does not activates" the "dimmer" function key
     Then user should receive a "Device not found" popup
  
  @ReplaceZwaveDimmerWithSwitch @LYDAS-5380
  Scenario: (ZwaveTC23) As a user when my dimmer goes offline I can replace with a zwave switch
  #dimmer with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Switch" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with "Dimmer" device on dashboard
  # check in settings that its a switch
  
  @ReplaceZwaveDimmerWithDimmer
  Scenario: (ZwaveTC24)  As a user when my dimmer goes offline I can replace with another zwave dimmer
  #dimmer with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Dimmer2" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with "Dimmer" device on dashboard
  
  @ReplaceZwaveSwitchWithDimmer
  Scenario: (ZwaveTC25) As a user when my switch goes offline I can replace with another zwave dimmer
  #switch with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Dimmer" function key
     Then user should receive a "Switch Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with "Switch" device on dashboard
  # check in settings that its a dimmer
  
  @ReplaceZwaveSwitchWithSwitch
  Scenario: (ZwaveTC26) As a user when my switch goes offline I can replace with another zwave switch
  #switch with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Switch2" function key
     Then user should receive a "Switch Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with "Switch" device on dashboard
  
  @ReplaceZwaveSwitchWithUnknown @LYDAS-5380
  Scenario: (ZwaveTC27) As a user when my switch goes offline I can replace with another unknown zwave devices
  #switch with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Unknown" function key
     Then user should receive a "Switch Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Known" device on dashboard
  
  @ReplaceZwaveDimmerWithUnknown
  Scenario: (ZwaveTC28) As a user when my Dimmer goes offline I can replace with another unknown zwave devices
  #dimmer with offline
      And user launches and logs in to the Lyric application 
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
     When user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Unknown" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer" device on dashboard
  
  @ReplaceZwaveUnKnownWithknown @LYDAS-6966/LYDAS-6964/LYDAS-5380
  Scenario Outline: (ZwaveTC29) As a user I want to replace any zwave unknown device with known zwave device through the application
  #Water value with offline
    Given user launches and logs in to the Lyric application 
     Then user should not be displayed with "UnKnown" device on dashboard
     When user navigates to <Individual settings> screen from the "Dashboard" screen
     When user selects "Replace" from "Z-Wave Utilities" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the <Device To Activate> function key
     Then user should receive a "Device Replaced Successfully" popup
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with <Expected device> device on dashboard 
  
    Examples: 
      | Individual settings  | Device To Activate | Expected device | 
      | Water value settings | switch             | Water value     | 
  
  @RestoreSwitchFromOfflineState
  Scenario: (ZwaveTC30) As a user my I want to toggle my zwave switch to different states through the application
  # switch configured but offline
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     Then user should see the "Switch" status as "offline" on the "Switch settings"
  #made online
     When user "powers" the "Dimmer" function key
     When user turns "on" the "Switch" through the "Switch settings"
     When user turns "off" the "Switch" through the "Switch settings"
     When user navigates to "Dashboard" screen from the "Switch settings" screen 
     When user navigates to "Switch Primary Card" screen from the "Dashboard" screen 
     Then user should see the "Dimmer" status as "Off" on the ""Switch Primary Card"
  
  @DeleteZwaveDimmerFromSettings 
  Scenario: (ZwaveTC31) As a user my I want to delete my zwave switch
  switch configured and online
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user selects "Delete" from "Switch settings" screen
     Then user should receive a "Remove Device" popup
     When user "confirms" the "Deletion on Remove Device" popup
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "confirms" the "Device Excluded" popup
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer" device on dashboard
  
  @DeleteZwaveTimeout @LYDAS-6763
  Scenario: (ZwaveTC32) As a user my I want to delete my zwave switch
  # switch configured and online
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activates" the "dimmer" function key
     Then user should receive a "Device not found" popup
  
  @AllOnZwaveDevices @LYDAS-6317 @LYDAS-5360
  Scenario Outline: (ZwaveTC33) As a user my I can turn on all my zwave devices at once
  # switch and dimmer configured
    Given user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    Given user turns <DimmerStatus> the "Dimmer" through the "Z-Wave device function key"
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "All ON" from "Z-Wave Utilities" screen
     Then user should see the "Dimmer" status as <SwitchExpectedState> on the "Z-Wave Utilities"
     Then user should see the "Switch" status as <DimmerExpectedState> on the "Z-Wave Utilities"
  
    Examples: 
      | SwitchStatus | DimmerStatus | SwitchExpectedState | DimmerExpectedState | 
      | Off          | Off          | On                  | On                  | 
      | Off          | On           | On                  | On                  | 
      | On           | Off          | On                  | On                  | 
      | Offline      | On           | Offline             | On                  | 
      | On           | Offline      | On                  | Offline             | 
  
  @AllOnZwaveUnknownDevices  @LYDAS-5360
  Scenario Outline: (ZwaveTC34) As a user my I can turn on all my zwave devices at once
  # switch and dimmer configured
    Given user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    Given user turns <UnknownStatus> the "Unknown" through the "Z-Wave device function key"
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "All ON" from "Z-Wave Utilities" screen
     Then user should see the "Dimmer" status as <SwitchExpectedState> on the "Z-Wave Utilities"
     Then user should see the "Switch" status as <UnknownExpectedState> on the "Z-Wave Utilities"
  
    Examples: 
      | SwitchStatus | UnknownStatus | SwitchExpectedState | UnknownExpectedState | 
      | Off          | Off           | On                  | On                   | 
      | Off          | On            | On                  | On                   | 
      | On           | Off           | On                  | On                   | 
      | Offline      | On            | Offline             | On                   | 
      | On           | Offline       | On                  | Offline              | 
  
  @AllOffZwaveDevices
  Scenario Outline: (ZwaveTC35) As a user my I can turn off all my zwave devices at once
  # switch and dimmer configured
    Given user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    Given user turns <DimmerStatus> the "Dimmer" through the "Z-Wave device function key"
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "All OFF" from "Z-Wave Utilities" screen
     Then user should see the "Dimmer" status as <ExpectedState> on the "Z-Wave Utilities"
     Then user should see the "Switch" status as <ExpectedState> on the "Z-Wave Utilities"
  
    Examples: 
      | SwitchStatus | DimmerStatus | ExpectedState | 
      | Off          | Off          | Off           | 
      | Off          | On           | Off           | 
      | On           | Off          | Off           | 
      | On           | On           | Off           | 
  
  @FixAllZwaveDevicesWhenAvailable
  Scenario: (ZwaveTC36) As a user my I can fiz all my zwave devices at once so that active devices will remain in app
  # switch was offline and available, dimmer was offline and unavailable
     When user "disconnects" the "Dimmer" function key
     When user "connects" the "Switch" function key
     When user "disconnects" the "Switch" function key
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "Fix all" from "Z-Wave Utilities" screen
     Then user should see the "Switch" status as "On" on the "Z-Wave Utilities"
     Then user should not see the "Dimmer" on the "Z-Wave Utilities"
  
  @FixAllZwaveDevicesWhenUnAvailable
  Scenario: (ZwaveTC36) As a user my I can fiz all my zwave devices at once so that active devices will remain in app
  # switch was online and unavailable, dimmer was offline and unavailable
     When user "disconnects" the "Dimmer" function key
     When user "disconnects" the "Switch" function key
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "Fix all" from "Z-Wave Utilities" screen
     Then user should not see the "Switch" on the "Z-Wave Utilities"
     Then user should not see the "Dimmer" on the "Z-Wave Utilities"
  
  @ChangeDimmerIntensity @LYDAS-5377
  Scenario: (ZwaveTC37) As a user I want to change the intensity my dimmer
      And user launches and logs in to the Lyric application 
     When user navigates to "Dimmer primary card" screen from the "Dashboard" screen
      And user changes the intensity of the dimmer to "~15%"
     Then user should be displayed with "~15%" on the "application"
      And user should be displayed with "~15%" on the "Z-Wave device"
     When user changes the intensity of the dimmer to "~40%"
     Then user should be displayed with "~40%" on the "application"
      And user should be displayed with "~40%" on the "Z-Wave device"
     When user changes the intensity of the dimmer to "~65%"
     Then user should be displayed with "~65%" on the "application"
      And user should be displayed with "~65%" on the "Z-Wave device"
  
  @ViewZWaveControllerDetails  @LYDAS-5931/LYDAS-5535
  Scenario: (ZwaveTC38) As a user my I should be shown with zwave controller details in app
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Contoller details" screen from the "Dashboard" screen
     Then user should be displayed with the "Z-Wave Controller info" screen
  #details of controller with Type, Version, Home ID, Product ID, Product Type, Manufacturer, Security
  
  @FactoryResetZWaveController  @LYDAS-5378
  Scenario: (ZwaveTC39) As a user my I can reset my zwave controller through factory reset zwave controller
  # switch and dimmer configured
    Given user is set to "Home" mode through CHIL 
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     Then user should receive a "controller reset confirmation" popup
     When user "cancels" the "controller reset" popup
     Then user should be displayed with the "Z-Wave utilities" screen
     When user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     When user "confirms" the "controller reset" popup
     Then user should receive a "factory reset successful" popup
      And user "confirms" the "factory reset successful" popup
     Then user should not see the "Switch" on the "Z-Wave Utilities"
     Then user should not see the "Dimmer" on the "Z-Wave Utilities"
  
  @FactoryResetZWaveControllerOnDiffModes @LYDAS-6081 
  Scenario Outline: (ZwaveTC40) As a user my I can reset my zwave controller through factory reset zwave controller
  # switch and dimmer configured
    Given user is set to <Command> mode through CHIL 
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     When user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     Then user should receive a "controller reset error" popup
    Examples: 
      | Command           | 
      | Away              | 
      | Night             | 
      | Off               | 
      | Alarm             | 
      | Sensor Enrollment | 
  
  @PanelOfflineWithNoZwaveDevices
  Scenario: (ZwaveTC41) As a user my I can not configure my zwave devices when das panel is offline or in programming mode
  #when das panel is offline or in programming mode
    Given user launches and logs in to the Lyric application
     When user navigates to "Add new device(dashboard)" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device"
  
  @PanelOfflineWithZwaveDevices @LYDAS-5416/LYDAS-5415
  Scenario: (ZwaveTC42) As a user my I should be shown offline zwave devices when das panel is offline
    Given user launches and logs in to the Lyric application
  #when das panel is offline
     Then user should be displayed with the "Switch offline" on the "Dashboard"
     Then user should be displayed with the "Dimmer offline" on the "Dashboard"
     When user navigates to "Global drawer" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device" 
  #when das panel is Online
     Then user should be displayed with the "Switch online" on the "Dashboard"
     Then user should be displayed with the "Dimmer online" on the "Dashboard"
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #when das panel is offline
     When user selects "Cancel" from "Activate Z-Wave Device" screen
     Then user should be displayed with the "Add new device" screen
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
  #when das panel is Online
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
  #when das panel is offline
     When user selects "Cancel" from "Exclusion Mode Active" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
  #when das panel is Online
     When user navigates to "Switch Settings" screen from the "Dashboard" screen
      And user edits the "Switch1" name to "Switch2"
  #when das panel is offline
  #Able to come out of Naming screen
  #when das panel is offline
  #not be allowed to perform Factory reset 
  #when das panel is Online
     When user "confirms" the "Factory Reset on Utilities" screen
  #when das panel is offline
  #shown with failed or timed out message
  #when das panel is offline
  #not shown with firmware controller info
  
  @CancelFunctionOnGeneralUtilities
  Scenario: (ZwaveTC43) As a user my I can cancel at any point of time from any screen
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user selects "Cancel" from "Exclusion Mode Active" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Z-Wave device(General Inclusion)" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user selects "Cancel" from "Inclusion Mode Active" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user selects "Cancel" from "Activate Z-Wave Device" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
  
  @AddSecondary @LYDAS-4882
  Scenario: (ZwaveTC44) As a user my I want to include a zwave switch through General Inclusion in the application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device(General inclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "AIO" function key
     When user names the "AIO" as "AIO"
     Then user should be displayed with "AIO" device on dashboard
  
  #actionable on any modes
  
  
