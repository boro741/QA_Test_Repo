@Sensors
Feature: As an user 
I want to control my sensor
So that i can move the sensor as per my prefernce 

@SensorDeleteSensor
  Scenario: To inform user on Deleting sensor from Stat
  As an user 
  I want to be informed on Delete options 
  So that pop up message is displayed to delete on stat
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
      And user navigates to "Device and Sensors" screen from the "Dashboard" screen
     When user selects "Delete" optionss
     Then user is displayed with "Pop Up" 
  
  @SensorMoveSensor
  Scenario: To move an sensos from one room to another 
  As an User 
  I want to move an sensor from Exiting to another 
  so that i can set my priroty in specific room 
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
      And user navigates to "Device and Sensors" screen from the "Dashboard" screen
     When user selects "Move" options
      And user moves "Exisintg Room" to "New Room"
     Then Verify if sensor is moves to "New Room"
      And user navigates to "Priority" screen from the "Device and Sensors" screen
      And Veirfy if "New room" is displayed in "Priority" screen
  
  @SensorMoveToSameRoom
  Scenario: To Move an Sesnor to Same room so that its displaye an average to stats temp
  As an user
  I want to move an Sensor to existing room 
  so that in priority screen, App displayed average of the stats
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
      And user navigates to "Device and Sensors" screen from the "Dashboard" screen
     When user selects "Move" options
      And user moves sensor to Exisiting Rooms 
     Then Veirfy if "Pop up" is displayed 
     When user navigates to "Priority" screen from the "Device and Sensors" screen
     Then veirfy if average temp of 2 stats is displayed when moved to same room
  
  @SensorIdentifySensor
  Scenario: To identify an sensor which is regustered to an stat 
  As an user 
  I want to identify an sensor which is registered 
  so that i can know which sensor is configured in the room
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
      And user navigates to "Device and Sensors" screen from the "Dashboard" screen
     When user selects "Identify" options
     Then user is displayed "Identify sensor" screen  
      And verify if configured sensor is blinking 
  
  @Sensordetails
  Scenario: To display sensor Firmware,Battery Life,Signal Strength and Model
  As an user 
  I want to view sensor details to be displayed 
  so that i know sensor Firmware,Battery Life,Signal Strength and Model details
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
     When user navigates to "Device and Sensors" screen from the "Dashboard" screen 
     Then User is displayed with "Sensor detail" Screen 
  
  @SensorComfortSettings
  Scenario: To Enable Motion detection for Sensors
  As an user 
  I want to enable sensor motion detection
  so that i can use Activie room in priority 
  
    Given user has get the Sensor list from the stat
      And user launches and logs in to the Lyric application
     When user navigates to "Device and Sensors" screen from the "Dashboard" screen 
      And user selects an "Sensor"
      And user "Enables" motion detection for sensor
     Then verify user can activate sensor in Priority Active rooms
