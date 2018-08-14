package com.honeywell.lyric.DR.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.honeywell.commons.mobile.MobileScreens;
import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.DR.VerifyDRMessage;
import com.honeywell.keywords.lyric.chil.TriggerDREvent;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.DRScreens;
import com.honeywell.screens.PrimaryCard;

public class DRUtils extends MobileScreens {

	private static final String screenName = "DR";

	public DRUtils(TestCases testCase) {
		super(testCase, screenName);
	}
	/**
     * <h1>Wait for until progress bar to complete</h1>
     * <p>
     * The waitForProgressBarToComplete method waits until the progress bar closes.
     * </p>
     *
     * @param testCase
     *            Instance of the TestCases class used to create the testCase.
     *            testCase instance.
     * @return boolean Returns 'true' if the progress bar disappears. Returns
     *         'false' if the progress bar is still displayed.
     */
     public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
            boolean flag = true;
            try {
                   FluentWait<String> fWait = new FluentWait<String>(" ");
                   fWait.pollingEvery(3, TimeUnit.SECONDS);
                   fWait.withTimeout(duration, TimeUnit.MINUTES);
                   DRScreens vhs = new DRScreens(testCase);
                   PrimaryCard pc = new PrimaryCard(testCase);
                   ActivityHistoryScreen ahs = new ActivityHistoryScreen(testCase);
                   Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
                         public Boolean apply(String a) {
                                try {
                                       switch (elementProgressBar) {
                                       case "DR Label": {
                                              if (pc.isDrGreenLabelVisible()) {
                                                     System.out.println("Waiting for DR label to disappear");
                                                     return false;
                                              } else {
                                                     return true;
                                              }
                                       }
                                       case "DR Label visible": {
                                           if (pc.isDrGreenLabelVisible()) {
                                                  System.out.println("Waiting for DR label to appear");
                                                  return true;
                                           } else {
                                                  return false;
                                           }
                                    }
                                       case "DR Popup visible": {
                                           if (vhs.isSavingEventTitleDisplayed()) {
                                                  System.out.println("Waiting for DR Popup to appear");
                                                  return true;
                                           } else {
                                                  return false;
                                           }
                                    }
                                       case "Messages": {
                                           if (ahs.isMessagesDisplayed()) {
                                                  System.out.println("Waiting for Messages to appear");
                                                  return true;
                                           } else {
                                                  return false;
                                           }
                                    }
                                       default: {
                                              Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                                            "Invalid argument passed : " + elementProgressBar);
                                              return true;
                                       }
                                       }
                                } catch (Exception e) {
                                       return false;
                                }
                         }
                   });
                   if (isEventReceived) {
                         Keyword.ReportStep_Pass(testCase, "Wait success");
                   }
            } catch (TimeoutException e) {
                   flag = false;
                   Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                               + duration + " minutes");
            } catch (Exception e) {
                   flag = false;
                   Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
            }

            return flag;
     }

	public static Boolean VerifyDRPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;

		String UTCStartTime = inputs.getInputValue(TriggerDREvent.DREVENTSTARTTIME);
		String deviceDRStartTime = LyricUtils.getDeviceEquivalentUTCTime(testCase, inputs, UTCStartTime);
		String currentDeviceTime = LyricUtils.getDeviceTime(testCase, inputs);
		String deviceDay;

		if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
			deviceDay = "today";
		} else {
			deviceDay = deviceDRStartTime.split("T")[0];
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			DRScreens dr = new DRScreens(testCase);
			if (dr.isSavingEventTitleDisplayed()) {
				Keyword.ReportStep_Pass(testCase,
						"Verifying Savings Event Pop Up Message : Savings Event alert title correctly displayed");
				String message = "A Savings Event is scheduled for " + deviceDay + " at "
						+ deviceDRStartTime.split("T")[1]
						+ ". During this time, your thermostats will be optimized by your utility provider.";
				String message2 = "A Savings Event is scheduled for " + deviceDay + " at "
						+ deviceDRStartTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.")
						+ ". During this time, your thermostats will be optimized by your utility provider.";

				if (dr.VerifyDRMessageContent().equalsIgnoreCase(message)
						|| dr.VerifyDRMessageContent().equalsIgnoreCase(message2)) {
					Keyword.ReportStep_Pass(testCase,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is not correctly displayed. Actual : "
									+ dr.VerifyDRMessageContent() + ". Expected : '" + message + "' or '" + message2
									+ "'");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verifying Savings Event Pop Up Message : Savings Event alert title is not correctly displayed");
			}
			flag = flag & dr.ClickOnOkPopup();
		}

		// IOS
		else {
			if (MobileUtils.isMobElementExists("name", "Savings Event Scheduled", testCase, 10, false)) {
				Keyword.ReportStep_Pass(testCase,
						"Verifying Savings Event Pop Up Message : Savings Event Alert title correctly displayed");
				SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
				String time = null;
				try {
					time = format.format(format.parse(deviceDRStartTime.split("T")[1]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String message = "A Savings Event is scheduled for " + deviceDay + " at " + time
						+ ". During this time, your thermostats will be optimized by your utility provider.";
				if (MobileUtils.isMobElementExists("name", message, testCase, 5, false)) {
					Keyword.ReportStep_Pass(testCase,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is not correctly displayed. Expected : "
									+ message);
				}
				flag = flag & MobileUtils.clickOnElement(testCase, "name", "OK", false);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verifying Savings Event Pop Up Message : Savings Event alert title is not displayed");
			}
		}

		return flag;

	}

	public static Boolean VerifyDRCancelPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag=true;	
		try{
					DRScreens dr = new DRScreens(testCase);
					if(dr.isSavingEventCancelTitleDisplayed()){
							Keyword.ReportStep_Pass(testCase,
									"Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel alert title correctly displayed");
					           String message = "";
	                            DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
	                            if (statInfo.isDutyCycleEnabled()) {
	                                message = "You might lose out on energy and money saving by cancelling this event.";
	                            } else {
	                                if (statInfo.getThermoStatMode().equalsIgnoreCase("Cool")) {
	                                    String setPoints = statInfo.getDRCoolSetPointLimit();
	                                    Double sP = Double.parseDouble(setPoints);
	                                    if (statInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
	                                        setPoints = String.valueOf(sP.intValue());
	                                    } else {
	                                        setPoints = String.valueOf(sP);
	                                    }
	                                    message = "You can not decrease temperature below " + setPoints
	                                    + "\u00B0F. You might lose out on energy and money saving by cancelling this event.";
	                                } else if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
	                                    String setPoints = statInfo.getDRHeatSetPointLimit();
	                                    Double sP = Double.parseDouble(setPoints);
	                                    if (statInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
	                                        setPoints = String.valueOf(sP.intValue());
	                                    } else {
	                                        setPoints = String.valueOf(sP);
	                                    }
	                                    message = "You can not increase temperature beyond " + setPoints
	                                    + "\u00B0F. You might lose out on energy and money saving by cancelling this event.";
	                                }
	                            }
	                                if (dr.VerifyDRMessageContent().equalsIgnoreCase(message)) 
	                                {
	                                    Keyword.ReportStep_Pass(testCase,
	                                                            "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel alert message is correctly displayed");
	                                } else {
	                                    flag = false;
Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
		"Verifying Savings Cancel Event Pop Up Message : Savings Event Cancel Alert message is not correctly displayed. Actual : " + dr.VerifyDRMessageContent()
	                                                            + " Expected : " + message);
	                                }
	                            }
	                         else {
	                            flag = false;
	                            Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
	                                                    "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel Alert title is not correctly displayed");
	                        }
	                    } catch (Exception e) {
	                        Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
	                    }
	                    return flag;
	                }
	

public static Boolean VerifyDRCancelByUserPopUp(TestCases testCase, TestCaseInputs inputs) {
	boolean flag=true;	
	try{
				DRScreens dr = new DRScreens(testCase);
				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
                String DREndTime = statInfo.getDREndTime() + ".000Z";
                String deviceDREndTime = LyricUtils.getDeviceEquivalentUTCTime(testCase, inputs, DREndTime);
				if(dr.isSavingEventCancelByUserTitleDisplayed()){
						Keyword.ReportStep_Pass(testCase,
								"Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel alert title correctly displayed");
						 String expectedDRText = "Savings Event Until " + deviceDREndTime.split("T")[1];
	                        String expectedDRText2 = "Savings Event Until "
	                        + deviceDREndTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.");
	                        String displayedText = dr.VerifyDRMessageContent();
	                        if (displayedText.equals(expectedDRText) || displayedText.equals(expectedDRText2)) {
	                            Keyword.ReportStep_Pass(testCase,
	                                                    "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel Alert title correctly displayed");
	                            String message = "";
	                            if (statInfo.isDutyCycleEnabled()) {
	                                message = "You can change the temperature setting, but please note during this time your thermostat will be optimized by your utility provider.";
	                            }
	                            else {
	                                if (statInfo.getThermoStatMode().equalsIgnoreCase("Cool")) {
	                                    String setPoints = statInfo.getDRCoolSetPointLimit();
	                                    Double sP = Double.parseDouble(setPoints);
	                                    if (statInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
	                                        setPoints = String.valueOf(sP.intValue());
	                                    } else {
	                                        setPoints = String.valueOf(sP);
	                                    }
	                                    message = "You can not decrease the temperature below "+ setPoints +"\u00B0F. During this time, your thermostat will be optimized by your utility provider.";
	                                } else if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
	                                    String setPoints = statInfo.getDRHeatSetPointLimit();
	                                    Double sP = Double.parseDouble(setPoints);
	                                    if (statInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
	                                        setPoints = String.valueOf(sP.intValue());
	                                    } else {
	                                        setPoints = String.valueOf(sP);
	                                    }
	                                    message ="You can not increase the temperature beyond "+ setPoints +"\u00B0F. During this time, your thermostat will be optimized by your utility provider.";
	                                }
	                            }
	                            if (dr.VerifyDRMessageContent().equalsIgnoreCase(message)) {
	                                    Keyword.ReportStep_Pass(testCase,
	                                                            "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel alert message is correctly displayed");
	                                } else {
	                                    flag = false;
	                                    Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
	                                                            "Verifying Savings Cancel Event Pop Up Message : Savings Event Cancel Alert message is not correctly displayed. Actual : "
	                                                            + dr.VerifyDRMessageContent()
	                                                            + " Expected : " + message);
	                                }
	                            }
	                        else{

	                            flag = false;
	                            Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
	                                                    "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel Alert title is not correctly displayed");
	                        }

	                            flag = false;
	                            Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
	                                                    "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel Alert title is not correctly displayed");
	                        
				}
				else{

                    flag = false;
                    Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                            "Verifying Savings Event Cancel Pop Up Message : Savings Event Cancel Alert title is not displayed");
				}
	
				}
	                        catch (Exception e) {
		                        Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		                    }
		                    return flag;
		                }
		
}

