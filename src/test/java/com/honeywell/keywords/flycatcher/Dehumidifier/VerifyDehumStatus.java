package com.honeywell.keywords.flycatcher.Dehumidifier;

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

public class VerifyDehumStatus extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyDehumStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Verify if DeHumidification \"(.+)\" in stat$")
	public boolean keywordSteps() {
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String getDeHumidifierValue = statInfo.getThermoStatDeHumidificationSettings();
			if (getDeHumidifierValue.equalsIgnoreCase("Auto") && exampleData.get(0).equalsIgnoreCase("Enabled")){
				if (!fly.isOverlayIconVisible(30)){
					Keyword.ReportStep_Pass(testCase, "DeHumidifier is " + exampleData.get(0));
					flag =  true;
				}
			}else if (getDeHumidifierValue.equalsIgnoreCase("Off") && exampleData.get(0).equalsIgnoreCase("Disabled")){
				if (fly.isOverlayIconVisible(30)){
					Keyword.ReportStep_Pass(testCase, "DeHumidifier is " + exampleData.get(0));
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
				ReportStep_Pass(testCase, "Verify DeHumidifier Status : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify DeHumidifier Status : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify DeHumidifier Status : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}
