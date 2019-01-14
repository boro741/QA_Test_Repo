
package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CreateAccountAndForgotPwdUtils;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.FAQsUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.ActivateAccountScreen;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CreateAccountScreen;
import com.honeywell.screens.DASCameraSolutionCard;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.FAQsScreen;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.GlobalDrawerScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ZwaveScreen;

public class VerifyScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SENSOR OFF": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isSensorOffScreenDisplayed();
				break;
			}
			case "SENSOR LIST": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				if (sensor.isSensorListScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							" displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"not displayed with " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NO ALARM": {
				if (!DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Not displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Displayed with " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "PAUSED STREAMING": {
				AlarmScreen check = new AlarmScreen(testCase);
				boolean b = check.isPlayStreamingVisible();
				if (b) {
					Keyword.ReportStep_Pass(testCase, "Paused Streaming is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Paused Streaming is not displayed");
				}
				break;
			}
			case "FEATURE SETUP COMPLETED": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				boolean b = bs.isFeatureSetupScreenDisplayed();
				if (b) {
					Keyword.ReportStep_Pass(testCase, "Feature Setup Completed is displayed");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Feature Setup Completed is not displayed");
				}
				break;
			}
			case "ALARM HISTORY": {
				AlarmScreen click = new AlarmScreen(testCase);
				flag = flag & click.isAlarmHistoryDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Alarm History is Displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Settings Introduction page is not displayed");
				}
				break;
			}
			case "CALL": {
				AlarmScreen click = new AlarmScreen(testCase);
				flag = click.isCallScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0).toUpperCase() + "is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "WAITING TO CLOSE": {
				AlarmScreen alarmScreen = new AlarmScreen(testCase);
				if (alarmScreen.isWaitingToCloseScreenDisplayed(30)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTRY DELAY": {
				if (DASAlarmUtils.verifyEntryDelayScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NO ENTRY DELAY": {
				if (!DASAlarmUtils.verifyEntryDelayScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Not displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Displayed with " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ALARM": {
				if (DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CAMERA SOLUTION CARD": {
				if (DASCameraUtils.isCameraLiveStreaming(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ZWAVE CONTROLLER INFO": {
				DASZwaveUtils.isControllerDetailsDisplayed(testCase);
				break;
			}
			case "ZWAVE DEVICES": {
				if (DASZwaveUtils.verifyZWaveDevicesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ZWAVE UTILITIES": {
				if (DASZwaveUtils.verifyZWaveUtilitiesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "REPLACE MODE ACTIVE": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isReplaceScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "INCLUSION MODE ACTIVE":
			case "ACTIVATE ZWAVE DEVICE": {
				DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isActivateZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "EXCLUSION MODE ACTIVE": {
				DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isExcludeZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD NEW DEVICE":
			case "ADD NEW DEVICE DASHBOARD": {
				AddNewDeviceScreen addDeviceSrceen = new AddNewDeviceScreen(testCase);
				if (addDeviceSrceen.isAddNewDeviceHeaderDisplayed(60)
						&& addDeviceSrceen.isSelectADeviceToInstallHeaderInAddNewDeviceScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "VIDEO CLIP": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (dasDIY.isVideoPlayerControlIconInAndroidVisible(30)) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
					}
				} else {
					if (dasDIY.isNavBackIconDisplayedInVideoClipScreen(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
					}
				}
				break;
			}
			case "WHAT TO EXPECT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isWhatToExpectScreenHeaderTitleVisible()
						&& dasDIY.isBackArrowInWhatToExpectScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CHOOSE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isChooseLocationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CREATE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCreateLocationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONFIRM YOUR ZIP CODE": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isConfirmYourAddressZipCodeTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME YOUR BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameYourBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CREATE NEW BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCreateBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "POWER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isPowerYourBaseStationHeaderTitleVisible() && dasDIY.isNextButtonVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "BASE STATION HELP": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBaseStationHelpHeaderTitleVisible()
						&& dasDIY.isBackButtonInBaseStationHelpScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "REGISTER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isRegisterBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
					dasDIY.clickOnOKButtonInQRCodeScanningFailurePopup();
					if (dasDIY.isRegisterBaseStationHeaderTitleVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "SELECT BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONNECT TO NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "NETWORK CONNECTION PROGRESS BAR", 1);
				if (dasDIY.isConnectToNetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTER YOUR WI-FI PASSWORD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isWiFiPasswordScreenSubTitleTextVisibile()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD A NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isAddANetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTER NETWORK CREDENTIALS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isEnterSSIDNameTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "SMART HOME SECURITY SUCCESS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
						"ALMOST DONE LOADING PROGRESS BAR TEXT", 5);
				if (dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENABLE GEOFENCING": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isGeoFencingHeaderTitleVisible(20)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
					if (dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible()) {
						if (dasDIY.isNoButtonInSmartHomeSecuritySuccessScreenVisible()) {
							dasDIY.clickOnNoButtonInSmartHomeSecuritySuccessScreen();
						}
					}
				}
				break;
			}
			case "GEOFENCE": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isGeoFenceScreenHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "PEOPLE DETECTION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isPeopleDetectionHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "DASHBOARD": {
				Dashboard d = new Dashboard(testCase);
				if (d.isIncreaseSecurityPopupVisible()) {
					d.clickOnDontUseButtonInIncreaseSecurityPopup();
				}
				if (d.isGlobalDrawerButtonVisible(20)
						&& (d.isAddDeviceIconVisible(10) || d.isAddDeviceIconBelowExistingDASDeviceVisible(10))) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CHECK LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCheckLocationScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME MOTION SENSOR DEFAULT NAME":
			case "NAME SENSOR DEFAULT NAME":
			case "NAME SENSOR LOCATION":
			case "NAME SENSOR": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameSensorScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CUSTOM NAME OSMV LOCATION":
			case "CUSTOM NAME ISMV LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCustomNameISMVOSMVLocationScreenVisible(10)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ALEXA APP DOWNLOAD PAGE": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.verifyAlexaAppPlayStoreTitleIsVisible(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Amazon Alexa App download page is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Amazon Alexa App download page is not displayed");
				}
				break;
			}
			case "CAMERA SETTINGS INTRODUCTION": {
				DASCameraSolutionCard dc = new DASCameraSolutionCard(testCase);
				if (dc.isCameraSettingsIntroImageVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Camera Settings Introduction page is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Settings Introduction page is not displayed");
				}
				break;
			}
			case "LOCATE VIEWER": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isLocateViewerScreenTitleVisible(30)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "TEST MOTION SENSOR":
			case "TEST ACCESS SENSOR": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				if (sensor.isTestSensorHeadingDisplayed()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0) + " is not displayed");
				}
				break;
			}
			case "TEST MOTION VIEWER": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				if (sensor.isTestMotionSensorHeadingDisplayed()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0) + " is not displayed");
				}
				break;
			}
			case "SIGNAL STRENGTH": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isSignalStrengthScreenDisplayed();
				break;
			}
			case "ACCESS SENSOR HELP": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isAccessSensorHelpScreenDisplayed();
				break;
			}
			case "MOTION SENSOR HELP": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isMotionSensorHelpScreenDisplayed();
				break;
			}
			case "GET ADDITIONAL HELP ON ACCESS SENSOR HELP": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isGetAdditionalHelpOnSensorHelpDisplayed();
				break;
			}
			case "SENSOR COVER TAMPER": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isSensorTamperedScreenDisplayed();
				break;
			}
			case "MODEL AND FIRMWARE DETAILS": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isModelDetailsDisplayed();
				flag = flag & sensor.isFirmwareDetailsDisplayed();
				break;
			}
			case "SENSOR OVERVIEW": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isSensorOverviewScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Sensor Overview Screen is displayed");
				}
				break;
			}
			case "SENSOR KEYFOB OVERVIEW":
			case "KEYFOB OVERVIEW": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isKEYFOBOverviewScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Keyfob Overview Screen is displayed");
				}
				break;
			}
			case "NAME KEYFOB": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isKeyfobNamingScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Keyfob Overview Screen is displayed");
				}
				break;
			}
			case "LOCATE MOTION SENSOR":
			case "LOCATE SENSOR": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isLocateSensorScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Locate Sensor Screen is displayed");
				}
				break;
			}
			case "PLACE SENSOR ON LOCATION":
			case "PLACE SENSOR": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isPlaceSensorScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Place Sensor Screen is displayed");
				}
				break;
			}
			case "PLACE VIEWER CHECK PLACEMENT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				flag = flag & dasDIY.isPlaceViewerCheckPlacementScreenTitileVisible(20);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Place Viewer Check Placement Screen is displayed");
				}
				break;
			}
			case "PLACE VIEWER SELECT MOUNTING OPTION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				flag = flag & dasDIY.isPlaceViewerSelectMountingOptionScreenTitileVisible(20);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Place Viewer Select Mounting Option Screen is displayed");
				}
				break;
			}
			case "PLACE VIEWER MOUNT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				flag = flag & dasDIY.isPlaceViewerMountScreenTitleVisible(20);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Place Viewer Mount Screen is displayed");
				}
				break;
			}
			case "ACCESS SENSOR INSTALL HELP": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isAccessSensorInstallHelpScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "AccessSensorInstallHelp Screen is displayed");
				}
				break;
			}
			case "THERMOSTAT DASHBOARD": {
				flag = true;
				Dashboard thermo = new Dashboard(testCase);
				flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
				flag = flag & thermo.isThermostatNameCorrectlyDisplayed(inputs.getInputValue("LOCATION1_DEVICE1_NAME"),
						inputs);
				flag = flag & thermo.isUpStepperDisplayed();
				flag = flag & thermo.isDownStepperDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is successfully displayed");
				} else {

					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not Displayed with " + expectedScreen.get(0).toUpperCase());
				}

				break;
			}
			case "THERMOSTAT SOLUTION CARD": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isThermostatSolutionCardDisplayed();
				break;
			}
			case "CHANGE MODE": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isChangeModeScreenDisplayed();
				break;
			}
			case "CHANGE FAN": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isChangeFanScreenDisplayed();
				break;
			}
			case "MODE INFO": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isModeInfoScreenDisplayed();
				break;
			}
			case "FAN INFO": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isFanInfoScreenDisplayed();
				break;
			}
			case "MOUNT SENSOR": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = sensor.isMountSensorScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Mount Sensor Screen is displayed");
				}
				break;
			}
			case "SET UP ACCESSORIES": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = sensor.isSetUpAccessoriesScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed");
				}
				break;
			}
			case "MOUNT IN A CORNER": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isMountInaCornerScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed");
				}
				break;
			}
			case "MOUNT ON THE WALL": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				sensor.isMountInaWallScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed");
				}
				break;
			}
			case "SENSOR PAIRING HELP": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.isSensorPairingScreenDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "COMFORT DEVICE": {
				Dashboard ds = new Dashboard(testCase);
				flag = flag & ds.VerifyComfortDeviceStatusInDashBoard(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " is displayed in DashBoard");
				}
				break;
			}
			case "CAMERA SETTINGS": {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				flag = flag & cs.isCameraSettingsHeaderTitleVisible(20);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "GEOFENCING SCHEDULE": {
				SchedulingScreen schl = new SchedulingScreen(testCase);
				flag = flag & schl.isUseGeofencingTextVisible(5);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "TIME BASED SCHEDULE": {
				SchedulingScreen schl = new SchedulingScreen(testCase);
				flag = flag & schl.isEverydayScheduleButtonVisible(10);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "TAP ON RESUME": {
				SchedulingScreen schl = new SchedulingScreen(testCase);
				flag = flag & schl.clickOnScheduleOffOverlay();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "IDENTIFY SENSOR": {
				FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
				flag = flag & fly.isIdentifySensorImageVisible();
			}
			case "COACH MARK": {
				flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase, CoachMarkUtils.DASCAMERA);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}

			case "ACCESS MORE INFORMATION": {
				flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase, CoachMarkUtils.DASCAMERA);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}

			case "HOME": {
				SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
				flag = flag & ssc.verifystate("Home");
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "AWAY": {
				SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
				flag = flag & ssc.verifystate("Away");
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "NIGHT": {
				SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
				flag = flag & ssc.verifystate("Night");
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				}
				break;
			}
			case "DAS SECURITY SETTINGS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isSecuritySettingHeaderVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + "screen is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0) + " Screen not displayed");
				}
				break;
			}
			case "GEOFENCE THIS LOCATION": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isGeofencingthislocationTextvisible() && gs.isGeofencingthislocationDescriptionvisible()) {
					Keyword.ReportStep_Pass(testCase, "Screen navigates to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "DOOR ACCESS SETTINGS": {
				SensorSettingScreen ss = new SensorSettingScreen(testCase);
				if (ss.isDoorAccessSettingsScreenTitleVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Screen navigates to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "WINDOW ACCESS SETTINGS": {
				SensorSettingScreen ss = new SensorSettingScreen(testCase);
				if (ss.isWindowAccessSettingsScreenTitleVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Screen navigates to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "MOTION SENSOR SETTINGS": {
				SensorSettingScreen ss = new SensorSettingScreen(testCase);
				if (ss.isMotionSensorSettingsScreenTitleVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Screen navigates to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "ISMV SENSOR SETTINGS":
			case "OSMV SENSOR SETTINGS": {
				SensorSettingScreen ss = new SensorSettingScreen(testCase);
				if (ss.isMotionViewerSettingsScreenTitleVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Screen navigates to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "GEOFENCE SETTINGS": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isGeofenceSettingsScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "PLEASE CONFIRM YOUR COUNTRY": {
				AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
				if (adn.isConfirmYourCountryScreenTitleVisible() && adn.isCurrentCountryButtonVisible()
						&& adn.isEnterCountryTextFieldVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "ADDRESS": {
				AddressScreen ads = new AddressScreen(testCase);
				if (ads.isAddressScreenTitleVisible() && ads.isBackButtonVisible()
						&& ads.isLocationNameInAddressScreenVisible() && ads.isLocationAddressInAddressScreenVisible()
						&& ads.isEditAddressInAddressScreenVisible()
						&& ads.isDeleteLocationButtonInAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "EDIT ADDRESS": {
				AddressScreen ads = new AddressScreen(testCase);
				if (ads.isEditAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "EDIT ACCOUNT": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isEditAccountScreenTitleVisible() && eas.isFirstNameLabelInEditAccountScreenVisible()
						&& eas.isFirstNameValueInEditAccountScreenVisible()
						&& eas.isLastNameLabelInEditAccountScreenVisible()
						&& eas.isLastNameValueInEditAccountScreenVisible()
						&& eas.isEmailLabelInEditAccountScreenVisible() && eas.isEmailValueInEditAccountScreenVisible()
						&& eas.isChangePasswordButtonInEditAccountScreenVisible()
						&& eas.isDeleteAccountButtonInEditAccountScreenVisible()
						&& eas.isPrivacyLabelInEditAccountScreenVisible()
						&& eas.isUsePasscodeLabelInEditAccountScreenVisible()
						&& eas.isUsePasscodeSwitchInEditAccountScreenVisible()
						&& eas.isSaveButtonInEditAccountScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "GLOBAL DRAWER": {
				GlobalDrawerScreen gds = new GlobalDrawerScreen(testCase);
				if (gds.isAccountHeaderTitleVisible() && gds.isEditAccountOptionVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "CHANGE PASSWORD": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isChangePasswordScreenTitleVisible() && eas.isOldPasswordLabelVisible()
						&& eas.isOldPasswordTextFieldVisible() && eas.isCreatePasswordLabelVisible()
						&& eas.isNewPasswordTextFeildVisible() && eas.isVerifyNewPasswordTextFieldVisible()
						&& eas.isSaveButtonInChangePasswordScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "HONEYWELL HOME": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isCreateAccountButtonInHoneywellHomeScreenVisible(20)
						&& eas.isLoginButtontInHoneywellHomeScreenVisible(20)) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "DELETE ACCOUNT WITH SOLUTION": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isDeleteAccountScreenTitleVisible() && eas.isCloseButtonInDeleteAccountScreenVisible()
						&& eas.isActionRequiredLabelVisible() && eas.isLearnHowToDeleteADeviceLinkVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "DELETE ACCOUNT WITHOUT SOLUTION": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isDeleteAccountScreenTitleVisible() && eas.isCloseButtonInDeleteAccountScreenVisible()
						&& eas.isDeleteAccountButtonInDeleteAccountScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "LEARN HOW TO DELETE A DEVICE": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isLearnHowToDeleteADeviceScreenTitleVisible()
						&& eas.isCloseButtonInLearnHowToDeleteADeviceScreenVisible()
						&& eas.isWebViewInLearnHowToDeleteADeviceScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "LEARN HOW TO CANCEL A MEMBERSHIP": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isLearnHowToCancelAMembershipScreenTitleVisible()
						&& eas.isCloseButtonInLearnHowToCancelAMembershipScreenVisible()
						&& eas.isWebViewInLearnHowToCancelAMembershipScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "ABOUT THE APP": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (atas.isAboutTheAppScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "PRIVACY POLICY AND EULA": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "PRIVACY POLICY LOADING SPINNER", 2);
				if (atas.isPrivacyPolicyAndEULAScreenTitleVisible()
						&& atas.isDoneButtonInPrivacyPolicyAndEULAScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "ACKNOWLEDGEMENTS": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (atas.isAcknowledgementsScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to " + expectedScreen.get(0));
					}
				} else {
					if (atas.isAcknowledgementsScreenTitleVisible()
							&& atas.isDoneButtonInAcknowledgementsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to " + expectedScreen.get(0));
					}
				}
				break;
			}
			case "GET HELP": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (atas.isGetHelpScreenTitleVisible(30)) {
						Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to " + expectedScreen.get(0));
					}
				} else {
					if (atas.isGetHelpScreenTitleVisible(30)
							&& atas.isNavBackToHoneywellButtonInGetHelpScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to " + expectedScreen.get(0));
					}
				}
				break;
			}
			case "APP FEEDBACK": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (atas.isAppFeedbackScreenTitleVisible() && atas.isCloseButtonInAppFeedbackScreenVisible()
						&& atas.isAppFeedbackThankYouNoteVisible() && atas.isAppFeedbackTextFieldVisible()
						&& atas.isAnonymousToggleButtonButtonVisible() && atas.isAnonymousSubTitleTextVisible()
						&& atas.isSendFeedbackButtonVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "HONEYWELL HOME IN GOOGLE PLAYSTORE": {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (atas.isGooglePlayHeaderTitleVisible() && atas.isHoneywellHomeTitleInGooglePlayStoreVisible()
						&& atas.isResideoTechnolgiesTitleInGooglePlayStoreVisible()
						&& atas.isUninstallButtonForHoneywellHomeAppInGooglePlayStoreVisible()
						&& atas.isOPENButtonForHoneywellHomeAppInGooglePlayStoreVisible()) {
					Keyword.ReportStep_Pass(testCase, "Navigated to " + expectedScreen.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "FAQS": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				if (faqsScreen.isFAQsScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "FAQs Screen title in the header is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"FAQs Screen title in the header is not displayed");
				}
				if (faqsScreen.isBackButtonInFAQsScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Back button in the header is not displayed");
				}
				if (faqsScreen.isHelpSearchTextFieldInFAQsScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Help Search in the header Text field is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Help Search Text field in the header is not displayed");
				}
				break;
			}
			case "GENERAL": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (faqsScreen.isFAQsScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "FAQs Screen title in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"FAQs Screen title in the header is not displayed");
					}
					if (faqsScreen.isBackButtonInGeneralScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isHelpSearchTextFieldInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Help Search in the header Text field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Help Search Text field in the header is not displayed");
					}
					if (faqsScreen.isGeneralScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "General title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"General Screen title is not displayed");
					}
					if (faqsScreen.isGeneralScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "General title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"General Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "General Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"General Question list is not displayed");
					}
				} else {
					if (faqsScreen.isBackButtonInGeneralScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isGeneralScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "General title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"General Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "General Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"General Question list is not displayed");
					}
				}
				break;
			}
			case "THERMOSTAT": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (faqsScreen.isFAQsScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "FAQs Screen title in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"FAQs Screen title in the header is not displayed");
					}
					if (faqsScreen.isBackButtonInThermostatScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isHelpSearchTextFieldInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Help Search in the header Text field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Help Search Text field in the header is not displayed");
					}
					if (faqsScreen.isThermostatScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Thermostat title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Question list is not displayed");
					}
				} else {
					if (faqsScreen.isBackButtonInThermostatScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isThermostatScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Thermostat title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Question list is not displayed");
					}
				}
				break;
			}
			case "WATER LEAK DETECTOR": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (faqsScreen.isFAQsScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "FAQs Screen title in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"FAQs Screen title in the header is not displayed");
					}
					if (faqsScreen.isBackButtonInWaterLeakDetectorScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isHelpSearchTextFieldInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Help Search in the header Text field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Help Search Text field in the header is not displayed");
					}
					if (faqsScreen.isWaterLeakDetectorScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Question list is not displayed");
					}
				} else {
					if (faqsScreen.isBackButtonInWaterLeakDetectorScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isWaterLeakDetectorScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Question list is not displayed");
					}
				}
				break;
			}
			case "CAMERA": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (faqsScreen.isFAQsScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "FAQs Screen title in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"FAQs Screen title in the header is not displayed");
					}
					if (faqsScreen.isBackButtonInCameraScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isHelpSearchTextFieldInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Help Search in the header Text field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Help Search Text field in the header is not displayed");
					}
					if (faqsScreen.isCameraScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Camera title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Question list is not displayed");
					}
				} else {
					if (faqsScreen.isBackButtonInCameraScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in the header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in the header is not displayed");
					}
					if (faqsScreen.isCameraScreenHeaderTitleVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Camera title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Screen title is not displayed");
					}
					if (faqsScreen.isQuestionListInAScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Water Leak Detector Question list is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Water Leak Detector Question list is not displayed");
					}
				}
				break;
			}
			case "QUESTION": {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				FAQsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER", 1);
				String questionTitleDisplayed = null;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (faqsScreen.isBackButtonInQuestionScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in header is not displayed");
					}
					if (faqsScreen.isQuestionTitleInQuestionScreenVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Question title is displayed");
						questionTitleDisplayed = faqsScreen.getQuestionTitleInQuestionScreen();
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Question title is not displayed");
					}
					if (faqsScreen.isAnswerToTheQuestionAskedInQuestionScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Answer to the question is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Answer to the question is not displayed");
					}
					if (questionTitleDisplayed.trim().replaceAll(" +", " ").equalsIgnoreCase(
							inputs.getInputValue("FIRST_QUESTION_IN_THE_SCREEN").trim().replaceAll(" +", " "))) {
						Keyword.ReportStep_Pass(testCase, "Question is correctly displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Question displayed is: " + questionTitleDisplayed + " which is not same as: "
										+ inputs.getInputValue("FIRST_QUESTION_IN_THE_SCREEN"));
					}
				} else {
					if (faqsScreen.isBackButtonInQuestionScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Back button in header is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back button in header is not displayed");
					}
					if (faqsScreen.isQuestionTitleInQuestionScreenVisible(20)) {
						Keyword.ReportStep_Pass(testCase, "Question title is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Question title is not displayed");
					}
					if (faqsScreen.isAnswerToTheQuestionAskedInQuestionScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Answer to the question is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Answer to the question is not displayed");
					}
				}
				break;
			}
			case "CREATE ACCOUNT": {
				CreateAccountScreen cas = new CreateAccountScreen(testCase);
				if (cas.createAccountTitle()) {
					Keyword.ReportStep_Pass(testCase, "Create Account Title Screen is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			case "SELECTED COUNTRY IN CREATE ACCOUNT": {
				CreateAccountScreen cas = new CreateAccountScreen(testCase);
				String actCountry = cas.isCreateAccountGetSelectedCountry();
				if (inputs.getInputValue("SELECTED_COUNTRY").equalsIgnoreCase(actCountry)) {
					Keyword.ReportStep_Pass(testCase,
							"Selected Country is the same as the one selected in Please Confirm your country screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Selected Country is not the same as the one selected in Please Confirm your country screen "
									+ expectedScreen.get(0));
				}
				break;
			}
			case "ACTIVATE ACCOUNT": {
				ActivateAccountScreen aas = new ActivateAccountScreen(testCase);
				CreateAccountAndForgotPwdUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 1);

				if (aas.isActivateAccountTitleDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Activate Account screen is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to " + expectedScreen.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input " + expectedScreen.get(0));
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		if (flag) {
			Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " displayed");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(0) + " not displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
