package com.honeywell.keywords.lyric.camerasettings;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.VacationHoldScreen;

public class NavigateBackAndForthInAScreen extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;
	public DataTable data;

	public NavigateBackAndForthInAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user navigates back and forth in \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		switch (expectedOption.get(0).toUpperCase()) {
		case "MANAGE ALERTS": {
			cs.navigateBackAndForthInManageAlertsScreen(testCase);
			break;
		}
		case "MOTION DETECTION SETTINGS": {
			cs.navigateBackAndForthInMotionDetectionScreen(testCase);
			break;
		}
		case "VACATION": {
			vhs.navigateBackAndForthInVacationsScreen(testCase);
			break;
		}
		case "VACATION STAT": {
			vhs.navigateBackAndForthInVacationsStatScreen(testCase,inputs);
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid option: " + expectedOption.get(0));
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}

}
