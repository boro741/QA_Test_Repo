package com.honeywell.lyric.relayutils;

public class RelayConstants {
	public static final String RelayBoardCOMPort = "COM29";
	public static final String RelayBoardTTYPort = "/dev/tty.usbmodem1441";
	
	//public static String RSI_Contact_Sensor_1_SerialNO= "3508142677";//window
	public static String RSI_Contact_Sensor_1_SerialNO= "289407302";//window
	public static String RSI_Contact_Sesor_1_Enroll_ON = "0:1";  // Window
	public static String RSI_Contact_Sesor_1_Enroll_OFF = "0:0";	// Window
	public static String RSI_Contact_Sesor_1_Tamper_ON = "1:1";  // Window
	public static String RSI_Contact_Sesor_1_Tamper_OFF = "1:0";	// Window
	public static String RSI_Contact_Sesor_1_Open = "2:1";    // Window
	public static String RSI_Contact_Sesor_1_Close = "2:0";   // Window
	
	//public static String RSI_Contact_Sensor_2_SerialNO= "289407736";//Door
	public static String RSI_Contact_Sensor_2_SerialNO= "3508142677";//Door
	public static String RSI_Contact_Sesor_2_Enroll_ON = "3:1";  // Door
	public static String RSI_Contact_Sesor_2_Enroll_OFF = "3:0"; // Door
	public static String RSI_Contact_Sesor_2_Tamper_ON = "4:1";  // Door
	public static String RSI_Contact_Sesor_2_Tamper_OFF = "4:0"; // Door
	public static String RSI_Contact_Sesor_2_Trip_ON = "5:1";    // Door
	public static String RSI_Contact_Sesor_2_Trip_OFF = "5:0";   // Door
	
	//public static String RSI_Motion_Sensor_1_SerialNO= "289408348";//motion
	public static String RSI_Motion_Sensor_1_SerialNO= "289408348";//motion
	public static String RSI_Motion_Sensor_1_Enroll_ON = "6:1";  // Motion
	public static String RSI_Motion_Sensor_1_Enroll_OFF = "6:0";  // Motion
	public static String RSI_Motion_Sensor_1_Tamper_ON = "7:1";  // Motion
	public static String RSI_Motion_Sensor_1_Tamper_OFF = "7:0";  // Motion
	
	public static String RSI_Keyfob_1_SerialNO= "323093303";//window
	//public static String RSI_Keyfob_1_SerialNO= "323093301";//window
	public static String RSI_Keyfob_1_Enroll_ON = "8:1,9:1";  // KEYFOB
	public static String RSI_Keyfob_1_Enroll_OFF = "8:0,9:0";  // KEYFOB
	public static String RSI_KeyfobPress_1_AWAY = "10:1";  // KEYFOB
	public static String RSI_KeyfobRelease_1_AWAY = "10:0";  // KEYFOB
	public static String RSI_KeyfobPress_1_HOME = "11:1";  // KEYFOB
	public static String RSI_KeyfobRelease_1_HOME = "11:0";  // KEYFOB
	public static String RSI_KeyfobPress_1_NIGHT = "12:1";  // KEYFOB
	public static String RSI_KeyfobRelease_1_NIGHT = "12:0";  // KEYFOB
	public static String RSI_KeyfobPress_1_OFF = "13:1";  // KEYFOB
	public static String RSI_KeyfobRelease_1_OFF = "13:0";  // KEYFOB
	
	//public static String RSI_ISMV_Motion_Sensor_1_SerialNO= "289997226";//ISMV
	public static String RSI_ISMV_Motion_Sensor_1_SerialNO= "2702901852";//ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Enroll_ON = "6:1";  //ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Enroll_OFF = "6:0";  //ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Tamper_ON = "7:1";  //ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Tamper_OFF = "7:0";  //ISMV
	
	public static String RSI_OSMV_Motion_Sensor_1_SerialNO= "2702901749";//OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Enroll_ON = "6:1";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Enroll_OFF = "6:0";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Tamper_ON = "7:1";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Tamper_OFF = "7:0";  //OSMV
	
	
	public static String RSI_AllSensor_Trip_OFF = "0:0;1:0;2:0;3:0;4:0;5:0;6:0;7:0,8:0;9:0;10:0;11:0";
	public static String RSI_AllSensor_Trip_ON = "43";
}
