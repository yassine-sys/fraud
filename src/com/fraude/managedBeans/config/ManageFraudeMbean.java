package com.fraude.managedBeans.config;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

import com.fraude.entities.FiltresReglesFraude;
import com.fraude.entities.Flow;
import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.DetailsMail;
import com.fraude.entities.DetailsSms;
import com.fraude.entities.FiltresFraude;
import com.fraude.entities.FluxCdr;
import com.fraude.entities.ListAppelant;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListCellid;
import com.fraude.entities.ListImei;
import com.fraude.entities.Offre;
import com.fraude.entities.ParametresFraude;
import com.fraude.entities.ParametresReglesFraude;
import com.fraude.entities.PlanTarifaire;
import com.fraude.entities.ReglesFraude;
import com.fraude.entities.TypeDestination;
import com.fraude.entities.Util;
import com.fraude.interfaces.FraudeHotlistRemote;
import com.fraude.interfaces.FraudeManagerRemote;
import com.fraude.interfaces.OffreRemote;
import com.fraude.interfaces.PlanTarifaireRemote;
import com.fraude.interfaces.TypeDestinationRemote;

@ManagedBean(name = "manage_fraude")
@ViewScoped
public class ManageFraudeMbean implements Serializable {

	public List<DetailsMail> getListregleMails() {
		return ListregleMails;
	}

	public void setListregleMails(List<DetailsMail> listregleMails) {
		ListregleMails = listregleMails;
	}

	public boolean isRecap() {
		return recap;
	}

	public void setRecap(boolean recap) {
		this.recap = recap;
	}

	public boolean isInstantané() {
		return instantané;
	}

	public void setInstantané(boolean instantané) {
		this.instantané = instantané;
	}

	public DetailsMail getDetails_mail() {
		return details_mail;
	}

	public void setDetails_mail(DetailsMail details_mail) {
		this.details_mail = details_mail;
	}

	public DetailsMail getDetails_mail1() {
		return details_mail1;
	}

	public void setDetails_mail1(DetailsMail details_mail1) {
		this.details_mail1 = details_mail1;
	}

	public DetailsSms getDetails_sms() {
		return details_sms;
	}

	public void setDetails_sms(DetailsSms details_sms) {
		this.details_sms = details_sms;
	}

	public DetailsMail getSelect_det_mail() {
		return select_det_mail;
	}

	public void setSelect_det_mail(DetailsMail select_det_mail) {
		this.select_det_mail = select_det_mail;
	}

	public DetailsSms getSelect_det_sms() {
		return select_det_sms;
	}

	public void setSelect_det_sms(DetailsSms select_det_sms) {
		this.select_det_sms = select_det_sms;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private FraudeManagerRemote fraude_service;

	@EJB
	private FraudeHotlistRemote hotlist_service;

	@EJB
	private OffreRemote offre_service;

	private List<DetailsMail> ListregleMails = new ArrayList<>();

	private List<DetailsSms> ListregleSms;

	private boolean recap;

	private boolean instantané;

	private DetailsMail details_mail;
	private DetailsMail details_mail1;
	private DetailsSms details_sms;
	private List<Flow> fluxs;

	private Flow flow;
	private DetailsMail select_det_mail;
	private DetailsSms select_det_sms;

	private List<Offre> lst_offre = new ArrayList<>();

	public List<Offre> getLst_offre() {
		return lst_offre;
	}

	public void setLst_offre(List<Offre> lst_offre) {
		this.lst_offre = lst_offre;
	}

	private List<ReglesFraude> liste_regle_fraude = new ArrayList<>();

	private List<SelectItem> TypeBlocageFilterOptions;

	private List<SelectItem> EtatFilterOptions;
	private List<SelectItem> CategoriesFilterOptions;

	private List<SelectItem> FluxFilterOptions;

	public List<SelectItem> getCategoriesFilterOptions() {
		return CategoriesFilterOptions;
	}

	public void setCategoriesFilterOptions(List<SelectItem> categoriesFilterOptions) {
		CategoriesFilterOptions = categoriesFilterOptions;
	}

	public List<SelectItem> getEtatFilterOptions() {
		return EtatFilterOptions;
	}

	public List<SelectItem> getFluxFilterOptions() {
		return FluxFilterOptions;
	}

	public List<SelectItem> getTypeBlocageFilterOptions() {
		return TypeBlocageFilterOptions;
	}

	public void setEtatFilterOptions(List<SelectItem> etatFilterOptions) {
		EtatFilterOptions = etatFilterOptions;
	}

	public void setFluxFilterOptions(List<SelectItem> fluxFilterOptions) {
		FluxFilterOptions = fluxFilterOptions;
	}

	public void setTypeBlocageFilterOptions(List<SelectItem> typeBlocageFilterOptions) {
		TypeBlocageFilterOptions = typeBlocageFilterOptions;
	}

	private ReglesFraude selected_regle;
	private ParametresReglesFraude selected_parametre;
	private FiltresReglesFraude selected_filtre;
	private ParametresReglesFraude nouveauparametre;
	private FiltresReglesFraude nouveaufiltre;
	private List<Flow> parametres;
	private List<ParametresReglesFraude> listparamregle;
	private List<FiltresReglesFraude> listfiltreregle;

	private List<FiltresFraude> filtres;
	private String choix_param_valeur;
	private String choix_filtre_valeur;
	private Integer param_valeur;
	private String filtre_valeur;

	public String getFiltre_valeur() {
		return filtre_valeur;
	}

	public void setFiltre_valeur(String filtre_valeur) {
		this.filtre_valeur = filtre_valeur;
	}

	@EJB
	private TypeDestinationRemote type_dest_service;

	private List<CategoriesFraude> lstCategories = new ArrayList<>();

	private List<Flow> lstFlux = new ArrayList<>();

	public List<CategoriesFraude> getLstCategories() {
		return lstCategories;
	}

	public void setLstCategories(List<CategoriesFraude> lstCategories) {
		this.lstCategories = lstCategories;
	}

	@EJB
	private PlanTarifaireRemote plan_service;
	private List<PlanTarifaire> plans = new ArrayList<>();

	private List<TypeDestination> typeDests = new ArrayList<>();

	@PostConstruct
	public void init() {

		details_mail1 = new DetailsMail();
		ListregleMails = new ArrayList<>();
		details_mail = new DetailsMail();
		details_sms = new DetailsSms();
		select_det_mail = new DetailsMail();
		details_sms = new DetailsSms();
		ListregleSms = new ArrayList<>();

		selected_regle = new ReglesFraude();

		liste_regle_fraude = fraude_service.getAllFraudes("");
		listfiltreregle = new ArrayList<>();
		listparamregle = new ArrayList<>(); 
		
		selected_regle = new ReglesFraude();
		lstFlux = fraude_service.getAllFlux2();
		lstCategories = fraude_service.getAllCategories();
		selected_filtre = new FiltresReglesFraude();
		selected_parametre = new ParametresReglesFraude();
		parametres = new ArrayList<>();
		filtres = new ArrayList<>();
		nouveaufiltre = new FiltresReglesFraude();
		nouveauparametre = new ParametresReglesFraude();
		
		
		CategoriesFilterOptions = new ArrayList<SelectItem>(fraude_service.getAllCategories().size() + 1);
		CategoriesFilterOptions.add(new SelectItem("", "Tous"));
		for (CategoriesFraude d : fraude_service.getAllCategories()) {
			CategoriesFilterOptions.add(new SelectItem(d.getNomCategorie(), d.getNomCategorie()));
		}
		
		
		FluxFilterOptions = new ArrayList<SelectItem>(fraude_service.getAllFlux().size() + 1);
		FluxFilterOptions.add(new SelectItem("", "Tous"));
		for (Flow d : fraude_service.getAllFlux()) {
			FluxFilterOptions.add(new SelectItem(d.getFlowName(), d.getFlowName()));
		}
		
		
		EtatFilterOptions = new ArrayList<SelectItem>(2);
		EtatFilterOptions.add(new SelectItem("", "Tous"));
		EtatFilterOptions.add(new SelectItem("A", "Activï¿½"));
		EtatFilterOptions.add(new SelectItem("D", "Dï¿½sactivï¿½"));
		
		
		TypeBlocageFilterOptions = new ArrayList<SelectItem>(2);
		TypeBlocageFilterOptions.add(new SelectItem("", "Tous"));
		TypeBlocageFilterOptions.add(new SelectItem("0", "Automatique"));
		TypeBlocageFilterOptions.add(new SelectItem("1", "Manuel"));
	}

	public List<Flow> getLstFlux() {
		return lstFlux;
	}

	public void setLstFlux(List<Flow> lstFlux) {
		this.lstFlux = lstFlux;
	}

	
	
	
	public void updateRegle() {
		selected_regle.setNomUtilisateur(Util.getUserName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		selected_regle.setDateModif(currentTimestamp);
		fraude_service.updateRegle(selected_regle);
		liste_regle_fraude = fraude_service.getAllFraudes("");
	}

	
	
	private boolean ListTypeDest = false;

	public boolean isListTypeDest() {
		return ListTypeDest;
	}

	public void setListTypeDest(boolean list) {
		ListTypeDest = list;
	}

	private boolean ListPlanTarifaire = false;

	public boolean isListPlanTarifaire() {
		return ListPlanTarifaire;
	}

	public void setListPlanTarifaire(boolean isListPlanTarifaire) {
		this.ListPlanTarifaire = isListPlanTarifaire;
	}

	private boolean text = true;

	public boolean isText() {
		return text;
	}

	public void setText(boolean text) {
		this.text = text;
	}

	private boolean typeAppel;

	public boolean isTypeAppel() {
		return typeAppel;
	}

	public void setTypeAppel(boolean typeAppel) {
		this.typeAppel = typeAppel;
	}

	private boolean typelocroa;

	public boolean isTypelocroa() {
		return typelocroa;
	}

	public void setTypelocroa(boolean typelocroa) {
		this.typelocroa = typelocroa;
	}

	private boolean listin = false;

	public boolean isListin() {
		return listin;
	}

	public void setListin(boolean listin) {
		this.listin = listin;
	}

	private boolean listinappelant = false;

	public boolean isListinappelant() {
		return listinappelant;
	}

	public void setListinappelant(boolean listinappelant) {
		this.listinappelant = listinappelant;
	}

	private List<ListCellid> lstcellid;
	private List<ListAppelant> lstappelant;
	private boolean listinappele = false;
	private boolean listinimei = false;

	public boolean isListinappele() {
		return listinappele;
	}

	public void setListinimei(boolean listinimei) {
		this.listinimei = listinimei;
	}

	public boolean isListinimei() {
		return listinimei;
	}

	public void setListinappele(boolean listinappele) {
		this.listinappele = listinappele;
	}

	private List<ListAppele> lstappele;
	private List<ListImei> lstimei;

	public List<ListAppele> getLstappele() {
		return lstappele;
	}

	public List<ListImei> getLstimei() {
		return lstimei;
	}

	public void setLstimei(List<ListImei> lstimei) {
		this.lstimei = lstimei;
	}

	public void setLstappele(List<ListAppele> lstappele) {
		this.lstappele = lstappele;
	}

	public List<ListAppelant> getLstappelant() {
		return lstappelant;
	}

	public void setLstappelant(List<ListAppelant> lstappelant) {
		this.lstappelant = lstappelant;
	}

	public List<ListCellid> getLstcellid() {
		return lstcellid;
	}

	public void setLstcellid(List<ListCellid> lstcellid) {
		this.lstcellid = lstcellid;
	}

	private boolean listinoffre = false;

	public boolean isListinoffre() {
		return listinoffre;
	}

	public void setListinoffre(boolean listinoffre) {
		this.listinoffre = listinoffre;
	}

	private String filtrefraude;

	public String getFiltrefraude() {
		return filtrefraude;
	}

	public void setFiltrefraude(String filtrefraude) {
		this.filtrefraude = filtrefraude;
	}

	public void ajouterRegle() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("Ajout_regle.xhtml");
	}

	public void selectChange(AjaxBehaviorEvent event) {
		ListTypeDest = false;
		listinoffre = false;
		listin = false;
		listinappelant = false;
		ListPlanTarifaire = false;
		text = true;
		typeAppel = false;
		typelocroa = false;
		listinimei = false;
		listinappele = false;

		if (filtrefraude.equals("type destination")) {
			typeDests = type_dest_service.getAllTypeDest();
			ListTypeDest = true;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;

		} else if (filtrefraude.equals("Offre")) {
			lst_offre = offre_service.getAllOffres();
			ListTypeDest = false;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			listinoffre = true;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (filtrefraude.equals("plan tarifaire")) {
			plans = plan_service.getAllPlanTarifaires();
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			listinappelant = false;
			ListPlanTarifaire = true;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (filtrefraude.equals("Type Appel")) {
			ListTypeDest = false;
			listin = false;
			listinappelant = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = true;
			typelocroa = false;
			listinimei = false;
			listinappele = false;

		} else if (filtrefraude.equals("Type msisdn")) {
			ListTypeDest = false;
			listinoffre = false;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = true;
			listinimei = false;
			listinappele = false;
		} else if (filtrefraude.equals("CellId")) {
			if (this.getChoix_filtre_valeur().equals("in")) {
				lstcellid = hotlist_service.getAllCellId();
				ListTypeDest = false;
				listin = true;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = false;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			} else {

				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}

		} else if (filtrefraude.equals("Appelant")) {
			if (this.getChoix_filtre_valeur().equals("in")) {
				lstappelant = hotlist_service.getAllAppelant();
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = true;
				ListPlanTarifaire = false;
				text = false;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			} else {
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}

		} else if (filtrefraude.equals("Appele")) {
			if (this.getChoix_filtre_valeur() != null) {
				if (this.getChoix_filtre_valeur().equals("in")) {
					lstappele = hotlist_service.getAllAppele();
					ListTypeDest = false;
					listin = false;
					listinoffre = false;
					listinappelant = false;
					ListPlanTarifaire = false;
					text = false;
					typeAppel = false;
					typelocroa = false;
					listinimei = false;
					listinappele = true;
				} else {

					ListTypeDest = false;
					listin = false;
					listinoffre = false;
					listinappelant = false;
					ListPlanTarifaire = false;
					text = true;
					typeAppel = false;
					typelocroa = false;
					listinimei = false;
					listinappele = false;
				}

			} else {
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}

		} else if (filtrefraude.equals("IMEI") && this.getChoix_filtre_valeur().equals("in")) {
			lstimei = hotlist_service.getAllImei();
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = true;
			listinappele = false;
		}

		else {
			ListTypeDest = false;
			listinoffre = false;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = true;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		}

	}

	private FiltresReglesFraude fr;

	public FiltresReglesFraude getFr() {
		return fr;
	}

	public void setFr(FiltresReglesFraude fr) {
		this.fr = fr;
	}

	private List<Object[]> listdetails;

	public List<Object[]> getListdetails() {
		return listdetails;
	}

	public void setListdetails(List<Object[]> listdetails) {
		this.listdetails = listdetails;
	}

	private String nameCellid;

	public String getNameCellid() {
		return nameCellid;
	}

	public void setNameCellid(String nameCellid) {
		this.nameCellid = nameCellid;
	}

	public void getHotlistCellid() {
		switch (fr.getFiltreFraude().getFiltre()) {
		case "Appelant":
			listdetails = hotlist_service.getListDetailsFromManageRegle("tableref.list_details_appelant",
					Integer.valueOf(fr.getInegal()));
			nameCellid = (String) hotlist_service.getFromRegle("ListDetailsAppelant", Integer.valueOf(fr.getInegal()));
			break;
		case "Appele":
			listdetails = hotlist_service.getListDetailsFromManageRegle("tableref.list_details_appelant",
					Integer.valueOf(fr.getInegal()));
			nameCellid = (String) hotlist_service.getFromRegle("ListAppele", Integer.valueOf(fr.getInegal()));
			break;
		case "CellId":
			listdetails = hotlist_service.getListDetailsFromManageRegle("tableref.list_details_cellid",
					Integer.valueOf(fr.getInegal()));
			nameCellid = (String) hotlist_service.getFromRegle("ListCellid", Integer.valueOf(fr.getInegal()));
			break;
		case "Imei":
			listdetails = hotlist_service.getListDetailsFromManageRegle("tableref.list_details_imei",
					Integer.valueOf(fr.getInegal()));
			nameCellid = (String) hotlist_service.getFromRegle("ListImei", Integer.valueOf(fr.getInegal()));
			break;

		default:
			break;
		}
	}

	public void selectChange1() {

		if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("type destination")) {
			typeDests = type_dest_service.getAllTypeDest();
			ListTypeDest = true;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("plan tarifaire")) {
			plans = plan_service.getAllPlanTarifaires();
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			listinappelant = false;
			ListPlanTarifaire = true;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getSelected_filtre().getFiltreFraude().equals("Appelant")) {
			if (this.getChoix_filtre_valeur1().equals("in")) {
				lstappelant = hotlist_service.getAllAppelant();
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = true;
				ListPlanTarifaire = false;
				text = false;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			} else {
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}

		}

		else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("Offre")) {
			lst_offre = offre_service.getAllOffres();
			ListTypeDest = false;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			listinoffre = true;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("Appele")) {
			if (this.getChoix_filtre_valeur1() != null) {
				if (this.getChoix_filtre_valeur1().equals("in")) {
					lstappele = hotlist_service.getAllAppele();
					ListTypeDest = false;
					listin = false;
					listinoffre = false;
					listinappelant = false;
					ListPlanTarifaire = false;
					text = false;
					typeAppel = false;
					typelocroa = false;
					listinimei = false;
					listinappele = true;
				} else {

					ListTypeDest = false;
					listin = false;
					listinoffre = false;
					listinappelant = false;
					ListPlanTarifaire = false;
					text = true;
					typeAppel = false;
					typelocroa = false;
					listinimei = false;
					listinappele = false;
				}

			} else {
				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}
		} else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("IMEI")
				&& this.getChoix_filtre_valeur1().equals("in")) {
			lstimei = hotlist_service.getAllImei();
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = true;
			listinappele = false;
		} else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("CellId")) {
			if (this.getChoix_filtre_valeur1().equals("in")) {
				lstcellid = hotlist_service.getAllCellId();
				ListTypeDest = false;
				listin = true;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = false;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			} else {

				ListTypeDest = false;
				listin = false;
				listinoffre = false;
				listinappelant = false;
				ListPlanTarifaire = false;
				text = true;
				typeAppel = false;
				typelocroa = false;
				listinimei = false;
				listinappele = false;
			}

		}

		else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("Type Appel")) {
			ListTypeDest = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = true;
			typelocroa = false;

		} else if (this.getSelected_filtre().getFiltreFraude().getFiltre().equals("Type msisdn")) {
			ListTypeDest = false;
			listin = false;
			listinappelant = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = true;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else {
			ListTypeDest = false;
			listinoffre = false;
			listin = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = true;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		}

	}

	private String choix_filtre_valeur1;

	public String getChoix_filtre_valeur1() {
		return choix_filtre_valeur1;
	}

	public void setChoix_filtre_valeur1(String choix_filtre_valeur1) {
		this.choix_filtre_valeur1 = choix_filtre_valeur1;
	}

	public void ajouter_filtre() {

		switch (this.getChoix_filtre_valeur()) {
		case "egale":
			nouveaufiltre.setVegal(filtre_valeur);
			nouveaufiltre.setVdef("-1");
			nouveaufiltre.setInegal("-1");
			nouveaufiltre.setVlike("-1");
			nouveaufiltre.setVnotlike("-1");

			break;
		case "like":
			nouveaufiltre.setVegal("-1");
			nouveaufiltre.setVdef("-1");
			nouveaufiltre.setInegal("-1");
			nouveaufiltre.setVlike(filtre_valeur);
			nouveaufiltre.setVnotlike("-1");

			break;
		case "not like":
			nouveaufiltre.setVegal("-1");
			nouveaufiltre.setVdef("-1");
			nouveaufiltre.setInegal("-1");
			nouveaufiltre.setVlike("-1");
			nouveaufiltre.setVnotlike(this.getFiltre_valeur());

			break;
		case "different":
			nouveaufiltre.setVegal("-1");
			nouveaufiltre.setVdef(this.getFiltre_valeur());
			nouveaufiltre.setInegal("-1");
			nouveaufiltre.setVlike("-1");
			nouveaufiltre.setVnotlike("-1");

			break;
		case "in":
			nouveaufiltre.setVegal("-1");
			nouveaufiltre.setVdef("-1");
			nouveaufiltre.setInegal(this.getFiltre_valeur());
			nouveaufiltre.setVlike("-1");
			nouveaufiltre.setVnotlike("-1");

			break;

		default:
			break;
		}
		nouveaufiltre.setFiltreFraude(fraude_service.getFiltreById(filtrefraude));
		nouveaufiltre.setNomUtlisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		nouveaufiltre.setDateModif(currentTimestamp);

		nouveaufiltre.setRegle(selected_regle);
		fraude_service.addFiltre(nouveaufiltre);
		listfiltreregle = fraude_service.getFiltresReglesFraudeByRegle(selected_regle);

		nouveaufiltre = new FiltresReglesFraude();
		filtrefraude = "";
		choix_filtre_valeur = "";
		filtre_valeur = "";

		// fraude_service.addParametre(nouveauparametre);
		// listparamregle =
		// fraude_service.getParametresReglesFraudeByRegle(selected_regle);
		// selected_regle.setListe_parameters(selected_regle.getListe_parameters());

	}

	private String filtre_valeur1;

	public String getFiltre_valeur1() {
		return filtre_valeur1;
	}

	public void setFiltre_valeur1(String filtre_valeur1) {
		this.filtre_valeur1 = filtre_valeur1;
	}

	public void update_filtre() {

		switch (this.getChoix_filtre_valeur1()) {
		case "egale":
			selected_filtre.setVegal(this.getFiltre_valeur1());
			selected_filtre.setVdef("-1");
			selected_filtre.setInegal("-1");
			selected_filtre.setVlike("-1");
			selected_filtre.setVnotlike("-1");

			break;
		case "like":
			selected_filtre.setVegal("-1");
			selected_filtre.setVdef("-1");
			selected_filtre.setInegal("-1");
			selected_filtre.setVlike(filtre_valeur1);
			selected_filtre.setVnotlike("-1");

			break;
		case "not like":
			selected_filtre.setVegal("-1");
			selected_filtre.setVdef("-1");
			selected_filtre.setInegal("-1");
			selected_filtre.setVlike("-1");
			selected_filtre.setVnotlike(filtre_valeur1);

			break;
		case "different":
			selected_filtre.setVegal("-1");
			selected_filtre.setVdef(filtre_valeur1);
			selected_filtre.setInegal("-1");
			selected_filtre.setVlike("-1");
			selected_filtre.setVnotlike("-1");

			break;
		case "in":
			selected_filtre.setVegal("-1");
			selected_filtre.setVdef("-1");
			selected_filtre.setInegal(filtre_valeur1);
			selected_filtre.setVlike("-1");
			selected_filtre.setVnotlike("-1");

			break;

		default:
			break;
		}
		selected_filtre.setNomUtlisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		selected_filtre.setDateModif(currentTimestamp);

		selected_filtre.setRegle(this.getSelected_regle());

		fraude_service.updateFiltre(this.getSelected_filtre());
		listfiltreregle = fraude_service.getFiltresReglesFraudeByRegle(selected_regle);
		selected_filtre = new FiltresReglesFraude();
		choix_filtre_valeur1 = "";
		filtre_valeur1 = "";

		// fraude_service.updateRegle(this.getSelected_regle());
		// fraude_service.addParametre(nouveauparametre);
		// listparamregle =
		// fraude_service.getParametresReglesFraudeByRegle(selected_regle);
		// selected_regle.setListe_parameters(selected_regle.getListe_parameters());
	}

	public void ajoutParam() {
		System.out.println("h1");

		boolean trouve = false;
		int i = 0;
		if (listparamregle != null) {

			System.out.println("h2" + nouveauparametre.getParametreFraudeFlow().getFlowName());

			System.out.println(trouve);
			while (i < listparamregle.size() && trouve == false) {
				if (nouveauparametre.getParametreFraudeFlow().getFlowName()
						.equals(listparamregle.get(i).getFlow().getFlowName())) {

					// System.out.println(("h3"+nouveauparametre.getParametreFraudeFlow().getFlowName()
					// .equals(listparamregle.get(i).getParametreFraudeFlow().getFlowName())));
					trouve = true;
				} else {
					i++;
				}
				System.out.println(trouve);
			}
		}
		System.out.println(trouve);
		if (trouve == false) {
			System.out.println("marche");
			switch (choix_param_valeur) {
			case "egale":
				nouveauparametre.setVegal(this.getParam_valeur());
				nouveauparametre.setVmax(-1);
				nouveauparametre.setVmin(-1);

				break;
			case "superieur":
				nouveauparametre.setVegal(-1);
				nouveauparametre.setVmax(-1);
				nouveauparametre.setVmin(this.getParam_valeur());

				break;
			case "inferieur":
				nouveauparametre.setVegal(-1);
				nouveauparametre.setVmax(this.getParam_valeur());
				nouveauparametre.setVmin(-1);

				break;

			default:
				break;
			}

			nouveauparametre.setNomUtilisateur(Util.getUser().getUName());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			nouveauparametre.setDateModif(currentTimestamp);
			nouveauparametre.setRegle(this.getSelected_regle());

			fraude_service.addParametre(nouveauparametre);
			listparamregle = fraude_service.getParametresReglesFraudeByRegle(selected_regle);
			nouveauparametre = new ParametresReglesFraude();
			choix_param_valeur = "";
			param_valeur = 0;
		} else {
			FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cette Parametre est dï¿½ja ajoutï¿½", "Veuillez choisir les parametres"));

		}
		// fraude_service.addParametre(nouveauparametre);
		// listparamregle =
		// fraude_service.getParametresReglesFraudeByRegle(selected_regle);
		// selected_regle.setListe_parameters(selected_regle.getListe_parameters());

	}

	public void update_param() {

		switch (this.getChoix_param_valeur()) {
		case "egale":
			selected_parametre.setVegal(param_valeur);
			selected_parametre.setVmax(-1);
			selected_parametre.setVmin(-1);

			break;
		case "superieur":
			selected_parametre.setVegal(-1);
			selected_parametre.setVmax(-1);
			selected_parametre.setVmin(param_valeur);

			break;
		case "inferieur":
			selected_parametre.setVegal(-1);
			selected_parametre.setVmax(param_valeur);
			selected_parametre.setVmin(-1);

			break;

		default:
			break;
		}

		selected_parametre.setRegle(selected_regle);
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		selected_parametre.setDateModif(currentTimestamp);

		// selected_regle.setListe_parameters(selected_regle.getListe_parameters());
		//selected_regle.setListe_parameters(selected_regle.getListe_parameters());
		fraude_service.updateParametre(selected_parametre);
		listparamregle = fraude_service.getParametresReglesFraudeByRegle(selected_regle);
		selected_parametre = new ParametresReglesFraude();
		// listparamregle=
		// fraude_service.getParametresReglesFraudeByRegle(selected_regle);
	}

	public void deleteParam() {

		fraude_service.deleteParametre(selected_parametre);
		listparamregle = fraude_service.getParametresReglesFraudeByRegle(selected_regle);
	}

	public void deleteFiltre() {
		fraude_service.deleteFiltre(selected_filtre);
		listfiltreregle.remove(selected_filtre);

		listfiltreregle = fraude_service.getFiltresReglesFraudeByRegle(selected_regle);
	}

	public void loadparametres(SelectEvent event) {
		listfiltreregle = fraude_service.getFiltresReglesFraudeByRegle(selected_regle);
		listparamregle = fraude_service.getParametresReglesFraudeByRegle(selected_regle);

		// String where = "p.flux.id=" + selected_regle.getFlux().getId();
		String where = "f.flow_type= 6";
		// parametres = fraude_service.getParametresFraude(where);
		parametres = fraude_service.getParametresFraude2(where);
		ListregleMails = fraude_service.getAlerteFraudeByRegleMail(selected_regle);
		ListregleSms = fraude_service.getAlerteFraudeByRegleSms(selected_regle);
		// where = "f.flux.id=" + selected_regle.getFlux().getId();
		filtres = fraude_service.getFilterFiltresFraude2();

		nouveaufiltre = new FiltresReglesFraude();

		System.out.println(filtres.size());
		System.out.println(parametres.size());

	}

	public List<Flow> getFluxs() {
		return fluxs;
	}

	public void setFluxs(List<Flow> fluxs) {
		this.fluxs = fluxs;
	}

	public List<Flow> getParametres() {
		return parametres;
	}

	public void setParametres(List<Flow> parametres) {
		this.parametres = parametres;
	}

	public void blocageRegle() {

		selected_regle.setType("0");
		fraude_service.updateRegle(selected_regle);
		liste_regle_fraude = fraude_service.getAllFraudes("");

	}

	public void nonblocageRegle() {

		selected_regle.setType("1");
		fraude_service.updateRegle(selected_regle);
		liste_regle_fraude = fraude_service.getAllFraudes("");

	}

	public void desactiverRegle() {

		selected_regle.setEtat("D");
		fraude_service.updateRegle(selected_regle);
		liste_regle_fraude = fraude_service.getAllFraudes("");

	}

	public void activerRegle() {

		selected_regle.setEtat("A");
		fraude_service.updateRegle(selected_regle);
		liste_regle_fraude = fraude_service.getAllFraudes("");

	}

	public Integer getParam_valeur() {
		return param_valeur;
	}

	public void setParam_valeur(Integer param_valeur) {
		this.param_valeur = param_valeur;
	}

	public String getChoix_filtre_valeur() {
		return choix_filtre_valeur;
	}

	public void setChoix_filtre_valeur(String choix_filtre_valeur) {
		this.choix_filtre_valeur = choix_filtre_valeur;
	}

	public String getChoix_param_valeur() {
		return choix_param_valeur;
	}

	public void setChoix_param_valeur(String choix_param_valeur) {
		this.choix_param_valeur = choix_param_valeur;
	}

	public FiltresReglesFraude getNouveaufiltre() {
		return nouveaufiltre;
	}

	public void setNouveaufiltre(FiltresReglesFraude nouveaufiltre) {
		this.nouveaufiltre = nouveaufiltre;
	}

	public ParametresReglesFraude getNouveauparametre() {
		return nouveauparametre;
	}

	public void setNouveauparametre(ParametresReglesFraude nouveauparametre) {
		this.nouveauparametre = nouveauparametre;
	}

	public List<ReglesFraude> getListe_regle_fraude() {
		return liste_regle_fraude;
	}

	public void setListe_regle_fraude(List<ReglesFraude> liste_regle_fraude) {
		this.liste_regle_fraude = liste_regle_fraude;
	}

	public ReglesFraude getSelected_regle() {
		return selected_regle;
	}

	public void setSelected_regle(ReglesFraude selected_regle) {
		this.selected_regle = selected_regle;
	}

	public ParametresReglesFraude getSelected_parametre() {
		return selected_parametre;
	}

	public void setSelected_parametre(ParametresReglesFraude selected_parametre) {
		this.selected_parametre = selected_parametre;
	}

	public FiltresReglesFraude getSelected_filtre() {
		return selected_filtre;
	}

	public void setSelected_filtre(FiltresReglesFraude selected_filtre) {
		this.selected_filtre = selected_filtre;
	}

	public List<FiltresFraude> getFiltres() {
		return filtres;
	}

	public void setFiltres(List<FiltresFraude> filtres) {
		this.filtres = filtres;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<FiltresReglesFraude> getListfiltreregle() {
		return listfiltreregle;
	}

	public void setListfiltreregle(List<FiltresReglesFraude> listfiltreregle) {
		this.listfiltreregle = listfiltreregle;
	}

	public List<ParametresReglesFraude> getListparamregle() {
		return listparamregle;
	}

	public void setListparamregle(List<ParametresReglesFraude> listparamregle) {
		this.listparamregle = listparamregle;
	}

	public List<TypeDestination> getTypeDests() {
		return typeDests;
	}

	public void setPlans(List<PlanTarifaire> plans) {
		this.plans = plans;
	}

	public void setTypeDests(List<TypeDestination> typeDests) {
		this.typeDests = typeDests;
	}

	public List<PlanTarifaire> getPlans() {
		return plans;
	}

	public void AjouterAlerteMail() {

		details_mail.setAdresseMail(details_mail.getAdresseMail());
		details_mail.setType(details_mail.getType());
		details_mail.setId_Reg(selected_regle);
		fraude_service.addAlerteMail(details_mail);

		details_mail = new DetailsMail();
		ListregleMails = fraude_service.getAlerteFraudeByRegleMail(selected_regle);

	}

	private Integer byId;

	public void deleteDetailMail() {

		// select_det_mail = new DetailsMail();

		// fraude_service.deleteDetailMails(select_det_mail.getId());
		fraude_service.deleteDetailMails(details_mail1);

		ListregleMails = fraude_service.getAlerteFraudeByRegleMail(selected_regle);
	}

	public Integer getById() {
		return byId;
	}

	public void setById(Integer byId) {
		this.byId = byId;
	}

	public List<DetailsSms> getListregleSms() {
		return ListregleSms;
	}

	public void setListregleSms(List<DetailsSms> listregleSms) {
		ListregleSms = listregleSms;
	}

	public void AjouterAlerteSms() {

		details_sms.setMsisdn(details_sms.getMsisdn());
		details_sms.setType(details_sms.getType());
		// details_sms.setId_Reg(selected_regle);
		System.out.println("id regle selected " + selected_regle.getId());
		fraude_service.addAlerteSms(details_sms, selected_regle.getId());
		details_sms = new DetailsSms();
		ListregleSms = fraude_service.getAlerteFraudeByRegleSms(selected_regle);

	}

	public void deleteDetailSms() {

		fraude_service.deleteDetailSms(details_sms);

		ListregleSms = fraude_service.getAlerteFraudeByRegleSms(selected_regle);
	}

}
