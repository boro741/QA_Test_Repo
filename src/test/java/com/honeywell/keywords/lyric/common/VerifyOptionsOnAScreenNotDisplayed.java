package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.ManageUsersScreen;

public class VerifyOptionsOnAScreenNotDisplayed extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenNotDisplayed(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedScreen, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with the following \"(.*)\" options:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "DETERRENCE SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "SELECT CHIME": {
					flag &= !bs.isSelectChimeVisible();
					break;
				}
				case "PLAY DOG BARK SOUND": {
					flag &= !bs.isPlayDogBarkSoundVisible();
					break;
				}
				case "PARTY IS ON": {
					flag &= !bs.isPartyIsOnVisible();
					break;
				}
				case "VACUUM": {
					flag &= !bs.isVacuumVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " is not displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " is displayed");
				}
				flag = true;
			}
			break;
		}
		case "GEOFENCE THIS LOCATION": {
			boolean flag = true;
			GeofenceSettings gs = new GeofenceSettings(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GeofenceThisLocation");
				switch (parameter.toUpperCase()) {
				case "GEOFENCE RADIUS": {
					flag &= gs.isGeofenceRadiusOptionVisible();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					}
					break;
				}
				case "LOCATION STATUS": {
					flag &= gs.isLocationStatusOptionVisible();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					}
					break;
				}
				case "GEOFENCE ALERT": {
					flag &= gs.isGeofenceAlertOptionVisible();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ACTIVITY HISTORY": {
			boolean flag = true;
			ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "ActivityHistoryOptions");
				switch (parameter.toUpperCase()) {
				case "EDIT": {
					flag &= ah.isEditButtonVisible();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					}
					break;
				}
				case "SELECT ALL": {
					flag &= ah.isSelectAllButtonVisible();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option: " + parameter + " is not displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option: " + parameter + " is displayed");
					}
					break;
				}
				case "DELETE": {
					flag &= ah.isDeletelButtonEnabled();
					if (!flag) {
						Keyword.ReportStep_Pass(testCase, "Option: " + parameter + " is not displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option: " + parameter + " is displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "INVITED USERS": {
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			String parameter = data.getData(0, "InvitedUsersList");
			if (mus.isNoInvitedUsersLabelVisible()) {
				Keyword.ReportStep_Pass(testCase, "There are no Invited Users.");
			} else {
				Keyword.ReportStep_Pass(testCase, "Invited users are present.");
				if (parameter.equalsIgnoreCase("LOGGED IN USER")) {
					if (!mus.isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(
							inputs.getInputValue("USERID"))) {
						Keyword.ReportStep_Pass(testCase, "Logged in user: " + inputs.getInputValue("USERID")
								+ " is not present in the list of Invited Users.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Logged in user: "
								+ inputs.getInputValue("USERID") + " is present in the list of Invited Users.");
					}
				} else {
					if (!mus.isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(parameter)) {
						Keyword.ReportStep_Pass(testCase,
								"User: " + parameter + " is not present in the list of Invited Users.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "User: "
								+ inputs.getInputValue("USERID") + " is present in the list of Invited Users.");
					}
				}
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
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
