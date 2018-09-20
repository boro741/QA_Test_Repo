package com.honeywell.keywords.jasper.Vacation;

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
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.screens.VacationHoldScreen;

public class DisplayVacationSetPointOnSoultionCard extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplayVacationSetPointOnSoultionCard(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" setpoint value in the solution card screen$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		try {
			DeviceInformation statInfo;
			switch (exampleData.get(0).toUpperCase()) {
			case "VACATION": {
				statInfo = new DeviceInformation(testCase, inputs);
				CHILUtil.vacationHeatSetPoint = (int) Double.parseDouble(statInfo.getVacationHeatSetPoint());
				CHILUtil.vacationCoolSetPoint = (int) Double.parseDouble(statInfo.getVacationCoolSetPoint());
				String vacationSetPointInPrimaryCard = vhs.getPrimaryCardValue();
				if (statInfo.getThermostatUnits().equalsIgnoreCase("Celsius")) {
					vacationSetPointInPrimaryCard  = JasperSetPoint.convertFromCelsiusToFahrenhiet
							(testCase,vacationSetPointInPrimaryCard);
				}
				
				if (Integer.parseInt(vacationSetPointInPrimaryCard) == CHILUtil.vacationHeatSetPoint
						|| (Integer.parseInt(vacationSetPointInPrimaryCard) == CHILUtil.vacationCoolSetPoint)){
					Keyword.ReportStep_Pass(testCase,
							"Vacation Setpoint displayed in Solution card is: " + vacationSetPointInPrimaryCard);

				} 
				else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Vacation Setpoint dispalyed in Solution card is: " + vacationSetPointInPrimaryCard);
				}
				break;
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured:" + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
