package com.honeywell.lyric.relayutils;

public class RelayUtils {

	public static void RSIContactSensorOpen_Window() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Open);
		SerialDriverArduino.closePort();

	}
	public static void RSIContactSensorClosed_Window() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Close);
		SerialDriverArduino.closePort();
	}
	public static void RSIWindowContactSensorTamperON() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Tamper_ON);
		SerialDriverArduino.closePort();
	}
	public static void RSIWindowContactSensorTamperCleared() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Tamper_OFF);
		SerialDriverArduino.closePort();
	}

	public static void RSIContactSensorEnroll_Window() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Enroll_ON);
		Thread.sleep(3000);
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_1_Enroll_OFF);
		SerialDriverArduino.closePort();
	}
	public static void RSIContactSensorOpen_Door() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Trip_ON);
		SerialDriverArduino.closePort();

	}
	
	public static void RSIContactSensorEnroll_Door() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Enroll_ON);
		Thread.sleep(3000);
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Enroll_OFF);
		SerialDriverArduino.closePort();
	}
	
	
	public static void RSIContactSensorClosed_Door() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Trip_OFF);
		SerialDriverArduino.closePort();
	}
	public static void RSIDoorContactSensorTampered() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Tamper_ON);
		SerialDriverArduino.closePort();
	}
	public static void RSIDoorContactSensorTamperCleared() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Contact_Sesor_2_Tamper_OFF);
		SerialDriverArduino.closePort();
	}
	public static void RSIAllSensorOff() throws Exception{
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_AllSensor_Trip_OFF);
		SerialDriverArduino.closePort();
	}
	
	public static void RSIMotionSensorTampered() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Motion_Sensor_1_Tamper_ON);
		SerialDriverArduino.closePort();
	}
	public static void RSIMotionSensorTamperCleared() throws Exception {
		SerialDriverArduino.initialize();
		SerialDriverArduino.setrelay(RelayConstants.RSI_Motion_Sensor_1_Tamper_OFF);
		SerialDriverArduino.closePort();
	}

}
