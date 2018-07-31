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

import io.appium.java_client.android.AndroidDriver;

public class ManageVacationEnd extends Keyword {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String>exampleData;

	public ManageVacationEnd(TestCases testCase, TestCaseInputs inputs,ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs=inputs;
		this.exampleData=exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) the guide message$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if(exampleData.get(0).equalsIgnoreCase("cancels")) {
			if(vhs.ClickOnCancelButtonInEndVacation()) {
				Keyword.ReportStep_Pass(testCase, String.format("Cancel Button in Vacation End Popup is clicked"));
				
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to click Cancel Button in Vacation End Popup."));
				flag=false;
			}
		}
		else if(exampleData.get(0).equalsIgnoreCase("ends")) {
			if(vhs.ClickOnEndVacationButton()) {
				Keyword.ReportStep_Pass(testCase, String.format("End Button in Vacation End Popup is clicked"));
				
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to click End Button in Vacation End Popup."));
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

