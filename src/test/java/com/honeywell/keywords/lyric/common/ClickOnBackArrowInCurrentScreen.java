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
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.ManageUsersScreen;

public class ClickOnBackArrowInCurrentScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;

	public ClickOnBackArrowInCurrentScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedScreen) {
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
	@KeywordStep(gherkins = "^user clicks on the back arrow in the \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "ADD NEW DEVICE DASHBOARD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackArrowInSelectADeviceScreenVisible(5)) {
					flag = flag & dasDIY.clickOnBackArrowInSelectADeviceScreen();
				}
				break;
			}
			case "WHAT TO EXPECT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackArrowInWhatToExpectScreenVisible()) {
					flag = flag & dasDIY.clickOnBackArrowInWhatToExpectScreen();
				}
				break;
			}
			case "CHOOSE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackArrowInChooseLocationScreenVisible()) {
					flag = flag & dasDIY.clickOnBackArrowInChooseLocationScreen();
				}
				break;
			}
			case "CREATE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackButtonInCreateLocationScreenVisible()) {
					flag = flag & dasDIY.clickOnBackButtonInCreateLocationScreen();
				}
				break;
			}
			case "NAME YOUR BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackArrowInNameYourBaseStationScreenVisible()) {
					flag = flag & dasDIY.clickOnBackArrowInNameYourBaseStationScreen();
				}
				break;
			}
			case "POWER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackArrowInPowerBaseStationVisible()) {
					flag = flag & dasDIY.clickOnBackArrowInPowerBaseStationScreen();
				}
				break;
			}
			case "SET UP ACCESSORIES": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBackButtonInSetUpAccessoriesScreenVisible(20)) {
					flag = flag & dasDIY.clickOnBackButtonInSetUpAccessoriesScreen();
				}
				break;
			}
			case "EDIT ADDRESS": {
				AddressScreen ads = new AddressScreen(testCase);
				if (ads.isBackButtonVisible()) {
					flag &= ads.clickOnBackButton();
				}
				break;
			}
			case "EDIT ACCOUNT": {
				EditAccountScreen eas = new EditAccountScreen(testCase);
				if (eas.isBackButtonVisible()) {
					flag &= eas.clickOnBackButton();
				}
				break;
			}
			case "INVITE NEW USER" : {
				ManageUsersScreen mus = new ManageUsersScreen(testCase);
				if(mus.isBackButtonVisible()) {
					flag &=mus.clickOnBackButton();
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
