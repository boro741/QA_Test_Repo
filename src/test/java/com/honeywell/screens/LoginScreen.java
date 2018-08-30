package com.honeywell.screens;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebElement;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

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
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoginButton",15);
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
				CustomDriver driver = testCase.getMobileDriver();
				TouchAction action = new TouchAction(driver);
				//element = MobileUtils.getMobElement(objectDefinition, testCase, "SecretMenuImage");
				//flag = flag & MobileUtils.longPress(testCase, element, 8000);
				try {
					action.press(element).waitAction(MobileUtils.getDuration(8000)).release().perform();
					if (!MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='CHIL Url:']", testCase,
							1)) {
						Keyword.ReportStep_Pass(testCase, "Successfully navigated to Secret menu");;
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Unable to open the Secret menu");
				}
			}
			return flag;
		}

		public boolean isLyricLogoVisible()
		{
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LyricLogo",3);
		}
		
		public boolean isSkipButtonVisible()
		{
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipIntroButton",3);
		}
		
		public boolean clickOnSkipIntroButton()
		{
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipIntroButton");
		}
		
		public boolean clickOnLyricLogo()
		{
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LyricLogo");
		}
		
}
