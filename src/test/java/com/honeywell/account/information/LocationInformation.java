package com.honeywell.account.information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class LocationInformation {
	private JSONObject locationInformation;
	private TestCaseInputs inputs;
	private TestCases testCase;

	public LocationInformation(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
		locationInformation = LyricUtils.getLocationInformation(this.testCase, this.inputs);
	}

	public void printLocationJSON() {
		if (locationInformation != null) {
			System.out.println(locationInformation);
		} else {
			System.out.println("Location Information is null");
		}
	}

	public long getLocationID() throws Exception {
		if (locationInformation != null) {
			return locationInformation.getLong("locationID");
		} else {
			return -1;
		}
	}
	
	public String getIANATimeZone() throws Exception {
		if (locationInformation != null) {
			return locationInformation.getString("ianaTimeZone");
		} else {
			return null;
		}
	}

	public long getUserID() throws Exception {
		if (locationInformation != null) {
			JSONArray users;
			long id = -1;
			users = locationInformation.getJSONArray("users");
			JSONObject tempObj = null;
			for (int i = 0; i < users.length(); i++) {
				tempObj = users.optJSONObject(i);
				if (tempObj.getString("username").equalsIgnoreCase(inputs.getInputValue("USERID"))) {
					id = tempObj.getLong("userID");
				}
			}
			return id;
		} else {
			return -1;
		}
	}
	
	public String getUserFirstName(){
		String firstName = null;
		if (locationInformation != null) {
			JSONArray users;

			try {
				users = locationInformation.getJSONArray("users");
				JSONObject tempObj = null;
				for (int i = 0; i < users.length(); i++) {
					tempObj = users.optJSONObject(i);
					if (tempObj.getString("username").equalsIgnoreCase(inputs.getInputValue("USERID"))) {
						firstName = tempObj.getString("firstname");
					}
				}
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
			return firstName;
		} else {
			return firstName;
		}
	}
	
	public String getDASDeviceID() {
		String deviceID="";
		if (locationInformation != null) {
			try {
				JSONArray devices = locationInformation.getJSONArray("devices");
				for(int i=0;i<devices.length();i++)
				{
					if(devices.optJSONObject(i).getString("deviceType").equals("HONDAS"))
					{
						deviceID = devices.optJSONObject(i).getString("deviceID");
						break;
					}
				}
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Device Schedule Type : Not Connected to CHAPI. Returning \"\" value");
		}

		return deviceID;
	}
	
	public int getNumberOfDeviceInLocation()
	{
		int numberOfDevices=-1;
		if (locationInformation != null) {
			try {
				JSONArray devices = locationInformation.getJSONArray("devices");
				numberOfDevices = devices.length();
			}
			catch(JSONException e)
			{
				numberOfDevices=0;
			}
			catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Device Schedule Type : Not Connected to CHAPI. Returning \"\" value");
		}
		return numberOfDevices;
	}
	
	public int getDeviceCountOfLocation() {
		int deviceCount = 0;
		if (locationInformation != null) {
			try {
				deviceCount = locationInformation.getJSONArray("devices").length();
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Device Schedule Type : Not Connected to CHAPI. Returning \"\" value");
		}

		return deviceCount;
	}

}
