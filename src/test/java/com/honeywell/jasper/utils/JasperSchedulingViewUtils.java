package com.honeywell.jasper.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

import io.appium.java_client.TouchAction;

public class JasperSchedulingViewUtils {
	public static boolean selectIndividualDaysViewOrGroupedDaysView(TestCases testCase, String viewDays) {
		boolean flag = true;
		SchedulingScreen schl = new SchedulingScreen(testCase);
		try {
			if (schl.isTimeScheduleButtonVisible(5)){
				flag = flag && schl.clickOnTimeScheduleButton();
			}
			if (!schl.isViewByIndividualDaysVisible(5)) {
				flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
				if (!schl.isViewByIndividualDaysVisible(5)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "App not in schedule view screen");
				}
			}
			if ("SINGLE DAY".equalsIgnoreCase(viewDays)) {
				if (schl.isViewByIndividualDaysVisible(5)) {
					if (!schl.clickOnViewByIndividualDays()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Individual days");
					}
				}
			} else {
				if (schl.isViewByGroupedDaysVisible(5)) {
					if (!schl.clickOnViewByGroupedDays()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Grouped days");
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean selectAnySchedulePeriodOfADay(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			String schedulePeriodToSelect = "", jasperStatType = "";
			WebElement period = null;
			SchedulingScreen schl = new SchedulingScreen(testCase);
			int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
			List<WebElement> scheduleDayHeaders = null;
			String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
			String[] schedulePeriods_EMEA = { "1", "2", "3", "4" };
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			Random rn = new Random();

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
				jasperStatType = statInfo.getJasperDeviceType();
			}
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
					if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
						if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
							schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1] + "_Everyday";
						} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
							schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1] + "_Everyday";
						}
					} else if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")) {
						schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1] + "_Everyday";
					}

				} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
					if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
						if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
							schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1] + "_"
									+ days[rn.nextInt((6 - 0) + 1) + 0];
						} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
							schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1] + "_"
									+ days[rn.nextInt((6 - 0) + 1) + 0];
						}
					} else if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")) {
						schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1] + "_"
								+ days[rn.nextInt((6 - 0) + 0) + 1];
					}
				}
			} else {
				if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
					if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
						if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
							schedulePeriodToSelect = "Everyday_" + schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1];
							System.out.println(schedulePeriodToSelect);
						} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
							schedulePeriodToSelect = "Everyday_" + schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1];
							System.out.println(schedulePeriodToSelect);
						}
					} else if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")) {
						schedulePeriodToSelect = "Everyday_" + schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1];
					}

				} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
					if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
						if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
							schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 1) + 0] + "_"
									+ schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1];
						} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
							schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 1) + 0] + "_"
									+ schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1];
						}
					} else if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")) {
						schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 0) + 1] + "_"
								+ schedulePeriods_NA[rn.nextInt((3 - 0) + 0) + 1];
					}
				}
			}

			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriodToSelect + "']",
						testCase, 5)) {
					testCase.getMobileDriver().scrollToExact(schedulePeriodToSelect.split("_")[1]);
					while (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriodToSelect + "']",
							testCase, 5)) {
						touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
						touchAction.perform();
					}
				}
				period = testCase.getMobileDriver()
						.findElement(By.xpath("//*[@content-desc='" + schedulePeriodToSelect + "']"));
			} else {
				desiredDayIndex = Arrays.asList(days).indexOf(schedulePeriodToSelect.split("_")[0]);
				if (schl.isScheduleDayHeaderVisible(5)) {
					scheduleDayHeaders = schl.getScheduleDayHeaderElements();
					lesserDayIndex = Arrays.asList(days).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
					greaterDayIndex = Arrays.asList(days)
							.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
				}
				int i = 0;
				while ((!MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + schedulePeriodToSelect + "']", testCase,
						5)) && i < 10) {
					if (desiredDayIndex > greaterDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					} else if (desiredDayIndex < lesserDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
						i++;
					} else {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					}
				}
				period = testCase.getMobileDriver().findElement(By.name(schedulePeriodToSelect));

			}
			if (period != null) {
				try {
					period.click();
					Keyword.ReportStep_Pass(testCase, "Selected period: " + schedulePeriodToSelect);
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to select the period: " + schedulePeriodToSelect);
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to locate the period: " + schedulePeriodToSelect);
			}
		}catch (Exception e){

		}

		return flag;
	}


}