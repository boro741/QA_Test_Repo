package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.AdhocScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.OSPopUps;

import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.das.utils.VacationSettingsUtils;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.VacationHoldScreen;
import com.honeywell.screens.WLDLeakDetectorSettings;
import com.honeywell.screens.WLDManageAlerts;
import com.honeywell.screens.WLDUpdateFrequency;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

public class ChangeBaseStationSettings extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public ChangeBaseStationSettings(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the \"(.*)\" to \"(.*)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Base Station Volume")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.VOLUME);
				String value = parameters.get(1).split("%")[0].split("~")[1];
				if (bs.setValueToVolumeSlider(value)) {
					Keyword.ReportStep_Pass(testCase, "Successfully set the volume to " + parameters.get(1));
					DASSettingsUtils.waitForProgressBarToComplete(testCase, "LOADING PROGRESS BAR", 2);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to set the volume to: " + parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofencing Status")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings Screen");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already disabled on the settings Screen");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("People Detection")){
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isPeopleDetectiontabVisible(15)){
					flag = flag && bs.clickOnPeopleDetectiontabOption();
					if (parameters.get(0).equalsIgnoreCase("on")){
						while(bs.getPeopleDetectionToggleValue().equalsIgnoreCase("0")){
							flag = flag && bs.clickOnPeopleDetectiontoggle();
						}
					}else{
						while(bs.getPeopleDetectionToggleValue().equalsIgnoreCase("1")){
							flag = flag && bs.clickOnPeopleDetectiontoggle();
						}
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed due to  " + parameters.get(0) + "Not visible in Settings");
				}
				bs.clickOnBackButton();
			}
			else if (parameters.get(0).equalsIgnoreCase("Geofencing Option")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);

				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					flag = flag & DASSettingsUtils.EnableGlobalGeofence(testCase);
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					Thread.sleep(2000);
					if (bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings page");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}

					flag = flag & DASSettingsUtils.navigateFromSecuritySettingsToSecuritySolutionCard(testCase);
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					Thread.sleep(3000);
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					Thread.sleep(2000);
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					Thread.sleep(2000);
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already disabled on the settings page");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}

				} else if (parameters.get(1).equalsIgnoreCase("ON when Global Geofence disabled")) {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					Thread.sleep(2000);
					flag = flag & DASSettingsUtils.DisableGlobalGeofence(testCase);
					Thread.sleep(2000);
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						bs.toggleGeofencingSwitch(testCase);
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings page");

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: "
								+ "Failed to disable Geofence settings when Global geofence got disabled");
					}

				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofencing this location")) {
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (DASSettingsUtils.EnableGlobalGeofence(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Succesfully turn on the " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed turn on the " + parameters.get(0));
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (DASSettingsUtils.DisableGlobalGeofence(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Succesfully turn OFF the " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed turn OFF the " + parameters.get(0));
					}
				}
			}
			// Amresh wld edit starts
			else if (parameters.get(0).equalsIgnoreCase("INDOOR TEMPERATURE ALERTS")) {
				WLDManageAlerts ale = new WLDManageAlerts(testCase);
				String status = "";
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {// Android
						if (ale.getIndoorTemperatureAlertsToggleValue().equals("OFF")) {
							status = ale.getIndoorTemperatureAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
							flag = flag && ale.clickIndoorTemperatureAlertsToggle();
						} else {
							status = ale.getIndoorTemperatureAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
						}
					} else {// ios
						flag = flag && ale.isIndoorTemperatureAlertToggleEnabled();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Status: Enabled");
						} else {
							flag = flag && ale.clickIndoorTemperatureAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Status: Enabled: Clicked");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {// Android
						if (ale.getIndoorTemperatureAlertsToggleValue().equals("ON")) {
							status = ale.getIndoorTemperatureAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
							ale.clickIndoorTemperatureAlertsToggle();
						} else {
							status = ale.getIndoorTemperatureAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
						}
					} else {// ios
						flag = flag && ale.isIndoorTemperatureAlertToggleEnabled();
						if (flag) {
							ale.clickIndoorTemperatureAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Status: Enabled: Clicked");
						} else {
							Keyword.ReportStep_Pass(testCase, "Status: Enabled");
						}
					}
				}
			} // Amresh Edit Ends
			// Amresh wld edit starts
			else if (parameters.get(0).equalsIgnoreCase("UPDATE FREQUENCY")) {
				WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
				WLDUpdateFrequency freq = new WLDUpdateFrequency(testCase);
				if (parameters.get(1).equalsIgnoreCase("DAILY")) {
					flag = flag & freq.clickOnDailyRadioButton();
					Keyword.ReportStep_Pass(testCase, "Clicked Daily");
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					Keyword.ReportStep_Pass(testCase, "Navigated from Frequency Card to Primary Card");
				}
				if (parameters.get(1).equalsIgnoreCase("TWICE DAILY")) {
					flag = flag & freq.clickOnTwiceDailyRadioButton();
					Keyword.ReportStep_Pass(testCase, "Clicked Twice Daily");
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					Keyword.ReportStep_Pass(testCase, "Navigated from Frequency Card to Primary Card");

				}
				if (parameters.get(1).equalsIgnoreCase("THREE TIMES DAILY")) {
					flag = flag & freq.clickOnThriceDailyRadioButton();
					Keyword.ReportStep_Pass(testCase, "Clicked Three Times Daily");
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					Keyword.ReportStep_Pass(testCase, "Navigated from Frequency Card to Primary Card");

				}
			}
			// Amresh Edit Ends
			// Amresh wld edit starts
			else if (parameters.get(0).equalsIgnoreCase("WLD INDOOR HUMIDITY ALERT")) {
				WLDManageAlerts ale = new WLDManageAlerts(testCase);
				String status = "";
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {// Android
						if (ale.getIndoorHumidityAlertsToggleValue().equals("OFF")) {
							status = ale.getIndoorHumidityAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
							flag = flag && ale.clickIndoorHumidityAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Clicked HumidityAlertsToggle Sucessfully");
						} else {
							status = ale.getIndoorHumidityAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
						}
					} else {// ios
						Thread.sleep(5000);
						if (ale.isIndoorHumidityAlertToggleEnabled()) {
							Keyword.ReportStep_Pass(testCase, "Status: Enabled");
						} else {
							flag = flag && ale.clickIndoorHumidityAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Clicked HumidityAlertsToggle Sucessfully");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {// Android
						if (ale.getIndoorHumidityAlertsToggleValue().equals("ON")) {
							status = ale.getIndoorHumidityAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
							flag = flag && ale.clickIndoorHumidityAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Clicked HumidityAlertsToggle Sucessfully");
						} else {
							status = ale.getIndoorHumidityAlertsToggleValue();
							Keyword.ReportStep_Pass(testCase, "Status: " + status);
						}
					} else {// ios
						if (ale.isIndoorHumidityAlertToggleEnabled()) {
							flag = flag && ale.clickIndoorHumidityAlertsToggle();
							Keyword.ReportStep_Pass(testCase, "Clicked HumidityAlertsToggle Sucessfully");

						} else {
							Keyword.ReportStep_Pass(testCase, "Status: Enabled");
						}
					}
				}
			} // Amresh Edit Ends
			else if (parameters.get(0).equalsIgnoreCase("Geofence this location toggle")) {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				OSPopUps os = new OSPopUps(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (gs.selectOptionFromGeofenceSettings(GeofenceSettings.ENABLEGEOFENCETHISLOCATION)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (os.isUpdateLocationPermissionsPopupVisible() && os.isAllowButtonVisible()) {
								Keyword.ReportStep_Pass(testCase,
										"Update Loation Permission Popup is displayed. Tap on Allow button in it");
								flag &= os.clickOnAllowButton();
							} else {
								Keyword.ReportStep_Pass(testCase, "Update Loation Permission Popup is not displayed.");
							}
						} else {
							if (os.isUpdateLocationPermissionsPopupVisible()) {
								Keyword.ReportStep_Pass(testCase,
										"Update Loation Permission Popup is displayed. Go to Settings-> Honeywell Location Services and Select Always Option");
								if (os.isGoToSettingsButtonVisibleInUpdateLocationPermissionsPopup()) {
									Keyword.ReportStep_Pass(testCase,
											"Go To Settings button is displayed in Update Location Permissions Popup");
									flag &= os.clickOnGoToSettingsButtonInUpdateLocationPermissionPopup();
									if (os.isLocationCellInHoneywellSettingsVisible()) {
										Keyword.ReportStep_Pass(testCase,
												"Location Option in Honeywell App Settings Screen is displayed");
										flag &= os.clickOnLocationCellInHoneywellSettings();
										if (os.isHoneywellButtonInLocationServicesScreenVisible()
												&& os.isAlwaysOptionInHoneywellLocationServicesScreenVisible()) {
											Keyword.ReportStep_Pass(testCase,
													"Always Option is displayed in Location Services screen under Settings");
											flag &= os.clickOnAlwaysOptionInHoneywellLocationServicesScreen();
											if (os.isAlwaysOptionSelectedInHoneywellLocationServicesScreenVisible()) {
												Keyword.ReportStep_Pass(testCase,
														"Always Option is selected in Location Services screen under Settings");
												if (os.isReturnToHoneywellButtonInHoneywellLocationServicesScreenVisible()) {
													Keyword.ReportStep_Pass(testCase,
															"Return To Honeywell Button is displayed in Location Services screen under Settings");
													flag &= os
															.clickOnReturnToHoneywellButtonInHoneywellLocationServicesScreen();
													if (gs.isGeofenceSettingsScreenTitleVisible()) {
														Keyword.ReportStep_Pass(testCase,
																"Navigated back to Honeywell Geofence Settings Screen");
														if (gs.selectOptionFromGeofenceSettings(
																GeofenceSettings.ENABLEGEOFENCETHISLOCATION)) {
															Keyword.ReportStep_Pass(testCase,
																	"Enabled geofence this location toggle option");
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"Already Enabled geofence this location toggle option");
														}
													} else {
														flag = false;
														Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
																"Failed to navigate back to Honeywell Geofence Settings Screen");
													}
												} else {
													flag = false;
													Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
															"Return To Honeywell button is not displayed in Location Services screen under Settings");
												}
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Failed to Select Always Option in Location Services screen under Settings");
											}
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Always Option is not displayed in Location Services screen under Settings");
										}
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Location Option in Honeywell App Settings Screen is not displayed");
									}
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Go To Settings button is not displayed in Update Location Permissions Popup");
								}
							} else {
								Keyword.ReportStep_Pass(testCase, "Update Loation Permission Popup is not displayed.");
							}
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to turn ON Geofence this Location toggle");
					}
					Thread.sleep(3000);
					if (gs.selectOptionFromGeofenceSettings(GeofenceSettings.ENABLEGEOFENCEALERT)) {
						Keyword.ReportStep_Pass(testCase, "Enabled geofence this location Alert toggle option");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to turn ON Geofence Alert toggle");
					}
				}
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (gs.selectOptionFromGeofenceSettings(GeofenceSettings.DISABLEGEOFENCETHISLOCATION)) {
						Keyword.ReportStep_Pass(testCase, "Disabled geofence this location toggle option");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to turn OFF Geofence this Location toggle");
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofence Alert toggle")) {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (gs.selectOptionFromGeofenceSettings(GeofenceSettings.ENABLEGEOFENCEALERT)) {
						Keyword.ReportStep_Pass(testCase, "Enabled Geofence Alert toggle option");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to turn ON Geofence Alert toggle");
					}
				}
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (gs.selectOptionFromGeofenceSettings(GeofenceSettings.DISABLEGEOFENCEALERT)) {
						Keyword.ReportStep_Pass(testCase, "Disabled geofence this location toggle option");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to turn OFF Geofence Alert toggle");
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Camera ON in Home Mode")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"camera ON in home mode is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleCameraOnInHomeModeSwitch();
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"camera ON in home mode is enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"camera ON in home mode is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleCameraOnInHomeModeSwitch();
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"camera ON in home mode is enabled in the Camera settings Screen");
							flag = flag & cs.toggleCameraOnInHomeModeSwitch();
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"camera ON in home mode is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraOnInHomeModeSwitch();
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Camera ON in Night Mode")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int startx = (dimension.width * 20) / 100;
					int starty = (dimension.height * 62) / 100;
					int endx = (dimension.width * 22) / 100;
					int endy = (dimension.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				} else {
					/*
					 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
					 * (dimension.getHeight() * .6)) .release().perform();
					 */
					action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
					.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				}
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraOnInNightModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"camera ON in night mode is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleCameraOnInNightModeSwitch();
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"camera ON in night mode is enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraOnInNightModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"camera ON in night mode is already disabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleCameraOnInNightModeSwitch();
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraOnInNightModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("CAMERA STATUS ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status On/Off Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status On/Off Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION EVENT ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is already enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("CAMERA FACE DETECTION ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already enabled in the Camera settings Screen");
						flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
						flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is disabled in the Camera settings Screen");
							flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
							flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Alerts is enabled in the Camera settings Screen");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to turn " + parameters.get(1) + " toggel option ");
							}
						}
					} else {
						flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
						flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is enabled in the Camera settings Screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to turn " + parameters.get(1) + " toggel option ");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already disabled in the Camera settings Screen");
						flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
						flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is enabled in the Camera settings Screen");
							flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
							flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Alerts is disabled in the Camera settings Screen");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to turn " + parameters.get(1) + " toggel option ");
							}
						}
					} else {
						flag &= cs.toggleCameraFaceDetectionAlertsSwitch(testCase);
						flag &= CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraFaceDectiontAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is disabled in the Camera settings Screen");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to turn " + parameters.get(1) + " toggel option ");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND EVENT ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isSoundEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is enabled in the Camera settings Screen");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isSoundEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Sound Event Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Sound Event Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Sound Event Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION DETECTION")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Motion Detection Toggle is already enabled in the Camera Motion Detection Screen");
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
							flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Motion Detection Toggle is enabled in the Camera Motion Detection Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Motion Detection Toggle is already disabled in the Camera Motion Detection Screen");
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
							flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION SENSITIVITY")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				@SuppressWarnings("rawtypes")
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int startx = (dimension.width * 20) / 100;
					int starty = (dimension.height * 62) / 100;
					int endx = (dimension.width * 22) / 100;
					int endy = (dimension.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				} else {
					/*
					 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
					 * (dimension.getHeight() * .6)) .release().perform(); action.press(10, (int)
					 * (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					 * .release().perform();
					 */
					action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
					.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					//					action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
					//							.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				}
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("NORMAL")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						if (cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1))) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("MEDIUM")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND DETECTION")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Sound Detection Toggle is already enabled in the Camera Sound Detection Screen");
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
							flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Sound Detection Toggle is enabled in the Camera Motion Detection Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Sound Detection Toggle is already disabled in the Camera Sound Detection Screen");
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned ON");
							flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
						}
					}
				}

			} else if (parameters.get(0).equalsIgnoreCase("NIGHT VISION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VIDEO QUALITY SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("CAMERA MICROPHONE")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", parameters.get(0));
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraMicrophoneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Microphone Toggle is already enabled in the Camera Settings Screen");
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMicrophoneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Microphone Toggle is turned OFF");
							flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						}
					} else {
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraMicrophoneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Microphone Toggle is already disabled in the Camera Settings Screen");
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMicrophoneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Microphone Toggle is turned ON");
							flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						}
					} else {
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("INDOOR TEMPERATURE ALERT")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Temperature Alert Toggle is already enabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned OFF");
							flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Temperature Alert Toggle is enabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Temperature Alert Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned ON");
							flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Temperature Alert Toggle is disabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("INDOOR HUMIDITY ALERT")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Humidity Alert Toggle is already enabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is turned OFF");
							flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Humidity Alert Toggle is enabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Humidity Alert Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is turned ON");
							flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Humidity Alert Toggle is disabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorHumidityAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SET FILTER REMINDER SWITCH")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is already enabled");
						flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is turned OFF");
							flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is enabled");
							}
						}
					} else {
						flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is already disabled");
						flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is turned ON");
							flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is disabled");
							}
						}
					} else {
						flag = flag & ts.toggleSetFilterReminderSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatSetFilterReminderSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("FINE TUNE")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatFineTuneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Fine Tune Switch is already enabled in Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatFineTuneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is turned OFF");
							flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatFineTuneSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is enabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatFineTuneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatFineTuneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Fine Tune Switch is already disabled in the Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatFineTuneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is turned ON");
							flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatFineTuneSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is disabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatFineTuneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatFineTuneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Fine Tune Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("ADAPTIVE RECOVERY")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Adaptive Recovery Switch is already enabled in Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is turned OFF");
							flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is enabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Adaptive Recovery Switch is already disabled in the Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is turned ON");
							flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is disabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatAdaptiveRecoverySwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("OPTIMISE")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatOptimiseSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Optimise Switch is already enabled in Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatOptimiseSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Optimise Switch is turned OFF");
							flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatOptimiseSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Optimise Switch is enabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatOptimiseSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Optimise Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatOptimiseSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Optimise Switch is already disabled in the Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatOptimiseSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Optimise Switch is turned ON");
							flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatOptimiseSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Optimise Switch is disabled");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatOptimiseSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatOptimiseSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Optimise Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("EMERGENCY HEAT")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatEmergencyHeatSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Emergency Heat is already enabled in Thermostat Settings Screen");

					} else {
						flag = flag & ts.toggleThermostatEmergencyHeatSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatEmergencyHeatSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Emergency Heat Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatEmergencyHeatSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Emergency Heat Switch is already disabled in the Thermostat Settings Screen");
					} else {
						flag = flag & ts.toggleThermostatEmergencyHeatSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatEmergencyHeatSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Emergency Heat Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("FROST PROTECTION MODE")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.FROSTPROTECTION);
				String value = parameters.get(1).split("%")[0].split("~")[1];
				if (ts.setValueToHumiditySlider(value)) {
					Keyword.ReportStep_Pass(testCase, "Successfully set the Frost proection to " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to set the Frost proection to: " + parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("HUMIDIFICATION")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatHumidificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Humidification is already enabled in Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatHumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Humidification Switch is turned OFF");
							flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatHumidificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Humidification Switch is enabled");
								HBNAEMEASettingsUtils
								.verifyThermostatHumidificationValueInHumidificationScreen(testCase, inputs);
							}
						}
					} else {
						flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatHumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Humidification Switch is turned ON");
							HBNAEMEASettingsUtils.verifyThermostatHumidificationValueInHumidificationScreen(testCase,
									inputs);
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatHumidificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Humidification Switch is already disabled in the Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatHumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Humidification Switch is turned ON");
							flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatHumidificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Humidification Switch is disabled");
								HBNAEMEASettingsUtils
								.verifyThermostatHumidificationValueInHumidificationScreen(testCase, inputs);
							}
						}
					} else {
						flag = flag & ts.toggleThermostatHumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatHumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Humidification Toggle is turned OFF");
							HBNAEMEASettingsUtils.verifyThermostatHumidificationValueInHumidificationScreen(testCase,
									inputs);
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("DEHUMIDIFICATION")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Dehumidification is already enabled in Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Dehumidification Switch is turned OFF");
							flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Dehumidification Switch is enabled");
								HBNAEMEASettingsUtils.verifyThermostatDehumidificationValueInDehumidificationScreen(
										testCase, inputs);
							}
						}
					} else {
						flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Dehumidification Switch is turned ON");
							HBNAEMEASettingsUtils
							.verifyThermostatDehumidificationValueInDehumidificationScreen(testCase, inputs);
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Dehumidification Switch is already disabled in the Thermostat Settings Screen");
						flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Dehumidification Switch is turned ON");
							flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Dehumidification Switch is disabled");
								HBNAEMEASettingsUtils.verifyThermostatDehumidificationValueInDehumidificationScreen(
										testCase, inputs);
							}
						}
					} else {
						flag = flag & ts.toggleThermostatDehumidificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatDehumidificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Dehumidification Toggle is turned OFF");
							HBNAEMEASettingsUtils
							.verifyThermostatDehumidificationValueInDehumidificationScreen(testCase, inputs);
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SLEEP BRIGHTNESS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				String value = parameters.get(1).split("%")[0].split("~")[1];
				if (ts.setValueToSleepBrightnessModeSlider(value)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully set the Sleep Brightness Mode to " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to set the Sleep Brightness Mode to: " + parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND")
					|| parameters.get(0).equalsIgnoreCase("SOUND SENSITIVITY	")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (ts.isSoundStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Sound Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setSoundStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Sound Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sound Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					if (ts.isSoundStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Sound Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setSoundStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Sound Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sound Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("NORMAL")) {
					if (ts.isSoundStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Sound Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setSoundStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Sound Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sound Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					if (ts.isSoundStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Sound Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setSoundStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Sound Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sound Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("AUTO CHANGEOVER")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Auto Changeover is already enabled in Thermostat Settings Screen");
					} else {
						flag = flag & ts.toggleThermostatAutoChangeOverSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Auto Changeover Switch is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Auto Changeover Switch is already disabled in the Thermostat Settings Screen");
					} else {
						flag = flag & ts.toggleThermostatAutoChangeOverSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatAutoChangeOverSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Auto Changeover Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VENTILATION")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (ts.isVentilationStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Ventilation Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setVentilationStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Ventilation Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Ventilation Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isVentilationStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Ventilation Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setVentilationStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Ventilation Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Ventilation Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					if (ts.isVentilationStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Ventilation Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & ts.setVentilationStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Ventilation Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Ventilation Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Camera Status Email Notification")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status ON/OFF Email Notification Toggle is already enabled in the Camera Manange Alerts Screen");
						flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
							flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Status ON/OFF Email Notification Toggle is enabled in the Camera Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status ON/OFF Email Notification Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status ON/OFF Email Notification Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status ON/OFF Email Notification Toggle is turned ON");
							flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Status ON/OFF Email Notification  Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleCameraEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status ON/OFF Email Notification Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Sound Event Email Notification")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Email Notification Toggle is already enabled in the Camera Manage Alerts Screen");
						flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Sound Event Email Notification Toggle is turned OFF");
							flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Sound Event Email Notificationn Toggle is enabled in the Camera Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Email Notificationn Toggle Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Sound Event Email Notificationn Toggle is turned ON");
							flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Sound Event Email Notificationn Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleSoundEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isSoundEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Sound Event Email Notificationn Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Motion Event Email Notification")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Email Notification Toggle is already enabled in the Camera Manage Alerts Screen");
						flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Motion Event Email Notification Toggle is turned OFF");
							flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Email Notificationn Toggle is enabled in the Camera Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Email Notificationn Toggle Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Motion Event Email Notificationn Toggle is turned ON");
							flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Email Notificationn Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleMotionEmailNotificationSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isMotionEmailNotificationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Motion Event Email Notificationn Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VACATION")) {
				VacationHoldScreen vhs = new VacationHoldScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!vhs.isVacationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Toggle is already disabled in the Vacation Settings Screen");
						flag = flag & vhs.toggleVacationDetectionSwitch(testCase);
						flag = flag & VacationSettingsUtils.waitForProgressBarToComplete(testCase,
								"LOADING SPINNER BAR", 2);
						if (vhs.isVacationSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Vacation Toggle is turned ON");
							flag = flag & vhs.toggleVacationDetectionSwitch(testCase);
						}
					} else {
						flag = flag & vhs.toggleVacationDetectionSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (vhs.isVacationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Toggle is already enabled in the Vacation Settings Screen");
					} else {
						flag = flag & vhs.toggleVacationDetectionSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VACATION UNTIL")) {
				AdhocScreen ads = new AdhocScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF") || parameters.get(1).equalsIgnoreCase("ON")) {
					if (!ads.isVacationStatusInSolutionCardVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Until button is not displayed in Solutions Card Screen");
						if (ads.isSystemIsOffLabelInSolutionsCardScreen()) {
							Keyword.ReportStep_Pass(testCase, "System Mode if OFF");
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Until button is displayed in Solutions Card Screen");
						flag = flag & ads.clickOnVacationStatusInSolutionCardScreen();
						flag = flag & ads.clickOnEndVacationOptionInSolutionCardScreen();
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VACATION HOLD SWITCH IN STAT SCREEN")) {
				VacationHoldScreen vhs = new VacationHoldScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!vhs.isVacationSwitchInStatScreenEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Vacation Toggle is already disabled in the Stats Screen");
						flag = flag & vhs.toggleVacationDetectionSwitchInStatScreen(testCase);
						flag = flag & VacationSettingsUtils.waitForProgressBarToComplete(testCase,
								"LOADING SPINNER BAR", 2);
						if (vhs.isVacationSwitchInStatScreenEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Vacation Toggle is turned ON");
							flag = flag & vhs.toggleVacationDetectionSwitchInStatScreen(testCase);
						}
					} else {
						flag = flag & vhs.toggleVacationDetectionSwitchInStatScreen(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (vhs.isVacationSwitchInStatScreenEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Vacation Toggle is already enabled in the Stats Screen");
					} else {
						flag = flag & vhs.toggleVacationDetectionSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SECURITY MODE CHANGE")) {
				BaseStationSettingsScreen mc = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (mc.isSecurityModeChangeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Security Mode Change Toggle is already enabled in the Manage alerts screen");
						flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isSecurityModeChangeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
							flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (mc.isSecurityModeChangeSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Security Mode Change Toggle is already enabled in the Manage alerts screen");
							}
						}
					} else {
						flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isSecurityModeChangeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!mc.isSecurityModeChangeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Security Mode Change Toggle is already disabled in the Manage alerts screen");
						flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isSecurityModeChangeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned ON");
							flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!mc.isSecurityModeChangeSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & mc.toggleSecurityModeChangeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isSecurityModeChangeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("ENHANCED DETERRENCE")) {
				BaseStationSettingsScreen mc = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Enhanced Deterrence Toggle is already enabled in the Manage alerts screen");
						flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
							flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Enhanced Deterrence Toggle is already enabled in the Manage alerts screen");
							}
						}
					} else {
						flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Enhanced Deterrence Toggle is already disabled in the Manage alerts screen");
						flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned ON");
							flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & mc.toggleEnhancedDeterrenceSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("OUTDOOR MOTION VIEWERS ON IN HOME MODE")) {
				//					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				//				@SuppressWarnings("rawtypes")
				//				TouchAction action = new TouchAction(testCase.getMobileDriver());
				//				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				//					int startx = (dimension.width * 20) / 100;
				//					int starty = (dimension.height * 62) / 100;
				//					int endx = (dimension.width * 22) / 100;
				//					int endy = (dimension.height * 35) / 100;
				//					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				//				} else {
				//					
				//					 	action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
				//							.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				//				}

				BaseStationSettingsScreen mc = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Enhanced Deterrence Toggle is already enabled in the Manage alerts screen");
						flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isEnhancedDeterrenceSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
							flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Enhanced Deterrence Toggle is already enabled in the Manage alerts screen");
							}
						}
					} else {
						flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Enhanced Deterrence Toggle is already disabled in the Manage alerts screen");
						flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "SEnhanced Deterrence Toggle is turned ON");
							flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & mc.toggleOutdoorMotionViewersOnInHomeModeSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isOutdoorMotionViewersOnInHomeModeSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Enhanced Deterrence Toggle is turned OFF");
						}
					}
				}
			}

			else if (parameters.get(0).equalsIgnoreCase("DOORS AND WINDOWS")) {
				BaseStationSettingsScreen mc = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Security Mode Change Toggle is already enabled in the Manage alerts screen");
						flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
							flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Security Mode Change Toggle is already enabled in the Manage alerts screen");
							}
						}
					} else {
						flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Security Mode Change Toggle is already disabled in the Manage alerts screen");
						flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned ON");
							flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & mc.toggleDoorsAndWindowsAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!mc.isDoorsAndWindowsAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Security mode change Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("ANONYMOUS TOGGLE BUTTON")) {
				AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (atas.isAnonymousToggleButtonEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								"Anonymous Toggle Button is already enabled in the App Feedback screen");
						flag = flag & atas.selectAnonymousToggleButton();
						if (!atas.isAnonymousToggleButtonEnabled()) {
							Keyword.ReportStep_Pass(testCase, "Anonymous Toggle Button is turned OFF");
							flag = flag & atas.selectAnonymousToggleButton();
							if (atas.isAnonymousToggleButtonEnabled()) {
								Keyword.ReportStep_Pass(testCase,
										"Anonymous Toggle Button is enabled in the App Feedback screen");
							}
						}
					} else {
						flag = flag & atas.selectAnonymousToggleButton();
						if (atas.isAnonymousToggleButtonEnabled()) {
							Keyword.ReportStep_Pass(testCase,
									"Anonymous Toggle Button is enabled in the App Feedback screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!atas.isAnonymousToggleButtonEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								"Anonymous Toggle Button is already disabled in the App Feedback screen");
						flag = flag & atas.selectAnonymousToggleButton();
						if (atas.isAnonymousToggleButtonEnabled()) {
							Keyword.ReportStep_Pass(testCase, "Anonymous Toggle Button is turned ON");
							flag = flag & atas.selectAnonymousToggleButton();
							if (!atas.isAnonymousToggleButtonEnabled()) {
								Keyword.ReportStep_Pass(testCase,
										"Anonymous Toggle Button is disabled in the App Feedback screen");
							}
						}
					} else {
						flag = flag & atas.selectAnonymousToggleButton();
						if (atas.isAnonymousToggleButtonEnabled()) {
							Keyword.ReportStep_Pass(testCase,
									"Anonymous Toggle Button is disabled in the App Feedback screen");
						}
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}