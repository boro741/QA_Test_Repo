package com.honeywell.lyric.das.utils;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ManageUsersScreen;

public class DASManageUsersUtils {

	public static boolean minimizeAndMaximizeTheApp(TestCases testCase) {
		boolean flag = true;
		MobileUtils.minimizeApp(testCase, 5);
		return flag;
	}

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
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "LOADING SPINNER": {
							if (mus.isLoadingSpinnerVisible()) {
								System.out.println("Waiting for Verifying loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "PROGRESS BAR": {
							if (mus.isProgressBarVisible()) {
								System.out.println("Waiting for Verifying progress bar to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared.");
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

	public static boolean inputEmailAddressInInviteUserScreen(TestCases testCase, TestCaseInputs inputs,
			String inviteEmailAddress) {
		boolean flag = true;
		ManageUsersScreen mus = new ManageUsersScreen(testCase);
		if (mus.isInviteUserScreenTitleVisible() && mus.isInvitieUserScreenSubTitleVisible()
				&& mus.isBackButtonVisible() && mus.isEmailTextFieldInInviteUserScreenVisible()
				&& !mus.isSendButtonEnabled()) {
			flag = flag & mus.enterInviteEmailAddress(inputs, inviteEmailAddress);
		}
		return flag;
	}

	public static boolean deleteInvitedUserInAddUsersScreen(TestCases testCase, TestCaseInputs inputs,
			String invitedUsersEmailAddress) {
		boolean flag = true;
		ManageUsersScreen mus = new ManageUsersScreen(testCase);
		if (mus.isAddUserButtonVisible()
				&& mus.isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(inputs, invitedUsersEmailAddress)) {
			flag = flag & mus.deleteInvitedUserInAddUsersScreen(invitedUsersEmailAddress);
		}
		return flag;
	}
}
