package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.*;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.DR.utils.DRUtils;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.DRScreens;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;

import java.util.ArrayList;

public class TaponElement extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedLocator;
	private TestCaseInputs inputs;
	private boolean flag = true;

	public TaponElement(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedLocator) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedLocator = expectedLocator;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return true;
	}

	@Override
	@KeywordStep(gherkins = "^user taps on (.*)$")
	public boolean keywordSteps() throws KeywordException {
		// if (testCase.isTestSuccessful()) {
		switch (expectedLocator.get(0).toUpperCase()) {
		case "ATTENTION FROM CAMERA SOLUTION CARD": {
			DASCameraUtils.CreateAttention(testCase, inputs);
			break;
		}
		case "DISMISS ALARM POPUP": {
			DASAlarmUtils.confirmDismissAlarm(testCase, inputs);
			break;
		}
		case "BELOW RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			ts.clickOnBelowAboveTempAlertRangeOption(expectedLocator.get(0));
			break;
		}
		case "ABOVE RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			ts.clickOnBelowAboveTempAlertRangeOption(expectedLocator.get(0));
			break;
		}
		case "DOWN STEPPER": {
			PrimaryCard ds = new PrimaryCard(testCase);
			ds.clickOnDownStepper();
			break;
		}
		case "UP STEPPER": {
			PrimaryCard us = new PrimaryCard(testCase);
			us.clickOnUpStepper();
			break;
		}
		case "DR POPUP": {
			DRScreens dp = new DRScreens(testCase);
			flag = flag & DRUtils.waitForProgressBarToComplete(testCase, "DR Popup visible", 2);
			dp.ClickOnOkPopup();
			break;
		}
		case "DR CANCEL OK": {
			DRScreens dp = new DRScreens(testCase);
			dp.ClickOnOkPopup();
			break;
		}
		case "DELETE SENSOR": {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			flag = flag && fly.ClickOnHumOptionButton();
			flag = flag && fly.ClickOnDeleteSensor();
			break;
		} case "CLEAR TAMPER":{
			SensorSettingScreen sc = new SensorSettingScreen(testCase);
			flag = flag & sc.clickOnClearCoverTamperOption();
			if (sc.isSensorTamperClearPopupDisplayed(10)) {
				flag = flag & sc.clickOnRetryTamperClearPopup();
			}
			break;
		}
		case "IDENTIFY SENSOR": {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			flag = flag && fly.ClickOnHumOptionButton();
			flag = flag & fly.ClickOnIdentifySensor();
			break;
		}
		case "MOVE SENSOR": {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			flag = flag && fly.ClickOnHumOptionButton();
			flag = flag & fly.ClickOnMoveSensor();
			break;
		} case "CANCEL": {
			SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
			flag = flag && ssc.clickOnCancelButtonWhileSwitchingModes();
			break;
		} 
		case "NEXT" :{
			CoachMarks cm = new CoachMarks(testCase);
			if (cm.isNextButtonVisible(10)) {
			flag = flag && cm.clickOnNextButton();
			}
			break;
		} 
		case "BACK" :{
			CoachMarks cm = new CoachMarks(testCase);
			if (cm.isCoachMarkBackButtonVisible(10)) {
			flag = flag && cm.clickOnCoachMarkBackButton();
			}
			break;
		} 
		case "DONE" :{
			CoachMarks cm = new CoachMarks(testCase);
			if (cm.isDoneButtonVisible(10)) {
			flag = flag && cm.clickOnDoneButton();
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					expectedLocator.get(0) + " input not handled");
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
