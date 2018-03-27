package com.honeywell.keywords.jasper.chil;

import java.util.List;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;


public class RemoveOverideStatusThroughCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public RemoveOverideStatusThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^setpoints are not overridden$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = statInfo.getDeviceID();
			List<String> allowedModes = statInfo.getAllowedModes();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (chUtil.getConnection()) {
					int result = chUtil.setCoolThermostatStatus(locationID, deviceID);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Remove Override Status Through CHIL : Successfully set Thermostat Set point to No Hold using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Remove Override Status Through CHIL : Failed to set Thermostat Set point to No Hold using CHIL");
					}
				}
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (chUtil.getConnection()) {
					int result = chUtil.setCoolThermostatStatus(locationID, deviceID);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Remove Override Status Through CHIL : Successfully set Thermostat Set point to No Hold using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Remove Override Status Through CHIL : Failed to set Thermostat Set point to No Hold using CHIL");
					}
				}
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				if (chUtil.getConnection()) {
					int result = chUtil.setHeatThermostatStatus(locationID, deviceID);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Remove Override Status Through CHIL : Successfully set Thermostat Set point to No Hold using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Remove Override Status Through CHIL : Failed to set Thermostat Set point to No Hold using CHIL");
					}
				}
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
