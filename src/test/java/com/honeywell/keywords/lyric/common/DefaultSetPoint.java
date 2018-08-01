package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
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

public class DefaultSetPoint extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DefaultSetPoint(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user is displayed with default set point value$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			if(vhs.ClickOnVacationHoldSetpointSettings()) {
				Keyword.ReportStep_Pass(testCase, String.format("The Vacation Hold Setpoint Screen is clicked"));
				if(CHILUtil.thermostatUnit.equalsIgnoreCase("Celsius")) {
					if(!vhs.GetHeatSetPointValue().equals(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.heatSetPoints)))) {
						flag=false;
					}
					if(!vhs.GetCoolSetPointValue().equals(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.coolSetPoints)))) {
						flag=false;
					}
				 
				}
				else {
					if(!vhs.GetHeatSetPointValue().equals(String.valueOf(CHILUtil.heatSetPoints))) {
						flag=false;
					}
					if(!vhs.GetCoolSetPointValue().equals(String.valueOf(CHILUtil.coolSetPoints))) {
						flag=false;
					}
				}
				 
				if(flag) {
					 Keyword.ReportStep_Pass(testCase, String.format("The user is displayed with default set point values"));
				 }
				 else
				 {
					 Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Failure:The user is  not displayed with default set point values"));
				 }
				
			}
			else {
				Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Unable to click the vacation hold setpoint screen"));
				flag=false;
			}
	   return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}


}