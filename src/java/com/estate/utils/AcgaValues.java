package com.estate.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class AcgaValues {
	
	public static void  main(String args[]) {
		AcgaValues av = new AcgaValues();
		
		System.out.println("Single age of 54 is " + Double.toString(av.singleLife(54)));
		System.out.println("Dual Life age of 50,53 is " + Double.toString(av.doubleLife(54,57)));
		
	}
	
	ArrayList<dLife> dList = new ArrayList<dLife>();
	
	double singleLifeAcga[] = {3.3,3.3,3.3,3.3,3.3,3.3,3.4,3.4,3.4,3.4,3.4,3.4,3.5,
			3.5,3.5,3.5,3.5,3.5,3.6,3.6,3.6,3.6,3.7,3.7,3.7,3.7,3.8,
			3.8,3.8,3.9,3.9,3.9,4.0,4.0,4.0,4.1,4.1,4.2,4.2,4.3,4.3,
			4.4,4.5,4.5,4.6,4.7,4.7,4.8,4.9,5.0,5.1,5.2,5.2,5.2,5.2,
			5.3,5.3,5.4,5.4,5.4,5.5,5.5,5.6,5.6,5.7,5.7,5.8,5.9,6.0,
			6.0,6.1,6.2,6.3,6.5,6.6,6.7,6.9,7.0,7.2,7.4,7.6,7.8,8.0,
			8.3,8.6,8.9,9.2,9.5,9.8,10.1,10.5};
	
	public AcgaValues() {
		try {
			/*
			BufferedReader in = new BufferedReader(
		            new InputStreamReader(new FileInputStream("infilename"), "UTF8"));
		        String str = in.readLine();
	        */
			URL u = new URL("http://localhost/teag/textFiles/acgaTwoLife");
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			String str;
			while((str = in.readLine())!= null) {
				dLife d = new dLife(str);
				dList.add(d);
			}
		} catch (MalformedURLException mue) {
			System.out.println("OUch - it happened");
		} catch (Exception ieo) {
			System.out.println("Oops 0 IOException");
		}
	}
	
	public double doubleLife(int a1, int a2) {
		int yAge;
		int xAge;
		
		// First check to make sure the smaller is first.
		if( a1 < a2) {
			yAge = a1;
			xAge = a2;
		} else {
			yAge = a2;
			xAge = a1;
		}
		
		for(dLife d: dList) {
			boolean f = d.findRange(yAge, xAge);
			if(f)
				return d.annuityRate;
		}
		return 0;
		
	}

	public double singleLife(int age){
		if( age < 0)
			return -1.0;
		if(age > 90)
			return 10.5;
		
		return singleLifeAcga[age];
	}
}

class dLife {
	int yMin;
	int yMax;
	int oMin;
	int oMax;
	double annuityRate;

	// The string should consist of three parts, youngest age, older age, and annuity rate.
	dLife(String str) {
		Scanner sc = new Scanner(str);
		String a1 = sc.next();
		String a2 = sc.next();
		annuityRate = sc.nextDouble();
		
		process1(a1);		// Youngest Age range
		process2(a2);		// Olderst age range
		
	}
	
	boolean findRange(int x, int y) {
		
		if(!(yMin <= x && x <= yMax))
			return false;
		
		if(y>=oMin && y <= oMax)
			return true;

		return false;
	}
	
	void process1(String s) {
		int i = s.indexOf("-");
		if(i <=0) {			// We only have one age here
			yMin = Integer.parseInt(s);
			yMax = Integer.parseInt(s);
		} else {
			String x = s.substring(0,i);
			String y = s.substring(i+1);
			yMin = Integer.parseInt(x);
			yMax = Integer.parseInt(y);
		}
	}

	void process2(String s) {
		int i = s.indexOf("-");
		if(i <= 0) {
			int j = s.indexOf("+");
			if(j<=0) {
				oMin = Integer.parseInt(s);
				oMax = oMin;
			} else {
				oMin = Integer.parseInt(s.substring(0,j));
				oMax = 150;		// The older age has no bounds......
			}
		} else {
			String s1 = s.substring(0,i);
			String s2 = s.substring(i+1);
			oMin = Integer.parseInt(s1);
			oMax = Integer.parseInt(s2);
		}
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		
		sb.append("index " + Integer.toString(yMin) + "-" + Integer.toString(yMax) + " ");
		sb.append("older age " + Integer.toString(oMin) + "-" + Integer.toString(oMax) + " ");
		sb.append("Annuity rate " + Double.toString(annuityRate) + "\n");
		return sb.toString();
	}

}
