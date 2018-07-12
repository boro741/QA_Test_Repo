package com.honeywell.keywords.flycatcher.Ventialtion;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.SchedulingScreen;

public class VerifyEdit_StopVentilationTimer extends Keyword {



	private TestCases testCase;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyEdit_StopVentilationTimer(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Verifying " + exampleData.get(0) + " Status");
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Verify ventilation Timer is \"(.+)\" at \"(.+)$")
	public boolean keywordSteps() {
		try {
			String TimerStatus = exampleData.get(0);
			String TimerValue = exampleData.get(1);
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase); 
			SchedulingScreen sch = new SchedulingScreen(testCase);
			if (TimerStatus.equalsIgnoreCase("Edits")){
				int Actual_TimerValue = fly.getVentilationTimeValue();
				if (Actual_TimerValue == Integer.parseInt(TimerValue)){
					Keyword.ReportStep_Pass(testCase,
							"Edited Ventilation Timer  : Successfully Edited the ventialtion timer to " + exampleData.get(1));
					return true;
				}
			} else{
				if (sch.IsSaveButtonVisible(10)){
					sch.clickOnSaveButton();
					Keyword.ReportStep_Pass(testCase,
							"Stoped Ventilation Timer  : Successfully Stoped the ventialtion timer");
					return true;
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
				ReportStep_Pass(testCase, "Verify Timer Status : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Timer Status : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Timer Status : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}
