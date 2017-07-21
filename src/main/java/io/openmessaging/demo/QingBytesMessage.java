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
public class QingBytesMessage implements BytesMessage {

	private QingKeyValue headers;
	private QingKeyValue properties;
	private byte[] body;

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
		if (properties == null)
			properties = new QingKeyValue();
		properties.put(key, value);
		return this;
	}

	public Message putProperties(String key, byte[] value) {
		if (properties == null)
			properties = new QingKeyValue();
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, long value) {
		if (properties == null)
			properties = new QingKeyValue();
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, double value) {
		if (properties == null)
			properties = new QingKeyValue();
		properties.put(key, value);
		return this;
	}

	@Override
	public Message putProperties(String key, String value) {
		if (properties == null)
			properties = new QingKeyValue();
		properties.put(key, value);
		return this;
	}

	public void setHeaders(QingKeyValue headers) {
		this.headers = headers;
	}

	public void setProperties(QingKeyValue properties) {
		this.properties = properties;
	}
}
