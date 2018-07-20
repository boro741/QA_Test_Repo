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
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

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

					} else if (modeToSet.toUpperCase().contains("OFF")) {
						url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/Disarm";
						headerData = "{\"EnableSilentMode\":\"true\""+"}";
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

	public int changeLocationGeofenceStatus(boolean turnOn, long locationID) throws Exception
	{
		if (isConnected) {
			String url = chilURL + String.format("api/v2/locations/%s", locationID);
			String headerData = String.format("{\"country\":\"US\",\"geoFenceEnabled\":%s,\"locationID\":%s,\"originCoordinates\":{\"TAG\":\"CoordinatesType\",\"latitude\":12.9251375,\"longitude\":77.68699},\"radius\":878}", turnOn,locationID);
			return doPutRequest(url, headerData).getResponseCode();
		}
		else {
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
			url = chilURL + "api/locations/" + locationID + "/Devices/" + chapiDeviceId+ "/hub";
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
	
	public int deleteSensor(long locationID,String deviceID,String sensorID,int partitionID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID+"/partitions/"+partitionID+"/Sensors/"+sensorID;
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}
	
	public int deleteKeyfob(long locationID,String deviceID,String keyFobID,int partitionID) throws Exception {
		int result = -1;
		if (isConnected) {
			String url = " ";
			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID+"/partitions/"+partitionID+"/Keyfobs/"+keyFobID;
			try {
				result = doDeleteRequest(url).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}

		}
		return result;
	}
	
	public int postSensor(long locationID,String deviceID,int partitionID,String serialNumber) throws Exception{
		int result = -1;
		String headerData=" ";
		String url = " ";
		
		
		if (isConnected) {
			
			
		    headerData = String.format("{\"SerialNo\":\"%s\",\"Name\":\"%s\",\"ResponseType\":\"Perimeter\",\"Sensitivity\":\"0\",\"Chime\":\"sensor\"}",serialNumber,"Sensor"+new Random().nextInt(200));
			
			url = chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID+"/partitions/"+partitionID+"/Sensors";
			try {
				result = doPostRequest(url,headerData).getResponseCode();
			} catch (IOException e) {
				throw new Exception(e.getMessage());
			}
			

		}
		return result;
	}

	
	public int postSensorDiscovery(long locationID,String deviceID,boolean isEnabled) throws Exception{
		int result = -1;
		String headerData=" ";
		String url = " ";
		
		
		if (isConnected) {
			
			// enable sensor
			if(isEnabled)
			    headerData = String.format("{\"mode\":\"Sensor\",\"TimeOut\":30,\"Id\":null}");
			else
				headerData = String.format("{\"mode\":\"Disable\",\"TimeOut\":30,\"Id\":null}");
				
				url = chilURL + "api/v3/locations/"+locationID+"/devices/"+deviceID+"/Discovery?partitionid=1";
				try {
					result = doPostRequest(url,headerData).getResponseCode();
				} catch (IOException e) {
					throw new Exception(e.getMessage());
				}
		}
					return result;
	}

	public void FeedDataIntoIOTHub(String deviceID,String payload,String xappUrl,String from,String method) throws Exception{
		DeviceClient client = null;
		try {
			String iotHubConString="";
			if(chilURL.contains("childev")) {
				iotHubConString="HostName=granite-dev-das01-iothub.azure-devices.net;DeviceId=B82CA0000740;SharedAccessKey=5mWRiN796NreGeCFgQqLzpmcymQW0c0pqkWUr+FrVXs=";
			}
			
			client = new DeviceClient(iotHubConString, IotHubClientProtocol.HTTPS);
			client.open();
			
			Message message = new Message(payload.getBytes(Charset.forName("ASCII")));
			message.setProperty("x-app-url", xappUrl);
            message.setProperty("deviceId", deviceID);
            message.setProperty("from",from);
            message.setProperty("mac", deviceID);
            message.setProperty("method",method);
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
	
	public int putLocationV2(long locationID,String body){
		int result = -1;
		try {
			if (isConnected) {
				String url = chilURL + String.format("api/v2/locations/%s", locationID);
				String headerData = String.format(
						body);
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
						String url = chilURL + "api/v3/locations/" + locationID+"/devices/"+deviceID+"/partitions/1/DisArm";
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
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;

	}
	
	public int RegisterAndActivateCamera(long locationID, String deviceID, TestCases testCase)
	{
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
		
		//Register Camera
		try {
			if (isConnected) {
				String url = chilURL
						+ String.format("api/locations/%s/devices/hub", locationID);
				String headerData = String.format(
						"{\"DeviceId\": \"%s\",\"DeviceTypes\": \"%s\",\"Name\": \"%s\",\"mac\": \"%s\"}", deviceID, deviceType, name, mac);
				try {
					result = doPostRequest(url, headerData);
					
					if (result.getResponseCode() == HttpURLConnection.HTTP_CREATED || result.getResponseCode() == HttpURLConnection.HTTP_OK)
					{
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
								            
								        	String url1 = chilURL
													+ String.format("provisioning/api/devices/activate");
											String headerData1 = String.format(
													"{\"deviceId\": \"%s\",\"registrationId\": \"%s\",\"certificate\": \"%s\"}", deviceID, registrationId, certificate);
											result1 = doPostRequest(url1, headerData1).getResponseCode();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		//Get Location
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
			            for(int j=0;j<two.length();){
			                JSONObject ingredObject= two.getJSONObject(j);
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
	
}
