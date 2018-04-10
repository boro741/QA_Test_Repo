package com.honeywell.keywords.jasper.Setpoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.lyric.utils.InputVariables;

public class HoldScheduleUntil extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public HoldScheduleUntil(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user holds the schedule until time \"(.+)\" from current time$")
	public boolean keywordSteps() throws KeywordException {
		try {
			SimpleDateFormat time12Format = new SimpleDateFormat("hh:mm a");
			flag = flag & JasperAdhocOverride.holdSetPointsUntilFromAdHoc(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				String currentTime = JasperAdhocOverride.getAndroidDeviceTime(testCase).trim();
				SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				Date date = androidDateFormat.parse(currentTime);
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				String day = "";
				c1.setTime(date);
				c2.setTime(date);
				if (exampleData.get(0).equals("greater than 12 hours")) {
					c2.add(Calendar.HOUR, 14);
					c2.set(Calendar.MINUTE, 0);
				} else if (exampleData.get(0).equals("lesser than 12 hours")) {
					c2.add(Calendar.HOUR, 2);
					c2.set(Calendar.MINUTE, 0);
				}

				if (c2.get(Calendar.DATE) != c1.get(Calendar.DATE)) {
					day = "Tomorrow";
				} else {
					day = "Today";
				}
				String time = time12Format.format(c2.getTime());
				flag = flag & JasperAdhocOverride.setHoldUntilTime(testCase, inputs, day, time);
				if (flag) {
					inputs.setInputValue(InputVariables.HOLD_UNTIL_TIME, time);
				}
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
				String IOSTime =JasperAdhocOverride.getIOSSimulatorTime(testCase);
				SimpleDateFormat IOSDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date date = IOSDateFormat.parse(IOSTime);
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();
				String day = "";
				c1.setTime(date);
				c2.setTime(date);
				if (exampleData.get(0).equals("greater than 12 hours")) {
					c2.add(Calendar.HOUR, 14);
					c2.set(Calendar.MINUTE, 0);
				} else if (exampleData.get(0).equals("lesser than 12 hours")) {
					c2.add(Calendar.HOUR, 2);
					c2.set(Calendar.MINUTE, 0);
				}
				day = dateFormat.format(c2.getTime());
				String time = time12Format.format(c2.getTime());
				flag = flag & JasperAdhocOverride.setHoldUntilTime(testCase, inputs, day, time);
				inputs.setInputValue(InputVariables.HOLD_UNTIL_TIME_DATE, day + "_" + time);
				if (flag) {
					inputs.setInputValue(InputVariables.HOLD_UNTIL_TIME, time);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}