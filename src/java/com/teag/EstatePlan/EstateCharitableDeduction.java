package com.teag.EstatePlan;


import java.util.LinkedList;

public class EstateCharitableDeduction {

	LinkedList<Deduction> dList = new LinkedList<Deduction>();
	
	double agiLimitation = .3;
	
	public void addDeduction(double v){
		Deduction ded = new Deduction();
		ded.yrsRemaining = 5;
		ded.value = v;
		dList.add(ded);
	}
	
	class Deduction {
		int yrsRemaining;
		double value;
	}
	
	public Deduction largestRemaining() {
		Deduction d = null;
		double value = -1;
		
		for(Deduction item : dList){
			if(item.value > value){
				d = item;
				value = item.value;
			}
		}
		
		dList.remove(d);
		
		return d;
		
	}
	
	public void listDeductions(){
		for(Deduction d : dList){
			if(d.yrsRemaining> 0)
				System.out.println("Deduction Value is " + String.format("%f",d.value) + " Yrs Remaining " + String.format("%d",d.yrsRemaining));
		}
	}

	public void cleanList() {
		LinkedList<Deduction> tmpList = new LinkedList<Deduction>();
		while(!dList.isEmpty()){
			Deduction d = dList.removeFirst();
			if(d.yrsRemaining > 0 && d.value > 0)
				tmpList.add(d);
		}
		dList = tmpList;
	}

	public double totalRemaining() {
		double value = 0;
		
		for(Deduction d : dList){
			value += d.value;
		}
		return value;
	}
	
	/**
	 * Get the charitable deduction available for this year.
	 * @param agi
	 * @return
	 */
	public double getDed(double agi){
		double value = 0;
			
		double maxAllowed = agi * .3;	// 30% of AGI, we are assuming cash here
		double total = totalRemaining();
		
		// If the total remaining is less than the max allowed, then remove all of the deduction in the list
		if(total < maxAllowed){
			dList.clear();
			return total;
		}
		
		value = maxAllowed;
		
		while(value > 0){
			Deduction d = largestRemaining();
			if(d.value > value && d.yrsRemaining >0 ){				// Only use partial
				d.value = d.value - value;
				dList.add(d);					// Put it back in the list
				value = 0;
			} else {
				value -= d.value;
				d.value = 0;
			}
		}

		for(Deduction d: dList){
			d.yrsRemaining--;
		}
		
		cleanList();
		return maxAllowed;
	}
	
	
	public static void main(String args[]){
		EstateCharitableDeduction cDed = new EstateCharitableDeduction();
		double[] ded = new double[10];
		
		cDed.addDeduction(2345100);
		double agi=500000;

		for(int i=0; i < 10; i++){
			cDed.addDeduction(25000);
			ded[i] = cDed.getDed(agi);
			System.out.println(String.format("Deduction used is : %.2f with %.2f remaining", ded[i],cDed.totalRemaining()));
		}
		cDed.listDeductions();
	}
}
