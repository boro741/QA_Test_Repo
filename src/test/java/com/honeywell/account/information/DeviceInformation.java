package com.honeywell.account.information;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	String locationName;
	String statName;

	// private TestCaseInputs inputs;

	public DeviceInformation(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		// this.inputs=inputs;
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
				String temp;
				temp = deviceInformation.get("allowedTimeIncrements").toString();
				if (temp.equals("10")) {
					type = "EMEA";
				} else if (temp.equals("15")) {
					type = "NA";
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
		return type ;
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
						currentSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
								.get("heatSetpoint").toString();
					} else if (getThermostatModeWhenAutoChangeOverActive().equals("Cool")) {
						currentSetPoints = deviceInformation.getJSONObject("thermostat").getJSONObject("changeableValues")
								.get("coolSetpoint").toString();
					}
				}

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Current Set Points : Not Connected to CHAPI. Returning \"\" value");
				return "";
			}
			if (getThermostatUnits().equals("Celsius")) {
				currentSetPoints = JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, currentSetPoints);
			} else if (getThermostatUnits().equals("Fahrenheit")) {
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
			return nextPeriodTime;
		} else {
			nextPeriodTime = "";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Information is null");
		}
		return nextPeriodTime;
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



}
