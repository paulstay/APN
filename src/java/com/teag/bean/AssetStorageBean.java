/*
 * Created on Mar 25, 2005
 *
 */
package com.teag.bean;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * @author Paul Stay
 * Description, This class is used to store persistant data for collecting assets during the
 * Estate Planning Tool Creation process. It keeps track of the assets that have been added to the
 * tool! 
 */
public class AssetStorageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4140565685454608015L;
	protected Vector<String> assets = new Vector<String>(40);
	
	public AssetStorageBean() {
		
	}
	
	public void addAsset(String itemId) {
		assets.add(itemId);
	}
	
	public void clearAssets() {
		assets.clear();
	}
	
	public Enumeration<String> getEnumeration() {
		return assets.elements();
	}
	
	public String getList() {
		return assets.toString();
	}

	public boolean hasAsset(String itemId) {
		boolean exists = false;
		Enumeration<String> e = getEnumeration();
		while( e.hasMoreElements()) {
			String str = e.nextElement();
			if( str.equals(itemId)) {
				exists = true;
				return exists;
			}
		}
		return exists;
	}
	
	public void removeAsset(String itemId) {
		assets.remove(itemId);
	}
}
