package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;
import com.honeywell.lyric.relayutils.RelayUtils;

public class SuiteExecutor {

	public static void main(String[] commandLineArguments) throws Exception {
		//	ZWaveRelayUtils.enrollZwaveSwitch1();
		//	ZWaveRelayUtils.pressButtonOnSwitch1();
		//	RelayUtils.RSIDoorContactSensorTampered();
		//	RelayUtils.RSIDoorContactSensorTamperCleared();
		//	RelayUtils.RSIContactSensorEnroll_Door();
		//	RelayUtils.RSIContactSensorEnroll_Window();
		/*		RelayUtils.RSIContactSensorOpen_Window();
		RelayUtils.RSIContactSensorClosed_Window();
			RelayUtils.RSIContactSensorOpen_Door();
		RelayUtils.RSIContactSensorClosed_Door();
		if(true)
		return ;
		 */
		commandLineArguments = new String[] {
				// "--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
				"--appToInstall", "Android:400000043,IOS:43", 
				"--groups", 
				"DASAccessSensorStatusVerification",
				"DASAccessSensorRenameVerification",
				"DASAccessSensorRenamePopUpVerification",
		    	"DASAccessSensorSignalStrengthAndTestVerificationWithAwayNightOffline",
		    	"DASDeleteAccessSensorPopupVerification",
		    	"DASAccessSensorSignalStrengthAndTestVerificationGetAdditionlHelp"
				//"LoginToApplication"
				//"EntryDelayScreenValidation",
				//"AlarmScreenValidation"
				//"Alarm_History"

				//PASS 
				/*
				"Doorsensor_NoAlarmInAwayExitDelay",
				"Doorsensor_OpenAfterAwayExitDelay_SwitchingToHomeFromPushNotification_withDoorOpen", //discuss
				"Doorsensor_OpenAfterAwayExitDelay_SwitchingToHomeFromEntryDelay_withDoorClosed",
				"Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm",
				"Doorsensor_OpenAfterAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInWaiting_NoAlarm",
				"Doorsensor_OpenAfterAwayExitDelay_AttentionFromEn@tryDelay", //fix needed for alarm history 
				"Doorsensor_OpenAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay",//discuss
				"Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToHomeFromEntryDelay",
				"Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromEntryDelay_DoorNotClosedInWaiting_Alarm",
				"Doorsensor_LeftOpenDuringAwayExitDelay_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_LeftOpenDuringAwayExitDelay_AttentionFromEntryDelay", // close alarm history
				"Doorsensor_LeftOpenDuringAwayExitDelay_AlarmWhenNoActionOnEntryDelay",
				"DoorSensor_TamperDuringAwayModeExitDelay",
				"DoorSensor_TamperDuringNightModeExitDelay",
				"DoorSensor_TamperAfterAwayModeExitDelay",
				"DoorSensor_TamperAfterNightModeExitDelay",
				"WindowSensor_TamperDuringAwayModeExitDelay",
				"WindowSensor_TamperDuringNightModeExitDelay",
				"WindowSensor_TamperAfterAwayModeExitDelay",
				"WindowSensor_TamperAfterNightModeExitDelay"
				 */
				//"WindowSensor_OpenDuringAwayModeExitDelay"
				//"WindowSensor_OpenAfterAwayModeExitDelay"

				/*		 /*a
				"GeneralExcludeZwaveSwitch",
				"DimmerExcludeFromAddDevice",
				"AddNewDeviceInclusionNoDeviceFound",
				"AddNewDeviceIncludeZwaveSwitch",
				"DeviceFoundAfterExclude",
				"ToggleZwaveSwitchThroughPrimaryCard",
				"StatusChangeOfSwitchFromSettings",
				"OfflineZwaveSwitchFromSettings",
				"ZwaveSwitchRename",
				"GeneralExcludeSwitchOnNoDeviceFound",
				"DeviceIncludedOnRetry",
				"ReIncludeZwaveSwitch",
				"DeleteZwaveSwitchFromSettings",


				"AddNewDeviceIncludeZwaveDimmer",
				"StatusChangeOfDimmerFromSettings",
				"ChangeDimmerIntensity",
				"OfflineZwaveDimmerFromSettings",
				"ZwaveDimmerRename",
				"DeleteZwaveDimmerFromSettings",
				 */
				/*
				"ReplaceZwaveSwitchWithDimmer",
				"ReplaceZwaveDimmerWithSwitch",
				"GeneralIncludeZwaveSwitch",
				"GeneralIncludeExcludeZwaveDimmer",
				"AllOnZwaveDevices",
				"AllOffZwaveDevices",
				"FixAllZwaveDevicesWhenAvailable",
				"ViewZWaveControllerDetails",
				"CancelFunctionOnGeneralUtilities",
			//	"FactoryResetZWaveControllerOnDiffModes",
				"FactoryResetZWaveController"
				/*
				//"ReplaceZwaveSwitchWithSwitch",
				//"ReplaceZwaveDimmerWithDimmer",
				 */

		};

		// ============= Executes the Suite created from the Suite Configuration
		// JSON file =============
		try {
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
