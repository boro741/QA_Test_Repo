package com.honeywell.account.information;

import org.json.JSONObject;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.LyricUtils;

public class DeviceInformation {

	private JSONObject deviceInformation;
	private TestCases testCase;
	// private TestCaseInputs inputs;

	public DeviceInformation(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		// this.inputs=inputs;
		deviceInformation = LyricUtils.getDeviceInformation(this.testCase, inputs);
	}

	public void printStatJSON() {
		if (deviceInformation != null) {
			System.out.println(deviceInformation);
		} else {
			System.out.println("Device information is null");
		}
	}
	
	public Boolean isOnline(){
		if (deviceInformation != null) {
			return  (Boolean) deviceInformation.getJSONObject("deviceDetails").getJSONArray("onboardDevices").getJSONObject(0).get("isAlive");
		} else {
			return false;
		}
	}
	public String getDeviceID() {
		if (deviceInformation != null) {
			return deviceInformation.getString("deviceID");
		} else {
			return " ";
		}
	}
}
