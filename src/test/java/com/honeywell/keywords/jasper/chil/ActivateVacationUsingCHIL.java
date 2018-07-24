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
				int coolSetPoints = 0;
				int heatSetPoints = 0;
				List<String> allowedModes = statInfo.getAllowedModes();
				HashMap<String, String> setPoints = new HashMap<String, String>();
				setPoints = statInfo.getDeviceMaxMinSetPoints();
				Double maxHeat;
				Double minHeat;
				Double maxCool;
				Double minCool;
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
					minHeat = Double.parseDouble(setPoints.get("MinHeat"));
					maxCool = Double.parseDouble(setPoints.get("MaxCool"));
					minCool = Double.parseDouble(setPoints.get("MinCool"));
					coolSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool, minCool));
					heatSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat, minHeat));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					maxCool = Double.parseDouble(setPoints.get("MaxCool"));
					minCool = Double.parseDouble(setPoints.get("MinCool"));
					coolSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool, minCool));
					heatSetPoints = 0;
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
					minHeat = Double.parseDouble(setPoints.get("MinHeat"));
					heatSetPoints = Integer.parseInt(
							JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat, minHeat));
					coolSetPoints = 0;
				}
				if (statInfo.getThermostatUnits().equalsIgnoreCase("Celsius")) {
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						coolSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(coolSetPoints)));
						heatSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(heatSetPoints)));
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						coolSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(coolSetPoints)));
						heatSetPoints = 0;
					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						heatSetPoints = Integer.parseInt(
								JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(heatSetPoints)));
						coolSetPoints = 0;
					}
				}
				String currentUTCTime = JasperSetPoint.getCurrentUTCTime(testCase);
				String startTime = JasperSetPoint.roundOffTimeToTheNearest15minutes(testCase, currentUTCTime);
				String endTime = JasperSetPoint.addDaysToDate(null, startTime, 7);

				if (chUtil.getConnection()) {
					int result = chUtil.enableVacation(locationID, deviceID, startTime, endTime,
							statInfo.getThermostatUnits(), coolSetPoints, heatSetPoints);
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

