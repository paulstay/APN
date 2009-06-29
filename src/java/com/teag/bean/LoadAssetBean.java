package com.teag.bean;


public class LoadAssetBean {

	// If we add assets (such as FLP) we will need to update this list!
	public static final int BONDS = 1;
	public static final int BUSINESS = 2;
	public static final int CASH = 3;
	public static final int DEBT = 4;
	public static final int ILLIQUID = 5;
	public static final int RETIREMENT = 6;
	public static final int SECURITIES = 7;
	public static final int REALESTATE = 8;
	public static final int PERSONALPROPERTY = 9;
	public static final int LIFEINSURANCE = 10;
	public static final int GIFTING = 11;
	
	public static final int FLP_GP = 12;
	public static final int FLP_LP = 13;
	public static final int LLC_MS = 14;
	public static final int LLC_MMS = 15;
	
	public static final int FLP = 20;
	public static final int LLC = 21;
	
	public static final int NOTES = 16;
	public static final int NOTES_PAYABLE = 17;

	private AssetSqlBean bondBean( long id) {
		BondBean bondBean = new BondBean();
		bondBean.setId(id);
		bondBean.initialize();
		bondBean.beanType = BONDS;
		return bondBean;
	}
	
	private AssetSqlBean businessBean( long id) {
		BusinessBean businessBean = new BusinessBean();
		businessBean.setId(id);
		businessBean.initialize();
		businessBean.beanType = BUSINESS;
		return businessBean;
	}

	private AssetSqlBean cashBean( long id) {
		CashBean cashBean = new CashBean();
		cashBean.setId(id);
		cashBean.initialize();
		cashBean.beanType = CASH;
		return cashBean;
	}

	private AssetSqlBean debtBean( long id) {
		DebtBean debt = new DebtBean();
		debt.setId(id);
		debt.initialize();
		debt.beanType = DEBT;
		return debt;
	}
	private AssetSqlBean flpGpBean( long id) {
		FlpGPBean flpGPBean = new FlpGPBean();
		flpGPBean.setId(id);
		flpGPBean.initialize();
		flpGPBean.beanType = LoadAssetBean.FLP_GP;
		return flpGPBean;
	}
	
	private AssetSqlBean flpLpBean( long id) {
		FlpLPBean flpLPBean = new FlpLPBean();
		flpLPBean.setId(id);
		flpLPBean.initialize();
		flpLPBean.beanType = LoadAssetBean.FLP_LP;
		return  flpLPBean;
	}
	
	private AssetSqlBean illiquidBean( long id) {
		IlliquidBean illiquidBean = new IlliquidBean();
		illiquidBean.setId(id);
		illiquidBean.initialize();
		illiquidBean.beanType = ILLIQUID;
		return illiquidBean;
	}

	private AssetSqlBean lifeBean( long id) {
		InsuranceBean lifeBean = new InsuranceBean();
		lifeBean.setId(id);
		lifeBean.initialize();
		lifeBean.beanType = LoadAssetBean.LIFEINSURANCE;
		return lifeBean;
	}

	private AssetSqlBean llcGpBean( long id) {
		LlcGPBean llcGpBean = new LlcGPBean();
		llcGpBean.setId(id);
		llcGpBean.initialize();
		llcGpBean.beanType = LoadAssetBean.LLC_MS;
		return  llcGpBean;
	}

	private AssetSqlBean llcLpBean( long id) {
		LlcLPBean llcLPBean = new LlcLPBean();
		llcLPBean.setId(id);
		llcLPBean.initialize();
		llcLPBean.beanType = LoadAssetBean.LLC_MMS;
		return  llcLPBean;
	}

	public AssetSqlBean loadAssetBean(EPTAssetBean eab) {
		long id = eab.getAssetId(); // This is the id from the database asset object.
		int tableId = (int) eab.getAssetType(); // Asset Type need to get actual table name from database!

		AssetSqlBean asb = null;
		
		switch (tableId) {
			case BONDS:
				asb = bondBean(id);
				break;
			case BUSINESS:
				asb = businessBean(id);
				break;
			case CASH:
				asb = cashBean(id);
				break;
			case DEBT:
				asb = debtBean(id);
				break;
			case ILLIQUID:
				asb = illiquidBean(id);
				break;
			case RETIREMENT:
				asb = retirementBean(id);
				break;
			case SECURITIES:
				asb = securitiesBean(id);
				break;
			case REALESTATE:
				asb = realEstateBean(id);
				break;
			case PERSONALPROPERTY:
				asb = propertyBean(id);
				break;
			case LIFEINSURANCE:
				asb = lifeBean(id);
				break;
			case FLP_LP:
				asb = flpLpBean(id);
				break;
			case FLP_GP:
				asb = flpGpBean(id);
				break;
			case LLC_MS:
				asb = llcGpBean(id);
				break;
			case LLC_MMS:
				asb = llcLpBean(id);
				break;
			case NOTES:
				asb = notesBean(id);
				break;
			case NOTES_PAYABLE:
				asb = notesPayableBean( id);
				break;
		}
		return asb;
	}
	
	/*
	private AssetSqlBean flpBean( long id) {
		FlpBean flpBean = new FlpBean();
		flpBean.setDbObject(dbObj);
		flpBean.setId(id);
		flpBean.initialize();
		flpBean.beanType = this.FLP;
		return (AssetSqlBean) flpBean;
	}
	*/

	private AssetSqlBean notesBean( long id) {
		NotesBean notesBean = new NotesBean();
		notesBean.setId(id);
		notesBean.initialize();
		notesBean.beanType = LoadAssetBean.NOTES;
		return notesBean;
	}
	
	private AssetSqlBean notesPayableBean( long id) {
		NotePayableBean npb = new NotePayableBean();
		npb.setId(id);
		npb.initialize();
		npb.beanType = LoadAssetBean.NOTES_PAYABLE;
		return npb;
	}
	
	private AssetSqlBean propertyBean(long id) {
		PropertyBean propertyBean = new PropertyBean();
		propertyBean.setId(id);
		propertyBean.initialize();
		propertyBean.beanType = PERSONALPROPERTY;
		return propertyBean;
	}
	
	private AssetSqlBean realEstateBean(long id) {
		RealEstateBean realEstateBean = new RealEstateBean();
		realEstateBean.setId(id);
		realEstateBean.initialize();
		realEstateBean.beanType = REALESTATE;
		return realEstateBean;
	}
	
	private AssetSqlBean retirementBean(long id) {
		RetirementBean retirementBean = new RetirementBean();
		retirementBean.setId(id);
		retirementBean.initialize();
		retirementBean.beanType = LoadAssetBean.RETIREMENT;
		return retirementBean;
	}
	
	private AssetSqlBean securitiesBean(long id) {
		SecuritiesBean securitiesBean = new SecuritiesBean();
		securitiesBean.setId(id);
		securitiesBean.initialize();
		securitiesBean.beanType = LoadAssetBean.SECURITIES;
		return securitiesBean;
	}
}
