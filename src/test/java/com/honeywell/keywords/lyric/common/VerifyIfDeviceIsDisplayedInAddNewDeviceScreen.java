package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class VerifyIfDeviceIsDisplayedInAddNewDeviceScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;

	// String values used in the methods
	public static final String DASDEVICETITLE = "Smart Home Security";
	public static final String HBDEVICETITLE = "Lyric Round Wi-Fi Thermostat";
	public static final String D6DEVICETITLE = "D6 Pro Wi-Fi Ductless Controller";
	public static final String T5DEVICETITLE = "T5 Wi-Fi Thermostat";
	public static final String T6PRODEVICETITLE = "T6 Pro Wi-Fi Thermostat";
	public static final String WLDDEVICETITLE = "Wi-Fi Water Leak and Freeze Detector";
	public static final String C1CAMERADEVICETITLE = "C1 Wi-Fi Security Camera";
	public static final String C2CAMERADEVICETITLE = "C2 Wi-Fi Security Camera";
	public static final String T6DEVICETITLE = "T6 Wired Thermostat";
	public static final String T6RDEVICETITLE = "T6R Wireless Thermostat";

	public VerifyIfDeviceIsDisplayedInAddNewDeviceScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^\"(.+)\" device should be displayed in Add New Device screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (parameters.get(0).toUpperCase()) {
			case "NO DAS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", DASDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", DASDEVICETITLE);
					System.out.println("******flag: " + flag);
				}
				System.out.println("******flag: " + flag);
				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							DASDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							DASDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES DAS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", DASDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", DASDEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							DASDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							DASDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO HB": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", HBDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", HBDEVICETITLE);
				}

				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							HBDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							HBDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES HB": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", HBDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", HBDEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							HBDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							HBDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO D6": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", D6DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", D6DEVICETITLE);
				}

				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							D6DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							D6DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES D6": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", D6DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", D6DEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							D6DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							D6DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO T5": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T5DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T5DEVICETITLE);
				}

				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							T5DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T5DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES T5": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T5DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T5DEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							T5DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T5DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO T6 PRO": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6PRODEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6PRODEVICETITLE);
				}

				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							T6PRODEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6PRODEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES T6 PRO": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6PRODEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6PRODEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							T6PRODEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6PRODEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO WLD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", WLDDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", WLDDEVICETITLE);
				}

				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							WLDDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							WLDDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES WLD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", WLDDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", WLDDEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							WLDDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							WLDDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO C1": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", C1CAMERADEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", C1CAMERADEVICETITLE);
				}
				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							C1CAMERADEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							C1CAMERADEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES C1": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", C1CAMERADEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", C1CAMERADEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							C1CAMERADEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							C1CAMERADEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO C2": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", C2CAMERADEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", C2CAMERADEVICETITLE);
				}
				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							C2CAMERADEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							C2CAMERADEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES C2": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", C2CAMERADEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", C2CAMERADEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							C2CAMERADEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							C2CAMERADEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO T6": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6DEVICETITLE);
				}
				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							T6DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES T6": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6DEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6DEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							T6DEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6DEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "NO T6R": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6RDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6RDEVICETITLE);
				}
				if (!flag) {
					Keyword.ReportStep_Pass(testCase,
							T6RDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6RDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}
			case "YES T6R": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "text", T6RDEVICETITLE);
				} else {
					flag &= LyricUtils.scrollToElementUsingExactAttribute(testCase, "name", T6RDEVICETITLE);
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							T6RDEVICETITLE + " is displayed in the list of devices in Add New Devices screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							T6RDEVICETITLE + " is not displayed in the list of devices in Add New Devices screen");
				}
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag &= LyricUtils.scrollUpTheElementsDisplayedInAList(testCase, dasDIY.getDeviceListWebElement());
				flag &= dasDIY.isAllMobElementsInAddNewDeviceHeaderSectionVisible();
				flag = true;
				break;
			}

			default: {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						parameters.get(0) + "Input does not match");
				flag = false;
				break;
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
