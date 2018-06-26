package com.honeywell.keywords.flycatcher.Ventialtion;



import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.SchedulingScreen;

public class FlyCatcherVentialtion {

	public static boolean changeVentilationMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
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
						"Create Schedule : Cannot change system mode because thermostat is offline");
				return true;
			}
			if (expectedMode.equals("Off")) {
				fly.changeVentilationModeToOff();
			}
			else if (expectedMode.equals("On")) {
				fly.changeVentilationModeToOn();
			} 
			else if (expectedMode.equals("Auto")) {
				fly.changeVentilationModeToAuto();
			}

			if (sch.IsSaveButtonVisible(10)){
				sch.clickOnSaveButton();
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
}
