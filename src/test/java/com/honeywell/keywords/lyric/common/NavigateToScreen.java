package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASActivityLogsUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.SensorStatusScreen;
import com.honeywell.screens.ZwaveScreen;

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

	@Override
	@KeywordStep(gherkins = "^user navigates to (.*) screen from the (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (screen.get(1).equalsIgnoreCase("Alarm history")) {
				switch (screen.get(0).toUpperCase()) {
				case "ALARM": {
					AlarmScreen open = new AlarmScreen(testCase);
					open.openAlarmHistory();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}
			else if (screen.get(1).equalsIgnoreCase("SENSOR STATUS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACTIVITY LOG": {
					SensorStatusScreen sensorScreen = new SensorStatusScreen(testCase);
					flag = flag & sensorScreen.clickOnSensorStatusScreenBack(testCase);
					flag = flag & DASActivityLogsUtils.openActivityLogs(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SWITCH PRIMARY CARD")
					|| screen.get(1).equalsIgnoreCase("DIMMER PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
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
				}
			}
			// Navigation from Dashboard
			else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "DOOR ACCESS SETTINGS": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if(security.isAppSettingsIconVisible(10)) {
						security.clickOnAppSettingsIcon();
					}
					flag=LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", "Base Station Configuration");

					flag = flag & security.clickOnSensorButton();
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
					security.clickOnUserGivenSensorName(givenSensorName);


					break;
				}
				case "ACTIVITY HISTORY": {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MESSAGES)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Activity history menu from Global drawer");
						}else{
							//Fetching Messages
						}
					}else {
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
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not click on Activity history menu from Global drawer");
						}else{
							//Fetching Messages
							sc.selectOptionFromSecondarySettings(SecondaryCardSettings.MEMBERSHIPSUBSCRIPTION);
							//Keyword.ReportStep_Pass(testCase,"Honeywell Membership page opened.");
						}
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not click on Global drawer menu from dashboard");
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
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

					break;
				}

				case "GLOBAL DRAWER": {
					Thread.sleep(5000);
					Dashboard ds = new Dashboard(testCase);
					if (!ds.clickOnGlobalDrawerButton()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
						flag=false;

					}

					break;
				}

				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
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
			} else if (screen.get(1).equalsIgnoreCase("Security Settings")) {
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
				}
			} else if (screen.get(1).equalsIgnoreCase("ADD NEW DEVICE DASHBOARD")) {
				switch (screen.get(0).toUpperCase()) {
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
					flag = flag & dasDIY.selectDeviceToInstall(screen.get(0));
					break;
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
				}
			} else if (screen.get(1).equalsIgnoreCase("VIDEO CLIP")) {
				switch (screen.get(0).toUpperCase()) {
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
				}
			} else if (screen.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE DASHBOARD": {
					AddNewDeviceScreen addNewDevice = new AddNewDeviceScreen(testCase);
					flag = flag & addNewDevice.isAddNewDeviceHeaderDisplayed(30);
					break;
				}
				}
			}  else if (screen.get(1).equalsIgnoreCase("CREATE LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CHOOSE LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if(dasDIY.isBackButtonInCreateLocationScreenVisible()) {
						flag = flag & dasDIY.clickOnBackButtonInCreateLocationScreen();
					}
					break;
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
				}
			}  else if (screen.get(1).equalsIgnoreCase("BASE STATION HELP")) {
				switch (screen.get(0).toUpperCase()) {
				case "POWER BASE STATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if(dasDIY.isBackButtonInBaseStationHelpScreenVisible()) {
						flag = flag & dasDIY.clickOnBackButtonInBaseStationHelpScreen();
					}
					break;
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
				}
			} else if (screen.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
				switch (screen.get(0).toUpperCase()) {
				case "SMART HOME SECURITY SUCCESS": {
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
							"ALMOST DONE LOADING PROGRESS BAR TEXT", 3);
					flag = flag & DIYRegistrationUtils.navigateFromConnectToNetworkToSmartHomeSecuritySuccess(testCase);
					break;
				}
				case "SET UP ACCESSORIES": {
					flag = flag & DIYRegistrationUtils.navigateFromConnectToNetworkToSetUpAccessories(testCase);
					break;
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
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE ADHESIVE STRIPS")) {
				switch (screen.get(0).toUpperCase()) {
				case "MOUNT SENSOR": {
					flag = flag & DIYRegistrationUtils.navigateFromPlaceAdhesiveStripsToMountSensor(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MOUNT SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR READY": {
					flag = flag & DIYRegistrationUtils.navigateFromMountSensorToSensorReady(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR READY")) {
				switch (screen.get(0).toUpperCase()) {
				case "SET UP ACCESSORIES CONFIGURED": {
					flag = flag & DIYRegistrationUtils.navigateFromSensorReadyToSetUpAccConfigured(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SET UP ACCESSORIES CONFIGURED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					flag = flag & DIYRegistrationUtils.navigateFromSetUpAccConfiguredToEnableGeoFencing(testCase);
					break;
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
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE ENABLED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE AMAZON ALEXA": {
					flag = flag & DIYRegistrationUtils.navigateFromGeoFenceEnabledToEnableAmazonAlexa(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SMART HOME SECURITY SUCCESS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					flag = flag & DIYRegistrationUtils.navigateFromSmartHomeSecuritySuccessToEnableGeoFencing(testCase);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENABLE AMAZON ALEXA")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					flag = flag & DIYRegistrationUtils.navigateFromEnableAmazonAlexaToDashboard(testCase);
					break;
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
					if (!inputs.isInputAvailable("LOCATION1_KEYFOB1_NAME")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No Keyfob names were provided in the Requirement file");
						return flag;
					}
					flag = flag & bs.selectKeyfobFromKeyfobList(inputs.getInputValue("LOCATION1_KEYFOB1_NAME"));
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
			}

			else if (screen.get(1).equalsIgnoreCase("Security Solution Card")) {
				switch (screen.get(0).toUpperCase()) {
				case "DOOR ACCESS SETTINGS": {
					SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
					Thread.sleep(5000);
					if(security.isAppSettingsIconVisible(15)) {
						security.clickOnAppSettingsIcon();

						flag=LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", "Base Station Configuration");

						flag = flag & security.clickOnSensorButton();
						String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
						security.clickOnUserGivenSensorName(givenSensorName);

					}
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
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
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
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			}
			else if (screen.get(1).equalsIgnoreCase("Alarm")) {
				switch (screen.get(0).toUpperCase()) {
				case "ALARM HISTORY": {
					AlarmScreen open = new AlarmScreen(testCase);
					open.openAlarmHistory();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
				}
				}
			} 
			else if(screen.get(1).equalsIgnoreCase("Honeywell Help web portal")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCESS SENSOR HELP": {
					if(!testCase.getPlatform().contains("IOS")){
						MobileUtils.pressBackButton(testCase);
					}
				}
				}
			}
			else if(screen.get(1).equalsIgnoreCase("Test Signal Strength")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCESS SENSOR HELP": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					sensor.clickOnSignalStrengthBackButton();
					sensor.isAccessSensorHelpScreenDisplayed();
				}
				}
			}
			else if(screen.get(1).equalsIgnoreCase("Access Sensor Help")) {
				switch (screen.get(0).toUpperCase()) {
				case "TEST ACCESS SENSOR": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					sensor.clickOnAccessSensorHelpBack();
					sensor.isTestSensorHeadingDisplayed();
				}
				}
			}
			else if(screen.get(1).equalsIgnoreCase("Test Access Sensor")) {
				switch (screen.get(0).toUpperCase()) {
				case "ACCESS SENSOR SETTINGS": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnTestSensorBack();
				}
				}
			}
			else if(screen.get(1).equalsIgnoreCase("Access Sensor Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SOLUTION CARD": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					SensorStatusScreen sensorStatus = new SensorStatusScreen(testCase);
					SecuritySolutionCardScreen security=new SecuritySolutionCardScreen(testCase);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if(!security.isSecuritySettingsTitleVisible() && !security.isAppSettingsIconVisible(10)) {
						flag = flag & sensor.clickOnSensorListSettingBack();
						if(sensorStatus.isSensorStatusVisible()) {
							Keyword.ReportStep_Pass(testCase, "Sensor screen visible");
						}
						if(!security.isSecuritySettingsTitleVisible()) {
							flag = flag & bs.clickOnBackButton();
						}
						flag = flag & bs.clickOnBackButton();
						if(security.isAppSettingsIconVisible(10))
						{
							Keyword.ReportStep_Pass(testCase, "Security solution screen visible");
						}
					}
					break;
				}
				case "ACCESS SENSOR OFF":{
					SensorSettingScreen s = new SensorSettingScreen(testCase);
					if(s.clickOnSensorStatusOffOnAccessSensorScreen()) {
						flag=flag & s.checkSensorNameInSensorOffScreen(inputs);
						flag=flag &s.checkSensorIsOffTextVisible();
						if(flag) {
							flag = flag & s.clickOnAccessSensorHelpBack();
							Keyword.ReportStep_Pass(testCase, "SensorOff screen is displayed");
						}
					}
					break;
				}
				}
			}
			else {
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
