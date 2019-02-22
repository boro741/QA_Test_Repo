package com.honeywell.lyric.das.utils;

import java.time.Duration;
import java.util.regex.Pattern;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.AddressScreen;

public class AddressUtils {

	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(2));
			fWait.withTimeout(Duration.ofMinutes(duration));
			AddressScreen ads = new AddressScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (ads.isProgressBarVisible()) {
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

	public static boolean verifyLocationNameDisplayedInAddressScreen(TestCases testCase, String locationName) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationNameInAddressScreen;
		locationNameInAddressScreen = ads.getLocationNameDisplayedInAddressScreen();
		if (locationNameInAddressScreen.equalsIgnoreCase(locationName)) {
			Keyword.ReportStep_Pass(testCase,
					"Location Name displayed in Address Screen is: " + locationNameInAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location Name displayed in Address Screen is: " + locationNameInAddressScreen
							+ " , which is not same as the actual location name: " + locationName);
		}
		return flag;
	}

	public static boolean verifyLocationNameDisplayedInEditAddressScreen(TestCases testCase, String locationName) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationNameInEditAddressScreen;
		locationNameInEditAddressScreen = ads.getLocationNameTextInEditAddressScreen();
		if (locationNameInEditAddressScreen.equalsIgnoreCase(locationName)) {
			Keyword.ReportStep_Pass(testCase,
					"Location Name displayed in Edit Address Screen is: " + locationNameInEditAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location Name displayed in Edit Address Screen is: " + locationNameInEditAddressScreen
							+ ", which is not same as the actual location name: " + locationName);
		}
		return flag;
	}

	public static boolean verifyLocationAddressValueDisplayedInEditAddressScreen(TestCases testCase,
			String locationAddress) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationAddressValueInEditAddressScreen;
		locationAddressValueInEditAddressScreen = ads.getAddressValueInEditAddressScreen();
		if (locationAddress.contains(locationAddressValueInEditAddressScreen)) {
			Keyword.ReportStep_Pass(testCase, "Location Address value displayed in Edit Address Screen is: "
					+ locationAddressValueInEditAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location Address value displayed in Edit Address Screen is: "
							+ locationAddressValueInEditAddressScreen
							+ ", which is not same as the actual location address: " + locationAddress);
		}
		return flag;
	}

	public static boolean verifyLocationCityValueDisplayedInEditAddressScreen(TestCases testCase,
			String locationAddress) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationCityValueInEditAddressScreen;
		locationCityValueInEditAddressScreen = ads.getCityValueInEditAddressScreen();
		if (locationAddress.contains(locationCityValueInEditAddressScreen)) {
			Keyword.ReportStep_Pass(testCase,
					"Location City value displayed in Edit Address Screen is: " + locationCityValueInEditAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location City value displayed in Edit Address Screen is: " + locationCityValueInEditAddressScreen
							+ ", which is not same as the actual location address: " + locationAddress);
		}
		return flag;
	}

	public static boolean verifyLocationStateValueDisplayedInEditAddressScreen(TestCases testCase,
			String locationAddress) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationStateValueInEditAddressScreen;
		locationStateValueInEditAddressScreen = ads.getStateValueInEditAddressScreen();
		if (locationAddress.contains(locationStateValueInEditAddressScreen)) {
			Keyword.ReportStep_Pass(testCase, "Location State value displayed in Edit Address Screen is: "
					+ locationStateValueInEditAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location State value displayed in Edit Address Screen is: " + locationStateValueInEditAddressScreen
							+ ", which is not same as the actual location address: " + locationAddress);
		}
		return flag;
	}

	public static boolean verifyLocationPostalCodeValueDisplayedInEditAddressScreen(TestCases testCase,
			String locationAddress) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		String locationPostalCodeValueInEditAddressScreen;
		locationPostalCodeValueInEditAddressScreen = ads.getPostalCodeValueInEditAddressScreen();
		if (locationAddress.contains(locationPostalCodeValueInEditAddressScreen)) {
			Keyword.ReportStep_Pass(testCase, "Location Postal Code value displayed in Edit Address Screen is: "
					+ locationPostalCodeValueInEditAddressScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Location Postal Code value displayed in Edit Address Screen is: "
							+ locationPostalCodeValueInEditAddressScreen
							+ ", which is not same as the actual location address: " + locationAddress);
		}
		return flag;
	}

	public static boolean enterLocationNameInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
			String locationNameValueInput) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		flag &= ads.enterLocationNameValueInEditAddressScreen(inputs, locationNameValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Location Name text field:" + locationNameValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Location Name text field:" + locationNameValueInput);
		}
		return flag;
	}

	public static boolean enterAddressInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
			String addressValueInput) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		flag &= ads.enterAddressValueInEditAddressScreen(inputs, addressValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully entered text in Address text field:" + addressValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Address text field:" + addressValueInput);
		}
		return flag;
	}

	public static boolean enterCityInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
			String cityValueInput) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		flag &= ads.enterCityValueInEditAddressScreen(inputs, cityValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully entered text in City text field:" + cityValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in City text field:" + cityValueInput);
		}
		return flag;
	}

	public static boolean enterStateInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
			String stateValueInput) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		flag &= ads.enterStateValueInEditAddressScreen(inputs, stateValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully entered text in State text field:" + stateValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in State text field:" + stateValueInput);
		}
		return flag;
	}

	public static boolean enterPostalCodeInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
			String postalCodeValueInput) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		flag &= ads.enterPostalCodeValueInEditAddressScreen(inputs, postalCodeValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Postal Code text field:" + postalCodeValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Postal Code text field:" + postalCodeValueInput);
		}
		return flag;
	}

	public static boolean clearTextDisplayedInEditAddressTextFields(TestCases testCase, TestCaseInputs inputs,
			String textFieldToBeCleared) {
		boolean flag = true;
		AddressScreen ads = new AddressScreen(testCase);
		switch (textFieldToBeCleared.toUpperCase()) {
		case "LOCATION NAME TEXT FIELD": {
			flag &= ads.clearTextDisplayedInLocationNameTextFieldInEditAddressScreen();
			break;
		}
		case "ADDRESS TEXT FIELD": {
			flag &= ads.clearTextDisplayedInAddressTextFieldInEditAddressScreen();
			break;
		}
		case "CITY TEXT FIELD": {
			flag &= ads.clearTextDisplayedInCityTextFieldInEditAddressScreen();
			break;
		}
		case "STATE TEXT FIELD": {
			flag &= ads.clearTextDisplayedInStateTextFieldInEditAddressScreen();
			break;
		}
		case "POSTAL CODE TEXT FIELD": {
			flag &= ads.clearTextDisplayedInPostalCodeTextFieldInEditAddressScreen();
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + textFieldToBeCleared);
		}
		}
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInLocationNameTxtFieldInEditAddressScreen(TestCases testCase,
			TestCaseInputs inputs, int maxAllowedCharsLength, String enteredMaxChars) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInEditAddressScreen = ads
				.getEnteredLocationNameInEditAddressScreen();
		if (enteredMaxChars.length() <= maxAllowedCharsLength) {
			if (valueDisplayedInLocationNameTxtFieldInEditAddressScreen.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInLocationNameTxtFieldInEditAddressScreen
								+ " is correctly displayed with character length: "
								+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInLocationNameTxtFieldInEditAddressScreen + " entered is of character length: "
								+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {
			if ((!valueDisplayedInLocationNameTxtFieldInEditAddressScreen.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInLocationNameTxtFieldInEditAddressScreen.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInLocationNameTxtFieldInEditAddressScreen
								+ " with max allowed character length: "
								+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to "
								+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen
								+ " with max allowed character length: "
								+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen.length());
			}
		}
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInLocationNameInAddressScreen(TestCases testCase,
			TestCaseInputs inputs, int maxAllowedCharsLength, String enteredMaxChars) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInAddressScreen = ads.getLocationNameDisplayedInAddressScreen();
		if (enteredMaxChars.length() <= maxAllowedCharsLength) {
			if (valueDisplayedInLocationNameTxtFieldInAddressScreen.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInLocationNameTxtFieldInAddressScreen
								+ " is correctly displayed with character length: "
								+ valueDisplayedInLocationNameTxtFieldInAddressScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInLocationNameTxtFieldInAddressScreen + " entered is of character length: "
								+ valueDisplayedInLocationNameTxtFieldInAddressScreen.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {
			if ((!valueDisplayedInLocationNameTxtFieldInAddressScreen.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInLocationNameTxtFieldInAddressScreen.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to \"" + valueDisplayedInLocationNameTxtFieldInAddressScreen
								+ "\" with max allowed character length: "
								+ valueDisplayedInLocationNameTxtFieldInAddressScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInLocationNameTxtFieldInAddressScreen
								+ " with max allowed character length: "
								+ valueDisplayedInLocationNameTxtFieldInAddressScreen.length());
			}
		}
		return flag;
	}

	public static boolean verifyIfLocationNameInEditAddressScreenContainsSpecialCharacters(TestCases testCase,
			TestCaseInputs inputs, String locationNameWithSpecialCharacters) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInEditAddressScreen = ads.getLocationNameTextInEditAddressScreen();
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		if (!valueDisplayedInLocationNameTxtFieldInEditAddressScreen.equalsIgnoreCase(locationNameWithSpecialCharacters)
				&& pattern.matcher(valueDisplayedInLocationNameTxtFieldInEditAddressScreen).matches()) {
			Keyword.ReportStep_Pass(testCase, valueDisplayedInLocationNameTxtFieldInEditAddressScreen
					+ " is correctly displayed in Address Screen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					locationNameWithSpecialCharacters + " is displayed, which contains special characters.");
		}
		return flag;
	}

	public static boolean verifyIfLocationNameInEditAddressScreenContainsEmptySpaces(TestCases testCase,
			TestCaseInputs inputs, String locationNameWithEmptySpaces) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInEditAddressScreen = ads.getLocationNameTextInEditAddressScreen();
		Pattern pattern = Pattern.compile("\\S");
		Pattern pattern1 = Pattern.compile("\\S+");
		Pattern pattern2 = Pattern.compile("\\s");
		Pattern pattern3 = Pattern.compile("\\s+");
		Pattern pattern4 = Pattern.compile("\\s*");
		Pattern pattern5 = Pattern.compile("\\S*");
		String name = " midhun ";
		if (pattern.matcher(name).matches()) {
			System.out.println("pattern: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern: " + "####Empty spaces are not displayed");
		}
		if (pattern1.matcher(name).matches()) {
			System.out.println("pattern1: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern1: " + "####Empty spaces are not displayed");
		}
		if (pattern2.matcher(name).matches()) {
			System.out.println("pattern1: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern1: " + "####Empty spaces are not displayed");
		}
		if (pattern3.matcher(name).matches()) {
			System.out.println("pattern1: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern1: " + "####Empty spaces are not displayed");
		}
		if (pattern4.matcher(name).matches()) {
			System.out.println("pattern1: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern1: " + "####Empty spaces are not displayed");
		}
		if (pattern5.matcher(name).matches()) {
			System.out.println("pattern1: " + "####Empty spaces are displayed");
		} else {
			System.out.println("pattern1: " + "####Empty spaces are not displayed");
		}
		System.out.println("######locationNameWithEmptySpaces: " + locationNameWithEmptySpaces);
		System.out.println("######locationNameWithEmptySpaces.trim(): " + locationNameWithEmptySpaces.trim());
		System.out.println("######valueDisplayedInLocationNameTxtFieldInEditAddressScreen: "
				+ valueDisplayedInLocationNameTxtFieldInEditAddressScreen);
		if (valueDisplayedInLocationNameTxtFieldInEditAddressScreen.equalsIgnoreCase(locationNameWithEmptySpaces)) {
			System.out.println("$$$$$$$$$$$$$$");
			Keyword.ReportStep_Pass(testCase, valueDisplayedInLocationNameTxtFieldInEditAddressScreen
					+ " is correctly displayed in Edit Address Screen");
			if (valueDisplayedInLocationNameTxtFieldInEditAddressScreen.contains(" ")) {
				System.out.println("************");
				Keyword.ReportStep_Pass(testCase, valueDisplayedInLocationNameTxtFieldInEditAddressScreen
						+ " contains empty spaces in Edit Address Screen");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInLocationNameTxtFieldInEditAddressScreen
								+ " does not contains empty spaces in Edit Address Screen");
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					valueDisplayedInLocationNameTxtFieldInEditAddressScreen + " is not matched to: "
							+ locationNameWithEmptySpaces + " in the Edit Address Screen");
		}
		return flag;
	}

	public static boolean verifyIfLocationNameInAddressScreenContainsSpecialCharacters(TestCases testCase,
			TestCaseInputs inputs, String locationNameWithSpecialCharacters) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInAddressScreen = ads.getLocationNameDisplayedInAddressScreen();
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		if (!valueDisplayedInLocationNameTxtFieldInAddressScreen.equalsIgnoreCase(locationNameWithSpecialCharacters)
				&& pattern.matcher(valueDisplayedInLocationNameTxtFieldInAddressScreen).matches()) {
			Keyword.ReportStep_Pass(testCase,
					valueDisplayedInLocationNameTxtFieldInAddressScreen + " is correctly displayed in Address Screen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					locationNameWithSpecialCharacters + " is displayed, which contains special characters.");
		}
		return flag;
	}

	public static boolean verifyIfLocationNameInAddressScreenContainsEmptySpaces(TestCases testCase,
			TestCaseInputs inputs, String locationNameWithSpecialCharacters) {
		AddressScreen ads = new AddressScreen(testCase);
		boolean flag = true;
		String valueDisplayedInLocationNameTxtFieldInAddressScreen = ads.getLocationNameDisplayedInAddressScreen();
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		if (!valueDisplayedInLocationNameTxtFieldInAddressScreen.equalsIgnoreCase(locationNameWithSpecialCharacters)
				&& pattern.matcher(valueDisplayedInLocationNameTxtFieldInAddressScreen).matches()) {
			Keyword.ReportStep_Pass(testCase,
					valueDisplayedInLocationNameTxtFieldInAddressScreen + " is correctly displayed in Address Screen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					locationNameWithSpecialCharacters + " is displayed, which contains special characters.");
		}
		return flag;
	}
}