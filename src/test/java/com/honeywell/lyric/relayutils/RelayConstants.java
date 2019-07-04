package com.honeywell.lyric.relayutils;

public class RelayConstants {
	public static final String RelayBoardCOMPort = "COM29";
	//public static final String RelayBoardTTYPort = "/dev/tty.usbmodem1441";
	public static final String RelayBoardTTYPort = "/dev/tty.usbmodem14101";

	public static String RSI_Contact_Sensor_2_SerialNO= "3508142677";//Door
	public static String RSI_Contact_Sesor_2_Enroll_ON = "trigger-3";  // Door
	public static String RSI_Contact_Sesor_2_Tamper_ON = "setpin-4:1";  // Door
	public static String RSI_Contact_Sesor_2_Tamper_OFF = "setpin-4:0"; // Door
	public static String RSI_Contact_Sesor_2_Trip_ON = "setpin-5:1";    // Door
	public static String RSI_Contact_Sesor_2_Trip_OFF = "setpin-5:0";   // Door


	public static String RSI_Motion_Sensor_1_SerialNO= "289407367";//motion
	public static String RSI_Motion_Sensor_1_Enroll_ON = "trigger-6:1";  // Motion
	public static String RSI_Motion_Sensor_1_Tamper_ON = "setpin-7:1";  // Motion
	public static String RSI_Motion_Sensor_1_Tamper_OFF = "setpin-7:0";  // Motion
	public static String RSI_Motion_Sensor_1_MotionSensed = "setpin-15:1";  // Motion
	public static String RSI_Motion_Sensor_1_MotionSenseClose = "setpin-15:0";  // Motion


	public static String RSI_Keyfob_1_SerialNO= "323093303";//window
	public static String RSI_Keyfob_1_Enroll_ON = "setpin-10:1,setpin-11:1";  // KEYFOB
	public static String RSI_Keyfob_1_Enroll_OFF = "setpin-10:0,11:0";  // KEYFOB
	public static String RSI_KeyfobPress_1_AWAY = "trigger-10";  // KEYFOB
	public static String RSI_KeyfobPress_1_HOME = "trigger-11";  // KEYFOB
	public static String RSI_KeyfobPress_1_NIGHT = "trigger-12";  // KEYFOB
	public static String RSI_KeyfobPress_1_OFF = "trigger-13";  // KEYFOB

	public static String RSI_ISMV_Motion_Sensor_1_SerialNO= "2702901852";//ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Enroll_ON = "setpin-6:1";  //ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Tamper_ON = "setpin-15:1";  //ISMV
	public static String RSI_ISMV_Motion_Sensor_1_Tamper_OFF = "setpin-15:0";  //ISMV

	public static String RSI_OSMV_Motion_Sensor_1_SerialNO= "2702901749";//OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Enroll_ON = "6:1";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Enroll_OFF = "6:0";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Tamper_ON = "14:1";  //OSMV
	public static String RSI_OSMV_Motion_Sensor_1_Tamper_OFF = "14:0";  //OSMV


	public static String RSI_AllSensor_Trip_OFF = "Setall-0";
	public static String RSI_AllSensor_Trip_ON = "Setall-1";

	public static String RFS_COMBO_SENSOR_1_SerialNO= "10001268816892126217";//Combo
	public static String RFS_COMBO_Sensor_1_TRIGGER_SMOKE = "trigger-16";  //Smoke
	public static String RFS_COMBO_Sensor_1_TRIGGER_CO = "trigger-17";  //CO

}
