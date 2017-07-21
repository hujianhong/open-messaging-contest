package io.openmessaging.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import io.openmessaging.BatchToPartition;
import io.openmessaging.BytesMessage;
import io.openmessaging.KeyValue;
import io.openmessaging.Message;
import io.openmessaging.Producer;
import io.openmessaging.Promise;

public class DefaultProducer implements Producer {
	private KeyValue properties;

	public DefaultProducer(KeyValue properties) {
		this.key = NUM.incrementAndGet() + "";
		this.properties = properties;
		this.storePath = properties.getString("STORE_PATH");
	}

	private static AtomicInteger NUM = new AtomicInteger(-1);

	private String key;

	private String storePath;
	
	private DefaultBytesMessage message = new DefaultBytesMessage(null);

	public BytesMessage createBytesMessageToTopic(String topic, byte[] body) {
		message.clear();
		message.setBody(body);
		message.putHeaders(IConstants.T, topic);
		return message;
	}

	public BytesMessage createBytesMessageToQueue(String queue, byte[] body) {
		message.clear();
		message.setBody(body);
		message.putHeaders(IConstants.Q, queue);
		return message;
	}

	/*private KV writers = new KV();
	
	class KV {
		String[] keys;
		IWriter[] writers;
		
		int k;
		
		public KV() {
			this.keys = new String[102];
			this.writers = new SnappyWriter[102];
			this.k = 0;
		}
		
		public IWriter get(String bucket) throws IOException {
			int i = 0;
			for(;i < k;i ++){
				if(keys[i].equals(bucket)){
					return writers[i];
				}
			}
			IWriter writer = null;
			if(i == k){
				String path = storePath + File.separator + bucket;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				writer = new SnappyWriter(path + File.separator + key);
				keys[k] = bucket;
				writers[k ++] = writer;
			}
			return writer;
		}
	}*/
	
	private Map<String, IWriter> writers = new HashMap<>();

	@Override
	public void send(Message message) {
		try {
			String bucket = message.headers().getString(IConstants.T);
			if (bucket == null) {
				bucket = message.headers().getString(IConstants.Q);
			}
			IWriter writer = writers.get(bucket);
			if(writer == null){
				String path = storePath + File.separator + bucket;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				writer = new SnappyWriter(path + File.separator + key);
				writers.put(bucket, writer);
			}
			writer.write((DefaultBytesMessage)message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void flush() {
		try {
			for (Entry<String, IWriter> e : writers.entrySet()) {
				e.getValue().close();
			}
//			for(int i = 0;i < writers.k;i ++){
//				writers.writers[i].close();
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void start() {

	}

	@Override
	public void shutdown() {

	}

	@Override
	public KeyValue properties() {
		return properties;
	}
	
	@Override
	public void send(Message message, KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public Promise<Void> sendAsync(Message message) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public Promise<Void> sendAsync(Message message, KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public void sendOneway(Message message) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public void sendOneway(Message message, KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public BatchToPartition createBatchToPartition(String partitionName) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public BatchToPartition createBatchToPartition(String partitionName, KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}
}
