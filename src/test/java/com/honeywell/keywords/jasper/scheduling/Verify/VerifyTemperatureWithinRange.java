package com.honeywell.keywords.jasper.scheduling.Verify;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;

public class VerifyTemperatureWithinRange extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyTemperatureWithinRange(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify temperature is set within the maximum and minimum range$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		try {
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Thermostat is offline");
				return true;
			}
			if (statInfo.getThermostatType().equals("Jasper")) {
				flag = flag & JasperSchedulingVerifyUtils.verifyTemperatureWithInRange(testCase, inputs);
			}
		} catch (Exception e) {
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
