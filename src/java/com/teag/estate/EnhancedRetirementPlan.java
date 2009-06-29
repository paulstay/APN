package com.teag.estate;

import java.text.DecimalFormat;
public class EnhancedRetirementPlan {

	public static void main(String args[]) {
		EnhancedRetirementPlan er = new EnhancedRetirementPlan();
		er.setTerm(10);
		er.setCCont(326446);
		er.setSCont(321341);
		er.setCDeathBenefit(1875000);
		er.setSDeathBenefit(1875000);
		er.setCMonthly(er.getCDeathBenefit()/100);
		er.setSMonthly(er.getSDeathBenefit()/100);
		er.setCLife(174027);
		er.setSLife(168826);
		er.setCAge(67);
		er.setSAge(65);
		
		er.buildStd();
		er.buildPS58();
		er.buildPension();
		er.buildContribution();
		er.buildSideFund();
		er.buildLife();
		er.buildToFamily();
	}
	DecimalFormat df = new DecimalFormat("$###,###,###");
	public double cCont;
	public double sCont;
	public int cAge;
	public int sAge;
	public double cLife;
	public double sLife;
	public double cDeathBenefit;
	public double sDeathBenefit;
	public double stdGrowth = .06;
	public double cpGrowth = .0713;
	public double taxRate = .4313;
	public double sidefundGrowth;
	public double cMonthly;
	public double sMonthly;
	
	public int term;
	public double before[] = new double[25];
	public double pension[] = new double[25];
	public double contribution[] = new double[25];
	public double cPs58Tax[] = new double[25];
	public double sPs58Tax[] = new double[25];
	public double sideFund[] = new double[25];
	public double lifeIns[] = new double[25];
	
	public double toFamily[] = new double[25];
	
	public int finalDeath = 25;

	public EnhancedRetirementPlan() {
		setTerm(10);
		setCCont(326446);
		setSCont(321341);
		setCDeathBenefit(1875000);
		setSDeathBenefit(1875000);
		setCMonthly(getCDeathBenefit()/100);
		setSMonthly(getSDeathBenefit()/100);
		setCLife(174027);
		setSLife(168826);
		setCAge(67);
		setSAge(65);
		
		buildStd();
		buildPS58();
		buildPension();
		buildContribution();
		buildSideFund();
		buildLife();
		buildToFamily();
	}
	
	public void buildContribution() {
		for(int i=0; i< 10; i++) {
			contribution[i] = sCont + cCont;
		}
	}
	
	public void buildLife() {
		double life = cDeathBenefit + sDeathBenefit;
		for(int i=0; i < finalDeath; i++){
			lifeIns[i] = life;
			life = life * 1.03;
		}
	}
	
	public void buildPension() {
		for(int i = 0; i < finalDeath; i++){
			if( i < 10) {
				pension[i] = 0;
			}else {
				pension[i] = (cMonthly + sMonthly)*12;
			}
		}
	}
	
	public void buildPS58() {
		for(int i = 0; i < finalDeath; i++) {
			if( i < 15)
				cPs58Tax[i] = (PS58Calculator.lookup(cAge +i)*cDeathBenefit/1000)*taxRate; 
			sPs58Tax[i] = (PS58Calculator.lookup(sAge +i)*sDeathBenefit/1000)*taxRate; 
		}
	}
	
	public void buildSideFund() {
		double s1 = sCont - sLife;
		double s2 = cCont - cLife;
		double temp = 0;
		for(int i=0; i < finalDeath; i++) {
			if( i < term) {
				temp+= s1+s2;
			}
			temp = temp*(1 + cpGrowth);
			temp -= pension[i];
			sideFund[i] = temp;
			temp = sideFund[i];
		}
	}
	
	public void buildStd() {
		double temp = 0;
		
		for(int i=0; i < finalDeath; i++) {
			double fund = 0;
			double tax = 0;
			if( i < term) {
				fund = cCont + sCont;
				tax = fund * taxRate;
			}
			before[i] = (temp + fund - tax)*1.06;
			temp = before[i];
		}
	}

	public void buildToFamily() {
		double life = cDeathBenefit + sDeathBenefit;
		for( int i=0; i < finalDeath; i++) {
			toFamily[i] = (lifeIns[i] - life) + sideFund[i];
		}
	}
	
	public double[] getBefore() {
		return before;
	}

	public int getCAge() {
		return cAge;
	}

	public double getCCont() {
		return cCont;
	}

	public double getCDeathBenefit() {
		return cDeathBenefit;
	}

	public double getCLife() {
		return cLife;
	}
	public double getCMonthly() {
		return cMonthly;
	}
	public double[] getContribution() {
		return contribution;
	}
	public double getCpGrowth() {
		return cpGrowth;
	}
	public double[] getCPs58Tax() {
		return cPs58Tax;
	}
	public int getFinalDeath() {
		return finalDeath;
	}
	public double[] getLifeIns() {
		return lifeIns;
	}
	public double[] getPension() {
		return pension;
	}
	public int getSAge() {
		return sAge;
	}
	public double getSCont() {
		return sCont;
	}
	public double getSDeathBenefit() {
		return sDeathBenefit;
	}
	public double[] getSideFund() {
		return sideFund;
	}
	public double getSidefundGrowth() {
		return sidefundGrowth;
	}
	public double getSLife() {
		return sLife;
	}
	public double getSMonthly() {
		return sMonthly;
	}
	public double[] getSPs58Tax() {
		return sPs58Tax;
	}
	public double getStdGrowth() {
		return stdGrowth;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public int getTerm() {
		return term;
	}
	public double[] getToFamily() {
		return toFamily;
	}
	public void setBefore(double[] before) {
		this.before = before;
	}
	public void setCAge(int age) {
		cAge = age;
	}

	public void setCCont(double cont) {
		cCont = cont;
	}

	public void setCDeathBenefit(double deathBenefit) {
		cDeathBenefit = deathBenefit;
	}

	public void setCLife(double life) {
		cLife = life;
	}

	public void setCMonthly(double monthly) {
		cMonthly = monthly;
	}

	public void setContribution(double[] contribution) {
		this.contribution = contribution;
	}

	public void setCpGrowth(double cpGrowth) {
		this.cpGrowth = cpGrowth;
	}

	public void setCPs58Tax(double[] ps58Tax) {
		cPs58Tax = ps58Tax;
	}

	public void setFinalDeath(int finalDeath) {
		this.finalDeath = finalDeath;
	}

	public void setLifeIns(double[] lifeIns) {
		this.lifeIns = lifeIns;
	}

	public void setPension(double[] pension) {
		this.pension = pension;
	}

	public void setSAge(int age) {
		sAge = age;
	}

	public void setSCont(double cont) {
		sCont = cont;
	}

	public void setSDeathBenefit(double deathBenefit) {
		sDeathBenefit = deathBenefit;
	}

	public void setSideFund(double[] sideFund) {
		this.sideFund = sideFund;
	}

	public void setSidefundGrowth(double sidefundGrowth) {
		this.sidefundGrowth = sidefundGrowth;
	}

	public void setSLife(double life) {
		sLife = life;
	}

	public void setSMonthly(double monthly) {
		sMonthly = monthly;
	}

	public void setSPs58Tax(double[] ps58Tax) {
		sPs58Tax = ps58Tax;
	}

	public void setStdGrowth(double stdGrowth) {
		this.stdGrowth = stdGrowth;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public void setToFamily(double[] toFamily) {
		this.toFamily = toFamily;
	}
	
}
