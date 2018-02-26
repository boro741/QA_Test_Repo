package com.honeywell.keywords.lyric.das.chil;

import java.util.ArrayList;

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

public class PutDeviceNameThroughCHIL extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	private TestCaseInputs inputs;
	public ArrayList<String> exampleData;

	public PutDeviceNameThroughCHIL(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user reverts back the (.*) through CHIL$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("DAS Device name")) {
				@SuppressWarnings("resource")
				CHILUtil chUtil = new CHILUtil(inputs);
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
				if (chUtil.getConnection()) {
					int result = chUtil.putDASDeviceName(locInfo.getLocationID(), deviceInfo.getDeviceID(),
							inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "Successfully changed the device name to : "
								+ inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change the device name to "
										+ inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
					}
				}
			} else if (exampleData.get(0).equalsIgnoreCase("Sensor name")) {

			}
			else if (exampleData.get(0).equalsIgnoreCase("Dimmer name")) {
				@SuppressWarnings("resource")
				CHILUtil chUtil = new CHILUtil(inputs);
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
				if (chUtil.getConnection()) {
					int result = chUtil.putZwaveDeviceName(locInfo.getLocationID(), deviceInfo.getDeviceID(),deviceInfo.getDimmerDeviceID(),
							inputs.getInputValue("LOCATION1_DIMMER1_NAME"));
					if (result == 200||result == 202) {
						Keyword.ReportStep_Pass(testCase, "Successfully changed the ZWAVE DIMMER name to : "
								+ inputs.getInputValue("LOCATION1_DIMMER1_NAME"));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change the ZWAVE DIMMER to "
										+ inputs.getInputValue("LOCATION1_DIMMER1_NAME"));
					}
				}
			}else{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to change the device name to "
								+ inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
