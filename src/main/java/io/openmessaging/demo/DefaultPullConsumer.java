package io.openmessaging.demo;

import java.io.File;
import java.util.Collection;
import java.util.TreeSet;

import io.openmessaging.KeyValue;
import io.openmessaging.Message;
import io.openmessaging.PullConsumer;

public class DefaultPullConsumer implements PullConsumer {
	private KeyValue properties;

	public DefaultPullConsumer(KeyValue properties) {
		this.properties = properties;
		this.storePath = properties.getString("STORE_PATH");
	}
	
	@Override
	public KeyValue properties() {
		return properties;
	}

	@Override
	public Message poll(KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public void ack(String messageId) {
		throw new UnsupportedOperationException("Unsupported");
	}

	@Override
	public void ack(String messageId, KeyValue properties) {
		throw new UnsupportedOperationException("Unsupported");
	}
	
	private String storePath;
	private String queue;
	private IReader[] readers;
	private int cur = 0;
	
	private int k = 0;

	private Message msg;

	public final Message poll() {
		try {
			while (cur < k) {
				if ((msg = readers[cur].read()) != null) {
					return msg;
				} else {
					cur++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final void attachQueue(final String queueName,final Collection<String> topics) {
		try {
			queue = queueName;
			TreeSet<String> bucketSets = new TreeSet<>();
			for(String bucket: topics){
				String dir = this.storePath + File.separator + bucket;
				File file = new File(dir);
				if(file.exists()){
					bucketSets.add(dir);
				}
			}
//			readers = new CMPReader[bucketSets.size() + 1];
//			for(String dir : bucketSets){
//				readers[k++] = new CMPReader(dir);
//			}
			
//			readers = new SnappyReader[bucketSets.size() + 1];
//			for(String dir : bucketSets){
//				readers[k++] = new SnappyReader(dir);
//			}
//			
//			String dir = this.storePath + File.separator + queue;
//			File file = new File(dir);
//			if(file.exists()){
//				readers[k++] = new SnappyReader(dir);
//			}
			
			readers = new SnappyFileReader[bucketSets.size() + 1];
			for(String dir : bucketSets){
				readers[k++] = new SnappyFileReader(dir);
			}
			
			String dir = this.storePath + File.separator + queue;
			File file = new File(dir);
			if(file.exists()){
				readers[k++] = new SnappyFileReader(dir);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
