package com.honeywell.lyric.das.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AlarmScreen;

public class DASAlarmUtils {
	private static HashMap<String, MobileObject> fieldObjects;

	// Activate device screen actions
	public static boolean timeOutForNoSensorAction(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (alarmScreen.isAlarmScreenDisplayed()) {
							inputs.setInputValue("ALARM_TIME",
									LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
							return true;
						} else {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Switching to completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Switching to complete did not complete after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean timeForExitDelayToEndWhenAppInBackground(TestCases testCase, TestCaseInputs inputs,
			int delay) {
		boolean flag = true;
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(delay, TimeUnit.SECONDS);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (alarmScreen.isAlarmScreenDisplayed()) {
							return true;
						} else {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Exit delay completed");
			}
		} catch (TimeoutException e) {
			flag = true;
			Keyword.ReportStep_Pass(testCase, "Exit delay completed");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean confirmDismissAlarm(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmDismissButton")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "DismissAlarmPopupOk", 10)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DismissAlarmPopupOk");
		DASSolutionCardUtils.waitForDismissProcessRequest(testCase);
		int i = 0;
		while (i < 5 && MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmDismissButton", 10)) {
			Keyword.ReportStep_Pass(testCase, "wait For Dismiss ProcessRequest");
			i++;
		}
		inputs.setInputValue("ALARM_DISMISSED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		Keyword.ReportStep_Pass(testCase, "ALARM_DISMISSED_TIME " + inputs.getInputValue("ALARM_DISMISSED_TIME"));
		inputs.setInputValue("HOME_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));

		return flag;
	}

	public static boolean verifyAlarmScreenDisplayed(TestCases testCase) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isAlarmScreenDisplayed();

	}

	public static boolean verifyProgressDisplayed(TestCases testCase) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isPleaseWaitDisplayed();

	}

	public static boolean clickOnDismissAlarm(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.clickOnDismissAlarm();
	}

	public static boolean clickOnCall(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.clickOnCall();
	}

	// Entry delay screen

	public static boolean verifyEntryDelayScreenDisplayed(TestCases testCase) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isEntryDelayScreenDisplayed();
	}

	public static boolean clickOnSwitchToHome(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("HOME_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		return alarmScreen.clickOnSwitchToHome(60);
	}

	public static boolean clickOnSwitchToNight(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("NIGHT_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		return alarmScreen.clickOnSwitchToNight();
	}

	public static boolean clickOnAlarm_NavigateBack(TestCases testCase) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.clickOnAlarm_NavigateBack();
	}

	public static boolean clickOnAttention(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("ALARM_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		inputs.setInputValue("ATTENTION_ALARM_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		return alarmScreen.clickOnAttention();
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
			AlarmScreen as = new AlarmScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (as.isLoadingProgressBarDisplayed()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
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
