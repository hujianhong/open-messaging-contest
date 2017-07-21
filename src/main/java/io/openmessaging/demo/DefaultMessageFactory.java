package io.openmessaging.demo;

import io.openmessaging.BytesMessage;
import io.openmessaging.MessageFactory;
import io.openmessaging.MessageHeader;

public class DefaultMessageFactory implements MessageFactory {

	public BytesMessage createBytesMessageToTopic(String topic, byte[] body) {
		DefaultBytesMessage message = new DefaultBytesMessage(body);
		message.putHeaders(MessageHeader.TOPIC, topic);
		return message;
	}

	public BytesMessage createBytesMessageToQueue(String queue, byte[] body) {
		DefaultBytesMessage message = new DefaultBytesMessage(body);
		message.putHeaders(MessageHeader.QUEUE, queue);
		return message;
	}
}
