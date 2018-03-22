package com.honeywell.keywords.jasper.scheduling.Verify;


import java.util.HashMap;
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
import com.honeywell.lyric.das.utils.DashboardUtils;

public class VerifySchedulePeriodEndTimeIsNotEditable extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifySchedulePeriodEndTimeIsNotEditable(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user should not be allowed to edit end time$")
	public boolean keywordSteps() throws KeywordException {
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "TimeChooserEndTime")) {
						flag = false;
					} else {
						if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "OkButton", 5)) {
							ReportStep_Pass(testCase, "Schedule period End Time is not editable");
						} else {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Schedule period End Time is editable");
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CancelChangeButton", 5)) {
								if (!MobileUtils.clickOnElement(fieldObjects, testCase, "CancelChangeButton")) {
									flag = false;
								}
							}
						}
					}
				} else {
					if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='End']", testCase,
							false)) {
						System.out.println(
								MobileUtils.getMobElement(testCase, "XPATH", "//XCUIElementTypeCell[@name='End']")
										.getAttribute("value"));
						if (MobileUtils.getMobElement(testCase, "XPATH", "//XCUIElementTypeCell[@name='End']")
								.getAttribute("value").equalsIgnoreCase("false")) {
							ReportStep_Pass(testCase, "Schedule period End Time is not editable");
						} else {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Schedule period End Time is editable");
						}
					}
					if (!MobileUtils.clickOnElement(testCase, "NAME", "Navigation_Left_Bar_Item")) {
						flag = false;
					}
				}
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Schedule period End Time");
			}

			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
