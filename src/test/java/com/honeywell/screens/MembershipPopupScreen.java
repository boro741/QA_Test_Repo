package com.honeywell.screens;

import java.util.HashMap;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class MembershipPopupScreen extends MobileScreens {

	private static final String screenName = "MembershipPopup";
	private static HashMap<String, MobileObject> fieldObjects;

	public MembershipPopupScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean clickOnPopupDone(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCancel");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Done")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "Done");
			return true;
		}
		return false;
	}
	
	public boolean clickOnPopupOk(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCancel");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Ok")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "Ok");
			return true;
		}
		return false;
	}
}
