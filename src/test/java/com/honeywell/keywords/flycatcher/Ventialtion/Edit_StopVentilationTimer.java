package com.honeywell.keywords.flycatcher.Ventialtion;

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

public class Edit_StopVentilationTimer extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public Edit_StopVentilationTimer(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Edit Stop Ventilation Mode");
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"(.+)\" Ventilation Timer to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			String TimerCondition = exampleData.get(0);
			String TimerValue = exampleData.get(1);
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase); 
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String ventilationstatus = statInfo.getThermoStatVentilationMode();
			if (ventilationstatus != null){
				if (fly.isVentilationIconVisible()){
					flag = flag && fly.ClickOnVentilationButton();
				} else{
					flag = flag && fly.ClickOnMoreButton();
					flag = flag && fly.ClickOnVentilationButton();
				}
				if (TimerCondition.equalsIgnoreCase("Edits")){
					flag = flag && fly.ClickEditVentTimer();
					FlyCatcherVentialtion fl = new FlyCatcherVentialtion();
					fl.SetVentilationTimer(testCase, inputs, TimerValue);
				}else{
					if (fly.isStopTimerVisible()){
						flag = flag && fly.ClickStopTimer();
					}
				}
			}else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						" Ventilation Mode is disabled in thermostat ");
			}
		} catch (Exception e){

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
				ReportStep_Pass(testCase, "Edit Ventilation Timer : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Change System Mode : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}
