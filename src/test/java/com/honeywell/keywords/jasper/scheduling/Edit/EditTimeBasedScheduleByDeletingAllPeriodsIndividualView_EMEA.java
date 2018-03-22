package com.honeywell.keywords.jasper.scheduling.Edit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

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
import com.honeywell.screens.SchedulingScreen;

import io.appium.java_client.TouchAction;

public class EditTimeBasedScheduleByDeletingAllPeriodsIndividualView_EMEA extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	List<WebElement> period = null;
	int daysDeleted = 0, counter;
	String temp = "";
	List<WebElement> scheduleDayHeaders = null;
	int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;

	public EditTimeBasedScheduleByDeletingAllPeriodsIndividualView_EMEA(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user tries to delete one of the schedule period of the last two schedule period$")
	public boolean keywordSteps() throws KeywordException {
		try {
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

			for (int i = 0; i < days.length; i++) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					testCase.getMobileDriver().scrollToExact(days[i]);
					counter = 0;
					while (MobileUtils.isMobElementExists("XPATH",
							"//*[contains(@content-desc,'" + "_" + days[i] + "')]", testCase, 5) && counter < 1) {
						period = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@content-desc,'" + "_" + days[i] + "')]");
						temp = period.get(0).getAttribute("name");
						Keyword.ReportStep_Pass(testCase,
								"*************** Deleting period- " + temp + " ***************");
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
							if (!MobileUtils.clickOnElement(fieldObjects, testCase, "PeriodDeleteIcon")) {
								flag = false;
							} else {
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmDeleteButton", 5)) {
									if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmDeleteButton")) {
										flag = false;
									} else {
										if (MobileUtils.isMobElementExists("name", inputs.getInputValue("LOCATION1_DEVICE1_NAME"), testCase)) {
											Keyword.ReportStep_Pass(testCase,
													"Schedule period is deleted when clicked on Delete during Delete operation");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Schedule period is not deleted when clicked on Delete during Delete operation");
										}
									}
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period Confirm Delete button not found");
								}
							}
						} else {
							if (i == 5 || i == 6) {
								Keyword.ReportStep_Pass(testCase, "Period Delete icon not found");
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
									if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
										flag = false;
									}
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Period delete icon");
							}
						}

						Keyword.ReportStep_Pass(testCase,
								"*************** Completed deleting period- " + "" + " ***************");
						touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
						touchAction.perform();
						counter++;
					}
				} else {
					if (i == 5 || i == 6) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					}
					desiredDayIndex = Arrays.asList(days).indexOf(days[i]);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
						scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
						lesserDayIndex = Arrays.asList(days).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
						greaterDayIndex = Arrays.asList(days)
								.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
					}
					int m = 0;
					while ((!MobileUtils.isMobElementExists("name", days[i], testCase, 20)) && m < 10) {
						if (desiredDayIndex > greaterDayIndex) {
							touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
							m++;
						} else if (desiredDayIndex < lesserDayIndex) {
							touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
							m++;
						} else {
							break;
						}
					}
					WebElement tempDay = testCase.getMobileDriver().findElement(By.name(days[i]));
					if (tempDay != null) {
						ReportStep_Pass(testCase, "Located - " + days[i]);
					} else {
						flag = false;
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate - " + days[i]);
						return flag;
					}
					counter = 0;
					while (MobileUtils.isMobElementExists("XPATH", "//*[contains(@name,'" + days[i] + "_')]", testCase,
							5) && counter < 1) {
						period = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@name,'" + days[i] + "_')]");
						temp = period.get(0).getAttribute("name");
						Keyword.ReportStep_Pass(testCase,
								"*************** Deleting period- " + temp + " ***************");
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
							if (!MobileUtils.clickOnElement(fieldObjects, testCase, "PeriodDeleteIcon")) {
								flag = false;
							} else {
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmDeleteButton", 5)) {
									if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmDeleteButton")) {
										flag = false;
									} else {
										Thread.sleep(5000);
										if (MobileUtils.isMobElementExists("name", inputs.getInputValue("LOCATION1_DEVICE1_NAME"), testCase)) {
											Keyword.ReportStep_Pass(testCase,
													"Schedule period is deleted when clicked on Delete during Delete operation");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Schedule period is not deleted when clicked on Delete during Delete operation");
										}
									}
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period Confirm Delete button not found");
								}
							}
						} else {
							if (i == 5 || i == 6) {
								Keyword.ReportStep_Pass(testCase, "Period Delete icon not found");
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
									if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
										flag = false;
									}
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Period delete icon");
							}
						}

						Keyword.ReportStep_Pass(testCase,
								"*************** Completed deleting period- " + "" + " ***************");
						counter++;
					}
				}
			}
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}