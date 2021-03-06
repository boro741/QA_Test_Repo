package com.honeywell.screens;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class FlyCatcherPrimaryCard extends MobileScreens {

	private static final String screenName = "FlyCatcherPrimaryCard";


	public FlyCatcherPrimaryCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public String getSystemMode()
	{
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "changedModeText").getText().toString();

		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "changedModeText").getText().toString();

		}
	}
	public boolean IsHeatModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeOption",timeOut);
	}

	public boolean IsCoolModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeOption",timeOut);
	}

	public boolean IsOffModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OffModeOption",timeOut);
	}
	public boolean IsAuotModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeOption",timeOut);
	}


	public Boolean changeSystemModeToHeatMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='HEAT']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToCoolMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='COOL']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToOffMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OffModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='OFF']")).click();
		}		
		return blnFlag;

	}
	public Boolean changeSystemModeToAutoMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (IsAuotModeButtonVisible(5)){
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoModeOption");
			}else{
				testCase.getMobileDriver().findElement(By.xpath("//*[@value='AUTO']")).click();
			}
		}
		return blnFlag;
	}

	public boolean clickOnSetPointSteppeDownButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SetPointStepper_Down");
	}

	public boolean ClickOnSetPointStepperUPButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SetPointStepper_UP");
	}

	public boolean IsDailerButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Dialer",timeOut);
	}

	public String getDailerValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "Dialer").getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "Dialer").getAttribute("value");			
		}
	}

	public boolean ClickCloseButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelOption");
	}
	public boolean IsCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelOption",timeOut);
	}

	public boolean ClickOnMoreButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MoreButton");
	}

	public boolean isMoreButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MoreButton",3);
	}

	public boolean isVentilationIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VentilationButton",3);
	}

	public boolean ClickOnVentilationButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationButton");
	}

	public boolean changeVentilationModeToOff()
	{
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationOffActiveButton");
		} else{
			testCase.getMobileDriver().findElementById("OFF").click();
		}
		return flag;
	}

	public boolean changeVentilationModeToOn()
	{
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationOnActiveButton");
		} else{
			testCase.getMobileDriver().findElementById("ON").click();
		}
		return flag;
	}

	public boolean changeVentilationModeToAuto()
	{
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationAutoActiveButton");
		} else{
			testCase.getMobileDriver().findElementById("AUTO").click();
		}
		return flag;

	}

	public String getVentialtionMode(){
		return MobileUtils.getMobElement(objectDefinition, testCase, "VentilationButton").getTagName();
	}

	public boolean VentilationTimerOnModes(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RunVentialtionButton");
	}

	public boolean ClickVentilationTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RunVentialtionButton");
	}

	public boolean ClickEditVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationEditTimer");
	}

	public boolean ClickStartVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationTimerStart");
	}

	public boolean ClickCancelVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationTimerCancel");
	}

	public int getTimerPickerValue(){
		int timervalue = 0;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
			String timervalueString = MobileUtils.getFieldValue(objectDefinition, testCase, "VentilationTimerPicker");
			timervalue = Integer.parseInt(timervalueString.replaceAll(" MINUTES", ""));
		} else{
			String timervalu = testCase.getMobileDriver().findElementByXPath("//XCUIElementTypePicker[@name='FCVentilationPickerView']/XCUIElementTypePickerWheel").getText();
			timervalue = Integer.parseInt(timervalu.replaceAll(" MINUTES", ""));
		}
		return timervalue;
	}

	public int[] getPickercordinates(){
		Point pointValue1 = MobileUtils.getMobElement(objectDefinition, testCase, "VentilationTimerPicker").getLocation();
		int startx = pointValue1.getX();
		int starty = pointValue1.getY();
		return new  int[] {startx,starty};

	}

	public int getVentilationTimeValue(){
		int TimerValue = 0;
		String ele = MobileUtils.getFieldValue(objectDefinition, testCase, "VentilationTimerValue");
		TimerValue = Integer.parseInt(ele);
		return TimerValue;
	}

	public boolean isVentilationTimerText()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VentilationTimerValue",3);
	}

	public boolean ClickStopTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "StopTimerButton");
	}

	public boolean isStopTimerVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StopTimerButton");
	}

	public boolean isCloseOtpionVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseOption");
	}

	public boolean ClickOnCloseOption(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseOption");
	}

	public boolean isHumButtonVisible(int timeout){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidifyButton",timeout);
	}
	public boolean isDeHumButtonVisible(int timeout){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeHumidityTab",timeout);
	}


	public boolean isHumButtonVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidifyButton");
	}
	public boolean ClickOnHumButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumidifyButton");
	}
	public boolean ClickOnDeHumButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeHumidityTab");
	}

	public boolean ClickOnIncrementButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumIncrementButton");
	}

	public boolean ClickOnDecrementButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumDecrementButton");
	}

	public boolean ClickOnSaveOptionButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveOption");
	}

	public boolean ClickOnHelpOptionButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HelpOption");
	}

	public String getCurrentHumidityValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			List<WebElement> ele = MobileUtils.getMobElements(objectDefinition, testCase, "CurrentHumidityPercentage");
			return ele.get(0).getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CurrentHumidityPercentage").getAttribute("value");			
		}
	}	

	public String getTargetHumidityValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TargetPercentage").getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "TargetPercentage").getAttribute("value");			
		}
	}

	public boolean isOverlayIconVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OverlayIcon");
	}

	public boolean isOverlayIconVisible(int Timeout){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OverlayIcon" ,Timeout);
	}

	public boolean ClickOnOverlayIconButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OverlayIcon");
	}

	public boolean isHumOptionVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HumOption");
	}

	public boolean ClickOnHumOptionButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumOption");
	}

	public boolean ClickOnDisableHumOptionButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DisableHumOption");
	}

	public WebElement getSeekBarElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SeekBar");
	}

	public boolean ClickOnWindowProtectionButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WindowProtectionOption");
	}

	public boolean setValueToHumSlider(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "HumSlider", value);
	}

	public WebElement getPickerElement(){
		return MobileUtils.getMobElement(objectDefinition, testCase, "VentilationTimerPicker");
	}

	public boolean ClickOnDeviceAndSensor(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeviceAndSensor");
	}

	public boolean ClickOnSettingsIcon(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SettingsIcon");
	}

	public boolean ClickOnDeleteSensor(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteSensor");
	}

	public boolean ClickOnIdentifySensor(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "IdentifySensor");
	}

	public boolean ClickOnMoveSensor(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MoveSensor");
	}

	public boolean isDeleteSensorPopUpVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUp");
	}

	public boolean isIdentifySensorImageVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IdentifySensorImage");
	}

	public boolean ClickOnSensorCustomNameField(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorCustomName");
	}

	public List<WebElement> getSensorRoomTypeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SensorRoomTypeList");
	}

	public List<WebElement> getSensorMoveTypeListElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SensorMoveTypeList");
	}

	public boolean isSensorPlacedButtonVisible(int timeout){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorPlacedButton" ,timeout);
	}

	public boolean ClickOnSensorPlacedButtonButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorPlacedButton");
	}
	
	public boolean ClickOnSensorCustomNameButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorCustomName");
	}

	public String getSensorDetailsHeaderText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetailsHeader").getText();
	}

	public String getProgressBarText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ProgressBarText").getText();
	}

	public boolean isPrgressBarVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBarText");
	}
	
	public boolean isMultipleSensorRoomPopUpVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MultipleSensorRoomPopUp");
	}

	public boolean setCoustomNametoSensor(String value) {
		boolean flag = true; 
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "SensorCustomName", value ,"Adding CustomName to textField");
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
		} else {
			try {
				MobileUtils.pressEnterButton(testCase);
				//				MobileUtils.hideKeyboard(testCase.getMobileDriver());
			} catch (Exception e) {
			}
		}
		return flag;
	}
	
	public boolean ClickOnYesButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Yesbutton");
	}
	
	public String getSensorDetialsTempText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsTempValue").getText();
	}

	public boolean isSensorDetialsTempVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsTempText");
	}
	
	public String getSensorDetialsHumidityText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsHumidityValue").getText();
	}

	public boolean isSensorDetialsHumidityVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsHumidityText");
	}
	
	public String getSensorDetialsBatteryText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsBatteryValue").getText();
	}

	public boolean isSensorDetialsBatteryVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsBatteryText");
	}
	
	public String getSensorDetialsSignalStrengthText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsSignalStrengthValue").getText();
	}

	public boolean isSensorDetialsSignalStrengthVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsSignalStrengthText");
	}
	
	public String getSensorDetialsModelText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsModelValue").getText();
	}

	public boolean isSensorDetialsModelVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsModelText");
	}
	
	public String getSensorDetialsFirmwareVersionText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorDetialsFirmwareVersionValue").getText();
	}

	public boolean isSensorDetialsFirmwareVersionVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsFirmwareVersionText");
	}

	public boolean isSensorDetialsUseMotionDetectionVisible(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorDetialsUseMotionDetectionText");
	}
	
	public String getSensorMotionDetectionToggleValue() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorMotionDetectionToggle").getText();
	}
	
	public boolean ClickOnSensorMotionDetectionToggleButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorMotionDetectionToggle");
	}
	

}
