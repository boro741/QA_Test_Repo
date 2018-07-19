package com.honeywell.keywords.lyric.chil;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.python.antlr.ast.keyword;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.GeofenceSettings;

public class UserArrivalAndDepartedWithinGeoRadius  extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> parameters;
	public boolean flag = true;
	
	public UserArrivalAndDepartedWithinGeoRadius(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}
	
	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user cross the geofence (.*) to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			CHILUtil chUtil = new CHILUtil(inputs);
			
			JSONObject jObj = LyricUtils.getLocationInformation(testCase, inputs);
			JSONArray jArrayGeof = (JSONArray) jObj.get("geoFences");
			int geofenceID = jArrayGeof.getJSONObject(0).getInt("geoFenceID");
			
			
			if (parameters.get(0).equalsIgnoreCase("home")) {				
				
				if (chUtil.getConnection()) {
					int result = chUtil.userArrived(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),chUtil.getUserID(),geofenceID);
					
					Thread.sleep(3000);
					
					result = chUtil.userDeparted(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),chUtil.getUserID(),geofenceID);
					Thread.sleep(2000);
					if (result == HttpURLConnection.HTTP_OK) {
						Keyword.ReportStep_Pass(testCase, "Successfully changed geofence status to AWAY");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change geofence status");
					}
				} else {
					flag = false;
					throw new Exception("Failed to connect to CHIL");
				}
			}
			else if(parameters.get(0).equalsIgnoreCase("away")) {
					
				
					if (chUtil.getConnection()) {
						int result = chUtil.userDeparted(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),chUtil.getUserID(),geofenceID);
						Thread.sleep(10000);
					result = chUtil.userArrived(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),chUtil.getUserID(),geofenceID);
					if (result == HttpURLConnection.HTTP_OK) {
						Keyword.ReportStep_Pass(testCase, "Successfully changed geofence status to HOME");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change geofence status");
					}
				} else {
					flag = false;
					throw new Exception("Failed to connect to CHIL");
				}
				
			}else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + parameters.get(0));
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
