package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

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
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
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
		return flag;
	}

	public boolean clickOnThermostatTempAlertRange() {
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
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
			if (indoorTempAlertRangeOption.contains("Below")) {
				if (testCase.getMobileDriver()
						.findElement(By.xpath(
								"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
						.isEnabled()) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				if (indoorTempAlertRangeOption.contains("Above")) {
					if (testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
							.isEnabled()) {
						return flag;
					} else {
						flag = false;
					}
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
			if (indoorTempAlertRangeOption.contains("Below")) {
				if (testCase.getMobileDriver()
						.findElement(By.xpath(
								"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
						.isEnabled()) {
					getTempAlertRangeValue = testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
							.getAttribute("value");
				}
			} else {
				if (indoorTempAlertRangeOption.contains("Above")) {
					if (testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
							.isEnabled()) {
						getTempAlertRangeValue = testCase.getMobileDriver().findElement(By.xpath(
								"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
								.getAttribute("value");
					}
				}
			}
		}
		return getTempAlertRangeValue;
	}

	public boolean setTempValueForTemperatureRange(String alertBelowTempRangeOption, double belowValue) {
		boolean flag = true;
		int counter = 0;
		System.out.println("#########" + (String.valueOf(belowValue)));
		System.out.println("$$$$$$$$$" + testCase.getMobileDriver()
				.findElement(By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
				.getAttribute("value"));
		if (alertBelowTempRangeOption.contains("Below")) {
			while (!testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
					.getAttribute("value").equals((String.valueOf(belowValue))) && counter <= 5) {
				testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.sendKeys((String.valueOf(belowValue)));
				counter++;
			}
		} else {
			if (alertBelowTempRangeOption.contains("Above")) {
				while (!testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
						.getAttribute("value").equals((String.valueOf(belowValue))) && counter <= 5) {
					testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
							.sendKeys((String.valueOf(belowValue)));
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
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

	public boolean isIndoorTempRangeValueVisible() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase,
					"IndoorAboveBelowTemperatureRangeValue", 10);
		} else {
			if (testCase.getMobileDriver()
					.findElement(By
							.xpath("//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
					.isEnabled()) {
				return flag;
			} else {
				flag = false;
			}
		}
		if (testCase.getMobileDriver()
				.findElement(
						By.xpath("//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
				.isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;

	}

	public String getBelowTempRangeValue() {
		String belowTempValue = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			belowTempValue = MobileUtils
					.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(0)
					.getText();
		} else {
			if (testCase.getMobileDriver()
					.findElement(By
							.xpath("//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
					.isEnabled()) {
				belowTempValue = testCase.getMobileDriver()
						.findElement(By.xpath(
								"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[1]"))
						.getAttribute("value");
			}
		}
		return belowTempValue;

	}

	public String getAboveTempRangeValue() {
		String aboveTempValue = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			aboveTempValue = MobileUtils
					.getMobElements(objectDefinition, testCase, "IndoorAboveBelowTemperatureRangeValue").get(0)
					.getText();
		} else {
			if (testCase.getMobileDriver()
					.findElement(By
							.xpath("//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
					.isEnabled()) {
				aboveTempValue = testCase.getMobileDriver()
						.findElement(By.xpath(
								"//XCUIElementTypePicker[@name='TemperatureChange']/XCUIElementTypePickerWheel[2]"))
						.getAttribute("value");
			}
		}
		return aboveTempValue;

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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorHumidityAlertTitle")) {
			listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase,
					"ThermostatIndoorHumidityAlertTitle");
			if (listAlertTitles.size() > 1) {
				Keyword.ReportStep_Pass(testCase,
						"Total number of Alert for this range options displayed in the screen is: "
								+ listAlertTitles.size());
				for (WebElement ele : listAlertTitles) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ele.getText().equalsIgnoreCase(indoorHumidityAlertOption)) {
							return flag;
						} else {
							flag = false;
						}
					} else {
						if (ele.getAttribute("value").equalsIgnoreCase(indoorHumidityAlertOption)) {
							return flag;
						} else {
							flag = false;
						}
					}
				}
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (listAlertTitles.get(0).getText().equalsIgnoreCase(indoorHumidityAlertOption)) {
						return flag;
					} else {
						flag = false;
					}
				} else {
					if (listAlertTitles.get(0).getAttribute("value").equalsIgnoreCase(indoorHumidityAlertOption)) {
						return flag;
					} else {
						flag = false;
					}
				}
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isThermostatHumidityAlertRangeVisible() {
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidityAlertRange")) {
			listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase, "ThermostatHumidityAlertRange");
			if (listAlertTitles.size() > 1) {
				Keyword.ReportStep_Pass(testCase,
						"Total number of Alert for this range options displayed in the screen are: "
								+ listAlertTitles.size());
			} else {
				Keyword.ReportStep_Pass(testCase,
						"Total number of Alert for this range options displayed in the screen are: "
								+ listAlertTitles.size());
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean clickOnThermostatHumidityAlertRange() {
		boolean flag = true;
		List<WebElement> listAlertTitles = new ArrayList<>();
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidityAlertRange")) {
			listAlertTitles = MobileUtils.getMobElements(objectDefinition, testCase, "ThermostatHumidityAlertRange");
			if (listAlertTitles.size() > 1) {
				Keyword.ReportStep_Pass(testCase,
						"Total number of Alert for this range options displayed in the screen are: "
								+ listAlertTitles.size());
				Keyword.ReportStep_Pass(testCase, "Click On the second Alert for this range element in the screen");
				listAlertTitles.get(1).click();
			} else {
				listAlertTitles.get(0).click();
			}
		} else {
			flag = false;
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
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypePicker[@name='HumidityChange']", testCase)
					&& MobileUtils.getMobElement(testCase, "XPATH", "//XCUIElementTypePicker[@name='HumidityChange']")
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
			if (indoorHumidityAlertRangeOption.contains("Below")) {
				if (testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.isEnabled()) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				if (indoorHumidityAlertRangeOption.contains("Above")) {
					if (testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
							.isEnabled()) {
						return flag;
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
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
			if (indoorHumidityAlertRangeOption.contains("Below")) {
				if (testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.isEnabled()) {
					getHumidityAlertRangeValue = testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
							.getAttribute("value");
				}
			} else {
				if (indoorHumidityAlertRangeOption.contains("Above")) {
					if (testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
							.isEnabled()) {
						getHumidityAlertRangeValue = testCase.getMobileDriver().findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
								.getAttribute("value");
					}
				}
			}
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
			if (indoorHumidityAlertRangeOption.contains("Below")) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BelowHumidityRangePicker")) {
					MobileUtils.clickOnElement(objectDefinition, testCase, "BelowHumidityRangePicker");
				}
			} else {
				if (indoorHumidityAlertRangeOption.contains("Above")) {
					if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AboveHumidityRangePicker")) {
						MobileUtils.clickOnElement(objectDefinition, testCase, "AboveHumidityRangePicker");
					}
				}
			}
		}
		return flag;
	}

	public boolean setHumidiityValueForHumidityRange(String alertBelowHumidityRangeOption, int setHumidityValue) {
		boolean flag = true;
		int counter = 0;
		System.out.println("#########" + (String.valueOf(setHumidityValue) + "%"));
		System.out.println("$$$$$$$$$" + testCase.getMobileDriver()
				.findElement(By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
				.getAttribute("value"));
		if (alertBelowHumidityRangeOption.contains("Below")) {
			while (!testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
					.getAttribute("value").equals((String.valueOf(setHumidityValue) + "%")) && counter <= 5) {
				testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.sendKeys((String.valueOf(setHumidityValue) + "%"));
				counter++;
			}
		} else {
			if (alertBelowHumidityRangeOption.contains("Above")) {
				while (!testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
						.getAttribute("value").equals((String.valueOf(setHumidityValue) + "%")) && counter <= 5) {
					testCase.getMobileDriver()
							.findElement(By.xpath(
									"//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
							.sendKeys((String.valueOf(setHumidityValue) + "%"));
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isIndoorHumidityRangeValueVisible() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase,
					"IndoorAboveBelowHumidityRangeValue", 10);
		} else {
			if (testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
					.isEnabled()) {
				return flag;
			} else {
				flag = false;
			}
		}
		if (testCase.getMobileDriver()
				.findElement(By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
				.isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public String getBelowHumidityRangeValue() {
		String belowHumidityValue = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			belowHumidityValue = MobileUtils
					.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(0).getText();
		} else {
			if (testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
					.isEnabled()) {
				belowHumidityValue = testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.getAttribute("value");
			}
		}
		return belowHumidityValue;
	}

	public String getAboveHumidityRangeValue() {
		String aboveHumidityValue = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			aboveHumidityValue = MobileUtils
					.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(1).getText();
		} else {
			if (testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
					.isEnabled()) {
				aboveHumidityValue = testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
						.getAttribute("value");
			}
		}
		return aboveHumidityValue;
	}

	public boolean clickOnBelowHumidityRangeValue() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue")) {
				MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(0)
						.click();
			} else {
				flag = false;
			}
		} else {
			if (testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
					.isEnabled()) {
				testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[1]"))
						.click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnAboveHumidityRangeValue() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue")) {
				MobileUtils.getMobElements(objectDefinition, testCase, "IndoorAboveBelowHumidityRangeValue").get(1)
						.click();
			} else {
				flag = false;
			}
		} else {
			if (testCase.getMobileDriver()
					.findElement(
							By.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
					.isEnabled()) {
				testCase.getMobileDriver()
						.findElement(By
								.xpath("//XCUIElementTypePicker[@name='HumidityChange']/XCUIElementTypePickerWheel[2]"))
						.click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isBackButtonVisible(int timeOut) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return flag;
		} else if (MobileUtils.isMobElementExists("NAME", "Back", testCase)) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean clickOnBackButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else if (MobileUtils.isMobElementExists("NAME", "Back", testCase)) {
			MobileUtils.clickOnElement(testCase, "NAME", "Back");
		} else {
			flag = false;
		}
		return flag;
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
		case BaseStationSettingsScreen.FROSTPROTECTION: {
			boolean flag = true;
			if (this.isThermostatFrostProtectionOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Frost Protection Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatFrostProtectionOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Frost Protection Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.FROSTPROTECTION);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatFrostProtectionOption");
			}
			if (this.isThermostatFrostProtectionOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Frost Protection Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatFrostProtectionOption");
			}
			return flag;
		}
		case BaseStationSettingsScreen.HUMIDIFICATION: {
			boolean flag = true;
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			if (this.isThermostatHumidificationOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Humidification Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatHumidificationOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Humidification Visible @ 2");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int startx = (dimension.width * 20) / 100;
					int starty = (dimension.height * 62) / 100;
					int endx = (dimension.width * 22) / 100;
					int endy = (dimension.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					Thread.sleep(3000);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							BaseStationSettingsScreen.HUMIDIFICATION);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatHumidificationOption");
			}
			if (this.isThermostatFrostProtectionOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Humidification Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatHumidificationOption");
			}
			return flag;
		}
		case BaseStationSettingsScreen.DEHUMIDIFICATION: {
			boolean flag = true;
			if (this.isThermostatDehumidificationOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Dehumidification Visible @ 1");
				flag = flag
						& MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatDeHumidificationOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Dehumidification Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.DEHUMIDIFICATION);
				flag = flag
						& MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatDeHumidificationOption");
			}
			if (this.isThermostatFrostProtectionOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Dehumidification Visible @ 3");
				flag = flag
						& MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatDeHumidificationOption");
			}
			return flag;
		}
		case BaseStationSettingsScreen.SLEEPBRIGHTNESSMODE: {
			boolean flag = true;
			if (this.isThermostatSleepBrightnessModeOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sleep Brightness Mode Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSleepBrightnessMode");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sleep Brightness Mode Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.SOUND);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSleepBrightnessMode");
			}
			if (this.isThermostatSleepBrightnessModeOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sleep Brightness Mode Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSleepBrightnessMode");
			}
			return flag;
		}
		case BaseStationSettingsScreen.SOUND: {
			boolean flag = true;
			if (this.isThermostatSoundOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sound Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSoundOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sound Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.SOUND);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSoundOption");
			}
			if (this.isThermostatSoundOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Sound Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSoundOption");
			}
			return flag;
		}
		case BaseStationSettingsScreen.EMERGENCYHEAT: {
			boolean flag = true;
			if (this.isThermostatEmergencyHeatSwitchVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Emergency Heat Switch Visible @ 1");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Emergency Heat Switch Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.EMERGENCYHEAT);
			}
			if (this.isThermostatEmergencyHeatSwitchVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Emergency Heat Switch Visible @ 3");
			}
			return flag;
		}
		case BaseStationSettingsScreen.VENTILATION: {
			boolean flag = true;
			if (this.isThermostatVentilationOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Ventilation Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatVentilationOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Thermostat Ventilation Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VENTILATION);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatVentilationOption");
			}
			if (this.isThermostatSoundOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat Ventilation Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatVentilationOption");
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

	public boolean isSetFilterReminderLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetFilterReminderLabel", timeOut);
	}

	public boolean clickOnSetFilterReminderLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SetFilterReminderLabel");
	}

	public boolean isThermostatSetFilterReminderSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatSetFilterReminderSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatSetFilterReminderSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatSetFilterReminderSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Set Filter Reminder Switch");
		}
	}

	public boolean toggleSetFilterReminderSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatSetFilterReminderSwitch");
	}

	public boolean isSetFilterReminderOptionsVisible(String setFilterReminderOptions) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@text='" + setFilterReminderOptions + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPath",
					"//XCUIElementTypeStaticText[contains(@name, '_subTitle') and @value='" + setFilterReminderOptions
							+ "']",
					testCase);
		}
	}

	public boolean isThermostatFineTuneSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatFineTuneSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatFineTuneSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatFineTuneSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Set Fine Tune Switch");
		}
	}

	public boolean toggleThermostatFineTuneSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatFineTuneSwitch");
	}

	public boolean isThermostatAdaptiveRecoverySwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatAdaptiveRecoverySwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAdaptiveRecoverySwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAdaptiveRecoverySwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Adaptive Recovery Switch");
		}
	}

	public boolean toggleThermostatAdaptiveRecoverySwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatAdaptiveRecoverySwitch");
	}

	public boolean isThermostatOptimiseSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatOptimiseSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatOptimiseSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatOptimiseSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Set Fine Tune Switch");
		}
	}

	public boolean toggleThermostatOptimiseSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatOptimiseSwitch");
	}

	public boolean isThermostatAutoChangeOverSwitchEnabled(TestCases testCase) throws Exception {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				int startx = (dimension.width * 20) / 100;
				int starty = (dimension.height * 62) / 100;
				int endy = (dimension.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, endy, startx, starty, 1000);
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				action.press(0, -(int) (dimension.getHeight() * .9)).moveTo(10, (int) (dimension.getHeight() * .6))
						.release().perform();
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean toggleThermostatAutoChangeOverSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatAutoChangeOverSwitch");
	}

	public boolean isThermostatAutoChangeOverSwitchEnabled(TestCases testCase, String fieldToBeVerified)
			throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//android.widget.Switch[@content-desc='" + fieldToBeVerified + "']", testCase)
					&& (MobileUtils
							.getMobElement(testCase, "XPATH",
									"//android.widget.Switch[@content-desc='" + fieldToBeVerified + "']")
							.getText().equalsIgnoreCase("ON"))) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + fieldToBeVerified
							+ "']/following-sibling::XCUIElementTypeSwitch[@name='autoChangeOver_toggle']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH", "//XCUIElementTypeStaticText[@value='" + fieldToBeVerified
									+ "']/following-sibling::XCUIElementTypeSwitch[@name='autoChangeOver_toggle']")
							.getAttribute("value").equalsIgnoreCase("1")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isThermostatEmergencyHeatSwitchEnabled(TestCases testCase) throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				flag = flag & this.selectOptionFromThermostatSettings(BaseStationSettingsScreen.EMERGENCYHEAT);
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				flag = flag & this.selectOptionFromThermostatSettings(BaseStationSettingsScreen.EMERGENCYHEAT);
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isThermostatEmergencyHeatSwitchEnabled(TestCases testCase, String fieldToBeVerified)
			throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//android.widget.Switch[@content-desc='" + fieldToBeVerified + "']", testCase)
					&& (MobileUtils
							.getMobElement(testCase, "XPATH",
									"//android.widget.Switch[@content-desc='" + fieldToBeVerified + "']")
							.getText().equalsIgnoreCase("ON"))) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + fieldToBeVerified
							+ "']/following-sibling::XCUIElementTypeSwitch[@name='emergencyHeat_toggle']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH", "//XCUIElementTypeStaticText[@value='" + fieldToBeVerified
									+ "']/following-sibling::XCUIElementTypeSwitch[@name='emergencyHeat_toggle']")
							.getAttribute("value").equalsIgnoreCase("1")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean toggleThermostatEmergencyHeatSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch");
	}

	public boolean isThermostatFrostProtectionOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatFrostProtectionOption", 3);
	}

	public boolean isThermostatFrostProtectionValueVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FrostProtectionValue", timeOut);
	}

	public boolean setValueToHumiditySlider(String value) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement humiditySlider = MobileUtils.getMobElement(objectDefinition, testCase, "FrostProtectionSlider");
			Dimension d1 = humiditySlider.getSize();
			Point p1 = humiditySlider.getLocation();
			float sliderLength = d1.getWidth();
			float pixelPerPercent = sliderLength / 100;
			float pixelToBeMoved = Integer.parseInt(value.equals("0") ? "1" : value) * pixelPerPercent;
			System.out.println("Setting for " + value);
			System.out.println("X: " + (int) (p1.getX() + pixelToBeMoved));
			System.out.println("Y: " + p1.getY());
			return MobileUtils.clickOnCoordinate(testCase, (int) (p1.getX() + pixelToBeMoved), p1.getY());
		} else {
			MobileUtils.setValueToElement(objectDefinition, testCase, "FrostProtectionSlider", value);
			return true;
		}
	}

	public boolean verifyThermostatFrostProtectionValue(String value) throws Exception {
		if (this.isThermostatFrostProtectionValueVisible(10)) {
			String displayedValue = null;
			int expectedValue = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedValue = MobileUtils.getMobElement(objectDefinition, testCase, "FrostProtectionValue")
						.getAttribute("text");
				displayedValue = displayedValue.split("%")[0];
				expectedValue = Integer.parseInt(value);
				expectedValue = expectedValue / 10;
			} else {
				displayedValue = MobileUtils.getMobElement(objectDefinition, testCase, "FrostProtectionValue")
						.getAttribute("value");
			}
			int actualValue = Integer.parseInt(displayedValue);
			return (expectedValue <= (actualValue + 5) && expectedValue >= (actualValue - 5));
		} else {
			throw new Exception("Could not find Frost Protection Value Elements");
		}
	}

	public boolean isThermostatHumidificationOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidificationOption", 3);
	}

	public boolean isThermostatHumidificationSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatHumidificationSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatHumidificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Humidification Switch");
		}
	}

	public boolean toggleThermostatHumidificationSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatHumidificationSwitch");
	}

	public String getThermostatHumidificationValueInHumidificationScreen(TestCases testCase) {
		String actualThermHumidificationValue = null;
		List<WebElement> listHumidificationSubTitles = new ArrayList<>();
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatHumidificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatHumidificationSwitch").getText()
						.equalsIgnoreCase("ON")) {
					actualThermHumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
							"ThermostatHumidificationValue");
					if (actualThermHumidificationValue.contains(" ")) {
						actualThermHumidificationValue = actualThermHumidificationValue.contains(" ")
								? actualThermHumidificationValue.split(" ")[1].split("%")[0]
								: actualThermHumidificationValue;
					} else {
						actualThermHumidificationValue = actualThermHumidificationValue.split("%")[0];
					}
				} else {
					actualThermHumidificationValue = "Off";
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatHumidificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					listHumidificationSubTitles = MobileUtils.getMobElements(objectDefinition, testCase,
							"ThermostatHumidificationValue");
					for (WebElement ele : listHumidificationSubTitles) {
						if (ele.getAttribute("value").contains("%")) {
							actualThermHumidificationValue = ele.getAttribute("value");
							if (actualThermHumidificationValue.contains(" ")) {
								actualThermHumidificationValue = actualThermHumidificationValue.contains(" ")
										? actualThermHumidificationValue.split(" ")[1].split("%")[0]
										: actualThermHumidificationValue;
							} else {
								actualThermHumidificationValue = actualThermHumidificationValue.split("%")[0];
							}
						}
					}
				} else {
					actualThermHumidificationValue = "Off";
				}
			}
		}
		return actualThermHumidificationValue;
	}

	public String getThermostatHumidificationValueInSettingsScreen(TestCases testCase, String humidificationStatus) {
		String actualThermHumidificationValue = null;
		if (humidificationStatus.equalsIgnoreCase("ON")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidificationStatusInSettingsScreen")) {
				actualThermHumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
						"HumidificationStatusInSettingsScreen");
				actualThermHumidificationValue = actualThermHumidificationValue.split("%")[0];
			} else {
				return null;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidificationStatusInSettingsScreen")) {
				actualThermHumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
						"HumidificationStatusInSettingsScreen");
			} else {
				return null;
			}
		}
		return actualThermHumidificationValue;
	}

	public boolean isThermostatDehumidificationOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatDeHumidificationOption", 3);
	}

	public boolean isThermostatDehumidificationSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatDeHumidificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatDeHumidificationSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatDeHumidificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Dehumidification Switch");
		}
	}

	public boolean toggleThermostatDehumidificationSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatDeHumidificationSwitch");
	}

	public String getThermostatDehumidificationValueInHumidificationScreen(TestCases testCase) {
		String actualThermDehumidificationValue = null;
		List<WebElement> listDehumidificationSubTitles = new ArrayList<>();
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatDeHumidificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatDeHumidificationSwitch").getText()
						.equalsIgnoreCase("ON")) {
					actualThermDehumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
							"ThermostatDeHumidificationValue");
					if (actualThermDehumidificationValue.contains(" ")) {
						actualThermDehumidificationValue = actualThermDehumidificationValue.contains(" ")
								? actualThermDehumidificationValue.split(" ")[1].split("%")[0]
								: actualThermDehumidificationValue;
					} else {
						actualThermDehumidificationValue = actualThermDehumidificationValue.split("%")[0];
					}
				} else {
					actualThermDehumidificationValue = "Off";
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatDeHumidificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					listDehumidificationSubTitles = MobileUtils.getMobElements(objectDefinition, testCase,
							"ThermostatDeHumidificationValue");
					for (WebElement ele : listDehumidificationSubTitles) {
						if (ele.getAttribute("value").contains("%")) {
							actualThermDehumidificationValue = ele.getAttribute("value");
							if (actualThermDehumidificationValue.contains(" ")) {
								actualThermDehumidificationValue = actualThermDehumidificationValue.contains(" ")
										? actualThermDehumidificationValue.split(" ")[1].split("%")[0]
										: actualThermDehumidificationValue;
							} else {
								actualThermDehumidificationValue = actualThermDehumidificationValue.split("%")[0];
							}
						}
					}
				} else {
					actualThermDehumidificationValue = "Off";
				}
			}
		}
		return actualThermDehumidificationValue;
	}

	public String getThermostatDehumidificationValueInSettingsScreen(TestCases testCase, String humidificationStatus) {
		String actualThermDehumidificationValue = null;
		if (humidificationStatus.equalsIgnoreCase("ON")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeHumidificationStatusInSettingsScreen")) {
				actualThermDehumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
						"DeHumidificationStatusInSettingsScreen");
				actualThermDehumidificationValue = actualThermDehumidificationValue.split("%")[0];
			} else {
				return null;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeHumidificationStatusInSettingsScreen")) {
				actualThermDehumidificationValue = MobileUtils.getFieldValue(objectDefinition, testCase,
						"DeHumidificationStatusInSettingsScreen");
			} else {
				return null;
			}
		}
		return actualThermDehumidificationValue;
	}

	public boolean isThermostatSoundOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatSoundOption", 3);
	}

	public String getCurrentSelectedSoundOption() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatSoundValueInSettingsScreen");
	}

	public boolean isThermostatSoundOptionVisible(String indoorSoundOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_horizontal_text_view_primary_text' and @text='"
							+ indoorSoundOption + "']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", indoorSoundOption + "_subTitle", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isSoundStatusSetToExpected(TestCases testCase, String soundStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='" + soundStatus
					+ "']/parent::android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='" + soundStatus + "_cell"
					+ "']/XCUIElementTypeImage[contains(@name, '" + soundStatus + "_Image" + "')]", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean setSoundStatusToExpected(TestCases testCase, String soundStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='" + soundStatus
					+ "']/parent::android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				flag = false;
			} else {
				flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='" + soundStatus + "']");
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='" + soundStatus + "_cell"
					+ "']/XCUIElementTypeImage[contains(@name, '" + soundStatus + "_Image" + "')]", testCase)) {
				flag = false;
			} else {
				flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
						"//XCUIElementTypeCell[@name= '" + soundStatus + "_cell" + "']");
			}
		}
		return flag;
	}

	public boolean verifySoundStatusInSettingsScreen(TestCases testCase, String soundStatus) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatSoundValueInSettingsScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatSoundValueInSettingsScreen")
						.equalsIgnoreCase(soundStatus)) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isThermostatSleepBrightnessModeOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SleepBrightnessValueInSettingsScreen", 3);
	}

	public boolean isThermostatSleepBrightnessValueInSleepBrightnessModeScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"SleepBrightnessValueInSleepBrightnessModeScreen", timeOut);
	}

	public boolean isThermostatSleepBrightnessValueInSettingsScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SleepBrightnessValueInSettingsScreen",
				timeOut);
	}

	public boolean setValueToSleepBrightnessModeSlider(String value) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement brightnessSlider = MobileUtils.getMobElement(objectDefinition, testCase,
					"SleepBrightnessModeSlider");
			Dimension d1 = brightnessSlider.getSize();
			Point p1 = brightnessSlider.getLocation();
			float sliderLength = d1.getWidth();
			float pixelPerPercent = sliderLength / 100;
			float pixelToBeMoved = Integer.parseInt(value.equals("0") ? "1" : value) * pixelPerPercent;
			System.out.println("Setting for " + value);
			System.out.println("X: " + (int) (p1.getX() + pixelToBeMoved));
			System.out.println("Y: " + p1.getY());
			return MobileUtils.clickOnCoordinate(testCase, (int) (p1.getX() + pixelToBeMoved), p1.getY());
		} else {
			MobileUtils.setValueToElement(objectDefinition, testCase, "SleepBrightnessModeSlider", value);
			return true;
		}
	}

	public boolean verifyThermostatSleepBrightnessValueInSleepBrightnessModeScreen(String value) throws Exception {
		boolean flag = true;
		String displayedValue = null;
		int expectedValue = 0;
		int actualValue = 0;
		if (this.isThermostatSleepBrightnessValueInSleepBrightnessModeScreenVisible(10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedValue = MobileUtils
						.getMobElement(objectDefinition, testCase, "SleepBrightnessValueInSleepBrightnessModeScreen")
						.getAttribute("text");
				if (displayedValue.contains("%")) {
					displayedValue = displayedValue.split("%")[0];
					if (displayedValue.matches("\\d+")) {
						expectedValue = Integer.parseInt(value);
						expectedValue = expectedValue / 2;
						actualValue = Integer.parseInt(displayedValue);
						if (expectedValue <= (actualValue + 5) && expectedValue >= (actualValue - 5)) {
							return flag;
						} else {
							flag = false;
						}
					}
				} else {
					if (displayedValue.equalsIgnoreCase(value)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				displayedValue = MobileUtils
						.getMobElement(objectDefinition, testCase, "SleepBrightnessValueInSleepBrightnessModeScreen")
						.getAttribute("value");
			}
		} else {
			throw new Exception("Could not find Sleep Brightness Value Elements");
		}
		return flag;
	}

	public boolean verifyThermostatSleepBrightnessValueInSettingsScreen(String value) throws Exception {
		boolean flag = true;
		String displayedValue = null;
		int expectedValue = 0;
		int actualValue = 0;
		if (this.isThermostatSleepBrightnessValueInSettingsScreenVisible(10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedValue = MobileUtils
						.getMobElement(objectDefinition, testCase, "SleepBrightnessValueInSettingsScreen")
						.getAttribute("text");
				if (displayedValue.contains("%")) {
					displayedValue = displayedValue.split("%")[0];
					if (displayedValue.matches("\\d+")) {
						expectedValue = Integer.parseInt(value);
						expectedValue = expectedValue / 2;
						actualValue = Integer.parseInt(displayedValue);
						if (expectedValue <= (actualValue + 5) && expectedValue >= (actualValue - 5)) {
							return flag;
						} else {
							flag = false;
						}
					}
				} else {
					if (displayedValue.equalsIgnoreCase(value)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				displayedValue = MobileUtils
						.getMobElement(objectDefinition, testCase, "SleepBrightnessValueInSettingsScreen")
						.getAttribute("value");
			}
		} else {
			throw new Exception("Could not find Sleep Brightness Value Elements");
		}
		return flag;
	}

	public boolean verifyParticularThermostatConfigurationVisible(String settingName) throws Exception {
		String attribute;
		WebElement element = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
			if (settingName.equals("NAME")) {
				settingName = "Name";
			}
			element = MobileUtils.getMobElement(testCase, "XPATH",
					"//android.widget.TextView[@" + attribute + "='" + settingName + "']");
		} else {
			attribute = "value";
			element = MobileUtils.getMobElement(testCase, "XPATH",
					"//XCUIElementTypeStaticText[@" + attribute + "='" + settingName + "']");
		}
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSetUpHomeKitAndSiriOptionVisible(String settingOption) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// *[@text='Set up HomeKit & Siri']
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='" + settingOption + "']",
					testCase);
		} else {
			// XCUIElementTypeStaticText[@value='Set up HomeKit & Siri']
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + settingOption + "']", testCase);
		}
	}

	public boolean isThermostatEmergencyHeatSwitchVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatEmergencyHeatSwitch", 3);
	}

	public boolean isThermostatNameTextBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatNameTextbox", timeOut);
	}

	public boolean clearThermostatNameTextBox() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatNameTextbox");
			return MobileUtils.clearTextField(objectDefinition, testCase, "ThermostatNameTextbox");
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeTextField"))
					.clear();
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatNameTextbox");
		}
	}

	public boolean setValueToThermostatNameTextBox(String value) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "ThermostatNameTextbox", value);
		} else {
			flag = flag & MobileUtils.setValueToElement(testCase, "XPATH",
					"//XCUIElementTypeCell[1]/XCUIElementTypeTextField", value);
			MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonInIOSKeyboard");
		}
		return flag;
	}

	public boolean isThermostatVentilationOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatVentilationOption", 3);
	}

	public String getCurrentSelectedVentilationOption() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatVentilationValueInSettingsScreen");
	}

	public boolean isThermostatVentilationOptionVisible(String indoorVentilationOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_horizontal_text_view_primary_text' and @text='"
							+ indoorVentilationOption + "']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", indoorVentilationOption + "_subTitle", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isVentilationStatusSetToExpected(TestCases testCase, String ventilationStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='" + ventilationStatus
					+ "']/parent::android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name='" + ventilationStatus + "_cell"
							+ "']/XCUIElementTypeImage[contains(@name, '" + ventilationStatus + "_Image" + "')]",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean setVentilationStatusToExpected(TestCases testCase, String ventilationStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='" + ventilationStatus
					+ "']/parent::android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				flag = false;
			} else {
				flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='" + ventilationStatus + "']");
				flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='" + ventilationStatus + "']");
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name='" + ventilationStatus + "_cell"
							+ "']/XCUIElementTypeImage[contains(@name, '" + ventilationStatus + "_Image" + "')]",
					testCase)) {
				flag = false;
			} else {
				flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
						"//XCUIElementTypeCell[@name= '" + ventilationStatus + "_cell" + "']");
			}
		}
		return flag;
	}

	public boolean verifyVentilationStatusInSettingsScreen(TestCases testCase, String soundStatus) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatVentilationValueInSettingsScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatVentilationValueInSettingsScreen")
						.equalsIgnoreCase(soundStatus)) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}
}