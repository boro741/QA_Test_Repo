package com.honeywell.screens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class VacationHoldScreen extends MobileScreens {

	private static final String screenName = "VacationHold";
	boolean flag = false;

	public VacationHoldScreen(TestCases testCase) {
		super(testCase, screenName);

	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
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
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, modeElementName);
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInMode");
		return flag;
	}

	public String hourInTimePicker() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerHour");
	}

	public String minuteInTimePicker() {
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
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndDate");
	}

	public boolean clickOnStartTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromTime");
	}

	public boolean clickOnEndTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndTime");
	}

	public String getStartDate() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FromDate");
	}

	public String getEndDate() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EndDate");
	}

	public String getStartTime() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FromTime");
	}

	public String getEndTime() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EndTime");
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
						.equalsIgnoreCase("1")) {
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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MonthCalendarOption");
	}

	public boolean clickOnVacationHoldSetpointSettings() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationHoldSetpointRow")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSetpointRow");
		} else {
			return false;
		}
	}

	public String getHeatSetPointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HeatSetPointRound");
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
		return MobileUtils.getFieldValue(objectDefinition, testCase, "PrimaryCardSetpoint");
	}

	public boolean editSetPointDownInPrimaryCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrimaryCardSetPointDown");
	}

	public String getCoolSetPointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CoolSetPointRound");
	}

}
