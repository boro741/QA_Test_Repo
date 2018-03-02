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
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ZwaveScreen;

public class SelectElementOnAScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
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
			if(parameters.get(1).equalsIgnoreCase("Exclusion Mode Active")||parameters.get(1).equalsIgnoreCase("Inclusion Mode Active")||parameters.get(1).equalsIgnoreCase("Activate Z-Wave Device")){
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRM CANCEL": {
					DASZwaveUtils.clickCancelFromNavigation(testCase);
					DASZwaveUtils.clickConfirmOnCancelFromNavigation(testCase);
					break;
				}
				}
			}else if (parameters.get(1).equalsIgnoreCase("install device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "Z-WAVE DEVICE": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					ads.clickOnZwaveFromAddNewDevice();
					break;
				}
				}
			}else if (parameters.get(1).equalsIgnoreCase("Z-wave Utilities")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONTROLLER FACTORY RESET": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFactoryReset();
					break;
				}
				}
			}else if (parameters.get(1).equalsIgnoreCase("Z-wave Devices")) {
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
			} else if (parameters.get(1).equalsIgnoreCase("Switch Settings")||parameters.get(1).equalsIgnoreCase("Dimmer Settings")) {
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
			} else if (parameters.get(1).equalsIgnoreCase("Choose Location")) {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "HOME": {
					boolean flag = true;
					if (dasDIY.isChooseLocationHeaderTitleVisible() && dasDIY.isHomeLocationDisplayed()) {
						flag = flag & dasDIY.clickOnHomeLocation();
					}
					return flag;
				}
				case "CALIFORNIA": {
					boolean flag = true;
					if (dasDIY.isChooseLocationHeaderTitleVisible() && dasDIY.isCustomLocationTextFieldVisible()) {
						flag = flag & dasDIY.enterCustomLocationName(parameters.get(0));
					}
					return flag;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Name Your Base Station")) {
				switch (parameters.get(0).toUpperCase()) {
				case "LIVING ROOM": {
					boolean flag = true;
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNameYourBaseStationHeaderTitleVisible() && dasDIY.isLivingRoomBaseStationDisplayed()) {
						flag = flag & dasDIY.clickOnLivingRoomBaseStation();
					}
					return flag;
				}
				case "SCRUM ROOM": {
					boolean flag = true;
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isNameYourBaseStationHeaderTitleVisible() && dasDIY.isCustomNameTextFieldDisplayed()) {
						flag = flag & dasDIY.enterCustomNameInNameYourBaseStationScreen(parameters.get(0));
					}
					return flag;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Connect to Network")) {

				boolean flag = true;
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isConnectToNetworkHeaderDescVisible() && dasDIY.isAddANetworkButtonVisible()) {
					flag = flag & dasDIY.clickOnWiFiNameOnWiFiScreen(parameters.get(0));
				}
				dasDIY.isWiFiPasswordTextFieldVisibile();
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
