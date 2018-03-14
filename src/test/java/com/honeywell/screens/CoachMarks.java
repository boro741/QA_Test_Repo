package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class CoachMarks extends MobileScreens {

	private static final String screenName = "CoachMark";

	public CoachMarks(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isGotitButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotItButton", timeOut, false);
	}

	public boolean clickOnGotitButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotItButton");
	}

	public String getCoachMarkHeaderText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "CoachMarkHeader").getText();
	}

	public String getCoachMarkDescription() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "CoachMarkDescription").getText();
	}

	
}
