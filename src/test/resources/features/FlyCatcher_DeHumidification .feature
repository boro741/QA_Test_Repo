@DeHumidicationsetup
Feature: As an user 
I want to set my humidity target
So that i can set my room humidity as per my prefrence 

@SetTargetDeHum
  Scenario Outline: To change stat humidity as per my prefrence
  As an user 
  I want to set my humidity 
  So that i can set my room De humidifies as per my prefrence
  
    Given user launches and logs in to the Lyric application
      And user selects "Flycatcher device" from the dashboard
      And user navigates to "DeHumidication" screen from the "Primary card" screen
     When User Sets the DeHumidication <Target Value>
     Then Verify DeHumidication is "displayed" and set to <Target Value>
    Examples: 
      | Target Value | 
      | 40           | 
      | 50           | 
      | 60           | 
      | 80           | 
      | 100           | 
  
  @HumidityBandwidthlimit
  Scenario Outline: To verify the humidity bandiwth limit
  As an user 
  I want to verify my Dehum min and max limits
  So that i check my boundry values
  
    Given user launches and logs in to the Lyric application
      And user selects "Flycatcher device" from the dashboard
      And user navigates to "DeHumidication" screen from the "Primary card" screen
     When User Sets the DeHumidication <Target Value>
     Then Verify DeHumidication cannot set to <Target Value>
  
    Examples: 
      | Target Value | 
      | 35            | 
      | 105           | 
  
  @DeHumidicationIncreamentalof5%
  Scenario Outline: To veify if DeHumidication is increamental of 5%
  As an user 
  I want set DeHumidication up/Down stepper increamental of 5%
  so on tapping Incrementing or decrementing stepper increases or reduces 5%
  
    Given user DeHumidication is set "40%"
      And user launches and logs in to the Lyric application
      And user selects "FlyCatcher device" from the dashboard
      And user navigates to "DeHumidication" screen from the "Primary card" screen
     When user <Control Buttons> the humidity <Target Value>
     Then Verify humidity is "displayed" and set to <Target Value>
    Examples: 
      | Control Buttons | Mintues | 
      | Increment       | 45      | 
      | Increment       | 50      | 
      | Increment       | 55      | 
      | Increment       | 60      | 
      | Decrement       | 55      | 
      | Decrement       | 50      | 
      | Decrement       | 45      | 
  
  @DeHumidicationEnable/Disable
  Scenario Outline: To verify DeHumidication can be enabled or disabled
  As an user 
  I want to know DeHumidication can be enabled or disabled
  so that it can turn on/off humidity respectively 
  
    Given user DeHumidication is <Pre mode>
      And user launches and logs in to the Lyric application
      And user selects "FlyCatcher device" from the dashboard
      And user navigates to "DeHumidication" screen from the "Primary card" screen
     When user <Post mode> DeHumidication 
     Then Verify if DeHumidication is <Post mode>
  
    Examples: 
      | Pre mode | Post mode | 
      | Enabled  | Disabled  | 
      | Disabled | Enabled   | 
  

  
