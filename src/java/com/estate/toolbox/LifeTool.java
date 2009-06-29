package com.estate.toolbox;


import java.util.ArrayList;
import java.util.Scanner;

public class LifeTool {

	String inputString;
	ArrayList<LifeElement> lifeLines = new ArrayList<LifeElement>();

	public double[] getCashValue() {
		ArrayList<Double> cvList = new ArrayList<Double>();
		double cashValue[] = null;
		for(LifeElement elem : lifeLines) {
			cvList.add(Double.valueOf(elem.getNetCashValue()));
		}
		cashValue = new double[cvList.size()];
		int i=0; 
		for(Double d : cvList) {
			double x = d.doubleValue();
			cashValue[i++] = x;
		}
		return cashValue;
	}

	public double[] getDeathBenefit() {
		double benefit[] = null;
		ArrayList<Double> dList = new ArrayList<Double>();
		for(LifeElement elem : lifeLines){
			dList.add(Double.valueOf(elem.getNetDeathBenefit()));
		}
		benefit = new double[dList.size()];
		int i = 0;
		for(Double d : dList){
			double x = d.doubleValue();
			benefit[i++] = x;
		}
		return benefit;
	}
	
	public double[] getDividends() {
		double dividends[] = null;
		ArrayList<Double> dList = new ArrayList<Double>();
		for(LifeElement elm : lifeLines){
			dList.add(Double.valueOf(elm.getDividends()));
		}
		dividends = new double[dList.size()];
		int i=0; 
		for(Double d : dList){
			double x = d.doubleValue();
			dividends[i++] = x;
		}
		return dividends;
	}
	
	public String getInputString() {
		return inputString;
	}
	
	public LifeElement[] getLifeElements() {
		LifeElement elements[] = null;
		if(!lifeLines.isEmpty()) {
			elements = new LifeElement[lifeLines.size()];
			lifeLines.toArray(elements);
		}
		return elements;
	}

	public double[] getPremiums() {
		double premiums[]= null;
		ArrayList<Double> dList = new ArrayList<Double>();
		for(LifeElement elem : lifeLines) {
			if( elem.getNetPremium()>0) {
				dList.add(Double.valueOf(elem.getNetPremium()));
			}
		}
		premiums = new double[dList.size()];
		int i=0;
		for(Double d : dList) {
			double x = d.doubleValue();
			premiums[i++] = x;
		}
		return premiums;
	}
	
	public void setInputString(String inputString) {
		this.inputString = inputString;
		lifeLines.clear(); // Clear out the old life insurance data
		Scanner scan = new Scanner(inputString);
		while(scan.hasNext()) {
			String line = scan.nextLine();
			LifeElement el = new LifeElement();
			el.setIStr(line);
			lifeLines.add(el);
		}
	}
}
