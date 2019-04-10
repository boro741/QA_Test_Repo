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
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.AddNewDeviceScreen;

public class ChangeTheDisplayedCountryTo extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> countryNames;
	public boolean flag = true;
	public DataTable data;

	public ChangeTheDisplayedCountryTo(TestCases testCase, TestCaseInputs inputs, ArrayList<String> countryNames) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.countryNames = countryNames;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the country to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
		inputs.setInputValue("SELECTED_COUNTRY", countryNames.get(0));
		System.out.println("SELECTED_COUNTRY: " + inputs.getInputValue("SELECTED_COUNTRY"));
		if (adn.isShowingDevicesForCountryLabelInAddNewDeviceScreenVisible(countryNames.get(0))) {
			Keyword.ReportStep_Pass(testCase,
					"Current Country displayed is same as the country to be updated: " + countryNames.get(0));
		} else if (adn.isChangeCountryLinkVisible()) {
			Keyword.ReportStep_Pass(testCase,
					"Current Country displayed is not same as the country to be updated. Changing the country to: "
							+ countryNames.get(0));
			flag &= adn.clickOnChangeCountryLink();
			flag &= DashboardUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 1);
			if (adn.isConfirmYourCountryScreenTitleVisible() && adn.isCurrentCountryButtonVisible()
					&& adn.isEnterCountryTextFieldVisible()) {
				Keyword.ReportStep_Pass(testCase, "Navigated to Change Your Country Screen");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to navigate to Change Your Country Screen");
			}
			flag &= DashboardUtils.enterCountryNameAndSelectItInConfirmYourCountryScreenAndAcceptNewAgreement(testCase,
					inputs, countryNames.get(0));
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Change Country link is not displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
