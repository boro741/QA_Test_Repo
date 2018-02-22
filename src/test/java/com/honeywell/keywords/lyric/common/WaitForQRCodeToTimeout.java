package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class WaitForQRCodeToTimeout extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> numberOfMinutes;
	public boolean flag = true;

	public WaitForQRCodeToTimeout(TestCases testCase, TestCaseInputs inputs, ArrayList<String> numberOfMinutes) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.numberOfMinutes = numberOfMinutes;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^QR code is not scanned for (.*) minutes$")
	public boolean keywordSteps() {
		DIYRegistrationUtils.waitForQRCodeScanningFailurePopupToDisplay(testCase,
				Integer.parseInt(numberOfMinutes.get(0)));
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
