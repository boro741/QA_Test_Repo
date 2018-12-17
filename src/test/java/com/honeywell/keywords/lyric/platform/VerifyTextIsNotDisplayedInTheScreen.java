package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.screens.AddressScreen;

public class VerifyTextIsNotDisplayedInTheScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputText;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyTextIsNotDisplayedInTheScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputText) {
		this.testCase = testCase;
		this.inputText = inputText;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with \"(.+)\" in \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputText.get(2).equalsIgnoreCase("EDIT ADDRESS")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (inputText.get(1).toUpperCase()) {
			case "LOCATION NAME TEXT FIELD": {
				if (inputText.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					if (ads.isLocationNameTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Location Name is displayed in Edit Address Screen");
						System.out.println("#####TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD: "
								+ inputs.getInputValue(
										"TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD"));
						flag &= AddressUtils.verifyIfLocationNameInEditAddressScreenContainsSpecialCharacters(testCase,
								inputs, inputs.getInputValue(
										"TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD"));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Location Name displayed in Edit Address Screen is: "
									+ ads.getLocationNameTextInEditAddressScreen());
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Location Name displayed in Edit Address Screen is: "
											+ inputs.getInputValue(
													"TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD")
											+ " which contains special characters.");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name is not displayed in Address Screen");
					}
					break;
				}
			}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}