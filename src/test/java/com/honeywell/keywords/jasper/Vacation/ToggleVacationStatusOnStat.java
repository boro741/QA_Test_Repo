package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.VacationHoldScreen;

public class ToggleVacationStatusOnStat extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ToggleVacationStatusOnStat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user can (*) the stat individually$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		switch (exampleData.get(0).toUpperCase()) {
		case "ENABLE":{
			if(vhs.EnableVacationHold()) {
				Keyword.ReportStep_Pass(testCase, String.format("The Vaction is {0}ed",exampleData.get(0)));
				flag=true;
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to {0} the Vacation Hold",exampleData.get(0)));
				flag=false;
			}
			break;
		}
		
		
	case "DISABLE":{
		if(vhs.DisableVacationHold()) {
			Keyword.ReportStep_Pass(testCase, String.format("The Vaction is {0}ed",exampleData.get(0)));
			flag=true;
		}
		else {
			Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to  {0} the Vacation Hold",exampleData.get(0)));
			flag=false;
		}
		break;
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

