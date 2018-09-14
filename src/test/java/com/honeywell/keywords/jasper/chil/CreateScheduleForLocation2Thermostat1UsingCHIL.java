package com.honeywell.keywords.jasper.chil;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;

public class CreateScheduleForLocation2Thermostat1UsingCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public CreateScheduleForLocation2Thermostat1UsingCHIL(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user thermostat1 is set to \"(.+)\" schedule$")
	public boolean keywordSteps() throws KeywordException {
		String Location1_name = inputs.getInputValue("LOCATION1_NAME");
		inputs.setInputValue("LOCATION1_NAME",inputs.getInputValue("LOCATION2_NAME"));
		String Location1_device1_name = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		inputs.setInputValue("LOCATION1_DEVICE1_NAME",inputs.getInputValue("LOCATION2_DEVICE1_NAME"));
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation statInfo = new LocationInformation(testCase, inputs);
			long locationID = statInfo.getLocationID();
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			String deviceID = devInfo.getDeviceID();
			String TPVstatus = devInfo.getThermostatSetPointsStatus();
			String jasperStatType = devInfo.getJasperDeviceType();

			/**Pre-Condition : if schedule override with Temporary or permanent or vacation hold  and schedule off**/
			if(!TPVstatus.equalsIgnoreCase("NoHold") || !devInfo.getscheduleStatus().equalsIgnoreCase("Resume"))
			{
				try {
					if (chUtil.getConnection()) {
						if(TPVstatus.equalsIgnoreCase("HoldUntil") || TPVstatus.equalsIgnoreCase("PermanentHold") || TPVstatus.equalsIgnoreCase("TemporaryHold"))
							if (chUtil.setResumeAdhocstatus(locationID,deviceID, testCase) == 200) {
								Keyword.ReportStep_Pass(testCase,
										"Resume override schedule Using CHIL : Successfully changed to "+ TPVstatus +" through CHIL");
							} else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Resume override schedule Using CHIL : Failed to change "+ TPVstatus +" through CHIL");
							}else 
								if (TPVstatus.equalsIgnoreCase("VacationHold")) {
									if (chUtil.disableVacation(locationID, deviceID) == 200) {
										Keyword.ReportStep_Pass(testCase,
												"Activate Vacation Using CHIL : Successfully disabled vacation using CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Activate Vacation Using CHIL : Failed to disable vacation using CHIL");
									}
								}
					}
					flag = flag & devInfo.SyncDeviceInfo(testCase, inputs);
					String Schedulestatus = devInfo.getscheduleStatus();
					if(Schedulestatus.equalsIgnoreCase("Pause"))
					{
						if (chUtil.getConnection()) {
							if (chUtil.changeScheduleStatus(locationID, deviceID, "Resume") == 200) {
								Keyword.ReportStep_Pass(testCase,
										"Schedule is Resumed in CHIL before creating new schedule");
							} else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Schedule is Not Resumed in CHIL before creating new schedule");
							}
						}


					}
				}catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured : " + e.getMessage());
				}
			}
			if (exampleData.get(0).equalsIgnoreCase("no")) {
				try {
					if (chUtil.getConnection()) {
						if (chUtil.deleteSchedule(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
								deviceID) == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule Using CHIL : Successfully deleted schedule through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule Using CHIL : Failed to delete schedule using CHIL");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured : " + e.getMessage());
				}
			} else if (exampleData.get(0).equalsIgnoreCase("time based")) {
				try {

					if (chUtil.getConnection()) {
						if (chUtil.createSchedule(inputs, "Time", chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID,
								jasperStatType) == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule Using CHIL : Successfully created time schedule through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule Using CHIL : Failed to create time schedule using CHIL");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured : " + e.getMessage());
				}
			} else if (exampleData.get(0).equalsIgnoreCase("geofence based")) {
				try {
					if (chUtil.getConnection()) {
						if (chUtil.createSchedule(inputs, "Geofence",
								chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID,
								jasperStatType) == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule Using CHIL : Successfully created geofence schedule through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule Using CHIL : Failed to create geofence schedule using CHIL");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured : " + e.getMessage());
				}
			}else if (exampleData.get(0).equalsIgnoreCase("Without sleep geofence based")) {
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER,"No");
				try {
					if (chUtil.getConnection()) {
						if (chUtil.createSchedule(inputs, "Geofence",
								chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID,
								jasperStatType) == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule Using CHIL : Successfully created geofence schedule with out sleep through CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule Using CHIL : Failed to create geofence schedule using CHIL");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured : " + e.getMessage());
				}
			}else if (exampleData.get(0).equalsIgnoreCase("pause"))
			{
				if (chUtil.getConnection()) {
					if (chUtil.changeScheduleStatus(locationID, deviceID, "Pause") == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Schedule is Paused ");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule is Not Paused");
					}
				}
			}else if (exampleData.get(0).equalsIgnoreCase("resume"))
			{
				if (chUtil.getConnection()) {
					if (chUtil.changeScheduleStatus(locationID, deviceID, "Resume") == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Schedule is Resumed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule is Not Resumed");
					}

				}

			}
			else
			{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + exampleData.get(0));
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		inputs.setInputValue("LOCATION1_NAME",Location1_name);
		System.out.println(inputs.getInputValue("LOCATION1_NAME"));
		inputs.setInputValue("LOCATION1_DEVICE1_NAME",Location1_device1_name);
		System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
