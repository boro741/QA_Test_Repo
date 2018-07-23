package com.honeywell.keywords.flycatcher.Humidifier;

import java.util.ArrayList;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class VerifyHumStatus extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyHumStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Verify if humidification \"(.+)\" in stat$")
	public boolean keywordSteps() {
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String getHumidifierValue = statInfo.getThermoStatHumidificationSettings();
			if (getHumidifierValue.equalsIgnoreCase("Auto") && exampleData.get(0).equalsIgnoreCase("Enabled")){
				if (!fly.isOverlayIconVisible(30)){
					Keyword.ReportStep_Pass(testCase, "Humidifier is " + exampleData.get(0));
					flag =  true;
				}
			}else if (getHumidifierValue.equalsIgnoreCase("Off") && exampleData.get(0).equalsIgnoreCase("Disabled")){
				if (fly.isOverlayIconVisible(30)){
					Keyword.ReportStep_Pass(testCase, "Humidifier is " + exampleData.get(0));
					flag =  true;
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
				ReportStep_Pass(testCase, "Verify Humidifier Status : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Humidifier Status : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Humidifier Status : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}

