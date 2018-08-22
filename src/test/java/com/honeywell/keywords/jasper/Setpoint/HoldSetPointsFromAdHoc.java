package com.honeywell.keywords.jasper.Setpoint;

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
import com.honeywell.screens.AdhocScreen;

public class HoldSetPointsFromAdHoc extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public HoldSetPointsFromAdHoc(TestCases testCase, TestCaseInputs inputs,ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user selects \"(.+)\" from adhoc$")
	public boolean keywordSteps() throws KeywordException {
		if(exampleData.get(0).equalsIgnoreCase("permanent hold")){
			flag = flag & JasperAdhocOverride.HoldPermanentlyFromAdHoc(testCase);
		}else if(exampleData.get(0).equalsIgnoreCase("remove hold")){
			flag = flag & JasperAdhocOverride.removeAdHoc(testCase);
		}else if(exampleData.get(0).equalsIgnoreCase("Adhoc Status")){
			AdhocScreen ActionSheet = new AdhocScreen(testCase);
			flag = flag & ActionSheet.clickOnAdhocStatusButton();
			if(flag){
				Keyword.ReportStep_Pass(testCase, "AdhocStatus selected");
			}else{
				Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE , "AdhocStatus not selected");
			}
		}else if(exampleData.get(0).equalsIgnoreCase("Cancel")){
			AdhocScreen ActionSheet = new AdhocScreen(testCase);
			flag = flag & ActionSheet.clickOnAdhocStatusButton();
			flag = flag & ActionSheet.clickOnAdhocCancelButton();
			if(flag){
				Keyword.ReportStep_Pass(testCase, "Cancel option selected");
			}else{
				Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE , "Cancel option not selected");
			}
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}

