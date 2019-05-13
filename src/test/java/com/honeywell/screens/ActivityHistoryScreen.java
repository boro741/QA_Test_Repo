package com.honeywell.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class ActivityHistoryScreen extends MobileScreens {

	private static final String screenName = "ActivityHistoryScreen";

	public ActivityHistoryScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isMessagesDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Message");
	}

	public boolean isActivityHistoryTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryTitle");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isNoMessagesLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoMessagesLabel");
	}

	public List<WebElement> getActivityHistoryMsgTitle() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "ActivityHistoryMsgTitle");
	}

	public boolean clickOnMessage() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ActivityHistoryMsgTitle");
	}

	public boolean isActivityHistoryMsgTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityHistoryMsgTitle");
	}

	public boolean isEditButtonVisible() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EditButton"))
		 * { return flag; } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='Edit']", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeButton[@value='Edit']", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		// }
		return flag;
	}

	public boolean clickOnEditButton() {
		boolean flag = true;
		// flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "EditButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='Edit']");
		} else {
			flag &= MobileUtils.clickOnElement(testCase, "ID", "Navigation_Right_Bar_Item");
		}
		return flag;
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public HashMap<String, String> getAllMessages(TestCases testCase) throws Exception {
		HashMap<String, String> combinedMessage = new HashMap<String, String>();
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

				List<WebElement> messagesGroup = MobileUtils.getMobElements(objectDefinition, testCase, "Message",
						false);
				int i = 1;
				for (int j = 1; j < messagesGroup.size(); j++) {
					WebElement ele = testCase.getMobileDriver().findElement(By.xpath("//android.widget.LinearLayout["
							+ i
							+ "]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView"));
					WebElement ele1 = testCase.getMobileDriver().findElement(By.xpath("//android.widget.LinearLayout["
							+ i
							+ "]/android.widget.LinearLayout/android.widget.RelativeLayout//following-sibling::android.widget.TextView"));
					if (!combinedMessage.containsKey(ele.getAttribute("text"))) {
						combinedMessage.put(ele.getAttribute("text"), ele1.getAttribute("text"));
					}
					System.out.println(ele.getAttribute("text"));
					System.out.println(ele1.getAttribute("text"));
					i++;
				}

			} else {// IOS
				List<WebElement> messagesGroup = MobileUtils.getMobElements(objectDefinition, testCase, "Message",
						false);
				int i = 1;
				// for (WebElement message : messagesGroup) {
				for (int j = 1; j < messagesGroup.size(); j++) {
					//// XCUIElementTypeCell/XCUIElementTypeStaticText[1]
					WebElement ele = testCase.getMobileDriver().findElement(
							By.xpath("(//XCUIElementTypeStaticText[@name='Messages_subTitle'])[" + (i + 1) + "]"));
					WebElement ele1 = testCase.getMobileDriver().findElement(
							By.xpath("(//XCUIElementTypeStaticText[@name='Messages_detail_subTitle'])[" + j + "]"));
					if (!combinedMessage.containsKey(ele.getAttribute("value"))) {
						combinedMessage.put(ele.getAttribute("value"), ele1.getAttribute("value"));
					}
					System.out.println(ele.getAttribute("value"));
					System.out.println(ele1.getAttribute("value"));
					i = i + 2;
				}

			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return combinedMessage;
	}

	public boolean isSelectAllButtonVisible() {
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "SelectAllButton")) { return true; } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "horizontal_button_previous", testCase);
		} else {
			return MobileUtils.isMobElementExists("ID", "LeftButton", testCase);
		}
		// }
	}

	public boolean clickOnSelectAllButton() {
		boolean flag = true;
		List<WebElement> selectedMsgList = new ArrayList<WebElement>();
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "SelectAllButton")) { flag &= MobileUtils.clickOnElement(objectDefinition,
		 * testCase, "SelectAllButton"); selectedMsgList =
		 * MobileUtils.getMobElements(objectDefinition, testCase,
		 * "SelectAMsgRadioButton"); for (WebElement ele : selectedMsgList) { if
		 * ((ele.getAttribute("checked") != null) &&
		 * (ele.getAttribute("checked").equalsIgnoreCase("true"))) { return flag; } else
		 * { flag = false; } } } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "horizontal_button_previous", testCase)) {
				flag &= MobileUtils.clickOnElement(testCase, "ID", "horizontal_button_previous");
				selectedMsgList = MobileUtils.getMobElements(testCase, "ID", "delete_radio_btn");
				for (WebElement ele : selectedMsgList) {
					if ((ele.getAttribute("checked") != null)
							&& (ele.getAttribute("checked").equalsIgnoreCase("true"))) {
						return flag;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to select the message");
					}
				}
			} else {
				flag = false;
			}
		} else {
			//iOS
			if (MobileUtils.isMobElementExists("NAME", "LeftButton", testCase)) {
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "LeftButton");
				selectedMsgList = MobileUtils.getMobElements(testCase, "XPATH",
						"//XCUIElementTypeCell[@name='Messages_cell']/XCUIElementTypeButton");
				for (WebElement ele : selectedMsgList) {
					if ((ele.getAttribute("value") != null) && (ele.getAttribute("value").equalsIgnoreCase("1"))) {
						return flag;
					} else {
						if (MobileUtils.isMobElementExists("NAME", "LeftButton", testCase)) {
							flag &= MobileUtils.clickOnElement(testCase, "NAME", "LeftButton");
							selectedMsgList = MobileUtils.getMobElements(testCase, "XPATH",
									"//XCUIElementTypeCell[@name='Messages_cell']/XCUIElementTypeButton");
							for (WebElement ele1 : selectedMsgList) {
								if ((ele1.getAttribute("value") != null)
										&& (ele1.getAttribute("value").equalsIgnoreCase("1"))) {
									return flag;
								}
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select all button");
						}
					}
				}
			} else {
				flag = false;
			}
		}
		// }
		return flag;
	}

	public boolean isDeletelButtonEnabled() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*
			 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
			 * "DeleteButton") && MobileUtils.getMobElement(objectDefinition, testCase,
			 * "DeleteButton").getAttribute("enabled") .equalsIgnoreCase("true")) { return
			 * flag; } else
			 */ if (MobileUtils.isMobElementExists("ID", "horizontal_button_next", testCase)
					&& MobileUtils.getMobElement(testCase, "ID", "horizontal_button_next").getAttribute("enabled")
							.equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			//iOS
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteButton")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "DeleteButton").getAttribute("enabled")
							.equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
			/*
			 * if (MobileUtils.isMobElementExists("NAME", "Delete", testCase) && MobileUtils
			 * .getMobElement(testCase, "NAME",
			 * "Delete").getAttribute("enabled").equalsIgnoreCase("true")) { return flag; }
			 * else { flag = false; }
			 */
			// }
		}
		return flag;
	}

	public boolean clickOnDeleteButton() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*
			 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
			 * "DeleteButton") && MobileUtils.getMobElement(objectDefinition, testCase,
			 * "DeleteButton").getAttribute("enabled") .equalsIgnoreCase("true")) { flag &=
			 * MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton"); }
			 * else {
			 */
			if (MobileUtils.isMobElementExists("ID", "horizontal_button_next", testCase)
					&& MobileUtils.getMobElement(testCase, "ID", "horizontal_button_next").getAttribute("enabled")
							.equalsIgnoreCase("true")) {
				flag &= MobileUtils.clickOnElement(testCase, "ID", "horizontal_button_next");
			} else {
				flag = false;
			}
			// }
		} else {
			
			 if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteButton") && MobileUtils.getMobElement(objectDefinition, testCase,
			 "DeleteButton").getAttribute("enabled") .equalsIgnoreCase("true")) { 
				 flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton"); }
			 else {
				 flag = false;
			}
			/*if (MobileUtils.isMobElementExists("NAME", "Delete", testCase) && MobileUtils
					.getMobElement(testCase, "NAME", "Delete").getAttribute("enabled").equalsIgnoreCase("true")) {
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Delete");
			} else {
				*/flag = false;
			
			// }
		}
		return flag;
	}

	public boolean isCancelOptionVisible() {
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "CancelOption")) { return true; } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//*[@text='Cancel']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeButton[@value='Cancel']", testCase);
		}
		// }
	}

	public boolean clickOnCancelOption() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "CancelOption")) { flag &= MobileUtils.clickOnElement(objectDefinition,
		 * testCase, "CancelOption"); } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='Cancel']");
		} else {
			flag &= MobileUtils.clickOnElement(testCase, "ID", "Navigation_Right_Bar_Item");
		}
		// }
		return flag;
	}

	public boolean isFirstMsgRadioButtonVisible() {
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "SelectAMsgRadioButton")) { return true; } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "delete_radio_btn", testCase);
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='Messages_cell'][1]", testCase)) {
				return true;
			} else {
				if (testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[@name='Messages_cell'][1]"))
						.isEnabled()) {
					return true;
				} else {
					return false;
				}
			}
		}
		// }
	}

	public boolean clickOnFirstMsgRadioButton() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "SelectAMsgRadioButton")) { flag &=
		 * MobileUtils.clickOnElement(objectDefinition, testCase,
		 * "SelectAMsgRadioButton"); } else {
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "delete_radio_btn", testCase)) {
				flag &= MobileUtils.clickOnElement(testCase, "ID", "delete_radio_btn");
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='Messages_cell'][1]", testCase)) {
				flag &= MobileUtils.clickOnElement(testCase, "XPATH",
						"//XCUIElementTypeCell[@name='Messages_cell'][1]");
			} else {
				if (testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[@name='Messages_cell'][1]"))
						.isDisplayed()) {
					testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[@name='Messages_cell'][1]"))
							.click();
					return true;
				} else {
					return false;
				}
			}
		}
		// }
		return flag;
	}

	public boolean firstMsgFromActivityHistoryListBeforeDelete(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		/*
		 * if ((MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgTime")) &&
		 * (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgTitle")) &&
		 * (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgDetail"))) {
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgTime"));
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgTitle"));
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgDetail")); } else { flag = false; }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if ((MobileUtils.isMobElementExists("ID", "view_notification_row_time", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_title", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_description", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_time"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_title"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_description"));
			} else {
				flag = false;
			}
		} else {
			if ((MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']",
					testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_subTitle'][1]", testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_detail_subTitle']", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE", MobileUtils
						.getFieldValue(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE", MobileUtils
						.getFieldValue(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_subTitle'][1]"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE", MobileUtils.getFieldValue(
						testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_detail_subTitle']"));
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean firstMsgFromActivityHistoryListAfterDelete(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		/*
		 * if ((MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgTime")) &&
		 * (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgTitle")) &&
		 * (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "ActivityHistoryMsgDetail"))) {
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgTime"));
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgTitle"));
		 * inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE",
		 * MobileUtils.getFieldValue(objectDefinition, testCase,
		 * "ActivityHistoryMsgDetail")); } else { flag = false; }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if ((MobileUtils.isMobElementExists("ID", "view_notification_row_time", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_title", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_description", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_time"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_title"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_description"));
			} else {
				flag = false;
			}
		} else {
			if ((MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']",
					testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_subTitle'][1]", testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_detail_subTitle']", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE", MobileUtils.getFieldValue(testCase,
						"XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE", MobileUtils
						.getFieldValue(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_subTitle'][1]"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE", MobileUtils.getFieldValue(
						testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_detail_subTitle']"));
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean firstMsgFromActivityHistoryListAfterGeofenceCross(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if ((MobileUtils.isMobElementExists("ID", "view_notification_row_time", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_title", testCase))
					&& (MobileUtils.isMobElementExists("ID", "view_notification_row_description", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_time"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_title"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE",
						MobileUtils.getFieldValue(testCase, "ID", "view_notification_row_description"));
			} else {
				flag = false;
			}
		} else {
			// iOS
			if ((MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']",
					testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_subTitle']", testCase))
					&& (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Messages_detail_subTitle'][1]", testCase))) {
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE", MobileUtils
						.getFieldValue(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_title']"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE", MobileUtils
						.getFieldValue(testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_subTitle'][1]"));
				inputs.setInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE", MobileUtils.getFieldValue(
						testCase, "XPATH", "//XCUIElementTypeStaticText[@name='Messages_detail_subTitle']"));
			} else {
				flag = false;
			}
		}
		return flag;
	}
}