package com.honeywell.keywords.jasper.scheduling.View;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import io.appium.java_client.TouchAction;

public class DisplayTimeScheduleDataInIndividualDaysView extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	WebElement period = null, periodTime = null, periodCoolPoint = null, periodHeatPoint = null;
	int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
	List<WebElement> scheduleDayHeaders = null;

	public DisplayTimeScheduleDataInIndividualDaysView(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user shown with Time schedule list in Individual days view$")
	public boolean keywordSteps() throws KeywordException {
		try {
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
			String[] schedulePeriods_EMEA = { "1", "2", "3", "4" };
			String[] schedulePeriods = new String[6];
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
				if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
					schedulePeriods = schedulePeriods_NA;
				} else {
					schedulePeriods = schedulePeriods_EMEA;
				}
			}
			List<String> allowedModes = statInfo.getAllowedModes();

			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

			for (int i = 0; i < days.length; i++) {
				ReportStep_Pass(testCase,
						"***************************** " + days[i] + " *****************************\n");
				System.out.println(days[i]);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					testCase.getMobileDriver().scrollToExact(days[i]);
					System.out.println(days[i]);
					for (int j = 0; j < schedulePeriods.length; j++) {
						while (!MobileUtils.isMobElementExists("XPATH",
								"//*[@content-desc='" + schedulePeriods[j] + "_" + days[i] + "']", testCase, 5)) {
							touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
							touchAction.perform();
						}
						if (!MobileUtils.isMobElementExists("XPATH",
								"//*[@content-desc='" + schedulePeriods[j] + "_" + days[i] + "']", testCase, 5)) {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the schedule period: " + schedulePeriods[j] + "_" + days[i]);
						} else {
							period = testCase.getMobileDriver().findElement(
									By.xpath("//*[@content-desc='" + schedulePeriods[j] + "_" + days[i] + "']"));
							periodTime = period.findElement(By.id("scheduling_period_time"));
							System.out.println(periodTime.getText());
							ReportStep_Pass(testCase, schedulePeriods[j] + "\t");
							if (periodTime != null) {
								ReportStep_Pass(testCase, periodTime.getText() + "\t");
							} else {
								flag = false;
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule period time for: " + schedulePeriods[j] + "_"
												+ days[i]);
							}
							if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
								periodCoolPoint = period.findElement(By.id("scheduling_period_cooling_point"));
								periodHeatPoint = period.findElement(By.id("scheduling_period_heating_point"));
								if (periodCoolPoint != null) {
									ReportStep_Pass(testCase, periodCoolPoint.getText() + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period cool point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
								if (periodHeatPoint != null) {
									ReportStep_Pass(testCase, periodHeatPoint.getText() + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period heat point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
							} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
								periodCoolPoint = period.findElement(By.id("scheduling_period_cooling_point"));
								if (periodCoolPoint != null) {
									ReportStep_Pass(testCase, periodCoolPoint.getText() + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period cool point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
							} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
								periodHeatPoint = period.findElement(By.id("scheduling_period_heating_point"));
								System.out.println(periodHeatPoint.getText());
								if (periodHeatPoint != null) {
									ReportStep_Pass(testCase, periodHeatPoint.getText() + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period heat point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
							}

						}
					}

				} else {
					for (int j = 0; j < schedulePeriods.length; j++) {
						desiredDayIndex = Arrays.asList(days).indexOf(days[i]);
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
							scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase,
									"ScheduleDayHeader");
							lesserDayIndex = Arrays.asList(days)
									.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
							greaterDayIndex = Arrays.asList(days).indexOf(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
						}
						int m = 0;
						while ((!MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + days[i] + "_"
										+ schedulePeriods[j] + "']",
								testCase, 5)) && m < 10) {
							if (desiredDayIndex > greaterDayIndex) {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
								m++;
							} else if (desiredDayIndex < lesserDayIndex) {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
								m++;
							} else {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.9)).release().perform();
								m++;
							}
						}

						WebElement period = testCase.getMobileDriver()
								.findElement(By.xpath("//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
										+ days[i] + "_" + schedulePeriods[j] + "']"));

						if (period == null) {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the schedule period: " + days[i] + "_" + schedulePeriods[j]);
						} else {

							WebElement periodTime = testCase.getMobileDriver()
									.findElement(By.name(days[i] + "_" + schedulePeriods[j] + "_Time"));

							ReportStep_Pass(testCase, schedulePeriods[j] + "\t");
							if (periodTime != null) {
								ReportStep_Pass(testCase, periodTime.getAttribute("value") + "\t");
							} else {
								flag = false;
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule period time for: " + schedulePeriods[j] + "_"
												+ days[i]);
							}
							if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {

								periodHeatPoint = testCase.getMobileDriver()
										.findElement(By.name(days[i] + "_" + schedulePeriods[j] + "_HeatTemperature"));
								periodCoolPoint = testCase.getMobileDriver()
										.findElement(By.name(days[i] + "_" + schedulePeriods[j] + "_CoolTemperature"));

								if (periodCoolPoint != null) {
									ReportStep_Pass(testCase, periodCoolPoint.getAttribute("value") + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period cool point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
								if (periodHeatPoint != null) {
									ReportStep_Pass(testCase, periodHeatPoint.getAttribute("value") + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period heat point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}

							} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
								periodCoolPoint = testCase.getMobileDriver()
										.findElement(By.name(days[i] + "_" + schedulePeriods[j] + "_CoolTemperature"));
								if (periodCoolPoint != null) {
									ReportStep_Pass(testCase, periodCoolPoint.getAttribute("value") + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period cool point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
							} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
								periodHeatPoint = testCase.getMobileDriver()
										.findElement(By.name(days[i] + "_" + schedulePeriods[j] + "_HeatTemperature"));
								if (periodHeatPoint != null) {
									ReportStep_Pass(testCase, periodHeatPoint.getAttribute("value") + "\t");
								} else {
									flag = false;
									ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate schedule period heat point for: " + schedulePeriods[j]
													+ "_" + days[i]);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}

		flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
