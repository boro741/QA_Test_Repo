package com.honeywell.keywords.flycatcher.Humidifier;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class FlyHumidifier {

	public static boolean setHumdifier(TestCases testCase, TestCaseInputs inputs, String expectedValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			for (int i= 0;i<12;i++){
				String ActualValue = fly.getTargetHumidityValue().replace("%", "");
				if (ActualValue != expectedValue){
					int act = Integer.parseInt(ActualValue);
					int exp = Integer.parseInt(expectedValue);
					if (act < exp){
						flag = flag && fly.ClickOnIncrementButton();
					}else if (act > exp){
						flag = flag && fly.ClickOnDecrementButton();
					} else{
						break;
					}
				}
			}
			Keyword.ReportStep_Pass(testCase,
					"Humidifier Value is Already set to " + fly.getTargetHumidityValue());
			flag = flag && fly.ClickOnSaveOptionButton();
		} catch(Exception e){
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

	public static boolean ChangeHumdifierstatus(TestCases testCase, TestCaseInputs inputs, String expectedValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			if (expectedValue.equalsIgnoreCase("Enabled")){
				if (fly.isOverlayIconVisible()){
					flag = flag && fly.ClickOnOverlayIconButton();
					Keyword.ReportStep_Pass(testCase,
							"Humidifier status changed to " + expectedValue);
				}else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Couldn't change the Humidifier Status to " + expectedValue);
				}
			} else{
				if (fly.isHumOptionVisible()){
					flag = flag && fly.ClickOnHumOptionButton();
					flag = flag && fly.ClickOnDisableHumOptionButton();
				} else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Couldn't change the Humidifier Status to " + expectedValue);
				}
			}
		} catch(Exception e){
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}
}