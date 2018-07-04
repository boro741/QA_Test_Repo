package com.honeywell.keywords.lyric.das.diyregistration;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;

public class VerifyIfUserIsInCurrentScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;

	public VerifyIfUserIsInCurrentScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user navigates to the (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "WHAT TO EXPECT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				Dashboard ds = new Dashboard(testCase);
				if (ds.isAddDeviceIconVisible(20)) {
					flag = flag & ds.clickOnAddNewDeviceIcon();
				} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
					flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
				}
				flag = flag & dasDIY.selectDeviceToInstall("Smart Home Security");
				break;
			}
			case "CHOOSE LOCATION": {
				Dashboard ds = new Dashboard(testCase);
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (ds.isAddDeviceIconVisible(20)) {
					flag = flag & ds.clickOnAddNewDeviceIcon();
				} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
					flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
				}
				flag = flag & dasDIY.selectDeviceToInstall("Smart Home Security");
				if (dasDIY.isNextButtonVisible()) {
					flag = flag & dasDIY.clickOnNextButton();
				}
				break;
			}
			case "CREATE LOCATION": {
				Dashboard ds = new Dashboard(testCase);
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (ds.isAddDeviceIconVisible(20)) {
					flag = flag & ds.clickOnAddNewDeviceIcon();
				} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
					flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
				}
				flag = flag & dasDIY.selectDeviceToInstall("Smart Home Security");
				if (dasDIY.isNextButtonVisible()) {
					flag = flag & dasDIY.clickOnNextButton();
					if (dasDIY.isCreateNewLocationLinkVisible()) {
						flag = flag & dasDIY.clickOnCreateNewLocationLink();
					}
				}
				break;
			}
			case "NAME YOUR BASE STATION": {
				Dashboard ds = new Dashboard(testCase);
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (ds.isAddDeviceIconVisible(20)) {
					flag = flag & ds.clickOnAddNewDeviceIcon();
				} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
					flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
				}
				flag = flag & dasDIY.selectDeviceToInstall("Smart Home Security");
				if (dasDIY.isNextButtonVisible()) {
					flag = flag & dasDIY.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvaiableLocation(testCase, "Home");
				}
				break;
			}
			case "POWER BASE STATION": {
				Dashboard ds = new Dashboard(testCase);
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (ds.isAddDeviceIconVisible(20)) {
					flag = flag & ds.clickOnAddNewDeviceIcon();
				} else if (ds.isAddDeviceIconBelowExistingDASDeviceVisible(10)) {
					flag = flag & ds.clickOnAddDeviceIconBelowExistingDASDevice();
				}
				flag = flag & dasDIY.selectDeviceToInstall("Smart Home Security");
				if (dasDIY.isNextButtonVisible()) {
					flag = flag & dasDIY.clickOnNextButton();
					flag = flag & DIYRegistrationUtils.selectAvaiableLocation(testCase, "Home");
					if (dasDIY.isNameYourBaseStationHeaderTitleVisible()) {
						flag = flag & DIYRegistrationUtils.selectAvaiableBaseStationName(testCase, "Living Room");
					}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input " + expectedScreen.get(0));
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		if (flag) {
			Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) + " displayed");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(0) + "not displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
