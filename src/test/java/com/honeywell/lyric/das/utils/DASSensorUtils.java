package com.honeywell.lyric.das.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.RelayUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SensorStatusScreen;

public class DASSensorUtils {
	private boolean flag =true;
	public static boolean openDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_OPENED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIContactSensorOpen_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	public static boolean closeDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_CLOSED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIContactSensorClosed_Door();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean tamperDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("ALARM_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		inputs.setInputValue("DOOR_TAMPERED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIDoorContactSensorTampered();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean tamperClearDoor(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIDoorContactSensorTamperCleared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean openWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_OPENED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		System.out.println("Open Window");
		try {
			RelayUtils.RSIContactSensorOpen_Window();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
	public static boolean closeWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_CLOSED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		System.out.println("Close Window");
		try {
			RelayUtils.RSIContactSensorClosed_Window();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean tamperWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("ALARM_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		inputs.setInputValue("WINDOW_TAMPERED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIWindowContactSensorTamperON();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static boolean tamperClearWindow(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		inputs.setInputValue("WINDOW_TAMPER_CLEARED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		try {
			RelayUtils.RSIDoorContactSensorTamperCleared();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static List<WebElement> getSensorList(TestCases testCase){
		SensorStatusScreen sensorStatusScreen = new SensorStatusScreen(testCase);
		return sensorStatusScreen.getSensorList();
	}
	public boolean verifySensorState(TestCases testCase, TestCaseInputs inputs, String sensor, String states){
		String sensorName="";
		String sensorState="";
		if(sensor.equalsIgnoreCase("door")){
			sensorName=inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		}else if(sensor.equalsIgnoreCase("window")){
			sensorName=inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
		}else{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor type not handled");
		}

		if(states.equalsIgnoreCase("open")){
			sensorState="Open";
		}else if(states.equalsIgnoreCase("closed")){
			sensorState="Closed";
		}else if(states.equalsIgnoreCase("tamper cleared")){
			sensorState="Closed";
		}else if(states.equalsIgnoreCase("tamper cleared to open")){
			sensorState="Open";
		}
		else if(states.equalsIgnoreCase("off")){
			sensorState="OFF";
			if(!testCase.getPlatform().contains("IOS")){
				sensorState="Off";
			}
		}else if(states.equalsIgnoreCase("cover tampered")){
			sensorState="Cover Tampered";
		}
		else{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor state not handled");
		}
		List<WebElement> list;

		list= DASSensorUtils.getSensorList(testCase);
		boolean sensorStateMatched=false;
		for(int i=0;i<list.size();i++){
			if(testCase.getPlatform().contains("IOS")){
				if(testCase.getMobileDriver().findElements(By.xpath(
						"//*[contains(@name,'SensorStatus_"+i+"_cell')]//*[@value='"+sensorName+"']")).size()>0){
					if(states.contains("tamper cleared")){
						if(MobileUtils.isMobElementExists("xpath" ,"//*[contains(@name,'SensorStatus_"+i+"_Image')]",testCase,10)){
							MobileUtils.clickOnElement(testCase,"xpath" ,"//*[contains(@name,'SensorStatus_"+i+"_Image')]");
							//MobileUtils.clickOnElement(fieldObjects, testCase, "BackToViewList");
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
						Keyword.ReportStep_Pass(testCase, "DOOR_TAMPER_CLEARED_TIME "+inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME"));
					}
					if(testCase.getMobileDriver().findElements(By.xpath(
							"//*[contains(@name,'SensorStatus_"+i+"_cell')]//*[contains(@value,'"+sensorState+"')]")).size()>0){
						Keyword.ReportStep_Pass(testCase, sensorName +"is in "+sensorState);
						sensorStateMatched=true;
						break;
					}

				}
			}else{
				if(testCase.getMobileDriver().findElements(By.xpath(
						"//*[contains(@content-desc,'sensor_status_item_"+i+"')]//*[@text='"+sensorName+"']")).size()>0){
					if(states.contains("tamper cleared")){
						////*[@content-desc='left_drawable'] - Removed
						if(MobileUtils.isMobElementExists("xpath" ,
								"//*[contains(@content-desc,'sensor_status_item_"+i+"')]",testCase,10)){
						    Keyword.ReportStep_Pass(testCase, "Current state "+testCase.getMobileDriver().findElement(By.xpath(
							"//*[contains(@content-desc,'sensor_status_item_"+i+"')]//*[contains(@text,'"+sensorState+"')]")).getText());
							MobileUtils.clickOnElement(testCase,"xpath" ,"//*[contains(@content-desc,'sensor_status_item_"+i+"')]");
							//MobileUtils.clickOnElement(fieldObjects, testCase, "BackToViewList");
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						inputs.setInputValue("DOOR_TAMPER_CLEARED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
						Keyword.ReportStep_Pass(testCase, "DOOR_TAMPER_CLEARED_TIME "+inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME"));
					}
					if(testCase.getMobileDriver().findElements(By.xpath(
							"//*[contains(@content-desc,'sensor_status_item_"+i+"')]//*[contains(@text,'"+sensorState+"')]")).size()>0){
						Keyword.ReportStep_Pass(testCase, sensorName +"is in "+sensorState);
						sensorStateMatched=true;
						break;
					}

				}
			}
		}
		if(list.size()==0){
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "not able to read Sensor list");
		}
		if(!sensorStateMatched){
			flag=false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sensor: "+sensorName+" state expected in "+sensorState);
		}
		SensorStatusScreen sensorStatusScreen = new SensorStatusScreen(testCase);
		flag = flag & sensorStatusScreen.clickOnSensorStatusScreenBack(testCase);
		return flag;
	}
}
