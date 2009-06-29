/*
 * Created on May 30, 2005
 *
 */
package com.teag.EstatePlan;

/**
 * @author Paul Stay
 * Description Save QPRT Calues
 *
 */

import com.teag.estate.QprtTool;
import com.teag.estate.QprtTrustTool;

public class QprtValues extends EstatePlanSqlBean {
	double clientGiftTax;
	double spouseGiftTax;
	double assetValue;
	double assetGrowth;
	double assetBasis;
	int trusts;
	int clientTerm;
	int spouseTerm;
	String name;
	
	public void calculate(long toolId) {
		QprtTool qprt = new QprtTool();
		qprt.setId(toolId);
		qprt.setDbObject();
		qprt.setClientLifeExp(estate.getClientLifeExp());
		qprt.setSpouseLifeExp(estate.getSpouseLifeExp());
		qprt.setClientAge(estate.getClientAge());
		qprt.setSpouseAge(estate.getSpouseAge());
		qprt.read();
		qprt.calculate();
		trusts = qprt.getNumberOfTrusts();
		
		QprtTrustTool clientQprtTrust = qprt.getClientQprt();
		QprtTrustTool spouseQprtTrust = qprt.getSpouseQprt();
		
		if( trusts > 1) {
			spouseTerm = qprt.getSpouseTerm();
			spouseGiftTax = spouseQprtTrust.getTaxableGiftValue();
		}

		clientTerm = qprt.getClientTerm();
		clientGiftTax = clientQprtTrust.getTaxableGiftValue();
		assetValue = qprt.getValue();
		assetGrowth = qprt.getGrowth();
		assetBasis = qprt.getBasis();
		
		name = qprt.getAssetName();
	}
	
	public double getClientGift() {
		return clientGiftTax;
	}
	
	public double getGrowth() {
		return assetGrowth;
	}
	
	public double getSpouseGift() {
		return spouseGiftTax;
	}
	
	public double getValue() {
		return assetValue;
	}
	
	public void initialize() {
		clientGiftTax  = 0;
		spouseGiftTax = 0;
	}
	
}
