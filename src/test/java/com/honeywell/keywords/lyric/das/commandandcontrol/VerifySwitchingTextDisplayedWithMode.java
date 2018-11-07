package com.honeywell.keywords.lyric.das.commandandcontrol;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class VerifySwitchingTextDisplayedWithMode extends Keyword {

	private TestCases testCase;
	//private TestCaseInputs inputs;
	private ArrayList<String> expectedText;
	public boolean flag = true;

	public VerifySwitchingTextDisplayedWithMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedText) {
		//this.inputs = inputs;
		this.testCase = testCase;
		this.expectedText = expectedText;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with a switching to \"(.*)\" text$")
	public boolean keywordSteps() {
		String displayedText = "";
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if ((expectedText.get(0).equalsIgnoreCase("AWAY"))||(expectedText.get(0).equalsIgnoreCase("NIGHT"))){
		flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "TIMER PROGRESS BAR TO APPEAR", 2);
		}
		if (sc.isSwitchingToTextVisible()) {
			displayedText = sc.getSwitchingTextLabel();
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					expectedText.get(0) + " text is not displayed");
			return flag;
		}

		System.out.println(displayedText);
		if (displayedText.toLowerCase().contains(expectedText.get(0).toLowerCase() )) {
			Keyword.ReportStep_Pass(testCase, expectedText.get(0) + " text is displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					expectedText.get(0) + " text is not displayed. Expected : '" + expectedText.get(0)
							+ "'. Displayed: '" + displayedText + "'.");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
