package com.honeywell.keywords.lyric.common;

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
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.screens.VacationHoldScreen;

public class DefaultSetPoint extends Keyword {
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DefaultSetPoint(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with default set point value$")
	public boolean keywordSteps() throws KeywordException {
		try {
			VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				CHILUtil.coolSetPoints = (int) Double.parseDouble(statInfo.getVacationCoolSetPoint());
				CHILUtil.heatSetPoints = (int) Double.parseDouble(statInfo.getVacationHeatSetPoint());
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				CHILUtil.coolSetPoints = Integer.parseInt(statInfo.getVacationCoolSetPoint());
				CHILUtil.heatSetPoints = 0;
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				CHILUtil.heatSetPoints = Integer.parseInt(statInfo.getVacationHeatSetPoint());
				CHILUtil.coolSetPoints = 0;
			}
			
					if (statInfo.getThermostatUnits().equals("Celsius")) {
						if (!vhs.getHeatSetPointValue().equals(JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, String.valueOf(CHILUtil.heatSetPoints))))){
							flag = false;
						}
						if (!vhs.getCoolSetPointValue().equals(JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, String.valueOf(CHILUtil.coolSetPoints))))) {
							flag = false;
						}
					} else {
						if (!vhs.getHeatSetPointValue().equals(String.valueOf(CHILUtil.heatSetPoints))) {
							flag = false;
						}
						if (!vhs.getCoolSetPointValue().equals(String.valueOf(CHILUtil.coolSetPoints))) {
							flag = false;
						}
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, String.format("Cool is set to: " + vhs.getCoolSetPointValue()
								+ " Heat is set to: " + vhs.getHeatSetPointValue()));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
								String.format("Cool is not set to: " + vhs.getCoolSetPointValue()
										+ " Heat is not set to: " + vhs.getHeatSetPointValue()));
					}

			} 
		 catch (Exception e) {
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
