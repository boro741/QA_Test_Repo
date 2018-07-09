package com.honeywell.lyric.das.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;

public class CameraUtils {

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForProgressBarToComplete method waits until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-06-07
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
			CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
			CameraSettingsScreen css = new CameraSettingsScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "LOADING SPINNER BAR": {
							if (cs.isLoadingSpinnerVisible()) {
								System.out.println("Waiting for Verifying loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "RETRY IN LOADING SNAPSHOT SPINNER": {
							if(!css.isRetryInLoadingSpanshotVisible()) {
								System.out.println("Waiting for Verifying loading snapshot spinner to disappear");
								return false;
							} else {
								return true;
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
					"Progress bar loading spinner did not disapper after waiting for: " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Minimize and Maximize the app</h1>
	 * <p>
	 * The minimizeAndMaximizeTheApp method is to minimize and maximize the app
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-01
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app is minimized and maximized. Returns
	 *         'false' if unsuccessful.
	 */
	public static boolean minimizeAndMaximizeTheApp(TestCases testCase) {
		boolean flag = true;
		MobileUtils.minimizeApp(testCase, 5);
		return flag;
	}
}
