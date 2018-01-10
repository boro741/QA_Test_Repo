package com.honeywell.CHIL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;

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
			if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals(
					"Production")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"CHIL_URL_PRODUCTION");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("CHIL Int (Azure)")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"CHIL_INT");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("CHIL Dev(Dev2)")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"CHIL_DEV2");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("CHIL Stage (Azure)")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"CHIL_URL_STAGING");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("Load Testing")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_LOAD_TESTING");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("Chil Das(QA)")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC,
						"CHIL_DAS_QA");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT)
					.equals("Chil Das(Test)")) {
				chilURL = SuiteConstants.getConstantValue(
						SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_TEST");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
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

}
