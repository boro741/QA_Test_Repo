package com.honeywell.screens;

import java.util.HashMap;

import org.openqa.selenium.WebElement;

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

	public boolean EnterValidCheckoutValues(TestCases testCase, TestCaseInputs inputs) {

		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCheckout");
		boolean flag = true;

		WebElement Expdate = MobileUtils.getMobElement(objectDefinition, testCase, "CardExpirationField");
		Expdate.click();
		Expdate.sendKeys("0924");

		WebElement CVC = MobileUtils.getMobElement(objectDefinition, testCase, "CVCField");
		CVC.click();
		CVC.sendKeys("123");

		WebElement zipcode = MobileUtils.getMobElement(objectDefinition, testCase, "ZipcodeField");
		zipcode.click();
		zipcode.sendKeys("30308");

		MobileUtils.hideKeyboard(testCase.getMobileDriver());

		WebElement cardNumber = MobileUtils.getMobElement(objectDefinition, testCase, "CardNumberField");
		cardNumber.click();
		cardNumber.sendKeys("5555555555554444");

		MobileUtils.hideKeyboard(testCase.getMobileDriver());

		WebElement Name = MobileUtils.getMobElement(objectDefinition, testCase, "CardHolderNameField");
		Name.click();
		Name.sendKeys("Test Card");

		MobileUtils.hideKeyboard(testCase.getMobileDriver());

		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "PayNow");
		return flag;
	}

	public boolean EnterInValidCheckoutValues(TestCases testCase, TestCaseInputs inputs) {

		fieldObjects = MobileUtils.loadObjectFile(testCase, "MembershipCheckout");
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "CardNumber", "4000 0000 0000 0069");
		flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "CardHolderName", "new");
		flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Expiration", "05/23");
		flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Cvc", "123");
		flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "Zip", "10001");
		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "PayNow");
		return flag;
	}
}