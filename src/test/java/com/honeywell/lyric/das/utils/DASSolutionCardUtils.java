package com.honeywell.lyric.das.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.perfecto.PerfectoConstants.PerfectoConstant;
import com.honeywell.commons.report.FailType;

public class DASSolutionCardUtils {

	public static boolean waitForEntryTimerToComplete(TestCases testCase) {
		boolean flag = true;

		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				private int waitingPresentTimerValue;
				private int presentTimerValue = 0;

				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmWillSoundInSeconds", 2)
								&& MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmWillSoundInText", 2)) {
							if (presentTimerValue == 0) {
								presentTimerValue = Integer.parseInt(
										MobileUtils.getFieldValue(fieldObjects, testCase, "AlarmWillSoundInSeconds"));
							} else {
								presentTimerValue = waitingPresentTimerValue;
							}
							try {
								if (presentTimerValue > 15 && MobileUtils.isMobElementExists(fieldObjects, testCase,
										"AlarmWillSoundInSeconds", 2)) {
									Thread.sleep(2000);
									waitingPresentTimerValue = Integer.parseInt(MobileUtils.getFieldValue(fieldObjects,
											testCase, "AlarmWillSoundInSeconds"));
									if (waitingPresentTimerValue < presentTimerValue) {
										Keyword.ReportStep_Pass(testCase, "Entry timer has decreased from "
												+ presentTimerValue + " to " + waitingPresentTimerValue);
									} else {
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Entry timer has not decreased from " + presentTimerValue + " to "
														+ waitingPresentTimerValue);
									}
								}
							} catch (Exception e) {
								System.out.println("not able to parse " + e);
							}
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "entry timer ended successfully");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Timer did not end even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForDismissProcessRequest(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DismissRequestProcessing", 2)) {
							System.out.println("Waiting for Processing Request to end");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, " Processing Request ended successfully");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					" Processing Request did not end even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForClosingDoorTimerToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WaitingToCloseDoor", 2)) {
							Keyword.ReportStep_Pass(testCase, "Waiting for closing door to end");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Waiting for closing door ended successfully");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Waiting for closing door did not end even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForSwitchingToNightProgressToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SwitchToNightProgress", 2)) {
								System.out.println("Waiting for Switch To Night request progress to end");
								return false;
							} else {
								return true;
							}
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Waiting for closing door ended successfully");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Waiting for closing door did not end even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean isTextAvailableVisually(TestCases testCase, String textToFind,
			String startCoordinateInPercentage, String heightInPercentage) {
		boolean flag = true;
		try {
			String functionName = "Is Text Available Visually : ";
			if (testCase.getTestCaseInputs().isRunningOn(PerfectoConstant.PERFECTO.getPerfectoConstant())) {
				CustomDriver driver = testCase.getMobileDriver();
				if (driver == null) {
					flag = false;
				} else {
					HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase,
							"DAS_VideoSolution");

					MobileElement video = MobileUtils.getMobElement(fieldObjects, testCase, "LiveFeedVideoContainer",
							false);
					if (video == null) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Is Text Available Visually : Video is not available.");
						flag = false;
					} else {
						Map<String, String> params = new HashMap<>();
						params.put("content", textToFind);
						params.put("timeout", "20");
						params.put("screen.top", startCoordinateInPercentage);
						params.put("screen.height", heightInPercentage);
						params.put("screen.width", "100%");

						try {
							flag = Boolean.parseBoolean((String) testCase.getMobileDriver()
									.executeScript("mobile:checkpoint:text", params));
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									functionName + "Error Occured - " + e.getMessage());
							flag = false;
						}

						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									functionName + textToFind + " Text is displayed on screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									functionName + textToFind + " Text isn't displayed on screen");
						}
					}
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public static void waitForConfigSettingsToUpdate(TestCases testCase, int time) {
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Camera");
		MobileUtils.isMobElementExists(fieldObjects, testCase, "Password", time);
		Keyword.ReportStep_Pass(testCase, "Waited for " + time + " secs to update");
	}

	public static boolean setValueToSlider(TestCases testCase, TestCaseInputs inputs, int currentValueInteger,
			WebElement element) {
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_Settings");
			String currentValue;
			int max = Integer.parseInt("100");
			int min = Integer.parseInt("0");
			int gradient = Integer.parseInt("1");
			int expectedValue = Integer.parseInt("20");

			if (currentValueInteger < 50) {
				expectedValue = 90;
			} else {
				expectedValue = 30;
			}
			if (element != null) {

				Dimension dimension = element.getSize();

				Point point = element.getLocation();

				int width = dimension.getWidth();
				int height = dimension.getHeight() / 2;
				int numberOfBlocks = (max - min) / gradient;
				int eachBlockLength = Math.round(width / numberOfBlocks);

				int currentCircleXPoint = point.getX() + ((currentValueInteger - min) / gradient) * eachBlockLength;
				int currentCircleYPoint = point.getY() + height;
				TouchAction tAction = new TouchAction(testCase.getMobileDriver());
				// x = 16, x = 159 , x = 312
				int difference = 0;

				double direction = 5; // left to right

				if (currentValueInteger < expectedValue) {
					difference = (expectedValue - currentValueInteger) / gradient;
					direction = 2;
				} else {
					difference = (currentValueInteger - expectedValue) / gradient;
					direction = -5;
				}

				for (int counter = 0; counter < difference; counter++) {
					try {
						tAction.press(currentCircleXPoint, currentCircleYPoint)
								.moveTo((int) (Math.round(eachBlockLength * direction)), 0).release().perform();
						Keyword.ReportStep_Pass(testCase, "Slided the volume");
					} catch (Exception e) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change Slider Value : Not able to move slider. Error Occured - " + e.getMessage());
						// break;
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ChimeVolumeValueAfterChange")) {
						currentValue = MobileUtils.getFieldValue(fieldObjects, testCase, "ChimeVolumeValueAfterChange")
								.replace("%", "").trim();
						if (Integer.parseInt(currentValue.trim()) != currentValueInteger) {
							Keyword.ReportStep_Pass(testCase,
									"at start currentValue is" + currentValueInteger + " after drag " + currentValue);
							break;
						} else {
							Keyword.ReportStep_Pass(testCase,
									"at start currentValue is" + currentValueInteger + " after drag " + currentValue);
							currentCircleXPoint = (int) Math.round(currentCircleXPoint + 1 * direction);
						}
					} else if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ChimeVolumeValue")) {
						currentValue = MobileUtils.getFieldValue(fieldObjects, testCase, "ChimeVolumeValue")
								.replace("%", "").trim();
						if (Integer.parseInt(currentValue.trim()) != currentValueInteger) {
							Keyword.ReportStep_Pass(testCase,
									"at start currentValue is" + currentValueInteger + " after drag " + currentValue);
							break;
						} else {
							Keyword.ReportStep_Pass(testCase,
									"at start currentValue is" + currentValueInteger + " after drag " + currentValue);
							currentCircleXPoint = (int) Math.round(currentCircleXPoint + 1 * direction);
						}

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change Slider Value : Not able to locate slider.");
					}
				}

			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Exception occured " + e.getMessage());

		}
		return true;

	}

	/*
	 * public static Boolean waitForElementToClickable(TestCases testCase,
	 * HashMap<String, MobileObject> fieldObjects,String elementToLookFor){
	 * FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
	 * testCase.getMobileDriver()); fWait.pollingEvery(10, TimeUnit.SECONDS);
	 * fWait.withTimeout(120, TimeUnit.SECONDS); Boolean isEventReceived =
	 * fWait.until(new Function<CustomDriver, Boolean>() { public Boolean
	 * apply(CustomDriver driver) { if( MobileUtils.isMobElementExists(fieldObjects,
	 * testCase, elementToLookFor)){ if(MobileUtils.getMobElement(fieldObjects,
	 * testCase, elementToLookFor)!=null){ WebElement we=
	 * fWait.until(ExpectedConditions.elementToBeClickable(MobileUtils.getMobElement
	 * (fieldObjects, testCase, elementToLookFor))); if(we!=null){ return true;
	 * }else{ return false; } } return false; } return false; } });
	 * 
	 * if (isEventReceived) {
	 * Keyword.ReportStep_Pass(testCase,elementToLookFor+" is clickable in the app"
	 * ); } else { Keyword.ReportStep_Fail(testCase,
	 * FailType.FUNCTIONAL_FAILURE,elementToLookFor+" is not clickable in the app");
	 * } return isEventReceived; }
	 */

	public static boolean navigateToClipCard(TestCases testCase) {
		Boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_VideoSolution");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ClipCardOpener")) {
			// Dimension startPoint= MobileUtils.getMobElement(fieldObjects, testCase,
			// "ClipCardOpener").getSize();
			Point startPoint = MobileUtils.getMobElement(fieldObjects, testCase, "ClipCardOpener").getCenter();
			int startx = startPoint.getX() / 2;
			int starty = (int) (startPoint.getY());
			Point endPoint = MobileUtils.getMobElement(fieldObjects, testCase, "LocationName").getCenter();
			// int endx = (int) (endPoint.getX()/2);
			int endy = (int) (endPoint.getY());
			try {
				// Swipe from down to up.
				// TouchAction tAction = new TouchAction(testCase.getMobileDriver());
				// 611, 2147
				// 340,150
				System.out.println(startx);
				System.out.println(startx);
				System.out.println(starty);
				System.out.println(endy);
				testCase.getMobileDriver().swipe(startx, starty, startx, endy, 1000);

			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to swipe");
			}
		}

		return flag;

	}

	public static boolean waitForExitTimerToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSecurityCard");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				/*
				 * private int waitingPresentTimerValue; private int presentTimerValue = 0;
				 * private MobileElement Timer;
				 */
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimerProgressBar", 2)
								&& MobileUtils.isMobElementExists(fieldObjects, testCase, "SwitchingToText", 2)) {
							/*
							 * Timer=MobileUtils.getMobElement(fieldObjects, testCase, "TimerProgressBar");
							 * if(presentTimerValue==0){
							 * if(testCase.getPlatform().toUpperCase().contains("IOS")){ presentTimerValue =
							 * Integer.parseInt(Timer.getAttribute("value")); }else{ presentTimerValue =
							 * Integer.parseInt(Timer.getAttribute("content-Desc")); } }else{
							 * presentTimerValue=waitingPresentTimerValue; } try { if(presentTimerValue>15
							 * && MobileUtils.isMobElementExists(fieldObjects, testCase, "TimerProgressBar",
							 * 2) && MobileUtils.isMobElementExists(fieldObjects, testCase,
							 * "SwitchingToText", 2)){
							 * if(testCase.getPlatform().toUpperCase().contains("IOS")){
							 * waitingPresentTimerValue = Integer.parseInt(Timer.getAttribute("value"));
							 * }else{ waitingPresentTimerValue =
							 * Integer.parseInt(Timer.getAttribute("text")); }
							 * if(waitingPresentTimerValue<presentTimerValue){
							 * Keyword.ReportStep_Pass(testCase,
							 * "Exit timer has decreased from "+presentTimerValue+" to "
							 * +waitingPresentTimerValue); }else{
							 * Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
							 * "Exit timer has not decreased from "+presentTimerValue+" to "
							 * +waitingPresentTimerValue); } Thread.sleep(2000); } } catch (Exception e) {
							 * System.out.println("not able to parse "+e); }
							 */
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Exit timer ended successfully");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Timer did not end even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static Boolean waitForElementToDisappear(TestCases testCase, HashMap<String, MobileObject> fieldObjects,
			String elementToLookFor, int timeLimit, int pollTime) {
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(pollTime, TimeUnit.SECONDS);
		fWait.withTimeout(timeLimit, TimeUnit.SECONDS);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			@Override
			public Boolean apply(CustomDriver driver) {
				Keyword.ReportStep_Pass(testCase, "Waiting for the " + elementToLookFor + " to disappear");
				if (!MobileUtils.isMobElementExists(fieldObjects, testCase, elementToLookFor, 5)) {
					return true;
				} else
					return false;
			}
		});
		return isEventReceived;
	}
}
