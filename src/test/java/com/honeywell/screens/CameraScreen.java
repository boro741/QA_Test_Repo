package com.honeywell.screens;



import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;

import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

import io.appium.java_client.MobileElement;
;

public class CameraScreen extends MobileScreens{
	//private TestCases testCase;
	private static final String screenName = "DAS_VideoSolution";


	public CameraScreen(TestCases testCase) {
		super(testCase, screenName);
		this.testCase=testCase;
	}

	public boolean isCameraToggleisOn(TestCases testCase) {
		MobileElement element= MobileUtils.getMobElement(objectDefinition, testCase, "Camera_on");
		boolean flag = element.isSelected();

		if(flag==true) {

			Keyword.ReportStep_Pass(testCase, "Camera is on after alarm");
		}
		else {

			Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Camera is off after alarm");
		}
		return flag;
	}

}
