package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
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
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.Schedule;
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
			if (screen.get(1).equalsIgnoreCase("SWITCH PRIMARY CARD")||screen.get(1).equalsIgnoreCase("DIMMER PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				}
			}
			else if (screen.get(1).equalsIgnoreCase("ZWAVE DEVICES")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				case "SWITCH SETTINGS": {
					ZwaveScreen zs = new ZwaveScreen(testCase);
					zs.ClickSwitchSettingFromZwaveUtilities();
					break;
				}
				case "SWITCH PRIMARY CARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					NavigateToPrimaryCardFromDashboard(testCase, "Switch1");
					break;
				}
				case "DIMMER PRIMARY CARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					NavigateToPrimaryCardFromDashboard(testCase, "Dimmer1");
					break;
				}
				}
			} 
			else if (screen.get(1).equalsIgnoreCase("SWITCH SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				case "SWITCH PRIMARY CARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					NavigateToPrimaryCardFromDashboard(testCase, "Switch1");
					break;
				}
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("DIMMER SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				case "DIMMER PRIMARY CARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					NavigateToPrimaryCardFromDashboard(testCase, "Dimmer1");
					break;
				}
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				}
			}else if (screen.get(1).equalsIgnoreCase("Z-Wave Utilities")) {
				switch (screen.get(0).toUpperCase()) {
				case "ZWAVE DEVICES": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL INCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag= flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL EXCLUSION": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					flag= flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
					break;
				}
				}
			}
			// Navigation from Dashboard
			else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "Z-WAVE CONTROLLER DETAILS":{
					DASZwaveUtils.navigateToControllerDetailsFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL INCLUSION": {
					DASZwaveUtils.navigateToGeneralInclusionFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICE THROUGH GENERAL EXCLUSION": {
					DASZwaveUtils.navigateToGeneralExclusionFromDashboard(testCase);
					break;
				}
				case "SWITCH PRIMARY CARD": {
					NavigateToPrimaryCardFromDashboard(testCase, "Switch1");
					break;
				}
				case "SWITCH SETTINGS": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ZWAVEDEVICES)) {
							ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
							if (!zwaveScreen.ClickSwitchSettingFromZwaveUtilities()) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not click on Switch Settings From Zwave Utilities");
							} else {
								Keyword.ReportStep_Pass(testCase,
										"Clicked on SwitchSetting From ZwaveUtilities");
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "Z-WAVE UTILITIES": {
					DASZwaveUtils.navigateToZwaveUtilitiesFromDashboard(testCase);
					break;
				}
				case "Z-WAVE DEVICES": {
					DASZwaveUtils.navigateToZwaveDevicesFromDashboard(testCase);
					break;
				}
				case "DIMMER PRIMARY CARD": {
					NavigateToPrimaryCardFromDashboard(testCase, "Dimmer1");
					break;
				}
				case "DIMMER SETTINGS": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ZWAVEDEVICES)) {
							ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
							if (!zwaveScreen.ClickDimmerSettingFromZwaveUtilities()) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not click on Switch Settings From Zwave Utilities");
							} else {
								Keyword.ReportStep_Pass(testCase,
										"Clicked on SwitchSetting From ZwaveUtilities");
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "Z-WAVE DEVICE ADD NEW DEVICE": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDNEWDEVICE)) {
							AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
							ads.clickOnZwaveFromAddNewDevice();
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "ADD NEW DEVICE GLOBAL DRAWER": {
					Dashboard ds = new Dashboard(testCase);
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
				case "ADD NEW DEVICE DASHBOARD": {
					Dashboard ds = new Dashboard(testCase);
					if (ds.isAddDeviceIconVisible(5)) {
						flag = flag & ds.clickOnAddNewDeviceIcon();
					}
					break;
				}
				case "ADD NEW DEVICE IN GLOBAL DRAWER": {
					Dashboard ds = new Dashboard(testCase);
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackArrowInSelectADeviceScreenVisible()) {
						dasDIY.clickOnBackArrowInSelectADeviceScreen();
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
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					break;
				}
				// Navigate from 'Dashboard' to 'Base Station Configuration'
				// Author: Pratik P. Lalseta (H119237)
				case "BASE STATION CONFIGURATION": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					flag = flag & bs
							.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
					break;
				}
				// Navigate from 'Dashboard' to 'Entry-Exit Delay Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "ENTRY-EXIT DELAY": {
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isEntryExitDelaySettingsOptionVisible()) {
						flag = flag & bs
								.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Unable to find Entry/Exit Delay option on DAS Panel Settings");
					}
					break;
				}


				// Navigate from 'Dashboard' to 'Keyfobs List'
				// Author: Pratik P. Lalseta (H119237)
				case "KEYFOB": {
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					break;
				}

				case "SENSOR SETTINGS": {
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
					String sensorName = "";
					if (!inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")
							&& !inputs.isInputAvailable("LOCATION1_MOTIONENSOR1_NAME")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No sensor names were provided in the Requirement file");
						return flag;
					}
					if (inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")) {
						sensorName = inputs.getInputValue("LOCATION1_ACCESSSENSOR1_NAME");
						inputs.setInputValue(DASInputVariables.SENSORTYPE, DASInputVariables.ACCESSSENSOR);
					} else {
						sensorName = inputs.getInputValue("LOCATION1_MOTIONSENSOR1_NAME");
						inputs.setInputValue(DASInputVariables.SENSORTYPE, DASInputVariables.MOTIONSENSOR);
					}
					flag = flag & bs.selectSensorFromSensorList(sensorName);
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					inputs.setInputValue(DASInputVariables.SENSORNAME, sensorName);
					inputs.setInputValue(DASInputVariables.SENSORID, devInfo.getDASSensorID(sensorName));
					inputs.setInputValue(DASInputVariables.SENSORRESPONSETYPE,
							devInfo.getDASSensorResponseType(sensorName));	
					break;
				}

				case "KEYFOB SETTINGS": {
					flag = flag & this.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					String keyfobName = "";
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
					if (!inputs.isInputAvailable("LOCATION1_KEYFOB1_NAME")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No keyfob names were provided in the Requirement file");
						return flag;
					}
					keyfobName = inputs.getInputValue("LOCATION1_KEYFOB1_NAME");
					flag = flag & bs.selectSensorFromSensorList(keyfobName);
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					inputs.setInputValue(DASInputVariables.KEYFOBNAME, keyfobName);
					inputs.setInputValue(DASInputVariables.KEYFOBID, devInfo.getDASKeyfobID(keyfobName));
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
				// Navigate from 'Entry/Exit Delay Settings' to 'Security Settings'
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
		}


				else if (screen.get(1).equalsIgnoreCase("Security Settings")) {
					switch (screen.get(0).toUpperCase()) {
					// Navigate from 'Security Settings' to 'Entry/Exit Delay Settings'
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
						if (bs.isBackButtonVisible()) {
							flag = flag & bs.clickOnBackButton();
						}
						if (bs.isBackButtonVisible(10)) {
							flag = flag & bs.clickOnBackButton();
						}
						if (bs.isBackButtonVisible()) {
							flag = flag & bs.clickOnBackButton();
						}
						if (!d.areDevicesVisibleOnDashboard()) {
							flag = flag & bs.clickOnBackButton();
							if (!d.areDevicesVisibleOnDashboard()) {
								flag = flag & bs.clickOnBackButton();
							}
						}
						break;
					}
					// Navigate from 'Base Station Configuration' to 'Model and Firmware Details'
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
						if (addNewDevice.isAddNewDeviceHeaderDisplayed()) {
							return flag;
						}
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("POWER BASE STATION")) {
					switch (screen.get(0).toUpperCase()) {
					case "POWER BASE STATION INSTRUCTIONS": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isNextButtonVisible()) {
							flag = flag & dasDIY.clickOnNextButton();
						}
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("POWER BASE STATION INSTRUCTIONS")) {
					switch (screen.get(0).toUpperCase()) {
					case "LOOKING FOR BASE STATION": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isNextButtonVisible()) {
							flag = flag & dasDIY.clickOnNextButton();
						}
						DIYRegistrationUtils.waitForLookingForBaseStationProgressBarToComplete(testCase);
						break;
					}
					case "REGISTER BASE STATION": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isNextButtonVisible()) {
							flag = flag & dasDIY.clickOnNextButton();
						}
						DIYRegistrationUtils.waitForLookingForBaseStationProgressBarToComplete(testCase);
						dasDIY.isRegisterBaseStationHeaderTitleVisible();
						dasDIY.isQRCodeDisplayed();
						break;
					}
					case "SELECT BASE STATION": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isNextButtonVisible()) {
							flag = flag & dasDIY.clickOnNextButton();
						}
						DIYRegistrationUtils.waitForLookingForBaseStationProgressBarToComplete(testCase);
						dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("REGISTER BASE STATION")) {
					switch (screen.get(0).toUpperCase()) {
					case "CONNECT TO NETWORK": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						DIYRegistrationUtils.waitForLookingForNetworkConnectionProgressBarToComplete(testCase);
						dasDIY.isConnectToNetworkHeaderTitleVisible();
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
					switch (screen.get(0).toUpperCase()) {
					case "SMART HOME SECURITY SUCCESS": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible();
						if (dasDIY.isNoButtonInSmartHomeSecuritySuccessScreenVisible()) {
							dasDIY.clickOnNoButtonInSmartHomeSecuritySuccessScreen();
						}
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("SMART HOME SECURITY SUCCESS")) {
					switch (screen.get(0).toUpperCase()) {
					case "ENABLE GEOFENCING": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						dasDIY.isGeoFencingHeaderTitleVisible();
						if (dasDIY.isSkipButtonInGeoFencingScreenVisible()) {
							dasDIY.clickOnSkipButtonInGeoFencingScreen();
						}
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("ENABLE GEOFENCING")) {
					switch (screen.get(0).toUpperCase()) {
					case "ENABLE AMAZON ALEXA": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						dasDIY.isAmazonAlexaHeaderTitleVisible();
						if (dasDIY.isSkipButtonInAmazonAlexaVisible()) {
							dasDIY.clickOnSkipButtonInAmazonAlexaScreen();
						}
						break;
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("ENABLE AMAZON ALEXA")) {
					switch (screen.get(0).toUpperCase()) {
					case "DASHBOARD": {
						DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
						if (dasDIY.isIncreaseSecurityPopupVisible()) {
							dasDIY.clickOnDontUseButtonInIncreaseSecurityPopup();
							if (dasDIY.isGotItButtonInAccessMoreInfoPopupVisible()) {
								dasDIY.clickOnGotItButtonInAccessMoreInfoPopup();
							}
							if (dasDIY.isGotItButtonInQuickControlsPopupVisible()) {
								dasDIY.clickOnGotItButtonInQuickControlsPopup();
							}
							if (dasDIY.isIncreaseSecurityPopupVisible()) {
								dasDIY.clickOnDontUseButtonInIncreaseSecurityPopup();
							}
						} else {
							if (dasDIY.isGotItButtonInAccessMoreInfoPopupVisible()) {
								dasDIY.clickOnGotItButtonInAccessMoreInfoPopup();
							}
							if (dasDIY.isGotItButtonInQuickControlsPopupVisible()) {
								dasDIY.clickOnGotItButtonInQuickControlsPopup();
							}
						}
						break;
					}
					}
				}
				else if (screen.get(1).equalsIgnoreCase("Keyfob")) {
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
					case "KEYFOB":{
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
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
					}
					}
				}

				else if (screen.get(1).equalsIgnoreCase("Sensor Settings")) {
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

	public static boolean NavigateToDashboardFromAnyScreen(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			Schedule sch = new Schedule(testCase);
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton")) {
				Keyword.ReportStep_Pass(testCase,
						"Navigate To Primary Card : User is already on the Primary Card or Dashboard");
				return flag;
			} else {
				int i = 0;
				while ((!MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton")) && i < 10) {
					if (sch.isCloseButtonVisible(3)) {
						flag = flag & sch.clickOnCloseButton();
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					}
					i++;
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton")
						|| (MobileUtils.isMobElementExists(fieldObjects, testCase, "AddNewDeviceIcon"))) {
					Keyword.ReportStep_Pass(testCase,
							"Navigate To Primary Card : Successfully navigated to Primary card or Dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Navigate To Primary Card : Failed to navigate to Primary card or Dashboard");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Navigate To Primary Card : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean NavigateToPrimaryCardFromDashboard(TestCases testCase, String expectedDevice) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardIconText", 30)) {
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(fieldObjects, testCase,
					"DashboardIconText");
			for (WebElement e : dashboardIconText) {
				String displayedText = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					displayedText = e.getText();
				} else {
					try {
						displayedText = e.getAttribute("value");
					} catch (Exception e1) {
					}
				}
				if (displayedText.equalsIgnoreCase(expectedDevice)) {
					e.click();
					Keyword.ReportStep_Pass(testCase, "Clicked on device: " + expectedDevice);
					break;
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dashboard Icons not found");
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Security Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToSecuritySettingsScreen method navigates
	 * from the dashboard to the security screen by clicking on the Global
	 * Drawer option and clicking on the camera name on the secondary card
	 * settings
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to
	 *            the testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns
	 *         'false' if navigation is not successful.
	 */
	private boolean navigateFromDashboardScreenToSecuritySettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		Dashboard d = new Dashboard(testCase);
		SecondaryCardSettings s = new SecondaryCardSettings(testCase);
		try {
			if (d.isGlobalDrawerButtonVisible(5)) {
				flag = flag & d.clickOnGlobalDrawerButton();
				if (!s.areSecondaryCardSettingsVisible(2)) {
					flag = flag & d.clickOnGlobalDrawerButton();
				}
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (s.areSecondaryCardSettingsVisible()) {
						List<WebElement> icons = s.getSecondaryCardSettings();
						boolean iconFound = false;
						for (WebElement icon : icons) {
							if (icon.getAttribute("text")
									.equalsIgnoreCase(inputs.getInputValue("LOCATION1_CAMERA1_NAME"))) {
								iconFound = true;
								icon.click();
								break;
							}
						}
						if (iconFound) {
							Keyword.ReportStep_Pass(testCase, "Successfully navigated to DAS Panel Settings");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not find DAS Panel Settings button");
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not find items on Global Drawer list");
					}
				} else {
					if (MobileUtils.isMobElementExists("name", inputs.getInputValue("LOCATION1_CAMERA1_NAME"), testCase,
							3)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "name",
								inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not find DAS Panel Settings button");
					}
				}

			} else

			{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Global Drawer button");
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
}
