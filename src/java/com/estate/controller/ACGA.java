package com.estate.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class ACGA {
	public static void  main(String args[]) {
		StringBuffer sb = new StringBuffer("");
		sb.append("double acga[] = {");
		try {
			BufferedReader in = new BufferedReader(new FileReader("acga.txt"));
			String str;
			while((str = in.readLine())!= null) {
				Scanner s = new Scanner(str);
				double b = s.nextDouble();
				sb.append(Double.toString(b) + ",");
			}
			sb.append("};");
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
		
		System.out.println(sb.toString());
	}
}
