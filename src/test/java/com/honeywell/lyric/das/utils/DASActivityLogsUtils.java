package com.honeywell.lyric.das.utils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.ActivityLogsScreen;

public class DASActivityLogsUtils {

	public static boolean openActivityLogs(TestCases testCase) throws Exception {
		boolean flag = true;
		ActivityLogsScreen al = new ActivityLogsScreen(testCase);
		if (al.isOpenActivityLogsIconVisible(15)) {
			al.clickOnOpenActivityLogsIcon();
		}
		return flag;
	}

	public static boolean closeActivityLogs(TestCases testCase) throws Exception {
		boolean flag = true;
		ActivityLogsScreen al = new ActivityLogsScreen(testCase);
		if (al.isCloseActivityLogsIconVisible()) {
			al.clickOnCloseActivityLogsIcon();
		}
		return flag;
	}

	public static String[][] fetchActivityList(TestCases testCase) {
		String[][] eventsList;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ActivityLogs");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			List<WebElement> activityLogTitleElements = MobileUtils.getMobElements(fieldObjects, testCase,
					"ActivityLogHeader");
			List<WebElement> activityLogSubTitleElements = MobileUtils.getMobElements(fieldObjects, testCase,
					"ActivityLogSubHeader");
			List<WebElement> activityLogTimeElements = MobileUtils.getMobElements(fieldObjects, testCase,
					"ActivityLogTime");
			System.out.println("#########activityLogTitleElements.size(): " + activityLogTitleElements.size());
			System.out.println("#########activityLogSubTitleElements.size(): " + activityLogSubTitleElements.size());
			System.out.println("#########activityLogTimeElements.size(): " + activityLogTimeElements.size());
			eventsList = new String[activityLogTitleElements.size()][3];
			int i = 0;
			for (WebElement ele : activityLogTitleElements) {
				eventsList[i][0] = ele.getAttribute("text");
				i++;
				// activityLogHeaders.add(ele.getAttribute("text"));
			}
			i = 0;
			for (WebElement ele : activityLogSubTitleElements) {
				eventsList[i][1] = ele.getAttribute("text");
				i++;
				// activityLogSubHeaders.add(ele.getAttribute("text"));
			}
			i = 0;
			for (WebElement ele : activityLogTimeElements) {
				eventsList[i][2] = ele.getAttribute("text");
				i++;
				// activityLogTime.add(ele.getAttribute("text"));
			}
			// expectedTime =
			// hour12Format.parse(hour12Format.format(timeFormat.parse(deviceLocationTime)));
			// System.out.println("#########1. expectedTime: " + expectedTime);
		} else {

			int events = 0;
			if (MobileUtils.isMobElementExists("xpath", "//*[contains(@name,'_Cell')]", testCase)) {
				events = MobileUtils.getMobElements(testCase, "xpath", "//*[contains(@name,'_Cell')]").size();
				System.out.println("#########events: " + events);
			}
			eventsList = new String[events][3];
			for (int i = 0; i < events; i++) {
				String[] cell = MobileUtils.getMobElements(testCase, "xpath", "//*[contains(@name,'_Cell')]").get(i)
						.getAttribute("name").split("_");
				System.out.println(cell[1]);
				try {
					if (MobileUtils.isMobElementExists("name", "Events_" + cell[1] + "_" + i + "_Title", testCase, 3)) {
						eventsList[i][0] = MobileUtils.getFieldValue(testCase, "name",
								"Events_" + cell[1] + "_" + i + "_Title");
					} else {
						eventsList[i][0] = "";
					}
				} catch (NoSuchElementException e) {
					eventsList[i][0] = "";
				} catch (NullPointerException e) {
					eventsList[i][0] = "";
				} catch (Exception e) {
					eventsList[i][0] = "";
				}
				try {
					if (MobileUtils.isMobElementExists("name", "Events_" + cell[1] + "_" + i + "_Subtitle", testCase,
							3)) {
						eventsList[i][1] = MobileUtils.getFieldValue(testCase, "name",
								"Events_" + cell[1] + "_" + i + "_Subtitle");
					} else {
						eventsList[i][1] = "";
					}
				} catch (NoSuchElementException e) {
					eventsList[i][1] = "";
				} catch (NullPointerException e) {
					eventsList[i][1] = "";
				} catch (Exception e) {
					eventsList[i][0] = "";
				}
				try {
					if (MobileUtils.isMobElementExists("name", "Events_" + cell[1] + "_" + i + "_Time", testCase, 3)) {
						eventsList[i][2] = MobileUtils.getFieldValue(testCase, "name",
								"Events_" + cell[1] + "_" + i + "_Time");
					} else {
						eventsList[i][2] = "";
					}
				} catch (NoSuchElementException e) {
					eventsList[i][2] = "";
				} catch (NullPointerException e) {
					eventsList[i][2] = "";
				} catch (Exception e) {
					eventsList[i][0] = "";
				}
			}
			System.out.println("##########eventsList.length: " + eventsList.length);

		}
		return eventsList;
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
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(duration));
			ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (ah.isProgressBarVisible()) {
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
