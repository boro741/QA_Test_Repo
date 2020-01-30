package com.resideo.test.lib.utility.eventhub.sender;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.resideo.test.lib.utility.eventhub.sender.config.EventHubSenderConfig;
import com.resideo.test.lib.utility.eventhub.sender.message.EventMessage;

public class EventSender {
	
	private final EventHubSenderConfig eventHubConfig;
	
	public EventSender (String configFilename) throws JsonProcessingException, IOException {
		eventHubConfig = new EventHubSenderConfig(configFilename);
	}
	
	public void sendEvent (String messageFilename) throws EventHubException, IOException {
		
		final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
		
		System.out.println("-----> Connecting to the EventHub!");
		final EventHubClient ehClient = EventHubClient.createFromConnectionStringSync(eventHubConfig.getConnectionString(), executorService);
		
		try {
			System.out.println("-----> Preparing Message!");
			EventData msgToBeSent = new EventMessage(messageFilename).getEventMessage();
			System.out.println("Message Body:");
			System.out.println(new String(msgToBeSent.getBytes()));
			System.out.println("Message Properties:");
			System.out.println(msgToBeSent.getProperties().toString());
			
			if(eventHubConfig.getPartitionKey() != null) {
				
				System.out.println("-----> Sending Message with Partition Key: " + eventHubConfig.getPartitionKey());
				ehClient.sendSync(msgToBeSent, eventHubConfig.getPartitionKey());
			}
			else {
				System.out.println("-----> Sending Message without any Partition Key!");
				ehClient.sendSync(msgToBeSent);
			}
				
		}
		finally
		{
			ehClient.closeSync();
			executorService.shutdown();
			System.out.println("-----> Connection to the EventHub is Closed!");
		}
		
	}

}
