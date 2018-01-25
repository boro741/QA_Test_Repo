package com.honeywell.CHIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;

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

		if (postResponse.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
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

	public int createSchedule(TestCaseInputs inputs, String scheduleType, long locationID, String deviceID,
			String jasperStatType) throws Exception {
		int result = -1;
		if (isConnected) {
			String headerData = " ";
			String url = " ";
			if (scheduleType.equals("Time")) {
				url = chilURL + "api/locations/" + locationID + "/thermostat/TimedScheduleTemplate";
				if (jasperStatType.equalsIgnoreCase("NA")) {
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
			throw new Exception("Not connected to CHIL");
		}
		return result;
	}
}
