package com.fraude.managedBeans.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fraude.entities.FiltresReglesFraude;
import com.fraude.entities.Flow;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.DetailsMail;
import com.fraude.entities.DetailsSms;
import com.fraude.entities.FiltresFraude;
import com.fraude.entities.FluxCdr;
import com.fraude.entities.ListAppelant;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListCellid;
import com.fraude.entities.ListDetailsAppelant;
import com.fraude.entities.ListDetailsCellid;
import com.fraude.entities.ListImei;
import com.fraude.entities.Offre;
import com.fraude.entities.ParametresFraude;
import com.fraude.entities.ParametresReglesFraude;
import com.fraude.entities.PlanTarifaire;
import com.fraude.entities.ReglesFraude;
import com.fraude.entities.TblLog;
import com.fraude.entities.TypeDestination;
import com.fraude.entities.Util;
import com.fraude.entities.YourDataWrapper;
import com.fraude.interfaces.FraudeHotlistRemote;
import com.fraude.interfaces.FraudeManagerRemote;
import com.fraude.interfaces.OffreRemote;
import com.fraude.interfaces.PlanTarifaireRemote;
import com.fraude.interfaces.TypeDestinationRemote;
import com.fraude.managedBeans.decisions.BlocageMbean;
import com.fraude.managedBeans.decisions.BlocageMbean.Nmro;

@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "fraude")
@ViewScoped
@JsonIgnoreProperties(ignoreUnknown = true)

public class FraudeMbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private FraudeManagerRemote fraude_service;

	@EJB
	private OffreRemote offre_service;

	@EJB
	private TypeDestinationRemote type_dest_service;

	@EJB
	private PlanTarifaireRemote plan_service;

	@EJB
	private FraudeHotlistRemote hotlist_service;

	private List<Offre> lst_offres = new ArrayList<>();

	public List<Offre> getLst_offres() {
		return lst_offres;
	}

	public void setLst_offres(List<Offre> lst_offres) {
		this.lst_offres = lst_offres;
	}

	private List<DetailsSms> ListregleSms;

	private DetailsMail details_mail;
	private DetailsMail details_mail1;
	private DetailsSms details_sms;
	private List<DetailsMail> ListregleMails = new ArrayList<>();

	private ReglesFraude selected_regle;
	private Flow flow;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	private DetailsMail select_det_mail;
	private DetailsSms select_det_sms;

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

	public List<ParametresReglesFraude> getListe_parametre_regle() {
		return liste_parametre_regle;
	}

	public void setListe_parametre_regle(List<ParametresReglesFraude> liste_parametre_regle) {
		this.liste_parametre_regle = liste_parametre_regle;
	}

	private List<ListCellid> lstcellid;

	private List<ListAppelant> lstappelant;

	private List<ListImei> lstimei;

	public List<ListCellid> getLstcellid() {
		return lstcellid;
	}

	public void setLstcellid(List<ListCellid> lstcellid) {
		this.lstcellid = lstcellid;
	}

	public List<ListAppelant> getLstappelant() {
		return lstappelant;
	}

	public void setLstappelant(List<ListAppelant> lstappelant) {
		this.lstappelant = lstappelant;
	}

	public List<ListImei> getLstimei() {
		return lstimei;
	}

	public void setLstimei(List<ListImei> lstimei) {
		this.lstimei = lstimei;
	}

	private List<String> filters;

	public List<String> getFilters() {
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	private Integer id_flux;

	public Integer getId_flux() {
		return id_flux;
	}

	public void setId_flux(Integer id_flux) {
		this.id_flux = id_flux;
	}

	private List<PlanTarifaire> plans = new ArrayList<>();

	private List<TypeDestination> typeDests = new ArrayList<>();

	public List<TypeDestination> getTypeDests() {
		return typeDests;
	}

	private String choix_Vfiltre;

	private ParametresReglesFraude paramter_regle;

	public ParametresReglesFraude getParamter_regle() {
		return paramter_regle;
	}

	public void setParamter_regle(ParametresReglesFraude paramter_regle) {
		this.paramter_regle = paramter_regle;
	}

	public List<PlanTarifaire> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanTarifaire> plans) {
		this.plans = plans;
	}

	public void setTypeDests(List<TypeDestination> typeDests) {
		this.typeDests = typeDests;
	}

	public TypeDestinationRemote getType_dest_service() {
		return type_dest_service;
	}

	private Integer paramtreValeur;

	private String choix_param_valeur;

	public String getChoix_Vfiltre() {
		return choix_Vfiltre;
	}

	public void setChoix_Vfiltre(String choix_Vfiltre) {
		this.choix_Vfiltre = choix_Vfiltre;
	}

	public String getChoix_param_valeur() {
		return choix_param_valeur;
	}

	public void setChoix_param_valeur(String choix_param_valeur) {
		this.choix_param_valeur = choix_param_valeur;
	}

	private FiltresFraude filtre;

	public FiltresFraude getFiltre() {
		return filtre;
	}

	public void setFiltre(FiltresFraude filtre) {
		this.filtre = filtre;
	}

	private String choix_filter_valeur;

	public String getChoix_filter_valeur() {
		return choix_filter_valeur;
	}

	public void setChoix_filter_valeur(String choix_filter_valeur) {
		this.choix_filter_valeur = choix_filter_valeur;
	}

	private List<ParametresReglesFraude> liste_parametre_regle = new ArrayList<ParametresReglesFraude>();

	private ParametresReglesFraude parametre_regle_fraude;

	private List<FiltresReglesFraude> liste_filters_regle = new ArrayList<>();

	private List<FiltresReglesFraude> listeFiltresRegle = new ArrayList<FiltresReglesFraude>();

	public List<FiltresReglesFraude> getListeFiltresRegle() {
		return listeFiltresRegle;
	}

	public void setListeFiltresRegle(List<FiltresReglesFraude> listeFiltresRegle) {
		this.listeFiltresRegle = listeFiltresRegle;
	}

	private FiltresReglesFraude filtre_regle_fraude;

	public Integer getParamtreValeur() {
		return paramtreValeur;
	}

	public void setParamtreValeur(Integer paramtreValeur) {
		this.paramtreValeur = paramtreValeur;
	}

	public FiltresReglesFraude getFiltre_regle_fraude() {
		return filtre_regle_fraude;
	}

	public void setFiltre_regle_fraude(FiltresReglesFraude filtre_regle_fraude) {
		this.filtre_regle_fraude = filtre_regle_fraude;
	}

	public void setListe_filters_regle(List<FiltresReglesFraude> liste_filters_regle) {
		this.liste_filters_regle = liste_filters_regle;
	}

	public List<FiltresReglesFraude> getListe_filters_regle() {
		return liste_filters_regle;
	}

	public ParametresReglesFraude getParametre_regle_fraude() {
		return parametre_regle_fraude;
	}

	public void setParametre_regle_fraude(ParametresReglesFraude parametre_regle_fraude) {
		this.parametre_regle_fraude = parametre_regle_fraude;
	}

	private List<CategoriesFraude> categories;
	private List<Flow> fluxs;
	// private List<ParametresFraude> parametres;
	private List<Flow> parametres;

	private List<FiltresFraude> filtres;

	public List<FiltresFraude> getFiltres() {
		return filtres;
	}

	public void setFiltres(List<FiltresFraude> filtres) {
		this.filtres = filtres;
	}

	public List<Flow> getParametres() {
		return parametres;
	}

	public void setParametres(List<Flow> parametres) {
		this.parametres = parametres;
	}

	private ReglesFraude regle;

	private FluxCdr flux;

	private CategoriesFraude categorie;

	private String id_offre;

	public String getId_offre() {
		return id_offre;
	}

	public void setId_offre(String id_offre) {
		this.id_offre = id_offre;
	}

	private ParametresFraude parametre;

	private FiltresReglesFraude filtre_regle;

	public FiltresReglesFraude getFiltre_regle() {
		return filtre_regle;
	}

	public void setFiltre_regle(FiltresReglesFraude filtre_regle) {
		this.filtre_regle = filtre_regle;
	}

//	@PostConstruct
//	public void init() {
//		categories = fraude_service.getAllCategories();
//		filters = new ArrayList<>();
//		fluxs = fraude_service.getAllFlux2();
//		parametres = new ArrayList<>();
//		choix_param_valeur = "egale";
//		choix_filter_valeur = "egale";
//		regle = new ReglesFraude();
//		flux = new FluxCdr();
//		filtre = new FiltresFraude();
//		categorie = new CategoriesFraude();
//		parametre = new ParametresFraude();
//		parametre_regle_fraude = new ParametresReglesFraude();
//		filtre_regle_fraude = new FiltresReglesFraude();
//		liste_filters_regle = new ArrayList<>();
//		paramter_regle = new ParametresReglesFraude();
//		filtre_regle = new FiltresReglesFraude();
//		filtres = fraude_service.getFilterFiltresFraude2();
//	}

	private boolean listin = false;
	private boolean listinoffre = false;

	public boolean isListinoffre() {
		return listinoffre;
	}

	public void setListinoffre(boolean listinoffre) {
		this.listinoffre = listinoffre;
	}

	private boolean listinappelant = false;
	private boolean listinimei = false;
	private boolean listinappele = false;

	public boolean isListinappelant() {
		return listinappelant;
	}

	public boolean isListinappele() {
		return listinappele;
	}

	public boolean isListinimei() {
		return listinimei;
	}

	public void setListinappelant(boolean listinappelant) {
		this.listinappelant = listinappelant;
	}

	public void setListinappele(boolean listinappele) {
		this.listinappele = listinappele;
	}

	public void setListinimei(boolean listinimei) {
		this.listinimei = listinimei;
	}

	public boolean isListin() {
		return listin;
	}

	public void setListin(boolean listin) {
		this.listin = listin;
	}

	public ParametresFraude getParametre() {
		return parametre;
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

	private Integer id_filter;

	public Integer getId_filter() {
		return id_filter;
	}

	public void setId_filter(Integer id_filter) {
		this.id_filter = id_filter;
	}

	private boolean typeAppel;

	public boolean isTypeAppel() {
		return typeAppel;
	}

	public void setTypeAppel(boolean typeAppel) {
		this.typeAppel = typeAppel;
	}

	private List<ListAppele> lstappele;

	public List<ListAppele> getLstappele() {
		return lstappele;
	}

	public void setLstappele(List<ListAppele> lstappele) {
		this.lstappele = lstappele;
	}

	private boolean typelocroa;

	public boolean isTypelocroa() {
		return typelocroa;
	}

	public void setTypelocroa(boolean typelocroa) {
		this.typelocroa = typelocroa;
	}

	private boolean iscellid = false;

	public boolean isIscellid() {
		return iscellid;
	}

	public void setIscellid(boolean iscellid) {
		this.iscellid = iscellid;
	}

	public void selectChange() {

		if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("type destination")) {
			typeDests = type_dest_service.getAllTypeDest();
			ListTypeDest = true;
			listin = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			listinappelant = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;

		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("Offre")) {
			lst_offres = offre_service.getAllOffres();
			ListTypeDest = false;
			listin = false;
			listinoffre = true;
			ListPlanTarifaire = false;
			listinappelant = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("plan tarifaire")) {
			plans = plan_service.getAllPlanTarifaires();
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			ListPlanTarifaire = true;
			listinappelant = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("Type Appel")) {
			ListTypeDest = false;
			listinappelant = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = true;
			typelocroa = false;
			listin = false;
			listinimei = false;
			listinappele = false;

		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("Type msisdn")) {
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = true;
			listinimei = false;
			listinappele = false;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("CellId")
				&& choix_filter_valeur.equals("in")) {
			lstcellid = hotlist_service.getAllCellId();
			listin = true;
			listinoffre = false;
			ListTypeDest = false;
			listinappelant = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("Appelant")
				&& choix_filter_valeur.equals("in")) {
			lstappelant = hotlist_service.getAllAppelant();
			listinappelant = true;
			ListTypeDest = false;
			listinoffre = false;
			listin = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("Appele")
				&& choix_filter_valeur.equals("in")) {
			lstappele = hotlist_service.getAllAppele();
			listinappelant = false;
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = true;
		} else if (this.getFiltre_regle_fraude().getFiltreFraude().getFiltre().equals("IMEI")
				&& choix_filter_valeur.equals("in")) {
			lstimei = hotlist_service.getAllImei();
			listinappelant = false;
			ListTypeDest = false;
			listin = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = false;
			typeAppel = false;
			typelocroa = false;
			listinimei = true;
			listinappele = false;
		} else {
			ListTypeDest = false;
			listin = false;
			listinappelant = false;
			listinoffre = false;
			ListPlanTarifaire = false;
			text = true;
			typeAppel = false;
			typelocroa = false;
			listinimei = false;
			listinappele = false;
		}

	}

	public void setParametre(ParametresFraude parametre) {
		this.parametre = parametre;
	}

	public void selectChangeFiltres() {

		// String where = "p.flux.id=" + this.getRegle().getFlux().getId();
		// parametres = fraude_service.getParametresFraude(where);

		// where = "f.flux.id=" + this.getRegle().getFlux().getId();
		// filtres = fraude_service.getFilterFiltresFraude(where);

		/////////////

		System.out.println("h1");
		String where = "f.flow_type=6";
		parametres = fraude_service.getParametresFraude2(where);
		System.out.println("h2" + parametres);
		// where = "f.flux.id=" + this.getRegle().getFlux().getId();

		// where = "f.id_type_flow= 6";

		// filtres = fraude_service.getFilterFiltresFraude(where);
		filtres = fraude_service.getFilterFiltresFraude2();

	}

	public CategoriesFraude getCategorie() {
		return categorie;
	}

	public FluxCdr getFlux() {
		return flux;
	}

	public void setCategorie(CategoriesFraude categorie) {
		this.categorie = categorie;
	}

	public void setFlux(FluxCdr flux) {
		this.flux = flux;
	}

	public ReglesFraude getRegle() {
		return regle;
	}

	public void setRegle(ReglesFraude regle) {
		this.regle = regle;
	}

	public List<CategoriesFraude> getCategories() {
		return categories;
	}

	public List<Flow> getFluxs() {
		return fluxs;
	}

	public void setFluxs(List<Flow> fluxs) {
		this.fluxs = fluxs;
	}

	public void setCategories(List<CategoriesFraude> categories) {
		this.categories = categories;
	}

	public void addParametre() {
		boolean trouve = false;
		int i = 0;

		if (this.liste_parametre_regle != null) {
			System.out.println(trouve);
			while (i < liste_parametre_regle.size() && trouve == false) {
				if (parametre_regle_fraude.getParametreFraudeFlow().getFlowName()
						.equals(liste_parametre_regle.get(i).getParametreFraudeFlow().getFlowName())) {
					trouve = true;
				} else {
					i++;
				}
				System.out.println(trouve);
			}
		}
		System.out.println(trouve);
		if (trouve == false) {
			switch (this.getChoix_param_valeur()) {
			case "egale":
				parametre_regle_fraude.setVegal(paramtreValeur);
				parametre_regle_fraude.setVmax(-1);
				parametre_regle_fraude.setVmin(-1);

				break;
			case "superieur":
				parametre_regle_fraude.setVegal(-1);
				parametre_regle_fraude.setVmax(-1);
				parametre_regle_fraude.setVmin(paramtreValeur);

				break;
			case "inferieur":
				parametre_regle_fraude.setVegal(-1);
				parametre_regle_fraude.setVmax(paramtreValeur);
				parametre_regle_fraude.setVmin(-1);

				break;

			default:
				break;
			}
			parametre_regle_fraude.setNomUtilisateur(Util.getUser().getULogin());

			parametre_regle_fraude.setRegle(regle);

			// parametre_regle_fraude.setFlow(flow);
			// parametre_regle_fraude.setId(flow.getId());

			this.liste_parametre_regle.add(parametre_regle_fraude);
			parametre_regle_fraude = new ParametresReglesFraude();

		} else {
			FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cette Parametre est déja ajouté", "Veuillez choisir les parametres"));

		}

	}

	public void addFiltreRegleFraude() {
		FiltresFraude f = new FiltresFraude();
		f.setFiltre("test");
		filtre_regle_fraude.setFiltreFraude(f);
		listeFiltresRegle.add(filtre_regle_fraude);
		filtre_regle_fraude = new FiltresReglesFraude();
	}

	public void addFiltre() {

		switch (this.getChoix_filter_valeur()) {
		case "egale":
			filtre_regle_fraude.setVegal(this.getChoix_Vfiltre());
			filtre_regle_fraude.setVdef("-1");
			filtre_regle_fraude.setInegal("-1");
			filtre_regle_fraude.setVlike("-1");
			filtre_regle_fraude.setVnotlike("-1");

			break;
		case "like":
			filtre_regle_fraude.setVegal("-1");
			filtre_regle_fraude.setVdef("-1");
			filtre_regle_fraude.setInegal("-1");
			filtre_regle_fraude.setVlike(this.getChoix_Vfiltre());
			filtre_regle_fraude.setVnotlike("-1");

			break;
		case "not like":
			filtre_regle_fraude.setVegal("-1");
			filtre_regle_fraude.setVdef("-1");
			filtre_regle_fraude.setInegal("-1");
			filtre_regle_fraude.setVlike("-1");
			filtre_regle_fraude.setVnotlike(this.getChoix_Vfiltre());

			break;
		case "different":
			filtre_regle_fraude.setVegal("-1");
			filtre_regle_fraude.setVdef(this.getChoix_Vfiltre());
			filtre_regle_fraude.setInegal("-1");
			filtre_regle_fraude.setVlike("-1");
			filtre_regle_fraude.setVnotlike("-1");

			break;
		case "in":
			filtre_regle_fraude.setVegal("-1");
			filtre_regle_fraude.setVdef("-1");
			filtre_regle_fraude.setInegal(this.getChoix_Vfiltre());
			filtre_regle_fraude.setVlike("-1");
			filtre_regle_fraude.setVnotlike("-1");

			break;

		default:
			break;
		}
		filtre_regle_fraude.setNomUtlisateur(Util.getUser().getULogin());
		filtre_regle_fraude.setRegle(regle);

		listeFiltresRegle.add(filtre_regle_fraude);
		System.out.println(listeFiltresRegle.size());
		filtre_regle_fraude = new FiltresReglesFraude();

	}

	public void deletefiltre() {
		listeFiltresRegle.remove(filtre_regle);

	}

	public void deleteParametre() {
		liste_parametre_regle.remove(paramter_regle);
	}

	public void valider() {
		try {
			if (liste_parametre_regle.size() > 0) {
				for (int i = 0; i < listeFiltresRegle.size(); i++) {
					// System.out.println(listeFiltresRegle.get(i).getFiltreFraude().getFiltre()
					// + "="
					// + listeFiltresRegle.get(i).getVegal() + "" +
					// listeFiltresRegle.get(i));
				}
				for (int i = 0; i < liste_parametre_regle.size(); i++) {
					// System.out.println(liste_parametre_regle.get(i).getParametreFraude().getNomParametre());

				}
				regle.setNomUtilisateur(Util.getUser().getULogin());
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				regle.setDateModif(currentTimestamp);
				regle.setListe_filters(listeFiltresRegle);

				for (int i = 0; i < liste_parametre_regle.size(); i++) {
					liste_parametre_regle.get(i).setRegle(regle);
				}
				regle.setListe_parameters(liste_parametre_regle);

				regle.setEtat("A");
				regle.setFlux(flow);
				fraude_service.addRegle(regle);
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(ec.getRequestContextPath() + "/fraude/configuration/manage_regles.jsf");

			} else {
				FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Création Invalide!!", "Vous devez ajouter 1 paramtere"));

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@POST
	@Path("/addsms")
	@Produces(MediaType.APPLICATION_JSON)
	public void AjouterSms(DetailsSms sms) {

		String smsdata = sms.getMsisdn();
		ReglesFraude regle = sms.getId_Reg();
		String type = sms.getType();
		List<String> listsms = Arrays.asList(smsdata.split("[,\\s\\n]+"));

		for (String s : listsms) {
			// Create a new ListDetailsImei instance for each hotlist number
			DetailsSms lda1 = new DetailsSms();
			lda1.setMsisdn(s);
			lda1.setType(type);
			lda1.setId_Reg(regle);

			fraude_service.addSMS(lda1);
		}

	}

	@POST
	@Path("/addmail")
	@Produces(MediaType.APPLICATION_JSON)
	public void AjouterMail(DetailsMail mail) {
		String maildata = mail.getAdresseMail();
		String type = mail.getType();
		ReglesFraude regle = mail.getId_Reg();
		List<String> mails = Arrays.asList(maildata.split("[,\\s\\n]+"));

		for (String email : mails) {
			// Create a new ListDetailsImei instance for each hotlist number
			DetailsMail lda1 = new DetailsMail();
			lda1.setAdresseMail(email);
			lda1.setType(type);
			lda1.setId_Reg(regle);

			fraude_service.addAlerteMail(lda1);
		}

	}

	public void AjouterAlerteMail() {

		details_mail.setAdresseMail(details_mail.getAdresseMail());
		details_mail.setType(details_mail.getType());
		// details_mail.setId_Reg(selected_regle);
		fraude_service.addAlerteMail(details_mail);
		details_mail = new DetailsMail();
		setListregleMails(fraude_service.getAlerteFraudeByRegleMail(selected_regle));

	}

	private Integer byId;

	public void deleteDetailMail() {

		// select_det_mail = new DetailsMail();

		// fraude_service.deleteDetailMails(select_det_mail.getId());
		fraude_service.deleteDetailMails(details_mail1);

		setListregleMails(fraude_service.getAlerteFraudeByRegleMail(selected_regle));
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
		details_sms.setId_Reg(selected_regle);
		fraude_service.addAlerteSms(details_sms, selected_regle.getId());
		details_sms = new DetailsSms();
		ListregleSms = fraude_service.getAlerteFraudeByRegleSms(selected_regle);

	}

	public void deleteDetailSms() {

		fraude_service.deleteDetailSms(details_sms);

		ListregleSms = fraude_service.getAlerteFraudeByRegleSms(selected_regle);
	}

	public List<DetailsMail> getListregleMails() {
		return ListregleMails;
	}

	public void setListregleMails(List<DetailsMail> listregleMails) {
		ListregleMails = listregleMails;
	}

	@GET
	@Path("/getAllFlows")
	public List<Flow> getAllFlows() {
		return fraude_service.getAllFlux();
	}

	@GET
	@Path("/getAllParam/{idflow}") // add flow id
	@Produces(MediaType.APPLICATION_JSON)
	public List<Flow> getAllParam(@PathParam("idflow") Long idflow) {

		return fraude_service.getAllParamperflow(idflow);
	}

	@GET
	@Path("/getAllFilter")
	public List<FiltresFraude> getAllFilter() {
		return fraude_service.getFilterFiltresFraude2();
	}

	@GET
	@Path("/getAllTypeDest")
	public List<TypeDestination> getAllTypeDest() {
		return fraude_service.getAllTypedest();
	}

	@GET
	@Path("/getAllPlanTarifaire")
	public List<PlanTarifaire> getAllPlanTarifaire() {
		return fraude_service.getAllPlanTarifaire();
	}

	@GET
	@Path("/getAllOffre")
	public List<Offre> getAllOffre() {
		return fraude_service.getAllOffre();
	}

	@GET
	@Path("/filtre/{filtre}")
	public List<?> fetchData(@PathParam("filtre") String filtre) {
		List<?> result = null;

		switch (filtre.toLowerCase()) {
		case "offre":
			result = fraude_service.getAllOffre();
			break;
		case "appelant":
			result = hotlist_service.getAllAppelant();
			break;
		case "appele":
			result = hotlist_service.getAllAppele();
			break;
		case "cellid":
			result = hotlist_service.getAllCellId();
			break;
		case "imei":
			result = hotlist_service.getAllImei();
			break;
		case "type destination":
			result = fraude_service.getAllTypedest();
			break;
		case "plan tarifaire":
			result = fraude_service.getAllPlanTarifaire();
			break;
		default:
			// Handle the default case here (e.g., return an empty list or specific
			// response)
			break;
		}

		return result;
	}

	@POST
	@Path("/Addregle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addReglewithparamandfiltre(YourDataWrapper dataWrapper) {
		try {
			// Ensure that regle, param, and filter are properly populated

			fraude_service.addReglewithparamandfiltre(dataWrapper);
			// Return a success response if needed.
		} catch (Exception e) {
			// Handle the exception and return an error response if needed.
		}
	}

	@GET
	@Path("/getAllRule")
	public List<ReglesFraude> getAllRule() {
		return fraude_service.getAllRules();
	}
	
	@GET
	@Path("/getAllRuleswithdates")
	public List<Object[]> getAllRuleswithdates() {
	  

	    return fraude_service.getAllRulesWithMaxDateDetection();
	}


	@PUT
	@Path("/desactivate/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response toggleEtat(@PathParam("id") int id, ReglesFraude regle) {
		try {
			fraude_service.updaterule(id, regle);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}

	}

	@PUT
	@Path("/blok/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response blok(@PathParam("id") int id, ReglesFraude regle) {
		try {
			fraude_service.updateblok(id, regle);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}

	}

	@PUT
	@Path("/filter/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatefilter(@PathParam("id") int id, FiltresReglesFraude filter) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			filter.setDateModif(currentTimestamp);
			fraude_service.updatefilter(id, filter);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}

	}

	@PUT
	@Path("/param/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateparam(@PathParam("id") Long id, ParametresReglesFraude param) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			param.setDateModif(currentTimestamp);
			fraude_service.updateparam(id, param);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}

	}

//	@PUT
//	@Path("/regle/{id}/{id_flow}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateregle(@PathParam("id") int id, ReglesFraude regle, @PathParam("id_flow") Integer id_flow) {
//		try {
//			Calendar calendar = Calendar.getInstance();
//			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
//			regle.setDateModif(currentTimestamp);
//			fraude_service.updateregle(id, regle, id_flow);
//			return Response.status(Response.Status.NO_CONTENT).build();
//		} catch (Exception e) {
//			// Handle exceptions or validation errors here
//			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
//		}
//
//	}

	@PUT
	@Path("/regle/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editregle(@PathParam("id") int id, ReglesFraude regle) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			regle.setDateModif(currentTimestamp);
			fraude_service.editregle(id, regle);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}

	}

	@GET
	@Path("/getSMSByRegleId/{idRegle}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<DetailsSms> getSMSByRegleId(@PathParam("idRegle") Integer idRegle) {

		return fraude_service.getSMSByRegleId(idRegle);
	}

	@GET
	@Path("/getMailsByRegleId/{idRegle}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<DetailsMail> getMailsByRegleId(@PathParam("idRegle") Integer idRegle) {

		return fraude_service.getMailsByRegleId(idRegle);
	}

	@GET
	@Path("/getParamsByRegleId/{idRegle}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ParametresReglesFraude> getParamsByRegleId(@PathParam("idRegle") Integer idRegle) {

		return fraude_service.getParamsByRegleId(idRegle);
	}

	@GET
	@Path("/getFiltersByRegleId/{idRegle}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<FiltresReglesFraude> getFiltersByRegleId(@PathParam("idRegle") Integer idRegle) {

		return fraude_service.getFiltersByRegleId(idRegle);
	}

	@POST
	@Path("/addparam")
	@Produces(MediaType.APPLICATION_JSON)
	public void addparam(ParametresReglesFraude param) {
		try {

			fraude_service.addparam(param);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@POST
	@Path("/addfilter")
	@Produces(MediaType.APPLICATION_JSON)
	public void addfilter(FiltresReglesFraude filter) {
		try {

			fraude_service.addfilter(filter);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getRegleByid/{idRegle}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ReglesFraude> getRegleByid(@PathParam("id") Integer id) {

		return fraude_service.getRegleByid(id);
	}

	@DELETE
	@Path("/deletesms/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletesms(@PathParam("id") Integer id) {
		try {
			fraude_service.deleteSMS(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete sms").build();
		}
	}

	@DELETE
	@Path("/deletemail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletemail(@PathParam("id") Integer id) {
		try {
			fraude_service.deleteMail(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete mail").build();
		}
	}

	@DELETE
	@Path("/deleteparam/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteparam(@PathParam("id") Long id) {
		try {
			fraude_service.deleteParam(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	@DELETE
	@Path("/deletefilter/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletefilter(@PathParam("id") Integer id) {
		try {
			fraude_service.deleteFiltre(id);
			return Response.status(Response.Status.NO_CONTENT).build();

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();

		}
	}

	@GET
	@Path("/getAllDecisionFraudes")
	public List<DecisionFraude> getAllDecisionFraudes() {
		return fraude_service.getAllDecisionFraudes();
	}

	@GET
	@Path("/getMsisdnAndRegleCount")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getMsisdnAndRegleCount() {
		return fraude_service.getMsisdnAndRegleCount();
	}

	@GET
	@Path("/getReglesByMSISDN/{msisdn}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getReglesByMSISDN(@PathParam("msisdn") String msisdn) {
		return fraude_service.getReglesByMSISDN(msisdn);
	}

	@GET
	@Path("/getDecisionsInDateRange/{startDate}/{endDate}")
	public List<DecisionFraude> getDecisionsInDateRange(@PathParam("startDate") java.sql.Date startDate,
			@PathParam("endDate") java.sql.Date endDate) {

		return fraude_service.findDecisionsInDateRange(startDate, endDate);
	}

	@GET
	@Path("/getMSISDNsByRegle/{regle_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getMSISDNsByRegle(@PathParam("regle_id") Long regle_id) {
		return fraude_service.getMSISDNsByRegle(regle_id);
	}

	@GET
	@Path("/getDetailsWarning/{msisdn}/{id_regle}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getDetailsWarning(@PathParam("msisdn") String msisdn,
			@PathParam("id_regle") Integer id_regle) {
		return fraude_service.getDetailsWarning(msisdn, id_regle);
	}

	@GET
	@Path("/chartbyregle/{idRegles}/{startDate}/{endDate}") // chartbyregle
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> chartbyregle(@PathParam("idRegles") String idRegles, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate) {
		return fraude_service.chartbyregle(idRegles, startDate, endDate);
	}

	@EJB
	FraudeManagerRemote decision_service;
	private List<Nmro> tabNum;

	public List<Nmro> getTabNum() {
		return tabNum;
	}

	public void setTabNum(List<Nmro> tabNum) {
		this.tabNum = tabNum;
	}

	private String Nmro;

	public String getNmro() {
		return Nmro;
	}

	public void setNmro(String nmro) {
		Nmro = nmro;
	}

	public class Nmro {
		private int index;
		private String num;
		private String Action;

		public String getAction() {
			return Action;
		}

		public void setAction(String action) {
			Action = action;
		}

		public int getIndex() {
			return index;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getNum() {
			return num;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	//
	// webservice list of number /type blocking
	@POST
	@Path("/blocking/{listNumber}/{typebloc}/{nameofcurrentuser}")
	@Produces(MediaType.APPLICATION_JSON)
	public String execCommande(@PathParam("listNumber") String textofnum, @PathParam("typebloc") String typebloc,
			@PathParam("nameofcurrentuser") String nameofcurrentuser) {
		// System.out.println("numero " + Nmro);
		String getfirstTwonumber = "";
		// getfirstTwonumber = Nmro.substring(0, 1);
		FacesContext context = FacesContext.getCurrentInstance();
		Nmro = "";
		String result = "";
		Process proc = null;
		// if (getfirstTwonumber.contains("1")) {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		textofnum.trim();
		textofnum.replaceAll("\\s", "");

		if (textofnum.contains(",") || textofnum.contains(" ") || textofnum.contains("\n")) {

			List<String> listnumber = Arrays.asList(textofnum.split("[,\\s\\n]+"));

			System.out.println("Blocage !");

			for (int i = 0; i < listnumber.size(); i++) {
				Nmro = listnumber.get(i).toString();
				Nmro nmro = new Nmro();
				try {
					String typeB = null;
					if (typebloc.equals("blocage")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blkonnet  " + Nmro });
						proc.waitFor();
						typeB = "blocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						TblLog log = new TblLog();
						log.setAction("blocage num : " + Nmro);
						log.setUsername(nameofcurrentuser);
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);

					} else if (typebloc.equals("deblocage")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblkonnet  " + Nmro });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						TblLog log = new TblLog();
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						log.setAction("deblocage num : " + Nmro);
						log.setUsername(nameofcurrentuser);
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					} else if (typebloc.equals("WhiteList")) {

						DecisionFraude df = new DecisionFraude();
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						df.setDateModif(currentTimestamp);
						df.setNomUtilisateur(Util.getUser().getUName());
						df.setMsisdn(numberprefix + Nmro);
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						df.setDecision("W");
						df.setIdRegle(0);
						decision_service.addDecision(df);
						TblLog log = new TblLog();
						log.setAction(" WhiteList : " + Nmro);
						log.setUsername(nameofcurrentuser);
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
						context.addMessage(null, new FacesMessage("Successful", "Number has been added to whiteList "));
					} else if (typebloc.equals("deblocageSMS")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + Nmro });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						TblLog log = new TblLog();
						log.setAction("deblocage num : " + Nmro);
						log.setUsername(nameofcurrentuser);
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					} else if (typebloc.equals("blocageSMS")) {
						proc = Runtime.getRuntime().exec(
								new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + "  " + Nmro });
						proc.waitFor();
						typeB = "blocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);

						TblLog log = new TblLog();
						log.setAction("blocage num : " + Nmro);
						log.setUsername(nameofcurrentuser);
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					}
					if (proc != null) {
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

						BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
						String s = null;
						boolean add = false;
						// read the output from the command
						System.out.println("Here is the standard output of the command:\n");
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;
								return result;
							} else {
								add = false;
							}
							System.out.println(s);

						}
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(nameofcurrentuser);
							df.setMsisdn(numberprefix + Nmro);

							if (typebloc.equals("blocage")) {
								df.setDecision("D");
							} else {
								df.setDecision("W");
							}
							df.setIdRegle(0);
							decision_service.updateDecision(df);
							if (typeB.equals("blocage")) {
								if (outputCode.contains("1"))
									result = s;
								nmro.setAction("Number has been blocke");
//									context.addMessage(null, new FacesMessage("Successful", "Number has been blocked "));
								if (outputCode.contains("2"))
									result = s;
								nmro.setAction("Number is already blocked ");
//									context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
//											"Number is already blocked "));
							}
							if (typeB.equals("deblocage")) {
								if (outputCode.contains("1"))
									result = s;
								nmro.setAction("Number has been unblocked ");
//									context.addMessage(null, new FacesMessage("Successful", "Number has been unblocked "));
								if (outputCode.contains("2"))

									nmro.setAction("Number is already unblocked");
//									context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
//											"Number is already unblocked "));
							}

						} else {
							if (typeB.equals("blocage")) {
//									context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
//											"Error while blocking number "));
								nmro.setAction("Error while blocking number");

							}
							if (typeB.equals("deblocage")) {
								nmro.setAction("Error while unblocking number ");
//									context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
//											"Error while unblocking number "));
							}
						}

						// read any errors from the attempted command
						System.out.println("Here is the standard error of the command (if any):\n");
						String m = "";
						while ((s = stdError.readLine()) != null) {

							m += s;
							System.out.println(s);
						}
						if (!m.isEmpty()) {
							context.addMessage("somekey",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tabNum.add(nmro);
			}
		} else {
			Nmro = textofnum;
			try {
				String typeB = null;
				if (typebloc.equals("blocage")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blkonnet  " + Nmro });
					proc.waitFor();
					typeB = "blocage";
					System.out.println(proc.exitValue());

					TblLog log = new TblLog();
					log.setAction("blocage num : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (typebloc.equals("deblocage")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblkonnet  " + Nmro });
					proc.waitFor();
					typeB = "deblocage";
					System.out.println(proc.exitValue());
					TblLog log = new TblLog();
					log.setAction("deblocage num : " + Nmro);
					log.setUsername(nameofcurrentuser);
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (typebloc.equals("WhiteList")) {

					DecisionFraude df = new DecisionFraude();
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					df.setDateModif(currentTimestamp);
					df.setNomUtilisateur(Util.getUser().getUName());
					df.setMsisdn(numberprefix + Nmro);
					df.setDecision("W");
					df.setIdRegle(0);
					decision_service.addDecision(df);
					TblLog log = new TblLog();
					log.setAction(" WhiteList : " + Nmro);
					log.setUsername(nameofcurrentuser);
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
					context.addMessage(null, new FacesMessage("Successful", "Number has been added to whiteList "));
				} else if (typebloc.equals("deblocageSMS")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + Nmro });
					proc.waitFor();
					typeB = "deblocage";
					System.out.println(proc.exitValue());
					TblLog log = new TblLog();
					log.setAction("deblocage num : " + Nmro);
					log.setUsername(nameofcurrentuser);
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (typebloc.equals("blocageSMS")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + "  " + Nmro });
					proc.waitFor();
					typeB = "blocage";
					System.out.println(proc.exitValue());

					TblLog log = new TblLog();
					log.setAction("blocage num : " + Nmro);
					log.setUsername(nameofcurrentuser);
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				}
				if (proc != null) {
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

					BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					String s = null;
					boolean add = false;
					// read the output from the command
					System.out.println("Here is the standard output of the command:\n");
					String outputCode = "0";
					while ((s = stdInput.readLine()) != null) {
						if (s.contains("1") || s.contains("2")) {
							add = true;
							outputCode = s;
							result = s;
						} else {
							add = false;
							result = s;
						}
						// System.out.println(s);

					}
					if (add) {
						DecisionFraude df = new DecisionFraude();
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						df.setDateModif(currentTimestamp);
						df.setNomUtilisateur(nameofcurrentuser);
						df.setMsisdn(numberprefix + Nmro);

						if (typebloc.equals("blocage")) {
							df.setDecision("D");
						} else {
							df.setDecision("W");
						}
						df.setIdRegle(0);
						decision_service.updateDecision(df);
						if (typeB.equals("blocage")) {
							if (outputCode.contains("1"))
								context.addMessage(null, new FacesMessage("Successful", "Number has been blocked "));
							if (outputCode.contains("2"))
								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
										"Number is already blocked "));
						}
						if (typeB.equals("deblocage")) {
							if (outputCode.contains("1"))
								context.addMessage(null, new FacesMessage("Successful", "Number has been unblocked "));
							if (outputCode.contains("2"))
								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
										"Number is already unblocked "));
						}

					} else {
						if (typeB.equals("blocage")) {
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
									"Error while blocking number "));
						}
						if (typeB.equals("deblocage")) {
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
									"Error while unblocking number "));
						}
					}

					// read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					String m = "";
					while ((s = stdError.readLine()) != null) {

						m += s;
						System.out.println(s);
					}
					if (!m.isEmpty()) {
						context.addMessage("somekey",
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return operator;
	}

}
