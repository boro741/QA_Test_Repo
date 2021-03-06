package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.json.JSONObject;
import org.openqa.selenium.By;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.DR.utils.DRUtils;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;

import io.appium.java_client.MobileElement;

public class VerifyingAOption extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyingAOption(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"(.+)\" with the \"(.+)\" option$")
	public boolean keywordSteps() throws KeywordException {

		if (expectedScreen.get(1).toUpperCase().equals("FR")) {
			JSONObject tempJSON = (JSONObject) LyricUtils.getLocationInformation(testCase, inputs);
			long locationID = tempJSON.getLong("locationID");
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD BE DISPLAYED": {
				SecondaryCardSettings scs = new SecondaryCardSettings(testCase);
				try {
					if (scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is present on the Secondary Cards screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is not present on the Secondary Cards screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED": {
				SecondaryCardSettings scs = new SecondaryCardSettings(testCase);
				try {
					if (!scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is not present on the Secondary Cards screen");

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is  present on the Secondary Cards screen but should not");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				} finally {
					// Update to the location where FR will permit
					FRUtils.updateLocationThroughCHIL(testCase, inputs,
							"{\"city\":\"Chicago Ridge\",\"state\":\"NY\",\"country\":\"US\",\"zipcode\":\"11747\"}",
							locationID);
				}
				break;

			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid Input: " + expectedScreen.get(0));
			}
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON SELECTED MODE")) {
			PrimaryCard card = new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED":
				if (card.isSetModeDisplayed(inputs)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is  displayed");

				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("EMAIL NOTIFICATIONS")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED":
				if (cs.isEmailNotificationCellVisibleAfterTurningONAlerts(testCase)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is  displayed");

				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
		}

		else if (expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON NEW SELECTED MODE")) {
			PrimaryCard card = new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED":
				if (card.isNewSetModeDisplayed(inputs)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");

				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
		}

		else if (expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN CANCEL")) {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if (sensor.isCancelButtonDisplayed()) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				}
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN BACK")) {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if (sensor.isBackButtonDisplayed()) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				}
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("CANCEL SENSOR SETUP POPUP")) {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (sensor.isCancelSetUpPopUpVisible()) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel sensor popup is displayed");
					return flag;

				} else {
					Keyword.ReportStep_Pass(testCase, "Cancel Sensor popup is not displayed");

				}

				break;

			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("DONE")) {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (sensor.isDoneButtonVisible() == false) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Done button is not displayed");
					return flag;

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Done button is displayed");
				}

				break;

			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("RESPECTIVE SETPOINT VALUE")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				Dashboard thermo = new Dashboard(testCase);
				if (!thermo.isUserExpectedTemperatureDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Temperature is not Displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Temperature Displayed");
				}

				break;

			}
			}
		}else if (expectedScreen.get(1).toUpperCase().equals("RESPECTIVE SETPOINT VALUE IN DASHBOARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				Dashboard thermo = new Dashboard(testCase);
				if (thermo.isUserExpectedTemperatureDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Temperature is Displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Temperature Not Displayed");
				}

				break;

			}
			}
		}
		else if (expectedScreen.get(1).toUpperCase().equals("RESPECTIVE SETPOINT VALUE IN SOLUTION CARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				if (thermo.isUserExpectedTemperatureDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Temperature is Displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Temperature Not Displayed");
				}

				break;

			}
			}
		}
		else if (expectedScreen.get(1).toUpperCase().equals("--")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				Dashboard dash = new Dashboard(testCase);
				if (dash.isTemperatureNotDisplayed()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON SELECTED FAN")) {

			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				String currentlySelectedFanMode = inputs.getInputValue("SelectedFanMode");
				if (currentlySelectedFanMode.equalsIgnoreCase("AUTO")) {

					flag = flag & thermo.isAutoFanElementSelected();
				} else if (currentlySelectedFanMode.equalsIgnoreCase("CIRCULATE")) {

					flag = flag & thermo.isCirculateFanElementSelected();
				} else if (currentlySelectedFanMode.equalsIgnoreCase("ON")) {

					flag = flag & thermo.isONFanElementSelected();
				}
			}
				break;
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,
						expectedScreen.get(1) + " is present for " + inputs.getInputValue("SelectedFanMode"));
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("AUTO FAN")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is not updated");
				}
				break;
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("CIRCULATE")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is not updated");
				}
				break;
			}
			}

		}

		else if (expectedScreen.get(1).toUpperCase().equals("CIRCULATE")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is not updated");
				}
				break;
			}
			}

		}

		else if (expectedScreen.get(1).toUpperCase().equals("ON")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + "Value is not updated");
				}
				break;
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("FAN")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.isFanButtonVisible();
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " mode is displayed");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " mode is not displayed");

				}
				break;
			}
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("AUTOMODE")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is updated");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			case "SHOULD BE PROVIDED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.isAutoModeVisible();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is provided");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is not provided");

				}
				break;
			}
			case "SHOULD NOT BE PROVIDED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.autoModeButtonVisible();
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is provided");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not provided");

				}
				break;
			}

			}
		} else if (expectedScreen.get(1).toUpperCase().equals("COOL")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is updated");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("HEAT")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not updated");
				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is updated");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("OFF")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is updated");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			case "SHOULD BE UPDATED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
				if (flag) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is updated");

				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is not updated");

				}
				break;
			}
			}

		} else if (expectedScreen.get(1).toUpperCase().equals("FAN")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = thermo.isFanButtonVisible();
				if (flag) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + expectedScreen.get(1) + " mode is displayed");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(1) + " mode is not displayed");

				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("MAX set temperature on SOLUTION CARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isMaxTemperatureVisibleOnSolutionCard(inputs);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("MIN set temperature on SOLUTION CARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isMinTemperatureVisibleOnSolutionCard(inputs);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("MAX set temperature on DASHBOARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				Dashboard thermo = new Dashboard(testCase);
				flag = flag & thermo.isMaxTemperatureVisibleOnDashBoard(inputs);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Emergency heat")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISABLED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isEmergencyHeatOptionDisabled();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isEmergencyHeatOptionNotDisplayed();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;

			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("MIN set temperature on DASHBOARD")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				Dashboard thermo = new Dashboard(testCase);
				flag = flag & thermo.isMinTemperatureVisibleOnDashBoard(inputs);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is updated");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " Value is not updated");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Thermostat settings")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DISABLES AUTO CHANGE OVER": {
				ThermostatSettingsScreen Settingscreen = new ThermostatSettingsScreen(testCase);
				try {
					if (!Settingscreen.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"AUTO CHANGE OVER is already disabled in the Thermostat Settings Screen");
					} else {
						flag = flag & Settingscreen.toggleThermostatAutoChangeOverSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!Settingscreen.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "AUTO CHANGE OVER Toggle is turned OFF");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
			case "ENABLES AUTO CHANGE OVER": {
				ThermostatSettingsScreen Settingscreen = new ThermostatSettingsScreen(testCase);
				try {
					if (Settingscreen.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"AUTO CHANGE OVER is already enabled in Thermostat Settings Screen");
					} else {
						flag = flag & Settingscreen.toggleThermostatAutoChangeOverSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (Settingscreen.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "AUTO CHANGE OVER Switch is turned ON");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Auto mode description")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isAutoDefinitionVisible();
				if (flag) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				} else {
					flag = true;
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				PrimaryCard thermo = new PrimaryCard(testCase);
				flag = flag & thermo.isAutoDefinitionVisible();
				if (flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("DR event label on primary card")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				PrimaryCard dr = new PrimaryCard(testCase);
				flag = flag & DRUtils.waitForProgressBarToComplete(testCase, "DR Label", 1);
				if (!dr.isDrGreenLabelVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				PrimaryCard dr = new PrimaryCard(testCase);
				flag = flag & DRUtils.waitForProgressBarToComplete(testCase, "DR Label visible", 1);
				if (dr.isDrGreenLabelVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("DR event label on dashboard")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				if (!dr.isDrEventLabelVisibleOnDashboard()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				if (dr.isDrEventLabelVisibleOnDashboard()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("NO SCHEDULE")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard pc = new PrimaryCard(testCase);
				flag = flag & DRUtils.waitForProgressBarToComplete(testCase, "DR Label", 1);
				if (pc.isNoScheduleTextAvailable()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}

		} else if (expectedScreen.get(1).equalsIgnoreCase("Heating on dashboard")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase, "HEATING TEXT TO DISAPPEAR", 2);
				if (!dr.isHeatingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase, "HEATING TEXT TO APPEAR", 4);
				if (dr.isHeatingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Heating on primary card")) {
			PrimaryCard pc = new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase,
						"HEATING TEXT TO DISSAPEAR IN PRIMARY CARD", 4);
				if (!pc.isHeatingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				PrimaryCard pc1 = new PrimaryCard(testCase);
				flag = flag
						& DashboardUtils.waitForOptionOnScreen(testCase, "HEATING TEXT TO APPEAR IN PRIMARY CARD", 4);
				if (pc1.isHeatingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Cooling on dashboard")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase, "COOLING TEXT TO DISAPPEAR", 4);
				if (!dr.isCoolingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				Dashboard dr = new Dashboard(testCase);
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase, "COOLING TEXT TO APPEAR", 4);
				if (dr.isCoolingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(0).equalsIgnoreCase("SHOULD BE DISPLAYED")) {
			switch (expectedScreen.get(1)) {
			case "Unable to connect, please try again": {
				if (testCase.getPlatform().contains("IOS")) {
					MobileElement element = testCase.getMobileDriver().findElement(By.name(expectedScreen.get(1)));
					if (element != null) {
						if (element.getAttribute("label").equalsIgnoreCase(expectedScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " exists");
						} else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Expected :" + expectedScreen.get(1) + "Actual :" + element.getAttribute("label"));
							flag = false;
						}
					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1) + " does not exists");
						flag = false;
					}
				} else {
					CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
					if (cs.isRetryTextExists()) {
						if (cs.getRetryText().equalsIgnoreCase(expectedScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " exists");
						} else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Expected :" + expectedScreen.get(1) + "Actual :" + cs.getRetryText());
							flag = false;
						}

					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1) + " does not exists");
						flag = false;
					}
				}
				break;
			}

			case "play icon": {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				if (cs.isCameraPlayButtonExists(10)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " exists");
				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Tap to continue live feed": {
				if (testCase.getPlatform().contains("IOS")) {
					MobileElement element = testCase.getMobileDriver().findElement(By.name(expectedScreen.get(1)));
					if (element != null) {
						if (element.getAttribute("label").equalsIgnoreCase(expectedScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " exists");
						} else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Expected :" + expectedScreen.get(1) + "Actual :" + element.getAttribute("label"));
							flag = false;
						}
					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1) + " does not exists");
						flag = false;
					}
				} else {
					CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
					if (cs.isRetryTextExists()) {
						if (cs.getRetryText().equalsIgnoreCase(expectedScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " exists");
						} else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Expected :" + expectedScreen.get(1) + "Actual :" + cs.getRetryText());
							flag = false;
						}
					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1) + " does not exists");
						flag = false;
					}
					break;
				}
			}
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Cooling on primary card")) {
			PrimaryCard pc = new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				flag = flag & DashboardUtils.waitForOptionOnScreen(testCase,
						"COOLING TEXT TO DISSAPEAR IN PRIMARY CARD", 4);
				if (!pc.isCoolingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is not displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is displayed");
				}
				break;
			}
			case "SHOULD BE DISPLAYED": {
				PrimaryCard pc1 = new PrimaryCard(testCase);
				flag = flag
						& DashboardUtils.waitForOptionOnScreen(testCase, "COOLING TEXT TO APPEAR IN PRIMARY CARD", 4);
				if (pc1.isCoolingTextVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}

			}
		} else if (expectedScreen.get(1).toUpperCase().equals("MOTION EVENT EMAIL ALERTS EMAIL")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				if (cs.isMotionEmailNotificationVisible(testCase)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " option displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " option not displayed");
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED": {
				if (!cs.isMotionEmailNotificationVisible(testCase)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " option not displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " option displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("DOORS AND WINDOWS")) {
			BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
				if (!cs.isSecurityModeDoorAndWindowVisible()) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " option not displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " option displayed");
				}
				break;
			}
			}
		} else if (expectedScreen.get(1).toUpperCase().equals("COACH MARK")) {
			CoachMarks cm = new CoachMarks(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				String getHeader = cm.getCoachMarkHeaderText();
				if (getHeader.equalsIgnoreCase("Access More Information")) {
					Keyword.ReportStep_Pass(testCase, "Access More Information coachmark is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input");
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED": {
				String getHeader = cm.getCoachMarkHeaderText();
				if (!getHeader.equalsIgnoreCase("Access More Information")) {
					Keyword.ReportStep_Pass(testCase, "Access More Information coachmark is not displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input");
				}

			}
				break;
			}
		} else if (expectedScreen.get(1).equalsIgnoreCase("Vacation")) {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD BE DISPLAYED": {
				PrimaryCard pc1 = new PrimaryCard(testCase);
				if (!pc1.isVacationStatusVisible(30)) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1) + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(1) + " is not displayed");
				}
				break;
			}
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