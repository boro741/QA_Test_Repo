package com.honeywell.keywords.lyric.common;

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
import com.honeywell.screens.AddNewDeviceScreen;

public class VerifyIfCountryIsDisplayedInAddNewDeviceScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;

	public VerifyIfCountryIsDisplayedInAddNewDeviceScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the label \"(.+)\" along with \"(.+)\" name$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (parameters.get(0).toUpperCase()) {
			case "SHOWING DEVICES FOR": {
				AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
				flag &= adn.isShowingDevicesForCountryLabelInAddNewDeviceScreenVisible(parameters.get(1));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Label: " + parameters.get(0) + " " + parameters.get(1)
							+ " is displayed in the Add New Device Screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Label: " + parameters.get(0) + " "
							+ parameters.get(1) + " is not displayed in the Add New Device Screen");
				}
				break;
			}
			default: {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						parameters.get(0) + "Input does not match");
				flag = false;
				break;
			}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
