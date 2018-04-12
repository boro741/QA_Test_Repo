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
		else{
			commandLineArguments = new String[] { "--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
					"--publishResult",
					"--requirementFileName","Requirement_file_Android.txt",
					"--testrunname",""+appName+"",
					"--appToInstall","IOS:1,Android:"+appName+"",
					"--groups",

					"AdhocOverrideScheduleTemperature",
					//"AdhocOverrideScheduleTemperatureMultistat",
					"AdhocOverrideScheduleTemperatureGeofenceschedulingChangemodeHeatcool",
					"AdhocOverrideScheduleTemperatureGeofenceschedulingResume",
					//"AdhocOverrideScheduleTemperatureGeofenceschedulingResumeMultistat",
					"AdhocOverrideScheduleTemperatureTimeschedulingChangemodeHeatcool",
					"AdhocOverrideScheduleTemperatureTimeschedulingPermanently",
					//"AdhocOverrideScheduleTemperatureTimeschedulingPermanentlyMultistat",
					//"AdhocOverrideScheduleTemperatureTimeschedulingHolduntil",
					//"AdhocOverrideScheduleTemperatureTimeschedulingHolduntilMultistat",
					"AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterPermanentHold",
					//"AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterPermanentHoldMultiStat",
					//"AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterHoldUntil",
					//"AdhocOverrideScheduleTemperatureTimeschedulingResumeAfterHoldUntilMultiStat",
					"OverrideTemperatureStatoffmode",
					"ScheduleOff",
					"ScheduleOn",
					"ScheduleOffAdhocOverride",
					"ScheduleOnAdhocOverride",
					//"ScheduleOffVacation",
					//"ScheduleOnVacation",
					"ScheduleOnMultistat",
					"ScheduleOffMultistat",
					"EditNAGeofencescheduleWithSleepsettings",
					"EditNAGeofenceschedulevalueWithoutsleepsettings",
					"EditNAGeofenceschedulevaluesleepsettings",
					"EditNAGeofencescheduleTemperatureRange",
					"EditNAGeofencescheduleSinglestatTemperatureAutochangeoverEnabled",
					"EditNAGeofencescheduleTimeformat",
					"EditNAGeofencescheduleAddingSleepsettingsTimeformat",
					"EditEMEAGeofencescheduleWithSleepsettings",
					"EditEMEAGeofenceschedulevalueWithoutsleepsettings",
					"EditEMEAGeofenceschedulevaluesleepsettings",
					"EditEMEAGeofencescheduleTemperatureRange",
					"EditEMEAGeofencescheduleTimeformat",
					"EditEMEAGeofencescheduleAddingSleepsettingsTimeformat",
					"ChangeStatmodeNANoschedule",
					"ChangeStatModeNAGofenceschedule",
					"ChangeStatModeNATimeschedule",
					"ChangeStatModeNAMultistat",
					"ChangeStatModeNAAutochangeover",
					"ChangeStatModeEMEA",
					/*"ChangeStatModeEMEAMultistat",
                 "GeofencingAlertsEnabled",
                 "GeofencingAlertsDisabled",*/
					"TemperatureAlertsOfStat",
					"HumidityAlertsOfStat_OnlyHBB",
					"LocationWithMultipleStats",
					"ViewFilterReminderScreen_OnlyNAandHBB",
					"ListOfAllStatsInComfortAlertsScreen",
					//"PushNotificationsOFTemperatureAlerts",
					"AboveAndBelowSetValuesForTemperature",
					//"PushNotificationsOFHumidityAlerts_OnlyHBB",
					"AboveAndBelowSetValuesForHumidity_OnlyHBB",
					//"PushNotificationForFilterReminder_OnlyNAandHBB",
					"FilterLastReplacedDate_OnlyNAandHBB",
					"NextScheduleReminderDateWrtToReplaceFilterMonth_OnlyNAAndHBB",
					"CreateGeofencescheduleSinglestatDefaultvalueWithSleepsettings",
					"CreateGeofencescheduleSinglestatDefaultvalueWithoutsleepsettings",
					"CreateGeofencescheduleSinglestatBychangingTemperaturevalue",
					"CreateGeofencescheduleSinglestatEditSleepsettings",
					"CreateGeofencescheduleSinglestatTemperatureRange",
					"CreateEMEAGeofencescheduleSinglestatTimeformat",
					"CreateNAGeofencescheduleSinglestatTimeformat",
					"CreateGeofencescheduleMultistatCopyschedule",
					"CreateNAGeofencescheduleSinglestatTemperatureAutochangeoverEnabled",
					"CreateEMEAEverydayscheduleSinglestatDefaultvalue",
					"CreateEMEAWeekdayandWeekendTimebasedscheduleSinglestatDefaultvalue",
					"CautionmessageTochangeGeofencescheduleFromWeekdayandWeekendscheduleCancels",
					//"CautionmessageTochangeGeofencescheduleFromWeekdayandWeekendscheduleConfirms",
					//"CautionmessageTochangeGeofencescheduleFromEverydayscheduleCancels",
					"CautionmessageTochangeGeofencescheduleFromEverydayscheduleConfirms",
					"CreateEMEAEverydayscheduleTemperatureRange",
					"CreateEMEAWeekdayandWeekendscheduleTemperatureRange",
					"CreateEMEAEverydayscheduleTimeformat",
					"CreateEMEAEverydayscheduleAddingperiod",
					//"CreateEMEAEverydayscheduleDeletingperiod",
					"CreateEMEAWeekedayandWeekendscheduleAddingperiod",
					"CreateEMEAWeekedayandWeekendscheduleDeletingperiod",
					//"CreateEMEAWeekdayandWeekendScheduleTimeformat",
					"CreateEMEAEverydayscheduleMultistatCopyschedule",
					"CreateEMEAWeekdayandWeekendscheduleMultistatCopyschedule",
					"CreateNAEverydayscheduleSinglestatDefaultvalue",
					"CreateNAWeekdayandWeekendTimebasedscheduleSinglestatDefaultvalue",
					"CautionmessageNATochangeGeofencescheduleFromWeekdayandWeekendscheduleCancels",
					//"CautionmessageNATochangeGeofencescheduleFromWeekdayandWeekendscheduleConfirms",
					//"CautionmessageNATochangeGeofencescheduleFromEverydayscheduleCancels",
					"CautionmessageNATochangeGeofencescheduleFromEverydayscheduleConfirms",
					"CreateNAEverydayscheduleTemperatureRange",
					"CreateNAWeekdayandWeekendscheduleTemperatureRange",
					"CreateNAEverydayscheduleTimeformat",
					"CreateNAEverydayscheduleDeletingperiod",
					"CreateNAEverydayscheduleDeletingAllperiod",
					"CreateNAWeekdayandWeekendscheduleDeletingperiod",
					"CreateNAWeekdayandWeekendscheduleDeletingAllperiod",
					//"CreateNAWeekdayandWeekendscheduleTimeformat",
					"CreateNAEverydayscheduleMultistatCopyschedule",
					"CreateNAWeekdayandWeekendscheduleMultistatCopyschedule",
					"SetTemperatureNAHBB",
					"SetTemperatureEMEA",
					//"SetTemperatureMultistatNA",
					//"SetTemperatureMultistatEMEA",
					"SetWheelGrayedoutOFFMode",
					"SetTemperatureAutochangeoverJasperNA",
					/*"VerifyStartAndEndDate",
                 "VerifyGuideMessage",
                 "VerifyDateInputs",
                 "VerifyTimeAndDateBoundaryConditionsForNA",
                 "VerifyTimeAndDateBoundaryConditionsForEMEA",
                 "VerifyVacationDefaultSetPoints",
                 "VerifyHBBStatsNotPresentOnComfortSettings",*/

					"WeatherforecastNA",
					"WeatherforecastEMEA",

					//"ViewTimeschedulePrimarycardNA",
					//"ViewTimescheduledefaultNA",
					//"ViewTimescheduleIndividualdaysNA",
					"EditTimescheduleTemperatureNA",
					"EditTimescheduleTemperatureEverydayAutochangeoverEnabledNA",
					"EditTimescheduleTimeformatNA",
					"EditTimescheduleOverlapTwoPeriodsNA",
					"CancellingDeleteperiodNA",
					"EditTimescheduleDeletingperiodNA",
					"EditTimescheduleDeletingallperiodNA",

					//"ViewTimeschedulePrimarycardEMEA",
					"AddTimeschedulePeriodEMEA",
					"DeleteTimeschedulePeriodGroupedDaysEMEA",
					//"DeleteTimeschedulePeriodIndividualDaysEMEA",
					"EditEndtimeTimeschedulePeriodEMEA",
					"EditEndtimeEMEA",
					//"ViewTimescheduleGroupedDaysEMEA",
					//"ViewTimescheduleIndividualdaysEMEA",
					"EditTimescheduleTemperatureEMEA",
					"EditTimescheduleTimeformatEMEA",

					"GeofenceCenter_Update_SingleStat",
					"GeofenceCenter_Update_BothLocation",
					"GeofenceCenter_UpdateAndCancel_SingleStat",
					//"GeofenceCenter_UpdateAndCancel_MultiStat",
					"GeofenceCenter_Update_MultiLocation",
					"GeofenceCenter_MobileLocation_PopupVerification",
					"GeofenceCenter_APPLocationPermission_PopupVerification",
					"GeofenceCenter_MobileLocationTurnON",
					"GeofenceCenter_AppLocation_PermissionTurnON",
					//"GeofenceCenter_UpdateInRadiusSetttingScreen",

					"Viewandeditlocationdetails",
					"DeleteLocationSingleuser",
					/*  "ToEditLocationName",
                 "DeleteLocationmultiplelocations",
                 "DeleteLocationmultipleuser",*/

					"GeofenceMonitoringForVariousCombination",
					"UserShouldnotRemovedfromGang",
					"UserShouldnotRemovedfromGangExtra",
					//"UserShouldnotRemovedfromGangExtra1",
					//"UserShouldnotRemovedfromGangExtra2",
					"StatShouldnotParticipateGeofenceMonitoring",
					"StatShouldnotParticipateGeofenceMonitoringMultipleStat",

					"GeofenceRadius_UpdateSingleLocation_InMultiLocationAccount",
					"GeofenceRadius_UpdateAndCancel_SingleLocation",
					"GeofenceRadius_MobileLocation_PopupVerification",
					"GeofenceRadius_APPLocationPermission_PopupVerification",
					"GeofenceRadius_MobileLocationTurnON",
					"GeofenceRadius_AppLocation_PermissionTurnON",
					"GeofenceRadius_Update_MaximumAndMinimum",
					//"Geofence_LocationStatus",
					"GeofenceTirggerVerification_ToggleOff",

					"VerifyMyAccount",
					"VerifyChangePasswordScenario",

					/*    "RateMyAppFromGlobalDrawer",
                 "RateMyAppFromPopUpForThirdTime",
                 "RateMyAppFromPopUpForFifteenthTime" */

					/*     
               "VerifySavingEventCancelByUserMessageDutyCycleOptoFalse",

                "VerifySavingEventScheduleMessageDutyCycle",
                "VerifySavingEventStartMessageDutyCycle",
                "VerifySavingEventEndMessageDutyCycle",
                "VerifySavingEventCancelByUserMessageDutyCycle",
                "VerifySavingEventCancelByUtilityMessageDutyCycle",
                "VerifyAdHocStatusAfterDREventEndsTimeBasedSchedulingDutyCycle",
                "VerifyAdHocStatusAfterDREventEndsGeofenceBasedSchedulingDutyCycle",
                "VerifyAdHocStatusAfterDREventEndsNoScheduleDutyCycle",
                "VerifyVacationStatusAfterDREventEndsDutyCycle",
                "VerifyTimeScheduleAdHocStatusAfterDREndsDutyCycle",
                "VerifyGeofenceScheduleAdHocStatusAfterDREndsDutyCycle",
                "VerifyDRStatusAfterVacationStartsDutyCycle",
                "VerifyDRStatusWhenChangingSystemModeDutyCycle",


                "VerifySavingEventScheduleMessageOffset",
                "VerifySavingEventStartMessageOffset",
                "VerifySavingEventEndMessageOffset",
                "VerifySavingEventCancelByUserMessageOffset",
                "VerifySavingEventCancelByUtilityMessageOffset",
                "VerifyAdHocStatusAfterDREventEndsTimeBassedSchedulingOffset",
                "VerifyAdHocStatusAfterDREventEndsGeofenceBasedSchedulingOffset",
                "VerifyAdHocStatusAfterDREventEndsNoScheduleOffset",
                "VerifyVacationStatusAfterDREventEndsOffset",
                "VerifyTimeScheduleAdHocStatusAfterDREndsOffset",
                "VerifyDRStatusAfterVacationStartsOffset",
                "VerifyDRStatusWhenChangingSystemModeOffset",

                "VerifySavingEventCancelByUserMessageOffsetOptoFalse",*/

					//   "cameraConfiguration_verifyAllOptions",
					"CameraLed_verifyAllOptions",
					"cameraMode_verifyAllOptions",
					"CameraMicrophone_verifyAllOptions",
					"nightVision_VerifyAllOptions",
					//  "soundDetectionSettings_verifyAllOptions1",
					//  "soundDetectionSettings_verifyAllOptions2",
					"soundDetectionSettings_verifyAllOptions3",
					//  "cameraModesDisabledState_verifyAllOptions",
					"cameraModesEnabledState_verifyAllOptions",
					//  "motionDetectionIcon_verifyAllOptions",
					"soundDetectionIcon_verifyAllOptions",
					"soundDetectionIconWithMicOff_verifyAllOptions",
					"videoQuality_VerifyAllOptions",
					"motionSensitivitySettings_verifyAllOptions",
					"motionDetectionSettings_verifyAllOptions",
					"pushToTalk_verifyAllOptions",
					"snapshot_verifyState",
					"liveStream_verifyAllOptions",
					"geofenceToggle_VerifyAllOptions",
					"geofenceUpdate_VerifyAllOptions",
					"clipListView_verifyAllOptions1",
					"clipListView_verifyAllOptions2",
					"clipListFilter_verifyAllOptions1",
					"clipListFilter_verifyAllOptions2",

			};
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}

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
