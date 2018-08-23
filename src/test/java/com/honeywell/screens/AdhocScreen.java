package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class AdhocScreen extends MobileScreens {

	private static final String screenName = "AdHocOverride";

	public AdhocScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAdhocStatusVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AdHocStatus");
	}

	public String getAdhocStatusElement() {
		String adHocStatus;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			adHocStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus").getAttribute("text");
		} else {
			adHocStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus").getAttribute("label");
		}
		return adHocStatus;
	}

	public boolean clickOnResumeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ResumeScheduleButton");
	}

	public boolean clickOnAdhocStatusButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AdHocStatus");
	}

	public boolean clickOnPemanentlyHoldButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PemanentlyHoldButton");
	}

	public boolean clickOnHoldUntilButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HoldUntilButton");
	}

	public boolean clickOnRemoveHoldButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RemoveHold");
	}

	public boolean isVacationStatusInSolutionCardVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AdHocStatus");
	}

	public String getVacationStatusInSolutionsCardScreen() {
		String vacationStatus;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			vacationStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus").getAttribute("text");
		} else {
			vacationStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus").getAttribute("label");
		}
		return vacationStatus;
	}

	public boolean clickOnVacationStatusInSolutionCardScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AdHocStatus");
	}

	public boolean clickOnEndVacationOptionInSolutionCardScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndVacationOptionInSolutionCardScreen");
	}

	public boolean isSystemIsOffLabelInSolutionsCardScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemIsOffLabelInSolutionsCardScreen");
	}
	public boolean isPermanentlyVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PemanentlyHoldButton");
	}

	public boolean isSpecificHoldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HoldUntilButton");
	}
	
	public boolean isRemoveAdhocVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveHold");
	}

	public boolean isAdhocCancelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AhocCancel");
	}
	
	public boolean clickOnAdhocCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AdhocCancel");
	}



}