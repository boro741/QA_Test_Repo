package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.screens.SchedulingScreen;

public class DeleteParticularTimePeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public DeleteParticularTimePeriod(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user deletes the \"(.+)\" time period$")
	public boolean keywordSteps() throws KeywordException {
		String locator = null;
		if (exampleData.get(0).equalsIgnoreCase("Home")) {
			 locator="//*[@content-desc='Home_Everyday']";
		}else if (exampleData.get(0).equalsIgnoreCase("Wake")) {
			 locator="//*[@content-desc='Wake_Everyday']";
		}else if (exampleData.get(0).equalsIgnoreCase("Sleep")) {
			 locator="//*[@content-desc='Sleep_Everyday']";
		}else if (exampleData.get(0).equalsIgnoreCase("Away")) {
			 locator="//*[@content-desc='Away_Everyday']";
		}
			SchedulingScreen ss = new SchedulingScreen(testCase);

			if (ss.isTimeScheduleButtonVisible()) {
				ss.clickOnTimeScheduleButton();
				MobileUtils.clickOnElement(testCase, "Xpath",locator );
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
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
