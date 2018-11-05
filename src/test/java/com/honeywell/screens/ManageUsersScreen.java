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

	public boolean isAddUsersScreenHeaderVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddUsersScreenHeader");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Add Users']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='Location Name' and contains(@value,'Add Users_')]", testCase);
		}
	}
	
	public boolean isBackButtonVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//*[@class='android.widget.ImageButton']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Back", testCase);
		}
	}
	
	public boolean clickOnBackButton() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		}*/
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@class='android.widget.ImageButton']", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "XPATH", "//*[@class='android.widget.ImageButton']");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Back", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Back");
		}
		return flag;
	}
	
	public boolean isNoInvitedUsersLabelVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoInvitedUsersLabel");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (!MobileUtils.isMobElementExists("ID", "list_item_lyric_subtext_primary_text_view", testCase)) {
				return true;
			} else {
				return false;
			}
		} else {
			return MobileUtils.isMobElementExists("NAME", "No Invited Users", testCase);
		}
	}
	
	public boolean isAddUserButtonVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddUserButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "add_users_btn", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Add User", testCase);
		}
	}
	
	public boolean clickOnAddUserButton() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddUserButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AddUserButton");
		}*/
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "add_users_btn", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "add_users_btn");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Add User", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Add User");
		}
		return flag;
	}
	
	public boolean isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(TestCaseInputs inputs,
			String invitedUsersEmailAddress) {
		boolean flag = false;
		List<WebElement> invitedUsersList = new ArrayList<>();
		/*if (invitedUsersEmailAddress.equalsIgnoreCase("USER WHO INVITED THE LOGGED IN USER")) {
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
		}*/
		if (invitedUsersEmailAddress.equalsIgnoreCase("USER WHO INVITED THE LOGGED IN USER")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("ID", "list_item_lyric_subtext_primary_text_view", testCase)) {
					invitedUsersList = MobileUtils.getMobElements(testCase, "ID",
							"list_item_lyric_subtext_primary_text_view");
					for (WebElement ele : invitedUsersList) {
						if (ele.getText().equalsIgnoreCase(inputs.getInputValue("USERID").toString())) {
							flag = true;
							break;
						}
					}
				}
			} else {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]",
						testCase)) {
					invitedUsersList = MobileUtils.getMobElements(testCase, "XPATH",
							"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]");
					for (WebElement ele : invitedUsersList) {
						if (ele.getText().equalsIgnoreCase(inputs.getInputValue("USERID").toString())) {
							flag = true;
							break;
						}
					}
				}
			}
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("ID", "list_item_lyric_subtext_primary_text_view", testCase)) {
					invitedUsersList = MobileUtils.getMobElements(testCase, "ID",
							"list_item_lyric_subtext_primary_text_view");
					for (WebElement ele : invitedUsersList) {
						if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
							flag = true;
							break;
						}
					}
				}
			} else {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]",
						testCase)) {
					invitedUsersList = MobileUtils.getMobElements(testCase, "XPATH",
							"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]");
					for (WebElement ele : invitedUsersList) {
						if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
							flag = true;
							break;
						}
					}
				}
			}
		}
		return flag;
	}
	
	public boolean isInviteUserScreenTitleVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "InviteUserScreenHeader");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Invite Users']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='Location Name' and @value='Invite User']", testCase);
		}
	}
	
	public boolean isInvitieUserScreenSubTitleVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvitieUserScreenSubTitle");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@text='Who would you like to invite to Home?']", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='Who would you like to invite to Home?']", testCase);
		}
	}
	
	public boolean isEmailTextFieldInInviteUserScreenVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailTextFieldInInviteUserScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "fragment_invite_user_email_edit_text", testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeTextField[@name='Email TextField']",
					testCase);
		}
	}
	
	public boolean isSendButtonEnabled() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SendButton")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "SendButton").getAttribute("enabled")
						.equalsIgnoreCase("true")) {
			return flag;
		} else {
			flag = false;
		}*/
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "fragment_invite_user_send_button", testCase)
					&& MobileUtils.getMobElement(testCase, "ID", "fragment_invite_user_send_button")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Send", testCase) && MobileUtils
					.getMobElement(testCase, "NAME", "Send").getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean clickOnSendButton() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SendButton")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "SendButton").getAttribute("enabled")
						.equalsIgnoreCase("true")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "SendButton");
		} else {
			flag = false;
		}*/
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "fragment_invite_user_send_button", testCase)
					&& MobileUtils.getMobElement(testCase, "ID", "fragment_invite_user_send_button")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				MobileUtils.clickOnElement(testCase, "ID", "fragment_invite_user_send_button");
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Send", testCase) && MobileUtils
					.getMobElement(testCase, "NAME", "Send").getAttribute("enabled").equalsIgnoreCase("true")) {
				MobileUtils.clickOnElement(testCase, "NAME", "Send");
			} else {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean enterInviteEmailAddress(String inviteEmailAddress) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "EmailTextFieldInInviteUserScreen",
					inviteEmailAddress);*/
			flag &= MobileUtils.setValueToElement(testCase, "ID", "fragment_invite_user_email_edit_text", inviteEmailAddress);
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			touchAction.tap((dimensions.width - 100), (dimensions.height - 100)).perform();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='User Sync Pending']", testCase)) {
					Keyword.ReportStep_Pass(testCase, "User Sync Pending popup is displayed. Click on OK button in the popup.");
					MobileUtils.clickOnElement(testCase, "ID", "button1");
				}
			}
			return flag;
		} else {
			flag &= MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField",
					inviteEmailAddress);
			//flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
			flag &= MobileUtils.clickOnElement(testCase, "NAME", "Done");
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
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlreadyInvitedUserErrorPopupTitle");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Error", testCase);
		}
	}
	
	public boolean isAlreadyInvitedUserErrorPopupMsgVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlreadyInvitedUserErrorPopupMsg");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "The requested user was already added to this account",
					testCase);
		}
	}
	
	public boolean isOKButtonInAlreadyInviteduserErrorPopupVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInAlreadyInviteduserErrorPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "OK", testCase);
		}
	}
	
	public boolean clickOnOKButtonInAlreadyInviteduserErrorPopup() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInAlreadyInviteduserErrorPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInAlreadyInviteduserErrorPopup");
		}*/
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
						TouchAction t1 = new TouchAction(testCase.getMobileDriver());
						t1.longPress(ele).perform();
						if (MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Remove Users']",
								testCase)) {
							MobileUtils.clickOnElement(testCase, "XPATH", "//android.widget.TextView[@text='Remove Users']");
						}
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]",
					testCase)) {
				invitedUsersList = MobileUtils.getMobElements(testCase, "XPATH",
						"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle')]");
				for (WebElement ele : invitedUsersList) {
					if (ele.getText().equalsIgnoreCase(invitedUsersEmailAddress)) {
						flag = true;
						if (MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle') and @value='"
										+ invitedUsersEmailAddress
										+ "']/following-sibling::XCUIElementTypeButton[starts-with(@name,'invited_user_0_') and contains(@name,'rightButton')]",
								testCase)) {
							Keyword.ReportStep_Pass(testCase, "Delete icon for the invited users email address: "
									+ invitedUsersEmailAddress + " is displayed. Clicking on it.");
							MobileUtils.clickOnElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[starts-with(@name,'invited_user_0_') and contains(@name,'_subTitle') and @value='"
											+ invitedUsersEmailAddress
											+ "']/following-sibling::XCUIElementTypeButton[starts-with(@name,'invited_user_0_') and contains(@name,'rightButton')]");
						}
					}
				}
			}
		}
		return flag;
	}
	
	public boolean isConfirmAccessRemovalPopupTitleVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmAccessRemovalPopupTitle");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text='Confirm Access Removal']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Confirm Access Removal", testCase);
		}
	}
	
	public boolean isConfirmAccessRemovalPopupMsgVisible() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmAccessRemovalPopupMsg");
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
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInConfirmAccessRemovalPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "button2", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Cancel", testCase);
		}
	}
	
	public boolean clickOnCancelButtonInConfirmAccessRemovalPopup() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInConfirmAccessRemovalPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInConfirmAccessRemovalPopup");
		}*/
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
		// return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveButtonInConfirmAccessRemovalPopup");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("ID", "button1", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", "Remove", testCase);
		}
	}
	
	public boolean clickOnRemoveButtonInConfirmAccessRemovalPopup() {
		boolean flag = true;
		/*if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveButtonInConfirmAccessRemovalPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "RemoveButtonInConfirmAccessRemovalPopup");
		}*/
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "button1", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "ID", "button1");
		} else {
			if (MobileUtils.isMobElementExists("NAME", "Remove", testCase))
				flag &= MobileUtils.clickOnElement(testCase, "NAME", "Remove");
		}
		return flag;
	}
}