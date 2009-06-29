package com.teag.bean;

import com.teag.estate.LlcTool;
import com.teag.estate.ToolAssetDistribution;

/**
 * @author stay
 * Created on May 26, 2005
 *
 */
public class LlcBean  extends AssetSqlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4414937647047656285L;
	private long id;
	private ToolAssetDistribution assets;
	
	// use the tool definition here....
	LlcTool llcTool = new LlcTool();
	
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
		return llcTool.getDiscountRate();
	}
	
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @return Returns the generalPartner.
	 */
	public double getManagerShares() {
		return llcTool.getManagerShares();
	}

	/**
	 * @return Returns the limitedPartner.
	 */
	public double getMemberShares() {
		return llcTool.getMemberShares();
	}

	/**
	 * @return Returns the ownerId.
	 */
	public long getOwnerId() {
		return llcTool.getOwnerId();
	}

	/**
	 * @return Returns the premiumGpValue.
	 */
	public double getPremiumGpValue() {
		return llcTool.getPremiumGpValue();
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
		llcTool.setDbObject();
		llcTool.setId(id);
		llcTool.read();
		llcTool.calculate();
		assets = llcTool.getAssets();
	}
	/* (non-Javadoc)
	 * @see com.teag.bean.SqlBean#insert()
	 */
	@Override
	public void insert() {
		llcTool.setDbObject();
		llcTool.insert();
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
		llcTool.setId(id);
		llcTool.setDbObject();
		llcTool.update();
	}
}
