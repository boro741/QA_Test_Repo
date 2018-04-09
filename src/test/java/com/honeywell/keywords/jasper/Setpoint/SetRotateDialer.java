package com.honeywell.keywords.jasper.Setpoint;


import java.util.HashMap;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.utils.InputVariables;

public class SetRotateDialer extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;

	public SetRotateDialer(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user sets the temperature by tapping or rotating the set wheel in the solution card$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, String> maxMinSetPoints = statInfo.getDeviceMaxMinSetPoints();
			String systemMode = statInfo.getThermoStatMode();
			if (systemMode.equals("Off")) {
				if (statInfo.getAllowedModes().contains("Heat") && statInfo.getAllowedModes().contains("Cool")) {
					flag = flag & JasperSetPoint.changeSystemMode(testCase, inputs, "Cool");
					systemMode = "Cool";
				} else if (!statInfo.getAllowedModes().contains("Heat") && statInfo.getAllowedModes().contains("Cool")) {
					flag = flag & JasperSetPoint.changeSystemMode(testCase, inputs, "Cool");
					systemMode = "Cool";
				} else if (statInfo.getAllowedModes().contains("Heat") && !statInfo.getAllowedModes().contains("Cool")) {
					flag = flag & JasperSetPoint.changeSystemMode(testCase, inputs, "Heat");
					systemMode = "Heat";
				}
			}
			Double currentTemp = JasperSetPoint.getCurrentSetPointInDialer(testCase);
			currentTemp += 3;
			if (systemMode.equals("Auto")) {
				systemMode = statInfo.getThermostatModeWhenAutoChangeOverActive();
			}
			if (systemMode.equals("Cool")) {
				if (currentTemp > Double.parseDouble(maxMinSetPoints.get("MaxCool"))) {
					currentTemp -= 6;
				}
			} else if (systemMode.equals("Heat")) {
				if (currentTemp > Double.parseDouble(maxMinSetPoints.get("MaxHeat"))) {
					currentTemp -= 6;
				}
			}
			flag = flag & JasperSetPoint.rotateDialer(testCase, inputs, currentTemp);
			if (systemMode.equals("Heat")) {
				inputs.setInputValue(InputVariables.OVERRIDE_HEAT_SETPOINTS, currentTemp);
				inputs.setInputValue(InputVariables.OVERRIDE_COOL_SETPOINTS, statInfo.getCoolSetPoints());
			} else if (systemMode.equals("Cool")) {
				inputs.setInputValue(InputVariables.OVERRIDE_COOL_SETPOINTS, currentTemp);
				inputs.setInputValue(InputVariables.OVERRIDE_HEAT_SETPOINTS, statInfo.getHeatSetPoints());
			}
		} catch(Exception e){

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

