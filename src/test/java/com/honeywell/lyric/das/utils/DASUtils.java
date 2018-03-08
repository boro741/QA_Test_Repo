package com.honeywell.lyric.das.utils;

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

public class DASUtils {

	public static JSONObject getDASCameraConfig(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				if (chUtil.isConnected()) {
					long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
					if (locationID == -1) {
						throw new Exception("Location: " + inputs.getInputValue("LOCATION1_NAME") + " not found");
					}
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					String cameraDeviceID = devInfo.getDASCameraID();
					String url = LyricUtils.getCHILURL(testCase, inputs) + "api/locations/" + locationID+"/devices/"+cameraDeviceID+"/config?CameraKind=HONDAS";
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
									"Get Camera Configuration : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}
					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get Camera Configuration Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}
				}
			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Camera Configuration Information  : Unable to connect to CHAPI.");
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Camera Configuration Information  : Unable to get location. Error occured - " + e.getMessage());
			jsonObject = null;
		}
		return jsonObject;
	}

}
