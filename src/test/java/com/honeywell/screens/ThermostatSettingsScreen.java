package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class ThermostatSettingsScreen extends MobileScreens {

	private static final String screenName = "ThermostatSettings";

	public ThermostatSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isThermostatSettingsHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatSettingsHeaderTitle", timeOut);
	}

	public boolean isManageAlertsLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ManageAlertsLabel", timeOut);
	}

	public boolean clickOnManageAlertsLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ManageAlertsLabel");
	}

	public boolean isThermostatIndoorTempAlertSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Thermostat Indoor Temperature Alert Switch");
		}
	}

	public boolean toggleThermostatIndoorTempAlertSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch");
	}

	public boolean isThermostatIndoorTempAlertOptionVisible(String indoorTempAlertOption) {
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorTempAlertTitle")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase,
						"ThermostatIndoorTempAlertTitle");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Temperature Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
					for (WebElement ele : listAlertTitles) {
						if (ele.getText().equalsIgnoreCase(indoorTempAlertOption)) {
							return flag;
						} else {
							flag = false;
						}
					}
				} else {
					if (listAlertTitles.get(0).getText().equalsIgnoreCase(indoorTempAlertOption)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']")
							.getAttribute("value").equalsIgnoreCase(indoorTempAlertOption)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']")
							.getAttribute("visible").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isThermostatTempAlertRangeVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "ThermostatTempAlertRange");

		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatTempAlertRange")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase, "ThermostatTempAlertRange");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Temperature Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Temperature Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
				}
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;
	}

	public boolean clickOnThermostatTempAlertRange() {
		// return MobileUtils.clickOnElement(objectDefinition, testCase,
		// "ThermostatTempAlertRange");

		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatTempAlertRange")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase, "ThermostatTempAlertRange");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Temperature Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
					Keyword.ReportStep_Pass(testCase, "Click On the Second Alert for this range element in the screen");
					listAlertTitles.get(0).click();
				} else {
					listAlertTitles.get(0).click();
				}
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;
	}

	public boolean isThermostatIndoorTempAlertRangeOptionVisible(String indoorTempAlertRangeOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
							+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]",
					testCase)
					&& MobileUtils.getFieldValue(testCase, "XPATH",
							"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
									+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]")
							.equalsIgnoreCase(indoorTempAlertRangeOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypePicker[@name='TemperatureChange']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH", "//XCUIElementTypePicker[@name='TemperatureChange']")
							.getAttribute("value").contains(indoorTempAlertRangeOption)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isBelowAboveTempAlertRangeOptionVisible(String indoorTempAlertRangeOption) {
		boolean flag = true;
		String expectedTempAlertRangeOption = indoorTempAlertRangeOption.contains(" ")
				? indoorTempAlertRangeOption.split(" ")[0]
				: indoorTempAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {

			// iOS
			if (expectedTempAlertRangeOption.equals("Below")) {
				WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "TemperatureRangeOption");
				if (ele.getAttribute("value").contains(expectedTempAlertRangeOption)) {
					return flag;
				} else {
					flag = false;
				}
			} else if (expectedTempAlertRangeOption.equals("Above")) {
				WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "TemperatureRangeOption");
				if (ele.getAttribute("value").contains(expectedTempAlertRangeOption)) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public String getBelowAboveTempAlertRangeValue(String indoorTempAlertRangeOption) {
		String getTempAlertRangeValue = null;
		String expectedTempAlertRangeOption = indoorTempAlertRangeOption.contains(" ")
				? indoorTempAlertRangeOption.split(" ")[0]
				: indoorTempAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				getTempAlertRangeValue = MobileUtils.getFieldValue(testCase, "XPATH",
						"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
								+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
								+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
								+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]");

			}
		} else {

			// iOS
			if (expectedTempAlertRangeOption.equals("Below")) {
				WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "SelectBelowTempRangeValue");
				getTempAlertRangeValue = ele.getAttribute("value");
			} else if (expectedTempAlertRangeOption.equals("Above")) {
				WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "SelectAboveTempRangeValue");
				getTempAlertRangeValue = ele.getAttribute("value");
			}
		}
		return getTempAlertRangeValue;
	}

	public boolean clickOnBelowAboveTempAlertRangeOption(String indoorTempAlertRangeOption) {
		boolean flag = true;
		String expectedTempAlertRangeOption = indoorTempAlertRangeOption.contains(" ")
				? indoorTempAlertRangeOption.split(" ")[0]
				: indoorTempAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
								+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
								+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
								+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]");
				return flag;
			} else {
				flag = false;
			}
		} else {

			// iOS
			if (expectedTempAlertRangeOption.equals("Below")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]", testCase)) {
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]");
				} else {
					flag = false;
				}
			} else if (expectedTempAlertRangeOption.equals("Above")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]", testCase)) {
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]");
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean setValueInBelowAboveTempRange(String indoorTempAlertRangeOption, String tempValue) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			if (indoorTempAlertRangeOption.contains("Below")) {
				
			} else if (indoorTempAlertRangeOption.equals("Above")) {
				
			}
		}
		return flag;
	}

	public boolean isIndoorTempRangeValueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue", 10);
	}

	public String getBelowTempRangeValue() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(0)
				.getText();
	}

	public String getAboveTempRangeValue() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(1)
				.getText();
	}

	public boolean clickOnBelowTempRangeValue() {
		MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(0).click();
		return true;
	}

	public boolean clickOnAboveTempRangeValue() {
		MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(1).click();
		return true;
	}

	public boolean isThermostatIndoorHumidityAlertSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorHumidityAlertSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorHumidityAlertSwitch")
						.getText().equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorHumidityAlertSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Thermostat Indoor Humidity Alert Switch");
		}
	}

	public boolean toggleThermostatIndoorHumidityAlertSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatIndoorHumidityAlertSwitch");
	}

	public boolean isThermostatIndoorHumidityAlertOptionVisible(String indoorHumidityAlertOption) {
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorHumidityAlertTitle")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase,
						"ThermostatIndoorHumidityAlertTitle");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Alert for this range options displayed in the screen is: "
									+ listAlertTitles.size());
					for (WebElement ele : listAlertTitles) {
						if (ele.getText().equalsIgnoreCase(indoorHumidityAlertOption)) {
							return flag;
						} else {
							flag = false;
						}
					}
				} else {
					if (listAlertTitles.get(0).getText().equalsIgnoreCase(indoorHumidityAlertOption)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + indoorHumidityAlertOption + "']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorHumidityAlertOption + "']")
							.getAttribute("value").equalsIgnoreCase(indoorHumidityAlertOption)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorHumidityAlertOption + "']")
							.getAttribute("visible").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isThermostatHumidityAlertRangeVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "ThermostatHumidityAlertRange");

		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidityAlertRange")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase,
						"ThermostatHumidityAlertRange");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Humidity Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Humidity Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
				}
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;

	}

	public boolean clickOnThermostatHumidityAlertRange() {
		// return MobileUtils.clickOnElement(objectDefinition, testCase,
		// "ThermostatHumidityAlertRange");

		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidityAlertRange")) {
				listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase,
						"ThermostatHumidityAlertRange");
				if (listAlertTitles.size() > 1) {
					Keyword.ReportStep_Pass(testCase,
							"Total number of Humidity Alert for this range options displayed in the screen are: "
									+ listAlertTitles.size());
					Keyword.ReportStep_Pass(testCase, "Click On the second Alert for this range element in the screen");
					listAlertTitles.get(1).click();
				} else {
					listAlertTitles.get(0).click();
				}
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;

	}

	public boolean isThermostatIndoorHumidityAlertRangeOptionVisible(String indoorTempAlertRangeOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
							+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]",
					testCase)
					&& MobileUtils.getFieldValue(testCase, "XPATH",
							"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
									+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]")
							.equalsIgnoreCase(indoorTempAlertRangeOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypePicker[@name='TemperatureChange']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH", "//XCUIElementTypePicker[@name='TemperatureChange']")
							.getAttribute("value").contains(indoorTempAlertRangeOption)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isBelowAboveHumidityAlertRangeOptionVisible(String indoorHumidityAlertRangeOption) {
		boolean flag = true;
		String expectedHumidityAlertRangeOption = indoorHumidityAlertRangeOption.contains(" ")
				? indoorHumidityAlertRangeOption.split(" ")[0]
				: indoorHumidityAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedHumidityAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;
	}

	public String getBelowAboveHumidityAlertRangeValue(String indoorHumidityAlertRangeOption) {
		String getHumidityAlertRangeValue = null;
		String expectedHumidityAlertRangeOption = indoorHumidityAlertRangeOption.contains(" ")
				? indoorHumidityAlertRangeOption.split(" ")[0]
				: indoorHumidityAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedHumidityAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				getHumidityAlertRangeValue = MobileUtils.getFieldValue(testCase, "XPATH",
						"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
								+ expectedHumidityAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
								+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
								+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]");

			}
		} else {

			// iOS
		}
		return getHumidityAlertRangeValue;
	}

	public boolean clickOnBelowAboveHumidityAlertRangeOption(String indoorHumidityAlertRangeOption) {
		boolean flag = true;
		String expectedHumidityAlertRangeOption = indoorHumidityAlertRangeOption.contains(" ")
				? indoorHumidityAlertRangeOption.split(" ")[0]
				: indoorHumidityAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedHumidityAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
								+ expectedHumidityAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
								+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
								+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]");
				return flag;
			} else {
				flag = false;
			}
		} else {

			// iOS
		}
		return flag;
	}

	public boolean isIndoorHumidityRangeValueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue", 10);
	}

	public String getBelowHumidityRangeValue() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(0)
				.getText();
	}

	public String getAboveHumidityRangeValue() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(1)
				.getText();
	}

	public boolean clickOnBelowHumidityRangeValue() {
		MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(0).click();
		return true;
	}

	public boolean clickOnAboveHumidityRangeValue() {
		MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(1).click();
		return true;
	}

	public boolean isBackButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isActivityHistoryAlertTimeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryAlertTime");
	}

	public String getActivityHistoryAlertTime() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ActivityHistoryAlertTime");
	}

	public boolean isActivityHistoryAlertTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryAlertTitle");
	}

	public List<WebElement> getActivityHistoryAlertTitlesList() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "ActivityHistoryAlertTitle");
	}

	public String getActivityHistoryAlertTitle() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ActivityHistoryAlertTitle");
	}

	public boolean isActivityHistoryAlertMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryAlertMsg");
	}

	public List<WebElement> getActivityHistoryAlertMsgsList() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "ActivityHistoryAlertMsg");
	}

	public String getActivityHistoryAlertMsg() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ActivityHistoryAlertMsg");
	}

	public boolean isThermostatConfigurationOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatConfigurationOption");
	}

	public boolean selectOptionFromThermostatSettings(String option) throws Exception {
		switch (option) {
		case BaseStationSettingsScreen.THERMOSTATCONFIGURATION: {
			boolean flag = true;
			if (this.isThermostatConfigurationOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Configuration Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatConfigurationOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.THERMOSTATCONFIGURATION);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatConfigurationOption");
			}
			if (this.isThermostatConfigurationOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatConfigurationOption");
			}
			return flag;
		}
		default: {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + option + "']");
			} else {
				return MobileUtils.clickOnElement(testCase, "value", option);
			}
		}
		}
	}

	public boolean clickOnThermostatConfigurationLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatConfigurationButton");
	}

	public boolean isDeleteThermostatButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteThermostatButton");
	}

	public boolean clickOnDeleteThermostatButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteThermostatButton");
	}

	public boolean isDeleteThermostatDevicePopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteThermostatDevicePopupTitle");
	}

	public boolean isCancelButtonInDeleteThermostatDevicePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInDeleteThermostatDevicePopup");
	}

	public boolean clickOnCancelButtonInDeleteThermostatDevice() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInDeleteThermostatDevicePopup");
	}

	public boolean isOKButtonInDeleteThermostatDevicePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInDeleteThermostatDevicePopup");
	}

	public boolean clickOnOKButtonInDeleteThermostatDevice() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInDeleteThermostatDevicePopup");
	}
}
