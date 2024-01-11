package com.fraude.managedBeans.decisions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import com.fraude.entities.Util;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.StatFraudeMsisdn;
import com.fraude.interfaces.FraudeManagerRemote;

@ManagedBean(name = "manage_decision")
@ViewScoped
public class ManageDecisionMbean {

	@EJB
	FraudeManagerRemote decision_service;

	private List<DecisionFraude> liste_decisions;

	private DecisionFraude decision;

	public DecisionFraude getDecision() {
		return decision;
	}

	public void setDecision(DecisionFraude decision) {
		this.decision = decision;
	}

	private String choix;

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	@PostConstruct
	public void init() {
		liste_decisions = decision_service.getDecisionFraudes(" ");
		decision = new DecisionFraude();

	}

	private Date dateDebut;
	private Date dateFin;

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void affichParDate() {
		if (this.typeDate.equals("ParDate")) {
			parDate = true;
		} else {
			parDate = false;

			if (this.getChoix() != null) {
				liste_decisions = new ArrayList<>();
				liste_decisions = decision_service.getDecisionFraudes("where decision ='" + choix + "'");
			}

		}
	}

	public void getDecisionsParDate() {
		if (choix != null) {
			liste_decisions = new ArrayList<>();
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String deb0 = df1.format(this.getDateDebut());
			Date dt1;
			Calendar c = Calendar.getInstance();
			c.setTime(this.getDateFin());
			c.add(Calendar.DATE, 1);
			dt1 = c.getTime();
			String fin1 = df1.format(this.getDateFin());
			String fin0 = df1.format(dt1);

			if (!deb0.equals(fin1)) {
				liste_decisions = decision_service
						.getDecisionFraudes("where decision ='" + choix + "' and dateDecision Between to_date(" + "'"
								+ deb0 + " 00:00:00'" + ",'yyyy-MM-dd HH24:MI:SS') And to_date(" + "'" + fin0
								+ " 23:59:59'" + ",'yyyy-MM-dd HH24:MI:SS')");

			} else {
				liste_decisions = decision_service.getDecisionFraudes(
						"where decision ='" + choix + "' and to_char(dateDecision,'yyyy-MM-dd')  = to_char(to_date("
								+ "'" + deb0 + "'" + ",'yyyy-MM-dd'),'yyyy-MM-dd')  ");

			}

		} else {
			liste_decisions = new ArrayList<>();
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			String deb0 = df1.format(this.getDateDebut());
			Date dt1;
			Calendar c = Calendar.getInstance();
			c.setTime(this.getDateFin());
			c.add(Calendar.DATE, 1);
			dt1 = c.getTime();
			String fin1 = df1.format(this.getDateFin());
			String fin0 = df1.format(dt1);
			if (!deb0.equals(fin1)) {
				liste_decisions = decision_service.getDecisionFraudes("where  dateDecision  Between  to_date(" + "'"
						+ deb0 + " 00:00:00'" + ",'yyyy-MM-dd HH24:MI:SS') And to_date(" + "'" + fin0 + " 23:59:59'"
						+ ",'yyyy-MM-dd HH24:MI:SS')");

			} else {
				liste_decisions = decision_service
						.getDecisionFraudes("where  to_char(dateDecision,'yyyy-MM-dd')  = to_char(to_date(" + "'" + deb0
								+ "'" + ",'yyyy-MM-dd'),'yyyy-MM-dd') ");

			}

		}

	}

	public void affichBlack() {
		if (this.parDate == true) {
			dateDebut = null;
			dateFin = null;
			parDate = false;
		} else {
			parDate = false;

		}

		liste_decisions = new ArrayList<>();
		liste_decisions = decision_service.getDecisionFraudes("where decision ='" + choix + "'");
	}

	private StatFraudeMsisdn fraudeur;

	public StatFraudeMsisdn getFraudeur() {
		return fraudeur;
	}

	public void setFraudeur(StatFraudeMsisdn fraudeur) {
		this.fraudeur = fraudeur;
	}

	private List<Object[]> detailsfraudeur = new ArrayList<>();

	public List<Object[]> getDetailsfraudeur() {
		return detailsfraudeur;
	}

	public void setDetailsfraudeur(List<Object[]> detailsfraudeur) {
		this.detailsfraudeur = detailsfraudeur;
	}

	public void getFraudeurInfo() {

		fraudeur = decision_service.getInfo(decision.getMsisdn());
		detailsfraudeur = decision_service.getLocationDuree(decision.getMsisdn());

	}

	private boolean parDate = false;

	public boolean isParDate() {
		return parDate;
	}

	public void setParDate(boolean parDate) {
		this.parDate = parDate;
	}

	private String typeDate;

	public String getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(String typeDate) {
		this.typeDate = typeDate;
	}

	public void black() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		decision.setDateDecision(currentTimestamp);
		decision.setNomUtilisateur(Util.getUser().getUName());
		decision.setDecision("B");
		decision_service.updateDecision(decision);
		decision_service.getDecisionFraudes(" ");
	}

	public void desactiver() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		decision.setDateDecision(currentTimestamp);
		decision.setNomUtilisateur(Util.getUser().getUName());
		decision.setDecision("D");
		decision_service.updateDecision(decision);
		decision_service.getDecisionFraudes(" ");
	}

	public void white() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		decision.setDateDecision(currentTimestamp);
		decision.setNomUtilisateur(Util.getUser().getUName());
		decision.setDecision("W");
		decision_service.updateDecision(decision);
		decision_service.getDecisionFraudes(" ");
	}

	public void supprimer() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		decision.setDateDecision(currentTimestamp);
		decision.setNomUtilisateur(Util.getUser().getUName());
		decision.setDecision("S");
		decision_service.updateDecision(decision);
		decision_service.getDecisionFraudes(" ");
	}

	public List<DecisionFraude> getListe_decisions() {
		return liste_decisions;
	}

	public void setListe_decisions(List<DecisionFraude> liste_decisions) {
		this.liste_decisions = liste_decisions;
	}

	public void genaratexcel(List<DecisionFraude> list) throws IOException, InvalidFormatException {

		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
		OutputStream out = response.getOutputStream();

		response.setContentType("application/vnd.ms-excel");
		String filename = "MyRepToExcel.xls";
		String attachmentName = "attachment; filename=\"" + filename + "\"";
		response.setHeader("Content-disposition", attachmentName);

		InputStream inputimg1 = new FileInputStream("/m.jpg");
		InputStream inputimg2 = new FileInputStream("/s.jpg");

		byte[] bytesimg1 = IOUtils.toByteArray(inputimg1);
		byte[] bytesimg2 = IOUtils.toByteArray(inputimg2);

		Workbook workbook = new HSSFWorkbook();

		int pictureIdx1 = workbook.addPicture(bytesimg1, Workbook.PICTURE_TYPE_JPEG);
		int pictureIdx2 = workbook.addPicture(bytesimg2, Workbook.PICTURE_TYPE_JPEG);

		// new HSSFWorkbook() for
		// generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like
		 * DataFormat, Hyperlink, RichTextString etc, in a format (HSSF, XSSF)
		 * independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Repport");
		// Creates the top-level drawing patriarch.
		Drawing drawing = sheet.createDrawingPatriarch();

		// Create an anchor that is attached to the worksheet
		ClientAnchor anchor = createHelper.createClientAnchor();
		// set top-left corner for the image
		anchor.setCol1(0);
		anchor.setRow1(0);

		// Creates a picture
		Picture pict = drawing.createPicture(anchor, pictureIdx1);
		// Reset the image to the original size
		pict.getImageDimension().setSize(150, 100);
		pict.resize();

		ClientAnchor anchor2 = createHelper.createClientAnchor();
		anchor2.setCol1(11);
		anchor2.setRow1(0);

		Picture pict2 = drawing.createPicture(anchor2, pictureIdx2);
		// Reset the image to the original size
		pict2.getImageDimension().setSize(200, 100);
		pict2.resize();

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setColor(IndexedColors.BLACK.index);
		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.DIAMONDS);
		headerCellStyle.setFont(headerFont);

		Font titleFont = workbook.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setColor(IndexedColors.BLACK.getIndex());

		CellStyle cellti = workbook.createCellStyle();
		cellti.setFont(titleFont);

		Row title = sheet.createRow(3);
		Cell cell_title = title.createCell(3);
		cell_title.setCellValue("Decisions Fraud ");
		cell_title.setCellStyle(cellti);
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 6));
		// Create a Row
		Row headerRow = sheet.createRow(10);

		// Create cells
		Row date = sheet.createRow(7);
		Cell cell_date = date.createCell(2);
		cell_date.setCellValue("Decisions Fraud");
		cell_date.setCellStyle(cellti);
		sheet.addMergedRegion(new CellRangeAddress(7, 8, 2, 9));

		Cell cell = headerRow.createCell(0);
		cell.setCellValue("MSISDN");
		cell.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(0);

		Cell cel2 = headerRow.createCell(1);
		cel2.setCellValue("Decision");
		cel2.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(1);

		Cell cel3 = headerRow.createCell(2);
		cel3.setCellValue("User");
		cel3.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(2);

		Cell cel4 = headerRow.createCell(3);
		cel4.setCellValue("Decision Date");
		cel4.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(3);

		Cell cel5 = headerRow.createCell(4);
		cel5.setCellValue("Rule");
		cel5.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(4);

		// Create Cell Style for formatting Date

		// Create Other rows and cells with employees data
		int rowNum = 11;
		for (int i = 0; i < list.size(); i++) {

			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(list.get(i).getMsisdn().toString());
			sheet.autoSizeColumn(0);
			row.createCell(1).setCellValue(list.get(i).getDecision().toString());
			sheet.autoSizeColumn(1);
			row.createCell(2).setCellValue(list.get(i).getNomUtilisateur().toString());
			sheet.autoSizeColumn(2);
			row.createCell(3).setCellValue(list.get(i).getDateDecision().toString());
			sheet.autoSizeColumn(3);
			row.createCell(4).setCellValue(list.get(i).getRegle().getNom().toString());
			sheet.autoSizeColumn(4);

		}

		// Resize all columns to fit the content size

		workbook.write(out);
		out.flush();
		out.close();
		FacesContext.getCurrentInstance().responseComplete();

	}
}
