package io.openmessaging.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.zip.Inflater;

import io.openmessaging.BytesMessage;
import io.openmessaging.MessageHeader;

public class CMPReader implements IReader {
	private int cnt = 0;
	private MappedByteBuffer[] mBuffers;
	
	public CMPReader(String dir) throws IOException {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			this.complete = true;
			return;
		}
		File[] files = dirFile.listFiles();
		if (files.length <= 0) {
			this.complete = true;
			return;
		}
		mBuffers = new MappedByteBuffer[files.length];
		for (int i = 0; i < files.length; i++) {
			@SuppressWarnings("resource")
			FileChannel channel = new FileInputStream(files[i])
					.getChannel();
			mBuffers[i] = channel.map(MapMode.READ_ONLY, 0, channel.size());
		}
		mBuffer = mBuffers[cnt++];
	}
    
    public BytesMessage read() throws Exception {
		if (complete) {
			return null;
		}
		if(p < limit){
			return fromBuffer();
		}
		int len = 0;
		while ((len = mBuffer.getShort()) == 0) {
			if (cnt == mBuffers.length) {
				this.complete = true;
				return null;
			}
			mBuffer = mBuffers[cnt++];
		}
		mBuffer.get(cmp, 0, len);
		inflater.reset();
		inflater.setInput(cmp, 0, len);
		limit = inflater.inflate(bytes);
		p = 0;
		return fromBuffer();
	}
	
	private byte[] cmp = new byte[IConstants.CMP_MS + IConstants.MSG_ML];
	private byte[] bytes = new byte[IConstants.CMP_MS + IConstants.MSG_ML];
	private int p;
	private int limit;
	
	private ByteBuffer mBuffer;
	private boolean complete = false;
	private Inflater inflater = new Inflater();
	
	private QingBytesMessage msg = new QingBytesMessage();
	private QingKeyValue hds = new QingKeyValue(2);
	private QingKeyValue pros = new QingKeyValue(4);
	
	private static final String TOPIC = MessageHeader.TOPIC;
	private static final String QUEUE = MessageHeader.QUEUE;
	
	private BytesMessage fromBuffer() {
		int keySize = getByte();
		hds.clear();
		for (int i = 0; i < keySize; i++) {
			String key = getString();
			switch (key) {
			case "T":
				hds.put(TOPIC, getBytes());
				break;
			case "Q":
				hds.put(QUEUE, getBytes());
				break;
			default:
				hds.put(key, getBytes());
				break;
			}
		}
		msg.setHeaders(hds);
		int pSize = getByte();
		if(pSize > 0){
			pros.clear();
			for (int i = 0; i < pSize; i++) {
				pros.put(getString(), getBytes());
			}
			msg.setProperties(pros);
		} else {
			msg.setProperties(null);
		}
		msg.setBody(getBytes());
		return msg;
	}
	
	
	public int getShort() {
		return (((bytes[p++] & 0xff) << 8) | ((bytes[p++] & 0xff)));
	}

	public int getByte() {
		return (bytes[p++] & 0xff);
	}

	public String getString() {
		int a = (bytes[p++] & 0xff);
		String s = new String(bytes, p, a);
		p += a;
		return s;
	}

	public byte[] getBytes() {
		int a = (((bytes[p++] & 0xff) << 8) | ((bytes[p++] & 0xff)));
		byte[] b = new byte[a];
		System.arraycopy(bytes, p, b, 0, a);
		p += a;
		return b;
	}
}
