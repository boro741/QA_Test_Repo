package com.honeywell.keywords.lyric.das.alarm;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASSensorUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.SecondaryCardSettings;

public class NavigateFromAlarmInVariousScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public DataTable screen;
	public boolean flag = true;

	public NavigateFromAlarmInVariousScreen(TestCases testCase, TestCaseInputs inputs, DataTable screen) {
		this.testCase = testCase;
		this.screen = screen;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user can navigate from the alarm in following screen:$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);

			for (int i = 0; i < screen.getSize(); i++) {
				switch (screen.getData(i, "Elements").toUpperCase()) {
				case "GEOFENCE SETTING": {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.GEOFENCE)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "GEOFENCE RADIUS":{
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.GEOFENCE)) {
							GeofenceSettings geoScreen = new GeofenceSettings(testCase);
							if (!geoScreen.selectOptionFromGeofenceSettings(GeofenceSettings.GEOFENCERADIUS)) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not click on "+screen.getData(i, "Elements").toUpperCase());
							}
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on GEOFENCE From SecondarySettings");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "ADD USERS":{
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.USERS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add user menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "LOCATION DETAILS":{
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDRESSDETAILS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Address menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "EDIT ACCOUNT":{
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ACCOUNTDETAILS)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on ACCOUNT DETAILS menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				case "ABOUT THE APP":{
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ABOUTTHEAPP)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on ABOUT THE APP menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.getData(i, "Elements").toUpperCase());
				}
				}

				if(flag){
					flag = flag & DASSensorUtils.openWindow(testCase, inputs,"WINDOW");
					if (DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)){
						Keyword.ReportStep_Pass(testCase,
								"Successfully displayed with Alarm screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Not in expected screen - Alarm");
					}

					flag = flag & DASAlarmUtils.clickOnAlarm_NavigateBack(testCase);

					switch (screen.getData(i, "Elements").toUpperCase()) {
					case "GEOFENCE SETTING": {
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						break;
					}
					case "GEOFENCE RADIUS":{
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						break;
					}
					case "ADD USERS":{
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						break;
					}
					case "LOCATION DETAILS":{
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						break;
					}
					case "EDIT ACCOUNT":{
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.getData(i, "Screen").toUpperCase());
					}
					}
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					flag= flag & DASAlarmUtils.clickOnDismissAlarm(testCase, inputs);
					AlarmScreen alarmScreen = new AlarmScreen(testCase);
					int j=0;
					while(j<3 && DASAlarmUtils.verifyProgressDisplayed(testCase) && alarmScreen.isAlarmDismissButtonDisplayed() ){
						System.out.println("Waiting for dismiss alarm request to complete");
						j++;
					}
					flag = flag & DASSensorUtils.closeWindow(testCase, inputs,"WINDOW");
					if (chUtil.getConnection()) {
						flag = flag & (chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"Away", testCase)==202 ? true:false);
					}

				}

				testCase.getMobileDriver().closeApp();
				testCase.getMobileDriver().launchApp();
				flag=true;
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
