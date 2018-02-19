package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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

public class VerifyEntryExitTime extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyEntryExitTime(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^(.*) value should be updated to (.*) on (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Entry-Exit Delay")
					&& parameters.get(2).equalsIgnoreCase("Entry-Exit Delay")) {
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				WebElement entryExitTable = null;
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EntryExitTable", 10)) {
					entryExitTable = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTable");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Could not find entry/exit delay values");
					return flag;
				}
				List<WebElement> cells = null;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					cells = entryExitTable.findElements(By.className("android.widget.RelativeLayout"));
				} else {
					cells = entryExitTable.findElements(By.xpath("//XCUIElementTypeCell"));
				}
				switch (parameters.get(1)) {
				case "15": {
					try {
						WebElement tickMark = null;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							tickMark = cells.get(0).findElement(By.id("list_item_lyric_image_view"));
						} else {
							tickMark = cells.get(0).findElement(By.xpath("//XCUIElementTypeImage"));
						}
						if (tickMark != null) {
							Keyword.ReportStep_Pass(testCase,
									"15 second option is selected on Entry/Exit Delay screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"15 second option not selected on Entry/Exit Delay screen");
						}
					} catch (NoSuchElementException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"15 second option not selected on Entry/Exit Delay screen");
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
					break;
				}
				case "30": {
					try {
						WebElement tickMark = null;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							tickMark = cells.get(1).findElement(By.id("list_item_lyric_image_view"));
						} else {
							tickMark = cells.get(1).findElement(By.xpath("//XCUIElementTypeImage"));
						}
						if (tickMark != null) {
							Keyword.ReportStep_Pass(testCase,
									"30 second option is selected on Entry/Exit Delay screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"30 second option not selected on Entry/Exit Delay screen");
						}
					} catch (NoSuchElementException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"30 second option not selected on Entry/Exit Delay screen");
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
					break;
				}
				case "45": {
					try {
						WebElement tickMark = null;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							tickMark = cells.get(2).findElement(By.id("list_item_lyric_image_view"));
						} else {
							tickMark = cells.get(2).findElement(By.xpath("//XCUIElementTypeImage"));
						}
						if (tickMark != null) {
							Keyword.ReportStep_Pass(testCase,
									"45 second option is selected on Entry/Exit Delay screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"45 second option not selected on Entry/Exit Delay screen");
						}
					} catch (NoSuchElementException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"45 second option not selected on Entry/Exit Delay screen");
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
					break;
				}
				case "60": {
					try {
						WebElement tickMark = null;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							tickMark = cells.get(3).findElement(By.id("list_item_lyric_image_view"));
						} else {
							tickMark = cells.get(3).findElement(By.xpath("//XCUIElementTypeImage"));
						}
						if (tickMark != null) {
							Keyword.ReportStep_Pass(testCase,
									"60 second option is selected on Entry/Exit Delay screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"60 second option not selected on Entry/Exit Delay screen");
						}
					} catch (NoSuchElementException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"60 second option not selected on Entry/Exit Delay screen");
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
					break;
				}
				}

			}

			else if (parameters.get(0).equalsIgnoreCase("Entry-Exit Delay")
					&& parameters.get(2).equalsIgnoreCase("Security Settings")) {
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EntryExitDelayOption", 15)) {
					switch (parameters.get(1)) {
					case "15": {
						String displayed;
						String expected;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("text");
							expected = "15 seconds";
						} else {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("value");
							// displayed = MobileUtils.getMobElement(testCase, "name",
							// "Security_Settings_0_3_cell")
							// .findElement(By.name("Security_Settings_0_3_value")).getAttribute("value");
							expected = "15 Seconds";
						}
						if (displayed.equalsIgnoreCase(expected)) {
							Keyword.ReportStep_Pass(testCase,
									"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
											+ displayed + ". Expected: " + expected);
						}
						break;
					}
					case "30": {
						String displayed;
						String expected;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("text");
							expected = "30 seconds";
						} else {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("value");
							// displayed = MobileUtils.getMobElement(testCase, "name",
							// "Security_Settings_0_3_cell")
							// .findElement(By.name("Security_Settings_0_3_value")).getAttribute("value");
							expected = "30 Seconds";
						}
						if (displayed.equalsIgnoreCase(expected)) {
							Keyword.ReportStep_Pass(testCase,
									"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
											+ displayed + ". Expected: " + expected);
						}
						break;
					}
					case "45": {
						String displayed;
						String expected;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("text");
							// displayed = MobileUtils.getMobElement(testCase, "name",
							// "Security_Settings_0_3_cell")
							// .findElement(By.name("Security_Settings_0_3_value")).getAttribute("value");
							expected = "45 seconds";
						} else {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("value");
							expected = "45 Seconds";
						}
						if (displayed.equalsIgnoreCase(expected)) {
							Keyword.ReportStep_Pass(testCase,
									"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
											+ displayed + ". Expected: " + expected);
						}
						break;
					}
					case "60": {
						String displayed;
						String expected;
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("text");
							// displayed = MobileUtils.getMobElement(testCase, "name",
							// "Security_Settings_0_3_cell")
							// .findElement(By.name("Security_Settings_0_3_value")).getAttribute("value");
							expected = "60 seconds";
						} else {
							displayed = MobileUtils.getMobElement(fieldObjects, testCase, "EntryExitTimerValue")
									.getAttribute("value");
							expected = "60 Seconds";
						}
						if (displayed.equalsIgnoreCase(expected)) {
							Keyword.ReportStep_Pass(testCase,
									"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
											+ displayed + ". Expected: " + expected);
						}
						break;
					}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Entry-Exit Delay Timer not displayed on DAS Settings screen");
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
