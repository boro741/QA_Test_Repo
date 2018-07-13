package com.honeywell.keywords.flycatcher.Ventialtion;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class ResetVentilationTimer extends Keyword {



	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;
	public ResetVentilationTimer(TestCases testCase, TestCaseInputs inputs) {
		super("Set Ventilation Timer");
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user Resets Ventilation Timer$")
	public boolean keywordSteps() {
		try 
		{
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID=statInfo.getDeviceID();
			try {
				if (chUtil.getConnection()) {
					
					if (chUtil.ResetVentilationTimer(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
							deviceID) == 400) {
						Keyword.ReportStep_Pass(testCase,
								"Ventilation Timer Using CHIL : Successfully Retset Ventilation Timer through CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"entilation Timer Using CHIL : Failed to Retset Ventilation Timer through CHIL");
					}
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured : " + e.getMessage());
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
				ReportStep_Pass(testCase, "Reset Ventilation Timer : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Reset Ventilation Timer : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Reset Ventilation Timer : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}