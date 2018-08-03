package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {    
    /**
     * The Starting point of Automation Framework.
     *
     * @param groups
     *            String[] array type, represents the groups to execute passed
     *            from Command line Argumentrinters.
     */
	public static void main(String[] commandLineArguments) throws Exception {
	
			commandLineArguments = new String[] {
					
						"--publishresult",
					// "--jira_credentials",
					// "aterbuild:aterbuild@123",
					"--useXCUITest", "true", 
					//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
					"--appToInstall", "Android:402001095,IOS:4200382", 
					"--groups", 
					
					
					//	"ScheduleOFFONNA",
					//	"ScheduleOFFONNAtimebase"
					//	"ScheduleOFFONEMEA",
					//	"ScheduleOFFONEMEAtimebase",
					//	"ScheduleOFFVacationNA",
					//	"ScheduleONOFFNAVacationNA",
					 // "ScheduleOFFVacationEMEA",
					//	"ScheduleOFFONVacationEMEA",
					//	"ScheduleONOFFHB",
					//	"ScheduleONOFFHBtimebase",
					//	"ScheduleONOFFNAgeofencebase",
					//	"ScheduleONOFFEMEAgeofencebase",
					//	"ScheduleONOFFHBgeofencebase",
					//	"ScheduleOFFAdhocOverrideNAtimebaseGeofence",
					//	"ScheduleONOFFNNAadhocoverrideTimebase"
					//	"ScheduleONOFFNAadhocoverrideGeofence",
					 //	"ScheduleOFFEMEAtimebasegeofencebase",
					 // "ScheduleONFFAdhocOverrideEMEAtimebase",
					// "ScheduleOFFONAadhocoverrideEMEAgeofence",
					//	"ScheduleONOFFNAswitchingmodes",
					//	"ScheduleONOFFEMEAswitchingmodes"
					//	"ScheduleONOFFHBswitchingmodes",
						"ScheduleONOFFNAgeofencebasefencecross",
					//	"ScheduleONOFFNAgeofencebasefencecross",
					//	"ScheduleONOFFHBgeofencebasefencecross",
					
                    };
       	 try {
                SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
                suiteUtils.executeSuite();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
