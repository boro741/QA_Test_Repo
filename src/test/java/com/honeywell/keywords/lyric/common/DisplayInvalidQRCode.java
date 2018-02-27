package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class DisplayInvalidQRCode extends Keyword {

	private TestCases testCase;

	public boolean flag = true;

	public DisplayInvalidQRCode(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user scans an invalid QR code$")
	public boolean keywordSteps() {
		DIYRegistrationUtils.scanInvalidQRCode(testCase);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
