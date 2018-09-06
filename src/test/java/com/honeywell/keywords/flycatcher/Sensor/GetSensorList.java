package com.honeywell.keywords.flycatcher.Sensor;


import java.util.List;

import org.json.JSONObject;

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
import com.honeywell.lyric.utils.InputVariables;

public class GetSensorList extends Keyword {


	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public GetSensorList(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user fetches Sensor list from the stat$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int groupid = statInfo.getSensorGroupID();
			String deviceID=statInfo.getDeviceID();
			try {
				if (chUtil.getConnection()) {
					JSONObject SensorListJson = chUtil.getSensorList(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID, groupid);
					List<String> sensorlist = statInfo.getSensorDeviceID(SensorListJson);
					if (sensorlist!= null){
						for (int i=0;i<sensorlist.size();i++){
							Keyword.ReportStep_Pass(testCase,
									"Available Sensors in Device " + sensorlist.get(i));
							inputs.setInputValue(InputVariables.SENSOR1, sensorlist.get(i));
						}
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

