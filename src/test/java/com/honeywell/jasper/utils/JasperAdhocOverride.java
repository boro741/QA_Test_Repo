package com.honeywell.jasper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.GlobalVariables;
import com.honeywell.screens.AdhocScreen;
import com.honeywell.screens.FlyCatcherPrimaryCard;

import io.appium.java_client.TouchAction;



public class JasperAdhocOverride {

	public static boolean verifyAdHocHoldPermanentlyStatus(TestCases testCase, TestCaseInputs inputs,
			String overrideSetPoints) {
		boolean flag = true;
		try {
			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
					testCase.getMobileDriver());
			fWait.pollingEvery(2, TimeUnit.SECONDS);
			fWait.withTimeout(60, TimeUnit.SECONDS);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			AdhocScreen adhoc = new AdhocScreen(testCase);
			Double overrideTemp = Double.parseDouble(overrideSetPoints);
			String status;
			if (statInfo.getThermostatUnits().equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
				status = "Hold " + overrideTemp.intValue() + "\u00B0 Permanently";
			} else {
				status = "Hold " + overrideTemp + "\u00B0 Permanently";
			}
			try {
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						String adHocStatus = "";
						if (adhoc.isAdhocStatusVisible()) {
							adHocStatus = adhoc.getAdhocStatusElement();
						}
						if (status.equalsIgnoreCase(adHocStatus)) {
							return true;
						} else {
							return false;
						}
					}
				});
				flag = isEventReceived;
			} catch (TimeoutException e) {
				flag = false;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:" + status
							+ " is not correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:" + status
							+ " is not correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				}
			}

			if (flag) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Keyword.ReportStep_Pass(testCase, "Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:"
							+ status + " is correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				} else {
					Keyword.ReportStep_Pass(testCase, "Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:"
							+ status + " is correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				}
			} else {
				flag = false;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:" + status
							+ " is not correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Ad Hoc Hold Permanently Status : Expected Ad hoc status:" + status
							+ " is not correctly displayed in the primary card with overridden set points:"
							+ adhoc.getAdhocStatusElement());
				}
			}
		} catch (Exception e){

		}
		return flag;
	}

	// This method will click on the system mode button and change the system
	// mode to
	// the value passed in the parameter by clicking on the mode in primary card
	/**
	 * <p>
	 * The changeSystemMode method changes system mode on the primary card by
	 * tapping on the system mode icon and selecting the system mode from the
	 * list of modes displayed.
	 * </p>
	 * 
	 * @param TestCases
	 * @param TestCaseInputs
	 * @param String
	 *            - This is the expected mode to which system mode has to be
	 *            changed to.
	 * @return Boolean indicating success or failure.
	 * 
	 * @author h119237 - Pratik Lalseta.
	 */
	public static boolean changeSystemMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);

		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Create Schedule : Cannot change system mode because thermostat is offline");
				return true;
			}
			if (expectedMode.equals("Heat")) {
				fly.changeSystemModeToHeatMode();
			}

			else if (expectedMode.equals("Cool")) {
				fly.changeSystemModeToCoolMode();
			}

			else if (expectedMode.equals("Off")) {
				fly.changeSystemModeToOffMode();
			} else if (expectedMode.equals("Auto")) {
				fly.changeSystemModeToAutoMode();
			}
			
			if (fly.IsCloseButtonVisible(5)){
				fly.ClickCloseButton();
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured : " + e.getMessage());

		}
		return flag;
	}

	public static boolean verifyVisibilityOfAdHocButtonOnSolutionCard(TestCases testCase, boolean isVisible) {
		boolean flag = true;
		AdhocScreen adhoc = new AdhocScreen(testCase);
		if (isVisible) {
			if (adhoc.isAdhocStatusVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Ad Hoc Override Present On Solution Card : Ad hoc override button is visible on solution card");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Ad Hoc Override Present On Solution Card : Ad hoc override button is not visible on solution card");
			}
		} else {
			if (adhoc.isAdhocStatusVisible()) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Ad Hoc Override Present On Solution Card : Ad hoc override button visible on solution card");
			} else {
				Keyword.ReportStep_Pass(testCase,
						"Verify Ad Hoc Override Present On Solution Card : Ad hoc override button not visible on solution card");
			}
		}
		return flag;
	}

	public static boolean resumeScheduleFromAdHoc(TestCases testCase) {
		boolean flag = true;
		AdhocScreen adhoc = new AdhocScreen(testCase);
		if (adhoc.isAdhocStatusVisible()) {
			flag = flag & adhoc.clickOnAdhocStatusButton();
			flag = flag & adhoc.clickOnResumeButton();
		} else {
			flag = false;
			Keyword.ReportStep_Pass(testCase, "Resume Schedule : Ad hoc is not overridden cannot resume schedule");
		}
		return flag;
	}

	public static boolean verifySetPointsAfterScheduleResume(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String setPoints = "";
		String systemMode = statInfo.getThermoStatMode();
		if (systemMode.equalsIgnoreCase("Auto")) {
			systemMode = statInfo.getThermostatModeWhenAutoChangeOverActive();
		}
		if (systemMode.equalsIgnoreCase("Cool")) {
			setPoints = statInfo.getCoolSetPoints();
		} else if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
			setPoints = statInfo.getHeatSetPoints();
		}
		flag = flag & verifyDialerTemperature(testCase, inputs, Double.parseDouble(setPoints));
		return flag;
	}

	public static boolean verifyDialerTemperature(TestCases testCase, TestCaseInputs inputs, Double expectedTemp) {
		boolean flag = true;
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
				testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(30, TimeUnit.SECONDS);
		try {
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					Double currentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					if (currentSetPoint - expectedTemp == 0.0) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Dialer Set Points : Dialer set points is set to : " + expectedTemp);
						return true;
					} else {
						return false;
					}
				}
			});
			flag = isEventReceived;
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Dialer Set Points : Dialer set points did not change to : " + expectedTemp
					+ " after waiting for 30 seconds");
		}
		return flag;
	}

	public static boolean holdSetPointsPermanentlyFromAdHoc(TestCases testCase) {
		boolean flag = true;
                AdhocScreen adhoc = new AdhocScreen(testCase);
		if (adhoc.isAdhocStatusVisible()) {
			flag = flag & adhoc.clickOnAdhocStatusButton();
			flag = flag & adhoc.clickOnPemanentlyHoldButton();
		} else {
			flag = false;
			Keyword.ReportStep_Pass(testCase,
					"Hold Set Points From AdHoc : Ad hoc is not overridden cannot resume schedule");
		}
		return flag;
	}

	public static boolean holdSetPointsUntilFromAdHoc(TestCases testCase) {
		boolean flag = true;
        AdhocScreen adhoc = new AdhocScreen(testCase);
		if (adhoc.isAdhocStatusVisible()) {
			flag = flag & adhoc.clickOnAdhocStatusButton();
			flag = flag & adhoc.clickOnHoldUntilButton();
		} else {
			flag = false;
			Keyword.ReportStep_Pass(testCase,
					"Hold Set Points From AdHoc : Ad hoc is not overridden cannot resume schedule");
		}
		return flag;
	}
	
	public static String getAndroidDeviceTime(TestCases testCase) {
		String time = " ";
		String cmd = "adb shell date";
		time = executeADBCommand(testCase, cmd);
		return time;
	}
	public static String executeADBCommand(TestCases testCase, String cmd) {
		String output = " ";
		try {
			String[] tempArr = cmd.split(" ");
			String[] cmdArray;
			List<String> cmdList = new ArrayList<String>();
			cmdList.add(SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "LOCAL_ADB_PATH"));
			for (int i = 1; i < tempArr.length; i++) {
				cmdList.add(tempArr[i]);
			}
			cmdArray = cmdList.toArray(new String[cmdList.size()]);
			int n = tempArr.length - 1;
			String[] commandArr = new String[n];
			System.arraycopy(tempArr, 1, commandArr, 0, n);
			Runtime run = Runtime.getRuntime();
			Process pr = null;

			// pr = run.exec(new
			// String[]{"/usr/local/Cellar/android-sdk/24.4.1_1/bin/adb"},commandArr);
			pr = run.exec(cmdArray);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String temp;
			while ((temp = buf.readLine()) != null)
				output += temp;
		} catch (IOException e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "IO Exception caused by " + e.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return output;
	}
	public static String getIOSSimulatorTime(TestCases testCase) {
		String time = " ";
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			time = vacationDateFormat.format(cal.getTime());
		} catch (Exception e) {
			time = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get IOS Simulator Time : Error Occurred : " + e.getMessage());
		}
		return time;
	}

	public static boolean setHoldUntilTime(TestCases testCase, TestCaseInputs inputs, String day,
			String timeIn12Hours) {
		boolean flag = true;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		try {
			SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");
			Date date = new Date();
			Calendar c1 = Calendar.getInstance();
			date = time12Format.parse(timeIn12Hours);
			c1.setTime(date);
			int hourToSet;
			int minutesToSet = c1.get(Calendar.MINUTE);
			String ampm = "";
			if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
				if (minutesToSet % 10 != 0) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Set Hold Until Time : Invalid time. Please provide minutes time to the nearest 10 minutes for EMEA");
					return flag;
				}
			} else if (statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
				if (minutesToSet % 15 != 0) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Set Hold Until Time : Invalid time. Please provide minutes time to the nearest 15 minutes for NA");
					return flag;
				}
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : Setting hold unitl day to : " + day);
				Keyword.ReportStep_Pass(testCase,
						"Set Hold Until Time : Setting hold unitl time to : " + timeIn12Hours);
				WebElement dayPicker = MobileUtils.getMobElement(testCase, "xpath",
						"//android.widget.NumberPicker[@index='0']");
				WebElement ele = dayPicker.findElement(By.id("numberpicker_input"));
				if (ele.getText().equalsIgnoreCase(day)) {
					Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : Selected day is set to " + day);
				} else {
					ele = MobileUtils.getMobElement(testCase, "xpath",
							"//android.widget.NumberPicker[@index='0']/android.widget.Button");
					try {
						TouchAction t1 = new TouchAction(testCase.getMobileDriver());
						t1.longPress(ele).perform();
						Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : Selected day is set to " + day);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Hold Until Time : Failed to set day to " + day + ". Error : " + e.getMessage());
					}
				}
				if (!MobileUtils.isMobElementExists("xpath", "//android.widget.NumberPicker[@index='3']", testCase,
						3)) {
					hourToSet = c1.get(Calendar.HOUR_OF_DAY);
				} else {
					hourToSet = c1.get(Calendar.HOUR);
					if (hourToSet == 0) {
						hourToSet = 12;
					}
				}

				WebElement hourPicker = MobileUtils.getMobElement(testCase, "xpath",
						"//android.widget.NumberPicker[@index='1']");
				int displayedHour = Integer
						.parseInt(hourPicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
				WebElement minutePicker = MobileUtils.getMobElement(testCase, "xpath",
						"//android.widget.NumberPicker[@index='2']");

				WebElement buttonToTap;
				TouchAction t1 = new TouchAction(testCase.getMobileDriver());
				if (displayedHour == hourToSet) {
					Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : Successfully set hours to : " + hourToSet);
				} else if (displayedHour > hourToSet) {
					buttonToTap = MobileUtils.getMobElement(testCase, "xpath",
							"//android.widget.NumberPicker[@index='1']/android.widget.Button[@index='0']");
					while (displayedHour != hourToSet) {
						MobileUtils.longPress(testCase, buttonToTap, 1);
						//t1.longPress(buttonToTap, 1).perform();
						displayedHour = Integer
								.parseInt(hourPicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
					}
				} else if (displayedHour < hourToSet) {
					if (MobileUtils.isMobElementExists("xpath",
							"//android.widget.NumberPicker[@index='1']/android.widget.Button[@index='1']", testCase,
							3)) {
						buttonToTap = MobileUtils.getMobElement(testCase, "xpath",
								"//android.widget.NumberPicker[@index='1']/android.widget.Button[@index='1']");
						MobileUtils.longPress(testCase, buttonToTap, 1);
						//t1.longPress(buttonToTap, 1).perform();
						displayedHour = Integer
								.parseInt(hourPicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
					}
					buttonToTap = MobileUtils.getMobElement(testCase, "xpath",
							"//android.widget.NumberPicker[@index='1']/android.widget.Button[@index='2']");
					while (displayedHour != hourToSet) {
						MobileUtils.longPress(testCase, buttonToTap, 1);
						//t1.longPress(buttonToTap, 1).perform();
						displayedHour = Integer
								.parseInt(hourPicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
					}
				}

				int displayedMinutes = Integer
						.parseInt(minutePicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
				if (displayedMinutes == minutesToSet) {
					Keyword.ReportStep_Pass(testCase,
							"Set Hold Until Time : Successfully set minutes to : " + minutesToSet);
				} else if (displayedMinutes > minutesToSet) {
					int count = 7;
					buttonToTap = MobileUtils.getMobElement(testCase, "xpath",
							"//android.widget.NumberPicker[@index='2']/android.widget.Button[@index='0']");
					while (displayedMinutes != minutesToSet) {
						MobileUtils.longPress(testCase, buttonToTap, 1);
						//t1.longPress(buttonToTap, 1).perform();
						displayedMinutes = Integer
								.parseInt(minutePicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
						count--;
						if (count == 0) {
							break;
						}
					}
				} else if (displayedMinutes < minutesToSet) {
					int count = 7;
					buttonToTap = MobileUtils.getMobElement(testCase, "xpath",
							"//android.widget.NumberPicker[@index='2']/android.widget.Button[@index='2']");
					while (displayedMinutes != minutesToSet) {
						MobileUtils.longPress(testCase, buttonToTap, 1);
						//t1.longPress(buttonToTap, 1).perform();
						displayedMinutes = Integer
								.parseInt(minutePicker.findElement(By.id("numberpicker_input")).getAttribute("text"));
						count--;
						if (count == 0) {
							break;
						}
					}
				}

				if (MobileUtils.isMobElementExists("xpath", "//android.widget.NumberPicker[@index='3']", testCase, 3)) {
					try {
						int temp = c1.get(Calendar.AM_PM);
						if (temp == Calendar.AM) {
							ampm = "AM";
						} else {
							ampm = "PM";
						}
						WebElement AMPMPicker = MobileUtils.getMobElement(testCase, "xpath",
								"//android.widget.NumberPicker[@index='3']");
						ele = AMPMPicker.findElement(By.id("numberpicker_input"));
						if (ele.getText().equalsIgnoreCase(ampm)) {
							Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : AMPM is set to " + ampm);
						} else {
							ele = MobileUtils.getMobElement(testCase, "xpath",
									"//android.widget.NumberPicker[@index='3']/android.widget.Button");
							try {
								t1.longPress(ele).perform();
								Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : AMPM is set to " + ampm);
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Set Hold Until Time : Failed to set AMPM to " + ampm + ". Error : "
												+ e.getMessage());
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Set Hold Until Time : Setting hold unitl day to : " + day);
				Keyword.ReportStep_Pass(testCase,
						"Set Hold Until Time : Setting hold unitl time to : " + timeIn12Hours);
				MobileUtils.clickOnElement(testCase, "name", "Hold Until");
				if (MobileUtils.isMobElementExists("xpath", "//UIAPickerWheel[4]", testCase,15)) {
					hourToSet = c1.get(Calendar.HOUR);
					if (hourToSet == 0) {
						hourToSet = 12;
					}
					int temp = c1.get(Calendar.AM_PM);
					if (temp == Calendar.AM) {
						ampm = "AM";
					} else {
						ampm = "PM";
					}
				} else {
					hourToSet = c1.get(Calendar.HOUR_OF_DAY);
				}

				// flag = flag & MobileUtils.setValueToElement(testCase,
				// "xpath", "//UIAPickerWheel[1]", day);
//				String currentDisplayedDay = MobileUtils.getMobElement(testCase, "xpath", "//UIAPickerWheel[1]")
//						.getAttribute("value");			
				String currentDisplayedDay = testCase.getMobileDriver().findElement(By.xpath("//UIAPickerWheel[1]")).getAttribute("value");
				SimpleDateFormat IOSDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				SimpleDateFormat timePickerDateFormat = new SimpleDateFormat("EEE, MMM d");
				String displayedDate;
				if (currentDisplayedDay.equals("Today")) {
					String currentTime = getIOSSimulatorTime(testCase);
					displayedDate = timePickerDateFormat.format(IOSDateFormat.parse(currentTime));
				} else {
					displayedDate = currentDisplayedDay;
				}
				if (!displayedDate.equalsIgnoreCase(day)) {
					WebElement dayPicker = testCase.getMobileDriver().findElement(By.xpath("//UIAPickerWheel[1]"));
					Point p1 = dayPicker.getLocation();
					Dimension d1 = dayPicker.getSize();
					int x = p1.getX();
					int y;
					TouchAction t1 = new TouchAction(testCase.getMobileDriver());
					if (currentDisplayedDay.equalsIgnoreCase("Today")) {
						y = p1.getY() + d1.getHeight() / 2 + 20;
					} else {
						y = p1.getY() + d1.getHeight() / 2 - 20;
					}
					t1.tap(dayPicker, x, y).perform();
				}
				if (testCase.getMobileDriver().findElement(By.xpath("//UIAPickerWheel[4]")) != null) {
					flag = flag & MobileUtils.setValueToElement(testCase, "xpath", "//UIAPickerWheel[4]", ampm);
				}
				flag = flag & MobileUtils.setValueToElement(testCase, "xpath", "//UIAPickerWheel[2]",
						String.valueOf(hourToSet));
				String min;
				if (minutesToSet == 0) {
					min = "00";
				} else {
					min = String.valueOf(minutesToSet);
				}
				flag = flag & MobileUtils.setValueToElement(testCase, "xpath", "//UIAPickerWheel[3]", min);
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean verifyHoldUntilTimeIsSet(TestCases testCase, String time, boolean isValidTime) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "AdHocOverride");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "OkButton");
				if (isValidTime) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Hold Until Time : Verifying hold until time is set to : " + time);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OkButton", 5)) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Failed to set hold until time to a valid time");
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Successfully set hold until time to a valid time");
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Hold Until Time : Verifying hold until time is not set to : " + time);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OkButton", 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Failed to set hold until time to an invalid time");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Successfully set hold until time to an invalid time");
					}
				}
			} else {
				String[] dateAndTime = time.split("_");
				String currentTime = getIOSSimulatorTime(testCase);
				SimpleDateFormat IOSDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				SimpleDateFormat timePickerDateFormat = new SimpleDateFormat("EEE, MMM d");
				String currentDate = timePickerDateFormat.format(IOSDateFormat.parse(currentTime));
				SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");
				Calendar c1 = Calendar.getInstance();
				c1.setTime(time12Format.parse(dateAndTime[1]));
				if (currentDate.equalsIgnoreCase(dateAndTime[0])) {
					dateAndTime[0] = "Today";
				}
				String hours = "";
				String ampm = "";
				String minutes = "";
				if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypePickerWheel[4]", testCase, 3)) {
					int temp = c1.get(Calendar.HOUR);
					if (temp == 0) {
						temp = 12;
					}
					hours = String.valueOf(temp);
					temp = c1.get(Calendar.AM_PM);
					if (temp == Calendar.AM) {
						ampm = "AM";
					} else {
						ampm = "PM";
					}
				} else {
					hours = String.valueOf(c1.get(Calendar.HOUR_OF_DAY));
				}
				hours = hours + " o'clock";
				if (c1.get(Calendar.MINUTE) == 0) {
					minutes = "00";
				} else {
					minutes = String.valueOf(c1.get(Calendar.MINUTE));
				}
				minutes = minutes + " minutes";
				if (isValidTime) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Hold Until Time : Verifying hold until time is set to : " + time);
					if (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[1]")
							.getAttribute("value").equalsIgnoreCase(dateAndTime[0])) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Displayed date is correct on the time picker");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Displayed date is not correct on the time picker");
					}
					if (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[2]")
							.getAttribute("value").equalsIgnoreCase(hours)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Displayed hour is correct on the time picker");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Displayed hour is not correct on the time picker");
					}

					if (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[3]")
							.getAttribute("value").equalsIgnoreCase(minutes)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Displayed minutes is correct on the time picker");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Displayed minutes is not correct on the time picker");
					}
					if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypePickerWheel[4]", testCase, 3)) {
						if (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[4]")
								.getAttribute("value").trim().equalsIgnoreCase(ampm.trim())) {
							Keyword.ReportStep_Pass(testCase,
									"Verify Hold Until Time : Displayed AM/PM is correct on the time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Hold Until Time : Displayed AM/PM is not correct on the time picker");
						}
					}
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//UIAButton[@name='Ok']");// (fieldObjects,
					// testCase,
					// "OkButton");
				} else {
					boolean validTime;
					Keyword.ReportStep_Pass(testCase,
							"Verify Hold Until Time : Verifying hold until time is not set to : " + time);
					if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypePickerWheel[4]", testCase, 3)) {
						if ((MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[1]")
								.getAttribute("value").equalsIgnoreCase(dateAndTime[0]))
								&& (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[2]")
										.getAttribute("value").equalsIgnoreCase(hours))
								&& (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[3]")
										.getAttribute("value").equalsIgnoreCase(minutes))
								&& (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[4]")
										.getAttribute("value").equalsIgnoreCase(ampm))) {
							validTime = true;
						} else {
							validTime = false;
						}
					} else {
						if ((MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[1]")
								.getAttribute("value").equalsIgnoreCase(dateAndTime[0]))
								&& (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[2]")
										.getAttribute("value").equalsIgnoreCase(hours))
								&& (MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypePickerWheel[3]")
										.getAttribute("value").equalsIgnoreCase(minutes))) {
							validTime = true;
						} else {
							validTime = false;
						}
					}
					if (!validTime) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Hold Until Time : Failed to set hold until time to an invalid time");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Hold Until Time : Successfully set hold until time to an invalid time");
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}


}
