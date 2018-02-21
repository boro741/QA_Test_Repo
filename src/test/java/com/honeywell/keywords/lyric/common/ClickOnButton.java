package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class ClickOnButton extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedButton;
	public boolean flag = true;
	private HashMap<String, MobileObject> fieldObjects;

	public ClickOnButton(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedButton) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedButton = expectedButton;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) by clicking on (.*) button$")
	public boolean keywordSteps() throws KeywordException {
		if (expectedButton.get(0).equalsIgnoreCase("deletes DAS device")) {
			switch (expectedButton.get(1).toUpperCase()) {
			case "DELETE": {
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DeleteDASButton");
				break;
			}
			}
		} else if (expectedButton.get(0).equalsIgnoreCase("cancels the set up")) {
			switch (expectedButton.get(1).toUpperCase()) {
			case "CANCEL": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if(dasDIY.isCancelButtonInChooseLocationScreenVisible()) {
					dasDIY.clickOnCancelButtonInChooseLocationScreen();
				}
				break;
			}
			}
		} else if (expectedButton.get(0).equalsIgnoreCase("views cancel setup")) {
			switch (expectedButton.get(1).toUpperCase()) {
			case "BACK ARROW": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if(dasDIY.isBackArrowInRegisterBaseStationVisible()) {
					dasDIY.clickOnBackArrowInRegisterBaseStationScreen();
					if(dasDIY.isCancelPopupVisible()) {
						return flag;
					}
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
