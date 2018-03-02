package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {

	public static void main(String[] commandLineArguments) throws Exception {

		commandLineArguments = new String[] {
				// "--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
				"--appToInstall", "Android:400000043,IOS:43", 
				"--groups", 
				//"DIYSmartHomeSecurityOptionInAddNewDevice",
				//"DIYCancelSetUp",
				//"DIYDenyAppAccessToLocationServices",
				//"DIYWhenNoBaseStationsAreAvailable",
				//"DIYCancelSetUpInRegisterBaseStation",
				//"DIYWhenQRCodeIsNotScanned",
				//"DIYWhenInvalidQRCodeIsScanned",
				//"DIYRefreshBaseStationsList",
				//"DIYDisconnectDASDevice",
				//"DIYTimeoutInDASDevice",
				//"DIYTurnOffMobileDeviceBluetooth",
				//"DIYCancelSetUpInConnectToNetworkScreen",
				//"DIYMoveAwayFromDASDeviceAfterScanningQRCode",
				//"DIYAddAWiFiNetwork",
				//"DIYInvalidWiFiPassword",
				//"DIYRegistrationWhenSingleBaseStationIsAvailable",
				//"DIYRegistrationWithNewLocationAndBaseStationName",
				//"DIYRegistrationWithSensorsGeoFencingOnAndAlexaConnect",
				//"DIYMultipleDASRegistrationsForTheSameAccount",
				//"DIYRegistrationByReceivingCallsAndMsgs",
				"DIYAddAWiFiNetworkWithInvalidPwdAndTryReconnectingWithAvailableNetwork",
				//"DIYConnectingToOpenWiFiNetwork",
				//"DIYRegistrationByNavigatingToOtherApps",
				//"DIYTryToReRegisterDAS",
				//"DIYTryToReRegisterDASAfterPerformingFactorySettingsOnDAS",
				//"DIYTryToReRegisterDASWhenDASIsOffline",
				//"DIYDeleteExistingDASAndRegisterIt",
				};

		//commandLineArguments = new String[] {"--help"};
		//commandLineArguments = new String[] {"--updateKeywordCatalogue"};
		
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
