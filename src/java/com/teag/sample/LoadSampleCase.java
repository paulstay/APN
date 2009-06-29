package com.teag.sample;

import java.util.ArrayList;

import com.estate.db.DBObject;
import com.teag.bean.ABTrust;
import com.teag.bean.AdvisorBean;
import com.teag.bean.BondBean;
import com.teag.bean.CashBean;
import com.teag.bean.CashFlowBean;
import com.teag.bean.ChildrenBean;
import com.teag.bean.ClientBean;
import com.teag.bean.DebtBean;
import com.teag.bean.DummySpouse;
import com.teag.bean.FamilyBean;
import com.teag.bean.GiftBean;
import com.teag.bean.HeirBean;
import com.teag.bean.InsuranceBean;
import com.teag.bean.LocationBean;
import com.teag.bean.MarriageBean;
import com.teag.bean.ObjectivesBean;
import com.teag.bean.ObservationBean;
import com.teag.bean.PersonBean;
import com.teag.bean.PhilosophiesBean;
import com.teag.bean.PropertyBean;
import com.teag.bean.RealEstateBean;
import com.teag.bean.RecommendationBean;
import com.teag.bean.RetirementBean;
import com.teag.bean.SecuritiesBean;
import com.teag.bean.VCFBean;
import com.teag.view.ChildrenView;
import com.teag.view.SpouseView;

public class LoadSampleCase {
	public final static int COPY_PLANNER = 39;

	public final static int CLARK_ID = 799;

	public final static int BARBARA_ID = 800;

	DBObject dbObject = new DBObject();

	// new Planner Id
	long plannerId;

	boolean isSingle = false;

	long oldOwnerId; // Clark

	long oldSpouseId; // Barbara

	// New owner Id created from new person
	long ownerId;

	long spouseId;

	// New Person ID for client
	long personId;

	// new marriage Id for client
	long marriageId;

	public LoadSampleCase() {
		super();
	}

	public void abTrust() {
		ABTrust ab = new ABTrust();
		ab.setOwnerId(oldOwnerId);
		ab.query(oldOwnerId);
		ab.setOwnerId(ownerId);
		ab.insert();

		CashFlowBean cfb = new CashFlowBean();
		cfb.setDefault();
		cfb.setOwnerId(oldOwnerId);
		cfb.initialize();
		cfb.setOwnerId(ownerId);
		cfb.insert();
	}

	public void advisors() {
		String w = AdvisorBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		AdvisorBean ab = new AdvisorBean();
		ab.setDbObject();
		ArrayList<AdvisorBean> aList = ab.getBeans(w);
		for (AdvisorBean a : aList) {
			a.setOwnerID(ownerId);
			a.insert();
		}
	}

	// Personal data copy!

	public void assets() {
		cash();
		debt();
		bonds();
		securities();
		retirement();
		realEstate();
		property();
		life();
		gift();
	}

	public void bonds() {
		String w = BondBean.OWNER_ID + "='" + Long.toString(oldOwnerId) + "'";
		BondBean b = new BondBean();
		ArrayList<BondBean> bList = b.getBeans(w);
		for (BondBean c : bList) {
			c.setOwnerId(ownerId);
			c.insert();
		}
	}

	public void cash() {
		String w = CashBean.OWNER_ID + "='" + Long.toString(oldOwnerId) + "'";
		CashBean b = new CashBean();
		ArrayList<CashBean> cList = b.getBeans(w);
		for (CashBean c : cList) {
			c.setOwnerId(ownerId);
			c.insert();
		}
	}

	public void children() {
		ChildrenView cv = new ChildrenView();
		ArrayList<ChildrenView> cList = cv.getView(oldOwnerId);
		for (ChildrenView c : cList) {
			PersonBean child = new PersonBean();
			child.setId(c.getId());
			child.initialize(); // Get all of the details
			child.insert(); // Create a new Record clears out the old ID
			// need to set up family for client and spouse marriage, for this child.
			FamilyBean f = new FamilyBean();
			f.setId(child.getId());
			f.setMId(marriageId);
			f.insert();
			// Get the number of grand children and update the number of grand children
			ChildrenBean cb = new ChildrenBean();
			cb.setOwnerId(c.getId());
			cb.initialize();
			cb.setOwnerId(child.getId());
			cb.insert();
			
			long cs = c.getSpouseId();
			if (cs > 0) {
				PersonBean spouse = new PersonBean();
				spouse.setId(cs);
				spouse.initialize();
				spouse.insert();
				cs = spouse.getId(); // This is the new ID from the above
										// insert
				MarriageBean m = new MarriageBean();
				m.setCity("BELCAMP");
				m.setState("MD");
				m.setDate("9/21/1984");
				m.setPersonId(child.getId());
				m.setSpouseId(cs);
				m.setHusband(c.getFirstName());
				m.setWife(spouse.getFirstName());
				m.setStatus("C");
				m.insert();
			}
		}
	}

	public void client() {
		ClientBean c = new ClientBean();
		c.setDbObject();
		c.setPrimaryId(ownerId);
		c.setPlannerId(plannerId);
		c.insert();
	}

	public void copyData(long plannerId, long oldOwnerId, long oldSpouseId, boolean isSingle) {
		this.plannerId = plannerId; // New id for planner
		this.oldOwnerId = oldOwnerId;
		this.oldSpouseId = oldSpouseId;
		this.isSingle = isSingle;
		// Copy personal data
		person();
		client();
		personalData();
		assets();
	}

	public void debt() {
		String w = DebtBean.OWNER_ID + "='" + Long.toString(oldOwnerId) + "'";
		DebtBean b = new DebtBean();
		ArrayList<DebtBean> cList = b.getBeans(w);
		for (DebtBean c : cList) {
			c.setOwnerId(ownerId);
			c.insert();
		}
	}

	public void dummySpouse() {
		DummySpouse ds = new DummySpouse();
		ds.addSpouseRecord(ownerId);
		marriageId = ds.getMarriageId();
	}

	public DBObject getDbObject() {
		return dbObject;
	}

	public void gift() {
		String w = GiftBean.OWNER_ID + "='" + Long.toString(oldOwnerId) + "'";
		GiftBean gb = new GiftBean();
		ArrayList<GiftBean> gList = gb.getBeans(w);
		for (GiftBean g : gList) {
			g.setOwnerId(ownerId);
			g.insert();
		}
	}

	public void heirs() {
		HeirBean h = new HeirBean();
		h.setOwnerId(oldOwnerId);
		h.initialize();
		h.setOwnerId(ownerId);
		h.insert();
	}

	public void life() {
		String w = InsuranceBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		InsuranceBean ib = new InsuranceBean();
		ArrayList<InsuranceBean> iList = ib.getBeans(w);
		for (InsuranceBean i : iList) {
			i.setOwnerId(ownerId);
			i.insert();
		}
	}

	public void objectives() {
		String w = ObjectivesBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		ObjectivesBean ob = new ObjectivesBean();
		ob.setDbObject();
		ArrayList<ObjectivesBean> oList = ob.getBeans(w);
		for (ObjectivesBean o : oList) {
			o.setOwnerId(ownerId);
			o.insert();
		}
	}

	public void observation() {
		String w = ObservationBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		ObservationBean ob = new ObservationBean();
		ob.setDbObject();
		ArrayList<ObservationBean> oList = ob.getBeans(w);
		for (ObservationBean o : oList) {
			o.setOwnerId(ownerId);
			o.insert();
		}
	}

	public void person() {
		PersonBean person = new PersonBean();
		person.setId(oldOwnerId);
		person.setDbObject();
		person.initialize();

		// just call insert and we have a new person record!
		person.insert();
		ownerId = person.getId(); // This is the new ID for the owner or
									// client

		if (!isSingle) {
			PersonBean spouse = new PersonBean();
			spouse.setId(oldSpouseId);
			spouse.setDbObject();
			spouse.initialize();
			spouse.insert();

			spouseId = spouse.getId();

			SpouseView sv = new SpouseView();
			ArrayList<SpouseView> sList = sv.getView(oldOwnerId);
			long mid = 0;
			for (SpouseView s : sList) {
				if (s.getStatus().equalsIgnoreCase("C")) {
					mid = s.getMarriageId();
				}
			}

			MarriageBean mb = new MarriageBean();
			mb.setId(mid);
			mb.setDbObject();
			mb.initialize();
			mb.setPersonId(ownerId);
			mb.setSpouseId(spouseId);
			mb.insert();
			marriageId = mb.getId();
		} else {
			dummySpouse();
			
		}
	}

	public void personalData() {
		residence();
		advisors();
		children();
		heirs();
		abTrust();
		variables();
		objectives();
		observation();
		recommendations();
		philosophy();
	}

	public void philosophy() {
		String w = PhilosophiesBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		PhilosophiesBean ob = new PhilosophiesBean();
		ob.setDbObject();
		ArrayList<PhilosophiesBean> oList = ob.getBeans(w);
		for (PhilosophiesBean o : oList) {
			o.setOwnerId(ownerId);
			o.insert();
		}
	}

	public void property() {
		String w = PropertyBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		PropertyBean pb = new PropertyBean();
		ArrayList<PropertyBean> pList = pb.getBeans(w);
		for (PropertyBean p : pList) {
			p.setOwnerId(ownerId);
			p.insert();
		}
	}

	public void realEstate() {
		String w = RealEstateBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		RealEstateBean rb = new RealEstateBean();
		ArrayList<RealEstateBean> rList = rb.getBeans(w);
		for (RealEstateBean r : rList) {
			r.setOwnerId(ownerId);
			r.insert();
		}
	}

	public void recommendations() {
		String w = RecommendationBean.OWNER_ID + "='"
				+ Long.toString(oldOwnerId) + "'";
		RecommendationBean ob = new RecommendationBean();
		ob.setDbObject();
		ArrayList<RecommendationBean> oList = ob.getBeans(w);
		for (RecommendationBean o : oList) {
			o.setOwnerId(ownerId);
			o.insert();
		}
	}

	public void residence() {
		String w = LocationBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		LocationBean lb = new LocationBean();
		lb.setDbObject();
		ArrayList<LocationBean> locList = lb.getBeans(w);
		for (LocationBean l : locList) {
			l.setOwnerId(ownerId);
			l.insert();
		}
	}

	public void retirement() {
		String w = RetirementBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		RetirementBean rb = new RetirementBean();
		ArrayList<RetirementBean> rList = rb.getBeans(w);
		for (RetirementBean r : rList) {
			r.setOwnerId(ownerId);
			r.insert();
		}
	}

	public void securities() {
		String w = SecuritiesBean.OWNER_ID + "='" + Long.toString(oldOwnerId)
				+ "'";
		SecuritiesBean s = new SecuritiesBean();
		ArrayList<SecuritiesBean> sList = s.getBeans(w);
		for (SecuritiesBean i : sList) {
			i.setOwnerId(ownerId);
			i.insert();
		}
	}

	public void variables() {
		String w = VCFBean.OWNER_ID + "='" + Long.toString(oldOwnerId) + "'";
		VCFBean v = new VCFBean();
		v.setDbObject();
		ArrayList<VCFBean> vList = v.getBeans(w);
		for (VCFBean b : vList) {
			b.setOwner_id(ownerId);
			b.insert();
		}
	}
	
	
}
