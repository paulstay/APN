package com.teag.EstatePlan;

/**
 * @author stay
 * Created on Jun 1, 2005
 *
 */
import com.teag.estate.ClatTool;

public class ClatValues extends EstatePlanSqlBean {
	double clatValue[];
	double toCharity[];
	double grantorTax[];
	double charitableDeduction;
	boolean isGrantor = false;
	
	
	public ClatValues() {
		clatValue = new double[EstatePlanTable.MAX_TABLE];
		toCharity = new double[EstatePlanTable.MAX_TABLE];
		grantorTax = new double[EstatePlanTable.MAX_TABLE];
	}
	
	public void calculate(long toolId){
		ClatTool tool = new ClatTool();
		tool.setDbObject();
		tool.setId(toolId);
		tool.read();
		tool.calculate();
		tool.buildAssetList();
		tool.buildStdTable();
		tool.buildClat2Table();

		
		double tmpValue[] = tool.getClatTrustValue();
		double charity[] = tool.getToCharity();
		double gTax[]  = tool.getGrantorTax();
		charitableDeduction = tool.getClatDeduction();
		if(tool.getGrantorFlag().equals("Y")){
			isGrantor = true;
		}
		
		for(int i= 0; i < finalDeath; i++) {
			clatValue[i] += tmpValue[i];
			toCharity[i] += charity[i+1];
			grantorTax[i] += gTax[i];
		}
	}
	
	public double getClatCharity(int year) {
		return toCharity[year];
	}
	
	public double getClatValue(int year) {
		return clatValue[year];
	}
	
	public double getGrantorTax(int year) {
		return grantorTax[year];
	}
	
	public boolean isGrantor() {
		return isGrantor;
	}
	
	public double getCharitableDeduction(){
		return charitableDeduction;
	}
}
