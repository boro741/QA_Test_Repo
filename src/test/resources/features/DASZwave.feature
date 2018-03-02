@DASZWAVE
Feature: DAS ZWAVE
As a user I want to control all devices using ZWave technology

@NoZwaveOption @IOSCOMPLETED
  Scenario: (ZwaveTC_A) As a user i should not be shown with Zwave options when there is no DAS panel available in the location
    Given user launches and logs in to the Lyric application 
     When user navigates to "Add new device dashboard" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device from Add device list" 
     When user navigates to "Global drawer" screen from the "Add new device dashboard" screen
     Then user should not be able to configure "Z-Wave Device from Global drawer menu"
     When user navigates to "Add new device global drawer" screen from the "Global drawer" screen
     Then user should not be able to configure "Z-Wave Device from Add device list" 
  
  @ZwaveOptionfromultipleDAS
  Scenario: (ZwaveTC_B) As a user i should be listed with available DAS panel(more than 1) in that location to add Zwave devices
  #DAS1 panel online and DAS2 panel offline 
    Given user launches and logs in to the Lyric application 
     When user navigates to "Add new device(dashboard)" screen from the "Dashboard" screen
     Then user should be able to configure "Z-Wave Device" 
     When user navigates to "Global drawer" screen from the "Add new device(dashboard)" screen
     Then user should be displayed with the "Z-Wave Device" option
     When user navigates to "Add new device(global drawer)" screen from the "Global drawer" screen
     Then user should be able to configure "Z-Wave Device"
     When user selects "Z-Wave Device" from "install device" screen
     Then user should be displayed with the "DAS panel list on Zwave" screen
      And user should be displayed with the "DAS1 online on Zwave" screen
      And user should be displayed with the "DAS1 offline on Zwave" screen
  
  @AddNewDeviceInclusionNoDeviceFound @Automated
  Scenario: (ZwaveTC_C) As a user I should be displayed no device popup when there is no zwave device available to add 
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
     When user selects "Z-Wave Device" from "install device" screen
      And user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Retries the inclusion on" the "Inclusion Device not found" popup
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #timeout
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Tries Exclusion on" the "Inclusion Device not found" popup 
  #timeout
      And user "does not activate" the "switch" function key
     Then user should be displayed with the "Add new device" screen
  
  @DeviceIncludedOnRetry @Automated @LYDAS-6172
  Scenario: (ZwaveTC_D) As a user I should be able to retry the zwave inclusion from No device found popup
    Given user launches and logs in to the Lyric application 
      And user has no "Switch1"
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "does not activate" the "switch" function key
     #timeout
     Then user should receive a "Inclusion Device not found" popup
     When user "Retries the inclusion on" the "Inclusion Device not found" popup
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
      And user names the "switch" to "Switch1"
  #null, string length with 14, only alpha numeric char}
     Then user should be displayed with "Switch1" device on dashboard
  
  @DeviceFoundAfterExclude @Automated 
  Scenario: (ZwaveTC_F) As a user I can exclude my zwave device from No device found popup and can include
  #switch already configured
    Given user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
  #timeout
     When user "does not activate" the "switch" function key
     Then user should receive a "Inclusion Device not found" popup
     When user "Tries Exclusion on" the "Inclusion Device not found" popup 
  #timeout
      And user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "Adds device NOW" the "Device Excluded" popup 
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
      And user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
  
  @AddNewDeviceIncludeZwaveSwitch @Automated @LYDAS-5209 @LYDAS-6587
  Scenario: (ZwaveTC_G) As a user I want to include a zwave switch through the Add new device flow in application
    Given user launches and logs in to the Lyric application 
      And user has no "switch1"
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
      And user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
  # Then user receives a "Zwave device added" activity log
  
  @ToggleZwaveSwitchThroughPrimaryCard @Automated @LYDAS-4594
  Scenario: (ZwaveTC_H) As a user I should be able to control my zwave switch to different states from primary card screen
      And user launches and logs in to the Lyric application
    Given user turns "off" the "Switch" through the "Z-Wave device function key"
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen
     Then user should see the "Switch" status as "off" on the "Dashboard"
  #action on Primary card - verify pc, dashboard and device
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen
      And user turns "on" the "Switch" through the "Switch Primary card"
     Then user should see the "Switch" status as "on" on the "Switch Primary card"
  #  Then user should see the "Timer" status as "Current time" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen
  #  Then user should see the "Switch" status as "on" on the "Dashboard"
      And user should see the "Switch" status as "on" on the "Z-Wave device"
  #action on Primary card - verify pc, dashboard and device
     When user navigates to "Switch Primary card" screen from the "Dashboard" screen
      And user turns "off" the "Switch" through the "Switch Primary card"
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
      And user should see the "Switch" status as "off" on the "Switch Primary card"
  
  @StatusChangeOfSwitchFromSettings @Automated
  Scenario: (ZwaveTC_I) As a user i should be able to control my zwave switch to different states from the settings screen
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user turns "on" the "Switch" through the "Switch settings"
     When user navigates to "Switch Primary card" screen from the "Switch settings" screen 
     Then user should see the "Switch" status as "on" on the "Switch Primary card"
     When user navigates to "Dashboard" screen from the "Switch Primary card" screen 
      And user navigates to "Switch settings" screen from the "Dashboard" screen
      And user turns "off" the "Switch" through the "Switch settings"
      And user navigates to "Switch Primary card" screen from the "Switch settings" screen 
     Then user should see the "Switch" status as "off" on the "Switch Primary card"
  
  @ReIncludeZwaveSwitch @Corrected @LYDAS-5209 @LYDAS-6587
  Scenario: (ZwaveTC_J) As a user i should be able to include a zwave switch through the Add new device flow in application
    Given user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
      And user names the "switch" to "Switch2"
     Then user should be displayed with "Switch2" device on dashboard
  # Then user receives a "Zwave device added" activity log
  
  @ZwaveSwitchRename @Automated @LYDAS-5395
  Scenario: (ZwaveTC_K) As a user I should be able to rename my zwave switch
      And user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user edits the "Switch" name to "Switch2"
      And user navigates to "Dashboard" screen from the "Switch settings" screen
     Then user should be displayed with "Switch2" device on dashboard
      And user reverts back the "Switch name"  through CHIL
  
  
  @DeleteZwaveSwitchFromSettings @DeleteZwaveTimeout @Automated @LYDAS-6763
  Scenario: (ZwaveTC_L) As a user I should be able to delete my zwave switch
  #switch configured and online
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user selects "Delete" from "Switch settings" screen
     Then user should receive a "Remove Device" popup
     When user "confirms" the "Deletion on Remove Device" popup
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activate" the "switch" function key
     Then user should receive a "Device not found" popup
     When user "confirms" the "Device not found" popup
     And user selects "Delete" from "Switch settings" screen
      When user "confirms" the "Device not found" popup
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "confirms" the "Device Excluded" popup
      And user navigates to "Dashboard" screen from the "ZWave DEVICES" screen
     Then user should not be displayed with "Switch1" device on dashboard
  
  @GeneralIncludeZwaveSwitch @Automated @LYDAS-5507
  Scenario: (ZwaveTC_M) As a user I should be able to exclude a zwave switch through General Inclusion in the application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device through General inclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "switch" function key
      And user names the "switch" to "Switch1"
     Then user should be displayed with "Switch1" device on dashboard
     
      @OfflineZwaveSwitchFromSettings @Automated
  Scenario: (ZwaveTC_N) As a user I should be infromed when my switch device goes offline
  # switch configured but offline
    Given user launches and logs in to the Lyric application
    When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
     When user turns "Offline" the "Switch" through the "Z-Wave device function key"
      When user navigates to "Switch settings" screen from the "ZWAVE DEVICES" screen
     Then user should see the "Switch" status as "offline" on the "Switch settings"
     When user navigates to "Zwave devices" screen from the "Switch settings" screen
     Then user should see the "All ON" status as "inactive" on the "Zwave devices"
      And user should see the "All OFF" status as "inactive" on the "Zwave devices"
      And user should see the "Fix All" status as "active" on the "Zwave devices"
       And user turns "On" the "Switch" through the "Z-Wave device function key"
       And user "fixes all zwave devices" by clicking on "Fix all" button
    #  And user navigates to "Switch Primary card" screen from the "Zwave devices" screen 
    # Then user should see the "Switch" status as "offline" on the "Switch Primary card"
      
    
  
  @OnlineZwaveSwitchFromSettings @LYDAS-6370 @Automated
  Scenario: (ZwaveTC_O) As a user I should be infromed when my switch device goes offline
  # switch configured but offline
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user turns "on" the "Switch" through the "Switch settings"
      And user turns "off" the "Switch" through the "Switch settings"
     Then user should see the "Switch" status as "offline" on the "Switch settings"
     When user powers the "switch"
     Then user should see the "Switch" status as "online" on the "Switch settings"
  
  
  @GeneralExcludeZwaveSwitch @Reviewed
  Scenario: (ZwaveTC_P) As a user I should be able to exclude a zwave switch through General exclusion in the application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device through General Exclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
     When user "dismisses" the "Further Exclusion Of Switch Excluded Successfully" popup 
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Switch1" device on dashboard
  
  @GeneralExcludeSwitchOnNoDeviceFound @corrected
  Scenario: (ZwaveTC_Q) As a user I should be able to exclude a zwave switch through the General exclusion in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device through General Exclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activate" the "switch" function key
     Then user should receive a "Exclusion Device not found" popup
     When user "dismisses" the "Exclusion Device not found" popup 
      And user navigates to "Z-Wave device through General Exclusion" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "does not activate" the "switch" function key
     Then user should receive a "Exclusion Device not found" popup
     When user "Retries" the "Exclusion Device not found" popup
      And user "activates" the "switch" function key
     Then user should receive a "Switch Excluded Successfully" popup
      When user "dismisses" the "Further Exclusion Of Switch Excluded Successfully" popup 
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Switch" device on dashboard
  
  @AddNewDeviceIncludeZwaveLock @LYDAS-5002
  Scenario: (ZwaveTC) As a user I should be able to include a zwave lock through the Add new device in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "lock" function key
      And user names the "lock" as "Lock1"
      And user navigates to "Lock settings" screen from the "Z-wave utilies" screen
     Then user should be displayed with the "Lock details" screen #check details
  
  @AddNewDeviceIncludeZwaveDimmer @Corrected
  Scenario: (ZwaveTC_R) As a user I should be able to include a zwave dimmer through the the Add new device in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "dimmer" function key
      And user names the "dimmer" to "Dimmer1"
     Then user should be displayed with "Dimmer1" device on dashboard	
     # And user receives a "Zwave device added" activity log
     
  @GeneralExcludeZwaveDimmer @corrected
  Scenario: (ZwaveTC_S) As a user I should be able to exclude a zwave dimmer through the General exclusion in application
      And user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device through General Exclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "dimmer" function key
     Then user should receive a "Dimmer Excluded Successfully" popup
     When user "dismisses" the "Further Exclusion Of Dimmer Excluded Successfully" popup  
  #   Then user should not be displayed with the "Further Exclusion" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer1" device on dashboard
  
  @StatusChangeOfDimmerFromSettings
  Scenario: (ZwaveTC_T) As a user I should be able to control zwave dimmer to different states through the settings
    Given user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user turns "on" the "Dimmer" through the "Dimmer settings"
     When user navigates to "Dimmer Primary card" screen from the "Dimmer settings" screen 
     Then user should see the "Dimmer" status as "on" on the "Dimmer Primary card"
     When user navigates to "Dashboard" screen from the "Dimmer Primary card" screen 
      And user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user turns "off" the "Dimmer" through the "Dimmer settings"
      And user navigates to "Dimmer Primary card" screen from the "Dimmer settings" screen 
     Then user should see the "Dimmer" status as "off" on the "Dimmer Primary card"
     
     @ChangeDimmerIntensity @LYDAS-5377
  Scenario: (ZwaveTC_T1) As a user I want to change the intensity my dimmer
    Given user launches and logs in to the Lyric application 
     When user navigates to "Dimmer primary card" screen from the "Dashboard" screen
      And user changes the intensity of the dimmer to "~15%"
     Then user should be displayed with "~15%" intensity on the "application"
      And user should be displayed with "~15%" intensity on the "Z-Wave device"
     When user changes the intensity of the dimmer to "~40%"
     Then user should be displayed with "~40%" intensity on the "application"
      And user should be displayed with "~40%" intensity on the "Z-Wave device"
     When user changes the intensity of the dimmer to "~65%"
     Then user should be displayed with "~65%" intensity on the "application"
      And user should be displayed with "~65%" intensity on the "Z-Wave device"
      
     @ZwaveDimmerRename @Reviewed
  Scenario: (ZwaveTC_U) As a user I should be able to rename my zwave dimmer
    Given user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user edits the "Dimmer" name to "Dimmer2"
      And user navigates to "Dashboard" screen from the "Dimmer settings" screen
     Then user should be displayed with "Dimmer2" device on dashboard
     And user reverts back the "Dimmer name" through CHIL
      
      @OfflineZwaveDimmerFromSettings
  Scenario: (ZwaveTC_V) As a user I should be informed when my dimmer device goes offline
  # Dimmer configured but offline
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
      When user turns "Offline" the "Dimmer" through the "Z-Wave device function key"
      When user navigates to "Dimmer settings" screen from the "ZWAVE DEVICES" screen
     Then user should see the "Dimmer" status as "offline" on the "Dimmer settings"
      When user navigates to "Zwave devices" screen from the "Dimmer settings" screen
      And user turns "On" the "Dimmer" through the "Z-Wave device function key"
       And user "fixes all zwave devices" by clicking on "Fix all" button
    #  And user navigates to "Dimmer Primary card" screen from the "Dimmer settings" screen 
    # Then user should see the "Dimmer" status as "offline" on the "Dimmer Primary card"
     
  
  @DeleteZwaveDimmerFromSettings 
  Scenario: (ZwaveTC_W) As a user my I want to delete my zwave switch
  switch configured and online
    Given user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user selects "Delete" from "Dimmer settings" screen
     Then user should receive a "Remove Device" popup
     When user "confirms" the "Deletion on Remove Device" popup
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user "activates" the "Dimmer" function key
     Then user should receive a "Dimmer Excluded Successfully" popup
     When user "confirms" the "Device Excluded" popup
     And user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer" device on dashboard
  
  @AllOnZwaveDevices @LYDAS-6317 @LYDAS-5360
  Scenario Outline: (ZwaveTC_X1) As a user my I can turn on all my zwave devices at once
  # switch and dimmer configured
  Given user turns "On" the "Switch" through the "Z-Wave device function key"
    And user turns "On" the "Dimmer" through the "Z-Wave device function key"
     And user launches and logs in to the Lyric application
    When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
    And user "fixes all zwave devices" by clicking on "Fix all" button
     Then user should see the "Switch" status as "On" on the "ZWAVE DEVICES"
     Then user should see the "Dimmer" status as "On" on the "ZWAVE DEVICES"
    When user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    And user turns <DimmerStatus> the "Dimmer" through the "Z-Wave device function key"
    Then user should see the "Switch" status as <SwitchStatus> on the "ZWAVE DEVICES"
     Then user should see the "Dimmer" status as <DimmerStatus> on the "ZWAVE DEVICES"
     And user selects "All ON" from "Z-Wave devices" screen
     Then user should see the "Dimmer" status as <DimmerExpectedState> on the "ZWAVE DEVICES"
     And user should see the "Switch" status as <SwitchExpectedState> on the "ZWAVE DEVICES"
  
    Examples: 
      | SwitchStatus | DimmerStatus | SwitchExpectedState | DimmerExpectedState | 
      | Off          | Off          | On                  | On                  | 
      | Off          | On           | On                  | On                  | 
      | On           | Off          | On                  | On                  | 
      | Offline      | On           | Offline             | On                  | 
      | On           | Offline      | On                  | Offline             | 
      
  @AllOffZwaveDevices
  Scenario Outline: (ZwaveTC_X2) As a user my I can turn off all my zwave devices at once
  # switch and dimmer configured
   Given user turns "On" the "Switch" through the "Z-Wave device function key"
    And user turns "On" the "Dimmer" through the "Z-Wave device function key"
      And user launches and logs in to the Lyric application
      When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
       And user "fixes all zwave devices" by clicking on "Fix all" button
        Then user should see the "Switch" status as "On" on the "ZWAVE DEVICES"
     Then user should see the "Dimmer" status as "On" on the "ZWAVE DEVICES"
     Given user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    And user turns <DimmerStatus> the "Dimmer" through the "Z-Wave device function key"
    Then user should see the "Switch" status as <SwitchStatus> on the "ZWAVE DEVICES"
     Then user should see the "Dimmer" status as <DimmerStatus> on the "ZWAVE DEVICES"
     And user selects "All OFF" from "Z-Wave devices" screen
     Then user should see the "Dimmer" status as <ExpectedState> on the "ZWAVE DEVICES"
     And user should see the "Switch" status as <ExpectedState> on the "ZWAVE DEVICES"
  
    Examples: 
      | SwitchStatus | DimmerStatus | ExpectedState | 
      | Off          | Off          | Off           | 
      | Off          | On           | Off           | 
      | On           | Off          | Off           | 
      | On           | On           | Off           | 
  
  @AllOnZwaveUnknownDevices  @LYDAS-5360
  Scenario Outline: (ZwaveTC_X3) As a user my I can turn on all my zwave devices at once
  # switch and dimmer configured
    Given user turns <SwitchStatus> the "Switch" through the "Z-Wave device function key"
    And user turns <UnknownStatus> the "Unknown" through the "Z-Wave device function key"
      And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     And user selects "All ON" from "Z-Wave Utilities" screen
     Then user should see the "Dimmer" status as <SwitchExpectedState> on the "Z-Wave Utilities"
     And user should see the "Switch" status as <UnknownExpectedState> on the "Z-Wave Utilities"
  
    Examples: 
      | SwitchStatus | UnknownStatus | SwitchExpectedState | UnknownExpectedState | 
      | Off          | Off           | On                  | On                   | 
      | Off          | On            | On                  | On                   | 
      | On           | Off           | On                  | On                   | 
      | Offline      | On            | Offline             | On                   | 
      | On           | Offline       | On                  | Offline              | 
  
  @FixAllZwaveDevicesWhenAvailable
  Scenario: (ZwaveTC_X4) As a user my I can fiz all my zwave devices at once so that active devices will remain in app
  # switch was offline and available, dimmer was offline and unavailable
  Given user turns "On" the "Switch" through the "Z-Wave device function key"
    And user turns "On" the "Dimmer" through the "Z-Wave device function key"
    And user launches and logs in to the Lyric application
    When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
    When user turns "Offline" the "Switch" through the "Z-Wave device function key"
    And user turns "Offline" the "Dimmer" through the "Z-Wave device function key"
    When user turns "On" the "Switch" through the "Z-Wave device function key"
    And user turns "On" the "Dimmer" through the "Z-Wave device function key"
    And user selects "Fix all" from "Z-Wave devices" screen
    Then user should see the "Switch" status as "Off" on the "ZWAVE DEVICES"
    Then user should see the "Dimmer" status as "On" on the "ZWAVE DEVICES"
  
  @FixAllZwaveDevicesWhenUnAvailable
  Scenario: (ZwaveTC_X5) As a user my I can fiz all my zwave devices at once so that active devices will remain in app
  # switch was online and unavailable, dimmer was offline and unavailable
      Given user turns "On" the "Switch" through the "Z-Wave device function key"
    And user turns "On" the "Dimmer" through the "Z-Wave device function key"
    And user launches and logs in to the Lyric application
    When user navigates to "Z-WAVE DEVICES" screen from the "Dashboard" screen
    When user turns "Offline" the "Switch" through the "Z-Wave device function key"
    And user turns "Offline" the "Dimmer" through the "Z-Wave device function key"
     And user selects "Fix all" from "Z-Wave devices" screen
   #  Then user should not see the "Switch" on the "ZWAVE DEVICES"
   #  And user should not see the "Dimmer" on the "ZWAVE DEVICES"
      When user navigates to "Dashboard" screen from the "ZWAVE DEVICES" screen
      Then user should not be displayed with "Switch" device on dashboard
      Then user should not be displayed with "Dimmer" device on dashboard
  
  @StatusChangeOfUnknownFromSettings
  Scenario: (ZwaveTC116) As a user I should be able to control my zwave unknown device to different states through the settings
    Given user launches and logs in to the Lyric application
     When user navigates to "Unknown settings" screen from the "Dashboard" screen
      And user turns "on" the "Unknown" through the "Unknown settings"
      And user navigates to "Z-Wave Utilities" screen from the "Unknown settings" screen
     Then user should see the "Unknown" status as "on" on the "Z-Wave Utilities"
     When user navigates to "Unknown settings" screen from the "Z-Wave Utilities" screen
      And user turns "off" the "Unknown" through the "Unknown settings"
      And user navigates to "Z-Wave Utilities" screen from the "Unknown settings" screen
     Then user should see the "Unknown" status as "off" on the "Z-Wave Utilities"
     
     @ReplaceZwaveSwitchWithSwitch1
  Scenario: (ZwaveTC26) As a user I should be able to replace with another zwave switch
  #switch with offline
    Given user launches and logs in to the Lyric application 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Switch2" function key
     Then user should receive a "Switch Replaced Successfully" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with "Switch" device on dashboard
  
  @ReplaceZwaveSwitchWithSwitch @LYDAS-5380 @ReplaceZwaveTimeout @LYDAS-6569/LYDAS-5427
  Scenario: (ZwaveTC_R) As a I should be able to replace with another zwave switch
  # switch configured but offline
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user turns "offline" the "Switch" through the "Z-Wave device function key"
      When user navigates to "Switch settings" screen from the "Zwave devices" screen
      And user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Replace Mode Active" screen
     When user "does not activate" the "Switch" function key
     Then user should receive a "Device not found" popup
     When user "confirms" the "Device not found" popup
     And user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Replace Mode Active" screen
     When user "connects" the "Switch power" function key
     When user "activates" the "Switch" function key
     Then user should receive a "Switch Replaced Successfully" popup
      And user should be displayed with the "Z-Wave devices" screen
      When user navigates to "Dashboard" screen from the "Z-Wave devices" screen
     Then user should be displayed with "Switch" device on dashboard
  
  @ReplaceZwaveDimmerWithDimmer
  Scenario: (ZwaveTC_R4)  As a user I should be able to replace my offline dimmer with a another dimmer
  #dimmer with offline
     Given user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user turns "offline" the "Dimmer" through the "Z-Wave device function key"
      When user navigates to "Dimmer settings" screen from the "Zwave devices" screen
      And user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Replace Mode Active" screen
     When user "connects" the "Dimmer power" function key
     When user "activates" the "Dimmer" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
       And user should be displayed with the "Z-Wave DEVICES" screen
     When user navigates to "Dashboard" screen from the "ZWave devices" screen
     Then user should be displayed with "Dimmer" device on "dashboard" screen
  
  @ReplaceZwaveSwitchWithDimmer
  Scenario: (ZwaveTC_R2) As a user I should be able to replace my offline switch with a zwave dimmer
  #switch with offline
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user turns "offline" the "Switch" through the "Z-Wave device function key"
      When user navigates to "Switch settings" screen from the "Zwave devices" screen
      And user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Replace Mode Active" screen
     When user "connects" the "Dimmer power" function key
     When user "activates" the "Dimmer" function key
     Then user should receive a "Switch Replaced Successfully" popup
      And user should be displayed with the "Z-Wave DEVICES" screen
     When user navigates to "Dashboard" screen from the "ZWave devices" screen
     Then user should be displayed with "Switch" device on "dashboard" screen
  # check in settings that its a dimmer
  
   @ReplaceZwaveDimmerWithSwitch @LYDAS-5380
  Scenario: (ZwaveTC_R3) As a user I should be able to replace my offline dimmer with a zwave switch
  #dimmer with offline
     Given user launches and logs in to the Lyric application
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user turns "offline" the "Dimmer" through the "Z-Wave device function key"
      When user navigates to "Dimmer settings" screen from the "Zwave devices" screen
      And user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Replace Mode Active" screen
     When user "connects" the "Switch power" function key
     When user "activates" the "Switch" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
      And user should be displayed with the "Z-Wave DEVICES" screen
     When user navigates to "Dashboard" screen from the "ZWave devices" screen
     Then user should be displayed with "Dimmer" device on "dashboard" screen
  # check in settings that its a switch
  
  @ReplaceZwaveSwitchWithUnknown @LYDAS-5380
  Scenario: (ZwaveTC27) As a user I should be able to replace my offline switch with unknown zwave devices
  #switch with offline
    Given user launches and logs in to the Lyric application 
     When user navigates to "Switch settings" screen from the "Dashboard" screen
      And user selects "Replace" from "Switch settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Unknown" function key
     Then user should receive a "Switch Replaced Successfully" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Known" device on dashboard
  
  @ReplaceZwaveDimmerWithUnknown
  Scenario: (ZwaveTC28) As a user I should be able to replace my offline Dimmer with unknown zwave devices
  #dimmer with offline
    Given user launches and logs in to the Lyric application 
     When user navigates to "Dimmer settings" screen from the "Dashboard" screen
      And user selects "Replace" from "Dimmer settings" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the "Unknown" function key
     Then user should receive a "Dimmer Replaced Successfully" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should not be displayed with "Dimmer" device on dashboard
  
  @ReplaceZwaveUnKnownWithknown @LYDAS-6966/LYDAS-6964/LYDAS-5380
  Scenario Outline: (ZwaveTC29) As a user I should be able to replace my offline unknown device with known zwave device through the application
  #Water value with offline
    Given user launches and logs in to the Lyric application 
     Then user should not be displayed with "UnKnown" device on dashboard
     When user navigates to <Individual settings> screen from the "Dashboard" screen
      And user selects "Replace" from "Z-Wave Utilities" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user "activates" the <Device To Activate> function key
     Then user should receive a "Device Replaced Successfully" popup
      And user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with <Expected device> device on dashboard 
  
    Examples: 
      | Individual settings  | Device To Activate | Expected device | 
      | Water value settings | switch             | Water value     | 
  
  @RestoreSwitchFromOfflineState
  Scenario: (ZwaveTC30) As a user I should be able to control my zwave switch to different states through the application
  # switch configured but offline
    Given user launches and logs in to the Lyric application
     When user navigates to "Switch settings" screen from the "Dashboard" screen
     Then user should see the "Switch" status as "offline" on the "Switch settings"
  #made online
     When user "powers" the "Dimmer" function key
      And user turns "on" the "Switch" through the "Switch settings"
      And user turns "off" the "Switch" through the "Switch settings"
     When user navigates to "Dashboard" screen from the "Switch settings" screen 
      And user navigates to "Switch Primary Card" screen from the "Dashboard" screen 
     Then user should see the "Dimmer" status as "Off" on the ""Switch Primary Card"
  
  @ViewZWaveControllerDetails  @LYDAS-5931/LYDAS-5535 @Automated
  Scenario: (ZwaveTC_Y1) As a user my I should be shown with zwave controller details in app
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Controller details" screen from the "Dashboard" screen
     Then user should be displayed with the "Z-Wave Controller info" screen
  #details of controller with Type, Version, Home ID, Product ID, Product Type, Manufacturer, Security
  
  @FactoryResetZWaveController  @LYDAS-5378
  Scenario: (ZwaveTC_Y2) As a user my I can reset my zwave controller through factory reset zwave controller
  # switch and dimmer configured
    Given user is set to "Home" mode through CHIL 
    And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     And user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     Then user should receive a "controller reset confirmation" popup
     When user "cancels" the "controller reset" popup
     Then user should be displayed with the "Z-Wave utilities" screen
     When user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     And user "confirms" the "controller reset" popup
     Then user should receive a "factory reset successful" popup
      When user "confirms" the "factory reset successful" popup
      When user navigates to "ZWave devices" screen from the "Z-Wave Utilities" screen
  #   Then user should not see the "Switch" on the "Z-Wave Utilities"
  #   And user should not see the "Dimmer" on the "Z-Wave Utilities"
  
  @FactoryResetZWaveControllerOnDiffModes @LYDAS-6081 
  Scenario Outline: (ZwaveTC_Y3) As a user my I can reset my zwave controller through factory reset zwave controller
  # switch and dimmer configured
    Given user is set to <Command> mode through CHIL 
    And user launches and logs in to the Lyric application
     When user navigates to "Z-Wave Utilities" screen from the "Dashboard" screen
     And user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     And user "confirms" the "controller reset" popup
     Then user should receive a "controller reset error" popup
    Examples: 
      | Command           | 
      | Away              | 
      | Night             | 
      | Off               | 
   #   | Alarm             | 
   #   | Sensor Enrollment | 
  
  @PanelOfflineWithNoZwaveDevices @NotAutomatable
  Scenario: (ZwaveTC41) As a user my I can not configure my zwave devices when das panel is offline or in programming mode
  #when das panel is offline or in programming mode
    Given user launches and logs in to the Lyric application
     When user navigates to "Add new device(dashboard)" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device"
  
  @PanelOfflineWithZwaveDevices @LYDAS-5416/LYDAS-5415 @NotAutomatable
  Scenario: (ZwaveTC42) As a user my I should be shown offline zwave devices when das panel is offline
    Given user launches and logs in to the Lyric application
  #when das panel is offline
     Then user should be displayed with the "Switch offline" on the "Dashboard"
     And user should be displayed with the "Dimmer offline" on the "Dashboard"
     When user navigates to "Global drawer" screen from the "Dashboard" screen
     Then user should not be able to configure "Z-Wave Device" 
  #when das panel is Online
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
  #not be allowed to perform Factory reset 
  #shown with failed or timed out message
  #not shown with firmware controller info
  
  
  @PanelOnAlarmWithZwaveDevices
  Scenario: (ZwaveTC42) As a user I should be able to perform zwave operation when das panel is alarm state
    Given user launches and logs in to the Lyric application
    And das panel is in alarm state
     When user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user navigates to "Z-Wave device(General Inclusion)" screen from the "Z-Wave device Add new device" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user navigates to "Z-Wave device(General Exclusion)" screen from the "Z-Wave device(General Inclusion)" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user selects "Cancel" from "Exclusion Mode Active" screen
     When user selects "Controller Factory Reset" from "Z-Wave Utilities" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
     Then user should receive a "controller reset confirmation" popup
     When user "cancels" the "controller reset" popup
      When user navigates to "Z-Wave Contoller details" screen from the "Dashboard" screen
     Then user should be displayed with the "Z-Wave Controller info" screen
     When user navigates to "Switch Settings" screen from the "Z-Wave Utilities" screen
     And user edits the "Switch1" name to "Switch2"
     When user selects "delete" from "Switch Settings" screen
     Then user should receive a "Remove Device" popup
     When user "cancels" the "Deletion on Remove Device" popup
     Then user should be displayed with the "Switch Settings" screen
      When user selects "replace" from "Switch Settings" screen
      Then user should receive a "Replace Device" popup
     When user selects "Cancel" from "Inclusion Mode Active" screen
      When user navigates to "Z-Wave Utilities" screen from the "Switch Settings" screen
     When user selects "Fix all" from "Z-Wave Utilities" screen
      Then user should be displayed with the "Fixing in progress" screen
     When user selects "All on" from "Z-Wave Utilities" screen
     Then user should be displayed with the "in progress" screen
     When user selects "All off" from "Z-Wave Utilities" screen
   Then user should be displayed with the "in progress" screen
   
  @CancelFunctionOnGeneralUtilities @Automated
  Scenario: (ZwaveTC_Z) As a user my I can cancel at any point of time from any screen
    Given user launches and logs in to the Lyric application
     When user navigates to "Z-Wave device through General Exclusion" screen from the "Dashboard" screen
     Then user should be displayed with the "Exclusion Mode Active" screen
     When user selects "CONFIRM CANCEL" from "Exclusion Mode Active" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Z-Wave device through General Inclusion" screen from the "Z-Wave Utilities" screen
     Then user should be displayed with the "Inclusion Mode Active" screen
     When user selects "CONFIRM CANCEL" from "Inclusion Mode Active" screen
     Then user should be displayed with the "Z-Wave Utilities" screen
     When user navigates to "Dashboard" screen from the "Z-Wave Utilities" screen
     And user navigates to "Z-Wave device Add new device" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user selects "CONFIRM CANCEL" from "Activate Z-Wave Device" screen
     Then user should be displayed with the "Add new device" screen
  
  @AddSecondary @LYDAS-4882 @NotAutomatable
  Scenario: (ZwaveTC44) As a user my I want to include a zwave switch through General Inclusion in the application
      Given user launches and logs in to the Lyric application 
     When user navigates to "Z-Wave device(General inclusion)" screen from the "Dashboard" screen
     Then user should be displayed with the "Activate Z-Wave Device" screen
     When user "activates" the "AIO" function key
     And user names the "AIO" as "AIO"
     Then user should be displayed with "AIO" device on dashboard
  
  #actionable on any modes
  
  
