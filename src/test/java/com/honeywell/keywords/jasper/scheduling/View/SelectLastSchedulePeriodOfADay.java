package com.honeywell.keywords.jasper.scheduling.View;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
import com.honeywell.lyric.utils.InputVariables;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class SelectLastSchedulePeriodOfADay extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true, enteredLoop = false;
	List<WebElement> schedulePeriods = null;
	String tempDay = "";
	int temp = 0;
	List<WebElement> scheduleDayHeaders = null;
	int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;

	public SelectLastSchedulePeriodOfADay(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects the last schedule period of a day$")
	public boolean keywordSteps() throws KeywordException {
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			Random rn = new Random();
			if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@content-desc,'_Everyday')]", testCase,
							5)) {
						schedulePeriods = MobileUtils.getMobElements(testCase, "XPATH",
								"//*[contains(@content-desc,'_Everyday')]");
					}
				} else {
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
							testCase, 5)) {
						schedulePeriods = MobileUtils.getMobElements(testCase, "XPATH",
								"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
					}
				}
			} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
				tempDay = days[rn.nextInt((6 - 0) + 1) + 0];

				CustomDriver driver = testCase.getMobileDriver();
				Dimension dimension = driver.manage().window().getSize();
				int height = dimension.getHeight();
				int width = dimension.getWidth();
				@SuppressWarnings("rawtypes")
				TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					testCase.getMobileDriver().scrollToExact(tempDay);
					while (!MobileUtils.isMobElementExists("XPATH", "//*[contains(@content-desc,'_" + tempDay + "')]",
							testCase, 5)) {
						/*
						 * touchAction.press(width / 2, height /
						 * 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
						 * touchAction.perform();
						 */
						touchAction.press(point(width / 2, height / 2))
								.waitAction(waitOptions(MobileUtils.getDuration(2000))).moveTo(point(width / 2, 82))
								.release().perform();
					}
					schedulePeriods = MobileUtils.getMobElements(testCase, "XPATH",
							"//*[contains(@content-desc,'_" + tempDay + "')]");
				} else {
					desiredDayIndex = Arrays.asList(days).indexOf(tempDay);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
						scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
						lesserDayIndex = Arrays.asList(days).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
						greaterDayIndex = Arrays.asList(days)
								.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
					}
					int i = 0;
					while ((!MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeStaticText[contains(@name,'"
							+ tempDay + "') and contains(@name,'_Time')]", testCase, 5)) && i < 10) {
						enteredLoop = true;
						if (desiredDayIndex > greaterDayIndex) {
							/*
							 * touchAction.press(10, (int) (dimension.getHeight() * .5)) .moveTo(0, (int)
							 * (dimension.getHeight() * -.4)).release().perform();
							 */
							touchAction.press(point(10, (int) (dimension.getHeight() * .5)))
									.moveTo(point(0, (int) (dimension.getHeight() * -.4))).release().perform();
							i++;
						} else if (desiredDayIndex < lesserDayIndex) {
							/*
							 * touchAction.press(10, (int) (dimension.getHeight() * .5)) .moveTo(0, (int)
							 * (dimension.getHeight() * .4)).release().perform();
							 */
							touchAction.press(point(10, (int) (dimension.getHeight() * .5)))
									.moveTo(point(0, (int) (dimension.getHeight() * .4))).release().perform();
							i++;
						} else {
							/*
							 * touchAction.press(10, (int) (dimension.getHeight() * .5)) .moveTo(0, (int)
							 * (dimension.getHeight() * -.2)).release().perform();
							 */
							touchAction.press(point(10, (int) (dimension.getHeight() * .5)))
									.moveTo(point(0, (int) (dimension.getHeight() * -.2))).release().perform();
							break;
						}
					}
					if (enteredLoop) {
						/*
						 * touchAction.press(10, (int) (dimension.getHeight() * .5)) .moveTo(0, (int)
						 * (dimension.getHeight() * -.4)).release().perform();
						 */
						touchAction.press(point(10, (int) (dimension.getHeight() * .5)))
								.moveTo(point(0, (int) (dimension.getHeight() * -.4))).release().perform();
					}

					schedulePeriods = MobileUtils.getMobElements(testCase, "XPATH",
							"//XCUIElementTypeStaticText[contains(@name,'" + tempDay
									+ "') and contains(@name,'_Time')]");
				}
			}

			temp = schedulePeriods.size();
			if (schedulePeriods != null) {
				String periodName = "";
				periodName = schedulePeriods.get(temp - 1).getAttribute("name");
				schedulePeriods.get(temp - 1).click();
				ReportStep_Pass(testCase, "Selected last period: " + periodName);
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to fetch all the periods of a day");
			}

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