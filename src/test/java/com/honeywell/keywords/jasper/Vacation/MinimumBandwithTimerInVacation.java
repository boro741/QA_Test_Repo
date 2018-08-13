package com.honeywell.keywords.jasper.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class MinimumBandwithTimerInVacation extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public MinimumBandwithTimerInVacation(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Minimum bandwidth timer between from and to is (.*) hour$")
	public boolean keywordSteps() throws KeywordException {
		if (exampleData.get(0).equals("1")) {
			CHILUtil chUtil;
			try {
				chUtil = new CHILUtil(inputs);
				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				long locationID = locInfo.getLocationID();
				String deviceID = statInfo.getDeviceID();
				SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Calendar c = Calendar.getInstance();
				c.setTime(vacationDateFormat.parse(CHILUtil.startTime));
				c.add(Calendar.HOUR, Integer.parseInt(exampleData.get(0)));
				String dateAfterAddition = vacationDateFormat.format(c.getTime());
				if (chUtil.getConnection()) {
					int result = chUtil.enableVacation(locationID, deviceID, CHILUtil.startTime, dateAfterAddition,
							statInfo.getThermostatUnits(), CHILUtil.coolSetPoints, CHILUtil.heatSetPoints);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Activate Vacation Using CHIL : Successfully activated vacation using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Activate Vacation Using CHIL : Failed to activate vacation using CHIL");
					}
				}
				statInfo = new DeviceInformation(testCase, inputs);
				String vacationStart = statInfo.getVacationStartTime();
				String vacationEnd = statInfo.getVacationEndTime();
				SimpleDateFormat vacationDateFormatStart = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date startTime;
				Date endTime;
				try {
					startTime = vacationDateFormatStart.parse(vacationStart);
					endTime = vacationDateFormatStart.parse(vacationEnd);
					long dif = startTime.getTime() - endTime.getTime();
					int diffInHours = (int) ((dif / 1000) / 60 / 60);
					if (diffInHours > Integer.parseInt(exampleData.get(0))) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Differnece between vacation start time and current time is greater than 1 hour");

					} else if (diffInHours <= Integer.parseInt(exampleData.get(0))) {
						Keyword.ReportStep_Pass(testCase, "Vacation Start and Current Time is 1 hour");
						flag = true;
					} else if (diffInHours < 0) {
						Keyword.ReportStep_Pass(testCase,
								"Wait for Vacation Start : Vacation start time is past current device time");
						flag = true;
					}
				} catch (ParseException e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:" + e.getMessage());
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured:" + e.getMessage());
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
