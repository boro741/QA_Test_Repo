package com.honeywell.keywords.lyric.das.commandandcontrol;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import com.honeywell.account.information.LocationInformation;

public class GetLocationTime extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public GetLocationTime(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user gets location time$")
	public boolean keywordSteps() {
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		try {
			System.out.println("##########" + locInfo.getLocationID());
			System.out.println("@@@@@@" + locInfo.getZipCode());
			System.out.println("#########getIANATimeZone:" + locInfo.getIANATimeZone());
			TimeZone timeZone = TimeZone.getTimeZone(locInfo.getIANATimeZone());
			
			System.out.println("#######timeZone: " + timeZone);
			Calendar c = Calendar.getInstance(timeZone);
			String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
			            String.format("%02d" , c.get(Calendar.MINUTE));
			if(c.get(Calendar.AM_PM) == 0) {
				time = time + " AM";
			} else {
				time = time + " PM";
			}
			System.out.println("#######time: " + time);
			
			locInfo.printLocationJSON();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
