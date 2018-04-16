package com.honeywell.suiteExecutor;

import java.util.HashMap;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.honeywell.commons.coreframework.SuiteUtils;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.lyric.relayutils.RelayUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;

public class SuiteExecutor {
	
	
	public static void main(String[] commandLineArguments) throws Exception {
	
	//	RelayUtils.RSIContactSensorOpen_Window();
		RelayUtils.RSIContactSensorClosed_Window();
	//	RelayUtils.RSIContactSensorOpen_Door();
	//	RelayUtils.RSIContactSensorClosed_Door();
	/*	
		ZWaveRelayUtils.enrollZwaveSwitch1();
		ZWaveRelayUtils.pressButtonOnSwitch1();
		
		ZWaveRelayUtils.enrollZwaveDimmer1();
		ZWaveRelayUtils.pressButtonOnDimmer1();
		
		if(true){
			return;
		}

		*/
		
		commandLineArguments = new String[] {
				// "--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
				"--appToInstall", "Android:400000043,IOS:43", 
				"--groups", 
				
				/*Passed
				"AwayMode_windowsensorOpenDuringExitDelay_OpenAfterExitDelay_and_NightMode_windowsensorOpenDuringExitDelay_OpenAfterExitDelay",
				"AwayMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_ClosedDoorNoAlarm_FromEntryDelayScreen",
				"AwayMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay_AlarmWhenNoActionTaken",
				"NightMode_doorsensorOpenAfterExitDelay_Switchtonight_DoorNotClosedAlarm_ClosedDoorNoAlarm_FromEntryDelayScreen",
				"NightMode_doorsensor_NoAlarmInExitDelay_EntryDelayAfterExitDelay_SwitchingToHome_SwitchToNight",
				"NightMode_doorsensorOpenAfterExitDelay_AttentionAlarmFromEntryDelay_AlarmWhenNoActionTaken",
				
				
				"Doortamper",
				"Windowtamper",
				"ZwaveOperations_Alarm"
				*/
				//"Attention_FromCamera",
				
				"DoorsensorOpenAfterExitDelay_windowopen",
				/*"DoorsensorOpenInExitDelay_windowopen",
				"Motionsensor_Switchtohome_Switchtonight_Attention_Alarm",
				*/
				//"AwayMode_MotionDetectedWindowOpenDoorOpen_WindowOpenMotionDetectedDoorOpen",
				//"NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen",
				//
				
				
				
				 /*
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

		//commandLineArguments = new String[] {"--help"};
		//commandLineArguments = new String[] {"--updateKeywordCatalogue"};
		
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
