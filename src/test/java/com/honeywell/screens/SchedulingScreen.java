package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

import io.appium.java_client.MobileElement;

public class SchedulingScreen extends MobileScreens {

	public static final String WHENIMHOMELOCATOR = "//*[@text='When I" + "\u2019" + "m Home']";
	public static final String WHENIMAWAYLOCATOR = "//*[@text='When I" + "\u2019" + "m Away']";

	private static final String screenName = "ScheduleScreen";

	public SchedulingScreen(TestCases testCase) {
		super(testCase, screenName);
		// osPopUps = new OSPopUps(testCase);
	}

	public boolean clickOnAddImageButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddImage");
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean IsDoneButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButton", timeOut);
	}

	public boolean IsSaveButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButton", timeOut);
	}

	public boolean clickOnCancelChangeButton() {
		//return MobileUtils.clickOnElement(testCase, "NAME", "CANCEL");
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelChangeButton");
	}

	public boolean clickOnCloseButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CrossButton");
	}

	public boolean clickOnConfirmChangeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmChangeButton");
	}

	public boolean clickOnConfirmDeleteButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmDeleteButton");
	}

	public boolean isScheduleOptionsVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOptionsButton", timeOut);
	}

	public boolean isScheduleOptionsElementEnabled() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ScheduleOptionsButton");
		if (element.isEnabled()) {
			return flag;
		} else
			return false;
	}

	public boolean clickOnCopyButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CopyButton");
	}

	public boolean clickOnCreateNewTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateNewTimeScheduleButton");
	}

	public boolean clickOnCreateScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateScheduleButton");
	}

	public boolean clickOnDoneButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
	}

	public boolean clickOnElementFromObjectDefinitions(String locatorValueinObjectDefinition) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, locatorValueinObjectDefinition);
	}

	public boolean clickOnEverydayScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EverydayScheduleButton");
	}

	public boolean clickOnLearnMoreButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LearnMoreButton");
	}

	public boolean clickOnGetStartedButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetStartedButton");
	}

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
	}

	public boolean clickOnNoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
	}

	public boolean clickOnOkButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}

	public boolean clickOnPeriodDeleteIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PeriodDeleteIcon");
	}

	public boolean clickOnSaveButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
	}

	public boolean clickOnScheduleOffOverlay() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOffOverlay");
	}

	public boolean clickOnScheduleOptionsButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOptionsButton");
	}

	public boolean clickOnSkipButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButton");
	}

	public boolean clickOnSkipSleepButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipSleepButton");
	}

	public boolean clickOnStartButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "StartButton");
	}

	public boolean clickOnSwitchToGeofenceButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToGeofencingButton");
	}

	public boolean isSwitchToTimeScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToTimeScheduleButton", timeOut);
	}

	public boolean clickOnSwitchToTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToTimeScheduleButton");
	}

	public boolean clickOnTimeOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeOption");
	}

	public boolean isTimeScheduleButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleButton");
	}

	public boolean clickOnTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleButton");
	}

	public boolean clickOnTimeScheduleEndTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleEndTime");
	}

	public boolean clickOnTimeScheduleEndTimeCell() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleEndTimeCell");
	}

	public boolean clickOnTimeScheduleStartTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleStartTime");
	}

	public boolean clickOnUseGeofencingText() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseGeofencingText");
	}

	public boolean clickOnWeekdayandWeekendScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WeekdayandWeekendScheduleButton");
	}

	public boolean clickOnYesButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButton");
	}

	public WebElement getAddImageElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AddImage");
	}

	public List<WebElement> getAddImageElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "AddImage");
	}

	public List<WebElement> getAllCheckBoxElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CheckBox");
	}

	public List<WebElement> getCoolDecrementElements() {
		//		return MobileUtils.getMobElements(objectDefinition, testCase, "CoolDecrement");
		if (testCase.getMobileDriver().getPlatformName().contains("Android")){
			return MobileUtils.getMobElements(objectDefinition, testCase, "CoolIncrement");
		} else{
			return MobileUtils.getMobElements(testCase,"id", "temp stepper down");
		}
	}

	public List<WebElement> getCoolIncrementElements() {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")){
			return MobileUtils.getMobElements(objectDefinition, testCase, "CoolIncrement");
		} else{
			return MobileUtils.getMobElements(testCase,"id", "temp stepper up");
		}
	}


	public String getCoolSetPointChooserSetPointsValue() {
		String string;
		List<WebElement> CoolElement = MobileUtils.getMobElements(objectDefinition, testCase, "CoolSetPointChooser");
		if (CoolElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				string = MobileUtils.getMobElements(objectDefinition, testCase, "CoolSetPointChooser").get(0).getText();
			} else {
				string = testCase.getMobileDriver()
						.findElement(By.name("//XCUIElementTypeStaticText[@name='Dialer'][1]")).getAttribute("value");
			}
		} else {
			string = MobileUtils.getFieldValue(objectDefinition, testCase, "CoolSetPointChooser");
		}
		return string;
	}

	public WebElement getCoolSetPointDownButton() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElements(testCase, "xpath",
					"//android.widget.ImageButton[@content-desc='Temperature decreasing']").get(0);
		} else {
			return testCase.getMobileDriver().findElements(By.name("temp stepper down")).get(0);
		}
	}

	public List<WebElement> getCoolSetPointsElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CoolSetPoints");
	}

	public String getCoolSetPointsOfGivenEverydayPeriod(String periodName) {
		String string;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			string = MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
					+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]");
		} else {
			string = MobileUtils.getMobElement(testCase, "name", "Everyday_" + periodName + "_CoolTemperature")
					.getAttribute("value");
		}
		return string;
	}

	public String getCoolSetPointsOfGivenWeekdayPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
				+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]");
	}

	public String getCoolSetPointsOfGivenWeekendPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
				+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]");
	}

	public WebElement getCoolSetPointUpButton() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElements(testCase, "xpath",
					"//android.widget.ImageButton[@content-desc='Temperature increasing']").get(0);
		} else {
			return testCase.getMobileDriver().findElements(By.name("temp stepper up")).get(0);
		}
	}

	public WebElement getElementFromObjectDefinitions(String locatorValueinObjectDefinition) {
		return MobileUtils.getMobElement(objectDefinition, testCase, locatorValueinObjectDefinition);
	}

	public WebElement getEveryday1Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Everyday1");
	}

	public WebElement getEveryday2Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Everyday2");
	}

	public WebElement getEveryday3Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Everyday3");
	}

	public WebElement getEveryday4Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Everyday4");
	}

	public WebElement getEverydayAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayAway");
	}

	public WebElement getEverydayHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayHome");
	}

	public List<WebElement> getEverydayPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydayTime");
	}

	public List<WebElement> getEverydaySchedulePeriodTimesElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydaySchedulePeriodTimes");
	}

	public List<WebElement> getEverydayScheduleTitleAndPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydayScheduleTitleAndPeriodTime");
	}

	public WebElement getEverydaySleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydaySleep");
	}

	public WebElement getEverydayText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayText");
	}

	public List<WebElement> getEverydayTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydayTime");
	}

	public List<WebElement> getEverydayTitleListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydayTitleList");
	}

	public WebElement getEverydayWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayWake");
	}

	public WebElement getHeatDecrementElements() {
		List<WebElement> HeatElement = MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser");
		if (HeatElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElements(objectDefinition, testCase, "HeatDecrement").get(1);
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper down'][2]"));
			}
		}else {
//			return MobileUtils.getMobElement(objectDefinition, testCase, "HeatDecrement");
			return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper down']"));
		}
	}

	public WebElement getHeatIncrementElements() {
		List<WebElement> HeatElement = MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser");
		if (HeatElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElements(objectDefinition, testCase, "HeatIncrement").get(1);
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper up'][2]"));
			}
		}else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElement(objectDefinition, testCase, "HeatIncrement");
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper up']"));
			}
		}
	}

	public String getHeatSetPointChooserSetPointsValue() {
		List<WebElement> HeatElement = MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser");
		if (HeatElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser").get(1).getText();
			} else {
				return MobileUtils.getMobElement(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Dialer'][2]").getAttribute("value");
			}
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "HeatSetPointChooser");
		}
	}

	public WebElement getHeatSetPointDownButton() {
		List<WebElement> HeatElement = MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser");
		if (HeatElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElements(objectDefinition, testCase, "HeatDecrement").get(1);
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper down'][2]"));
			}
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElement(objectDefinition, testCase, "HeatDecrement");
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper down']"));
			}
		}
	}

	public List<WebElement> getHeatSetPointsElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase,"HeatSetPoints");
	}



	public List<MobileElement> getHeatOnlySetPointsElements() {
		// return MobileUtils.getMobElements(objectDefinition, testCase,
		// "heatTemperatureLabel");
		return testCase.getMobileDriver().findElements(By.xpath("//XCUIElementTypeStaticText[@name='Dialer']"));
	}

	public String getHeatSetPointsOfGivenEverydayPeriod(String periodName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
					+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[2]");
		} else {
			return MobileUtils.getMobElement(testCase, "name", "Everyday_" + periodName + "_HeatTemperature")
					.getAttribute("value");
		}
	}

	public String getHeatSetPointsOfGivenWeekdayPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
				+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[2]");
	}

	public String getHeatSetPointsOfGivenWeekendPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath", "//*[contains(@content-desc,'" + periodName
				+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[2]");
	}

	public WebElement getHeatSetPointUpButton() {
		List<WebElement> HeatElement = MobileUtils.getMobElements(objectDefinition, testCase, "HeatSetPointChooser");
		if (HeatElement.size() > 1) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElements(objectDefinition, testCase, "HeatIncrement").get(1);
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper up'][2]"));
			}
		}else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.getMobElement(objectDefinition, testCase, "HeatIncrement");
			} else {
				return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='temp stepper up']"));
			}
		}
	}

	public WebElement getHeatSetPointUpButton(int index) {
		return MobileUtils.getMobElement(objectDefinition, testCase, "HeatIncrement");
	}

	public String getPeriodName(String locatorValue) {
		return MobileUtils.getMobElement(testCase, "name", locatorValue).getAttribute("name");
	}

	public boolean isSchedulePeriodHeatSetPointVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SchedulePeriodHeatSetPoint", timeOut);
	}

	public boolean isSchedulePeriodCoolSetPointVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SchedulePeriodCoolSetPoint", timeOut);
	}

	public List<WebElement> getSchedulePeriodHeatSetPointElement() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodHeatSetPoint");
	}

	public List<WebElement> getSchedulePeriodCoolSetPointElement() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodCoolSetPoint");
	}

	public List<WebElement> getSchedulePeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodTime");
	}

	public List<WebElement> getSchedulePeriodTitleElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodTitle");
	}

	public String getTimeChooserEndTimeValue() {
		if (testCase.getPlatform().contains("Android")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooserEndTime").getText();
		} else
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooserEndTime").getAttribute("value");
	}

	public String getTimeChooserValue() {
		if (testCase.getPlatform().contains("Android")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooser").getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooser").getAttribute("value");
		}
	}

	public String getTimeOfEverydayScheduleOfGivenPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath",
				"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
						+ periodName
						+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView");
	}

	public String getTimeOfWeekdayScheduleOfGivenPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath",
				"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
						+ periodName
						+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView");
	}

	public String getTimeOfWeekendScheduleOfGivenPeriod(String periodName) {
		return MobileUtils.getFieldValue(testCase, "xpath",
				"//*[contains(@content-desc,'_Saturday - Sunday')]//android.widget.TextView[@text='" + periodName
				+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView");
	}

	public String getTimeScheduleEndTimeValue() {
		if (testCase.getPlatform().contains("Android")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeScheduleEndTime").getText();
		} else
			return MobileUtils.getMobElement(objectDefinition, testCase, "TimeScheduleEndTime").getAttribute("value");
	}

	public String getValueOfEverydayHeatTemperatureElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "name", "Everyday_" + index + "_HeatTemperature")
				.getAttribute("value");
	}

	public List<WebElement> getValueOfEverydayTimeElementAtIndex(int index) {
		return MobileUtils.getMobElements(testCase, "name", "Everyday_" + index + "_Time");
	}

	public List<WebElement> getValueOfWeekdayTimeElementAtIndex(int index) {
		return MobileUtils.getMobElements(testCase, "name", "Monday - Friday_" + index + "_Time");
	}

	public WebElement getValueOfWeekendTimeElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "xpath",
				"//*[@content-desc='" + index + "_Saturday - Sunday']/android.widget.TextView");
	}

	public List<WebElement> getValueOfWeekendTimesElementAtIndex(int index) {
		return MobileUtils.getMobElements(testCase, "xpath",
				"//XCUIElementTypeStaticText[@name='Saturday - Sunday_" + index + "_Time']");
	}

	public String getValueOfWeekdayHeatTemperatureElementAtIndex(String periodName) {
		return MobileUtils.getMobElement(testCase, "name", "Monday - Friday_" + periodName + "_HeatTemperature")
				.getAttribute("value");
	}

	public String getValueOfWeekdayCoolTemperatureElementAtIndex(String periodName) {
		return MobileUtils.getMobElement(testCase, "name", "Monday - Friday_" + periodName + "_CoolTemperature")
				.getAttribute("value");
	}

	public String getValueOfWeekendHeatTemperatureElementAtIndex(String periodName) {
		return MobileUtils.getMobElement(testCase, "name", "Saturday - Sunday_" + periodName + "_HeatTemperature")
				.getAttribute("value");
	}

	public String getValueOfWeekendCoolTemperatureElementAtIndex(String periodName) {
		return MobileUtils.getMobElement(testCase, "name", "Saturday - Sunday_" + periodName + "_CoolTemperature")
				.getAttribute("value");
	}

	public String getValueOfWeekendHeatTemperatureElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "name", "Saturday - Sunday_" + index + "_HeatTemperature")
				.getAttribute("value");
	}

	public WebElement getWeekday1Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekday1");
	}

	public WebElement getWeekday2Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekday2");
	}

	public WebElement getWeekday3Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekday3");
	}

	public WebElement getWeekday4Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekday4");
	}

	public WebElement getWeekdayAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayAway");
	}

	public List<WebElement> getWeekdayHeatSetpointListEMEAElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayHeatSetpointListEMEA");
	}

	public List<WebElement> getWeekdayHeatSetpoints() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayHeatSetpoints");
	}

	public List<WebElement> getWeekendHeatSetpoints() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendHeatSetpoints");
	}

	public List<WebElement> getWeekdayCoolSetpoints() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayCoolSetpoints");
	}

	public List<WebElement> getWeekendCoolSetpoints() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendCool" + "Setpoints");
	}

	public WebElement getWeekdayHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayHome");
	}

	public List<WebElement> getWeekdayPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayPeriodTime");
	}

	public String getWeekdayPeroidTimeValueAtIndex(int index) {
		return MobileUtils
				.getMobElement(testCase, "xpath",
						"//android.widget.FrameLayout[" + index
						+ "]//*[contains(@content-desc,'_Monday - Friday')]/android.widget.TextView[1]")
				.getText();
	}

	public List<WebElement> getWeekdaySchedulePeriodTitleElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdaySchedulePeriodTitle");
	}

	public List<WebElement> getWeekdayScheduleTitleAndPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayScheduleTitleAndPeriodTime");
	}

	public List<WebElement> getWeekendScheduleTitleAndPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendScheduleTitleAndPeriodTime");
	}

	public WebElement getWeekdaySleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdaySleep");
	}

	public WebElement getWeekdayText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayText");
	}

	public List<WebElement> getWeekdayTimeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayTimeList");
	}

	public List<WebElement> getWeekdayTitleListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayTitleList");
	}

	public WebElement getWeekdayWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayWake");
	}

	public List<WebElement> getWeekdendHeatSetpointListEMEAElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendHeatSetpointListEMEA");
	}

	public List<WebElement> getWeekdendTimeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendTimeList");
	}

	public WebElement getWeekend1Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekend1");
	}

	public WebElement getWeekend2Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekend2");
	}

	public WebElement getWeekend3Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekend3");
	}

	public WebElement getWeekend4Element() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Weekend4");
	}

	public WebElement getWeekendAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendAway");
	}

	public WebElement getWeekendHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendHome");
	}

	public List<WebElement> getWeekendPeriodTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendPeriodTime");
	}

	public String getWeekendPeroidTimeValueAtIndex(int index) {
		return MobileUtils
				.getMobElement(testCase, "xpath",
						"//android.widget.FrameLayout[" + (index)
						+ "]//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]")
				.getText();
	}

	public List<WebElement> getWeekendSchedulePeriodSetPointsElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendSchedulePeriodSetPoints");
	}

	public List<WebElement> getWeekendSchedulePeriodTimesElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendSchedulePeriodTimes");
	}

	public List<WebElement> getWeekendSchedulePeriodTitleElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendSchedulePeriodTitle");
	}

	public WebElement getWeekendSleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendSleep");
	}

	public WebElement getWeekendText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendText");
	}

	public List<WebElement> getWeekendTitleListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendTitleList");
	}

	public WebElement getWeekendWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendWake");
	}

	public boolean isAddImageButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddImage", timeOut);
	}

	public boolean isAMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AMLabel", timeOut);
	}

	public boolean isAMPMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AMPMLabel", timeOut);
	}

	public boolean isAwayTemperatureHeaderMultiTemperatureVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AwayTemperatureHeaderMultiTemperature",
				timeOut);
	}

	public boolean isAwayTemperatureHeaderSingleTemperatureVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AwayTemperatureHeaderSingleTemperature",
				timeOut);
	}

	public boolean isBackButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}

	public boolean isCancelChangeButtonVisible(int timeOut) {
		
		//return MobileUtils.isMobElementExists("NAME", "Cancel", testCase);
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelChangeButton", timeOut);
	}

	public boolean isCheckBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CheckBox", timeOut);
	}

	public boolean isCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CrossButton", timeOut);
	}

	public boolean isConfirmChangeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmChangeButton", timeOut);
	}

	public boolean isConfirmDeleteButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmDeleteButton", timeOut);
	}

	public boolean isCopyButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CopyButton", timeOut);
	}

	public boolean isCreateScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateScheduleButton", timeOut);
	}

	public boolean isDoneButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButton", timeOut);
	}

	public boolean isElementFromObjectDefinitionsVisible(String locatorValueinObjectDefinition, int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, locatorValueinObjectDefinition, timeOut);
	}

	public boolean isEverydayPeriodTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayTime", timeOut);
	}

	public boolean isEverydayScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayScheduleButton", timeOut);
	}

	public boolean isEverydaySchedulePeriodTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydaySchedulePeriodTimes", timeOut);
	}

	public boolean isEverydayScheduleTitleAndPeriodTimeVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayScheduleTitleAndPeriodTime",
				timeOut);
	}

	public boolean isEverydayTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayText", timeOut);
	}

	public boolean isEverydayTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayTime", timeOut);
	}

	public boolean isHomeTemperatureHeaderMultiTemperatureVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HomeTemperatureHeaderMultiTemperature",
				timeOut);
	}

	public boolean isHomeTemperatureHeaderSingleTemperatureVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HomeTemperatureHeaderSingleTemperature",
				timeOut);
	}

	public boolean isNextButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", timeOut);
	}

	public boolean isPeriodDeleteIconVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PeriodDeleteIcon", timeOut);
	}

	public boolean isPeriodEditScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeChooserHeader", timeOut);
	}

	public boolean isPMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PMLabel", timeOut);
	}

	public boolean isScheduleOffOverlayVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOffOverlay", timeOut);
	}

	public boolean isSchedulePeriodTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SchedulePeriodTime", timeOut);
	}

	public boolean isSkipButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButton", timeOut);
	}

	public boolean isSwitchToGeofenceButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToGeofencingButton", timeOut);
	}

	public boolean isTempChooserHeaderVisible(int timeOut) {
		// TODO
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TempChooserHeader", timeOut);
	}

	public boolean isTimeAMPMVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeAMPM", timeOut);
	}

	public boolean isTimeChooserHeaderVisible(int timeOut) {
		// TODO
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeChooserHeader", timeOut);
	}

	public boolean isTimeHoursVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeHours", timeOut);
	}

	public boolean isTimeMinutesVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeMinutes", timeOut);
	}

	public boolean isTimeOptionVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeOption", timeOut);
	}

	public boolean isTimePickerVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimePicker", timeOut);
	}

	public boolean isTimeScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleButton", timeOut);
	}

	public boolean isTimeScheduleEndTimeCellVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleEndTimeCell", timeOut);
	}

	public boolean isTimeScheduleEndTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleEndTime", timeOut);
	}

	public boolean isWeekday1ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekday1", timeOut);
	}

	public boolean isWeekdayPeriodTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdayPeriodTime", timeOut);
	}

	public boolean isWeekdaySchedulePeriodTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdaySchedulePeriodTitle", timeOut);
	}

	public boolean isWeekdayScheduleTitleAndPeriodTimeVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdayScheduleTitleAndPeriodTime", timeOut);
	}

	public boolean isWeekendScheduleTitleAndPeriodTimeVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendScheduleTitleAndPeriodTime", timeOut);
	}

	public boolean isWeekdayTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdayText", timeOut);
	}

	public boolean isWeekDayTimeListVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdayTimeList", timeOut);
	}

	public boolean isWeekend1ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekend1", timeOut);
	}

	public boolean isWeekend2ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekend2", timeOut);
	}

	public boolean isWeekend3ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekend3", timeOut);
	}

	public boolean isWeekend4ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekend4", timeOut);
	}

	public boolean isWeekendAwayElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendAway", timeOut);
	}

	public boolean isWeekendHomeElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendHome", timeOut);
	}

	public boolean isWeekendPeriodTimeElementAtIndexVisible(int index, int timeOut) {
		return MobileUtils.isMobElementExists("Xpath",
				"//android.widget.FrameLayout[" + (index)
				+ "]//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]",
				testCase, timeOut);
	}

	public boolean isWeekendPeriodTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendPeriodTime", timeOut);
	}

	public boolean isWeekendSchedulePeriodTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendSchedulePeriodTitle", timeOut);
	}

	public boolean isWeekendSleepElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendSleep", timeOut);
	}

	public boolean isWeekendTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendText", timeOut);
	}

	public boolean isWeekendTimeListVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendTimeList", timeOut);
	}

	public boolean isWeekendWakeElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendWake", timeOut);
	}

	public boolean setValueToTimeAMPMPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeAMPM", value);
	}

	public boolean setValueToTimeHoursPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeHours", value);
	}

	public boolean setValueToTimeMinutesPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeMinutes", value);
	}

	public boolean setValueToTimePicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimePicker", value);
		// String ele = MobileUtils.getMobElement(objectDefinition, testCase,
		// "TimePicker").getText();
		// testCase.getMobileDrivers().replace(ele, value);
	}

	public boolean setHoursValueToTimePickerAndroid(String value) {
		MobileUtils.clickOnElement(objectDefinition, testCase, "TimeHours");
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeHours", "8");
	}

	public boolean setMinsValueToTimePickerAndroid(String value) {
		MobileUtils.clickOnElement(objectDefinition, testCase, "TimeMinutes");
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeMinutes", value);
	}

	public boolean setAMPMTimeFormatValueToTimePickerAndroid(String value) {
		if (value.toUpperCase().contains("A.M.")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeAM");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "TimePM");
		}
	}

	public boolean isWhenImHomeTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists("xpath", SchedulingScreen.WHENIMHOMELOCATOR, testCase, timeOut);
	}

	public boolean isWhenImAwayTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists("xpath", SchedulingScreen.WHENIMAWAYLOCATOR, testCase, timeOut);
	}

	public boolean isUseMyHomeSettingsTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseMyHomeSettingsText", timeOut);
	}

	public boolean isCreateSleepSettingsTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateSleepSettingsText", timeOut);
	}

	public boolean isCreateSleepSettingsVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateSleepSettings", timeOut);
	}

	public boolean clickOnCreateSleepSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateSleepSettings");
	}

	public boolean isUseMySleepSettingsTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseMySleepSettingsText", timeOut);
	}

	public boolean isUseMyAwaySettingsTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseMyAwaySettingsText", timeOut);
	}

	public String getSleepStartEndTime() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SchedulingPeriodSleepStartTime").getText();
	}

	public boolean isGeofenceHomeHeatElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceHomeHeat", timeOut);
	}

	public WebElement getGeofenceHomeHeatElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceHomeHeat");
	}

	public boolean isGeofenceHomeCoolElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceHomeCool", timeOut);
	}

	public WebElement getGeofenceHomeCoolElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceHomeCool");
	}

	public boolean isGeofenceSleepCoolElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceSleepCool", timeOut);
	}

	public boolean isGeofenceHomeElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceHome", timeOut);
	}

	public WebElement getGeofenceSleepCoolElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceSleepCool");
	}

	public boolean isGeofenceAwayHeatElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceAwayHeat", timeOut);
	}

	public boolean isGeofenceAwayCoolElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceAwayCool", timeOut);
	}

	public WebElement getGeofenceAwayCoolElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceAwayCool");
	}

	public boolean isGeofenceSleepHeatElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceSleepHeat", timeOut);
	}

	public WebElement getGeofenceAwayHeatElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceAwayHeat");
	}

	public boolean isNoScheduleTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoScheduleText", timeOut);
	}

	public WebElement getGeofenceSleepHeatElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceSleepHeat");
	}

	public boolean isGeofenceSleepSubTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceSleepSubTitle", timeOut);
	}

	public String getGeofenceSleepSubTitleText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceSleepSubTitle").getAttribute("value");
	}

	public String getGeofenceSleepSubTitleTimeText() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "GeofenceSleepSubTitle").get(1)
				.getAttribute("value");
	}

	public boolean isViewByIndividualDaysVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ViewByIndividualDays", timeOut);
	}

	public boolean clickOnViewByIndividualDays() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ViewByIndividualDays");
	}

	public boolean isViewByGroupedDaysVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ViewByGroupedDays", timeOut);
	}

	public boolean clickOnViewByGroupedDays() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ViewByGroupedDays");
	}

	public boolean isGeofenceCoolHeatTemperatureVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceCoolHeatTemperature", timeOut);
	}

	public boolean isGeofenceSleepElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseMySleepSettingsText", timeOut);
	}

	public boolean isGeofenceAwayElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseMyAwaySettingsText", timeOut);
	}

	public WebElement getGeofenceCoolHeatTemperatureElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceCoolHeatTemperature");
	}

	public boolean isSchedulingPeriodSleepStartTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SchedulingPeriodSleepStartTime", timeOut);
	}

	public String getSchedulingPeriodSleepStartTimeText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SchedulingPeriodSleepStartTime").getText();
	}

	public boolean isScheduleDayHeaderVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleDayHeader", timeOut);
	}

	public List<WebElement> getScheduleDayHeaderElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "ScheduleDayHeader");
	}

	public boolean isTimeChooserEndTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeChooserEndTime", timeOut);
	}

	public boolean isNavigationLeftBarItemVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NavigationLeftBarItem", timeOut);
	}

	public boolean clickOnNavigationLeftBarItem() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NavigationLeftBarItem");
	}

	public List<WebElement> getSchedulePeriodTimeElement() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodTime");
	}

	public List<WebElement> getSchedulePeriodbuttons() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "AddPeriodButton");
	}

	public WebElement getSchedulePeriodbutton() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AddPeriodButton");
	}

	public boolean isAddPeriodButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddPeriodButton", timeOut);
	}

	public boolean clickOnAddPeriodButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddPeriodButton");
	}

	public boolean clickOnUseMyHomeSettingsButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseMyHomeSettingsText");
	}

	public boolean clickOnGeofenceHomeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceHome");
	}

	public boolean clickOnUseMySleepSettingsButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseMySleepSettingsText");
	}

	public boolean clickOnGeofenceSleepButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseMySleepSettingsText");
	}

	public boolean clickOnUseMyAwaySettingsButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseMyAwaySettingsText");
	}

	public boolean clickOnGeofenceAwayButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceAway");
	}

	public boolean isScheduleOffStatusVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOffStatus", timeOut);
	}

	public boolean clickOnScheduleOffStatus() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOffStatus");
	}

	public boolean isFollowingSchedulesVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FollowingSchedule", timeOut);
	}

	public boolean isFollowingSchedulesNotVisible(int timeOut) {
		return !MobileUtils.isMobElementExists(objectDefinition, testCase, "FollowingSchedule", timeOut);
	}

	public boolean clickOnFollowingSchedules() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FollowingSchedule");
	}

	public boolean isUsingAwayVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UsingAway", timeOut);
	}

	public boolean clickOnUsingAway() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UsingAway");
	}

	public boolean isUsingHomeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UsingHome", timeOut);
	}

	public boolean clickOnUsingHome() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UsingHome");
	}

	public boolean isUsingSleepVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UsingSleep", timeOut);
	}

	public boolean clickOnUsingSleep() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UsingSleep");
	}

	public boolean isScheduleOffButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOffButton");
	}

	public boolean isUseGeofencingTextVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseGeofencingText", timeout);
	}

	public boolean clickOnScheduleOffButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOffButton");
	}

	public boolean isCreateGeofenceSleepSettingsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateGeofenceSleepSettings");
	}

	public boolean clickOnCreateGeofenceSleepSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateGeofenceSleepSettings");
	}

	public boolean isDeletePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmDeletePopup");
	}

	public boolean isDeleteButtonVisibleInDeletePopup() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmDeletePopup");
	}

	public String getDeleteMessageContent() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "PeriodDeleteDialogBoxMessage");
	}
	public boolean ClickOnMoreButton(){

		return MobileUtils.clickOnElement(objectDefinition, testCase, "MoreButton");
	}

	public boolean isMoreButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MoreButton", 3);
	}
}