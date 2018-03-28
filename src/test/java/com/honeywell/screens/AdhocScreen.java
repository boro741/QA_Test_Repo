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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AdHocStatus", 3, false);
	}

	public String getAdhocStatusElement() {
		String adHocStatus;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			adHocStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus")
					.getAttribute("text");
		} else {
			adHocStatus = MobileUtils.getMobElement(objectDefinition, testCase, "AdHocStatus")
					.getAttribute("label");
		}
		return adHocStatus;
	}

}
