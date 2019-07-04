package com.honeywell.keywords.lyric.common;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.util.ArrayList;
/*
 import com.honeywell.account.information.*;
import com.honeywell.commons.coreframework.*;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.*;
import com.honeywell.lyric.utils.*;
import com.honeywell.screens.*;
 */

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.das.utils.DASActivityLogsUtils;
import com.honeywell.lyric.das.utils.DASSensorUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.GlobalDrawerScreen;
import com.honeywell.screens.ManageUsersScreen;
import com.honeywell.screens.OSPopUps;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.SensorStatusScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.VacationHoldScreen;
import com.honeywell.screens.WLDConfigurationScreen;
import com.honeywell.screens.WLDLeakDetectorSettings;
import com.honeywell.screens.WLDManageAlerts;
import com.honeywell.screens.WLDSolutionCard;
import com.honeywell.screens.WeatherForecastScreen;
import com.honeywell.screens.ZwaveScreen;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

public class NavigateToScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> screen;
	public boolean flag = true;

	public NavigateToScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> screen) {
		this.testCase = testCase;
		this.screen = screen;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@SuppressWarnings({ "static-access", "null" })
	@Override
	@KeywordStep(gherkins = "^user navigates to \"(.+)\" screen from the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (screen.get(1).equalsIgnoreCase("Alarm history")) {
				switch (screen.get(0).toUpperCase()) {
				case "ALARM": {
					AlarmScreen open = new AlarmScreen(testCase);
					open.closeAlarmHistory();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR STATUS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACTIVITY LOG": {
					SensorStatusScreen sensorScreen = new SensorStatusScreen(testCase);
					flag = flag & sensorScreen.clickOnSensorStatusScreenBack(testCase);
					flag = flag & DASActivityLogsUtils.openActivityLogs(testCase);
					break;
				}
				case "SECURITY SOLUTION CARD": {
					SensorSettingScreen ssc = new SensorSettingScreen(testCase);
					SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
					if (ssc.isSensorsScreenTitleVisible()) {
						flag = flag & sc.clickOnBackButton();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SWITCH PRIMARY CARD")
					|| screen.get(1).equalsIgnoreCase("DIMMER PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					/*
					 * PrimaryCard sensorScreen = new PrimaryCard(testCase); flag = flag &
					 * sensorScreen.clickOnBackButton();
					 */
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}

				}
			} else if (screen.get(1).equalsIgnoreCase("CAMERA SOLUTION CARD")
					&& screen.get(0).equalsIgnoreCase("DASHBOARD")) {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				flag = flag & cs.clickOnBackButtonInCameraSolutionCardScreen();
			} else if (screen.get(1).equalsIgnoreCase("CAMERA SOLUTION CARD")
					&& screen.get(0).equalsIgnoreCase("Activity log")) {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				if (cs.isActivityLogExists()) {
					flag = flag & cs.clickActivityLog();
				} else {
					flag = false;
				}
			} else if (screen.get(1).equalsIgnoreCase("CAMERA SOLUTION CARD")
					&& screen.get(0).equalsIgnoreCase("DASHBOARD")) {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				flag = flag & cs.clickOnBackButtonInCameraSolutionCardScreen();
			} else if (screen.get(1).equalsIgnoreCase("THERMOSTAT DASHBOARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					PrimaryCard sensorScreen = new PrimaryCard(testCase);
					if (sensorScreen.isCogIconVisible()) {
						flag = flag & sensorScreen.clickOnCogIcon();
					}
					break;
				}
				case "THERMOSTAT SOLUTION CARD": {
					Dashboard sensorScreen = new Dashboard(testCase);
					flag = flag & sensorScreen.NavigatetoThermostatDashboard();
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ZWAVE DEVICES")) {
				switch (screen.get(0).toUpperCase()) {
				case "GENERAL INCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					if (!testCase.getPlatform().toUpperCase().contains("IOS")) {
						BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
						flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ZWAVEDEVICES);
					}
					zwaveScreen.clickZwaveUtilitiesMenu();
					flag = flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
					break;
				}
				case "GENERAL EXCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					if (!testCase.getPlatform().toUpperCase().contains("IOS")) {
						BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
						flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ZWAVEDEVICES);
					}
					zwaveScreen.clickZwaveUtilitiesMenu();
					flag = flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
					break;
				}
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromZwaveDevicesSettings(testCase, inputs);
					break;
				}
				case "SWITCH SETTINGS": {
					ZwaveScreen zs = new ZwaveScreen(testCase);
					flag = flag & zs.ClickSwitchSettingFromZwaveDevices();
					break;

				}
				case "DIMMER SETTINGS": {
					ZwaveScreen zs = new ZwaveScreen(testCase);
					flag = flag & zs.ClickDimmerSettingFromZwaveDevices();
					break;

				}
				case "SWITCH PRIMARY CARD": {
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Switch1");
					break;
				}
				case "DIMMER PRIMARY CARD": {
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Dimmer1");
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SWITCH SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					break;
				}
				case "SWITCH PRIMARY CARD VIA PRIMARY CARD": {
					flag = flag & DASZwaveUtils.navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
					break;
				}
				case "SWITCH PRIMARY CARD VIA ZWAVE DEVICES": {
					flag = flag
							& DASZwaveUtils.navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToSecuritySettingsFromZwaveDevices(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToSwitchPrimaryCardFromDashboard(testCase);
					break;
				}
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromZwaveIndividualDeviceSettings(testCase, inputs);
					break;
				}
				case "DASHBOARD VIA PRIMARY CARD": {
					flag = flag
							& DASZwaveUtils.navigateToPrimaryCardFromZwaveIndividualDeviceSettings(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					break;
				}
				case "DASHBOARD VIA ZWAVE CARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("DIMMER SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
					break;
				}
				case "DIMMER PRIMARY CARD VIA PRIMARY CARD": {
					flag = flag & DASZwaveUtils.navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
					break;
				}
				case "DIMMER PRIMARY CARD VIA ZWAVE DEVICES": {
					flag = flag
							& DASZwaveUtils.navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToSecuritySettingsFromZwaveDevices(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					flag = flag & DASZwaveUtils.navigateToDimmerPrimaryCardFromDashboard(testCase);
					break;
				}
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromZwaveIndividualDeviceSettings(testCase, inputs);
					break;
				}
				case "DASHBOARD VIA ZWAVE CARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ZWAVE Utilities")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.navigateToZwaveDevicesFromZwaveUtilitiesSettings(testCase, inputs);
					break;
				}
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromZwaveUtilitiesSettings(testCase, inputs);
					break;
				}
				case "ZWAVE DEVICE THROUGH GENERAL INCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag = flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
					break;
				}
				case "ZWAVE DEVICE THROUGH GENERAL EXCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag = flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}
			// Amresh H297378
			else if (screen.get(1).equalsIgnoreCase("TEMPERATURE GRAPH")) {
				switch (screen.get(0).toUpperCase()) {
				case "HUMIDITY GRAPH": {
					WLDSolutionCard humi = new WLDSolutionCard(testCase);
					flag = flag & humi.clickOnHumidityGraphTitle();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "HUMIDITY GRAPH Tab was Clicked");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Unable to click HUMIDITY GRAPH Tab");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("WLD SOLUTION CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "LEAK DETECTOR CONFIGURATION": {
					WLDSolutionCard humi = new WLDSolutionCard(testCase);
					flag = flag & humi.navigateFromPrimaryCardToLeakDetectorConfigurationScreen();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated to from WLD Solution card to Config screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Navigated ");
					}
					break;
				}
				case "UPDATE FREQUENCY": {
					WLDSolutionCard humi = new WLDSolutionCard(testCase);
					flag = flag & humi.navigateFromSolutionCardToUpdateFrequencyScreen();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Navigated to from WLD Solution card to Update Frequency screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Navigated ");
					}
					break;
				}
				case "LEAK DETECTOR SETTINGS": {
					WLDSolutionCard humi = new WLDSolutionCard(testCase);
					flag = flag & humi.navigateFromSolutionCardToLeakDetectorSettingsScreen();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Navigated to from WLD Solution card to Update Frequency screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Navigated ");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}
			// Amresh wld
			else if (screen.get(1).equalsIgnoreCase("WLD SETTINGS")) {
				WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);

				switch (screen.get(0).toUpperCase()) {

				case "DASHBOARD": {
					flag = flag && set.navigateFromWLDSettingsScreenToPrimaryCard();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Settings to Primary Card");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				case "UPDATE FREQUENCY": {
					flag = flag && set.navigateFromWLDSettingsScreenToUpdateFrequencyCard();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Settings to Update Frequency");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("LEAK DETECTOR CONFIGURATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					WLDConfigurationScreen set = new WLDConfigurationScreen(testCase);
					flag = flag & set.navigateFromWLDConfigurationScreenToDashboard(testCase);
					break;
				}
				case "WLD SOLUTION CARD": {
					WLDConfigurationScreen set = new WLDConfigurationScreen(testCase);
					flag = flag & set.navigateFromWLDConfigurationScreenToSolutionCard(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}
			// Navigation from Dashboard
			else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "KEYFOB CONFIGURATION SUCCESS": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							"Base Station Configuration");
					flag = flag & security.clickOnSensorButton();
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollKeyfob(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "keyfob");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.editKeyfobName("keyfob");
					break;
				}
				case "SENSOR OVERVIEW": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							"Base Station Configuration");
					flag = flag & security.clickOnSensorButton();
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					break;
				}
				case "SENSOR KEYFOB OVERVIEW": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							"Base Station Configuration");
					flag = flag & security.clickOnSensorButton();
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollKeyfob(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "keyfob");
					break;
				}
				case "KEYFOB OVERVIEW": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollKeyfob(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "KEYFOB");
					break;
				}
				case "NAME KEYFOB": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollKeyfob(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "KEYFOB");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "MOTION SENSOR SETTINGS":
				case "WINDOW ACCESS SETTINGS":
				case "DOOR ACCESS SETTINGS":
				case "ISMV SENSOR SETTINGS":
				case "OSMV SENSOR SETTINGS": {
					DASSensorUtils.navigateToSensorTypeSettingsFromDashboard(screen.get(0).toUpperCase(), inputs,
							testCase);
					break;
				}
				case "ACTIVITY HISTORY": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MESSAGES)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Activity history menu from Global drawer");
						} else {
							// Fetching messages
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "HONEYWELL MEMBERSHIP": {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MEMBERSHIPSUBSCRIPTION)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Honeywell Membership menu from Global drawer");
						} else {
							// Fetching Messages
							sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MEMBERSHIPSUBSCRIPTION);
							// Keyword.ReportStep_Pass(testCase,"Honeywell Membership page opened.");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "GEOFENCE SETTING": {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.GEOFENCE)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "GEOFENCE RADIUS": {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.GEOFENCE)) {
							if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.GEOFENCE)) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not click on Add new device menu from Global drawer");
							}
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "ZWAVE CONTROLLER DETAILS": {
					flag = flag & DASZwaveUtils.navigateToControllerDetailsFromDashboard(testCase);
					break;
				}
				case "ZWAVE DEVICE THROUGH GENERAL INCLUSION": {
					flag = flag & DASZwaveUtils.navigateToGeneralInclusionFromDashboard(testCase);
					break;
				}
				case "ZWAVE DEVICE THROUGH GENERAL EXCLUSION": {
					flag = flag & DASZwaveUtils.navigateToGeneralExclusionFromDashboard(testCase);
					break;
				}
				case "SWITCH PRIMARY CARD": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Switch1");
					/*
					 * if(LyricUtils.verifyDeviceDisplayedOnDashboard(testCase, "Switch1")){ flag =
					 * flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Switch1"); }else
					 * if(LyricUtils.verifyDeviceDisplayedOnDashboard(testCase, "Switch 001")){ flag
					 * = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Switch 001");
					 * inputs.setInputValue("LOCATION1_SWITCH1_NAME","Switch 001"); }else{
					 * ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Switch found"); }
					 */
					break;
				}
				case "SWITCH SETTINGS": {
					flag = flag & DASZwaveUtils.navigateToSwitchSettingsFromDashboard(testCase);
					break;
				}
				case "SWITCH SETTINGS VIA ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.navigateToSwitchSettingsFromDashboardViaZwaveDevices(testCase);
					break;
				}
				case "ZWAVE UTILITIES": {
					flag = DASZwaveUtils.navigateToZwaveUtilitiesFromDashboard(testCase);
					break;
				}
				case "ZWAVE DEVICES": {
					flag = DASZwaveUtils.navigateToZwaveDevicesFromDashboard(testCase);
					break;
				}
				case "DIMMER PRIMARY CARD": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Dimmer1");
					break;
				}
				case "DIMMER SETTINGS": {
					flag = flag & DASZwaveUtils.navigateToDimmerSettingsFromDashboard(testCase);
					break;
				}
				case "DIMMER SETTINGS VIA ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.navigateToDimmerSettingsFromDashboardViaZwaveDevices(testCase);
					break;
				}
				case "ZWAVE INCLUSION THROUGH ADD NEW DEVICE ICON": {
					flag = flag & DASZwaveUtils.navigateToAddDeviceScreenFromDashboardThroughIcon(testCase);
					break;
				}
				case "ADD NEW DEVICE DASHBOARD": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.isCloseButtonInHoneywellRatingPopupVisible(5)) {
						ds.clickOnCloseButtonInHoneywellRatingPopup();
					}
					if (ds.isDoneButtonInWeatherForecastIsVisible(20)) {
						ds.clickOnDoneButtonInWeatherForecast();
					}
					if (ds.isAddDeviceIconVisible(20)) {
						flag = flag & ds.clickOnAddNewDeviceIcon();
					} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
						flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
					}
					break;
				}
				case "ADD NEW DEVICE IN GLOBAL DRAWER": {
					Dashboard ds = new Dashboard(testCase);
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackArrowInSelectADeviceScreenVisible(5)) {
						flag = flag & dasDIY.clickOnBackArrowInSelectADeviceScreen();
					}
					if (ds.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDNEWDEVICE)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				// Navigate from 'Dashboard' to 'Security Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "SECURITY SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Camera Settings'
				case "CAMERA SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToCameraSettingsScreen(testCase, inputs);
					break;
				}
				// Amresh - Navigate from 'Dashboard' to 'WLD Settings'
				case "WLD SETTINGS": {
					flag = flag & WLDSolutionCard.navigateFromDashboardScreenToWLDSettingsScreen(testCase, inputs);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Dashboard to Settings");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				case "UPDATE FREQUENCY": {
					flag = flag & WLDLeakDetectorSettings
							.navigateFromDashboardScreenToWLDSettingsUpdateFrequencyScreen(testCase, inputs);
					break;
				}
				// Navigates to camera solution card screen from dashboard screen
				case "CAMERA SOLUTION CARD": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToCameraSolutionScreen(testCase, inputs);
					break;
				}
				// Navigates to DAS camera solution card screen from dashboard screen
				case "DAS CAMERA SOLUTION CARD": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToDASCameraSolutionScreen(testCase, inputs);
					break;
				}

				/* Method to navigate to camera configuration screen from dashboard */
				case "CAMERA CONFIGURATION": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToCameraConfigurationScreen(testCase, inputs);
					break;
				}
				/*
				 * Method to navigate to camera configuration with edited camera name screen
				 * from dashboard
				 */
				case "CAMERA CONFIGURATION WITH EDITED CAMERA NAME": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToCameraConfigurationScreen(testCase,
							inputs, "Camera Name Test");
					break;
				}
				// String substr = word.substring(word.length() - 3)
				// Navigate from 'Dashboard' to 'Thermostat Settings'
				case "THERMOSTAT SETTINGS": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToThermostatSettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Manage Alerts Screen'
				case "MANAGE ALERTS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToManageAlertsScreen(testCase, inputs);
					break;
				}
				// Amresh dashboard to wld manage alerts
				case "WLD MANAGE ALERTS": {
					flag = flag & WLDManageAlerts.navigateFromDashboardScreenToWLDManageAlerts(testCase, inputs);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Dashboard to Manage Alerts ");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				// Amresh dashboard to Leak Detector Configuration
				case "LEAK DETECTOR CONFIGURATION": {
					WLDSolutionCard sol = new WLDSolutionCard(testCase);
					flag = flag && sol.checkAndDismissControlState();
					flag = flag
							& WLDConfigurationScreen.navigateFromDashboardToWLDConfigurationScreen(testCase, inputs);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Dashboard to Configuration ");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				// Navigate from 'Dashboard' to 'WLD Email Notifications' Screen //Amresh
				case "EMAIL NOTIFICATIONS": {
					WLDSolutionCard sol = new WLDSolutionCard(testCase);
					flag = flag && sol.checkAndDismissControlState();
					flag = flag
							& WLDManageAlerts.navigateFromDashboardScreenToEmailNotificationsScreen(testCase, inputs);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Navigated from Dashboard to EmailNotifications");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to Navigate");
					}
					break;
				}
				// Navigate from 'Dashboard' to 'Thermostat Humidification Screen'
				case "THERMOSTAT HUMIDIFICATION": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToThermostatHumidificationScreen(testCase,
							inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Thermostat Dehumidification Screen'
				case "THERMOSTAT DEHUMIDIFICATION": {
					flag = flag & DASSettingsUtils
							.navigateFromDashboardScreenToThermostatDehumidificationScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Sound Screen'
				case "SOUND": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSoundScreen(testCase, inputs);
					break;
				}

				// Navigate from 'Dashboard' to 'Camera Sound Detection Screen'
				case "SOUND DETECTION": {
					flag = flag & DASSettingsUtils
							.navigateFromDashboardScreenToCameraSoundDetectionSettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Ventilation Screen'
				case "VENTILATION": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToVentilationScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Sound Brightness Mode Screen'
				case "SLEEP BRIGHTNESS MODE": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToSleepBrigthnessModeScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Camera Dashboard' to Manage Alerts Screen'
				case "CAMERA MANAGE ALERTS": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToCameraManageAlertsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Thermostat Configuration'
				case "THERMOSTAT CONFIGURATION": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToThermostatConfigurationScreen(testCase,
							inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Set Filter Reminder Screen'
				case "SET FILTER REMINDER": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToSetFilterReminderScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Camera Motion Detection Settings Screen'
				case "MOTION DETECTION SETTINGS": {
					flag = flag & DASSettingsUtils
							.navigateFromDashboardScreenToCameraMotionDetectionSettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Camera Night Vision Settings Screen'
				case "NIGHT VISION SETTINGS": {
					flag = flag & DASSettingsUtils
							.navigateFromDashboardScreenToCameraNightVisionSettingsScreen(testCase, inputs);
					break;
				}
				case "DAS NIGHT VISION SETTINGS": {
					flag = flag
							& DASSettingsUtils.navigateFromDashboardScreenToDASCameraSolutionScreen(testCase, inputs);
					break;
				}
				case "SCHEDULING": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					SchedulingScreen scheduleScreen = new SchedulingScreen(testCase);
					if (scheduleScreen.isTimeScheduleButtonVisible()) {
						flag = flag & scheduleScreen.clickOnTimeScheduleButton();
					} else if (scheduleScreen.isMoreButtonVisible()) {
						flag = flag & scheduleScreen.ClickOnMoreButton();
						flag = flag & scheduleScreen.clickOnTimeScheduleButton();

					}

					break;
				}
				case "HUMIDIFICATION": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
					if (fly.isHumButtonVisible()) {
						flag = flag && fly.ClickOnHumButton();
					} else {
						flag = flag && fly.ClickOnMoreButton();
						flag = flag && fly.ClickOnHumButton();
					}
					break;
				}
				case "DEHUMIDIFICATION": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
					if (fly.isDeHumButtonVisible(10)) {
						flag = flag && fly.ClickOnDeHumButton();
					} else {
						flag = flag && fly.ClickOnMoreButton();
						flag = flag && fly.ClickOnDeHumButton();
					}
					break;
				}
				case "DEVICE AND SENSORS": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
					flag = flag && fly.ClickOnSettingsIcon();
					flag = flag && fly.ClickOnDeviceAndSensor();
					if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
						flag = flag && MobileUtils.clickOnElement(testCase, "xpath",
								"//*[contains(@text,'" + inputs.getInputValue("SENSOR1") + "')]");
					} else {
						flag = flag && MobileUtils.clickOnElement(testCase, "id",
								inputs.getInputValue("SENSOR1") + " Indoor Air Sensors");
					}
					break;
				}
				case "WINDOW PROTECTION": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
					if (fly.isHumButtonVisible()) {
						flag = flag && fly.ClickOnHumButton();
						flag = flag && fly.ClickOnHumOptionButton();
						flag = flag && fly.ClickOnWindowProtectionButton();
					} else {
						flag = flag && fly.ClickOnMoreButton();
						flag = flag && fly.ClickOnHumButton();
						flag = flag && fly.ClickOnHumOptionButton();
						flag = flag && fly.ClickOnWindowProtectionButton();
					}
					break;
				}
				// Navigate from 'Dashboard' to 'Camera Video Quality Settings Screen'
				case "VIDEO QUALITY SETTINGS": {
					flag = flag & DASSettingsUtils
							.navigateFromDashboardScreenToCameraVideoQualitySettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Base Station Configuration'
				// Author: Pratik P. Lalseta (H119237)
				case "BASE STATION CONFIGURATION": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToBaseStationConfigurationScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Entry-Exit Delay Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "ENTRY-EXIT DELAY": {

					flag = flag & DASSettingsUtils.navigateFromDashboardToEntryExitDelayScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Keyfobs List'
				// Author: Pratik P. Lalseta (H119237)
				case "KEYFOB": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToKeyfobScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Sensors List'
				// Author: Pratik P. Lalseta (H119237)
				case "SENSORS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToSensorsScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Sensor Settings List'
				// Author: Pratik P. Lalseta (H119237)
				case "SENSOR SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToSensorSettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Keyfob Settings List'
				// Author: Pratik P. Lalseta (H119237)
				case "KEYFOB SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToKeyfobSettingsScreen(testCase, inputs);
					break;
				}

				// Navigate from 'Dashboard' to 'Amazon Alexa Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "AMAZON ALEXA SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToAmazonAlexaScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Video Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "VIDEO SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardToVideoSettingsScreen(testCase);
					break;
				}
				// Navigate from 'Dashboard' to 'Security Solutions Card'
				// Author: Pratik P. Lalseta (H119237)
				case "SECURITY SOLUTION CARD": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					// flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					break;
				}
				case "SOLUTION": {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					break;

				}
				case "GLOBAL DRAWER": {
					Thread.sleep(5000);
					Dashboard dScreen = new Dashboard(testCase);
					ManageUsersScreen mus = new ManageUsersScreen(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					AddressScreen ads = new AddressScreen(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Global drawer menu from dashboard");
					}
					break;
				}
				case "SENSOR LIST SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					if (flag) {
						System.out.println("Successfully navigated to Sensor List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Sensor List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Sensor List Settings Screen");
					}
					break;
				}
				case "KEYFOB LIST SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					if (flag) {
						System.out.println("Successfully navigated to Keyfob List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Keyfob List Settings Screen");
					}
					break;
				}
				case "MULTISTAT LOCATION": {
					Dashboard dashBoardScreen = new Dashboard(testCase);
					flag = flag & dashBoardScreen.selectLocationFromDashBoard(testCase,
							inputs.getInputValue("LOCATION2_NAME"));
					inputs.setInputValue("LOCATION1_NAME", inputs.getInputValue("LOCATION2_NAME"));
					inputs.setInputValue("LOCATION1_DEVICE1_NAME", inputs.getInputValue("LOCATION2_DEVICE1_NAME"));
					break;
				}
				case "VACATION": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						flag = flag & sc.selectOptionFromSecondarySettings(SecondaryCardSettings.VACATION);
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to Vacation Settings Screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Vacation Settings Screen");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "SOLUTION CARD": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					break;
				}
				case "SMART HOME SECURITY SENSOR ACCESSORIES": {
					Dashboard db = new Dashboard(testCase);
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (db.isAddDeviceIconBelowExistingDASDeviceVisible(20)) {
						flag = flag & db.clickOnAddDeviceIconBelowExistingDASDevice();
						flag = flag & dasDIY.selectDeviceToInstall(screen.get(0));
					}
					break;
				}
				case "USERS": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					ManageUsersScreen mus = new ManageUsersScreen(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					} else {
						if (os.isCloseButtonVisible(1)) {
							os.clickOnCloseButton();
							return false;
						}
					}
					
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.USERS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Add Users screen from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					
					/*if(mus.isManageUsersScreenHeaderVisible()) {
						flag &= mus.clickOnBackButton();
						if(flag) {
							Keyword.ReportStep_Pass(testCase, "User is in Account Details screen");
						}else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Users option from Global Drawer screen");
						}
					}*/
					break;
				}
				case "INVITE NEW USER": {
					Dashboard dScreen = new Dashboard(testCase);
					ManageUsersScreen mus = new ManageUsersScreen(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.USERS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Add Users screen from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					if (mus.isInviteNewUserButtonVisible()) {
						flag &= mus.clickOnInviteNewUserButton();
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Add Button in Add Users Screen");
					}
					break;
				}
				case "ADDRESS": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					AddressScreen ads = new AddressScreen(testCase);
					String locationNameDisplayedInDashboardScreen = null;
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					locationNameDisplayedInDashboardScreen = dScreen.getLocationNameDisplayedInDashboardScreen();
					System.out.println("#######locationNameDisplayedInDashboardScreen: " + locationNameDisplayedInDashboardScreen);
					// Get Default Country Name from CHIL
					if (!locationNameDisplayedInDashboardScreen.isEmpty()
							&& locationNameDisplayedInDashboardScreen != null) {
						try {
							@SuppressWarnings("resource")
							CHILUtil chUtil = new CHILUtil(inputs);
							long locationID;
							int humidityFromCHIL, humidityDisplayedInTheApp;
							String getDefaultCountryName = null;
							if (chUtil.getConnectionForUserAccount()) {
								locationID = chUtil.getLocationID(locationNameDisplayedInDashboardScreen);
								System.out.println("The CHIL location ID is: " + locationID);
								getDefaultCountryName = chUtil.getCountryName(locationID);
								if (getDefaultCountryName.equalsIgnoreCase("USA")) {
									getDefaultCountryName = "United States";
									inputs.setInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL", getDefaultCountryName);
								} else {
									inputs.setInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL", getDefaultCountryName);
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured : " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name displayed in Dashboard is either empty or null");
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDRESSDETAILS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Address from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					if (ads.isLocationNameInAddressScreenVisible()) {
						inputs.setInputValue("LOCATION_NAME", ads.getLocationNameDisplayedInAddressScreen());
						System.out.println("###########LOCATION_NAME: " + inputs.getInputValue("LOCATION_NAME"));
						if (ads.isLocationAddressInAddressScreenVisible()) {
							inputs.setInputValue("LOCATION_ADDRESS", ads.getLocationAddressDisplayedInAddressScreen());
							System.out.println(
									"###########LOCATION_ADDRESS: " + inputs.getInputValue("LOCATION_ADDRESS"));
						}
					}
					if (inputs.getInputValue("LOCATION_ADDRESS")
							.contains(inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"))) {
						Keyword.ReportStep_Pass(testCase,
								inputs.getInputValue("LOCATION_ADDRESS") + " contains the default country from CHIL: "
										+ inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION_ADDRESS")
										+ " does not contains the default country from CHIL: "
										+ inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"));
					}
					break;
				}
				case "ACCOUNT DETAILS": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					EditAccountScreen eas = new EditAccountScreen(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ACCOUNTDETAILS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Account Details from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					if (eas.isEditAccountScreenTitleVisible() && eas.isNameLabelInEditAccountScreenVisible()) {
						inputs.setInputValue("NAME_IN_EDIT_ACCOUNT", eas.getNameValueInEditAccountScreen());
						System.out.println(
								"###########NAME_IN_EDIT_ACCOUNT: " + inputs.getInputValue("NAME_IN_EDIT_ACCOUNT"));
						String[] name= inputs.getInputValue("NAME_IN_EDIT_ACCOUNT").split(" ");
						String firstName= name[0];
						String lastName= name[1];
						System.out.println("First Name is: "+ firstName);
						System.out.println("Last Name is: "+ lastName);
						inputs.setInputValue("PREVIOUS_FIRST_NAME", firstName);
						inputs.setInputValue("PREVIOUS_LAST_NAME", lastName);
					}
					break;
				}
				case "ABOUT THE APP": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
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
							action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						}
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ABOUTTHEAPP)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to About the app from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "FAQS": {
					Dashboard dScreen = new Dashboard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					CoachMarks cm = new CoachMarks(testCase);
					Thread.sleep(5000);
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (os.isNotNowButtonVisible(1)) {
							flag &= os.clickOnNotNowButton();
							if (os.isNotNowButtonVisible(1)) {
								flag &= os.clickOnNotNowButton();
							}
						}
						if (cm.isDoneButtonVisible(1)) {
							flag = flag & cm.clickOnDoneButton();
						}
					}
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
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
							action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();

						}
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.FAQS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to FAQs from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}	
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CAMERA SOLUTION CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SETTINGS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					if (pc.isCogIconVisible()) {
						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settins icon");

						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}
				case "CAMERA CONFIGURATION": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {

						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settins icon");
							flag &= ds.navigateFromCameraSolutionScreenToCameraConfigurationScreen(testCase);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}
				case "BASE STATION CONFIGURATION": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {
						if (pc.clickOnBackButton()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on back option");
							flag &= DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
							flag &= ds.navigateFromSecuritySolutionToBaseStationConfigurationScreen(testCase);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click back option");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}

					break;
				}
				case "MANAGE ALERTS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {
						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settings icon");
							flag &= ds.navigateFromCameraSolutionScreenToManageAlertsScreen(testCase, inputs);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}
				case "MOTION DETECTION SETTINGS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {
						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settings icon");
							flag &= ds.navigateFromCameraSolutionScreenToCameraMotionDetectionSettingsScreen(testCase,
									inputs);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}
				case "NIGHT VISION SETTINGS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {
						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settings icon");
							flag &= ds.navigateFromCameraSolutionScreenToCameraNightVisionSettingsScreen(testCase,
									inputs);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}
				case "VIDEO QUALITY SETTINGS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					DASSettingsUtils ds = new DASSettingsUtils();
					if (pc.isCogIconVisible()) {
						if (pc.clickOnCogIcon()) {
							Keyword.ReportStep_Pass(testCase, "Successfully cliked on settings icon");
							flag &= ds.navigateFromCameraSolutionScreenToCameraVideoQualitySettingsScreen(testCase,
									inputs);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click settings icon");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings icon not visible");
					}
					break;
				}

				case "THERMOSTAT SOLUTION CARD": {
					Dashboard sensorScreen = new Dashboard(testCase);
					flag &= sensorScreen.NavigatetoThermostatDashboard();
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("AMAZON ALEXA SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "BASE STATION CONFIGURATION": {
					flag = flag
							& DASSettingsUtils.navigateFromAmazonAlexaScreenToBaseStationConfigurationScreen(testCase);
					break;
				}
				case "SENSOR LIST SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromAmazonAlexaScreenToSensorListScreen(testCase);
					if (flag) {
						System.out.println("Successfully navigated to Sensor List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Sensor List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Sensor List Settings Screen");
					}
					break;
				}
				case "KEYFOB LIST SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromAmazonAlexaScreenToKeyFobListScreen(testCase);
					if (flag) {
						System.out.println("Successfully navigated to Keyfob List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Keyfob List Settings Screen");
					}
					break;
				}

				// Navigate from 'Amazon Alexa Settings' to 'Setup Amazon Alexa screen'
				// Author: Gautham (H138526)
				case "SETUP AMAZON ALEXA": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isAmazonSetUpButtonVisible()) {
						flag = flag & bs.clickOnAmazonSetUpButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Back button");
					}
					break;
				}
				case "SECURITY SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isBackButtonVisible()) {
						flag = flag & bs.clickOnBackButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Back button");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
					break;
				}
			}

			else if (screen.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				switch (screen.get(0).toUpperCase()) {
				// Navigate from 'Entry/Exit Delay Settings' to 'Security
				// Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "SECURITY SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isBackButtonVisible()) {
						flag = flag & bs.clickOnBackButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Back button");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("SECURITY SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				// Navigate from 'Security Settings' to 'Entry/Exit Delay
				// Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "ENTRY-EXIT DELAY": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag
							& bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
					break;
				}
				case "MANAGE ALERTS": {
					BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
					if (cs.isManageAlertExist()) {
						if (cs.clickOnManageAlerts()) {
							CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							Keyword.ReportStep_Pass(testCase, "Navigates to manage alerts screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select Manage alerts option");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage alert option not visible");
					}
					break;
				}
				case "SENSOR LIST SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					if (flag) {
						System.out.println("Successfully navigated to Sensor List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Sensor List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Sensor List Settings Screen");
					}
					break;
				}
				case "KEYFOB LIST SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					if (flag) {
						System.out.println("Successfully navigated to Keyfob List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Keyfob List Settings Screen");
					}
					break;
				}
				case "MOTION SENSOR SETTINGS":
				case "WINDOW ACCESS SETTINGS":
				case "DOOR ACCESS SETTINGS":
				case "ISMV SENSOR SETTINGS":
				case "OSMV SENSOR SETTINGS": {
					DASSensorUtils.navigateToSensorTypeSettingsFromSecuritySettingsScreen(screen.get(0).toUpperCase(),
							inputs, testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}

			else if (screen.get(1).equalsIgnoreCase("Base Station Configuration")) {
				switch (screen.get(0).toUpperCase()) {
				// Navigate from 'Base Station Configuration' to 'Dashboard'
				// Author: Pratik P. Lalseta (H119237)
				case "DASHBOARD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					Dashboard d = new Dashboard(testCase);
					int counter = 0;
					while (bs.isBackButtonVisible() && !d.areDevicesVisibleOnDashboard() && counter < 4) {
						flag = flag & bs.clickOnBackButton();
						counter++;
					}
					break;
				}
				// Navigate from 'Base Station Configuration' to 'Model and
				// Firmware Details'
				// Author: Pratik P. Lalseta (H119237)
				case "MODEL AND FIRMWARE DETAILS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isModelAndFirmwareOptionsVisibleOnBaseStationConfigurationScreen()) {
						flag = flag & bs.clickOnModelAndFirmwareOptionsOnBaseStationConfigurationScreen();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not find 'Model And Firmaware Details' option");
					}
					break;
				}
				case "SENSOR LIST": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					if (flag) {
						System.out.println("Successfully navigated to Keyfob List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Keyfob List Settings Screen");
					}
					break;
				}
				case "KEYFOB LIST": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					if (flag) {
						System.out.println("Successfully navigated to Keyfob List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Keyfob List Settings Screen");
					}
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GLOBAL DRAWER")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE GLOBAL DRAWER": {
					SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
					if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDNEWDEVICE)) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Add new device menu from Global drawer");
					}
					break;
				}
				case "ACCOUNT DETAILS": {
					GlobalDrawerScreen gds = new GlobalDrawerScreen(testCase);
					EditAccountScreen eas = new EditAccountScreen(testCase);
					if (gds.isAccountDetailsOptionVisible()) {
						flag &= gds.clickAccountDetailsOption();
						if (eas.isEditAccountScreenTitleInEditAccountVisible()) {
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to " + screen.get(0) + " screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to " + screen.get(0) + " screen");
						}
					}
					break;
				}
				case "DASHBOARD" : {
					GlobalDrawerScreen gds = new GlobalDrawerScreen(testCase);
					if(gds.isBackButtonVisible()) {
						flag&= gds.clickOnBackButton();
						if(flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to " + screen.get(0) + " screen");
						}else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to " + screen.get(0) + " screen");
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}

			} else if (screen.get(1).equalsIgnoreCase("SENSOR COVER TAMPER")) {
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SOLUTION CARD": {

					SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
					SensorSettingScreen ssc = new SensorSettingScreen(testCase);
					if (ssc.isSensorTamperedScreenDisplayed()) {
						flag = flag & sc.clickOnBackButton();
					}
					if (ssc.isSensorsScreenTitleVisible()) {
						flag = flag & sc.clickOnBackButton();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ADD NEW DEVICE DASHBOARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					AddNewDeviceScreen addScreen = new AddNewDeviceScreen(testCase);
					if (addScreen.isBackButtonVisible()) {
						flag &= addScreen.clickOnBackButton();
					} else {
						flag = false;
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to " + screen.get(0) + " screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to " + screen.get(0) + " screen");
					}
					break;
				}
				case "GLOBAL DRAWER": {
					AddNewDeviceScreen addScreen = new AddNewDeviceScreen(testCase);
					if (addScreen.clickOnCancelButtonOfAddDeviceScreen()) {
						Dashboard ds = new Dashboard(testCase);
						if (!ds.clickOnGlobalDrawerButton()) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Global drawer menu from dashboard");

						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Add new device - cancel");
						flag = false;
					}
					break;
				}
				case "SMART HOME SECURITY": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.isAddNewDeviceScreenVisible(20);
					flag = flag & dasDIY.selectDeviceToInstall(screen.get(0));
					break;
				}
				case "SMART HOME SECURITY SENSOR ACCESSORIES": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.isAddNewDeviceScreenVisible(20);
					flag = flag & dasDIY.selectDeviceToInstall(screen.get(0));
					break;
				}
				case "C1 WI-FI SECURITY CAMERA": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.isAddNewDeviceScreenVisible(20);
					flag = flag & dasDIY.selectDeviceToInstall(screen.get(0));
					break;
				}
				case "WEATHER FORECAST": {
					WeatherForecastScreen w = new WeatherForecastScreen(testCase);
					Dashboard d = new Dashboard(testCase);
					if (d.isDashboardWeatherForecastDisplayed(10)) {
						d.clickOnWeatherTempValue();
						if (w.isWeatherScreenTitleDisplayed(20)) {
							ReportStep_Pass(testCase, "Weather Forecast screen is displayed");
						} else {
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Weather Forecast screen is not displayed");
						}
					} else {
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Weather Forecast icon is not displayed in dashboard screen");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("WHAT TO EXPECT")) {
				switch (screen.get(0).toUpperCase()) {
				case "CHOOSE LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
					}
					break;
				}
				case "WATCH HOW-TO VIDEO": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isWatchHowToVideoLinkVisible()) {
						flag = flag & dasDIY.clickOnWatchHowToVideoLink();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("VIDEO CLIP")) {
				switch (screen.get(0).toUpperCase()) {
				case "KEYFOB OVERVIEW":
				case "SENSOR OVERVIEW":
				case "LOCATE VIEWER": {
					if (!testCase.getPlatform().contains("IOS")) {
						MobileUtils.pressBackButton(testCase);
					} else {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						dasDIY.clickOnNavBackIconInVideoClipScreen();
					}
					break;
				}
				case "WHAT TO EXPECT": {
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isNavBackIconDisplayedInVideoClipScreen(10)) {
							flag = flag & dasDIY.clickOnNavBackIconInVideoClipScreen();
						}
					} else {
						if (MobileUtils.pressBackButton(testCase)) {
							System.out.println("Clicked on Android back button");
							ReportStep_Pass(testCase, "Clicked on Android back button");
						} else {
							System.out.println("Not able to click on back button");
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Not able to click on Android back button");
							flag = false;
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("LOCATE VIEWER")) {
				switch (screen.get(0).toUpperCase()) {
				case "SELECT ISMV LOCATION":
				case "SELECT OSMV LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						flag = flag & dasDIY.isISMVOSMVNameViewerScreenTitleVisible(10);
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE DASHBOARD": {
					AddNewDeviceScreen addNewDevice = new AddNewDeviceScreen(testCase);
					flag = flag & addNewDevice.isAddNewDeviceHeaderDisplayed(30);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CREATE LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CHOOSE LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackButtonInCreateLocationScreenVisible()) {
						flag = flag & dasDIY.clickOnBackButtonInCreateLocationScreen();
						break;
					}
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("POWER BASE STATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "POWER BASE STATION INSTRUCTIONS": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "WAIT UNTIL DAS REBOOT", 1);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
					}
					break;
				}
				case "REGISTER BASE STATION": {
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "WAIT UNTIL DAS REBOOT", 1);
					flag = flag & DIYRegistrationUtils.navigateFromPowerBaseStationToRegisterBaseStation(testCase);
					break;
				}
				case "LOOKING FOR BASE STATION": {
					flag = flag & DIYRegistrationUtils.navigateFromPowerBaseStationToLookingForBaseStation(testCase);
					break;
				}
				case "SELECT BASE STATION": {
					flag = flag & DIYRegistrationUtils.navigateFromPowerBaseStationToSelectBaseStation(testCase);
					break;
				}
				case "DASHBOARD": {
					flag = flag & DIYRegistrationUtils.navigateFromPowerBaseStationToDashboard(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("BASE STATION HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "POWER BASE STATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackButtonInBaseStationHelpScreenVisible()) {
						flag = flag & dasDIY.clickOnBackButtonInBaseStationHelpScreen();
						break;
					}
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("REGISTER BASE STATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CONNECT TO NETWORK": {
					flag = flag & DIYRegistrationUtils.navigateFromRegisterBaseStationToConnectToNetwork(testCase);
					break;
				}
				case "ADD NEW DEVICE DASHBOARD": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddNewDeviceScreenVisible(5)) {
						return true;
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SELECT BASE STATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE DASHBOARD": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddNewDeviceScreenVisible(5)) {
						return true;
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
				switch (screen.get(0).toUpperCase()) {
				case "SMART HOME SECURITY SUCCESS": {
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
							"ALMOST DONE LOADING PROGRESS BAR TEXT", 3);
					flag = flag & DIYRegistrationUtils.navigateFromConnectToNetworkToSmartHomeSecuritySuccess(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SET UP ACCESSORIES")) {
				switch (screen.get(0).toUpperCase()) {
				case "OVERVIEW": {
					flag = flag & DIYRegistrationUtils.navigateFromSetUpAccessoriesToOverview(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("OVERVIEW")) {
				switch (screen.get(0).toUpperCase()) {
				case "LOCATE SENSOR": {
					flag = flag & DIYRegistrationUtils.navigateFromOverviewToLocateSensor(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("LOCATE SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "NAME SENSOR": {
					flag = flag & DIYRegistrationUtils.navigateFromLocateSensorToNameSensor(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CHECK LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CHECK LOCATION SIGNAL": {
					flag = flag & DIYRegistrationUtils.navigateFromCheckLocationToCheckLocationSignal(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CHECK LOCATION SIGNAL")) {
				switch (screen.get(0).toUpperCase()) {
				case "PREPARE SENSOR": {
					flag = flag & DIYRegistrationUtils.navigateFromCheckLocationSignalToPrepareSensor(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PREPARE SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE ADHESIVE STRIPS": {
					flag = flag & DIYRegistrationUtils.navigateFromPrepareSensorToPlaceAdhesiveStrips(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE ADHESIVE STRIPS")) {
				switch (screen.get(0).toUpperCase()) {
				case "MOUNT SENSOR": {
					flag = flag & DIYRegistrationUtils.navigateFromPlaceAdhesiveStripsToMountSensor(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MOUNT SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR READY": {
					flag = flag & DIYRegistrationUtils.navigateFromMountSensorToSensorReady(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR READY")) {
				switch (screen.get(0).toUpperCase()) {
				case "SET UP ACCESSORIES CONFIGURED": {
					flag = flag & DIYRegistrationUtils.navigateFromSensorReadyToSetUpAccConfigured(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SET UP ACCESSORIES CONFIGURED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					flag = flag & DIYRegistrationUtils.navigateFromSetUpAccConfiguredToEnableGeoFencing(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENABLE GEOFENCING")) {
				switch (screen.get(0).toUpperCase()) {
				case "GEOFENCE": {
					flag = flag & DIYRegistrationUtils.navigateFromEnableGeoFencingToGeoFence(testCase);
					break;
				}
				case "ENABLE AMAZON ALEXA": {
					flag = flag & DIYRegistrationUtils.navigateFromEnableGeoFencingToEnableAmazonAlexa(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE")) {
				switch (screen.get(0).toUpperCase()) {
				case "GEOFENCE ENABLED": {
					flag = flag & DIYRegistrationUtils.navigateFromGeoFenceToGeoFenceEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE ENABLED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE AMAZON ALEXA": {
					flag = flag & DIYRegistrationUtils.navigateFromGeoFenceEnabledToEnableAmazonAlexa(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SMART HOME SECURITY SUCCESS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					flag = flag & DIYRegistrationUtils.navigateFromSmartHomeSecuritySuccessToEnableGeoFencing(testCase);
					break;
				}
				case "SET UP ACCESSORIES": {
					flag = flag & DIYRegistrationUtils.navigateFromSmartHomeSecuritySuccessToSetUpAccessories(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENABLE AMAZON ALEXA")) {
				switch (screen.get(0).toUpperCase()) {
				case "PEOPLE DETECTION": {
					flag = flag & DIYRegistrationUtils.navigateFromEnableAmazonAlexaToPeopleDetection(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PEOPLE DETECTION")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					flag = flag & DIYRegistrationUtils.navigateFromPeopleDetectionToDashboard(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Keyfob")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSORS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					break;
				}
				case "KEYFOB SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (!inputs.isInputAvailable("LOCATION1_DEVICE1_KEYFOB1")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No Keyfob names were provided in the Requirement file");
						return flag;
					}
					flag = flag & bs.selectKeyfobFromKeyfobList(inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1"));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Keyfob Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "MODEL AND FIRMWARE DETAILS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnModelAndFirmwareOptionsOnKeyfobSettingsScreen();
					break;
				}
				case "KEYFOB": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					break;
				}
				case "BASE STATION CONFIGURATION": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnBackButton();
					if (sensor.isSensorListScreenDisplayed()) {
						flag = flag & sensor.clickOnBackButton();
						if (flag) {
							System.out.println("Successfully navigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to " + screen.get(0));
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Keyfob Model and Firmware Details")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSORS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MODEL AND FIRMWARE DETAILS")) {
				switch (screen.get(0).toUpperCase()) {
				case "DOOR ACCESS SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
					break;
				}
				case "WINDOW ACCESS SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1"));
					break;
				}
				case "MOTION SENSOR SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					inputs.setInputValue(DASInputVariables.MOTIONSENSORTYPE, DASInputVariables.MOTIONSENSOR);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"));
					break;
				}
				case "KEYFOB SETTINGS": {
					boolean flag = true;
					try {
						BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
						String keyfobName = "";
						SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
						flag = flag & bs.clickOnBackButton();
						flag = flag & bs.clickOnBackButton();
						flag = flag & bs.clickOnBackButton();
						flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
						if (flag) {
							System.out.println("Successfully navigated to Keyfob List Settings Screen");
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Did not navigated to Keyfob List Settings Screen");
						}
						if (!inputs.isInputAvailable("LOCATION1_DEVICE1_KEYFOB1")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"No keyfob names were provided in the Requirement file");
							return flag;
						}
						keyfobName = inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1");
						flag = flag & bs.selectSensorFromSensorList(keyfobName);
						DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
						inputs.setInputValue(DASInputVariables.KEYFOBNAME, keyfobName);
						inputs.setInputValue(DASInputVariables.KEYFOBID, devInfo.getDASKeyfobID(keyfobName));
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
					return flag;
				}
				case "ISMV SENSOR SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					inputs.setInputValue(DASInputVariables.ISMVMOTIONSENSORTYPE, DASInputVariables.ISMVMOTIONSENSOR);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"));
					break;
				}
				case "OSMV SENSOR SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					inputs.setInputValue(DASInputVariables.OSMVMOTIONSENSORTYPE, DASInputVariables.OSMVMOTIONSENSOR);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Sensor")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (!inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")
							&& !inputs.isInputAvailable("LOCATION1_MOTIONENSOR1_NAME")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No sensor names were provided in the Requirement file");
						return flag;
					}
					if (inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")) {
						flag = flag
								& bs.selectSensorFromSensorList(inputs.getInputValue("LOCATION1_ACCESSSENSOR1_NAME"));
					} else {
						flag = flag
								& bs.selectSensorFromSensorList(inputs.getInputValue("LOCATION1_MOTIONSENSOR1_NAME"));
					}
					break;
				}
				case "BASE STATION CONFIGURATION": {
					flag = flag & DASSettingsUtils.navigateFromSensorScreenToBaseStationConfigurationScreen(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENTER YOUR WI-FI PASSWORD")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE DASHBOARD": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddNewDeviceScreenVisible(5)) {
						return true;
					}
					break;
				}
				case "CONNECT TO NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackButtonInEnterWiFiPwdScreenVisible()) {
						dasDIY.clickOnBackButtonInEnterWiFiPwdScreen();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Sensor Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "MODEL AND FIRMWARE DETAILS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnModelAndFirmwareOptionsOnSensorSettingsScreen();
					break;
				}
				case "SENSOR": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Video Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SOLUTION CARD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & bs.clickOnBackButton();
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MOTION DETECTION SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SETTINGS": {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isBackButtonVisibleInMotionDetectionSettingsScreen()) {
						flag = flag & cs.clickOnBackButtonInMotionDetectionSettingsScreen();
						TimeUnit.SECONDS.sleep(3);
						if (cs.isAreasOutsideZonesWarningPopupHeaderTitleDisplayed()) {
							if (cs.clickOnNOButtonInAreasOutsideZonesWarningPopup()) {
								Keyword.ReportStep_Pass(testCase, "Clicked on NO option in warrning pop up");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Not selected NO option in warrning pop up");
							}
						}
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Navigated to camera settings screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Not Navigated to camera settings screen");
						}
					}
					break;
				}
				case "SOUND DETECTION SETTINGS": {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isBackButtonVisibleInMotionDetectionSettingsScreen()) {
						cs.clickOnBackButtonInMotionDetectionSettingsScreen();
						if (cs.isCameraSettingsHeaderTitleVisible(20) && cs.isSoundDetectionLabelVisible(testCase, 1)) {
							cs.clickOnSoundDetectionLabel();
							if (cs.isSoundDetectionScreenHeaderTitleVisible(20)) {
								return flag;
							} else {
								flag = false;
							}
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SOUND DETECTION SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SETTINGS": {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isBackButtonVisibleInSoundDetectionSettingsScreen()) {
						cs.clickOnBackButtonInSoundDetectionSettingsScreen();
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("NIGHT VISION SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SETTINGS": {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isBackButtonVisibleInNightVisionSettingsScreen()) {
						cs.clickOnBackButtonVisibleInNightVisionSettingsScreen();
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("VIDEO QUALITY SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SETTINGS": {
					if (cs.isBackButtonVisibleInVideoQualitySettingsScreen()) {
						cs.clickOnBackButtonVisibleInVideoQualitySettingsScreen();
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "MOTION DETECTION SETTINGS": {
					if (cs.isMotionDetectionLabelVisible(testCase, 20)) {
						cs.clickOnMotionDetectionLabel();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					}
					break;
				}
				case "MANAGE ALERTS": {
					if (cs.isManageAlertsLabelVisible(2)) {
						if (cs.clickOnManageAlertsLabel()) {
							CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							Keyword.ReportStep_Pass(testCase, "Navigates to manage alerts screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select Manage alerts option");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage alert option not visible");
					}
					break;
				}
				case "SOUND DETECTION SETTINGS": {
					if (cs.isSoundDetectionLabelVisible(testCase, 20)) {
						cs.clickOnSoundDetectionLabel();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					}
					break;
				}
				case "NIGHT VISION SETTINGS": {
					if (cs.isNightVisionLabelVisible(20)) {
						cs.clickOnNightVisionLabel();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					}
					break;
				}
				case "VIDEO QUALITY SETTINGS": {
					if (cs.isVideoQualityLabelVisible(20)) {
						cs.clickOnVideoQualityLabel();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					}
					break;
				}
				case "CAMERA SOLUTION CARD": {
					if (cs.clickonbackoption()) {
						Keyword.ReportStep_Pass(testCase, "clicked on camera settings back option");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed clicked on camera settings back option");
					}
					break;
				}
				case "SECURITY SOLUTION CARD": {
					DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}

			} else if (screen.get(1).equalsIgnoreCase("Alarm Security Solution Card")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					AlarmScreen alarmScreen = new AlarmScreen(testCase);
					alarmScreen.clickOnAlarm_NavigateBack();
					break;
				}

				}
			} else if (screen.get(1).equalsIgnoreCase("SECURITY SOLUTION CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR LIST": {
					/*
					 * SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
					 * flag = flag & sc.isSensorsTextVisible();
					 */
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					if (security.isAppSettingsIconVisible(15)) {
						flag = security.clickOnAppSettingsIcon();
					} else if (security.isAltAppSettingsIconVisible(10)) {
						flag = security.clickOnAltAppSettingsIcon();
					}
					flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							"Base Station Configuration");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					break;
				}
				case "DOOR ACCESS SETTINGS":
				case "WINDOW ACCESS SETTINGS":
				case "MOTION SENSOR SETTINGS": {
					DASSensorUtils.navigateToSensorTypeSettingsFromSecuritySolutionCard(screen.get(0).toUpperCase(),
							inputs, testCase);
					break;
				}
				case "SENSOR STATUS": {
					SecuritySolutionCardScreen securityScreen = new SecuritySolutionCardScreen(testCase);
					if (securityScreen.isSensorNoIssueVisible()) {
						flag = flag & securityScreen.ClickOnSensorNoIssue();
					} else {
						flag = flag & securityScreen.ClickOnSensorIssue();
					}
					break;
				}
				case "DASHBOARD": {
					SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
					SensorStatusScreen sensorStatusScreen = new SensorStatusScreen(testCase);
					if (sensorStatusScreen.isSensorStatusVisible()
							&& sensorStatusScreen.isAddButtonNotVisibleInSensorStatusScreen()) {
						flag = flag & sensorStatusScreen.clickOnSensorStatusScreenBack(testCase);
					}
					if (sc.isBackButtonVisible()) {
						flag = flag & sc.clickOnBackButton();
					}
					break;
				}
				case "CAMERA SOLUTION CARD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					break;
				}
				case "DAS SECURITY SETTINGS": {
					Dashboard d = new Dashboard(testCase);
					PrimaryCard pc = new PrimaryCard(testCase);
					OSPopUps os = new OSPopUps(testCase);
					if (os.isNotNowButtonVisible(10)){
						flag = flag & os.clickOnNotNowButton();
					}
					if (d.areDevicesVisibleOnDashboard(25)) {
						try {
							flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
							flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
							Thread.sleep(2000);
							if (pc.isCogIconVisible()) {
								flag = flag & pc.clickOnCogIcon();
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured: " + e.getMessage());
						}
					} else {
						if (pc.isCogIconVisible()) {
							flag = flag & pc.clickOnCogIcon();
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Camera Settings Introduction")) {
				switch (screen.get(0).toUpperCase()) {
				case "CAMERA SOLUTION CARD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnBackButton();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Alarm")) {
				switch (screen.get(0).toUpperCase()) {
				case "ALARM HISTORY": {
					AlarmScreen open = new AlarmScreen(testCase);
					open.openAlarmHistory();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Honeywell Help web portal")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR PAIRING HELP":
				case "MOTION SENSOR HELP":
				case "ACCESS SENSOR HELP": {
					if (!testCase.getPlatform().contains("IOS")) {
						MobileUtils.pressBackButton(testCase);
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Test Signal Strength")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCESS SENSOR HELP": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					sensor.clickOnSignalStrengthBackButton();
					sensor.isAccessSensorHelpScreenDisplayed();
					break;
				}
				case "TEST MOTION SENSOR":
				case "TEST SENSOR": {
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					sensor.clickOnSignalStrengthBackButton();
					Thread.sleep(3000);
					sensor.clickOnBackButton();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ACCESS SENSOR HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST ACCESS SENSOR": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnAccessSensorHelpBack();
					flag = flag & sensor.isTestSensorHeadingDisplayed();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Motion Sensor Help")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST MOTION SENSOR": {
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnMotionSensorHelpBack();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("TEST ACCESS SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCESS SENSOR SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					break;
				}
				case "DOOR ACCESS SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					flag = flag & sensor.isDoorAccessSettingsScreenTitleVisible(30);
					break;
				}
				case "WINDOW ACCESS SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					flag = flag & sensor.isWindowAccessSettingsScreenTitleVisible(30);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("TEST MOTION SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "MOTION SENSOR SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					flag = flag & sensor.isMotionSensorSettingsScreenTitleVisible(30);
					break;
				}
				case "MOTION VIEWER SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					flag = flag & sensor.isMotionSensorSettingsScreenTitleVisible(30);
					break;
				}
				case "MOTION VIEWER HELP": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isViewerNotWorkingLinkInTestMotionViewerScreenVisible()) {
						flag = flag & dasDIY.clickOnViewerNotWorkingLinkInTestMotionViewerScreen();
						if (dasDIY.isMotionViewerHelpScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Access Sensor Settings")
					|| screen.get(1).equalsIgnoreCase("MOTION Sensor Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SOLUTION CARD": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					SensorStatusScreen sensorStatus = new SensorStatusScreen(testCase);
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (!security.isSecuritySettingsTitleVisible() && !security.isAppSettingsIconVisible(10)) {
						flag = flag & sensor.clickOnSensorListSettingBack();
						if (sensorStatus.isSensorStatusVisible()) {
							Keyword.ReportStep_Pass(testCase, "Sensor screen visible");
						}
						if (!security.isSecuritySettingsTitleVisible()) {
							flag = flag & bs.clickOnBackButton();
						}
						flag = flag & bs.clickOnBackButton();
						if (security.isAppSettingsIconVisible(10)) {
							Keyword.ReportStep_Pass(testCase, "Security solution screen visible");
						}
					}
					break;
				}
				case "ACCESS SENSOR OFF": {
					SensorSettingScreen s = new SensorSettingScreen(testCase);
					if (s.clickOnSensorStatusOffOnAccessSensorScreen()) {
						String expectedSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
						flag = flag & s.checkSensorNameInSensorOffScreen(inputs, expectedSensorName);
						flag = flag & s.checkSensorIsOffTextVisible();
						if (flag) {
							flag = flag & s.clickOnAccessSensorHelpBack();
							Keyword.ReportStep_Pass(testCase, "SensorOff screen is displayed");
						}
					}
					break;
				}
				case "SENSOR LIST": {
					MobileUtils.hideKeyboard(testCase.getMobileDriver());
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					sensor.clickOnBackButton();
					break;
				}
				case "MOTION SENSOR OFF": {
					SensorSettingScreen s = new SensorSettingScreen(testCase);
					if (s.clickOnSensorStatusOffOnAccessSensorScreen()) {
						String expectedSensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
						flag = flag & s.checkSensorNameInSensorOffScreen(inputs, expectedSensorName);
						flag = flag & s.checkSensorIsOffTextVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "SensorOff screen is displayed");
						}
						flag = flag & s.clickOnMotionSensorHelpBack();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Locate Sensor")) {
				switch (screen.get(0).toUpperCase()) {
				case "NAME SENSOR": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.clickOnNextButton()) {
						System.out.println("NAvigated to " + screen.get(0));
						Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
					}
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Place Sensor")
					|| screen.get(1).equalsIgnoreCase("Mount in a corner")
					|| screen.get(1).equalsIgnoreCase("Mount on the wall")
					|| screen.get(1).equalsIgnoreCase("Place Base Plate")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE SENSOR ON LOCATION": 
				case "PLACE SENSOR BODY":{
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.clickOnNextButton()) {
						System.out.println("NAvigated to " + screen.get(0));
						Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
					}
					break;
				}
				case "TEST SENSOR": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.clickOnNextButton()) {
						System.out.println("NAvigated to " + screen.get(0));
						Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE VIEWER CHECK PLACEMENT")
					||(screen.get(1).equalsIgnoreCase("PLACE SENSOR BODY"))) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE VIEWER WALL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						if (dasDIY.isPlaceViewerWallScreenTitleVisible(20)) {
							System.out.println("Navigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "Navigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}case "TEST DETECTOR": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						Keyword.ReportStep_Pass(testCase, "Navigated to " + screen.get(0));
					}
					break;
				}
				
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE VIEWER WALL")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE VIEWER ARM": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						if (dasDIY.isPlaceViewerArmScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE VIEWER ARM")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE VIEWER MOTION VIEWER": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						if (dasDIY.isPlaceViewerMotionViewerScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE VIEWER MOTION VIEWER")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE VIEWER ADJUST VIEWER": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						if (dasDIY.isPlaceViewerAdjustViewerScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE VIEWER ADJUST VIEWER")
					|| screen.get(1).equalsIgnoreCase("PLACE VIEWER MOUNT")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST MOTION VIEWER": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
						if (dasDIY.isTestMotionViewerScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("TEST MOTION VIEWER")) {
				switch (screen.get(0).toUpperCase()) {
				case "MOTION VIEWER HELP": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isViewerNotWorkingLinkInTestMotionViewerScreenVisible()) {
						flag = flag & dasDIY.clickOnViewerNotWorkingLinkInTestMotionViewerScreen();
						if (dasDIY.isMotionViewerHelpScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				case "MOTION VIEWER SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
					flag = flag & sensor.isMotionViewerSettingsScreenTitleVisible(30);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MOTION VIEWER HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "SIGNAL STRENGTH": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isTestSignalStrengthButtonInMotionViewerHelpScreenVisible()) {
						flag = flag & dasDIY.clickOnTestSignalStrengthButtonInMotionViewerHelpScreen();
						if (dasDIY.isOutOfRangePopupInSignalStrengthScreenVisible()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Out of Range Popup is displayed in Signal Strength Screen");
							if (dasDIY.isOKButtonInOutOfRangePopupVisible()) {
								flag = flag & dasDIY.clickOnOKButtonInOutOfRangePopup();
							}
						}
						if (dasDIY.isSignalStrengthScreenTitleVisible(20)) {
							System.out.println("NAvigated to " + screen.get(0));
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				case "TEST VIEWER SENSOR": {
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnMotionSensorHelpBack();
					flag = flag & sensor.clickOnAccessSensorHelpBack();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SIGNAL STRENGTH")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST MOTION VIEWER": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackButtonInSignalStrengthScreenVisible()) {
						flag = flag & dasDIY.clickOnBackButtonInSignalStrengthScreen();
						if (dasDIY.isMotionViewerHelpScreenTitleVisible(20)
								&& dasDIY.isTestSignalStrengthButtonInMotionViewerHelpScreenVisible()) {
							Keyword.ReportStep_Pass(testCase, "NAvigated to Motion Viewer Help Screen");
							if (dasDIY.isBackButtonInMotionViewerHelpScreenVisible()) {
								flag = flag & dasDIY.clickOnBackButtonInMotionViewerHelpScreen();
								if (dasDIY.isTestMotionViewerScreenTitleVisible(10)) {
									System.out.println("NAvigated to " + screen.get(0));
									Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
								}
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to : " + screen.get(0));
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Next button not displayed in: " + screen.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Access sensor Install help")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE SENSOR ON LOCATION": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.clickOnBackButton()) {
						System.out.println("NAvigated to " + screen.get(0));
						Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
					}
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Place Sensor on location")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST SENSOR": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.clickOnNextButton()) {
						System.out.println("NAvigated to " + screen.get(0));
						Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
					}
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR LIST")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR OVERVIEW": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					break;
				}
				case "LOCATE SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "LOCATE MOTION SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					break;
				}
				case "NAME SENSOR LOCATION": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					break;
				}
				case "NAME MOTION SENSOR LOCATION": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnNextButton();
					break;
				}
				case "NAME SENSOR DEFAULT NAME": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					break;
				}
				case "NAME MOTION SENSOR DEFAULT NAME": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "NAME MOTION SENSOR CUSTOM NAME": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Create Custom Name");
					break;
				}
				case "NAME SENSOR CUSTOM NAME": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Create Custom Name");
					break;
				}
				case "MOUNT SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnNextButton();
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					break;
				}
				case "MOUNT ON THE WALL": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnNextButton();
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Flat on a Wall");
					break;
				}
				case "MOUNT IN A CORNER": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnNextButton();
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "In a Wall Corner");
					break;
				}
				case "PLACE SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Door");
					break;
				}
				case "PLACE SENSOR ON LOCATION": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Door");
					flag = flag & sensor.clickOnNextButton();
					break;
				}
				case "TEST ACCESS SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Door");
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.clickOnNextButton();
					break;

				}
				case "TEST MOTION SENSOR": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Flat on a Wall");
					flag = flag & sensor.clickOnNextButton();
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					break;
				}
				case "MOTION SENSOR SIGNAL STRENGTH": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.isLocateSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Flat on a Wall");
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isTestSensorHeadingDisplayed();
					flag = flag & sensor.clickOnSensorNotWorking();
					flag = flag & sensor.isMotionSensorHelpScreenDisplayed();
					flag = flag & sensor.clickOnTestSignalStrength();
					flag = flag & sensor.isSignalStrengthScreenDisplayed();
					break;
				}
				case "SIGNAL STRENGTH": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.isSensorOverviewScreenDisplayed();
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.isLocateSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Door");
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isTestSensorHeadingDisplayed();
					flag = flag & sensor.clickOnSensorNotWorking();
					flag = flag & sensor.isAccessSensorHelpScreenDisplayed();
					flag = flag & sensor.clickOnTestSignalStrength();
					flag = flag & sensor.isSignalStrengthScreenDisplayed();
					break;
				}
				case "MOTION SENSOR HELP": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DASSensorUtils.enrollMotionSensor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "motion sensor");
					flag = flag & sensor.isLocateSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Hall");
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Flat on a Wall");
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isTestSensorHeadingDisplayed();
					flag = flag & sensor.clickOnSensorNotWorking();
					flag = flag & sensor.isMotionSensorHelpScreenDisplayed();
					break;
				}
				case "ACCESS SENSOR HELP": {
					flag = flag & sensor.clickOnAddSensorButton();
					DASSensorUtils.enrollDoor(testCase, inputs);
					flag = flag & sensor.clickOnSetUpButton(inputs, "access sensor");
					flag = flag & sensor.isSensorOverviewScreenDisplayed();
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					flag = flag & sensor.isLocateSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, "Door");
					DIYRegistrationUtils.selectAvailableSensorName(testCase, "Front Door");
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isPlaceSensorScreenDisplayed();
					flag = flag & sensor.clickOnNextButton();
					flag = flag & sensor.isTestSensorHeadingDisplayed();
					flag = flag & sensor.clickOnSensorNotWorking();
					flag = flag & sensor.isAccessSensorHelpScreenDisplayed();
					break;
				}
				case "MOTION SENSOR SETTINGS":
				case "WINDOW ACCESS SETTINGS":
				case "DOOR ACCESS SETTINGS": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					String givenSensorName = "";
					if (screen.get(0).toUpperCase().contains("DOOR")) {
						givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
					} else if (screen.get(0).toUpperCase().contains("MOTION SENSOR")) {
						givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
					} else if (screen.get(0).toUpperCase().contains("WINDOW")) {
						givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
					}
					security.clickOnUserGivenSensorName(givenSensorName);
					break;
				}
				case "SECURITY SOLUTION CARD": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					if (sensor.clickOnBackButton()) {
						Keyword.ReportStep_Pass(testCase, "Navigated to Base station settings");
						if (!security.isAppSettingsIconVisible(10)) {
							flag = flag & sensor.clickOnBackButton();
							Keyword.ReportStep_Pass(testCase, "NAvigated to " + screen.get(0));
						}
					}
					break;
				}
				case "BASE STATION CONFIGURATION": {
					flag = flag
							& DASSettingsUtils.navigateFromSensoListScreenToBaseStationConfigurationScreen(testCase);
					break;
				}
				case "ISMV SENSOR SETTINGS": {
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					inputs.setInputValue(DASInputVariables.ISMVMOTIONSENSORTYPE, DASInputVariables.ISMVMOTIONSENSOR);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"));
					break;
				}
				case "OSMV SENSOR SETTINGS": {
					SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
					inputs.setInputValue(DASInputVariables.OSMVMOTIONSENSORTYPE, DASInputVariables.OSMVMOTIONSENSOR);
					flag = flag & sensorScreen
							.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"));
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("KEYFOB LIST")) {
				switch (screen.get(0).toUpperCase()) {
				case "KEYFOB SETTINGS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					String keyfobName = inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1");
					flag = flag & bs.selectSensorFromSensorList(keyfobName);
					System.out.println("#############LOCATION1_NAME: " + inputs.getInputValue("LOCATION1_NAME"));
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					inputs.setInputValue(DASInputVariables.KEYFOBNAME, keyfobName);
					inputs.setInputValue(DASInputVariables.KEYFOBID, devInfo.getDASKeyfobID(keyfobName));
					System.out
							.println("#############KEYFOBNAME: " + inputs.getInputValue(DASInputVariables.KEYFOBNAME));
					System.out.println("#############KEYFOBID: " + inputs.getInputValue(DASInputVariables.KEYFOBID));
					break;
				}
				case "BASE STATION CONFIGURATION": {
					flag = flag
							& DASSettingsUtils.navigateFromKeyFobListScreenToBaseStationConfigurationScreen(testCase);
					break;
				}
				case "SENSOR LIST SETTINGS": {
					flag = flag & DASSettingsUtils.navigateFromKeyFobListScreenToSensorListScreen(testCase);
					if (flag) {
						System.out.println("Successfully navigated to Sensor List Settings Screen");
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Sensor List Settings Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Did not navigated to Sensor List Settings Screen");
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR PAIRING HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "SET UP ACCESSORIES": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnBackButton();
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SCHEDULING")) {
				switch (screen.get(0).toUpperCase()) {
				case "PRIMARY CARD": {
					SchedulingScreen scheduleScreen = new SchedulingScreen(testCase);
					flag = flag & scheduleScreen.clickOnCloseButton();

					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}

					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("THERMOSTAT SOLUTION CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT DASHBOARD": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnBackButton();
					break;
				}
				case "THERMOSTAT SETTINGS": {
					PrimaryCard pc = new PrimaryCard(testCase);
					if (pc.isCogIconVisible()) {
						flag = flag & pc.clickOnCogIcon();
						break;
					}
				}
				case "DASHBOARD": {
					PrimaryCard sensorScreen = new PrimaryCard(testCase);
					flag = flag & sensorScreen.clickOnBackButton();
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "SCHEDULING": {
					SchedulingScreen ss = new SchedulingScreen(testCase);
					if (ss.isTimeScheduleButtonVisible()) {
						flag = flag & ss.clickOnTimeScheduleButton();
					} else if (ss.isMoreButtonVisible()) {
						flag = flag & ss.ClickOnMoreButton();
						flag = flag & ss.clickOnTimeScheduleButton();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + screen.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to select schedule icon: " + screen.get(1));
					}
					break;
				}
				case "THERMOSTAT SETTINGS": {
					PrimaryCard sensorScreen = new PrimaryCard(testCase);
					if (sensorScreen.isCogIconVisible()) {
						flag = flag & sensorScreen.clickOnCogIcon();
					}
					break;
				}

				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACTIVITY HISTORY": {
					Dashboard dScreen = new Dashboard(testCase);
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					PrimaryCard pc = new PrimaryCard(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						if (pc.isBackButtonVisible()) {
							pc.clickOnBackButton();
							if (dScreen.clickOnGlobalDrawerButton()) {
								SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
								if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MESSAGES)) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Could not click on Activity history menu from Global drawer");
								} else {
									// Fetching Messages
								}
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not click on Global drawer menu from dashboard");
							}
							break;
						}
					} else if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MESSAGES)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Activity history menu from Global drawer");
						} else {
							// Fetching Messages
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "THERMOSTAT SOLUTION CARD": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.clickOnBackButton();
					break;
				}
				case "THERMOSTAT HUMIDIFICATION": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.HUMIDIFICATION);
					break;
				}
				case "THERMOSTAT DEHUMIDIFICATION": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.DEHUMIDIFICATION);
					break;
				}
				case "SLEEP BRIGHTNESS MODE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.SLEEPBRIGHTNESSMODE);
					break;
				}
				case "SOUND": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.SOUND);
					break;
				}
				case "DASHBOARD": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					PrimaryCard pc = new PrimaryCard(testCase);
					Dashboard d = new Dashboard(testCase);
					if (ts.isThermostatSettingsHeaderTitleVisible(10) && ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (pc.isCogIconVisible() && pc.isBackButtonVisible()) {
							pc.clickOnBackButton();
							HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							flag = flag & d.isGlobalDrawerButtonVisible();
						}
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
					break;
				}
				case "PRIMARYCARD": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					PrimaryCard pc = new PrimaryCard(testCase);
					if (ts.isThermostatSettingsHeaderTitleVisible(10) && ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						flag = flag & pc.isCogIconVisible();
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
					break;
				}
				case "VENTILATION": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.VENTILATION);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ACTIVITY HISTORY")) {
				switch (screen.get(0).toUpperCase()) {
				case "MANAGE ALERTS": {
					DASSettingsUtils.navigateFromActivityHistoryScreenToManageAlertsScreen(testCase, inputs);
					break;
				}
				case "GLOBAL DRAWER": {
					ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
					if (ah.isBackButtonVisible()) {
						flag &= ah.clickOnBackButton();
					}
					break;
				}
				case "DASHBOARD": {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("FROST PROTECTION")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("THERMOSTAT HUMIDIFICATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("THERMOSTAT DEHUMIDIFICATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("SLEEP BRIGHTNESS MODE")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("SOUND")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("VENTILATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT SETTINGS": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					PrimaryCard pc = new PrimaryCard(testCase);
					if (ts.isBackButtonVisible(10)) {
						ts.clickOnBackButton();
						HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isBackButtonVisible(5)) {
							ts.clickOnBackButton();
							HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (pc.isCogIconVisible()) {
								flag = flag & pc.clickOnCogIcon();
								break;
							}
						}
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(1));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("T1PRIMARYCARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "THERMOSTAT2": {
					PrimaryCard PC = new PrimaryCard(testCase);
					flag = flag && PC.clickOnBackButton();
					flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION2_DEVICE2_NAME"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + screen.get(0) + " button");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(0));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("STATS")) {
				switch (screen.get(0).toUpperCase()) {
				case "VACATION": {
					VacationHoldScreen vhs = new VacationHoldScreen(testCase);
					flag = flag && vhs.clickOnBackButton();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + screen.get(0));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + screen.get(0));
					}
				}
					break;
				}
			} else if (screen.get(1).equalsIgnoreCase("DAS SECURITY SETTINGS")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					if (DashboardUtils.navigateToDashboardFromAnyScreen(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Successfully naviagates to " + screen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigates to  " + screen.get(0));
					}
					break;
				}
				case "SECURITY SOLUTION CARD": {
					flag &= bs.clickOnBackButton();
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					break;
				}
				case "ENHANCED DETERRENCE": {
					flag &= bs.clickOnEnhancedDeterrenceOption(20);
					flag &= bs.isEnhancedDeterrenceInsideDetererenceDescriptionvisible();
					flag &= bs.isEnhancedDeterrenceheadervisible();
					break;
				}
				case "ABOUT SECURITY MODES": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						flag &= bs.clickonAboutSecurityModesoption();
						flag &= bs.isSecurityModesHeader();
					} else {
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(1000)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
								testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", "About Security Modes");
						flag &= bs.clickonAboutSecurityModesoption();
						flag &= bs.isSecurityModesHeader();
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MANAGE ALERTS")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SOLUTION CARD": {
					if (bs.clickOnBackButton() && bs.clickOnBackButton()) {
						Keyword.ReportStep_Pass(testCase, "Successfully naviagates to " + screen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigates to  " + screen.get(0));
					}
					break;
				}
				case "SECURITY SETTINGS": {
					if (bs.clickOnBackButton() && bs.clickOnBackButton()) {
						Keyword.ReportStep_Pass(testCase, "Successfully naviagates to " + screen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigates to  " + screen.get(0));
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MANAGE USERS")) {
				ManageUsersScreen mus = new ManageUsersScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "INVITE NEW USER": {
					if (mus.isInviteNewUserButtonVisible()) {
						flag &= mus.clickOnInviteNewUserButton();
						if (mus.isInviteUserScreenTitleVisible()) {
							Keyword.ReportStep_Pass(testCase, "Successfully naviagates to " + screen.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigates to  " + screen.get(0));
						}
						break;
					}
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("INVITE NEW USER")) {
				ManageUsersScreen mus = new ManageUsersScreen(testCase);
				switch (screen.get(0).toUpperCase()) {
				case "MANAGE USERS": {
					if (mus.isBackButtonVisible()) {
						flag &= mus.clickOnBackButton();
						if (mus.isManageUsersScreenHeaderVisible(10)) {
							Keyword.ReportStep_Pass(testCase, "Successfully naviagates to " + screen.get(0));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigates to  " + screen.get(0));
						}
						break;
					}
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "GEOFENCE RADIUS": {
					GeofenceSettings gs = new GeofenceSettings(testCase);
					if (gs.isGeofenceRadiusOptionVisible()) {
						flag &= gs.clickOnGeofenceRadiusOption();
						if (gs.isGeofenceRadiusScreenTitleVisible()) {
							Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to: " + screen.get(1));
						}
					}
					break;
				}
				case "GLOBAL DRAWER" : {
					GeofenceSettings gs = new GeofenceSettings(testCase);
					GlobalDrawerScreen gds= new GlobalDrawerScreen(testCase);
					if(gs.isBackButtonVisible()) {
						flag &= gs.clickOnBackButton();
						if(gds.isAccountHeaderTitleVisible()) {
							Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
						}else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to: " + screen.get(1));
						}
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + screen.get(0) + " for " + screen.get(1));
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ADDRESS")) {
				switch (screen.get(0).toUpperCase()) {
				case "EDIT ADDRESS": {
					AddressScreen ads = new AddressScreen(testCase);
					if (ads.isEditButtonInAddressScreenVisible()) {
						flag &= ads.clickOnEditButtonInAddressScreen();
						if (ads.isEditAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
							Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to: " + screen.get(1));
						}
					}
					break;
				}
				case "ACCOUNT DETAILS": {
					AddressScreen ads = new AddressScreen(testCase);
					GlobalDrawerScreen gds = new GlobalDrawerScreen(testCase);
					EditAccountScreen eas = new EditAccountScreen(testCase);
					if (ads.isAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
						flag &= ads.clickOnBackButton();
						if (gds.isAccountDetailsOptionVisible()) {
							flag &= gds.clickAccountDetailsOption();
							if (eas.isEditAccountScreenTitleVisible()) {
								Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to navigate to: " + screen.get(1));
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to display Account Details option in Global Drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to display Address Screen");
					}
					break;
				}
				case "DASHBOARD": {
					AddressScreen ads = new AddressScreen(testCase);
					Dashboard d = new Dashboard(testCase);
					if (ads.isAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
						flag &= ads.clickOnBackButton();
						if (ads.isBackButtonVisible()) {
							flag &= ads.clickOnBackButton();
							if (d.isAddDeviceIconVisible(10) || d.isAddDeviceIconBelowExistingDevicesVisible(1)) {
								Keyword.ReportStep_Pass(testCase, screen.get(1) + " Screen is displayed");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to display the screen: " + screen.get(1));
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to display Back button in Global Drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to display Back button in Address Screen");
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("EDIT ADDRESS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCOUNT DETAILS": {
					AddressScreen ads = new AddressScreen(testCase);
					GlobalDrawerScreen gds = new GlobalDrawerScreen(testCase);
					EditAccountScreen eas = new EditAccountScreen(testCase);
					if (ads.isBackButtonVisible()) {
						flag &= ads.clickOnBackButton();
						if (ads.isCancelLocationChangesPopupVisible() && ads.isCancelLocationChangesPopupMsgVisible()
								&& ads.isNoButtonInCancelLocationChangesPopupVisible()
								&& ads.isYesButtonInCancelLocationChangesPopupVisible()) {
							flag &= ads.clickOnYesButtonInCancelLocationChangesPopup();
							if (ads.isAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
								flag &= ads.clickOnBackButton();
								if (gds.isAccountDetailsOptionVisible()) {
									flag &= gds.clickAccountDetailsOption();
									if (eas.isEditAccountScreenTitleVisible()) {
										Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
									} else {
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to navigate to: " + screen.get(1));
									}
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to display Edit Account option in Global Drawer");
								}
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to display Address Screen");
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to display Cancel Location Popup");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to display Back button in Edit Address screen");
					}
					break;
				}
				case "DASHBOARD": {
					AddressScreen ads = new AddressScreen(testCase);
					Dashboard d = new Dashboard(testCase);
					if (ads.isBackButtonVisible()) {
						flag &= ads.clickOnBackButton();
						if (ads.isCancelLocationChangesPopupVisible() && ads.isCancelLocationChangesPopupMsgVisible()
								&& ads.isNoButtonInCancelLocationChangesPopupVisible()
								&& ads.isYesButtonInCancelLocationChangesPopupVisible()) {
							flag &= ads.clickOnYesButtonInCancelLocationChangesPopup();
							if (ads.isAddressScreenTitleVisible() && ads.isBackButtonVisible()) {
								flag &= ads.clickOnBackButton();
								if (ads.isBackButtonVisible()) {
									flag &= ads.clickOnBackButton();
									if (d.isAddDeviceIconVisible(10)
											|| d.isAddDeviceIconBelowExistingDevicesVisible(1)) {
										Keyword.ReportStep_Pass(testCase, screen.get(1) + " Screen is displayed");
									} else {
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to display the screen: " + screen.get(1));
									}
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to display Back button in Global Drawer");
								}
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to display Back button in Address Screen");
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to display Cancel Location Popup");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to display Back button in Edit Address screen");
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PRIVACY POLICY AND EULA")) {
				switch (screen.get(0).toUpperCase()) {
				case "ABOUT THE APP": {
					AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
					if (atas.isDoneButtonInPrivacyPolicyAndEULAScreenVisible()) {
						flag &= atas.clickOnDoneButtonInPrivacyPolicyAndEULAScreen();
						if (atas.isAboutTheAppScreenTitleVisible()
								&& atas.isPrivacyPolicyAndEULAOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to: " + screen.get(1));
						}
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ACKNOWLEDGEMENTS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ABOUT THE APP": {
					AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						testCase.getMobileDriver().navigate().back();
					} else {
						if (atas.isDoneButtonInAcknowledgementsScreenVisible()) {
							flag &= atas.clickOnDoneButtonInAcknowledgementsScreen();
							if (atas.isAboutTheAppScreenTitleVisible()
									&& atas.isAcknowledgementsOptionInAboutTheAppArrowVisible()) {
								Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to navigate to: " + screen.get(1));
							}
						}
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GET HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "ABOUT THE APP": {
					AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						testCase.getMobileDriver().navigate().back();
					} else {
						if (atas.isNavBackToHoneywellButtonInGetHelpScreenVisible()) {
							flag &= atas.clickOnNavBackToHoneywellButtonInGetHelpScreen();
							if (atas.isAboutTheAppScreenTitleVisible() && atas.isGetHelpOptionInAboutTheAppVisible()) {
								Keyword.ReportStep_Pass(testCase, screen.get(1) + " displayed");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to navigate to: " + screen.get(1));
							}
						}
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("USERS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCOUNT DETAILS": {
					SecondaryCardSettings sc= new SecondaryCardSettings(testCase);
					ManageUsersScreen mus = new ManageUsersScreen(testCase);
					if(mus.isManageUsersScreenHeaderVisible(10)) {
						flag &= mus.clickOnBackButton();
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ACCOUNTDETAILS)) {
							Keyword.ReportStep_Pass(testCase, "User is in Account Details screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Account Details screen");
						}
					}
					break;
				}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + screen.get(1));
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
