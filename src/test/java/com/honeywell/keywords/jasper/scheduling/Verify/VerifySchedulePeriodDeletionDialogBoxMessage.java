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

public class VerifySchedulePeriodDeletionDialogBoxMessage extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifySchedulePeriodDeletionDialogBoxMessage(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify the dialog box message for period deletion$")
	public boolean keywordSteps() throws KeywordException {
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH", "//*[@text='Confirm Delete']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase, "Confirm Delete dialog box is shown");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Confirm Delete dialog box is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteDialogBoxMessage", 5)) {
					Keyword.ReportStep_Pass(testCase, "Dialog box message is shown:\n"
							+ MobileUtils.getMobElement(fieldObjects, testCase, "PeriodDeleteDialogBoxMessage").getText());
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dialog box message is not shown");
				}
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmDeletePopup", 5)) {
					Keyword.ReportStep_Pass(testCase, "Confirm Delete dialog box is shown");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Confirm Delete dialog box is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteDialogBoxMessage", 5)) {
					Keyword.ReportStep_Pass(testCase, "Dialog box message is shown:\n"
							+ MobileUtils.getMobElement(fieldObjects, testCase, "PeriodDeleteDialogBoxMessage").getAttribute("value"));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dialog box message is not shown");
				}

			}
			

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