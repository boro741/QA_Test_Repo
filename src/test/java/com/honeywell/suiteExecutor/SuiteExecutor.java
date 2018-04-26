package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {    
    /**
     * The Starting point of Automation Framework.
     *
     * @param groups
     *            String[] array type, represents the groups to execute passed
     *            from Command line Arguments.
     */
	public static void main(String[] commandLineArguments) throws Exception {
			
		/*########################################################################
		 * Don't Modify this Command Line Arguments in SuiteExecutor class to avoid issue on executing Nightly test build *
		 * ###################################################################### */
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}
