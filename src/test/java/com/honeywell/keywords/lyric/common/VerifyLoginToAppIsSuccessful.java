package com.honeywell.keywords.lyric.common;

import java.io.File;
import java.io.PrintWriter;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.LyricUtils;

public class VerifyLoginToAppIsSuccessful extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public VerifyLoginToAppIsSuccessful(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		try {
			if (inputs.isInputAvailable("COLLECT_LOGS")) {
				if (inputs.getInputValue("COLLECT_LOGS").equalsIgnoreCase("true")) {
					File appiumLogFile = new File(SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC,
							"APPIUM_LOG_FILE_PATH"));
					PrintWriter writer = new PrintWriter(appiumLogFile);
					writer.print("");
					writer.close();
				}
			}
		} catch (Exception e) {
		}
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies login is successful when user logs in first time$")
	public boolean keywordSteps() {
		try {
			flag = flag & LyricUtils.loginToApplicationIsSuccessful(testCase, inputs);
		} catch (InterruptedException e) {
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
