package com.honeywell.CHIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

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

	public CHILUtil(TestCaseInputs inputs) {
		try {
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

		} catch (Exception e) {
			e.printStackTrace();
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

	public boolean getConnection() {
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
			System.out.println("Error Occured: " + e.getMessage());
			isConnected = false;
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

	public HttpURLConnection doPostRequest(String urlString, String headerData) {

		HttpURLConnection postResponse = null;
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}

		return postResponse;
	}

	public HttpURLConnection doGetRequest(String urlString) {
		HttpURLConnection getResponse = null;
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}

		return getResponse;
	}

	public HttpURLConnection doDeleteRequest(String urlString, boolean... addClientHeader) {
		HttpURLConnection getResponse = null;
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
		}

		return getResponse;
	}

	public HttpURLConnection doPutRequest(String urlString, String headerData) {

		HttpURLConnection postResponse = null;
		try {
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

		} catch (IOException e) {
			e.printStackTrace();
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

	public int clearAlarm(long locationID, String deviceID, TestCases testCase) {

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
						String url = this.chilURL + "api/v3/locations/" + locationID + "/devices/" + deviceID
								+ "/partitions/1/dismiss";
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

	public int setBaseStationMode(long locationID, String deviceID, String modeToSet, TestCases testCase) {
		int result = -1;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				if (locationID == -1) {
					return -1;
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
					try {
						result = doPutRequest(url, headerData).getResponseCode();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Camera Configuration Information  : Unable to connect to CHAPI.");
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Camera Configuration Information  : Unable to get location. Error occured - "
							+ e.getMessage());
			result = -1;
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
}
