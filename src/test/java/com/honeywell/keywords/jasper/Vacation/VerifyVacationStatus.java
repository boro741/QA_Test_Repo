package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.VacationHoldScreen;

public class VerifyVacationStatus extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyVacationStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user verifies vacation is \"(.+)\" in \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (exampleData.get(0).equalsIgnoreCase("on")) {
			if(exampleData.get(1).trim().equals("vacation settings card"))
			{
				boolean vacationHoldStatus=vhs.GetVacationHoldStatus().equalsIgnoreCase("On")||vhs.GetVacationHoldStatus().equalsIgnoreCase("true");
				flag = flag & vacationHoldStatus;
			}
			else if(exampleData.get(1).trim().equals("solution card"))
			{
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);
			}
			else if(exampleData.get(1).trim().equals("dashboard"))
			{
				flag = flag & JasperVacation.waitForVacationStart(testCase, inputs);
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);			}
		} else if (exampleData.get(0).equalsIgnoreCase("off")) {
			if(exampleData.get(1).trim().equals("vacation settings card"))
			{
				boolean vacationHoldStatus=vhs.GetVacationHoldStatus().equalsIgnoreCase("Off")||vhs.GetVacationHoldStatus().equalsIgnoreCase("false");
				flag = flag & vacationHoldStatus;
			}
			else if(exampleData.get(1).trim().equals("solution card"))
			{
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, false);
			}
			else if(exampleData.get(1).trim().equals("dashboard"))
			{
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

