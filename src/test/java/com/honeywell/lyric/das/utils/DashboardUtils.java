package com.honeywell.lyric.das.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.Schedule;

public class DashboardUtils {

	public static boolean selectDeviceFromDashboard(TestCases testCase, String deviceToBeSelected) throws Exception {
		List<WebElement> dashboardIconText = null;
		Dashboard d = new Dashboard(testCase);
		if (d.areDevicesVisibleOnDashboard(5)) {
			dashboardIconText = d.getDashboardDeviceNameElements();
		}
		boolean f = false;
		List<String> availableDevices = new ArrayList<String>();
		for (WebElement e : dashboardIconText) {
			String displayedText;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedText = e.getText();
			} else {
				displayedText = e.getAttribute("value");
			}
			availableDevices.add(displayedText);
			if (displayedText.equals(deviceToBeSelected)) {
				e.click();
				f = true;
				break;
			}
		}
		if (f) {
			Keyword.ReportStep_Pass(testCase, "Successfully selected " + deviceToBeSelected + " device from Dashboard");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to select " + deviceToBeSelected + " device from Dashboard");
		}
		return f;
	}

	public static boolean navigateToDashboardFromAnyScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			Schedule sch = new Schedule(testCase);
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

}
