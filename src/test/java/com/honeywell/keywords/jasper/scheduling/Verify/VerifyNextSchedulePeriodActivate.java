package com.honeywell.keywords.jasper.scheduling.Verify;


import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperAdhocOverride;

public class VerifyNextSchedulePeriodActivate extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyNextSchedulePeriodActivate(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify next schedule period activated$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			if (devInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
				flag = flag & JasperAdhocOverride.waitForHoldToCompleteForNA(testCase);
			} else if (devInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
				flag = flag & JasperAdhocOverride.waitForHoldToCompleteForEMEA(testCase);
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