package com.honeywell.screens;

import java.util.HashMap;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class HoneywellMembershipScreen extends MobileScreens {

	private static final String screenName = "HoneywellMembership";
	private static HashMap<String, MobileObject> fieldObjects;

	public HoneywellMembershipScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean clickOnMonthlyPlan(TestCases testCase, TestCaseInputs inputs) {
		//return MobileUtils.clickOnElement(objectDefinition, testCase, "StartMembership");
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "HoneywellMembership");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "StartMembership")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "StartMembership");
			return true;
		}
		return true;
	}
	
	public boolean clickOnAnnualPlan(TestCases testCase, TestCaseInputs inputs) {
		fieldObjects = MobileUtils.loadObjectFile(testCase, "HoneywellMembership");
		
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AnnualButton")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "AnnualButton");
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "StartMembership")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "StartMembership");
			return true;
		}
		
		return true;
	}
	public boolean clickOnManageMembership(TestCases testCase, TestCaseInputs inputs)
		{
			fieldObjects = MobileUtils.loadObjectFile(testCase, "HoneywellMembership");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ManageMembership")) {
				MobileUtils.clickOnElement(fieldObjects, testCase, "ManageMembership");
				return true;
			}
		return true;
		}
}