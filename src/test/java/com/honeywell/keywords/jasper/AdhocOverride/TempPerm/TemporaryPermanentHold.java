package com.honeywell.keywords.jasper.AdhocOverride.TempPerm;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperAdhocOverride;

public class TemporaryPermanentHold extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public TemporaryPermanentHold(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has \"(.+)\" status$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("TEMPORARY")
					|| exampleData.get(0).equalsIgnoreCase("TEMPORARY DASHBOARD")) {
				flag = flag & JasperAdhocOverride.HoldTemporaryHold(testCase, inputs);
			} else if (exampleData.get(0).equalsIgnoreCase("Permanent")) {
				flag = flag & JasperAdhocOverride.HoldTemporaryHold(testCase, inputs);
				flag = flag & JasperAdhocOverride.HoldPermanentlyFromAdHoc(testCase);
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
		return flag;
	}
}
