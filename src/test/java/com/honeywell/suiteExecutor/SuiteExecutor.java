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
					"--testrunname","DLS_"+appName+"",
					"--appToInstall","IOS:1,Android:"+appName+"",
					"--groups",

					/**Jasper NA-DashbboardandSolutionCard**/
					"ViewDashboard",
					"ViewDashboardOFF",
					"ViewSolutionCard",
					"ViewSolutionCardOFF",
					"SystemModeInfoscreenwithCoolandHeatMode",
					"SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled",
					"SystemModeInfoscreenwithCoolOnly",
					"SystemModeInfoscreenwithHeatOnly",
					"SystemModeswitchSystemmodescreenwithbothcoolandheatandautoenabledCancelfunctionality",
					"SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionality",
					"SystemModeswitchSystemmodescreenwithcoolonlyCancelfunctionality",
					"SystemModeswitchSAVEfunctionbothcoolandheat",
					"SystemModeswitchSAVEfunctionbothcoolandheatandauto",
					"SystemModeswitchSAVEfunctioncoolonly",
					"SystemModeswitchSAVEfunctionHeatonly",
					"FanOptionInfoOption",
					"FanModeSwitchcancelfunction",
					"FanModeSwitchSAVEfunction",
					"SetTemperatureSolutionCardMAX",
					"SetTemperatureSolutionCardMIN",
					"SetTemperatiureOFFModeNA",
					"DashboardandsolutioncardAutochangeover",
					"DashboardandsolutioncardAutoModeCheckingFromPrimaryCard",
					"DashboardandsolutioncardAutoModeNegative1",
					"DashboardCoachmarkverification",
					"SolutionCardCoachmarkverificationNA",
					"SolutionCardEmergencyHeatbothcoolandheat",
					"SolutionCardEmergencyHeatHeatonly",
					"SolutionCardEmergencyHeatCoolonly",

					/**Jasper NA-Scheduling**/
					/**NA_GeofenceSchedule**/
					"NA_CreateGeofenceSchedule",
					"NA_SleepSettings",
					"NA_EditGeofenceWithTemperature",
					"NA_EditGeofenceSetpointsWhenAutoChnageOverEnabled",
					"JasperNA_GeofenceTimerClockIsInCrementalOf15mins",
					"NA_GeofenceScheduleOptions",
					"NA_CreateGeofenceScheduleInOffMode",
					"NA_CreateGeonceScheduleInLearnMode",
					"NA_CopyScheduleToMulitpleStat",
					"NA_WhenHeat_CoolOnly",
					"NA_CreateGeofenceWithEditingHome_Sleep_AwaySettings",
					/**NATimeBasedScheduling**/
					"JasperNA_CreateNAScheduleSinglestatwithDefaultvalue",
					"JasperNA_CancelToRetainExisitngscheduling",
					"JasperNA_ConfirmToCreateNewSchedule",
					"JasperNA_TempretureBandwidthforEachPeriod",
					"JasperNA_TimerClockIsInCrementalOf15mins",
					"JasperNA_DeletingDefaultPeriodSameEveryDay",
					"JasperNA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JapserNA_CopyScheduleToMulitpleStat",
					"JasperNA_CreateTimeBasedScheduleInOffMode",
					"JasperNA_WhenHeat_CoolOnly",
					"NA_CreateTimeBasedScheduleInOffMode",
					/**EditNATimeschedule**/
					"NA_GroupEditedDays",
					"NA_EditTempratureInSchedule",
					"NA_EditSetpointsWhenAutoChnageOverEnabled",
					"NA_PeriodTimeShouldNotOverlap",
					"NA_DeletePerioConfirmdAlert",
					"NA_DeletingDefaultPeriodDifferentOnWeekdays",
					"NA_DeletingAllPeriod",
					"NA_ScheduleOptions",
					"NA_ResumeSchedule",

					/**Jasper NA - Settings**/
					"VerifyJasperNASettings",
					//"VerifyJasperNAEMEASpruceSettings",
					//"VerifyHBBSettings",
					"EnableDisableIndoorTemperatureAlert",

					/** NA Schedule ON/OFF**/
					"ScheduleOFFONNA",
					"ScheduleOFFONNAtimebase",
					"ScheduleONOFFNAgeofencebase",
					"ScheduleOFFAdhocOverrideNAtimebaseGeofence", 
					"ScheduleONOFFNNAadhocoverrideTimebase", 
					"ScheduleONOFFNAadhocoverrideGeofence",
					"ScheduleOFFVacationNA",
					"ScheduleONOFFNAVacationNA",
					"ScheduleONOFFNAswitchingmodes",
					"ScheduleONOFFNAgeofencebasefencecross",
					"ScheduleONMultistatNA",
					"ScheduleOFFMultistatNA",
					
					/**Adhoc-NA**/
					"AdhocOverrideTimebaseSchedulefollowingfromsolutioncard",
					"AdhocOverrideGeofencebaseScheduleusing",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowing",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule",
					//"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousing",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusing",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing",
					//"AdhocOverrideCreateTimebasescheduleTemporaryHold",
					"AdhocOverrideCreateTimebasescheduleOFFModeNormalflow",
					"AdhocOverrideCreateGeofencebasechedulethroughFollowingSchedule",
					"AdhocOverrideCreateGeofencebasescheduleOFFthroughFollowingSchedule",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCard",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboard",
					"AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideGeofencebaseScheduleTempHoldSolCardSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideGeofencebaseScheduleTemporaryHoldDashboardSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoTemporaryHold",
					"AdhocOverrideTimeSchedulingChangeDifferentModeHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideTimeSchedulingChangeSameModeHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangeModeWithDifferentModesHeatCoolAutoOFFTemporaryHold",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold",
					"AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold",
					"AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHold",
					"AdhocOverrideCreateGeofencebasecheduleTemporaryHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHold",
					"AdhocOverridetimebaseschedulingdeleteALLPERIODSTemporaryHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictime",
					"AdhocOverrideCreateGeofencebasescheduleAspecifictime",
					"AdhocOverrideCreateTimebasescheduleOFFModeAspecifictime",
					//"AdhocOverrideCreateTimebasescheduleAspecifictime",
					"AdhocOverrideTimebaseSchedulespecifictimedeleteallperiods",
					"AdhocOverrideTimebaseSchedulespecifictimeRemoveHold",
					"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHold",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHold",
					"AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHold",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictime",
					"AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatauto",
					"AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint",
					"AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint",
					"AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetNA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityNA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCard",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchange",
					"AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchange",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoPermanentHold",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHold",
					"AdhocOverrideTimebaseSchedulePermanentRemoveHold",
					"AdhocOverridetimebaseschedulingdeleteALLPERIODSPermanentHold",
					"AdhocOverrideCreateTimebaseschedulePermanentHold",
					"AdhocOverrideCreateTimebasescheduleOFFModePermanentHold",
					"AdhocOverrideCreateGeofencebaseschedulePermanentHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFPermanentHold",
					
					/**Vacation-Settings-NA**/
					"Vacations_VerifyStartAndEndDate",
					"Vacations_VerifyGuideMessage",
					"Vacations_DefaultVacationTimeForNA",
					"Vacations_VerifyVacationDefaultSetPoints",
					"Vacations_MinimumBandwidthTimer",
					"Vacation_TimerValueIncreamentOf15NA",
					"Vacation_EditSetPoints",
					//"Vacation_EnableDisbaleIndividualStat",
					//"Vacation_Enable_DisbaleIndividulaStat",
					"Vacation_WhenScheduleEnables",
					"Vacation_EditSetPointsFromPrimaryCard",
					"VacationActiveSwitchingModesNA",
					//"VacationTimebaseSolutionCardAfterVacationEndsNA",
					"VacationGeofenceSolutionCardAfterVacationEndsNA",
					"Vacations_VactionStatusOnDashabord",
					
					/**Jasper EMEA-Dashboard&Solutioncard**/
					"ViewDashboardEMEA",
					"ViewSolutionCardEMEA",
					"SystemModeInfoscreenwithHeatOnlyEMEA",
					"SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionalityEMEA",
					"SystemModeswitchSAVEfunctionEMEA",
					"FanModeOptionONEMEA",
					"SetTemperatureSolutionCardMAXEMEA",
					"SetTemperatureSolutionCardMINEMEA",
					"SolutionCardCoachmarkverificationEMEA",
					"SolutionCardEmergencyHeatHeatonlyEMEA",

					/**Jasper EMEA-Scheduling**/
					/**EMEATimeBasedScheduling**/
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CancelToRetainExisitngscheduling",
					"JasperEMEA_ConfirmToCreateNewSchedule",
					"JasperEMEA_TempretureBandwidthforEachPeriod",
					"JasperEMEA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JasperEMEA_CanCreateMaximumOfSixPeriods",
					"JasperEMEA_CreateTimeBasedScheduleInOffMode",
					"JasperEMEA_CopyScheduleToMulitpleStat",
					/**EMEA_GeofenceSchedule**/
					"EMEA_CreateGeofenceSchedule",
					"EMEA_CreateNewGeofenceSchedulewithExistingTimeBasedSchedule",
					"EMEA_SleepSettings",
					"EMEA_EditGeofenceWithTemperature",
					"JasperEMEA_TimerClockIsInCrementalOf15mins",
					"EMEA_GeofenceScheduleOptions",
					"EMEA_CreateGeofenceScheduleInOffMode",
					"EMEA_CreateGeonceScheduleInLearnMode",
					"EMEA_CopyGeofenceScheduleToMulitpleStat",
					"EMEA_CreateGeofenceWithEditingHome_Sleep_AwaySettings",
					"EMEA_EditGeofenceScheduleSettingsAndBackNavigation",
					/**EditEMEATimeschedule**/
					"EMEA_AtleastOnePeriodNotDeletableInGroupDay",
					"EMEA_AtleastTwoPeriodInIndividualDay",
					"EMEA_EndtimeTimeschedulePeriod",
					"EMEA_EditingEndtime",
					"EMEA_EditingStartTime",
					"EMEA_GroupEditedDays",
					"EMEA_ViewTimescheduleIndividualdaysEMEA", 
					"EMEA_ScheduleOptions",
					"EMEA_ResumeSchedule",


					/** EMEA Schedule ON/OFF**/
					"ScheduleOFFONEMEA",
					"ScheduleOFFONEMEAtimebase",
					"ScheduleONOFFEMEAgeofencebase",
					"ScheduleOFFEMEAtimebasegeofencebase", 
					"ScheduleONFFAdhocOverrideEMEAtimebase",
					"ScheduleOFFONAadhocoverrideEMEAgeofence", 
					"ScheduleOFFVacationEMEA",
					"ScheduleOFFONVacationEMEA",
					"ScheduleONOFFEMEAswitchingmodes",
					"ScheduleONOFFEMEAgeofencebasefencecross", 
					"ScheduleONMultistatEMEA",
					"ScheduleOFFMultistatEMEA",
					
					/** Adhoc-EMEA**/
					"AdhocOverrideTimebaseSchedulefollowingfromsolutioncardEMEA",
					"AdhocOverrideGeofencebaseScheduleusingEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowingEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleEMEA",
					"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA",
					"AdhocOverrideCreateGeofencebasescheduleAspecifictimeEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleduplicateEMEA",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHoldEMEA",
					//"AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboardEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsetpointchangeEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordsetpointEMEA",
					"AdhocOverrideGeofencebaseScheduleTemporaryHoldSolutionCardEMEATemporaryHoldEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatOffTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHoldEMEA",
					"AdhocOverrideCreateGeofencebasecheduleTemporaryHoldEMEA",
					"AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHoldEMEA",
					//"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA",
					"AdhocOverrideScheduletemperatureGeofencebaseSchedulingChangemodeHeatOFFTemporaryHoldEMEA",
					//"AdhocOverrideCreateGeofencebasecheduleTemporaryHold",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodEMEATemporaryHoldEMEA",
					"AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodEMEATemporaryHoldEMEA",
					"AdhocOverridegeofencebaseschedulingremoveholdEMEATemporaryHoldEMEA",
					"AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint_EMEA",
					"AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint_EMEA",
					"AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold_EMEA",
					"AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold_EMEA",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing_EMEA",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold_EMEA",
					//"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA",
					//"AdhocOverrideCreateTimebasescheduleAspecifictime",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleOFFModeAspecifictimeEMEA",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHoldEMEA",
					//"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHoldEMEA",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictimeEMEA",
					"AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatautoEMEA",
					"AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchangeEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchangeEMEA",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHoldEMEA",
					"AdhocOverrideTimebaseSchedulePermanentRemoveHoldEMEA",
					
					/**Vacation-Settings-EMEA**/
					"Vacations_VerifyTimeAndDateBoundaryConditionsForEMEA",
					"Vacation_TimerValueIncreamentOf10EMEA",
					"VacationActiveSwitchingModesEMEA",
					//"VacationTimebaseSolutionCardAfterVacationEndsEMEA",
					"VacationGeofenceSolutionCardAfterVacationEndsEMEA",
					
					/**HBB-Dashboard&Solutioncard**/
					"SetTemperatiureOFFModeHB",

					/** HBB Schedule ON/OFF**/
					"ScheduleONOFFHB",
					"ScheduleONOFFHBtimebase",
					"ScheduleONOFFHBgeofencebase",
					"ScheduleONOFFHBswitchingmodes",
					"ScheduleONOFFHBgeofencebasefencecross",
					"ScheduleONMultistatHB", 
					"ScheduleOFFMultistatHB",
					
					/**Vacation-Settings-HBB**/
					//"Vacations_VerifyHBBStatsNotPresentOnComfortSettings",

//					/** Camera **/
//					/*"CameraSettingsCameraOn",
//					"CameraSettingsManageAlertsDisabled",
//					"CameraSettingsEnableDisableMotionDetection",
//					"VerifyCameraMotionSensitivitySettingsC1",
//					"VerifyCameraMotionSensitivitySettingsC2",
//					"CameraSettingsEnableDisableSoundDetection",
//					"CameraSettingsVerifyNightVisionSettings",
//					"CameraSettingsVerifyVideoQualitySettings",
//					"CameraSettingsEnableDisableCameraMicrophone",*/

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
					"--requirementFileName","Requirement_file_ComfortSettings_iOS",
					"--testrunname","DLS-"+appName+"",
					"--appToInstall","IOS:"+appName+",Android:1",
					"--groups",

					/**Jasper NA-DashbboardandSolutionCard**/
					"ViewDashboard",
					"ViewDashboardOFF",
					"ViewSolutionCard",
					"ViewSolutionCardOFF",
					"SystemModeInfoscreenwithCoolandHeatMode",
					"SystemModeInfoscreenwithCoolandHeatModeWhenautoModeEnabled",
					"SystemModeInfoscreenwithCoolOnly",
					"SystemModeInfoscreenwithHeatOnly",
					"SystemModeswitchSystemmodescreenwithbothcoolandheatandautoenabledCancelfunctionality",
					"SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionality",
					"SystemModeswitchSystemmodescreenwithcoolonlyCancelfunctionality",
					"SystemModeswitchSAVEfunctionbothcoolandheat",
					"SystemModeswitchSAVEfunctionbothcoolandheatandauto",
					"SystemModeswitchSAVEfunctioncoolonly",
					"SystemModeswitchSAVEfunctionHeatonly",
					"FanOptionInfoOption",
					"FanModeSwitchcancelfunction",
					"FanModeSwitchSAVEfunction",
					"SetTemperatureSolutionCardMAX",
					"SetTemperatureSolutionCardMIN",
					"SetTemperatiureOFFModeNA",
					"DashboardandsolutioncardAutochangeover",
					"DashboardandsolutioncardAutoModeCheckingFromPrimaryCard",
					"DashboardandsolutioncardAutoModeNegative1",
					"DashboardCoachmarkverification",
					"SolutionCardCoachmarkverificationNA",
					"SolutionCardEmergencyHeatbothcoolandheat",
					"SolutionCardEmergencyHeatHeatonly",
					"SolutionCardEmergencyHeatCoolonly",

					/**Jasper NA-Scheduling**/
					/**NA_GeofenceSchedule**/
					"NA_CreateGeofenceSchedule",
					"NA_SleepSettings",
					"NA_EditGeofenceWithTemperature",
					"NA_EditGeofenceSetpointsWhenAutoChnageOverEnabled",
					"JasperNA_GeofenceTimerClockIsInCrementalOf15mins",
					"NA_GeofenceScheduleOptions",
					"NA_CreateGeofenceScheduleInOffMode",
					"NA_CreateGeonceScheduleInLearnMode",
					"NA_CopyScheduleToMulitpleStat",
					"NA_WhenHeat_CoolOnly",
					"NA_CreateGeofenceWithEditingHome_Sleep_AwaySettings",
					/**NATimeBasedScheduling**/
					"JasperNA_CreateNAScheduleSinglestatwithDefaultvalue",
					"JasperNA_CancelToRetainExisitngscheduling",
					"JasperNA_ConfirmToCreateNewSchedule",
					"JasperNA_TempretureBandwidthforEachPeriod",
					"JasperNA_TimerClockIsInCrementalOf15mins",
					"JasperNA_DeletingDefaultPeriodSameEveryDay",
					"JasperNA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JapserNA_CopyScheduleToMulitpleStat",
					"JasperNA_CreateTimeBasedScheduleInOffMode",
					"JasperNA_WhenHeat_CoolOnly",
					"NA_CreateTimeBasedScheduleInOffMode",
					/**EditNATimeschedule**/
					"NA_GroupEditedDays",
					"NA_EditTempratureInSchedule",
					"NA_EditSetpointsWhenAutoChnageOverEnabled",
					"NA_PeriodTimeShouldNotOverlap",
					"NA_DeletePerioConfirmdAlert",
					"NA_DeletingDefaultPeriodDifferentOnWeekdays",
					"NA_DeletingAllPeriod",
					"NA_ScheduleOptions",
					"NA_ResumeSchedule",

					/**Jasper NA - Settings**/
					"VerifyJasperNASettings",
					//"VerifyJasperNAEMEASpruceSettings",
					//"VerifyHBBSettings",
					"EnableDisableIndoorTemperatureAlert",

					/** NA Schedule ON/OFF**/
					"ScheduleOFFONNA",
					"ScheduleOFFONNAtimebase",
					"ScheduleONOFFNAgeofencebase",
					"ScheduleOFFAdhocOverrideNAtimebaseGeofence", 
					"ScheduleONOFFNNAadhocoverrideTimebase", 
					"ScheduleONOFFNAadhocoverrideGeofence",
					"ScheduleOFFVacationNA",
					"ScheduleONOFFNAVacationNA",
					"ScheduleONOFFNAswitchingmodes",
					"ScheduleONOFFNAgeofencebasefencecross",
					"ScheduleONMultistatNA",
					"ScheduleOFFMultistatNA",
					
					/**Adhoc-NA**/
					"AdhocOverrideTimebaseSchedulefollowingfromsolutioncard",
					"AdhocOverrideGeofencebaseScheduleusing",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowing",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingschedule",
					//"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutousing",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusing",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing",
					//"AdhocOverrideCreateTimebasescheduleTemporaryHold",
					"AdhocOverrideCreateTimebasescheduleOFFModeNormalflow",
					"AdhocOverrideCreateGeofencebasechedulethroughFollowingSchedule",
					"AdhocOverrideCreateGeofencebasescheduleOFFthroughFollowingSchedule",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCard",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboard",
					"AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideGeofencebaseScheduleTempHoldSolCardSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideGeofencebaseScheduleTemporaryHoldDashboardSetpointChangeAndNEXTPeriodResume",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoTemporaryHold",
					"AdhocOverrideTimeSchedulingChangeDifferentModeHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideTimeSchedulingChangeSameModeHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingChangeModeWithDifferentModesHeatCoolAutoOFFTemporaryHold",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold",
					"AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold",
					"AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHold",
					"AdhocOverrideCreateGeofencebasecheduleTemporaryHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHold",
					"AdhocOverridetimebaseschedulingdeleteALLPERIODSTemporaryHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictime",
					"AdhocOverrideCreateGeofencebasescheduleAspecifictime",
					"AdhocOverrideCreateTimebasescheduleOFFModeAspecifictime",
					//"AdhocOverrideCreateTimebasescheduleAspecifictime",
					"AdhocOverrideTimebaseSchedulespecifictimedeleteallperiods",
					"AdhocOverrideTimebaseSchedulespecifictimeRemoveHold",
					"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHold",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHold",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHold",
					"AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHold",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictime",
					"AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatauto",
					"AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint",
					"AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint",
					"AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetNA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityNA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCard",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchange",
					"AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchange",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoPermanentHold",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHold",
					"AdhocOverrideTimebaseSchedulePermanentRemoveHold",
					"AdhocOverridetimebaseschedulingdeleteALLPERIODSPermanentHold",
					"AdhocOverrideCreateTimebaseschedulePermanentHold",
					"AdhocOverrideCreateTimebasescheduleOFFModePermanentHold",
					"AdhocOverrideCreateGeofencebaseschedulePermanentHold",
					"AdhocOverrideCreateGeofencebasescheduleOFFPermanentHold",
					
					/**Vacation-Settings-NA**/
					"Vacations_VerifyStartAndEndDate",
					"Vacations_VerifyGuideMessage",
					"Vacations_DefaultVacationTimeForNA",
					"Vacations_VerifyVacationDefaultSetPoints",
					"Vacations_MinimumBandwidthTimer",
					"Vacation_TimerValueIncreamentOf15NA",
					"Vacation_EditSetPoints",
					//"Vacation_EnableDisbaleIndividualStat",
					//"Vacation_Enable_DisbaleIndividulaStat",
					"Vacation_WhenScheduleEnables",
					"Vacation_EditSetPointsFromPrimaryCard",
					"VacationActiveSwitchingModesNA",
					//"VacationTimebaseSolutionCardAfterVacationEndsNA",
					"VacationGeofenceSolutionCardAfterVacationEndsNA",
					"Vacations_VactionStatusOnDashabord",
					
					/**Jasper EMEA-Dashboard&Solutioncard**/
					"ViewDashboardEMEA",
					"ViewSolutionCardEMEA",
					"SystemModeInfoscreenwithHeatOnlyEMEA",
					"SystemModeswitchSystemmodescreenwithheatonlyCancelfunctionalityEMEA",
					"SystemModeswitchSAVEfunctionEMEA",
					"FanModeOptionONEMEA",
					"SetTemperatureSolutionCardMAXEMEA",
					"SetTemperatureSolutionCardMINEMEA",
					"SolutionCardCoachmarkverificationEMEA",
					"SolutionCardEmergencyHeatHeatonlyEMEA",

					/**Jasper EMEA-Scheduling**/
					/**EMEATimeBasedScheduling**/
					"JasperEMEA_CreateEMEAScheduleSinglestatwithDefaultvalue",
					"JasperEMEA_CreateEMEAEverydayscheduleAddingperiod",
					"JasperEMEA_CancelToRetainExisitngscheduling",
					"JasperEMEA_ConfirmToCreateNewSchedule",
					"JasperEMEA_TempretureBandwidthforEachPeriod",
					"JasperEMEA_DeletingDefaultPeriodDifferentOnWeekdays",
					"JasperEMEA_CanCreateMaximumOfSixPeriods",
					"JasperEMEA_CreateTimeBasedScheduleInOffMode",
					"JasperEMEA_CopyScheduleToMulitpleStat",
					/**EMEA_GeofenceSchedule**/
					"EMEA_CreateGeofenceSchedule",
					"EMEA_CreateNewGeofenceSchedulewithExistingTimeBasedSchedule",
					"EMEA_SleepSettings",
					"EMEA_EditGeofenceWithTemperature",
					"JasperEMEA_TimerClockIsInCrementalOf15mins",
					"EMEA_GeofenceScheduleOptions",
					"EMEA_CreateGeofenceScheduleInOffMode",
					"EMEA_CreateGeonceScheduleInLearnMode",
					"EMEA_CopyGeofenceScheduleToMulitpleStat",
					"EMEA_CreateGeofenceWithEditingHome_Sleep_AwaySettings",
					"EMEA_EditGeofenceScheduleSettingsAndBackNavigation",
					/**EditEMEATimeschedule**/
					"EMEA_AtleastOnePeriodNotDeletableInGroupDay",
					"EMEA_AtleastTwoPeriodInIndividualDay",
					"EMEA_EndtimeTimeschedulePeriod",
					"EMEA_EditingEndtime",
					"EMEA_EditingStartTime",
					"EMEA_GroupEditedDays",
					"EMEA_ViewTimescheduleIndividualdaysEMEA", 
					"EMEA_ScheduleOptions",
					"EMEA_ResumeSchedule",


					/** EMEA Schedule ON/OFF**/
					"ScheduleOFFONEMEA",
					"ScheduleOFFONEMEAtimebase",
					"ScheduleONOFFEMEAgeofencebase",
					"ScheduleOFFEMEAtimebasegeofencebase", 
					"ScheduleONFFAdhocOverrideEMEAtimebase",
					"ScheduleOFFONAadhocoverrideEMEAgeofence", 
					"ScheduleOFFVacationEMEA",
					"ScheduleOFFONVacationEMEA",
					"ScheduleONOFFEMEAswitchingmodes",
					"ScheduleONOFFEMEAgeofencebasefencecross", 
					"ScheduleONMultistatEMEA",
					"ScheduleOFFMultistatEMEA",
					
					/** Adhoc-EMEA**/
					"AdhocOverrideTimebaseSchedulefollowingfromsolutioncardEMEA",
					"AdhocOverrideGeofencebaseScheduleusingEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutofollowingEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleEMEA",
					"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA",
					"AdhocOverrideCreateGeofencebasescheduleAspecifictimeEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatcoolAutoOFFfollowingscheduleduplicateEMEA",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatcoolAutoOFFusingEMEA",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodTemporaryHoldEMEA",
					//"AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromDashboardEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldSolutionCardsetpointchangeEMEA",
					"AdhocOverrideTimebaseScheduleTemporaryHoldDashbaordsetpointEMEA",
					"AdhocOverrideGeofencebaseScheduleTemporaryHoldSolutionCardEMEATemporaryHoldEMEA",
					"AdhocOverrideTimeschedulingChangemodeHeatOffTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleOFFModeTemporaryHoldEMEA",
					"AdhocOverrideCreateGeofencebasecheduleTemporaryHoldEMEA",
					"AdhocOverrideCreateGeofencebasescheduleOFFTemporaryHoldEMEA",
					//"AdhocOverrideCreateGeofencebasescheduleOFFAspecifictimeEMEA",
					"AdhocOverrideScheduletemperatureGeofencebaseSchedulingChangemodeHeatOFFTemporaryHoldEMEA",
					//"AdhocOverrideCreateGeofencebasecheduleTemporaryHold",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodEMEATemporaryHoldEMEA",
					"AdhocOverridegeofencebaseschedulingdeletecurrentsleepperiodEMEATemporaryHoldEMEA",
					"AdhocOverridegeofencebaseschedulingremoveholdEMEATemporaryHoldEMEA",
					"AdhocOverrideTimebaseScheduleAspecifictimeDashbaordsetpoint_EMEA",
					"AdhocOverrideTimebaseScheduleAspecifictimeSolutionCardsetpoint_EMEA",
					"AdhocOverridetimebaseschedulingdeleteNextperiodTemporaryHold_EMEA",
					"AdhocOverrideGeofencebaseSchedulingChangeModesWithSameModesHeatCoolAutoOFFTemporaryHold_EMEA",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodUsing_EMEA",
					"AdhocOverridetimebaseschedulingdeletecurrentperiodTemporaryHold_EMEA",
					//"AdhocOverrideTimebaseSchedulespecifictimetoPermanentHoldEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimeRemoveHoldEMEA",
					//"AdhocOverrideCreateTimebasescheduleAspecifictime",
					"AdhocOverrideGeofencebaseSchedulingDeleteCurrentSleepPeriodByRemovingTemporaryHoldEMEA",
					"AdhocOverrideCreateTimebasescheduleOFFModeAspecifictimeEMEA",
					"AdhocOverrideGeofencebaseSchedulingChangemodeHeatCoolAutoTemporaryHoldEMEA",
					//"AdhocOverrideTimebaseScheduleTemporaryHoldStatusFromSolutionCardEMEA",
					"AdhocOverrideTimebaseSchedulespecifictimeSolutionCardPermanentHoldEMEA",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFAspcifictimeEMEA",
					"AdhocOverrideTimebaseScheduleSpcifictimesystemmodeswitchcoolheatautoEMEA",
					"AdhocOverrideTimebaseScheduleAdhocOverrideActionSheetEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardCancelfunctionalityEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldSolutionCardsetpointchangeEMEA",
					"AdhocOverrideTimebaseSchedulePermanentHoldDashboardsetpointchangeEMEA",
					"AdhocOverrideScheduletemperatureTimeschedulingChangemodeHeatcoolAutoOFFPermanentHoldEMEA",
					"AdhocOverrideTimebaseSchedulePermanentRemoveHoldEMEA",
					
					/**Vacation-Settings-EMEA**/
					"Vacations_VerifyTimeAndDateBoundaryConditionsForEMEA",
					"Vacation_TimerValueIncreamentOf10EMEA",
					"VacationActiveSwitchingModesEMEA",
					//"VacationTimebaseSolutionCardAfterVacationEndsEMEA",
					"VacationGeofenceSolutionCardAfterVacationEndsEMEA",
					
					/**HBB-Dashboard&Solutioncard**/
					"SetTemperatiureOFFModeHB",

					/** HBB Schedule ON/OFF**/
					"ScheduleONOFFHB",
					"ScheduleONOFFHBtimebase",
					"ScheduleONOFFHBgeofencebase",
					"ScheduleONOFFHBswitchingmodes",
					"ScheduleONOFFHBgeofencebasefencecross",
					"ScheduleONMultistatHB", 
					"ScheduleOFFMultistatHB",
					
					/**Vacation-Settings-HBB**/
					//"Vacations_VerifyHBBStatsNotPresentOnComfortSettings",

//					/** Camera **/
//					/*"CameraSettingsCameraOn",
//					"CameraSettingsManageAlertsDisabled",
//					"CameraSettingsEnableDisableMotionDetection",
//					"VerifyCameraMotionSensitivitySettingsC1",
//					"VerifyCameraMotionSensitivitySettingsC2",
//					"CameraSettingsEnableDisableSoundDetection",
//					"CameraSettingsVerifyNightVisionSettings",
//					"CameraSettingsVerifyVideoQualitySettings",
//					"CameraSettingsEnableDisableCameraMicrophone",*/
					
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