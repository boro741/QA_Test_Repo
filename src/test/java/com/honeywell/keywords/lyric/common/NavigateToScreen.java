package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

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
			HashMap<String, MobileObject> fieldObjects;
			if (screen.get(1).equalsIgnoreCase("SWITCH PRIMARY CARD")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("SWITCH SETTINGS")) {
				switch (screen.get(0).toUpperCase()) {
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
			} else if (screen.get(1).equalsIgnoreCase("Z-Wave Utilities")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
					DASZwaveUtils.clickNavigateUp(testCase, inputs);
				}
				}
			}
			// Navigation from Dashboard
			else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "Z-WAVE DEVICE THROUGH GENERAL INCLUSION": {
					DASZwaveUtils.navigateToGeneralInclusionFromDashboard(testCase);
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
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not click on ");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not click on Add new device menu from Global drawer");
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
					Dashboard db = new Dashboard(testCase);
					if (db.isAddDeviceIconVisible(5)) {
						flag = flag & db.clickOnAddNewDeviceIcon();
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
					flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
					break;
				}
				// Navigate from 'Dashboard' to 'Entry-Exit Delay Settings'
				// Author: Pratik P. Lalseta (H119237)
				case "ENTRY-EXIT DELAY":
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
			} else if (screen.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SETTINGS": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Back button");
					}
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("Security Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "ENTRY-EXIT DELAY": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EntryExitDelayOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "EntryExitDelayOption");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not find Entry/Exit Delay option on DAS Panel Settings screen");
					}
					break;
				}
				}
			}

			else if (screen.get(1).equalsIgnoreCase("Base Station Settings")) {
				switch (screen.get(0).toUpperCase()) {
				case "DASHBOARD": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton");
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 10)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton");
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton");
					}
					break;
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
					flag = flag & dasDIY.clickOnSmartHomeSecurityButton();
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
				case "REGISTER BASE STATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNextButtonVisible()) {
						flag = flag & dasDIY.clickOnNextButton();
					}
					dasDIY.waitForLookingForBaseStationProgressBarToComplete();
					dasDIY.verifyRegisterBaseStationHeaderTitle();
					dasDIY.isQRCodeDisplayed();
					dasDIY.scanQRCode();
					break;
				}
				}
			} else if (screen.get(1).equalsIgnoreCase("REGISTER BASE STATION")) {
				switch (screen.get(0).toUpperCase()) {
				case "CONNECT TO NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					dasDIY.waitForLookingForNetworkConnectionProgressBarToComplete();
					dasDIY.verifyConnectToNetworkHeaderTitle();
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
	 * The navigateFromDashboardScreenToSecuritySettingsScreen method navigates from
	 * the dashboard to the security screen by clicking on the Global Drawer option
	 * and clicking on the camera name on the secondary card settings
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
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
