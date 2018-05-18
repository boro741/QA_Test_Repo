
package com.honeywell.screens;

import java.util.HashMap;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class MembershipCompleteScreen extends MobileScreens {

	private static final String screenName = "MembershipComplete";
	private static HashMap<String, MobileObject> fieldObjects;

	public MembershipCompleteScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean ClickDoneButton(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipComplete");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Done")) {
			
			boolean flag = true;
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "Done");
			return flag;
		}
		return false;
	}
}