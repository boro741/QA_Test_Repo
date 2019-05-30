package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;

public class ManageUsersScreen extends MobileScreens {

	private static final String screenName = "ManageUsers";

	public ManageUsersScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar", false);
	}

	public boolean isManageUsersScreenHeaderVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ManageUsersScreenHeader");
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean clickOnBackButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		}
		return flag;
	}

	public boolean isNoInvitedUsersLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoInvitedUsersLabel");
	}

	public boolean isInviteNewUserButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InviteNewUserButton");
	}

	public boolean clickOnInviteNewUserButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "InviteNewUserButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "InviteNewUserButton");
		}
		return flag;
	}

	public boolean isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(TestCaseInputs inputs,
			String invitedUsersEmailAddress) {
		boolean flag = false;
		List<WebElement> invitedUsersList = new ArrayList<>();
		if (invitedUsersEmailAddress.equalsIgnoreCase("USER WHO INVITED THE LOGGED IN USER")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitedUsersList")) {
				invitedUsersList = MobileUtils.getMobElements(objectDefinition, testCase, "InvitedUsersList");
				for (WebElement ele : invitedUsersList) {
					if (ele.getText().equalsIgnoreCase(inputs.getInputValue("USERID").toString())) {
						flag = true;
						break;
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitedUsersList")) {
				invitedUsersList = MobileUtils.getMobElements(objectDefinition, testCase, "InvitedUsersList");
				for (WebElement ele : invitedUsersList) {
					if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	public boolean isInviteUserScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InviteUserScreenHeader");
	}

	public boolean isInvitieUserScreenSubTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitieUserScreenSubTitle");
	}

	public boolean isEmailTextFieldInInviteUserScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailTextFieldInInviteUserScreen");
	}

	public boolean isSendButtonEnabled() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SendButton")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "SendButton").getAttribute("enabled")
						.equalsIgnoreCase("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean clickOnSendButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SendButton");
	}

	public boolean isDeleteInvitedEmailIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteInvitedEmailIcon");
	}

	public boolean clickOnDeleteInvitedEmailIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteInvitedEmailIcon");
	}

	public boolean isAddUsersScreenHeaderVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "AddUsersScreenHeader");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Add Users']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='Location Name' and contains(@value,'Add Users_')]", testCase);
		}
	}

	public boolean isNoUsersLabelVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "NoInvitedUsersLabel");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*
			 * if (!MobileUtils.isMobElementExists("ID",
			 * "list_item_lyric_subtext_primary_text_view", testCase)) { return true; } else
			 * { return false; }
			 */
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='No Users']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "No Users", testCase);
		}
	}

	public boolean isAddUserButtonVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "AddUserButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "add_users_btn", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Add User", testCase);
		}
	}

	public boolean clickOnAddUserButton() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "AddUserButton")) { flag &= MobileUtils.clickOnElement(objectDefinition,
		 * testCase, "AddUserButton"); }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "add_users_btn", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "add_users_btn");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Add User", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Add User");
		}
		return flag;
	}

	public boolean enterInviteEmailAddress(TestCaseInputs inputs, String inviteEmailAddress) {
		boolean flag = true;
		@SuppressWarnings("rawtypes")
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*
			 * flag &= MobileUtils.setValueToElement(objectDefinition, testCase,
			 * "EmailTextFieldInInviteUserScreen", inviteEmailAddress);
			 */
			flag &= MobileUtils.setValueToElement(testCase, "ID", "fragment_invite_user_email_edit_text",
					inviteEmailAddress);
			System.out.println("######dimensions.width:a- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Email Address text field in Invite User Screen");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='User Sync Pending']",
						testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"User Sync Pending popup is displayed. Click on OK button in the popup.");
					MobileUtils.clickOnElement(testCase, "ID", "button1");
				} else {
					Keyword.ReportStep_Pass(testCase,
							"User Sync Pending popup is not displayed. Click on Send button in the Invite User screen.");
				}
				if (this.isSendButtonEnabled()) {
					flag &= this.clickOnSendButton();
				}
			}
			return flag;
		} else {
			flag &= MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField", inviteEmailAddress);
			if (inputs.isRunningOn("SauceLabs")) {
				System.out.println("##############hideKeyboardIOS");
				MobileUtils.hideKeyboardIOS(testCase.getMobileDriver(), "Done");
			} else {
				// flag &= MobileUtils.clickOnElement(objectDefinition, testCase,
				// "DoneButtonInKeyboard");
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Done");
			}
			if (this.isSendButtonEnabled()) {
				flag &= this.clickOnSendButton();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isAlreadyInvitedUserMsgVisibleInAndroid() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@text='The requested user was already added to this account.']",
					testCase);
		}
		return flag;
	}

	public boolean isAlreadyInvitedUserErrorPopupTitleVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "AlreadyInvitedUserErrorPopupTitle");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Error", testCase);
		}
	}

	public boolean isAlreadyInvitedUserErrorPopupMsgVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "AlreadyInvitedUserErrorPopupMsg");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "The requested user was already added to this account",
					testCase);
		}
	}

	public boolean isOKButtonInAlreadyInviteduserErrorPopupVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "OKButtonInAlreadyInviteduserErrorPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "OK", testCase);
		}
	}

	public boolean clickOnOKButtonInAlreadyInviteduserErrorPopup() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "OKButtonInAlreadyInviteduserErrorPopup")) { flag &=
		 * MobileUtils.clickOnElement(objectDefinition, testCase,
		 * "OKButtonInAlreadyInviteduserErrorPopup"); }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "OK", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "OK");
		}
		return flag;
	}

	public boolean deleteInvitedUserInAddUsersScreen(String invitedUsersEmailAddress) {
		boolean flag = false;
		List<WebElement> invitedUsersList = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "list_item_lyric_subtext_primary_text_view", testCase)) {
				invitedUsersList = MobileUtils.getMobElements(testCase, "ID",
						"list_item_lyric_subtext_primary_text_view");
				for (WebElement ele : invitedUsersList) {
					if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
						flag = true;
						@SuppressWarnings("rawtypes")
						TouchAction t1 = new TouchAction(testCase.getMobileDriver());
						// t1.longPress(ele).perform();
						t1.longPress(longPressOptions().withElement(element(ele))).perform();
						if (MobileUtils.isMobElementExists("ID", "list_item_delete_user_button_view", testCase)) {
							MobileUtils.clickOnElement(testCase, "ID", "list_item_delete_user_button_view");
						}
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitedUsersList")) {
				invitedUsersList = MobileUtils.getMobElements(objectDefinition, testCase, "InvitedUsersList");
				for (WebElement ele : invitedUsersList) {
					if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
						if (MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_title') and @value='"
										+ invitedUsersEmailAddress
										+ "']/following-sibling::XCUIElementTypeButton[starts-with(@name,'invited_user_0_') and contains(@name,'rightButton')]",
								testCase)) {
							flag = true;
							Keyword.ReportStep_Pass(testCase, "Delete icon for the invited users email address: "
									+ invitedUsersEmailAddress + " is displayed. Clicking on it.");
							MobileUtils.clickOnElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_title') and @value='"
											+ invitedUsersEmailAddress
											+ "']/following-sibling::XCUIElementTypeButton[starts-with(@name,'invited_user_0_') and contains(@name,'rightButton')]");
						}
					}
				}
			}
		}
		System.out.println(flag);
		return flag;
	}

	public boolean isConfirmAccessRemovalPopupTitleVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "ConfirmAccessRemovalPopupTitle");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Confirm Access Removal']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Confirm Access Removal", testCase);
		}
	}

	public boolean isConfirmAccessRemovalPopupMsgVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "ConfirmAccessRemovalPopupMsg");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[contains(@text,'Are you sure you want to remove access for')]",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[contains(@name,'Are you sure you want to remove access for')]",
					testCase);
		}
	}

	public boolean isCancelButtonInConfirmAccessRemovalPopupVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "CancelButtonInConfirmAccessRemovalPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "button2", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Cancel", testCase);
		}
	}

	public boolean clickOnCancelButtonInConfirmAccessRemovalPopup() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "CancelButtonInConfirmAccessRemovalPopup")) { flag &=
		 * MobileUtils.clickOnElement(objectDefinition, testCase,
		 * "CancelButtonInConfirmAccessRemovalPopup"); }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "button2", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "button2");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Cancel", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Cancel");
		}
		return flag;
	}

	public boolean isRemoveButtonInConfirmAccessRemovalPopupVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "RemoveButtonInConfirmAccessRemovalPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "button1", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Remove", testCase);
		}
	}

	public boolean clickOnRemoveButtonInConfirmAccessRemovalPopup() {
		boolean flag = true;
		/*
		 * if (MobileUtils.isMobElementExists(objectDefinition, testCase,
		 * "RemoveButtonInConfirmAccessRemovalPopup")) { flag &=
		 * MobileUtils.clickOnElement(objectDefinition, testCase,
		 * "RemoveButtonInConfirmAccessRemovalPopup"); }
		 */
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "button1", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "button1");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Remove", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Remove");
		}
		return flag;
	}

	public boolean isTheRequestedUserAlreadyAddedErrorDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UserAlreadyAddedErrorValidation");
	}

	public boolean isDeleteUserPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteUserPopupTitle");
	}

	public boolean isDeleteUserPopupMsgVisible(String invitedUsersEmailAddress) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
				"//android.widget.TextView[contains(@text,'This will delete " + invitedUsersEmailAddress + " from this account')]",testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeStaticText[@name='This will delete "
					+ invitedUsersEmailAddress + " from this account']", testCase);
		}
	}

	public boolean isCancelButtonInDeleteUserPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInDeleteUserPopup");
	}

	public boolean isOKButtonInDeleteUserPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInDeleteUserPopup");
	}
	
	public boolean clickOnCancelButtonInDeleteUserPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInDeleteUserPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInDeleteUserPopup");
		}
		return flag;
	}
	
	public boolean isInvitedUserErrorMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitedUserErrorMsg");
	}
	
	public String getInvitedUserErrorMsg() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "InvitedUserErrorMsg");
	}
	
	public boolean clickOnOKButtonInDeleteUserPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInDeleteUserPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInDeleteUserPopup");
		}
		return flag;
	}
}