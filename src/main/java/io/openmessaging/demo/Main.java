package io.openmessaging.demo;

import org.iq80.snappy.Snappy;

public class Main {

	public static void main(String[] args) {
//		String string = "hujianhong";
//		for(int i = 0;i < 10;i ++){
//			string += string;
//		}
//		System.out.println(string.getBytes().length);
//		byte[] bytes = QuickLZ.compress(string.getBytes(), 1);
//		System.out.println(bytes.length);
//		System.out.println(new String(QuickLZ.decompress(bytes)));
		
//		String string = "hujianhong";
//		for(int i = 0;i < 10;i ++){
//			string += string;
//		}
//		byte[] source = string.getBytes();
//		byte[] cmp = new byte[source.length + 400];
//		
//		int clen = QingQuickLZ.compress(source, source.length, cmp, 3);
//		System.out.println(clen);
//		byte[] kk = new byte[source.length + 400];
//		
//		int len = QingQuickLZ.decompress(cmp, clen, kk);
//		System.out.println(new String(kk,0,len));
		
		
//		String string = "hujianhong";
//		for(int i = 0;i < 10;i ++){
//			string += string;
//		}
//		byte[] source = string.getBytes();
//		byte[] cmp = new byte[source.length];
//		
//		int clen = Snappy.compress(source,0, source.length, cmp, 0);
//		System.out.println(clen);
//		byte[] kk = new byte[source.length];
//		
//		int len = Snappy.uncompress(cmp, 0, clen, kk, 0);
//		System.out.println(new String(kk,0,len));
		
//		int h = 2;
//		int p = 4;
//		
//		byte t = (byte) ((((byte)h) << 4 & 0xf0) | (p & 0x0f));
//		
//		System.out.println(t);
//		
//		System.out.println(t >> 4 & 0x0f);
//		
//		System.out.println(t & 0x0f);
		
//		int a = 64 * 1024 -1;
//		System.out.println(a);
//		byte b1 = (byte) (a >> 8 & 0xff);
//		
//		byte b2 = (byte) (a & 0xff);
//		
//		System.out.println(b1);
//		
//		System.out.println(b2);
//		
//		
//		System.out.println(((((b1 & 0xff) << 8) | (b2 & 0xff))) & 0x7fffffff);
//		
//		System.out.println(Short.MAX_VALUE);
		
		byte a = 10;
		
		byte b = (byte) (a | 0x80);
		
		System.out.println(b);
		
		byte c = (byte) (b & 0x7f);
		
		System.out.println(c);
	}

}
