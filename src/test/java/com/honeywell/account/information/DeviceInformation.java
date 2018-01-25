package com.honeywell.account.information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.JasperSchedulingUtils;
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

	public void printStatJSON() throws Exception {
		if (deviceInformation != null) {
			System.out.println(deviceInformation);
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public Boolean isOnline() throws Exception {
		if (deviceInformation != null) {
			return (Boolean) deviceInformation.getJSONObject("deviceDetails").getJSONArray("onboardDevices")
					.getJSONObject(0).get("isAlive");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public String getDeviceID() throws Exception {
		if (deviceInformation != null) {
			return deviceInformation.getString("deviceID");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public String getJasperDeviceType() throws Exception {
		String type = "";
		if (deviceInformation != null) {
			String temp;
			temp = deviceInformation.get("allowedTimeIncrements").toString();
			if (temp.equals("10")) {
				type = "EMEA";
			} else if (temp.equals("15")) {
				type = "NA";
			}
		} else {
			throw new Exception("Device Information not found");
		}
		return type;
	}

	public String getThermostatUnits() throws Exception {
		String units = " ";
		if (deviceInformation != null) {
			units = deviceInformation.getJSONObject("thermostat").getString("units");
		} else {
			throw new Exception("Device Information not found");
		}
		return units;
	}

	public String getThermoStatScheduleType() throws Exception {
		String systemMode = "";
		if (deviceInformation != null) {
			systemMode = deviceInformation.getJSONObject("scheduleType").getString("scheduleType");
		} else {
			throw new Exception("Device Information not found");
		}
		return systemMode;
	}

	public List<String> getAllowedModes() throws Exception {
		List<String> allowedModes = new ArrayList<String>();
		if (deviceInformation != null) {
			JSONArray temp = new JSONArray();
			temp = deviceInformation.getJSONObject("thermostat").getJSONArray("allowedModes");
			for (int i = 0; i < temp.length(); i++) {
				allowedModes.add(temp.getString(i));
			}
		} else {
			throw new Exception("Device Information not found");
		}
		return allowedModes;
	}

	public HashMap<String, String> getDeviceMaxMinSetPoints() throws Exception {
		HashMap<String, String> setPoints = new HashMap<String, String>();
		if (deviceInformation != null) {
			List<String> allowedModes = getAllowedModes();
			if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				String temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("maxHeatSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MaxHeat", temp);
				temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("maxCoolSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MaxCool", temp);
				temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("minHeatSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MinHeat", temp);
				temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("minCoolSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MinCool", temp);
			} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
				String temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("maxCoolSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MaxCool", temp);
				temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("minCoolSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MinCool", temp);
			} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				String temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("maxHeatSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MaxHeat", temp);
				temp = String.valueOf(deviceInformation.getJSONObject("thermostat").get("minHeatSetpoint"));
				if (getThermostatUnits().equals("Celsius")) {
					temp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
							JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, temp));
				}
				setPoints.put("MinHeat", temp);
			}
			return setPoints;
		} else {
			throw new Exception("Device Information not found");
		}
	}

}
