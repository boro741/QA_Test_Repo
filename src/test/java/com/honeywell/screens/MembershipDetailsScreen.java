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
	public boolean flag;
	
	public MembershipDetailsScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean clickOnProceedToCheckout(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipDetails");
		
		flag = (MobileUtils.isMobElementExists(fieldObjects, testCase, "ProceedToCheckout")?
				MobileUtils.clickOnElement(fieldObjects, testCase, "ProceedToCheckout"):
				false);
			
		return flag;
	}
	
	public boolean clickOnUnsubscribe(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCancel");
		
		flag = (MobileUtils.isMobElementExists(fieldObjects, testCase, "Unsubscribe")?
				MobileUtils.clickOnElement(fieldObjects, testCase, "Unsubscribe"):
				false);
			
		return flag;
	}
}