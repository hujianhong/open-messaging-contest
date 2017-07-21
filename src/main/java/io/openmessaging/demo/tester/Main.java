package io.openmessaging.demo.tester;

import java.util.HashSet;
import java.util.Set;

public class Main {
	
	private static int simpleHash(String key) {
	      if ( key == null ) return 0;
	      else {
	         int hash = 7;
	         for (int i=0; i < key.length(); i++) {
	            hash = hash * 13 + key.charAt(i);
	         }
	         return hash;
	      }
	   }

	public static void main(String[] args) {
		String topic = "topic_";
		int size = 100;
		Set<Integer> integers = new HashSet<>();
		for(int i = 0;i < 90;i++){
			String key = topic + i;
            int index = simpleHash(key) % size;
//			System.out.println(index);
			if(integers.contains(index)){
				System.out.println(index);
			} else {
				integers.add(index);
			}
		}

	}

}
