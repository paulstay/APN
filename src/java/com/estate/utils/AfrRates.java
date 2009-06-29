/**
 * 
 */
package com.estate.utils;

/**
 * @author Paul Stay
 * Description: This class calculates the 7520 rate from the AFR mid term rate
 * which is then rounded to the nearest 2/10 of 1%
 *
 */

import java.net.*;
import java.io.*;

import org.jdom.input.SAXBuilder;
import org.jdom.*; 
import java.util.*;
import org.jdom.output.*;
import static java.lang.System.out;

public class AfrRates {

	public static boolean USE_URL = true;
	public static String monthArray[] = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY",
		"AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
	
	int year;
	String month;
	int afr;
	double irsRate;
	
	public AfrRates(int year, String month, int afr){
		this.year = year;
		this.month = month;
		this.afr = afr;
		irsRate = AfrRates.calcRate((int)afr);
	}
	
	public static Document loadDoc(BufferedReader reader) {
		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc;

			if(USE_URL){
				URL url = new URL("ftp://ftp.brentmark.com/afr.xml");
				doc = builder.build(url);
			} else {
				doc = builder.build(reader);
			}
			
			return doc;
		} catch(Exception e){
			out.println("Error loading XML Document");
		}
		return null;
	}
	public static ArrayList<AfrRates> loadRates(){
		ArrayList<AfrRates> aList = new ArrayList<AfrRates>();
		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc;

			if(USE_URL){
				URL url = new URL("ftp://ftp.brentmark.com/afr.xml");
				doc = builder.build(url);
			} else {
				doc = builder.build(new File("http://localhost/teag/webapp/textFiles/afr.xml"));
			}

			Element root = doc.getRootElement();
			Element afr = root.getChild("ANNUAL_AFR");
			List afrList = afr.getChildren();
			Iterator itr = afrList.iterator();
			while(itr.hasNext()){
				Element c = (Element) itr.next();
				int year = Integer.parseInt(c.getName().substring(5));
				List months = c.getChildren();
				Iterator mitr = months.iterator();
				while(mitr.hasNext()){
					Element m = (Element) mitr.next();
					int afrNumber = Integer.parseInt(m.getText());
					AfrRates a = new AfrRates(year,m.getName(), afrNumber);
					aList.add(a);
				}
			}

			return aList;

		} catch(Exception e){
			System.err.println("error loading afr rates");
		}
		return null;
	}
	
	public static ArrayList<AfrRates> loadFromURL(){
		ArrayList<AfrRates> aList = new ArrayList<AfrRates>();

		try{
			SAXBuilder builder = new SAXBuilder();
			Document doc;

			URL url = new URL("ftp://ftp.brentmark.com/afr.xml");
			doc = builder.build(url);

			Element root = doc.getRootElement();
			Element afr = root.getChild("ANNUAL_AFR");
			List afrList = afr.getChildren();
			Iterator itr = afrList.iterator();
			while(itr.hasNext()){
				Element c = (Element) itr.next();
				int year = Integer.parseInt(c.getName().substring(5));
				List months = c.getChildren();
				Iterator mitr = months.iterator();
				while(mitr.hasNext()){
					Element m = (Element) mitr.next();
					int afrNumber = Integer.parseInt(m.getText());
					AfrRates a = new AfrRates(year,m.getName(), afrNumber);
					aList.add(a);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Something wrong with JDOM");
		}
		return aList;
	}
	
	public static void writeXml(Document doc, String fileName){
		try {
			XMLOutputter outputter = new XMLOutputter();
			FileWriter writer = new FileWriter(fileName);
			outputter.output(doc, writer);
			
			writer.close();
		} catch (Exception e){
			System.err.println("Error writing AFR File");
		}
	}
	
	public static double calcRate(int afr){
		double irsRate = 0;
		int floorResult;
		irsRate = afr/10;
		floorResult = (int) Math.floor(irsRate);
		
		if(Integer.lowestOneBit(floorResult)== 1) {
			floorResult++;
		}
		
		// Convert to percent
		irsRate = ((double)(floorResult))/10;
		
		return irsRate;
	}

	public static void main(String args[]) {
		Document doc = loadDoc(null);
		writeXml(doc, "e:\\dev\\aes\\webapp\\textfiles\\afr.xml");
		
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public String getMonth() {
		return month;
	}



	public void setMonth(String month) {
		this.month = month;
	}



	public int getAfr() {
		return afr;
	}

	public void setAfr(int afr) {
		this.afr = afr;
	}
}
