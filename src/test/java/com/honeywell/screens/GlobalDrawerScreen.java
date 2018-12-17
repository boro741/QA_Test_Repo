package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class GlobalDrawerScreen extends MobileScreens {

	private static final String screenName = "GlobalDrawer";

	public GlobalDrawerScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar", timeOut, false);
	}

	public boolean isAutomationHeaderTitleVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/device_type_section_header' and (@text='Automation' or @text='AUTOMATION')]",
					testCase);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutomationHeaderTitle");
		}
	}

	public boolean isGeofenceOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceOption");
	}

	public boolean clickOnGeofenceOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceOption");
	}

	public boolean isVacationOptionVisible() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Vacation']", testCase);
		} else {
			flag &= MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='vacation_subTitle']",
					testCase);
		}
		return flag;
	}

	public boolean clickOnVacationOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationOption");
	}

	public boolean isHomeHeaderTitleVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/device_type_section_header' and (@text='Home' or @text='HOME')]",
					testCase);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "HomeHeaderTitle");
		}
	}

	public boolean isActivityHistoryOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryOption");
	}

	public boolean isAddUsersOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddUsersOption");
	}

	public boolean isAddressOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressOption");
	}

	public boolean isAccountHeaderTitleVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/device_type_section_header' and (@text='Account' or @text='ACCOUNT')]",
					testCase);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AccountHeaderTitle");
		}
	}

	public boolean isHoneywellMembershipOptionVisible() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Membership']", testCase);
		} else {
			flag &= MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='Honeywell Membership']",
					testCase);
		}
		return flag;
	}

	public boolean isEditAccountOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditAccountOption");
	}
	
	public boolean clickEditAccountOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EditAccountOption");
	}

	public boolean isAboutTheAppOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboutTheAppOption");
	}

	public boolean isFAQsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FAQsOption");
	}

	public boolean isLogoutOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LogoutOption");
	}
}