package com.fraude.managedBeans.config;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.fraude.entities.ChampRec;
import com.fraude.entities.EtlDataType;
import com.fraude.entities.EtlFieldFlow;
import com.fraude.entities.EtlFieldProc;
import com.fraude.entities.EtlProc;
import com.fraude.entities.EtlProcFlow;
import com.fraude.entities.EtlTypeFlow;
import com.fraude.entities.Flow;
import com.fraude.entities.RuleRecGauche;
import com.fraude.interfaces.FlowInterface;

@ManagedBean(name = "statview")
@ViewScoped
public class StatMbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823496149479065319L;

	@EJB
	private FlowInterface flow_service;

	private boolean aggregation = true;
	private boolean groupeby = true;

	private String selected_operator1;
	private String selected_function;

	private String selected_function1;

	private int id_filtre1 = 1;
	private int id_filtre = 1;
	private EtlFieldFlow champs_Type_Noeud_selected1;
	private EtlFieldProc new_etlfield_proc;
	private List<EtlFieldFlow> list_champs = new ArrayList<>();
	private boolean showglobalpan2;

	private boolean showrec2 = false;
	private boolean showglobalpan = true;
	private boolean showstat = true;
	private ChampRec selected_champ_rec_to_add;
	private EtlFieldFlow champs_Type_Noeud_selected;
	private boolean showRuleFitres = false;
	private Boolean showRuleFitres2 = false;

	private RuleRecGauche selected_rec_gauche;

	private TreeNode stat_control;
	private TreeNode selectedNode;

	private Flow selected_flow1;
	private List<EtlFieldFlow> list_champs1;
	private List<EtlFieldFlow> list_select_champs1;

	private List<ChampRec> list_champs_rec_gauche = new ArrayList<>();
	private List<Flow> list_flow = new ArrayList<>();
	private List<RuleRecGauche> list_rules_gauche = new ArrayList<>();

	private List<Flow> list_flow2 = new ArrayList<>();

	public static List<EtlFieldProc> list_etldield_proc = new ArrayList<>();
	public static List<EtlFieldProc> list_etldield_proc2 = new ArrayList<>();
	public static List<EtlFieldProc> list_etldield_proc3 = new ArrayList<>();

	private List<EtlFieldFlow> listfieldsOP = new ArrayList<>();
	private Flow selected_flow2;

	private List<EtlFieldFlow> list_champs_rec = new ArrayList<>();

	private List<EtlFieldFlow> list_select_rec;
	private List<ChampRec> list_champs_rec_gauche2 = new ArrayList<>();

	private EtlFieldFlow champs_Type_Noeud_selected2;

	private List<RuleRecGauche> list_rules_gauche2;

	private EtlProc new_etl_procF = new EtlProc();

	private EtlProcFlow epf;

	private List<EtlProc> list_proc_stat = new ArrayList<>();
	private String selecteted_functionagg;
	private String selecteted_functionaGroupe;

	public void validFiltreproc() {
		System.out.println("begin inserting of etl_proc_Flow");
		new_etl_procF = new EtlProc();
		new_etl_procF.setProc_type("STAT");
		new_etl_procF.setName(flowRec.getTable_name());
		flow_service.addProc(new_etl_procF);
		epf.setFlow(selected_flow1);
		epf.setProc(new_etl_procF);
		System.out.println("epf1  id --- " + epf.getId());
		String filtre = "";
		for (RuleRecGauche rrg : list_rules_gauche) {
			if (rrg.getParenteses_ouvrant() != null) {
				for (String s : rrg.getParenteses_ouvrant()) {
					filtre = filtre + s;
				}
			}
			for (ChampRec rc : rrg.getList_champs_rec1()) {
				if (rc.getOperator() != null) {
					filtre = filtre + rc.getOperator();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre = filtre + fn + "(";
					}
				}
				if (rc.getChamp().getLibelle() != null) {
					filtre = filtre + rc.getChamp().getLibelle();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre = filtre + ")";
					}
				}
			}
			if (rrg.getValeur() != null) {
				filtre = filtre + rrg.getOperator() + rrg.getValeur();
			}

			if (rrg.getParenteses_fermant() != null) {
				for (String s : rrg.getParenteses_fermant()) {
					filtre = filtre + s;
				}
			}

			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				filtre = filtre + rrg.getLogic();
			}
		}
		epf.setQuery_filters(filtre);
		System.out.println(selected_flow1.getId());
		flow_service.addEtlProcFlow(epf);

	}

	private String type_valeur;
	private boolean showstring;
	private boolean showvaleur;
	private boolean showfield;
	private boolean showlisttype;
	private List<EtlFieldFlow> listfield = new ArrayList<>();

	public void changetypeinsertingvalue() {
		if (type_valeur.equals("valeur")) {
			showvaleur = true;
			showstring = false;
			showfield = false;
		}
		if (type_valeur.equals("field")) {
			showfield = true;
			showstring = false;
			showvaleur = false;
			listfield = flow_service.getAllChamps(selected_flow1);
		}
		if (type_valeur.equals("String")) {
			showstring = true;
			showfield = false;
			showvaleur = false;
		}
		if (type_valeur.equals("type")) {
			RequestContext.getCurrentInstance().update("changeinput");
		}

	}

	private Flow flowRec;
	private EtlTypeFlow flow_type = new EtlTypeFlow();

	private List<EtlFieldFlow> old_list_agg = new ArrayList<>();
	private List<EtlFieldFlow> old_list_groupe = new ArrayList<>();

	private boolean showflow;

	public void insertnewflowREC() {

		System.out.println("begin inserting of etl_proc_Flow");
		new_etl_procF = new EtlProc();
		new_etl_procF.setProc_type("STAT");
		new_etl_procF.setName(flowRec.getTable_name());

		EtlProcFlow epf = new EtlProcFlow();
		epf.setFlow(selected_flow1);
		epf.setProc(new_etl_procF);
		System.out.println("epf1  id --- " + epf.getId());
		String filtre = "";

		for (RuleRecGauche rrg : list_rules_gauche) {
			if (rrg.getParenteses_ouvrant() != null) {
				for (String s : rrg.getParenteses_ouvrant()) {
					filtre = filtre + s;
				}
			}
			for (ChampRec rc : rrg.getList_champs_rec1()) {
				if (rc.getOperator() != null) {
					filtre = filtre + rc.getOperator();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre = filtre + fn + "(";
					}
				}
				if (rc.getChamp().getLibelle() != null) {
					filtre = filtre + rc.getChamp().getLibelle();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre = filtre + ")";
					}
				}
			}
			if (rrg.getValeur() != null) {
				filtre = filtre + rrg.getOperator() + rrg.getValeur();
			}

			if (rrg.getParenteses_fermant() != null) {
				for (String s : rrg.getParenteses_fermant()) {
					filtre = filtre + s;
				}
			}
			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				filtre = filtre + rrg.getLogic();
			}
		}
		epf.setQuery_filters(filtre);
		System.out.println(selected_flow1.getId());
		flow_service.addEtlProcFlow(epf);

		flowRec.setProc(new_etl_procF);
		flow_service.addFlow(flowRec);
		flowRec.setProc(new_etl_procF);
		flow_service.updateFlow(flowRec);

		// old_list = (List<EtlFieldFlow>)
		// list_etldield_proc2.get(0).getField();
		System.out.println("id of epf--- " + epf.getId() + flowRec.getId());

		flow_service.insertflowstat(old_list_agg, flowRec, epf);
		flow_service.insertflowstat(old_list_groupe, flowRec, epf);
		// flow_service.insertlistfieldSTAT(listeRenameField, flowRec, epf);
		flow_service.insertlistfieldSTAT_agg(old_list_agg, flowRec, epf);
		flow_service.insertlistfieldSTAT_Groupe(old_list_groupe, flowRec, epf);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("Stat.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		old_list_agg = new ArrayList<>();
		list_etldield_proc = new ArrayList<>();
		list_etldield_proc2 = new ArrayList<>();
		list_etldield_proc3 = new ArrayList<>();
		functions_agg = new ArrayList<>();
		flowRec = new Flow();
		list_Groupe_champs = new ArrayList<>();
		functions_group = new ArrayList<>();
		selected_champ_agg = new ChampRec();
		list_agg_champs = new ArrayList<>();
		flowRec = new Flow();
		epf = new EtlProcFlow();
		new_etl_procF = new EtlProc();
		list_rules_gauche2 = new ArrayList<>();
		list_rules_gauche = new ArrayList<>();

	}

	public EtlFieldFlow deepCopy(EtlFieldFlow input) {

		EtlFieldFlow copy = new EtlFieldFlow();
		copy.setData_type(input.getData_type());
		copy.setFlow(input.getFlow());
		copy.setFormat(input.getFormat());
		copy.setLength_data(input.getLength_data());
		copy.setMapping(input.getMapping());
		copy.setPrecision_data(input.getPrecision_data());
		copy.setPrincipalField(input.getPrincipalField());
		copy.setLibelle(input.getLibelle());
		copy.setId(input.getId());
		return copy;

	}

	public void onRuleGaucheDrop2() {
		//
		System.out.println("begin of add filtre gauche");
		list_champs_rec_gauche2 = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setId(id_filtre1);
		cr.setChamp(champs_Type_Noeud_selected2);
		cr.setFonctions(new ArrayList<>());
		list_champs_rec_gauche2.add(cr);
		id_filtre1++;

		System.out.println("midle of add filtre gauche");

		RuleRecGauche rrg = new RuleRecGauche();
		System.out.println("rrg 1 ");
		rrg.setId(id_filtre);
		System.out.println("rrg 2");
		rrg.setList_champs_rec1(list_champs_rec_gauche2);
		rrg.setParenteses_ouvrant(new ArrayList<>());
		rrg.setParenteses_fermant(new ArrayList<>());
		System.out.println(" rrg 3 ");

		try {
			System.out.println("try 1");
			list_rules_gauche2.add(rrg);
			System.out.println("try 2");

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (RuleRecGauche r : list_rules_gauche2) {
			System.out.println(r.getList_champs_rec1().get(0).getChamp().getLibelle());
		}

		id_filtre++;

	}

	public void NextTo() {
		System.out.println("next button 2 filtre ");
		selected_champ_rec_to_add = new ChampRec();
		champs_Type_Noeud_selected = new EtlFieldFlow();
		showRuleFitres = true;
		showRuleFitres2 = true;

	}

	public void addChampToruleGauche() {
		System.out.println("ADD to ");
		ChampRec rec1 = new ChampRec();
		rec1.setChamp(champs_Type_Noeud_selected2);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator1);
		selected_rec_gauche.getList_champs_rec1().add(rec1);

	}

	public void onFieldDrop2(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_rec.add(car);
		list_champs_rec.remove(car);
	}

	public void loadChamps1() {

		setList_champs1(new ArrayList<>());
		setList_champs1(selected_flow1.getListe_champs());
		EtlFieldFlow star = new EtlFieldFlow();
		star.setLibelle("*");
		list_champs1.add(0, star);
		setList_select_champs1(new ArrayList<>());

	}

	public void loadChamps12() {

		setList_champs_rec(new ArrayList<>());
		setList_champs_rec(selected_flow2.getListe_champs());
		setList_select_rec(new ArrayList<>());

	}

	public void switchView() {

		if (selectedNode != null) {
			if (((Doc) selectedNode.getData()).getName().equals("Stat Controle")) {

				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("stat_control.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	private List<EtlProcFlow> Listetlprocflow = new ArrayList<>();
	List<EtlTypeFlow> list_type_flow;
	private List<EtlDataType> list_data_type;

	@PostConstruct
	public void init() {
		list_select_champs1 = new ArrayList<>();

		old_list_agg = new ArrayList<>();
		list_etldield_proc = new ArrayList<>();
		list_etldield_proc2 = new ArrayList<>();
		list_etldield_proc3 = new ArrayList<>();
		functions_agg = new ArrayList<>();
		flowRec = new Flow();
		list_Groupe_champs = new ArrayList<>();
		functions_group = new ArrayList<>();
		selected_champ_agg = new ChampRec();
		list_agg_champs = new ArrayList<>();
		flowRec = new Flow();
		epf = new EtlProcFlow();
		new_etl_procF = new EtlProc();
		list_rules_gauche2 = new ArrayList<>();
		list_rules_gauche = new ArrayList<>();

		list_data_type = flow_service.getAllDataType();
		list_type_flow = flow_service.getAllTypeFlow();

		functions_agg.add("SUM");
		functions_agg.add("COUNT");
		functions_agg.add("AVG");
		functions_agg.add("substr");
		functions_agg.add("TO_INTEGER");
		functions_agg.add("TO_DATE");
		functions_agg.add("distinct");

		functions_group.add("substr");
		functions_group.add("TO_INTEGER");
		functions_group.add("TO_DATE");

		list_proc_stat = flow_service.getAllprocStat();
		flow_type = flow_service.getTypeflow();

		list_flow2 = flow_service.getAllFlow();
		list_flow = flow_service.getAllFlow();
		// ******************************** tree node lister
		// *******************************
		stat_control = new DefaultTreeNode(new Doc(null, "Stat Controle"), null);

		for (EtlProc e : list_proc_stat) {

			TreeNode stat = new DefaultTreeNode(new Doc(e, e.getName()), stat_control);

			TreeNode Flow = new DefaultTreeNode(new Doc(null, "Flow"), stat);
			setListetlprocflow(flow_service.getAllprocflowbyidprocs(e.getId()));
			List<BigInteger> list_id_procsFlow = new ArrayList<>();
			list_id_procsFlow = flow_service.getprocflowbyidprocs(e.getId());

			Flow f = flow_service.getFlowbyid(list_id_procsFlow.get(0));
			TreeNode table;
			table = new DefaultTreeNode(new Doc(f, f.getFlowName()), Flow);
			EtlProcFlow filtre = new EtlProcFlow();
			filtre = flow_service.get_query_filtre(e.getId(), f.getId());

			TreeNode myf = new DefaultTreeNode(new Doc(null, "Filtre"), table);

			TreeNode filtre_table1 = new DefaultTreeNode(new Doc(filtre, filtre.getQuery_filters()), myf);

			List<EtlFieldProc> list_field = flow_service.getallfieldprocbyprocflow(filtre.getId());

			TreeNode fields = new DefaultTreeNode(new Doc(null, "Fields"), table);
			TreeNode agg = new DefaultTreeNode(new Doc(null, "Aggregation"), table);
			TreeNode Groupe = new DefaultTreeNode(new Doc(null, "Groupe By"), table);

			for (EtlFieldProc fp : list_field) {

				EtlFieldFlow eff = flow_service.getfieldflowbyid(fp.getId_field_dest());
				EtlFieldFlow eff2 = flow_service.getfieldflowbyid(fp.getField().getId());

				TreeNode id_of_field = new DefaultTreeNode(new Doc(fp, "field_origine :" + eff2.getLibelle()), fields);
				TreeNode id_of_field_dest = new DefaultTreeNode(new Doc(fp, "field_dest :" + eff.getLibelle()),
						id_of_field);

				if (fp.getType_field().equals("Aggregation")) {
					TreeNode agg_field = new DefaultTreeNode(new Doc(fp, "field_origine :" + fp.getField().getId()),
							agg);
					TreeNode agg_field_dest = new DefaultTreeNode(new Doc(fp, "FUNCTION :" + fp.getFunction()),
							agg_field);
				}

				if (fp.getType_field().equals("Group By")) {
					TreeNode group_field = new DefaultTreeNode(new Doc(fp, "id_origine :" + fp.getField().getId()),
							Groupe);
					TreeNode group_field_dest = new DefaultTreeNode(new Doc(fp, "FUNCTION:" + fp.getFunction()),
							group_field);
				}

			}

		}

	}

	public List<EtlTypeFlow> getList_type_flow() {
		return list_type_flow;
	}

	public void setList_type_flow(List<EtlTypeFlow> list_type_flow) {
		this.list_type_flow = list_type_flow;
	}

	private boolean show_listfild_rename = false;
	private boolean showRenamefield = false;

	public static List<EtlFieldFlow> listeRenameField = new ArrayList<>();

	public void cache() {
		old_list_agg = new ArrayList<>();
		showglobalpan = false;
		setShowrec2(true);
		setShowglobalpan2(true);
		showRuleFitres = false;
		showglobalpan = false;
		showRuleFitres = false;
		showRuleFitres2 = false;
		showglobalpan2 = false;
		showrec2 = false;
		setShow_listfild_rename(true);
		aggregation = false;
		groupeby = false;
		showflow = true;

		// for (int i = 0; i < list_etldield_proc2.size(); i++) {
		// old_list.add(deepCopy(list_etldield_proc2.get(i).getField()));
		// }
		//

		for (int i = 0; i < list_Groupe_champs.size(); i++) {

			new_etlfield_proc = new EtlFieldProc();

			new_etlfield_proc.setFunction(list_Groupe_champs.get(i).getChamp().getLibelle());
			new_etlfield_proc.setField(list_Groupe_champs.get(i).getChamp());
			new_etlfield_proc.setId_field_dest(list_Groupe_champs.get(i).getChamp().getId());
			new_etlfield_proc.setProc_flow(null);
			list_etldield_proc3.add(new_etlfield_proc);

		}
		for (int i = 0; i < list_agg_champs.size(); i++) {

			new_etlfield_proc = new EtlFieldProc();

			new_etlfield_proc.setFunction(list_agg_champs.get(i).getChamp().getLibelle());
			new_etlfield_proc.setField(list_agg_champs.get(i).getChamp());
			new_etlfield_proc.setId_field_dest(list_agg_champs.get(i).getChamp().getId());
			new_etlfield_proc.setProc_flow(null);
			list_etldield_proc2.add(new_etlfield_proc);

		}

		for (int i = 0; i < list_agg_champs.size(); i++) {

			old_list_agg.add(deepCopy(list_agg_champs.get(i).getChamp()));
		}
		for (int i = 0; i < list_Groupe_champs.size(); i++) {

			old_list_groupe.add(deepCopy(list_Groupe_champs.get(i).getChamp()));
		}

	}

	public void getlisteRenameField() {

		listeRenameField = list_select_champs1;
		showRenamefield = true;
		System.out.println("rename field " + showRenamefield);
	}

	public void onRuleGaucheDrop() {
		list_champs_rec_gauche = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setId(id_filtre1);
		cr.setChamp(deepCopy(champs_Type_Noeud_selected1));
		cr.setFonctions(new ArrayList<>());
		list_champs_rec_gauche.add(deepcopyListchaps_rec(cr));
		id_filtre1++;

		RuleRecGauche rrg = new RuleRecGauche();
		rrg.setId(id_filtre);
		rrg.setList_champs_rec1(new ArrayList<>());
		for (int i = 0; i < list_champs_rec_gauche.size(); i++) {
			rrg.getList_champs_rec1().add(deepcopyListchaps_rec(list_champs_rec_gauche.get(i)));
		}

		rrg.setParenteses_ouvrant(new ArrayList<>());
		rrg.setParenteses_fermant(new ArrayList<>());
		list_rules_gauche.add(rrg);
		id_filtre++;

		new_etlfield_proc = new EtlFieldProc();
		new_etlfield_proc.setField(champs_Type_Noeud_selected1);
		new_etlfield_proc.setId_field_dest(champs_Type_Noeud_selected1.getId());
		new_etlfield_proc.setFunction("");
		new_etlfield_proc.setProc_flow(null);
		new_etlfield_proc.setType_field(null);
		list_etldield_proc.add(new_etlfield_proc);

		new_etlfield_proc = new EtlFieldProc();
	}

	public ChampRec deepcopyListchaps_rec(ChampRec input) {

		ChampRec copy = new ChampRec();

		copy.setChamp(input.getChamp());
		copy.setFonctions(new ArrayList<String>());
		copy.setOperator(input.getOperator());
		copy.setId(input.getId());
		return copy;
	}

	public void onRuleDelete2(RuleRecGauche selected_rec_gauche) {
		list_rules_gauche2.remove(selected_rec_gauche);
	}

	public void onFieldDrop(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_champs1.add(car);
		list_champs1.remove(car);

		selected_champ_rec_to_add = new ChampRec();
		champs_Type_Noeud_selected = new EtlFieldFlow();
		setShowRuleFitres(true);
		setShowRuleFitres2(true);
		Ajax.update(":form1:");
		RequestContext.getCurrentInstance().update(":form1:");

	}

	public void otherFiltre() {
		System.out.println("other flow ");
		showglobalpan = false;
		setShowrec2(true);
		setShowglobalpan2(true);
		showRuleFitres = false;
		showglobalpan = false;

		System.out.println(" begin for ");

		for (int i = 0; i < list_flow2.size(); i++) {
			System.out.println("top if ");
			if (list_flow2.get(i).equals(selected_flow1)) {

				System.out.println(" if condition");

				list_flow2.remove(list_flow2.get(i));

			}

			System.out.println("end of boucle ");
		}
	}

	public void getAllfields(Flow f) {
		listfieldsOP = flow_service.getAllChamps(f);
		for (int i = 0; i < listfieldsOP.size(); i++) {
			System.out.println("======>>" + listfieldsOP.get(i).getLibelle());
		}
		System.out.println("selected flow field ");
	}

	private boolean is_date = false;

	public void onEdit(RowEditEvent event) {
		RuleRecGauche R = (RuleRecGauche) event.getObject();

		if (type_valeur.equals("String") && !(R.getValeur().isEmpty())) {
			String s = "";
			s = "'" + R.getValeur() + "'";
			R.setValeur(s);

		}

	}

	public void onEditgrupe(RowEditEvent event) {
		EtlFieldFlow R = (EtlFieldFlow) event.getObject();

		R.setIsdate(is_date);

	}

	public void onEditagg(RowEditEvent event) {
		EtlFieldFlow R = (EtlFieldFlow) event.getObject();

		R.setIsdate(is_date);

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Item Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		list_champs.remove((EtlFieldFlow) event.getObject());
	}

	public void addparentesegauche() {

		selected_rec_gauche.getParenteses_fermant().add(")");
		System.out.println("Par gauche parenteses )");
	}

	public void addparentesegauche_ouvrant() {
		System.out.println("Par gauche");
		selected_rec_gauche.getParenteses_ouvrant().add("(");
	}

	public void onRuleDelete(RuleRecGauche selected_rec_gauche) {

		System.out.println("delete function started");
		list_rules_gauche.remove(selected_rec_gauche);
		System.out.println("delete function ended ");

	}

	public void supprimerzgreegation(ChampRec selected_champ_agg) {

		list_agg_champs.remove(selected_champ_agg);
	}

	public void supprimergroupe(ChampRec selected_champ_groupe) {

		list_Groupe_champs.remove(selected_champ_groupe);
	}

	private boolean substr = false;
	private boolean substrG = false;

	public void changeTypeFunction() {
		if (selected_function1.equals("substr")) {
			substr = true;
		} else {
			substr = false;
		}
	}

	public void changeTypeFunctionG() {
		if (selecteted_functionaGroupe.equals("substr")) {
			substrG = true;
		} else {
			substrG = false;
		}
	}

	public void addFunctionGroupe() {
		String selecteted_functionagroupe = "";
		if (selecteted_functionaGroupe.equals("substr")) {
			selected_champ_groupe.setFonctions(new ArrayList<>());
			selected_champ_groupe.getFonctions().add(selecteted_functionaGroupe);
			selecteted_functionagroupe = selecteted_functionagroupe + selecteted_functionaGroupe + "("
					+ selected_champ_groupe.getChamp().getLibelle() + "," + start_sab + "," + finsh_sab + ")";
			System.out.println(selecteted_functionagroupe);
			selected_champ_groupe.getChamp().setLibelle(selecteted_functionagroupe);

		}
		if (selecteted_functionaGroupe.equals("TO_INTEGER")) {

		}
		if (selecteted_functionaGroupe.equals("TO_DATE")) {

		}

		new_etlfield_proc = new EtlFieldProc();
		new_etlfield_proc.setFunction(selecteted_functionagroupe);
		new_etlfield_proc.setField(selected_champ_groupe.getChamp());
		new_etlfield_proc.setId_field_dest(selected_champ_groupe.getChamp().getId());
		new_etlfield_proc.setProc_flow(null);

		int id_selection = 0;

		id_selection = list_Groupe_champs.indexOf(selected_champ_groupe);

		System.out.println(" id of selection field " + id_selection + "size of list " + list_etldield_proc3.size());

		// if (list_etldield_proc3.size() == id_selection) {
		// list_etldield_proc3.add(id_selection, new_etlfield_proc);
		//
		// }
		//
		// if (list_etldield_proc3.size() >= id_selection) {
		// list_etldield_proc3.remove(id_selection);
		// list_etldield_proc3.add(id_selection, new_etlfield_proc);
		// }

		new_etlfield_proc = new EtlFieldProc();

		RequestContext.getCurrentInstance().update(":form1:dropAreaFiltre2");

		finsh_sab = 0;
		start_sab = 0;
		selecteted_functionagroupe = "";
	}

	
	public void addFunctionAgg() {

		String selecteted_functionagg1 = "";
		selecteted_functionagg1 = selecteted_functionagg1 + selecteted_functionagg + "("
				+ selected_champ_agg.getChamp().getLibelle() + ")";

		System.out.println(selecteted_functionagg1);

		selected_champ_agg.getChamp().setLibelle(selecteted_functionagg1);

		new_etlfield_proc = new EtlFieldProc();
		new_etlfield_proc.setFunction(selecteted_functionagg1);
		new_etlfield_proc.setField(selected_champ_agg.getChamp());
		new_etlfield_proc.setId_field_dest(selected_champ_agg.getChamp().getId());
		new_etlfield_proc.setProc_flow(null);

		int id_selection = 0;

		id_selection = list_agg_champs.indexOf(selected_champ_agg);

		System.out.println(" id of selection field " + id_selection + "size of list " + list_etldield_proc2.size());

		// if (list_etldield_proc2.size() == id_selection) {
		// list_etldield_proc2.add(id_selection, new_etlfield_proc);
		// }
		// if (list_etldield_proc2.size() >= id_selection) {
		// list_etldield_proc2.remove(id_selection);
		// list_etldield_proc2.add(id_selection, new_etlfield_proc);
		// }

		new_etlfield_proc = new EtlFieldProc();

	}

	private int start_sab;
	private int finsh_sab;

	public void addFunction1() {
		// selected_rec_gauche.getList_champs_rec1().get(0).setFonctions(new
		// ArrayList<String>());
		selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().add(selected_function1);
		selected_function1 = "";
		String function_field = "";
		int c = 0;

		for (int i = 0; i < selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().size(); i++) {

			function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().get(i)
					+ "(";
			c = i;
		}

		function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getChamp().getLibelle() + ","
				+ start_sab + "," + finsh_sab + ")";

		for (int i = 0; i < c; i++) {
			function_field = function_field + ")";
		}

		selected_rec_gauche.getList_champs_rec1().get(0).getChamp().setLibelle(function_field);

		new_etlfield_proc = new EtlFieldProc();
		new_etlfield_proc.setFunction(function_field);
		new_etlfield_proc.setField(selected_rec_gauche.getList_champs_rec1().get(0).getChamp());
		new_etlfield_proc.setId_field_dest(selected_rec_gauche.getList_champs_rec1().get(0).getChamp().getId());
		new_etlfield_proc.setProc_flow(null);

		int id_selection = 0;

		id_selection = list_rules_gauche.indexOf(selected_rec_gauche);
		System.out.println(" id of selection field " + id_selection + "size of list " + list_etldield_proc.size());
		if (list_etldield_proc.size() == id_selection) {
			list_etldield_proc.add(id_selection, new_etlfield_proc);
		} else {
			list_etldield_proc.remove(id_selection);
			list_etldield_proc.add(id_selection, new_etlfield_proc);
		}
		
		System.out.println(list_etldield_proc.size());
		for (int i = 0; i < list_etldield_proc.size(); i++) {
			System.out.println("etl_field_proc list 2 " + list_etldield_proc.get(i).getFunction());
			System.out.println("etl_field_proc list 4 " + list_etldield_proc.get(i).getId_field_dest());
		}

		new_etlfield_proc = new EtlFieldProc();

		RequestContext.getCurrentInstance().update("form1:dropAreaRule1");
		function_field = "";
		selected_rec_gauche.getList_champs_rec1().get(0).setFonctions(new ArrayList<>());
	}

	private int id_agg = 1;
	private int id_group = 1;
	public static List<ChampRec> list_agg_champs = new ArrayList<>();
	public static List<ChampRec> list_Groupe_champs = new ArrayList<>();

	private ChampRec selected_champ_agg = new ChampRec();
	private ChampRec selected_champ_groupe = new ChampRec();
	private List<String> functions_agg;
	private List<String> functions_group;

	public void addAggregation() {
		ChampRec cr = new ChampRec();
		cr.setId(id_agg);
		cr.setChamp(deepCopy(champs_Type_Noeud_selected1));
		cr.setFonctions(new ArrayList<>());
		list_agg_champs.add(deepcopyListchaps_rec(cr));
		id_agg++;

	}

	public void addGroupBy() {
		ChampRec cr = new ChampRec();
		cr.setId(id_group);
		cr.setChamp(deepCopy(champs_Type_Noeud_selected1));
		cr.setFonctions(new ArrayList<>());
		list_Groupe_champs.add(deepcopyListchaps_rec(cr));
		id_group++;
	}

	// ********************* geters and seters
	// ******************************************/
	public int getId_group() {
		return id_group;
	}

	public void setId_group(int id_group) {
		this.id_group = id_group;
	}

	public List<String> getFunctions_agg() {
		return functions_agg;
	}

	public void setFunctions_agg(List<String> functions_agg) {
		this.functions_agg = functions_agg;
	}

	public ChampRec getSelected_champ_agg() {
		return selected_champ_agg;
	}

	public void setSelected_champ_agg(ChampRec selected_champ_agg) {
		this.selected_champ_agg = selected_champ_agg;
	}

	public List<ChampRec> getList_agg_champs() {
		return list_agg_champs;
	}

	public void setList_agg_champs(List<ChampRec> list_agg_champs) {
		this.list_agg_champs = list_agg_champs;
	}

	public int getId_agg() {
		return id_agg;
	}

	public void setId_agg(int id_agg) {
		this.id_agg = id_agg;
	}

	public EtlFieldFlow getChamps_Type_Noeud_selected() {
		return champs_Type_Noeud_selected;
	}

	public void setChamps_Type_Noeud_selected(EtlFieldFlow champs_Type_Noeud_selected) {
		this.champs_Type_Noeud_selected = champs_Type_Noeud_selected;
	}

	public ChampRec getSelected_champ_rec_to_add() {
		return selected_champ_rec_to_add;
	}

	public void setSelected_champ_rec_to_add(ChampRec selected_champ_rec_to_add) {
		this.selected_champ_rec_to_add = selected_champ_rec_to_add;
	}

	public TreeNode getStat_control() {
		return stat_control;
	}

	public void setStat_control(TreeNode stat_control) {
		this.stat_control = stat_control;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public boolean isShowglobalpan() {
		return showglobalpan;
	}

	public void setShowglobalpan(boolean showglobalpan) {
		this.showglobalpan = showglobalpan;
	}

	public boolean isShowstat() {
		return showstat;
	}

	public void setShowstat(boolean showrec2) {
		this.showstat = showrec2;
	}

	public Flow getSelected_flow1() {
		return selected_flow1;
	}

	public void setSelected_flow1(Flow selected_flow1) {
		this.selected_flow1 = selected_flow1;
	}

	public List<EtlFieldFlow> getList_champs1() {
		return list_champs1;
	}

	public void setList_champs1(List<EtlFieldFlow> list_champs1) {
		this.list_champs1 = list_champs1;
	}

	public List<EtlFieldFlow> getList_select_champs1() {
		return list_select_champs1;
	}

	public void setList_select_champs1(List<EtlFieldFlow> list_select_champs1) {
		this.list_select_champs1 = list_select_champs1;
	}

	public List<Flow> getList_flow() {
		return list_flow;
	}

	public void setList_flow(List<Flow> list_flow) {
		this.list_flow = list_flow;
	}

	public boolean isShowRuleFitres() {
		return showRuleFitres;
	}

	public void setShowRuleFitres(boolean showRuleFitres) {
		this.showRuleFitres = showRuleFitres;
	}

	public Boolean getShowRuleFitres2() {
		return showRuleFitres2;
	}

	public void setShowRuleFitres2(Boolean showRuleFitres2) {
		this.showRuleFitres2 = showRuleFitres2;
	}

	public EtlFieldFlow getChamps_Type_Noeud_selected1() {
		return champs_Type_Noeud_selected1;
	}

	public void setChamps_Type_Noeud_selected1(EtlFieldFlow champs_Type_Noeud_selected1) {
		this.champs_Type_Noeud_selected1 = champs_Type_Noeud_selected1;
	}

	public List<RuleRecGauche> getList_rules_gauche() {
		return list_rules_gauche;
	}

	public void setList_rules_gauche(List<RuleRecGauche> list_rules_gauche) {
		this.list_rules_gauche = list_rules_gauche;
	}

	public EtlFieldProc getNew_etlfield_proc() {
		return new_etlfield_proc;
	}

	public void setNew_etlfield_proc(EtlFieldProc new_etlfield_proc) {
		this.new_etlfield_proc = new_etlfield_proc;
	}

	public static List<EtlFieldProc> getList_etldield_proc() {
		return list_etldield_proc;
	}

	public static void setList_etldield_proc(List<EtlFieldProc> list_etldield_proc) {
		StatMbean.list_etldield_proc = list_etldield_proc;
	}

	public List<EtlFieldFlow> getList_champs() {
		return list_champs;
	}

	public void setList_champs(List<EtlFieldFlow> list_champs) {
		this.list_champs = list_champs;
	}

	public RuleRecGauche getSelected_rec_gauche() {
		return selected_rec_gauche;
	}

	public void setSelected_rec_gauche(RuleRecGauche selected_rec_gauche) {
		this.selected_rec_gauche = selected_rec_gauche;
	}

	public List<EtlFieldFlow> getListfieldsOP() {
		return listfieldsOP;
	}

	public void setListfieldsOP(List<EtlFieldFlow> listfieldsOP) {
		this.listfieldsOP = listfieldsOP;
	}

	public boolean isShowrec2() {
		return showrec2;
	}

	public void setShowrec2(boolean showrec2) {
		this.showrec2 = showrec2;
	}

	public boolean isShowglobalpan2() {
		return showglobalpan2;
	}

	public void setShowglobalpan2(boolean showglobalpan2) {
		this.showglobalpan2 = showglobalpan2;
	}

	public List<Flow> getList_flow2() {
		return list_flow2;
	}

	public void setList_flow2(List<Flow> list_flow2) {
		this.list_flow2 = list_flow2;
	}

	public Flow getSelected_flow2() {
		return selected_flow2;
	}

	public void setSelected_flow2(Flow selected_flow2) {
		this.selected_flow2 = selected_flow2;
	}

	public List<EtlFieldFlow> getList_champs_rec() {
		return list_champs_rec;
	}

	public void setList_champs_rec(List<EtlFieldFlow> list_champs_rec) {
		this.list_champs_rec = list_champs_rec;
	}

	public List<EtlFieldFlow> getList_select_rec() {
		return list_select_rec;
	}

	public void setList_select_rec(List<EtlFieldFlow> list_select_rec) {
		this.list_select_rec = list_select_rec;
	}

	public List<ChampRec> getList_champs_rec_gauche2() {
		return list_champs_rec_gauche2;
	}

	public void setList_champs_rec_gauche2(List<ChampRec> list_champs_rec_gauche2) {
		this.list_champs_rec_gauche2 = list_champs_rec_gauche2;
	}

	public EtlFieldFlow getChamps_Type_Noeud_selected2() {
		return champs_Type_Noeud_selected2;
	}

	public void setChamps_Type_Noeud_selected2(EtlFieldFlow champs_Type_Noeud_selected2) {
		this.champs_Type_Noeud_selected2 = champs_Type_Noeud_selected2;
	}

	public List<RuleRecGauche> getList_rules_gauche2() {
		return list_rules_gauche2;
	}

	public void setList_rules_gauche2(List<RuleRecGauche> list_rules_gauche2) {
		this.list_rules_gauche2 = list_rules_gauche2;
	}

	public boolean isShow_listfild_rename() {
		return show_listfild_rename;
	}

	public void setShow_listfild_rename(boolean show_listfild_rename) {
		this.show_listfild_rename = show_listfild_rename;
	}

	public List<EtlFieldFlow> getListeRenameField() {
		return listeRenameField;
	}

	public void setListeRenameField(List<EtlFieldFlow> listeRenameField) {
		StatMbean.listeRenameField = listeRenameField;
	}

	public boolean isShowRenamefield() {
		return showRenamefield;
	}

	public void setShowRenamefield(boolean showRenamefield) {
		this.showRenamefield = showRenamefield;
	}

	public EtlProc getNew_etl_procF() {
		return new_etl_procF;
	}

	public void setNew_etl_procF(EtlProc new_etl_procF) {
		this.new_etl_procF = new_etl_procF;
	}

	public EtlProcFlow getEpf() {
		return epf;
	}

	public void setEpf(EtlProcFlow epf) {
		this.epf = epf;
	}

	public Flow getFlowRec() {
		return flowRec;
	}

	public void setFlowRec(Flow flowRec) {
		this.flowRec = flowRec;
	}

	public EtlTypeFlow getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(EtlTypeFlow flow_type) {
		this.flow_type = flow_type;
	}

	public List<EtlFieldFlow> getOld_list_agg() {
		return old_list_agg;
	}

	public void setOld_list_agg(List<EtlFieldFlow> old_list) {
		this.old_list_agg = old_list;
	}

	public List<EtlProc> getList_proc_stat() {
		return list_proc_stat;
	}

	public void setList_proc_stat(List<EtlProc> list_proc_stat) {
		this.list_proc_stat = list_proc_stat;
	}

	public List<EtlProcFlow> getListetlprocflow() {
		return Listetlprocflow;
	}

	public void setListetlprocflow(List<EtlProcFlow> listetlprocflow) {
		Listetlprocflow = listetlprocflow;
	}

	public String getSelected_operator1() {
		return selected_operator1;
	}

	public void setSelected_operator1(String selected_operator1) {
		this.selected_operator1 = selected_operator1;
	}

	public String getSelecteted_functionagg() {
		return selecteted_functionagg;
	}

	public void setSelecteted_functionagg(String selecteted_functionagg) {
		this.selecteted_functionagg = selecteted_functionagg;
	}

	public String getSelected_function() {
		return selected_function;
	}

	public void setSelected_function(String selected_function) {
		this.selected_function = selected_function;
	}

	public String getSelected_function1() {
		return selected_function1;
	}

	public void setSelected_function1(String selected_function1) {
		this.selected_function1 = selected_function1;
	}

	public boolean isSubstr() {
		return substr;
	}

	public void setSubstr(boolean substr) {
		this.substr = substr;
	}

	public List<EtlFieldProc> getList_etldield_proc2() {
		return list_etldield_proc2;
	}

	public void setList_etldield_proc2(List<EtlFieldProc> list_etldield_proc2) {
		StatMbean.list_etldield_proc2 = list_etldield_proc2;
	}

	public List<ChampRec> getList_Groupe_champs() {
		return list_Groupe_champs;
	}

	public void setList_Groupe_champs(List<ChampRec> list_Groupe_champs) {
		StatMbean.list_Groupe_champs = list_Groupe_champs;
	}

	public String getSelecteted_functionaGroupe() {
		return selecteted_functionaGroupe;
	}

	public void setSelecteted_functionaGroupe(String selecteted_functionaGroupe) {
		this.selecteted_functionaGroupe = selecteted_functionaGroupe;
	}

	public List<String> getFunctions_group() {
		return functions_group;
	}

	public void setFunctions_group(List<String> functions_group) {
		this.functions_group = functions_group;
	}

	public ChampRec getSelected_champ_groupe() {
		return selected_champ_groupe;
	}

	public void setSelected_champ_groupe(ChampRec selected_champ_groupe) {
		this.selected_champ_groupe = selected_champ_groupe;
	}

	public List<EtlFieldProc> getList_etldield_proc3() {
		return list_etldield_proc3;
	}

	public void setList_etldield_proc3(List<EtlFieldProc> list_etldield_proc3) {
		this.list_etldield_proc3 = list_etldield_proc3;
	}

	public boolean isAggregation() {
		return aggregation;
	}

	public void setAggregation(boolean aggregation) {
		this.aggregation = aggregation;
	}

	public boolean isGroupeby() {
		return groupeby;
	}

	public void setGroupeby(boolean groupeby) {
		this.groupeby = groupeby;
	}

	public int getStart_sab() {
		return start_sab;
	}

	public void setStart_sab(int start_sab) {
		this.start_sab = start_sab;
	}

	public int getFinsh_sab() {
		return finsh_sab;
	}

	public void setFinsh_sab(int finsh_sab) {
		this.finsh_sab = finsh_sab;
	}

	public boolean isSubstrG() {
		return substrG;
	}

	public void setSubstrG(boolean substrG) {
		this.substrG = substrG;
	}

	public boolean isShowflow() {
		return showflow;
	}

	public void setShowflow(boolean showflow) {
		this.showflow = showflow;
	}

	public List<EtlDataType> getList_data_type() {
		return list_data_type;
	}

	public void setList_data_type(List<EtlDataType> list_data_type) {
		this.list_data_type = list_data_type;
	}

	public String getType_valeur() {
		return type_valeur;
	}

	public void setType_valeur(String type_valeur) {
		this.type_valeur = type_valeur;
	}

	public boolean isShowstring() {
		return showstring;
	}

	public void setShowstring(boolean showstring) {
		this.showstring = showstring;
	}

	public boolean isShowvaleur() {
		return showvaleur;
	}

	public void setShowvaleur(boolean showvaleur) {
		this.showvaleur = showvaleur;
	}

	public boolean isShowfield() {
		return showfield;
	}

	public void setShowfield(boolean showfield) {
		this.showfield = showfield;
	}

	public boolean isShowlisttype() {
		return showlisttype;
	}

	public void setShowlisttype(boolean showlisttype) {
		this.showlisttype = showlisttype;
	}

	public List<EtlFieldFlow> getListfield() {
		return listfield;
	}

	public void setListfield(List<EtlFieldFlow> listfield) {
		this.listfield = listfield;
	}

	public List<EtlFieldFlow> getOld_list_groupe() {
		return old_list_groupe;
	}

	public void setOld_list_groupe(List<EtlFieldFlow> old_list_groupe) {
		this.old_list_groupe = old_list_groupe;
	}

	public boolean isIs_date() {
		return is_date;
	}

	public void setIs_date(boolean is_date) {
		this.is_date = is_date;
	}
}
