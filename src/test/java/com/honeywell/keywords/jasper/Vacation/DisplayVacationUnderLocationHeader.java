package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.VacationHoldScreen;

public class DisplayVacationUnderLocationHeader extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplayVacationUnderLocationHeader(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed (.*) on Dashboard header$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if(exampleData.get(0).equalsIgnoreCase("Vacation Active")) {
			if(vhs.isVacationHoldHeaderPresentUnderLocationOnDashBoard()) {
				if(vhs.GetTextOfVacationOnTheDashboard().contains("Vacation until")) {
					Keyword.ReportStep_Pass(testCase, String.format("Vacation Active Text is present under location on the dashboard"));
				}
				else {
					Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Vacation Active Text is not present under location on the dashboard"));
					flag=false;
				}
				
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Vacation Active Header is not present under location on the dashboard"));
				flag=false;
			}
			
		}
		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

