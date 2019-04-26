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
import com.honeywell.screens.OSPopUps;

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
		case "TURN ON LOCATION SERVICES" : {
			OSPopUps ops= new OSPopUps(testCase);
			if(!ops.isTurnOnLocationServicesPopupVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Turn On Location Services popup is not displayed");
			}else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Turn On Location Services pop up is displayed");
			}
			break;
		}
		
		case "ALLOW HONEYWELL TO ACCESS THIS DEVICES LOCATION" :{
			OSPopUps ops= new OSPopUps(testCase);
			if(!ops.isAllowHoneywellToAccessDeviceLocationPopupVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Allow Honeywell to access this devices location popup is not displayed");
			}else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Allow Honeywell to access this devices location pop up is displayed");
			}
			break;
		}
		
		case "ALLOW HONEYWELL TO ACCESS YOUR LOCATION" : {
			OSPopUps ops= new OSPopUps(testCase);
			if(!ops.isAllowHoneywellToAccessYourLocationPopupVisible(20)) {
				Keyword.ReportStep_Pass(testCase,
						"Allow Honeywell to access your location popup is not displayed");
			}else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Allow Honeywell to access your location pop up is displayed");
			}
			break;
		}
		
		case "HONEYWELL WOULD LIKE TO SEND YOU NOTIFICATIONS" : {
			OSPopUps ops= new OSPopUps(testCase);
			if(!ops.isHoneywellWouldLikeToSendYouNotificationsPopupVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Honeywell would like to send you notifications popup is not displayed");
			}else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Honeywell would like to send you notifications popup is displayed");
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
