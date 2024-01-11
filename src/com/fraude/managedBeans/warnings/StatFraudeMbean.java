package com.fraude.managedBeans.warnings;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.panelgrid.PanelGrid;

import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.PlanTarifaire;

import com.fraude.interfaces.FraudeManagerRemote;
import com.fraude.interfaces.PlanTarifaireRemote;
import com.fraude.interfaces.StatFraudeMsisdnRemote;
import com.jsf2leaf.model.LatLong;
import com.jsf2leaf.model.Layer;
import com.jsf2leaf.model.Map;
import com.jsf2leaf.model.Marker;

@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "stat_fraude")
@ViewScoped
public class StatFraudeMbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8097289811767030870L;

	private String choix_periode;

	private PlanTarifaire choix_plan;

	public PlanTarifaire getChoix_plan() {
		return choix_plan;
	}

	public void setChoix_plan(PlanTarifaire choix_plan) {
		this.choix_plan = choix_plan;
	}

	private Object[] selected_ajustement;

	public Object[] getSelected_ajustement() {
		return selected_ajustement;
	}

	public void setSelected_ajustement(Object[] selected_ajustement) {
		this.selected_ajustement = selected_ajustement;
	}

	@EJB
	private StatFraudeMsisdnRemote stat_remote;

	@EJB
	private FraudeManagerRemote categories_service;

	private Date date_Parheure;
	private Date date_ParJourDeb;
	private Date date_ParJourFin;
	private Integer date_year_deb;
	private Integer date_year_fin;
	private Date date_mois_fin;
	private Date date_mois_debut;
	private Date date_an_fin;
	private Date date_an_debut;

	public Date getDate_an_debut() {
		return date_an_debut;
	}

	public void setDate_an_debut(Date date_an_debut) {
		this.date_an_debut = date_an_debut;
	}

	private boolean chartDisplayed = false;

	private List<Object[]> staticListStatMsc = new ArrayList<>();

	public List<Object[]> getStaticListStatMsc() {
		return staticListStatMsc;
	}

	public void setStaticListStatMsc(List<Object[]> staticListStatMsc) {
		this.staticListStatMsc = staticListStatMsc;
	}

	private List<Object[]> staticListStatMscTAB = new ArrayList<>();

	public boolean isChartDisplayed() {
		return chartDisplayed;
	}

	public void setChartDisplayed(boolean chartDisplayed) {
		this.chartDisplayed = chartDisplayed;
	}

	private CategoriesFraude category;

	private String operateur;

	public String getOperateur() {
		return operateur;
	}

	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}

	private boolean bypass = false;

	public boolean isBypass() {
		return bypass;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}

	public CategoriesFraude getCategory() {
		return category;
	}

	public void setCategory(CategoriesFraude category) {
		this.category = category;
	}

	public List<String> getHeures() {
		return heures;
	}

	public void setHeures(List<String> heures) {
		this.heures = heures;
	}

	private List<String> where_liste;

	public List<String> getWhere_liste() {
		return where_liste;
	}

	public void setWhere_liste(List<String> where_liste) {
		this.where_liste = where_liste;
	}

	public Date getDate_an_fin() {
		return date_an_fin;
	}

	public void setDate_an_fin(Date date_an_fin) {
		this.date_an_fin = date_an_fin;
	}

	private List<CategoriesFraude> lst_CategoriesFraudes = new ArrayList<>();

	public void setLst_CategoriesFraudes(List<CategoriesFraude> lst_CategoriesFraudes) {
		this.lst_CategoriesFraudes = lst_CategoriesFraudes;
	}

	public List<CategoriesFraude> getLst_CategoriesFraudes() {
		return lst_CategoriesFraudes;
	}

	private String SubTitle;

	public String getSubTitle() {
		return SubTitle;
	}

	public void setSubTitle(String subTitle) {
		SubTitle = subTitle;
	}

	private List<Object[]> lstByCategory = new ArrayList<>();
	private List<Object[]> lstByLocation = new ArrayList<>();

	public List<Object[]> getLstByCategory() {
		return lstByCategory;
	}

	public List<Object[]> getLstByLocation() {
		return lstByLocation;
	}

	public void setLstByCategory(List<Object[]> lstByCategory) {
		this.lstByCategory = lstByCategory;
	}

	public void setLstByLocation(List<Object[]> lstByLocation) {
		this.lstByLocation = lstByLocation;
	}

	private List<String> heures = new ArrayList<>();

	public void handleCategoryChange() {
		chartDisplayed = false;
		if (category != null) {
			if (category.getNomCategorie().equals("ByPass")) {

				bypass = true;
			} else {
				bypass = false;
			}

		} else {
			bypass = false;
		}

	}

	@PostConstruct
	public void init() {
		staticListStatMscTAB = new ArrayList<>();
		lst_CategoriesFraudes = categories_service.getAllCategories();

		heures = new ArrayList<>();
		heures.add("00");
		heures.add("01");
		heures.add("02");
		heures.add("03");
		heures.add("04");
		heures.add("05");
		heures.add("06");
		heures.add("07");
		heures.add("08");
		heures.add("09");
		heures.add("10");
		heures.add("11");
		heures.add("12");
		heures.add("13");
		heures.add("14");
		heures.add("15");
		heures.add("16");
		heures.add("17");
		heures.add("18");
		heures.add("19");
		heures.add("20");
		heures.add("21");
		heures.add("22");
		heures.add("23");

	}

	private List<Object[]> lstByOperateur = new ArrayList<>();

	public List<Object[]> getLstByOperateur() {
		return lstByOperateur;
	}

	public void setLstByOperateur(List<Object[]> lstByOperateur) {
		this.lstByOperateur = lstByOperateur;
	}

	public void handlechange(AjaxBehaviorEvent event) {

		chartDisplayed = false;

		FacesContext facesCtx = FacesContext.getCurrentInstance();
		ELContext elContext = facesCtx.getELContext();
		Application app = facesCtx.getApplication();
		ExpressionFactory elFactory = app.getExpressionFactory();
		PanelGrid comp = (PanelGrid) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:PanelPeriode");
		System.out.println(comp.getId());
		System.out.println(comp.getChildCount());
		UIComponent compJourOutput0 = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:cld_jour_debut");
		UIComponent compJourOutput1 = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:cld_jour_fin");

		UIComponent compJourOutputHeure = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:cld_jour");
		UIComponent compJourOutputYear = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:cld_an_debut");
		UIComponent compJourOutputYearFin = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:cld_an_fin");

		UIComponent compSLYearMois = FacesContext.getCurrentInstance().getViewRoot()
				.findComponent("form1:lstDateYearsMois");
		UIComponent compSLMois = FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_mois_debut");
		UIComponent compSLMoisFin = FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_mois_fin");

		if (compJourOutput0 != null) {

			comp.getChildren().remove(compJourOutput0);
			comp.getChildren().remove(compJourOutput1);
		}
		if (compSLMois != null) {

			comp.getChildren().remove(compSLMois);
			comp.getChildren().remove(compSLMoisFin);

		}
		if (compJourOutputYear != null) {

			comp.getChildren().remove(compJourOutputYear);
			comp.getChildren().remove(compJourOutputYearFin);
		}
		if (compJourOutputHeure != null) {

			comp.getChildren().remove(compJourOutputHeure);
		}

		if (this.getChoix_periode().equals("Par Jour")) {

			Calendar cld_jour_debut = new Calendar();
			cld_jour_debut.setId("cld_jour_debut");
			cld_jour_debut.setRequired(true);
			addAjaxkeyup(cld_jour_debut);
			addAjax1(cld_jour_debut);
			ValueExpression valjour = createValueExpression("#{stat_fraude.date_ParJourDeb}", Date.class);
			cld_jour_debut.setValueExpression("value", valjour);

			Calendar cld_jour_fin = new Calendar();

			cld_jour_fin.setId("cld_jour_fin");
			addAjax2(cld_jour_fin);
			ValueExpression valjourfin = createValueExpression("#{stat_fraude.date_ParJourFin}", Date.class);
			cld_jour_fin.setValueExpression("value", valjourfin);

			addAjaxkeyup(cld_jour_fin);

			comp.getChildren().add(cld_jour_debut);

			comp.getChildren().add(cld_jour_fin);

		} else if (this.getChoix_periode().equals("Par Heure")) {
			Calendar cld_jour = new Calendar();

			cld_jour.setId("cld_jour");

			ValueExpression valjour = createValueExpression("#{stat_fraude.date_Parheure}", Date.class);
			cld_jour.setValueExpression("value", valjour);
			AjaxBehavior ajax = new AjaxBehavior();
			// MethodExpression me = elFactory.createMethodExpression(elContext,
			// "#{sampleMBean.processAction}", null, new
			// Class<?>[]{BehaviorEvent.class});
			ajax.addAjaxBehaviorListener(
					new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange1}"),
							createActionMethodExpression("#{stat_fraude.handlechange1}")));
			ajax.setUpdate("@form");

			//

			cld_jour.addClientBehavior("dateSelect", ajax);
			AjaxBehavior ajax1 = new AjaxBehavior();
			// MethodExpression me = elFactory.createMethodExpression(elContext,
			// "#{sampleMBean.processAction}", null, new
			// Class<?>[]{BehaviorEvent.class});
			ajax1.addAjaxBehaviorListener(
					new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange2}"),
							createActionMethodExpression("#{stat_fraude.handlechange2}")));
			ajax1.setUpdate("@form");

			cld_jour.setOnkeyup("return this.value.length >= 8");

			//

			cld_jour.addClientBehavior("keyup", ajax1);

			comp.getChildren().add(cld_jour);
		} else

		if (this.getChoix_periode().equals("Par An")) {
			Calendar cld_an_debut = new Calendar();
			cld_an_debut.setId("cld_an_debut");
			cld_an_debut.setRequired(true);

			addAjax8(cld_an_debut);
			ValueExpression valjour = createValueExpression("#{stat_fraude.date_an_debut}", Date.class);
			cld_an_debut.setValueExpression("value", valjour);
			addAjaxkeyup(cld_an_debut);

			Calendar cld_an_fin = new Calendar();

			cld_an_fin.setId("cld_an_fin");
			addAjax7(cld_an_fin);
			addAjaxkeyup(cld_an_fin);
			ValueExpression valjourfin = createValueExpression("#{stat_fraude.date_an_fin}", Date.class);
			cld_an_fin.setValueExpression("value", valjourfin);

			comp.getChildren().add(cld_an_debut);

			comp.getChildren().add(cld_an_fin);

		} else if (this.getChoix_periode().equals("Par Mois")) {
			Calendar cld_mois_debut = new Calendar();
			cld_mois_debut.setId("cld_mois_debut");
			cld_mois_debut.setRequired(true);
			addAjaxkeyup(cld_mois_debut);
			addAjax4(cld_mois_debut);
			ValueExpression valjour = createValueExpression("#{stat_fraude.date_mois_debut}", Date.class);
			cld_mois_debut.setValueExpression("value", valjour);

			Calendar cld_mois_fin = new Calendar();

			cld_mois_fin.setId("cld_mois_fin");
			addAjaxkeyup(cld_mois_fin);
			addAjax3(cld_mois_fin);
			ValueExpression valjourfin = createValueExpression("#{stat_fraude.date_mois_fin}", Date.class);
			cld_mois_fin.setValueExpression("value", valjourfin);

			comp.getChildren().add(cld_mois_debut);

			comp.getChildren().add(cld_mois_fin);

		}
	}

	@POST
	@Path("/repfraude")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat) throws ParseException {

		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();
		where_liste2 = new ArrayList<String>();
		where_liste3 = new ArrayList<String>();
		List<String> where_liste1 = new ArrayList<>();

		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;
//
		choix_periode = type_Filtre;

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					generateList.add(ob);

				}

				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date(" + "'"
						+ fin + "'" + ",'yyMMdd') ");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
				where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
						+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");

				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("YYYY");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());
				where_liste2.add(
						"to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin + "'" + "");
				where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
						+ "'" + "");// where_liste.add(" Extract(year from to_date(date_detection,'YYMMDD')) >=
									// Extract(year from to_date("

				// + "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_detection,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("YYMM");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());
				where_liste2
						.add("to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");

				where_liste.add(
						"to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			// lstByCategory = stat_remote.getStatFraude("category.nomCategorie",
			// "count(distinct msisdn)", where_liste);

			if (this.list_id_categorie == null || this.list_id_categorie.isEmpty()) {
				// where_liste.addAll( this.list_id_categorie);
				// where_liste1.add("id_categorie= " + this.getCategory().getId());

				// SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as
				// Category";

				for (int i = 0; i < lst_CategoriesFraudes.size(); i++) {
					list_id_categorie.add(lst_CategoriesFraudes.get(i).getId());
					// where_liste.addAll( this.list_id_categorie);
				}
			}

//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}

			// lstByLocation = stat_remote.getStatFraude("location ", "count(distinct
			// msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection, 'YYYY' )",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
				// "SUM(duree) as dur e,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				// staticListStatMscChart = stat_remote.getStatFraudeAlertPie(" ", " ",
				// list_id_categorie, where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection, 'yyyy-MM' )",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as dur e,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				// staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "",
				// list_id_categorie, where_liste);
			} else if (this.getChoix_periode().equals("Par Jour")) {

				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char(date_detection,'yyyy-mm-dd')",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as dur e ,SUM(nb_appel) as nb_appel", where_liste1 , where_liste2);
				// staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "",
				// list_id_categorie, where_liste2);
				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return staticListStatMsc;
	}

//	@POST
//	@Path("/repfraude")
//	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
//	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
//	public List<Object[]> Valider_alerte_Rest(@FormParam("type_Filtre") String type_Filtre,
//			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
//			@FormParam("list_cat") String listcat) throws ParseException {
//		
//		System.out.println(type_Filtre);
//		System.out.println(startDate);
//		System.out.println(endDate);
//		System.out.println(listcat);
//
//		date_ParJourDeb = startDate;
//		date_ParJourFin = endDate;
//
//		date_mois_fin = endDate;
//		date_mois_debut = startDate;
//		date_an_fin = startDate;
//		date_an_debut = endDate;
//
//		choix_periode = type_Filtre;
//
//		// lst_CategoriesFraudes = listcat;
//
//		List<CategoriesFraude> categoriesList = new ArrayList<>();
//
//		String[] categoryStrings = listcat.split(",");
//		for (String categoryString : categoryStrings) {
//			String[] parts = categoryString.split(":");
//			if (parts.length == 2) {
//				int id = Integer.parseInt(parts[0]);
//				String namecategorie = parts[1];
//				CategoriesFraude catego = new CategoriesFraude();
//				catego.setId(id);
//				catego.setNomCategorie(namecategorie);
//				categoriesList.add(catego);
//			}
//		}
//		
//		lst_CategoriesFraudes = categoriesList;
//		where_liste = new ArrayList<String>();
//		staticListStatMsc = new ArrayList<>();
//
//		List<String> where_liste1 = new ArrayList<>();
//
//		List<Object[]> generateList = new ArrayList<>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		if (this.getChoix_periode().equals(" ")) {
//			FacesContext.getCurrentInstance().addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
//		} else {
//			if (this.getChoix_periode().equals("Par Jour")) {
//
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_ParJourDeb());
//				String fin0 = df1.format(this.getDate_ParJourFin());
//				DateTime start = DateTime.parse(deb0);
//				//System.out.println("Start: " + start);
//
//				DateTime end = DateTime.parse(fin0);
//				//System.out.println("End: " + end);
//
//				List<DateTime> between = getDateRange(start, end);
//				for (DateTime d : between) {
//					String s = d + "";
//
//					//System.out.println(" " + s.substring(8, 10));
//					Object[] ob = new Object[4];
//					ob[0] = s.substring(0, 10);
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//				}
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
//				String deb = df.format(this.getDate_ParJourDeb());
//				String fin = df.format(this.getDate_ParJourFin());
//				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//
//				SubTitle = "Period Between " + deb + " and " + fin;
//
//			} else
//
//			if (this.getChoix_periode().equals("Par Heure")) {
//
//				for (int i = 0; i < heures.size(); i++) {
//					Object[] ob = new Object[4];
//					ob[0] = heures.get(i);
//					//System.out.println(heures.get(i));
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//				}
//				System.out.println(generateList.size());
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				System.out.println(date_Parheure);
//				String deb = df.format(this.getDate_Parheure());
//				System.out.println(deb);
//				where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
//
//				SubTitle = "Per schedule on the date " + deb;
//
//			} else
//
//			if (this.getChoix_periode().equals("Par An")) {
//
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_an_debut());
//				String fin0 = df1.format(this.getDate_an_fin());
//				LocalDate date1 = new LocalDate(deb0);
//				LocalDate date2 = new LocalDate(fin0);
//
//				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
//					Object[] ob = new Object[5];
//					ob[0] = date1.toString("yyyy");
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//					ob[4] = 0.0;
//
//					generateList.add(ob);
//				//	System.out.println(date1.toString("yyyy"));
//					date1 = date1.plus(Period.years(1));
//
//				}
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//				String deb = df.format(this.getDate_an_debut());
//				String fin = df.format(this.getDate_an_fin());
//
//				where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//
//				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
//				// SubTitle ="Between Year "+deb+" and Year
//				// "+date2.toString("DD-MM-YYYY");
//
//			}
//
//			else if (this.getChoix_periode().equals("Par Mois")) {
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_mois_debut());
//				String fin0 = df1.format(this.getDate_mois_fin());
//				LocalDate date1 = new LocalDate(deb0);
//				LocalDate date2 = new LocalDate(fin0);
//				date2 = date2.plus(Period.months(1));
//				while (date1.isBefore(date2)) {
//					Object[] ob = new Object[4];
//					ob[0] = date1.toString("MM/yyyy");
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//					//System.out.println(date1.toString("MM/yyyy"));
//					date1 = date1.plus(Period.months(1));
//
//				}
//
//				DateFormat df = new SimpleDateFormat("MM-YYYY");
//				System.out.println(date_Parheure);
//				String deb = df.format(this.getDate_mois_debut());
//				String fin = df.format(this.getDate_mois_fin());
//
//				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");
//
//				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
//			}
//
//			lstByCategory = stat_remote.getStatFraude("category.nomCategorie", "count(distinct msisdn)", where_liste);
//
//			if (this.getCategory() != null) {
//				where_liste.add("category.id =" + this.getCategory().getId());
//				where_liste1.add("id_categorie= " + this.getCategory().getId());
//
//				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
//			}
//
//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}
//
//			lstByLocation = stat_remote.getStatFraude("location", "count(distinct msisdn)", where_liste);
//
//			// if(this.getChoix_periode().equals("Par Heure")){
//			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
//			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
//			// "", where_liste));
//			// staticListStatMsc = removeDuplicate(generateList,
//			// stat_remote.getStatFraude(x, y, where));
//			//
//			//
//			// }else
//			if (this.getChoix_periode().equals("Par An")) {
//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
//								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//			} else if (this.getChoix_periode().equals("Par Mois")) {
//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("", "SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//			} else if (this.getChoix_periode().equals("Par Jour")) {
//
//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
//								"SUM(duree),SUM(nbAppel),count(msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//				// data =statRemote.getMscByFilters("
//				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
//				// chd.getOperation()+"(","Group By
//				// to_date(dateDecision,'YYMMDD') ORDER BY
//				// to_date(dateDecision,'YYMMDD') DESC", where_liste);
//
//			}
//
//			if (staticListStatMsc != null) {
//				chartDisplayed = true;
//				return staticListStatMsc;
//			} else {
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));
//
//			}
//
//			// data =statRemote.getMscByFilters("
//			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
//			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
//			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);
//
//		}
//		return staticListStatMsc;
//	}
//	

//	@POST
//	@Path("/repfraude2")
//	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
//	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
//	public List<Object[]> Valider_alerte_Rest2(@FormParam("type_Filtre") String type_Filtre,
//			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
//			@FormParam("list_cat") String listcat) throws ParseException {
//		
//		System.out.println(type_Filtre);
//		System.out.println(startDate);
//		System.out.println(endDate);
//		System.out.println(listcat);
//
//		date_ParJourDeb = startDate;
//		date_ParJourFin = endDate;
//
//		date_mois_fin = endDate;
//		date_mois_debut = startDate;
//		date_an_fin = startDate;
//		date_an_debut = endDate;
//
//		choix_periode = type_Filtre;
//
//		// lst_CategoriesFraudes = listcat;
//
//		List<CategoriesFraude> categoriesList = new ArrayList<>();
//
//		String[] categoryStrings = listcat.split(",");
//		for (String categoryString : categoryStrings) {
//			String[] parts = categoryString.split(":");
//			if (parts.length == 2) {
//				int id = Integer.parseInt(parts[0]);
//				String namecategorie = parts[1];
//				CategoriesFraude catego = new CategoriesFraude();
//				catego.setId(id);
//				catego.setNomCategorie(namecategorie);
//				categoriesList.add(catego);
//			}
//		}
//		
//		lst_CategoriesFraudes = categoriesList;
//		where_liste = new ArrayList<String>();
//		staticListStatMsc = new ArrayList<>();
//
//		List<String> where_liste1 = new ArrayList<>();
//
//		List<Object[]> generateList = new ArrayList<>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//		if (this.getChoix_periode().equals(" ")) {
//			FacesContext.getCurrentInstance().addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
//		} else {
//			if (this.getChoix_periode().equals("Par Jour")) {
//
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_ParJourDeb());
//				String fin0 = df1.format(this.getDate_ParJourFin());
//				DateTime start = DateTime.parse(deb0);
//				//System.out.println("Start: " + start);
//
//				DateTime end = DateTime.parse(fin0);
//				//System.out.println("End: " + end);
//
//				List<DateTime> between = getDateRange(start, end);
//				for (DateTime d : between) {
//					String s = d + "";
//
//					//System.out.println(" " + s.substring(8, 10));
//					Object[] ob = new Object[4];
//					ob[0] = s.substring(0, 10);
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//				}
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
//				String deb = df.format(this.getDate_ParJourDeb());
//				String fin = df.format(this.getDate_ParJourFin());
//				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//
//				SubTitle = "Period Between " + deb + " and " + fin;
//
//			} else
//
//			if (this.getChoix_periode().equals("Par Heure")) {
//
//				for (int i = 0; i < heures.size(); i++) {
//					Object[] ob = new Object[4];
//					ob[0] = heures.get(i);
//					//System.out.println(heures.get(i));
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//				}
//				System.out.println(generateList.size());
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				System.out.println(date_Parheure);
//				String deb = df.format(this.getDate_Parheure());
//				System.out.println(deb);
//				where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
//
//				SubTitle = "Per schedule on the date " + deb;
//
//			} else
//
//			if (this.getChoix_periode().equals("Par An")) {
//
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_an_debut());
//				String fin0 = df1.format(this.getDate_an_fin());
//				LocalDate date1 = new LocalDate(deb0);
//				LocalDate date2 = new LocalDate(fin0);
//
//				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
//					Object[] ob = new Object[5];
//					ob[0] = date1.toString("yyyy");
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//					ob[4] = 0.0;
//
//					generateList.add(ob);
//				//	System.out.println(date1.toString("yyyy"));
//					date1 = date1.plus(Period.years(1));
//
//				}
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//				String deb = df.format(this.getDate_an_debut());
//				String fin = df.format(this.getDate_an_fin());
//
//				where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//
//				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
//				// SubTitle ="Between Year "+deb+" and Year
//				// "+date2.toString("DD-MM-YYYY");
//
//			}
//
//			else if (this.getChoix_periode().equals("Par Mois")) {
//				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//				String deb0 = df1.format(this.getDate_mois_debut());
//				String fin0 = df1.format(this.getDate_mois_fin());
//				LocalDate date1 = new LocalDate(deb0);
//				LocalDate date2 = new LocalDate(fin0);
//				date2 = date2.plus(Period.months(1));
//				while (date1.isBefore(date2)) {
//					Object[] ob = new Object[4];
//					ob[0] = date1.toString("MM/yyyy");
//					ob[1] = 0.0;
//					ob[2] = 0.0;
//					ob[3] = 0.0;
//
//					generateList.add(ob);
//					//System.out.println(date1.toString("MM/yyyy"));
//					date1 = date1.plus(Period.months(1));
//
//				}
//
//				DateFormat df = new SimpleDateFormat("MM-YYYY");
//				System.out.println(date_Parheure);
//				String deb = df.format(this.getDate_mois_debut());
//				String fin = df.format(this.getDate_mois_fin());
//
//				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");
//
//				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
//			}
//
//			lstByCategory = stat_remote.getStatFraude("category.nomCategorie", "count(distinct msisdn)", where_liste);
//
//			if (this.getCategory() != null) {
//				where_liste.add("category.id =" + this.getCategory().getId());
//				where_liste1.add("id_categorie= " + this.getCategory().getId());
//
//				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
//			}
//
//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}
//
//	//		lstByLocation = stat_remote.getStatFraude("location", "count(distinct msisdn)", where_liste);
//
//			// if(this.getChoix_periode().equals("Par Heure")){
//			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
//			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
//			// "", where_liste));
//			// staticListStatMsc = removeDuplicate(generateList,
//			// stat_remote.getStatFraude(x, y, where));
//			//
//			//
//			// }else
//			if (this.getChoix_periode().equals("Par An")) {
////				return staticListStatMsc = removeDuplicate(generateList,
////						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
////								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//			} else if (this.getChoix_periode().equals("Par Mois")) {
////				return staticListStatMsc = removeDuplicate(generateList,
////						stat_remote.getStatFraude("", "SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//			} else if (this.getChoix_periode().equals("Par Jour")) {
//
//			//	return staticListStatMsc = removeDuplicate(generateList,
//				//		stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
//					//			"SUM(duree),SUM(nbAppel),count(msisdn)", where_liste));
//				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				// "SUM(duree),SUM(nbAppel)", where_liste);
//
//				// data =statRemote.getMscByFilters("
//				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
//				// chd.getOperation()+"(","Group By
//				// to_date(dateDecision,'YYMMDD') ORDER BY
//				// to_date(dateDecision,'YYMMDD') DESC", where_liste);
//
//			}
//
//			if (staticListStatMsc != null) {
//				chartDisplayed = true;
//			//	return staticListStatMsc;
//			} else {
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));
//
//			}
//
//			// data =statRemote.getMscByFilters("
//			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
//			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
//			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);
//
//		}
//		return lstByCategory;
//	}
//	

	@POST
	@Path("/repfraude2")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest2(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat) throws ParseException {

		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();
		where_liste2 = new ArrayList<String>();
		where_liste3 = new ArrayList<String>();
		List<String> where_liste1 = new ArrayList<>();
		list_id_categorie = new ArrayList<>();
		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;
//
		choix_periode = type_Filtre;

		List<CategoriesFraude> categoriesList = new ArrayList<>();
		String[] categoryStrings = listcat.split(",");
		for (String categoryString : categoryStrings) {
			String[] parts = categoryString.split(":");
			if (parts.length == 2) {
				int id = Integer.parseInt(parts[0]);
				String namecategorie = parts[1];
				CategoriesFraude catego = new CategoriesFraude();
				catego.setId(id);
				catego.setNomCategorie(namecategorie);
				categoriesList.add(catego);
			}
		}
		lst_CategoriesFraudes = categoriesList;

		if (this.list_id_categorie == null || this.list_id_categorie.isEmpty()) {
			// where_liste.addAll( this.list_id_categorie);
			// where_liste1.add("id_categorie= " + this.getCategory().getId());

			// SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as
			// Category";

			for (int i = 0; i < lst_CategoriesFraudes.size(); i++) {
				list_id_categorie.add(lst_CategoriesFraudes.get(i).getId());
				// where_liste.addAll( this.list_id_categorie);
			}
		}

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					generateList.add(ob);

				}

				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());

//				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//				+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//		where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//				+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
//

				where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date(" + "'"
						+ fin + "'" + ",'yyMMdd') ");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
				where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
						+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");

				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("YYYY");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());
				where_liste2.add(
						"to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin + "'" + "");
				where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
						+ "'" + "");// where_liste.add(" Extract(year from to_date(date_detection,'YYMMDD')) >=
									// Extract(year from to_date("

				// + "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_detection,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("YYMM");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());
				where_liste2
						.add("to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");

				where_liste.add(
						"to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			if (this.getChoix_periode().equals("Par An")) {
				// staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection,
				// 'YYYY' )",
				// "count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
				// "SUM(duree) as dur e,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie(" ", " ", list_id_categorie, where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				// staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection,
				// 'yyyy-MM' )",
				// "count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as dur e,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste);
			} else if (this.getChoix_periode().equals("Par Jour")) {

//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
//								"SUM(duree),SUM(nbAppel),count(msisdn)", where_liste));
//				 staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
//				 "SUM(duree),SUM(nbAppel)", where_liste); 
//				 data =statRemote.getMscByFilters("to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
//				 chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD') ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste2);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return staticListStatMscChart;
	}

	public void Valider_alerte() throws ParseException {

		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();
		where_liste2 = new ArrayList<String>();
		where_liste3 = new ArrayList<String>();
		List<String> where_liste1 = new ArrayList<>();

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					generateList.add(ob);

				}

				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date(" + "'"
						+ fin + "'" + ",'yyMMdd') ");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
				where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
						+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");

				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("YYYY");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());
				where_liste2.add(
						"to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin + "'" + "");
				where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
						+ "'" + "");// where_liste.add(" Extract(year from to_date(date_detection,'YYMMDD')) >=
									// Extract(year from to_date("

				// + "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_detection,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("YYMM");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());
				where_liste2
						.add("to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");

				where_liste.add(
						"to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			// lstByCategory = stat_remote.getStatFraude("category.nomCategorie",
			// "count(distinct msisdn)", where_liste);

			if (this.list_id_categorie == null || this.list_id_categorie.isEmpty()) {
				// where_liste.addAll( this.list_id_categorie);
				// where_liste1.add("id_categorie= " + this.getCategory().getId());

				// SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as
				// Category";

				for (int i = 0; i < lst_CategoriesFraudes.size(); i++) {
					list_id_categorie.add(lst_CategoriesFraudes.get(i).getId());
					// where_liste.addAll( this.list_id_categorie);
				}
			}

//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}

			// lstByLocation = stat_remote.getStatFraude("location ", "count(distinct
			// msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection, 'YYYY' )",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
				// "SUM(duree) as durée,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie(" ", " ", list_id_categorie, where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char( date_detection, 'yyyy-MM' )",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as durée,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste);
			} else if (this.getChoix_periode().equals("Par Jour")) {

				staticListStatMsc = stat_remote.getStatFraudeAlert("to_char(date_detection,'yyyy-mm-dd')",
						"count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as durée ,SUM(nb_appel) as nb_appel", where_liste1 , where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste2);
				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
	}

	private List<String> where_liste2;

	public List<String> getWhere_liste2() {
		return where_liste2;
	}

	public void setWhere_liste2(List<String> where_liste2) {
		this.where_liste2 = where_liste2;
	}

	private List<String> where_liste3;

	public List<String> getWhere_liste3() {
		return where_liste3;
	}

	public void setWhere_liste3(List<String> where_liste3) {
		this.where_liste3 = where_liste3;
	}

	private List<Object[]> staticListStatMscChart = new ArrayList<>();

	public List<Object[]> getStaticListStatMscChart() {
		return staticListStatMscChart;
	}

	public void setStaticListStatMscChart(List<Object[]> staticListStatMscChart) {
		this.staticListStatMscChart = staticListStatMscChart;
	}

	private List<Integer> list_id_categorie = new ArrayList<>();

	public List<Integer> getList_id_categorie() {
		return list_id_categorie;
	}

	public void setList_id_categorie(List<Integer> list_id_categorie) {
		this.list_id_categorie = list_id_categorie;
	}

	@POST
	@Path("/repfraudealert")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest4(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat) throws ParseException {
		where_liste = new ArrayList<String>();
		choix_periode = type_Filtre;
		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;
		list_id_categorie = new ArrayList<>();
		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();
		where_liste2 = new ArrayList<String>();
		where_liste3 = new ArrayList<String>();
		List<String> where_liste1 = new ArrayList<>();

		List<CategoriesFraude> categoriesList = new ArrayList<>();
		String[] categoryStrings = listcat.split(",");
		for (String categoryString : categoryStrings) {
			String[] parts = categoryString.split(":");
			if (parts.length == 2) {
				int id = Integer.parseInt(parts[0]);
				String namecategorie = parts[1];
				CategoriesFraude catego = new CategoriesFraude();
				catego.setId(id);
				catego.setNomCategorie(namecategorie);
				categoriesList.add(catego);
			}
		}
		lst_CategoriesFraudes = categoriesList;
		
		if (this.list_id_categorie == null || this.list_id_categorie.isEmpty()) {
			// where_liste.addAll( this.list_id_categorie);
			// where_liste1.add("id_categorie= " + this.getCategory().getId());

			// SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as
			// Category";

			for (int i = 0; i < lst_CategoriesFraudes.size(); i++) {
				list_id_categorie.add(lst_CategoriesFraudes.get(i).getId());
				// where_liste.addAll( this.list_id_categorie);
			}
		}

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') ");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
				where_liste2.add("stat.decision_fraude.date_decision Between to_timestamp(" + "'" + deb + "000000'"
						+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");
				where_liste3.add(" to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss') Between to_timestamp(" + "'" + deb + "000000'"
						+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");


				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste3.add("  to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss')= to_timestamp(" + "'" + deb + " 000000'" + ",'yyyy-MM-dd hh24miss')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());

				where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("MM-YYYY");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());

				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

		//	lstByCategory = stat_remote.getStatFraude("category.nomCategorie", "count(distinct msisdn)", where_liste);

//			if (this.getCategory() != null) {
//				where_liste.add("category.id =" + this.getCategory().getId());
//				where_liste1.add("id_categorie= " + this.getCategory().getId());
//
//				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
//			}
//
//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}
//
//			lstByLocation = stat_remote.getStatFraude("location  ", "count(distinct msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
//						"SUM(duree) as durée,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
//				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
//						" to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn",
//						where_liste1, where_liste3);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("", "SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn",
//						"SUM(duree) as durée,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
//				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
//						"to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn",
//						where_liste1, where_liste3);
			} else if (this.getChoix_periode().equals("Par Jour")) {

				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
//				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn",
//						"SUM(duree) as durée ,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
//				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
//						" to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn",
//						where_liste1, where_liste3);
				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}


			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return staticListStatMsc;
	}

	private List<Object[]> lstByDestination = new ArrayList<>();

	public List<Object[]> getLstByDestination() {
		return lstByDestination;
	}

	public void setLstByDestination(List<Object[]> lstByDestination) {
		this.lstByDestination = lstByDestination;
	}

	private List<Object[]> lstByLocation2 = new ArrayList<>();

	public List<Object[]> getLstByLocation2() {
		return lstByLocation2;
	}

	public void setLstByLocation2(List<Object[]> lstByLocation2) {
		this.lstByLocation2 = lstByLocation2;
	}

	private List<Object[]> Locations = new ArrayList<>();

	public List<Object[]> getLocations() {
		return Locations;
	}

	public void setLocations(List<Object[]> locations) {
		Locations = locations;
	}

	Map springfieldMap = new Map();
	Map springfieldMap2 = new Map();

	public Map getSpringfieldMap() {
		return springfieldMap;
	}

	public void setSpringfieldMap(Map springfieldMap) {
		this.springfieldMap = springfieldMap;
	}

	public Map getSpringfieldMap2() {
		return springfieldMap2;
	}

	public void setSpringfieldMap2(Map springfieldMap2) {
		this.springfieldMap2 = springfieldMap2;
	}

	public void addMarker(Float lat, Float lng, String title) {
		springfieldMap.setWidth("350px").setHeight("250px").setCenter(new LatLong(lat, lng)).setZoom(3);
		springfieldMap.setAttribution("Elite Business Map");
		springfieldMap.setWidth("1500");
		// Places Layer
		Layer placesLayer = (new Layer()).setLabel("Places");
		placesLayer.addMarker(new Marker(new LatLong(lat, lng), title));

		springfieldMap.addLayer(placesLayer);
	}

	public void addMarker2(Float lat, Float lng, String title) {
		springfieldMap2.setWidth("350px").setHeight("250px").setCenter(new LatLong(lat, lng)).setZoom(3);
		springfieldMap2.setAttribution("Elite Business Map");
		springfieldMap2.setWidth("1500");
		// Places Layer
		Layer placesLayer = (new Layer()).setLabel("Places");
		placesLayer.addMarker(new Marker(new LatLong(lat, lng), title));

		springfieldMap2.addLayer(placesLayer);
	}

	@POST
	@Path("/repfraudealert2")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest6(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat, @FormParam("operateur") String operateur) throws ParseException {

		choix_periode = type_Filtre;
		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;
		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		operateur = operateur;
		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();

		List<String> where_liste1 = new ArrayList<>();

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				if (operateur.equals("onNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') And msisdn like '2491%'");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') And msisdn like '2491%'");
					try {
						where_liste2.add("stat.decision_fraude.date_decision Between to_timestamp(" + "'" + deb
								+ "000000'" + ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'"
								+ ",'yymmdd hh24miss') And msisdn like '2491%'");
					} catch (NullPointerException e) {
						// Handle the exception
						e.printStackTrace(); // or log the error
					}

					try {
						where_liste3.add(
								" to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss') Between to_timestamp("
										+ "'" + deb + "000000'" + ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin
										+ "235959'" + ",'yymmdd hh24miss') And msisdn like '2491%'");
					} catch (NullPointerException e) {
						// Handle the exception
						e.printStackTrace(); // or log the error
					}

				} else if (operateur.equals("offNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') And msisdn not like '2491%'");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') And msisdn not like '2491%'");
					where_liste2.add("stat.decision_fraude.date_decision Between to_timestamp(" + "'" + deb + "000000'"
							+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'"
							+ ",'yymmdd hh24miss') And msisdn not like '2491%'");
					where_liste3.add(
							" to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss') Between to_timestamp("
									+ "'" + deb + "000000'" + ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin
									+ "235959'" + ",'yymmdd hh24miss') And msisdn not like '2491%'");

				} else {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') ");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
					where_liste2.add("stat.decision_fraude.date_decision Between to_timestamp(" + "'" + deb + "000000'"
							+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");
					where_liste3.add(
							" to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss') Between to_timestamp("
									+ "'" + deb + "000000'" + ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin
									+ "235959'" + ",'yymmdd hh24miss')");
				}
				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				if (operateur.equals("onNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
					where_liste3.add(
							"  to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss')= to_timestamp("
									+ "'" + deb + " 000000'" + ",'yyyy-MM-dd hh24miss') And msisdn like '2491%'");
				} else if (operateur.equals("offNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'"
							+ ",'yyyy-MM-dd') And msisdn not like '2491%'");
					where_liste3.add(
							"  to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss')= to_timestamp("
									+ "'" + deb + " 000000'" + ",'yyyy-MM-dd hh24miss') And msisdn not like '2491%'");

				} else {
					where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
					where_liste3.add(
							"  to_timestamp(to_char(date_decision,'yymmdd hh24miss') ,'yymmdd hh24miss')= to_timestamp("
									+ "'" + deb + " 000000'" + ",'yyyy-MM-dd hh24miss')");
				}
				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());
				if (operateur.equals("onNet")) {
					where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
							+ "'" + deb + "'"
							+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
							+ "'" + fin + "'" + ",'yyyy-MM-dd')) And msisdn like '2491%' ");
					where_liste1
							.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
									+ "'" + deb + "'"
									+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
									+ "'" + fin + "'" + ",'yyyy-MM-dd')) And msisdn like '2491%'");
				} else if (operateur.equals("offNet")) {
					where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
							+ "'" + deb + "'"
							+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
							+ "'" + fin + "'" + ",'yyyy-MM-dd')) And msisdn not like '2491%'");
					where_liste1
							.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
									+ "'" + deb + "'"
									+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
									+ "'" + fin + "'" + ",'yyyy-MM-dd')) And msisdn not like '2491%'");
				} else {
					where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
							+ "'" + deb + "'"
							+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
							+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
					where_liste1
							.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
									+ "'" + deb + "'"
									+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
									+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
				}

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("MM-YYYY");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());
				if (operateur.equals("onNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY') And msisdn like '2491%'");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'"
							+ ",'MM-YYYY') And msisdn like '2491%'");

				} else if (operateur.equals("offNet")) {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + fin + "'"
							+ ",'MM-YYYY')  And msisdn not like '2491%'");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'"
							+ ",'MM-YYYY')  And msisdn not like '2491%'");
				} else {
					where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");
				}

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			lstByCategory = stat_remote.getStatFraude("category.nomCategorie", "count(distinct msisdn)", where_liste);
			lstByDestination = stat_remote.getStatFraude2("t2.dest", "round(SUM(duree)/60.0,2)", where_liste1);
			if (this.getCategory() != null) {
				where_liste.add("category.id =" + this.getCategory().getId());
				where_liste1.add("id_categorie= " + this.getCategory().getId());

				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
			}

			if (bypass) {
				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);

				if (!this.getOperateur().equals("Tous")) {
					where_liste.add("msisdn like " + this.getOperateur() + "");

				}

			}
			where_liste.add("(location <> '0' )");
			where_liste.add("location IS NOT NULL");

			// ss lstByLocation = stat_remote.getStatFraude("location ", "count(distinct
			// msisdn)", where_liste);
			// ss lstByLocation2 = stat_remote.getStatFraud("location ", "count(distinct
			// msisdn)", where_liste);
			int j = 0;
			System.out.println("******************************** befor lstbyLocation");
			System.out.println(lstByLocation.size());
			System.out.println(lstByLocation2.size());
			for (Object[] loc : lstByLocation) {
				String loct = (String) loc[0];
				Locations = (List<Object[]>) stat_remote.getLoct(loct);
				if (Locations.size() == 0) {
					continue;
				}
				try {
					System.out.println("**** 2" + Locations.get(0)[2]);
					System.out.println("**** 1" + Locations.get(0)[1]);
					System.out.println("**** 0" + Locations.get(0)[0]);

					System.out.println(Locations.get(0)[0]);
					System.out.println("la location ********* " + Locations.get(0)[0]);
					String name = (String) Locations.get(0)[0];
					Float att = (Float) Locations.get(0)[1];
					Float lng = (Float) Locations.get(0)[2];
					System.out.println("------------------------");
					System.out.println(att);
					System.out.println(lng);
					System.out.println("------------------------");
					System.out.println("atitude = " + Locations.get(0)[0] + "*** longitude = " + Locations.get(0)[1]);
					addMarker(lng, att, name);

					j++;
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();

					System.out.println("la location  " + loct + "n existe pas");
				}

			}
			for (Object[] l : lstByLocation2) {
				String loct = (String) l[0];
				Locations = (List<Object[]>) stat_remote.getLoct(loct);
				if (Locations.size() == 0) {
					continue;
				}
				try {
					System.out.println("**** 2" + Locations.get(0)[2]);
					System.out.println("**** 1" + Locations.get(0)[1]);
					System.out.println("**** 0" + Locations.get(0)[0]);

					System.out.println(Locations.get(0)[0]);
					System.out.println("la location ********* " + Locations.get(0)[0]);
					String name = (String) Locations.get(0)[0];
					Float att = (Float) Locations.get(0)[1];
					Float lng = (Float) Locations.get(0)[2];
					System.out.println("------------------------");
					System.out.println(att);
					System.out.println(lng);
					System.out.println("------------------------");
					System.out.println("atitude = " + Locations.get(0)[0] + "*** longitude = " + Locations.get(0)[1]);
					addMarker2(lng, att, name);

					j++;
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();

					System.out.println("la location  " + loct + "n existe pas");
				}

			}
			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
								"round(SUM(duree)/60.0,2),SUM(nbAppel),count(distinct msisdn)", where_liste1));
				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
						"round(SUM(duree)/60.0,2) as durée,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
						" to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn", where_liste1,
						where_liste3);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				staticListStatMsc = removeDuplicate(generateList, stat_remote.getStatFraude("",
						"round(SUM(duree)/60.0,2),SUM(nbAppel),count(distinct msisdn)", where_liste));
				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn",
						"round(SUM(duree)/60.0,2) as durée,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
						"to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn", where_liste1,
						where_liste3);
			} else if (this.getChoix_periode().equals("Par Jour")) {

				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
								"round(SUM(duree)/60.0,2),SUM(nbAppel),count(distinct msisdn)", where_liste1));
				staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn",
						"round(SUM(duree)/60.0,2) as durée ,SUM(nb_appel) as nb_appel", where_liste1, where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeMsisdnLine(
						" to_char(min(date_decision),'yyyy-mm-dd') as date_detection ", "msisdn", where_liste1,
						where_liste3);
				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return lstByCategory;
	}

	@POST
	@Path("/repfraudealert1")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest5(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat) throws ParseException {

		choix_periode = type_Filtre;
		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;

		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();
		staticListStatMscChart = new ArrayList<>();
		where_liste2 = new ArrayList<String>();
		where_liste3 = new ArrayList<String>();
		List<String> where_liste1 = new ArrayList<>();

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<CategoriesFraude> categoriesList = new ArrayList<>();
		String[] categoryStrings = listcat.split(",");
		for (String categoryString : categoryStrings) {
			String[] parts = categoryString.split(":");
			if (parts.length == 2) {
				int id = Integer.parseInt(parts[0]);
				String namecategorie = parts[1];
				CategoriesFraude catego = new CategoriesFraude();
				catego.setId(id);
				catego.setNomCategorie(namecategorie);
				categoriesList.add(catego);
			}
		}
		lst_CategoriesFraudes = categoriesList;

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					generateList.add(ob);

				}

				DateFormat df = new SimpleDateFormat("yyMMdd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());

				if (operateur != null) {

					if (operateur.equals("'2491%'")) {
						where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date("
								+ "'" + fin + "'" + ",'yyMMdd') And msisdn like '2491%' ");
						where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
								+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd') And msisdn like '2491%' ");
						where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
								+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'"
								+ ",'yymmdd hh24miss') And msisdn like '2491%'");

					} else if (operateur.equals("not like '2491%'")) {
						where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date("
								+ "'" + fin + "'" + ",'yyMMdd') And msisdn not like '2491%'");
						where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
								+ ",'yyMMdd') And to_date(" + "'" + fin + "'"
								+ ",'yyMMdd') And msisdn not like '2491%' ");
						where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
								+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'"
								+ ",'yymmdd hh24miss') And msisdn not like '2491%'");

					}
				} else {
					where_liste.add(" date_detection Between to_date(" + "'" + deb + "'" + ",'yyMMdd') And to_date("
							+ "'" + fin + "'" + ",'yyMMdd') ");
					where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
							+ ",'yyMMdd') And to_date(" + "'" + fin + "'" + ",'yyMMdd')");
					where_liste2.add("date_detection Between to_timestamp(" + "'" + deb + "000000'"
							+ ",'yymmdd hh24miss') And to_timestamp(" + "'" + fin + "235959'" + ",'yymmdd hh24miss')");
				}
				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				if (operateur.equals("'2491%'")) {
					where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'"
							+ ",'yyyy-MM-dd') And msisdn like '2491%' ");
					where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'"
							+ ",'yyyy-MM-dd') And msisdn like '2491%' ");
				} else if (operateur.equals("not like '2491%'")) {
					where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'"
							+ ",'yyyy-MM-dd') And msisdn not like '2491%'");
					where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'"
							+ ",'yyyy-MM-dd') And msisdn not like '2491%'");

				} else {
					where_liste.add(" to_date(date_detection,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
					where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				}
				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("YYYY");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());
				if (operateur.equals("'2491%'")) {
					where_liste2.add("to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + " And msisdn like '2491%' ");
					where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + " And msisdn like '2491%' ");
				} else if (operateur.equals("not like '2491%'")) {
					where_liste2.add("to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + " And msisdn not like '2491%'");
					where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + " And msisdn not like '2491%'");
				} else {
					where_liste2.add("to_char(date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + "");
					where_liste.add("to_char(t1.date_detection,'YYYY') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'" + "");
				}
				// where_liste.add(" Extract(year from to_date(date_detection,'YYMMDD')) >=
				// Extract(year from to_date("

				// + "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_detection,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
//				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
//						+ "'" + deb + "'"
//						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
//						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("YYMM");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());
				if (operateur.equals("'2491%'")) {
					where_liste2.add("to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'  And msisdn like '2491%' ");

					where_liste.add("to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "'  And msisdn like '2491%' ");
//			where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//					+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				} else if (operateur.equals("not like '2491%'")) {
					where_liste2.add("to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "' And msisdn not like '2491%'");

					where_liste.add("to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "' And msisdn not like '2491%' ");

				} else {
					where_liste2.add(
							"to_char(date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin + "' ");

					where_liste.add("to_char(t1.date_detection,'yymm') Between " + "'" + deb + "'" + " And " + "'" + fin
							+ "' ");
				}
//				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
//						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			// lstByCategory = stat_remote.getStatFraude("category.nomCategorie",
			// "count(distinct msisdn)", where_liste);

			if (this.list_id_categorie == null || this.list_id_categorie.isEmpty()) {
				// where_liste.addAll( this.list_id_categorie);
				// where_liste1.add("id_categorie= " + this.getCategory().getId());

				// SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as
				// Category";

				for (int i = 0; i < lst_CategoriesFraudes.size(); i++) {
					list_id_categorie.add(lst_CategoriesFraudes.get(i).getId());
					// where_liste.addAll( this.list_id_categorie);
				}
			}

//			if (bypass) {
//				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);
//
//				if (!this.getOperateur().equals("Tous")) {
//					where_liste.add("msisdn like " + this.getOperateur() + "");
//
//				}
//
//			}

			// lstByLocation = stat_remote.getStatFraude("location ", "count(distinct
			// msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				// nbn staticListStatMsc = stat_remote.getStatFraudeAlert("to_char(
				// date_detection, 'YYYY' )",
				// nbn "count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn(" msisdn ",
				// "SUM(duree) as durée,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie(" ", " ", list_id_categorie, where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				// nbn staticListStatMsc = stat_remote.getStatFraudeAlert("to_char(
				// date_detection, 'yyyy-MM' )",
				// nbnb "count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as durée,SUM(nb_appel) as nb_appel", where_liste1,where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste);
			} else if (this.getChoix_periode().equals("Par Jour")) {

				// nbn staticListStatMsc =
				// stat_remote.getStatFraudeAlert("to_char(date_detection,'yyyy-mm-dd')",
				// nbn "count(distinct(msisdn))", list_id_categorie, where_liste2);
				// staticListStatMscTAB = stat_remote.getStatFraudeMsisdn("msisdn", "SUM(duree)
				// as durée ,SUM(nb_appel) as nb_appel", where_liste1 , where_liste2);
				staticListStatMscChart = stat_remote.getStatFraudeAlertPie("", "", list_id_categorie, where_liste2);
				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return staticListStatMscChart;
	}

	@POST
	@Path("/repfraude3")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
	public List<Object[]> Valider_alerte_Rest3(@FormParam("type_Filtre") String type_Filtre,
			@FormParam("startDate") Date startDate, @FormParam("endDate") Date endDate,
			@FormParam("list_cat") String listcat) throws ParseException {

		System.out.println(type_Filtre);
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(listcat);

		date_ParJourDeb = startDate;
		date_ParJourFin = endDate;

		date_mois_fin = endDate;
		date_mois_debut = startDate;
		date_an_fin = startDate;
		date_an_debut = endDate;

		choix_periode = type_Filtre;

		// lst_CategoriesFraudes = listcat;

		List<CategoriesFraude> categoriesList = new ArrayList<>();

		String[] categoryStrings = listcat.split(",");
		for (String categoryString : categoryStrings) {
			String[] parts = categoryString.split(":");
			if (parts.length == 2) {
				int id = Integer.parseInt(parts[0]);
				String namecategorie = parts[1];
				CategoriesFraude catego = new CategoriesFraude();
				catego.setId(id);
				catego.setNomCategorie(namecategorie);
				categoriesList.add(catego);
			}
		}

		lst_CategoriesFraudes = categoriesList;
		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();

		List<String> where_liste1 = new ArrayList<>();

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				// System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				// System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					// System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					// System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					// System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());

				where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					// System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("MM-YYYY");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());

				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			// lstByCategory = stat_remote.getStatFraude("category.nomCategorie",
			// "count(distinct msisdn)", where_liste);

			if (this.getCategory() != null) {
				where_liste.add("category.id =" + this.getCategory().getId());
				where_liste1.add("id_categorie= " + this.getCategory().getId());

				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
			}

			if (bypass) {
				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);

				if (!this.getOperateur().equals("Tous")) {
					where_liste.add("msisdn like " + this.getOperateur() + "");

				}

			}

			lstByLocation = stat_remote.getStatFraude("location", "count(distinct msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
//								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
				// "SUM(duree),SUM(nbAppel)", where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
//				return staticListStatMsc = removeDuplicate(generateList,
//						stat_remote.getStatFraude("", "SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
				// "SUM(duree),SUM(nbAppel)", where_liste);

			} else if (this.getChoix_periode().equals("Par Jour")) {

				// return staticListStatMsc = removeDuplicate(generateList,
				// stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD')
				// ",
				// "SUM(duree),SUM(nbAppel),count(msisdn)", where_liste));
				// staticListStatMscTAB = stat_remote.getStatFraude("msisdn",
				// "SUM(duree),SUM(nbAppel)", where_liste);

				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
				// return staticListStatMsc;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
		return lstByLocation;
	}

	public void Valider() throws ParseException {

		where_liste = new ArrayList<String>();
		staticListStatMsc = new ArrayList<>();

		List<String> where_liste1 = new ArrayList<>();

		List<Object[]> generateList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (this.getChoix_periode().equals(" ")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a Period"));
		} else {
			if (this.getChoix_periode().equals("Par Jour")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_ParJourDeb());
				String fin0 = df1.format(this.getDate_ParJourFin());
				DateTime start = DateTime.parse(deb0);
				System.out.println("Start: " + start);

				DateTime end = DateTime.parse(fin0);
				System.out.println("End: " + end);

				List<DateTime> between = getDateRange(start, end);
				for (DateTime d : between) {
					String s = d + "";

					System.out.println(" " + s.substring(8, 10));
					Object[] ob = new Object[4];
					ob[0] = s.substring(0, 10);
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
				String deb = df.format(this.getDate_ParJourDeb());
				String fin = df.format(this.getDate_ParJourFin());
				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'yyyy-MM-dd') And to_date(" + "'" + fin + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Period Between " + deb + " and " + fin;

			} else

			if (this.getChoix_periode().equals("Par Heure")) {

				for (int i = 0; i < heures.size(); i++) {
					Object[] ob = new Object[4];
					ob[0] = heures.get(i);
					System.out.println(heures.get(i));
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
				}
				System.out.println(generateList.size());
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_Parheure());
				System.out.println(deb);
				where_liste.add(" to_date(dateDecision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");
				where_liste1.add(" to_date(date_decision,'YYMMDD')= to_date(" + "'" + deb + "'" + ",'yyyy-MM-dd')");

				SubTitle = "Per schedule on the date " + deb;

			} else

			if (this.getChoix_periode().equals("Par An")) {

				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_an_debut());
				String fin0 = df1.format(this.getDate_an_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);

				while (date1.isBefore(date2) || date1.getYear() == date2.getYear()) {
					Object[] ob = new Object[5];
					ob[0] = date1.toString("yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;
					ob[4] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("yyyy"));
					date1 = date1.plus(Period.years(1));

				}
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				String deb = df.format(this.getDate_an_debut());
				String fin = df.format(this.getDate_an_fin());

				where_liste.add(" Extract(year from to_date(dateDecision,'YYMMDD')) >=   Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(dateDecision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");
				where_liste1.add(" Extract(year from to_date(date_decision,'YYMMDD')) >=  Extract(year from to_date("
						+ "'" + deb + "'"
						+ ",'yyyy-MM-dd')) And Extract(year from to_date(date_decision,'YYMMDD')) <= Extract(year from to_date("
						+ "'" + fin + "'" + ",'yyyy-MM-dd'))");

				SubTitle = "Between  " + deb.substring(0, 4) + " and  " + fin.substring(0, 4);
				// SubTitle ="Between Year "+deb+" and Year
				// "+date2.toString("DD-MM-YYYY");

			}

			else if (this.getChoix_periode().equals("Par Mois")) {
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String deb0 = df1.format(this.getDate_mois_debut());
				String fin0 = df1.format(this.getDate_mois_fin());
				LocalDate date1 = new LocalDate(deb0);
				LocalDate date2 = new LocalDate(fin0);
				date2 = date2.plus(Period.months(1));
				while (date1.isBefore(date2)) {
					Object[] ob = new Object[4];
					ob[0] = date1.toString("MM/yyyy");
					ob[1] = 0.0;
					ob[2] = 0.0;
					ob[3] = 0.0;

					generateList.add(ob);
					System.out.println(date1.toString("MM/yyyy"));
					date1 = date1.plus(Period.months(1));

				}

				DateFormat df = new SimpleDateFormat("MM-YYYY");
				System.out.println(date_Parheure);
				String deb = df.format(this.getDate_mois_debut());
				String fin = df.format(this.getDate_mois_fin());

				where_liste.add(" to_date(dateDecision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + fin + "'" + ",'MM-YYYY')");
				where_liste1.add(" to_date(date_decision,'YYMMDD') Between to_date(" + "'" + deb + "'"
						+ ",'MM-YYYY') And to_date(" + "'" + date2.toString("MM-YYYY") + "'" + ",'MM-YYYY')");

				SubTitle = "Between Month " + deb + " and Month " + date2.toString("MM-YYYY");
			}

			lstByCategory = stat_remote.getStatFraude("category.nomCategorie", "count(distinct msisdn)", where_liste);

			if (this.getCategory() != null) {
				where_liste.add("category.id =" + this.getCategory().getId());
				where_liste1.add("id_categorie= " + this.getCategory().getId());

				SubTitle = SubTitle + " with " + this.getCategory().getNomCategorie() + " as Category";
			}

			if (bypass) {
				lstByOperateur = stat_remote.getStatFraudeParOperateur("", "", where_liste1);

				if (!this.getOperateur().equals("Tous")) {
					where_liste.add("msisdn like " + this.getOperateur() + "");

				}

			}

			lstByLocation = stat_remote.getStatFraude("location", "count(distinct msisdn)", where_liste);

			// if(this.getChoix_periode().equals("Par Heure")){
			// //System.out.println(stat_remote.getAllStatMscStatic("substring(trancheHoraire,1,2)",
			// "SUM(moyenneDuree),SUM(duree),SUM(nbMoyenne),SUM(nbAppel)", "",
			// "", where_liste));
			// staticListStatMsc = removeDuplicate(generateList,
			// stat_remote.getStatFraude(x, y, where));
			//
			//
			// }else
			if (this.getChoix_periode().equals("Par An")) {
				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("Extract (year from to_date(dateDecision,'YYMMDD'))",
								"SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
				staticListStatMscTAB = stat_remote.getStatFraude("msisdn", "SUM(duree),SUM(nbAppel)", where_liste);

			} else if (this.getChoix_periode().equals("Par Mois")) {
				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("", "SUM(duree),SUM(nbAppel),count(distinct msisdn)", where_liste));
				staticListStatMscTAB = stat_remote.getStatFraude("msisdn", "SUM(duree),SUM(nbAppel)", where_liste);

			} else if (this.getChoix_periode().equals("Par Jour")) {

				staticListStatMsc = removeDuplicate(generateList,
						stat_remote.getStatFraude("to_char(to_date(dateDecision,'YYMMDD'),'YYYY-MM-DD') ",
								"SUM(duree),SUM(nbAppel),count(msisdn)", where_liste));
				staticListStatMscTAB = stat_remote.getStatFraude("msisdn", "SUM(duree),SUM(nbAppel)", where_liste);

				// data =statRemote.getMscByFilters("
				// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
				// chd.getOperation()+"(","Group By
				// to_date(dateDecision,'YYMMDD') ORDER BY
				// to_date(dateDecision,'YYMMDD') DESC", where_liste);

			}

			if (staticListStatMsc != null) {
				chartDisplayed = true;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "0 results for selected Filters "));

			}

			// data =statRemote.getMscByFilters("
			// to_date(dateDecision,'YYMMDD')",chd.getList_axe_y().get(nb_y)+")",
			// chd.getOperation()+"(","Group By to_date(dateDecision,'YYMMDD')
			// ORDER BY to_date(dateDecision,'YYMMDD') DESC", where_liste);

		}
	}

	public ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(),
				valueExpression, valueType);
	}

	public Date getDate_Parheure() {
		return date_Parheure;
	}

	public void setDate_Parheure(Date date_Parheure) {
		this.date_Parheure = date_Parheure;
	}

	public Date getDate_ParJourDeb() {
		return date_ParJourDeb;
	}

	public void setDate_ParJourDeb(Date date_ParJourDeb) {
		this.date_ParJourDeb = date_ParJourDeb;
	}

	public Date getDate_ParJourFin() {
		return date_ParJourFin;
	}

	public void handlechange1() {

		chartDisplayed = false;
	}

	public void setDate_ParJourFin(Date date_ParJourFin) {
		this.date_ParJourFin = date_ParJourFin;
	}

	public Integer getDate_year_deb() {
		return date_year_deb;
	}

	public void setDate_year_deb(Integer date_year_deb) {
		this.date_year_deb = date_year_deb;
	}

	public Integer getDate_year_fin() {
		return date_year_fin;
	}

	public void setDate_year_fin(Integer date_year_fin) {
		this.date_year_fin = date_year_fin;
	}

	public Date getDate_mois_debut() {
		return date_mois_debut;
	}

	public void setDate_mois_debut(Date date_mois_debut) {
		this.date_mois_debut = date_mois_debut;
	}

	public Date getDate_mois_fin() {
		return date_mois_fin;
	}

	public void setDate_mois_fin(Date date_mois_fin) {
		this.date_mois_fin = date_mois_fin;
	}

	public String getChoix_periode() {
		return choix_periode;
	}

	public void setChoix_periode(String choix_periode) {
		this.choix_periode = choix_periode;
	}

	public MethodExpression createActionMethodExpression(String name) {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		ELContext elContext = facesCtx.getELContext();
		return facesCtx.getApplication().getExpressionFactory().createMethodExpression(elContext, name, String.class,
				new Class[] {});
	}

	public static List<DateTime> getDateRange(DateTime start, DateTime end) {

		List<DateTime> ret = new ArrayList<DateTime>();
		DateTime tmp = start;
		while (tmp.isBefore(end) || tmp.equals(end)) {
			ret.add(tmp);
			tmp = tmp.plusDays(1);
		}
		return ret;
	}

	public List<Object[]> removeDuplicate(List<Object[]> list, List<Object[]> list1) {
		List<Object[]> liste = new ArrayList<>();
		int i = 0;
		int j = 0;
		if (list1 == null) {
			liste = list;
		} else if (list1.size() == list.size()) {

			while (j < list.size()) {

				if (list.get(j)[0].toString().equals(list1.get(i)[0].toString())) {
					liste.add(list1.get(i));
				} else {
					liste.add(list.get(j));
				}
				i++;
				j++;

			}
		} else if (list.size() > list1.size()) {
			System.out.println(list1.size());
			if (list.size() > 0) {
				for (int nb = 0; nb < list.size(); nb++) {

					boolean trouve = false;
					int nb1 = 0;
					while ((trouve == false) && (nb1 < list1.size())) {
						System.out.println(trouve);

						if (list.get(nb)[0].toString().equals(list1.get(nb1)[0].toString())) {

							trouve = true;

						} else {
							nb1++;
						}

					}
					if (trouve == false) {
						liste.add(list.get(nb));
					} else {
						liste.add(list1.get(nb1));
						System.out.println(list1.get(nb1)[2]);
					}

				}
			} else {
				liste = list;
			}

		}

		return liste;
	}

	public void handlechange3() {
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_jour_fin");
		cld.setMindate(this.getDate_ParJourDeb());
		chartDisplayed = false;
		System.out.println(this.getDate_ParJourDeb());

	}

	public void handlechange4() {
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_jour_debut");
		cld.setMaxdate(this.getDate_ParJourFin());
		chartDisplayed = false;
	}

	public void handlechange5() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_mois_debut");

		cld.setMaxdate(this.getDate_mois_fin());
		chartDisplayed = false;
	}

	public void handlechange6() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_mois_fin");
		System.out.println(this.getDate_mois_fin());

		cld.setMindate(this.getDate_mois_debut());
	}

	public void handlechange7() {
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_an_fin");
		cld.setMindate(this.getDate_an_debut());
		System.out.println(this.getDate_an_debut());
		chartDisplayed = false;
	}

	public void handlechange8() {
		Calendar cld = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:cld_an_debut");
		cld.setMaxdate(this.getDate_an_fin());
		chartDisplayed = false;
	}

	public void addAjax7(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange8}"),
						createActionMethodExpression("#{stat_fraude.handlechange8}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjax8(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange7}"),
						createActionMethodExpression("#{stat_fraude.handlechange7}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjax3(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange5}"),
						createActionMethodExpression("#{stat_fraude.handlechange5}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjax4(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange6}"),
						createActionMethodExpression("#{stat_fraude.handlechange6}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjax1(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange3}"),
						createActionMethodExpression("#{stat_fraude.handlechange3}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjax2(Calendar cld_jour_debut) {

		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange4}"),
						createActionMethodExpression("#{stat_fraude.handlechange4}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("dateSelect", ajax);

	}

	public void addAjaxkeyup(Calendar cld_jour_debut) {
		cld_jour_debut.setOnkeyup("return this.value.length >= 8");
		AjaxBehavior ajax = new AjaxBehavior();
		//
		ajax.addAjaxBehaviorListener(
				new AjaxBehaviorListenerImpl(createActionMethodExpression("#{stat_fraude.handlechange2}"),
						createActionMethodExpression("#{stat_fraude.handlechange2}")));
		ajax.setUpdate("@form");

		cld_jour_debut.addClientBehavior("keyup", ajax);

	}

	public void handlechange2(AjaxBehaviorEvent event) {

		chartDisplayed = false;
	}

	public List<Object[]> getStaticListStatMscTAB() {
		return staticListStatMscTAB;
	}

	public void setStaticListStatMscTAB(List<Object[]> staticListStatMscTAB) {
		this.staticListStatMscTAB = staticListStatMscTAB;
	}

}
