package com.honeywell.lyric.das.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.RelayUtils;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.SensorStatusScreen;

import io.appium.java_client.MobileElement;

public class DASSensorUtils {
	private boolean flag = true;

	public static boolean enrollDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_ENROLLED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIContactSensorEnroll_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean openDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_OPENED_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIContactSensorOpen_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean closeDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_CLOSED_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIContactSensorClosed_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("ALARM_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		inputs.setInputValue("DOOR_TAMPERED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIDoorContactSensorTampered();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperClearDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIDoorContactSensorTamperCleared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean enrollWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_ENROLLED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIContactSensorEnroll_Window();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean openWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_OPENED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		System.out.println("Open Window");
		try {
			RelayUtils.RSIContactSensorOpen_Window();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean closeWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_CLOSED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		System.out.println("Close Window");
		try {
			RelayUtils.RSIContactSensorClosed_Window();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean enrollMotionSensor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("MOTIONSENSOR_ENROLLED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIMotionSensorEnroll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("ALARM_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		inputs.setInputValue("WINDOW_TAMPERED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIWindowContactSensorTamperON();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperClearWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_TAMPER_CLEARED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIWindowContactSensorTamperCleared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean openMotionSensor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("MOTION_SENSOR_OPENED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			// RelayUtils.RSIContactSensorOpen_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean closeMotionSensor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("MOTION_SENSOR_CLOSED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			// RelayUtils.RSIContactSensorClosed_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperMotionSensor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("ALARM_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		inputs.setInputValue("MOTION_SENSOR_TAMPERED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIMotionSensorTampered();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean tamperClearMotionSensor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("MOTION_SENSOR_TAMPER_CLEARED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIMotionSensorTamperCleared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean enrollKeyfob(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("KEYFOB_ENROLLED_TIME",
				LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.RSIKeyfobEnroll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean setHomeViaKeyfob(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("KEYFOB_HOME_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.Keyfob_Home();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean setAwayViaKeyfob(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("KEYFOB_AWAY_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.Keyfob_Away();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean setNightViaKeyfob(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("KEYFOB_NIGHT_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.Keyfob_Night();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean setOffViaKeyfob(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("KEYFOB_OFF_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		try {
			RelayUtils.Keyfob_Off();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static List<WebElement> getSensorList(TestCases testCase) {
		SensorStatusScreen sensorStatusScreen = new SensorStatusScreen(testCase);
		return sensorStatusScreen.getSensorList();
	}

	public boolean verifySensorState(TestCases testCase, TestCaseInputs inputs, String sensor, String states) {
		SensorStatusScreen sensorStatusScreen = new SensorStatusScreen(testCase);
		String sensorName = "";
		String sensorState = "";
		if (sensor.equalsIgnoreCase("Door")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		} else if (sensor.equalsIgnoreCase("window")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
		} else if (sensor.equalsIgnoreCase("motion sensor")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor type not handled");
		}

		if (states.equalsIgnoreCase("open")) {
			sensorState = "Open";
		} else if (states.equalsIgnoreCase("closed")) {
			sensorState = "Closed";
		} else if (states.equalsIgnoreCase("tamper cleared")) {
			sensorState = "Closed";
		} else if (states.equalsIgnoreCase("tamper cleared to open")) {
			sensorState = "Open";
		} else if (states.equalsIgnoreCase("off")) {
			sensorState = "OFF";
			if (!testCase.getPlatform().contains("IOS")) {
				sensorState = "Off";
			}
		} else if (states.equalsIgnoreCase("cover tampered")) {
			sensorState = "Cover Tampered";
		}else if (states.equalsIgnoreCase("standby")) {
			sensorState = "Standby";
		} else if (states.equalsIgnoreCase("good")) {
			sensorState = "Good";
		} else if (states.equalsIgnoreCase("active")) {
			sensorState = "Active";
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor state not handled");
		}
		List<WebElement> list;

		list = DASSensorUtils.getSensorList(testCase);
		System.out.println("########list.size(): " + list.size());
		System.out.println("########sensorName: " + sensorName);
		System.out.println("########sensorState: " + sensorState);
		boolean sensorStateMatched = false;
		for (int i = 0; i < list.size(); i++) {
			if (testCase.getPlatform().contains("IOS")) {
				if (testCase.getMobileDriver()
						.findElements(By.xpath(
								"//*[contains(@name,'SensorStatus_" + i + "_cell')]//*[@value='" + sensorName + "']"))
						.size() > 0) {
					System.out.println("Sensor status");
					// Sensor status
					if (states.contains("tamper cleared")) {
						if (MobileUtils.isMobElementExists("xpath",
								"//*[contains(@name,'SensorStatus_" + i + "_Image')]", testCase, 10)) {
							MobileUtils.clickOnElement(testCase, "xpath",
									"//*[contains(@name,'SensorStatus_" + i + "_Image')]");
							//code to click on clear tamper

							SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
							flag = flag & settingScreen.clickOnClearCoverTamperOption();
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(settingScreen.isSensorTamperClearPopupDisplayed()){
								flag = flag & settingScreen.clickOnOkTamperClearPopup();
							}
						}
						inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME",
								LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
						Keyword.ReportStep_Pass(testCase,
								"DOOR_TAMPER_CLEARED_TIME " + inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME"));
					}

					if (testCase.getMobileDriver().findElements(By.xpath("//*[contains(@name,'SensorStatus_" + i
							+ "_cell')]//*[contains(@value,'" + sensorState + "')]")).size() > 0) {
						Keyword.ReportStep_Pass(testCase, sensorName + " is in " + sensorState);
						sensorStateMatched = true;
						break;
					}
				} else {
					System.out.println("Sensor list");
					// Sensor list
					List<MobileElement> sensorNameList = testCase.getMobileDriver()
							.findElements(By.xpath("//*[@name='Sensor_cell']//*[@name='Sensor_subTitle']"));
					List<MobileElement> sensorStatusList = testCase.getMobileDriver()
							.findElements(By.xpath("//*[@name='Sensor_cell']//*[@name='Sensor_value']"));
					for (int k = 0; k < sensorNameList.size(); k++) {
						if (sensorNameList.get(k).getAttribute("value").equalsIgnoreCase(sensorName)) {
							if (states.contains("tamper cleared")) {
								sensorStatusList.get(k).click();
								try {
									Thread.sleep(10000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME",
										LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
								Keyword.ReportStep_Pass(testCase,
										"DOOR_TAMPER_CLEARED_TIME " + inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME"));
							} else if (sensorStatusList.get(k).getAttribute("value").equalsIgnoreCase(states)) {
								Keyword.ReportStep_Pass(testCase, sensorName + " is in " + sensorState);
								sensorStateMatched = true;
								break;
							}
						}
					}
				}
			} else {
				if (testCase.getMobileDriver().findElements(By.xpath("//*[@content-desc = '" + sensorName + "']"))
						.size() > 0) {
					if (states.contains("tamper cleared")) {
						//// *[@content-desc='left_drawable'] - Removed
						if (MobileUtils.isMobElementExists("xpath", "//*[@content-desc = '" + sensorName + "']",
								testCase, 10)) {
							Keyword.ReportStep_Pass(testCase,
									"Current state "
											+ testCase
											.getMobileDriver().findElement(By.xpath("//*[@content-desc = '"
													+ sensorName + "']//*[contains(@text, 'Cover Tampered')]"))
											.getText());
							MobileUtils.clickOnElement(testCase, "xpath", "//*[@content-desc = '" + sensorName + "']");
							// MobileUtils.clickOnElement(fieldObjects, testCase, "BackToViewList");
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME",
								LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
						Keyword.ReportStep_Pass(testCase,
								"DOOR_TAMPER_CLEARED_TIME " + inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME"));
					}
					if (MobileUtils.isMobElementExists("ID", "action_button", testCase, 5)) {
						MobileUtils.clickOnElement(testCase, "ID", "action_button");
						DASCommandControlUtils.waitForProgressBarToComplete(testCase, "LOADING PROGRESS TEXT", 2);
					}
					if (testCase.getMobileDriver()
							.findElements(By.xpath(
									"//*[@content-desc = '" + sensorName + "']//*[@text ='" + sensorState + "']"))
							.size() > 0) {
						Keyword.ReportStep_Pass(testCase, sensorName + " is in " + sensorState);
						sensorStateMatched = true;
					}

				}
			}
		}
		if (sensorStatusScreen.isSensorStatusVisible() && sensorStatusScreen.isAddButtonNotVisibleInSensorStatusScreen()) {
			flag = flag & sensorStatusScreen.clickOnSensorStatusScreenBack(testCase);
		}
		if (list.size() == 0) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "not able to read Sensor list");
		}
		if (!sensorStateMatched) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Sensor: " + sensorName + " state expected is " + sensorState);
		}
		return flag;
	}

	//NAVIGATION FROM DASHBOARD To SENSOR
	public static boolean navigateToSensorTypeSettingsFromDashboard(String SensorType,TestCaseInputs inputs,TestCases testCase){
		boolean flag=false;
		SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
		try {
			switch(SensorType){
			case "DOOR ACCESS SETTINGS":{
				flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						"Base Station Configuration");
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
				break;
			}
			case "WINDOW ACCESS SETTINGS":{
				flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1"));
				break;
			}
			case "MOTION SENSOR SETTINGS":{
				flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				inputs.setInputValue(DASInputVariables.MOTIONSENSORTYPE,DASInputVariables.MOTIONSENSOR);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"));
				break;
			}
			default: {
				System.out.println("Input not handled");
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE,"Input not handled -"+SensorType);
				break;
			}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	//NAVIGATION FROM DASHBOARD To SENSOR
	public static boolean navigateToSensorTypeSettingsFromSecuritySolutionCard(String SensorType,TestCaseInputs inputs,TestCases testCase){
		boolean flag=false;
		SensorSettingScreen sensorScreen = new SensorSettingScreen(testCase);
		try {
			switch(SensorType){
			case "DOOR ACCESS SETTINGS":{
				SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
				if (security.isAppSettingsIconVisible(15)) {
					flag = security.clickOnAppSettingsIcon();
				}

				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						"Base Station Configuration");
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"));
				break;
			}
			case "WINDOW ACCESS SETTINGS":{
				SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
				if (security.isAppSettingsIconVisible(15)) {
					flag = security.clickOnAppSettingsIcon();
				}
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1"));
				break;
			}
			case "MOTION SENSOR SETTINGS":{
				flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
				inputs.setInputValue(DASInputVariables.MOTIONSENSORTYPE,DASInputVariables.MOTIONSENSOR);
				flag = flag & sensorScreen.clickOnUserGivenSensorName(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"));
				break;
			}
			default: {
				System.out.println("Input not handled");
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE,"Input not handled -"+SensorType);
				break;
			}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
