package com.honeywell.CHIL;

import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;

public class EventCallback implements IotHubEventCallback {

	@Override
	public void execute(IotHubStatusCode status, Object context)
    {
        Message msg = (Message) context;
        
        System.out.println("IoT Hub responded to message "+ msg.getMessageId()  + " with status " + status.name());
    }
}
