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
import com.honeywell.lyric.utils.LyricUtils;

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
			if (screen.get(1).equalsIgnoreCase("Dashboard")) {
				switch (screen.get(0).toUpperCase()) {
				case "SECURITY SETTINGS": {
					flag = flag
							& NavigateToScreen.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					break;
				}
				case "BASE STATION SETTINGS": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					flag = flag
							& NavigateToScreen.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BaseStationSettingsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BaseStationSettingsOption");
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
									"Base Station Settings");
						} else {
							flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
									"Base Station Settings");
						}
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BaseStationSettingsOption");
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BaseStationSettingsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "BaseStationSettingsOption");
					}
					break;
				}
				case "ENTRY-EXIT DELAY":
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					flag = flag
							& NavigateToScreen.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EntryExitDelayOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "EntryExitDelayOption");
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EntryExitDelayOption", 3)) {
							flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "EntryExitDelayOption");
						}
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

	public static boolean navigateFromDashboardScreenToSecuritySettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		try {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton", 5)) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "GlobalDrawerButton");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerIconList", 3)) {
						List<WebElement> icons = MobileUtils.getMobElements(fieldObjects, testCase,
								"GlobalDrawerIconList");
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
