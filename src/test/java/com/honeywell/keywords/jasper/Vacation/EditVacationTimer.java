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

public class EditVacationTimer extends Keyword {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EditVacationTimer(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user edits Vacation Timer$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			if(vhs.isStartAndEndTimeDisplayed()) {
				
				if(vhs.ClickOnStartTime()) {
					Keyword.ReportStep_Pass(testCase, String.format("The Start Time button is clicked for timer edition"));
					
				}
				else {
					Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to click start time"));
					flag=false;
				}
				if(vhs.ClickOnEndTime()) {
					Keyword.ReportStep_Pass(testCase, String.format("The End Time button is clicked for timer edition"));
					
				}
				else {
					Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to click end time"));
					flag=false;
				}
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Start And End time is not visible"));
				flag=false;
			}
	   return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

