package io.openmessaging.demo;

import io.openmessaging.BytesMessage;
import io.openmessaging.KeyValue;
import io.openmessaging.Message;

/**
 * 
 * 
 * @author jianhonghu
 *
 */
public class DefaultBytesMessage implements BytesMessage {

	private DefaultKeyValue headers = new DefaultKeyValue(2);
	private DefaultKeyValue properties = new DefaultKeyValue(4);

	private byte[] body;

	public DefaultBytesMessage(byte[] body) {
		this.body = body;
	}

	@Override
	public byte[] getBody() {
		return body;
	}

	@Override
	public BytesMessage setBody(byte[] body) {
		this.body = body;
		return this;
	}

	@Override
	public KeyValue headers() {
		return headers;
	}

	@Override
	public KeyValue properties() {
		return properties;
	}

	@Override
	public Message putHeaders(String key, int value) {
		headers.put(key, value);
		return this;
	}

	public Message putHeaders(String key, byte[] value) {
		headers.put(key, value);
		return this;
	}

	@Override
	public Message putHeaders(String key, long value) {
		headers.put(key, value);
		return this;
	}

	@Override
	public Message putHeaders(String key, double value) {
		headers.put(key, value);
		return this;
	}

	@Override
	public Message putHeaders(String key, String value) {
		headers.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, int value) {
		properties.put(key, value);
		return this;
	}

	public Message putProperties(String key, byte[] value) {
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, long value) {
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, double value) {
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, String value) {
		properties.put(key, value);
		return this;
	}
	
	public void clear(){
		this.headers.clear();
		this.properties.clear();
	}
}
