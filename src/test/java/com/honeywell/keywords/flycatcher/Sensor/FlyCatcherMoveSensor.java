package com.honeywell.keywords.flycatcher.Sensor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class FlyCatcherMoveSensor extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public FlyCatcherMoveSensor(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Edit Stop Ventilation Mode");
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user moves sensor to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int groupid = statInfo.getSensorGroupID();
			String deviceID=statInfo.getDeviceID();
			if (chUtil.getConnection()) {
				JSONObject SensorListJson = chUtil.getSensorList(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID, groupid);
				List<String> sensorRoomTypelist = statInfo.getSensorRoomType(SensorListJson);
				List<String> sensorname = statInfo.getSensorDeviceID(SensorListJson);
				List<WebElement> getRoomTypelist = fly.getSensorRoomTypeListElements();
				if(exampleData.get(0).equalsIgnoreCase("New Room")){
					for (int i=0;i<sensorname.size();i++){
						if (sensorname.get(i).equalsIgnoreCase(inputs.getInputValue("SENSOR1"))){
							for (int j=0;j<getRoomTypelist.size();j++){
								System.out.println(sensorRoomTypelist.get(i) + getRoomTypelist.get(j).getText());
								if (!sensorRoomTypelist.get(i).equalsIgnoreCase(getRoomTypelist.get(j).getText())){
									flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='"+getRoomTypelist.get(j).getText() +"']"," User Selected " + getRoomTypelist.get(j).getText() + " Room");
									List<WebElement> getMoveTypelist = fly.getSensorMoveTypeListElements();
									String newRoom = getMoveTypelist.get(i).getText();
									flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='"+getMoveTypelist.get(i).getText() +"']"," User Selected " + getMoveTypelist.get(i).getText() + " Room");
									inputs.setInputValue(InputVariables.EDITEDSENSORNAME, newRoom);
									break;
								}
							}
						}
					}
					Keyword.ReportStep_Pass(testCase, "Sensor is moved to New Room");
				} else if (exampleData.get(0).equalsIgnoreCase("Custom Room")){
					String firstRoomname = getRoomTypelist.get(0).getText();
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='"+firstRoomname +"']"," User Selected " + firstRoomname + " Room");
					String generatedString = RandomStringUtils.randomAlphabetic(10);
					flag = flag & fly.setCoustomNametoSensor(generatedString);
					inputs.setInputValue(InputVariables.EDITEDSENSORNAME, generatedString);
				} else if (exampleData.get(0).equalsIgnoreCase("Exisiting Room")){
					for (int i=0;i<sensorname.size();i++){
						if (sensorname.get(i).equalsIgnoreCase(inputs.getInputValue("SENSOR1"))){
							for (int j=0;j<getRoomTypelist.size();j++){
								if (sensorRoomTypelist.get(i).equalsIgnoreCase(getRoomTypelist.get(j).getText())){
									flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='"+getRoomTypelist.get(j).getText() +"']"," User Selected " + getRoomTypelist.get(j).getText() + " Room");
									List<WebElement> getMoveTypelist = fly.getSensorMoveTypeListElements();
									String exisitingRoom = getMoveTypelist.get(i).getText();
									flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='"+getMoveTypelist.get(i).getText() +"']"," User Selected " + getMoveTypelist.get(i).getText() + " Room");
									inputs.setInputValue(InputVariables.EDITEDSENSORNAME, exisitingRoom);
									if (fly.isMultipleSensorRoomPopUpVisible()){
										flag = flag & fly.ClickOnYesButton();
										Keyword.ReportStep_Pass(testCase,
												"Use multiple sensors in same room pop up is displayed");
									}
									break;
								}
							}
						}

					}
				}
				if (fly.isSensorPlacedButtonVisible(60)){
					flag = flag & fly.ClickOnSensorPlacedButtonButton();
				}
			}

		} catch (Exception e){
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}
}
