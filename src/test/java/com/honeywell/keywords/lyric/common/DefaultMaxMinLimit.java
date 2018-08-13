package com.honeywell.keywords.lyric.common;

import java.util.HashMap;
import java.util.List;

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
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.screens.VacationHoldScreen;

public class DefaultMaxMinLimit extends Keyword {
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DefaultMaxMinLimit(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with temperature values within maximum minimum limit$")
	public boolean keywordSteps() throws KeywordException {
		try {
			VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			HashMap<String, String> setPoints = new HashMap<String, String>();
			setPoints = statInfo.getDeviceMaxMinSetPoints();
			CHILUtil.thermostatUnit = statInfo.getThermostatUnits();
			int currentHeatSetPoint = Integer.parseInt(vhs.getHeatSetPointValue());
			int currentCoolSetPoint = Integer.parseInt(vhs.getCoolSetPointValue());
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				CHILUtil.maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
				CHILUtil.minHeat = Double.parseDouble(setPoints.get("MinHeat"));
				CHILUtil.maxCool = Double.parseDouble(setPoints.get("MaxCool"));
				CHILUtil.minCool = Double.parseDouble(setPoints.get("MinCool"));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				CHILUtil.maxCool = Double.parseDouble(setPoints.get("MaxCool"));
				CHILUtil.minCool = Double.parseDouble(setPoints.get("MinCool"));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				CHILUtil.maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
				CHILUtil.minHeat = Double.parseDouble(setPoints.get("MinHeat"));
			}

			if (CHILUtil.thermostatUnit.equalsIgnoreCase("Celsius")) {
				try {
					flag = currentHeatSetPoint <= Integer.parseInt(
							JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.maxHeat)))
							&& currentHeatSetPoint >= Integer.parseInt(JasperSetPoint
									.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.minHeat)));
					if (!flag) {
						flag = false;
					}
					flag = currentCoolSetPoint <= Integer.parseInt(
							JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.maxCool)))
							&& currentCoolSetPoint >= Integer.parseInt(JasperSetPoint
									.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.minCool)));
					if (!flag) {
						flag = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					flag = currentHeatSetPoint <= CHILUtil.maxHeat.intValue()
							&& currentHeatSetPoint >= CHILUtil.minHeat.intValue();

					if (!flag) {
						flag = false;
					}
					flag = currentCoolSetPoint <= CHILUtil.maxCool.intValue()
							&& currentCoolSetPoint >= CHILUtil.minCool.intValue();
					if (!flag) {
						flag = false;
					}

					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								String.format("Current heat set point: " + currentHeatSetPoint
										+ " is less max heat value: " + CHILUtil.maxHeat.intValue() + " and "
										+ " greater than min heat value: " + CHILUtil.minHeat.intValue()
										+ ". Current cool set point: " + currentCoolSetPoint
										+ " is less max cool value: " + CHILUtil.maxCool.intValue() + " and "
										+ " greater than min cool value: " + CHILUtil.minCool.intValue()));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
								String.format(String.format("Current heat set point: " + currentHeatSetPoint
										+ " is not less max heat value: " + CHILUtil.maxHeat.intValue() + " and "
										+ " not greater than min heat value: " + CHILUtil.minHeat.intValue()
										+ ". Current cool set point: " + currentCoolSetPoint
										+ " is not less max cool value: " + CHILUtil.maxCool.intValue() + " and "
										+ " not greater than min cool value: " + CHILUtil.minCool.intValue())));
					}
				} catch (Exception e) {
					e.printStackTrace();
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
