package com.honeywell.keywords.lyric.platform;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.LyricUtils;

public class LoginToLyricWithInviteUser extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> inputName;

	public boolean flag = true;

	public LoginToLyricWithInviteUser(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputName) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.inputName = inputName;
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
	@KeywordStep(gherkins = "^user launches and logs in to the Lyric Application with \"(.*)\"$")
	public boolean keywordSteps() {
		flag = flag & LyricUtils.launchAndLoginToApplicationWithInviteUsersAccount(testCase, inputs, inputName.get(0));
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
