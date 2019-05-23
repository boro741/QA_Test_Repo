package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;

public class SelectLocationFromLocationDropDownInDashboardScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> correctSortingOrderForExpectedLocations;
	public boolean flag = true;
	public DataTable data;

	public SelectLocationFromLocationDropDownInDashboardScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> correctSortingOrderForExpectedLocations, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.correctSortingOrderForExpectedLocations = correctSortingOrderForExpectedLocations;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects the following \"(.+)\" from the location drop down:$")
	public boolean keywordSteps() throws KeywordException {
		if (testCase.isTestSuccessful() || VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.FLAG) {
			Dashboard d = new Dashboard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Location");
				inputs.setInputValue("SELECTED_LOCATION_IN_DASHBOARD", parameter);
				flag &= d.selectLocationFromDropDownListInDashBoardScreen(testCase, parameter);
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Skipping this step since default selected location is not: "
							+ VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONFROMCHIL);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}