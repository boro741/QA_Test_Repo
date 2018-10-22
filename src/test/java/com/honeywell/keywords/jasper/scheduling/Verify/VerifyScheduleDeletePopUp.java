package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

public class VerifyScheduleDeletePopUp extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public VerifyScheduleDeletePopUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Verify the the schedule delete pop up \"(.+)\" \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		SchedulingScreen ss = new SchedulingScreen(testCase);
		try {
						String content = ss.getDeleteMessageContent();
						//content = "Delete 1 Period for Monday - Friday?";
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							//System.out.println(exampleData.get(0) + " "+ exampleData.get(1)+"?");
							//System.out.println(content);
						if (content.equalsIgnoreCase(exampleData.get(0) + " "+ exampleData.get(1)+"?")){
							
							Keyword.ReportStep_Pass(testCase,
									"Period name on pop up is matching with -" + content);
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to find Period name on delete pop up "
											+ content);
						}
						}
						else{
						String content1 = (exampleData.get(0).replace("for", "on") + " " + exampleData.get(1));
						 if (content.toUpperCase().contains(content1.toUpperCase())){
							
							Keyword.ReportStep_Pass(testCase,
									"Period name on pop up is matching with -" + content);
						}
					
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to find Period name on delete pop up "
											+ content);
						}
						}
						

				
				}catch (Exception e) {
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
