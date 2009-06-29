package com.teag.EstatePlan;

/**
 * @author stay
 * Created on May 30, 2005
 *
 */
public class Salary {
	double salary;
	double salaryGrowth;
	
	double salaryTable[];
	
	public void calculate() {
		salaryTable = new double[EstatePlanTable.MAX_TABLE];
		double consulting = salary;
        
        salaryTable = new double[EstatePlanTable.MAX_TABLE];
        for(int i= 0; i < EstatePlanTable.MAX_TABLE; i++) {
               salaryTable[i] = consulting;
               consulting += (consulting * salaryGrowth);
        }
	}
	
	/**
	 * @return Returns the salary.
	 */
	public double getSalary() {
		return salary;
	}
	
	public double getSalary(int year) {
		return salaryTable[year];
	}
	/**
	 * @return Returns the salaryGrowth.
	 */
	public double getSalaryGrowth() {
		return salaryGrowth;
	}
	/**
	 * @param salary The salary to set.
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}
	/**
	 * @param salaryGrowth The salaryGrowth to set.
	 */
	public void setSalaryGrowth(double salaryGrowth) {
		this.salaryGrowth = salaryGrowth;
	}
}
