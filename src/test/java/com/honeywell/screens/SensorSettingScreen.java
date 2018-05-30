package com.honeywell.screens;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class SensorSettingScreen extends MobileScreens{

	private static final String screenName = "SensorSettingScreen";
	public SensorSettingScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isTestSensorHeadingDisplayed() {


		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(2, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "TestSensorHeading")) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			return true;				
		}
		return false;
	}
	public boolean isDoorStatusVisible(String status) {
		WebElement ele1=MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorName");
		if(ele1!=null) {
			System.out.println("Entered door status checking func");
			String s1 = ele1.getText();
			System.out.println(s1);
			if(s1.toUpperCase().contains("DOOR")) {
				WebElement ele2=MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorStatus");
				if(ele2!=null) {
					String s2 = ele2.getText();
					System.out.println(s2);
					if(s2.equalsIgnoreCase(status)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean clickOnSensorNotWorking() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorNotWorkingButton");

	}

	public boolean isAccessSensorHelpScreenDisplayed() {
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(2, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorHelpTitle")) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			return true;				
		}
		return false;

	}

	public boolean isGetAdditionalHelpOnSensorHelpDisplayed() {
		try {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "name", "Get Additional Help");
		} catch (Exception e) {

			e.printStackTrace();
		}
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "GetAdditionalHelpButton")) {
			return true;
		}
		return false;
	}

	public boolean clickOnFirmwareDetailsOption() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}
	
	public boolean isModelDetailsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelSubTitle") && MobileUtils.isMobElementExists(objectDefinition, testCase, "VersionDetail");
    }
	
	public boolean isFirmwareDetailsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareSubTitle") && MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareSubTitle");
    }
	public boolean clickOnGetAdditionalHelpButton() {
		try {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "name", "Get Additional Help");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetAdditionalHelpButton");	
	}

	public boolean clickOnTestSignalStrength() {
		try {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "name", "Get Additional Help");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestSignalStrengthButton");

	}
	public boolean isSignalStrengthScreenDisplayed() {
		System.out.println("Entered into signl stren func");
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(1, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthHeading")) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			return true;				
		}
		return false;

	}

	public boolean isSignalStrengthVisible(String string) {

		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "SignalStrengthStatus");
		if(ele!=null) {
			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);

			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if(ele.getText().toUpperCase().contains(string.toUpperCase())){
						return true;
					}else return false;

				}

			});

			if(isEventReceived) {
				return true;				
			}
		}
		return false;

	}

	public boolean clickOnSignalStrengthBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SignalStrengthBackButton");	
	}

	public boolean clickOnAccessSensorHelpBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AccessSensorHelpBack");	
	}

	public boolean clickOnTestSensorBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestSensorBack");	
	}
	
	public boolean clickOnSensorSettingBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorSettingBack");	
	}
	
	public boolean clickOnSensorListSettingBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorListBack");	
	}
	public boolean isSensorTamperedScreenDisplayed() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorTamperScreen") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ClearTamperButton") ) {
			return true;
		}
		return false;
	}
	
	public boolean isSensorTamperClearPopupDisplayed() {
		MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup");
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup")) {
			return true;
		}
		return false;
	}
	
	public boolean clickOnOkTamperClearPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TamperClearPopupOk");	
	}
	
	public boolean clickOnRetryTamperClearPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TamperClearPopupRetry");	
	}
	
	public boolean clickOnSensorCoverTamperOption() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
		if(status.equalsIgnoreCase("Cover Tampered")){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorStatusOptionValue");
		}
		return false;
	}
	public boolean clickOnClearCoverTamperOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ClearTamperButton");
	}
	public boolean clickOnSensorStatusOffOnAccessSensorScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorStatusOptionValue");
	}
	
	public boolean checkSensorNameInSensorOffScreen(TestCaseInputs inputs) {
		String expectedSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		String actualSensorName = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorNameTitleInOffScreen");
			if(actualSensorName.equalsIgnoreCase(expectedSensorName)) {
				System.out.println("Sensor Name is same in Off Screen");
				return true;
			}
	 return false;
	}

	public boolean checkSensorIsOffTextVisible() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorOffText")) {
			return true;
		}
		return false;
	}
}
