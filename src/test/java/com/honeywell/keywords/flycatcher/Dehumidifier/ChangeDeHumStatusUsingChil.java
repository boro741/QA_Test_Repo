package com.honeywell.keywords.flycatcher.Dehumidifier;

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

public class ChangeDeHumStatusUsingChil extends Keyword {


	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public ChangeDeHumStatusUsingChil(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user Dehumidification is \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID=statInfo.getDeviceID();
			String DehumidificationStatus = "";
			if (exampleData.get(0).equalsIgnoreCase("Enabled")){
				DehumidificationStatus = "Auto";
			}else{
				DehumidificationStatus="off";
			}
			try {
				if (chUtil.getConnection()) {

					if (chUtil.setDehumidificationStatus(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
							deviceID,DehumidificationStatus) == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Change Dehumidification status Using CHIL : Successfully changed Dehumidification status to "+ DehumidificationStatus +" through CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change Dehumidification status Using CHIL : Failed to change Dehumidification status to "+ DehumidificationStatus +" through CHIL");
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

