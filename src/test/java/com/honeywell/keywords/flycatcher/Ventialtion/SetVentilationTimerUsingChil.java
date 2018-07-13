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

public class SetVentilationTimerUsingChil extends Keyword {



	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;
	public SetVentilationTimerUsingChil(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Set Ventilation Timer");
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
	@KeywordStep(gherkins = "^user sets Ventilation Timer \"(.+)\"$")
	public boolean keywordSteps() {
		try 
		{
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID=statInfo.getDeviceID();
			int VentilationTimer = Integer.parseInt(exampleData.get(0));
			try {
				if (chUtil.getConnection()) {
					if (chUtil.SetVentilationTimer(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
							deviceID,VentilationTimer) == 400) {
						Keyword.ReportStep_Pass(testCase,
								"Ventilation Timer Using CHIL : Successfully set Ventilation Timer to "+ VentilationTimer +" through CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"entilation Timer Using CHIL : Failed to set Ventilation Timer to  "+ VentilationTimer +" through CHIL");
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
						"Set Ventilation Timer : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Set Ventilation Timer : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}