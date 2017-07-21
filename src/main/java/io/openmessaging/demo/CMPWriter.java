package io.openmessaging.demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.Deflater;

/**
 * 
 * 
 * 
 * @author jianhonghu
 *
 */
public class CMPWriter implements IWriter {

	private RandomAccessFile file;

	public static final int DEFAULT_SIZE = IConstants.CMP_MS;

	private byte[] bytes = new byte[DEFAULT_SIZE + IConstants.MSG_ML];

	private int p;

	private byte[] cmp = new byte[DEFAULT_SIZE + IConstants.MSG_ML];
	
	private Deflater deflater = new Deflater(Deflater.BEST_SPEED);

	public CMPWriter(String dir) throws IOException {
		file = new RandomAccessFile(dir, "rw");
	}

	private void put(short a) {
		bytes[p++] = (byte) (a >> 8 & 0xff);
		bytes[p++] = (byte) (a & 0xff);
	}

	private void put(byte a) {
		bytes[p++] = (byte) (a & 0xff);
	}

	private void put(byte[] bs) {
		System.arraycopy(bs, 0, bytes, p, bs.length);
		p += bs.length;
	}
	
	public void write(DefaultBytesMessage message) throws IOException {
		DefaultKeyValue h = (DefaultKeyValue)message.headers();
		put((byte) h.num);
		for (int i = 0; i < h.num; i++) {
			String key = h.keys[i];
			byte[] bytes = key.getBytes();
			put((byte) bytes.length);
			put(bytes);

			byte[] body = h.values[i];
			put((short) body.length);
			put(body);
		}
		DefaultKeyValue pro = (DefaultKeyValue)message.properties();
		if(pro == null){
			put((byte)0);
		} else {
			put((byte) pro.num);
			for (int i = 0; i < pro.num; i++) {
				String key = pro.keys[i];
				byte[] bytes = key.getBytes();
				put((byte) bytes.length);
				put(bytes);

				byte[] body = pro.values[i];
				put((short) body.length);
				put(body);
			}
		}
		byte[] body = ((DefaultBytesMessage) message).getBody();
		put((short) body.length);
		put(body);
		
		if (p >= DEFAULT_SIZE) {
			deflater.reset();
			deflater.setInput(bytes, 0, p);
			deflater.finish();
			short clen = (short) deflater.deflate(cmp, 2, p - 2);
			cmp[0] = (byte) (clen >> 8 & 0xff);
			cmp[1] = (byte) (clen & 0xff);
			file.write(cmp, 0, clen + 2);
			this.p = 0;
		}
	}

	public void close() throws IOException {
		if (this.p > 0) {
			deflater.reset();
			deflater.setInput(bytes, 0, this.p);
			deflater.finish();
			short clen = (short) deflater.deflate(cmp, 2, cmp.length - 2);
			cmp[0] = (byte) (clen >> 8 & 0xff);
			cmp[1] = (byte) (clen & 0xff);
			file.write(cmp, 0, clen + 2);
			this.p = 0;
		}
		file.writeShort(0);
		file.close();
	}
}
