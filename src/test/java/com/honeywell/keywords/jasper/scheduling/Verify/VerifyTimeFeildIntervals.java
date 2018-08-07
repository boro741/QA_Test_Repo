package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;


public class VerifyTimeFeildIntervals extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyTimeFeildIntervals(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed sleep setting timer with increments of \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String timeInterval = "";
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}

			if (exampleData.get(0).equalsIgnoreCase("10 minutes")) {
				timeInterval = "10";
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, "EMEA");
			} else if (exampleData.get(0).equalsIgnoreCase("15 minutes")) {
				timeInterval = "15";
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, "NA");
			}

			if (statInfo.getThermostatType().equals("Jasper")) {
				flag = flag & JasperSchedulingVerifyUtils.verifyTimeFieldIncrements(testCase, inputs, timeInterval);
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
