package com.fraude.managedBeans.warnings;

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
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

import com.jsf2leaf.model.LatLong;
import com.jsf2leaf.model.Layer;
import com.jsf2leaf.model.Map;
import com.jsf2leaf.model.Marker;
import com.fraude.entities.Util;
import com.fraude.entities.Cell;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.Flow;
import com.fraude.entities.FluxCdr;
import com.fraude.entities.Non_Frodulent_seq;
import com.fraude.entities.ReglesFraude;

import com.fraude.interfaces.CellRemote;
import com.fraude.interfaces.FraudeManagerRemote;
@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "alerte_fraude")
@ViewScoped
public class AlertesFraude {

	@EJB
	private FraudeManagerRemote fraude_service;

	@EJB
	FraudeManagerRemote decision_service;

	@EJB
	private CellRemote cell_service;

	

	private boolean afficheCDR = false;
	private boolean affichactivation = false;
	private boolean affichrecharge = false;
	private boolean affichtransfert = false;

	private Object[] selected_alerte;
	private Object[] selected_parametre;
	private List<Object[]> cdrs;
	private List<Object[]> cdrs_in_activations;
	private List<Object[]> cdrs_in_recharge;
	private List<Object[]> cdrs_in_transfert;
	private List<Object[]> regles;
	private List<Object[]> Mos;
	private List<Object[]> Mts;
	private List<String> fields;

	

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<Object[]> getMts() {
		return Mts;
	}

	public void setMts(List<Object[]> mts) {
		Mts = mts;
	}

	private List<Object> namebase;

	public Object[] getSelected_parametre() {
		return selected_parametre;
	}

	public void setSelected_parametre(Object[] selected_parametre) {
		this.selected_parametre = selected_parametre;
	}

	public List<Object[]> getMos() {
		return Mos;
	}

	public void setMos(List<Object[]> mos) {
		Mos = mos;
	}

	public List<Object[]> getRegles() {
		return regles;
	}

	public void setRegles(List<Object[]> regles) {
		this.regles = regles;
	}

	private Object[] identities;

	public void afichageReglesParametre() {

		boolean a = false;
		regles = decision_service.getAllParametre(selected_alerte);
		a = regles.isEmpty();
		System.out.println(a);
		// for (int i=0; i<=regles.size(); i++)
		// {
		// RegleParametre ch2= regles.get(i);
		// System.out.println(ch2) ;
		// }

	}

	public void affichageCdrMo() {
	    // afficheNameField();
		String date = affichedate();
		fields = decision_service.GetNameField(selected_parametre);
		for(String field : fields) {
			System.out.println(field);
		}
		Mos = decision_service.GetMo(selected_parametre, date);
		

	}

	public void afficheNameField() {

		fields = decision_service.GetNameField(selected_parametre);
		for(String field : fields) {  System.out.println(field); }
		
	}

	public void affichageCdrMt() {
		String date = affichedate();
	
		Mts = decision_service.GetMT(selected_parametre, date);
		

	}

	public Object[] getIdentities() {
		return identities;
	}

	public void setIdentities(Object[] identities) {
		this.identities = identities;
	}

	private List<Object[]> liste_parametres = new ArrayList<>();

	private List<ReglesFraude> liste_regles;

	private List<Object[]> listeAlerte = new ArrayList<>();

	private ReglesFraude selected_regle;

	private List<Object[]> details_fraude_location;

	public List<Object[]> getDetails_fraude_location() {
		return details_fraude_location;
	}

	public void setDetails_fraude_location(List<Object[]> details_fraude_location) {
		this.details_fraude_location = details_fraude_location;
	}

	private List<SelectItem> FluxFilterOptions;

	@PostConstruct
	public void init() {

		// listeAlerte= decision_service.getAllDecisionFraudes("");
		selected_regle = new ReglesFraude();

		liste_regles = fraude_service.getAllFraudes("where etat='A'");
		selected_alerte = new Object[7];
		selected_parametre = new Object[7];
		FluxFilterOptions = new ArrayList<SelectItem>(fraude_service.getAllFlux().size() + 1);
		FluxFilterOptions.add(new SelectItem("", "All"));
		for (Flow d : fraude_service.getAllFlux2()) {
			FluxFilterOptions.add(new SelectItem(d.getFlowName(), d.getFlowName()));
		}
	}

	public void selectRegle() {

		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	private boolean mapshowed = false;

	private Map springfieldMap = new Map();

	public Map getSpringfieldMap() {
		return springfieldMap;
	}

	public void setSpringfieldMap(Map springfieldMap) {
		this.springfieldMap = springfieldMap;
	}

	public void showMap() {
		mapshowed = true;
		List<Cell> cells = new ArrayList<>();
		for (Object[] ob : details_fraude_location) {
			String[] s = ob[1].toString().split("-");
			System.out.println(s[0].toString() + ":" + s[1].toString());
			String lac = s[0].toString();
			String cellid = s[1].toString();
			System.out.println(lac);
			System.out.println("Cellid=" + cellid);
			Cell c = cell_service.getCellByLacCellId(lac, cellid);
			if (c != null) {
				cells.add(c);
			}
		}
	}

	public void showMo() {

		List<Cell> cells = new ArrayList<>();
		for (Object[] ob : details_fraude_location) {
			String[] s = ob[1].toString().split("-");
			System.out.println(s[0].toString() + ":" + s[1].toString());
			String lac = s[0].toString();
			String cellid = s[1].toString();
			System.out.println(lac);
			System.out.println("Cellid=" + cellid);
			Cell c = cell_service.getCellByLacCellId(lac, cellid);
			if (c != null) {
				cells.add(c);
			}
		}

		Layer placesLayer = (new Layer()).setLabel("Places");
		for (Cell c : cells) {
			System.out.println(c.getLatitude() + ":" + c.getAltitude() + "");
			placesLayer.addMarker(new Marker(new LatLong(c.getLatitude() , c.getAltitude() ),
					"<b>" + c.getCellNom() + "</b>"));

		}

		springfieldMap.setWidth("350px").setHeight("250px").setCenter(new LatLong((float)18.11,(float)15.9)).setZoom(10);
		springfieldMap.addLayer(placesLayer);
	}

	public void loadIdentitéCdr() {

//		liste_parametres = fraude_service.getParametres(selected_regle, selected_alerte[1].toString());
//		details_fraude_location = fraude_service.getLocationMsisdn(selected_alerte[1].toString(),
//				selected_regle.getId());
//		identities = new Object[20];
//
//		identities = fraude_service.getDecisions(selected_alerte[1].toString());
//
//		if (fraude_service.testifexists("cdrs_in_act_" + selected_alerte[6].toString().substring(0, 6))) {
//			cdrs_in_activations = fraude_service
//					.getCDRBActivatonyTypeRegle(selected_alerte[6].toString().substring(0, 6), "where servedmsisdn = '"
//							+ selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()) + "'");
//			if (cdrs_in_activations != null) {
//				affichactivation = true;
//
//			} else {
//				affichactivation = false;
//
//			}
//		}
//
//		if (fraude_service.testifexists("cdrs_in_recharge" + selected_alerte[6].toString().substring(0, 6))) {
//
//			cdrs_in_recharge = fraude_service.getCDRRechargeByTypeRegle(selected_alerte[6].toString().substring(0, 6),
//					"where servedmsisdn = '"
//							+ selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()) + "'");
//			if (cdrs_in_recharge != null) {
//				affichrecharge = true;
//			} else {
//				affichrecharge = false;
//
//			}
//		}
//
//		if (fraude_service.testifexists("cdrs_in_mgr_" + selected_alerte[6].toString().substring(0, 6))) {
//
//			cdrs_in_transfert = fraude_service.getCDRTransfertByTypeRegle(selected_alerte[6].toString().substring(0, 6),
//					"where servedmsisdn = '"
//							+ selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()) + "'");
//			if (cdrs_in_transfert != null) {
//				affichtransfert = true;
//			} else {
//				affichtransfert = false;
//			}
//		}
//
//		System.out.println(selected_alerte[6]);
//
//		switch (selected_regle.getFlux().getFlowName()) {
//		case "msc":
//			if (fraude_service.testifexists("cdrs_msc" + selected_alerte[6].toString().substring(0, 6))) {
//				cdrs = fraude_service.getCDRByTypeRegle("msc" + selected_alerte[6].toString().substring(0, 6),
//						"where appelant = '" + selected_alerte[1].toString() + "'");
//				if (cdrs != null) {
//					afficheCDR = true;
//
//				} else {
//					afficheCDR = false;
//
//				}
//			} else {
//				afficheCDR = false;
//			}
//
//			break;
//		case "IN":
//			if (fraude_service.testifexists("cdrs_in" + selected_alerte[6].toString().substring(0, 6))) {
//				cdrs = fraude_service.getCDRByTypeRegle("in" + selected_alerte[6].toString().substring(0, 6),
//						"where appelant = '" + selected_alerte[1].toString() + "'");
//				afficheCDR = true;
//
//			} else {
//				afficheCDR = false;
//			}
//			break;
//		case "sys fact":
//			if (fraude_service.testifexists("cdrs_sysfact" + selected_alerte[6].toString().substring(0, 6))) {
//				cdrs = fraude_service.getCDRByTypeRegle("sysfact" + selected_alerte[6].toString().substring(0, 6),
//						"where appelant = '" + selected_alerte[1].toString() + "'");
//				afficheCDR = true;
//
//			} else {
//				afficheCDR = false;
//			}
//
//			break;
//		case "TAP IN":
//			if (fraude_service.testifexists("tapin" + selected_alerte[6].toString().substring(0, 6))) {
//				cdrs = fraude_service.getCDRByTypeRegle("tapin" + selected_alerte[6].toString().substring(0, 6),
//						"where appelant = '" + selected_alerte[1].toString() + "'");
//				afficheCDR = true;
//
//			} else {
//				afficheCDR = false;
//			}
//			break;
//		case "TAP OUT":
//			if (fraude_service.testifexists("tapout" + selected_alerte[6].toString().substring(0, 6))) {
//				cdrs = fraude_service.getCDRByTypeRegle("tapout" + selected_alerte[6].toString().substring(0, 6),
//						"where appelant = '" + selected_alerte[1].toString() + "'");
//				afficheCDR = true;
//
//			} else {
//				afficheCDR = false;
//			}
//			break;
//
//		default:
//			break;
//		}
		afichageReglesParametre();
		// affichageCdrMo ();
	}
	// selected_alerte = msisdn
	@POST
    @Path("/decision")
    public void blackList(DecisionFraude dec) {
		
        DecisionFraude df = new DecisionFraude();
        df.setDecision("B");
        df.setRegle(dec.getRegle());
        df.setMsisdn(dec.getMsisdn().toString().substring(3, dec.getMsisdn().toString().length()));
        
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        df.setDateDecision(currentTimestamp);
      df.setNomUtilisateur(dec.getNomUtilisateur());
        fraude_service.decision(df,dec.getMsisdn().toString());
        
        System.out.println(""+ dec.getIdRegle());
       // listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
    }
	
	@POST
    @Path("/decisionwhitelist")
    public void whiteList(DecisionFraude dec) {
        DecisionFraude df = new DecisionFraude();
        df.setDecision("W");
        df.setRegle(dec.getRegle());
        df.setMsisdn(dec.getMsisdn().toString().substring(3, dec.getMsisdn().toString().length()));
        
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        df.setDateDecision(currentTimestamp);
        
        fraude_service.decision(df,dec.getMsisdn().toString());
       // listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
    }
	
	
	@POST
    @Path("/supprimer")
    public void supprimer(DecisionFraude dec) {
        DecisionFraude df = new DecisionFraude();
        df.setDecision("S");
        df.setRegle(dec.getRegle());
        df.setMsisdn(dec.getMsisdn().toString().substring(3, dec.getMsisdn().toString().length()));
        
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        df.setDateDecision(currentTimestamp);
        
        fraude_service.decision(df,dec.getMsisdn().toString());
       // listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
    }
	
	@POST
    @Path("/desactiver")
    public void desactiver(Non_Frodulent_seq dec) {
		Non_Frodulent_seq df = new Non_Frodulent_seq();
        df.setDecision("D");
        df.setRegle(dec.getRegle());
        df.setMsisdn(dec.getMsisdn().toString().substring(3, dec.getMsisdn().toString().length()));
        
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
        df.setDateDecision(currentTimestamp);
        
        fraude_service.decisionnonfraudilentseq(df,dec.getMsisdn().toString());
       // listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
    }
	
	
	public void BlackList() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("B");
		df.setRegle(selected_regle);
		df.setMsisdn(selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()));
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		df.setDateDecision(currentTimestamp);
		df.setNomUtilisateur(Util.getUser().getUName());
		fraude_service.decision(df, selected_alerte[1].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void WhiteList() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("W");
		df.setRegle(selected_regle);
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		df.setDateDecision(currentTimestamp);
		df.setMsisdn(selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()));
		// df.setNomUtilisateur(Util.getUser().getUName());
		fraude_service.decision(df, selected_alerte[1].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void Supprimer() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("S");
		df.setRegle(selected_regle);
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		df.setDateDecision(currentTimestamp);
		df.setMsisdn(selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()));
		df.setNomUtilisateur(Util.getUser().getUName());
		fraude_service.decision(df, selected_alerte[1].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void Desactiver() {
		DecisionFraude df = new DecisionFraude();
		Calendar calendar = Calendar.getInstance();
		df.setRegle(selected_regle);
		df.setNomUtilisateur(Util.getUser().getUName());
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		df.setDateDecision(currentTimestamp);
		df.setDecision("D");
		df.setMsisdn(selected_alerte[1].toString().substring(3, selected_alerte[1].toString().length()));
		System.out.println(df.getMsisdn());
		fraude_service.decision(df, selected_alerte[1].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	private Marker marker;

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	// Shared coordinates

	//////////////////////////////////////////////////////////////////
	private String typeDate;
	private boolean parDate = false;

	// private List<DecisionFraude> liste_decisions;

	public void affichParDate() {
		if (this.typeDate.equals("ParDate")) {
			parDate = true;
		} else {
			parDate = false;

			// if(this.getChoix()!=null){
			listeAlerte = new ArrayList<>();
			listeAlerte = decision_service.getAllDecisionFraudes("");
			// }

		}
	}

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String affichedate() {
		System.out.println(date);
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String dat = dateFormat.format(date);
		System.out.println(dat);

		return dat;
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

	public void getDecisionsParDate() {

		listeAlerte = new ArrayList<>();
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
			listeAlerte = decision_service.getAllDecisionFraudes("where  date_detection Between to_date(" + "'" + deb0
					+ " 00:00:00'" + ",'yyyy-MM-dd HH24:MI:SS') And to_date(" + "'" + fin0 + " 23:59:59'"
					+ ",'yyyy-MM-dd HH24:MI:SS') group by id_regle,msisdn");

		} else {
			listeAlerte = decision_service
					.getAllDecisionFraudes("where   to_char(dateDecision,'yyyy-MM-dd')  = to_char(to_date(" + "'" + deb0
							+ "'" + ",'yyyy-MM-dd'),'yyyy-MM-dd')  group by id_regle,msisdn");

		}

	}
	///////////////////////////////////////////////////////////////

	public boolean isParDate() {
		return parDate;
	}

	public void setParDate(boolean parDate) {
		this.parDate = parDate;
	}

	public String getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(String typeDate) {
		this.typeDate = typeDate;
	}

	public List<Object[]> getListeAlerte() {
		return listeAlerte;
	}

	public void setListeAlerte(List<Object[]> listeAlerte) {
		this.listeAlerte = listeAlerte;
	}

	public ReglesFraude getSelected_regle() {
		return selected_regle;
	}

	public void setSelected_regle(ReglesFraude selected_regle) {
		this.selected_regle = selected_regle;
	}

	public List<ReglesFraude> getListe_regles() {
		return liste_regles;
	}

	public void setListe_regles(List<ReglesFraude> liste_regles) {
		this.liste_regles = liste_regles;
	}

	public List<Object[]> getListe_parametres() {
		return liste_parametres;
	}

	public void setListe_parametres(List<Object[]> liste_parametres) {
		this.liste_parametres = liste_parametres;
	}

	public boolean isAfficheCDR() {
		return afficheCDR;
	}

	public void setAfficheCDR(boolean afficheCDR) {
		this.afficheCDR = afficheCDR;
	}

	public boolean isAffichactivation() {
		return affichactivation;
	}

	public void setAffichactivation(boolean affichactivation) {
		this.affichactivation = affichactivation;
	}

	public List<SelectItem> getFluxFilterOptions() {
		return FluxFilterOptions;
	}

	public void setFluxFilterOptions(List<SelectItem> fluxFilterOptions) {
		FluxFilterOptions = fluxFilterOptions;
	}

	public boolean isAffichrecharge() {
		return affichrecharge;
	}

	public void setAffichrecharge(boolean affichrecharge) {
		this.affichrecharge = affichrecharge;
	}

	public boolean isAffichtransfert() {
		return affichtransfert;
	}

	public boolean isMapshowed() {
		return mapshowed;
	}

	public void setMapshowed(boolean mapshowed) {
		this.mapshowed = mapshowed;
	}

	public void setAffichtransfert(boolean affichtransfert) {
		this.affichtransfert = affichtransfert;
	}

	public List<Object[]> getCdrs() {
		return cdrs;
	}

	public void setCdrs(List<Object[]> cdrs) {
		this.cdrs = cdrs;
	}

	public List<Object[]> getCdrs_in_activations() {
		return cdrs_in_activations;
	}

	public void setCdrs_in_activations(List<Object[]> cdrs_in_activations) {
		this.cdrs_in_activations = cdrs_in_activations;
	}

	public List<Object[]> getCdrs_in_recharge() {
		return cdrs_in_recharge;
	}

	public void setCdrs_in_recharge(List<Object[]> cdrs_in_recharge) {
		this.cdrs_in_recharge = cdrs_in_recharge;
	}

	public List<Object[]> getCdrs_in_transfert() {
		return cdrs_in_transfert;
	}

	public void setCdrs_in_transfert(List<Object[]> cdrs_in_transfert) {
		this.cdrs_in_transfert = cdrs_in_transfert;
	}

	public Object[] getSelected_alerte() {
		return selected_alerte;
	}

	public void setSelected_alerte(Object[] selected_alerte) {
		this.selected_alerte = selected_alerte;
	}

	public boolean filterByDate(Object value, Object filter, Locale locale) {

		if (filter == null) {
			return true;
		}

		if (value == null) {
			return false;
		}

		return DateUtils.truncatedEquals((Date) filter, (Date) value, Calendar.DATE);
	}

	public void genaratexcel(List<Object[]> list) throws IOException, InvalidFormatException {

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
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
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
		org.apache.poi.ss.usermodel.Cell cell_title = title.createCell(3);
		cell_title.setCellValue("Warning ");
		cell_title.setCellStyle(cellti);
		sheet.addMergedRegion(new CellRangeAddress(2, 3, 3, 6));
		// Create a Row
		Row headerRow = sheet.createRow(10);

		// Create cells
		Row date = sheet.createRow(7);
		org.apache.poi.ss.usermodel.Cell cell_date = date.createCell(2);
		cell_date.setCellValue("WARNING");
		cell_date.setCellStyle(cellti);
		sheet.addMergedRegion(new CellRangeAddress(7, 8, 2, 9));

		org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(0);
		cell.setCellValue("Rule");
		cell.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(0);

		org.apache.poi.ss.usermodel.Cell cel2 = headerRow.createCell(1);
		cel2.setCellValue("MSISDN");
		cel2.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(1);

		org.apache.poi.ss.usermodel.Cell cel3 = headerRow.createCell(2);
		cel3.setCellValue("START DATE");
		cel3.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(2);

		org.apache.poi.ss.usermodel.Cell cel4 = headerRow.createCell(3);
		cel4.setCellValue("END DATE");
		cel4.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(3);

		org.apache.poi.ss.usermodel.Cell cel5 = headerRow.createCell(4);
		cel5.setCellValue("DETECTION DATE");
		cel5.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(4);

		org.apache.poi.ss.usermodel.Cell cel6 = headerRow.createCell(5);
		cel6.setCellValue("NUMBER OCCURENCES");
		cel6.setCellStyle(headerCellStyle);
		sheet.autoSizeColumn(5);

		// Create Cell Style for formatting Date

		// Create Other rows and cells with employees data
		int rowNum = 11;
		for (int i = 0; i < list.size(); i++) {

			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(selected_regle.getNom().toString());
			sheet.autoSizeColumn(0);
			row.createCell(1).setCellValue(list.get(i)[1].toString());
			sheet.autoSizeColumn(1);
			row.createCell(2).setCellValue(list.get(i)[2].toString());
			sheet.autoSizeColumn(2);
			row.createCell(3).setCellValue(list.get(i)[3].toString());
			sheet.autoSizeColumn(3);
			row.createCell(4).setCellValue(list.get(i)[4].toString());
			sheet.autoSizeColumn(4);
			row.createCell(5).setCellValue(list.get(i)[5].toString());
			sheet.autoSizeColumn(5);

		}

		// Resize all columns to fit the content size

		workbook.write(out);
		out.flush();
		out.close();
		FacesContext.getCurrentInstance().responseComplete();

	}
}
