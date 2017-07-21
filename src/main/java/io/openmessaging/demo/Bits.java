package io.openmessaging.demo;

/**
 * 
 * 
 * 
 * 基本数值类型short,int,long,double和byte[] 数组的转换的压缩版本，减少存储空间
 * 
 * 
 * @author jianhonghu
 *
 */
public class Bits {

	public static byte[] toBytes(short a) {
		if (Byte.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if (a < 0) {
				neg = true;
			}
			return new byte[] { neg ? (byte) ((a & 0xff) | 0x80) : (byte) (a & 0xff) };
		}
		return new byte[] { (byte) (a >> 8 & 0xff), (byte) (a & 0xff) };
	}

	public static byte[] toBytes(int a) {
		if (Byte.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if(a < 0){
				neg = true;
			}
			return new byte[] { neg ? (byte)((a & 0xff) | 0x80): (byte)(a & 0xff)};
		}
		if (Short.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if(a < 0){
				neg = true;
			}
			return new byte[] { neg ? (byte)((a >> 8 & 0xff) | 0x80): (byte)(a >> 8 & 0xff), (byte)(a & 0xff)};
		}
		return new byte[]{(byte)(a >> 24 & 0xff),(byte)(a >> 16 & 0xff),(byte)(a >> 8 & 0xff),(byte)(a & 0xff)};
	}

	public static byte[] toBytes(long a) {
		if (Byte.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if(a < 0){
				neg = true;
			}
			return new byte[] { neg ? (byte)((a & 0xff) | 0x80): (byte)(a & 0xff)};
		}
		if (Short.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if(a < 0){
				neg = true;
			}
			return new byte[] { neg ? (byte)((a >> 8 & 0xff) | 0x80): (byte)(a >> 8 & 0xff), (byte)(a & 0xff)};
		}
		if (Short.MIN_VALUE <= a && a <= Byte.MAX_VALUE) {
			boolean neg = false;
			if(a < 0){
				neg = true;
			}
			return new byte[]{neg ? (byte)((a >> 24 & 0xff) | 0x80): (byte)(a >> 24 & 0xff),(byte)(a >> 16 & 0xff),(byte)(a >> 8 & 0xff),(byte)(a & 0xff)};
		}
		return new byte[]{(byte)(a >> 56 & 0xff),(byte)(a >> 48 & 0xff),(byte)(a >> 40 & 0xff),(byte)(a >> 32 & 0xff),(byte)(a >> 24 & 0xff),(byte)(a >> 16 & 0xff),(byte)(a >> 8 & 0xff),(byte)(a & 0xff)};
	}

	public static byte[] toBytes(double a) {
		return toBytes(Double.doubleToRawLongBits(a));
	}
	
	public static short toShort(byte[] b) {
		switch (b.length) {
		case 1:
			return b[0];
		default:
			return (short) ((b[0] << 8) | (b[1] & 0xff));
		}
	}

	public static int toInt(byte[] b) {
		switch (b.length) {
		case 1:
			return b[0];
		case 2:
			return ((b[0] << 8) | (b[1] & 0xff));
		default:
			return (((b[0]) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) <<  8) |((b[3] & 0xff)));
		}
	}

	public static long toLong(byte[] b) {
		switch (b.length) {
		case 1:
			return b[0];
		case 2:
			return ((b[0] << 8) | (b[1] & 0xff));
		case 4:
			return (((b[0]) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) <<  8) |((b[3] & 0xff)));
		default:
			return ((((long) b[0]) << 56) | 
					(((long) b[1] & 0xff) << 48) | 
					(((long) b[2] & 0xff) << 40) | 
					(((long) b[3] & 0xff) << 32) | 
					(((long) b[4] & 0xff) << 24) | 
					(((long) b[5] & 0xff) << 16) | 
					(((long) b[6] & 0xff) << 8)  | 
					(((long) b[7] & 0xff)));
		}
	}

	public static double toDouble(byte[] b) {
		return Double.longBitsToDouble(toLong(b));
	}

	public static void main(String[] args) {
		for(int i = -3666666;i < 3888888;i ++){
			byte[] b = Bits.toBytes(i);
			assert Bits.toInt(b) == i;
		}
		double a = 123.9;
		byte[] b = toBytes(a);
		System.out.println(b.length);
		System.out.println(Bits.toDouble(b));
		
		int a1 = 123;
		byte[] b1 = toBytes(a1);
		System.out.println(b1.length);
		System.out.println(Bits.toInt(b1));
	}

}
