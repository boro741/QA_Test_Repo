package com.honeywell.lyric.relayutils;

public class ZWaveRelayUtils {

	public static void enrollZwaveSwitch1() throws Exception {
		SerialDriver.initialize();
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		Thread.sleep(10000);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		SerialDriver.closePort();
	}

	public static void pressButtonOnSwitch1() throws Exception {
		SerialDriver.initialize();
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		SerialDriver.setrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		Thread.sleep(1000);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveSwitch1RelayPort1);
		Thread.sleep(1000);
		SerialDriver.closePort();
	}

	public static boolean isSwitch1ON() throws Exception {
		SerialDriver.initialize();
		if (SerialDriver.getstatus(ZWaveConstants.ZWaveSwitch1OutputStatusPort).equalsIgnoreCase("OFF")) {
			SerialDriver.closePort();
			return true;
		} else {
			SerialDriver.closePort();
			return false;
		}

	}

	public static void enrollZwaveDimmer1() throws Exception {
		SerialDriver.initialize();
		SerialDriver.setrelay(ZWaveConstants.ZWaveDimmer1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveDimmer1RelayPort1);
		Thread.sleep(10000);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveDimmer1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveDimmer1RelayPort1);
		SerialDriver.closePort();
	}

	public static void pressButtonOnDimmer1() throws Exception {
		SerialDriver.initialize();
		SerialDriver.setrelay(ZWaveConstants.ZWaveDimmer1RelayPort2);
		SerialDriver.setrelay(ZWaveConstants.ZWaveDimmer1RelayPort1);
		Thread.sleep(1000);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveDimmer1RelayPort2);
		SerialDriver.resetrelay(ZWaveConstants.ZWaveDimmer1RelayPort1);
		SerialDriver.closePort();
	}

	public static String getDimmerIntensityRange() throws Exception {
		String dimmerIntensityRange = "";

		try {
			int i = 0;
			SerialDriver.initialize();
			while (i < 5) {
				dimmerIntensityRange = SerialDriver
						.getDimmerIntensityPercentageRange(ZWaveConstants.ZWaveDimmer1OutputPercentagePort);
				if (!dimmerIntensityRange.equals("Invalid")) {
					break;
				}
				i++;
			}
			SerialDriver.closePort();
		} catch (Exception e) {
			SerialDriver.closePort();
			throw new Exception(e.getMessage());
		}
		return dimmerIntensityRange;
	}
}
