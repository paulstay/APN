package com.teag.estate;

/** 
 * @author stay 
 * FederalEstateTax.java
 * Created on Jun 20, 2005
 *
 */

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.zcalc.zCalc;

public class FederalEstateTax {

    public static FederalEstateTax federalEstateTax = new FederalEstateTax();
    
    public static double getFET(int year) {
        if( federalEstateTax == null )  {
            federalEstateTax = new FederalEstateTax();
        }

        return federalEstateTax.fet( year); 
    }
    
    HashMap<String,Object> fetTable = new HashMap<String,Object>();
    
    public FederalEstateTax() {
        zCalc zc = new zCalc();
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        
        zc.StartUp();
        for(int i = 0; i < 100; i++) {
            String yr = Integer.toString(i + year);
            Double fet = new Double(zc.zFETMAXRATE(i + year,0));
            fetTable.put(yr,fet);
        }
        zc.ShutDown();
    }
    
    public double fet(int i) {
        String yr = Integer.toString(i);
        Double db = (Double) fetTable.get(yr);
        return db.doubleValue();
    }
    
    
}

