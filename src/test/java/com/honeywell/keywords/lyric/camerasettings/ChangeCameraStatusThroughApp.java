package com.honeywell.keywords.lyric.camerasettings;

import java.util.ArrayList;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
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
			Dashboard d = new Dashboard(testCase);
			CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
			String currentCameraStatus = d.getCameraStatus(25);
			switch (expectedOption.get(0).toUpperCase()) {
			case "ON": {
				if (currentCameraStatus.contains("ON") || currentCameraStatus.contains("on")) {
					Keyword.ReportStep_Pass(testCase, "Camera Current status is ON");
					break;
				} else {
					Keyword.ReportStep_Pass(testCase, "Camera Current status is OFF and turning it to ON");
					flag = flag & DashboardUtils.selectCameraDeviceFromDashboard(testCase, "Camera");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (cs.isAppSettingsIconVisible() && cs.isCameraOnButtonVisible(10)) {
						cs.clickOnCameraOnButton();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						Keyword.ReportStep_Pass(testCase, "Camera status is turned ON");
						if (cs.isBackButtonInCameraSolutionCardScreenVisible()) {
							cs.clickOnBackButtonInCameraSolutionCardScreen();
						}
					}
				}
				break;
			}
			case "OFF": {
				if (currentCameraStatus.contains("OFF") || currentCameraStatus.contains("off")) {
					Keyword.ReportStep_Pass(testCase, "Camera Current status is OFF");
					break;
				} else {
					Keyword.ReportStep_Pass(testCase, "Camera Current status is ON and turning it to OFF");
					flag = flag & DashboardUtils.selectCameraDeviceFromDashboard(testCase, "Camera");
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (cs.isAppSettingsIconVisible() && cs.isCameraOffButtonVisible(10)) {
						cs.clickOnCameraOffButton();
						CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						Keyword.ReportStep_Pass(testCase, "Camera status is turned OFF");
						if (cs.isBackButtonInCameraSolutionCardScreenVisible()) {
							cs.clickOnBackButtonInCameraSolutionCardScreen();
						}
					}
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
