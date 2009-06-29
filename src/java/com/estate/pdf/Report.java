package com.estate.pdf;

import java.io.FileOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aes.pdf.ClatLife;
import com.estate.beans.AssetList;
import com.estate.controller.GratController;
import com.estate.report.CRTReport;
import com.estate.report.ClatReport;
import com.estate.report.GratReport;
import com.estate.report.IditReport;
import com.estate.report.RpmReport;
import com.estate.report.ScinReport;
import com.estate.toolbox.CRT;
import com.estate.toolbox.Clat;
import com.estate.toolbox.Idit;
import com.estate.toolbox.LifeTool;
import com.estate.toolbox.Rpm;
import com.estate.toolbox.SCIN;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.teag.bean.PdfBean;

public class Report  {

	private PdfWriter writer;
	private Document document;
	private int pageNum = 1;
	private HttpSession session;
	private PdfBean userInfo;
	
	public boolean initialized = false;
	
	
	public Report(HttpServletResponse response) {
		// We need to start the document.
		// creat a document-object
		Document.compress = true;
		document = new Document(PageSize.LETTER.rotate());

		try {
			// BaseFont font = PageUtils.LoadFont("times.ttf");
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			writer = PdfWriter
					.getInstance(document, response.getOutputStream());

			// step 3: we open the document
			document.open();
			initialized = true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Report(String name) {
		String id = session.getId();
		System.out.println(id);
		Document.compress = true;
		document = new Document(PageSize.LETTER.rotate());

		try {
			// BaseFont font = PageUtils.LoadFont("times.ttf");
			// step 2:
			// we create a writer that listens to the document
			// and directs a PDF-stream to a file

			writer = PdfWriter
					.getInstance(document, new FileOutputStream(name));

			// step 3: we open the document
			document.open();
			initialized = true;
			
			writer.flush();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void finish() {
		document.close();
	}
	
	public void genAesTitle() {
		com.aes.pdf.TitlePage titlePage = new com.aes.pdf.TitlePage(document,writer);
		titlePage.setUserInfo(userInfo);
		titlePage.setPageNum(1);
		titlePage.draw();
		newPage();
	}
	
	public void genClat() {
		genTitle("Charitable Lead Annuity Trust");
		Clat clat = (Clat) session.getAttribute("clat");
		ClatReport cReport = new ClatReport(document, writer);
		cReport.setClat(clat);
		cReport.setUserInfo(userInfo);
		cReport.setPageNum(pageNum);
		cReport.draw();
		newPage();
	}
	
	
	public void genClatLife() {
		com.aes.pdf.TitlePage page = new com.aes.pdf.TitlePage(document,writer);
		page.setUserInfo(userInfo);
		page.draw();
		
		ClatLife cl = new ClatLife(document,writer);
		
		Clat clat = (Clat) session.getAttribute("clat");
		cl.setClat(clat);
		
		LifeTool life = (LifeTool) session.getAttribute("life");
		cl.setLifeInsurance(life);

		AssetList aList = (AssetList) session.getAttribute("aList");
		cl.setAList(aList);
		cl.setUserInfo(userInfo);
		cl.genData();
		cl.draw();
		newPage();
	}
	
	public void genCrt() {
		genTitle("Charitable Remainder Trust (CRT)");
		CRT crt = (CRT) session.getAttribute("sCrt");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		CRTReport cReport = new CRTReport(document,writer);
		cReport.setCrt(crt);
		cReport.setUserInfo(userInfo);
		cReport.setPageNum(pageNum);
		cReport.draw();
	}
	
	public void genGrat() {
		genTitle("Grantor Retained Annuity Trust (GRAT)");
		GratController grat = (GratController) session.getAttribute("grat");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		GratReport gratReport = new GratReport(document,writer);

		gratReport.setGrat(grat);
		gratReport.setUserInfo(userInfo);
		gratReport.setPageNum(pageNum);
		gratReport.draw();
		newPage();
	}
	
	public void genIdit() {
		genTitle("Intentionally Defective Irrevocable Trust (IDIT)");
		Idit idit = (Idit) session.getAttribute("idit");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		IditReport iReport = new IditReport(document,writer);
		iReport.setIdit(idit);
		iReport.setUserInfo(userInfo);
		iReport.setPageNum(pageNum);
		iReport.draw();
		newPage();
	}
	
	public void genRpm() {
		genTitle("Retirement Plan Maximizer (TM)");
		Rpm rpm = (Rpm) session.getAttribute("rpm");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		RpmReport rReport = new RpmReport(document,writer);
		rReport.setRpm(rpm);
		rReport.setUserInfo(userInfo);
		rReport.setPageNum(pageNum);
		rReport.draw();
		newPage();
	}
	
	public void genScin() {
		genTitle("Self Canceling Installment Note");
		SCIN scin = (SCIN) session.getAttribute("scin");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		ScinReport sReport = new ScinReport(document,writer);
		sReport.setScin(scin);
		sReport.setUserInfo(userInfo);
		sReport.setPageNum(pageNum);
		sReport.draw();
		newPage();
	}
	
	public void genTitle(String title) {
		com.estate.report.TitlePage titlePage = new com.estate.report.TitlePage(document,writer);
		titlePage.addAdvisor("", userInfo.getPlannerFirstName() + " " + userInfo.getPlannerLastName(), "");
		titlePage.setUserInfo(userInfo);
		titlePage.setTitle(title);
		titlePage.setClient(userInfo.getFirstName() + " " + userInfo.getLastName());
		titlePage.setPageNum(1);
		titlePage.draw();
		newPage();
	}
	
	public void genQprt(){
		/*
		genTitle("Qualified Personal Residence Trust");
		QprtController qprt = (QprtController) session.getAttribute("qprt");
		PdfBean userInfo = (PdfBean) session.getAttribute("userInfo");
		*/
	}
	
	public void genSC1(){
		
		
	}
	
	
	public HttpSession getSession() {
		return session;
	}
	
	public PdfBean getUserInfo() {
		return userInfo;
	}
	
	public void newPage() {
		try {
			document.newPage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		pageNum++;
	}

	public void setPage(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setUserInfo(PdfBean userInfo) {
		this.userInfo = userInfo;
	}
}
