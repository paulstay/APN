package com.teag.analysis;

/**
 * @author stay
 *
 */

import java.util.HashMap;

import com.teag.bean.ClientBean;
import com.teag.bean.PersonBean;

public class NetWorth extends AnalysisSqlBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3229083917571540701L;

	private final static int MAX_TABLE = 120;
    ClientBean cb = null;
    PersonBean person = new PersonBean();
    int retirementAge;
    int currentAge;
    double total[] = new double[MAX_TABLE];
    
    HashMap netWorth = new HashMap();
    
    public NetWorth() {
        for(int i = 0; i < MAX_TABLE; i++)
            total[i] = 0.0;
    }
    
    public void setClient(ClientBean cb){
        this.cb = cb;
    }
    
    public void addValue(int i, double v) {
        total[i] += v;
    }
    
    public double getTotal(int i) {
        return total[i];
    }
}
