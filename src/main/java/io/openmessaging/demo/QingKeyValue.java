package io.openmessaging.demo;

import java.util.HashSet;
import java.util.Set;

import io.openmessaging.KeyValue;

/**
 * 
 * @author jianhonghu
 *
 */
public class QingKeyValue implements KeyValue {

    private String[] keys;
    
    private byte[][] values;
    
    private int num;
    
    
    public QingKeyValue() {
		this.keys = new String[16];
		this.values = new byte[16][];
		this.num = 0;
	}
    
    public QingKeyValue(int capacity) {
		this.keys = new String[capacity];
		this.values = new byte[capacity][];
		this.num = 0;
	}
    
    public KeyValue put(String key, byte[] value) {
        keys[num] = key;
        values[num ++] = value;
        return this;
    }
    
    public void clear() {
		this.num = 0;
	}
    
    @Override
    public KeyValue put(String key, int a) {
        put(key, new byte[]{(byte)(a >> 24 & 0xff),(byte)(a >> 16 & 0xff),(byte)(a >> 8 & 0xff),(byte)(a & 0xff)});
        return this;
    }

    @Override
    public KeyValue put(String key, long value) {
        put(key, toBytes(value));
        return this;
    }

    @Override
    public KeyValue put(String key, double value) {
    	put(key,toBytes(value));
        return this;
    }

    @Override
    public KeyValue put(String key, String value) {
        put(key, value.getBytes());
        return this;
    }

    @Override
    public int getInt(String key) {
    	byte[] b = get(key);
        return (((b[0]) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) <<  8) |((b[3] & 0xff)));
    }

    @Override
    public long getLong(String key) {
    	return toLong(get(key));
    }

    @Override
    public double getDouble(String key) {
    	byte[] bytes = get(key);
    	 return toDouble(bytes);
    }

    @Override
    public String getString(String key) {
    	byte[] bytes = get(key);
        return bytes == null ? null : new String(bytes);
    }
    
    
    private byte[] get(String key){
    	for(int i = 0;i < num;i ++){
    		if(keys[i].equals(key)){
     		   return values[i];
     	   }
    	}
    	return null;
    }

    @Override
    public Set<String> keySet() {
    	Set<String> set = new HashSet<>();
    	for(int i = 0;i < num;i ++){
    		set.add(keys[i]);
    	}
        return set;
    }
    
    public int size(){
    	return num;
    }

    @Override
    public boolean containsKey(String key) {
       for(int i = 0;i < num;i ++){
    	   if(keys[i].equals(key)){
    		   return true;
    	   }
       }
       return false;
    }
    
	private static long toLong(byte[] bs){
		return ((((long) bs[0]) << 56) | 
				(((long) bs[1] & 0xff) << 48) | 
				(((long) bs[2] & 0xff) << 40) | 
				(((long) bs[3] & 0xff) << 32) | 
				(((long) bs[4] & 0xff) << 24) | 
				(((long) bs[5] & 0xff) << 16) | 
				(((long) bs[6] & 0xff) << 8)  | 
				(((long) bs[7] & 0xff)));
	}
	
	private static byte[] toBytes(long a){
		return new byte[]{(byte)(a >> 56 & 0xff),(byte)(a >> 48 & 0xff),(byte)(a >> 40 & 0xff),(byte)(a >> 32 & 0xff),(byte)(a >> 24 & 0xff),(byte)(a >> 16 & 0xff),(byte)(a >> 8 & 0xff),(byte)(a & 0xff)};
	}
	
	private static byte[] toBytes(double a){
		return toBytes(Double.doubleToRawLongBits(a));
	}
	
    private static double toDouble(byte[] bs){
		return Double.longBitsToDouble(toLong(bs));
	}
}
