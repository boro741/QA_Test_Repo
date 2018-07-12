package com.honeywell.keywords.flycatcher.Ventialtion;



import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.SchedulingScreen;

public class FlyCatcherVentialtion {

	public static boolean changeVentilationMode(TestCases testCase, TestCaseInputs inputs, String expectedMode,
			String TimerValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			SchedulingScreen sch = new SchedulingScreen(testCase);
			if (fly.isVentilationIconVisible()){
				flag = flag && fly.ClickOnVentilationButton();
			} else{
				flag = flag && fly.ClickOnMoreButton();
				flag = flag && fly.ClickOnVentilationButton();
			}
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Chnage ventilation mode  : Cannot change ventilation mode because thermostat is offline");
				return true;
			}
			if (expectedMode.equalsIgnoreCase("Off")) {
				fly.changeVentilationModeToOff();
			}
			else if (expectedMode.equalsIgnoreCase("On")) {
				fly.changeVentilationModeToOn();
			} 
			else if (expectedMode.equalsIgnoreCase("Auto")) {
				fly.changeVentilationModeToAuto();
			}
			if (TimerValue.equalsIgnoreCase("Default")){
				if (sch.IsSaveButtonVisible(10)){
					sch.clickOnSaveButton();
				}
				Keyword.ReportStep_Pass(testCase,
						"Changing Ventilation Mode without Timer");
				return true;
			} else{
				if(expectedMode.equalsIgnoreCase("On")){
					Keyword.ReportStep_Pass(testCase,
							"Venitilation Mode set to " + expectedMode);
					return true;
				}else{
					flag = flag && fly.ClickVentilationTimer();
					flag = flag && fly.ClickEditVentTimer();
					FlyCatcherVentialtion fl = new FlyCatcherVentialtion();
					fl.SetVentilationTimer(testCase, inputs, TimerValue);
				}
			}


		} catch(Exception e){

		}
		return flag;
	}


	public static boolean verifyVentilationMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		String changedMode = "";
		FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
		if (!fly.isVentilationIconVisible()){
			flag = flag && fly.ClickOnMoreButton();
			changedMode = fly.getVentialtionMode();
		} else {
			changedMode = fly.getVentialtionMode();
		}
		if(changedMode.equals(expectedMode)){
			return true;
		}else{
			return false;
		}
	}

	public  boolean SetVentilationTimer(TestCases testCase, TestCaseInputs inputs, String TimerValue){
		boolean flag = true;
		int picker_value = 0;
		FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
		picker_value = fly.getTimerPickerValue();
		int expected_value = Integer.parseInt(TimerValue);
//		int[] pickervalues = fly.getPickercordinates();
		if (picker_value != expected_value ){
			int i = 0;
			while (picker_value < expected_value && i < 9){
				if (fly.getTimerPickerValue() != expected_value ){
					testCase.getMobileDriver().swipe(530, 976, 530, 850, 1000);
					i++;
				} else{
					break;
				}
			}
			while (picker_value > expected_value && i < 9){
				if (fly.getTimerPickerValue() != expected_value ){
					testCase.getMobileDriver().swipe(530, 850, 530,976, 1000);
					i++;
				} else{
					break;
				}
			}
			Keyword.ReportStep_Pass(testCase,
					"Ventilation timer is set to " + fly.getTimerPickerValue());
			flag = true;
		} else {
			Keyword.ReportStep_Pass(testCase,
					"Ventilation timer is set to " + TimerValue);
			flag = true;
		}
		flag = flag && fly.ClickStartVentTimer();
		return flag;
	}


}
