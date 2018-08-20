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

public class VerifyWindowProtection extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyWindowProtection(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Verify Window Protection is displayed and set to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int getwindowProtectionValue = statInfo.getWindowProtection();
			flag = flag && fly.ClickOnHumOptionButton();
			flag = flag && fly.ClickOnWindowProtectionButton();
			String actualValue = fly.getTargetHumidityValue();
			String expectedValue = exampleData.get(0).replace("0", "");
			if (actualValue.equalsIgnoreCase(expectedValue) && actualValue.equalsIgnoreCase(String.valueOf(getwindowProtectionValue))){
				Keyword.ReportStep_Pass(testCase, "Window Protection is set to " + expectedValue + " With current Indoor Humidity Value " + fly.getTargetHumidityValue());
				flag =  true;
			}else{
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						" Unable to Set Window Protection ");
				flag =  false;
			}
			flag = flag && fly.ClickOnSaveOptionButton();
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
				ReportStep_Pass(testCase, "Verify Window Protection : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Window Protection : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Window Protection : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}


