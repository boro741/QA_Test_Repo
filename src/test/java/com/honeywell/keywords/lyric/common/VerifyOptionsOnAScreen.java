package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.spi.LocationInfo;
import org.json.JSONObject;
import org.python.antlr.ast.Assert;

import com.google.errorprone.annotations.Var;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;

public class VerifyOptionsOnAScreen extends Keyword {

	private TestCases testCase;
 private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the following (.*) options:$")
	public boolean keywordSteps() throws KeywordException {
		CHILUtil chUtil = null;
		DeviceInformation deviceInfo=null;
		
		try {
			chUtil = new CHILUtil(inputs);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		switch (expectedScreen.get(0).toUpperCase()) {
		
		case "ENTRYDELAY":{
			AlarmScreen check = new AlarmScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Elements");
				switch(parameter.toUpperCase()) {
				case "ENTRY DELAY WITH DOOR OPEN TITLE":{
					flag = flag & check.isEntryDelayTitleVisible();
					break;
				}
				case "ENTRY DELAY WITH SENSOR NAME":{
					flag = flag & check.isEntryDelaySubTitleVisible(inputs);
					break;
				}
                case "ENTRY DELAY SUBTITLE AS LOCATION NAME":{
                	flag = flag & check.isEntryDelayLocationVisible(inputs);
                	break;
				}
                case "ENTRY DELAY LIVE STREAM":{
                	flag = flag & check.verifyLiveStreamingProgress();
                	break;
                } 
               case "ENTRY DELAY ALARM IN SECS TEXT  ":{
            		flag = flag & check.isAlarmWillSoundInTextVisible();
            		break;
                 }
               case "ENTRY DELAY ALARM IN SECS COUNTER":{
            	   flag = flag & check.AlarmInSecsCounter();
            	   break;
               }
              }
				if(flag) {
					Keyword.ReportStep_Pass(testCase, "The "+parameter+"has found");
				}
				else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "The "+parameter+" has not found");
				}
				
				}
			break;
			}
		case "ALARM":{
			AlarmScreen check = new AlarmScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Elements");
				switch(parameter.toUpperCase()) {
				case "ALARM TITLE":{
					flag = flag & check.isAlarmTitleVisible();
					break;
				}
				case "ALARM SUBTITLE":{
					flag = flag & check.isAlarmLocationVisible(inputs);
					break;
				}
				case "ALARM LIVE STREAM":{
					flag = flag & check.verifyLiveStreamingProgress();
		               
					break;
				}
				case "ALARM NAVIGATE BACK BUTTON":{
					flag = flag & check.isAlarm_NavigatebackVisible();
					break;
				}
				case "CALL":{
					flag = flag & check.isCallButtonVisible();
					break;
				}
				}
				if(flag) {
					Keyword.ReportStep_Pass(testCase, "The "+parameter+"has found");
				}
				else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "The"+parameter+"has not found");
				}
			}
			break;
		}
		case "SECURITY SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the DAS Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Settings: '" + fieldTobeVerified + "' is not present on the DAS Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		
		case "ALERTS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			ArrayList<String> lstData = new ArrayList<String>();
			for (int i = 0; i < data.getSize(); i++)
			{
				lstData.add(data.getData(i, "Alerts"));
			}
			
				if(lstData.contains("Doors and Windows")) {
					//Check for sensor and if not present add
					 deviceInfo = new DeviceInformation(testCase, inputs);
						try {
							ArrayList<String> getDASSensors=deviceInfo.getDASSensorIDsInADevice();
							if(getDASSensors.size()<=0) {
								try {
								if (chUtil.getConnection()) {
									String serialNumber=java.util.UUID.randomUUID().toString();
									serialNumber=serialNumber.substring(0, serialNumber.indexOf("-")+2).replace("-",":");
									String payload="{\"config\":{\"identifiers\":{\"id\":\"Sensor Serial No\",\"serialNumber\":\"Sensor Serial No\",\"shortAddress\":\"BridgeId\",\"macAddress\":\"3234454\"},\"modelName\":\"Model name\",\"versions\":{\"hardware\":\"1.0.0\",\"swPackage\":\"1.0.0\"},\"encryptionKey\":\"000102030405060708090A0B0C0D0E0F\",\"expand\":{\"PeripheralConnectedToInterface\":[{\"identifiers\":{\"id\":\"7\"}}],\"PeripheralAssignedDevice\":[{\"identifiers\":{\"id\":\"00158D000052DC20:1\"},\"type\":\"Input\",\"_subType_id\":[\"889\",\"Contact\"],\"supervisionInterval\":\"PT30M\",\"extension\":[{\"name\":\"isomPIRConfig\",\"sensitivity\":50}]}]},\"extension\":[{\"name\":\"wiselinkPeripheralInfo\",\"RadioCycleValue\":10,\"FastCycleValue\":1,\"NonceKey\":\"000000000000000000000000000000000\",\"ClientID\":\"8787\"}]},\"state\":{\"commState\":{\"state\":[\"normal\"],\"linkQuality\":100},\"batteryState\":{\"state\":[\"normal\"],\"batteryLevel\":60}}}";
									payload= payload.replace("Sensor Serial No", serialNumber).replace("BridgeId",String.valueOf(new Random().nextInt(100)));
									if(chUtil.postSensorDiscovery(LyricUtils.locationID,deviceInfo.getDeviceID(), true)==202) {
										Keyword.ReportStep_Pass(testCase, "Sensor Discovery enabled through CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to enable Sensor through CHIL");
									}	
									
									chUtil.FeedDataIntoIOTHub(deviceInfo.getDeviceID().replace("LSC-", ""),payload,"POST ISOM/DeviceMgmt/Peripherals/fullEntity","DAS","D-Change");
								     Thread.sleep(5000);
									getDASSensors=deviceInfo.getDASSensorIDsInADevice();
									if (chUtil.postSensor(LyricUtils.locationID, deviceInfo.getDeviceID(), 1,serialNumber) == 202) {
										Keyword.ReportStep_Pass(testCase, "Sensor Created through CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to create Sensor through CHIL");
									}
									if(chUtil.postSensorDiscovery(LyricUtils.locationID,deviceInfo.getDeviceID(), false)==202) {
										Keyword.ReportStep_Pass(testCase, "Sensor Discovery disabled through CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to disable Sensor through CHIL");
									}	
									
								}
								}
								catch (Exception e) {
									// TODO Auto-generated catch block
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
								}
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							flag = false;
							e.printStackTrace();
						}
						try {
							if(!bs.clickOnBackButton()) {
								Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
										"Manage Alerts: Unable to navigate to Back Screen after sensor creation");
							}
							if(!bs.clickOnManageAlerts()) {
								Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
										"Manage Alerts: Unable to click on Manage Alerts Screen after sensor creation");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
				}
				else if(!lstData.contains("Doors and Windows")) {
					///check for sensor in chil get and delete
					 deviceInfo = new DeviceInformation(testCase, inputs);
						try {
							
							ArrayList<String> getDASSensors=deviceInfo.getDASSensorIDsInADevice();
							if(getDASSensors.size()>0) {
								for (String sensorID : getDASSensors){ 
								try {
								if (chUtil.getConnection()) {
									if (chUtil.deleteSensor(LyricUtils.locationID, deviceInfo.getDeviceID(), sensorID, 1) == 202) {
										Keyword.ReportStep_Pass(testCase, "Sensor deleted through CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to delete Sensor through CHIL");
									}
									
								}
								}
								catch (Exception e) {
									// TODO Auto-generated catch block
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
								}
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							flag = false;
							e.printStackTrace();
						}
						
						//This will execute when the requirement file does not have Doors and Windows data
							try {
								if(!bs.clickOnBackButton()) {
									Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
											"Manage Alerts: Unable to navigate to Back Screen after sensor creation");
								}
								if(!bs.clickOnManageAlerts()) {
									Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
											"Manage Alerts: Unable to click on Manage Alerts Screen after sensor creation");
								}
								if (!bs.isDoorAndWindowsToggleVisible()) {
									Keyword.ReportStep_Pass(testCase,
											"Manage Alerts: Doors and Windows is not present on the DAS Manage Alerts screen for without sensors");
								}
								else {
									Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,
											"Manage Alerts: Doors and Windows is not present on the DAS Manage Alerts screen for without sensors");
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
							}
								
						}
					
				
			
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Alerts");
				
				try {
					if (bs.isElementEnabled(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Manage Alerts: '" + fieldTobeVerified + "' is present on the DAS Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: '" + fieldTobeVerified + "' is not present on the DAS Manage Alerts screen");
					}
					
					
				
				
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		
		case "ENTRY-EXIT DELAY": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				try {
					if (bs.verifyParticularEntryExitDelayOptionVisible(data.getData(i, "Delays"))) {
						Keyword.ReportStep_Pass(testCase, "Option: '" + data.getData(i, "Delays")
								+ "' is present on the Entry/Exit Delay screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option: '"
								+ data.getData(i, "Delays") + "' is not present on the Entry/Exit Delay screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "BASE STATION CONFIGURATION": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifyDASNameOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' DAS Option is not displayed on the Base Station Configurations Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Battery")) {
					if (bs.verifyBatteryOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Battery' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery' DAS Option is not displayed on the Base Station Configurations Screen");
					}

					if (bs.verifyBatteryStatusTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Battery Status' is correctly displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery Status' is not correctly displayed");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Base Station Configurations Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}

		case "SENSOR SETTINGS": {
            BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
            SensorSettingScreen sensorSettingScreen = new SensorSettingScreen(testCase);
            for (int i = 0; i < data.getSize(); i++) {
                  if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
                         if (bs.verifySensorNameOptionTextOnSensorSettingsScreen()) {
                        Keyword.ReportStep_Pass(testCase,
                                              "'Name' Sensor Option is present on the Sensors Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Name' Sensor Option is not displayed on the Sensor Settings Screen");
                         }
                  } else if (data.getData(i, "Settings").equalsIgnoreCase("Status")) {
                         if (bs.verifyStatusOptionTextOnSensorSettingsScreen()) {
                           Keyword.ReportStep_Pass(testCase, "'Status' Option is present on the Sensors Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Status' Option is not displayed on the Sensor Settings Screen");
                         }
                         if (bs.verifySensorStatusTextOnSensorSettingsScreen()) {
                           Keyword.ReportStep_Pass(testCase, "'Sensor Status' is correctly displayed");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Sensor Status' is not correctly displayed");
                         }
                        
                  } else if (data.getData(i, "Settings").equalsIgnoreCase("Signal Strength And Test")) {
                         if (bs.verifySignalStrengthOptionTextOnSensorSettingsScreen()) {
                           Keyword.ReportStep_Pass(testCase,
                                              "'Signal Strength And Test' Option is present on the Sensors Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Signal Strength And Test' Option is not displayed on the Sensor Settings Screen");
                         }
                  }

                  else if (data.getData(i, "Settings").equalsIgnoreCase("Battery")) {
                         if (bs.verifyBatteryOptionTextOnSensorSettingsScreen()) {
                           Keyword.ReportStep_Pass(testCase, "'Battery' Option is present on the Sensor Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Battery' Option is not displayed on the Sensor Settings Screen");
                         }

                         if (sensorSettingScreen.verifyBatteryStatusTextOnSensorSettingsScreen()) {
                         Keyword.ReportStep_Pass(testCase, "'Battery Status' is correctly displayed");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Battery Status' is not correctly displayed");
                         }
                  } else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
                         if (bs.verifyModelAndFirmwareDetailsOptionTextOnSensorSettingsScreen()) {
                         Keyword.ReportStep_Pass(testCase,
                                              "'Model and Firmware Details' Option is present on the Sensor Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Model and Firmware Details' Option is not displayed on the Sensor Settings Screen");
                         }
                  }
                  else if (data.getData(i, "Settings").equalsIgnoreCase("Delete")) {
                         if (bs.verifyDeleteOptionOnSensorSettingsScreenVisible()) {
                                Keyword.ReportStep_Pass(testCase,
                                              "'Delete' Option is present on the Sensor Settings Screen");
                         } else {
                                flag = false;
                                Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                              "'Delete' Option is not displayed on the Sensor Settings Screen");
                         }
                  }
          else {
                         flag = false;
                         Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                       "Invalid Input: " + data.getData(i, "Settings"));
                  }
            }
            break;
     }

		case "KEYFOB SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifyKeyfobNameOptionTextOnKeyfobSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' Keyfob Option is present on the Keyfob Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' Keyfob Option is not displayed on the Keyfob Settings Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnKeyfobSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Keyfob Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Keyfob Settings Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}

		case "PANEL MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifyDASModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifyDASFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "KEYFOB MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifyKeyfobModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifyKeyfobFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "SENSOR MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifySensorModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifySensorFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "VIDEO SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Video Setting: '" + fieldTobeVerified + "' is present on the Video Settings screen");
					} else {
						flag = false;

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Setting: '" + fieldTobeVerified + "' is not present on the Video Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "SECURITY MODE": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "SecurityStates");
				try {
					if (sc.isSecurityStateVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Security State: '" + fieldTobeVerified
								+ "' is present on the Security Solutions Card screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security State: '"
								+ fieldTobeVerified + "' is not present on the Security Solutions Card screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}

		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
