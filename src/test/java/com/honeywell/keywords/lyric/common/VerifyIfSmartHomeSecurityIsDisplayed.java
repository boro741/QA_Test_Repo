package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class VerifyIfSmartHomeSecurityIsDisplayed extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> deviceList;
	public boolean flag = true;

	public VerifyIfSmartHomeSecurityIsDisplayed(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> deviceList) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.deviceList = deviceList;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with (.*) option$")
	public boolean keywordSteps() {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		try {
			dasDIY.isSmartHomeSecurityOptionVisible(deviceList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
