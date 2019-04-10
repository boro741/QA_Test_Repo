package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;
import java.util.List;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;
import com.honeywell.lyric.das.utils.FAQsUtils;
import com.honeywell.screens.AddressScreen;

public class VerifyTextDisplayedInTheScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputText;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyTextDisplayedInTheScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputText) {
		this.testCase = testCase;
		this.inputText = inputText;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputText.get(1).equalsIgnoreCase("ADDRESS")) {
			AddressScreen ads = new AddressScreen(testCase);
			List<String> locationAddressDisplayedInAddressScreen = new ArrayList<String>();
			switch (inputText.get(0).toUpperCase()) {
			case "LOCATION NAME": {
				if (ads.isLocationNameInAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Location Name is displayed in Address Screen");
					flag &= AddressUtils.verifyLocationNameDisplayedInAddressScreen(testCase,
							inputs.getInputValue("LOCATION_NAME"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Location Name displayed in Address Screen is: "
								+ inputs.getInputValue("LOCATION_NAME"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name displayed in Address Screen is not: "
										+ inputs.getInputValue("LOCATION_NAME"));
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Location Name is not displayed in Address Screen");
				}
				break;
			}
			case "UPDATED LOCATION NAME": {
				if (ads.isLocationNameInAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Location Name is displayed in Address Screen");
					System.out.println("#####TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN: "
							+ inputs.getInputValue("TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN"));
					int maxEnteredCharacters = Integer.parseInt(inputs.getInputValue("MAX_ALLOWED_CHARS_LENGTH"));
					flag &= AddressUtils.verifyIfMaxCharsEnteredInLocationNameInAddressScreen(testCase, inputs,
							maxEnteredCharacters,
							inputs.getInputValue("TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Location Name displayed in Address Screen is: "
								+ ads.getLocationNameDisplayedInAddressScreen());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name displayed in Address Screen is not: "
										+ inputs.getInputValue("TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN"));
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Location Name is not displayed in Address Screen");
				}
				break;
			}
			case "LOCATION NAME WITHOUT SPECIAL CHARACTERS": {
				if (ads.isLocationNameInAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Location Name is displayed in Address Screen");
					System.out.println("#####TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD: "
							+ inputs.getInputValue("TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD"));
					flag &= AddressUtils.verifyIfLocationNameInAddressScreenContainsSpecialCharacters(testCase, inputs,
							inputs.getInputValue("TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Location Name displayed in Address Screen is: "
								+ ads.getLocationNameDisplayedInAddressScreen());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name displayed in Address Screen is: "
										+ inputs.getInputValue(
												"TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD")
										+ " which contains special characters.");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Location Name is not displayed in Address Screen");
				}
				break;
			}
			case "LOCATION ADDRESS": {
				if (ads.isLocationAddressInAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Location Address is displayed in Address Screen");
					locationAddressDisplayedInAddressScreen = ads.getLocationAddressDisplayedInAddressScreen();
					for (String str : locationAddressDisplayedInAddressScreen) {
						System.out.println("*********str:" + str);
						System.out.println("*********LOCATION_ADDRESS:" + inputs.getInputValue("LOCATION_ADDRESS"));
						if (inputs.getInputValue("LOCATION_ADDRESS").contains(str)) {
							Keyword.ReportStep_Pass(testCase,
									"Location Address displayed in Address Screen is: " + str);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									inputs.getInputValue("LOCATION_ADDRESS")
											+ " does not contain the Location Address: " + str
											+ " in the Address Screen");
						}
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							inputs.getInputValue("LOCATION_ADDRESS")
									+ " is not displayed in Location Address section in the Address Screen");
				}
				break;
			}
			case "DEFAULT COUNTRY": {
				String countryName = ads.getCountryNameDisplayedInAddressScreen(inputs);
				if (!countryName.isEmpty() && countryName != null) {
					inputs.setInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN", countryName);
					System.out.println("#########DEFAULT_COUNTRY_IN_ADDRESS_SCREEN: "
							+ inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
					Keyword.ReportStep_Pass(testCase, "Default Country is displayed in Address Screen is: "
							+ inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Default Country is not displayed in Address Screen");
				}
				break;
			}
			case "UPDATED COUNTRY": {
				String countryName = ads.getCountryNameDisplayedInAddressScreen(inputs);
				if (!countryName.isEmpty() && countryName != null) {
					System.out.println("#########UPDATED_COUNTRY_IN_ADDRESS_SCREEN: "
							+ inputs.getInputValue("UPDATED_COUNTRY_IN_ADDRESS_SCREEN"));
					Keyword.ReportStep_Pass(testCase, "Updated Country is displayed in Address Screen is: "
							+ inputs.getInputValue("UPDATED_COUNTRY_IN_ADDRESS_SCREEN"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Updated Country is not displayed in Address Screen");
				}
				break;
			}
			default: {
				if (ads.isLocationAddressInAddressScreenVisible()) {
					System.out.println("###########addressText.get(0): " + inputText.get(0));
					Keyword.ReportStep_Pass(testCase, "Location Address is displayed in Address Screen");
					locationAddressDisplayedInAddressScreen = ads.getLocationAddressDisplayedInAddressScreen();
					for (String str : locationAddressDisplayedInAddressScreen) {
						System.out.println("########str:" + str);
						if (str.contains(inputText.get(0))) {
							Keyword.ReportStep_Pass(testCase,
									inputText.get(0) + " Location Address is displayed in Address Screen");
							break;
						}
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Location Address is not displayed in Address Screen");
				}
			}
			}
		} else if (inputText.get(1).equalsIgnoreCase("EDIT ADDRESS")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (inputText.get(0).toUpperCase()) {
			case "LOCATION NAME WITH EMPTY SPACES": {
				if (ads.isLocationNameTextInEditAddressScreenVisible()) {
					Keyword.ReportStep_Pass(testCase, "Location Name is displayed in Edit Address Screen");
					System.out.println("#####TEXT_ENTERED_WITH_EMPTY_SPACES_IN_LOCATION_NAME_TEXT_FIELD: "
							+ inputs.getInputValue("TEXT_ENTERED_WITH_EMPTY_SPACES_IN_LOCATION_NAME_TEXT_FIELD"));
					flag &= AddressUtils.verifyIfLocationNameInEditAddressScreenContainsEmptySpaces(testCase, inputs,
							inputs.getInputValue("TEXT_ENTERED_WITH_EMPTY_SPACES_IN_LOCATION_NAME_TEXT_FIELD"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Location Name displayed in Edit Address Screen is: "
								+ ads.getLocationNameTextInEditAddressScreen());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Location Name displayed in Edit Address Screen is: "
										+ inputs.getInputValue(
												"TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD")
										+ " which is incorrect.");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Location Name is not displayed in Edit Address Screen");
				}
				break;
			}
			case "DEFAULT COUNTRY": {
				System.out.println("#########DEFAULT_COUNTRY_IN_ADDRESS_SCREEN: "
						+ inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
				flag &= ads.isNotInTheCountryLabelInEditAddressScreenVisible(
						inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Default Country is displayed in Edit Address Screen is: "
							+ inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN")
									+ " Default Country is not displayed in Edit Address Screen");
				}
				break;
			}
			case "UPDATED COUNTRY": {
				System.out.println("#########SELECTED_COUNTRY: " + inputs.getInputValue("SELECTED_COUNTRY"));
				flag &= ads.isNotInTheCountryLabelInEditAddressScreenVisible(inputs.getInputValue("SELECTED_COUNTRY"));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Updated Country is displayed in Edit Address Screen is: "
							+ inputs.getInputValue("SELECTED_COUNTRY"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							inputs.getInputValue("SELECTED_COUNTRY")
									+ " Updated Country is not displayed in Edit Address Screen");
				}
				break;
			}
			}
		} else if (inputText.get(1).equalsIgnoreCase("LOCATION NAME TEXT FIELD IN EDIT ADDRESS")) {
			System.out.println("###########addressText.get(0): " + inputText.get(0));
			flag &= AddressUtils.verifyLocationNameDisplayedInEditAddressScreen(testCase, inputText.get(0));
		} else if (inputText.get(1).equalsIgnoreCase("ADDRESS TEXT FIELD IN EDIT ADDRESS")) {
			System.out.println("###########addressText.get(0): " + inputText.get(0));
			flag &= AddressUtils.verifyLocationAddressValueDisplayedInEditAddressScreen(testCase, inputText.get(0));
		} else if (inputText.get(1).equalsIgnoreCase("CITY TEXT FIELD IN EDIT ADDRESS")) {
			System.out.println("###########addressText.get(0): " + inputText.get(0));
			flag &= AddressUtils.verifyLocationCityValueDisplayedInEditAddressScreen(testCase, inputText.get(0));
		} else if (inputText.get(1).equalsIgnoreCase("STATE TEXT FIELD IN EDIT ADDRESS")) {
			System.out.println("###########addressText.get(0): " + inputText.get(0));
			flag &= AddressUtils.verifyLocationStateValueDisplayedInEditAddressScreen(testCase, inputText.get(0));
		} else if (inputText.get(1).equalsIgnoreCase("POSTAL CODE TEXT FIELD IN EDIT ADDRESS")) {
			System.out.println("###########addressText.get(0): " + inputText.get(0));
			flag &= AddressUtils.verifyLocationPostalCodeValueDisplayedInEditAddressScreen(testCase, inputText.get(0));
		} else if (inputText.get(1).equalsIgnoreCase("EDIT ACCOUNT")) {
			switch (inputText.get(0).toUpperCase()) {
			case "UPDATED FIRST NAME": {
				flag &= EditAccountUtils.verifyFirstNameDisplayedInEditAccountScreen(testCase,
						inputs.getInputValue("UPDATED_FIRST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			case "UPDATED LAST NAME": {
				flag &= EditAccountUtils.verifyLastNameDisplayedInEditAccountScreen(testCase,
						inputs.getInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			case "EXISTING FIRST NAME": {
				flag &= EditAccountUtils.verifyFirstNameDisplayedInEditAccountScreen(testCase,
						inputs.getInputValue("FIRST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			case "EXISTING LAST NAME": {
				flag &= EditAccountUtils.verifyLastNameDisplayedInEditAccountScreen(testCase,
						inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			}
		} else if (inputText.get(1).equalsIgnoreCase("QUESTION")) {
			switch (inputText.get(0).toUpperCase()) {
			case "YOU FOUND THIS HELPFUL":
			case "YOU DID NOT FIND THIS HELPFUL": {
				flag &= FAQsUtils.verifyWasThisHelpfulTextAfterSelectingYesOrNo(testCase, inputText.get(0));
				break;
			}
			}
		} else if (inputText.get(1).equalsIgnoreCase("ADD NEW DEVICE DASHBOARD")) {
			switch (inputText.get(0).toUpperCase()) {
			case "DEFAULT COUNTRY": {
				System.out.println("#########COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN: "
						+ inputs.getInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN"));
				flag &= DashboardUtils.verifyCountryDisplayedInAddNewDeviceScreen(testCase,
						inputs.getInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN"));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Default Country is displayed in Add New Device Screen is: "
							+ inputs.getInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							inputs.getInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN")
									+ " Default Country is not displayed in Add New Device Screen");
				}
				break;
			}
			case "UPDATED COUNTRY": {
				System.out.println("#########NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN: "
						+ inputs.getInputValue("NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN"));
				flag &= DashboardUtils.verifyCountryDisplayedInAddNewDeviceScreen(testCase,
						inputs.getInputValue("NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN"));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Updated Country is displayed in Add New Device Screen is: "
							+ inputs.getInputValue("NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							inputs.getInputValue("NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN")
									+ " Country is not displayed in Add New Device Screen");
				}
				break;
			}
			}
		} else if (inputText.get(1).equalsIgnoreCase("NEW AGREEMENT")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (inputText.get(0).toUpperCase()) {
			case "UPDATED COUNTRY IN PRIVACY POLICY AND EULA LINK": {
				System.out.println("######SELECTED_COUNTRY: " + inputs.getInputValue("SELECTED_COUNTRY"));
				if (ads.verifyCountryNamePrivacyPolicyAndEULALink(inputs.getInputValue("SELECTED_COUNTRY"))) {
					System.out.println("#########SELECTED_COUNTRY: " + inputs.getInputValue("SELECTED_COUNTRY"));
					Keyword.ReportStep_Pass(testCase,
							"Updated Country is displayed in Privacy Policy and EULA link in New Agreement Screen is: "
									+ inputs.getInputValue("SELECTED_COUNTRY"));
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Updated Country is not displayed in Address Screen");
				}
				break;
			}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}