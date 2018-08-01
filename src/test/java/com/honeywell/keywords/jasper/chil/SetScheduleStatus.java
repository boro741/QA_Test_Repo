package com.honeywell.keywords.jasper.chil;

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

public class SetScheduleStatus extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public SetScheduleStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user selects \"(.+)\" option on Scheduling$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID=statInfo.getDeviceID();
			String ScheduleStatus="";
			if (exampleData.get(0).equalsIgnoreCase("Pause")) {
				ScheduleStatus="Pause";

			} else {
				ScheduleStatus="Resume";
			}

			try {
				if (chUtil.getConnection()) {

					if (chUtil.changeScheduleStatus(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
							deviceID,ScheduleStatus) == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Change System Mode Using CHIL : Successfully changed system mode to "+ ScheduleStatus +" through CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change System Mode Using CHIL : Failed to change system mode to "+ ScheduleStatus +" through CHIL");
					}
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured : " + e.getMessage());
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
		return flag;
	}
}

