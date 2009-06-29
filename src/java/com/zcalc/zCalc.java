/*
 * Created on Dec 16, 2004
 *
 */
package com.zcalc;

/**
 * @author Paul Stay
 *
 */
public class zCalc {

	static {
		System.loadLibrary("zCalcJNI");
	}

	public static void main(String[] args) {
		zCalc zc = new zCalc();
        double d;
        zc.StartUp();
        System.out.print("zLE(67+2,0,0,0,0,0,11) = ");
        d = zc.zLE(67+2,0,0,0,0,0,11);
        System.out.println(d);
	}

	public zCalc() {
		
	}
    public native void displayHelloWorld();
        

	public native boolean ShutDown( );

	public native boolean StartUp( );
	public native double z2PCTLIM( double AGI, double Amount );
	public native double z3PCTLIM( double AGI, double Amount, long Status, long Year, double Inflation, long TaxLaw );
	public native double z5YRAVG( double Amount, double CapGain, long Year, double Inflation, long TaxLaw );
	public native double zADJPAY( double Rate, long PmtPeriod, long UniLag );
	public native double zAMT( double RegTaxInc, double AMTI, long Status, double CapGain15, double CapGain28, double CapGain25, long Year, double Inflation, double AMTCapGain15, double AMTCapGain28, double AMTCapGain25, long TaxLaw, double Dividends );
	public native double zAMTCORP ( double RegTaxInc, double AdjPref, long Year );
	public native double zAMTEXEMPT( double AMTI, long Status, long Year, long TaxLaw );
	public native double zAMTI( double AGI, double ItemDed, long Status, double AdjPref, double Adj3Pct, long AddStdDed, long Year, double Inflation, long TaxLaw );
	public native double zAMTISO( double FIT, double RegTaxInc, double AMTI, long Status, double CapGain15, double CapGain28, double CapGain25, long Year, double Inflation, double AMTCapGain15, double AMTCapGain28, double AMTCapGain25, long TaxLaw, long Factor, double Dividends );
	public native double zANNADJ( double Rate, long PmtPeriod, long EndBegin );
	public native double zANNBEN( double Principal, double Annuity, long IYear, double Growth, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef );
	public native double zANNLIFE( double Principal, double Annuity, double Rate, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table, long Revalued );
	public native double zANNRATEMAX( double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zANNRATETARGET( double Principal, double Target, double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zANNTERM( double Principal, double Annuity, double Rate, long Term, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Revalued );
	public native double zANNTERMLIFE( double Principal, double Annuity, double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table, long Revalued );
	public native double zAPPCREDIT( long Year, long EstateGift, double FOBIDed, long TaxLaw );
	public native double zAPPEXCLUSION( long Year, long EstateGift, double FOBIDed, long TaxLaw );
	public native double zBONDDUR( double ParValue, double AnnualCoupon, double YieldToMaturity, double MaturityInYrs, long PmtsPerYear );
	public native double zBONDPRICE( double ParValue, double AnnualCoupon, double YieldToMaturity, double MaturityInYrs, long PmtsPerYear );
	public native double zBONDYTC( double ParValue, double CallPrice, double BondPrice, double AnnualCoupon, double YrsTilCalled, long PmtsPerYear );
	public native double zBONDYTM( double ParValue, double BondPrice, double AnnualCoupon, double MaturityInYrs, long PmtsPerYear );
	public native double zBSCALL( double StockPrice, double StrikePrice, double Term, double RiskFreeRate, double Volatility, double DividendYield );
	public native double zBSPUT( double StockPrice, double StrikePrice, double Term, double RiskFreeRate, double Volatility, double DividendYield );
	public native double zCHARITYLIM( double AGI, double CharOrd50, double CharCG30, double CharOrd30, double CharCG20, long CarryOver );
	public native double zCLATGST( double NetTransferFV, double Allocation, double Rate, long Term, long Year, long TaxLaw );
	public native double zCLUTGST( double Payout, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double Rate, long PmtPeriod, long UniLag, long YrsDef, long Status, long Table, double UsedGST, long Year, double Inflation, long TaxLaw );
	public native double zCRATEX( double Principal, double Annuity, double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zCRATMAX( double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zCRUTMAX( long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double Rate, long PmtPeriod, long UniLag, long YrsDef, long Status, long Table );
	public native double zDIVIDENDS( double Dividends, long Year, long TaxLaw );
	public native double zERM( long Age1, long Age2, long Status, long Table );
	public native double zESTDISTRIB( double NetEstate, double Percent, double NonDedPct, double DedDistrib, double NonDedDistrib, double Base, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zET( double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zETMIN( double Target, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zEXANN( long Factor, double Principal, double Annuity, double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zEXEMPT( double AGI, long Status, long Exemptions, long Year, double Inflation, long TaxLaw );
	public native double zFET( double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zFETMAXRATE( long Year, long TaxLaw );
	public native double zFETMIN( double Target, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zFGT( double TaxGift, double Post76Gifts, double Pre77Gifts, double AdjustUC, long Year, double UsedUC, long TaxLaw );
	public native double zFGTBASIS( double Gift, double Basis, double Post76Gifts, double Pre77Gifts, double AdjustUC, long Year, double UsedUC, long TaxLaw );
	public native double zFGTET( double TaxGift, double Post76Gifts, double Pre77Gifts, double AdjustUC, long GiftYear, double UsedUC, long TaxLaw, long DeathYear );
	public native double zFGTMIN( double Target, double Post76Gifts, double Pre77Gifts, double AdjustUC, long Year, double UsedUC, long TaxLaw );
	public native double zFICA( double Wages, long Factor, long Year, double Inflation );
	public native double zFIT( double TaxInc, long Status, double CapGain15, double CapGain28, double CapGain25, long Year, double Inflation, long TaxLaw, double Dividends );
	public native double zFITBRACKET( long Year, long Bracket, long Factor, long Status, double Inflation, long TaxLaw );
	public native double zFITCAPGAINRATE( long Bracket, long Year, long TaxLaw );
	public native double zFITCORP ( double TaxInc, long Year );
	public native double zFITDETAILS( long Factor, double TaxInc, long Status, double CapGain15, double CapGain28, double CapGain25, long Year, double Inflation, long TaxLaw, double Dividends );
	public native double zFITET( double TaxInc, double CapGain15, double CapGain28, double CapGain25, long Year, double Inflation, long TaxLaw, double Dividends );
	public native double zGIFTANN( long Factor, double Principal, double Basis, double Annuity, double Rate, long Age1, long Age2, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long IYear, long Table1, long Table2, long Term, long Status, long GainPeriod );
	public native double zGIFTX( long Exclusions, long Year, double Inflation );
	public native double zGRATAF( double Rate, long Term, long Age1, long Age2, double AnnIncr, long PmtPeriod, long Table );
	public native double zGRATBEN( double Rate, long Term, double Growth, double AnnIncr, long PmtPeriod, double Principal, double Annuity, long ReGRAT );
	public native double zGRATGIFT( double Rate, long Term, long Age1, long Age2, double AnnIncr, long PmtPeriod, double Principal, double Annuity, long Table );
	public native double zGRATZO( double Rate, long Term, double AnnIncr, long PmtPeriod, double Principal );
	public native double zGROUPTERM( double Insurance, long Age, long KeyEE, double Contrib, long Months );
	public native double zGSTEXEMPT( double UsedGST, long Year, double Inflation, long TaxLaw );
	public native double zGSTIR( double NetTransfer, double Allocation, double UsedGST, long Year, double Inflation, long AutoAllocate, long TaxLaw );
	public native double zGSTTAX( double NetTransfer, double Allocation, double UsedGST, long Year, double Inflation, long AutoAllocate, double NetTransferFV, long TaxLaw );
	public native double zINHERITEDGAINS( long Year, double GainsNonSpouse, double GainsSpouse, long Factor, double Losses, double Inflation, long TaxLaw );
	public native double zINSTALL( long Factor, double Principal, double Basis, double NoteRate, long Term, long IYear, long NoteType, long Xterm, long EndBegin, long YrsDef );
	public native double zIRDDED( double NetIRD, double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zIRSAGE( String Bday, String tDate );
	public native double zLE( long Age1, long Age2, long Age3, long Age4, long Age5, long Status, long Table );
	public native double zLIFE( long Factor, double Rate, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zLIFE5X5( double Principal, double Rate, long Age, long Table, long Factor );
	public native double zLIM401K( long Year, long Age, double Inflation, long TaxLaw );
	public native double zLIM415( long Year, long Factor, double Inflation, long TaxLaw );
	public native double zLIMCOMP( long Year, double Inflation, long TaxLaw );
	public native double zLIMIRA( long Year, long Age, long IRAType, double Inflation, long TaxLaw );
	public native double zLIMSIMPLE( long Year, long Age, double Inflation, long TaxLaw );
	public native double zLX( long Age, long Table );
	public native double zMEDLIM( double AGI, double Amount );
	public native double zMINDIS( long PAge, long DBAge, long DisYr, long Recalc, long PDthDY, long DBDthDY, long DB, long Table, long OldRegs, long DBBornAfter );
	public native double zNETGIFT( double GrossAmt, double Post76Gifts, double Pre77Gifts, double AdjustUC, long Year, double UsedUC, long TaxLaw );
	public native double zNPX( long Age, long nYrs, long Table );
	public native double zPIF( double Principal, double Rate, long Age1, long Age2, long Age3, long Age4, long Age5, long YrsDef, long Status, long Table );
	public native double zPRIVANN( long Factor, double Principal, double Basis, double Rate, long Age1, long Age2, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long IYear, long Table1, long Table2, long Term, long Status, long ExAnn );
	public native double zPS58( long Age1, long Age2, long Status );
	public native double zPVFUTUREPMT( double Payment, double Rate, long nYrs, long Age1, long Age2, long Age3, long Age4, long Age5, long Status, long Table );
	public native double zQPRTGIFT( double Rate, long Term, long Age1, long Age2, long Reversion, double Principal, long Table );
	public native double zQPRTREV( double Rate, long Term, long Age1, long Age2, double Principal, long Table );
	public native double zRANDLOGNORMAL( double Mean, double StdDev );
	public native double zRANDNORMAL( double Mean, double StdDev );
	public native double zRANDUNIFORMINT( double Mean, double StdDev );
	public native double zRANDUNIFORMREAL( double Mean, double StdDev );
	public native double zRESGIFT( long Factor, double Residence, long UsefulLife, double Residual, double Land, double Rate, long Age1, long Age2, long Age3, long Age4, long Age5, long Status, long Table );
	public native double zSCIN( long Factor, double Principal, double Basis, double IRSRate, long Age1, double NoteRate, long Term, long NoteType, long Xterm, long EndBegin, long YrsDef, long Table, long Method, long Age2, long Status );
	public native double zSDT( double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, long TaxLaw, double SDT, long SDTYear );
	public native double zSEC2013( double Years, double Sec2013Lim1, double Sec2013Lim2 );
	public native double zSEC2013LIM1( double NetTransfer, double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, double AdjFET, long TaxLaw, double SDT, long SDTYear );
	public native double zSEC2013LIM2( double NetTransfer, double TaxEst, double Post76Gifts, double GiftsInGE, double AdjustUC, long Year, double GiftTaxPaid, double FOBIDed, double OtherCredits, double AdjTaxEst, long TaxLaw, double SDT, long SDTYear );
	public native double zSEC6166( long Factor, double AdjGE, double BusVal, double FET, long Year, long TaxLaw );
	public native double zSEC72TAMORT( double Balance, double Rate, long Age1, long Age2, long PmtPeriod, long EndBegin, long Table, long Uniform );
	public native double zSEC72TANN( double Balance, double Rate, long Age1, long Age2, long PmtPeriod, long EndBegin, long Status, long Table );
	public native double zSEC72TMINDIS( double Balance, long Age1, long Age2, long Table, long Uniform );
	public native double zSEC79( long Age );
	public native double zSETAX( double NetEarnings, double Wages, long Factor, long Year, double Inflation );
	public native double zSOCSEC( double SocSec, double ModAGI, long Status, long Year );
	public native double zSPLITDOLLAR( double Insurance, long Age1, long Age2, long Status, double Premium, double Contribution, long PremTable );
	public native double zSSNRA( long BirthYear );
	public native double zSTDDED( long Status, long AddStdDed, long Year, double Inflation, long TaxLaw );
	public native double zSTEPUP( long Year, long Factor, double Losses, double Inflation, long TaxLaw );
	public native double zTABLE2001( long Age1, long Age2, long Status );
	public native double zTERM( long Factor, double Rate, long Term, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef );
	public native double zTERMLIFE( long Factor, double Rate, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double AnnIncr, long PmtPeriod, long EndBegin, long YrsDef, long Status, long Table );
	public native double zTI( double AGI, double ItemDed, long Status, long Exemptions, long AddStdDed, long Year, double Inflation, long TaxLaw );
	public native double zTTAX( double Amount, long Year, long TaxLaw, long EstateGift );
	public native double zTTAX2011( double Amount );
	public native double zUC( double Post76Gifts, double Pre77Gifts, double AdjustUC, long Year, double UsedUC, double FOBIDed, long TaxLaw );
	public native double zUNIBEN( double Principal, double Payout, long IYear, double Growth, long PmtPeriod, long UniLag, long YrsDef );
	public native double zUNILIFE( long Factor, double Payout, long Age1, long Age2, long Age3, long Age4, long Age5, double Rate, long PmtPeriod, long UniLag, long YrsDef, long Status, long Table );
	public native double zUNIPAYOUTTARGET( double Principal, double Target, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double Rate, long PmtPeriod, long UniLag, long YrsDef, long Status, long Table );
	public native double zUNITERM( long Factor, double Payout, long Term, double Rate, long PmtPeriod, long UniLag, long YrsDef );
	public native double zUNITERMLIFE( long Factor, double Payout, long Term, long Age1, long Age2, long Age3, long Age4, long Age5, double Rate, long PmtPeriod, long UniLag, long YrsDef, long Status, long Table );
	public native double zWITHHOLD( double AnnWages, long PayPeriod, long Status, long Allowances, long Year, double Inflation, long TaxLaw );
	//public native double zYEARFRAC( DATE StartDATE, DATE EndDATE );

}
