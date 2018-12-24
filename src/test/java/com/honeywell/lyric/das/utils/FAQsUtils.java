package com.honeywell.lyric.das.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FAQsScreen;

public class FAQsUtils {

	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			FAQsScreen faqsScreen = new FAQsScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (faqsScreen.isProgressBarVisible()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
								return true;
							} else {
								return false;
							}
						}
						case "LOADING SPINNER": {
							if (faqsScreen.isLoadingSpinnerVisible()) {
								System.out.println("Waiting for loading spinner to disappear");
								return true;
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
					"Progress bar loading spinner did not disapper after waiting for " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifyWasThisHelpfulTextAfterSelectingYesOrNo(TestCases testCase,
			String wasThisHelpfulTextAfterSelectingYesOrNoOption) {
		boolean flag = true;
		FAQsScreen faqsScreen = new FAQsScreen(testCase);
		if (wasThisHelpfulTextAfterSelectingYesOrNoOption.equalsIgnoreCase("YOU DID NOT FIND THIS HELPFUL")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			wasThisHelpfulTextAfterSelectingYesOrNoOption = "You didn't find this helpful";
			} else {
				wasThisHelpfulTextAfterSelectingYesOrNoOption = "You didn't find this helpful.";
			}
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				wasThisHelpfulTextAfterSelectingYesOrNoOption = wasThisHelpfulTextAfterSelectingYesOrNoOption + ".";
			}
		}
		String textDisplayedInTheQuestionFooter;
		textDisplayedInTheQuestionFooter = faqsScreen.getQuestionFooterMessage();
		if (textDisplayedInTheQuestionFooter.equalsIgnoreCase(wasThisHelpfulTextAfterSelectingYesOrNoOption)) {
			Keyword.ReportStep_Pass(testCase,
					"Text displayed after selecting either Yes or No button in Question Screen is: "
							+ textDisplayedInTheQuestionFooter);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Text displayed after selecting either Yes or No button in Question Screen is: "
							+ textDisplayedInTheQuestionFooter + ", which is not same as the actual: "
							+ wasThisHelpfulTextAfterSelectingYesOrNoOption);
		}
		return flag;
	}
	
	public static boolean enterHelpTextInFAQsScreen(TestCases testCase, TestCaseInputs inputs, String inputText) {
		boolean flag = true;
		FAQsScreen faqsScreen = new FAQsScreen(testCase);
		flag &= faqsScreen.enterTextInHelpTextField(inputText);
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully entered text in Help text field:" + inputText);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Help text field:" + inputText);
		}
		return flag;
	}
}