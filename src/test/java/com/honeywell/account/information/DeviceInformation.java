package com.honeywell.account.information;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class DeviceInformation {

	private JSONObject deviceInformation;
	private TestCases testCase;
	private TestCaseInputs inputs;
	String locationName;
	String statName;

	public DeviceInformation(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
		deviceInformation = LyricUtils.getDeviceInformation(testCase, inputs);
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
			return (Boolean) deviceInformation.get("isAlive");
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

	public String getVacationHeatSetPoint() {
		System.out.println(deviceInformation.getJSONObject("vacationHold").get("heatSetpoint"));
		return String.valueOf(deviceInformation.getJSONObject("vacationHold").get("heatSetpoint"));
	}

	public int getSensorGroupID() throws Exception {
		int groupid = 0;
		if (deviceInformation != null) {
			groupid = deviceInformation.getJSONArray("groups").getJSONObject(0).getInt("id");
		} else {
			throw new Exception("Device Information not found");
		}
		return groupid;
	}

	public List<String> getSensorDeviceID(JSONObject SensorListJson) throws Exception {
		List<String> sensorID = new ArrayList<String>();
		if (deviceInformation != null) {
			JSONArray temp = new JSONArray();
			temp = SensorListJson.getJSONArray("rooms");
			for (int i = 0; i < temp.length()-1;i++) {
				sensorID.add(temp.getJSONObject(i+1).get("name").toString());
			}
		} else {
			throw new Exception("Device Information not found");
		}
		return sensorID;
	}

	public List<String> getSensorRoomType(JSONObject SensorListJson) throws Exception {
		List<String> sensorID = new ArrayList<String>();
		if (deviceInformation != null) {
			JSONArray temp = new JSONArray();
			temp = SensorListJson.getJSONArray("rooms");
			for (int i = 0; i < temp.length();i++) {
				sensorID.add(temp.getJSONObject(i).get("type").toString());
			}
		} else {
			throw new Exception("Device Information not found");
		}
		return sensorID;
	}

	public String getVacationCoolSetPoint() {
		return String.valueOf(deviceInformation.getJSONObject("vacationHold").get("coolSetpoint"));
	}

	public Boolean SyncDeviceInfo(TestCases testCase, TestCaseInputs inputs) {
		deviceInformation = LyricUtils.getDeviceInformation(testCase, inputs);
		return true;
	}

	public String getZwaveDeviceID(String name) throws Exception {
		String sDimmerDeviceID = "";
		if (deviceInformation != null) {
			for (int i = 0; i < deviceInformation.getJSONObject("deviceDetails").getJSONObject("automationDevices")
					.getJSONArray("switches").length(); i++) {
				System.out.println(deviceInformation.getJSONObject("deviceDetails").getJSONObject("automationDevices")
						.getJSONArray("switches").getJSONObject(i).get("name"));
				if (deviceInformation.getJSONObject("deviceDetails").getJSONObject("automationDevices")
						.getJSONArray("switches").getJSONObject(i).get("name").toString().equals(name)) {
					return deviceInformation.getJSONObject("deviceDetails").getJSONObject("automationDevices")
							.getJSONArray("switches").getJSONObject(i).get("id").toString();
				}
			}
		} else {
			throw new Exception("Device Information not found");
		}
		return sDimmerDeviceID;
	}

	public String getJasperDeviceType() {
		String type = " ";
		if (deviceInformation != null) {
			try {
				String temp, deviceType;
				deviceType = deviceInformation.get("deviceType").toString();
				if(deviceType.equalsIgnoreCase("Jasper")){
					temp = deviceInformation.get("allowedTimeIncrements").toString();
					if (temp.equals("10")) {
						type = "EMEA";
					} else if (temp.equals("15")) {
						type = "NA";
					}	
				} else if(deviceType.equalsIgnoreCase("FlyCatcher")){
					type = "FlyCatcher";
				
				}else if(deviceType.equalsIgnoreCase("HoneyBadger")){
					type = "HoneyBadger";
				}

			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Jasper Device Type : Not Connected to CHAPI. Returning \"\" value");
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

	public String getThermoStatVentilationMode() throws Exception {
		String VentilationMode = "";
		if (deviceInformation != null) {
			VentilationMode = deviceInformation.getJSONObject("settings").getJSONObject("ventilationModeSettings")
					.getString("changeableValues");
		} else {
			throw new Exception("Device Information not found");
		}
		return VentilationMode;
	}

	public String getThermoStatHumidificationSettings() throws Exception {
		String HumidificationMode = "";
		if (deviceInformation != null) {
			HumidificationMode = deviceInformation.getJSONObject("settings").getJSONObject("humidifierSettings")
					.getJSONObject("changeableValues").getString("mode");
		} else {
			throw new Exception("Device Information not found");
		}
		return HumidificationMode;
	}

	public String getThermoStatDeHumidificationSettings() throws Exception {
		String DeHumidificationMode = "";
		if (deviceInformation != null) {
			DeHumidificationMode = deviceInformation.getJSONObject("settings").getJSONObject("dehumidifierSettings")
					.getJSONObject("changeableValues").getString("mode");
		} else {
			throw new Exception("Device Information not found");
		}
		return DeHumidificationMode;
	}

	public int getWindowProtection() throws Exception {
		int WindowProtection = 0;
		if (deviceInformation != null) {
			WindowProtection = deviceInformation.getJSONObject("settings").getJSONObject("humidifierSettings")
					.getInt("frostIndex");
		} else {
			throw new Exception("Device Information not found");
		}
		return WindowProtection;
	}

	public int getHumidifierValue() throws Exception {
		int HumidifierValue = 0;
		if (deviceInformation != null) {
			HumidifierValue = deviceInformation.getJSONObject("settings").getJSONObject("humidifierSettings")
					.getJSONObject("changeableValues").getInt("setpoint");
		} else {
			throw new Exception("Device Information not found");
		}
		return HumidifierValue;
	}
	public int getDeHumidifierValue() throws Exception {
		int DeHumidifierValue = 0;
		if (deviceInformation != null) {
			DeHumidifierValue = deviceInformation.getJSONObject("settings").getJSONObject("dehumidifierSettings")
					.getJSONObject("changeableValues").getInt("setpoint");
		} else {
			throw new Exception("Device Information not found");
		}
		return DeHumidifierValue;
	}

	public int getVentilationTimerValue() throws Exception {
		int VentilationTimerValue = 0;
		if (deviceInformation != null) {
			VentilationTimerValue = deviceInformation.getJSONObject("settings").getJSONObject("ventilationModeSettings")
					.getInt("ventilationBoostTimer");
		} else {
			throw new Exception("Device Information not found");
		}
		return VentilationTimerValue;
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
		}
		return setPoints;
	}

	public String getDASSensorID(String sensorName) throws Exception {
		if (deviceInformation != null) {
			JSONArray sensors = deviceInformation.getJSONObject("deviceDetails").getJSONArray("sensors");
			for (int i = 0; i < sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				if (sensor.getString("name").equals(sensorName)) {
					return sensor.getString("id");
				}
			}
			throw new Exception("Sensor Name: '" + sensorName + "' not found in configured sensors");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public ArrayList<String> getDASSensorIDsInADevice() throws Exception {
		ArrayList<String> lstSensorID = new ArrayList<String>();
		if (deviceInformation != null) {
			JSONArray sensors = deviceInformation.getJSONObject("deviceDetails").getJSONArray("sensors");
			for (int i = 0; i < sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				if (!sensor.isNull("id"))
					lstSensorID.add(sensor.getString("id").toString());

			}
			return lstSensorID;

		} else {
			throw new Exception("getDASSensorIDsInADevice:Sensor Information not found");
		}
	}

	public ArrayList<String> getDASKeyFobsIDInADevice() throws Exception {
		ArrayList<String> lstKeyFobID = new ArrayList<String>();
		if (deviceInformation != null) {
			JSONArray keyfobs = deviceInformation.getJSONObject("deviceDetails").getJSONArray("keyFobs");
			for (int i = 0; i < keyfobs.length(); i++) {
				JSONObject keyfob = keyfobs.getJSONObject(i);
				if (!keyfob.isNull("id"))
					lstKeyFobID.add(keyfob.getString("id").toString());

			}
			return lstKeyFobID;

		} else {
			throw new Exception("getDASKeyFobsInADevice:KeyFob Information not found");
		}
	}

	public String getDASKeyfobID(String keyfobName) throws Exception {
		if (deviceInformation != null) {
			JSONArray keyfobs = deviceInformation.getJSONObject("deviceDetails").getJSONArray("keyFobs");
			for (int i = 0; i < keyfobs.length(); i++) {
				JSONObject keyfob = keyfobs.getJSONObject(i);
				if (keyfob.getString("name").equals(keyfobName)) {
					return keyfob.getString("id");
				}
			}
			throw new Exception("Keyfob Name: '" + keyfobName + "' not found in configured sensors");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public String getDASSensorResponseType(String sensorName) throws Exception {
		if (deviceInformation != null) {
			JSONArray sensors = deviceInformation.getJSONObject("deviceDetails").getJSONArray("sensors");
			for (int i = 0; i < sensors.length(); i++) {
				JSONObject sensor = sensors.getJSONObject(i);
				if (sensor.getString("name").equals(sensorName)) {
					return sensor.getJSONObject("configurations").getString("responseType");
				}
			}
			throw new Exception("Sensor Name: " + sensorName + " not found in configured sensors");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public String getDASCameraID() throws Exception {
		if (deviceInformation != null) {
			JSONArray onBoardDevices = deviceInformation.getJSONObject("deviceDetails").getJSONArray("onboardDevices");
			for (int i = 0; i < onBoardDevices.length(); i++) {
				JSONObject onBoardDevice = onBoardDevices.getJSONObject(i);
				if (onBoardDevice.getString("deviceClass").equals("Camera")) {
					return onBoardDevice.getString("deviceID");
				}
			}
			throw new Exception("No Onboard devices found");
		} else {
			throw new Exception("Device Information not found");
		}
	}

	public String getThermostatType() {
		String type = "";
		if (deviceInformation != null) {
			try {
				type = deviceInformation.get("deviceType").toString();
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		}
		return type;
	}

	public String getThermoStatMode() {
		String systemMode = "";
		if (deviceInformation != null) {
			try {
				systemMode = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.getString("mode");
			} catch (Exception e) {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
						+ " at location - " + locationName + " : Error occured - " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get System Mode : Not Connected to CHAPI. Returning \"\" value");
			return "";
		}
		return systemMode;
	}

	public String getThermostatModeWhenAutoChangeOverActive() {
		String systemMode = "";
		if (deviceInformation != null) {
			try {
				systemMode = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.getString("heatCoolMode");
			} catch (Exception e) {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
						+ " at location - " + locationName + " : Error occured - " + e.getMessage());
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get System Mode : Not Connected to CHAPI. Returning \"\" value");
			return "";
		}
		return systemMode;
	}

	public String getCoolSetPoints() {
		String coolSetPoints = " ";
		try {
			if (deviceInformation != null) {

				coolSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.get("coolSetpoint").toString();

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Heat Set Points : Not Connected to CHAPI. Returning \"\" value");
				return "";
			}
			if (getThermostatUnits().equals("Celsius")) {
				coolSetPoints = JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, coolSetPoints);
			} else if (getThermostatUnits().equals("Fahrenheit")) {
				Double temp = Double.parseDouble(coolSetPoints);
				coolSetPoints = String.valueOf(temp.intValue());
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
					+ " at location - " + locationName + " : Error occured - " + e.getMessage());
		}
		return coolSetPoints;
	}

	public String getIndoorTemperature() {
		String indoorTemp = " ";
		try {
			if (deviceInformation != null) {

				indoorTemp = deviceInformation.getJSONObject("thermostat").get("indoorTemperature").toString();

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Indoor Temperature : Not Connected to CHAPI. Returning \"\" value");
				return "";
			}
			if (getThermostatUnits().equals("Celsius")) {
				indoorTemp = JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, indoorTemp);
			} else if (getThermostatUnits().equals("Fahrenheit")) {
				Double temp = Double.parseDouble(indoorTemp);
				indoorTemp = String.valueOf(temp.intValue());
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
					+ " at location - " + locationName + " : Error occured - " + e.getMessage());
		}
		return indoorTemp;
	}

	public String getCurrentSetPoints() {
		String currentSetPoints = " ";
		try {
			if (deviceInformation != null) {

				if (getThermoStatMode().equals("Heat")) {
					currentSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
							.get("heatSetpoint").toString();
				} else if (getThermoStatMode().equals("Cool")) {
					currentSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
							.get("coolSetpoint").toString();
				} else if (getThermoStatMode().equals("Auto")) {
					if (getThermostatModeWhenAutoChangeOverActive().equals("Heat")) {
						currentSetPoints = deviceInformation.getJSONObject("thermostat")
								.getJSONObject("changeableValues").get("heatSetpoint").toString();
					} else if (getThermostatModeWhenAutoChangeOverActive().equals("Cool")) {
						currentSetPoints = deviceInformation.getJSONObject("thermostat")
								.getJSONObject("changeableValues").get("coolSetpoint").toString();
					}
				}

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Current Set Points : Not Connected to CHAPI. Returning \"\" value");
				return "";
			}
			Keyword.ReportStep_Pass(testCase, currentSetPoints);
			if (deviceInformation.getJSONObject("thermostat").getString("units").equals("Celsius")) {
				currentSetPoints = JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, currentSetPoints);
			} else if (deviceInformation.getJSONObject("thermostat").getString("units").equals("Fahrenheit")) {
				Double temp = Double.parseDouble(currentSetPoints);
				currentSetPoints = String.valueOf(temp.intValue());
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
					+ " at location - " + locationName + " : Error occured - " + e.getMessage());
		}
		return currentSetPoints;
	}

	public String getHeatSetPoints() {
		String heatSetPoints = " ";
		try {

			if (deviceInformation != null) {

				heatSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.get("heatSetpoint").toString();

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Heat Set Points : Not Connected to CHAPI. Returning \"\" value");
				return "";
			}
			if (getThermostatUnits().equals("Celsius")) {
				heatSetPoints = JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, heatSetPoints);
			} else if (getThermostatUnits().equals("Fahrenheit")) {
				Double temp = Double.parseDouble(heatSetPoints);
				heatSetPoints = String.valueOf(temp.intValue());
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get Current System mode for Stat - " + statName
					+ " at location - " + locationName + " : Error occured - " + e.getMessage());
		}
		return heatSetPoints;
	}

	public String getCurrentSchedulePeriod() {
		String schedulePeriod = "";
		if (deviceInformation != null) {
			schedulePeriod = deviceInformation.getJSONObject("currentSchedulePeriod").get("period").toString();
			Keyword.ReportStep_Pass(testCase, "Stat is in period - "+schedulePeriod);
			if (schedulePeriod.equalsIgnoreCase("P1") || schedulePeriod.equalsIgnoreCase("P3")) {
				schedulePeriod = "Home";
			} else if (schedulePeriod.equalsIgnoreCase("P2")) {
				schedulePeriod = "Away";
			} else if (schedulePeriod.equalsIgnoreCase("P4")) {
				schedulePeriod = "Sleep";
			}
			return schedulePeriod;
		} else {
			schedulePeriod = "";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Information is null");
		}
		return schedulePeriod;
	}

	public String getThermostatSetPointsStatus() {
		String status = "";
		if (getThermostatType().equalsIgnoreCase("Jasper")) {
			if (deviceInformation != null) {
				status = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.get("thermostatSetpointStatus").toString();
				return status;
			} else {
				status = "";
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Stat Information is null");
			}
		}
		return status;
	}

	public String getNextPeriodTime() {
		String nextPeriodTime = "";
		if (deviceInformation != null) {
			nextPeriodTime = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
					.get("nextPeriodTime").toString();

		} else {
			nextPeriodTime = "";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Information is null");
		}
		return nextPeriodTime;
	}

	public String getOverrrideSetpoint() {
		String OverrideMode = "";
		String OverrideSet = "";

		if (deviceInformation != null) {

			OverrideMode = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues").get("mode")
					.toString();

			if (OverrideMode.equals("Heat")) {
				OverrideSet = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
						.get("heatSetpoint").toString();
			} else
				OverrideSet = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
				.get("coolSetpoint").toString();
		}
		return OverrideSet;
	}

	public String getHoldUntilTime() {
		String holdUntil = "";
		if (deviceInformation != null) {
			holdUntil = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues").get("holdUntil")
					.toString();
			return holdUntil;
		} else {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Information is null");
		}
		return holdUntil;
	}

	public String getVacationStartTime() {
		String startTime = "";
		SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if (deviceInformation != null) {
			try {
				startTime = deviceInformation.getJSONObject("vacationHold").getString("vacationStart");
				Date date = vacationDateFormat.parse(startTime);
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				c1.set(Calendar.SECOND, 0);
				startTime = vacationDateFormat.format(c1.getTime());
			} catch (Exception e) {
				startTime = " ";
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		}
		return startTime;
	}

	public String getVacationStartDateTime() {
		String startTime = "";
		//SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if (deviceInformation != null) {
			try {
				startTime = deviceInformation.getJSONObject("vacationHold").getString("vacationStart");

			} catch (Exception e) {
				startTime = " ";
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		}
		return startTime;
	}

	public boolean isVacationRunning() {
		boolean status;
		if (deviceInformation != null) {
			if (getThermostatSetPointsStatus().equalsIgnoreCase("VacationHold")) {
				status = true;
			} else {
				status = false;
			}
			return status;
		} else {
			status = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"StatInformation is null");
		}
		return status;
	}

	public String getVacationEndTime() {
		String endTime = "";
		SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if (deviceInformation != null) {
			try {
				endTime = deviceInformation.getJSONObject("vacationHold").getString("vacationEnd");
				Date date = vacationDateFormat.parse(endTime);
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date);
				c1.set(Calendar.SECOND, 0);
				endTime = vacationDateFormat.format(c1.getTime());
			} catch (Exception e) {
				endTime = " ";
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		}
		return endTime;
	}

	public String getVacationEndDateTime() {
		String endTime = "";
		//SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if (deviceInformation != null) {
			try {
				endTime = deviceInformation.getJSONObject("vacationHold").getString("vacationEnd");

			} catch (Exception e) {
				endTime = " ";
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
		}
		return endTime;
	}

	public String getscheduleStatus() throws Exception {
		String status = " ";
		if (deviceInformation != null) {
			status = deviceInformation.getString("scheduleStatus");
		} else {
			throw new Exception("Device Information not found");
		}
		return status;

	}

	public int getDREventID() throws Exception {
		int eventID = -1;
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					eventID = dr.getInt("eventID");
				} catch (JSONException e) {
					eventID = -1;
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return eventID;
	}

	public boolean isDREventStarted() throws Exception {
		boolean isStarted = false;
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					if (dr != null) {
						isStarted = true;
					}
				} catch (JSONException e) {
					isStarted = false;
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return isStarted;
	}

	public String getDeviceMacID() throws Exception {
		try {
			if (deviceInformation != null) {
				return deviceInformation.getString("macID");
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String getDREndTime() throws Exception {
		String DREndTime = "";
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					DREndTime = dr.getString("endTime");
				} catch (JSONException e) {
					throw new Exception("DR Event has not started");
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return DREndTime;
	}

	public boolean isDutyCycleEnabled() throws Exception {
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					JSONArray intervals = dr.getJSONArray("intervals");
					JSONObject temp = intervals.getJSONObject(0);
					return temp.getBoolean("dutyCycleEnabled");
				} catch (JSONException e) {
					throw new Exception(e.getMessage());
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public String getDRCoolSetPointLimit() throws Exception {
		String setPointLimit;
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					setPointLimit = dr.get("coolSetpointLimitMin").toString();
				} catch (JSONException e) {
					throw new Exception(e.getMessage());
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return setPointLimit;
	}

	public String getDRHeatSetPointLimit() throws Exception {
		String setPointLimit;
		try {
			if (deviceInformation != null) {
				try {
					JSONObject dr = deviceInformation.getJSONObject("drEvent");
					setPointLimit = dr.get("heatSetpointLimitMax").toString();
				} catch (JSONException e) {
					throw new Exception(e.getMessage());
				}
			} else {
				throw new Exception("Thermostat Information not found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return setPointLimit;
	}
}
