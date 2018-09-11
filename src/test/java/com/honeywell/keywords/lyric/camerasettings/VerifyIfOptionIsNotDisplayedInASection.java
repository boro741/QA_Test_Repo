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
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;

public class VerifyIfOptionIsNotDisplayedInASection extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;
	public DataTable data;

	public VerifyIfOptionIsNotDisplayedInASection(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with \"(.*)\" option$")
	public boolean keywordSteps() throws KeywordException {
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		switch (expectedOption.get(0).toUpperCase()) {
		case "EMAIL NOTIFICATIONS": {
			cs.isEmailNotificationCellVisibleAfterTurningOffAlerts(testCase);
			break;
		}
		case "COVER TAMPER STATUS": {
			SensorSettingScreen ssc = new SensorSettingScreen(testCase);			
			if (!ssc.isClearTamperOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Cover Tampered Text Not Found");
			}
			break;
		}	case "OFFLINE STATUS": {
			SensorSettingScreen ssc = new SensorSettingScreen(testCase);			
			if (!ssc.isSensorOffScreenDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Offline Status Text Not Found");
			}
			break;
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
