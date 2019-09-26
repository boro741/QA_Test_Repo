package com.honeywell.keywords.lyric.Katana.accessories;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.report.FailType;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;

public class DeleteSensorFromDevice extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;


	public boolean flag = true;

	public DeleteSensorFromDevice(TestCases testCase, TestCaseInputs inputs ,ArrayList<String> expectedScreen) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Delete (.*) Sensor from Device$")
	public boolean keywordSteps() {
		DeviceInformation sensorinfo = new DeviceInformation(testCase, inputs);
		String deviceID;
		try {
			if (!expectedScreen.get(0).equalsIgnoreCase("Combo")) {
				String sensorid = sensorinfo.getAceessSensor(expectedScreen.get(0));
				if(sensorid != null ){
					deviceID = sensorinfo.getDeviceID();
					@SuppressWarnings("resource")
					CHILUtil chUtil = new CHILUtil(inputs);
					if (chUtil.getConnection()) {
						if (chUtil.deleteAccessSensor(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),deviceID,testCase, sensorid) == 202) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully Deleted Combo Sensor through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to Deleted Combo Sensor through CHIL");
						}
					}
				}
			}else {
				deviceID = sensorinfo.getDeviceID();
				String grpid = sensorinfo.getComboSensor();
				if(grpid != null ){
					@SuppressWarnings("resource")
					CHILUtil chUtil = new CHILUtil(inputs);
					if (chUtil.getConnection()) {
						if (chUtil.deletecomboSensor(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),deviceID,testCase) == 202) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully Deleted Combo Sensor through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to Deleted Combo Sensor through CHIL");
						}
					} 
				}
			}
		}	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
