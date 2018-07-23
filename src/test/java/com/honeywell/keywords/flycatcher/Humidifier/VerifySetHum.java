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

public class VerifySetHum extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifySetHum(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Verify humidifier set to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int getHumidifierValue = statInfo.getHumidifierValue();
			if (!fly.isHumButtonVisible(30)){
				flag = flag && fly.ClickOnMoreButton();
				flag = flag && fly.ClickOnHumButton();
			} else{
				flag = flag && fly.ClickOnHumButton();
			}
			int actualValue = Integer.parseInt(fly.getTargetHumidityValue().replace("%", ""));
			int expectedValue = Integer.parseInt(exampleData.get(0));
					if (actualValue == expectedValue && actualValue == getHumidifierValue ){
						Keyword.ReportStep_Pass(testCase, "Humidifier value is set to " + expectedValue + " With current Indoor Humidity Value " + fly.getCurrentHumidityValue());
						flag =  true;
					}else{
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								" Unable to Set Humidifier value ");
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

