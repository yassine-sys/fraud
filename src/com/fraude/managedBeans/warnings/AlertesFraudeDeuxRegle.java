package com.fraude.managedBeans.warnings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.fraude.entities.DecisionFraude;
import com.fraude.entities.ReglesFraude;

import com.fraude.interfaces.FraudeManagerRemote;

@ManagedBean(name = "alertes_fraude_deux")
@ViewScoped
public class AlertesFraudeDeuxRegle {

	@EJB
	private FraudeManagerRemote fraude_service;

	@EJB
	FraudeManagerRemote decision_service;
	
	private Object[] selected_msisdn;

	public Object[] getSelected_msisdn() {
		return selected_msisdn;
	}

	public void setSelected_msisdn(Object[] selected_msisdn) {
		this.selected_msisdn = selected_msisdn;
	}

	private List<Object[]> lst_regles;

	public List<Object[]> getLst_regles() {
		return lst_regles;
	}

	public void setLst_regles(List<Object[]> lst_regles) {
		this.lst_regles = lst_regles;
	}

	private List<Object[]> liste_msisdn;

	public List<Object[]> getListe_msisdn() {
		return liste_msisdn;
	}

	public void setListe_msisdn(List<Object[]> liste_msisdn) {
		this.liste_msisdn = liste_msisdn;
	}

	private List<Object[]> listeAlerte = new ArrayList<>();

	public List<Object[]> getListeAlerte() {
		return listeAlerte;
	}

	public void setListeAlerte(List<Object[]> listeAlerte) {
		this.listeAlerte = listeAlerte;
	}

	private boolean afficheCDR = false;
	private boolean affichactivation = false;
	private boolean affichrecharge = false;
	private boolean affichtransfert = false;

	private Object[] selected_alerte;
	private List<Object[]> cdrs;
	private List<Object[]> cdrs_in_activations;
	private List<Object[]> cdrs_in_recharge;
	private List<Object[]> cdrs_in_transfert;

	private Object[] identities;

	public Object[] getIdentities() {
		return identities;
	}

	public void setIdentities(Object[] identities) {
		this.identities = identities;
	}

	private List<Object[]> liste_parametres = new ArrayList<>();

	public Object[] getSelected_alerte() {
		return selected_alerte;
	}

	public void setSelected_alerte(Object[] selected_alerte) {
		this.selected_alerte = selected_alerte;
	}

	private ReglesFraude selected_regle;

	public ReglesFraude getSelected_regle() {
		return selected_regle;
	}

	public void setSelected_regle(ReglesFraude selected_regle) {
		this.selected_regle = selected_regle;
	}

	@PostConstruct
	public void init() {
		liste_msisdn = fraude_service.getMSISDNMorethantwo();
		selected_regle = new ReglesFraude();
		regle_id = new Object[2];
		selected_alerte = new Object[7];
	}

	private Object[] regle_id;

	public Object[] getRegle_id() {
		return regle_id;
	}

	public void setRegle_id(Object[] regle_id) {
		this.regle_id = regle_id;
	}

	public void selectRegle() {

		listeAlerte = fraude_service.getReglesByMSISDN(selected_msisdn[0].toString());

	}

	public void loadIdentitéCdr() {
		selected_regle = fraude_service.getRegleById(Integer.valueOf(selected_alerte[1].toString()));
		System.out.println(selected_alerte[6]);
		switch (selected_regle.getFlux().getFlowName()) {
		case "msc":
			cdrs = fraude_service.getCDRByTypeRegle("msc" + selected_alerte[6].toString().substring(0, 6),
					"where appelant = '"
							+ selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()) + "");
			break;
		case "IN":
			cdrs = fraude_service.getCDRByTypeRegle("in" + selected_alerte[6].toString().substring(0, 6),
					"where appelant = '"
							+ selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()) + "");
			break;
		case "sys fact":
			cdrs = fraude_service.getCDRByTypeRegle("sysfact" + selected_alerte[6].toString().substring(0, 6),
					"where appelant = '"
							+ selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()) + "");
			break;
		case "TAP IN":
			cdrs = fraude_service.getCDRByTypeRegle("tapin" + selected_alerte[6].toString().substring(0, 6),
					"where appelant = '"
							+ selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()) + "");
			break;
		case "TAP OUT":
			cdrs = fraude_service.getCDRByTypeRegle("tapout" + selected_alerte[6].toString().substring(0, 6),
					"where appelant = '"
							+ selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()) + "");
			break;

		default:
			break;
		}
		if (cdrs != null)
			afficheCDR = true;

		cdrs_in_activations = fraude_service.getCDRBActivatonyTypeRegle(selected_alerte[6].toString().substring(0, 6),
				"where servermsisdn = '" + selected_msisdn[0].toString());
		if (cdrs_in_activations != null)
			affichactivation = true;

		cdrs_in_recharge = fraude_service.getCDRRechargeByTypeRegle(selected_alerte[6].toString().substring(0, 6),
				"where servermsisdn = '" + selected_msisdn[0].toString());
		if (cdrs_in_recharge != null)
			affichrecharge = true;

		cdrs_in_transfert = fraude_service.getCDRTransfertByTypeRegle(selected_alerte[6].toString().substring(0, 6),
				"where servermsisdn = '" + selected_msisdn[0].toString());
		if (cdrs_in_transfert != null)
			affichtransfert = true;
		liste_parametres = fraude_service.getParametres(selected_regle, selected_msisdn[0].toString());

		identities = fraude_service.getDecisions(selected_msisdn[0].toString());
		simpleModel = new DefaultMapModel();
		LatLng coord1 = new LatLng(36.879466, 30.667648);
		LatLng coord2 = new LatLng(36.883707, 30.689216);
		LatLng coord3 = new LatLng(36.879703, 30.706707);
		LatLng coord4 = new LatLng(36.885233, 30.702323);
		simpleModel.addOverlay(new Marker(coord1, "Konyaalti"));
		simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
		simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
		simpleModel.addOverlay(new Marker(coord4, "Kaleici"));

	}

	public void BlackList() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("B");
		df.setMsisdn(selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()));
		df.setRegle(selected_regle);
		fraude_service.decision(df, selected_msisdn[0].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void WhiteList() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("W");
		df.setRegle(selected_regle);
		df.setMsisdn(selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()));

		fraude_service.decision(df, selected_msisdn[0].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void Supprimer() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("S");
		df.setRegle(selected_regle);
		df.setMsisdn(selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()));

		fraude_service.decision(df, selected_msisdn[0].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	public void Desactiver() {
		DecisionFraude df = new DecisionFraude();
		df.setDecision("D");
		df.setMsisdn(selected_msisdn[0].toString().substring(3, selected_msisdn[0].toString().length()));
		df.setRegle(selected_regle);
		fraude_service.decision(df, selected_msisdn[0].toString());
		listeAlerte = fraude_service.getAlerteFraudeByRegle(selected_regle);
	}

	private MapModel simpleModel;

	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}

	private Marker marker;

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	// Shared coordinates

	public void onMarkerSelect(OverlaySelectEvent event) {
		marker = (Marker) event.getOverlay();

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Selected", marker.getLatlng() + ""));
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

	public boolean isAffichrecharge() {
		return affichrecharge;
	}

	public void setAffichrecharge(boolean affichrecharge) {
		this.affichrecharge = affichrecharge;
	}

	public boolean isAffichtransfert() {
		return affichtransfert;
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

	////////////////////////////////

	private String typeDate;
	private boolean parDate = false;

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
			listeAlerte = decision_service.getAllDecisionFraudesBY("where  date_detection Between to_date(" + "'" + deb0
					+ " 00:00:00'" + ",'yyyy-MM-dd HH24:MI:SS') And to_date(" + "'" + fin0 + " 23:59:59'"
					+ ",'yyyy-MM-dd HH24:MI:SS') group by id_regle,msisdn");

		} else {
			listeAlerte = decision_service
					.getAllDecisionFraudesBY("where   to_char(dateDecision,'yyyy-MM-dd')  = to_char(to_date(" + "'"
							+ deb0 + "'" + ",'yyyy-MM-dd'),'yyyy-MM-dd')  group by id_regle,msisdn");

		}

	}
	///////////////////////////////

}
