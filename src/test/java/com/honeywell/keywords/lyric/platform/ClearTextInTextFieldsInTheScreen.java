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
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;
import com.honeywell.lyric.das.utils.NameEditAccountUtils;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.NameEditAccountScreen;

public class ClearTextInTextFieldsInTheScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public ClearTextInTextFieldsInTheScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
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
	@KeywordStep(gherkins = "^user clears the text displayed in the following text fields in the \"(.+)\" screen:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "EDIT ADDRESS": {
			AddressScreen ads = new AddressScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "TextFieldsInEditAddressScreen");
				switch (parameter.toUpperCase()) {
				case "LOCATION NAME TEXT FIELD": {
					if (ads.isLocationNameTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.clearTextDisplayedInEditAddressTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS TEXT FIELD": {
					if (ads.isAddressTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.clearTextDisplayedInEditAddressTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "CITY TEXT FIELD": {
					if (ads.isCityTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.clearTextDisplayedInEditAddressTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "STATE TEXT FIELD": {
					if (ads.isStateTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.clearTextDisplayedInEditAddressTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "POSTAL CODE TEXT FIELD": {
					if (ads.isPostalCodeTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.clearTextDisplayedInEditAddressTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
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
		case "EDIT ACCOUNT": {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "TextFieldsInEditAccountScreen");
				switch (parameter.toUpperCase()) {
				case "FIRST NAME TEXT FIELD": {
					if (eas.isFirstNameLabelVisibleInNameScreen()
							&& eas.isFirstNameValueVisibleInNameScreen()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= EditAccountUtils.clearTextDisplayedInEditAccountTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "LAST NAME TEXT FIELD": {
					if (eas.isLastNameLabelVisibleInNameScreen()
							&& eas.isLastNameValueVisibleInNameScreen()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= EditAccountUtils.clearTextDisplayedInEditAccountTextFields(testCase, inputs, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
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
		case "NAME EDIT ACCOUNT": {
			NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "TextFieldsInNameEditAccountScreen");
				switch (parameter.toUpperCase()) {
					case "FIRST NAME TEXT FIELD": {
						if (neas.isFirstNameTitleDisplayed()
							&& neas.isFirstNameValueVisibleInNameScreen()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= NameEditAccountUtils.clearTextDisplayedInNameEditAccountTextFields(testCase, inputs, parameter);
						} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
						}
						break;
					}
					case "LAST NAME TEXT FIELD": {
						if (neas.isLastNameTitleDisplayed() && neas.isLastNameValueVisibleInNameScreen()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
							flag &= NameEditAccountUtils.clearTextDisplayedInNameEditAccountTextFields(testCase, inputs, parameter);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
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
