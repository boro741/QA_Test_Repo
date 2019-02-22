package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSensorUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ZwavePrimardCardScreen;
import com.honeywell.screens.ZwaveScreen;

public class VerifyStatusOnScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;
	private String currentStatus;

	public VerifyStatusOnScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should see the (.*) status as (.*) on the (.*)$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(2).toUpperCase()) {
		case "CAMERA SOLUTION CARD":
		case "CAMERA": {
			CameraScreen camStatus = new CameraScreen(testCase);
			String value = expectedScreen.get(1).toUpperCase();

			if (!camStatus.isCameraToggleButtonExists(testCase)) {
				String cameraName = inputs.getInputValue("LOCATION1_CAMERA1_NAME");
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
				try {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, cameraName);
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());

				}
			}

			switch (value) {
			case "ON": {
				if (camStatus.isCameraToggleisOn(testCase)) {
					return flag;
				} else {
					return flag;
				}

			}
			case "OFF": {
				if (camStatus.isCameraToggleisOff(testCase)) {
					return flag;
				} else {
					return flag;
				}
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedScreen.get(1).toUpperCase() + " is not handled " + expectedScreen.get(0).toUpperCase());
			}
			}

			break;
		}
		case "THERMOSTAT SOLUTION CARD": {
			PrimaryCard dash = new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "INSIDE TEMPERATURE": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "OFF": {
					if (dash.isOffStatusVisibleOnSolutionCard()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is not in "
										+ expectedScreen.get(1).toUpperCase());
					}
					break;
				}

				}
				break;
			}

			}

			break;
		}
		case "SENSOR LIST":
		case "SENSOR STATUS": {
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 2);
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DOOR SENSOR":
			case "DOOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "OPEN": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "CLOSED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "OFF": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "COVER TAMPERED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0).toUpperCase() + " is not in " + expectedScreen.get(1).toUpperCase());
				}
				break;
			}
			case "WINDOW SENSOR":
			case "WINDOW": {
			switch (expectedScreen.get(1).toUpperCase()) {
				case "OPEN": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "CLOSED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "OFF": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "COVER TAMPERED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0).toUpperCase() + " is not in " + expectedScreen.get(1).toUpperCase());
				}
				break;
			}
			case "MOTION SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "STANDBY":
				case "GOOD": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Good is found");
					}
					break;
				}
				case "ACTIVE": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Active is found");
					}
					break;
				}
				case "OFF": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "COVER TAMPERED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0).toUpperCase() + " is not in " + expectedScreen.get(1).toUpperCase());
				}
				break;
			}
			case "ISMV":
			case "ISMV SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "STANDBY": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Good is found");
					}
					break;
				}
				case "ACTIVE": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Active is found");
					}
					break;
				}
				case "OFF": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "COVER TAMPERED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "LOW BATTERY": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0).toUpperCase() + " is not in " + expectedScreen.get(1).toUpperCase());
				}
				break;
			}
			case "OSMV":
			case "OSMV SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "STANDBY": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Good is found");
					}
					break;
				}
				case "ACTIVE": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					if (flag) {
						System.out.println("Active is found");
					}
					break;
				}
				case "OFF": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				case "COVER TAMPERED": {
					DASSensorUtils sensorUtils = new DASSensorUtils();
					flag = sensorUtils.verifySensorState(testCase, inputs, expectedScreen.get(0),
							expectedScreen.get(1));
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedScreen.get(0).toUpperCase() + " is not in " + expectedScreen.get(1).toUpperCase());
				}
				break;
			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedScreen.get(0).toUpperCase() + " is not handled");
			}
			}
			break;
		}
		case "SECURITY SOLUTION CARD": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SENSOR": {
				SecuritySolutionCardScreen securityScreen = new SecuritySolutionCardScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ISSUE": {
					flag = securityScreen.isSensorIssueVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is not in "
										+ expectedScreen.get(1).toUpperCase());
					}
					break;
				}
				case "NO ISSUE": {
					flag = securityScreen.isSensorNoIssueVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is not in "
										+ expectedScreen.get(1).toUpperCase());
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1).toUpperCase()
							+ " is not handled " + expectedScreen.get(0).toUpperCase());
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedScreen.get(0).toUpperCase() + " is not handled ");
			}
			}

			break;
		}
		case "DIMMER PRIMARY CARD": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DIMMER": {
				ZwavePrimardCardScreen zp = new ZwavePrimardCardScreen(testCase);
				DASZwaveUtils.waitForSwitchingToComplete(testCase);
				if (zp.verifyPresenceOfDimmerStatus()) {
					currentStatus = zp.getDimmerStatus();
					switch (expectedScreen.get(1).toUpperCase()) {
					case "ON": {
						if (Integer.parseInt(currentStatus) > 0) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					case "OFF": {
						if (currentStatus.equalsIgnoreCase("Off")) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					case "OFFLINE": {
						if (currentStatus.equalsIgnoreCase("OFFLINE")) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1).toUpperCase() + " is not handled "
										+ expectedScreen.get(0).toUpperCase());
					}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "switch status not found");
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedScreen.get(0).toUpperCase() + " is not handled ");
			}
			}
			break;
		}
		case "SWITCH PRIMARY CARD": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {
				ZwavePrimardCardScreen zp = new ZwavePrimardCardScreen(testCase);
				DASZwaveUtils.waitForSwitchingToComplete(testCase);
				if (zp.verifyPresenceOfSwitchStatus()) {
					currentStatus = zp.getSwitchStatus();
					switch (expectedScreen.get(1).toUpperCase()) {
					case "ON": {
						if (zp.getSwitchStatus().equalsIgnoreCase("On")) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					case "OFF": {
						if (zp.getSwitchStatus().equalsIgnoreCase("Off")) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					case "OFFLINE": {
						if (zp.getSwitchStatus().equalsIgnoreCase("OFFLINE")) {
							Keyword.ReportStep_Pass(testCase,
									expectedScreen.get(0) + " status is " + expectedScreen.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									expectedScreen.get(0) + " status is not in " + expectedScreen.get(1)
											+ " instead found to be " + currentStatus);
						}
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(1).toUpperCase() + " is not handled "
										+ expectedScreen.get(0).toUpperCase());
					}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "switch status not found");
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "DIMMER SETTINGS":
		case "SWITCH SETTINGS": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DIMMER":
			case "SWITCH": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					currentStatus = zwaveScreen.getSwitchStatus();
					if (currentStatus.equals("ON")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFF": {
					currentStatus = zwaveScreen.getSwitchStatus();
					if (currentStatus.equals("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFFLINE": {
					currentStatus = zwaveScreen.getSwitchStatusOffline();
					if (currentStatus.toUpperCase().equals("OFFLINE")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "ZWAVE DEVICES": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "ALL ON": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ACTIVE": {
					if (zwaveScreen.isAllOnEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isAllOnEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isAllOnEnabled());
					}
					break;
				}
				case "INACTIVE": {
					if (!zwaveScreen.isAllOnEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isAllOnEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isAllOnEnabled());
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			case "ALL OFF": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ACTIVE": {
					if (zwaveScreen.isAllOffEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isAllOffEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isAllOffEnabled());
					}
					break;
				}
				case "INACTIVE": {
					if (!zwaveScreen.isAllOffEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isAllOffEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isAllOffEnabled());
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			case "FIX ALL": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ACTIVE": {
					if (zwaveScreen.isFixAllEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isFixAllEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + zwaveScreen.isFixAllEnabled());
					}
					break;
				}
				case "INACTIVE": {
					if (!zwaveScreen.isFixAllEnabled()) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isFixAllEnabled());
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + !zwaveScreen.isFixAllEnabled());
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			case "SWITCH": {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				String currentStatus = zwaveScreen
						.getSwitchStatusFromDevicesListScreen(inputs.getInputValue("LOCATION1_SWITCH1_NAME"));
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					if (currentStatus.equalsIgnoreCase("ON")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFF": {
					if (currentStatus.equalsIgnoreCase("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFFLINE": {
					if (currentStatus.equalsIgnoreCase("OFFLINE")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			case "DIMMER": {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				String currentStatus = zwaveScreen
						.getSwitchStatusFromDevicesListScreen(inputs.getInputValue("LOCATION1_DIMMER1_NAME"));
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					if (currentStatus.equalsIgnoreCase("ON")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFF": {
					if (currentStatus.equalsIgnoreCase("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFFLINE": {
					if (currentStatus.equalsIgnoreCase("OFFLINE")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 2 not handled");
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "DASHBOARD": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {
				Dashboard ds = new Dashboard(testCase);
				currentStatus = ds.getZwaveDeviceStatus(inputs.getInputValue("LOCATION1_SWITCH1_NAME"));
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					if (currentStatus.equals("ON")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFF": {
					if (currentStatus.equals("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFFLINE": {
					if (currentStatus.toUpperCase().equals("OFFLINE")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}

				}
				/*
				 * }else{ flag=false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE, "switch status not found"); }
				 */
				break;
			}
			case "DIMMER": {
				Dashboard ds = new Dashboard(testCase);
				currentStatus = ds.getZwaveDeviceStatus(inputs.getInputValue("LOCATION1_DIMMER1_NAME"));
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					if (currentStatus.equals("ON")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFF": {
					if (currentStatus.equals("OFF")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				case "OFFLINE": {
					if (currentStatus.toUpperCase().equals("OFFLINE")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					}
					break;
				}
				}
				/*
				 * }else{ flag=false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE, "switch status not found"); }
				 */
				break;
			}
			case "ENTRY DELAY": {
				// TODO
				break;
			}

			case "SECURITY": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ATTENTION": {
					if (MobileUtils.getFieldValue(testCase, "NAME", "Control_State").equalsIgnoreCase("ATTENTION")) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is in " + currentStatus);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is NOT IN Alarm");
					}
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "ZWAVE DEVICE": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					try {
						if (ZWaveRelayUtils.isSwitch1ON()) {
							Keyword.ReportStep_Pass(testCase, "Switch is in on state in ZWAVE device");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Switch is not in on state on the ZWAVE device");
						}
					} catch (Exception e) {
						ReportStep_Fail(testCase, FailType.FRAMEWORK_CONFIGURATION, "Relay issue" + e.getMessage());
					}
					break;
				}
				case "OFF": {
					try {
						if (!ZWaveRelayUtils.isSwitch1ON()) {
							Keyword.ReportStep_Pass(testCase, "Switch is in off state in ZWAVE device");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Switch is not in off state on the ZWAVE device");
						}
					} catch (Exception e) {
						ReportStep_Fail(testCase, FailType.FRAMEWORK_CONFIGURATION, "Relay issue" + e.getMessage());
					}
					break;
				}
				}
				break;
			}
			}
			break;
		}
		case "TEST MOTION SENSOR":
		case "TEST MOTION VIEWER":
		case "TEST ACCESS SENSOR": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DOOR":
			case "DOOR SENSOR": {
				try {
					TimeUnit.SECONDS.sleep(10);
					Keyword.ReportStep_Pass(testCase, "Delay introduced for Panel to detect sensor state ");
				} catch (InterruptedException e) {
				}
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CLOSED":
				case "OPEN": {
					if (sensor.isDoorStatusVisible(expectedScreen.get(1), inputs)) {
						Keyword.ReportStep_Pass(testCase, "Door Sensor is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Door Sensor is not " + (expectedScreen.get(1)));
					}
					break;
				}
				}
				break;
			}
			case "WINDOW":
			case "WINDOW SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CLOSED":
				case "OPEN": {
					if (sensor.isWindowStatusVisible(expectedScreen.get(1), inputs)) {
						Keyword.ReportStep_Pass(testCase, "Window Sensor is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Window Sensor is not " + (expectedScreen.get(1)));
					}
					break;
				}
				}
				break;
			}
			case "MOTION SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "MOTION DETECTED": {
					if (sensor.isMotionSensorStatusVisible(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"),
							expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				case "NO MOTION DETECTED":
				case "MOTION NOT DETECTED": {
					if (sensor.isMotionSensorStatusVisible(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"),
							expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				}
				break;
			}
			case "ISMV SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "MOTION DETECTED": {
					if (sensor.isMotionSensorStatusVisible(
							inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"), expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				case "NO MOTION DETECTED":
				case "MOTION NOT DETECTED": {
					if (sensor.isMotionSensorStatusVisible(
							inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"), expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				}
				break;
			}
			case "OSMV SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "MOTION DETECTED": {
					if (sensor.isMotionSensorStatusVisible(
							inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"), expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				case "NO MOTION DETECTED":
				case "MOTION NOT DETECTED": {
					if (sensor.isMotionSensorStatusVisible(
							inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"), expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1")
								+ " is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1") + " is not "
										+ (expectedScreen.get(1)));
					}
					break;
				}
				}
				break;
			}
			}
			break;
		}
		case "SIGNAL STRENGTH": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "MOTION SENSOR":
			case "WINDOW SENSOR":
			case "WINDOW":
			case "DOOR":
			case "DOOR SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "HIGH": {
					if (sensor.isSignalStrengthVisible(expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0) + "  signal is " + (expectedScreen.get(1)));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0) + " signal is not in " + expectedScreen.get(1));
					}

					break;
				}
				}
				break;
			}
			case "SIGNAL TO BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				String testSignalStrengthValue = null;
				String expTestSignalStrengthValue = null;
				expTestSignalStrengthValue = expectedScreen.get(0);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "HIGH": {
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
							"SIGNAL TO BASE STATION: HIGH", 2);
					if (flag && dasDIY.isSignalStrengthIsHighSubTitleVisibleInSignalStrengthScreen()) {
						testSignalStrengthValue = dasDIY.getSensorRangeSubTitleText();
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							expTestSignalStrengthValue = "Signal to base station";
						}
						if (testSignalStrengthValue
								.contains(expTestSignalStrengthValue + ": " + expectedScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, "Verified the signal strength value: "
									+ testSignalStrengthValue + "in the screen: " + expTestSignalStrengthValue);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to verify the signal strength value: " + expectedScreen.get(1) + " for: "
											+ expTestSignalStrengthValue + " in the screen: " + expectedScreen.get(2)
											+ ". Displayed signal strength value is: " + testSignalStrengthValue);
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Displayed signal strength value is: " + testSignalStrengthValue);
					}
					break;
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "ACCESS SENSOR SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DOOR":
			case "DOOR SENSOR":
			case "WINDOW":
			case "WINDOW SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "OFF":
				case "OPEN":
				case "CLOSED": {
					if (bs.verifySensorStatusAfterTestSignalStrength(expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Door Sensor is " + (expectedScreen.get(1) + " after test signal"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Door Sensor is not " + (expectedScreen.get(1) + " after test signal"));
					}

					break;
				}
				}
				break;
			}
			}
			break;
		}
		case "MOTION SENSOR SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "MOTION":
			case "MOTION SENSOR": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "GOOD":
				case "STANDBY":
				case "COVER TAMPERED":
				case "OFF":
				case "NO MOTION DETECTED":
				case "MOTION DETECTED": {
					if (bs.verifySensorStatusAfterTestSignalStrength(expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"MOTION Sensor is " + (expectedScreen.get(1) + " after test signal"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"MOTION Sensor is not " + (expectedScreen.get(1) + " after test signal"));
					}
					break;
				}
				}
				break;
			}
			}
			break;
		}
		case "SET UP ACCESSORIES": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "DOOR SENSOR":
			case "DOOR": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CONFIGURED": {
					flag = flag & sensorSetting.isSensorConfigured(
							inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1"), expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			case "WINDOW SENSOR":
			case "WINDOW": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CONFIGURED": {
					flag = flag & sensorSetting.isSensorConfigured(
							inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1"), expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			case "MOTION SENSOR": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CONFIGURED": {
					System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"));
					flag = flag & sensorSetting.isSensorConfigured(
							inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1"), expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			case "KEYFOB": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ASSIGNED": {
					flag = flag & sensorSetting.isSensorConfigured(inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1"),
							expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;

			}
			case "WINDOW ACCESS SETTINGS":
			case "DOOR ACCESS SETTINGS": {
				switch (expectedScreen.get(0).toUpperCase()) {
				case "BATTERY": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.verifyBatteryStatusTextOnAccessSensorSettingsScreen(expectedScreen.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Battery status is " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Battery status is not " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			case "ISMV SENSOR": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CONFIGURED": {
					System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"));
					flag = flag & sensorSetting.isSensorConfigured(
							inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1"), expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			case "OSMV SENSOR": {
				SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(1).toUpperCase()) {
				case "CONFIGURED": {
					System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"));
					flag = flag & sensorSetting.isSensorConfigured(
							inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1"), expectedScreen.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully Verified " + expectedScreen.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to verify " + expectedScreen.get(1));
					}
					break;
				}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "THERMOSTAT DASHBOARD": {
			Dashboard dash = new Dashboard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "INSIDE TEMPERATURE": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "OFF": {
					if (dash.isOffStatusVisible(inputs)) {
						Keyword.ReportStep_Pass(testCase,
								expectedScreen.get(0).toUpperCase() + " is " + expectedScreen.get(1).toUpperCase());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								expectedScreen.get(0).toUpperCase() + " is not in "
										+ expectedScreen.get(1).toUpperCase());
					}
					break;
				}
				}
				break;
			}
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 3 not handled");
		}
			break;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
