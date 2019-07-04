package com.honeywell.keywords.lyric.common;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.platform.VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.lyric.das.utils.DASSolutionCardUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.ActivateAccountScreen;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.ActivityLogsScreen;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.CreateAccountScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.EndUserLicenseAgreementScreen;
import com.honeywell.screens.FAQsScreen;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.GlobalDrawerScreen;
import com.honeywell.screens.LoginScreen;
import com.honeywell.screens.ManageUsersScreen;
import com.honeywell.screens.MobileDeviceSettingsScreen;
import com.honeywell.screens.NameEditAccountScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.PrivacyStatementScreen;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.WLDLeakDetectorSettings;
import com.honeywell.screens.WLDManageAlerts;
import com.honeywell.screens.WeatherForecastScreen;
import com.honeywell.screens.ZwaveScreen;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;

public class SelectElementOnAScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects \"(.+)\" from \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("Entry delay")) {
				switch (parameters.get(0).toUpperCase()) {
				case "PAUSE": {
					AlarmScreen click = new AlarmScreen(testCase);
					boolean flag = false;
					while (flag == false) {
						click.clickLiveStreamingArea();
						flag = click.clickPauseStreaming();
					}
					break;
				}
				case "RESUME": {
					AlarmScreen click = new AlarmScreen(testCase);
					click.clickPlayStreaming();
					break;
				}
				case "SWITCH TO HOME": {
					DASAlarmUtils.clickOnSwitchToHome(testCase, inputs);
					int i = 0;
					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)) {
						System.out.println("Waiting for dismiss alarm request to complete");
					}
					break;
				}
				case "SWITCH TO NIGHT": {
					DASAlarmUtils.clickOnSwitchToNight(testCase, inputs);
					break;
				}
				case "ATTENTION": {
					DASAlarmUtils.clickOnAttention(testCase, inputs);
					break;
				}
				case "NO OPTIONS": {
					DASAlarmUtils.timeOutForNoSensorAction(testCase, inputs);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Thermostat Solution Card")) {
				switch (parameters.get(0).toUpperCase()) {
				case "MODE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					thermo.clickOnModeButton();
					break;
				}
				case "RESUME": {
					AlarmScreen click = new AlarmScreen(testCase);
					click.clickPlayStreaming();
					break;
				}
				case "SWITCH TO HOME": {
					DASAlarmUtils.clickOnSwitchToHome(testCase, inputs);
					int i = 0;
					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)) {
						System.out.println("Waiting for dismiss alarm request to complete");
					}
					break;
				}
				case "SWITCH TO NIGHT": {
					DASAlarmUtils.clickOnSwitchToNight(testCase, inputs);
					break;
				}
				case "ATTENTION": {
					DASAlarmUtils.clickOnAttention(testCase, inputs);
					break;
				}
				case "NO OPTIONS": {
					DASAlarmUtils.timeOutForNoSensorAction(testCase, inputs);
					break;
				}
				case "FAN": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnFanButton();
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("thermostat settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "EMERGENCY HEAT": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					thermo.clickOnEmergencyHeatButton();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Change Mode")) {
				inputs.setInputValue("SystemMode", parameters.get(0).toUpperCase());
				switch (parameters.get(0).toUpperCase()) {
				case "INFO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					thermo.clickOnInfoButton();
					break;
				}
				case "SAVE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnSaveButton()) {
						Keyword.ReportStep_Pass(testCase, "Clicked on the button: " + parameters.get(0).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Button: " + parameters.get(0).toUpperCase() + " is not selected");
					}
					break;
				}
				case "X": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnXButton()) {
						Keyword.ReportStep_Pass(testCase, "Clcked on the icon: " + parameters.get(0).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Icon: " + parameters.get(0).toUpperCase() + " is not selected");
					}
					break;
				}
				case "HEAT": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnHeatButton()) {
						Keyword.ReportStep_Pass(testCase, "Mode: " + parameters.get(0).toUpperCase() + " is selected");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mode: " + parameters.get(0).toUpperCase() + " is not selected");
					}
					break;
				}
				case "COOL": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnCoolButton()) {
						Keyword.ReportStep_Pass(testCase, "Mode: " + parameters.get(0).toUpperCase() + " is selected");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mode: " + parameters.get(0).toUpperCase() + " is not selected");
					}
					break;
				}
				case "OFF": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnOffButton()) {
						Thread.sleep(10);
						Keyword.ReportStep_Pass(testCase, "Mode: " + parameters.get(0).toUpperCase() + " is selected");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mode: " + parameters.get(0).toUpperCase() + " is not selected");
					}
					break;
				}
				case "AUTO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if (thermo.clickOnAutoButton()) {
						Keyword.ReportStep_Pass(testCase, "Mode: " + parameters.get(0).toUpperCase() + "is selected");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mode: " + parameters.get(0).toUpperCase() + "is not selected");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Camera Solution Card")
					|| parameters.get(1).equalsIgnoreCase("Activity Log")) {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRMS ATTENTION": {
					flag = DASCameraUtils.clickOnAttention(testCase);
					flag = flag & DASCameraUtils.clickOnCreateAttention(testCase, inputs);
					break;
				}
				case "CANCELS ATTENTION": {
					flag = flag & DASCameraUtils.clickOnCancelAttention(testCase, inputs);
					break;

				}
				case "SNAPSHOT": {
					if (cs.isCameraPlayButtonExists(5)) {
						cs.clickOnCameraPlayButton();
						Thread.sleep(6000);
					}
					flag = flag & cs.clickSanpShotIcon();
					if (cs.isAllowExists(5)) {
						Keyword.ReportStep_Pass_With_ScreenShot(testCase,
								"user displayed with \"Need to enable Phone settings\" popup");
						cs.clickAllow();
					}
					flag = flag & cs.clickSanpShotIcon();
					break;

				}
				case "PUSHTOTALK": {
					flag = flag & cs.clickPushtoTalkIcon();
					if (cs.isAllowExists(5)) {
						cs.clickAllow();
					}
					if (cs.isPushIconExists()) {
						flag = flag & cs.clickPushtoTalkIcon();
					}
					break;

				}
				case "PUSHTOTALK1": {
					flag = flag & cs.clickPushtoTalkIcon();
					break;

				}
				case "TALKMIC": {
					@SuppressWarnings("rawtypes")
					TouchAction tAction = new TouchAction(testCase.getMobileDriver());
					Duration oneHours = Duration.ofSeconds(8);
					Thread th = new Thread() {
						public void run() {
							// tAction.longPress(cs.getMicrophonePushToTalk()).waitAction(oneHours).release().perform();
							tAction.longPress(longPressOptions().withElement(element(cs.getMicrophonePushToTalk()))
									.withDuration(oneHours)).release().perform();
						}
					};
					th.start();
					Keyword.ReportStep_Pass_With_ScreenShot(testCase, "Talk  now... is displayed");
					break;
				}
				case "ATTENTION": {
					if (cs.isAttentionIconExists()) {
						flag = flag & cs.clickAttentionIcon();
					}
					break;
				}
				case "CANCEL ATTENTION": {
					if (cs.isAttentionCancelExists()) {
						flag = flag & cs.clickAttentionCancel();
					}
					break;
				}
				case "ATTENTION PANIC ICON": {
					if (cs.isAttentionIconExists()) {
						flag = flag & cs.clickAttentionIcon();
					}
					if (cs.isAttentionPanicImageExists()) {
						flag = flag & cs.clickAttentionPanicImag();
					}
					break;
				}
				case "DELETE CLIP": {
					if (al.isDeleteClipsExists()) {
						flag = flag & al.clickDeleteClips();
					}
					break;
				}
				case "DOWNLOAD CLIP": {
					if (cs.isAllowExists(3)) {
						cs.clickAllow();
					}
					if (al.isDowloadIconExists()) {
						flag = flag & al.clickDowloadIcon();
					}
					break;
				}
				case "CANCEL DOWNLOAD": {
					if (testCase.getPlatform().contains("IOS")) {
						if (al.isDowloadCloseExists()) {
							al.clickDowloadClose();
						}
					} else {
						flag = flag & MobileUtils.pressBackButton(testCase,
								"User tried to cancel the download of the clip");
					}
					break;
				}
				case "DO NOT CANCEL": {
					if (al.isCancelNoExists()) {
						flag = flag & al.clickCancelNo();
					}
					break;
				}
				case "SELECT CANCEL POPUP": {
					if (al.isCancelYesExists()) {
						flag = flag & al.clickCancelYes();
					}
					break;
				}
				case "CANCEL DELETE CLIP": {
					if (al.isDeleteCancelExists()) {
						flag = flag & al.clickDeleteCancel();
					}
					break;
				}
				case "DELETE CLIP OK": {
					if (al.isDeleteOkExists()) {
						flag = flag & al.clickDeleteOk();
					}
					break;
				}
				case "GET HELP": {
					if (cs.isGetHelpExists()) {
						flag = flag & cs.clickGetHelp();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(0));
				}
				}

			} else if (parameters.get(1).equalsIgnoreCase("alarm")) {
				switch (parameters.get(0).toUpperCase()) {
				case "PAUSE": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = click.clickPauseStreaming();
					break;
				}
				case "RESUME": {
					AlarmScreen click = new AlarmScreen(testCase);
					click.clickPlayStreaming();
					break;
				}
				case "DISMISS ALARM": {
					inputs.setInputValue("ALARM_DISMISSED_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					inputs.setInputValue("HOME_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					flag = flag & DASAlarmUtils.clickOnDismissAlarm(testCase, inputs);
					AlarmScreen alarmScreen = new AlarmScreen(testCase);
					int i = 0;

					while (i < 3 && (DASAlarmUtils.verifyProgressDisplayed(testCase)
							|| alarmScreen.isAlarmDismissButtonDisplayed()
							|| DASSolutionCardUtils.waitForDismissProcessRequest(testCase))) {
						System.out.println("Waiting for dismiss alarm request to complete");
						i++;
					}
					break;
				}
				case "DISMISS ALARM OVERLAY WITH ZWAVE ACTION IN PROGRESS": {
					inputs.setInputValue("ALARM_DISMISSED_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					inputs.setInputValue("HOME_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					flag = flag & DASAlarmUtils.clickOnDismissAlarm(testCase, inputs);
					ZwaveScreen ZScreen = new ZwaveScreen(testCase);
					int i = 0;

					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)
							&& !ZScreen.isCancelFromNavigationDisplayed()) {
						System.out.println("Waiting for dismiss alarm request to complete");
					}
					break;
				}
				case "CALL": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickOnCall();
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Exclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Inclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Activate ZWAVE Device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRM CANCEL": {
					DASZwaveUtils.clickCancelFromNavigation(testCase);
					DASZwaveUtils.clickConfirmOnCancelFromNavigation(testCase);
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("install device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ZWAVE DEVICE": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					ads.clickOnZwaveFromAddNewDevice();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ZWAVE Utilities")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONTROLLER FACTORY RESET": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFactoryReset();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ZWAVE Devices")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ALL ON": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOn();
					break;
				}
				case "ALL OFF": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOff();
					break;
				}
				case "FIX ALL": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFixAll();
					zwaveScreen.clickOnFixAllPopupConfirm();
					zwaveScreen.clickOnFixAllPopupAccept();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Switch Settings")
					|| parameters.get(1).equalsIgnoreCase("Dimmer Settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "DELETE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					break;
				}
				case "REPLACE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnReplaceButton();
					zwaveScreen.clickOnReplacePopupOk();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "15": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (bs.is15SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn15SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"15 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "30": {
					if (bs.is30SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn30SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"30 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "45": {
					if (bs.is45SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn45SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"45 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "60": {
					if (bs.is60SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn60SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"60 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				}
			} 
			else if(parameters.get(1).equalsIgnoreCase("WHAT TO EXPECT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "GET STARTED": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if(dasDIY.isGetStartedButtonInWhatToExpectScreenVisible()) {
						flag &= dasDIY.clickOnGetStartedButtonInWhatToExpectScreen();
						if(flag) {
							Keyword.ReportStep_Pass(testCase, "Clicked on Get Started button");
						}else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Get Started button in What to Expect Screen not displayed");
						}
					}
					break;
				}
				}
			}

			else if (parameters.get(1).equalsIgnoreCase("ADD NEW DEVICE")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SMART HOME SECURITY": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.selectDeviceToInstall(parameters.get(0));
					break;
				}
				case "SMART HOME SECURITY PRO": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.selectDeviceToInstall(parameters.get(0));
					break;
				}
				case "CLOSE BUTTON": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					if (ads.isCloseButtonInAddNewDeviceScreenVisible()) {
						flag &= ads.clickOnCloseButtonInAddNewDeviceScreen();
					}
					break;
				}
				case "CHANGE COUNTRY": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					if(ads.isChangeCountryLinkVisible()) {
						flag &=ads.clickOnChangeCountryLink();
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CREATE NEW LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCreateNewLocationLinkVisible()) {
						flag = flag & dasDIY.clickOnCreateNewLocationLink();
					}
					break;
				}
				default: {
					inputs.setInputValue("LOCATION1_NAME", parameters.get(0));
					flag = flag & DIYRegistrationUtils.selectAvaiableLocation(testCase, parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("NAME YOUR BASE STATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CREATE NEW BASE STATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCreateCustomBaseStationNameLinkVisible()) {
						flag = flag & dasDIY.clickOnCreateCustomBaseStationNameLink();
					}
					break;
				}
				default: {
					if (!inputs.isInputAvailable("LOCATION1_CAMERA1_NAME")) {
						inputs.setInputValue("LOCATION1_CAMERA1_NAME", parameters.get(0));
					} else {
						inputs.setInputValue("LOCATION1_CAMERA2_NAME", parameters.get(0));
					}
					flag = flag & DIYRegistrationUtils.selectAvaiableBaseStationName(testCase, parameters.get(0));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("POWER BASE STATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "NOT PULSING BLUE": {
					flag = flag & DIYRegistrationUtils.clickOnNotPulsingBlueLink(testCase);
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("SELECT BASE STATION")) {
				flag = flag & DIYRegistrationUtils.selectABaseStationFromListOfAvailableBaseStations(testCase,
						parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ADD A NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddANetworkButtonVisible()) {
						dasDIY.clickOnAddANetworkButton();
					}
					break;
				}
				case "ANY WIFI NAME": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isWiFiNetworkNameDisplayedInConnectToNetworkScreen()) {
						dasDIY.clickOnWiFiNetworkNameInConnectToNetworkScreen();
					}
					break;
				}
				default: {
					flag = flag & DIYRegistrationUtils.selectWiFiNameFromTheListOfAvailableNetworks(testCase,
							parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ADD A NETWORK")) {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isAddANetworkHeaderTitleVisible()) {
					flag = flag & DIYRegistrationUtils.selectAvailableNetworkType(testCase, parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("NAME MOTION SENSOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(0));
			}else if (parameters.get(1).toUpperCase().equals("NAME MOTION SENSOR CUSTOM NAME")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = settingScreen.clickOnCustomNameBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("SELECT ISMV LOCATION")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				inputs.setInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1", parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("PLACE VIEWER SELECT MOUNTING OPTION")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				inputs.setInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWERMOUNTINGOPTION1", parameters.get(0));
			}else if (parameters.get(1).equalsIgnoreCase("NAME DETECTOR")) {
				inputs.setInputValue("LOCATION1_DEVICE1_CO_DETECTOR", parameters.get(0));
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
			}else if (parameters.get(1).equalsIgnoreCase("PLACE DETECTOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
			}else if (parameters.get(1).equalsIgnoreCase("SELECT OSMV LOCATION")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				inputs.setInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1", parameters.get(0));
			} else if (parameters.get(1).toUpperCase().equals("DOOR")
					|| parameters.get(1).toUpperCase().equals("WINDOW")
					|| parameters.get(1).toUpperCase().equals("MOTION SENSOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				if (parameters.get(1).toUpperCase().equals("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", parameters.get(0));
				} else if (parameters.get(1).toUpperCase().equals("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", parameters.get(0));
				} else if (parameters.get(1).toUpperCase().equals("MOTION SENSOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(0));
				}
				/*
				 * flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
				 * "SAVING SENSOR PROGRESS BAR", 1);
				 */
			} else if (parameters.get(1).toUpperCase().equals("NAME SENSOR")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = settingScreen.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Call")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickCancelButton();
					break;
				}
				case "CALL THE POLICE": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickCallPoliceButton();
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Test Access Sensor")
					|| parameters.get(1).equalsIgnoreCase("Test Motion Sensor")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SENSOR NOT WORKING": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnSensorNotWorking();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Keyfob settings")
					|| parameters.get(1).equalsIgnoreCase("Window Access settings")
					|| parameters.get(1).equalsIgnoreCase("Door Access settings")
					|| parameters.get(1).equalsIgnoreCase("Motion sensor settings")
					|| parameters.get(1).equalsIgnoreCase("ISMV sensor settings")
					|| parameters.get(1).equalsIgnoreCase("OSMV sensor settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "OFF STATUS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnSensorStatusTextOnSensorSettingsScreen(parameters.get(0).toUpperCase());
					if (flag) {
						System.out.println("Successfully clicked on" + parameters.get(0));
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on" + parameters.get(0));
					}
					break;
				}
				case "MODEL AND FIRMWARE DETAILS": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimensions.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimensions.getHeight() * .9)) .moveTo(0, -(int) (dimensions.getHeight() *
						 * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(1000)))
						.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
					}
					flag = flag & settingScreen.clickOnFirmwareDetailsOption();
					break;
				}
				case "SENSOR COVER TAMPERED": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = flag & settingScreen.clickOnSensorCoverTamperOption();
					break;
				}
				case "SIGNAL STRENGTH AND TEST": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimensions.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimensions.getHeight() * .9)) .moveTo(0, -(int) (dimensions.getHeight() *
						 * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimensions.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimensions.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
					}
					flag = flag & bs.clickOnSignalStrengthandTestOption();
					break;
				}
				case "DELETE SENSOR": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (parameters.get(1).equalsIgnoreCase("Keyfob settings")) {
						flag = flag & bs.clickOnDeleteKeyfobSensorButton();
					} else {
						flag = flag & bs.clickOnDeleteSensorButton();
					}
					break;
				}
				case "NAME TEXT FIELD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (parameters.get(1).equalsIgnoreCase("Keyfob settings")) {
						flag = flag & bs.clickOnKeyfobNameTextField();
					} else {
						flag = flag & bs.clickOnNameTextField();
					}
					if (flag) {
						System.out.println("Successfully clicked on" + parameters.get(0));
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on" + parameters.get(0));
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("MOTION DETECTION SETTINGS")) {
				switch (parameters.get(0).toUpperCase()) {
				default: {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					cs.selectZone(parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor Cover Tamper")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CLEAR TAMPER": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = flag & settingScreen.clickOnClearCoverTamperOption();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Access Sensor Help")
					|| parameters.get(1).equalsIgnoreCase("Motion Sensor Help")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GET ADDITIONAL HELP": {
					flag = flag & sensor.clickOnGetAdditionalHelpButton();
					break;
				}
				case "TEST SIGNAL STRENGTH": {
					if (parameters.get(1).toUpperCase().contains("ACCESS")) {
						flag = flag & sensor.clickOnTestSignalStrength();
					} else if (parameters.get(1).toUpperCase().contains("MOTION SENSOR")) {
						flag = flag & sensor.clickOnTestSignalStrengthForMotionSensor();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}

				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor List Settings")
					|| parameters.get(1).equalsIgnoreCase("Keyfob List Settings")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "ADD BUTTON": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("SET UP ACCESSORIES")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "MOTION SENSOR SETUP BUTTON":
				case "DOOR ACCESS SENSOR SETUP BUTTON":
				case "WINDOW ACCESS SENSOR SETUP BUTTON":
				case "COMBO SETUP":
				case "KEYFOB SETUP BUTTON":
				case "ISMV SENSOR SETUP BUTTON":
				case "OSMV SENSOR SETUP BUTTON": {
					flag = flag & sensor.clickOnSetUpButton(inputs, parameters.get(0));
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				case "DONE": {
					Thread.sleep(3000);
					flag = flag & sensor.clickOnDoneButton();
					break;
				}
				case "HELP": {
					flag = flag & sensor.clickOnSensorHelpButton();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully clicked on " + parameters.get(0).toUpperCase() + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					||parameters.get(1).equalsIgnoreCase("Detector Overview")
					|| parameters.get(1).equalsIgnoreCase("Sensor Keyfob Overview")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WATCH THE HOW TO VIDEO": {
					flag = flag & sensor.clickOnWatchHowToVideoButton();
					break;
				}
				case "GET STARTED": {
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						flag = flag & sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor on location")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WONT FIT AS SHOWN": {
					flag = flag & sensor.clickOnWontFitAsShownButton();
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on \"+parameters.get(0)+\" button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor")
					|| parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("MOUNT IN A CORNER")
					|| parameters.get(1).equalsIgnoreCase("MOUNT ON THE WALL")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("NAME Keyfob")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Unable to click on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("Sensor Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("Locate Viewer")
					|| parameters.get(1).equalsIgnoreCase("Primary Card")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WATCH THE HOW TO VIDEO": {
					flag = flag & sensor.clickOnWatchHowToVideoButton();
					break;
				}
				case "GET STARTED": {
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						flag = flag & sensor.clickOnCancelButton();
					}
					break;
				}
				case "MAX SET TEMPERATURE BY TAPING ON UP STEPPER": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.setMaxTemperatureByTappingUpStepper(inputs);

					break;
				}
				case "MIN SET TEMPERATURE BY TAPING ON DOWN STEPPER": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.setMinTemperatureByTappingDownStepper(inputs);
					break;
				}
				case "DR EVENT LABEL": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnDrEventLabel();
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor on location")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WONT FIT AS SHOWN": {
					flag = flag & sensor.clickOnWontFitAsShownButton();
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on \"+parameters.get(0)+\" button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor")
					|| parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("MOUNT IN A CORNER")
					|| parameters.get(1).equalsIgnoreCase("MOUNT ON THE WALL")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("NAME Keyfob")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Change Fan")) {
				switch (parameters.get(0).toUpperCase()) {
				case "INFO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnInfoButton();
					break;
				}
				case "AUTO FAN": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnAutoFanButton();
					inputs.setInputValue("SelectedFanMode", parameters.get(0).toUpperCase());
					System.out.println(inputs.getInputValue("SelectedFanMode"));
					break;
				}
				case "CIRCULATE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnCirculateFanButton();
					inputs.setInputValue("SelectedFanMode", parameters.get(0).toUpperCase());
					break;
				}
				case "ON": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnONFanButton();
					inputs.setInputValue("SelectedFanMode", parameters.get(0).toUpperCase());
					break;
				}
				case "X": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnXButton();
					break;
				}
				case "SAVE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnSaveButton();
					break;
				}

				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Mode info")
					|| parameters.get(1).equalsIgnoreCase("Fan info")) {

				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnBackButton();

					break;
				}

				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Locate Sensor")
					|| parameters.get(1).equalsIgnoreCase("Signal Strength")
					|| parameters.get(1).equalsIgnoreCase("Locate Motion Sensor")
					|| parameters.get(1).equalsIgnoreCase("Motion Sensor Signal Strength")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					flag = sensor.clickOnTestSensorBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}

			} else if (parameters.get(1).equalsIgnoreCase("Test Sensor")
					|| parameters.get(1).equalsIgnoreCase("Configuration Success")
					|| parameters.get(1).equalsIgnoreCase("keyfob Configuration Success")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "DONE": {
					flag = flag & sensor.clickOnDoneButton();
					break;
				}
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						Thread.sleep(3000);
						sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on \"+parameters.get(0)+\" button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Mount Sensor")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("sensor pairing help")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GET ADDITIONAL HELP": {
					flag = flag & sensor.clickOnGetAdditionalHelpButton();
					break;
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Setup Amazon Alexa")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SIGN IN": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isAmazonSignInVisible()) {
						flag = flag & bs.clickOnAmazonSignInButton();

					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("AMAZON ALEXA SETTINGS")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SIGN OUT": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isAmazonSignOutVisible()) {
						flag = flag & bs.clickOnAmazonSignOutVisible();
						Thread.sleep(3000);
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("MANAGE ALERTS")) {
				switch (parameters.get(0).toUpperCase()) {
				case "TEMPERATURE ALERT FOR THIS RANGE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isThermostatTempAlertRangeVisible()) {
						flag = flag & ts.clickOnThermostatTempAlertRange();
					}
					break;
				}
				case "BELOW TEMPERATURE RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectBelowTemperatureRangeValue(testCase, inputs,
							parameters.get(0));
					break;
				}
				case "ABOVE TEMPERATURE RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectAboveTemperatureRangeValue(testCase, inputs,
							parameters.get(0));
					break;
				}
				case "HUMIDITY ALERT FOR THIS RANGE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isThermostatHumidityAlertRangeVisible()) {
						flag = flag & ts.clickOnThermostatHumidityAlertRange();
					}
					break;
				}
				case "BELOW HUMIDITY RANGE": {
					flag = flag
							& HBNAEMEASettingsUtils.selectBelowHumidityRangeValue(testCase, inputs, parameters.get(0));
					break;
				}
				case "ABOVE HUMIDITY RANGE": {
					flag = flag
							& HBNAEMEASettingsUtils.selectAboveHumidityRangeValue(testCase, inputs, parameters.get(0));
					break;
				}

				}
			} // Schedule screen
			else if (parameters.get(1).equalsIgnoreCase("Scheduling")) {
				switch (parameters.get(0).toUpperCase()) {
				case "OPTION": {
					SchedulingScreen option = new SchedulingScreen(testCase);
					flag = flag & option.isScheduleOptionsVisible(2);
					flag = flag & option.isScheduleOptionsElementEnabled();
					if (flag) {
						flag = flag & option.clickOnScheduleOptionsButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully clicked on " + parameters.get(0) + " button");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click: " + parameters.get(1));
						}
					} else {
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " button disabled ");
					}
					break;
				}
				case "SCHEDULE OFF OVERLAY": {
					SchedulingScreen Schoffoverlay = new SchedulingScreen(testCase);
					flag = flag & Schoffoverlay.clickOnScheduleOffOverlay();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					if (!Schoffoverlay.isScheduleOffOverlayVisible(15)) {
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " screen disappered");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("thermostat2")) {
				switch (parameters.get(0).toUpperCase()) {
				case "PRIMARYCARD": {
					// inputs.setInputValue("LOCATION1_NAME",
					// inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					// inputs.setInputValue("LOCATION1_NAME",
					// inputs.getInputValue("LOCATION1_DEVICE2_NAME"));
					String device1Name = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					System.out.println(locInfo.getDeviceCountOfLocation());
					for (int i = 1; i <= locInfo.getDeviceCountOfLocation(); i++) {
						String str = inputs.getInputValue("LOCATION2_DEVICE" + i + "_NAME");
						if (str != null && !str.isEmpty()) {
							inputs.setInputValue("LOCATION1_DEVICE1_NAME", str);
							if (!inputs.getInputValue("LOCATION1_DEVICE1_NAME").equals(device1Name))

								flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
										inputs.getInputValue("LOCATION1_DEVICE2_NAME"));
							if (flag) {
								Keyword.ReportStep_Pass(testCase,
										"Successfully clicked on " + parameters.get(1) + " stat");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click: " + parameters.get(1) + " stat");
							}
						}
					}
				}
				break;
				}
			}
			// Amresh wld
			else if (parameters.get(1).equalsIgnoreCase("WLD Settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "TEMPERATURE UNIT": {
					WLDLeakDetectorSettings settings = new WLDLeakDetectorSettings(testCase);
					flag = flag & settings.clickonTemperatureUnit();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Temp Unit");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Temp unit Not clicked");
					}
					break;
				}
				case "TEMPERATURE UNIT IN FARENHEIT": {
					WLDLeakDetectorSettings settings = new WLDLeakDetectorSettings(testCase);
					flag = flag & settings.clickonTemperatureUnit();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Temp Unit");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Temp unit Not clicked");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("WLD Manage Alerts")) {// Amresh wld
				switch (parameters.get(0).toUpperCase()) {
				case "ALERT FOR THIS TEMPERATURE RANGE": {
					WLDManageAlerts ale = new WLDManageAlerts(testCase);
					flag = flag && ale.isAlertforthisRangeTemperaturePercentageButtonVissible();
					flag = flag && ale.clickAlertforthisRangeTemperaturePercentageButton();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Temperature Alert Percentage");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Temperature Alert Percentage Not clicked");
					}
					break;
				}
				case "ALERT FOR THIS HUMIDITY RANGE": {
					WLDManageAlerts ale = new WLDManageAlerts(testCase);
					flag = flag && ale.isAlertforthisRangeHumidityPercentageButtonVissible();
					flag = flag && ale.clickAlertforthisRangeHumidityPercentageButton();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Humidity Alert Percentage");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Temperature Alert Percentage Not clicked");
					}
					break;
				}
				}
			}
			// Amresh wld
			else if (parameters.get(1).equalsIgnoreCase("WLD Settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "TEMPERATURE UNIT": {
					WLDLeakDetectorSettings settings = new WLDLeakDetectorSettings(testCase);
					flag = flag & settings.clickonTemperatureUnit();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Temp Unit");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Temp unit Not clicked");
					}
					break;
				}
				case "TEMPERATURE UNIT IN FARENHEIT": {
					WLDLeakDetectorSettings settings = new WLDLeakDetectorSettings(testCase);
					flag = flag & settings.clickonTemperatureUnit();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on  Temp Unit");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Temp unit Not clicked");
					}
					break;
				}
				}
			}
			// select schedule off from option action sheet
			else if (parameters.get(1).equalsIgnoreCase("Option")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SCHEDULE OFF": {
					SchedulingScreen scheoff = new SchedulingScreen(testCase);
					flag = flag & scheoff.isScheduleOffButtonVisible();
					flag = flag & scheoff.clickOnScheduleOffButton();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Failed to click", true);
				}
				}
			}

			else if (parameters.get(1).equalsIgnoreCase("Security Settings")) {
				BaseStationSettingsScreen click = new BaseStationSettingsScreen(testCase);
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				switch (parameters.get(0).toUpperCase()) {
				case "MANAGE ALERTS": {
					if (!click.isManageAlertExist()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts Element does not exist");
					}
					flag = click.clickOnManageAlerts();
					break;
				}
				case "GEOFENCING": {
					// flag &= !click.isGeofencingEnabled();
					flag &= click.toggleGeofencingSwitch(testCase);
					break;
				}
				case "ENHANCED DETERRENCE": {
					// flag &= !click.isEnhancedDeterrenceEnabled();
					flag &= click.clickOnEnhancedDeterrenceOption(20);
					break;
				}
				case "OUTDOOR MOTION VIEWERS ON IN HOME MODE": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					// flag &= !click.isOutdoorMotionViewerOnInHomeModeEnabled();
					flag &= click.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
					break;
				}
				case "ENTRY-EXIT DELAY": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.waitAction(waitOptions(MobileUtils.getDuration(3000)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= !click.isEntryExitDelayEnabled();
					flag &= click.clickonEntryExistDelayoption();
					break;
				}
				case "BASE STATION VOLUME": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimension.getHeight() * .9)) .moveTo(0, -(int) (dimension.getHeight() *
						 * .6)).release().perform(); action.press(10, (int) (dimension.getHeight() *
						 * .9)) .moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= !click.isBaseStationVolumeEnabled();
					flag &= click.clickonbasestationvolumeoption();
					break;
				}
				case "RESET WI-FI": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimension.getHeight() * .9)) .moveTo(0, -(int) (dimension.getHeight() *
						 * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= !click.isBaseStationResetWifiEnabled();
					flag &= click.clickonbasestationresetwifioption();
					break;
				}
				case "DELETE": {
					flag &= click.clickOnDeleteDASButton();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"failed to click on " + parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("Video Quality Settings")) {
				CameraSettingsScreen Video = new CameraSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "AUTO": {
					flag = flag & Video.ClickOnVideoQualityAutoOption();
					break;
				}
				case "HIGH": {
					flag = flag & Video.ClickOnVideoQualityHighOption();
					break;
				}
				case "LOW": {
					flag = flag & Video.ClickOnVideoQualityLowOption();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Night Vision Settings")) {
				CameraSettingsScreen Video = new CameraSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "AUTO": {
					flag = flag & Video.ClickOnNightVisionAutoOption();
					break;
				}
				case "ON": {
					flag = flag & Video.ClickOnNightVisionONOption();
					break;
				}
				case "OFF": {
					flag = flag & Video.ClickOnNightVisionOFFOption();
					break;
				}

				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully click on " + parameters.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on " + parameters.get(0));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("GEOFENCE THIS LOCATION")) {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					if (gs.clickOnBackButton()) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
				}
				break;
				}
			} else if (parameters.get(1).equalsIgnoreCase("ENHANCED DETERRENCE")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "PLAY DOG BARK SOUND": {
					flag &= bs.clickonPlayDogBarkSoundoption();
					break;
				}
				case "PARTY IS ON": {
					flag &= bs.clickonPartyIsOnoption();
					break;
				}
				case "VACUUM": {
					flag &= bs.clickonvacuumoption();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully selected on " + parameters.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to selected on " + parameters.get(0));
				}
				break;
				}
			} else if (parameters.get(1).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "MOTION DETECTION": {
					if (cs.isMotionDetectionLabelVisible(testCase, 10)) {
						flag &= cs.clickOnMotionDetectionLabel();
					}
					break;
				}
				case "NIGHT VISION": {
					if (cs.isNightVisionLabelVisible(10)) {
						flag &= cs.clickOnNightVisionLabel();
					}
					break;
				}
				case "VIDEO QUALITY": {
					if (cs.isVideoQualityLabelVisible(10)) {
						flag &= cs.clickOnVideoQualityLabel();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully selected on " + parameters.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to selected on " + parameters.get(0));
				}
				break;
				}
			} else if (parameters.get(1).equalsIgnoreCase("GLOBAL DRAWER")) {
				GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GEOFENCE": {
					if (gd.isGeofenceOptionVisible()) {
						gd.clickOnGeofenceOption();
						Keyword.ReportStep_Pass(testCase, "Successfully selected on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to selected on " + parameters.get(0));
					}
					break;
				}
				case "LOG OUT": {
					if (gd.isLogoutOptionVisible()) {
						gd.clickOnLogoutOption();
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimension.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimension.getHeight() * .9)))
							.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						}
						if (gd.isLogoutOptionVisible()) {
							gd.clickOnLogoutOption();
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						}
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to clicked on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ACTIVITY HISTORY")) {
				ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "EDIT": {
					if (ah.isEditButtonVisible()) {
						flag &= ah.clickOnEditButton();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully selected the option: " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select the option: " + parameters.get(0));
					}
					break;
				}
				case "CANCEL": {
					if (ah.isCancelOptionVisible()) {
						ah.clickOnCancelOption();
						Keyword.ReportStep_Pass(testCase, "Successfully selected the option: " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select the option: " + parameters.get(0));
					}
					break;
				}
				case "A MESSAGE": {
					if (ah.isFirstMsgRadioButtonVisible()) {
						ah.clickOnFirstMsgRadioButton();
						Keyword.ReportStep_Pass(testCase,
								"Successfully selected first message radio button in Activity History Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select first message radio button in Activity History Screen");
					}
					break;
				}
				case "ALL MESSAGES": {
					if (ah.isSelectAllButtonVisible()) {
						ah.clickOnSelectAllButton();
						Keyword.ReportStep_Pass(testCase,
								"Successfully selected all messages in Activity History Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select all messages in Activity History Screen");
					}
					break;
				}
				case "DELETE": {
					if (ah.isDeletelButtonEnabled()
							&& ah.firstMsgFromActivityHistoryListBeforeDelete(testCase, inputs)) {
						flag &= ah.clickOnDeleteButton();
					}
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully selected the option: " + parameters.get(0));
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select the option: " + parameters.get(0));
					}
					break;
				}
				case "THE DELETE BUTTON": {
					if (ah.isDeletelButtonEnabled()) {
						ah.clickOnDeleteButton();
						Keyword.ReportStep_Pass(testCase, "Successfully selected the option: " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select the option: " + parameters.get(0));
					}
					break;
				}
				case "GEOFENCE HOME EVENT - SUCCESS": {
					List<WebElement> messageTitle = ah.getActivityHistoryMsgTitle();
					int listCount = messageTitle.size();
					System.out.println(listCount);
					for (int i = 0; i <= listCount; i++) {
						String activityHistory = messageTitle.get(i).getText();
						System.out.println(messageTitle.get(i).getText());
						System.out.println(activityHistory);
						if (activityHistory.equalsIgnoreCase("Geofence home event - success")) {
							ah.clickOnMessage();
							Keyword.ReportStep_Pass(testCase,
									"Successfully clicked on the message" + parameters.get(0));
							break;
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on the message" + parameters.get(0));
						}
					}
					break;
				}
				case "GEOFENCE AWAY EVENT - SUCCESS": {
					List<WebElement> messageTitle = ah.getActivityHistoryMsgTitle();
					int listCount = messageTitle.size();
					System.out.println(listCount);
					for (int i = 0; i <= listCount; i++) {
						String activityHistory = messageTitle.get(i).getText();
						System.out.println(messageTitle.get(i).getText());
						System.out.println(activityHistory);
						if (activityHistory.equalsIgnoreCase("Geofence away event - success")) {
							ah.clickOnMessage();
							Keyword.ReportStep_Pass(testCase,
									"Successfully clicked on the message" + parameters.get(0));
							break;
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on the message" + parameters.get(0));
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("GEOFENCE RADIUS")) {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "UPDATE GEOFENCE CENTER": {
					if (gs.isUpdateGeofenceCenterButtonVisible()) {
						flag &= gs.clickOnUpdateGeofenceCenterButton();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "SAVE BUTTON": {
					if (gs.isSaveButtonInGeofenceRadiusScreenVisible()) {
						flag &= gs.clickOnSaveButtonInGeofenceRadiusScreen();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "BACK BUTTON": {
					if (gs.isBackButtonInGeofenceRadiusScreenVisible()) {
						flag &= gs.clickOnBackButtonInGeofenceRadiusScreen();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ADD NEW DEVICE DASHBOARD")) {
				AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
				String getFooterTextDisplayedInAddNewDeviceScreen = null;
				String getCurrentCountryFromAddNewDeviceScreen = null;
				switch (parameters.get(0).toUpperCase()) {
				case "DONE" : {
					if(adn.isDoneButtonVisible()) {
						flag &=adn.clickOnDoneButton();
					}else {
						flag = false;
					}
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "CHANGE COUNTRY": {
					if (adn.isFooterTextInAddNewDeviceScreenVisible()) {
						getFooterTextDisplayedInAddNewDeviceScreen = adn.getFooterTextDisplayedInAddNewDeviceScreen();
						String[] footerText = getFooterTextDisplayedInAddNewDeviceScreen.split("\\W+");
						getCurrentCountryFromAddNewDeviceScreen = footerText[footerText.length - 2] + " "
								+ footerText[footerText.length - 1];
						System.out.println(
								"getCurrentCountryFromAddNewDeviceScreen: " + getCurrentCountryFromAddNewDeviceScreen);
						if (getCurrentCountryFromAddNewDeviceScreen.contains("for")) {
							String[] currentCountryName = getCurrentCountryFromAddNewDeviceScreen.split("\\W+");
							getCurrentCountryFromAddNewDeviceScreen = currentCountryName[currentCountryName.length - 1];
							System.out.println("getCurrentCountryFromAddNewDeviceScreen after removing for: "
									+ getCurrentCountryFromAddNewDeviceScreen);
							inputs.setInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN",
									getCurrentCountryFromAddNewDeviceScreen);
						} else {
							inputs.setInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN",
									getCurrentCountryFromAddNewDeviceScreen);
						}
					}
					if (adn.isChangeCountryLinkVisible()) {
						flag &= adn.clickOnChangeCountryLink();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("DASHBOARD")) {
				Dashboard d = new Dashboard(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "LOCATION DROP DOWN ARROW": {
					if (testCase.isTestSuccessful()
							|| VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.FLAG) {
						flag &= d.selectLocationDropDownArrow();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Skipping this step since default selected location is not: "
										+ VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONFROMCHIL);
					}
					break;
				}
				default: {
					flag &= d.selectLocationFromDashBoard(testCase, parameters.get(0));
				}
				}
			}
			else if (parameters.get(1).equalsIgnoreCase("PLEASE CONFIRM YOUR COUNTRY")) {
				// Write code for selecting country from the list of countries displayed in
				// Please confirm your country screen
				/*
				 * flag &= DashboardUtils.enterCountryNameAndSelectItInConfirmYourCountryScreen(
				 * testCase, inputs, parameters.get(0));
				 */
				switch (parameters.get(0).toUpperCase()) {
				case "CURRENT COUNTRY":
				case "DEFAULT COUNTRY": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					if (ads.isCurrentCountryButtonVisible()) {
						flag &= ads.clickOnCurrentCountryButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on " + parameters.get(0));
						}
					}
					break;
				}

				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on " + parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("EDIT ADDRESS")) {
				AddressScreen ads = new AddressScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CHANGE COUNTRY": {
					if (ads.isChangeCountryButtonInEditAddressScreenVisible()) {
						flag &= ads.clickOnChangeCountryButtonInEditAddressScreen();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "SAVE BUTTON": {
					if (ads.isSaveButtonEnabledInEditAddressScreen()) {
						flag &= ads.clickOnSaveButtonInEditAddressScreen();
						if (ads.isEditAddressScreenTitleVisible()
								&& ads.isChangeCountryButtonInEditAddressScreenVisible()
								&& ads.isSaveButtonEnabledInEditAddressScreen()) {
							Keyword.ReportStep_Pass(testCase, "User is still in Edit Address Screen. Clicking on the "
									+ parameters.get(0) + " again.");
							flag &= ads.clickOnSaveButtonInEditAddressScreen();
						} else {
							Keyword.ReportStep_Pass(testCase, "User is not in Edit Address Screen.");
						}
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Edit Address Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ACCOUNT DETAILS")) {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "SAVE BUTTON": {
					if (eas.isSaveButtonEnabled()) {
						flag &= eas.clickOnSaveButtonScreen();
						if (eas.isEditAccountScreenTitleVisible() && eas.isNameLabelInEditAccountScreenVisible()
								&& eas.isNameValueInEditAccountScreenVisible()) {
							Keyword.ReportStep_Pass(testCase, "User is still in Edit Account Screen. Clicking on the "
									+ parameters.get(0) + " again.");
							flag &= eas.clickOnSaveButtonScreen();
						} else {
							Keyword.ReportStep_Pass(testCase, "User is not in Edit Account Screen.");
						}
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Edit Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "CHANGE PASSWORD": {
					if (eas.isChangePasswordInEditAccountScreenVisible()) {
						flag &= eas.clickOnChangePasswordInEditAccountScreen();
						if (eas.isEditAccountScreenTitleVisible()) {
							Keyword.ReportStep_Pass(testCase, "User is still in Edit Account Screen. Clicking on the "
									+ parameters.get(0) + " again.");
							flag &= eas.clickOnChangePasswordInEditAccountScreen();
						} else {
							Keyword.ReportStep_Pass(testCase, "User is not in Edit Account Screen.");
						}
						// EditAccountUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Edit Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "DELETE ACCOUNT": {
					if (eas.isDeleteAccountButtonInEditAccountScreenVisible()) {
						flag &= eas.clickOnDeleteAccountButtonInEditAccountScreen();
						flag &= EditAccountUtils.waitForProgressBarToComplete(testCase, "CHECKING SPINNER", 2);
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Edit Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "NAME" :{
					if(eas.isNameValueInEditAccountScreenVisible()) {
						flag &= eas.clickOnNameValueArrowInEditAccountScreen();
						if(flag) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully clicked on " + parameters.get(0) + " in Edit Account Screen.");
						}else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on " + parameters.get(0));
						}
					}

					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("CHANGE PASSWORD")) {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "SAVE BUTTON": {
					if (eas.isSaveButtonInChangePasswordScreenVisible()) {
						flag &= eas.clickOnSaveButtonInChangePasswordScreen();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Change Password Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("DELETE ACCOUNT")) {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "LEARN HOW TO DELETE A DEVICE": {
					if (eas.isLearnHowToDeleteADeviceLinkVisible()) {
						flag &= eas.clickOnLearnHowToDeleteADeviceLink();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Delete Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "LEARN HOW TO CANCEL A MEMBERSHIP": {
					if (eas.isLearnHowToCancelAMembershipLinkVisible()) {
						flag &= eas.clickOnLearnHowToCancelAMembershipLink();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Delete Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "CLOSE BUTTON": {
					if (eas.isCloseButtonInDeleteAccountScreenVisible()) {
						flag &= eas.clickOnCloseButtonInDeleteAccountScreen();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Delete Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "DELETE ACCOUNT BUTTON": {
					if (eas.isDeleteAccountButtonInDeleteAccountScreenVisible()) {
						flag &= eas.clickOnDeleteAccountButtonInDeleteAccountScreen();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in Delete Account Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("LEARN HOW TO DELETE A DEVICE")) {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CLOSE BUTTON": {
					if (eas.isCloseButtonInLearnHowToDeleteADeviceScreenVisible()) {
						flag &= eas.clickOnCloseButtonInLearnHowToDeleteADeviceScreen();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0)
						+ " in Learn How To Delete A Device Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("LEARN HOW TO CANCEL A MEMBERSHIP")) {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CLOSE BUTTON": {
					if (eas.isCloseButtonInLearnHowToCancelAMembershipScreenVisible()) {
						flag &= eas.clickOnCloseButtonInLearnHowToCancelAMembershipScreenScreen();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0)
						+ " in Learn How To Cancel A Membership Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ABOUT THE APP")) {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "PRIVACY POLICY AND EULA": {
					if (atas.isPrivacyPolicyAndEULAOptionInAboutTheAppVisible()) {
						flag &= atas.clickOnPrivacyPolicyAndEULAOptionInAboutTheApp();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in About the App Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "ACKNOWLEDGEMENTS": {
					if (atas.isAcknowledgementsOptionInAboutTheAppVisible()) {
						flag &= atas.clickOnAcknowledgementsOptionInAboutTheApp();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in About the App Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "GET HELP": {
					if (atas.isGetHelpOptionInAboutTheAppVisible()) {
						flag &= atas.clickOnGetHelpOptionInAboutTheApp();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in About the App Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "RATE THE APP": {
					if (atas.isRateTheAppOptionInAboutTheAppVisible()) {
						flag &= atas.clickOnRateTheAppOptionInAboutTheApp();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully clicked on " + parameters.get(0) + " in About the App Screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("RATE THE APP")) {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				switch (parameters.get(0)) {
				case "1": {
					if (atas.isRateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()) {
						flag &= atas.clickOnRateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup();
					}
					break;
				}
				case "2": {
					if (atas.isRateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()) {
						flag &= atas.clickOnRateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup();
					}
					break;
				}
				case "3": {
					if (atas.isRateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()) {
						flag &= atas.clickOnRateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup();
					}
					break;
				}
				case "4": {
					if (atas.isRateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()) {
						flag &= atas.clickOnRateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup();
					}
					break;
				}
				case "5": {
					if (atas.isRateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()) {
						flag &= atas.clickOnRateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup();
					}
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully clicked on " + parameters.get(0) + " in Rate the App Popup.");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on " + parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("APP FEEDBACK")) {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "SEND FEEDBACK BUTTON": {
					if (atas.isSendFeedbackButtonEnabled()) {
						flag &= atas.selectSendFeedbackButton();
					}
					break;
				}
				case "CLOSE BUTTON": {
					if (atas.isCloseButtonInAppFeedbackScreenVisible()) {
						flag &= atas.clickOnCloseButtonInAppFeedbackScreen();
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("FAQS")) {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GENERAL": {
					if (faqsScreen.isGeneralOptionInFAQsScreenVisible()) {
						flag &= faqsScreen.clickOnGeneralOptionInFAQsScreen();
					}
					break;
				}
				case "THERMOSTAT": {
					if (faqsScreen.isThermostatOptionInFAQsScreenVisible()) {
						flag &= faqsScreen.clickOnThermostatOptionInFAQsScreen();
					}
					break;
				}
				case "WATER LEAK DETECTOR": {
					if (faqsScreen.isWaterLeakDetectorOptionInFAQsScreenVisible()) {
						flag &= faqsScreen.clickOnWaterLeakDetectorOptionInFAQsScreen();
					}
					break;
				}
				case "CAMERA": {
					if (faqsScreen.isCameraOptionInFAQsScreenVisible()) {
						flag &= faqsScreen.clickOnCameraOptionInFAQsScreen();
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("GENERAL") || parameters.get(1).equalsIgnoreCase("THERMOSTAT")
					|| parameters.get(1).equalsIgnoreCase("WATER LEAK DETECTOR")
					|| parameters.get(1).equalsIgnoreCase("CAMERA")) {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "A QUESTION": {
					if (faqsScreen.isFirstQuestionDisplayedInTheScreen()
							&& faqsScreen.isFirstQuestionDisplayedInTheScreen()) {
						inputs.setInputValue("FIRST_QUESTION_IN_THE_SCREEN",
								faqsScreen.getFirstQuestionDisplayedInTheScreen());
						flag &= faqsScreen.clickOnFirstQuestionDisplayedInTheScreen();
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("QUESTION")) {
				FAQsScreen faqsScreen = new FAQsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "YES BUTTON FROM WAS THIS HELPFUL SECTION": {
					if (faqsScreen.isYESButtonInWasThisHelpfulTextInQuestionScreenVisible()) {
						flag &= faqsScreen.clickOnYESButtonInWasThisHelpfulTextInQuestionScreen();
					}
					break;
				}
				case "NO BUTTON FROM WAS THIS HELPFUL SECTION": {
					if (faqsScreen.isNOButtonInWasThisHelpfulTextInQuestionScreenVisible()) {
						flag &= faqsScreen.clickOnNOButtonInWasThisHelpfulTextInQuestionScreen();
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("HONEYWELL HOME")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CREATE ACCOUNT": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountButtonDisplayed()) {
						flag &= cas.clickOnCreateAccountButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Unable to click on Create Account button");
						}

						break;
					}
				}
				case "LOGIN": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountLoginButtonDisplayed()) {
						flag &= cas.clickOnLoginButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Unable to click on Login button");
						}
					}

					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("LOGIN")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					LoginScreen ls = new LoginScreen(testCase);
					if (ls.isLoginCancelButtonDisplayed()) {
						flag &= ls.clickOnCancelButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Unable to click on Cancel button");
						}
						break;
					}
				}
				// Login button in Login screen
				case "LOGIN BUTTON": {
					LoginScreen ls = new LoginScreen(testCase);
					if (ls.isLoginButtonDisplayed()) {
						flag &= ls.clickOnLoginButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Unable to click on Login button");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("CREATE ACCOUNT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SIGN ME UP TOGGLE BUTTON": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (!cas.isCreateAccountSignMeUpToggleButtonEnabled()) {
						flag &= cas.isCreateAccountClickOnSignMeUpToggleButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Sign me up for exclusive updates toggle button is clicked");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sign me up for exclusive updates toggle button is not clicked");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sign me up for exclusive updates toggle button is enabled by default");
					}
					break;
				}
				case "BACK BUTTON": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					/*if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						flag &= cas.isCreateAccountClickOnBack();
					} else {
						// ios
						flag &= cas.isCreateAccountClickOnBackButtonOnIOS();

					}*/
					flag &= cas.isCreateAccountClickOnBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Back or Cancel button is clicked");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Back or Cancel button not clicked");
					}
					break;
				}
				case "COUNTRY": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountCountrySelectionDisplayed()) {
						flag &= cas.isCreateAccountClickOnCountrySelection();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Country selection button is clicked");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Country selection button not clicked");
					}
					break;
				}
				case "PRIVACY STATEMENT": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
						flag &= cas.isCreateAccountClickOnPrivacyStatementLink();
					} else {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9)))
							.waitAction(waitOptions(MobileUtils.getDuration(2000)))
							.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
						if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
							flag &= cas.isCreateAccountClickOnPrivacyStatementLink();
							if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
								flag &= cas.isCreateAccountClickOnPrivacyStatementLink();
							}
						} else {
							flag = false;
						}
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Privacy Statement link is clicked");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Privacy Statement link is not clicked");
					}
					break;
				}
				case "END USER LICENSE AGREEMENT": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountEULALinkDisplayed()) {
						flag &= cas.isCreateAccountClickOnEULALink();
					} else {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9)))
							.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
						if (cas.isCreateAccountEULALinkDisplayed()) {
							flag &= cas.isCreateAccountClickOnEULALink();
						} else {
							flag = false;
						}
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "EULA link is clicked");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "EULA link is not clicked");
						}
					}
					break;
				}
				case "CREATE BUTTON": {
					CreateAccountScreen cas = new CreateAccountScreen(testCase);
					if (cas.isCreateAccountRegisterButtonDisplayed()) {
						flag &= cas.clickOnCreateAccountRegisterButton();
					} else {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						System.out.println("$$$$$$$$$$$$$$: " + testCase.getPlatform());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9)))
							.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
						if (cas.isCreateAccountRegisterButtonDisplayed()) {
							flag &= cas.clickOnCreateAccountRegisterButton();
						} else {
							flag &= false;
						}
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Create Account Register button is clicked");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Account Register button is not clicked");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ACTIVATE ACCOUNT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK BUTTON": {
					ActivateAccountScreen aas = new ActivateAccountScreen(testCase);
					if (aas.isActivateAccountBackButtonDisplayed()) {
						flag &= aas.isActivateAccountClickOnBackButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Close button is clicked");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Close button is not clicked");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("PRIVACY STATEMENT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					PrivacyStatementScreen pStatementScreen = new PrivacyStatementScreen(testCase);
					if (pStatementScreen.isBackButtonInPrivacyStatmentScreenVisible()) {
						flag &= pStatementScreen.clickOnBackButtonInPrivacyStatmentScreen();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Clicked on back button in Privacy statement screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on back button in Privacy statement screen");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("END USER LICENSE AGREEMENT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					EndUserLicenseAgreementScreen eulaScreen = new EndUserLicenseAgreementScreen(testCase);
					if (eulaScreen.isBackButtonInEULAScreenVisible()) {
						flag &= eulaScreen.clickOnBackButtonInEULAScreen();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Clicked on back button in End user License Agreement screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on back button in End user License Agreement screen");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("NEW AGREEMENT")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					AddNewDeviceScreen and = new AddNewDeviceScreen(testCase);
					if (and.isCancelButtonInNewAgreementScreenVisible()) {
						flag &= and.clickOnCancelButtonInNewAgreementScreen();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Clicked on Cancel button in New Agreement screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Cancel button in New Agreement screen");
						}
					}
					break;
				}
				case "ACCEPT": {
					AddNewDeviceScreen and = new AddNewDeviceScreen(testCase);
					if (and.isAcceptButtonInNewAgreementScreenVisible()) {
						flag &= and.clickOnAcceptButtonInNewAgreementScreen();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Clicked on Accept button in New Agreement screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Accept button in New Agreement screen");
						}
					}
					break;
				}
				case "PRIVACY POLICY AND EULA LINK": {
					AddNewDeviceScreen and = new AddNewDeviceScreen(testCase);
					if (and.isPrivacyPolicyAndEULALinkInNewAgreementScreenVisible()) {
						flag &= and.clickOnPrivacyPolicyAndEULALinkInNewAgreementScreen();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Clicked on Privacy Policy and EULA Link in New Agreement screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Privacy Policy and EULA Link in New Agreement screen");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("PRIVACY POLICY AND EULA FOR SELECTED COUNTRY")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					AddNewDeviceScreen and = new AddNewDeviceScreen(testCase);
					if (and.isBackButtonVisible()) {
						flag &= and.clickOnBackButton();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Clicked on Back button in Privacy Policy and EULA screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Back button in Privacy Policy and EULA screen");
						}
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("WEATHER FORECAST")) {
				switch (parameters.get(0).toUpperCase()) {
				case "FARENHEIT": {
					WeatherForecastScreen w = new WeatherForecastScreen(testCase);
					if (w.whichWeatherTempUnitIsEnabled().contains("F")) {
						Keyword.ReportStep_Pass(testCase, "Farenheit Unit is already enabled");
					} else {
						w.clickOnFarenheitUnit();
						Keyword.ReportStep_Pass(testCase, "Farenheit Unit is selected and enabled");
					}
					break;
				}
				case "CELSIUS": {
					WeatherForecastScreen w = new WeatherForecastScreen(testCase);
					if (w.whichWeatherTempUnitIsEnabled().contains("C")) {
						Keyword.ReportStep_Pass(testCase, "Celsius Unit is already enabled");
					} else {
						w.clickOnCelsiusUnit();
						Keyword.ReportStep_Pass(testCase, "Celsius Unit is selected and enabled");
					}
					break;
				}
				case "BACK BUTTON": {
					WeatherForecastScreen w = new WeatherForecastScreen(testCase);
					flag &= w.clickOnBackIcon();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Back button is clicked");
					} else {
						w.clickOnCelsiusUnit();
						Keyword.ReportStep_Pass(testCase, "CBack button is not clicked");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("MOBILE DEVICE LOCATION SETTINGS")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ENABLE LOCATION": {
					MobileDeviceSettingsScreen mdls = new MobileDeviceSettingsScreen(testCase);
					if (mdls.isMobileDeviceLocationDisabled()) {
						flag &= mdls.clickOnMobileDeviceLocationToEnable();
						if (flag) {
							if (mdls.isAgreeLocationAccracyDisplayed(50)) {
								mdls.clickToAgreeLocationAccuracy();
								Keyword.ReportStep_Pass(testCase, "Clicked to enable Mobile device location service");
							} else if (!mdls.isAgreeLocationAccracyDisplayed(50)) {
								Keyword.ReportStep_Pass(testCase, "Mobile device location service is enabled");
							}

						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Mobile device location service is disabled");
						}
					} else if (mdls.isMobileDeviceLocationEnabled()) {
						Keyword.ReportStep_Pass(testCase, "Mobile device location service is already enabled");
					}
					break;
				}
				}
			}else if(parameters.get(1).equalsIgnoreCase("USERS")) {
				ManageUsersScreen mus= new ManageUsersScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "INVITE NEW USER" : {
					if(mus.isInviteNewUserButtonVisible()) {
						flag &=mus.clickOnInviteNewUserButton();
					}
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				case "DELETE INVITED EMAIL" : {
					if(mus.isDeleteInvitedEmailIconVisible()) {
						flag &=mus.clickOnDeleteInvitedEmailIcon();
					}
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				}				
			} else if(parameters.get(1).equalsIgnoreCase("NAME ACCOUNT DETAILS")) {
				NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "SAVE BUTTON" : {
					if(neas.isSaveButtonEnabledInNameEditAccountScreen()) {
						flag &=neas.clickOnSaveButtonInNameEditAccountScreen();
					}
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0));
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on " + parameters.get(0));
					}
					break;
				}
				}
			  }

	} catch (Exception e) {
		flag = false;
		Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
	}
	return flag;
}

@Override
@AfterKeyword
public boolean postCondition() throws KeywordException {
	return flag;
}
}
