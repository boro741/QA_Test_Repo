package com.honeywell.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

public class SensorSettingScreen extends MobileScreens {

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
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "TestSensorHeading")) {
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			return true;
		}
		return false;
	}

	public boolean isDoorStatusVisible(String status) {
		WebElement ele1 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorName");
		if (ele1 != null) {
			System.out.println("Entered door status checking func");
			String s1 = ele1.getText();
			System.out.println(s1);
			if (s1.toUpperCase().contains("DOOR")) {
				WebElement ele2 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorStatus");
				if (ele2 != null) {
					String s2 = ele2.getText();
					System.out.println(s2);
					if (s2.equalsIgnoreCase(status)) {
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
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorHelpTitle")) {
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			return true;
		}
		return false;

	}

	public boolean isGetAdditionalHelpOnSensorHelpDisplayed() {

		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		try {
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "Get additional help");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Get Additional Help");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GetAdditionalHelpButton")) {
			return true;
		}
		return false;
	}

	public boolean clickOnFirmwareDetailsOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean isModelDetailsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelSubTitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "VersionDetail");
	}

	public boolean verifyBatteryStatusTextOnSensorSettingsScreen() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorSettingBatteryStatusValue");
		return (status.equalsIgnoreCase("Good") || status.equalsIgnoreCase("Low"));
	}

	public boolean isFirmwareDetailsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareSubTitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareSubTitle");
	}

	public boolean clickOnGetAdditionalHelpButton() {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "Get additional help");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Get Additional Help");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetAdditionalHelpButton");
	}

	public boolean clickOnTestSignalStrength() {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "Get additional help");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Get Additional Help");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestSignalStrengthButton");

	}

	public boolean clickOnTestSignalStrengthForMotionSensor() {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "Get additional help");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Get Additional Help");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestMotionSensorSignalStrengthButton");

	}

	public boolean isSignalStrengthScreenDisplayed() {
		System.out.println("Entered into signl stren func");
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(1, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthHeading")) {
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			return true;
		}
		return false;

	}

	public boolean isSignalStrengthVisible(String string) {

		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "SignalStrengthStatus");
		if (ele != null) {
			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);

			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if (ele.getText().toUpperCase().contains(string.toUpperCase())) {
						return true;
					} else
						return false;

				}

			});

			if (isEventReceived) {
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

	public boolean clickOnMotionSensorHelpBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestSensorBack");
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorTamperScreen")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ClearTamperButton")) {
			return true;
		}
		return false;
	}

	public boolean isSensorTamperClearPopupDisplayed() {
		MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup");
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup")) {
			return true;
		}
		return false;
	}

	public boolean clickOnOkTamperClearPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TamperClearPopupOk");
	}

	public boolean clickOnRetryTamperClearPopup() {
		boolean flag = MobileUtils.clickOnElement(objectDefinition, testCase, "TamperClearPopupRetry");
		MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperClearPopupRetry", 60);
		return flag;
	}

	public boolean clickOnSensorCoverTamperOption() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
		if (status.equalsIgnoreCase("Cover Tampered")) {
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

	public boolean checkSensorNameInSensorOffScreen(TestCaseInputs inputs, String expectedSensorName) {
		String actualSensorName = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorNameTitleInOffScreen");
		if (actualSensorName.equalsIgnoreCase(expectedSensorName)) {
			System.out.println("Sensor Name is same in Off Screen");
			return true;
		}
		return false;
	}

	public boolean checkSensorIsOffTextVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorOffText")) {
			return true;
		}
		return false;
	}

	public boolean clickOnAddSensorButton() {

		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorAddButton");
	}

	public boolean clickOnSetUpButton(String SensorType) {
		String locator;
		String SensorName = null;
		if (testCase.getPlatform().contains("IOS")) {
			locator = "value";
		} else {
			locator = "text";
		}
		if (SensorType.toLowerCase().contains("keyfob")) {
			SensorName = "Key Fob";
		} else if (SensorType.toLowerCase().contains("motion sensor")) {
			SensorName = "Motion Sensor";
		} else if (SensorType.toLowerCase().contains("access sensor")) {
			SensorName = "Access Sensor";
		}
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		try {
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				MobileUtils.clickOnElement(testCase, "XPATH", "//*[contains(@" + locator + ",'" + SensorName + "')]");
				if (MobileUtils.isMobElementExists("xpath",
						"//*[contains(@" + locator + ",'" + SensorName
								+ "')]/following-sibling::XCUIElementTypeButton[contains(@name,'rightButton')]",
						testCase, 10)) {
					return MobileUtils.clickOnElement(testCase, "xpath", "//*[contains(@" + locator + ",'" + SensorName
							+ "')]/following-sibling::XCUIElementTypeButton[contains(@name,'rightButton')]");
				} else {
					System.out.println("Unable to locate sensor with setup button");
				}
			} else {
				System.out.println(MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'"
						+ SensorName
						+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up')]",
						testCase, 10));
				if (MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'" + SensorName
						+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up')]",
						testCase, 10)) {
					return MobileUtils.clickOnElement(testCase, "xpath", "//*[contains(@" + locator + ",'" + SensorName
							+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up')]");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate setup button");
					return false;
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate " + e.getMessage(),
					true);
			return false;

		}
		return false;
	}

	public boolean isSensorOverviewScreenDisplayed() {

		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorOverviewHeading")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorHowitWorks");

	}

	public boolean isKEYFOBOverviewScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobOverview")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobOverviewImage");
	}

	public boolean isKeyfobNamingScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobNamingScreen")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobNamingScreenDesc");
	}

	public boolean editKeyfobName(String name) {
		boolean flag = false;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobNamingScreenTextbox")) {
			flag = MobileUtils.setValueToElement(objectDefinition, testCase, "KeyfobNamingScreenTextbox", name);

			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
			} else {
				try {
					MobileUtils.hideKeyboard(testCase.getMobileDriver());
				} catch (Exception e) {
					// Ignoring any exceptions because keyboard is sometimes not displayed on some
					// Android devices.
				}
			}
			return flag;
		}
		return false;
	}

	public boolean clickOnWatchHowToVideoButton() {

		return MobileUtils.clickOnElement(objectDefinition, testCase, "WatchHowToVideo");
	}

	public boolean clickOnGetStartedFromSensorOverview() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetStarted");
	}

	public boolean isLocateSensorScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocateSensorHeading")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "FindTheLit");

	}

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");

	}

	public boolean isPlaceSensorScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceSensorHeading");

	}

	public boolean clickOnWontFitAsShownButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WontFitAsShown");
	}

	public boolean isAccessSensorInstallHelpScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorInstallHelpHeading");

	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");

	}

	public boolean isWindowStatusVisible(String status) {
		WebElement ele1 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorName");
		if (ele1 != null) {
			System.out.println("Entered door status checking func");
			String s1 = ele1.getText();
			System.out.println(s1);
			if (s1.toUpperCase().contains("WINDOW")) {
				WebElement ele2 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorStatus");
				if (ele2 != null) {
					String s2 = ele2.getText();
					System.out.println(s2);
					if (s2.equalsIgnoreCase(status)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean clickOnDoneButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
	}

	public boolean checkSensorNameNotInSensorList(String sensorName) {
		if (testCase.getPlatform().contains("Android")) {
			if (MobileUtils.isMobElementExists("xpath", "//*[@text='" + sensorName + "']", testCase)) {
				return true;
			}
		} else {
			if (MobileUtils.isMobElementExists("xpath", "//*[@name='" + sensorName + "']", testCase)) {
				return true;
			}
		}
		return false;
	}

	public boolean editSensorNameToCustom(String customName, TestCaseInputs inputs) {

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameSensor", customName)) {
				// ((AndroidDriver<MobileElement>)
				// testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.KEYCODE_ENTER);

				MobileUtils.clickOnCoordinate(testCase, 991, 1804);
				// TODO
				if (customName.toUpperCase().contains("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", customName);
				} else if (customName.toUpperCase().contains("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", customName);
				} else if (customName.toUpperCase().contains("HALL")
						|| customName.toUpperCase().contains("LIVING ROOM")) {
					inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", customName);
				}

				return true;
			}
		}

		return false;
	}

	public boolean isMountSensorScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MountSensorHeading");
	}

	public boolean isMotionSensorStatusVisible(String SensorName, String status) {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensor_SensorName")) {
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "MotionSensor_SensorName").toUpperCase()
					.contains(SensorName.toUpperCase())) {
				if (status.toUpperCase().contains("NOT DETECTED")
						|| status.toUpperCase().contains("NO MOTION DETECTED")) {
					if (MobileUtils.isMobElementExists(objectDefinition, testCase,
							"MotionSensor_SensorStatus_NotDetected")) {
						return true;
					} else
						return false;
				}
				if (status.toUpperCase().contains("DETECTED")) {
					if (MobileUtils.isMobElementExists(objectDefinition, testCase,
							"MotionSensor_SensorStatus_Detected")) {
						return true;
					} else
						return false;
				}
			}
		}
		return false;
	}

	public boolean isSensorConfigured(String SensorName, String state) {
		if (state.toUpperCase().equals("ASSIGNED")) {
			state = "Assigned";
		} else if (state.toUpperCase().equals("CONFIGURED")) {
			state = "Configured";
		} else {
			System.out.println("input not handled");
		}
		String locator;
		if (testCase.getPlatform().contains("IOS")) {
			locator = "value";
		} else {
			locator = "text";
		}
		if (testCase.getPlatform().contains("IOS")) {
			return MobileUtils.isMobElementExists("xpath", "//*[@" + locator + "='" + SensorName
					+ "']/following-sibling::XCUIElementTypeStaticText[contains(@" + locator + ",'" + state + "')]",
					testCase, 10);
		} else {
			if (testCase.getPlatform().contains("ANDROID")) {
				return MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'" + SensorName
						+ "')]/following-sibling::android.widget.LinearLayout/android.widget.TextView[contains(@text,'"
						+ state + "')]", testCase, 10);
			}
		}
		return false;
	}

	public boolean performOnlyInHome() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInHomeModePopup")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInHomeModePopupAck");
			return true;
		} else {
			return false;
		}

	}

	/*
	 * public boolean isSensorConfigured(String SensorName) {
	 * 
	 * if(MobileUtils.isMobElementExists(objectDefinition, testCase,
	 * "ConfiguredText",5)) { System.out.println("Found the Configured text");
	 * String displayedSensorName = MobileUtils.getFieldValue(objectDefinition,
	 * testCase, "SensorNameInSetUpAccessories");
	 * if(SensorName.equalsIgnoreCase(displayedSensorName)) {
	 * System.out.println("The given Sensor Name is Configured "+displayedSensorName
	 * ); } } else { try {
	 * LyricUtils.scrollToElementUsingExactAttributeValue(testCase,testCase.
	 * getPlatform().toUpperCase().contains("ANDROID") ? "text" : "name",
	 * "Configured"); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } if(MobileUtils.isMobElementExists(objectDefinition,
	 * testCase, "ConfiguredText",15)) { String displayedSensorName =
	 * MobileUtils.getFieldValue(objectDefinition, testCase,
	 * "SensorNameInSetUpAccessories");
	 * if(SensorName.equalsIgnoreCase(displayedSensorName)) {
	 * System.out.println("The given Sensor Name is Configured "+displayedSensorName
	 * ); } } } return false; }
	 */
}
