package io.openmessaging.demo;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.iq80.snappy.Snappy;

/**
 * 
 * 
 * 
 * @author jianhonghu
 *
 */
public class SnappyWriter implements IWriter {

	private RandomAccessFile file;

	public static final int DEFAULT_SIZE = IConstants.CMP_MS;

	private byte[] bytes = new byte[DEFAULT_SIZE + IConstants.MSG_ML];

	private int p;

	private byte[] cmp = new byte[DEFAULT_SIZE + IConstants.MSG_ML];


	public SnappyWriter(String dir) throws IOException {
		file = new RandomAccessFile(dir, "rw");
	}


	private void put(byte a) {
		bytes[p++] = (byte) (a & 0xff);
	}

	private void put(byte[] bs) {
		int a = bs.length;
		if(a < Byte.MAX_VALUE){
			bytes[p++] = (byte) (a & 0xff);
		} else {
			bytes[p++] = (byte) ((a >> 8 & 0xff) | 0x80);
			bytes[p++] = (byte) (a & 0xff);
		}
		System.arraycopy(bs, 0, bytes, p, bs.length);
		p += bs.length;
	}
	
	public void write(DefaultBytesMessage message) throws IOException {
		DefaultKeyValue h = (DefaultKeyValue)message.headers();
		DefaultKeyValue pro = (DefaultKeyValue)message.properties();
		// 减少一个字节存储头部和属性的长度
		byte tsize = (byte) ((((byte)h.num) << 4 & 0xf0) | (pro.num & 0x0f));
		put(tsize);
		for (int i = 0; i < h.num; i++) {
			put(h.keys[i].getBytes());
			put(h.values[i]);
		}
		for (int i = 0; i < pro.num; i++) {
			put(pro.keys[i].getBytes());
			put(pro.values[i]);
		}
		byte[] body = ((DefaultBytesMessage) message).getBody();
		put(body);
		
		if (p >= DEFAULT_SIZE) {
			int clen = Snappy.compress(bytes, 0,p, cmp, 0);
			file.writeShort(clen);
			file.write(cmp, 0, clen);
			this.p = 0;
		}
	}

	public void close() throws IOException {
		if (this.p > 0) {
			int clen = Snappy.compress(bytes, 0,p, cmp, 0);
			file.writeShort(clen);
			file.write(cmp, 0, clen);
		}
		file.writeShort(0);
		file.close();
	}
}
