package com.backend.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.fraude.entities.Function;
import com.fraude.entities.RepModule;
import com.fraude.entities.RepSubModule;
import com.fraude.entities.Report;
import com.fraude.interfaces.FunctionInterface;
import com.fraude.interfaces.ModuleInterface;
import com.fraude.interfaces.SubModuleInterface;


//import org.omnifaces.component.output.OutputLabel;


@ManagedBean
@ViewScoped
public class ReportingMBean {

	private TreeNode root;

	private String show;

	private Doc selectedDocument;

	private TreeNode selectedNode;

	private RepModule module;

	private RepSubModule submodule;

	private Function function;

	private List<RepModule> listOfModules;

	private List<RepSubModule> listSubModules;

	private List<Function> listFunctions;
	
	private List<Report> listReports;

	@EJB
	private ModuleInterface module_service;

	@EJB
	private SubModuleInterface submodule_service;
	
	@EJB 
	private FunctionInterface function_service;

	@PostConstruct
	public void init() {

		module = new RepModule();
		submodule = new RepSubModule();
		function = new Function();

		listOfModules = new ArrayList<>();
		listOfModules = module_service.getAllModules();

		root = new DefaultTreeNode(new Doc(null, null, null, null), null);

		for (RepModule m : listOfModules) {
			TreeNode mod = new DefaultTreeNode(new Doc(m, null, null, m.getModuleName()), root);
			listSubModules = new ArrayList<>();

			if (m.getList_sub_modules() != null) {
				listSubModules = m.getList_sub_modules();

				for (RepSubModule subm : listSubModules) {
					TreeNode submod = new DefaultTreeNode(new Doc(m, subm, null, subm.getRepSubModuleName()), mod);
					listFunctions = new ArrayList<>();
					listFunctions = subm.getList_functions();
					for (Function f : listFunctions) {
						TreeNode func = new DefaultTreeNode(new Doc(m, subm, f, f.getFunctionName()), submod);
						listReports = new ArrayList<>();
						listReports = f.getList_reports();
						for(Report r : listReports){
							TreeNode rep = new DefaultTreeNode(new Doc(m, subm, f, r.getReportName()), func);
						}
					}
				}
			}
		}
	}

	/*
	 * 
	 */
	public void addModule() {
		module_service.addModule(module);
		module = new RepModule();
		init();
	}

	public void onAdd() {
		if (selectedDocument.getSubModule() == null) { // Add submodule
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgAddSubModule').show();");
		} else if (selectedDocument.getSubModule() != null && selectedDocument.getFunction() == null) { // Add
																										// function
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgAddFunction').show();");
		}
	}

	public void onEdit() {

		if (selectedDocument.getSubModule() == null) { // Edit module
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgEditModule').show();");
		} else if (selectedDocument.getSubModule() != null && selectedDocument.getFunction() == null) { // Edit
																										// submodule
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgEditSubModule').show();");
		} else if (selectedDocument.getFunction() != null) { // Edit Function
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dlgEditFunction').show();");
		}
	}

	/*
	 * 
	 */
	public void updateModule() {

		// module.setModuleName(selectedDocument.getModule().getModuleName());
		module_service.updateModule(selectedDocument.getModule());

		init();
	}

	public void addSubModule() {
		submodule.setRepModule(selectedDocument.getModule());
		selectedDocument.getModule().getList_sub_modules().add(submodule);
		System.out.println(
				"+++++ " + selectedDocument.getModule().getModuleName() + " **** " + submodule.getRepSubModuleName()
						+ " ----- " + selectedDocument.getModule().getList_sub_modules().get(0).getRepSubModuleName());
		module_service.updateModule(selectedDocument.getModule());
		submodule = new RepSubModule();

		init();
	}

	public void updateSubModule() {
		submodule_service.updateSubModule(selectedDocument.getSubModule());
		init();
	}

	public void addFunction() {
		function.setRepSubmodule(selectedDocument.getSubModule());
		selectedDocument.getSubModule().getList_functions().add(function);
		submodule_service.updateSubModule(selectedDocument.getSubModule());
		function = new Function();
		init();
	}

	public void updateFunction() {
		function_service.updateFunction(selectedDocument.getFunction());
		init();
	}

	public RepModule getModule() {
		return module;
	}

	public void setModule(RepModule module) {
		this.module = module;
	}

	public List<RepSubModule> getListSubModules() {
		return listSubModules;
	}

	public void setListSubModules(List<RepSubModule> listSubModules) {
		this.listSubModules = listSubModules;
	}

	public List<RepModule> getListOfModules() {
		return listOfModules;
	}

	public void setListOfModules(List<RepModule> listOfModules) {
		this.listOfModules = listOfModules;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Doc getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(Doc selectedDocument) {
		this.selectedDocument = selectedDocument;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public RepSubModule getSubmodule() {
		return submodule;
	}

	public void setSubmodule(RepSubModule submodule) {
		this.submodule = submodule;
	}

	public List<Function> getListFunctions() {
		return listFunctions;
	}

	public void setListFunctions(List<Function> listFunctions) {
		this.listFunctions = listFunctions;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public List<Report> getListReports() {
		return listReports;
	}

	public void setListReports(List<Report> listReports) {
		this.listReports = listReports;
	}

}
