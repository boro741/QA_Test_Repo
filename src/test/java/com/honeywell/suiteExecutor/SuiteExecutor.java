package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {
	
	
	public static void main(String[] commandLineArguments) throws Exception {
		// ============= Executes the Suite created from the Suite Configuration
		// JSON file =============
		try {
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
