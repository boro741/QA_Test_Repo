package com.honeywell.screens;

import java.util.HashMap;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class MembershipDetailsScreen extends MobileScreens {

	private static final String screenName = "MembershipDetails";
	private static HashMap<String, MobileObject> fieldObjects;

	public MembershipDetailsScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean clickOnProceedToCheckout(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipDetails");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ProceedToCheckout")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "ProceedToCheckout");
			return true;
		}
		return false;
	}
	
	public boolean clickOnUnsubscribe(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCancel");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Unsubscribe")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "Unsubscribe");
			return true;
		}
		return false;
	}
}