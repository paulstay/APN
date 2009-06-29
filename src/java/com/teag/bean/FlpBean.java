/*
 * Created on Apr 11, 2005
 *
 */
package com.teag.bean;

/**
 * @author Paul Stay
 *
 */
import com.teag.estate.FlpTool;
import com.teag.estate.ToolAssetDistribution;

public class FlpBean extends AssetSqlBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4839346076702949693L;
	private long id;
	private ToolAssetDistribution assets;
	
	// use the tool definition here....
	FlpTool flpTool = new FlpTool();
	
	@Override
	public  double getAssetBasis() {
		return 0;
	}
	
	@Override
	public  double getAssetGrowth() {
		return assets.getWeightedGrowth();
	}
	@Override
	public  double getAssetIncome() {
		return assets.getWeightedIncome();
	}
	@Override
	public  double getAssetLiability(){
		return 0;
	}

	@Override
	public  String getAssetName() {
		return assets.getAssetNames();
	}
	
	/**
	 * @return Returns the assets.
	 */
	public ToolAssetDistribution getAssets() {
		return assets;
	}
	@Override
	public  double getAssetValue() {
		return assets.getTotalValue();
	}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return getAssetName();
	}
	
	
	/**
	 * @return Returns the discountRate.
	 */
	public double getDiscountRate() {
		return flpTool.getDiscountRate();
	}
	
	/**
	 * @return Returns the generalPartner.
	 */
	public double getGeneralPartner() {
		return flpTool.getGeneralPartnerShares();
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Returns the limitedPartner.
	 */
	public double getLimitedPartner() {
		return flpTool.getLimitedPartnerShares();
	}

	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return flpTool.getOwnerId();
	}

	/**
	 * @return Returns the premiumGpValue.
	 */
	public double getPremiumGpValue() {
		return flpTool.getPremiumGpValue();
	}

	/**
	 * @return Returns the value.
	 */
	public double getValue() {
		return assets.getTotalValue();
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#initialize()
	 */
	@Override
	public void initialize() {
		flpTool.setDbObject();
		flpTool.setId(id);
		flpTool.read();
		flpTool.calculate();
		assets = flpTool.getAssets();
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		flpTool.setDbObject();
		flpTool.insert();
	}

	/**
	 * @param assets The assets to set.
	 */
	public void setAssets(ToolAssetDistribution assets) {
		this.assets = assets;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#update()
	 */
	@Override
	public void update() {
		flpTool.setId(id);
		flpTool.setDbObject();
		flpTool.update();
	}
}
