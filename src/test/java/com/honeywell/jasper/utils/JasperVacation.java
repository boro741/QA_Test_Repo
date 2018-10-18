package com.honeywell.jasper.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
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
import com.honeywell.screens.SchedulingScreen;

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
				adHocText = adhoc.getAdhocStatusElement().toUpperCase();
			} catch (NoSuchElementException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Vacation Status On Primary Card : Could not find Ad Hoc Button on Primary Card");
				return flag;
			}
			endDate = statInfo.getVacationEndTime();
			Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			if (inputs.isRunningOn("Saucelabs")) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date date = sdf.parse(endDate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					System.out.println(cal.getTime());
					System.out.println("Date In UTC is " + endDate);
					SimpleDateFormat pstDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					pstDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					System.out.println("*******" + TimeZone.getTimeZone("CET"));
					String endDateInPSTFormat = pstDateFormat.format(vacationDateFormat.parse(endDate));
					System.out.println("Date In CET is " + endDateInPSTFormat);

					Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
					SimpleDateFormat adHocDateFormat = new SimpleDateFormat("MMM dd");
					try {
						endDateToBeDisplayed = adHocDateFormat.format(vacationDateFormat.parse(endDateInPSTFormat))
								.toUpperCase();
						Keyword.ReportStep_Pass(testCase,
								"Vacation End date from CHIL device information to be displayed is: "
										+ endDateToBeDisplayed);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occored : " + e.getMessage());
					}
				} catch (Exception e) {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occored : " + e.getMessage());
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
				SimpleDateFormat adHocDateFormat = new SimpleDateFormat("MMM dd");
				try {
					endDateToBeDisplayed = adHocDateFormat.format(vacationDateFormat.parse(endDate)).toUpperCase();
					Keyword.ReportStep_Pass(testCase,
							"Vacation End date from CHIL device information to be displayed is: "
									+ endDateToBeDisplayed);
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occored : " + e.getMessage());
				}
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				Keyword.ReportStep_Pass(testCase,
						"Expected Vacation until label to be displayed is: " + "Vacation until "
								+ endDateToBeDisplayed.split("\\s+")[1] + " " + endDateToBeDisplayed.split("\\s+")[0]);
				Keyword.ReportStep_Pass(testCase, "Actual Vacation until label displayed is: " + adHocText);
				if (adHocText.equalsIgnoreCase("Vacation until " + endDateToBeDisplayed.split("\\s+")[1] + " "
						+ endDateToBeDisplayed.split("\\s+")[0])) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Primary Card : Vacation is on in the primary card and displayed end date is correct: "
									+ adHocText);
				} else {
					if (adHocText.contains("Vacation")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Primary Card : Vacation is on in the primary card but displayed end date is incorrect: "
										+ adHocText);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Primary Card : Vacation is not on in the primary card");
					}
				}
			} else {
				Keyword.ReportStep_Pass(testCase,
						"Expected Vacation until label to be displayed is: " + "VACATION UNTIL "
								+ endDateToBeDisplayed.split("\\s+")[0] + " " + endDateToBeDisplayed.split("\\s+")[1]);
				Keyword.ReportStep_Pass(testCase, "Actual Vacation until label displayed is: " + adHocText);
				if (adHocText.contains("VACATION UNTIL " + endDateToBeDisplayed.split("\\s+")[0] + " "
						+ endDateToBeDisplayed.split("\\s+")[1])) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Primary Card : Vacation is on in the primary card and displayed end date is correct: "
									+ adHocText);
				} else {
					if (adHocText.contains("Vacation")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Primary Card : Vacation is on in the primary card but displayed end date is incorrect: "
										+ adHocText);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Primary Card : Vacation is not on in the primary card");
					}
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

	public static boolean verifyVacationStatusOnSolutionsCard(TestCases testCase, TestCaseInputs inputs, boolean isOn) {
		boolean flag = true;
		String adHocText = "";
		String endDate = "";
		String endDateToBeDisplayed = "";
		AdhocScreen adhoc = new AdhocScreen(testCase);
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		if (isOn) {
			try {
				SchedulingScreen ss= new SchedulingScreen(testCase);
				ss.isNoScheduleTextVisible(60);
				adHocText = adhoc.getVacationStatusInSolutionsCardScreen();
			} catch (NoSuchElementException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Vacation Status On Solutions Card : Could not find Vacation status on Solutions Card");
				return flag;
			}
			endDate = statInfo.getVacationEndTime();
			Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			if (inputs.isRunningOn("Saucelabs")) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Date date = sdf.parse(endDate);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					System.out.println(cal.getTime());
					System.out.println("Date In UTC is " + endDate);
					SimpleDateFormat pstDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					pstDateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
					System.out.println("*******" + TimeZone.getTimeZone("CET"));
					String endDateInPSTFormat = pstDateFormat.format(vacationDateFormat.parse(endDate));
					System.out.println("Date In CET is " + endDateInPSTFormat);

					Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
					SimpleDateFormat adHocDateFormat = new SimpleDateFormat("MMM dd");
					try {
						endDateToBeDisplayed = adHocDateFormat.format(vacationDateFormat.parse(endDateInPSTFormat))
								.toUpperCase();
						Keyword.ReportStep_Pass(testCase,
								"Vacation End date from CHIL device information to be displayed is: "
										+ endDateToBeDisplayed);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occored : " + e.getMessage());
					}
				} catch (Exception e) {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occored : " + e.getMessage());
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Vacation End time from CHIL device information is: " + endDate);
				SimpleDateFormat adHocDateFormat = new SimpleDateFormat("MMM dd");
				try {
					
					endDateToBeDisplayed = adHocDateFormat.format(vacationDateFormat.parse(endDate));
					
					Keyword.ReportStep_Pass(testCase,
							"Vacation End date from CHIL device information to be displayed is: "
									+ endDateToBeDisplayed);
					
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occored : " + e.getMessage());
				}
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				Keyword.ReportStep_Pass(testCase,
						"Expected Vacation until label to be displayed is: " + "Vacation until "
								+ endDateToBeDisplayed.split("\\s+")[1] + " " + endDateToBeDisplayed.split("\\s+")[0]);
				Keyword.ReportStep_Pass(testCase, "Actual Vacation until label displayed is: " + adHocText);
				if (adHocText.equalsIgnoreCase("Vacation until " + endDateToBeDisplayed.split("\\s+")[1] + " "
						+ endDateToBeDisplayed.split("\\s+")[0])) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Solutions Card : Vacation is on in the Solutions card and displayed end date is correct: "
									+ adHocText);
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Expected Vacation until label to be displayed is: " + "Vacation until "
									+ endDateToBeDisplayed.split("\\s+")[1] + " "
									+ endDateToBeDisplayed.split("\\s+")[0]);
					Keyword.ReportStep_Pass(testCase, "Actual Vacation until label displayed is: " + adHocText);
					if (adHocText.contains("Vacation")) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Solutions Card : Vacation is on in the Solutions card but displayed end date is incorrect: "
										+ adHocText);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Status On Solutions Card : Vacation is not on in the Solutions card");
					}
				}
			} else {
				if (adHocText.equalsIgnoreCase("Vacation Until " + endDateToBeDisplayed.split("\\s+")[0] + " "
						+ endDateToBeDisplayed.split("\\s+")[1])) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Solutions Card : Vacation is on in the Solutions card and displayed end date is correct: "
									+ adHocText);
				} else {
					try {
						SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
	                    SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
	                    SimpleDateFormat vacationDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	                   
	                    String endTime = date24Format.format(vacationDateFormat1.parse(endDate));
	                    String adhoctexttime = adhoc.getVacationStatusInSolutionsCardScreen().replace("Vacation Until ", "");
	                    adhoctexttime = date24Format.format(date12Format.parse(adhoctexttime));
	                    adhoctexttime = convertTimetoUTCTime(testCase, "2018-09-12T"+ adhoctexttime+":00");
	                    if(adhoctexttime.contains(endTime))
						{
	                    	Keyword.ReportStep_Pass(testCase,
	    							"Verify Vacation Status On Solutions Card : Vacation is on in the Solutions card and displayed end date is correct: "
	    									+ adhoctexttime);
						}
	                    else if (adHocText.contains("Vacation")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Vacation Status On Solutions Card : Vacation is on in the Solutions card but displayed end date is incorrect: "
											+ adHocText);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Vacation Status On Solutions Card : Vacation is not on in the Solutions card");
						}
					}catch(Exception e) {
						System.out.println("");
					}
				}
			}
		} else {
			if (adhoc.isAdhocStatusVisible()) {
				adHocText = adhoc.getAdhocStatusElement();
				if (adHocText.toUpperCase().contains("VACATION")) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Vacation Status on Solutions Card : Vacation is on in the Solutions card");
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Vacation Status On Solutions Card : Vacation is off in the Solutions Card");
				}

			} else {
				Keyword.ReportStep_Pass(testCase,
						"Verify Vacation Status On Solutions Card : Vacation is off in the Solutions Card");
			}
		}
		return flag;
	}

	public static boolean verifyVacationSwitchStatus(TestCases testCase, boolean isOn) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "VacationSettings");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "VacationHoldSwitch", 5)) {
			if (isOn) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationHoldSwitch").getText()
							.equalsIgnoreCase("ON")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(ON) : Vacation switch is in ON state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(ON) : Vacation switch is not in ON state");
					}
				} else {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationHoldSwitch").getAttribute("value")
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
					if (MobileUtils.getMobElement(fieldObjects, testCase, "VacationHoldSwitch").getText()
							.equalsIgnoreCase("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Vacation Switch Status(OFF) : Vacation switch is in OFF state");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Vacation Switch Status(OFF) : Vacation switch is not in OFF state");
					}
				} else {
					System.out.println(MobileUtils.getMobElement(fieldObjects, testCase, "VacationHoldSwitch")
							.getAttribute("value"));
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
						"Wait for Vacation start : Difference between vacation start time and current time is greater than 15 mins");
				return false;
			} else if (diffInMinutes < 0) {
				Keyword.ReportStep_Pass(testCase,
						"Wait for Vacation Start : Vacation start time is past current device time");
				return true;
			} else {
				System.out.println("Waiting for vacation to start");
				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
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
								String time = vacationDateFormat.format(androidDateFormat
										.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = convertTimetoUTCTime(testCase, time);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}

						} else {
							MobileUtils.getMobElement(testCase, "name", "notification");
							deviceTime = convertTimetoUTCTime(testCase,
									JasperAdhocOverride.getIOSSimulatorTime(testCase));
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
	public boolean setMaxTemperatureByTappingUpStepperVacation(TestCases testCase, TestCaseInputs inputs){
		boolean flag = true;
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "VacationHold");
			String maxSetPointh = "";
			String maxSetPointc = "";
			float maxSetPointFloat = 0;
			int maxSetPointInt = 0;
			boolean systemIsCelsius = false;
			String currentSetPoint = "";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints = statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
				maxSetPointh = setPoints.get("MaxHeat");
			if (maxSetPointh.contains(".")) {
				systemIsCelsius = true;
				maxSetPointFloat = Float.parseFloat(maxSetPointh);
			} else {
				maxSetPointInt = (int) Float.parseFloat(maxSetPointh);
			}

			WebElement ele = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointRound");
			currentSetPoint = ele.getText();
			if (systemIsCelsius == false) {
				while (Integer.parseInt(currentSetPoint) < maxSetPointInt) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "HeatSetPointStepperUpButton");
					currentSetPoint = ele.getText();
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatSetPointRound") && MobileUtils
							.getFieldValue(fieldObjects, testCase, "HeatSetPointRound").equalsIgnoreCase(currentSetPoint)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				while (Float.parseFloat(currentSetPoint) < maxSetPointFloat) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "HeatSetPointStepperUpButton");
					currentSetPoint = ele.getText();
				}
			}
			maxSetPointc = setPoints.get("MaxCool");
			if (maxSetPointc.contains(".")) {
				systemIsCelsius = true;
				maxSetPointFloat = Float.parseFloat(maxSetPointc);
			} else {
				maxSetPointInt = (int) Float.parseFloat(maxSetPointc);
			}
			WebElement ele1 = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointRound");
			currentSetPoint = ele1.getText();
			if (systemIsCelsius == false) {
				while (Integer.parseInt(currentSetPoint) < maxSetPointInt) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CoolSetPointStepperUpButton");
					currentSetPoint = ele1.getText();
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolSetPointRound") && MobileUtils
							.getFieldValue(fieldObjects, testCase, "CoolSetPointRound").equalsIgnoreCase(currentSetPoint)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				while (Float.parseFloat(currentSetPoint) < maxSetPointFloat) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CoolSetPointStepperUpButton");
					currentSetPoint = ele1.getText();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return flag;
		}
		
		public boolean setMinTemperatureByTappingDownStepperVacation(TestCases testCase, TestCaseInputs inputs) {
			boolean flag = true;
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "VacationHold");
			String minSetPointh = "";
			String minSetPointc = "";
			float minSetPointFloat = 0;
			int minSetPointInt = 0;
			boolean systemIsCelsius = false;
			String currentSetPoint = "";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints = statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
				minSetPointh = setPoints.get("MinHeat");
			if (minSetPointh.contains(".")) {
				systemIsCelsius = true;
				minSetPointFloat = Float.parseFloat(minSetPointh);
			} else {
				minSetPointInt = (int) Float.parseFloat(minSetPointh);
			}

			WebElement ele = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointRound");
			currentSetPoint = ele.getText();
			if (systemIsCelsius == false) {
				while (minSetPointInt<Integer.parseInt(currentSetPoint)) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "HeatSetPointStepperDownButton");
					currentSetPoint = ele.getText();
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatSetPointRound") && MobileUtils
							.getFieldValue(fieldObjects, testCase, "HeatSetPointRound").equalsIgnoreCase(currentSetPoint)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				while ( minSetPointFloat < Float.parseFloat(currentSetPoint) ) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "HeatSetPointStepperDownButton");
					currentSetPoint = ele.getText();
				}
			}
			minSetPointc = setPoints.get("MinCool");
			if (minSetPointc.contains(".")) {
				systemIsCelsius = true;
				minSetPointFloat = Float.parseFloat(minSetPointc);
			} else {
				minSetPointInt = (int) Float.parseFloat(minSetPointc);
			}
			WebElement ele1 = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointRound");
			currentSetPoint = ele1.getText();
			if (systemIsCelsius == false) {
				while (minSetPointInt < Integer.parseInt(currentSetPoint)) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CoolSetPointStepperDownButton");
					currentSetPoint = ele1.getText();
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolSetPointRound") && MobileUtils
							.getFieldValue(fieldObjects, testCase, "CoolSetPointRound").equalsIgnoreCase(currentSetPoint)) {
						return flag;
					} else {
						flag = false;
					}
				}
			} else {
				while (minSetPointFloat < Float.parseFloat(currentSetPoint)) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CoolSetPointStepperDownButton");
					currentSetPoint = ele1.getText();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return flag;
		}
}
