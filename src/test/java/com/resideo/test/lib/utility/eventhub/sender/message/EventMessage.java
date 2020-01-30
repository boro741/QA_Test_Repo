package com.resideo.test.lib.utility.eventhub.sender.message;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.eventhubs.EventData;

public class EventMessage {
	private JsonNode body;
	private JsonNode properties;
	
	public EventMessage (String messageFilename) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(new File(messageFilename));
		body = root.get("Body");
		properties = root.get ("Properties");
	}

	public JsonNode getBody() {
		return body;
	}

	public JsonNode getProperties() {
		return properties;
	}
	
	public EventData getEventMessage () throws IOException {
		if (body == null)
			return null;
		
		byte[] payloadBytes = this.body.toString().getBytes();
		EventData msg = EventData.create(payloadBytes);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> propertiesMap = mapper.convertValue(properties, new TypeReference<Map<String, String>>() {});
		msg.getProperties().putAll(propertiesMap);
		
		return msg;
	}
}
