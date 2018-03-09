package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class SelectElementOnAScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects \"(.*)\" from \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("Exclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Inclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Activate Z-Wave Device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRM CANCEL": {
					DASZwaveUtils.clickCancelFromNavigation(testCase);
					DASZwaveUtils.clickConfirmOnCancelFromNavigation(testCase);
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("install device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "Z-WAVE DEVICE": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					ads.clickOnZwaveFromAddNewDevice();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Z-wave Utilities")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONTROLLER FACTORY RESET": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFactoryReset();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Z-wave Devices")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ALL ON": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOn();
					break;
				}
				case "ALL OFF": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOff();
					break;
				}
				case "FIX ALL": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFixAll();
					zwaveScreen.clickOnFixAllPopupConfirm();
					zwaveScreen.clickOnFixAllPopupAccept();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Switch Settings")
					|| parameters.get(1).equalsIgnoreCase("Dimmer Settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "DELETE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					break;
				}
				case "REPLACE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnReplaceButton();
					zwaveScreen.clickOnReplacePopupOk();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "15": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (bs.is15SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn15SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"15 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "30": {
					if (bs.is30SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn30SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"30 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "45": {
					if (bs.is45SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn45SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"45 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "60": {
					if (bs.is60SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn60SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"60 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				inputs.setInputValue("LOCATION1_NAME", parameters.get(0));
				return DIYRegistrationUtils.selectAvaiableLocation(testCase, parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("NAME YOUR BASE STATION")) {
				if (!inputs.isInputAvailable("LOCATION1_CAMERA1_NAME")) {
					inputs.setInputValue("LOCATION1_CAMERA1_NAME", parameters.get(0));
				} else {
					inputs.setInputValue("LOCATION1_CAMERA2_NAME", parameters.get(0));
				}

				return DIYRegistrationUtils.selectAvaiableBaseStationName(testCase, parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("SELECT BASE STATION")) {
				return DIYRegistrationUtils.selectABaseStationFromListOfAvailableBaseStations(testCase,
						parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("Connect to Network")) {
				return DIYRegistrationUtils.selectWiFiNameFromTheListOfAvailableNetworks(testCase, parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("NAME SENSOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				flag = flag
						& DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "SAVING SENSOR PROGRESS BAR", 1);
			}
		} catch (

		Exception e) {
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
