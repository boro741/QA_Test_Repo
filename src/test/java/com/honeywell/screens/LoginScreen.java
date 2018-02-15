package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class LoginScreen extends MobileScreens{

		private static final String screenName = "LoginScreen";		
		//private OSPopUps osPopUps;
		
		public LoginScreen(TestCases testCase) {
			super(testCase,screenName);
			//osPopUps = new OSPopUps(testCase);
		}
		
		public boolean clickOnLoginButton(){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LoginButton");
		}
		
		public boolean setEmailAddressValue(String value)
		{
			return MobileUtils.setValueToElement(objectDefinition, testCase, "EmailAddress", value);
		}
		
		public boolean setPasswordValue(String value)
		{
			return MobileUtils.setValueToElement(objectDefinition, testCase, "Password", value);
		}
		
		public boolean isLoginButtonVisible()
		{
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoginButton",3);
		}
		
		public boolean isEmailAddressTextFieldVisible()
		{
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailAddress",3);
		}

}
