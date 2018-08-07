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
					"--testrunname","DLS_EMEAScheduling-"+appName+"",
					"--appToInstall","IOS:1,Android:"+appName+"",
					"--groups",

//					/**Jasper NA-DashbboardandSolutionCard**/
//					"ViewDashboard",
//					"ViewDashboardOFF",
//					"ViewSolutionCard",
//					"ViewSolutionCardOFF",
//					"SystemModeInfoscreenwithCoolandHeatMode",
//					"SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled",

//					/**Jasper NA-Scheduling**/
//					"NA_CreateGeofenceSchedule",
//					"NA_SleepSettings",
//					"NA_EditGeofenceWithTemperature",
//					"NA_EditGeofenceSetpointsWhenAutoChnageOverEnabled",
//					"NA_GeofenceScheduleOptions",
//					"NA_CreateGeofenceScheduleInOffMode",
//					"NA_CreateGeonceScheduleInLearnMode",
//					"NA_CreateGeofenceWithEditingHome_Sleep_AwaySettings",
//					"JasperNA_CreateNAScheduleSinglestatwithDefaultvalue",
//					"JasperNA_CancelToRetainExisitngscheduling",
//					"JasperNA_ConfirmToCreateNewSchedule",
//					"JasperNA_TempretureBandwidthforEachPeriod",
//					"JasperNA_DeletingDefaultPeriodSameEveryDay",
//					"JasperNA_DeletingDefaultPeriodDifferentOnWeekdays",
//					"JasperNA_CreateTimeBasedScheduleInOffMode",
//					"NA_GroupEditedDays",
//					"NA_EditTempratureInSchedule",
//					"NA_PeriodTimeShouldNotOverlap",
//					"NA_DeletePerioConfirmdAlert",
//					"NA_DeletingDefaultPeriodDifferentOnWeekdays",
//					"NA_DeletingAllPeriod",
//					"NA_ScheduleOptions",
//					"NA_ResumeSchedule",

//					/**Jasper NA - Settings**/
//					"VerifyJasperNASettings",
//					//"VerifyJasperNAEMEASpruceSettings",
//					//"VerifyHBBSettings",
//					"EnableDisableIndoorTemperatureAlert",

//					/** NA Schedule ON/OFF**/
//					"ScheduleOFFONNA",
//					"ScheduleOFFONNAtimebase",
//					"ScheduleOFFVacationNA",
//					"ScheduleONOFFNAVacationNA",
//					"ScheduleONOFFNAgeofencebase",

//					/**Jasper EMEA-Dashboard&Solutioncard**/
//					"ViewDashboardEMEA",
//					"ViewSolutionCardEMEA",
//					"SystemModeInfoscreenwithHeatOnlyEMEA",

					/**Jasper EMEA-Scheduling**/
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CancelToRetainExisitngscheduling",
					"JasperEMEA_ConfirmToCreateNewSchedule",
					"JasperEMEA_TempretureBandwidthforEachPeriod",
					"JasperEMEA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JasperEMEA_CanCreateMaximumOfSixPeriods",
					"JasperEMEA_CreateTimeBasedScheduleInOffMode",
					"JasperEMEA_CopyScheduleToMulitpleStat",
					"EMEA_CreateGeofenceSchedule",
					"EMEA_CreateNewGeofenceSchedulewithExistingTimeBasedSchedule",
					"EMEA_SleepSettings",
					"EMEA_EditGeofenceWithTemperature",
					"EMEA_CreateGeofenceScheduleInOffMode",
					"EMEA_CreateGeonceScheduleInLearnMode",
					"EMEA_CopyGeofenceScheduleToMulitpleStat",
					"EMEA_AtleastOnePeriodNotDeletableInGroupDay",
					"EMEA_AtleastTwoPeriodInIndividualDay",


//					/** EMEA Schedule ON/OFF**/
//					"ScheduleOFFONEMEA",
//					"ScheduleOFFONEMEAtimebase",
//					"ScheduleOFFVacationEMEA",
//					"ScheduleOFFONVacationEMEA",
//					"ScheduleONOFFEMEAgeofencebase",
//
//					/** HBB Schedule ON/OFF**/
//					"ScheduleONOFFHB",
//					"ScheduleONOFFHBtimebase",
//					"ScheduleONOFFHBgeofencebase",

					/** Camera **/
//					"CameraSettingsCameraOn",
//					"CameraSettingsManageAlertsDisabled",
//					"CameraSettingsEnableDisableMotionDetection",
//					"VerifyCameraMotionSensitivitySettingsC1",
//					"VerifyCameraMotionSensitivitySettingsC2",
//					"CameraSettingsEnableDisableSoundDetection",
//					"CameraSettingsVerifyNightVisionSettings",
//					"CameraSettingsVerifyVideoQualitySettings",
//					"CameraSettingsEnableDisableCameraMicrophone",

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
					"--useXCUITest", "true",
					"--requirementFileName","Requirement_file_iOS.txt",
					"--testrunname","DLS-"+appName+"",
					"--appToInstall","IOS:"+appName+",Android:1",
					"--groups",

					"ViewDashboard",
					"ViewDashboardOFF",
					"ViewSolutionCard",
					"ViewSolutionCardOFF",
					"SystemModeInfoscreenwithCoolandHeatMode",
					"SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled",
					
					/**Jasper EMEA-Scheduling**/
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CancelToRetainExisitngscheduling",
					"JasperEMEA_ConfirmToCreateNewSchedule",
					"JasperEMEA_TempretureBandwidthforEachPeriod",
					"JasperEMEA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JasperEMEA_CanCreateMaximumOfSixPeriods",
					"JasperEMEA_CreateTimeBasedScheduleInOffMode",
					"JasperEMEA_CopyScheduleToMulitpleStat",
					"EMEA_CreateGeofenceSchedule",
					"EMEA_CreateNewGeofenceSchedulewithExistingTimeBasedSchedule",
					"EMEA_SleepSettings",
					"EMEA_EditGeofenceWithTemperature",
					"EMEA_CreateGeofenceScheduleInOffMode",
					"EMEA_CreateGeonceScheduleInLearnMode",
					"EMEA_CopyGeofenceScheduleToMulitpleStat",
					"EMEA_AtleastOnePeriodNotDeletableInGroupDay",
					"EMEA_AtleastTwoPeriodInIndividualDay",
					
//					/** Camera **/
//					"CameraSettingsCameraOn",
//					"CameraSettingsManageAlertsDisabled",
//					"CameraSettingsEnableDisableMotionDetection",
//					"VerifyCameraMotionSensitivitySettingsC1",
//					"VerifyCameraMotionSensitivitySettingsC2",
//					"CameraSettingsEnableDisableSoundDetection",
//					"CameraSettingsVerifyNightVisionSettings",
//					"CameraSettingsVerifyVideoQualitySettings",
//					"CameraSettingsEnableDisableCameraMicrophone",
					
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