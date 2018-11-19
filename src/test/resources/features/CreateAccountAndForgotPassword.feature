@Platform
Feature: As an user, I want to Create an account, forget password and Login scenaios 

@GenralCreateaccountscreen
  Scenario: To verify the Create Account screen  
    Given app is launched 
     When the user taps on Create Account  
     Then Create Account screen should be displayed 

     #requirement : Copuntry should be US 
     @GernalcreateaccountfieldvalicationUSLocation          @Automatable
     Scenario: To veirfy create account fields text
     Given user launches the app
     When user selects "Create account" option
     Then user should be dispalyed header with "Create account" text 
     And user should be displayed with below "Fields" option:
     |Fieldvalication |
     |First Name|
     |Last Name|
     |Email |
     |Password|
     |Verify Password |
     |Password must have: |
     |Country selection |
     |By Tapping create below.|
     |Privacy Statement |
     |EULA|
     |Create button|

     #requirement : Copuntry should be UK 
     @GernalcreateaccountfieldvalicationEMEALocation          @Automatable
     Scenario: To veirfy create account fields text
     Given user launches the app
     When user selects "Create account" option
     Then user should be dispalyed header with "Create account" text 
     And user should be displayed with below "Fields" option:
     |Fieldvalication |
     |First Name|
     |Last Name|
     |Email |
     |Password|
     |Verify Password |
     |Password must have: |
     |Country selection |
     |Marketing communications sign-up |
     |By Tapping create below.|
     |Privacy Statement |
     |EULA|
     |Create button|

     @Gernalcreateaccountcancelfunctionalitywithoutenteringtext          @Automatable
     Scenario:To verify cancel functionlity with out fieling the fields
     Given user launches the app
     When user selects "Create account" option
     Then user selects the "Cancel" option
     And user should navigates to "Login" screen

     @genralcreateaccountcancelfunctionalitywithfielingtext          @Automatable
     Scenario : To veirfy cancel functionlity with fieling the fields
      Given user launches the app
     When user selects "Create account" option
     Then user enters the "First name" and "Last name"
     When user selects the "Cancel" option
     Then user should be displayed with "confirmation" pop up
     When user dismiss the popup
     Then user should be displayed with "Create account" screen
     When user selects the "Cancel" option
     Then user should be displayed with "confirmation" pop up
     When user accepts the popup
     Then user should navigates to "Login" screen

     @Gernalcreateaccountwithvaliddetails          @NotAutomatable
     Scenario Outline: As a user i wanted to create account
      Given user launches the app
     When user selects "Create account" option
     Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user navigates "Activate Account" screen
     And user should be displayed with "Details" options:
     |Details|
     |Almost Done |
     |New email |
     |Go To Mail |
     |Resend Email|
     When user navigates to "Email" and selects activation link
     Then user should be navigates to "Add new device" screen 
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | New email | Password1 | Password1 |
      | sami       | krishna   | New email | Password1 | Password1 |
      | vijay      | Govda     | New email | Password1 | Password1 |
      | anju       | sweets    | New email | Password1 | Password1 |

      @Gernalcreateaccountwithvaliddetailsgotoemnail          @NotAutomatable
     Scenario Outline: As a user i wanted to create account with go to email
      Given user launches the app
     When user selects "Create account" option
     Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user navigates "Activate Account" screen
     And user should be displayed with "Details" options:
     |Details|
     |Almost Done |
     |New email |
     |Go To Mail |
     |Resend Email|
     When user selects "Go To Mail" option
     Then user navigates to "email inbox"
     When user navigates to "Email" and selects activation link
     Then user should be navigates to "Add new device" screen 
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | New email | Password1 | Password1 |
      | sami       | krishna   | New email | Password1 | Password1 |
      | vijay      | Govda     | New email | Password1 | Password1 |
      | anju       | sweets    | New email | Password1 | Password1 |

@Gernalcreateaccountwithvaliddetailsresendlink          @NotAutomatable
     Scenario Outline: As a user i wanted to create account
      Given user launches the app
     When user selects "Create account" option
     Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user navigates "Activate Account" screen
     And user should be displayed with "Details" options:
     |Details|
     |Almost Done |
     |New email |
     |Go To Mail |
     |Resend Email|
     Then user selects "Resend Email" option
    And user should dispalyed with "Activation email sent" pop up
    When user accepts "Activaction email sent" pop up
    Then user should navigates to "Activate account" screen
    When user navigates to email inbox
    Then user should be dispalyed two activation mail
     When user navigates to "Email" and selects activation link
     Then user should be navigates to "Add new device" screen  
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | New email | Password1 | Password1 |
      | sami       | krishna   | New email | Password1 | Password1 |
      | vijay      | Govda     | New email | Password1 | Password1 |
      | anju       | sweets    | New email | Password1 | Password1 |

      @Gernalcreateaccountwithvaliddetailsbackoption          @Automatable
     Scenario Outline: As a user i wanted to verify back option in activatin account screen
      Given user launches the app
     When user selects "Create account" option
     Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user navigates "Activate Account" screen
     And user should be displayed with "Details" options:
     |Details|
     |Almost Done |
     |New email |
     |Go To Mail |
     |Resend Email|
     Then user selects "Back" option
    And user should be navigates to "Login" screen
    When user selects "Login" option
    Then user enters the <Email> and <Password>
    When user selects "Login" option
    Then user should be navigates to "Activate account" screen
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | New email | Password1 | Password1 |
      | sami       | krishna   | New email | Password1 | Password1 |
      | vijay      | Govda     | New email | Password1 | Password1 |
      | anju       | sweets    | New email | Password1 | Password1 |
 
@Gernalcreateaccountwithmaxaphanumericcharecters          @Automatable
     Scenario Outline: As a user i wanted to verify max charecters allow for first and last name
      Given user launches the app
     When user selects "Create account" option
     Then the user edits the <first name> and <last name>
      Then “First Name” and “Last Name” should allow to edit max 40 characters
      And “First Name” and “Last Name” should allow to enter special characters

    @Gernalcreateaccountwithoutfieldsthedetailserrorvalidation          @Automatable
     Scenario Outline: As a user i wanted to verify back option in activatin account screen
      Given user launches the app
     When user selects "Create account" option
     Then user selects "Create" option
     And user shoudl be dispalyed with "You must enter a first name" below First name field with red color
     And user shoudl be dispalyed with "You must enter a last name" below last name field with red color
     And user shoudl be dispalyed with "You must enter a valid email address" below email name field with red color
     And user should be displayed with below “Old password” edit box text “You must enter your password” with red color
     And user should be displayed with below “New Password” edit box text “You must enter your new password” with red color

 @Gernalcreateaccountwithalreadyregistedemailid          @Automatable
     Scenario Outline: As a user i wanted to verify create account with already registered account
      Given user launches the app
     When user selects "Create account" option
     Then user selects "Create" option
      Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user should be dispalyed with below "Email" edit box "This email address has already been registered." with red color
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | unit@grr.la | Password1 | Password1 |
  

 @GernalcreateaccountwithInvalidpassword          @Automatable
     Scenario Outline: As a user i wanted to verify create account with already registered account
      Given user launches the app
     When user selects "Create account" option
     Then user selects "Create" option
      Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user should be displayed with “Invalid Password Format” text below “New Password” edit box with red color
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | unit@grr.la |  | Password1 |

 @GernalcreateaccountwithInvalidpassword          @Automatable
     Scenario Outline: As a user i wanted to verify create account with already registered account
      Given user launches the app
     When user selects "Create account" option
     Then user selects "Create" option
      Then the user edits the <first name> and <last name>
     And user eidts the <Email> 
     And user edits the <Password> and <Verify Password>
     When user selects "Create" button
     Then user should be displayed with “Passwords don't match” text below “verify Password” edit box with red color
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | unit@grr.la | Password2 | Password1 |

 @GernalcreateaccountPrivacystatementandEULA          @Automatable
     Scenario Outline: As a user i wanted to verify create account with already registered account
      Given user launches the app
     When user selects "Create account" option
     Then user selects "Create" option
      Then user selects the "Privacy statement"
      And user shoudl be navigates to "Privacy policy & EULA" screen
      When user selects "Back" option
      Then user should be naivgates to "Create account"
      When user selects "EULA"
      Then user should be navigates to "Privacy policy & EULA" screen
     When user selects "Back" option
      Then user should be naivgates to "Create account"
    Examples: 
      | first name | last name | Email | Password | Verify Password |
      | giri       | THEJ      | unit@grr.la | Password2 | Password1 |

@CreateAccounOptIn @LYR25497          @Automatable
  Scenario: To verify that user has provided with a option for opting-in marketing emails while creating an account
     Given app is launched 
     And user navigates to “Create Account” screen
     When user selects any EMEA country
     #As of now toggle is only for EMEA countries
     Then verify marketing communication toggle button is “present”
     When user selects non EMEA country
     #As of now toggle is only for EMEA countries
     Then verify marketing communication toggle button is “ not present” 
     #Default value of toggle button should be off
     When toggle is “on” description should be “shown”
     And toggle is “off” description should be “hidden”
     Then create account with toggle “on”
     And navigate back to “Login screen”
     And create account with toggle “off”

@EditAccounOptInOptOut @LYR25497          @NotAutomatable
 Scenario: To verify that user has provided with a option for opting-in and opting out marketing emails in edit account screen 
    Given app is launched 
    And user taps on LOGIN
    When user navigates to “Edit Account” screen
    Then check for opt in link
    And verify opt in weblink 
    And opt in for marketing email
    And verify the inbox for the opt in confirmation email
    And user navigates to “Edit Account” screen
    Then check for opt out link
    And verify opt out weblink 
    And verify the inbox for the opt out confirmation email
  

   @GenralCreateaccountSelectcountry          @Automatable
  Scenario Outline: To verify Select country screen 
    Given Confirm country screen is dislayed
     When the user selects any country from auto-populated list OR enters the required country in Search field
      And tap on the required country
     Then the selected country should be displayed in Create account screen
  
   
  @GenralLoginFunctionality          @Automatable
  Scenario: Verify LOGIN in Create Account
    Given user launces the app
    When user select "Login" option 
    Then suer should be dispalyed with below "Fields" options:
    |Details|
    |Email |
    |Password|
    |Forgot Password? |
    |Disabled Login button|
    |Cacnel |

    @GenralLogincancelFunctionality          @Automatable
  Scenario: Verify LOGIN in Create Account
    Given user launces the app
    When user select "Login" option 
    Then suer should be dispalyed with below "Fields" options:
    |Details|
    |Email |
    |Password|
    |Forgot Password? |
    |Disabled Login button|
    |Cacnel |
    When user selects "Cancel" option
    Then user should navigates to "Login" screen

    @GernalLoginwithvalidcredentialwithoutlocation          @Automatable
    Scenario : As a user i wanted to verify valid login with out location
    Given user launches the app
    when user edits the <Email> and <Password>
    Then user should be displayed with "ADD NEW DEVICE" screen
    Examples:
    |Email| Password |
    |ure@grr.la | Password |

    @GernalLoginwithvalidcredentialwithlocation          @Automatable
    Scenario : As a user i wanted to verify valid login with location
    Given user launches the app
    when user edits the <Email> and <Password>
    Then user should be displayed with "Dashboard" screen
    Examples:
    |Email| Password |
    |unit@grr.la | Password |

    @GernalLoginwithinvalidemailandpassword          @Automatable
    Scenario : As a user i wanted to verify valid login with location
    Given user launches the app
    when user edits the <Email> and <Password>
    Then user should be displayed with "Unable to login. Email or password incorrect" text above email edit box with red color
    Examples:
    |Email| Password |
    |bajssm@grr.la | Password1 |

     @GernalLoginwithinvalidemail          @Automatable
    Scenario : As a user i wanted to verify valid login with location
    Given user launches the app
    when user edits the <Email> and <Password>
    Then user should be displayed with "Your email address or password is invalid" text above email edit box with red color
    Examples:
    |Email| Password |
    |bajssm| Password1 |

  
 #Forget Password 
 
 
 @GenralForgetPasswordfieldvalidation          @Automatable
  Scenario: To verify the Forgot Password screen  
    Given user launches the app
     When user selects "Login" screen
     Then user selects "Forget password"
     And user should be dispalyed with below "Details" options:
     |Details|
     |Email text with edit box|
     |Disabled Reset button |
     |Login |

      @GenralForgetPasswordloginfunctionlity          @Automatable
  Scenario: To verify the Forgot Password screen  
    Given user launches the app
     When user selects "Login" screen
     Then user selects "Forget password"
     When user edits the <Email>
     Then user selects the "Login" option
     And user should navigates to "Login" screen with clear edit box
     Examples:
     |Email|
     | unit@grr.la | 
  
    @GenralForgetPasswordmailactivation          @NotAutomatable
  Scenario: To verify the Forgot Password screen  
    Given user launches the app
     When user selects "Login" screen
     Then user selects "Forget password"
     When user edits the <Email>
     Then user selects the "Reset" option
     #iOS
     And user should navigates to "Great" screen
     And user should be dispalyed with below options:
     |Options|
     |Description |
     |Email |
     |Resend option|
     |Login |
      When user select resend option
      Then user should receive forget password mail
      #Android
      And user received toast message "Password link sent"
      Examples:
     |Email|
     | unit@grr.la | 

   
