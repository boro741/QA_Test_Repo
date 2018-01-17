package com.honeywell.account.information;

import org.json.JSONObject;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
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
	public long getLocationID() {
		if (locationInformation != null) {
			return locationInformation.getLong("locationID");
		} else {
			return -1;
		}
	}

}
