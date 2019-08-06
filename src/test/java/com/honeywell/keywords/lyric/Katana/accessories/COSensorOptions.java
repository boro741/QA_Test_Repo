package com.honeywell.keywords.lyric.Katana.accessories;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.SensorSettingScreen;

public class COSensorOptions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public COSensorOptions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.states = states;
	}
	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user Combo sensor \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			SensorSettingScreen sensorstatus = new SensorSettingScreen(testCase);
			flag = false;
			switch (states.get(0).toUpperCase()){
			case "SMOKE DETECTED":{
				Thread.sleep(15000);
				String status_Value = sensorstatus.GetComboSensorWalktestStatus();
				Keyword.ReportStep_Pass(testCase, "Senssor Status Value : " + status_Value);
				if (status_Value.equalsIgnoreCase("Smoke Signal Confirmed")){
					flag = true;
					Keyword.ReportStep_Pass(testCase, states.get(0) + " is Displayed");
					break;
				}
			}
			case "SMOKE NOT DETECTED":{
				String status_Value = sensorstatus.GetComboSensorWalktestStatus();
				Keyword.ReportStep_Pass(testCase, "Senssor Status Value : " + status_Value);
				if (status_Value.equalsIgnoreCase("Waiting For Smoke Signal")){
					flag = true;
					Keyword.ReportStep_Pass(testCase, states.get(0) + " is Displayed");
				}
				break;
			} 
			case "CO DETECTED":{
				Thread.sleep(15000);
				String status_Value = sensorstatus.GetComboSensorWalktestStatus();
				Keyword.ReportStep_Pass(testCase, "Senssor Status Value : " + status_Value);
				if (status_Value.equalsIgnoreCase("CO Signal Confirmed")){
					flag = true;
					Keyword.ReportStep_Pass(testCase, states.get(0) + " is Displayed");
				}
				break;
			} 
			case "CO NOT DETECTED":{
				String status_Value = sensorstatus.GetComboSensorWalktestStatus();
				Keyword.ReportStep_Pass(testCase, "Senssor Status Value : " + status_Value);
				if (status_Value.equalsIgnoreCase("Waiting For CO Signal")){
					flag = true;
					Keyword.ReportStep_Pass(testCase, states.get(0) + " is Displayed");
				}
				break;
			} default :{
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Input not handled");
			}
			}
		} catch (Throwable e) {
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
