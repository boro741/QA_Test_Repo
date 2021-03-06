package com.honeywell.keywords.flycatcher.Humidifier;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class FlyHumidifier {

	public static boolean setHumdifier(TestCases testCase, TestCaseInputs inputs, String expectedValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
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
			}else{
				String ActualValue = fly.getTargetHumidityValue().replace("%", "");
				if (ActualValue != expectedValue){
					int exp = Integer.parseInt(expectedValue);
					double extvalue = 0.1 - (((exp/5)/10));
					fly.setValueToHumSlider(String.valueOf(extvalue));
				}
			}
			flag = flag && fly.ClickOnSaveOptionButton();
		} catch(Exception e){
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change Humidifier Value : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}


	public static boolean setDeHumdifier(TestCases testCase, TestCaseInputs inputs, String expectedValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")){
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
						"DeHumidifier Value is Already set to " + fly.getTargetHumidityValue());
			}else{
				String ActualValue = fly.getTargetHumidityValue().replace("%", "");
				if (ActualValue != expectedValue){
					int exp = Integer.parseInt(expectedValue);
					double extvalue = 0.1 - (((exp/5)/10));
					fly.setValueToHumSlider(String.valueOf(extvalue));
				}
			}
			flag = flag & fly.ClickOnSaveOptionButton();
		} catch(Exception e){
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change DeHumidifier Value : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

	public static boolean setWindowProtection(TestCases testCase, TestCaseInputs inputs, String expWindowProtectionValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			WebElement windowProtection = fly.getSeekBarElement();
			if(testCase.getPlatform().toUpperCase().contains("ANDROID")){
				Dimension d1 = windowProtection.getSize();
				Point p1 = windowProtection.getLocation();
				float sliderLength = d1.getWidth();
				float pixelPerPercent = sliderLength / 100;
				float pixelToBeMoved = Integer.parseInt(expWindowProtectionValue.equals("0") ? "1" : expWindowProtectionValue) * pixelPerPercent;
				flag = flag && MobileUtils.clickOnCoordinate(testCase, (int) (p1.getX() + pixelToBeMoved), p1.getY());
				Keyword.ReportStep_Pass(testCase, "Window Protection Value Set " + expWindowProtectionValue);
			}else{
			}
			flag = flag & fly.ClickOnSaveOptionButton();

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

	public static boolean ChangeDeHumdifierstatus(TestCases testCase, TestCaseInputs inputs, String expectedValue) {
		boolean flag = true;
		try {
			FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
			if (expectedValue.equalsIgnoreCase("Enabled")){
				if (fly.isOverlayIconVisible()){
					flag = flag && fly.ClickOnOverlayIconButton();
					Keyword.ReportStep_Pass(testCase,
							"DeHumidifier status changed to " + expectedValue);
				}else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Couldn't change the DeHumidifier Status to " + expectedValue);
				}
			} else{
				if (fly.isDeHumButtonVisible(5)){
					flag = flag && fly.ClickOnDeHumButton();
					flag = flag && fly.ClickOnDisableHumOptionButton();
				} else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Couldn't change the DeHumidifier Status to " + expectedValue);
				}
			}
		} catch(Exception e){
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change DeHumidifier status : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}
}