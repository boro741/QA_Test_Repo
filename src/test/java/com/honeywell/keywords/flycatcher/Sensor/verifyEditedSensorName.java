package com.honeywell.keywords.flycatcher.Sensor;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class verifyEditedSensorName extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public verifyEditedSensorName(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Verify if sensor is moved to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			if (exampleData.get(0).equalsIgnoreCase("New Room") || exampleData.get(0).equalsIgnoreCase("Custom Room")  || exampleData.get(0).equalsIgnoreCase("Exisiting Room")){
				FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
					if (fly.getSensorDetailsHeaderText().contains(inputs.getInputValue("EditSensorName"))){
					Keyword.ReportStep_Pass(testCase, "Sensor is moved to " + inputs.getInputValue("SENSOR1"));
					flag =  true;
				} else{
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							" Unable to move sensor to " + inputs.getInputValue("SENSOR1"));
					flag =  false;
				}
			}

		} catch (Exception e){
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/*
	 * ========================================After
	 * method============================================================= Check
	 * if the Keyword has been executed successfully.
	 * =========================================================================
	 * ==========================================
	 */
	@Override
	@AfterKeyword
	public boolean postCondition() {

		try {
			if (flag) {
				ReportStep_Pass(testCase, "Verify Humidifier Value : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Humidifier Value : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Humidifier Value : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}
}