package com.honeywell.keywords.lyric.camerasettings;

import java.util.ArrayList;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.Dashboard;

public class ChangeCameraStatusThroughApp extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;
	public DataTable data;

	public ChangeCameraStatusThroughApp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user camera is set to \"(.*)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
			boolean currentCameraStatus = cs.getCameraStatus(10);
			switch (expectedOption.get(0).toUpperCase()) {
			case "ON": {
				if (currentCameraStatus){
					Keyword.ReportStep_Pass(testCase, "Camera Current status is already ON");
				} else {
					cs.clickOnCameraOnButton();
					Thread.sleep(5000);
					Keyword.ReportStep_Pass(testCase, "Camera Current status is set to ON");
				}
			
				break;
			}
			case "OFF": {
				if (!currentCameraStatus) {
					Keyword.ReportStep_Pass(testCase, "Camera Current status is already OFF");
				} else{
					cs.clickOnCameraOffButton();
					Thread.sleep(10000);
					Keyword.ReportStep_Pass(testCase, "Camera Current status is set to OFF");
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid Option " + expectedOption.get(0));
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}

}
