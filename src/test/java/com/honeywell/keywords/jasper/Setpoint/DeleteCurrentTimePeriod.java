package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.AdhocScreen;

public class DeleteCurrentTimePeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public DeleteCurrentTimePeriod(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user deletes the \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		if (exampleData.get(0).equalsIgnoreCase("SLEEP")) {
			AdhocScreen adHoc = new AdhocScreen(testCase);
			if(adHoc.isSchedulingVisibleInSolutionsCardScreen()) {
				adHoc.clickOnSchedulingVisibleInSolutionsCardScreen();
				if(adHoc.isCreateGeofenceSleepSettingsVisible()) {
					adHoc.clickOnCreateGeofenceSleepSettings();
					if(adHoc.isDeleteButtonVisible()) {
						adHoc.clickOnDeleteButton();
						if(adHoc.isDeletePopupVisible() && adHoc.isDeleteButtonVisibleInDeletePopup()) {
							adHoc.clickOnDeleteButtonInDeletePopup();
							if(adHoc.isCloseIconVisibleInScheduleScreen()) {
								adHoc.clickOnCloseIconInScheduleScreen();
							}
						}
					}
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

