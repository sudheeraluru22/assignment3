package com.sudheer.assignment3;

import java.security.SecureRandom;

public class Test {
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
public static void main(String[] args) {
	

	StringBuilder sb = new StringBuilder();
	   for( int i = 0; i < 7; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   System.out.println(sb.toString()); ;
	}
}
