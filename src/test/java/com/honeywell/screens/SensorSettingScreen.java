package com.honeywell.screens;

import java.time.Duration;
import java.util.List;
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
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.relayutils.RelayConstants;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class SensorSettingScreen extends MobileScreens {

	private static final String screenName = "SensorSettingScreen";

	public SensorSettingScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isSensorsScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorsScreenTitle");
	}


	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean clickOnUserGivenSensorName(String givenSensorName) {
		String actualSensorName = null;
		List<WebElement> sensorList;
		/*
		 * if (testCase.getPlatform().contains("IOS")) { sensorList =
		 * MobileUtils.getMobElements(testCase, "xpath", "//XCUIElementTypeStaticText");
		 * } else {
		 */
		sensorList = MobileUtils.getMobElements(objectDefinition, testCase, "SensorName");
		// }

		for (WebElement sensor : sensorList) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				actualSensorName = sensor.getText();
			} else {
				actualSensorName = sensor.getAttribute("value");
				System.out.println("#####actualSensorName: " + actualSensorName);
			}
			if (givenSensorName.equalsIgnoreCase(actualSensorName)) {
				sensor.click();
				Keyword.ReportStep_Pass(testCase, "Clicked on " + givenSensorName);
				return true;
			}
		}
		Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
				"Failed to locate sensor named " + givenSensorName);
		return false;

	}

	public boolean isGivenSensorNameDisplayed(String givenSensorName) {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> sensorList;
		if (testCase.getPlatform().contains("IOS")) {
			sensorList = MobileUtils.getMobElements(testCase, "xpath", "//XCUIElementTypeStaticText");
		} else {
			sensorList = MobileUtils.getMobElements(objectDefinition, testCase, "SensorName");
		}

		for (WebElement sensor : sensorList) {
			String actualSensorName = sensor.getText();
			if (givenSensorName.equalsIgnoreCase(actualSensorName)) {

				return true;
			}
		}
		return false;
	}

	public boolean assigningDuplicateSensorName(TestCaseInputs inputs) {
		List<WebElement> sensorList;
		if (testCase.getPlatform().contains("IOS")) {
			sensorList = MobileUtils.getMobElements(testCase, "xpath", "//XCUIElementTypeStaticText");
		} else {
			sensorList = MobileUtils.getMobElements(objectDefinition, testCase, "SensorName");
		}
		String firstAccessSensorName = "";
		int count = 0;

		for (WebElement sensor : sensorList) {
			inputs.setInputValue("SECOND_SENSORNAME", sensor.getText());
			if (count == 1) {
				sensor.click();
				break;
			}
			firstAccessSensorName = sensor.getText();
			count++;
		}
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.DuplicateSensorName(firstAccessSensorName)) {
			return true;
		}
		return false;
	}

	public boolean isTestSensorHeadingDisplayed() {
		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 5);
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(2, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(2));
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

	public boolean isTestMotionSensorHeadingDisplayed() {
		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(2, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(2));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "TestMotionViewerHeading")) {
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

	public boolean clickOnSensorNotWorking() {
		//		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
		//		MobileUtils.clickOnElement(objectDefinition, testCase, "SensorNotWorkingButton");
		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorNotWorkingButton");
	}

	public boolean isMotionSensorHelpScreenDisplayed() {
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(2, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(2));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorHelpTitle")) {
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

	public boolean isAccessSensorHelpScreenDisplayed() {
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(2, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(2));
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
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		try {
			/*
			 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
			 * (dimension.getHeight() * .6)) .release().perform();
			 */
			action.press(point(10, (int) (dimension.getHeight() * .9)))
			.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
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
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SerialDetail");
	}

	public boolean verifyBatteryStatusTextOnSensorSettingsScreen() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorSettingBatteryStatusValue");
		return (status.equalsIgnoreCase("Good") || status.equalsIgnoreCase("Low"));
	}

	public boolean isSensorListScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorAddButton");
	}

	public boolean isFirmwareDetailsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareSubTitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "VersionDetail");
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
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "GetAdditionalHelpButton");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetAdditionalHelpButton");
		}
	}

	public boolean clickOnTestSignalStrength() {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "Get additional help");
			} else {
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
				/*
				 * touchAction.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
				 * (dimension.getHeight() * .6)) .release().perform();
				 */
				touchAction.press(point(10, (int) (dimension.getHeight() * .9)))
				.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
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
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		if (dasDIY.isOutOfRangePopupInSignalStrengthScreenVisible()) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Out of Range Popup is displayed in Signal Strength Screen");
			if (dasDIY.isOKButtonInOutOfRangePopupVisible()) {
				dasDIY.clickOnOKButtonInOutOfRangePopup();
			}
			return false;
		}
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(1, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(1));
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
			// fWait.pollingEvery(5, TimeUnit.SECONDS);
			// fWait.withTimeout(2, TimeUnit.MINUTES);
			fWait.pollingEvery(Duration.ofSeconds(5));
			fWait.withTimeout(Duration.ofMinutes(2));
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
		DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TestSensorBack");
	}

	public boolean clickOnCustomNameBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CustomNameBack");
	}

	public boolean clickOnSensorSettingBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorSettingBack");
	}

	public boolean clickOnSensorListSettingBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorListBack");
	}

	public boolean isSensorTamperedScreenDisplayed() {
		boolean flag = false;
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorTamperScreen")
					&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ClearTamperButton")) {
				flag = true;
			}
		}else if (testCase.getMobileDriver().findElementByXPath("//*[contains(@name,'RightButton')]") != null
				) {

			flag = true;
		}
		return flag;
	}

	public boolean isSensorTamperClearPopupDisplayed(int timeOut) {
		boolean flag = true;
		MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup", timeOut);
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "TamperNotClearedPopup")) {
			return flag;
		}
		return flag;
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
		} else {
			return false;
		}
	}

	public boolean isClearTamperOptionVisible() {
		boolean flag = true;
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
		if (status.equalsIgnoreCase("Cover Tampered")) {
			Keyword.ReportStep_Pass(testCase, "Cover Tampered Text Found");
			flag = true;
		} else {
			flag = false;
		}
		return flag;
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorAddButton", 5)) {
			Keyword.ReportStep_Pass(testCase, "Located Add button");
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorAddButton");
	}

	public boolean clickOnSetUpButton(TestCaseInputs inputs, String SensorType) {
		String locator;
		String SensorName = null;
		String serialNo = null;
		boolean flag = true;
		if (testCase.getPlatform().contains("IOS")) {
			locator = "value";
		} else {
			locator = "text";
		}
		switch(SensorType.toLowerCase()){
		case "keyfob":{
			SensorName = "Key Fob";
			serialNo = RelayConstants.RSI_Keyfob_1_SerialNO;
			System.out.println("###########Keyfob Serial No: " + serialNo);
			break;
		}case "motion sensor":{
			SensorName = "Motion Sensor";
			serialNo = RelayConstants.RSI_Motion_Sensor_1_SerialNO;
			inputs.setInputValue(DASInputVariables.MOTIONSENSORTYPE, DASInputVariables.MOTIONSENSOR);
			System.out.println("###########Motion Sensor Serial No: " + serialNo);
			break;
		}case "door access sensor":{
			serialNo = RelayConstants.RSI_Contact_Sensor_2_SerialNO;
			SensorName = "Access Sensor";
			System.out.println("###########Door Access Sensor Serial No: " + serialNo);
			break;
		}case "window access sensor":{
			serialNo = RelayConstants.RSI_Contact_Sensor_1_SerialNO;
			SensorName = "Access Sensor";
			System.out.println("###########Window Sensor Serial No: " + serialNo);
			break;
		}case "ismv":{
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				SensorName = "Indoor Motion Viewer";
			} else {
				SensorName = "Indoor Motion Viewers";
			}
			serialNo = RelayConstants.RSI_ISMV_Motion_Sensor_1_SerialNO;
			inputs.setInputValue(DASInputVariables.ISMVMOTIONSENSORTYPE, DASInputVariables.ISMVMOTIONSENSOR);
			System.out.println("###########ISMV Serial No: " + serialNo);
			break;
		}case "osmv":{
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				SensorName = "Outdoor Motion Viewer";
			} else {
				SensorName = "Outdoor Motion Viewers";
			}
			serialNo = RelayConstants.RSI_OSMV_Motion_Sensor_1_SerialNO;
			inputs.setInputValue(DASInputVariables.OSMVMOTIONSENSORTYPE, DASInputVariables.OSMVMOTIONSENSOR);
			System.out.println("###########OSMV Serial No: " + serialNo);
			break;
		}case "combo setup":{
			serialNo = RelayConstants.RFS_COMBO_SENSOR_1_SerialNO;
			System.out.println("###########COMBO Serial No: " + serialNo);
				SensorName = "Smoke/CO Detector";
			break;
		}
		case "rfsix door access sensor setup button":{
			serialNo = RelayConstants.RFS_DOOR_SENSOR_1_SerialNO;
			SensorName = "Access sensor";
			System.out.println("###########RF Door Access Sensor Serial No: " + serialNo);
			break;
		}case "rfsix window access sensor setup button":{
			serialNo = RelayConstants.RFS_WINDOW_SENSOR_1_SerialNO;
			SensorName = "Access sensor";
			System.out.println("###########RF Window Sensor Serial No: " + serialNo);
			break;
		}default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
		}
		}

		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		try {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {

				int counter = 0;
				if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeTable", testCase)) {
					while ((MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeTable", testCase))
							&& counter < 10) {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimension.getHeight() * .9)) .moveTo(0, -(int) (dimension.getHeight() *
						 * .6)).release().perform();
						 */
						if (MobileUtils.isMobElementExists("xpath", "//*[contains(@name,'more accessories')]", testCase)){
							MobileUtils.clickOnElement(testCase, "xpath", "//*[contains(@name,'more accessories')]");
						}
						try {
							TimeUnit.SECONDS.sleep(3);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(counter);
						Keyword.ReportStep_Pass(testCase, "Count to scroll " + counter);
						if (MobileUtils.isMobElementExists("xpath",
								"//*[contains(@" + locator + ",'" + SensorName.toUpperCase()
								+ "')]/following-sibling::XCUIElementTypeStaticText[contains(@value,'"
								+ serialNo + "')]",
								testCase, 20)) {
							MobileUtils.clickOnElement(testCase, "xpath",
									"//*[contains(@" + locator + ",'" + SensorName.toUpperCase() + "')]");
							Keyword.ReportStep_Pass(testCase, "Clicked on sensor");
							if (MobileUtils.clickOnElement(testCase, "xpath",
									"//*[contains(@" + locator + ",'" + SensorName.toUpperCase()
									+ "')]/following-sibling::XCUIElementTypeStaticText[contains(@value,'"
									+ serialNo + "')]")) {
								System.out.println("located sensor with serial number");
								if (MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'"
										+ SensorName.toUpperCase()
										+ "')]/following-sibling::XCUIElementTypeStaticText[contains(@value,'"
										+ serialNo
										+ "')]/following-sibling::XCUIElementTypeButton[contains(@name,'SetupAccessories_2_0_rightButton')]",
										testCase, 10)) {

									MobileUtils.clickOnElement(testCase, "xpath", "//*[contains(@" + locator + ",'"
											+ SensorName.toUpperCase()
											+ "')]/following-sibling::XCUIElementTypeStaticText[contains(@value,'"
											+ serialNo
											+ "')]/following-sibling::XCUIElementTypeButton[contains(@name,'SetupAccessories_2_0_rightButton')]");
									Keyword.ReportStep_Pass(testCase, "Clicked on set up button");
									System.out.println("Clicked on set up button");
								}
							} else {
								System.out.println("not able to locate sensor with serial number");
							}
						} else {
							System.out.println("not able to locate sensor with serial number");
						}

						counter++;
					}
				} else {
					System.out.println("Unable to locate sensor with setup button");
				}
			} else {
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				int startx = (dimensions.width * 20) / 100;
				int starty = (dimensions.height * 62) / 100;
				int endx = (dimensions.width * 22) / 100;
				int endy = (dimensions.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				System.out.println(MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'"
						+ SensorName
						+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up') or contains(@text,'Set up')]",
						testCase, 30));
				if (MobileUtils.isMobElementExists("xpath", "//*[contains(@" + locator + ",'" + SensorName
						+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up') or contains(@text,'Set up')]",
						testCase, 10)) {
					return MobileUtils.clickOnElement(testCase, "xpath", "//*[contains(@" + locator + ",'" + SensorName
							+ "')]/following-sibling::android.widget.LinearLayout/android.widget.Button[contains(@text,'Set Up') or contains(@text,'Set up')]");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate setup button");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate " + e.getMessage(),
					true);
		}
		return flag;
	}

	public boolean isSensorOverviewScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorOverviewHeading")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorHowitWorks");
	}

	public boolean isKEYFOBOverviewScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobOverview");
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
				@SuppressWarnings("rawtypes")
				TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				System.out.println("######dimensions.width:- " + dimensions.width);
				System.out.println("######dimensions.height:- " + dimensions.height);
				System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
				System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
				// touchAction.tap((dimensions.width - 100), (dimensions.height -
				// 100)).perform();
				touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			}
		}
		return flag;
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

	public boolean clickOnDoneButton() {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		try {
			Thread.sleep(10000);
			/*
			 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
			 * (dimension.getHeight() * .6)) .release().perform(); action.press(10, (int)
			 * (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
			 * .release().perform();
			 */
			action.press(point(10, (int) (dimension.getHeight() * .9)))
			.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
			action.press(point(10, (int) (dimension.getHeight() * .9)))
			.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeButton[contains(@name,'rightButton')]",
						testCase, 30)) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SetUpAccessoriesBack");
				} else {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
				}
			} else {
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				int startx = (dimensions.width * 20) / 100;
				int starty = (dimensions.height * 62) / 100;
				int endx = (dimensions.width * 22) / 100;
				int endy = (dimensions.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				if (MobileUtils.isMobElementExists("xpath",
						"//android.widget.Button[contains(@text,'Set Up') or contains(@text,'Set up')]", testCase,
						30)) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SetUpAccessoriesBack");
				} else {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate " + e.getMessage(),
					true);
		}
		return flag;
	}

	public boolean checkSensorNameNotInSensorList(String sensorName) {
		boolean flag = true;
		if (testCase.getPlatform().contains("Android")) {
			if (MobileUtils.isMobElementExists("xpath", "//*[@text='" + sensorName + "']", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("xpath", "//*[@name='" + sensorName + "']", testCase)) {
				if (MobileUtils.getMobElements(testCase, "xpath", "//*[@name='" + sensorName + "']").size() > 1) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean editSensorNameToCustom(String customName, TestCaseInputs inputs) {

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameSensor", customName)) {
				// ((AndroidDriver<MobileElement>)
				// testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.KEYCODE_ENTER);

				MobileUtils.clickOnCoordinate(testCase, 991, 1804);
				if (customName.toUpperCase().contains("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", customName);
				} else if (customName.toUpperCase().contains("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", customName);
				} else if (customName.toUpperCase().contains("MOTION SENSOR")) {
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensor_SensorName", 30)) {
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "MotionSensor_SensorName").toUpperCase()
					.contains(SensorName.toUpperCase())) {
				if (status.toUpperCase().contains("NOT DETECTED")
						|| status.toUpperCase().contains("NO MOTION DETECTED")) {
					if (MobileUtils.isMobElementExists(objectDefinition, testCase,
							"MotionSensor_SensorStatus_NotDetected", 30)) {
						return true;
					} else
						return false;
				}
				if (status.toUpperCase().contains("DETECTED")) {
					if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensor_SensorStatus_Detected",
							30)) {
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
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		if (testCase.getPlatform().contains("IOS")) {
			/*
			 * action.press(10, (int) (dimensions.getHeight() * .9)).moveTo(0, -(int)
			 * (dimensions.getHeight() * .6)) .release().perform(); action.press(10, (int)
			 * (dimensions.getHeight() * .9)).moveTo(0, -(int) (dimensions.getHeight() *
			 * .6)) .release().perform();
			 */
			action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(5000)))
			.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
			action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(5000)))
			.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
			return MobileUtils.isMobElementExists("xpath", "//*[@" + locator + "='" + SensorName
					+ "']/following-sibling::XCUIElementTypeStaticText[contains(@" + locator + ",'" + state + "')]",
					testCase, 10);
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				int startx = (dimensions.width * 20) / 100;
				int starty = (dimensions.height * 62) / 100;
				int endx = (dimensions.width * 22) / 100;
				int endy = (dimensions.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
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

	public boolean editSensorNameToCustom(String sensor, String customName, TestCaseInputs inputs) {
		boolean flag = false;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameSensor", customName)) {
				@SuppressWarnings("rawtypes")
				TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				System.out.println("######dimensions.width:- " + dimensions.width);
				System.out.println("######dimensions.height:- " + dimensions.height);
				System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
				System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
				// touchAction.tap((dimensions.width - 100), (dimensions.height -
				// 100)).perform();
				touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
				// MobileUtils.clickOnCoordinate(testCase, 991, 1804);
				flag = true;
				if (sensor.toUpperCase().contains("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", customName);
					System.out.println(
							"After entering custom name: " + inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
				} else if (sensor.toUpperCase().contains("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", customName);
					System.out.println(
							"After entering custom name" + inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));

				} else if (sensor.toUpperCase().contains("HALL") || customName.toUpperCase().contains("LIVING ROOM")) {
					inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", customName);
				}
				return true;
			}
		} else {
			if (MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameSensor", customName)) {
				MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
				flag = true;
			}
		}
		if (sensor.toUpperCase().contains("DOOR")) {
			inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", customName);
			System.out.println("After entering custom name" + inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
		} else if (sensor.toUpperCase().contains("WINDOW")) {
			inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", customName);
			System.out.println("After entering custom name" + inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));

		} else if (sensor.toUpperCase().contains("HALL") || customName.toUpperCase().contains("LIVING ROOM")) {
			inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", customName);
		}
		return flag;
	}

	public boolean isCancelButtonDisplayed() {

		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5);
	}

	public boolean isBackButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 5);
	}

	public boolean clickOnCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean clickOnConfirmCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton_Yes");
	}

	public boolean clickOnDismissCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton_No");
	}

	public boolean isSetUpAccessoriesScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpAccessoriesHeading", 15);
	}

	public boolean isDoorStatusVisible(String status, TestCaseInputs inputs) {
		WebElement ele1 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorName");
		if (ele1 != null) {
			System.out.println("Entered door status checking func");
			String s1 = ele1.getText();
			System.out.println(s1);
			System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
			if (s1.toUpperCase().contains(inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1").toUpperCase())) {
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

	public boolean isCancelSetUpPopUpVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelSensorSetupPopup", 5)
				|| MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelKeyfobSetupPopup", 5)) {
			return true;
		}
		return false;
	}

	public boolean isWindowStatusVisible(String status, TestCaseInputs inputs) {
		WebElement ele1 = MobileUtils.getMobElement(objectDefinition, testCase, "TestSensor_SensorName");
		if (ele1 != null) {
			System.out.println("Entered door status checking func");
			String s1 = ele1.getText();
			System.out.println(s1);
			if (s1.toUpperCase().contains(inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1").toUpperCase())) {
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

	public boolean isTimeOutErrorForDiscoveryDisplayed() {
		System.out.println("Entered into time out popup verification");
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(10, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(10));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeOutErrorForDiscovery")) {
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

	public boolean clickOnTimeOutOkPopup() {
		if (isTimeOutErrorForDiscoveryDisplayed()) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "TimeOutOk");
			return true;
		}
		return false;
	}

	public boolean isMountInaCornerScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MountInaCorner", 10);
	}

	public boolean isMountInaWallScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MountInaWall", 10);
	}

	public boolean isSensorOffScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorisOfflineText", 10);
	}

	public boolean isSensorPairingScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorPairingHelpScreenTitle");

	}

	public boolean isDoneButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButton", 4);
	}

	public boolean clickOnSensorHelpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorHelpButton");
	}

	public boolean isDoorAccessSettingsScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoorAccessSensorScreenTitle");
	}

	public boolean isWindowAccessSettingsScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WindowAccessSensorScreenTitle", timeOut);
	}

	public boolean isMotionSensorSettingsScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorSettingsScreenTitle", timeOut);
	}

	public boolean isMotionViewerSettingsScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionViewerSettingsScreenTitle", timeOut);
	}

	public boolean isSensorNameAlreadyAssignedErrorPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorNameAlreadyAssignedErrorPopup");
	}

	public boolean isSensorNameAlreadyAssignedMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorNameAlreadyAssignedMsg");
	}

	public boolean isOKButtonInSensorNameAlreadyAssignedPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInSensorNameAlreadyAssignedPopup");
	}

	public boolean clickOnOKButtonInSensorNameAlreadyAssignedPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInSensorNameAlreadyAssignedPopup");
	}

	public String GetComboSensorWalktestStatus(){
		String walkTestStatus = null;
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")){
			walkTestStatus	= MobileUtils.getFieldValue(objectDefinition, testCase, "ComboWalkTestStatus");
		} else{
			if (MobileUtils.isMobElementExists("id", "Waiting For Smoke Signal", testCase)){
				walkTestStatus = MobileUtils.getFieldValue(testCase, "id", "Waiting For Smoke Signal");
			}else if (MobileUtils.isMobElementExists("id", "Smoke Signal Confirmed", testCase)){
				walkTestStatus = MobileUtils.getFieldValue(testCase, "id","Smoke Signal Confirmed");
			}else if (MobileUtils.isMobElementExists("id", "Waiting For CO Signal", testCase)){
				walkTestStatus = MobileUtils.getFieldValue(testCase, "id","Waiting For CO Signal");
			}else if (MobileUtils.isMobElementExists("id", "CO Signal Confirmed", testCase)){
				walkTestStatus = MobileUtils.getFieldValue(testCase, "id","CO Signal Confirmed");
			}
		}
		return walkTestStatus;
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
