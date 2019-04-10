package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class AddressScreen extends MobileScreens {

	private static final String screenName = "AddressScreen";

	public AddressScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isAddressScreenTitleVisible() {
		boolean flag = true;
		String locationNameDisplayedInAddressScreen = null;
		String AddressScreenHeaderTitle = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressScreenTitle");
		} else {
			// XCUIElementTypeStaticText[@name='Location Name' and @value='Home
			// Details_HOME']
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressScreenTitle")) {
				AddressScreenHeaderTitle = MobileUtils.getFieldValue(objectDefinition, testCase, "AddressScreenTitle");
				if (this.isLocationNameInAddressScreenVisible()) {
					locationNameDisplayedInAddressScreen = this.getLocationNameDisplayedInAddressScreen();
					if (AddressScreenHeaderTitle.contains("...")) {
						flag &= MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[contains(@value,'"
								+ locationNameDisplayedInAddressScreen.toUpperCase() + "')]", testCase);
					} else {
						flag &= MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeStaticText[@name='Location Name' and @value='"
										+ locationNameDisplayedInAddressScreen + " Details_"
										+ locationNameDisplayedInAddressScreen.toUpperCase() + "']",
								testCase);
					}
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isBackButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return true;
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean clickOnBackButton() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
		} else {
			return false;
		}
	}

	public boolean isLocationNameInAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationNameInAddressScreen");
	}

	public String getLocationNameDisplayedInAddressScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LocationNameInAddressScreen");
	}

	public boolean isLocationAddressInAddressScreenVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationCityStatePostalCodeValuesInAddressScreenInAndroid")
					&& MobileUtils.isMobElementExists(objectDefinition, testCase,
							"LocationCountryValueInAddressScreenInAndroid")) {
				return true;
			} else {
				return false;
			}
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationAddressInAddressScreen");
		}
	}

	public String getCountryNameDisplayedInAddressScreen(TestCaseInputs inputs) {
		String countryName = null;
		List<String> locationAddressInIOS = new ArrayList<String>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationCountryValueInAddressScreenInAndroid")) {
				countryName = MobileUtils.getFieldValue(objectDefinition, testCase,
						"LocationCountryValueInAddressScreenInAndroid");
				if (countryName.equalsIgnoreCase(inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"))) {
					Keyword.ReportStep_Pass(testCase, "Country displayed in Address Screen is: " + countryName);
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Country displayed in CHIL: " + inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL")
									+ " is not same as the Country: " + countryName + " displayed in Address Screen.");
				}
			}
		} else {
			// Code for iOS
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationAddressInAddressScreen")) {
				locationAddressInIOS = getLocationAddressDisplayedInAddressScreen();
				System.out.println(
						"DEFAUL_COUNTRY_NAME_FROM_CHIL: " + inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"));
				System.out.println("locationAddressInIOS: " + locationAddressInIOS);
				if (locationAddressInIOS.toString().contains(inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"))) {
					Keyword.ReportStep_Pass(testCase, "Country displayed in Address Screen is: "
							+ inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL"));
					countryName = inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Country displayed in CHIL: " + inputs.getInputValue("DEFAUL_COUNTRY_NAME_FROM_CHIL")
									+ " is not same as the Country: " + countryName + " displayed in Address Screen.");
				}
			}
		}
		return countryName;
	}

	public List<String> getLocationAddressDisplayedInAddressScreen() {
		List<String> locationAddressDisplayedInAddressScreen = new ArrayList<String>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationAddressValueInAddressScreenInAndroid")
					&& MobileUtils.getFieldValue(objectDefinition, testCase,
							"LocationAddressValueInAddressScreenInAndroid") != null
					&& !MobileUtils
							.getFieldValue(objectDefinition, testCase, "LocationAddressValueInAddressScreenInAndroid")
							.isEmpty()) {
				Keyword.ReportStep_Pass(testCase,
						"Locations Address Value displayed in Address Screen is: " + MobileUtils.getFieldValue(
								objectDefinition, testCase, "LocationAddressValueInAddressScreenInAndroid"));
				locationAddressDisplayedInAddressScreen.add(MobileUtils.getFieldValue(objectDefinition, testCase,
						"LocationAddressValueInAddressScreenInAndroid"));
			} else {
				Keyword.ReportStep_Pass(testCase, "Locations Address Value is not present in Address Screen");
			}
			if (MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationCityStatePostalCodeValuesInAddressScreenInAndroid")
					&& MobileUtils.getFieldValue(objectDefinition, testCase,
							"LocationCityStatePostalCodeValuesInAddressScreenInAndroid") != null
					&& !MobileUtils.getFieldValue(objectDefinition, testCase,
							"LocationCityStatePostalCodeValuesInAddressScreenInAndroid").isEmpty()) {
				Keyword.ReportStep_Pass(testCase,
						"Locations City, State and Postal Code values displayed in Address Screen is: "
								+ MobileUtils.getFieldValue(objectDefinition, testCase,
										"LocationCityStatePostalCodeValuesInAddressScreenInAndroid"));
				locationAddressDisplayedInAddressScreen.add(MobileUtils.getFieldValue(objectDefinition, testCase,
						"LocationCityStatePostalCodeValuesInAddressScreenInAndroid"));
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Either Locations City, State or Postal Code value is not present in Address Screen");
			}
			if (MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationCountryValueInAddressScreenInAndroid")
					&& MobileUtils.getFieldValue(objectDefinition, testCase,
							"LocationCountryValueInAddressScreenInAndroid") != null
					&& !MobileUtils
							.getFieldValue(objectDefinition, testCase, "LocationCountryValueInAddressScreenInAndroid")
							.isEmpty()) {
				Keyword.ReportStep_Pass(testCase,
						"Locations Country Value displayed in Address Screen is: " + MobileUtils.getFieldValue(
								objectDefinition, testCase, "LocationCountryValueInAddressScreenInAndroid"));
				locationAddressDisplayedInAddressScreen.add(MobileUtils.getFieldValue(objectDefinition, testCase,
						"LocationCountryValueInAddressScreenInAndroid"));
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Locations Country Value is not present in Address Screen");
			}
			return locationAddressDisplayedInAddressScreen;
		} else {
			Keyword.ReportStep_Pass(testCase, "Locations Address Value displayed in Address Screen is: "
					+ MobileUtils.getFieldValue(objectDefinition, testCase, "LocationAddressInAddressScreen"));
			locationAddressDisplayedInAddressScreen
					.add(MobileUtils.getFieldValue(objectDefinition, testCase, "LocationAddressInAddressScreen"));
			return locationAddressDisplayedInAddressScreen;
		}
	}

	public boolean isEditButtonInAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditButtonInAddressScreen");
	}

	public boolean clickOnEditButtonInAddressScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EditButtonInAddressScreen");
	}

	public boolean isDeleteLocationButtonInAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteLocationButtonInAddressScreen");
	}

	public boolean clickOnDeleteLocationButtonInAddressScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteLocationButtonInAddressScreen");
	}

	public boolean isEditAddressScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditAddressScreenTitle");
	}

	public boolean isLocationNameHeaderTitleInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationNameHeaderTitleInEditAddressScreen");
	}

	public boolean isLocationNameTextInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
	}

	public boolean isPlaceHolderValueInLocationNameTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceHolderValueInLocationNameTextField");
	}

	public String getLocationNameTextInEditAddressScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
	}

	public boolean clearTextDisplayedInLocationNameTextFieldInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("view_address_location_name")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isAddressHeaderTitleInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressHeaderTitleInEditAddressScreen");
	}

	public boolean isAddressTextInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressTextInEditAddressScreen");
	}

	public boolean isPlaceHolderValueInAddressTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceHolderValueInAddressTextField");
	}

	public String getAddressValueInEditAddressScreen() {
		String addressValueInEditAddressScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddressTextInEditAddressScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "AddressTextInEditAddressScreen") != null
				&& !MobileUtils.getFieldValue(objectDefinition, testCase, "AddressTextInEditAddressScreen").isEmpty()) {
			Keyword.ReportStep_Pass(testCase, "Locations Address Value is displayed in Edit Address Screen");
			addressValueInEditAddressScreen = MobileUtils.getFieldValue(objectDefinition, testCase,
					"AddressTextInEditAddressScreen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Locations Address Value is not present in Edit Address Screen");
		}
		return addressValueInEditAddressScreen;
	}

	public boolean clearTextDisplayedInAddressTextFieldInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("view_address_street_address")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AddressTextInEditAddressScreen");
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "AddressTextInEditAddressScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isCityTextInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CityTextInEditAddressScreen");
	}

	public boolean isPlaceHolderValueInCityTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceHolderValueInCityTextField");
	}

	public String getCityValueInEditAddressScreen() {
		String cityValueInEditAddressScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CityTextInEditAddressScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "CityTextInEditAddressScreen") != null
				&& !MobileUtils.getFieldValue(objectDefinition, testCase, "CityTextInEditAddressScreen").isEmpty()) {
			Keyword.ReportStep_Pass(testCase, "Locations City Value is displayed in Edit Address Screen");
			cityValueInEditAddressScreen = MobileUtils.getFieldValue(objectDefinition, testCase,
					"CityTextInEditAddressScreen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Locations City Value is not present in Edit Address Screen");
		}
		return cityValueInEditAddressScreen;
	}

	public boolean clearTextDisplayedInCityTextFieldInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("view_address_city")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CityTextInEditAddressScreen");
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "CityTextInEditAddressScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isStateTextInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StateTextInEditAddressScreen");
	}

	public boolean isPlaceHolderValueInStateTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceHolderValueInStateTextField");
	}

	public String getStateValueInEditAddressScreen() {
		String stateValueInEditAddressScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "StateTextInEditAddressScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "StateTextInEditAddressScreen") != null
				&& !MobileUtils.getFieldValue(objectDefinition, testCase, "StateTextInEditAddressScreen").isEmpty()) {
			Keyword.ReportStep_Pass(testCase, "Locations City Value is displayed in Edit Address Screen");
			stateValueInEditAddressScreen = MobileUtils.getFieldValue(objectDefinition, testCase,
					"StateTextInEditAddressScreen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Locations State Value is not present in Edit Address Screen");
		}
		return stateValueInEditAddressScreen;
	}

	public boolean clearTextDisplayedInStateTextFieldInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("view_address_state")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "StateTextInEditAddressScreen");
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "StateTextInEditAddressScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isPostalCodeTextInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen");
	}

	public boolean isPlaceHolderValueInPostalCodeTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceHolderValueInPostalCodeTextField");
	}

	public String getPostalCodeValueInEditAddressScreen() {
		String postalCodeValueInEditAddressScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen") != null
				&& !MobileUtils.getFieldValue(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen")
						.isEmpty()) {
			Keyword.ReportStep_Pass(testCase, "Locations Postal Code Value is displayed in Edit Address Screen");
			postalCodeValueInEditAddressScreen = MobileUtils.getFieldValue(objectDefinition, testCase,
					"PostalCodeTextInEditAddressScreen");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Locations Postal Code Value is not present in Edit Address Screen");
		}
		return postalCodeValueInEditAddressScreen;
	}

	public boolean clearTextDisplayedInPostalCodeTextFieldInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("view_address_zipcode")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;

	}

	public boolean isChangeCountryButtonInEditAddressScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeCountryButtonInEditAddressScreen");
	}

	public boolean clickOnChangeCountryButtonInEditAddressScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ChangeCountryButtonInEditAddressScreen");
	}

	public boolean isNotInTheCountryLabelInEditAddressScreenVisible(String existingCountryName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/location_details_not_in_selected_country' and @text='"
							+ "Not in the " + existingCountryName + "?']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeButton[@name='Not in the " + existingCountryName + "?  Change Country']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean enterLocationNameValueInEditAddressScreen(TestCaseInputs inputs, String inputLocationNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.id("view_address_location_name")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LocationNameTextInEditAddressScreen",
					inputLocationNameText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='Location']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LocationNameTextInEditAddressScreen",
					inputLocationNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public String getEnteredLocationNameInEditAddressScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LocationNameTextInEditAddressScreen");
	}

	public boolean enterAddressValueInEditAddressScreen(TestCaseInputs inputs, String inputAddressText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AddressTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.id("view_address_street_address")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "AddressTextInEditAddressScreen",
					inputAddressText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Address Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AddressTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='Address']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "AddressTextInEditAddressScreen",
					inputAddressText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterCityValueInEditAddressScreen(TestCaseInputs inputs, String inputCityText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CityTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.id("view_address_city")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "CityTextInEditAddressScreen",
					inputCityText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for City Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CityTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='City']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "CityTextInEditAddressScreen",
					inputCityText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterStateValueInEditAddressScreen(TestCaseInputs inputs, String inputStateText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "StateTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.id("view_address_state")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "StateTextInEditAddressScreen",
					inputStateText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for State Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "StateTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='State']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "StateTextInEditAddressScreen",
					inputStateText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterPostalCodeValueInEditAddressScreen(TestCaseInputs inputs, String inputPostalCodeText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.id("view_address_zipcode")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen",
					inputPostalCodeText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Postal Code Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='Postal Code']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "PostalCodeTextInEditAddressScreen",
					inputPostalCodeText);
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ReturnButtonIniOSKeyboard")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
			} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButtonIniOSKeyboard")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonIniOSKeyboard");
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isSaveButtonEnabledInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInEditAddressScreen")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "SaveButtonInEditAddressScreen")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInEditAddressScreen")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "SaveButtonInEditAddressScreen")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnSaveButtonInEditAddressScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInEditAddressScreen");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "RightButton", testCase)) {
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "RightButton");
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isCancelLocationChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelLocationChangesPopup");
	}

	public boolean isCancelLocationChangesPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelLocationChangesPopupMsg");
	}

	public boolean isNoButtonInCancelLocationChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelLocationChangesPopup");
	}

	public boolean clickOnNoButtonInCancelLocationChangesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInCancelLocationChangesPopup");
	}

	public boolean isYesButtonInCancelLocationChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelLocationChangesPopup");
	}

	public boolean clickOnYesButtonInCancelLocationChangesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelLocationChangesPopup");
	}

	public boolean isInvalidZipCodePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvalidZipCodePopup");
	}

	public boolean isInvalidZipCodePopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvalidZipCodePopupMsg");
	}

	public boolean isOKButtonInInvalidZipCodePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInInvalidZipCodePopup");
	}

	public boolean clickOnOKButtonInInvalidZipCodePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInInvalidZipCodePopup");
	}

	public boolean isNameMustStartWithErrorPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameMustStartWithErrorPopup");
	}

	public boolean isNameMustStartWithErrorPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameMustStartWithErrorPopupMsg");
	}

	public boolean isOKButtonInNameMustStartWithPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInNameMustStartWithPopup");
	}

	public boolean clickOnOKButtonInNameMustStartWithPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInNameMustStartWithPopup");
	}

	public boolean verifyCountryNamePrivacyPolicyAndEULALink(String countryName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// *[@resource-id='com.honeywell.android.lyric:id/fragment_new_eula_link_container'
			// and @text='PRIVACY POLICY & EULA : Australia']
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/fragment_new_eula_link_container' and @text='PRIVACY POLICY & EULA : "
							+ countryName + "']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='PRIVACY POLICY & EULA: " + countryName + "']", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}
}