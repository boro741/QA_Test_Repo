package com.honeywell.jasper.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.AdhocScreen;


public class JasperVacation {
	
	public static boolean verifyVacationStatusOnPrimaryCard(TestCases testCase, TestCaseInputs inputs, boolean isOn) {
		boolean flag = true;
		String adHocText = "";
		String endDate = "";
		String endDateToBeDisplayed = "";
		AdhocScreen adhoc = new AdhocScreen(testCase);
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		if (isOn) {
			try {
				adHocText = adhoc.getAdhocStatusElement();
			} catch (NoSuchElementException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Vacation Status On Primary Card : Could not find Ad Hoc Button on Primary Card");
				return flag;
			}
			endDate = statInfo.getVacationEndTime();
			SimpleDateFormat adHocDateFormat = new SimpleDateFormat("MMM dd");
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				endDateToBeDisplayed = adHocDateFormat.format(vacationDateFormat.parse(endDate));
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occored : " + e.getMessage());
			}
			if (adHocText.equalsIgnoreCase("Vacation until " + endDateToBeDisplayed.split("\\s+")[1] + " "
					+ endDateToBeDisplayed.split("\\s+")[0])) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Vacation Status On Primary Card : Vacation is on in the primary card and displayed end date is displayed correctly");
			} else {
				if (adHocText.contains("Vacation")) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Vacation Status On Primary Card : Vacation is on in the primary card but displayed end date is not correct");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Vacation Status On Primary Card : Vacation is not on in the primary card");
				}
			}
		} else {
			if (adhoc.isAdhocStatusVisible()) {
				adHocText = adhoc.getAdhocStatusElement();
				if (adHocText.toUpperCase().contains("VACATION")) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Vacation Status on Primary Card : Vacation is on in the primary card");
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Primary Card : Vacation is off in the Primary Card");
				}

			} else {
				Keyword.ReportStep_Pass(testCase,
						"Verify Vacation Status On Primary Card : Vacation is off in the Primary Card");
			}
		}
		return flag;
	}

	public static boolean verifyVacationSwitchStatus(TestCases testCase, boolean isOn) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "VacationSettings");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "VacationSwitch", 5)) {
			if (isOn) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationSwitch").getText()
							.equalsIgnoreCase("ON")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(ON) : Vacation switch is in ON state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(ON) : Vacation switch is not in ON state");
					}
				} else {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationSwitch").getAttribute("value")
							.equalsIgnoreCase("1")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(ON) : Vacation switch is in ON state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(ON) : Vacation switch is not in ON state");
					}
				}
			} else if (!isOn) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationSwitch").getText()
							.equalsIgnoreCase("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(OFF) : Vacation switch is in OFF state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(OFF) : Vacation switch is not in OFF state");
					}
				} else {
					System.out.println(
							MobileUtils.getMobElement(fieldObjects, testCase, "VacationSwitch").getAttribute("value"));
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationSwitch").getAttribute("value")
							.equalsIgnoreCase("0")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(OFF) : Vacation switch is in OFF state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(OFF) : Vacation switch is not in OFF state");
					}
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Vacation Switch : Vacation switch not present");
		}
		return flag;
	}

	public static boolean waitForVacationStartDR(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (statInfo.isVacationRunning()) {
				Keyword.ReportStep_Pass(testCase, "Wait For Vacation Start : Vacation is already running");
				return true;
			}
			String vacationStartTime = statInfo.getVacationStartTime() + ".000Z";
			vacationStartTime = JasperSetPoint.getDeviceEquivalentUTCTime(testCase, inputs, vacationStartTime);
			String deviceTime = "";
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-M-d'T'HH:mm a");
			try {
				deviceTime = getDeviceTime(testCase, inputs);
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
				return false;
			}
			Date startTime = vacationDateFormat.parse(vacationStartTime);
			Date currentTime = vacationDateFormat.parse(deviceTime);
			long dif = startTime.getTime() - currentTime.getTime();
			int diffInMinutes = (int) ((dif / 1000) / 60);
			if (diffInMinutes > 15) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Wait for Vacation start : Difference between vacation start time and current time is greater than 15 mins");
				return false;
			} else if (diffInMinutes < 0) {
				Keyword.ReportStep_Pass(testCase,
						"Wait for Vacation Start : Vacation start time is past current device time");
				return true;
			} else {
				System.out.println("Waiting for vacation to start");
				FluentWait<String> fWait = new FluentWait<String>(" ");
				fWait.pollingEvery(10, TimeUnit.SECONDS);
				fWait.withTimeout(15, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
					public Boolean apply(String a) {
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						String vacationStartTime = "";
						String deviceTime = "";
						try {
							try {
								if (testCase.getMobileDriver() != null) {
									MobileUtils.isMobElementExists("id", "actionbar_activity_log_image", testCase);
								}
							} catch (NullPointerException e) {
							}
							vacationStartTime = statInfo.getVacationStartTime() + ".000Z";
							vacationStartTime = JasperSetPoint.getDeviceEquivalentUTCTime(testCase, inputs,
									vacationStartTime);
							deviceTime = getDeviceTime(testCase, inputs);
						} catch (Exception e) {
							return false;
						}
						try {
							Date startTime = vacationDateFormat.parse(vacationStartTime);
							Date currentTime = vacationDateFormat.parse(deviceTime);
							long dif = startTime.getTime() - currentTime.getTime();
							int diffInMinutes = (int) ((dif / 1000) / 60);
							if (diffInMinutes != 0) {
								System.out.println(diffInMinutes + " minutes remaining");
								return false;
							} else {
								return true;
							}
						} catch (Exception e) {
							return false;
						}
					}
				});
				System.out.println("Waiting for vacation to start running in CHIL");
				fWait.pollingEvery(10, TimeUnit.SECONDS);
				fWait.withTimeout(2, TimeUnit.MINUTES);
				isEventReceived = fWait.until(new Function<String, Boolean>() {
					public Boolean apply(String a) {
						try {
							DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
							if (statInfo.isVacationRunning()) {
								return true;
							} else {
								return false;
							}
						} catch (Exception e) {
							return false;
						}
					}
				});
				if (isEventReceived) {
					Keyword.ReportStep_Pass(testCase, "Wait for Vacation To Start : Vacation is running in CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Wait for Vacation To Start : Vacation is not running in CHIL after waiting for 2 minutes");
				}
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Wait for Vacation To Start : Vacation is not running in CHIL after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForVacationStart(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "AdHocOverride");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (statInfo.isVacationRunning()) {
				Keyword.ReportStep_Pass(testCase, "Wait For Vacation Start : Vacation is already running");
				return true;
			}
			String vacationStartTime = statInfo.getVacationStartTime();
			String deviceTime = "";
			final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				try {
					String time = vacationDateFormat
							.format(androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
					deviceTime = convertTimetoUTCTime(testCase, time);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} else {
				deviceTime = convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
			}
			Date startTime = vacationDateFormat.parse(vacationStartTime);
			Date currentTime = vacationDateFormat.parse(deviceTime);
			long dif = startTime.getTime() - currentTime.getTime();
			int diffInMinutes = (int) ((dif / 1000) / 60);
			if (diffInMinutes > 15) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Wait for Vacation start : Differnece between vacation start time and current time is greater than 15 mins");
				return false;
			} else if (diffInMinutes < 0) {
				Keyword.ReportStep_Pass(testCase,
						"Wait for Vacation Start : Vacation start time is past current device time");
				return true;
			} else {
				System.out.println("Waiting for vacation to start");
				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
						testCase.getMobileDriver());
				fWait.pollingEvery(10, TimeUnit.SECONDS);
				fWait.withTimeout(15, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						String vacationStartTime = statInfo.getVacationStartTime();
						String deviceTime = "";
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							try {
								MobileUtils.getMobElement(testCase, "ID", "toolbar");
								String time = vacationDateFormat.format(
										androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = convertTimetoUTCTime(testCase, time);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

						} else {
							MobileUtils.getMobElement(testCase, "name", "notification");
							deviceTime = convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
						}
						try {
							Date startTime = vacationDateFormat.parse(vacationStartTime);
							Date currentTime = vacationDateFormat.parse(deviceTime);
							long dif = startTime.getTime() - currentTime.getTime();
							int diffInMinutes = (int) ((dif / 1000) / 60);
							if (dif > 0) {
								diffInMinutes = diffInMinutes + 1;
							}
							if (diffInMinutes != 0) {
								System.out.println(diffInMinutes + " minutes remaining");
								return false;
							} else {
								return true;
							}
						} catch (Exception e) {
							return false;
						}
					}
				});
				System.out.println("Waiting for vacation to start running in CHIL");
				fWait.pollingEvery(10, TimeUnit.SECONDS);
				fWait.withTimeout(2, TimeUnit.MINUTES);
				isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						String text = "";
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AdHocStatus", 2)) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								text = MobileUtils.getMobElement(fieldObjects, testCase, "AdHocStatus")
										.getAttribute("text");
							} else {
								text = MobileUtils.getMobElement(fieldObjects, testCase, "AdHocStatus")
										.getAttribute("label");
							}
						} else if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ToolbarSubtitle", 2)) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								text = MobileUtils.getMobElement(fieldObjects, testCase, "ToolbarSubtitle")
										.getAttribute("text");
							} else {
								text = MobileUtils.getMobElement(fieldObjects, testCase, "ToolbarSubtitle")
										.getAttribute("value");
							}
						}
						try {
							if (statInfo.isVacationRunning() && text.toUpperCase().contains("VACATION")) {
								return true;
							} else {
								return false;
							}
						} catch (NoSuchElementException e) {
							return false;
						} catch (Exception e) {
							System.out.println(e.getMessage());
							return false;
						}
					}
				});
				if (isEventReceived) {
					Keyword.ReportStep_Pass(testCase, "Wait for Vacation Active : Vacation is running in CHIL and UI");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Wait for Vacation Active : Vacation is not running in CHIL and UI after waiting for 2 minutes");
				}
			}
		} catch (Exception e) {

		}
		return flag;
	}

	
	/**
	 * <p>
	 * The convertTimeToUTCTime method converts time to UTC time.
	 * </p>
	 * 
	 * @param TestCases
	 * @param String
	 *            - This is the time value in the format 'yyyy-MM-dd'T'HH:mm:ss'
	 *            that has to be converted to UTC time.
	 * @return String - This is the time value converted to UTC time.
	 * 
	 * @author h119237 - Pratik Lalseta.
	 */
	public static String convertTimetoUTCTime(TestCases testCase, String time) {
		String convertedTime = " ";
		try {
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date date = vacationDateFormat.parse(time);
			vacationDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			convertedTime = vacationDateFormat.format(date);
		} catch (Exception e) {
			convertedTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Convert Time to UTC Time : Error Occured : " + e.getMessage());
		}
		return convertedTime;
	}


	public static String getDeviceTime(TestCases testCase, TestCaseInputs inputs) {
		String time = " ";
		try {
			Calendar date = Calendar.getInstance(JasperSetPoint.getDeviceTimeZone(testCase, inputs));
			String ampm;
			if (date.get(Calendar.AM_PM) == Calendar.AM) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			String hour;
			if (date.get(Calendar.HOUR) == 0) {
				hour = "12";
			} else {
				hour = String.valueOf(date.get(Calendar.HOUR));
			}
			if (Integer.parseInt(hour) < 10) {
				hour = "0" + hour;
			}
			String minute;
			if (date.get(Calendar.MINUTE) < 10) {
				minute = "0" + date.get(Calendar.MINUTE);
			} else {
				minute = String.valueOf(date.get(Calendar.MINUTE));
			}
			int month = date.get(Calendar.MONTH) + 1;
			time = String.valueOf(date.get(Calendar.YEAR) + "-" + month + "-" + date.get(Calendar.DAY_OF_MONTH) + "T"
					+ hour + ":" + minute + " " + ampm);
		} catch (Exception e) {
			time = "";
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Android Device Time : Error Occured : " + e.getMessage());
		}

		return time;
	}

}
