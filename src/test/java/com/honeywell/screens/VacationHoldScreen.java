package com.honeywell.screens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class VacationHoldScreen extends MobileScreens {

	private static final String screenName = "VacationHold";
	boolean flag = true;

	public VacationHoldScreen(TestCases testCase) {
		super(testCase, screenName);

	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isVacationsLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationsLabel");
	}

	public boolean clickOnVacationsLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationsLabel");
	}

	public boolean isStartAndEndDateDisplayed() {
		flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "FromDate");
		flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "ToDate");
		return flag;
	}

	public boolean isVacationLabelPresentOnSolutionCard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationOnPrimaryCard");
	}

	public boolean isSystemIsOffOnSolutionCard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatOff");
	}

	public boolean isAdhocOverrideShown() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AdhocOnPrimaryCard");
	}

	public boolean isVacationEndCautionMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationEndCautionMessage");
	}

	public boolean isStartAndEndTimeDisplayed() {
		flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "FromTime");
		flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "ToTime");
		return flag;
	}

	public boolean clickOnModeText() {
		flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatMode");
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatMode");
		return flag;
	}

	public boolean clickOnSystemModes(String modeElementName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, modeElementName)) {
				flag &= MobileUtils.clickOnElement(objectDefinition, testCase, modeElementName);
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, modeElementName)) {
				flag &= MobileUtils.clickOnElement(objectDefinition, testCase, modeElementName);
			} else if (testCase.getMobileDriver().findElement(By.id(modeElementName.toUpperCase())).isEnabled()) {
				testCase.getMobileDriver().findElement(By.id(modeElementName.toUpperCase())).click();
			} else {
				flag = false;
			}
		}
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInMode");
		return flag;
	}

	public String hourInTimePicker() {
		System.out.println(MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerHour"));
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerHour");
	}

	public String minuteInTimePicker() {
		System.out.println(MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerMinute"));
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerMinute");
	}

	@SuppressWarnings("deprecation")
	public String increaseTimerWithStartOrEndTime(String time, String incrementalTime) throws ParseException {
		int newHour;
		int newMinute;
		int additionalMinute;
		String newTime = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date d = df.parse(time);
			System.out.println(d);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			additionalMinute = Integer.parseInt(incrementalTime);
			cal.add(Calendar.MINUTE, additionalMinute);
			newTime = df.format(cal.getTime());
			newHour = new SimpleDateFormat("HH mm ss").parse(newTime).getHours();
			newMinute = new SimpleDateFormat("HH mm ss").parse(newTime).getMinutes();
			testCase.getMobileDriver().findElementByAccessibilityId(String.valueOf(newHour)).click();
			testCase.getMobileDriver().findElementByAccessibilityId(String.valueOf(newMinute)).click();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("Error in setting value to time"));
		}
		return newTime;
	}

	public boolean isVacationHoldHeaderPresentUnderLocationOnDashBoard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationOnLocationHeader");
	}

	public String getTextOfVacationOnTheDashboard() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "VacationOnLocationHeader");
	}

	public boolean isStartAndEndDateEnabled() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FromDate")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ToDate")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean clickOnStartDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromDate");
	}

	public boolean clickOnEndDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ToDate");
	}

	public boolean clickOnStartTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromTime");
	}

	public boolean clickOnEndTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndTime");
	}

	public String getStartDate() {
		if (testCase.getPlatform().contains("IOS")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "FromDate").split(",")[0]
					+ MobileUtils.getFieldValue(objectDefinition, testCase, "FromDate").split(",")[1] + ","
					+ MobileUtils.getFieldValue(objectDefinition, testCase, "FromDate").split(",")[2];
		} else
			return MobileUtils.getFieldValue(objectDefinition, testCase, "FromDate");
	}

	public String getEndDate() {
		if (testCase.getPlatform().contains("IOS")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "ToDate").split(",")[0]
					+ MobileUtils.getFieldValue(objectDefinition, testCase, "ToDate").split(",")[1] + ","
					+ MobileUtils.getFieldValue(objectDefinition, testCase, "ToDate").split(",")[2];
		} else
			return MobileUtils.getFieldValue(objectDefinition, testCase, "ToDate");
	}

	public String getStartTime() {
		if (testCase.getPlatform().contains("IOS")) {
			System.out.println(MobileUtils.getFieldValue(objectDefinition, testCase, "FromTime"));
			return MobileUtils.getFieldValue(objectDefinition, testCase, "FromTime").split(",")[3].trim();
		} else
			return MobileUtils.getFieldValue(objectDefinition, testCase, "FromTime");
	}

	public String getEndTime() {
		if (testCase.getPlatform().contains("IOS")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "ToTime").split(",")[3].trim();
		} else
			return MobileUtils.getFieldValue(objectDefinition, testCase, "ToTime");
	}

	public boolean isCancelButtonInTimePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInTimePopup");
	}

	public boolean clickOnCancelButtonInTimePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInTimePopup");
	}

	public boolean isOKButtonInTimePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInTimePopup");
	}

	public boolean clickOnOKButtonInTimePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInTimePopup");
	}

	public boolean endVacationButtonInSolutionCard() {
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "VacationOnPrimaryCard");
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "VacationEndInSolutionCard");
		return flag;
	}

	public boolean isVacationSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationHoldSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitch").getAttribute("value")
						.equalsIgnoreCase("1")
						|| MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitch")
								.getAttribute("value").equalsIgnoreCase("true")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find the Vacation Switch");
		}
	}

	public boolean toggleVacationDetectionSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSwitch");
	}

	public boolean clickOnVacationHoldSwitch() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSwitch");
	}

	public boolean isCalendarPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CalendarPopup");
	}

	public boolean isCancelButtonInCalendarPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInCalendarPopup");
	}

	public boolean clickOnCancelButtonInCalendarPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInCalendarPopup");
	}

	public boolean isOKButtonInCalendarPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInCalendarPopup");
	}

	public boolean clickOnOKButtonInCalendarPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInCalendarPopup");
	}

	public boolean isStatInVacationScreenVisible(String statName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// *[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_subtext_primary_text_view'
			// and @text='Jasper NA']
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_subtext_primary_text_view' and @text='"
							+ statName + "']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", statName + "_cell", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnStatInVacationScreen(String statName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_subtext_primary_text_view' and @text='"
							+ statName + "']",
					testCase)) {
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//*[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_subtext_primary_text_view' and @text='"
								+ statName + "']");
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", statName + "_cell", testCase)) {
				MobileUtils.clickOnElement(testCase, "NAME", statName + "_cell");
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public String getHeatSetPointValue() {
		String heatSetPoint = MobileUtils.getFieldValue(objectDefinition, testCase, "HeatSetPointRound");
		System.out.println("########heatSetPoint: " + heatSetPoint);
		return heatSetPoint;
	}

	public String getCoolSetPointValue() {
		String coolSetPoint = MobileUtils.getFieldValue(objectDefinition, testCase, "CoolSetPointRound");
		System.out.println("########coolSetPoint: " + coolSetPoint);
		return coolSetPoint;
	}

	public boolean isCoolToSectionInStatScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolToSectionInStatScreen");
	}

	public boolean isHeatToSectionInStatScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatToSectionInStatScreen");
	}

	public boolean isHeatSetPointStepperUpButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatSetPointStepperUpButton");
	}

	public boolean clickOnHeatSetPointStepperUpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatSetPointStepperUpButton");
	}

	public boolean isHeatSetPointStepperDownButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatSetPointStepperDownButton");
	}

	public boolean clickOnHeatSetPointStepperDownButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatSetPointStepperDownButton");
	}

	public boolean isCoolSetPointStepperUpButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolSetPointStepperUpButton");
	}

	public boolean clickOnCoolSetPointStepperUpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolSetPointStepperUpButton");
	}

	public boolean isCoolSetPointStepperDownButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolSetPointStepperDownButton");
	}

	public boolean clickOnCoolSetPointStepperDownButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolSetPointStepperDownButton");
	}

	public boolean editHeatSetPointUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatSetPointRoundChooser");
	}

	public boolean editCoolSetPointUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolSetPointRoundChooser");
	}

	public boolean editSetPointUpInPrimaryCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrimaryCardSetPointUp");
	}

	public boolean isEndVacationModePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EndVacationModePopupTitle");
	}

	public boolean isCancelButtonInEndVacationModePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInEndVacationModePopup");
	}

	public boolean clickOnCancelButtonInEndVacationModePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInEndVacationModePopup");
	}

	public boolean isEndButtonInEndVacationPopupModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EndButtonInEndVacationPopupMode");
	}

	public boolean clickOnEndButtonInEndVacationPopupMode() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndButtonInEndVacationPopupMode");
	}

	public String getPrimaryCardValue() {
		System.out.println("%%%%%%%" + MobileUtils.getFieldValue(objectDefinition, testCase, "PrimaryCardSetpoint"));
		return MobileUtils.getFieldValue(objectDefinition, testCase, "PrimaryCardSetpoint");
	}

	public boolean editSetPointDownInPrimaryCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrimaryCardSetPointDown");
	}

	public boolean navigateBackAndForthInVacationsScreen(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
			if (isVacationsLabelVisible(10)) {
				flag = flag & clickOnVacationsLabel();
			}
		}
		return flag;
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isVacationSwitchInStatScreenEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationHoldSwitchInStatScreen", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitchInStatScreen").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitchInStatScreen")
						.getAttribute("value").equalsIgnoreCase("1")
						|| MobileUtils.getMobElement(objectDefinition, testCase, "VacationHoldSwitchInStatScreen")
								.getAttribute("value").equalsIgnoreCase("true")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find the Vacation Switch in Stat screen");
		}
	}

	public boolean toggleVacationDetectionSwitchInStatScreen(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSwitchInStatScreen");
	}

	public boolean isNoSettingsLabelDisplayedInStatSectionInVacationScreen(TestCases testCase, String labelNoSettings) {
		if (MobileUtils.getFieldValue(objectDefinition, testCase, "NoSettingsLabelInVacationsSettings")
				.equalsIgnoreCase(labelNoSettings)) {
			Keyword.ReportStep_Pass(testCase, "Displayed the stat status: " + labelNoSettings);
			return true;
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to display the stat status: " + labelNoSettings);
			return false;
		}
	}

	public boolean isCoolToOrHeatToLabelInVacationSettingsVisible(TestCases testCase) {
		String labelCoolTo = "Cool to";
		String labelHeatTo = "Heat to";
		if (MobileUtils.getFieldValue(objectDefinition, testCase, "CoolToOrHeatToLabelInVacationSettings")
				.contains(labelCoolTo)
				|| MobileUtils.getFieldValue(objectDefinition, testCase, "CoolToOrHeatToLabelInVacationSettings")
						.contains(labelHeatTo)) {
			Keyword.ReportStep_Pass(testCase, "Displayed the stat status: " + labelCoolTo);
			return true;
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to display the stat status: " + labelCoolTo);
			return false;
		}
	}

	public boolean isDevicesListInVacationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DevicesListInVactionScreen");
	}

	public List<WebElement> getDevicesListInVacationScreen() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "DevicesListInVactionScreen");
	}
}