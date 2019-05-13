package com.honeywell.keywords.lyric.Katana.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.json.JSONObject;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class ProMonitoringUtils {


	public static JSONObject getKatanaDealerInfo(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				if (chUtil.isConnected()) {
					long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
					if (locationID == -1) {
						throw new Exception("Location: " + inputs.getInputValue("LOCATION1_NAME") + " not found");
					}
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					String DeviceID = devInfo.getDeviceID();
					String url = LyricUtils.getCHILURL(testCase, inputs) + "api/v1/locations/" + locationID+"/devices/"+DeviceID+"/promonitoring";
					HttpURLConnection connection = chUtil.doGetRequest(url);
					try {
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
							jsonObject = new JSONObject(html.toString().trim());
						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"Get Promonitoring Dealer  : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}
					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get Promonitoring Dealer  Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}
				}
			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Promonitoring Dealer  Information  : Unable to connect to CHAPI.");
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Promonitoring Dealer  Information  : Unable to get location. Error occured - " + e.getMessage());
			jsonObject = null;
		}
		return jsonObject;
	}

	public static String getDealerTitle(TestCases testCase, TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String dealerTitle = null;
		try {
			getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
			dealerTitle = getKatanaDealerInfo.getJSONObject("dealerDetailsResponse").getString("dealerName");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return dealerTitle;
	}

	public static String getPrimaryContactList(TestCases testCase, TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String firstName;
		String lastName;
		String PrimaryContactList = null;
		try {
			getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
			firstName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(0).getString("firstName");
			lastName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(0).getString("lastName");
			PrimaryContactList = firstName + " " + lastName;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return PrimaryContactList;
	}

	public static String getSecondaryContactList(TestCases testCase, TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String firstName;
		String lastName;
		String SecondaryContactList = null;
		try {
			getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
			firstName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(1).getString("firstName");
			lastName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(1).getString("lastName");
			SecondaryContactList = firstName + " " + lastName;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SecondaryContactList;
	}

	public static String getTertiaryContactList(TestCases testCase, TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String firstName;
		String lastName;
		String TertiaryContactList = null;
		getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
		firstName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(2).getString("firstName");
		lastName = getKatanaDealerInfo.getJSONObject("emergencyInfo").getJSONArray("emergencyContacts").getJSONObject(2).getString("lastName");
		TertiaryContactList = firstName + " " + lastName;
		return TertiaryContactList;
	}

	public static String getSecurityPassPharse(TestCases testCase,TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String securityPharse = null;
		getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
		securityPharse = getKatanaDealerInfo.getJSONObject("emergencyInfo").getString("alarmDismissalPassPhrase");
		return securityPharse;
	}
	
	public static String getDuressPassPharse(TestCases testCase,TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String duressPassPharse = null;
		getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
		duressPassPharse = getKatanaDealerInfo.getJSONObject("emergencyInfo").getString("duressPassPhrase");
		return duressPassPharse;
	}
	
	public static String getHomeOwnerInfo(TestCases testCase,TestCaseInputs inputs) throws Exception {
		JSONObject getKatanaDealerInfo;
		String ownerFirstName ;
		String ownerLastName ;
		getKatanaDealerInfo = getKatanaDealerInfo(testCase, inputs);
		ownerFirstName = getKatanaDealerInfo.getJSONObject("customerInfo").getString("firstName");
		ownerLastName = getKatanaDealerInfo.getJSONObject("customerInfo").getString("lastName");
		String HomeOwnerName = ownerFirstName + " " +  ownerLastName;
		return HomeOwnerName;
	}

}

