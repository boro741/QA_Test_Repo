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

public class DefaultMaxMinLimit extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DefaultMaxMinLimit(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user is displayed temperature values within maximum minimum limit$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		int currentHeatSetPoint=Integer.parseInt(vhs.GetHeatSetPointValue());
		int currentCoolSetPoint=Integer.parseInt(vhs.GetCoolSetPointValue());
		
				if(CHILUtil.thermostatUnit.equalsIgnoreCase("Celsius")) {
					boolean checkFlag=currentHeatSetPoint<=Integer.parseInt(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.maxHeat)))
							&& currentHeatSetPoint>=Integer.parseInt(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.minHeat)));
					
					if(!checkFlag) {
						flag=false;
					}
					 checkFlag=currentCoolSetPoint<=Integer.parseInt(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.maxCool)))
								&& currentCoolSetPoint>=Integer.parseInt(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(CHILUtil.minCool)));
					if(!checkFlag) {
						flag=false;
					}
				 
				}
				else {
					boolean checkFlag=currentHeatSetPoint<=Integer.parseInt(String.valueOf(CHILUtil.maxHeat))
							&& currentHeatSetPoint>=Integer.parseInt(String.valueOf(CHILUtil.minHeat));
					
					if(!checkFlag) {
						flag=false;
					}
					checkFlag=currentHeatSetPoint<=Integer.parseInt(String.valueOf(CHILUtil.maxCool))
							&& currentHeatSetPoint>=Integer.parseInt(String.valueOf(CHILUtil.minCool));
					if(!checkFlag) {
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
				
			
	   return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}


}
