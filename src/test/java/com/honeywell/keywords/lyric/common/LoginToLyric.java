package com.honeywell.keywords.lyric.common;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class LoginToLyric extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private HashMap<String, MobileObject> fieldObjects;

	public boolean flag = true;

	public LoginToLyric(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.fieldObjects = MobileUtils.loadObjectFile(testCase, "HomeScreen");
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		try {
			if (inputs.isInputAvailable("COLLECT_LOGS")) {
				File appiumLogFile = new File(SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"APPIUM_LOG_FILE_PATH"));
				PrintWriter writer = new PrintWriter(appiumLogFile);
				writer.print("");
				writer.close();
			}
		} catch (Exception e) {
		}
		flag = MobileUtils.launchApplication(inputs, testCase, true);
		flag = flag & LyricUtils.closeAppLaunchPopups(testCase);
		//flag = flag & JasperUtils.setAppEnvironment(testCase, inputs, true);

		if (MobileUtils.isMobElementExists(fieldObjects, testCase,
				"LoginButton", 5)) {
			flag = flag
					& MobileUtils.clickOnElement(fieldObjects, testCase,
							"LoginButton");
		}

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user logs in to Lyric app$", description = "This keyword is used to launch and login to the app")
	public boolean keywordSteps() {
		flag = flag & LyricUtils.loginToLyricApp(testCase, inputs);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		flag = flag & LyricUtils.verifyLoginSuccessful(testCase,inputs);
		return flag;
	}

}
