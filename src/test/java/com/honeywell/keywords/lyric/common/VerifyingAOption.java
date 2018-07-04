package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SensorSettingScreen;


public class VerifyingAOption extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyingAOption(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"([^\"]*)\" with the \"([^\"]*)\" option$")
	public boolean keywordSteps() throws KeywordException {
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
		if(expectedScreen.get(1).toUpperCase().equals("FR")){
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD BE DISPLAYED": {
				SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
				try {
					if (scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is present on the Secondary Cards screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is not present on the Secondary Cards screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED":{
				SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
				try {
					if (!scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is not present on the Secondary Cards screen");


					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is  present on the Secondary Cards screen but should not");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				finally{
					//Update to the location where FR will permit
					FRUtils.updateLocationThroughCHIL(testCase, inputs,"{\"city\":\"Chicago Ridge\",\"state\":\"NY\",\"country\":\"US\",\"zipcode\":\"11747\"}",locationID);
				}
				break;

			}



			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
			}
			}
		}
		else if(expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN CANCEL")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if(sensor.isCancelButtonDisplayed()){
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is displayed");
				}
				else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
				}
			}
			}

		}
		else if(expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN BACK")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if(sensor.isBackButtonDisplayed()) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is displayed");
				}
				else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
				}
			}
			}

		}
		else if(expectedScreen.get(1).toUpperCase().equals("CANCEL SENSOR SETUP POPUP")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
			
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sensor.isCancelSetUpPopUpVisible()) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel sensor popup is displayed");
					return flag;

				} else {
					 Keyword.ReportStep_Pass(testCase, "Cancel Sensor popup is not displayed");
				
				}
				
				break;
			
			}
			}
			
			}else if(expectedScreen.get(1).toUpperCase().equals("DONE")){
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE DISPLAYED": {
				
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(sensor.isDoneButtonVisible()==false) {
						flag = true;
						 Keyword.ReportStep_Pass(testCase, "Done button is not displayed");
						return flag;

					} else {
						flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Done button is displayed");
					}
					
					break;
				
				}
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
