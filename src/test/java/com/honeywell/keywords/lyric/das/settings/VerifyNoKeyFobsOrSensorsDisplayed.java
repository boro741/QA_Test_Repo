package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyNoKeyFobsOrSensorsDisplayed extends Keyword {

	private TestCases testCase;
	 private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyNoKeyFobsOrSensorsDisplayed(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user should not be displayed with \"(.*)\" on the \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		CHILUtil chUtil = null;
		DeviceInformation deviceInfo=null;
		try {
			chUtil = new CHILUtil(inputs);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			flag = false;
			 Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured-"+e1.getMessage());
			e1.printStackTrace();
		}
		try {
			
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			deviceInfo = new DeviceInformation(testCase, inputs);
			if (parameters.get(0).equalsIgnoreCase("keyfobs")
					&& parameters.get(1).equalsIgnoreCase("keyfob")) {
				
				try {
					ArrayList<String> getDASKeyFobs=deviceInfo.getDASKeyFobsIDInADevice();
					if(getDASKeyFobs.size()>0) {
						//Delete the keyfob if present through CHIL
						for (String keyfobID : getDASKeyFobs){ 
						if (chUtil.getConnection()) {
							if (chUtil.deleteKeyfob(LyricUtils.locationID, deviceInfo.getDeviceID(), keyfobID, 1) == 202) {
								Keyword.ReportStep_Pass(testCase, "keyfob deleted through CHIL");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to delete keyfob through CHIL");
							}
					}
						}
				}
				}
				 catch (Exception e1) {
						// TODO Auto-generated catch block
					 flag = false;
					 Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured-"+e1.getMessage());
						e1.printStackTrace();
					}
//				if(!bs.clickOnBackButton()) {
//					flag=false;
//					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to move to back button after keyfob deletion");
//				}
				if(bs.isNoKeyFobTextVisible())
				{
					Keyword.ReportStep_Pass(testCase, "No Keyfob text is displayed");
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Keyfob text is not displayed");
				}
			}
			else if (parameters.get(0).equalsIgnoreCase("sensors")
					&& parameters.get(1).equalsIgnoreCase("sensors")) {
				try {
					ArrayList<String> getDASSensors=deviceInfo.getDASSensorIDsInADevice();
					if(getDASSensors.size()>0) {
						//Delete the keyfob if present through CHIL
						for (String sensorID : getDASSensors){ 
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
				}
				}
				 catch (Exception e1) {
						// TODO Auto-generated catch block
					 flag = false;
					 Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured-"+e1.getMessage());
					}
				if(bs.isNoSensorTextVisible(15))
				{
					Keyword.ReportStep_Pass(testCase, "No Sensors text is displayed");
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Sensors text is not displayed");
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
