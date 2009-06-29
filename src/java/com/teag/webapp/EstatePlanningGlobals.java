package com.teag.webapp;
import com.teag.bean.ClientBean;
import com.teag.client.EPTAssets;
import com.zcalc.zCalc;


public class EstatePlanningGlobals {

    public int clientAge;
    public int spouseAge;
    public String clientGender;
    public String spouseGender;
    public double clientLifeExp;
    public double spouseLifeExp;
    public String clientFirstName;
    public String spouseFirstName;
    public String lastName;
    public ClientBean client;
    public EPTAssets eptassets;
    public long scenarioId;
    public boolean isSingle = false;
    
    /**
     * @return Returns the client.
     */
    public ClientBean getClient() {
        return client;
    }


	/**
     * @return Returns the clientAge.
     */
    public int getClientAge() {
        return clientAge;
    }


	/**
     * @return Returns the clientFirstName.
     */
    public String getClientFirstName() {
        return clientFirstName;
    }
    
    
    /**
     * @return Returns the clientGender.
     */
    public String getClientGender() {
        return clientGender;
    }
    /**
     * @return Returns the clientLifeExp.
     */
    public double getClientLifeExp() {
        return clientLifeExp;
    }
    /**
	 * @return Returns the eptAssets.
	 */
	public EPTAssets getEptassets() {
		return eptassets;
	}
    /**
     * @return Returns the lastName.
     */
    public String getLastName() {
        return lastName;
    }
    /**
	 * @return Returns the scenarioId.
	 */
	public long getScenarioId() {
		return scenarioId;
	}
    /**
     * @return Returns the spouseAge.
     */
    public int getSpouseAge() {
        return spouseAge;
    }
    /**
     * @return Returns the spouseFirstName.
     */
    public String getSpouseFirstName() {
        return spouseFirstName;
    }
    /**
     * @return Returns the spouseGender.
     */
    public String getSpouseGender() {
        return spouseGender;
    }
    /**
     * @return Returns the spouseLifeExp.
     */
    public double getSpouseLifeExp() {
        return spouseLifeExp;
    }
    public void init() {
        // Used to calculate Life expectancy for client and spouse
        zCalc zc = new zCalc();
        zc.StartUp();
        int ageFactor = 0;
        
        
        if(clientGender.equals("M") ) 
            ageFactor = 2;
        else 
            ageFactor = -4;

        clientLifeExp = zc.zLE(clientAge + ageFactor,0,0,0,0,0,0);
        
        if( !isSingle()) {
	        if( spouseGender.equals("F"))
	            ageFactor = -4;
	        else
	            ageFactor = 2;
	        
	        spouseLifeExp = zc.zLE(spouseAge + ageFactor, 0,0,0,0,0,0);
        }
        zc.ShutDown();
    }
    public boolean isSingle() {
		return isSingle;
	}
    /**
     * @param client The client to set.
     */
    public void setClient(ClientBean client) {
        this.client = client;
    }
    /**
     * @param clientAge The clientAge to set.
     */
    public void setClientAge(int clientAge) {
        this.clientAge = clientAge;
    }
    /**
     * @param clientFirstName The clientFirstName to set.
     */
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }
    /**
     * @param clientGender The clientGender to set.
     */
    public void setClientGender(String clientGender) {
        this.clientGender = clientGender;
    }
    /**
     * @param clientLifeExp The clientLifeExp to set.
     */
    public void setClientLifeExp(double clientLifeExp) {
        this.clientLifeExp = clientLifeExp;
    }
    /**
	 * @param eptAssets The eptAssets to set.
	 */
	public void setEptassets(EPTAssets eptassets) {
		this.eptassets = eptassets;
	}
    /**
     * @param lastName The lastName to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
	 * @param scenarioId The scenarioId to set.
	 */
	public void setScenarioId(long scenarioId) {
		this.scenarioId = scenarioId;
	}
    public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
    
	/**
     * @param spouseAge The spouseAge to set.
     */
    public void setSpouseAge(int spouseAge) {
        this.spouseAge = spouseAge;
    }
	/**
     * @param spouseFirstName The spouseFirstName to set.
     */
    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }
	/**
     * @param spouseGender The spouseGender to set.
     */
    public void setSpouseGender(String spouseGender) {
        this.spouseGender = spouseGender;
    }
	/**
     * @param spouseLifeExp The spouseLifeExp to set.
     */
    public void setSpouseLifeExp(double spouseLifeExp) {
        this.spouseLifeExp = spouseLifeExp;
    }
}
