package com.honeywell.lyric.das.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class DASCommandControlUtils {

	public static boolean changeStatus(TestCases testCase, String statusToSelect, TestCaseInputs inputs) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if (testCase.getMobileDriver().getPlatformName().contains("Android")){
		flag = flag & sc.clickOnState(statusToSelect, inputs);
		} else{
		testCase.getMobileDriver().findElementById(statusToSelect).click();
		}
		return flag;
	}

	public static boolean verifystate(TestCases testCase, String stateToVerify) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		flag = flag & sc.verifystate(stateToVerify);
		return flag;
	}

	public static boolean verifyTimeStamp(TestCases testCase, String stateTimeStamp) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if (stateTimeStamp.contains(sc.getTimeStamp())) {
			Keyword.ReportStep_Pass(testCase, "Timestamp verified successfully");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Timestamp not matched");
		}
		return flag;
	}

	public static boolean verifySetToOffPopUpIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if (sc.isSetToOffPopupVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Set to Off Pop Up is still displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Set to Off Pop Up is not displayed");
		}
		return flag;
	}

	public static boolean verifySwitchToAwayPopupIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if (sc.isSwitchToAwayPopupVisible(5)) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Switch to away popup is still displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Switch to away popup is not displayed");
		}
		return flag;
	}

	public static boolean verifySwitchToNightPopupIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		if (sc.isSwitchToNightPopupVisible(5)) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Switch to night popup is still displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Swith to night popup is not displayed");
		}
		return flag;
	}

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForProgressBarToComplete method waits until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-19
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "TIMER PROGRESS BAR": {
							if (sc.isCountDownTimerVisible() && sc.isSwitchingToTextVisible()) {
								System.out.println("Waiting for timer progress bar loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "PROGRESS BAR": {
							if (sc.isProgressBarVisible()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
								return true;
							} else {
								return false;
							}
						}
						case "LOADING PROGRESS TEXT": {
							if(sc.isLoadingProgressVisible()) {
								System.out.println("Waiting for loading progress bar text to disappear");
								return true;
							} else {
								return false;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + elementProgressBar);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Progress bar loading spinner did not disapper after waiting for " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
}