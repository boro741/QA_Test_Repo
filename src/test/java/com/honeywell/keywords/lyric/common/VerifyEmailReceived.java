package com.honeywell.keywords.lyric.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.openqa.selenium.TimeoutException;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.GuerrillaMailUtils;

public class VerifyEmailReceived extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> link;
	private boolean flag = true;

	public VerifyEmailReceived(TestCases testCase, TestCaseInputs inputs, ArrayList<String> link) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.link = link;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user receives a (.*) email$")
	public boolean keywordSteps() {
		try {
			String subject = null;
			String expectedContent=null;
			String expectedContent1 = null;
			LocationInformation locInfo = new LocationInformation(testCase, inputs);

			if (link.get(0).equalsIgnoreCase("Alarm")) {
				subject ="Security Alarm in progress";
				expectedContent="Security Alarm in progress at"+inputs.getInputValue("LOCATION1_NAME")+" at "+timeConversion(inputs.getInputValue("ALARM_TIME"))+".";

				expectedContent1="Check the app for details";
			}else if(link.get(0).equalsIgnoreCase("Alarm cancelled")){
				subject ="Security Alarm cancelled";
				expectedContent=locInfo.getUserFirstName()+" cancelled Security Alarm at "+inputs.getInputValue("LOCATION1_NAME")+" at "+timeConversion(inputs.getInputValue("ALARM_DISMISSED_TIME"));
			}else{
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "input not handled");
			}
			if(subject!=null){
				String mailContent= GuerrillaMailUtils.FetchMailContent(testCase, subject, inputs.getInputValue("USERID"), 0,inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT));
				if(link.get(0).equalsIgnoreCase("Alarm")){
					System.out.println(mailContent.contains(expectedContent));
					if(mailContent.contains("Security Alarm in progress at")){
						Keyword.ReportStep_Pass(testCase, expectedContent + " -  found");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ expectedContent + " - not found");
					}
					if( mailContent.contains(inputs.getInputValue("LOCATION1_NAME"))){
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_NAME") + " -  found");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ inputs.getInputValue("LOCATION1_NAME") + " - not found");
					}
					int startMail=mailContent.indexOf(timeConversion(inputs.getInputValue("ALARM_TIME")).split(":")[0]);
					System.out.println(startMail);
					Keyword.ReportStep_Pass(testCase,mailContent.substring(startMail));
					if( mailContent.contains(timeConversion(inputs.getInputValue("ALARM_TIME")))){
						Keyword.ReportStep_Pass(testCase, timeConversion(inputs.getInputValue("ALARM_TIME")) + " -  found");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ timeConversion(inputs.getInputValue("ALARM_TIME"))  + " - not found");
					}
					if( mailContent.contains(expectedContent1)){
						Keyword.ReportStep_Pass(testCase, expectedContent1 + " -  found");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ " Expected "+expectedContent1+" but found -"+mailContent );
					}
				}
				else if(link.get(0).equalsIgnoreCase("Alarm cancelled")){
					if( mailContent.contains(locInfo.getUserFirstName())){
						Keyword.ReportStep_Pass(testCase, locInfo.getUserFirstName() + " -  found");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ " Expected "+locInfo.getUserFirstName()+" but found -"+mailContent );
					}
					if(mailContent.contains("cancelled Security Alarm at")){
						Keyword.ReportStep_Pass(testCase, "cancelled Security Alarm at" + " -  found");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ " Expected - cancelled Security Alarm at - but found -"+mailContent);
					}
					if(mailContent.contains(inputs.getInputValue("LOCATION1_NAME"))){
						Keyword.ReportStep_Pass(testCase, inputs.getInputValue("LOCATION1_NAME")+ " -  found");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ " Expected -"+inputs.getInputValue("LOCATION1_NAME") +"- but found -"+mailContent);
					}
					if(mailContent.contains(timeConversion(inputs.getInputValue("ALARM_DISMISSED_TIME")))){
						Keyword.ReportStep_Pass(testCase,timeConversion(inputs.getInputValue("ALARM_DISMISSED_TIME"))+ " -  found");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Mail content mismatched"+ " Expected -"+timeConversion(inputs.getInputValue("ALARM_DISMISSED_TIME"))+"- but found -"+mailContent);
					}
					
				}else{
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"INPUT NOT HANDLED");
				}
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to get link on email after waiting for 1 minute");
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	private String timeConversion(String time) throws ParseException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
		SimpleDateFormat hour12Format = new SimpleDateFormat("hh:mm a");
		System.out.println(hour12Format.format(timeFormat.parse(time)));
		return hour12Format.format(timeFormat.parse(time));
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
