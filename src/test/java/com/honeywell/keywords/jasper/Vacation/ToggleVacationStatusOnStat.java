package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.VacationHoldScreen;

public class ToggleVacationStatusOnStat extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ToggleVacationStatusOnStat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user \"(.+)\" the \"(.+)\" individually$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		try{
			if(exampleData.get(1).equalsIgnoreCase("stat1")){
				if(testCase.getPlatform().contains("Android")){
					MobileUtils.clickOnElement(testCase,"Xpath","//android.widget.TextView[@text='"+inputs.getInputValue("LOCATION2_DEVICE1_NAME")+"']");
				}else{
					MobileUtils.clickOnElement(testCase,"Xpath","//*[@name='"+inputs.getInputValue("LOCATION2_DEVICE1_NAME")+"_cell']");
				}
			}else{
				if(testCase.getPlatform().contains("Android")){
					MobileUtils.clickOnElement(testCase,"Xpath","//android.widget.TextView[@text='"+inputs.getInputValue("LOCATION2_DEVICE2_NAME")+"']");
				}else{
					MobileUtils.clickOnElement(testCase,"Xpath","//*[@name='"+inputs.getInputValue("LOCATION2_DEVICE2_NAME")+"_cell']");
				}
			}
			switch (exampleData.get(0).toUpperCase()) {
			case "ENABLE": {
				if (vhs.toggleVacationDetectionSwitchInStatScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							String.format("The Vacation Hold Setpoint of Stat is clicked to edit the Set point"));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
							String.format("Failure:Unable to select Stat for set point edit"));
				}
				break;
			}

			case "DISABLE": {
				if (vhs.toggleVacationDetectionSwitchInStatScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							String.format("The Vacation Hold Setpoint of Stat is clicked to edit the Set point"));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
							String.format("Failure:Unable to select Stat for set point edit"));
				}
				break;
			}
			}
			vhs.clickOnBackButton();
		}catch(Exception e){
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("Exception occured ", exampleData.get(0)));
			flag = false;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
