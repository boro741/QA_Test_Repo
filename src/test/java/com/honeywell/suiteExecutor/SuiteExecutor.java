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


		/**************Android Suite Scenarios*********************/

		commandLineArguments = new String[] { 
//				"--deviceCloudProviderCredentials","SauceLabs::GraniteCI:46479bde-6cfd-4de0-96b9-9a331359b3e8,Perfecto::surendar.subramani@honeywell.com:Password1",
//				"--publishResult",
				"--useXCUITest", "true", 
				 "--jira_credentials",
				 "aterbuild:aterbuild@123",
				"--requirementFileName","Requirement_file_DAS_Alarm_IOS.txt",
				"--appToInstall","IOS:Lyric_Xcode_10_build,Android:405001538",
				"--groups",
//				/** Das Camera Settings **/   
//				"DAS_CameraSettingsCameraOff",
//				"DAS_CameraSettingsCameraOn",
//				"CameraSettingsCameraOffPanelArmed",
//				"CameraSettingsCameraOnPanelArmed",
//				"DAS_CameraSettingsCameraModeGeofenceAway",
//				"DAS_CameraSettingsCameraModeGeofenceHome",
//				"DAS_CameraSettingsManageAlertsDisabled",
//				"DAS_CameraEnableDisableMotionDetection",
//				"DAS_CameraVerifyMotionSensitivitySettings",
//				"DAS_VerifyNightVisionSettings",
//				"DAS_VerifyVideoQualitySettings",
//				"DAS_EditCameraName",
//				"VerifyCameraOnInHomeMode",
//				"VerifyCameraOffInHomeMode",
//				"VerifyCameraOnInNightMode",
//				"VerifyCameraOffInNightMode",
//				"VerifyMotionDetectioncameraoff",
//				"VerifyNightVisioncameraoff",
//				"VerifyVideoQualitycameraoff",
//				"VerifyDASSettings",
//				"VerifyDASSettingswhenmodechange",
//				"VerifyManageAlertsScreen",
//				"VerifyManageAlertsScreenEnableDisableSecurityModeChange",
//				"VerifyManageAlertsScreenEnableDisableDoorsAndWindowsModeChangeHomeAwayNight",
//				"VerifyManageAlertsScreenEnableDisableDoorsAndWindowsModeChangeOff",
//				"VerifySecuritySettingsGeofenceoptionAwayNightOffline",
//				"VerifySecuritySettingsGeofenceoptionHomeOff",
//				"VerifySecuritySettingsGeofenceoptionHomeOffDisbaledgeofencethislocation",
//				"EnableDisableGeofencing",
//				"VerifyEnhancedDeterrenceAwayNightmode",
//				"VerifyoutdorrmotionviewersoninhomemodeHomeOFFmode",
//				"Verifyoutdorrmotionviewersoninhomemodenightawaymode",
//				"DASEntryExitDelaySettings",
//				"DASEntryExitDelaySettingsNightAway",
//				"DASAboutSecurityModesVerification",
//				"ChangeBaseStationVolumeHomeOff",
//				"ChangeBaseStationVolumeNightAwayOffline",
//				"VerifyDASPanelModelAndFirmwareDetails",
//				"DeleteDASBaseStationAwayNightOffline",
//				"DeleteBaseStation",

				/**********START DAS ALARMS WORKFLOW**********/
//				"Attention_FromCamera",																									//— Related to Motion sensor
				"Doorsensor_InAwayExitDelay_OpenDoor_NoAlarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToHomeFromPushNotification_DoorNotClosedInEntryDelay",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToHomeFromEntryDelay_DoorNotClosedInEntryDelay",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToHomeFromPushNotification_DoorClosedInEntryDelay",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToHomeFromEntryDelay_DoorClosedInEntryDelay",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromPushNotification_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromEntryDelay_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromPushNotification_DoorClosedInWaiting_NoAlarm",
				"Doorsensor_ArmedAway_OpenDoor_SwitchingToNightFromEntryDelay_DoorOpenedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedAway_OpenDoor_AttentionInEntryDelay",
				"Doorsensor_ArmedAway_OpenDoor_AlarmWhenNoActionInEntryDelay",
				"Doorsensor_ArmingAway_ExitError_SwitchingToHomeInEntryDelay",
				"Doorsensor_ArmingAway_ExitError_SwitchingToNightFromEntryDelay_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmingAway_ExitError_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmingAway_ExitError_AttentionInEntryDelay",
				"Doorsensor_ArmingAway_ExitError_AlarmWhenNoActionInEntryDelay",
				"Doorsensor_InNightExitDelay_OpenDoor_NoAlarm",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToHomeFromPushNotification_DoorNotClosedInEntryDelay",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToHomeFromEntryDelay_DoorNotClosedInEntryDelay",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToHomeFromPushNotification_DoorClosedInEntryDelay",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToHomeFromEntryDelay_DoorClosedInEntryDelay",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromPushNotification_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromEntryDelay_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromPushNotification_DoorClosedInEntryDelay_NoAlarm_Duplicate",
				"Doorsensor_ArmedNight_OpenDoor_SwitchingToNightFromEntryDelay_DoorOpenedInEntryDelay_NoAlarm",
				"Doorsensor_ArmedNight_OpenDoor_AttentionInEntryDelay",
				"Doorsensor_ArmedNight_OpenDoor_AlarmWhenNoActionInEntryDelay",
				"Doorsensor_ArmingNight_ExitError_SwitchingToHomeInEntryDelay",
				"Doorsensor_ArmingNight_ExitError_SwitchingToNightFromEntryDelay_DoorNotClosedInEntryDelay_Alarm",
				"Doorsensor_ArmingNight_ExitError_SwitchingToNightFromEntryDelay_DoorClosedInEntryDelay_NoAlarm",
				"Doorsensor_ArmingNight_ExitError_AttentionInEntryDelay",
				"Doorsensor_ArmingNight_ExitError_AlarmWhenNoActionInEntryDelay",
				"WindowSensor_OpenDuringAwayModeExitDelay",
				"WindowSensor_OpenAfterAwayModeExitDelay",
				"WindowSensor_OpenDuringNightModeExitDelay",
				"WindowSensor_OpenAfterNightModeExitDelay",
				"DoorSensor_TamperDuringAwayModeExitDelay",
				"DoorSensor_TamperDuringNightModeExitDelay",
				"DoorSensor_TamperAfterAwayModeExitDelay",
				"DoorSensor_TamperAfterNightModeExitDelay",
				"WindowSensor_TamperDuringAwayModeExitDelay",
				"WindowSensor_TamperDuringNightModeExitDelay",
				"WindowSensor_TamperAfterAwayModeExitDelay",
				"WindowSensor_TamperAfterNightModeExitDelay",
//				"Motionsensor_MotionDetectedAfterAwayExitDelay_SwitchingToHomeFromEntryDelay",										//— Related to Motion sensor
//				"Motionsensor_MotionDetectedAfterAwayExitDelay_SwitchingToNightFromEntryDelay",										//— Related to Motion sensor
//				"Motionsensor_MotionDetectedAfterAwayExitDelay_AttentionFromEntryDelay",											//— Related to Motion sensor
//				"Motionsensor_MotionDetectedAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay",										//— Related to Motion sensor
//				"MotionSensor_TamperedInAwayExitDelay",																			//— Related to Motion sensor
//				"MotionSensor_TamperedDuringAwayExitDelay",																		//— Related to Motion sensor
				"DoorsensorOpenAfterAwayExitDelay_windowopen",
				"DoorsensorOpenAfterNightExitDelay_windowopen",
//				"AwayMode_MotionDetectedWindowOpenDoorOpen",																		//— Related to Motion sensor
//				"AwayMode_WindowOpenMotionDetectedDoorOpen",																		//— Related to Motion sensor
////				"NightMode_Dooropen_MotionsensorAfterExitDelay_windowopen",															//— Related to Motion sensor
				"DismissAlarm_Navigation",
				"Alarm_Navigation_Otherscreen",
				"Alarm_Call",
				"AlarmOnlogin",
				"EntrydelayOnlogin",
				"Alarm_History",
//				"ZwaveOperations_Alarm",																							//— Related to ZWave
				"Livestreaming_Alarm",
				"Livestreaming_Entrydelay",
				"EntryDelayScreenValidation",
				"AlarmScreenValidation",
//				"MotionViewer_AwayMode_SwitchingToHomeFromEntryDelay",																//— Related to Motion sensor
//				"MotionViewer_AwayMode_SwitchingToNightFromEntryDelayThroughPushNotification",										//— Related to Motion sensor
//				"MotionViewer_OpenAfterAwayExitDelay_AttentionFromEntryDelay",														//— Related to Motion sensor
//				"MotionViewer_OpenAfterAwayExitDelay_AlarmWhenNoActionOnEntryDelay",												//— Related to Motion sensor
//				"MotionViewerSensor_TamperDuringNightModeExitDelay",																//— Related to Motion sensor
//				"AwayMode_MotiondetectedByOSMV_DoorOpened_MotiondetectedByMotionSensor_MotiondetectedByISMV_WindowOpened",			//— Related to Motion sensor
//				"Entrydelay_Navigation_Otherscreen",																				//Covered in @EntryDelayScreenValidation Scenario
//				"AlarmDismissedViaKeyfobDiffModes",
//				"AwayMode_DoorOpened_MotiondetectedByISMV_MotiondetectedByMotionSensor_WindowOpened",
//				"AwayMode_WindowOpened_MotiondetectedByISMV_MotiondetectedByMotionSensor_DoorOpened_MotiondetectedByOSMV",

				//**********END DAS ALARMS WORKFLOW**********//
				
				
				///***** Sensor Settings ***//
//				"DASSecuritySettingsSensorStatus",
//				"DASDoorSensorRenameVerification",
//				"DASWindowSensorRenameVerification",
//				"DASMotionRenameVerification",
//				"DASKeyfobRenameVerification",
//				"DASISMVSensorRenameVerification",
//				"DASOSMVSensorRenameVerification",
//				"DASSensorRenamePopUpVerification",
//				"DASKeyfobRenamePopUpVerification",
//				"DASSensorStatusOFFWithAwayMode",
//				"DASSensorStatusOFFWithNightMode",
//				"DASDoorWindowSensorCoverTamperStatus",
//				"DASMotionSensorCoverTamperStatus",
//				"DASSensorModelAndFirmwareDetailsVerification",
//				"DASDoorSensorSignalStrengthWithSensor",
//				"DASWindowSensorSignalStrengthWithSensor",
//				"DASMotionSensorSignalStrengthWithSensor",
//				"DASISMVSensorSignalStrengthWithSensor",
//				"DASOSMVSensorSignalStrengthWithSensor",

				//***** Sensor Settings ***//
				
				//**** Command And Control ****//
//				"SwitchingSystemModeOption",
//				"CommandControlSecuritycardModeFromHomeandOFFChangeActivitlyLogAndPushNotificaiton",
//				"CommandControlSecuritycardModetoOFFChangeActivitlyLogAndPushNotificaiton",
//				"CommandControlSecuritycardModeToHomeChangeActivitlyLogAndPushNotificaiton",
//				"CommandControlDasboardModetoHomeAwayAndNightChangePushNotificaiton",
//				"CommandControlDasboardOFFModeStatusAndPushNotificaiton",
//				"CommandControlDashbaordModeToHomeChangeActivitlyLogAndPushNotificaiton",
//				"CommandControlDasboardModefromSolutionCardChangePushNotificaiton",
//				"SensorstatusHomemode",
//				"SensorstatusArmmode",
//				"CoverTamperSensorstatus",
////				"OfflineSensorstatus",
//				"OpenSensorstatusHome",
//				"OpenSensorstatusOff",
//				"ContactSensorstatusPriority",
//				"MotionSensorstatusMotionSensorPriority",
//				"MotionSensorstatusISMVandOSMVSensorPriority",
//				"BasestationSensorenrollment",
//				"CloseandLaunchApp",
//				"CommandControlfromkeyfobwhensensorfault",
//				"CommandControlfromkeyfobwhenContactsensortrouble",
//				"CommandControlfromkeyfobwhenMotionsensortrouble",
//				"commandandcontrolmotionsensorfault",
//				"commandandcontrolExitdelayFault",
//				"commandandcontrolmultiplesensorwithfault",
//				"UserpressesbackbuttonWhileSwitchingModes",
//				"CancelSwitchingModeAway",
//				"CancelSwitchingModeNight",
//				"LowBatterySensorstatus",
//				"MultipleSensorwithstatusscrolling",
//				"SwitchingSystemModeOffSensorstatus",
//				"SwitchingSystemModeHomeSensorstatus",
//				"SwitchingSystemModeNightMotionSensorstatus",
//				"SwitchingSystemModeNightContactSensorstatus",
//				"SwitchingSystemModeAwaySensorstatus",
//				"DASPanelDashboardandsecuritycardcoach_markverification",
				//**** Command And Control ****//
		};
		try {
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}