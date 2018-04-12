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

		String appName="";
		if(commandLineArguments.length>1){
			appName=commandLineArguments[1];
		}

		if(commandLineArguments[0].equalsIgnoreCase("smokeTestIOS")){
			commandLineArguments = new String[] {
					"--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
					"--useXCUITest","true",
					"--publishResult",
					"--requirementFileName","Requirement_file_IOS.txt",
					"--testrunname","Smoketest-"+appName+"",
					"--appToInstall","IOS:"+appName+",Android:"+appName+"",
					//"--testrunname","Smoketest-Granite_IOS",
					//"--appToInstall","IOS:1,Android:1",
					"--groups",
					"EditNAGeofencescheduleWithSleepsettings"
			};
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}



		//commandLineArguments = new String[] { "--updatekeywordcatalog" };

		// ============= Executes the Suite created from the Suite Configuration JSON file  =============
		/*try {
            SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
            suiteUtils.executeSuite();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

	}

}
