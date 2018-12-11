package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSensorUtils;

public class SensorStatusScreen extends MobileScreens {

	private static final String screenName = "SensorStatus";

	public SensorStatusScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public List<WebElement> getSensorList() {
		List<WebElement> list;
		if (!testCase.getPlatform().contains("IOS")) {
			// list = MobileUtils.getMobElements(testCase, "xpath",
			// "//*[contains(@content-desc,'sensor_status_item_')]");
			list = MobileUtils.getMobElements(testCase, "xpath",
					"//*[@resource-id = 'com.honeywell.android.lyric:id/parent_alert_item']");
		} else {
			list = MobileUtils.getMobElements(objectDefinition, testCase, "SensorList");
			if (list.size() == 0) {
				list = MobileUtils.getMobElements(objectDefinition, testCase, "AltSensorList");
			}
		}
		return list;
	}

	public boolean isAddButtonNotVisibleInSensorStatusScreen() {
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "AddButton", 5)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSensorStatusVisible() {
		if (!testCase.getPlatform().contains("IOS")) {
			// return MobileUtils.isMobElementExists("id", "sensor_issue_priority_text",
			// testCase);
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/toolbar_device_name' and @text='Security']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//*[@value='Sensors_SECURITY']", testCase);
		}
	}

	public boolean clickOnSensorStatusScreenBack(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorListBack");
	}

	public boolean isSensorOfflineInStatus() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorisOfflineText");
	}

	public boolean isCoverTamperedTextVisibleinSensorStatusScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DASSensorUtils sensorUtils = new DASSensorUtils();
		flag = flag & sensorUtils.verifySensorState(testCase, inputs, "door", "cover tampered");
		return flag;
	}

	public boolean selectTamperedClear(TestCases testCase, TestCaseInputs inputs, String Sensor) {
		List<WebElement> list;
		list = DASSensorUtils.getSensorList(testCase);
		String sensorName = "";
		if (Sensor.equalsIgnoreCase("Door") || Sensor.equalsIgnoreCase("Door Sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		} else if (Sensor.equalsIgnoreCase("window") || Sensor.equalsIgnoreCase("Window Sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
		} else if (Sensor.equalsIgnoreCase("motion sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
		} else if (Sensor.equalsIgnoreCase("ISMV") || Sensor.equalsIgnoreCase("ISMV Sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1");
		} else if (Sensor.equalsIgnoreCase("OSMV") || Sensor.equalsIgnoreCase("OSMV Sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor type not handled");
		}
		for (int i = 0; i < list.size(); i++) {
			if (testCase.getPlatform().contains("IOS")) {
				if (testCase.getMobileDriver()
						.findElements(By.xpath(
								"//*[contains(@name,'SensorStatus_" + i + "_cell')]//*[@value='" + sensorName + "']"))
						.size() > 0) {
					if (testCase.getMobileDriver().findElements(By.xpath(
							"//*[contains(@name,'SensorStatus_" + i + "_cell')]//*[contains(@value,'Cover tampered')]"))
							.size() > 0) {
						Keyword.ReportStep_Pass(testCase, sensorName + " is in " + "Cover tampered");
					}
					System.out.println("Sensor status");
					// Sensor status
					System.out.println("//*[contains(@name,'SensorStatus_" + i + "_Image')]");
					if (MobileUtils.isMobElementExists("xpath", "//*[contains(@name,'SensorStatus_" + i + "_Image')]",
							testCase, 10)) {
						MobileUtils.clickOnElement(testCase, "xpath",
								"//*[contains(@name,'SensorStatus_" + i + "_Image')]");
					}
				}
			} else if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (testCase.getMobileDriver().findElements(By.xpath("//*[@content-desc = '" + sensorName + "']"))
						.size() > 0) {
					if (MobileUtils.isMobElementExists("xpath", "//*[@content-desc = '" + sensorName + "']", testCase,
							10)) {
						Keyword.ReportStep_Pass(testCase,
								"Current state "
										+ testCase
												.getMobileDriver().findElement(By.xpath("//*[@content-desc = '"
														+ sensorName + "']//*[contains(@text, 'Cover Tampered')]"))
												.getText());
						MobileUtils.clickOnElement(testCase, "xpath", "//*[@content-desc = '" + sensorName + "']");
						break;
					}
				}
			}
		}
		return true;
	}

	public boolean isLowbatteryVisible() {
		if (!testCase.getPlatform().contains("IOS")) {
			return MobileUtils.isMobElementExists("TEXT", "Low Battery", testCase);
		} else {
			return MobileUtils.isMobElementExists("xpath", "//*[@value='Low Battery']", testCase);
		}
	}

	public boolean isLowbatteryTextVisibleInSensroDetailsScreen() {
		if (!testCase.getPlatform().contains("IOS")) {
			return MobileUtils.isMobElementExists("TEXT", "Sensor with Low Battery", testCase);
		} else {
			return MobileUtils.isMobElementExists("xpath", "//*[@value='Sensor With Low Battery']", testCase);
		}
	}

}