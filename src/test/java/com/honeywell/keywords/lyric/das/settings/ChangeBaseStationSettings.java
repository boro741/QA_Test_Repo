package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;

public class ChangeBaseStationSettings extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public ChangeBaseStationSettings(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the (.*) to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Base Station Volume"))
			{
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.VOLUME);
				String value = parameters.get(1).split("%")[0].split("~")[1];
				if(bs.setValueToVolumeSlider(value))
				{
					Keyword.ReportStep_Pass(testCase, "Successfully set the volume to " + parameters.get(1));
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set the volume to: " + parameters.get(1));
				}
			}
			else if(parameters.get(0).equalsIgnoreCase("Geofencing Status"))
			{
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if(parameters.get(1).equalsIgnoreCase("ON"))
				{
					if(bs.isGeofencingSwitchEnabled(testCase))
					{
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings page");
					}
					else
					{
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				}
				else if(parameters.get(1).equalsIgnoreCase("OFF"))
				{
					if(!bs.isGeofencingSwitchEnabled(testCase))
					{
						Keyword.ReportStep_Pass(testCase, "Geofence is already disabled on the settings page");
					}
					else
					{
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				}
			}
			else if(parameters.get(0).equalsIgnoreCase("Camera ON in Home Mode"))
			{
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if(parameters.get(1).equalsIgnoreCase("ON"))
				{
					if(bs.isCameraOnInHomeModeSwitchEnabled(testCase))
					{
						Keyword.ReportStep_Pass(testCase, "Camera On in Home Mode is already enabled on the settings page");
					}
					else
					{
						flag = flag & bs.toggleCameraOnInHomeModeSwitch(testCase);
					}
				}
				else if(parameters.get(1).equalsIgnoreCase("OFF"))
				{
					if(!bs.isCameraOnInHomeModeSwitchEnabled(testCase))
					{
						Keyword.ReportStep_Pass(testCase, "Camera On in Home Mode is already disabled on the settings page");
					}
					else
					{
						flag = flag & bs.toggleCameraOnInHomeModeSwitch(testCase);
					}
				}
			}
					
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
