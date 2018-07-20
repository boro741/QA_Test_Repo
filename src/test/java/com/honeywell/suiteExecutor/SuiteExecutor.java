package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

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
				
				"--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				"--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
				"--appToInstall", "Android:Lyric_Develop_Daily_Android_402001109,IOS:4200382", 
				"--groups",
				
				/**********START DAS CAMERA SETTINGS WORKFLOW**********/
				
				
				//"CameraSettingsManageAlertsDisabled",
				//"CameraSettingsEnableDisableMotionDetection",
				//"VerifyCameraMotionSensitivitySettingsC1",
				//"VerifyCameraMotionSensitivitySettingsC2",
				//"CameraSettingsEnableDisableSoundDetection",
				//"CameraSettingsVerifyNightVisionSettings",
				//"CameraSettingsVerifyVideoQualitySettings",
				//"CameraSettingsEnableDisableCameraMicrophone",
				"ViewSolutionCard",         
				//"ViewSolutionCardEMEA",
				
				/**********END CAMERA SETTINGS WORKFLOW**********/

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
