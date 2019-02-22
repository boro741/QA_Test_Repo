package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

public class LoginToLyricEitherAfterDeletingAccountOrUpdatePassword extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> inputName;
	boolean flag = false;

	public LoginToLyricEitherAfterDeletingAccountOrUpdatePassword(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> inputName) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.inputName = inputName;
	}

	@SuppressWarnings("unchecked")
	@Override
	@BeforeKeyword
	public boolean preCondition() {
		boolean flag = true;
		inputs.setInputValue("LOCATION1_NAME", inputs.getInputValue(InputVariables.LOCATION_NAME));
		inputs.setInputValue("LOCATION1_DEVICE1_NAME", inputs.getInputValue(InputVariables.DEVICE_NAME));

		if (inputs.isRunningOn("Perfecto")) {
			if (inputs.getInputValue(InputVariables.MOBILE_LOCATION_OFF).equalsIgnoreCase("true")) {
				flag = flag & LyricUtils.changeLocationSettings(testCase, inputs, "ON");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Activity activity = new Activity("com.honeywell.android.lyric",
							"com.honeywell.granite.graniteui.presentation.activity.dashboard.DashBoardActivity");
					((AndroidDriver<MobileElement>) testCase.getMobileDriver()).startActivity(activity);
				} else {
					testCase.getMobileDriver().launchApp();
				}
			}
			/*
			 * if (inputs.getInputValue(InputVariables.MOBILE_LOCATIONPERMISSION_OFF).
			 * equalsIgnoreCase("true")) { InputVariables.changeLocationPermission(testCase,
			 * inputs); if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			 * ((AndroidDriver<MobileElement>)
			 * testCase.getMobileDriver()).startActivity("com.honeywell.android.lyric",
			 * "com.honeywell.granite.graniteui.presentation.activity.dashboard.DashBoardActivity"
			 * ); } else { testCase.getMobileDriver().launchApp(); } }
			 */
		}
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user logs in to the Lyric Application with \"(.*)\"$")
	public boolean keywordSteps() {
		boolean flag = true;
		if (inputName.get(0).equalsIgnoreCase("UPDATED PASSWORD")) {
			flag &= LyricUtils.loginToApplicationWithUpdatedPassword(testCase, inputs);
		} else if (inputName.get(0).equalsIgnoreCase("PREVIOUS PASSWORD")) {
			flag &= LyricUtils.loginToApplicationWithPreviousPassword(testCase, inputs);
		} else if (inputName.get(0).equalsIgnoreCase("DELETED ACCOUNT CREDENTIALS")) {
			flag &= LyricUtils.loginToApplicationWithDeletedAccountCredentials(testCase, inputs);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		boolean flag = true;
		return flag;
	}

}
