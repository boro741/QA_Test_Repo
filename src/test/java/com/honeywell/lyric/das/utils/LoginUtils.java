package com.honeywell.lyric.das.utils;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.LoginScreen;

public class LoginUtils {
	
	public static boolean enterEmailIdInLoginScreen(TestCases testCase, TestCaseInputs inputs, String email) {
		boolean flag = true;
		LoginScreen ls = new LoginScreen(testCase);
		flag &=ls.enterEmailIdInLoginScreen(inputs, email);
		return flag;
	}
	
	public static boolean enterPasswordInLoginScreen(TestCases testCase, TestCaseInputs inputs, String password) {
		boolean flag = true;
		LoginScreen ls = new LoginScreen(testCase);
		flag &=ls.enterPasswordInLoginScreen(inputs, password);
		return flag;
	}
	
}
