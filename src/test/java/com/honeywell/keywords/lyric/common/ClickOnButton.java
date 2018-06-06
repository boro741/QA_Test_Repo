 package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ZwaveScreen;

public class ClickOnButton extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedButton;
	public boolean flag = true;

	public ClickOnButton(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedButton) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedButton = expectedButton;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) by clicking on (.*) button$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (expectedButton.get(0).equalsIgnoreCase("deletes DAS device")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnDeleteDASButton();
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("fixes all zwave devices")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "FIX ALL": {
					ZwaveScreen zs = new ZwaveScreen(testCase);
					if (zs.isFixAllEnabled()) {
						flag = flag & zs.clickOnFixAll();
						flag = flag & zs.clickOnFixAllPopupCancel();
						flag = flag & zs.clickOnFixAll(); 
						flag = flag & zs.clickOnFixAllPopupConfirm(); 
						flag = flag & zs.clickOnFixAllPopupAccept();
					} else {
						Keyword.ReportStep_Pass(testCase, "No device found to be offline");
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS THE SET UP")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonVisible()) {
						flag = flag & dasDIY.clickOnCancelButton();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS THE CONNECT TO WIFI NETWORK")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInWiFiScreenVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInWiFiScreen();
					}
					break;
				}
				}
			}  else if (expectedButton.get(0).equalsIgnoreCase("VIEWS CANCEL SETUP")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "BACK ARROW": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInRegisterBaseStationVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInRegisterBaseStationScreen();
						flag = flag & dasDIY.isCancelPopupVisible();
					}

					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("VIEWS SELECT BASE STATION SCREEN")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "REFRESH": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isRefereshButtonInSelectBaseStationScreenVisible()) {
						flag = flag & dasDIY.clickOnRefereshButtonInSelectBaseStationScreen();
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
						break;
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("VIEWS CANCEL SETUP")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInRegisterBaseStationVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInRegisterBaseStationScreen();
						flag = flag & dasDIY.isCancelPopupVisible();
						break;
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("ADDS A NETWORK")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "ADD A NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddANetworkButtonVisible()) {
						flag = flag & dasDIY.clickOnAddANetworkButton();
						flag = flag & dasDIY.isAddANetworkHeaderTitleVisible();
						break;
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS THE ENTER SSID")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInEnterSSIDScreenVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInEnterSSIDScreen();
					}
					break;
				}
				}
			}   else if (expectedButton.get(0).equalsIgnoreCase("VIEWS SELECT BASE STATION SCREEN")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "REFRESH": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isRefereshButtonInSelectBaseStationScreenVisible()) {
						flag = flag & dasDIY.clickOnRefereshButtonInSelectBaseStationScreen();
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
						break;
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("FIRMWARE UPDATE")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "YES": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isYesButtonInSmartHomeSecuritySuccessScreenVisible()) {
						flag = flag & dasDIY.clickYesButtonInSmartHomeSecuritySuccessScreen();
						flag = flag & dasDIY.isFirmwareUpdatePopupVisible(20);
						break;
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS ENABLING GEOFENCE")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInGeoFenceScreenVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInGeoFenceScreen();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("deletes sensor")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnDeleteSensorButton();
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("views select base station screen")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "REFRESH": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isRefereshButtonInSelectBaseStationScreenVisible()) {
						flag = flag & dasDIY.clickOnRefereshButtonInSelectBaseStationScreen();
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("ADDS A NETWORK")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "ADD A NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddANetworkButtonVisible()) {
						flag = flag & dasDIY.clickOnAddANetworkButton();
						flag = flag & dasDIY.isAddANetworkHeaderTitleVisible();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("DELETES LOCATION DETAILS")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					flag = flag & DIYRegistrationUtils.deleteLocation(testCase, inputs);
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("DELETES DEFAULT LOCATION DETAILS")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					flag = flag & DIYRegistrationUtils.deleteDefaultLocation(testCase, inputs);
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("deletes keyfob")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnDeleteDASButton();
					break;
				}
				}
			}
			else if (expectedButton.get(0).equalsIgnoreCase("downloads the Alexa app")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "ALEXA APP": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnAlexaAppButton();
					break;
				}
				}
			}
			else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid Input: " + expectedButton.get(1));
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
