package com.honeywell.keywords.lyric.das.diyregistration;

import com.honeywell.commons.coreframework.*;
import com.honeywell.screens.BaseStationSettingsScreen;

import java.util.ArrayList;

public class VerifyIfSignOutIsDIsplayedInAmazonAlexaScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedValue;
	private boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyIfSignOutIsDIsplayedInAmazonAlexaScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedValue) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedValue = expectedValue;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return true;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with \"(.*)\" button on the \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		if(expectedValue.get(1).equalsIgnoreCase("AMAZON ALEXA")) {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.isSignOutButtonInAmazonAlexaScreenVisible(expectedValue.get(0));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
