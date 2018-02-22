package com.honeywell.screens;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class LoginScreen extends MobileScreens{

		private static final String screenName = "LoginScreen";
		
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
		
		public boolean longPressOnSecretMenuImage()
		{
			boolean flag = true;
			WebElement element = null;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				element = MobileUtils.getMobElement(objectDefinition, testCase, "HoneywellRosette");
				flag = flag & MobileUtils.longPress(testCase, element, 8000);
			} else {
				element = MobileUtils.getMobElement(objectDefinition, testCase, "SecretMenuImage");
				flag = flag & MobileUtils.longPress(testCase, element, 8000);
			}
			return flag;
		}

		public boolean isLyricLogoVisible()
		{
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LyricLogo",3);
		}
		
}
