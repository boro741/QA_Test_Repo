package com.honeywell.keywords.lyric.common;



import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.screens.AlarmScreen;

public class ViewLiveStreaming extends Keyword {

	private TestCases testCase;
	public boolean flag =false;
	// private TestCaseInputs inputs;
	public ArrayList<String> activity;

	public ViewLiveStreaming(TestCases testCase, TestCaseInputs inputs, ArrayList<String> activity) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.activity = activity;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		flag = true;
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^view the (.*) in full screen$")
	public boolean keywordSteps() {
		DASCameraUtils cam = new DASCameraUtils();
		AlarmScreen alarm = new AlarmScreen(testCase);
		
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(2, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
			
				if(cam.verifyLiveStreaming(testCase)) {
					System.out.println("Camera is Live streaming");
					return true;
					
				}else return false;

			}


		});

		if(isEventReceived) {
			
			flag=flag & alarm.clickLiveStreamingArea();
			
			flag=flag & alarm.clickLiveStreamingMaximize();		
			
          }
		else {
	        flag=false;
			}
		if(flag==true) {
			
			if(alarm.clickLiveStreamingArea()) {
				 	
				flag= flag & alarm.clickLiveStreamingMinimize();
			
				flag= flag & alarm.verifyLiveStreamingProgress();
					
				
              }
		}
      	if(flag==true) {
			Keyword.ReportStep_Pass(testCase, "Testcase Passed");
		}
		else {
			Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Testcase is not Passed");
			
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}

