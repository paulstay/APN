package com.estate.constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class InputMaps {

	public static Map<String,String> yesNo = new LinkedHashMap<String,String>();
	
	public static Map<String, String> states = new LinkedHashMap<String, String>();

	public static Map<String, String> gender = new LinkedHashMap<String, String>();

	public static Map<String, String> health = new LinkedHashMap<String, String>();
	
	public static Map<String,String> marriage = new LinkedHashMap<String,String>();
	
	public static Map<String,String> advisorTypes = new LinkedHashMap<String,String>();
	
	public static Map<String,String> residenceTypes = new LinkedHashMap<String,String>();
	
	public static Map<String,String> ownershipTypes = new LinkedHashMap<String,String>();

	public static Map<String,String> titleTypes = new LinkedHashMap<String,String>();

	public static Map<String,String> businessTypes = new LinkedHashMap<String,String>();

	public static Map<String,String> debtTypes = new LinkedHashMap<String,String>();

	public static Map<String,String> notePayTypes = new LinkedHashMap<String,String>();

	public static Map<String,String> noteRecTypes = new LinkedHashMap<String,String>();
	
	public static Map<String,String> depreciationTypes = new LinkedHashMap<String,String>();
	
	public static Map<String,String> freqTypes = new LinkedHashMap<String,String>();
	
	public static Map<String,String> lifeExp = new LinkedHashMap<String,String>();
	
	public static Map<String,String> retSpouse = new LinkedHashMap<String,String>();

	public static Map<String,String> lifeTypes = new LinkedHashMap<String,String>();
	 
	public static Map<String,String> lifeInc = new LinkedHashMap<String,String>(); 

	public static Map<String,String> premiumFreq = new LinkedHashMap<String,String>(); 

	public static Map<String,String> scenarioUse = new LinkedHashMap<String,String>();
	
	public static Map<String,String> years = new LinkedHashMap<String,String>();
	
	public static Map<String,String> vassets = new LinkedHashMap<String,String>();
	
	public static Map<String,String> abTrust = new LinkedHashMap<String,String>();
	
	public static Map<String,String> nTrusts = new LinkedHashMap<String,String>();
	
	public static Map<String,String> taxableIncome = new LinkedHashMap<String,String>();
	
	public static Map<String,String> toolList = new LinkedHashMap<String,String>();
	
	public static Map<String,String> discountEntity = new LinkedHashMap<String,String>();
	
	public static Map<String,String> endBegin = new LinkedHashMap<String,String>();
	
	static {

		for( int i = 2006; i < 2050; i++) {
			years.put(Integer.toString(i),Integer.toString(i)); 
		}
		
		
		nTrusts.put("1","1");
		nTrusts.put("2","2");

		scenarioUse.put("Both", "B");
		scenarioUse.put("Scenario 1 Only","1");
		scenarioUse.put("Scenario 2 Only","2");
		
		lifeInc.put("Inclusive", "Y");
		lifeInc.put("Addition To", "N");
		
		retSpouse.put("Spouse", "S");
		retSpouse.put("Non Spouse", "N");
		
		lifeExp.put("Recalcucate Both","B");
		lifeExp.put("Recalcucate Owner","O");
		lifeExp.put("Recalcucate Beneficiary","F");
		lifeExp.put("Recalcucate Neither","N");
		
		freqTypes.put("Monthly", "12");
		freqTypes.put("Bi-Monthly", "6");
		freqTypes.put("Semi-Annual", "2");
		freqTypes.put("Annual","1");

		yesNo.put("Yes", "Y");
		yesNo.put("No", "N");

		health.put("Non Insurable", "1");
		health.put("Standard", "2");
		health.put("Prefered", "3");
		health.put("Super Preferred", "4");
		health.put("Non Standard", "5");

		states.put("Alabama", "AL");
		states.put("Alaska", "AK");
		states.put("Arizona", "AZ");
		states.put("Arkansas", "AR");
		states.put("California", "CA");
		states.put("Colorado", "CO");
		states.put("Connecticut", "CT");
		states.put("Delaware", "DE");
		states.put("Dist. of Columbia", "DC");
		states.put("Florida", "FL");
		states.put("Georgia", "GA");
		states.put("Guam", "GU");
		states.put("Hawaii", "HI");
		states.put("Idaho", "ID");
		states.put("Illinois", "IL");
		states.put("Indiana", "IN");
		states.put("Iowa", "IA");
		states.put("Kansas", "KS");
		states.put("Kentucky", "KY");
		states.put("Louisiana", "LA");
		states.put("Maine", "ME");
		states.put("Maryland", "MD");
		states.put("Massachusetts", "MA");
		states.put("Michigan", "MI");
		states.put("Minnesota", "MN");
		states.put("Mississippi", "MS");
		states.put("Missouri", "MO");
		states.put("Montana", "MT");
		states.put("Nebraska", "NE");
		states.put("Nevada", "NV");
		states.put("New Hampshire", "NH");
		states.put("New Jersey", "NJ");
		states.put("New Mexico", "NM");
		states.put("New York", "NY");
		states.put("North Carolina", "NC");
		states.put("North Dakota", "ND");
		states.put("Ohio", "OH");
		states.put("Oklahoma", "OK");
		states.put("Oregon", "OR");
		states.put("Pennsylvania", "PA");
		states.put("Puerto Rico", "PR");
		states.put("Rhode Island", "RI");
		states.put("South Carolina", "SC");
		states.put("South Dakota", "SD");
		states.put("Tennessee", "TN");
		states.put("Texas", "TX");
		states.put("Utah", "UT");
		states.put("Vermont", "VT");
		states.put("Virgin Islands", "VI");
		states.put("Virginia", "VA");
		states.put("Washington", "WA");
		states.put("West Virginia", "WV");
		states.put("Wisconsin", "WI");
		states.put("Wyoming", "WY");

		gender.put("MALE", "M");
		gender.put("FEMALE", "F");
		
		marriage.put("Current", "C");
		marriage.put("Previous", "P");

		discountEntity.put("FLP", "F");
		discountEntity.put("LLC", "L");
		
		endBegin.put("End", "0");
		endBegin.put("Begin", "1");
		
		for( AdvisorTypes a : AdvisorTypes.values()) {
			advisorTypes.put(a.description(),Integer.toString(a.id()));
		}
		
		for(ResidenceTypes r : ResidenceTypes.values()) {
			residenceTypes.put(r.description(),r.id());
		}
		
		for(Ownership o : Ownership.values()) {
			ownershipTypes.put(o.description(), Integer.toString(o.id()));
		}
		
		for(Title t : Title.values()) {
			titleTypes.put(t.description(), Integer.toString(t.id()));
		}
		
		for(BusinessTypes b : BusinessTypes.values()) {
			businessTypes.put(b.description(), Integer.toString(b.id()));
		}
		
		for(DebtTypes d : DebtTypes.values()) {
			debtTypes.put(d.description(), Integer.toString(d.id()));
		}
		
		for(NotePayableTypes a : NotePayableTypes.values()){
			notePayTypes.put(a.description(),a.code());
		}
		
		for(NoteReceivableTypes a : NoteReceivableTypes.values()) {
			noteRecTypes.put(a.description(), a.code());
		}
		
		for(DepreciationTypes d : DepreciationTypes.values()) {
			depreciationTypes.put(d.description(),d.code());
		}
		
		for(LifeInsuranceTypes l : LifeInsuranceTypes.values()) {
			lifeTypes.put(l.description(),Integer.toString(l.id()));
		}
		
		for(PremiumFreq p : PremiumFreq.values()) {
			premiumFreq.put(p.description(), Integer.toString(p.id()));
		}
		
		for(VAssetTypes v : VAssetTypes.values()) {
			vassets.put(v.description(), Integer.toString(v.id()));
		}
		
		for(TaxableIncome t : TaxableIncome.values()) {
			taxableIncome.put(t.description(),Integer.toString(t.id()));
		}
		
		for(ToolTableTypes t : ToolTableTypes.values()) {
			toolList.put(t.description(),Integer.toString(t.id()));
		}
		
	}

	static Map<String,String> getAdvisorTypes() {
		return advisorTypes;
	}

	static Map<String,String> getGender() {
		return gender;
	}
	
	static Map<String,String> getStates() {
		return states;
	}

}
