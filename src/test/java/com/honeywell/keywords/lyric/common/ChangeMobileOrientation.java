package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.openqa.selenium.ScreenOrientation;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSolutionCardScreen;

public class ChangeMobileOrientation extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;

	public ChangeMobileOrientation(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.data = data;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes his mobile view to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
		if(this.parameters.get(0).equalsIgnoreCase("Landscape"))
		{
			testCase.getMobileDriver().rotate(ScreenOrientation.LANDSCAPE);
		}else if (this.parameters.get(0).equalsIgnoreCase("potrait"))
		{
			testCase.getMobileDriver().rotate(ScreenOrientation.PORTRAIT);
		}
		}catch(Exception e)
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Exception:" + e.getMessage());
				}
		return flag;
	
}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
