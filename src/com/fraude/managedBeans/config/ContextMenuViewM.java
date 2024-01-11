package com.fraude.managedBeans.config;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.BigIntegerConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.omnifaces.component.output.OutputLabel;
import org.omnifaces.util.Ajax;
import org.primefaces.component.button.Button;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.tree.Tree;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.w3c.dom.ls.LSInput;

import com.fraude.entities.ChampRec;
import com.fraude.entities.Config_ASCII;
import com.fraude.entities.Config_ASN1;
import com.fraude.entities.Convertisseur;
import com.fraude.entities.EtlBinType;
import com.fraude.entities.EtlDataType;
import com.fraude.entities.EtlFieldFlow;
import com.fraude.entities.EtlFieldProc;
import com.fraude.entities.EtlFieldService;
import com.fraude.entities.EtlProc;
import com.fraude.entities.EtlProcFlow;
import com.fraude.entities.EtlServiceConverter;
import com.fraude.entities.EtlTypeFlow;
import com.fraude.entities.FiltreRec;
import com.fraude.entities.Flow;
import com.fraude.entities.Noeud;
import com.fraude.entities.Repertoire;
import com.fraude.entities.RuleRecDroite;
import com.fraude.entities.RuleRecGauche;
import com.fraude.entities.RuleRecJoin;
import com.fraude.entities.Service;
import com.fraude.interfaces.ConverterInterface;
import com.fraude.interfaces.FlowInterface;
import com.fraude.interfaces.FunctionsInterfaces;
import com.fraude.interfaces.NoeudInterface;
import com.fraude.interfaces.RepositoryInterface;
import com.fraude.interfaces.ServiceInterface;




@ManagedBean(name = "treeContextMenuViewM")
@ViewScoped
public class ContextMenuViewM implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4365956269405845183L;

	private EtlProc new_etl_proc;

	public static List<EtlFieldProc> list_etldield_proc = new ArrayList<>();
	public static List<EtlFieldProc> list_etldield_proc2 = new ArrayList<>();

	public EtlProc getNew_etl_proc() {
		return new_etl_proc;
	}

	public void setNew_etl_proc(EtlProc new_etl_proc) {
		this.new_etl_proc = new_etl_proc;
	}

	private TreeNode root;

	private TreeNode selectedNode;

	@EJB
	private FlowInterface flow_service;

	@EJB
	private ConverterInterface converter_service;

	@EJB
	private NoeudInterface noeud_service;

	private List<RuleRecJoin> list_Rule_Join = new ArrayList<>();

	private List<FiltreRec> liste_filtres;

	private RuleRecJoin selected_Rule_Join;

	private RuleRecJoin selected_Rule_Join2;

	private EtlProcFlow epf;
	private EtlProcFlow epf2;

	public EtlProcFlow getEpf2() {
		return epf2;
	}

	public void setEpf2(EtlProcFlow epf2) {
		this.epf2 = epf2;
	}

	public EtlProcFlow getEpf() {
		return epf;
	}

	public void setEpf(EtlProcFlow epf) {
		this.epf = epf;
	}

	private List<FiltreRec> liste_filtres1;

	public List<FiltreRec> getListe_filtres() {
		return liste_filtres;
	}

	public void setListe_filtres(List<FiltreRec> liste_filtres) {
		this.liste_filtres = liste_filtres;
	}

	public List<FiltreRec> getListe_filtres1() {
		return liste_filtres1;
	}

	public void setListe_filtres1(List<FiltreRec> liste_filtres1) {
		this.liste_filtres1 = liste_filtres1;
	}

	private List<EtlFieldService> liste_champs_service;

	public List<EtlFieldService> getListe_champs_service() {
		return liste_champs_service;
	}

	public void setListe_champs_service(List<EtlFieldService> liste_champs_service) {
		this.liste_champs_service = liste_champs_service;
	}

	EtlProc new_etl_procF = new EtlProc();

	public EtlProc getNew_etl_procF() {
		return new_etl_procF;
	}

	public void setNew_etl_procF(EtlProc new_etl_procF) {
		this.new_etl_procF = new_etl_procF;
	}

	@EJB
	private ServiceInterface services_service;
	@EJB
	private RepositoryInterface rep_service;

	private List<Flow> list_flow = new ArrayList<>();
	private List<Flow> list_flow2 = new ArrayList<>();
	private List<EtlFieldFlow> list_champs = new ArrayList<>();

	private TreeNode flows;
	private TreeNode rating_controle;
	private TreeNode Jobs;
	private TreeNode Converters;
	private TreeNode champs;
	private TreeNode nodes;

	private RuleRecJoin joinRec = new RuleRecJoin();

	private RuleRecJoin joinRec2 = new RuleRecJoin();

	public TreeNode getRating_controle() {
		return rating_controle;
	}

	public void setRating_controle(TreeNode rating_controle) {
		this.rating_controle = rating_controle;
	}

	public TreeNode getChamps() {
		return champs;
	}

	public void setChamps(TreeNode champs) {
		this.champs = champs;
	}

	public TreeNode getNodes() {
		return nodes;
	}

	public void setNodes(TreeNode nodes) {
		this.nodes = nodes;
	}

	public List<EtlFieldFlow> getList_champs() {
		return list_champs;
	}

	public void setList_champs(List<EtlFieldFlow> list_champs) {
		this.list_champs = list_champs;
	}

	public TreeNode getConverters() {
		return Converters;
	}

	public void setConverters(TreeNode converters) {
		Converters = converters;
	}

	public TreeNode getFlows() {
		return flows;
	}

	public void setFlows(TreeNode flows) {
		this.flows = flows;
	}

	private List<String> type_query = new ArrayList<>();
	private List<String> type_operator = new ArrayList<>();
	private List<String> type_fields = new ArrayList<>();
	private List<String> type_sq_op = new ArrayList<>();

	private boolean showWhere = false;

	public boolean isShowWhere() {
		return showWhere;
	}

	public void setShowWhere(boolean showWhere) {
		this.showWhere = showWhere;
	}

	public List<String> getType_query() {
		return type_query;
	}

	public void setType_operator(List<String> type_operator) {
		this.type_operator = type_operator;
	}

	public void setType_fields(List<String> type_fields) {
		this.type_fields = type_fields;
	}

	public void setType_query(List<String> type_query) {
		this.type_query = type_query;
	}

	public List<String> getType_fields() {
		return type_fields;
	}

	public void setType_sq_op(List<String> type_sq_op) {
		this.type_sq_op = type_sq_op;
	}

	public List<String> getType_sq_op() {
		return type_sq_op;
	}

	public List<String> getType_operator() {
		return type_operator;
	}

	private boolean showaddfiltres = false;

	public boolean isShowaddfiltres() {
		return showaddfiltres;
	}

	public void setShowaddfiltres(boolean showaddfiltres) {
		this.showaddfiltres = showaddfiltres;
	}

	private boolean showaddfiltres2 = false;

	public boolean isShowaddfiltres2() {
		return showaddfiltres2;
	}

	public void setShowaddfiltres2(boolean showaddfiltres2) {
		this.showaddfiltres2 = showaddfiltres2;
	}

	public void onSelect(SelectEvent event) {

		showaddfiltres = true;

	}

	public void onSelect1(SelectEvent event) {
		showaddfiltres2 = true;

	}

	private List<EtlDataType> list_data_type;

	public List<EtlDataType> getList_data_type() {
		return list_data_type;
	}

	public void setList_data_type(List<EtlDataType> list_data_type) {
		this.list_data_type = list_data_type;
	}

	private List<EtlBinType> list_bin_type;

	private List<EtlTypeFlow> list_type_flow;

	public List<EtlTypeFlow> getList_type_flow() {
		return list_type_flow;
	}

	public void setList_type_flow(List<EtlTypeFlow> list_type_flow) {
		this.list_type_flow = list_type_flow;
	}

	public List<EtlBinType> getList_bin_type() {
		return list_bin_type;
	}

	public void setList_bin_type(List<EtlBinType> list_bin_type) {
		this.list_bin_type = list_bin_type;
	}

	private String start_substr;

	private String finish_substr;

	public String getStart_substr() {
		return start_substr;
	}

	public void setStart_substr(String start_substr) {
		this.start_substr = start_substr;
	}

	public String getFinish_substr() {
		return finish_substr;
	}

	public void setFinish_substr(String finish_substr) {
		this.finish_substr = finish_substr;
	}

	public void changeTypeFunction() {
		if (this.getSelected_function().equals("substring")) {
			substr = true;
		} else {
			substr = false;
		}
	}

	private List<RuleRecJoin> list_Rule_Join2 = new ArrayList<>();

	private List<EtlProc> list_proc = new ArrayList<>();

	private List<EtlProcFlow> Listetlprocflow = new ArrayList<>();

	@PostConstruct
	public void init() {
		flow_type = flow_service.getTypeflow();
		epf = new EtlProcFlow();
		epf2 = new EtlProcFlow();
		show_listfild_rename = false;
		showrec = true;
		showglobalpan = true;
		list_etldield_proc = new ArrayList<>();
		list_Rule_Join = new ArrayList<>();
		list_Rule_Join2 = new ArrayList<>();
		new_etl_proc = new EtlProc();
		list_rules_gauche = new ArrayList<>();
		list_rules_gauche2 = new ArrayList<>();

		functions = new ArrayList<>();
		list_type_flow = flow_service.getAllTypeFlow();
		list_bin_type = flow_service.getAllBinType();

		list_data_type = flow_service.getAllDataType();
		functions.add("substring");
		functions.add("to_date");
		functions.add("to_integer");
		functions_agg = new ArrayList<>();
		functions_agg.add("SUM");
		functions_agg.add("AVG");
		functions_agg.add("COUNT");
		liste_services = services_service.getAllServices();
		list_flow = flow_service.getAllFlow();
		list_flow2 = flow_service.getAllFlow();
		list_rules_droite = new ArrayList<>();
		selected_champ_rec_to_add = new ChampRec();

		list_proc = flow_service.getAllproc();

		// root = new DefaultTreeNode(new Doc(null, "ETL"), null);

		// root = new DefaultTreeNode(new Doc(null, "Flows"), null);

		flows = new DefaultTreeNode(new Doc(null, "Flows"), null);

		// Jobs = new DefaultTreeNode(new Doc(null, "Jobs"), root);

		rating_controle = new DefaultTreeNode(new Doc(null, "Traffic Controle"), null);

		// TreeNode plateform_intgrity = new DefaultTreeNode(new Doc(null,
		// "Platform Integrity"), root);
		// TreeNode recon_hlr_ocs = new DefaultTreeNode(new Doc(null, "Recon
		// HLR_IN"), plateform_intgrity);
		// TreeNode recon_hlr = new DefaultTreeNode(new Doc(null, "Recon
		// HLR_Billing"), plateform_intgrity);

		for (EtlProc e : list_proc) {

			TreeNode msc = new DefaultTreeNode(new Doc(e, e.getName()), rating_controle);
			TreeNode Flow = new DefaultTreeNode(new Doc(null, "Flows"), msc);

			TreeNode QueryJoin = new DefaultTreeNode(new Doc(null, "Query Join"), msc);
			TreeNode QJ = new DefaultTreeNode(new Doc(e, e.getQuery_join()), QueryJoin);
			Listetlprocflow = flow_service.getAllprocflowbyidprocs(e.getId());
			List<BigInteger> list_id_procsFlow = new ArrayList<>();
			list_id_procsFlow = flow_service.getprocflowbyidprocs(e.getId());

			Flow f = flow_service.getFlowbyid(list_id_procsFlow.get(0));
			Flow f2 = flow_service.getFlowbyid(list_id_procsFlow.get(1));

			TreeNode table;
			TreeNode table2;

			table = new DefaultTreeNode(new Doc(f, f.getFlowName()), Flow);
			table2 = new DefaultTreeNode(new Doc(f2, f2.getFlowName()), Flow);

			EtlProcFlow filtre = new EtlProcFlow();
			EtlProcFlow filtre2 = new EtlProcFlow();

			filtre = flow_service.get_query_filtre(e.getId(), f.getId());
			filtre2 = flow_service.get_query_filtre(e.getId(), f2.getId());

			TreeNode myf = new DefaultTreeNode(new Doc(null, "Filtre"), table);
			TreeNode myf2 = new DefaultTreeNode(new Doc(null, "Filtre"), table2);

			TreeNode filtre_table1 = new DefaultTreeNode(new Doc(filtre, filtre.getQuery_filters()), myf);
			TreeNode filtre_table2 = new DefaultTreeNode(new Doc(filtre2, filtre2.getQuery_filters()), myf2);

			TreeNode fields = new DefaultTreeNode(new Doc(null, "Fields"), table);
			TreeNode fields2 = new DefaultTreeNode(new Doc(null, "Fields"), table2);

			List<EtlFieldProc> list_field = flow_service.getallfieldprocbyprocflow(filtre.getId());

			for (EtlFieldProc fp : list_field) {
				TreeNode id_of_field = new DefaultTreeNode(new Doc(fp, "field_origine :" + fp.getField().getId()),
						fields);
				TreeNode id_of_field_dest = new DefaultTreeNode(new Doc(fp, "field_dest :" + fp.getId_field_dest()),
						id_of_field);

			}
			List<EtlFieldProc> list_field2 = flow_service.getallfieldprocbyprocflow(filtre2.getId());
			for (EtlFieldProc fp : list_field2) {
				TreeNode id_of_field2 = new DefaultTreeNode(new Doc(fp, "field_origine :" + fp.getField().getId()),
						fields2);
				TreeNode id_of_field_dest = new DefaultTreeNode(new Doc(fp, "field_dest :" + fp.getId_field_dest()),
						id_of_field2);

			}

		}

	}

	private Flow flow;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	@EJB
	private FunctionsInterfaces functions_service;

	private List<String> functions;
	private List<String> functions_agg;

	public List<String> getFunctions_agg() {
		return functions_agg;
	}

	public void setFunctions_agg(List<String> functions_agg) {
		this.functions_agg = functions_agg;
	}

	public List<String> getFunctions() {
		return functions;
	}

	public void setFunctions(List<String> functions) {
		this.functions = functions;
	}

	private FiltreRec selected_filtre_droite;

	private FiltreRec selected_filtre_gauche;

	public FiltreRec getSelected_filtre_droite() {
		return selected_filtre_droite;
	}

	public void setSelected_filtre_droite(FiltreRec selected_filtre_droite) {
		this.selected_filtre_droite = selected_filtre_droite;
	}

	public FiltreRec getSelected_filtre_gauche() {
		return selected_filtre_gauche;
	}

	public void setSelected_filtre_gauche(FiltreRec selected_filtre_gauche) {
		this.selected_filtre_gauche = selected_filtre_gauche;
	}

	private RuleRecDroite selected_rec_droite;

	public RuleRecDroite getSelected_rec_droite() {
		return selected_rec_droite;
	}

	public void setSelected_rec_droite(RuleRecDroite selected_rec_droite) {
		this.selected_rec_droite = selected_rec_droite;
	}

	public void setRep_service(RepositoryInterface rep_service) {
		this.rep_service = rep_service;
	}

	private String query;

	private boolean showaddVariable = false;

	private Flow selected_flow1;

	private Flow selected_flow2;

	public Flow getSelected_flow1() {
		return selected_flow1;
	}

	public void setSelected_flow1(Flow selected_flow1) {
		this.selected_flow1 = selected_flow1;
	}

	public Flow getSelected_flow2() {
		return selected_flow2;
	}

	public void setSelected_flow2(Flow selected_flow2) {
		this.selected_flow2 = selected_flow2;
	}

	private List<EtlFieldFlow> list_champs_tostat;

	public List<EtlFieldFlow> getList_champs_tostat() {
		return list_champs_tostat;
	}

	public void setList_champs_tostat(List<EtlFieldFlow> list_champs_tostat) {
		this.list_champs_tostat = list_champs_tostat;
	}

	private Flow selected_flow_stat;

	public Flow getSelected_flow_stat() {
		return selected_flow_stat;
	}

	public void setSelected_flow_stat(Flow selected_flow_stat) {
		this.selected_flow_stat = selected_flow_stat;
	}

	private List<EtlFieldFlow> list_champs3;
	private List<EtlFieldFlow> list_champs1;
	private List<EtlFieldFlow> list_champs2;
	private List<EtlFieldFlow> list_champs4;

	private List<EtlFieldFlow> list_champs_rec;

	private List<EtlFieldFlow> list_select_rec;

	private List<EtlFieldFlow> list_field_select;

	private List<EtlFieldFlow> list_select_champs1;
	private List<EtlFieldFlow> list_select_champs2;

	private List<EtlFieldFlow> list_select_champs3;

	public List<EtlFieldFlow> getList_champs3() {
		return list_champs3;
	}

	public void setList_champs3(List<EtlFieldFlow> list_champs3) {
		this.list_champs3 = list_champs3;
	}

	public List<EtlFieldFlow> getList_select_champs3() {
		return list_select_champs3;
	}

	public void setList_select_champs3(List<EtlFieldFlow> list_select_champs3) {
		this.list_select_champs3 = list_select_champs3;
	}

	public List<EtlFieldFlow> getList_select_champs1() {
		return list_select_champs1;
	}

	public void setList_select_champs1(List<EtlFieldFlow> list_select_champs1) {
		this.list_select_champs1 = list_select_champs1;
	}

	public List<EtlFieldFlow> getList_select_champs2() {
		return list_select_champs2;
	}

	public void setList_select_champs2(List<EtlFieldFlow> list_select_champs2) {
		this.list_select_champs2 = list_select_champs2;
	}

	public List<EtlFieldFlow> getList_champs1() {
		return list_champs1;
	}

	public void setList_champs2(List<EtlFieldFlow> list_champs2) {
		this.list_champs2 = list_champs2;
	}

	public void setList_champs1(List<EtlFieldFlow> list_champs1) {
		this.list_champs1 = list_champs1;
	}

	public List<EtlFieldFlow> getList_champs2() {
		return list_champs2;
	}

	private EtlFieldFlow selected_champs1;

	public EtlFieldFlow getSelected_champs1() {
		return selected_champs1;
	}

	public void setSelected_champs1(EtlFieldFlow selected_champs1) {
		this.selected_champs1 = selected_champs1;
	}

	List<EtlFieldFlow> listfieldsOP = new ArrayList<>();

	public List<EtlFieldFlow> getListfieldsOP() {
		return listfieldsOP;
	}

	public void setListfieldsOP(List<EtlFieldFlow> listfieldsOP) {
		this.listfieldsOP = listfieldsOP;
	}

	public void getAllfields(Flow f) {
		listfieldsOP = flow_service.getAllChamps(f);
		for (int i = 0; i < listfieldsOP.size(); i++) {
			System.out.println("======>>" + listfieldsOP.get(i).getLibelle());
		}
		System.out.println("selected flow field ");
	}

	public static List<EtlFieldFlow> listeRenameField = new ArrayList<>();

	public List<EtlFieldFlow> listeRenameField2;

	private boolean showRenamefield = false;

	public void getlisteRenameField() {

		for (int i = 0; i < list_select_champs1.size(); i++) {
			listeRenameField.add(deepCopy(list_select_champs1.get(i)));
		}
		for (int i = 0; i < list_select_rec.size(); i++) {
			listeRenameField.add(deepCopy(list_select_rec.get(i)));
		}

		showRenamefield = true;
		System.out.println("rename field " + showRenamefield);
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

	private Flow flowRec;

	public Flow getFlowRec() {
		return flowRec;
	}

	public void setFlowRec(Flow flowRec) {
		this.flowRec = flowRec;
	}

	List<EtlFieldFlow> old_list = new ArrayList<>();

	public List<EtlFieldFlow> getOld_list() {
		return old_list;
	}

	public void setOld_list(List<EtlFieldFlow> old_list) {
		this.old_list = old_list;
	}

	private EtlFieldProc efp = new EtlFieldProc();
	EtlTypeFlow flow_type = new EtlTypeFlow();

	public void insertnewflowREC() {
		System.out.println("begin inserting of etl_proc_Flow");

		new_etl_procF = new EtlProc();
		new_etl_procF.setProc_type("REC");
		new_etl_procF.setName("REC_" + selected_flow1.getFlowName() + "_" + selected_flow2.getFlowName());
		System.out.println("epf1 id " + epf.getId());
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
				for (String fn : rc.getFonctions()) {
					filtre = filtre + fn + "(";
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

			for (String s : rrg.getParenteses_fermant()) {
				filtre = filtre + s;
			}

			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				if (rrg.getLogic() != null) {
					filtre = filtre + rrg.getLogic();
				}
			}

		}

		epf.setQuery_filters(filtre);
		System.out.println(selected_flow1.getId());
		flow_service.addEtlProcFlow(epf);

		System.out.println("epf2 id " + epf2.getId());
		epf2.setFlow(selected_flow2);
		epf2.setProc(new_etl_procF);
		String filtre2 = "";
		for (RuleRecGauche rrg : list_rules_gauche2) {
			for (String s : rrg.getParenteses_ouvrant()) {
				filtre2 = filtre2 + s;
			}
			for (ChampRec rc : rrg.getList_champs_rec1()) {
				if (rc.getOperator() != null) {
					filtre2 = filtre2 + rc.getOperator();

				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre2 = filtre2 + fn + "(";
					}
				}

				if (rc.getChamp().getLibelle() != null) {
					filtre2 = filtre2 + rc.getChamp().getLibelle();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre2 = filtre2 + ")";
					}
				}
			}

			if ((rrg.getValeur() != null) && (rrg.getOperator() != null)) {
				filtre2 = filtre2 + rrg.getOperator() + rrg.getValeur();
			}
			if (rrg.getParenteses_fermant() != null) {
				for (String s : rrg.getParenteses_fermant()) {
					filtre2 = filtre2 + s;
				}
			}
			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				filtre2 = filtre2 + rrg.getLogic();
			}

		}

		System.out.println("filtre 2" + filtre2);
		epf2.setQuery_filters(filtre2);
		flow_service.updateEtlProcFlow(epf2);

		String queryjoin = "";

		for (int i = 0; i < list_Rule_Join.size(); i++) {

			if (list_Rule_Join.get(i).getParenteses_ouvrant() != null) {

				for (String s : list_Rule_Join.get(i).getParenteses_ouvrant()) {
					queryjoin = queryjoin + s;
				}
			}

			if (list_Rule_Join.get(i).getField() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getField().toString();
			}
			if (list_Rule_Join.get(i).getOperator() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getOperator().toString();
			}
			if (list_Rule_Join.get(i).getValeur() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getValeur();
			}
			if (list_Rule_Join.get(i).getParenteses_fermant() != null) {
				for (String s : list_Rule_Join.get(i).getParenteses_fermant()) {
					queryjoin = queryjoin + s;
				}
			}
			if (list_Rule_Join.get(i).getLogic() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getLogic();
			}
		}

		new_etl_procF.setQuery_join(queryjoin);
		flow_service.updateProc(new_etl_procF);

		flowRec = new Flow();
		flowRec.setFlowName("REC" + selected_flow1.getFlowName() + selected_flow2.getFlowName());
		flowRec.setTable_name("REC" + selected_flow1.getFlowName()  + selected_flow2.getFlowName());
		flowRec.setFlow_type(null);
		flowRec.setProc(new_etl_procF);
		flowRec.setFlow_type(flow_type);	
		flow_service.addFlow(flowRec);				
		flowRec.setProc(new_etl_procF);	
		flow_service.updateFlow(flowRec);
		
		old_list = listeRenameField;

		System.out.println("id of epf--- " + epf.getId() + flowRec.getId());

		List<EtlProcFlow> listepf = new ArrayList<>();
		listepf = flow_service.getEtl_procflow();
		flow_service.insertflowstat(listeRenameField, flowRec, epf);
		flow_service.insertlistfieldRec(listeRenameField, flowRec, epf);
		flow_service.insertlistfieldRec2(listeRenameField, flowRec, listepf.get(listepf.size() - 1));
	}

	public void loadChamps() {

		list_champs1 = new ArrayList<>();
		list_champs1 = selected_flow1.getListe_champs();
		list_select_champs1 = new ArrayList<>();

		System.out.println(list_champs1.size());

		for (int i = 0; i < list_champs1.size(); i++) {

			list_champs1.get(i).getLibelle();

		}

	}

	public void loadChamps1() {

		list_champs1 = new ArrayList<>();
		list_champs1 = selected_flow1.getListe_champs();
		list_select_champs1 = new ArrayList<>();

	}

	public void loadChamps12() {
		
		list_champs_rec = new ArrayList<>();
		list_champs_rec = flow_service.getflowbyid(selected_flow2.getId());
		list_select_rec = new ArrayList<>();

	}

	public void QueryJoin() {

		joinRec.setParenteses_ouvrant(new ArrayList<String>());
		joinRec.setFlow(selected_flow1);
		joinRec.setList_champs_rec1(selected_flow1.getListe_champs());
		joinRec.setParenteses_fermant(new ArrayList<String>());

	}

	private EtlFieldFlow add;

	private List<EtlFieldFlow> list_filtre = new ArrayList<>();

	public void addfiltreList() {
		if (list_filtre == null) {
			list_filtre = new ArrayList<>();
		}
		list_filtre.add(add);

	}

	public List<EtlFieldFlow> getListeRenameField2() {
		return listeRenameField2;
	}

	public void setListeRenameField2(List<EtlFieldFlow> listeRenameField2) {
		this.listeRenameField2 = listeRenameField2;
	}

	public List<EtlFieldFlow> getListeRenameField() {
		return listeRenameField;
	}

	public void setListeRenameField(List<EtlFieldFlow> listeRenameField) {
		this.listeRenameField = listeRenameField;
	}

	// public void addPanel(ActionEvent event) {
	// UIComponent component =
	// FacesContext.getCurrentInstance().getViewRoot().findComponent("form1:panelgridJoin");
	//
	// if (component != null) {
	// Panel p = new Panel();
	// p.setClosable(true);
	// p.setHeader("add Function");
	// p.setVisible(true);
	// for (int i = 0; i < list_field_select.size(); i++) {
	// OutputLabel L = new OutputLabel();
	// SelectOneMenu SO = new SelectOneMenu();
	//
	// L.setValue(list_field_select.get(i).getLibelle());
	// p.getChildren().add(L);
	// p.getChildren().add(SO);
	// PanelGrid pn = new PanelGrid();
	// pn.setColumns(2);
	// pn.getChildren().add(L);
	// pn.getChildren().add(SO);
	// p.getChildren().add(pn);
	// }
	// component.getChildren().add(p);
	// }
	// }

	private boolean showrec = true;

	private boolean showstat = false;

	public boolean isShowstat() {
		return showstat;
	}

	public void setShowstat(boolean showstat) {
		this.showstat = showstat;
	}

	public boolean isShowrec() {
		return showrec;
	}

	public void setShowrec(boolean showrec) {
		this.showrec = showrec;
	}

	public void addRec() {
		showrec = true;
	}

	public void loadChamps2() {
		list_champs2 = new ArrayList<>();
		list_champs2 = selected_flow2.getListe_champs();
		list_select_champs2 = new ArrayList<>();
	}

	private List<String> allFunctions = new ArrayList<>();

	public List<String> getAllFunctions() {
		return allFunctions;
	}

	public void setAllFunctions(List<String> allFunctions) {
		this.allFunctions = allFunctions;
	}

	private List<RuleRecGauche> list_rules_gauche;

	private List<RuleRecGauche> list_rules_gauche2;

	private List<RuleRecDroite> list_rules_droite;

	public List<RuleRecDroite> getList_rules_droite() {
		return list_rules_droite;
	}

	public void setList_rules_droite(List<RuleRecDroite> list_rules_droite) {
		this.list_rules_droite = list_rules_droite;
	}

	private List<ChampRec> list_agg_champs = new ArrayList<>();

	public List<ChampRec> getList_agg_champs() {
		return list_agg_champs;
	}

	public void setList_agg_champs(List<ChampRec> list_agg_champs) {
		this.list_agg_champs = list_agg_champs;
	}

	private int id_filtre = 1;

	public int getId_filtre() {
		return id_filtre;
	}

	public void setId_filtre(int id_filtre) {
		this.id_filtre = id_filtre;
	}

	public List<RuleRecGauche> getList_rules_gauche() {
		return list_rules_gauche;
	}

	public void setList_rules_gauche(List<RuleRecGauche> list_rules_gauche) {
		this.list_rules_gauche = list_rules_gauche;
	}

	private List<ChampRec> list_champs_rec_gauche = new ArrayList<>();
	private List<ChampRec> list_champs_rec_droite = new ArrayList<>();

	private List<ChampRec> list_champs_filtre_rec_gauche = new ArrayList<>();
	private List<ChampRec> list_champs_filtre_rec_droite = new ArrayList<>();
	

	private List<ChampRec> list_champs_rec_gauche2 = new ArrayList<>();

	public List<ChampRec> getList_champs_filtre_rec_droite() {
		return list_champs_filtre_rec_droite;
	}

	public void setList_champs_filtre_rec_droite(List<ChampRec> list_champs_filtre_rec_droite) {
		this.list_champs_filtre_rec_droite = list_champs_filtre_rec_droite;
	}

	public List<ChampRec> getList_champs_filtre_rec_gauche() {
		return list_champs_filtre_rec_gauche;
	}

	public void setList_champs_filtre_rec_gauche(List<ChampRec> list_champs_filtre_rec_gauche) {
		this.list_champs_filtre_rec_gauche = list_champs_filtre_rec_gauche;
	}

	public List<ChampRec> getList_champs_rec_droite() {
		return list_champs_rec_droite;
	}

	public void setList_champs_rec_droite(List<ChampRec> list_champs_rec_droite) {
		this.list_champs_rec_droite = list_champs_rec_droite;
	}

	public List<ChampRec> getList_champs_rec_gauche() {
		return list_champs_rec_gauche;
	}

	public void setList_champs_rec_gauche(List<ChampRec> list_champs_rec_gauche) {
		this.list_champs_rec_gauche = list_champs_rec_gauche;
	}

	private EtlFieldFlow champs_Type_Noeud_selected1;
	private EtlFieldFlow champs_Type_Noeud_selected2;

	public EtlFieldFlow getChamps_Type_Noeud_selected1() {
		return champs_Type_Noeud_selected1;
	}

	public void setChamps_Type_Noeud_selected1(EtlFieldFlow champs_Type_Noeud_selected1) {
		this.champs_Type_Noeud_selected1 = champs_Type_Noeud_selected1;
	}

	private EtlFieldFlow champs_Type_Noeud_selected;

	public EtlFieldFlow getChamps_Type_Noeud_selected() {
		return champs_Type_Noeud_selected;
	}

	public void setChamps_Type_Noeud_selected(EtlFieldFlow champs_Type_Noeud_selected) {
		this.champs_Type_Noeud_selected = champs_Type_Noeud_selected;
	}

	public void onFiltreDroiteDrop() {
		list_champs_filtre_rec_droite = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setChamp(champs_Type_Noeud_selected);
		cr.setFonctions(new ArrayList<>());
		list_champs_filtre_rec_droite.add(cr);
		FiltreRec rrd = new FiltreRec();
		rrd.setParenteses_ouvrant(new ArrayList<>());
		rrd.setParenteses_fermant(new ArrayList<>());
		rrd.setList_champs_rec1(list_champs_filtre_rec_droite);
		liste_filtres.add(rrd);
	}

	public void onFiltreGaucheDrop() {
		list_champs_filtre_rec_gauche = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setChamp(champs_Type_Noeud_selected1);
		cr.setFonctions(new ArrayList<>());
		list_champs_filtre_rec_gauche.add(cr);
		FiltreRec rrg = new FiltreRec();
		rrg.setParenteses_ouvrant(new ArrayList<>());
		rrg.setParenteses_fermant(new ArrayList<>());
		rrg.setList_champs_rec1(list_champs_filtre_rec_gauche);
		liste_filtres1.add(rrg);
	}

	public void onRuleDroiteDrop() {
		list_champs_rec_droite = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setChamp(champs_Type_Noeud_selected);
		cr.setFonctions(new ArrayList<>());
		list_champs_rec_droite.add(cr);
		RuleRecDroite rrd = new RuleRecDroite();
		rrd.setParenteses(new ArrayList<>());
		rrd.setList_champs_rec1(list_champs_rec_droite);
		list_rules_droite.add(rrd);

	}

	public void onFiltreDrop() {
		list_champs_rec_droite = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setChamp(champs_Type_Noeud_selected);
		cr.setFonctions(new ArrayList<>());
		list_champs_rec_droite.add(cr);
		RuleRecDroite rrd = new RuleRecDroite();
		rrd.setParenteses(new ArrayList<>());
		rrd.setList_champs_rec1(list_champs_rec_droite);
		list_rules_droite.add(rrd);

	}

	private ChampRec selected_champ_rule_rec;
	private ChampRec selected_champ_rule_rec1;

	private ChampRec selected_champ_rule_filtre_rec;
	private ChampRec selected_champ_rule_filtre_rec1;

	public ChampRec getSelected_champ_rule_filtre_rec() {
		return selected_champ_rule_filtre_rec;
	}

	public void setSelected_champ_rule_filtre_rec(ChampRec selected_champ_rule_filtre_rec) {
		this.selected_champ_rule_filtre_rec = selected_champ_rule_filtre_rec;
	}

	public ChampRec getSelected_champ_rule_filtre_rec1() {
		return selected_champ_rule_filtre_rec1;
	}

	public void setSelected_champ_rule_filtre_rec1(ChampRec selected_champ_rule_filtre_rec1) {
		this.selected_champ_rule_filtre_rec1 = selected_champ_rule_filtre_rec1;
	}

	public ChampRec getSelected_champ_rule_rec1() {
		return selected_champ_rule_rec1;
	}

	public void setSelected_champ_rule_rec1(ChampRec selected_champ_rule_rec1) {
		this.selected_champ_rule_rec1 = selected_champ_rule_rec1;
	}

	public ChampRec getSelected_champ_rule_rec() {
		return selected_champ_rule_rec;
	}

	public void setSelected_champ_rule_rec(ChampRec selected_champ_rule_rec) {
		this.selected_champ_rule_rec = selected_champ_rule_rec;
	}

	public void NextTo() {
		System.out.println("next button 2 filtre ");
		selected_champ_rec_to_add = new ChampRec();
		champs_Type_Noeud_selected = new EtlFieldFlow();
		showRuleFitres = true;
		showRuleFitres2 = true;

	}

	private boolean showglobalpan2;

	public void otherFiltre() {
		System.out.println("other flow ");
		showglobalpan = false;
		showrec2 = true;
		showglobalpan2 = true;
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

	private String selected_function_filtre;
	private String selected_function_filtre1;

	public String getSelected_function_filtre() {
		return selected_function_filtre;
	}

	public void setSelected_function_filtre1(String selected_function_filtre1) {
		this.selected_function_filtre1 = selected_function_filtre1;
	}

	public String getSelected_function_filtre1() {
		return selected_function_filtre1;
	}

	public void setSelected_function_filtre(String selected_function_filtre) {
		this.selected_function_filtre = selected_function_filtre;
	}

	private String selected_function;
	private String selected_function1;
	private String selected_function2;

	private String selecteted_functionagg;

	public String getSelecteted_functionagg() {
		return selecteted_functionagg;
	}

	public void setSelecteted_functionagg(String selecteted_functionagg) {
		this.selecteted_functionagg = selecteted_functionagg;
	}

	public String getSelected_function1() {
		return selected_function1;
	}

	public void setSelected_function1(String selected_function1) {
		this.selected_function1 = selected_function1;
	}

	public String getSelected_function() {
		return selected_function;
	}

	public void setSelected_function(String selected_function) {
		this.selected_function = selected_function;
	}

	public void addparentesedroite() {
		System.out.println("Par droite");
		selected_rec_droite.getParenteses().add("(");
	}

	public void addparentesegauche() {

		selected_rec_gauche.getParenteses_fermant().add(")");
		System.out.println("Par gauche parenteses )");
	}

	// ******* JJJJJJJJJJJJ
	public void addparentesegaucheJ() {
		System.out.println("Par gauche parenteses ) J");
		selected_Rule_Join.getParenteses_fermant().add(")");

	}

	/// *********** JJJJJJJJJJJ222222222222
	public void addparentesegaucheJ2() {
		System.out.println("Par gauche parenteses ) J2");
		selected_Rule_Join2.getParenteses_fermant().add(")");
	}

	public void addparentesegauche_ouvrant() {
		System.out.println("Par gauche");
		selected_rec_gauche.getParenteses_ouvrant().add("(");
	}

	// ******* JJJJJJJ
	public void addparentesegauche_ouvrantJ() {
		System.out.println("Par gauche JJJJJJ ");

		for (int i = 0; i < selected_Rule_Join.getParenteses_ouvrant().size(); i++) {
			System.out.println(" parentesse " + selected_Rule_Join.getParenteses_ouvrant().get(i).toString());

		}

		System.out.println(selected_Rule_Join.getParenteses_ouvrant());
		selected_Rule_Join.getParenteses_ouvrant().add("(");
	}

	/**********///// JJJJJJJJ2222222
	public void addparentesegauche_ouvrantJ2() {
		System.out.println("Par gauche JJJJ2");
		selected_Rule_Join2.getParenteses_ouvrant().add("(");
	}

	public void addparenteseouvrantgauche() {
		System.out.println("Par gauche");
		selected_filtre_gauche.getParenteses_ouvrant().add("(");
	}

	public void addparentesefermantgauche() {
		System.out.println("Par gauche");
		selected_filtre_gauche.getParenteses_fermant().add(")");
	}

	public void addparenteseouvrantdroite() {
		System.out.println("Par droite");
		selected_filtre_droite.getParenteses_ouvrant().add("(");
	}

	public void addparentesefermantdroite() {
		System.out.println("Par droite");
		selected_filtre_droite.getParenteses_fermant().add(")");
	}

	public void addFunctionFiltre() {

		selected_champ_rule_filtre_rec.getFonctions().add(selected_function_filtre);

	}

	public void addFunctionFiltre1() {

		selected_champ_rule_filtre_rec1.getFonctions().add(selected_function_filtre1);

	}

	public void addFunction() {
		if (this.getSelected_function().equals("substring")) {
			selected_function = selected_function + "/" + start_substr + "/" + finish_substr;
		}
		selected_champ_rule_rec.getFonctions().add(selected_function);

	}

	private List<RuleRecGauche> list_field_function = new ArrayList<>();

	private RuleRecGauche field_selection_function;

	private EtlFieldProc new_etlfield_proc;

	public void addFunction1() {

		// selected_champ_rule_rec1.getFonctions().add(selected_function1);
		selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().add(selected_function1);
		selected_function1 = "";
		String function_field = "";
		int c = 0;

		for (int i = 0; i < selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().size(); i++) {

			function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().get(i)
					+ "(";
			c = i;
		}
		function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getChamp().getLibelle()
				+ ")";

		for (int i = 0; i < c; i++) {
			function_field = function_field + ")";
		}

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

	}

	public void addFunction2() {

		// selected_champ_rule_rec1.getFonctions().add(selected_function1);
		selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().add(selected_function2);
		selected_function2 = "";
		String function_field = "";
		int c = 0;

		for (int i = 0; i < selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().size(); i++) {

			function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getFonctions().get(i)
					+ "(";
			c = i;
		}
		function_field = function_field + selected_rec_gauche.getList_champs_rec1().get(0).getChamp().getLibelle()
				+ ")";

		for (int i = 0; i < c; i++) {
			function_field = function_field + ")";
		}

		new_etlfield_proc = new EtlFieldProc();
		new_etlfield_proc.setFunction(function_field);
		new_etlfield_proc.setField(selected_rec_gauche.getList_champs_rec1().get(0).getChamp());
		new_etlfield_proc.setId_field_dest(selected_rec_gauche.getList_champs_rec1().get(0).getChamp().getId());
		new_etlfield_proc.setProc_flow(null);

		int id_selection = 0;

		id_selection = list_rules_gauche2.indexOf(selected_rec_gauche);

		System.out.println(" id of selection field " + id_selection + "size of list " + list_etldield_proc2.size());

		if (list_etldield_proc2.size() == id_selection) {

			list_etldield_proc2.add(id_selection, new_etlfield_proc);

		} else {

			list_etldield_proc2.remove(id_selection);
			list_etldield_proc2.add(id_selection, new_etlfield_proc);
		}

		System.out.println(list_etldield_proc2.size());
		for (int i = 0; i < list_etldield_proc2.size(); i++) {
			System.out.println("etl_field_proc list 2 " + list_etldield_proc2.get(i).getFunction());
			System.out.println("etl_field_proc list 4 " + list_etldield_proc2.get(i).getId_field_dest());
		}

		new_etlfield_proc = new EtlFieldProc();

	}

	public void addFunctionAgg() {
		selected_champ_agg.getFonctions().add(selecteted_functionagg);
	}

	private String selected_operator1;
	private String selected_operator;
	private String selected_operator_filtre1;
	private String selected_operator_filtre;

	public String getSelected_operator_filtre() {
		return selected_operator_filtre;
	}

	public void setSelected_operator_filtre(String selected_operator_filtre) {
		this.selected_operator_filtre = selected_operator_filtre;
	}

	public String getSelected_operator_filtre1() {
		return selected_operator_filtre1;
	}

	public void setSelected_operator_filtre1(String selected_operator_filtre1) {
		this.selected_operator_filtre1 = selected_operator_filtre1;
	}

	public String getSelected_operator1() {
		return selected_operator1;
	}

	public void setSelected_operator1(String selected_operator1) {
		this.selected_operator1 = selected_operator1;
	}

	public String getSelected_operator() {
		return selected_operator;
	}

	public void setSelected_operator(String selected_operator) {
		this.selected_operator = selected_operator;
	}

	private ChampRec selected_champ_rec_to_add;

	public ChampRec getSelected_champ_rec_to_add() {
		return selected_champ_rec_to_add;
	}

	public void setSelected_champ_rec_to_add(ChampRec selected_champ_rec_to_add) {
		this.selected_champ_rec_to_add = selected_champ_rec_to_add;
	}

	private boolean showRuleFitres = false;

	private Boolean showRuleFitres2 = false;

	public boolean isShowRuleFitres() {
		return showRuleFitres;
	}

	public void setShowRuleFitres(boolean showRuleFitres) {
		this.showRuleFitres = showRuleFitres;
	}

	public void addChampToruleDroite() {
		System.out.println("ADD addChampToruleDroite");
		ChampRec rec1 = new ChampRec();
		rec1.setChamp(champs_Type_Noeud_selected);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator);
		selected_rec_droite.getList_champs_rec1().add(rec1);
	}

	public void addChampToFiltreDroite() {
		System.out.println("ADD");
		ChampRec rec1 = new ChampRec();
		rec1.setChamp(champs_Type_Noeud_selected);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator_filtre);
		selected_filtre_droite.getList_champs_rec1().add(rec1);
	}

	public void addChampToFiltreGauche() {
		System.out.println("ADD");
		ChampRec rec1 = new ChampRec();
		rec1.setChamp(champs_Type_Noeud_selected1);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator_filtre1);
		selected_filtre_gauche.getList_champs_rec1().add(rec1);
	}

	// add operator button 3
	public void addChampToruleGauche() {
		System.out.println("ADD to ");
		ChampRec rec1 = new ChampRec();
		System.out.println(champs_Type_Noeud_selected2);
		rec1.setChamp(champs_Type_Noeud_selected2);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator1);
		selected_rec_gauche.getList_champs_rec1().add(rec1);
	}

	public void addChampToruleGauche2() {
		System.out.println("ADD to ");
		ChampRec rec1 = new ChampRec();
		rec1.setChamp(champs_Type_Noeud_selected1);
		rec1.setFonctions(new ArrayList<>());
		rec1.setOperator(selected_operator1);
		selected_rec_gauche.getList_champs_rec1().add(rec1);
	}

	// add filtre buttion actionListener
	public void onRuleGaucheDrop() {
		list_champs_rec_gauche = new ArrayList<>();
		ChampRec cr = new ChampRec();
		cr.setId(id_filtre1);
		cr.setChamp(champs_Type_Noeud_selected1);
		cr.setFonctions(new ArrayList<>());
		list_champs_rec_gauche.add(cr);
		id_filtre1++;

		RuleRecGauche rrg = new RuleRecGauche();
		rrg.setId(id_filtre);
		rrg.setList_champs_rec1(list_champs_rec_gauche);
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
			list_rules_gauche2.add(deepcopylist_rules_gauche2(rrg));
			System.out.println("try 2");

		} catch (Exception e) {
			System.out.println("try problem ");
		}
		System.out.println("rrg 4 ");
		id_filtre++;
		System.out.println("midle of add filtre gauche");

	}
	public RuleRecGauche deepcopylist_rules_gauche2(RuleRecGauche input){
			
		RuleRecGauche copy = new RuleRecGauche();
		copy.setId(input.getId());
		copy.setList_champs_rec1(input.getList_champs_rec1());
		copy.setLogic(input.getLogic());
		copy.setOperator(input.getOperator());
		copy.setParenteses_fermant(input.getParenteses_fermant());
		copy.setParenteses_ouvrant(input.getParenteses_ouvrant());
		copy.setValeur(input.getValeur());
		
		return copy;
	}

	public void onRuleDelete(RuleRecGauche selected_rec_gauche) {

		System.out.println("delete function started");
		list_rules_gauche.remove(selected_rec_gauche);
		System.out.println("delete function ended ");
	}

	public void onRuleDelete2(RuleRecGauche selected_rec_gauche) {
		list_rules_gauche2.remove(selected_rec_gauche);
	}

	private int id_filtre1 = 1;

	public int getId_filtre1() {
		return id_filtre1;
	}

	public void setId_filtre1(int id_filtre1) {
		this.id_filtre1 = id_filtre1;
	}

	private int id_agg = 1;

	public int getId_agg() {
		return id_agg;
	}

	public void setId_agg(int id_agg) {
		this.id_agg = id_agg;
	}

	public void addAggregation() {
		ChampRec cr = new ChampRec();
		cr.setId(id_agg);
		cr.setChamp(champs_Type_Noeud_selected1);
		cr.setFonctions(new ArrayList<>());
		list_agg_champs.add(cr);
		id_agg++;
	}

	public void onFieldStatDrop(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_champs3.add(car);
		list_champs3.remove(car);
	}

	public void onFieldDrop(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_champs1.add(car);
		list_champs1.remove(car);

		selected_champ_rec_to_add = new ChampRec();
		champs_Type_Noeud_selected = new EtlFieldFlow();
		showRuleFitres = true;
		showRuleFitres2 = true;
		Ajax.update(":form1:");
		RequestContext.getCurrentInstance().update(":form1:");

	}

	public void onFieldDrop2(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_rec.add(car);
		list_champs_rec.remove(car);
	}

	public void onFieldDrop1(DragDropEvent ddEvent) {
		EtlFieldFlow car = ((EtlFieldFlow) ddEvent.getData());
		list_select_champs2.add(car);
		list_champs2.remove(car);

	}

	public boolean isShowaddVariable() {
		return showaddVariable;
	}

	public void setShowaddVariable(boolean showaddVariable) {
		this.showaddVariable = showaddVariable;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	private String variable;

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public void addVariable() {

		query = query + " " + variable;

	}

	private List<String> all_references;

	public List<String> getAll_references() {
		return all_references;
	}

	public void setAll_references(List<String> all_references) {
		this.all_references = all_references;
	}

	public void createFunctions() {

	}

	public void addFlow() throws IOException {
		List<Flow> lst_flow = new ArrayList<>();
		lst_flow = flow_service.getAllFlow();
		System.out.println("add flow message ");
		int i = 0;
		boolean check = false;

		while (i < lst_flow.size()) {
			System.out.println("check whe list");
			if (lst_flow.get(i).getTable_name() == flow.getTable_name()) {
				System.out.println("equals ********");
				check = true;
			}
			i++;
			if (!check) {
				System.out.println("add flow message2 ");
				flow_service.addFlow(flow);
				flow_service.createTableProcess(flow);
				flow = new Flow();
				initTree();
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Successful", "ce flow deja existe !! "));
			}

		}

	}

	public void addChamp_type_noeud() {

	}

	private boolean showaddFlow = false;
	private boolean showChamps = false;
	private boolean showaddNode = false;
	private boolean showaddRep = false;

	public boolean isShowaddFlow() {
		return showaddFlow;
	}

	public void setShowaddFlow(boolean showaddFlow) {
		this.showaddFlow = showaddFlow;
	}

	public boolean isShowChamps() {
		return showChamps;
	}

	public void setShowChamps(boolean showChamps) {
		this.showChamps = showChamps;
	}

	public boolean isShowaddNode() {
		return showaddNode;
	}

	public void setShowaddNode(boolean showaddNode) {
		this.showaddNode = showaddNode;
	}

	public boolean isShowaddRep() {
		return showaddRep;
	}

	public void setShowaddRep(boolean showaddRep) {
		this.showaddRep = showaddRep;
	}

	private boolean showasn1 = false;
	private boolean showascii = false;

	private boolean showaddService = false;

	public boolean isShowaddService() {
		return showaddService;
	}

	public void setShowaddService(boolean showaddService) {
		this.showaddService = showaddService;
	}

	public boolean isShowascii() {
		return showascii;
	}

	public void setShowascii(boolean showascii) {
		this.showascii = showascii;
	}

	public boolean isShowasn1() {
		return showasn1;
	}

	public void setShowasn1(boolean showasn1) {
		this.showasn1 = showasn1;
	}

	private Config_ASCII new_config = new Config_ASCII();
	private Config_ASN1 new_config_asn1 = new Config_ASN1();

	public Config_ASCII getNew_config() {
		return new_config;
	}

	public void setNew_config(Config_ASCII new_config) {
		this.new_config = new_config;
	}

	public void setNew_config_asn1(Config_ASN1 new_config_asn1) {
		this.new_config_asn1 = new_config_asn1;
	}

	public Config_ASN1 getNew_config_asn1() {
		return new_config_asn1;
	}

	public void addJob() {

	}

	boolean form1Show;

	public boolean isForm1Show() {
		return form1Show;
	}

	public void setForm1Show(boolean form1Show) {
		this.form1Show = form1Show;
	}

	private boolean showglobalpan;

	public void switchView() throws IOException {
		flow = new Flow();
		form1Show = false;
		new_noeud = new Noeud();
		new_config_asn1 = new Config_ASN1();
		new_Convertisseur = new Convertisseur();
		liste_champs = new ArrayList<>();
		liste_champs_service = new ArrayList<>();
		showaddFlow = false;
		showstat = false;
		showAddJob = false;
		showaddNode = false;
		showaddRep = false;
		showaddService = false;
		showascii = false;
		showasn1 = false;
		showChamps = false;
		showChampsService = false;
		showFieldQuery = false;
		showWhere = false;
		showrec = false;
		showglobalpan = true;
		if (selectedNode != null) {
			if (((Doc) selectedNode.getData()).getType() == null) {
				if (((Doc) selectedNode.getData()).getName().equals("Flows")) {
					showaddFlow = true;
				} else if (((Doc) selectedNode.getData()).getName().equals("Fields")) {
					Flow f = (Flow) ((Doc) selectedNode.getParent().getData()).getType();
					new_champ = new EtlFieldFlow();
					list_champs = flow_service.getAllChampsByFlow(f);
					showChamps = true;
				} else if (((Doc) selectedNode.getData()).getName().equals("ASN1")) {
					showasn1 = true;
				} else if (((Doc) selectedNode.getData()).getName().equals("ASCII")) {
					showascii = true;
				} else if (((Doc) selectedNode.getData()).getName().equals("Nodes ASN1")) {
					showaddNode = true;
				} else if (((Doc) selectedNode.getData()).getName().equals("Traffic Controle")) {
					showrec = true;
					showglobalpan = true;
					form1Show = true;
					FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
					FacesContext.getCurrentInstance().getExternalContext().redirect("C.xhtml");
					RequestContext.getCurrentInstance().update(":form1");
				} else if (((Doc) selectedNode.getData()).getName().equals("Services")
						&& ((Doc) selectedNode.getParent().getData()).getType() instanceof Convertisseur) {
					new_conv_service = new EtlServiceConverter();
					showaddService = true;

				}
			} else if (((Doc) selectedNode.getData()).getType() instanceof Noeud) {
				showaddRep = true;
			}
		}
	}

	private boolean showAddJob = false;

	public boolean isShowAddJob() {
		return showAddJob;
	}

	public void setShowAddJob(boolean showAddJob) {
		this.showAddJob = showAddJob;
	}

	private EtlFieldFlow selected_champ;
	private EtlFieldFlow selected_champ3;

	public EtlFieldFlow getSelected_champ3() {
		return selected_champ3;
	}

	public void setSelected_champ3(EtlFieldFlow selected_champ3) {
		this.selected_champ3 = selected_champ3;
	}

	public EtlFieldFlow getSelected_champ() {
		return selected_champ;
	}

	public void setSelected_champ(EtlFieldFlow selected_champ) {
		this.selected_champ = selected_champ;
	}

	private String sql_query_transform;

	public String getSql_query_transform() {
		return sql_query_transform;
	}

	public void setSql_query_transform(String sql_query_transform) {
		this.sql_query_transform = sql_query_transform;
	}

	public void change_sql(String change) {
		sql_query_transform = sql_query_transform + " " + change;
	}

	private boolean showChampsService = false;

	public boolean isShowChampsService() {
		return showChampsService;
	}

	public void setShowChampsService(boolean showChampsService) {
		this.showChampsService = showChampsService;
	}

	private Noeud new_noeud;

	public Noeud getNew_noeud() {
		return new_noeud;
	}

	public void setNew_noeud(Noeud new_noeud) {
		this.new_noeud = new_noeud;
	}

	private RuleRecGauche selected_rec_gauche;

	private boolean substr = false;

	public boolean isSubstr() {
		return substr;
	}

	public void setSubstr(boolean substr) {
		this.substr = substr;
	}

	private ChampRec selected_champ_agg = new ChampRec();

	public ChampRec getSelected_champ_agg() {
		return selected_champ_agg;
	}

	public void setSelected_champ_agg(ChampRec selected_champ_agg) {
		this.selected_champ_agg = selected_champ_agg;
	}

	public RuleRecGauche getSelected_rec_gauche() {
		return selected_rec_gauche;
	}

	public void setSelected_rec_gauche(RuleRecGauche selected_rec_gauche) {
		this.selected_rec_gauche = selected_rec_gauche;
	}

	private RuleRecJoin selected_Rule_Joinadd1;

	private RuleRecJoin selected_Rule_Joinadd2;

	public void addLigneTable1() {

		selected_Rule_Joinadd1 = new RuleRecJoin();
		selected_Rule_Joinadd1.setParenteses_ouvrant(new ArrayList<>());
		selected_Rule_Joinadd1.setParenteses_fermant(new ArrayList<>());
		selected_Rule_Joinadd1.setFlow(selected_flow1);
		selected_Rule_Joinadd1.setField(selected_flow1.getTable_name() + ".");
		selected_Rule_Joinadd1.setList_champs_rec1(selected_flow1.getListe_champs());
		list_Rule_Join.add(selected_Rule_Joinadd1);

		inputtext = true;
		listselected_flow = false;

	}

	public void addLigneTable2() {

		selected_Rule_Joinadd2 = new RuleRecJoin();
		selected_Rule_Joinadd2.setParenteses_ouvrant(new ArrayList<>());
		selected_Rule_Joinadd2.setParenteses_fermant(new ArrayList<>());
		selected_Rule_Joinadd2.setFlow(selected_flow2);
		selected_Rule_Joinadd2.setField(selected_flow2.getTable_name() + ".");
		selected_Rule_Joinadd2.setList_champs_rec1(selected_flow2.getListe_champs());
		list_Rule_Join2.add(selected_Rule_Joinadd2);

		inputtext2 = true;
		listselected_flow2 = false;

	}

	public void initTree() {

		// root = new DefaultTreeNode(new Doc(null, "Flows"), null);
		// flows = new DefaultTreeNode(new Doc(null, "Flows"), root);

		showaddFlow = false;
		showAddJob = false;
		showaddNode = false;
		showaddRep = false;
		showaddService = false;
		showascii = false;
		showasn1 = false;
		showChamps = false;
		showChampsService = false;
		showFieldQuery = false;
		showWhere = false;

		list_rules_gauche = new ArrayList<>();
		functions = new ArrayList<>();
		list_type_flow = flow_service.getAllTypeFlow();
		list_bin_type = flow_service.getAllBinType();
		functions.add("substring");
		functions.add("to_date");
		functions.add("to_integer");

		liste_services = services_service.getAllServices();
		list_flow = flow_service.getAllFlow();
		list_rules_droite = new ArrayList<>();
		selected_champ_rec_to_add = new ChampRec();

		// root = new DefaultTreeNode(new Doc(null, "Flows"), null);
		// Doc flows= new Doc(null, "Flows");

		flows = new DefaultTreeNode(new Doc(null, "Flows"), null);

		// Jobs = new DefaultTreeNode(new Doc(null, "Jobs"), root);
		rating_controle = new DefaultTreeNode(new Doc(null, "Traffic Controle"), null);
		// TreeNode plateform_intgrity = new DefaultTreeNode(new Doc(null,
		// "Platform Integrity"), root);
		// TreeNode recon_hlr_ocs = new DefaultTreeNode(new Doc(null, "Recon
		// HLR_IN"), plateform_intgrity);

		// TreeNode recon_hlr = new DefaultTreeNode(new Doc(null, "Recon
		// HLR_Billing"), plateform_intgrity);

	}

	public void AddNode() {
		Convertisseur f = (Convertisseur) ((Doc) selectedNode.getParent().getData()).getType();
		List<Repertoire> list_rep = new ArrayList<>();
		list_rep.add(new_repertoire);

		new_noeud.setConverter(f);
		new_repertoire.setRep_noeud(new_noeud);
		new_noeud.setNoeud_repertoires(list_rep);
		noeud_service.addNoeud(new_noeud);
		System.out.println(new_noeud.getId());
		for (int i = 1; i <= new_repertoire.getNb_fils(); i++) {
			String table = f.getFlow().getTable_name().toLowerCase() + "_" + new_noeud.getId() + "_" + i;

			flow_service.createTable(f.getFlow().getListe_champs(), table);
			flow_service.createTableProcessNodeChild(f.getFlow(), new_noeud, i);
		}

		new_noeud = new Noeud();
		initTree();

	}

	private List<Service> liste_services;

	public List<Service> getListe_services() {
		return liste_services;
	}

	public void setListe_services(List<Service> liste_services) {
		this.liste_services = liste_services;
	}

	private EtlServiceConverter new_conv_service;

	public EtlServiceConverter getNew_conv_service() {
		return new_conv_service;
	}

	public void setNew_conv_service(EtlServiceConverter new_conv_service) {
		this.new_conv_service = new_conv_service;
	}

	private Convertisseur new_Convertisseur;

	public Convertisseur getNew_Convertisseur() {
		return new_Convertisseur;
	}

	public void setNew_Convertisseur(Convertisseur new_Convertisseur) {
		this.new_Convertisseur = new_Convertisseur;
	}

	public void validateProcStat() {
		new_etl_proc = new EtlProc();
		new_etl_proc.setProc_type("Stat");
		new_etl_proc.setName("Stat " + selected_flow1.getFlowName());

		flow_service.addProc(new_etl_proc);
		System.out.println("inserting ");

		EtlProcFlow epf = new EtlProcFlow();
		epf.setFlow(selected_flow1);
		epf.setProc(new_etl_proc);

		String filtre = "";
		for (RuleRecGauche rrg : list_rules_gauche) {
			for (String s : rrg.getParenteses_ouvrant()) {
				filtre = filtre + s;
			}

			for (ChampRec rc : rrg.getList_champs_rec1()) {
				if (rc.getOperator() != null) {
					filtre = filtre + rc.getOperator();

				}
				for (String fn : rc.getFonctions()) {
					filtre = filtre + fn + "(";
				}
				filtre = filtre + rc.getChamp().getLibelle();
				for (String fn : rc.getFonctions()) {
					filtre = filtre + ")";
				}
			}

			filtre = filtre + rrg.getOperator() + rrg.getValeur();

			for (String s : rrg.getParenteses_fermant()) {
				filtre = filtre + s;

			}
			if (rrg.getParenteses_fermant().size() >= rrg.getParenteses_ouvrant().size()) {
				filtre = filtre + "/";
			}
			if (rrg.getLogic() != null) {
				filtre = filtre + rrg.getLogic();
			}

		}
		epf.setQuery_filters(filtre);
		flow_service.addEtlProcFlow(epf);

		List<EtlFieldProc> listetlfields = new ArrayList<>();

		for (ChampRec crf : list_agg_champs) {
			EtlFieldProc efp = new EtlFieldProc();
			efp.setProc_flow(epf);
			efp.setField(crf.getChamp());
			efp.setFunction(crf.getFonctions().get(0));
			efp.setType_field("Aggregation");
			flow_service.addEtlFieldProc(efp);
		}
		epf.setList_fields(listetlfields);
		flow_service.updateEtlProcFlow(epf);
	}

	private boolean showrec2 = false;

	public void NextFlow() {

		showrec2 = true;

	}

	boolean show_listfild_rename = false;

	public boolean isShow_listfild_rename() {
		return show_listfild_rename;
	}

	public void setShow_listfild_rename(boolean show_listfild_rename) {
		this.show_listfild_rename = show_listfild_rename;
	}

	long idepf = 1;

	public void show_listfild_rename() {
		show_listfild_rename = true;
	}

	public void validFiltreproc() {
		System.out.println("begin inserting of etl_proc_Flow");

		new_etl_procF = new EtlProc();
		new_etl_procF.setProc_type("REC");
		new_etl_procF.setName("REC_" + selected_flow1.getFlowName() + "_" + selected_flow2.getFlowName());

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
				for (String fn : rc.getFonctions()) {
					filtre = filtre + fn + "(";
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

			for (String s : rrg.getParenteses_fermant()) {
				filtre = filtre + s;
			}

			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				if (rrg.getLogic() != null) {
					filtre = filtre + rrg.getLogic();
				}
			}

		}

		epf.setQuery_filters(filtre);
		System.out.println(selected_flow1.getId());
		flow_service.addEtlProcFlow(epf);

		EtlProcFlow epf2 = new EtlProcFlow();
		epf2.setFlow(selected_flow2);
		epf2.setProc(new_etl_procF);
		String filtre2 = "";
		for (RuleRecGauche rrg : list_rules_gauche2) {
			for (String s : rrg.getParenteses_ouvrant()) {
				filtre2 = filtre2 + s;
			}
			for (ChampRec rc : rrg.getList_champs_rec1()) {
				if (rc.getOperator() != null) {
					filtre2 = filtre2 + rc.getOperator();

				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre2 = filtre2 + fn + "(";
					}
				}

				if (rc.getChamp().getLibelle() != null) {
					filtre2 = filtre2 + rc.getChamp().getLibelle();
				}
				if (rc.getFonctions() != null) {
					for (String fn : rc.getFonctions()) {
						filtre2 = filtre2 + ")";
					}
				}
			}

			if ((rrg.getValeur() != null) && (rrg.getOperator() != null)) {
				filtre2 = filtre2 + rrg.getOperator() + rrg.getValeur();
			}
			if (rrg.getParenteses_fermant() != null) {
				for (String s : rrg.getParenteses_fermant()) {
					filtre2 = filtre2 + s;
				}
			}
			// if (rrg.getParenteses_fermant().size() >=
			// rrg.getParenteses_ouvrant().size()) {
			// filtre = filtre + "/";
			// }
			if (rrg.getLogic() != null) {
				filtre2 = filtre2 + rrg.getLogic();
			}

		}
		System.out.println("filtre 2" + filtre2);
		epf2.setQuery_filters(filtre2);
		flow_service.updateEtlProcFlow(epf2);

		String queryjoin = "";

		for (int i = 0; i < list_Rule_Join.size(); i++) {

			if (list_Rule_Join.get(i).getParenteses_ouvrant() != null) {

				for (String s : list_Rule_Join.get(i).getParenteses_ouvrant()) {
					queryjoin = queryjoin + s;
				}
			}

			if (list_Rule_Join.get(i).getField() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getField().toString();
			}
			if (list_Rule_Join.get(i).getOperator() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getOperator().toString();
			}
			if (list_Rule_Join.get(i).getValeur() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getValeur();
			}
			if (list_Rule_Join.get(i).getParenteses_fermant() != null) {
				for (String s : list_Rule_Join.get(i).getParenteses_fermant()) {
					queryjoin = queryjoin + s;
				}
			}
			if (list_Rule_Join.get(i).getLogic() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getLogic();
			}
		}

		new_etl_procF.setQuery_join(queryjoin);
		flow_service.updateProc(new_etl_procF);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Successful", "Query Filtre Inserted "));
	}

	private boolean showpanjoin = false;

	private List<EtlFieldFlow> list_champs_Join1 = new ArrayList<>();

	private List<EtlFieldFlow> list_champs_Join2 = new ArrayList<>();

	public List<EtlFieldFlow> getList_champs_Join() {
		return list_champs_Join1;
	}

	public void setList_champs_Join(List<EtlFieldFlow> list_champs_Join) {
		this.list_champs_Join1 = list_champs_Join;
	}

	private String valeur;

	private Flow selectdemandeFlow;
	private List<EtlFieldFlow> list_selected = new ArrayList<>();
	private boolean inputtext = true;
	private boolean listselected_flow = false;

	private boolean inputtext2 = true;
	private boolean listselected_flow2 = false;

	public void demander() {
		inputtext = false;
		setList_selected(flow_service.getflowbyid(selectdemandeFlow.getId()));
		listselected_flow = true;
		valeur = null;
		RequestContext.getCurrentInstance().update("gridvalue");

	}

	public void demander2() {
		inputtext2 = false;
		System.out.println("demander 2222 *****");
		setList_selected(selectdemandeFlow.getListe_champs());
		listselected_flow2 = true;
		valeur = null;
		System.out.println("demander2 *****");

	}

	private String functionAgg = null;

	private String selected_champ_To_function = null;

	public void addfunctionToJoinpage() {

		System.out.println(selected_champ_To_function);
		System.out.println(functionAgg);

		if (selected_Rule_Join.getField().toString().equals(selected_champ_To_function)) {

			String field = selected_Rule_Join.getField();
			functionAgg = functionAgg + "(" + field + ")";
			selected_Rule_Join.setField(functionAgg);

		} else if (selected_Rule_Join.getValeur().toString().equals(selected_champ_To_function)) {

			String valeur = selected_Rule_Join.getValeur();
			functionAgg = functionAgg + "(" + valeur + ")";
			selected_Rule_Join.setValeur(functionAgg);

		}

	}

	public void cache() {
		showRuleFitres = false;
		showRuleFitres2 = false;
		showglobalpan2 = false;
		showrec2 = false;
		showpanjoin = true;

		list_champs_Join1 = selected_flow1.getListe_champs();
		list_champs_Join2 = selected_flow2.getListe_champs();

		joinRec.setParenteses_ouvrant(new ArrayList<>());
		joinRec.setFlow(selected_flow1);
		joinRec.setList_champs_rec1(selected_flow1.getListe_champs());
		joinRec.setParenteses_fermant(new ArrayList<>());

		joinRec2.setParenteses_ouvrant(new ArrayList<>());
		joinRec2.setFlow(selected_flow2);
		joinRec2.setList_champs_rec1(selected_flow2.getListe_champs());
		joinRec2.setParenteses_fermant(new ArrayList<>());

		list_Rule_Join.add(joinRec);

		list_Rule_Join2.add(joinRec2);

		System.out.println("1st flow" + selected_flow1);
		System.out.println("1st flow" + selected_flow2);

	}

	public void showquery() {

		String queryjoin = "";

		for (int i = 0; i < list_Rule_Join.size(); i++) {

			if (list_Rule_Join.get(i).getParenteses_ouvrant() != null) {

				for (String s : list_Rule_Join.get(i).getParenteses_ouvrant()) {
					queryjoin = queryjoin + s;
				}
			}

			if (list_Rule_Join.get(i).getField() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getField().toString();
			}
			if (list_Rule_Join.get(i).getOperator() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getOperator().toString();
			}
			if (list_Rule_Join.get(i).getValeur() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getValeur();
			}
			if (list_Rule_Join.get(i).getParenteses_fermant() != null) {
				for (String s : list_Rule_Join.get(i).getParenteses_fermant()) {
					queryjoin = queryjoin + s;
				}
			}
			if (list_Rule_Join.get(i).getLogic() != null) {
				queryjoin = queryjoin + list_Rule_Join.get(i).getLogic();
			}
		}
		for (int j = 0; j < list_Rule_Join2.size(); j++) {

			if (list_Rule_Join2.get(j).getParenteses_ouvrant() != null) {

				for (String s : list_Rule_Join2.get(j).getParenteses_ouvrant()) {
					queryjoin = queryjoin + s;
				}
			}

			if (list_Rule_Join2.get(j).getField() != null) {
				queryjoin = queryjoin + list_Rule_Join2.get(j).getField().toString();
			}
			if (list_Rule_Join2.get(j).getOperator() != null) {
				queryjoin = queryjoin + list_Rule_Join2.get(j).getOperator().toString();
			}
			if (list_Rule_Join2.get(j).getValeur() != null) {
				queryjoin = queryjoin + list_Rule_Join2.get(j).getValeur();
			}
			if (list_Rule_Join2.get(j).getParenteses_fermant() != null) {
				for (String s : list_Rule_Join2.get(j).getParenteses_fermant()) {
					queryjoin = queryjoin + s;
				}
			}
			if (list_Rule_Join2.get(j).getLogic() != null) {
				queryjoin = queryjoin + list_Rule_Join2.get(j).getLogic();
			}
		}
		System.out.println("final query =====>>>" + queryjoin);
	}

	private boolean joinRule2 = false;

	public void AddNewJoinRule() {

		setJoinRule22(true);

		System.out.println(" show table join 2 add join field ");
	}

	public void AddConvertisser() {
		Flow f = (Flow) ((Doc) selectedNode.getParent().getParent().getData()).getType();
		new_Convertisseur.setFlow(f);
		new_Convertisseur.setConfig_asn1(new_config_asn1);
		converter_service.addConverter(new_Convertisseur);
		new_Convertisseur = new Convertisseur();
		new_config_asn1 = new Config_ASN1();
		initTree();

	}

	private List<EtlFieldFlow> liste_champs;

	public List<EtlFieldFlow> getListe_champs() {
		return liste_champs;
	}

	public void setListe_champs(List<EtlFieldFlow> liste_champs) {
		this.liste_champs = liste_champs;
	}

	private EtlFieldFlow new_champ;

	public EtlFieldFlow getNew_champ() {
		return new_champ;
	}

	public void setNew_champ(EtlFieldFlow new_champ) {
		this.new_champ = new_champ;
	}

	private Repertoire new_repertoire = new Repertoire();

	public Repertoire getNew_repertoire() {
		return new_repertoire;
	}

	public void setNew_repertoire(Repertoire new_repertoire) {
		this.new_repertoire = new_repertoire;
	}

	public void AddRepertoire() {
		Noeud n = (Noeud) ((Doc) selectedNode.getData()).getType();
		new_repertoire.setRep_noeud(n);
		rep_service.addRepository(new_repertoire);
		Flow f = new_repertoire.getRep_noeud().getConverter().getFlow();

		for (int i = 1; i <= new_repertoire.getNb_fils(); i++) {
			String table = f.getTable_name() + "_" + new_repertoire.getRep_noeud().getId() + "_" + i;
			flow_service.createTable(f.getListe_champs(), table);
		}
		new_repertoire = new Repertoire();
		initTree();

	}

	private boolean showFieldQuery = false;

	public boolean isShowFieldQuery() {
		return showFieldQuery;
	}

	public void setShowFieldQuery(boolean showFieldQuery) {
		this.showFieldQuery = showFieldQuery;
	}

	public String addAction() {

		Flow f = (Flow) ((Doc) selectedNode.getParent().getData()).getType();
		new_champ.setFlow(f);
		list_champs.add(new_champ);
		new_champ = new EtlFieldFlow();
		return null;
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Field Edited", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Item Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		list_champs.remove((EtlFieldFlow) event.getObject());
	}

	public void AddRepestory() {
		noeud_service.addNoeud(new_noeud);
		initTree();
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public List<Flow> getList_flow() {
		return list_flow;
	}

	public void setList_flow(List<Flow> list_flow) {
		this.list_flow = list_flow;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public void displaySelectedSingle() {
		flow = new Flow();
	}

	public void deleteNode() {
		selectedNode.getChildren().clear();
		selectedNode.getParent().getChildren().remove(selectedNode);
		if (selectedNode.getParent().getData().toString().equals("Flows")) {
			System.out.println(" selected node is flow *********** ");
		}
		selectedNode.setParent(null);
		selectedNode = null;

	}

	public List<EtlFieldFlow> getList_champs4() {
		return list_champs4;
	}

	public void setList_champs4(List<EtlFieldFlow> list_champs4) {
		this.list_champs4 = list_champs4;
	}

	public List<EtlFieldFlow> getList_field_select() {
		return list_field_select;
	}

	public void setList_field_select(List<EtlFieldFlow> list_field_select) {
		this.list_field_select = list_field_select;
	}

	public EtlFieldFlow getAdd() {
		return add;
	}

	public void setAdd(EtlFieldFlow add) {
		this.add = add;
	}

	public List<EtlFieldFlow> getList_filtre() {
		return list_filtre;
	}

	public void setList_filtre(List<EtlFieldFlow> list_filtre) {
		this.list_filtre = list_filtre;
	}

	public boolean isShowglobalpan() {
		return showglobalpan;
	}

	public void setShowglobalpan(boolean showglobalpan) {
		this.showglobalpan = showglobalpan;
	}

	public boolean isShowrec2() {
		return showrec2;
	}

	public void setShowrec2(boolean showrec2) {
		this.showrec2 = showrec2;
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

	public Boolean getShowRuleFitres2() {
		return showRuleFitres2;
	}

	public void setShowRuleFitres2(Boolean showRuleFitres2) {
		this.showRuleFitres2 = showRuleFitres2;
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

	public List<RuleRecGauche> getList_rules_gauche2() {
		return list_rules_gauche2;
	}

	public void setList_rules_gauche2(List<RuleRecGauche> list_rules_gauche2) {
		this.list_rules_gauche2 = list_rules_gauche2;
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

	public boolean isShowpanjoin() {
		return showpanjoin;
	}

	public void setShowpanjoin(boolean showpanjoin) {
		this.showpanjoin = showpanjoin;
	}

	public List<EtlFieldFlow> getList_champs_Join2() {
		return list_champs_Join2;
	}

	public void setList_champs_Join2(List<EtlFieldFlow> list_champs_Join2) {
		this.list_champs_Join2 = list_champs_Join2;
	}

	public List<RuleRecJoin> getList_Rule_Join() {
		return list_Rule_Join;
	}

	public void setList_Rule_Join(List<RuleRecJoin> list_Rule_Join) {
		this.list_Rule_Join = list_Rule_Join;
	}

	public RuleRecJoin getJoinRec() {
		return joinRec;
	}

	public void setJoinRec(RuleRecJoin joinRec) {
		this.joinRec = joinRec;
	}

	public RuleRecJoin getJoinRec2() {
		return joinRec2;
	}

	public void setJoinRec2(RuleRecJoin joinRec2) {
		this.joinRec2 = joinRec2;
	}

	public List<RuleRecJoin> getList_Rule_Join2() {
		return list_Rule_Join2;
	}

	public void setList_Rule_Join2(List<RuleRecJoin> list_Rule_Join2) {
		this.list_Rule_Join2 = list_Rule_Join2;
	}

	public RuleRecJoin getSelected_Rule_Join() {
		return selected_Rule_Join;
	}

	public void setSelected_Rule_Join(RuleRecJoin selected_Rule_Join) {
		this.selected_Rule_Join = selected_Rule_Join;
	}

	public RuleRecJoin getSelected_Rule_Join2() {
		return selected_Rule_Join2;
	}

	public void setSelected_Rule_Join2(RuleRecJoin selected_Rule_Join2) {
		this.selected_Rule_Join2 = selected_Rule_Join2;
	}

	public boolean isJoinRule22() {
		return joinRule2;
	}

	public void setJoinRule22(boolean joinRule22) {
		this.joinRule2 = joinRule22;
	}

	public RuleRecJoin getSelected_Rule_Joinadd() {
		return selected_Rule_Joinadd1;
	}

	public void setSelected_Rule_Joinadd(RuleRecJoin selected_Rule_Joinadd) {
		this.selected_Rule_Joinadd1 = selected_Rule_Joinadd;
	}

	public Flow getSelectdemandeFlow() {
		return selectdemandeFlow;
	}

	public void setSelectdemandeFlow(Flow selectdemandeFlow) {
		this.selectdemandeFlow = selectdemandeFlow;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

	public List<EtlFieldFlow> getList_selected() {
		return list_selected;
	}

	public void setList_selected(List<EtlFieldFlow> list_selected) {
		this.list_selected = list_selected;
	}

	public boolean isListselected_flow() {
		return listselected_flow;
	}

	public void setListselected_flow(boolean listselected_flow) {
		this.listselected_flow = listselected_flow;
	}

	public boolean isInputtext() {
		return inputtext;
	}

	public void setInputtext(boolean inputtext) {
		this.inputtext = inputtext;
	}

	public boolean isInputtext2() {
		return inputtext2;
	}

	public void setInputtext2(boolean inputtext2) {
		this.inputtext2 = inputtext2;
	}

	public boolean isListselected_flow2() {
		return listselected_flow2;
	}

	public void setListselected_flow2(boolean listselected_flow2) {
		this.listselected_flow2 = listselected_flow2;
	}

	public String getFunctionAgg() {
		return functionAgg;
	}

	public void setFunctionAgg(String functionAgg) {
		this.functionAgg = functionAgg;
	}

	public String getSelected_champ_To_function() {
		return selected_champ_To_function;
	}

	public void setSelected_champ_To_function(String selected_champ_To_function) {
		this.selected_champ_To_function = selected_champ_To_function;
	}

	public boolean isShowRenamefield() {
		return showRenamefield;
	}

	public void setShowRenamefield(boolean showRenamefield) {
		this.showRenamefield = showRenamefield;
	}

	public List<RuleRecGauche> getList_field_function() {
		return list_field_function;
	}

	public void setList_field_function(List<RuleRecGauche> list_field_function) {
		this.list_field_function = list_field_function;
	}

	public RuleRecGauche getField_selection_function() {
		return field_selection_function;
	}

	public void setField_selection_function(RuleRecGauche field_selection_function) {
		this.field_selection_function = field_selection_function;
	}

	public EtlFieldProc getNew_etlfield_proc() {
		return new_etlfield_proc;
	}

	public void setNew_etlfield_proc(EtlFieldProc new_etlfield_proc) {
		this.new_etlfield_proc = new_etlfield_proc;
	}

	public List<EtlFieldProc> getList_etldield_proc() {
		return list_etldield_proc;
	}

	public void setList_etldield_proc(List<EtlFieldProc> list_etldield_proc) {
		this.list_etldield_proc = list_etldield_proc;
	}

	public EtlFieldProc getEfp() {
		return efp;
	}

	public void setEfp(EtlFieldProc efp) {
		this.efp = efp;
	}

	public List<EtlProc> getList_proc() {
		return list_proc;
	}

	public void setList_proc(List<EtlProc> list_proc) {
		this.list_proc = list_proc;
	}

	public List<EtlProcFlow> getListetlprocflow() {
		return Listetlprocflow;
	}

	public void setListetlprocflow(List<EtlProcFlow> listetlprocflow) {
		Listetlprocflow = listetlprocflow;
	}

	public String getSelected_function2() {
		return selected_function2;
	}

	public void setSelected_function2(String selected_function2) {
		this.selected_function2 = selected_function2;
	}

	public List<EtlFieldProc> getList_etldield_proc2() {
		return list_etldield_proc2;
	}

	public void setList_etldield_proc2(List<EtlFieldProc> list_etldield_proc2) {
		this.list_etldield_proc2 = list_etldield_proc2;
	}

}