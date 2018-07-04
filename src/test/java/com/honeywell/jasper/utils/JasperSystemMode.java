package com.honeywell.jasper.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class JasperSystemMode {

	public static boolean verifySystemMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
				testCase.getMobileDriver());
		fWait.pollingEvery(1, TimeUnit.SECONDS);
		fWait.withTimeout(60, TimeUnit.SECONDS);
		FlyCatcherPrimaryCard fly = new  FlyCatcherPrimaryCard(testCase);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				String changedMode = "";
				changedMode = fly.getDailerValue();
				if (changedMode.equals(expectedMode)) {
					return true;
				} else {
					return false;
				}
			}
		});

		if (isEventReceived) {
			Keyword.ReportStep_Pass(testCase,
					"Verify System Mode : Thermostat is in " + expectedMode + " mode in the app");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify System Mode : Thermostat is not in " + expectedMode + " mode in the app");
		}
		return flag;
	}


}
