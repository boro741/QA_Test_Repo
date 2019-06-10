package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.MultiValueMap;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.screens.Dashboard;

public class VerifyGroupingOfDevicesInDashboardScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> groupingOfDevices;
	public boolean flag = true;
	public DataTable data;
	public static MultiValueMap LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL = null;
	public List<String> devicesListInDashboardScreen = new ArrayList<String>();

	public VerifyGroupingOfDevicesInDashboardScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> groupingOfDevices, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.groupingOfDevices = groupingOfDevices;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	@KeywordStep(gherkins = "^user verifies if the \"(.+)\" and displayed:$")
	public boolean keywordSteps() throws KeywordException {
		Dashboard d = new Dashboard(testCase);

		// Get the devices list from CHIL
		try {
			@SuppressWarnings("resource")
			CHILUtil chilUtils = new CHILUtil(inputs);
			if (chilUtils.getConnection()) {
				LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL = chilUtils.getDevicesListWithDeviceTypeForALocation("S 2");
			}
			Keyword.ReportStep_Pass(testCase, "####LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL is: "
					+ LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get the devices list from Dashboard screen
		if (d.isLocationNameDisplayedInDashboardScreen()) {
			devicesListInDashboardScreen = d.getDevicesListDisplayedInDashboardScreen();
		}

		// Compare if devices are grouped in Dashboard screen
		for (int i = 0; i < data.getSize(); i++) {
			String parameter = data.getData(i, "GroupingOrderOfDevices");
			if (parameter.equalsIgnoreCase("SECURITY DEVICES AND ASSOCIATED SECURITY CAMERAS")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("HONDAS")) {
					Keyword.ReportStep_Pass(testCase, "DAS Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("HONDAS")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(0))) {
							Keyword.ReportStep_Pass(testCase, "First element is DAS: " + str);
							break;
						}
					}
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("BuiltIn")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(1))) {
							Keyword.ReportStep_Pass(testCase, "Second element is DAS Camera: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "DAS Device is not configured for the Location");
				}
			} else if (parameter.equalsIgnoreCase("CAMERA DEVICES")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("C1")) {
					Keyword.ReportStep_Pass(testCase, "C1 Camera Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("C1")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(2))) {
							Keyword.ReportStep_Pass(testCase, "Second element is C1: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "C1 device is not configured for the Location");
				}
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("C2")) {
					Keyword.ReportStep_Pass(testCase, "C2 Camera Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("C2")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(3))) {
							Keyword.ReportStep_Pass(testCase, "Third element is C2: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "C2 device is not configured for the Location");
				}
			} else if (parameter.equalsIgnoreCase("Z WAVE DEVICES")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("Z WAVE")) {
					Keyword.ReportStep_Pass(testCase, "Z Wave Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("Z WAVE")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(4))) {
							Keyword.ReportStep_Pass(testCase, "Fourth element is Z WAVE: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Z WAVE Device is not configured for the Location");
				}
			} else if (parameter.equalsIgnoreCase("THERMOSTATS")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("Jasper")) {
					Keyword.ReportStep_Pass(testCase, "JASPER Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("Jasper")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(5))) {
							Keyword.ReportStep_Pass(testCase, "Fifth element is Jasper: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Jasper Device is not configured for the Location");
				}
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("HoneyBadger")) {
					Keyword.ReportStep_Pass(testCase, "HoneyBadger Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("HoneyBadger")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(6))) {
							Keyword.ReportStep_Pass(testCase, "Sixth element is HoneyBadger: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Honey Badger Device is not configured for the Location");
				}
			} else if (parameter.equalsIgnoreCase("SKYBELL")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("Skybell")) {
					Keyword.ReportStep_Pass(testCase, "SKYBELL Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.get("Skybell")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(7))) {
							Keyword.ReportStep_Pass(testCase, "Seventh element is HoneyBadger: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Skybell Device is not configured for the Location");
				}
			} else if (parameter.equalsIgnoreCase("WATER LEAK DETECTOR")) {
				if (LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL.containsKey("Water Leak Detector")) {
					Keyword.ReportStep_Pass(testCase, "Water Leak Detector Device is configured for the Location");
					for (String str : (List<String>) LISTOFDEVICETYPEANDNAMEFORALOCATIONFROMCHIL
							.get("Water Leak Detector")) {
						if (str.equalsIgnoreCase(devicesListInDashboardScreen.get(8))) {
							Keyword.ReportStep_Pass(testCase, "Eigth element is HoneyBadger: " + str);
							break;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Water Leak Detector Device is not configured for the Location");
				}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
