package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;
import java.util.List;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.Dashboard;

public class CreateMultipleLocationsIfNotPresent extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedLocation;
	public boolean flag = true;
	public DataTable data;
	public static List<String> LOCATIONNAMESINDROPDOWNLISTINDASHBOARDSCREEN = new ArrayList<String>();

	public CreateMultipleLocationsIfNotPresent(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedLocation, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedLocation = expectedLocation;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user creates the following \"(.+)\" after login:$")
	public boolean keywordSteps() throws KeywordException {
		Dashboard d = new Dashboard(testCase);
		// List<String> locationNamesInDashboardScreen = new ArrayList<String>();
		if (testCase.isTestSuccessful() || VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.FLAG) {
			if (VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.LISTOFLOCATIONSFORLOGGEDINUSERFROMCHIL
					.size() == data.getSize()) {
				Keyword.ReportStep_Pass(testCase,
						"Number of locations expected to be displayed in Dashboard are: " + data.getSize());
			} else {
				Keyword.ReportStep_Pass(testCase, "Number of locations displayed in Dashboard are: "
						+ VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.LISTOFLOCATIONSFORLOGGEDINUSERFROMCHIL
								.size());
				Keyword.ReportStep_Pass(testCase, "########Start creating the locations########");
				for (int i = 0; i < data.getSize(); i++) {
					String parameter = data.getData(i, "Locations");
					if (VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.LISTOFLOCATIONSFORLOGGEDINUSERFROMCHIL
							.contains(parameter)) {
						Keyword.ReportStep_Pass(testCase,
								"Location : " + parameter + " is present in the list of locations");
					} else {
						Keyword.ReportStep_Pass(testCase, "Location : " + parameter
								+ " is not present in the list of locations. Start creating the location");
						flag &= DashboardUtils.createANewLocation(testCase, inputs, parameter);
					}
				}
			}
			flag &= d.selectLocationDropDownArrow();
			if (d.isLocationListDisplayedWhenClickedOnLocationDropDownArrow()) {
				// locationNamesInDashboardScreen =
				// d.getLocationNamesDisplayedWhenClickedOnLocationDropDownArrow();
				LOCATIONNAMESINDROPDOWNLISTINDASHBOARDSCREEN
						.addAll(d.getLocationNamesDisplayedWhenClickedOnLocationDropDownArrow(data.getSize()));
			}

			if (LOCATIONNAMESINDROPDOWNLISTINDASHBOARDSCREEN.size() == (data.getSize() - 1)) {
				Keyword.ReportStep_Pass(testCase,
						"List of locations count displayed in Dashboard is same as expected locations count: "
								+ data.getSize());
				for (int i = 0; i < data.getSize(); i++) {
					String parameter = data.getData(i, "Locations");
					if (LOCATIONNAMESINDROPDOWNLISTINDASHBOARDSCREEN.contains(parameter)) {
						Keyword.ReportStep_Pass(testCase,
								"Expected Location: " + parameter + " is displayed in Dashboard drop down list");
					} else if (VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN
							.equalsIgnoreCase(parameter)) {
						Keyword.ReportStep_Pass(testCase, "Expected Location: " + parameter
								+ " is displayed as default location in Dashboard screen");
					} else {
						flag = false;
						Keyword.ReportStep_Pass(testCase, "Expected Location: " + parameter
								+ " is not displayed in Dashboard drop down list. Start creating the location.");
					}
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Not all locations are displayed in the Location drop down list in the Dashboard screen");
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,
						"Locations are displayed in Locations drop down list in Dashboard screen");
				Keyword.ReportStep_Pass(testCase, "Close the Locations drop down list");
				flag &= d.closeLocationDropDownList();
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Skipping this step since default selected location is not: "
							+ VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONFROMCHIL);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
