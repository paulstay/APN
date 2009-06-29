/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

import com.teag.util.TeagDefines;

/**
 * @author Paul Stay
 *
 */
public class SocialSecurityPayment {
	
	double socialPayment;
	double socialGrowth;
	double socialTable[];
	
	public void calculate(int clientAge, int spouseAge) {
		double socialSecurity = socialPayment;
        double sInc = socialGrowth;
        int cAge = clientAge;
        int sAge = spouseAge;
        int primaryBeginSocial = TeagDefines.SOCIAL_START_PRIMARY;
        int spouseBeginSocial = TeagDefines.SOCIAL_START_SPOUSE;
        socialTable = new double[EstatePlanTable.MAX_TABLE];
        for( int i=0; i < EstatePlanTable.MAX_TABLE; i++) {
        	double totalSS = 0;
            if( cAge++ >= primaryBeginSocial) {
            	totalSS += 12 * socialSecurity;
            }
            if( sAge++ >= spouseBeginSocial ) {
            	totalSS += (12 * socialSecurity) * .50; 
            }
            if( cAge > primaryBeginSocial || sAge > spouseBeginSocial) {
            	socialSecurity += socialSecurity * sInc;
            }
            socialTable[i] = totalSS;
        }
	}
	
	public double getSocialPayment(int year) {
		return socialTable[year];
	}
}
