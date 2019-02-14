package com.honeywell.keywords.flycatcher.Ventialtion;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.SchedulingScreen;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;

public class FlyCatcherVentialtion {

	public static boolean changeVentilationMode(TestCases testCase, TestCaseInputs inputs, String expectedMode,
			String TimerValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			SchedulingScreen sch = new SchedulingScreen(testCase);
			if (fly.isVentilationIconVisible()) {
				flag = flag && fly.ClickOnVentilationButton();
			} else {
				flag = flag && fly.ClickOnMoreButton();
				flag = flag && fly.ClickOnVentilationButton();
			}
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Chnage ventilation mode  : Cannot change ventilation mode because thermostat is offline");
				return true;
			}
			if (expectedMode.equalsIgnoreCase("Off")) {
				fly.changeVentilationModeToOff();
			} else if (expectedMode.equalsIgnoreCase("On")) {
				fly.changeVentilationModeToOn();
			} else if (expectedMode.equalsIgnoreCase("Auto")) {
				fly.changeVentilationModeToAuto();
			}
			if (TimerValue.equalsIgnoreCase("Default")) {
				if (sch.IsSaveButtonVisible(10)) {
					sch.clickOnSaveButton();
				}
				Keyword.ReportStep_Pass(testCase, "Changing Ventilation Mode without Timer");
				return true;
			} else {
				if (expectedMode.equalsIgnoreCase("On")) {
					Keyword.ReportStep_Pass(testCase, "Venitilation Mode set to " + expectedMode);
					return true;
				} else {
					flag = flag && fly.ClickVentilationTimer();
					flag = flag && fly.ClickEditVentTimer();
					FlyCatcherVentialtion fl = new FlyCatcherVentialtion();
					fl.SetVentilationTimer(testCase, inputs, TimerValue);
				}
			}

		} catch (Exception e) {

		}
		return flag;
	}

	public static boolean verifyVentilationMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		String changedMode = "";
		FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
		if (!fly.isVentilationIconVisible()) {
			flag = flag && fly.ClickOnMoreButton();
			changedMode = fly.getVentialtionMode();
		} else {
			changedMode = fly.getVentialtionMode();
		}
		if (changedMode.equals(expectedMode)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean SetVentilationTimer(TestCases testCase, TestCaseInputs inputs, String TimerValue) {
		boolean flag = true;
		int picker_value = 0;
		FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
		picker_value = fly.getTimerPickerValue();
		int expected_value = Integer.parseInt(TimerValue);
		// int[] pickervalues = fly.getPickercordinates();
		if (picker_value != expected_value) {
			int i = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				while (picker_value < expected_value && i < 15) {
					if (fly.getTimerPickerValue() != expected_value) {
						WebElement ele = fly.getPickerElement();
						int start_x = ele.getLocation().getX();
						int start_y = ele.getLocation().getY();
						@SuppressWarnings("rawtypes")
						TouchAction touch = new TouchAction(testCase.getMobileDriver());
						// touch.press(start_x+ele.getSize().getWidth()/2, start_y).moveTo(0,
						// start_y-10).release().perform();
						touch.press(point(start_x + ele.getSize().getWidth() / 2, start_y))
								.moveTo(point(0, start_y - 10)).release().perform();
						i++;
					} else {
						break;
					}
				}
				while (picker_value > expected_value && i < 15) {
					if (fly.getTimerPickerValue() != expected_value) {
						testCase.getMobileDriver().swipe(530, 850, 530, 976, 1000);
						i++;
					} else {
						break;
					}
				}
			} else {
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				/*
				 * action.press(10, (int) (dimension.getHeight() * .5)) .moveTo(0, (int)
				 * (dimension.getHeight() * -.2)).release().perform();
				 */
				action.press(point(10, (int) (dimension.getHeight() * .5)))
						.moveTo(point(0, (int) (dimension.getHeight() * -.2))).release().perform();
			}
			Keyword.ReportStep_Pass(testCase, "Ventilation timer is set to " + fly.getTimerPickerValue());
			flag = true;
		} else {
			Keyword.ReportStep_Pass(testCase, "Ventilation timer is Already set to " + TimerValue);
			flag = true;
		}
		flag = flag && fly.ClickStartVentTimer();
		return flag;
	}

}
