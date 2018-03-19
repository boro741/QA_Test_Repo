package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
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
			if (screen.get(1).equalsIgnoreCase("SWITCH PRIMARY CARD")
					|| screen.get(1).equalsIgnoreCase("DIMMER PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromPrimaryCard(testCase, inputs);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ZWAVE DEVICES")) {
				switch (screen.get(0).toUpperCase()) {
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
					flag = flag & DASZwaveUtils.navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
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
					flag = flag & DASZwaveUtils.navigateToPrimaryCardFromZwaveIndividualDeviceSettings(testCase, inputs);
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
					flag = flag & DASZwaveUtils.navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
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
			} else if (screen.get(1).equalsIgnoreCase("Z-Wave Utilities")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					flag = flag & DASZwaveUtils.navigateToZwaveDevicesFromZwaveUtilitiesSettings(testCase, inputs);
					break;
				}
				case "DASHBOARD": {
					flag = flag & DASZwaveUtils.navigateToDashboardFromZwaveUtilitiesSettings(testCase, inputs);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL INCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag = flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL EXCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag = flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
					break;
				}
				}
			}
			// Navigation from Dashboard
			else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "Z-WAVE CONTROLLER DETAILS": {
					flag = flag & DASZwaveUtils.navigateToControllerDetailsFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL INCLUSION": {
					flag = flag & DASZwaveUtils.navigateToGeneralInclusionFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL EXCLUSION": {
					flag = flag & DASZwaveUtils.navigateToGeneralExclusionFromDashboard(testCase);
					break;
				}
				case "SWITCH PRIMARY CARD": {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Switch1");
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
				case "Z-WAVE UTILITIES": {
					flag = DASZwaveUtils.navigateToZwaveUtilitiesFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICES": {
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
				case "Z-WAVE INCLUSION THROUGH ADD NEW DEVICE ICON": {
					flag = flag & DASZwaveUtils.navigateToAddDeviceScreenFromDashboardThroughIcon(testCase);
					break;
				}
				case "ADD NEW DEVICE DASHBOARD": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.isAddDeviceIconVisible(15)) {
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
					if (bs.isEntryExitDelaySettingsOptionVisible()) {
						flag = flag & bs
								.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not find Entry/Exit Delay option on DAS Panel Settings screen");
					}
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
			} else if (screen.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "ADD NEW DEVICE DASHBOARD": {
					AddNewDeviceScreen addNewDevice = new AddNewDeviceScreen(testCase);
					flag = flag & addNewDevice.isAddNewDeviceHeaderDisplayed(30);
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
				}
			} else if (screen.get(1).equalsIgnoreCase("POWER BASE STATION INSTRUCTIONS")) {
				switch (screen.get(0).toUpperCase()) {
				case "LOOKING FOR BASE STATION": {
					return DIYRegistrationUtils.navigateFromPowerBaseStationToLookingForBaseStation(testCase);

				}
				case "REGISTER BASE STATION": {
					return DIYRegistrationUtils.navigateFromPowerBaseStationToRegisterBaseStation(testCase);
				}
				case "SELECT BASE STATION": {
					return DIYRegistrationUtils.navigateFromPowerBaseStationToSelectBaseStation(testCase);
				}
				case "DASHBOARD": {
					return DIYRegistrationUtils.navigateFromPowerBaseStationToDashboard(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("REGISTER BASE STATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CONNECT TO NETWORK": {
					return DIYRegistrationUtils.navigateFromRegisterBaseStationToConnectToNetwork(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
				switch (screen.get(0).toUpperCase()) {
				case "SMART HOME SECURITY SUCCESS": {
					return DIYRegistrationUtils.navigateFromConnectToNetworkToSmartHomeSecuritySuccess(testCase);
				}
				case "SET UP ACCESSORIES": {
					return DIYRegistrationUtils.navigateFromConnectToNetworkToSetUpAccessories(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SET UP ACCESSORIES")) {
				switch (screen.get(0).toUpperCase()) {
				case "OVERVIEW": {
					return DIYRegistrationUtils.navigateFromSetUpAccessoriesToOverview(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("OVERVIEW")) {
				switch (screen.get(0).toUpperCase()) {
				case "LOCATE SENSOR": {
					return DIYRegistrationUtils.navigateFromOverviewToLocateSensor(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("LOCATE SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "NAME SENSOR": {
					return DIYRegistrationUtils.navigateFromLocateSensorToNameSensor(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CHECK LOCATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CHECK LOCATION SIGNAL": {
					return DIYRegistrationUtils.navigateFromCheckLocationToCheckLocationSignal(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("CHECK LOCATION SIGNAL")) {
				switch (screen.get(0).toUpperCase()) {
				case "PREPARE SENSOR": {
					return DIYRegistrationUtils.navigateFromCheckLocationSignalToPrepareSensor(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PREPARE SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "PLACE ADHESIVE STRIPS": {
					return DIYRegistrationUtils.navigateFromPrepareSensorToPlaceAdhesiveStrips(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("PLACE ADHESIVE STRIPS")) {
				switch (screen.get(0).toUpperCase()) {
				case "MOUNT SENSOR": {
					return DIYRegistrationUtils.navigateFromPlaceAdhesiveStripsToMountSensor(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("MOUNT SENSOR")) {
				switch (screen.get(0).toUpperCase()) {
				case "SENSOR READY": {
					return DIYRegistrationUtils.navigateFromMountSensorToSensorReady(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SENSOR READY")) {
				switch (screen.get(0).toUpperCase()) {
				case "SET UP ACCESSORIES CONFIGURED": {
					return DIYRegistrationUtils.navigateFromSensorReadyToSetUpAccConfigured(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SET UP ACCESSORIES CONFIGURED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					return DIYRegistrationUtils.navigateFromSetUpAccConfiguredToEnableGeoFencing(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENABLE GEOFENCING")) {
				switch (screen.get(0).toUpperCase()) {
				case "GEOFENCE": {
					return DIYRegistrationUtils.navigateFromEnableGeoFencingToGeoFence(testCase);
				}
				case "ENABLE AMAZON ALEXA": {
					return DIYRegistrationUtils.navigateFromEnableGeoFencingToEnableAmazonAlexa(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE")) {
				switch (screen.get(0).toUpperCase()) {
				case "GEOFENCE ENABLED": {
					return DIYRegistrationUtils.navigateFromGeoFenceToGeoFenceEnabled(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("GEOFENCE ENABLED")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE AMAZON ALEXA": {
					return DIYRegistrationUtils.navigateFromGeoFenceEnabledToEnableAmazonAlexa(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SMART HOME SECURITY SUCCESS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENABLE GEOFENCING": {
					return DIYRegistrationUtils.navigateFromSmartHomeSecuritySuccessToEnableGeoFencing(testCase);
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("ENABLE AMAZON ALEXA")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					return DIYRegistrationUtils.navigateFromEnableAmazonAlexaToDashboard(testCase);
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
			} 
			else if (screen.get(1).equalsIgnoreCase("Camera Settings Introduction")) {
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
