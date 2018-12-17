package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.AboutTheAppScreen;

public class VerifyIfPopUpIsNotDisplayed extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	private TestCaseInputs inputs;
	// private HashMap<String, MobileObject> fieldObjects;

	public boolean flag = true;

	public VerifyIfPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedPopUp) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.expectedPopUp = expectedPopUp;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not receive a \"(.+)\" popup$")
	public boolean keywordSteps() {
		switch (expectedPopUp.get(0).toUpperCase()) {
		case "WHAT DO YOU THINK OF HONEYWELL HOME APP": {
			AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
			if (!atas.isWhatDoYouThinkOfHoneywellHomeAppPopupVisible()
					&& !atas.isRateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()
					&& !atas.isRateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()
					&& !atas.isRateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()
					&& !atas.isRateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()
					&& !atas.isRateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile()
					&& !atas.isCloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopupVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"What do you think of Honeywell home app popup is not displayed with ratings 1 to 5 and close button in About the App Screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"What do you think of Honeywell home app popup is displayed with ratings 1 to 5 and close button in About the App Screen");
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
			return flag;
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
