/*
 * Created on Dec 23, 2004
 *
 */
package com.teag.util;

/**
 * @author Paul Stay
 * Description - These static functions provide an interface to The Estate Advisory Group 
 * math functions used in calculating Estate Planning.
 * 
 */

import java.text.DecimalFormat;

public class Function {

    public static double FV(double pv, double i, double n){
        double a = 0.0;
        if( pv > 0 ) {
            a = pv * Math.pow( (1 +i),n );
        } else {
            a = -pv * Math.pow( (1 +i),n );
            a = -pv - Math.abs(a + pv);
        }

        return Math.round(100.0 * a)/100.0;
    }
    
    public static double IPMT(double rate, double period, double nper, double loan){
        double pmt = PMT(loan, rate, nper);
        double balance = loan;
        double interest = 0.0;
        for( int i = 0; i <= period; i++) {
            interest = rate * balance;
            balance -= (pmt - interest);
        }
        return interest;
    }
    
    public static double IPMT2(double rate, double period, double loan, double pmt) {
        double balance = loan;
        double interest = 0.0;
        for( int i = 0; i <= period; i++) {
            interest = rate * balance;
            balance -= (pmt - interest);
        }
        return interest;
    }
    
    public static double[][] loanTable(double rate, double remainingMonths, double loanBalance, double pmt ) {
        double[][] tbl = new double[4][30];
        double interestSum = 0.0;
        
        for( int i= 0; i < 12; i++){
            interestSum += IPMT2(rate, i, loanBalance, pmt);
        }
        
        tbl[0][0] = loanBalance;
        tbl[1][0] = pmt * 12 - interestSum;
        tbl[2][0] = interestSum; //need to calculate yearly interest
        tbl[3][0] = loanBalance - (tbl[1][0]);
        remainingMonths -= 12;
        
        for( int i = 1; i < 30; i++){
            interestSum = 0.0;
            for(int j = 0; j < 12; j++) { 
                interestSum += IPMT2(rate, i*12 + j, loanBalance, pmt);
            }
            tbl[0][i] = tbl[3][i-1];
            tbl[1][i] = pmt * 12 - interestSum;
            tbl[2][i] = interestSum;
            tbl[3][i] = tbl[0][i] - (tbl[1][i]);
            
            // Fix table
            if( tbl[0][i] < 0 ) {
                tbl[1][i] = 0;
                tbl[2][i] = 0;
                tbl[3][i] = 0;
            }
        }
        
        return tbl;
    }
    
    public static void main(String args[]) {
        
        double[][] loan = Function.loanTable(.06, 240, 97000, 781);
        
        DecimalFormat df = new DecimalFormat("###,###");
        
        System.out.println("Balance\t");
        for( int i = 0; i < 30; i++) {
            System.out.print(df.format(loan[0][i]) + "  ");
        }
        System.out.println("Principal\t");
        for( int i = 0; i < 30; i++) {
            System.out.print(df.format(loan[1][i]) + "  ");
        }
        System.out.println("Interest\t");
        for( int i = 0; i < 30; i++) {
            System.out.print(df.format(loan[2][i]) + "  ");
        }
        System.out.println("New Balance\t");
        for( int i = 0; i < 30; i++) {
            System.out.print(df.format(loan[3][i]) + "  ");
        }
    }
    
    public static double PMT(double loan, double i, double n) {
        double a = 0.0;
        
        double p = Math.pow((1 + i),n);
        
        if(i==0){
        	a = loan/n;
        } else {
           	a = (loan * p * i) / (p - 1.0);
        }
        return a;
    }
}
