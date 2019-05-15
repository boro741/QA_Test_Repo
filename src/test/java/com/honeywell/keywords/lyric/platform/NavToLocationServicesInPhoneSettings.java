package com.honeywell.keywords.lyric.platform;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.deviceCloudProviders.LocalExecutionDesiredCapability;
import com.honeywell.commons.mobile.Mobile;


public class NavToLocationServicesInPhoneSettings extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public NavToLocationServicesInPhoneSettings(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@SuppressWarnings("null")
	@Override
	@KeywordStep(gherkins = "^user turns off location services in app settings privacy screen$")
	public boolean keywordSteps() {
		boolean flag = true;
		CustomDriver driver = testCase.getMobileDriver();
		DesiredCapabilities desiredCapabilities = null;
		if (inputs.getInputValue(TestCaseInputs.OS_NAME).equalsIgnoreCase(Mobile.IOS)) {
		desiredCapabilities.setCapability("app", "Settings");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
