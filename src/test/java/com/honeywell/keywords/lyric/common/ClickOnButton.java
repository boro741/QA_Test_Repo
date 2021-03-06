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
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraConfigurationScreen;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ThermostatSettingsScreen;
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
					DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					if (dasDIY.isCancelButtonVisible()) {
						flag = flag & dasDIY.clickOnCancelButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
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
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("VIEWS CANCEL SETUP")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "BACK ARROW": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInRegisterBaseStationVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInRegisterBaseStationScreen();
						flag = flag & dasDIY.isCancelPopupVisible();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
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
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
								"BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Refresh button not displayed in : " + expectedButton.get(0));
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
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
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
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Add Network button not displayed in : " + expectedButton.get(0));
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS THE ENTER SSID")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInEnterSSIDScreenVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInEnterSSIDScreen();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
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
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
								"BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
						break;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Refresh button not displayed in : " + expectedButton.get(0));
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
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Yes button not displayed in : " + expectedButton.get(0));
					}
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS SENSOR SETUP")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonVisible()) {
						flag = flag & dasDIY.clickOnCancelButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("CANCELS ENABLING GEOFENCE")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInGeoFenceScreenVisible()) {
						flag = flag & dasDIY.clickOnCancelButtonInGeoFenceScreen();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button not displayed in : " + expectedButton.get(0));
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
						flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
								"BASE STATION PROGRESS BAR", 1);
						flag = flag & dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Refresh button not displayed in : " + expectedButton.get(0));
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
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Add Network button not displayed in : " + expectedButton.get(0));
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
			} else if (expectedButton.get(0).equalsIgnoreCase("DELETES THE EXISTING LOCATION DETAILS")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					flag = flag & DIYRegistrationUtils.deleteTheExistingLocation(testCase, inputs);
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
			} else if (expectedButton.get(0).equalsIgnoreCase("downloads the Alexa app")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "ALEXA APP": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnAlexaAppButton();
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("DELETES THERMOSTAT")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if(ts.isDeleteThermostatButtonVisible()) {
						flag = flag & ts.clickOnDeleteThermostatButton();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("DELETES CAMERA DEVICE")) {
					switch (expectedButton.get(1).toUpperCase()) {
					case "DELETE": {
						CameraConfigurationScreen ccs = new CameraConfigurationScreen(testCase);
						flag = flag & ccs.DeleteCamera(testCase, inputs);
						Keyword.ReportStep_Pass(testCase, "Delete Camera Device");
						
						break;
					}
					}
			} else if(expectedButton.get(0).equalsIgnoreCase("DELETES LOCATION")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE LOCATION": {
					AddressScreen as = new AddressScreen(testCase);
					if(as.isDeleteLocationButtonInAddressScreenVisible()) {
						flag = flag & as.clickOnDeleteLocationButtonInAddressScreen();
						Keyword.ReportStep_Pass(testCase, "Location deleted");
					}else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location not deleted : ");
					}
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
