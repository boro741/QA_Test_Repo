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

		/**************Android Suite Scenarios*********************/

		if(commandLineArguments[0].equalsIgnoreCase("Android")){
			commandLineArguments = new String[] { "--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
					"--publishResult",
					"--requirementFileName","Requirement_file_ComfortSettings_Android.txt",
					"--testrunname","Smoketest-"+appName+"",
					"--appToInstall","IOS:1,Android:"+appName+"",
					"--groups",

					/**Jasper NA-DashbboardandSolutionCard**/
					"ViewDashboard",
					/*			"ViewDashboardOFF",
    				"ViewSolutionCard",
    				"ViewSolutionCardOFF",
    				"SystemModeInfoscreenwithCoolandHeatMode",
    				"SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled",

					 *//**Jasper NA-Scheduling**//*
    				"JasperNA_CreateNAScheduleSinglestatwithDefaultvalue",
    				"JasperNA_CancelToRetainExisitngscheduling",
    				"JasperNA_ConfirmToCreateNewSchedule",
    				"JasperNA_TempretureBandwidthforEachPeriod",
    				"JasperNA_DeletingDefaultPeriodSameEveryDay",
    				"JasperNA_DeletingDefaultPeriodDifferentOnWeekdays",
    				"JasperNA_CreateTimeBasedScheduleInOffMode",

					  *//**Jasper NA - Settings**//*
    				"VerifyJasperNASettings",
    				"VerifyJasperNAEMEASpruceSettings",
    				"VerifyHBBSettings",
    				"EnableDisableIndoorTemperatureAlert",

					   *//**Jasper EMEA-Dashboard&Solutioncard**//*
    				"ViewDashboardEMEA",
    				"ViewSolutionCardEMEA",
    				"SystemModeInfoscreenwithHeatOnlyEMEA",

					    *//**Jasper EMEA-Scheduling**//*
    				"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
    				"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",*/

			};
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**************END Of Android Suite Scenarios*********************/

		/**************iOS Suite Scenarios*********************/
		if(commandLineArguments[0].equalsIgnoreCase("iOS")){
			commandLineArguments = new String[] { "--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
					"--publishResult",
					"--requirementFileName","Requirement_file_Android.txt",
					"--testrunname","Smoketest-"+appName+"",
					"--appToInstall","IOS:1,Android:"+appName+"",
					"--groups",
			};
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}
			/**************END Of iOS Suite Scenarios*********************/
		}
	}
}