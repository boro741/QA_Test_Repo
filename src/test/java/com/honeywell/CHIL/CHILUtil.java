package com.honeywell.CHIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.lyric.das.utils.DASUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.microsoft.azure.sdk.iot.device.*;

public class CHILUtil implements AutoCloseable {

	/**
	 * @param args
	 */

	private String chilURL;
	private boolean isConnected;
	private HttpURLConnection chilConnection;
	private String cookie;
	private HashMap<String, Long> locations;
	private int userID;
	private String verificationToken;
	private String bodyToken;
	private String sessionID;
	private TestCaseInputs inputs;
	public static String chapiDeviceId;
	public static int coolSetPoints = 0;
	public static int heatSetPoints = 0;
	public static Double maxHeat;
	public static Double minHeat;
	public static Double maxCool;
	public static Double minCool;
	public static String thermostatUnit = "";
	public static String startTime = "";
	public static String endTime = "";
	public static int setPointInPrimaryCard = 0;
	public static int setPointInVacationCard = 0;
	public static int vacationHeatSetPoint = 0;
	public static int vacationCoolSetPoint = 0;
	public static JSONObject SensorList;
	public CHILUtil(TestCaseInputs inputs) throws Exception {
		String environment = inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT);
		environment = environment.replaceAll("\\s", "");
		if (environment.equalsIgnoreCase("Production")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_PRODUCTION");
		} else if (environment.equalsIgnoreCase("CHILInt(Azure)")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_INT");
		} else if (environment.equalsIgnoreCase("ChilDev(Dev2)")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DEV2");
		} else if (environment.equalsIgnoreCase("CHILStage(Azure)")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_STAGING");
		} else if (environment.equalsIgnoreCase("LoadTesting")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_LOAD_TESTING");
		} else if (environment.equalsIgnoreCase("ChilDas(QA)")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_QA");
		} else if (environment.equalsIgnoreCase("ChilDas(Test)")) {
			chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_TEST");
		}
		this.inputs = inputs;
		this.isConnected = false;
		locations = new HashMap<String, Long>();
		new HashMap<String, String>();
	}

	public boolean isConnected() {
		return isConnected;
	}

	public HttpURLConnection getCHILConnection() {
		return chilConnection;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public String getBodyToken() {
		return bodyToken;
	}

	public String getSessionID() {
		return sessionID;
	}

	public boolean getConnection() throws Exception {
		try {
			URL url = new URL(this.chilURL + "api/v2/Session");
			String input = "{\"username\": \"" + inputs.getInputValue("USERID") + "\",\"password\": \""
					+ inputs.getInputValue("PASSWORD") + "\"}";

			chilConnection = (HttpURLConnection) url.openConnection();

			chilConnection.setDoOutput(true);
			chilConnection.setRequestMethod("POST");
			chilConnection.setRequestProperty("content-type", "application/json");
			chilConnection.setRequestProperty("content-length", String.valueOf(input.length()));

			OutputStream os = chilConnection.getOutputStream();

			os.write(input.getBytes());
			os.flush();
			//System.out.println(chilConnection.getResponseCode());

			if (chilConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				BufferedReader in = new BufferedReader(new InputStreamReader(chilConnection.getInputStream()));

				String inputLine;
				StringBuffer html = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}

				in.close();

				JSONObject jsonObj = new JSONObject(html.toString().trim());

				cookie = chilConnection.getHeaderField("Set-Cookie");

				bodyToken = jsonObj.get("bodytoken").toString();

				sessionID = jsonObj.get("sessionID").toString();

				verificationToken = chilConnection.getHeaderField("RequestVerificationToken");

				userID = jsonObj.getJSONObject("user").getInt("userID");
				JSONArray jsonUsers = (JSONArray) ((JSONObject) jsonObj.get("user")).get("locationRoleMapping");
				for (int i = 0; i < jsonUsers.length(); i++) {
					JSONObject tempJSON = (JSONObject) jsonUsers.get(i);
					locations.put(tempJSON.getString("locationName"), tempJSON.getLong("locationID"));
				}

				isConnected = true;
			} else {
				isConnected = false;
			}

		} catch (Exception e) {
			throw new Exception("Error Occured: " + e.getMessage());
		}
		return isConnected;
	}

	public boolean disconnect() {
		if (isConnected) {
			if (chilConnection != null) {
				try {
					chilConnection.disconnect();
					isConnected = false;
				} catch (Exception e) {
					isConnected = true;
				}

			} else {
				isConnected = false;
			}
		} else {
			isConnected = false;
		}

		return !isConnected;
	}

	public HttpURLConnection doPostRequest(String urlString, String headerData)
			throws MalformedURLException, IOException {

		HttpURLConnection postResponse = null;
		URL url = new URL(urlString);
		postResponse = (HttpURLConnection) url.openConnection();

		postResponse.setRequestMethod("POST");
		postResponse.setDoOutput(true);

		postResponse.setRequestProperty("sessionID", getSessionID());
		postResponse.setRequestProperty("RequestVerificationToken", getVerificationToken() + ":" + getBodyToken());
		postResponse.setRequestProperty("Cookie", cookie);

		postResponse.setRequestProperty("content-type", "application/json");

		if (!headerData.equals("")) {
			postResponse.setRequestProperty("content-length", String.valueOf(headerData.length()));
			OutputStream os = postResponse.getOutputStream();
			os.write(headerData.getBytes("UTF-8"));
			os.flush();
		}
		return postResponse;
	}

	public HttpURLConnection doGetRequest(String urlString) throws MalformedURLException, IOException {
		HttpURLConnection getResponse = null;
		URL url = new URL(urlString);

		getResponse = (HttpURLConnection) url.openConnection();

		getResponse.setRequestProperty("sessionID", getSessionID());
		getResponse.setRequestProperty("RequestVerificationToken", getVerificationToken() + ":" + getBodyToken());
		getResponse.setRequestProperty("Cookie", cookie);
		getResponse.setDoOutput(true);
		getResponse.setRequestMethod("GET");

		if (getResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED
				|| getResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {

			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// getResponse.getInputStream()));
			//
			// String inputLine;
			// StringBuffer html = new StringBuffer();
			//
			// while ((inputLine = in.readLine()) != null) {
			// html.append(inputLine);
			// }
			//
			//
			// System.out.println(html);

		} else {
			// System.out.println("Connection to Chapi is not successFul.
			// Response Code - " + getResponse.getResponseCode());
			// return null;
		}

		return getResponse;
	}

	public HttpURLConnection doDeleteRequest(String urlString, boolean... addClientHeader)
			throws MalformedURLException, IOException {
		HttpURLConnection getResponse = null;
		URL url = new URL(urlString);

		getResponse = (HttpURLConnection) url.openConnection();

		getResponse.setRequestProperty("sessionID", getSessionID());
		getResponse.setRequestProperty("RequestVerificationToken", getVerificationToken() + ":" + getBodyToken());
		getResponse.setRequestProperty("Cookie", cookie);
		getResponse.setDoOutput(true);
		getResponse.setRequestMethod("DELETE");
		if (addClientHeader.length > 0) {
			if (addClientHeader[0]) {
				getResponse.setRequestProperty("Client", "MessageProcessor");
			}
		}
		return getResponse;
	}

	public HttpURLConnection doPutRequest(String urlString, String headerData)
			throws MalformedURLException, IOException {

		HttpURLConnection postResponse = null;
		URL url = new URL(urlString);
		postResponse = (HttpURLConnection) url.openConnection();

		postResponse.setRequestMethod("PUT");
		postResponse.setDoOutput(true);

		postResponse.setRequestProperty("sessionID", getSessionID());
		postResponse.setRequestProperty("RequestVerificationToken", getVerificationToken() + ":" + getBodyToken());
		postResponse.setRequestProperty("Cookie", cookie);

		postResponse.setRequestProperty("content-type", "application/json");

		// postResponse.connect();

		if (!headerData.equals("")) {
			postResponse.setRequestProperty("content-length", String.valueOf(headerData.length()));
			OutputStream os = postResponse.getOutputStream();
			os.write(headerData.getBytes("UTF-8"));
			os.flush();
		}

		if (postResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED
				|| postResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
			System.out.println("Success");
		} else {
			// System.out.println(postResponse.getResponseCode());
		}
		return postResponse;
	}

	@Override
	public void close() throws Exception {
		disconnect();

	}
	
	public Set<String> getLocationNames() {
		return locations.keySet();
	}

	public int getUserID() {
		return userID;
	}

	public long getLocationID(String locationName) {
		if (locations.containsKey(locationName)) {
			return locations.get(locationName);
		} else {
			return -1;
		}
	}

	public int clearAlarm(long locationID, String deviceID, TestCases testCase) throws Exception {
		int result = -1;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				if (locationID == -1) {
					return -1;
				}
				if (chUtil.isConnected()) {
					String headerData = " ";
					String url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
							+ "/partitions/1/dismiss";
					result = doPutRequest(url, headerData).getResponseCode();

				}
			}
		} catch (Exception e) {
			throw new Exception("(Clear Alarm) Error Occured : " + e.getMessage());
		}
		return result;

	}

	public int setBaseStationMode(long locationID, String deviceID, String modeToSet, TestCases testCase)
			throws Exception {
		int result = -1;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				if (locationID == -1) {
					throw new Exception("Invalid location ID found");
				}
				if (chUtil.isConnected()) {
					String url = " ";
					String headerData = " ";
					// api/v3/locations/{0}/devices/{1}/partitions/{2}/Disarm
					if (modeToSet.toUpperCase().contains("HOME")) {
						url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/Disarm";
						headerData = "{\"EnableSilentMode\":\"false\"," + "\"CorrelationId\":\"CorrId\""
								+ ",\"ChannelId\":\"ChannId\"" + "}";
					} else if (modeToSet.toUpperCase().contains("AWAY")) {
						url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/Arm";
						headerData = "{\"ArmType\":\"0\"," + "\"InstantArm\":\"true\"" + ",\"SilenceBeep\":\"false\""
								+ ",\"QuickArm\":\"false\"" + ",\"BypassSensors\": []}";
					} else if (modeToSet.toUpperCase().contains("NIGHT")) {
						url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/Arm";
						headerData = "{\"ArmType\":\"1\"," + "\"InstantArm\":\"true\"" + ",\"SilenceBeep\":\"false\""
								+ ",\"QuickArm\":\"false\"" + ",\"BypassSensors\": []}";

					} else if (modeToSet.toUpperCase().contains("OFF")) {
						url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/Disarm";
						headerData = "{\"EnableSilentMode\":\"true\"" + "}";
					}
					result = doPutRequest(url, headerData).getResponseCode();
				}
			} else {
				throw new Exception("(Set Base Station Mode) Unable to connect to CHIL");
			}
		} catch (Exception e) {
			throw new Exception("(Set Base Station Mode) Unable to connect to CHIL");
		}
		return result;
	}

	public int putEntryExitTimer(long locationID, String deviceID, String entryExitTime) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = "";
			String headerData = "";
			url = this.chilURL + "/api/v3/locations/" + locationID + "/devices/" + deviceID
					+ "/partition/1/EntryExitDelay";
			try {
				headerData = String.format("{\"entryDelayInSeconds\":%s,\"exitDelayInSeconds\":%s}", entryExitTime,
						entryExitTime);
				result = doPutRequest(url, headerData).getResponseCode();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}
	
	
	public int putAlarmSignalStatus(long locationID, String deviceID, String SignalStatus) throws Exception {
		int status = -1;
		if (isConnected) {
			String url = "";
			String headerData = "";
			url = this.chilURL + "/api/v3/locations/" + locationID + "/devices/" + deviceID
					+ "/alarmverificationconfig";
			try {
				headerData = String.format("{\"alarmVerificationState\":\"%s\"}", 
						SignalStatus);
				status = doPutRequest(url, headerData).getResponseCode();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return status ;
	}

	public int dismissAllAlerts(List<Long> notificationIDS) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + "/api/v2/NotificationMessages";
			String headerData = "{\"NotificationIDS\" : " + notificationIDS.toString()
					+ ",\"NotificationStatus\" : \"Dismiss\"}";
			try {
				result = doPutRequest(url, headerData).getResponseCode();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}

	public int deleteSchedule(long locationID, String deviceID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/ThermostatSchedule", locationID);
			String headerData = String.format("{\"scheduleType\": \"None\",\"deviceIDs\": [\"%s\"]}", deviceID);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}
	
	public int deletecomboSensor(long locationID, String deviceID, TestCases testCase) throws Exception {
		int result = -1;
		DeviceInformation sensorinfo = new DeviceInformation(testCase, inputs);
		String grpid = sensorinfo.getComboSensor();
		if (isConnected) {
			String url = chilURL + String.format("api/v3/locations/%s/devices/%s/partitions/1/sensors/%s?isGroupable=true", locationID,
					deviceID, grpid);
			result = doDeleteRequest(url).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}
	
	public int deleteAccessSensor(long locationID, String deviceID, TestCases testCase ,String SensorId) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/v3/locations/%s/devices/%s/partitions/1/sensors/%s", locationID,
					deviceID, SensorId);
			System.out.println(url);
			result = doDeleteRequest(url).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int createSchedule(TestCaseInputs inputs, String scheduleType, long locationID, String deviceID,
			String jasperStatType) throws Exception {
		int result = -1;
		if (isConnected) {
			String headerData = " ";
			String url = " ";
			if (jasperStatType.equalsIgnoreCase("NA") || jasperStatType.equalsIgnoreCase("EMEA")
					|| jasperStatType.equalsIgnoreCase("HoneyBadger")) {
				if (scheduleType.equals("Time")) {
					url = chilURL + "api/locations/" + locationID + "/thermostat/TimedScheduleTemplate";
					if (jasperStatType.equalsIgnoreCase("NA") || jasperStatType.equalsIgnoreCase("HoneyBadger")) {
						headerData = "{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
								+ deviceID + "\"]}";
					} else {
						headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
								+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
								+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
								+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
								+ "\"DeviceIds\":[\"" + deviceID + "\"]}";
					}
				} else if (scheduleType.equals("Geofence")) {
					url = chilURL + "api/locations/" + locationID + "/thermostat/GeofenceScheduleTemplate";
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
						if (jasperStatType.equalsIgnoreCase("NA")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":\"70.00\",\"CoolSetPoint\":\"78.00\"},\"AwayPeriod\":{\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"85.00\"}},\"DeviceIds\":[\""
											+ deviceID + "\"]}");
						} else {
							headerData = String.format(
									"{\"name\":\"GeofenceScheduleRequestWithAutoFanMode\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":70,\"CoolSetPoint\":79,\"FanSwitch\": {\"Position\": 0,\"Speed\": 8}},\"AwayPeriod\":{\"HeatSetPoint\":62,\"CoolSetPoint\":89,\"FanSwitch\": {\"Position\": 0,\"Speed\": 8}}},\"DeviceIds\":[\""
											+ deviceID + "\"]}");
						}
					} else {
						if (jasperStatType.equalsIgnoreCase("NA")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":\"70.00\",\"CoolSetPoint\":\"78.00\"},\"AwayPeriod\":{\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"85.00\"},\"sleepMode\":{\"startTime\":\"22:00:00\",\"endTime\":\"06:00:00\",\"heatSetPoint\":\"62.00\",\"coolSetPoint\":\"82.00\"}},\"DeviceIds\":[\""
											+ deviceID + "\"]}");
						} else {
							headerData = String.format(
									"{\"name\":\"GeofenceScheduleRequestWithAutoFanMode\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":70,\"CoolSetPoint\":79,\"FanSwitch\": {\"Position\": 0,\"Speed\": 8}},\"AwayPeriod\":{\"HeatSetPoint\":62,\"CoolSetPoint\":89,\"FanSwitch\": {\"Position\": 0,\"Speed\": 8}},\"sleepMode\":{\"startTime\":\"22:00:00\",\"endTime\":\"06:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":59,\"FanSwitch\": {\"Position\": 0,\"Speed\": 8}}},\"DeviceIds\":[\""
											+ deviceID + "\"]}");
						}
					}
				}

				result = doPostRequest(url, headerData).getResponseCode();

			} else {
				if (scheduleType.equals("Time")) {
					url = chilURL + "api/v3/locations/" + locationID + "/ThermostatSchedule";
					if (jasperStatType.equalsIgnoreCase("FlyCatcher")) {
						headerData = "{\"ScheduleType\": \"Timed\",\"deviceIds\":[\"" + deviceID
								+ "\"],\"TimedSchedule\": {\"days\":[{\"day\":\"Monday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Tuesday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Wednesday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Thursday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Friday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Saturday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]},{\"day\":\"Sunday\",\"periods\":[{\"isCancelled\":false,\"periodType\":\"Wake\",\"periodName\":\"Wake\",\"startTime\":\"06:00:00\",\"heatSetPoint\":71,\"coolSetPoint\":79,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Away\",\"periodName\":\"Away\",\"startTime\":\"08:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":85,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Home\",\"periodName\":\"Home\",\"startTime\":\"18:00:00\",\"heatSetPoint\":70,\"coolSetPoint\":78,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}},{\"isCancelled\":false,\"periodType\":\"Sleep\",\"periodName\":\"Sleep\",\"startTime\":\"22:00:00\",\"heatSetPoint\":62,\"coolSetPoint\":82,\"fanSwitch\":{\"position\":\"Auto\"},\"priority\":{\"priorityType\":\"PickARoom\",\"selectedRooms\":[1,2,3]}}]}]}}";
					}

					result = doPutRequest(url, headerData).getResponseCode();

					// Using old Template and API version for Flycatcher geofence schedule, needs to
					// be changed later
				} else if (scheduleType.equals("Geofence")) {
					url = chilURL + "api/locations/" + locationID + "/thermostat/GeofenceScheduleTemplate";
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {

						headerData = String.format(
								"{\"name\":\"Template\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":\"70.00\",\"CoolSetPoint\":\"78.00\"},\"AwayPeriod\":{\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"85.00\"}},\"DeviceIds\":[\""
										+ deviceID + "\"]}");

					} else {
						headerData = String.format(
								"{\"name\":\"Template\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":\"70.00\",\"CoolSetPoint\":\"78.00\"},\"AwayPeriod\":{\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"85.00\"},\"sleepMode\":{\"startTime\":\"22:00:00\",\"endTime\":\"06:00:00\",\"heatSetPoint\":\"62.00\",\"coolSetPoint\":\"82.00\"}},\"DeviceIds\":[\""
										+ deviceID + "\"]}");
					}
					result = doPostRequest(url, headerData).getResponseCode();
				}
			}
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int putDASDeviceName(long locationID, String deviceID, String deviceNameToBePut) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/devices/%s", locationID, deviceID);
			String headerData = String.format("{\"OnboardDeviceName\": \"%s\",\"name\": \"Security\"}",
					deviceNameToBePut);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int putDASSensorName(long locationID, String deviceID, String sensorNameToBePut, String sensorID,
			String sensorResponseType) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL
					+ String.format("api/v3/locations/%s/devices/%s/partitions/1/Sensors", locationID, deviceID);
			String headerData = String.format(
					"{\"chime\":\"Standard\",\"responseType\":\"%s\",\"sensitivity\":0,\"isActive\":true,\"id\":\"%s\",\"name\":\"%s\"}",
					sensorResponseType, sensorID, sensorNameToBePut);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int putZwaveDeviceName(long locationID, String deviceID, String subDeviceID, String deviceNameToBePut)
			throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL
					+ String.format("api/v3/locations/%s/devices/%s/subdevices/%s", locationID, deviceID, subDeviceID);
			String headerData = String.format("{\"name\": \"%s\"}", deviceNameToBePut);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int putDASKeyfobName(long locationID, String deviceID, String keyfobNameToBePut, String keyfobID)
			throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/v3/locations/%s/devices/%s/partitions/1/KeyFobs/%s", locationID,
					deviceID, keyfobID);
			String headerData = String.format("{\"name\":\"%s\"}", keyfobNameToBePut);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int putForgotPasscode() throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/PinStatus");

			String headerData = String.format("{\"userName\":\"%s\",\"pinStatus\":\"ForgotPin\"}",
					inputs.getInputValue("USERID"));

			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int changeDASCameraModeStatus(TestCases testCase, TestCaseInputs inputs, long locationID, String cameraID,
			boolean turnOn) throws Exception {
		if (isConnected) {
			JSONObject cameraConfig = DASUtils.getDASCameraConfig(testCase, inputs);
			String url = this.chilURL + "api/locations/" + locationID + "/devices/" + cameraID
					+ "/config?CameraKind=HONDAS";
			cameraConfig.put("privacyMode", turnOn ? "off" : "on");
			cameraConfig.put("hwPrivacyMode", turnOn ? "off" : "on");
			String headerData = cameraConfig.toString();
			return doPostRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
	}

	public int changeLocationGeofenceStatus(boolean turnOn, long locationID) throws Exception {
		if (isConnected) {
			String url = chilURL + String.format("api/v2/locations/%s", locationID);
			String headerData = String.format(
					"{\"country\":\"US\",\"locationID\":\"%s\",\"Geofenceenabled\":\"%s\",\"radius\":0,\"OriginCoordinates\":{\"Latitude\":0,\"Longitude\":0}}",locationID,
					turnOn);
			return doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}

	}

	public int userArrived(long locationID, int userID, int geofenceID) throws Exception {
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/GeoFence/%s/GangMember/%s/GeoFenceEvent", locationID,
					geofenceID, userID);
			String headerData = "{\"type\":\"UserArrived\"}";
			return doPostRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
	}

	public int userDeparted(long locationID, int userID, int geofenceID) throws Exception {
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/GeoFence/%s/GangMember/%s/GeoFenceEvent", locationID,
					geofenceID, userID);
			String headerData = "{\"type\":\"UserDeparted\"}";
			return doPostRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
	}

	public int deleteDevice(long locationID, String deviceID, boolean isDasDevice) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/locations/" + locationID + "/Devices/" + deviceID;
			try {
				if (isDasDevice) {
					result = doDeleteRequest(url, true).getResponseCode();
				} else {
					result = doDeleteRequest(url).getResponseCode();
				}
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int deleteDeviceHub(long locationID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/locations/" + locationID + "/Devices/" + chapiDeviceId + "/hub";
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int deleteLocation(long locationID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/locations/" + locationID + "/users/" + userID;
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int deleteSensor(long locationID, String deviceID, String sensorID, int partitionID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID + "/partitions/" + partitionID
					+ "/Sensors/" + sensorID;
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int deleteKeyfob(long locationID, String deviceID, String keyFobID, int partitionID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID + "/partitions/" + partitionID
					+ "/Keyfobs/" + keyFobID;
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int postSensor(long locationID, String deviceID, int partitionID, String serialNumber) throws Exception {
		int result = -1;
		String headerData = " ";
		String url = " ";

		if (isConnected) {

			headerData = String.format(
					"{\"SerialNo\":\"%s\",\"Name\":\"%s\",\"ResponseType\":\"Perimeter\",\"Sensitivity\":\"0\",\"Chime\":\"sensor\"}",
					serialNumber, "Sensor" + new Random().nextInt(200));

			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID + "/partitions/" + partitionID
					+ "/Sensors";
			try {
				result = doPostRequest(url, headerData).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}

	public int postSensorDiscovery(long locationID, String deviceID, boolean isEnabled) throws Exception {
		int result = -1;
		String headerData = " ";
		String url = " ";

		if (isConnected) {

			// enable sensor
			if (isEnabled)
				headerData = String.format("{\"mode\":\"Sensor\",\"TimeOut\":900,\"Id\":null}");
			else
				headerData = String.format("{\"mode\":\"Disable\",\"TimeOut\":900,\"Id\":null}");

			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID + "/Discovery?partitionid=1";
			try {
				result = doPostRequest(url, headerData).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}

	public void FeedDataIntoIOTHub(String deviceID, String payload, String xappUrl, String from, String method)
			throws Exception {
		DeviceClient client = null;
		try {
			String iotHubConString = "";
			if (chilURL.contains("childev")) {
				iotHubConString = "HostName=granite-dev-das01-iothub.azure-devices.net;DeviceId=B82CA0000740;SharedAccessKey=5mWRiN796NreGeCFgQqLzpmcymQW0c0pqkWUr+FrVXs=";
			}

			client = new DeviceClient(iotHubConString, IotHubClientProtocol.HTTPS);
			client.open();

			Message message = new Message(payload.getBytes(Charset.forName("ASCII")));
			message.setProperty("x-app-url", xappUrl);
			message.setProperty("deviceId", deviceID);
			message.setProperty("from", from);
			message.setProperty("mac", deviceID);
			message.setProperty("method", method);
			message.setProperty("cor", "B82CA0000456-54387");
			message.setProperty("seq", "-1");
			client.sendEventAsync(message, new EventCallback(), message);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public int createTimeScheduleWithSpecificNumberOfPeriods_EMEA(TestCaseInputs inputs, long locationID,
			String deviceID) {
		int result = -1;

		try {
			if (isConnected) {
				String headerData = " ";
				String url = " ";
				url = chilURL + "api/locations/" + locationID + "/thermostat/TimedScheduleTemplate";
				if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("One")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";

				} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("Two")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";

				} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("Three")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";

				} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("Four")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";

				} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("Five")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";

				} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
						.equalsIgnoreCase("Six")) {
					headerData = "{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
							+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
							+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:00:00\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
							+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"23:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
							+ "\"DeviceIds\":[\"" + deviceID + "\"]}";
				}
				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println();
		}

		return result;
	}

	public int changeSystemMode(long locationID, String deviceID, String thermostatMode) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/thermostat/Mode", locationID, deviceID);
				String headerData = String.format("{\"thermostatMode\":\"%s\"}", thermostatMode);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int changeScheduleStatus(long locationID, String deviceID, String ScheduleStatus) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/V2/locations/%s/Schedule/Status/", locationID);
				String headerData = String.format("{\"deviceIds\":[\"%s\"],\"scheduleStatus\":\"%s\"}", deviceID,
						ScheduleStatus);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int changeVentilationMode(long locationID, String deviceID, String VentilationMode) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/Settings/VentilationMode", locationID, deviceID);
				String headerData = String.format("{\"changeableValues\":\"%s\"}", VentilationMode);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public JSONObject getSensorList(long locationID, String deviceID, int groupid) {
		JSONObject SensorList = new JSONObject();
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				if (locationID == -1) {
					throw new Exception("Invalid locationID: -1");
				}
				if (chUtil.isConnected()) {
					if (chUtil.isConnected()) {
						String url = chilURL + String.format("api/v1/locations/%s/devices/%s/group/%s/rooms",
								locationID, deviceID, groupid);
						HttpURLConnection connection = chUtil.doGetRequest(url);
						if (connection != null) {
							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							String inputLine;
							StringBuffer html = new StringBuffer();
							while (!in.ready()) {
							}
							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}
							in.close();
							SensorList = new JSONObject(html.toString().trim());
						} else {
							throw new Exception("Unable to connect to CHIL");
						}
					}
				} else {
					throw new Exception("Not connected to CHIL");
				}
			}
		} catch (Exception e) {

		}
		return SensorList;
	}

	public int setResumeAdhocstatus(long locationID, String deviceID, TestCases testCase) {
		int result = -1;
		try {
			if (isConnected) {
				DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
				String SystemMode = devInfo.getThermoStatMode();
				String url = "";
				if (SystemMode.equalsIgnoreCase("Heat")) {
					url = chilURL + String.format("api/V2/locations/%s/devices/%s/thermostat/HeatSetpoint/", locationID,
							deviceID);

				}
				if (SystemMode.equalsIgnoreCase("Cool")) {
					url = chilURL + String.format("api/V2/locations/%s/devices/%s/thermostat/CoolSetpoint/", locationID,
							deviceID);
				}
				String headerData = String.format("{\"thermostatSetpointStatus\":\"%s\"}", "NoHold");
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
					TimeUnit.SECONDS.sleep(5);
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setHumidificationValue(long locationID, String deviceID, String HumidificationValue) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/Settings/humidifier", locationID, deviceID);
				String headerData = String.format("{\"changeableValues\":{\"mode\":\"%s\",\"setPoint\":\"%s\"}}",
						"Auto", HumidificationValue);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setHumidificationstatus(long locationID, String deviceID, String HumidificationStatus) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/Settings/humidifier", locationID, deviceID);
				String headerData = String.format("{\"changeableValues\":{\"mode\":\"%s\",\"setPoint\":\"%s\"}}",
						HumidificationStatus, 30);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setDehumidificationStatus(long locationID, String deviceID, String DehumidificationStatus) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/Settings/Dehumidifier", locationID, deviceID);
				String headerData = String.format("{\"changeableValues\":{\"mode\":\"%s\",\"setPoint\":\"%s\"}}",
						DehumidificationStatus, 40);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int SetVentilationTimer(long locationID, String deviceID, int VentilationTimer) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/device/%s/ventilation/settings", locationID, deviceID);
				String headerData = String.format(
						"{\"ventilationBoostTimer\":\"%s\",\"ventilationBoostTimerReset\":\"%s\"}", VentilationTimer,
						"false");
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int ResetVentilationTimer(long locationID, String deviceID) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/device/%s/ventilation/settings", locationID, deviceID);
				String headerData = String
						.format("{\"ventilationBoostTimer\":\"%s\",\"ventilationBoostTimerReset\":\"%s\"}", 0, true);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setCoolThermostatStatus(long locationID, String deviceID) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/v2/locations/%s/devices/%s/thermostat/coolSetpoint", locationID, deviceID);
				String headerData = String.format("{\"thermostatSetpointStatus\":\"%s\"}", "NoHold");
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setHeatThermostatStatus(long locationID, String deviceID) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/v2/locations/%s/devices/%s/thermostat/HeatSetpoint", locationID, deviceID);
				String headerData = String.format("\"thermostatSetpointStatus\":\"%s\"", "NoHold");
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int enableVacation(long locationID, String deviceID, String startTime, String endTime, String deviceUnits,
			int coolSetPoints, int heatSetPoints) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/v2/locations/%s/vacationHold", locationID);
				String headerData = String.format(
						"{\"vacationStart\": \"%s\",\"vacationEnd\": \"%s\",\"thermostatVacationHoldSettings\": [{\"deviceID\": \"%s\",\"coolSetpoint\": %d,\"heatSetpoint\": %d,\"units\": \"%s\"}],\"enabled\": true}",
						startTime, endTime, deviceID, coolSetPoints, heatSetPoints, deviceUnits);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int disableVacation(long locationID, String deviceID) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/v2/locations/%s/vacationHold", locationID);
				String headerData = String.format(
						"{\"thermostatVacationHoldSettings\":[{\"deviceID\":\"%s\"}],\"enabled\":false}", deviceID);
				System.out.println(headerData);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int TriggerGeoEventSleep(long locationID, String deviceID, String startTime, String endTime) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/thermostat/GeofenceScheduleTemplate", locationID);
				String headerData = String.format(
						"{\"Name\":\"Template\",\"GeoFenceSchedule\":{\"HomePeriod\":{\"HeatSetPoint\":\"70.00\","
								+ "\"CoolSetPoint\":\"78.00\"},\"AwayPeriod\":{\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"85.00\"},"
								+ "\"SleepMode\":{\"StartTime\":\"%s\",\"EndTime\":\"%s\",\"HeatSetPoint\":\"62.00\",\"CoolSetPoint\":\"82.00\"}},\"DeviceIDs\":[\""
								+ deviceID + "\"]}",
						startTime, endTime);

				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int TriggerTimePeriod(long locationID, String deviceID, String Period, String startTime,
			String jasperStatType) {
		int result = -1;
		try {
			if (isConnected) {
				String headerData = " ";
				String url = " ";
				url = chilURL + String.format("api/locations/" + locationID + "/thermostat/TimedScheduleTemplate");
				if (jasperStatType.equalsIgnoreCase("NA")) {
					if (Period.equalsIgnoreCase("HOME")) {
						headerData = String.format(
								"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
										+ deviceID + "\"]}",
								startTime, startTime, startTime, startTime, startTime, startTime, startTime);
					}
					if (Period.equalsIgnoreCase("AWAY")) {
						headerData = String.format(
								"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
										+ deviceID + "\"]}",
								startTime, startTime, startTime, startTime, startTime, startTime, startTime);
					}
					if (Period.equalsIgnoreCase("WAKE")) {
						headerData = String.format(
								"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
										+ deviceID + "\"]}",
								startTime, startTime, startTime, startTime, startTime, startTime, startTime);
					}
					if (Period.equalsIgnoreCase("SLEEP")) {
						headerData = String.format(
								"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
										+ deviceID + "\"]}",
								startTime, startTime, startTime, startTime, startTime, startTime, startTime);
					}
				} else if (Period.equalsIgnoreCase("P1")) {
					headerData = String.format(
							"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
									+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
									+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
							startTime, startTime, startTime, startTime, startTime, startTime, startTime);
				}
				if (Period.equalsIgnoreCase("P2")) {
					headerData = String.format(
							"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
									+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
									+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
							startTime, startTime, startTime, startTime, startTime, startTime, startTime);
				}

				if (Period.equalsIgnoreCase("P3")) {
					headerData = String.format(
							"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
									+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
									+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
							startTime, startTime, startTime, startTime, startTime, startTime, startTime);
				}
				if (Period.equalsIgnoreCase("P4")) {
					headerData = String.format(
							"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
									+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
									+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:39\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
									+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
									+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
							startTime, startTime, startTime, startTime, startTime, startTime, startTime);
				}

				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int TriggerTimNextPeriod(long locationID, String deviceID, String CurrentPeriod, String Period,
			String previousStartTime, String NextPeriodStartTime, String jasperStatType) {
		int result = -1;
		try {
			if (isConnected) {
				String headerData = " ";
				String url = " ";
				url = chilURL + String.format("api/locations/" + locationID + "/thermostat/TimedScheduleTemplate");
				if (jasperStatType.equalsIgnoreCase("NA")) {
					if (CurrentPeriod.equalsIgnoreCase("Home")) {
						if (Period.equalsIgnoreCase("AWAY")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else if (Period.equalsIgnoreCase("WAKE")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s0\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("Away")) {
						if (Period.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("WAKE")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("Wake")) {
						if (Period.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("AWAY")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
						/*** Sleep ****/
					} else {
						if (Period.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("AWAY")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					}
				} else {
					if (CurrentPeriod.equalsIgnoreCase("P3")) {
						if (Period.equalsIgnoreCase("P2")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else if (Period.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("P2")) {
						if (Period.equalsIgnoreCase("P3")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("P1")) {
						if (Period.equalsIgnoreCase("P3")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("P2")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
						/*** Sleep ****/
					} else {
						if (Period.equalsIgnoreCase("P3")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (Period.equalsIgnoreCase("P2")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					}
				}
				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int TriggerTimNextPeriod(long locationID, String deviceID, String PreviousPeriod, String CurrentPeriod,
			String NextPeriod, String previousStartTime, String startTime, String NextPeriodStartTime,
			String jasperStatType) {
		int result = -1;
		try {
			if (isConnected) {
				String headerData = " ";
				String url = " ";
				url = chilURL + String.format("api/locations/" + locationID + "/thermostat/TimedScheduleTemplate");
				if (jasperStatType.equalsIgnoreCase("NA")) {
					if (CurrentPeriod.equalsIgnoreCase("Home")) {
						if (NextPeriod.equalsIgnoreCase("AWAY") && PreviousPeriod.equalsIgnoreCase("Wake")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, startTime, previousStartTime,
									NextPeriodStartTime, startTime, previousStartTime, NextPeriodStartTime, startTime,
									previousStartTime, NextPeriodStartTime, startTime, previousStartTime,
									NextPeriodStartTime, startTime, previousStartTime, NextPeriodStartTime, startTime,
									previousStartTime, NextPeriodStartTime, startTime, previousStartTime,
									NextPeriodStartTime, startTime);
						} else if (NextPeriod.equalsIgnoreCase("WAKE")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s0\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("Away")) {
						if (NextPeriod.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("WAKE")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("Wake")) {
						if (NextPeriod.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("AWAY")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"22:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						}
						/*** Sleep ****/
					} else {
						if (NextPeriod.equalsIgnoreCase("HOME")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("AWAY")) {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\":"
											+ " [{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"06:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime);
						} else {
							headerData = String.format(
									"{\"name\":\"Template\",\"ScheduleSubType\": 0,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": \"Sunday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Monday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Tuesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Wednesday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Thursday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Friday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]},"
											+ "{\"Day\": \"Saturday\",\"Periods\": [{\"IsCancelled\": \"false\",\"PeriodType\": \"Wake\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Away\",\"StartTime\": \"08:00:00\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"85.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Home\",\"StartTime\": \"18:00:00\",\"HeatSetpoint\": \"70.00\",\"CoolSetpoint\": \"78.00\"},"
											+ "{\"IsCancelled\": \"false\",\"PeriodType\": \"Sleep\",\"StartTime\": \"%s\",\"HeatSetpoint\": \"62.00\",\"CoolSetpoint\": \"82.00\"}]}]},\"DeviceIds\":[\""
											+ deviceID + "\"]}",
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime, startTime,
									NextPeriodStartTime, startTime, NextPeriodStartTime, startTime, NextPeriodStartTime,
									startTime, NextPeriodStartTime, startTime, NextPeriodStartTime);
						}
					}
				} else {
					if (CurrentPeriod.equalsIgnoreCase("P3")) {
						if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("P2")) {
						if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					} else if (CurrentPeriod.equalsIgnoreCase("P1")) {
						if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"22:30:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
						/*** Sleep ****/
					} else {
						if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						} else if (NextPeriod.equalsIgnoreCase("P2") && PreviousPeriod.equalsIgnoreCase("P1")) {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"06:30:43.5857903\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime, NextPeriodStartTime, previousStartTime,
									NextPeriodStartTime, previousStartTime);
						} else {
							headerData = String.format(
									"{\"name\":\"emeaTimedScheduleFor6PeriodsWithoutPTAndIsCancelled\",\"ScheduleSubType\": 1,\"TimedSchedule\": {\"Days\": "
											+ "[{\"Day\": 0,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 1,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 2,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 3,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 4,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 5,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]},"
											+ "{\"Day\": 6,\"Periods\": [{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 66.2,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"08:00:00\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"18:00:00\",\"HeatSetpoint\": 69.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}},"
											+ "{\"IsCancelled\": null,\"PeriodType\": null,\"StartTime\": \"%s\",\"HeatSetpoint\": 60.8,\"CoolSetpoint\": 78,\"FanSwitch\": {\"Position\": 2,\"Speed\": 8}}]}]},"
											+ "\"DeviceIds\":[\"" + deviceID + "\"]}",
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime, previousStartTime, NextPeriodStartTime,
									previousStartTime, NextPeriodStartTime);
						}
					}

				}
				try {
					System.out.println(headerData);
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int TriggerGeoEvent(long locationID, long geofenceID, long userID, String geoEvent) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("/api/locations/%s/GeoFence/%s/GangMember/%s/GeoFenceEvent",
						locationID, geofenceID, userID);
				String headerData = String.format("{\"type\": \"%s\"}", geoEvent);
				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int putLocationV2(long locationID, String body) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/v2/locations/%s", locationID);
				String headerData = String.format(body);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int switchToOff(long locationID, String deviceID, TestCases testCase) {

		int result = -1;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				if (locationID == -1) {
					return -1;
				}
				if (chUtil.isConnected()) {
					try {
						String headerData = " ";
						String url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/DisArm";
						headerData = "{\"enableSilentMode\":1}";
						try {
							result = doPutRequest(url, headerData).getResponseCode();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public int RegisterAndActivateCamera(long locationID, String deviceID, TestCases testCase) {
		HttpURLConnection result;
		int result1 = -1;
		String deviceType = "C1";
		String name = "Certificate C1 Camera";
		String mac = "74DA38B86396";
		String certificate = "-----BEGIN CERTIFICATE-----MIIDJjCCAsugAwIBAgIIBEzqqdIM5bwwCgYIKoZIzj0EAwIwWTELMAkGA1UEBhMCVVMxJTAjBgNVBAoMHEhvbmV5d2VsbCBJbnRlcm5hdGlvbmFsIEluYy4xDDAKBgNVBAsMA0FDUzEVMBMGA1UEAwwMU2hhcmVkIFFBIENBMCAXDTE3MDEwNjExNDA0M1oYDzk5OTkwMjAxMDAwMDAwWjCBjDELMAkGA1UEBhMCVVMxJTAjBgNVBAoMHEhvbmV5d2VsbCBJbnRlcm5hdGlvbmFsIEluYy4xCzAJBgNVBAsMAkMxMUkwRwYDVQQDDEAzMTMzOEMwQUI2QTVEOTRDMDdFQjFCMEE2NjlFQUE5NERBNTYxMUE1Q0NEMjVCRjNBQjVBNEJEMUJDQjFEOTBGMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEmpwvFDyqziYlPAuQzaQ6C1GtEfqwuWfQxT7kQZqnUacBL6Nd7d03JIG6rPohITnclPzgT5e53PeEBfeRWDILPqOCAUUwggFBMDwGCCsGAQUFBwEBBDAwLjAsBggrBgEFBQcwAYYgaHR0cDovL3FocHBraS5ob25leXdlbGwuY29tL29jc3AwHQYDVR0OBBYEFLe/0jghGBODIpATNg8iIRj9b0xiMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUigo7HXBqSQk3b40Axd8hqEVvrg0wSQYDVR0gBEIwQDA+BgwrBgEEAYGkBgIBAQEwLjAsBggrBgEFBQcCARYgaHR0cHM6Ly9xaHBwa2kuaG9uZXl3ZWxsLmNvbS9jcHMwQAYDVR0fBDkwNzA1oDOgMYYvaHR0cDovL3FocHBraS5ob25leXdlbGwuY29tL2NybC9TaGFyZWRRQUNBMi5jcmwwDgYDVR0PAQH/BAQDAgOIMBYGA1UdJQEB/wQMMAoGCCsGAQUFBwMCMAoGCCqGSM49BAMCA0kAMEYCIQDiMBQaEIgFwqtMXV1jWDs2w8eDZVizoJXzACHZ02Zy2gIhAID4akK42b7xoQYXNi6lk6Ynab57jyEH/NTMnPHupMqI-----END CERTIFICATE-----";

		try {
			getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Register Camera
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/locations/%s/devices/hub", locationID);
				String headerData = String.format(
						"{\"DeviceId\": \"%s\",\"DeviceTypes\": \"%s\",\"Name\": \"%s\",\"mac\": \"%s\"}", deviceID,
						deviceType, name, mac);
				try {
					result = doPostRequest(url, headerData);

					if (result.getResponseCode() == HttpURLConnection.HTTP_CREATED
							|| result.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader((result.getInputStream())));
						StringBuilder sb = new StringBuilder();
						String output;
						while ((output = br.readLine()) != null) {
							sb.append(output);
						}
						String JsonString = sb.toString();

						JSONObject obj = new JSONObject(JsonString);

						String registrationId = (String) obj.get("RegistrationId");
						chapiDeviceId = (String) obj.get("CHAPIdeviceID");

						String url1 = chilURL + String.format("provisioning/api/devices/activate");
						String headerData1 = String.format(
								"{\"deviceId\": \"%s\",\"registrationId\": \"%s\",\"certificate\": \"%s\"}", deviceID,
								registrationId, certificate);
						result1 = doPostRequest(url1, headerData1).getResponseCode();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		// Get Location
		return result1;
	}

	public int getStripeCustomerAndDeleteSubscription(String stripeCustomerId, String stripePrivateKey)

			throws MalformedURLException, IOException {
		int result = -1;
		HttpURLConnection getResponse = null;
		URL url = new URL("https://api.stripe.com/v1/customers/" + stripeCustomerId);

		getResponse = (HttpURLConnection) url.openConnection();

		getResponse.setRequestProperty("customersId", stripeCustomerId);
		getResponse.setRequestProperty("Authorization", "Bearer " + stripePrivateKey);
		getResponse.setRequestProperty("Cookie", cookie);
		getResponse.setDoOutput(true);
		getResponse.setRequestMethod("GET");

		if (getResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED
				|| getResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {

			BufferedReader br = new BufferedReader(new InputStreamReader

			((getResponse.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			String JsonString = sb.toString();

			JSONObject obj = new JSONObject(JsonString);
			String subscription = "";
			JSONObject one = obj.getJSONObject("subscriptions");
			JSONArray two = one.getJSONArray("data");
			for (int j = 0; j < two.length();) {
				JSONObject ingredObject = two.getJSONObject(j);
				subscription = ingredObject.getString("id");
				break;
			}

			HttpURLConnection deleteResponse = null;
			URL url1 = new URL("https://api.stripe.com/v1/subscriptions/" + subscription);

			deleteResponse = (HttpURLConnection) url1.openConnection();

			deleteResponse.setRequestProperty("Authorization", "Bearer " + stripePrivateKey);
			deleteResponse.setDoOutput(true);
			deleteResponse.setRequestMethod("DELETE");
			result = deleteResponse.getResponseCode();
		}

		return result;
	}

	public static String getAPIGEEClientID(TestCaseInputs inputs) throws Exception {
		JSONObject enrollmentObj;
		String clientID = "";
		try {
			enrollmentObj = getEnrollmentJSON(inputs);
			clientID = enrollmentObj.getString("client_id");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return clientID;
	}

	public static JSONObject getEnrollmentJSON(TestCaseInputs inputs) throws Exception {
		JSONObject enrollmentObj = new JSONObject();
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			String url = "";
			if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("Production")) {
				url = "https://api.honeywell.com/oauth2/accesstoken";
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("CHIL Stage (Azure)")) {
				url = "https://connectedhome-qa.apigee.net/oauth2/accesstoken";
			}
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
			httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.addHeader("authorization",
					"Basic Qk1wVnJyeGZha29BM0MxeEhoUm9qaWdzMGN5RzI1VnM6Z0xHWE9qMG9OQ05MZWZmQQ==");
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				httpClient.close();
				enrollmentObj = new JSONObject(result.toString());
			} else {
				throw new Exception("Get Apigee Token : Unable to create session");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return enrollmentObj;
	}

	public static String getAPIGEEAccessToken(TestCaseInputs inputs) throws Exception {
		JSONObject enrollmentObj;
		String accessToken = "";
		try {
			enrollmentObj = getEnrollmentJSON(inputs);
			accessToken = enrollmentObj.getString("access_token");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return accessToken;
	}

	public int putThermostatDeviceName(long locationID, String deviceID, String deviceNameToBePut) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/Devices/%s", locationID, deviceID);
			String headerData = String.format("{\"name\": \"%s\"}", deviceNameToBePut);

			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public static String cancelDREvent(TestCaseInputs inputs, int eventID, String deviceID) throws Exception {
		String result = "";
		String urlString = "";
		if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("Production")) {
			urlString = "https://api.honeywell.com/v1/demandresponse/event/" + eventID + "?apikey=";
		} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("CHIL Stage (Azure)")) {
			urlString = "http://connectedhome-qa.apigee.net/v1/demandresponse/event/" + eventID + "?apikey=";
		}
		urlString = urlString + getAPIGEEClientID(inputs);
		String headerData = String.format("{\"devices\": [\"%s\"]}", deviceID);
		HttpURLConnection deleteResponse = null;
		try {
			URL url = new URL(urlString);
			deleteResponse = (HttpURLConnection) url.openConnection();
			deleteResponse.setRequestMethod("DELETE");
			deleteResponse.setRequestProperty("content-type", "application/json");
			deleteResponse.setRequestProperty("authorization", "Bearer " + getAPIGEEAccessToken(inputs));
			deleteResponse.setDoOutput(true);
			if (!headerData.equals("")) {
				deleteResponse.setRequestProperty("content-length", String.valueOf(headerData.length()));
				OutputStream os = deleteResponse.getOutputStream();
				os.write(headerData.getBytes("UTF-8"));
				os.flush();
			}
			System.out.println(deleteResponse.getResponseCode());
			if (deleteResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				result = "Successfully cancelled DR Event with eventID : " + eventID;
			} else {
				BufferedReader rd = new BufferedReader(
						new InputStreamReader((InputStream) deleteResponse.getContent()));
				StringBuffer r = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					r.append(line);
				}
				result = "Failed to cancel DR event HTTP Error Code : " + deleteResponse.getResponseCode()
						+ ". Error Message : " + r.toString();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		System.out.println(result);
		return result;
	}

	public static String postDREvent(TestCaseInputs inputs, HashMap<String, String> headerValues) throws Exception {

		String eventID = "";
		try {
			Random rn = new Random();
			String urlString = "";
			int randomInt = rn.nextInt(1000000);
			if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("Production")) {
				urlString = "https://api.honeywell.com/v1/demandresponse/event/" + randomInt + "?apikey=";
				System.out.println("Event ID - " + randomInt);
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("CHIL Stage (Azure)")) {
				urlString = "http://connectedhome-qa.apigee.net/v1/demandresponse/event/" + rn.nextInt(1000000)
						+ "?apikey=";
			}
			urlString = urlString + getAPIGEEClientID(inputs);
			boolean isOptOutable = Boolean.parseBoolean(headerValues.get("isOptOutable"));
			boolean isLocallyOptOutable = Boolean.parseBoolean(headerValues.get("isLocallyOptOutable"));
			int randomizationInterval = Integer.parseInt(headerValues.get("randomizationInterval"));
			String headerData = String.format(
					"{\"startTime\":\"%s\",\"isOptOutable\":%s,\"isLocallyOptOutable\":%s,\"randomizationInterval\":%s,\"dutyCyclePeriod\":\"%s\",",
					headerValues.get("startTime"), isOptOutable, isLocallyOptOutable, randomizationInterval,
					headerValues.get("dutyCyclePeriod"));

			// 111
			if (headerValues.containsKey("phase1_duration") && headerValues.containsKey("phase2_duration")
					&& headerValues.containsKey("phase3_duration")) {
				headerData = headerData
						+ String.format("\"phase1\":{\"duration\":\"%s\",", headerValues.get("phase1_duration"));
				if (headerValues.containsKey("phase1_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase1_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase1_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase1_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase1_dutyCycle"));

				headerData = headerData
						+ String.format("\"phase2\":{\"duration\":\"%s\",", headerValues.get("phase2_duration"));
				if (headerValues.containsKey("phase2_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase2_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase2_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase2_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase2_dutyCycle"));

				headerData = headerData
						+ String.format("\"phase3\":{\"duration\":\"%s\",", headerValues.get("phase3_duration"));
				if (headerValues.containsKey("phase3_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase3_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase3_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase3_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase3_dutyCycle"));
			}

			// 110
			else if (headerValues.containsKey("phase1_duration") && headerValues.containsKey("phase2_duration")
					&& !headerValues.containsKey("phase3_duration")) {
				headerData = headerData
						+ String.format("\"phase1\":{\"duration\":\"%s\",", headerValues.get("phase1_duration"));
				if (headerValues.containsKey("phase1_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase1_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase1_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase1_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase1_dutyCycle"));

				headerData = headerData
						+ String.format("\"phase2\":{\"duration\":\"%s\",", headerValues.get("phase2_duration"));
				if (headerValues.containsKey("phase2_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase2_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase2_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase2_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase2_dutyCycle"));

			}

			// 100
			if (headerValues.containsKey("phase1_duration") && !headerValues.containsKey("phase2_duration")
					&& !headerValues.containsKey("phase3_duration")) {
				headerData = headerData
						+ String.format("\"phase1\":{\"duration\":\"%s\",", headerValues.get("phase1_duration"));
				if (headerValues.containsKey("phase1_heatDelta")) {
					int heatDelta = Integer.parseInt(headerValues.get("phase1_heatDelta"));
					headerData = headerData + String.format("\"heatDelta\":%s,", heatDelta);
				}
				if (headerValues.containsKey("phase1_coolDelta")) {
					int coolDelta = Integer.parseInt(headerValues.get("phase1_coolDelta"));
					headerData = headerData + String.format("\"coolDelta\":%s,", coolDelta);
				}
				headerData = headerData + String.format("\"dutyCycle\":%s},", headerValues.get("phase1_dutyCycle"));

			}

			headerData = headerData + String.format("\"devices\":[\"%s\"]}", headerValues.get("devices"));
			System.out.println(headerData);
			HttpURLConnection postResponse = null;
			URL url = new URL(urlString);
			postResponse = (HttpURLConnection) url.openConnection();
			postResponse.setRequestMethod("POST");
			postResponse.setRequestProperty("content-type", "application/javascript");
			postResponse.setRequestProperty("authorization", "Bearer " + getAPIGEEAccessToken(inputs));
			postResponse.setDoOutput(true);
			if (!headerData.equals("")) {
				postResponse.setRequestProperty("content-length", String.valueOf(headerData.length()));
				OutputStream os = postResponse.getOutputStream();
				os.write(headerData.getBytes("UTF-8"));
				os.flush();
			}

			if (postResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				BufferedReader rd = new BufferedReader(new InputStreamReader((InputStream) postResponse.getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				JSONObject obj = new JSONObject(result.toString());
				eventID = String.valueOf(obj.getInt("eventId"));
			} else {
				System.out.println(postResponse.getResponseMessage());
				throw new Exception(postResponse.getResponseMessage());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return eventID;
	}

	public static String putDREnrollement(TestCaseInputs inputs, String deviceID, String deviceMAC, String locationID)
			throws Exception {
		String result = "";
		String urlString = "";
		if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("Production")) {
			urlString = "https://api.honeywell.com/v1/demandresponse/enrollment?apikey=";
		} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equalsIgnoreCase("CHIL Stage (Azure)")) {
			urlString = "https://connectedhome-qa.apigee.net/v1/demandresponse/enrollment?apikey=";
		}
		urlString = urlString + getAPIGEEClientID(inputs);
		String headerData = String.format("{\"DeviceId\":\"%s\",\"DeviceMac\":\"%s\",\"LocationId\":\"%s\"}", deviceID,
				deviceMAC, locationID);

		HttpURLConnection putResponse = null;
		try {
			URL url = new URL(urlString);
			putResponse = (HttpURLConnection) url.openConnection();
			putResponse.setRequestMethod("PUT");
			putResponse.setRequestProperty("content-type", "application/javascript");
			putResponse.setRequestProperty("authorization", "Bearer " + getAPIGEEAccessToken(inputs));
			putResponse.setRequestProperty("userId", "1000");
			putResponse.setDoOutput(true);
			if (!headerData.equals("")) {
				putResponse.setRequestProperty("content-length", String.valueOf(headerData.length()));
				OutputStream os = putResponse.getOutputStream();
				os.write(headerData.getBytes("UTF-8"));
				os.flush();
			}

			if (putResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = "Device " + deviceMAC + " has successfully enrolled to DR";
			} else if (putResponse.getResponseCode() == HttpURLConnection.HTTP_CONFLICT) {
				result = "Device " + deviceMAC + " is already enrolled to DR";
			} else {
				result = "Failed to enroll device : " + deviceMAC + " to DR. HTTP Error Code : "
						+ putResponse.getResponseCode();
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return result;
	}

	public int changeFanMode(long locationID, String deviceID, String FanMode) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/fan/changeableValues", locationID, deviceID);
				String headerData = String.format("{\"mode\":\"%s\"}", FanMode);
				try {
					result = doPostRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int setCoolSetPoint(long locationID, String deviceID, int setpoint) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/thermostat/coolSetpoint", locationID, deviceID);
				String headerData = String.format("{\"thermostatSetpoint\":\"%s\"}", setpoint);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int setHeatSetPoint(long locationID, String deviceID, double setpoint) {
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/%s/thermostat/HeatSetpoint", locationID, deviceID);
				String headerData = String.format("{\"thermostatSetpoint\":\"%s\"}}", setpoint);
				try {
					result = doPutRequest(url, headerData).getResponseCode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public int putAlertStatusForSensor(long locationID, String deviceID, String isActive) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = chilURL
					+ String.format("api/v3/locations/%s/devices/%s/NotificationConfigurations", locationID, deviceID);
			String headerData = String.format("{\"notificationTypeSubscriptions\" :[" + "{\"notificationTypeId\":40014,"
					+ "\"isActive\":\"%s\"}]}", isActive);
			result = doPutRequest(url, headerData).getResponseCode();
		} else {
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}

	public int activateDeletedUserAccount() throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			String headerData = " ";
			url = chilURL + "api/v2/user";
			headerData = String.format("{  \n  \"email\": \"" + inputs.getInputValue("DELETEDUSERID")
					+ "\",  \n  \"firstName\": \"Test\",  \n  \"lastName\": \"QA\",  \n  \"password\": \""
					+ inputs.getInputValue("PASSWORD") + "\",  \n  \"acceptedEULAId\": 1\n}");
			try {
				result = doPostRequest(url, headerData).getResponseCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public JSONObject getUserActivationID(String deletedUserEmail) throws Exception {
		JSONObject userActivationID = new JSONObject();
		if (isConnected) {
			String url = chilURL + String.format("api/useractivation?email=%s", deletedUserEmail);
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				userActivationID = new JSONObject(html.toString().trim());
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return userActivationID;
	}

	public int putDeletedUserActivation(String deletedUserEmail, String deletedUserActivationID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = "";
			String headerData = "";
			url = this.chilURL + "api/useractivation";
			try {
				headerData = String.format("{  \n  \"Email\": \"" + deletedUserEmail + "\", \n  \"ActivationID\": \""
						+ deletedUserActivationID + "\", \n}");
				result = doPutRequest(url, headerData).getResponseCode();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return result;
	}

	public boolean getConnectionForUserAccount() throws Exception {
		try {
			String input = null;
			URL url = new URL(this.chilURL + "api/v2/Session");
			if (!inputs.getInputValue("DELETEDUSERID").isEmpty() && inputs.getInputValue("DELETEDUSERID") != null) {
				input = "{\"username\": \"" + inputs.getInputValue("DELETEDUSERID") + "\",\"password\": \""
						+ inputs.getInputValue("PASSWORD") + "\"}";
			} else {
				input = "{\"username\": \"" + inputs.getInputValue("USERID") + "\",\"password\": \""
						+ inputs.getInputValue("PASSWORD") + "\"}";
			}
			chilConnection = (HttpURLConnection) url.openConnection();

			chilConnection.setDoOutput(true);
			chilConnection.setRequestMethod("POST");
			chilConnection.setRequestProperty("content-type", "application/json");
			chilConnection.setRequestProperty("content-length", String.valueOf(input.length()));

			OutputStream os = chilConnection.getOutputStream();

			os.write(input.getBytes());
			os.flush();

			if (chilConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				BufferedReader in = new BufferedReader(new InputStreamReader(chilConnection.getInputStream()));

				String inputLine;
				StringBuffer html = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}

				in.close();

				JSONObject jsonObj = new JSONObject(html.toString().trim());

				cookie = chilConnection.getHeaderField("Set-Cookie");

				bodyToken = jsonObj.get("bodytoken").toString();

				sessionID = jsonObj.get("sessionID").toString();

				verificationToken = chilConnection.getHeaderField("RequestVerificationToken");

				userID = jsonObj.getJSONObject("user").getInt("userID");
				JSONArray jsonUsers = (JSONArray) ((JSONObject) jsonObj.get("user")).get("locationRoleMapping");
				for (int i = 0; i < jsonUsers.length(); i++) {
					JSONObject tempJSON = (JSONObject) jsonUsers.get(i);
					locations.put(tempJSON.getString("locationName"), tempJSON.getLong("locationID"));
				}

				isConnected = true;
			} else {
				isConnected = false;
			}

		} catch (Exception e) {
			throw new Exception("Error Occured: " + e.getMessage());
		}
		return isConnected;
	}

	public String getWeather(long locationID) throws Exception {
		JSONObject weather = new JSONObject();
		JSONObject realFeelTemperature = new JSONObject();
		String weatherTemperature = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/Weather", String.valueOf(locationID));
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				weather = new JSONObject(html.toString().trim());
				realFeelTemperature = weather.getJSONObject("realFeelTemperature");
				weatherTemperature = realFeelTemperature.get("value").toString();
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return weatherTemperature;
	}

	public String getHumidty(long locationID) throws Exception {
		JSONObject weather = new JSONObject();
		// JSONObject relativeHumidity = new JSONObject();
		String humidity = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/Weather", String.valueOf(locationID));
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				weather = new JSONObject(html.toString().trim());
				humidity = weather.get("relativeHumidity").toString();
				// humidity = relativeHumidity.toString();
				System.out.println(humidity);
				inputs.setInputValue("WEATHER_HUMIDITY_FROM_CHIL", humidity);
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return humidity;
	}

	public String getMinTemperature(long locationID) throws Exception {
		JSONObject weather = new JSONObject();
		JSONObject weatherMinTemperature = new JSONObject();
		String minTemperature = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/Weather", String.valueOf(locationID));
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				weather = new JSONObject(html.toString().trim());
				weatherMinTemperature = weather.getJSONObject("minimum");
				minTemperature = weatherMinTemperature.get("value").toString();
				inputs.setInputValue("WEATHER_MIN_TEMP_IN_CELSIUS_FROM_CHIL", minTemperature);
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return minTemperature;
	}

	public String getMaxTemperature(long locationID) throws Exception {
		JSONObject weather = new JSONObject();
		JSONObject weatherMaxTemperature = new JSONObject();
		String maxTemperature = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s/Weather", String.valueOf(locationID));
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				weather = new JSONObject(html.toString().trim());
				weatherMaxTemperature = weather.getJSONObject("maximum");
				maxTemperature = weatherMaxTemperature.get("value").toString();
				inputs.setInputValue("WEATHER_MIN_TEMP_IN_CELSIUS_FROM_CHIL", maxTemperature);
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return maxTemperature;
	}

	public String getCountryName(long locationID) throws Exception {
		JSONObject locationDetails = new JSONObject();
		String countryName = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations/%s", String.valueOf(locationID));
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				locationDetails = new JSONObject(html.toString().trim());
				countryName = locationDetails.get("country").toString();
				System.out.println(countryName);
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return countryName;
	}
	
	public String getDefaultSelectedLocationName() throws Exception {
		JSONObject locationDetails = new JSONObject();
		String defaultSelectedLocationName = null;
		if (isConnected) {
			String url = chilURL + String.format("api/locations");
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				JSONArray jArray = new JSONArray(html.toString());
				locationDetails = jArray.getJSONObject(0);
				defaultSelectedLocationName = locationDetails.getString("name");
			} else {
				throw new Exception("Unable to connect to CHIL");
			}
		}
		return defaultSelectedLocationName;
	}
	
	public MultiValueMap getDevicesListWithDeviceTypeForALocation(String locationName) throws Exception {
		long locationID;
		JSONObject devicesList = new JSONObject();
		JSONArray devices = new JSONArray();
		MultiValueMap multiValueMap = new MultiValueMap();
		if (isConnected) {
			locationID = this.getLocationID(locationName);
			String url = chilURL + String.format("api/locations/%s", locationID);
			HttpURLConnection connection = doGetRequest(url);
			if (connection != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer html = new StringBuffer();
				while (!in.ready()) {
				}
				while ((inputLine = in.readLine()) != null) {
					html.append(inputLine);
				}
				in.close();
				devicesList = new JSONObject(html.toString().trim());
				devices = devicesList.getJSONArray("devices");
				for (int i = 0; i < devices.length(); i++) {
					JSONObject tempJSON = (JSONObject) devices.get(i);
					if (tempJSON.has("deviceDetails")) {
						JSONObject deviceModel = tempJSON.getJSONObject("deviceDetails");
						if (deviceModel.has("configurations")) {
							JSONObject deviceConfiguration = deviceModel.getJSONObject("configurations");
							if ((deviceConfiguration.getString("model") != null)
									&& (!deviceConfiguration.getString("model").isEmpty())
									&& (deviceConfiguration.getString("model").equalsIgnoreCase("DAS"))) {
								JSONArray onBoardDevicesList = deviceModel.getJSONArray("onboardDevices");
								for (int j = 0; j < onBoardDevicesList.length(); j++) {
									multiValueMap.put(onBoardDevicesList.getJSONObject(j).getString("deviceType"),
											onBoardDevicesList.getJSONObject(j).getString("userDefinedDeviceName"));
								}
							}
						}
					}
					multiValueMap.put(tempJSON.getString("deviceType"), tempJSON.getString("userDefinedDeviceName"));
				}
			} else {
				throw new Exception("Not connected to CHIL");
			}
		}
		return multiValueMap;
	}
}
