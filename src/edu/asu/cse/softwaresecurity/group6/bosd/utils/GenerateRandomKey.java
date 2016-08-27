package edu.asu.cse.softwaresecurity.group6.bosd.utils;

import java.util.Random;

public class GenerateRandomKey {
	public static String getRandomKey(){
		int key = 0, key1 = 0;
		int range = 100000 ;     
		   key1 = (int)(Math.random() * range);
		   String keys;
		   StringBuilder sb = new StringBuilder();
		   sb.append("");
		   sb.append(key1);
		   keys = sb.toString();
		   return keys;
	}
	
	public static String createUserName(String firstname, String lastname) {
		String username = null;
		Random r = new Random(System.currentTimeMillis());
		int rand = 10000 + r.nextInt(99999);
		if (lastname.length() > 3) {
			username = String.valueOf(firstname.toLowerCase().charAt(0)) + lastname.toLowerCase().substring(0, 4) + rand;
		} else {
			while (lastname.length() < 4) {
				lastname += 0;
			}
			username = String.valueOf(firstname.charAt(0)) + lastname + rand;
		}
		return username.toLowerCase();
	}
}
