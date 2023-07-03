package kr.smhrd.controller;

import java.util.Random;

public class asd {

	public static void main(String[] args) {
		Random random = new Random();
		String otp = "";

		for (int i = 1; i < 9; i++) {

			otp = otp + String.valueOf(random.nextInt(10));

		}

		
		System.out.println(otp);
	}
	

}
