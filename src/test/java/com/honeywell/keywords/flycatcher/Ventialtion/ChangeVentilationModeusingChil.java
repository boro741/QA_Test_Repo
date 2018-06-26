package com.honeywell.keywords.flycatcher.Ventialtion;

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

public class ChangeVentilationModeusingChil extends Keyword {


	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public ChangeVentilationModeusingChil(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user has \"(.+)\" Vantilation mode$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID=statInfo.getDeviceID();
			String VentilationMode="";
			if (exampleData.get(0).equalsIgnoreCase("On")) {
				VentilationMode="On";
			}
			else if(exampleData.get(0).equalsIgnoreCase("Off"))
			{
				VentilationMode="off";
			}
			else if(exampleData.get(0).equalsIgnoreCase("Auto"))
			{
				VentilationMode="Auto";
			}
			try {
				if (chUtil.getConnection()) {
					
					if (chUtil.changeVentilationMode(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
							deviceID,VentilationMode) == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Change Ventilation Mode Using CHIL : Successfully changed Ventilation mode to "+ VentilationMode +" through CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change Ventilation Mode Using CHIL : Failed to change Ventilation mode to "+ VentilationMode +" through CHIL");
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
