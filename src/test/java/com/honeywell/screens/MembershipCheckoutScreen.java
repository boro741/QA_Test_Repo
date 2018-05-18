package com.honeywell.screens;

import java.util.HashMap;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class MembershipCheckoutScreen extends MobileScreens {

	private static final String screenName = "MembershipCheckout";
	private static HashMap<String, MobileObject> fieldObjects;

	public MembershipCheckoutScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean EnterCheckoutValues(TestCases testCase, TestCaseInputs inputs) {
		
		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCheckout");
		//if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CardNumber")) {
			
			boolean flag = true;
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "CardNumber", "5555 5555 5555 4444");
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "CardHolderName", "new");
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Expiration", "05/23");
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Cvc", "123");
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Zip", "10001");
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "PayNow");
			return flag;
		//}
		//return false;
	}
}