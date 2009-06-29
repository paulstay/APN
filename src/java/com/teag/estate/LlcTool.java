package com.teag.estate;

import java.util.HashMap;

import com.estate.constants.ToolTableTypes;
import com.teag.bean.LlcGPBean;
import com.teag.bean.LlcLPBean;

/**
 * @author stay
 * Created on Apr 8, 2005
 * Description - Limited Liability Corporation tool calculations
 * 
 */
public class LlcTool extends EstatePlanningTool {
	private String uuid;
	private long id;
	private double managerShares;
	private double memberShares;
	private double discountRate;
	private double premiumGpValue;
	private String toolType;
	private long ownerId;
	private double growth;
	private double income;
	private double value;
	private String name;

	private ToolAssetDistribution assets;
	private ToolAssetData llcAssets[];
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
		llcAssets = assets.getToolAssetData();
	}
	
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#delete()
	 */
	@Override
	public void delete() {
//		Delete any lp and gp assets associated with this flp
		LlcLPBean lpBean = new LlcLPBean();
		lpBean.delete(id);
		lpBean = null;
		
		LlcGPBean gpBean = new LlcGPBean();
		gpBean.delete(id);
		gpBean = null;
		
		dbObj.start();
		dbObj.delete("LLC_TOOL", "ID='" + id + "'");
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

	/**
	 * @return Returns the growth.
	 */
	public double getGrowth() {
		return growth;
	}

	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Returns the income.
	 */
	public double getIncome() {
		return income;
	}

	/**
	 * @return Returns the llcAssets.
	 */
	public ToolAssetData[] getLlcAssets() {
		return llcAssets;
	}
	
	/**
	 * @return Returns the managerShares.
	 */
	public double getManagerShares() {
		return managerShares;
	}
	/**
	 * @return Returns the memberShares.
	 */
	public double getMemberShares() {
		return memberShares;
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

		return ToolTableTypes.LLC.id();
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
	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return value;
	}
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#insert()
	 */
	@Override
	public void insert() {
		calculate();	// We need to do this just in case we don't click on the summary page first.
		dbObj.start();
		dbObj.setTable("LLC_TOOL");
		dbObj.clearFields();
		
		dbAddField("MANAGER_SHARE", getManagerShares() );
		dbAddField("MEMBER_SHARE", getMemberShares());
		dbAddField("DISCOUNT_RATE", getDiscountRate());
		dbAddField("PREMIUM_MANAGER_SHARE_VALUE", getPremiumGpValue());
		dbAddField("OWNER_ID", getOwnerId());
		dbAddField("VALUE", value);
		dbAddField("DESCRIPTION", getName());
		
		int err = dbObj.insert();
		if( err == 0) {
			uuid = dbObj.getUUID();
			HashMap<String,Object> rec = dbObj.execute("select ID from LLC_TOOL where UUID='" + uuid + "'");
			Object o = rec.get("ID");
			if( o != null ) 
				id= Integer.parseInt(o.toString());
		}
		if( value < 0)
			calculate();
		
		LlcLPBean lpBean = new LlcLPBean();
		lpBean.setLlcToolId(id);
		lpBean.setValue(getMemberShares() * value);
		lpBean.setOwnerId(getOwnerId());
		lpBean.setAssetName(getName() + " - MI");
		lpBean.insert();
		lpBean = null;
		
		LlcGPBean gpBean = new LlcGPBean();
		gpBean.setLlcToolId(id);
		gpBean.setValue(this.getManagerShares() * value);
		gpBean.setOwnerId(getOwnerId());
		gpBean.setAssetName(getName() + " - MMI");
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
			dbObj.setTable("LLC_TOOL");
			HashMap<String,Object> account;
			String sql = "Select * from LLC_TOOL where ID='" + id + "'";
			account = dbObj.execute(sql);
			dbObj.stop();
			
			if( account != null) {
				setManagerShares(getDouble(account, "MANAGER_SHARE"));
				setMemberShares(getDouble(account, "MEMBER_SHARE"));
				setDiscountRate(getDouble(account, "DISCOUNT_RATE"));
				setPremiumGpValue(getDouble(account,"PREMIUM_MANAGER_SHARE_VALUE"));
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
	 * @param assets The assets to set.
	 */
	public void setAssets(ToolAssetDistribution assets) {
		this.assets = assets;
	}
	/**
	 * @param discountRate The discountRate to set.
	 */
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	/**
	 * @param growth The growth to set.
	 */
	public void setGrowth(double growth) {
		this.growth = growth;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @param income The income to set.
	 */
	public void setIncome(double income) {
		this.income = income;
	}
	/**
	 * @param llcAssets The llcAssets to set.
	 */
	public void setLlcAssets(ToolAssetData[] llcAssets) {
		this.llcAssets = llcAssets;
	}
	/**
	 * @param managerShares The managerShares to set.
	 */
	public void setManagerShares(double managerShares) {
		this.managerShares = managerShares;
	}
	/**
	 * @param memberShares The memberShares to set.
	 */
	public void setMemberShares(double memberShares) {
		this.memberShares = memberShares;
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
	/* (non-Javadoc)
	 * @see com.teag.estate.EstatePlanningTool#update()
	 */
	@Override
	public void update() {
		calculate();
		dbObj.start();
		dbObj.setTable("LLC_TOOL");
		dbObj.clearFields();
		
		dbAddField("MANAGER_SHARE", getManagerShares() );
		dbAddField("MEMBER_SHARE", getMemberShares());
		dbAddField("DISCOUNT_RATE", getDiscountRate());
		dbAddField("PREMIUM_MANAGER_SHARE_VALUE", getPremiumGpValue());
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
