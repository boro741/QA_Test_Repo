package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class SchedulingScreen extends MobileScreens {

	private static final String screenName = "ScheduleScreen";

	public SchedulingScreen(TestCases testCase) {
		super(testCase, screenName);
		// osPopUps = new OSPopUps(testCase);
	}

	public boolean clickOnCloseButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CrossButton");
	}

	public boolean isCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CrossButton", timeOut);
	}

	public boolean isCreateScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateScheduleButton", timeOut);
	}

	public boolean clickOnCreateScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateScheduleButton");
	}

	public boolean isScheduleOverlayVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOffOverlay", timeOut);
	}

	public boolean clickOnScheduleOverlay() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOffOverlay");
	}

	public boolean clickOnScheduleOptionsButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOptionsButton");
	}

	public boolean isSwitchToGeofenceButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToGeofencingButton", timeOut);
	}

	public boolean clickOnSwitchToGeofenceButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToGeofencingButton");
	}

	public boolean clickOnUseGeofencingText() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UseGeofencingText");
	}

	public boolean clickOnLearnMoreButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LearnMoreButton");
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

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
	}

	public boolean isAwayTemperatureHeaderMultiTemperatureVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AwayTemperatureHeaderMultiTemperature",
				timeOut);
	}

	public boolean isAwayTemperatureHeaderSingleTemperatureVisble(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AwayTemperatureHeaderSingleTemperature",
				timeOut);
	}

	public boolean clickOnSkipSleepButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipSleepButton");
	}

	public boolean clickOnNoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
	}

	public boolean clickOnYesButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButton");
	}

	public boolean isDoneButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButton", timeOut);
	}

	public boolean clickOnDoneButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
	}

	public boolean isCheckBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CheckBox", timeOut);
	}

	public List<WebElement> getAllCheckBoxElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CheckBox");
	}

	public boolean isCopyButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CopyButton", timeOut);
	}

	public boolean clickOnCopyButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CopyButton");
	}

	public boolean isSkipButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButton", timeOut);
	}

	public boolean clickOnSkipButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButton");
	}

	public boolean isTimeScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleButton", timeOut);
	}

	public boolean clickOnTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleButton");
	}

	public boolean isTimeOptionVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeOption", timeOut);
	}

	public boolean clickOnTimeOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeOption");
	}

	public boolean isScheduleOffOverlayVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleOffOverlay", timeOut);
	}

	public boolean clickOnScheduleOffOverlay() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ScheduleOffOverlay");
	}

	public boolean clickOnCreateNewTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateNewTimeScheduleButton");
	}

	public boolean clickOnSwitchToTimeScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToTimeScheduleButton");
	}

	public boolean isEverydayScheduleButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayScheduleButton", timeOut);
	}

	public boolean clickOnEverydayScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EverydayScheduleButton");
	}

	public WebElement getEverydayWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayWake");
	}

	public WebElement getEverydayAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayAway");
	}

	public WebElement getEverydayHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydayHome");
	}

	public WebElement getEverydaySleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EverydaySleep");
	}

	public boolean clickOnSaveButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
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

	public boolean isTimeChooserHeaderVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeChooserHeader", timeOut);
	}

	public boolean isTempChooserHeaderVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TempChooserHeader", timeOut);
	}

	public boolean clickOnWeekdayandWeekendScheduleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WeekdayandWeekendScheduleButton");
	}

	public WebElement getWeekdayWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayWake");
	}

	public WebElement getWeekdayAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayAway");
	}

	public WebElement getWeekdayHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdayHome");
	}

	public WebElement getWeekdaySleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekdaySleep");
	}

	// WeekendWake
	public WebElement getWeekendWakeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendWake");
	}

	public WebElement getWeekendAwayElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendAway");
	}

	public WebElement getWeekendHomeElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendHome");
	}

	public WebElement getWeekendSleepElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WeekendSleep");
	}

	public boolean isWeekendWakeElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendWake", timeOut);
	}

	public boolean isWeekendAwayElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendAway", timeOut);
	}

	public boolean isWeekendHomeElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendHome", timeOut);
	}

	public boolean isWeekendSleepElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendSleep", timeOut);
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

	public boolean isConfirmChangeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmChangeButton", timeOut);
	}

	public boolean clickOnConfirmChangeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmChangeButton");
	}

	public boolean isCancelChangeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelChangeButton", timeOut);
	}

	public boolean clickOnCancelChangeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelChangeButton");
	}

	public boolean isBackButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public WebElement getElementFromObjectDefinitions(String locatorValueinObjectDefinition) {
		return MobileUtils.getMobElement(objectDefinition, testCase, locatorValueinObjectDefinition);
	}

	public boolean isElementFromObjectDefinitionsVisible(String locatorValueinObjectDefinition, int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, locatorValueinObjectDefinition, timeOut);
	}

	public boolean clickOnElementFromObjectDefinitions(String locatorValueinObjectDefinition) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, locatorValueinObjectDefinition);
	}

	public String getTimeChooserValue() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooser").getAttribute("value");
	}

	public String getTimeChooserEndTimeValue() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "TimeChooserEndTime").getAttribute("value");
	}

	public boolean isAddImageButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddImage", timeOut);
	}

	public boolean clickOnAddImageButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddImage");
	}

	public WebElement getAddImageElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AddImage");
	}

	public boolean isEverydayTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EverydayTime", timeOut);
	}

	public List<WebElement> getEverydayTimeElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "EverydayTime");
	}

	public boolean isWeekday1ElementVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Weekday1", timeOut);
	}

	public boolean isWeekDayTimeListVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekdayTimeList", timeOut);
	}

	public List<WebElement> getWeekdayTimeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayTimeList");
	}

	public boolean isWeekendTimeListVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeekendTimeList", timeOut);
	}

	public List<WebElement> getWeekdendTimeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendTimeList");
	}

	public List<WebElement> getAddImageElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "AddImage");
	}

	public List<WebElement> getSchedulePeriodHeatSetPointElement() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SchedulePeriodHeatSetPoint");
	}

	public String getValueOfEverydayHeatTemperatureElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "name", "Everyday_" + index + "_HeatTemperature")
				.getAttribute("value");
	}

	public List<WebElement> getWeekdayHeatSetpointListEMEAElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekdayHeatSetpointListEMEA");
	}

	public String getValueOfWeekdayHeatTemperatureElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "name", "Monday - Friday_" + index + "_HeatTemperature")
				.getAttribute("value");
	}

	public List<WebElement> getWeekdendHeatSetpointListEMEAElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "WeekendHeatSetpointListEMEA");
	}

	public String getValueOfWeekendHeatTemperatureElementAtIndex(int index) {
		return MobileUtils.getMobElement(testCase, "name", "Saturday - Sunday_" + index + "_HeatTemperature")
				.getAttribute("value");
	}

	public boolean clickOnTimeScheduleStartTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleStartTime");
	}

	public boolean isTimeScheduleEndTimeVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleEndTime", timeOut);
	}

	public String getTimeScheduleEndTimeValue() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "TimeScheduleEndTime").getAttribute("value");
	}

	public boolean clickOnTimeScheduleEndTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleEndTime");
	}

	public boolean clickOnStartButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "StartButton");
	}

	public boolean isTimeScheduleEndTimeCellVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeScheduleEndTimeCell", timeOut);
	}

	public boolean clickOnTimeScheduleEndTimeCell() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TimeScheduleEndTimeCell");
	}

	public boolean isAMPMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AMPMLabel", timeOut);
	}

	public boolean isAMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AMLabel", timeOut);
	}

	public boolean isPMLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PMLabel", timeOut);
	}

	public boolean isTimePickerVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimePicker", timeOut);
	}

	public boolean setValueToTimePicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimePicker", value);
	}

	public boolean clickOnOkButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}

	public boolean isTimeHoursVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeHours", timeOut);
	}

	public boolean isTimeMinutesVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeMinutes", timeOut);
	}

	public boolean isTimeAMPMVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TimeAMPM", timeOut);
	}

	public boolean setValueToTimeHoursPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeHours", value);
	}

	public boolean setValueToTimeMinutesPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeMinutes", value);
	}

	public boolean setValueToTimeAMPMPicker(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "TimeAMPM", value);
	}

	public String getCoolSetPointChooserSetPointsValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CoolSetPointChooser")
					.findElement(By.id("scheduling_period_temp_point")).getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CoolSetPoints").getAttribute("value");
		}
	}

	public WebElement getCoolSetPointUpButton() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CoolSetPointChooser")
					.findElement(By.id("scheduling_temp_chooser_up"));
		} else {
			return testCase.getMobileDriver().findElements(By.name("coolTemparatureUpperButton")).get(0);
		}
	}

	public WebElement getCoolSetPointDownButton() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CoolSetPointChooser")
					.findElement(By.id("scheduling_temp_chooser_down"));
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CoolDecrement");
		}
	}

	public List<WebElement> getCoolSetPointsElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CoolSetPoints");
	}
	
	public List<WebElement> getCoolIncrementElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CoolIncrement");
	}
	
	public List<WebElement> getCoolDecrementElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "CoolDecrement");
	}
}
