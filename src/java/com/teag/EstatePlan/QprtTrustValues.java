package com.teag.EstatePlan;

import com.teag.estate.*;

/**
 * @author Paul Stay
 * Description QPRT Trust values used in the Scenario 2 Cash Flow and Net Worth Report
 * 	  Store Individual Trust values in this class
 *       Net Worth until TERM
 *       To Family after TERM
 *       Gift Tax (or part of exdclusion)
 *		 
 */
public class QprtTrustValues {
	QprtTrustTool qprt;
	String description;
	double netWorth[];
	double toFamily[];
	double gift;			// We will need this when we start looking at Life Time Exemptions
	
	public QprtTrustValues() {
		
	}
	
	
}
