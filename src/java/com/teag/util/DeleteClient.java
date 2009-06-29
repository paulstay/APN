package com.teag.util;

import java.util.ArrayList;

import com.estate.constants.AssetDatabase;
import com.estate.db.DBObject;
import com.teag.asset.Asset;
import com.teag.asset.AssetList;
import com.teag.bean.AdvisorBean;
import com.teag.bean.ClientBean;
import com.teag.bean.EstatePlanningToolBean;
import com.teag.bean.LocationBean;
import com.teag.bean.ObjectivesBean;
import com.teag.bean.ObservationBean;
import com.teag.bean.PhilosophiesBean;
import com.teag.bean.RecommendationBean;
import com.teag.bean.ScenarioBean;
import com.teag.view.ChildrenView;
import com.teag.view.SpouseView;

public class DeleteClient {
	DBObject dbObject = new DBObject();
	long ownerId;
	long clientId;
	
	
	public void deleteAdvisors() {
		String wClause = AdvisorBean.OWNER_ID + "='" + ownerId + "'";
		AdvisorBean ab = new AdvisorBean();
		ab.setDbObject();
		ArrayList<AdvisorBean> aList = ab.getBeans(wClause);
		for(AdvisorBean a : aList) {
			a.delete();
		}
	}
	
	public void deleteAssets() {
		AssetList assetList = new AssetList();
		ArrayList<Asset> list = assetList.listAssets();
		dbObject.start();
		for(Asset a : list) {
			AssetDatabase ad = a.getAsset();
			String sql = "Delete from " + ad.tableName() + " where OWNER_ID='"
				+ Long.toString(a.getId()) + "'";
			dbObject.executeStatement(sql);
		}
		dbObject.stop();
	}
	
	public void deleteChild() {
		ChildrenView cv = new ChildrenView();
		ArrayList<ChildrenView> cList = cv.getView(ownerId);
		for(ChildrenView c : cList) {
			dbObject.start();
			// Delete Child spouse
			dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=0");
			if( c.getSpouseId() > 0) {
				dbObject.delete("MARRIAGE", "PRIMARY_ID='" + c.getSpouseId() + "'");
				dbObject.delete("PERSON", "PERSON_ID='" + c.getSpouseId() + "'");
			}
			
			// Delete family and children record.
			dbObject.delete("FAMILY", "PERSON_ID='" + c.getId() + "'");
			dbObject.delete("MARRIAGE", "PRIMARY_ID='" + c.getId() + "'");
			dbObject.delete("PERSON", "PERSON_ID='" + c.getId() + "'");
			dbObject.delete("CHILDREN", "OWNER_ID='" + c.getId() + "'");
			dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=1");
			dbObject.stop();
		}
	}
	
	public void deleteDoc() {
		String wClause = ObjectivesBean.OWNER_ID + "='" + ownerId + "'";
		ObjectivesBean b = new ObjectivesBean();
		b.setDbObject();
		ArrayList<ObjectivesBean> bList = b.getBeans(wClause);
		for( ObjectivesBean bean : bList) {
			bean.delete();
		}
		
		ObservationBean ob = new ObservationBean();
		ob.setDbObject();
		ArrayList<ObservationBean> obList = ob.getBeans(wClause);
		for(ObservationBean bean2 : obList ) {
			bean2.delete();
		}
		
		RecommendationBean rb = new RecommendationBean();
		rb.setDbObject();
		ArrayList<RecommendationBean> rList = rb.getBeans(wClause);
		for(RecommendationBean bean3 : rList) {
			bean3.delete();
		}
		
		PhilosophiesBean phB = new PhilosophiesBean();
		phB.setDbObject();
		ArrayList<PhilosophiesBean> phList = phB.getBeans(wClause);
		for(PhilosophiesBean bean4 : phList) {
			bean4.delete();
		}
	}
	
	public void deletePerson() {
		ClientBean cb = new ClientBean();
		cb.setDbObject();
		cb.setClientId(clientId);
		cb.delete();
		dbObject.start();
		dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=0");
		dbObject.delete("PERSON", "PERSON_ID='" + ownerId + "'");
		dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=1");
		dbObject.stop();
	}
	
	public void deleteRecords() {
		if(ownerId > 0L && clientId > 0L) {
			deleteTools();
			deleteAssets();
			deleteDoc();
			deleteAdvisors();
			deleteRes();
			deleteChild();
			deleteSpouse();
			deletePerson();
		}
	}
	
	// Delete Residences
	public void deleteRes() {
		String wClause = LocationBean.OWNER_ID + "='" + ownerId + "'";
		LocationBean ab = new LocationBean();
		ab.setDbObject();
		ArrayList<LocationBean> aList = ab.getBeans(wClause);
		for(LocationBean a : aList) {
			a.delete();
		}
	}
	
	public void deleteSpouse() {
		SpouseView sv = new SpouseView();
		ArrayList<SpouseView> sList = sv.getView(ownerId);
		for(SpouseView s : sList) {
			dbObject.start();
			dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=0");
			dbObject.delete("MARRIAGE", "MARRIAGE_ID='"+ s.getMarriageId() + "'");
			dbObject.delete("FAMILY", "PERSON_ID='" + s.getId() + "'");
			dbObject.delete("PERSON", "PERSON_ID='" + s.getId() + "'");
			dbObject.executeStatement("SET FOREIGN_KEY_CHECKS=1");
			dbObject.stop();
		}
	}
	
	public void deleteTools(){
		if( ownerId > 0) {
			ScenarioBean sb = new ScenarioBean();
			sb.setDbObject();
			sb.setId(ownerId);
			ArrayList<ScenarioBean> sList = sb.getBeans(ScenarioBean.OWNER_ID + "='" + ownerId + "'");
			for(ScenarioBean s : sList) {
				long scenarioId = s.getId();
				EstatePlanningToolBean estatePlanningTool = new EstatePlanningToolBean();
				estatePlanningTool.setDbObject();
				estatePlanningTool.setScenarioId(scenarioId);
				estatePlanningTool.deleteTools(scenarioId);
				s.delete();
			}
		}
	}
	
	public long getClientId() {
		return clientId;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
}
