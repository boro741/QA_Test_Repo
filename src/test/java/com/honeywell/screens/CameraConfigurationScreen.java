package com.honeywell.screens;

import org.openqa.selenium.WebElement;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class CameraConfigurationScreen extends MobileScreens {
	
	private static final String screenName = "CameraConfiguration";
	
	public CameraConfigurationScreen(TestCases testCase) {
		super(testCase, screenName);
		this.testCase = testCase;
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean EnterCameraName(TestCases testCase, TestCaseInputs inputs, String  cameraName) {
		boolean flag = true;
		WebElement CameraName = MobileUtils.getMobElement(objectDefinition, testCase, "CameraNameTextBox");
		CameraName.click();
		CameraName.clear();
		CameraName.sendKeys(cameraName);
		
		MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		
		return flag;
	}
	
	public boolean DeleteCamera(TestCases testCase, TestCaseInputs inputs) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteCameraButton");
	}
	
	public boolean AcceptDeleteCameraPopup(TestCases testCase, TestCaseInputs inputs) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton");
	}
	
	public boolean CancelDeleteCameraPopup(TestCases testCase, TestCaseInputs inputs) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
	
}

