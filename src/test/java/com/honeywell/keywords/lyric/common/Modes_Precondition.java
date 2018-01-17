package com.honeywell.keywords.lyric.common;

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

public class Modes_Precondition extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	private TestCaseInputs inputs;
	public Modes_Precondition(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs=inputs;
		this.parameters=parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^(.*) mode as precondition$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if(deviceInfo.isOnline()){   

				if (chUtil.getConnection()) {
					switch (parameters.get(0).toUpperCase()) {
					case "HOME":{
						if(chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase)==202){
							Keyword.ReportStep_Pass(testCase, "Precondition using Chapi -"+parameters.get(0)+" executed");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set"+parameters.get(0) );
						} 
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"Home",testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to home");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in home : " + result);
						}
						/*if(chUtil.switchToHome(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase)==202){
							Keyword.ReportStep_Pass(testCase, "Precondition using Chapi -"+parameters.get(0)+" executed");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set"+parameters.get(0) );
						}*/
						break;
					}
					case "AWAY":{
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"AWAY",testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to AWAY MODE");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in AWAY MODE : " + result);
						}
						break;
					}
					case "NIGHT":{
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"NIGHT",testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to NIGHT MODE");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in NIGHT MODE : " + result);
						}
						break;
					}
					default:{
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Input not handled " + parameters.get(0).toUpperCase());
					}
					}
				}
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
