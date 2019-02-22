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
import com.honeywell.screens.AddressScreen;

public class VerifyPlaceholderTextForTheDisplayedTextFieldsInTheScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyPlaceholderTextForTheDisplayedTextFieldsInTheScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedScreen, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the placeholder text for the following fields in \"(.+)\" screen:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "EDIT ADDRESS": {
			AddressScreen ads = new AddressScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "TextFieldsInEditAddressScreen");
				switch (parameter.toUpperCase()) {
				case "LOCATION NAME TEXT FIELD": {
					if (ads.isPlaceHolderValueInLocationNameTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "ADDRESS TEXT FIELD": {
					if (ads.isPlaceHolderValueInAddressTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "CITY TEXT FIELD": {
					if (ads.isPlaceHolderValueInCityTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "STATE TEXT FIELD": {
					if (ads.isPlaceHolderValueInStateTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "POSTAL CODE TEXT FIELD": {
					if (ads.isPlaceHolderValueInPostalCodeTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " Place holder is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
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
