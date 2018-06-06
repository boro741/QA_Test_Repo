package com.honeywell.keywords.lyric.das.diyregistration;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class EnterWiFiPassword extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedWiFiPassword;
	public boolean flag = true;

	public EnterWiFiPassword(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedWiFiPassword) {
		this.testCase = testCase;
		this.expectedWiFiPassword = expectedWiFiPassword;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user inputs \"(.*)\" as the WiFi Password$")
	public boolean keywordSteps() {

		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		dasDIY.enterWiFiPassword(expectedWiFiPassword.get(0));
		if (dasDIY.isJoinButtonInConnectToNetworkScreenVisible()) {
			dasDIY.clickOnJoinButtonInConnectToNetworkScreen();
		}
		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "SMART HOME SECURITY PROGRESS BAR", 5);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
