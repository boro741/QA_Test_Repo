package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.SchedulingScreen;

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
			SchedulingScreen ss = new SchedulingScreen(testCase);

			if (ss.isTimeScheduleButtonVisible()) {
				ss.clickOnTimeScheduleButton();
				if (ss.isCreateGeofenceSleepSettingsVisible()) {
					ss.clickOnCreateGeofenceSleepSettings();
					if (ss.isPeriodDeleteIconVisible(10)) {
						ss.clickOnPeriodDeleteIcon();
						if (ss.isDeletePopupVisible() && ss.isDeletePopupVisible()
								&& ss.isConfirmDeleteButtonVisible(1)) {
							ss.clickOnConfirmDeleteButton();
							if (ss.isCloseButtonVisible(5)) {
								ss.clickOnCloseButton();
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
