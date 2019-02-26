package com.honeywell.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;

public class SecuritySolutionCardScreen extends MobileScreens {

	private static final String screenName = "SecuritySolutionCard";
	public static final String NIGHTSECURITYSTATEINANDROID = "Sleep";
	public static final String OFFSECURITYSTATEINANDROID = "OFF";

	public SecuritySolutionCardScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAppSettingsIconVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppSettingsIcon", timeOut);
	}

	public boolean isAltAppSettingsIconVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AltAppSettingsIcon", timeOut);
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorListBack");
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorListBack");
	}

	public boolean isSensorsTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorButton");
	}

	public boolean isFrontPorchCoverTamperedTextVisibleinSecuritySolutions() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FrontPorchCoverTamperedText");
	}

	public boolean isFrontHallCoverTamperedTextVisibleinSecuritySolutions() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FrontHallCoverTamperedText");
	}

	public boolean isWindowCoverTamperedTextVisibleinSecuritySolutions() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WindowCoverTamperedText");
	}

	public boolean isDoorCoverTamperedTextVisibleinSecuritySolutions() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FrontDoorCoverTamperedText");
	}

	public boolean isMotionSensorCoverTamperedTextVisibleinSecuritySolutions() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorCoverTamperedText");
	}

	public boolean isFrontDoorOpenTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoorSensorOpen");
	}

	public boolean isKitchenWindowOpenTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WindowSensorOpen");
	}

	public boolean isSensorOffline() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorisOfflineText");
	}

	public boolean isSecurityStateVisible(String securityState) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (securityState.equalsIgnoreCase("Night")) {
				return MobileUtils.isMobElementExists("XPATH",
						"//android.widget.LinearLayout[@content-desc='" + NIGHTSECURITYSTATEINANDROID + "']", testCase);
			} else if (securityState.equalsIgnoreCase("OFF")) {
				return MobileUtils.isMobElementExists("XPATH",
						"//android.widget.LinearLayout[@content-desc='" + OFFSECURITYSTATEINANDROID + "']", testCase);
			} else {
				return MobileUtils.isMobElementExists("XPATH",
						"//android.widget.LinearLayout[@content-desc='" + securityState + "']", testCase);
			}
		} else {
			if (testCase.getMobileDriver()
					.findElementsByXPath("//XCUIElementTypeCell[@value='" + securityState + "'])") != null) {
				return testCase.getMobileDriver()
						.findElementsByXPath("//XCUIElementTypeCell[@value='" + securityState + "'])") != null;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnSecurityState(String securityState) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (securityState.equalsIgnoreCase("Night")) {
				return MobileUtils.clickOnElement(testCase, "XPATH",
						"//android.widget.LinearLayout[@content-desc='" + NIGHTSECURITYSTATEINANDROID + "']");
			} else if (securityState.equalsIgnoreCase("OFF")) {
				return MobileUtils.clickOnElement(testCase, "XPATH",
						"//android.widget.LinearLayout[@content-desc='" + OFFSECURITYSTATEINANDROID + "']");
			} else {
				return MobileUtils.clickOnElement(testCase, "XPATH",
						"//android.widget.LinearLayout[@content-desc='" + securityState + "']");
			}
		} else {
			if (securityState == "Home") {
				if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@value='" + securityState + "']",
						testCase)) {
					return MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell[@value='" + securityState + "']");
				} else {
					flag = false;
				}
			} else {
				if (MobileUtils.isMobElementExists("NAME", securityState, testCase)) {
					return MobileUtils.clickOnElement(testCase, "NAME", securityState);
				} else {
					if (MobileUtils.isMobElementExists("XPATH", "//*[@value='" + securityState + "']", testCase)) {
						return MobileUtils.clickOnElement(testCase, "XPATH", "//*[@value='" + securityState + "']");
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	public String getCurrentSecurityState() {
		String currentSecurityState = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			currentSecurityState = MobileUtils.getMobElement(objectDefinition, testCase, "PanelState")
					.getAttribute("name");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PanelState")) {
				currentSecurityState = MobileUtils.getMobElement(objectDefinition, testCase, "PanelState")
						.getAttribute("value");
			}
		}
		return currentSecurityState;
	}

	public boolean isCurrentSecurityStateVisible(String currentSecurityState) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.Button[@text='" + currentSecurityState + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", currentSecurityState, testCase);
		}
	}

	public boolean clickOnState(String stateToChange, TestCaseInputs inputs) {
		boolean flag = true;
		switch (stateToChange.toUpperCase()) {
		case "HOME": {
			inputs.setInputValue("HOME_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));
			if (isSecurityStateVisible(stateToChange)) {
				return clickOnSecurityState(stateToChange);
			} else {
				flag = false;
			}
		}
		case "AWAY": {
			inputs.setInputValue("AWAY_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "AWAY_TIME " + inputs.getInputValue("AWAY_TIME"));
			if (isSecurityStateVisible(stateToChange)) {
				return clickOnSecurityState(stateToChange);
			} else {
				flag = false;
			}
		}
		case "NIGHT": {
			inputs.setInputValue("NIGHT_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "NIGHT_TIME " + inputs.getInputValue("NIGHT_TIME"));
			if (isSecurityStateVisible(stateToChange)) {
				return clickOnSecurityState(stateToChange);
			} else {
				flag = false;
			}
			break;
		}
		case "OFF": {
			inputs.setInputValue("OFF_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "OFF_TIME " + inputs.getInputValue("OFF_TIME"));
			if (isSecurityStateVisible(stateToChange)) {
				return clickOnSecurityState(stateToChange);
			} else {
				flag = false;
			}
		}
		default: {
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "input not handled");
		}

		}
		return flag;
	}

	public boolean isSwitchingToTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchingToText", 5);
	}

	public String getSwitchingTextLabel() {
		String switchingSecurityStateText = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			switchingSecurityStateText = MobileUtils.getMobElement(objectDefinition, testCase, "SwitchingToText")
					.getAttribute("text") + " "
					+ MobileUtils.getMobElement(objectDefinition, testCase, "PanelState").getAttribute("text");
		} else {
			List<WebElement> elements = MobileUtils.getMobElements(objectDefinition, testCase, "PanelState");
			System.out.println(elements.size());
			WebElement panelState = null;
			for (WebElement ele : elements) {
				System.out.println(ele.isDisplayed());
				if (ele.isDisplayed()) {
					panelState = ele;
				}
			}
			if (panelState == null) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find switching to text");
			} else {
				switchingSecurityStateText = "Switching to " + panelState.getAttribute("value");
			}
		}
		return switchingSecurityStateText;
	}

	public boolean isCountDownTimerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimerProgressBar", 10);
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isLoadingProgressVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingProgressBar");
	}

	public boolean ispleaseWaitProgressVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingProgressBar");
	}

	public boolean isSetToOffPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetToOffPopupTitle");
	}

	public boolean isCancelButtonInSetToOffPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInSetToOffPopup");
	}

	public boolean clickOnCancelButtonInSetToOffPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInSetToOffPopup");
	}

	public boolean isOKButtonInSetToOffPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInSetToOffPopup");
	}

	public boolean clickOnOKButtonInSetToOffPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInSetToOffPopup");
	}

	public String getTimeStamp() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimeStamp");
	}

	public boolean isClearNotificationsIconVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ClearNotificationsIcon", 5);
	}

	public boolean clickOnClearNotificationsIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ClearNotificationsIcon");
	}

	public boolean isClearNotificationsTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ClearNotificationsText", 5);
	}

	public boolean clickOnClearNotificationsText() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ClearNotificationsText");
	}

	public boolean isClearNotificationsIconEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ClearNotificationsIcon").getAttribute("enabled")
				.equalsIgnoreCase("true");
	}

	public boolean clickOnConfirmButtonInClearNotifications() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmButtonInClearNotifications");
	}

	public boolean verifyIfPushNotificationIsVisible(String notification) {
		String locatorValue = "";
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			locatorValue = "//*[contains(@text='" + notification + "')]";
		} else {
			locatorValue = "//XCUIElementTypeCell[contains(@label,'" + notification + "')]";
		}
		if (testCase.getMobileDriver().findElementsByXPath(locatorValue).get(0) != null) {
			Keyword.ReportStep_Pass(testCase, "'" + notification + "' Push Notification Present");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"'" + notification + "' Push Notification not present");
		}
		return flag;
	}

	public boolean verifystate(String stateToVerify) {
		String currentPanelState = MobileUtils.getFieldValue(objectDefinition, testCase, "PanelState");
		switch (stateToVerify.toUpperCase()) {
		case "HOME": {
			if (stateToVerify.equalsIgnoreCase(currentPanelState)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}
		}
		case "AWAY": {
			if (stateToVerify.equalsIgnoreCase(currentPanelState)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		case "NIGHT": {

			if (stateToVerify.equalsIgnoreCase(currentPanelState)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		case "OFF": {

			if (stateToVerify.equalsIgnoreCase(currentPanelState)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		default: {
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "input not handled");
		}

		}
		return false;
	}

	public boolean ClickOnSensorIssue() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorWithIssueButon");
	}

	public boolean isSensorIssueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorWithIssueButon");
	}

	public boolean isSensorNoIssueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorNoIssueButton");
	}

	public boolean ClickOnSensorNoIssue() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorNoIssueButton");
	}

	public boolean isSwitchToAwayPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToAwayPopupTitle", timeOut);
	}

	public boolean isSwitchToNightPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightPopupTitle", timeOut);
	}

	public boolean isCancelButtonInSwitchToPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInPopup");
	}

	public boolean clickOnCancelButtonInSwitchToPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInPopup");
	}

	public boolean isOKButtonInSwitchToPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInPopup");
	}

	public boolean clickOnOKButtonInSwitchToPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInPopup");
	}

	public boolean isUnableToConnectToBaseStationAlertVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UnableToConnectToBaseStationAlert");
	}

	public boolean isOKButtonInUnableToConnectToBaseStationAlertVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInPopup");
	}

	public boolean clickOnOKButtonInUnableToConnectToBaseStationAlert() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInPopup");
	}

	public boolean clickOnAppSettingsIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AppSettingsIcon");
	}

	public boolean clickOnAltAppSettingsIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AltAppSettingsIcon");
	}

	public boolean clickOnCancelButtonWhileSwitchingModes() {
		 if (testCase.getMobileDriver().getPlatformName().contains("Android")){
			 return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInSwitchingModes");
		 } else{
			 testCase.getMobileDriver().findElementByName("countDownTimer").click();
			 return true;
		 }
	}

	public boolean clickOnSensorButton() {
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
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorButton", 5)) {
				Keyword.ReportStep_Pass(testCase, "not able to locate Sensor menu");
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						"Base Station Configuration");
			} else {
				Keyword.ReportStep_Pass(testCase, "Located Sensor menu");
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not able to locate " + e.getMessage(),
					true);
			return false;

		}
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorButton");
	}

	public boolean clickOnUserGivenSensorName(String givenSensorName) {
		List<WebElement> sensorList;
		String actualSensorName = null;
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
				if (sensor.getAttribute("value") != null) {
					actualSensorName = sensor.getAttribute("value");
				}
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

	public boolean isSensorDisplayed(String givenSensorName) {
		List<WebElement> sensorList;
		/*
		 * if (testCase.getPlatform().contains("IOS")) { sensorList =
		 * MobileUtils.getMobElements(testCase, "xpath", "//XCUIElementTypeStaticText");
		 * } else {
		 */
		sensorList = MobileUtils.getMobElements(objectDefinition, testCase, "SensorName");
		// }

		for (WebElement sensor : sensorList) {
			String actualSensorName = sensor.getText();
			if (givenSensorName.equalsIgnoreCase(actualSensorName)) {
				return true;
			}
		}
		return false;

	}

	public boolean isSecuritySettingsTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecuritySettingsTitle", 10);

	}
}