package com.honeywell.lyric.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.deviceCloudProviders.PCloudyExecutionDesiredCapability.PCloudyDeviceInformation;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.perfecto.PerfectoLabUtils;
import com.honeywell.commons.report.FailType;

import io.appium.java_client.TouchAction;

public class LyricUtils {
	public static String takeScreenShot(String path, WebDriver driv) {

		String scrName = "#";

		if (driv == null) {
			return scrName;
		} else {
			try {
				File scrSht = ((TakesScreenshot) new Augmenter().augment((RemoteWebDriver) driv))
						.getScreenshotAs(OutputType.FILE);
				String temp = scrSht.getName();

				File scrFolder = new File(path);

				FileUtils.copyFileToDirectory(scrSht, scrFolder);
				scrName = temp;
			} catch (Exception e) {
				scrName = scrName + "Error : " + e.getCause();
			}
		}
		return scrName;
	}

	public static JSONObject getLocationInformation(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {
				long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));

				if (locationID == -1) {
					return jsonObject;
				}

				if (chUtil.isConnected()) {
					String chapiURL = getCHILURL(testCase, inputs);
					String url = chapiURL + "api/v3/locations/" + locationID;
					HttpURLConnection connection = chUtil.doGetRequest(url);

					try {

						if (connection != null) {

							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

							String inputLine;
							StringBuffer html = new StringBuffer();

							while (!in.ready()) {
							}

							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}

							in.close();

							jsonObject = new JSONObject(html.toString().trim());

						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"Get Location Information : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}

					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get StatLocation Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}
				}

			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Location Information  : Unable to connect to CHAPI.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Location Information  : Unable to get location. Error occured - " + e.getMessage());
			jsonObject = null;
		}

		return jsonObject;
	}

	public static JSONObject getDeviceInformation(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;

		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {
				long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));

				if (locationID == -1) {
					return jsonObject;
				}

				if (chUtil.isConnected()) {
					String chapiURL = getCHILURL(testCase, inputs);
					String url = chapiURL + "api/v3/locations/" + locationID;

					HttpURLConnection connection = chUtil.doGetRequest(url);

					try {

						if (connection != null) {

							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

							String inputLine;
							StringBuffer html = new StringBuffer();

							while (!in.ready()) {
							}

							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}

							in.close();

							JSONObject jsonObj = new JSONObject(html.toString().trim());

							JSONArray array = (JSONArray) jsonObj.get("devices");

							JSONObject tempJSONObject = null;

							boolean elementFound = false;

							for (int counter = 0; counter < array.length(); counter++) {
								tempJSONObject = array.getJSONObject(counter);

								if (inputs.getInputValue("LOCATION1_DEVICE1_NAME")
										.equalsIgnoreCase(tempJSONObject.getString("userDefinedDeviceName"))) {

									jsonObject = array.getJSONObject(counter);
									elementFound = true;
									break;
								}
							}

							if (elementFound) {
							} else {
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
										"Get Stat Information : Stat not found by name - "
												+ inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
							}
						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"Get Stat Information : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}

					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get Stat Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}

				} else {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Get Stat Information  : Unable to connect to CHAPI.");
				}
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get information for Stat - "
							+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " at location - "
							+ inputs.getInputValue("LOCATION1_NAME") + " : Error occured - " + e.getMessage());
			jsonObject = null;
		}

		return jsonObject;
	}

	public static String getCHILURL(TestCases testCase, TestCaseInputs inputs) throws Exception {
		String chilURL = " ";
		try {
			if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("Production")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_PRODUCTION");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("CHIL Int (Azure)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_INT");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("CHIL Dev(Dev2)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DEV2");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("CHIL Stage (Azure)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_STAGING");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("Load Testing")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_LOAD_TESTING");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("Chil Das(QA)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_QA");
			} else if (inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT).equals("Chil Das(Test)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_TEST");
			} else {
				throw new Exception("Invalid URL");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return chilURL;
	}

	public static boolean loginToLyricApp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "LoginScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "LoginButton", 3)
				&& !MobileUtils.isMobElementExists(fieldObjects, testCase, "EmailAddress", 3)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "LoginButton");
		}
		if (MobileUtils.setValueToElement(fieldObjects, testCase, "EmailAddress",
				inputs.getInputValue("USERID").toString())) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileUtils.hideKeyboard(testCase.getMobileDriver());
			}
			Keyword.ReportStep_Pass(testCase,
					"Login To Lyric : Email Address set to - " + inputs.getInputValue("USERID"));
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Login To Lyric : Not able to set Email Address.");
			flag = false;
		}
		if (MobileUtils.setValueToElement(fieldObjects, testCase, "Password",
				inputs.getInputValue("PASSWORD").toString())) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileUtils.hideKeyboard(testCase.getMobileDriver());
			}
			Keyword.ReportStep_Pass(testCase, "Login To Lyric : Password set to - " + inputs.getInputValue("PASSWORD"));
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Login To Lyric : Not able to set Password.");
			flag = false;
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "LoginButton", 5)) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "LoginButton");
			}
		} else {
			flag = flag & MobileUtils.clickOnElement(testCase, "NAME", "Login_Button");
		}
		return flag;
	}

	public static boolean verifyLoginSuccessful(TestCases testCase, TestCaseInputs inputs) {
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "LoginScreen");
		boolean flag = true;
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);

		try {
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						int counter = 0;
						while (MobileUtils.isMobElementExists("name", "Allow", testCase, 2, false) && counter < 3) {
							MobileUtils.clickOnElement(testCase, "name", "Allow");
							counter++;
						}
						if (MobileUtils.isMobElementExists("name", "Not Now", testCase, 3, false)) {
							MobileUtils.clickOnElement(testCase, "name", "Not Now");
							return false;
						}
					} else {
						if (MobileUtils.isMobElementExists("id", "btn_close", testCase, 3, false)) {
							MobileUtils.clickOnElement(testCase, "id", "btn_close");
							return false;
						}
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AlertsIcon", 2, false)) {
						return true;
					} 
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DontUseButton", 2, false)) {
						MobileUtils.clickOnElement(fieldObjects, testCase, "DontUseButton");
						return false;
					} 
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CreateAPasscodeTitle", 3)) {
						createPasscode(testCase, inputs.getInputValue("PASSCODE"));
						return false;
					} 
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PasscodePopUpTitle", 2)) {
						MobileUtils.clickOnElement(fieldObjects, testCase, "CreatePasscodeButton");
						LyricUtils.createPasscode(testCase, inputs.getInputValue("PASSCODE"));
						return false;
					} else {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Login to Lyric : Successfully navigated to HomeScreen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Login To Lyric : Unable to navigate to homepage. Could not find notification icon on homepage");
			}

		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Timed out while loading. Wait time : 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean closeAppLaunchPopups(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "LoginScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "com.android.packageinstaller:id/permission_allow_button",
					testCase, 10)) {
				if (!MobileUtils.clickOnElement(testCase, "ID",
						"com.android.packageinstaller:id/permission_allow_button")) {
					flag = false;
				}
			}
		} else {
			int counter = 0;
			while (!MobileUtils.isMobElementExists(fieldObjects, testCase, "LyricLogo", 2) && counter < 5) {
				if (MobileUtils.isMobElementExists("name", "Allow", testCase, 3)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "Allow")) {
						flag = false;
					}
				}
				if (MobileUtils.isMobElementExists("name", "OK", testCase, 3)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "OK")) {
						flag = false;
					}
				}
				if (MobileUtils.isMobElementExists("name", "Cancel", testCase, 3)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "Cancel")) {
						flag = false;
					}
				}
				counter++;
			}
		}
		return flag;
	}

	public static boolean createPasscode(TestCases testCase, String passcode) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "LoginScreen");

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CreateAPasscodeTitle", 5, false)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(fieldObjects, testCase, "CreateAPasscodeTitle").getAttribute("text")
						.equalsIgnoreCase("Create a Passcode")) {
					Keyword.ReportStep_Pass(testCase, "Entering passcode in 'Create a Passcode Screen'");
					flag = flag & enterPasscode(testCase, passcode);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully set passcode to : " + passcode);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set the passcode to: " + passcode);
					}
					if (MobileUtils.getMobElement(fieldObjects, testCase, "CreateAPasscodeTitle").getAttribute("text")
							.equalsIgnoreCase("Verify Passcode")) {
						Keyword.ReportStep_Pass(testCase, "Entering passcode in 'Verify Passcode Screen'");
						flag = flag & enterPasscode(testCase, passcode);
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Successfully set passcode to : " + passcode);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to set the passcode to: " + passcode);
						}
					}
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Add Fingerprint Unlock']", testCase, 5)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='No' or @text='NO']");
					}
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Entering passcode in 'Create a Passcode Screen'");
				flag = flag & enterPasscode(testCase, passcode);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully set passcode to : " + passcode);
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to set the passcode to: " + passcode);
				}

				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "VerifyPasscodeTitle", 5, false)) {
					Keyword.ReportStep_Pass(testCase, "Entering passcode in 'Verify Passcode Screen'");
					flag = flag & enterPasscode(testCase, passcode);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully set passcode to : " + passcode);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set the passcode to: " + passcode);
					}
				}
				if (MobileUtils.isMobElementExists("name", "Add Touch ID", testCase, 5)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "name", "No");
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Create Passcode Screen not visible");
		}
		return flag;
	}

	public static boolean enterPasscode(TestCases testCase, String passcode) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement ele = MobileUtils.getMobElement(testCase, "id", "activity_detail_container");
			Dimension d1 = ele.getSize();
			HashMap<Integer, Integer> xValues = new HashMap<Integer, Integer>();
			xValues.put(1, (int) (d1.getWidth() * 0.15));
			xValues.put(4, (int) (d1.getWidth() * 0.15));
			xValues.put(7, (int) (d1.getWidth() * 0.15));
			xValues.put(2, (int) (d1.getWidth() * 0.40));
			xValues.put(5, (int) (d1.getWidth() * 0.40));
			xValues.put(8, (int) (d1.getWidth() * 0.40));
			xValues.put(0, (int) (d1.getWidth() * 0.40));
			xValues.put(3, (int) (d1.getWidth() * 0.65));
			xValues.put(6, (int) (d1.getWidth() * 0.65));
			xValues.put(9, (int) (d1.getWidth() * 0.65));
			HashMap<Integer, Integer> yValues = new HashMap<Integer, Integer>();
			yValues.put(1, (int) (d1.getHeight() * 0.7));
			yValues.put(2, (int) (d1.getHeight() * 0.7));
			yValues.put(3, (int) (d1.getHeight() * 0.7));
			yValues.put(4, (int) (d1.getHeight() * 0.8));
			yValues.put(5, (int) (d1.getHeight() * 0.8));
			yValues.put(6, (int) (d1.getHeight() * 0.8));
			yValues.put(7, (int) (d1.getHeight() * 0.9));
			yValues.put(8, (int) (d1.getHeight() * 0.9));
			yValues.put(9, (int) (d1.getHeight() * 0.9));
			yValues.put(0, (int) (d1.getHeight() * 0.95));
			TouchAction t1 = new TouchAction(testCase.getMobileDriver());
			for (int i = 0; i < passcode.length(); i++) {
				char c = passcode.charAt(i);
				int value = Integer.parseInt(String.valueOf(c));// Character.getNumericValue(c);
				t1.tap(xValues.get(value), yValues.get(value)).perform();
			}
		} else {
			for (int i = 0; i < passcode.length(); i++) {
				char c = passcode.charAt(i);
				flag = flag & MobileUtils.clickOnElement(testCase, "name", String.valueOf(c));
			}
		}
		return flag;
	}

	public static boolean setAppEnvironment(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		String environmentToSelect = inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT);
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "LoginScreen");
		WebElement element = null;
		try {
			if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
			{
				element = MobileUtils.getMobElement(fieldObjects, testCase, "HoneywellRosette");
				flag = flag & MobileUtils.longPress(testCase, element, 8000);
			}
			else
			{
				element = MobileUtils.getMobElement(fieldObjects, testCase, "LyricLogo");
				if (!MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeTextField[contains(@value,'https')]",
						testCase, 3, false)) {
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					int x = element.getSize().getWidth()/2 + element.getLocation().getX();
					int y = (int) (element.getLocation().getY() - (element.getSize().getHeight() * 2));
					action.press(x,y).waitAction(MobileUtils.getDuration(8000)).release().perform();
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Set App Environment : Failed to open secret menu");
			return flag;
		}

		String buttonToClick = "";
		environmentToSelect = environmentToSelect.replaceAll("\\s", "");
		if (environmentToSelect.equalsIgnoreCase("ChilDas(QA)")) {
			buttonToClick = "Chil Das(QA)";
		} else if (environmentToSelect.equalsIgnoreCase("Production")) {
			buttonToClick = "Production";
		} else if (environmentToSelect.equalsIgnoreCase("CHILStage(Azure)")) {
			buttonToClick = "CHIL Stage (Azure)";
		} else if (environmentToSelect.equalsIgnoreCase("CHILInt(Azure)")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				buttonToClick = "CHIL Int (Azure)";
			} else {
				buttonToClick = "Camera DEV - CHIL INT";
			}
		} else if (environmentToSelect.equalsIgnoreCase("ChilDev(Dev2)")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				buttonToClick = "Chil Dev(Dev2)";
			} else {
				buttonToClick = "Chil Das(DEV2)";
			}
		} else if (environmentToSelect.equalsIgnoreCase("LoadTesting")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				buttonToClick = "Load Testing";
			} else {
				buttonToClick = "Chil Load(Test)";
			}
		} else if (environmentToSelect.equalsIgnoreCase("ChilDas(Test)")) {
			buttonToClick = "Chil Das(Test)";
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Environment");
			return false;
		}
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			try {
				if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeTextField[contains(@value,'https')]",
						testCase, 5, false)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
							"//XCUIElementTypeTextField[contains(@value,'https')]");
				}

				if (MobileUtils.isMobElementExists("name", buttonToClick, testCase, 2)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "name", buttonToClick);
				} else {
					element = MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypeTable");
					Dimension d1 = element.getSize();
					Point p1 = element.getLocation();
					testCase.getMobileDriver().swipe(p1.getX(), p1.getY(), p1.getX(), d1.getHeight(), 1);
					if (MobileUtils.isMobElementExists("name", buttonToClick, testCase, 2)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "name", buttonToClick);
					} else {
						testCase.getMobileDriver().swipe(p1.getX(), p1.getY(), p1.getX(), -d1.getHeight(), 1);
						if (MobileUtils.isMobElementExists("name", buttonToClick, testCase, 2)) {
							flag = flag & MobileUtils.clickOnElement(testCase, "name", buttonToClick);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not find environment : " + environmentToSelect);
							return flag;
						}
					}
				}
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//XCUIElementTypeTextField[contains(@value,'https')]");
				flag = flag & MobileUtils.clickOnElement(testCase, "name", "Done");
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set App Environment :  Error Occured - " + e.getMessage());
				flag = false;
			}
		} else {
			try {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WebServerURL")) {
					flag = MobileUtils.clickOnElement(fieldObjects, testCase, "WebServerURL");
					Thread.sleep(1000);
				}
				String xpath = "//*[@text='" + buttonToClick + "']";

				if (MobileUtils.clickOnElement(testCase, "XPATH", xpath)) {
					Keyword.ReportStep_Pass(testCase,
							"Set App Environment :  App environment set to " + environmentToSelect);
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FRAMEWORK_CONFIGURATION,
							"Set App Environment : Not able to Select the required environment - "
									+ environmentToSelect);
					flag = false;
				}
				if (!MobileUtils.getMobElement(testCase, "id", "url_selection_header").getAttribute("text")
						.equalsIgnoreCase(buttonToClick)) {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "WebServerURL");
					Thread.sleep(1000);
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", xpath);
				}
				if (MobileUtils.pressBackButton(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Set App Environment : Navigating back to Login screen");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Set App Environment : Navigating back to Login screen failed");
					flag = false;
				}
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set App Environment : [Set App Environment ] : Error occured - " + e.getMessage());
				flag = false;
			}
		}

		flag = flag & (MobileUtils.getMobElement(fieldObjects, testCase, "LyricLogo") == null) ? false : true;
		return flag;
	}

	public static boolean launchAndLoginToApplication(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		flag = MobileUtils.launchApplication(inputs, testCase, true);
		flag = flag & LyricUtils.closeAppLaunchPopups(testCase);
		flag = flag & LyricUtils.setAppEnvironment(testCase, inputs);
		flag = flag & LyricUtils.loginToLyricApp(testCase, inputs);
		flag = flag & LyricUtils.verifyLoginSuccessful(testCase, inputs);

		return flag;
	}
	public static TimeZone getDeviceTimeZone(TestCases testCase,
			TestCaseInputs inputs) throws Exception {
		TimeZone timeZone = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String zone = new String();
			try {
				if (inputs.isRunningOn("Local")) {
					if (inputs.isInputAvailable("ANDROID_UDID")) {
						zone = ADBUtils.getAndroidMobileDeviceTimeZone(inputs
								.getInputValue("ANDROID_UDID"));
					} else {
						zone = ADBUtils.getAndroidMobileDeviceTimeZone();
					}
				} else if (inputs.isRunningOn("Perfecto")) {
					zone = PerfectoLabUtils.getTimeZoneAndroidOnly(testCase
							.getMobileDriver());
				} else if (inputs.isRunningOn("pCloudy")) {
					PCloudyDeviceInformation deviceInfo = testCase
							.getPcloudyDeviceInformation();
					zone = deviceInfo
							.getpCloudySession()
							.getConnector()
							.executeAdbCommand(deviceInfo.getAuthToken(),
									deviceInfo.getBookingDtoDevice(),
									"adb shell getprop persist.sys.timezone");
				} else if (inputs.isRunningOn("TestObject")) {
					zone = "CET";
				} else if (inputs.isRunningOn("Saucelabs")) {
					zone = "CET";
				}
				zone = zone.trim();
				timeZone = TimeZone.getTimeZone(zone);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			try {
				if (inputs.isRunningOn("Local")) {
					timeZone = TimeZone.getDefault();
				} else if (inputs.isRunningOn("Perfecto")) {
					timeZone = TimeZone.getTimeZone("US/Eastern");
				} else if (inputs.isRunningOn("Saucelabs")) {
					timeZone = TimeZone.getTimeZone("US/Pacific");
				} else if (inputs.isRunningOn("TestObject")) {
					timeZone = TimeZone.getTimeZone("CET");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return timeZone;
	}
	public static String getDeviceTime(TestCases testCase, TestCaseInputs inputs) {
		String time = " ";
		try {
			Calendar date = Calendar.getInstance(LyricUtils.getDeviceTimeZone(
					testCase, inputs));
			String ampm;
			if (date.get(Calendar.AM_PM) == Calendar.AM) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			String hour;
			if (date.get(Calendar.HOUR) == 0) {
				hour = "12";
			} else {
				hour = String.valueOf(date.get(Calendar.HOUR));
			}
			String minute;
			if (date.get(Calendar.MINUTE) < 10) {
				minute = "0" + date.get(Calendar.MINUTE);
			} else {
				minute = String.valueOf(date.get(Calendar.MINUTE));
			}
			int month = date.get(Calendar.MONTH) + 1;
			time = String.valueOf(date.get(Calendar.YEAR) + "-" + month + "-"
					+ date.get(Calendar.DAY_OF_MONTH) + "T" + hour + ":"
					+ minute + " " + ampm);
		} catch (Exception e) {
			time = "";
			Keyword.ReportStep_Fail(
					testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Get Android Device Time : Error Occured : "
							+ e.getMessage());
		}

		return time;
	}
}
