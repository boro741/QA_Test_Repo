package com.resideo.SuiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;
import com.resideo.lumina.relayutils.SerialComm;

public class SuiteExecutor {    
	/**
	 * The Starting point of Automation Framework.
	 *
	 * @param groups
	 *            String[] array type, represents the groups to execute passed
	 *            from Command line Arguments.
	 */
	public static void main(String[] commandLineArguments) throws Exception {
		commandLineArguments = new String[] { 
				//                         "--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
				//				"--publishResult",
				"--useXCUITest", "true", 
				"--jira_credentials",
				"aterbuild:aterbuild@123",
				"--requirementFileName",
				"Requirement_file_Android.txt",
//				"Requirement_file_IOS.txt",
				//				"Requirement_file_DAS_Alarm_IOS.txt",
				//                         "Requirement_file_DASSettings.txt",
				//                         "Requirement_file_DAS_DIY_iOS.txt",
				//                         "Requirement_file_DAS_Sensors_iOS.txt",
				"--appToInstall","IOS:Resideo_Build_1122,Android:Lumina_1169",
				"--groups",
				"HumidityTimeStamp",
//				"currentTemperature",
//				"StatusOfHome"
//			"FahrenheitToCelcius",
//				"WLDDIYRegistrationToExistingLocation",
//				"WLDDIYRegistrationToExistingLocation",
//				"WLDDIYRegistrationToExistingLocation",
//				"WLDDIYRegistrationToExistingLocation",
//								"WLDDIYRegistrationNewaccount",
//								"DisplayWaterLeakDetectorwithin48hrs",
//								"DisplayWaterLeakDetectorafter48hrs",
//				                "EnableTemperatureAlert",
//				                "DisableTemperatureAlert",
//				                 "EnableHumidityAlert",
//				                 "DisableHumidityAlert",
//				                 "WLDSettings",
//								"ChangeTemperatureUnit"					
								
		};
		try {
		//	new SerialComm();
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
