package com.honeywell.lyric.das.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.CameraScreen;

public class DashboardUtils {

	public static boolean selectDeviceFromDashboard(TestCases testCase, String deviceToBeSelected,
			boolean... closeCoachMarks) throws Exception {
		List<WebElement> dashboardIconText = null;
		Dashboard d = new Dashboard(testCase);
		CoachMarks cm = new CoachMarks(testCase);

		boolean flag = false;
		
		List<String> availableDevices = new ArrayList<String>();
		
		if (d.areDevicesVisibleOnDashboard(25)) {
			dashboardIconText = d.getDashboardDeviceNameElements();
		}
		for (WebElement e : dashboardIconText) {
			String displayedText = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if(e!=null) {
				displayedText = e.getText();
				}
			} else {
				try {
					if ((e.getAttribute("visible").equals("true")) && (e.getAttribute("value").trim() != null)
							&& (!e.getAttribute("value").trim().isEmpty()))
						displayedText = e.getAttribute("value");
				} catch (Exception e1) {
				}
			}
			availableDevices.add(displayedText);
			if (displayedText.equals(deviceToBeSelected)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (cm.isGotitButtonVisible(1)) {
			if (closeCoachMarks.length > 0 && !closeCoachMarks[0]) {
				return true;
			} else {
				return LyricUtils.closeCoachMarks(testCase);
			}
		}
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully selected " + deviceToBeSelected + " device from Dashboard");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to select " + deviceToBeSelected + " device from Dashboard");
		}
		return flag;
	}

	public static boolean navigateToDashboardFromAnyScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			CameraScreen camScr = new CameraScreen(testCase);
			SchedulingScreen sch = new SchedulingScreen(testCase);
			Dashboard d = new Dashboard(testCase);
			if (d.isGlobalDrawerButtonVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Navigate To Primary Card : User is already on the Primary Card or Dashboard");
				return flag;
			} else {
				int i = 0;
				while ((!d.isGlobalDrawerButtonVisible()) && i < 10) {
					if (sch.isCloseButtonVisible(3)) {
						flag = flag & sch.clickOnCloseButton();
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					} else if (camScr.isBackButtonVisible(2)) {
						flag = flag & camScr.clickOnBackButton();
					} else if (sch.IsSaveButtonVisible(5)) {
						flag = flag & sch.clickOnSaveButton();
					}
					i++;
				}
				if (d.isGlobalDrawerButtonVisible() || (d.isAddDeviceIconVisible(5))) {
					Keyword.ReportStep_Pass(testCase,
							"Navigate To Primary Card : Successfully navigated to Primary card or Dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Navigate To Primary Card : Failed to navigate to Primary card or Dashboard");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Navigate To Primary Card : Error Occured : " + e.getMessage());
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
	 * @since 2018-03-08
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForSecurityStatusToUpdate(TestCases testCase, String securityState, int duration) {
		boolean flag = true;
		System.out.println("%%%%%%%%%%securityState: " + securityState);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			Dashboard d = new Dashboard(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (securityState) {
						case "HOME": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "AWAY": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "NIGHT": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "OFF": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + securityState);
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
					"Progress bar loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
}