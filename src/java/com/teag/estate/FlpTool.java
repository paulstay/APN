package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.FlpGPBean;
import com.teag.bean.FlpLPBean;

/**
 * @author stay
 * Description - Famliy Limited Partnership Tool, keeps track of the assets and discounts!.
 */
public class FlpTool extends EstatePlanningTool {

	private String uuid;
	private long id;
	private double generalPartnerShares;
	private double limitedPartnerShares;
	private double discountRate;
	private double premiumGpValue;
	private long ownerId;
	private double value = -1;
	private double growth;
	private double income;
	private String name;
	
	private String toolType;
	
	private ToolAssetDistribution assets;
	private ToolAssetData flipAssets[];
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#calculate()
	 */
	@Override
	public void calculate() {
		assets = null;
		assets = new ToolAssetDistribution();
		assets.setDbObject();
		assets.setToolId(id);
		assets.setToolTableId(getToolTableId());
		assets.calculate();
		value = assets.getTotalValue();
		growth = assets.getWeightedGrowth();
		income = assets.getWeightedIncome();
		flipAssets = assets.getToolAssetData();
	}
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
		//Delete any lp and gp assets associated with this flp
		FlpLPBean lpBean = new FlpLPBean();
		lpBean.delete(id);
		lpBean = null;
		
		FlpGPBean gpBean = new FlpGPBean();
		gpBean.delete(id);
		gpBean = null;
		
		dbObj.start();
		dbObj.delete("FLP_TOOL", "ID='" + id + "'");
		dbObj.stop();
	}

	/**
	 * @return Returns the assets.
	 */
	public ToolAssetDistribution getAssets() {
		return assets;
	}

	/**
	 * @return Returns the discountRate.
	 */
	public double getDiscountRate() {
		return discountRate;
	}

	public ToolAssetData[] getFlipAssets() {
		return flipAssets;
	}

	/**
	 * @return Returns the generalPartnerShares.
	 */
	public double getGeneralPartnerShares() {
		return generalPartnerShares;
	}

	public double getGrowth() {
		return growth;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	
	public double getIncome() {
		return income;
	}
	/**
	 * @return Returns the limitedPartnerShares.
	 */
	public double getLimitedPartnerShares() {
		return limitedPartnerShares;
	}
	public String getName() {
		return name;
	}
	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @return Returns the premiumGpValue.
	 */
	public double getPremiumGpValue() {
		return premiumGpValue;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#getToolTableId()
	 */
	@Override
	public long getToolTableId() {

		return ToolTableTypes.FLP.id();
	}
	/**
	 * @return Returns the toolType.
	 */
	public String getToolType() {
		return toolType;
	}
	public String getUuid() {
		return uuid;
	}
	public double getValue() {
		return value;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		calculate();
		dbObj.start();
		dbObj.setTable("FLP_TOOL");
		dbObj.clearFields();
		
		dbAddField("GENERAL_PARTNER", getGeneralPartnerShares() );
		dbAddField("LIMITED_PARTNER", getLimitedPartnerShares());
		dbAddField("DISCOUNT_RATE", getDiscountRate());
		dbAddField("PREMIUM_GP_VALUE", getPremiumGpValue());
		dbAddField("OWNER_ID", getOwnerId());
		dbAddField("DESCRIPTION", getName());
		
		int err = dbObj.insert();
		if( err == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> rec = dbObj.execute("select ID from FLP_TOOL where UUID='" + uuid + "'");
			Object o = rec.get("ID");
			if( o != null ) 
				id= Integer.parseInt(o.toString());
		}

		if( value < 0)
			calculate();
		
		FlpLPBean lpBean = new FlpLPBean();
		lpBean.setFlpToolId(id);
		lpBean.setValue(getLimitedPartnerShares() * value);
		lpBean.setOwnerId(getOwnerId());
		lpBean.setAssetName(getName() + " - LP");
		lpBean.insert();
		lpBean = null;
		
		FlpGPBean gpBean = new FlpGPBean();
		gpBean.setFlpToolId(id);
		gpBean.setValue(getGeneralPartnerShares() * value);
		gpBean.setOwnerId(getOwnerId());
		gpBean.setAssetName(getName() + " - GP");
		gpBean.insert();
		gpBean = null;
		
		dbObj.stop();
	}

	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#read()
	 */
	@Override
	public void read() {
		if( id > 0L) {
			dbObj.start();
			dbObj.setTable("FLP_TOOL");
			HashMap<String,Object> account;
			String sql = "Select * from FLP_TOOL where ID='" + id + "'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if( account != null) {
				setGeneralPartnerShares(getDouble(account, "GENERAL_PARTNER"));
				setLimitedPartnerShares(getDouble(account, "LIMITED_PARTNER"));
				setDiscountRate(getDouble(account, "DISCOUNT_RATE"));
				setPremiumGpValue(getDouble(account,"PREMIUM_GP_VALUE"));
				setName((String)account.get("DESCRIPTION"));
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#report()
	 */
	@Override
	public void report() {

	}

	/**
	 * @param discountRate The discountRate to set.
	 */
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public void setFlipAssets(ToolAssetData[] flipAssets) {
		this.flipAssets = flipAssets;
	}
	/**
	 * @param generalPartnerShares The generalPartnerShares to set.
	 */
	public void setGeneralPartnerShares(double generalPartnerShares) {
		this.generalPartnerShares = generalPartnerShares;
	}
	public void setGrowth(double growth) {
		this.growth = growth;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	public void setIncome(double income) {
		this.income = income;
	}
	/**
	 * @param limitedPartnerShares The limitedPartnerShares to set.
	 */
	public void setLimitedPartnerShares(double limitedPartnerShares) {
		this.limitedPartnerShares = limitedPartnerShares;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @param premiumGpValue The premiumGpValue to set.
	 */
	public void setPremiumGpValue(double premiumGpValue) {
		this.premiumGpValue = premiumGpValue;
	}
	/**
	 * @param toolType The toolType to set.
	 */
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	/**
	 * @param uuid The uuid to set.
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setValue(double value) {
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		calculate();	// Just in case....
		dbObj.start();
		dbObj.setTable("FLP_TOOL");
		dbObj.clearFields();
		
		dbAddField("GENERAL_PARTNER", getGeneralPartnerShares() );
		dbAddField("LIMITED_PARTNER", getLimitedPartnerShares());
		dbAddField("DISCOUNT_RATE", getDiscountRate());
		dbAddField("PREMIUM_GP_VALUE", getPremiumGpValue());
		dbAddField("DESCRIPTION", getName());

		dbObj.setWhere("ID='" + id + "'");
		dbObj.update();
		dbObj.stop();
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#writeupText()
	 */
	@Override
	public String writeupText() {

		return null;
	}
}
