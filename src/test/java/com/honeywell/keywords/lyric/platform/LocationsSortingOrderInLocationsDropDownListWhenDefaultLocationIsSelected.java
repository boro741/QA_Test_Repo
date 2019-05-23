package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;

public class LocationsSortingOrderInLocationsDropDownListWhenDefaultLocationIsSelected extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> correctSortingOrderForExpectedLocations;
	public boolean flag = true;
	public DataTable data;

	public LocationsSortingOrderInLocationsDropDownListWhenDefaultLocationIsSelected(TestCases testCase,
			TestCaseInputs inputs, ArrayList<String> correctSortingOrderForExpectedLocations, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.correctSortingOrderForExpectedLocations = correctSortingOrderForExpectedLocations;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^list of \"(.+)\" should be sorted in the following alphanumeric order when default location is selected:$")
	public boolean keywordSteps() throws KeywordException {
		Dashboard d = new Dashboard(testCase);
		if (testCase.isTestSuccessful() || VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.FLAG) {

			for (int i = 0; i < data.getSize(); i++) {
				correctSortingOrderForExpectedLocations.add(data.getData(i, "Locations"));
				if (correctSortingOrderForExpectedLocations.contains("Locations")) {
					correctSortingOrderForExpectedLocations.remove("Locations");
				}
			}

			for (int i = 0; i < correctSortingOrderForExpectedLocations.size(); i++) {
				if (correctSortingOrderForExpectedLocations.get(i).equals(
						VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN)) {
					Keyword.ReportStep_Pass(testCase, "Expected locations list contains the location: "
							+ correctSortingOrderForExpectedLocations.get(i)
							+ " displayed as default location in the Dashboard screen. Ignoring it from the locations list sorting order.");
					correctSortingOrderForExpectedLocations.remove(correctSortingOrderForExpectedLocations.get(i));
				} else if ((!inputs.getInputValue("SELECTED_LOCATION_IN_DASHBOARD").isEmpty()
						&& inputs.getInputValue("SELECTED_LOCATION_IN_DASHBOARD") != null)
						&& (correctSortingOrderForExpectedLocations.get(i)
								.equals(inputs.getInputValue("SELECTED_LOCATION_IN_DASHBOARD")))) {
					Keyword.ReportStep_Pass(testCase,
							"Expected locations list contains the location displayed in the Dashboard screen. Removing it from the list");
					correctSortingOrderForExpectedLocations.remove(correctSortingOrderForExpectedLocations.get(i));
				}
			}

			for (int i = 0; i < correctSortingOrderForExpectedLocations.size(); i++) {
				if (VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN
						.equals(correctSortingOrderForExpectedLocations.get(i))) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Location: " + correctSortingOrderForExpectedLocations.get(i)
									+ " is displayed as default Location in Dashboard Screen");
				} else if (CreateMultipleLocationsIfNotPresent.LOCATIONNAMESINDROPDOWNLISTINDASHBOARDSCREEN.get(i)
						.equals(correctSortingOrderForExpectedLocations.get(i))) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Location: " + correctSortingOrderForExpectedLocations.get(i)
									+ " is displayed in Dashboard drop down list at position: " + i);
				} else {
					flag = false;
					Keyword.ReportStep_Pass(testCase,
							"Expected Location: " + correctSortingOrderForExpectedLocations.get(i)
									+ " is not displayed in Dashboard drop down list at position: " + i);
				}
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				Keyword.ReportStep_Pass(testCase, "Scroll top in the Locations dropdown list");
				Dimension dimensions = d.getLocationListViewInDashboardScreenScrollableEleInAndroid().getSize();
				int startx = dimensions.width;
				int starty = (dimensions.height * 75) / 100;
				int endx = dimensions.width;
				int endy = (dimensions.height * 25) / 100;
				testCase.getMobileDriver().swipe(startx, endy, endx, starty, 1000);
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
