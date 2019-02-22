package com.honeywell.lyric.das.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.Dashboard;

public class FRUtils {

	public static boolean navigateToGlobalDrawerFromDashBoard(TestCases testCase) {
		Dashboard dScreen = new Dashboard(testCase);
		if (dScreen.clickOnGlobalDrawerButton()) {

		}
		return true;
	}

	public static Boolean checkLocationPermittedForFR(TestCases testCase, TestCaseInputs inputs, Boolean isFRPermitted,
			long locationID) {
		ArrayList<String> dasDeviceName = new ArrayList<String>();
		String html = "";
		Boolean isFREnabledInLocation = false;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {

				if (chUtil.isConnected()) {
					String chilURL = LyricUtils.getCHILURL(testCase, inputs);
					try {
						String url = chilURL + "api/v3/locations/" + locationID;
						HttpURLConnection connection = chUtil.doGetRequest(url);
						try {
							if (connection != null) {

								BufferedReader br = new BufferedReader(
										new InputStreamReader(connection.getInputStream()));
								StringBuilder sb = new StringBuilder();
								String line;
								while ((line = br.readLine()) != null) {
									sb.append(line + "\n");
								}
								br.close();
								html = sb.toString();
								JSONObject tempJSON = new JSONObject(html.toString().trim());
								isFREnabledInLocation = false;
								if (!tempJSON.isNull("devices")) {
									JSONArray jsonDevices = (JSONArray) tempJSON.get("devices");
									for (int j = 0; j < jsonDevices.length(); j++) {
										JSONObject tempJSON1 = jsonDevices.getJSONObject(j);
										if (tempJSON1.getString("deviceType").toUpperCase().equals("HONDAS")) {
											if (!dasDeviceName.contains(tempJSON1.getString("deviceID"))) {
												dasDeviceName.add(tempJSON1.getString("deviceID"));
											}
										}

									}
								}
								if (!tempJSON.isNull("configuration")) {
									JSONObject tempJSON3 = tempJSON.getJSONObject("configuration");
									if (!tempJSON3.isNull("faceRecognition")) {
										JSONObject tempJSON2 = tempJSON.getJSONObject("configuration")
												.getJSONObject("faceRecognition");
										if (tempJSON2.getBoolean("enabled")) {
											isFREnabledInLocation = true;

										}

									}
								}
								if (dasDeviceName.isEmpty()) {
									if (isFREnabledInLocation == isFRPermitted) {
										Keyword.ReportStep_Pass(testCase,
												"No DAS Device Available in the given location and FR Disabled");
									} else {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FRAMEWORK_CONFIGURATION,
												"checkLocationPermittedForFR : No DAS Device Found- But FR is Permitted");
									}
								}

								if (isFREnabledInLocation != isFRPermitted) {
									if (isFRPermitted)
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FRAMEWORK_CONFIGURATION,
												"checkLocationPermittedForFR : FR is not enabled in any locations of the user but it should have enabled");
									else if (!isFRPermitted)
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FRAMEWORK_CONFIGURATION,
												"checkLocationPermittedForFR : FR is enabled in any locations of the user but it should not have enabled/permitted");

								}

							}

						} catch (IOException e) {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"IsDASDeviceAvailableAndRegistered : Error occured - " + e.getMessage());

						}

					} catch (Exception e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"checkLocationPermittedForFR  : Error occured - " + e.getMessage());

					}
				}

			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"checkLocationPermittedForFR  : Unable to connect to CHIL.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"checkLocationPermittedForFR  : Unable to get location. Error occured - " + e.getMessage());

		}

		return isFREnabledInLocation == isFRPermitted;

	}

	public static Boolean IsDASDeviceAvailableAndRegistered(TestCases testCase, TestCaseInputs inputs,
			Boolean expectedBehaviour, long locationID) {
		String html = "";
		Boolean actualBehaviour = false;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {

				if (chUtil.isConnected()) {
					String chilURL = LyricUtils.getCHILURL(testCase, inputs);
					if (locationID > 0) {
						String url = chilURL + "api/v3/locations/" + locationID;
						HttpURLConnection connection = chUtil.doGetRequest(url);

						try {

							if (connection != null) {

								BufferedReader br = new BufferedReader(
										new InputStreamReader(connection.getInputStream()));
								StringBuilder sb = new StringBuilder();
								String line;
								while ((line = br.readLine()) != null) {
									sb.append(line + "\n");
								}
								br.close();
								html = sb.toString();

								JSONObject tempJSON = new JSONObject(html.toString().trim());
								if (!tempJSON.isNull("devices")) {
									JSONArray jsonDevices = (JSONArray) tempJSON.get("devices");
									for (int j = 0; j < jsonDevices.length(); j++) {
										JSONObject tempJSON1 = jsonDevices.getJSONObject(j);
										if (tempJSON1.getString("deviceType").toUpperCase().equals("HONDAS")) {
											if (tempJSON1.getBoolean("isProvisioned")) {
												actualBehaviour = true;
											} else {

												actualBehaviour = false;
											}
										}

									}
								}

							} else {
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
										"IsDASDeviceAvailableAndRegistered : Get Location By ID Failed"
												+ inputs.getInputValue("LOCATION1_NAME"));
							}

						} catch (IOException e) {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"IsDASDeviceAvailableAndRegistered : Error occured - " + e.getMessage());

						}
					}
				} else {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"IsDASDeviceAvailableAndRegistered  :Unable to connect to CHIL.");
				}

			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"IsDASDeviceAvailableAndRegistered  : Post Session Failed");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"IsDASDeviceAvailableAndRegistered : Unable to get location. Error occured - " + e.getMessage());

		}

		return expectedBehaviour == actualBehaviour;
	}

	public static void updateLocationThroughCHIL(TestCases testCase, TestCaseInputs inputs, String body,
			long locationID) {
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {

				if (chUtil.isConnected()) {
					if (locationID > 0) {

						int result = chUtil.putLocationV2(locationID, body);

						try {

							if (result == 200) {

								Keyword.ReportStep_Pass(testCase, "Updated Location Successflly");

							} else {
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
										"updateLocationThroughCHIL : Location not updated");
							}

						} catch (Exception e) {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"updateLocationThroughCHIL : Error occured - " + e.getMessage());

						}
					}
				}

				else {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"updateLocationThroughCHIL  : Unable to connect to CHIL.");
				}
			}

			else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"updateLocationThroughCHIL  : Unable to call Post Session.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"updateLocationThroughCHIL : Unable to update location. Error occured - " + e.getMessage());

		}
	}

	public static long createLocationThroughCHIL(TestCases testCase, TestCaseInputs inputs, String body) {
		String html = "";
		long locationID = 0;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {

				if (chUtil.isConnected()) {
					String chilURL = LyricUtils.getCHILURL(testCase, inputs);
					HttpURLConnection connection = chUtil.doPostRequest(chilURL, body);

					try {

						if (connection != null) {

							BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							StringBuilder sb = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								sb.append(line + "\n");
							}
							br.close();
							html = sb.toString();
							JSONObject jsonObject = new JSONObject(html.toString().trim());
							locationID = jsonObject.getLong("locationID");
						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"createLocationThroughCHIL : Create Location Failed");
						}
					} catch (Exception e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"createLocationThroughCHIL : Error occured - " + e.getMessage());

					}

				}

				else {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"createLocationThroughCHIL  : Unable to connect to CHIL.");
				}
			}

			else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"createLocationThroughCHIL  : Unable to call Post Session.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"createLocationThroughCHIL : Unable to create location. Error occured - " + e.getMessage());

		}
		return locationID;
	}

}
