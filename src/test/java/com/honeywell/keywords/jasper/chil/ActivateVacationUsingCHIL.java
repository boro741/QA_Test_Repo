package com.honeywell.keywords.jasper.chil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.jasper.utils.JasperSetPoint;


public class ActivateVacationUsingCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public ActivateVacationUsingCHIL(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^vacation mode is \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = statInfo.getDeviceID();
			if (exampleData.get(0).equalsIgnoreCase("active")) {
				
				List<String> allowedModes = statInfo.getAllowedModes();
				HashMap<String, String> setPoints = new HashMap<String, String>();
				setPoints = statInfo.getDeviceMaxMinSetPoints();
				 CHILUtil.thermostatUnit=statInfo.getThermostatUnits();
			
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					CHILUtil.maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
					CHILUtil.minHeat = Double.parseDouble(setPoints.get("MinHeat"));
					CHILUtil.maxCool = Double.parseDouble(setPoints.get("MaxCool"));
					CHILUtil.minCool = Double.parseDouble(setPoints.get("MinCool"));
					CHILUtil.coolSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, CHILUtil.maxCool, CHILUtil.minCool));
					CHILUtil.heatSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, CHILUtil.maxHeat, CHILUtil.minHeat));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					CHILUtil.maxCool = Double.parseDouble(setPoints.get("MaxCool"));
					CHILUtil.minCool = Double.parseDouble(setPoints.get("MinCool"));
					CHILUtil.coolSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, CHILUtil.maxCool, CHILUtil.minCool));
					CHILUtil.heatSetPoints = 0;
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					CHILUtil.maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
					CHILUtil.minHeat = Double.parseDouble(setPoints.get("MinHeat"));
					CHILUtil.heatSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, CHILUtil.maxHeat, CHILUtil.minHeat));
					CHILUtil.coolSetPoints = 0;
				}
				if (statInfo.getThermostatUnits().equalsIgnoreCase("Celsius")) {
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						CHILUtil.coolSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.coolSetPoints)));
						CHILUtil.heatSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.heatSetPoints)));
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						CHILUtil.coolSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.coolSetPoints)));
						CHILUtil.heatSetPoints = 0;
					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						CHILUtil.heatSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.heatSetPoints)));
						CHILUtil.coolSetPoints = 0;
					}
				}
				String currentUTCTime = JasperSetPoint.getCurrentUTCTime(testCase);
				String startTime = JasperSetPoint.roundOffTimeToTheNearest15minutes(testCase, currentUTCTime);
				String endTime = JasperSetPoint.addDaysToDate(null, startTime, 7);

				if (chUtil.getConnection()) {
					int result = chUtil.enableVacation(locationID, deviceID, startTime, endTime,
							statInfo.getThermostatUnits(), CHILUtil.coolSetPoints, CHILUtil.heatSetPoints);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Activate Vacation Using CHIL : Successfully activated vacation using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Activate Vacation Using CHIL : Failed to activate vacation using CHIL");
					}
				}
			} else if (exampleData.get(0).equalsIgnoreCase("disable")) {
				if (chUtil.getConnection()) {
					int result = chUtil.disableVacation(locationID, deviceID);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Activate Vacation Using CHIL : Successfully disabled vacation using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Activate Vacation Using CHIL : Failed to disable vacation using CHIL");
					}
				}
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
		return true;
	}
}

