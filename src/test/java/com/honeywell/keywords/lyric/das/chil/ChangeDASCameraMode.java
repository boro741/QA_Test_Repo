package com.honeywell.keywords.lyric.das.chil;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class ChangeDASCameraMode extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	private TestCaseInputs inputs;
	public ArrayList<String> exampleData;

	public ChangeDASCameraMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user DAS camera is set to (.*) through CHIL$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			boolean status = false;
			if (exampleData.get(0).equalsIgnoreCase("off")) {
				status = false;
			} else if (exampleData.get(0).equalsIgnoreCase("on")) {
				status = true;
			} else {
				throw new Exception("Invalid Input:" + exampleData.get(0));
			}
			if (chUtil.getConnection()) {
				int result = chUtil.changeDASCameraModeStatus(testCase, inputs,
						chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceInfo.getDASCameraID(),
						status);
				if (result == 200) {
					Keyword.ReportStep_Pass(testCase, "Successfully changed the camera status");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to change the camera status");
				}
			} else {
				throw new Exception("Failed to connect to CHIL");
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
