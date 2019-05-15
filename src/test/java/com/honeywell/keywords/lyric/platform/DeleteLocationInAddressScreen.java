package com.honeywell.keywords.lyric.platform;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.AddressScreen;

public class DeleteLocationInAddressScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public DeleteLocationInAddressScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user deletes the existing location$")
	public boolean keywordSteps() throws KeywordException {
		AddressScreen ads = new AddressScreen(testCase);
		if (testCase.isTestSuccessful() || VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.FLAG) {
			if (ads.isDeleteLocationButtonInAddressScreenVisible()) {
				Keyword.ReportStep_Pass(testCase, "Delete Location button is visible in Address Screen.");
				flag &= ads.clickOnDeleteLocationButtonInAddressScreen();
				if (ads.isDeleteLocationPopupTitleVisible() && ads.isYesButtonInDeleteLocationPopupVisible()
						&& ads.isNoButtonInDeleteLocationPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, "Delete Location popup is visible in Address Screen.");
					ads.clickOnYesButtonInDeleteLocationPopup();
					if (!ads.isAddressScreenTitleVisible()) {
						Keyword.ReportStep_Pass(testCase, "User is not in Address screen.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"User is still in Address screen.");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Delete Location popup is not visible in Address Screen.");
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Location button is not visible in Address Screen.");
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
