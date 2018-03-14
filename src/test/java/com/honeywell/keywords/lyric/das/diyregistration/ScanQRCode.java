package com.honeywell.keywords.lyric.das.diyregistration;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class ScanQRCode extends Keyword {

	private TestCases testCase;

	public boolean flag = true;

	public ScanQRCode(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user scans the QR code by showing it to the base station camera$")
	public boolean keywordSteps() {
		if (testCase.isTestSuccessful()) {

			DIYRegistrationUtils.scanQRCode(testCase);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
