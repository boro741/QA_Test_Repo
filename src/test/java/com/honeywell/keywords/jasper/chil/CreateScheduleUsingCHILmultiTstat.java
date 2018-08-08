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

public class CreateScheduleUsingCHILmultiTstat extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public CreateScheduleUsingCHILmultiTstat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user thermostat2 is set to \"(.+)\" stats$")
	public boolean keywordSteps() throws KeywordException {
		try {
			
			String Location1_device1_name = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
			inputs.setInputValue("LOCATION1_DEVICE1_NAME",inputs.getInputValue("LOCATION1_DEVICE2_NAME"));
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation statInfo = new LocationInformation(testCase, inputs);
			long locationID = statInfo.getLocationID();
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			String deviceID = devInfo.getDeviceID();
			String jasperStatType = devInfo.getJasperDeviceType();
			if (exampleData.get(0).equalsIgnoreCase("no")) {
				try {
					if (chUtil.getConnection()) {
						if (chUtil.deleteSchedule(chUtil.getLocationID(inputs.getInputValue("LOCATION1_DEVICE2_NAME")),
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
						if (chUtil.createSchedule(inputs, "Geofence",locationID, deviceID,
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
			inputs.setInputValue("LOCATION1_DEVICE1_NAME",Location1_device1_name);
			System.out.println(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
