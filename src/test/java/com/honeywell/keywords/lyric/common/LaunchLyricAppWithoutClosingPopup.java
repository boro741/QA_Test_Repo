package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class LaunchLyricAppWithoutClosingPopup extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public LaunchLyricAppWithoutClosingPopup(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user launches the Lyric application without closing the popup$")
	public boolean keywordSteps() {
		flag = flag & LyricUtils.launchLyricApplicationWithoutClosingPopup(testCase, inputs);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.minimizeApp(testCase, -1);
		} else {
			// ios
			MobileUtils.minimizeApp(testCase, -1);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}
