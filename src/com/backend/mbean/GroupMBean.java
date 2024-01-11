package com.backend.mbean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import com.fraude.entities.Function;
import com.fraude.entities.GuiGroup;
import com.fraude.entities.RepModule;
import com.fraude.entities.RepSubModule;
import com.fraude.interfaces.GuiGroupInterface;
import com.fraude.interfaces.ModuleInterface;

@ManagedBean(name = "Group_managed_bean")
@ViewScoped
public class GroupMBean {

	private GuiGroup guiGroup;

	private List<RepModule> listModules;

	private List<RepSubModule> listSubModules;

	private List<Function> listFunctions;

	private List<GuiGroup> listGroups;

	private TreeNode root;

	private TreeNode[] selectedNodes;

	private GuiGroup selectedGroup;

	@EJB
	private ModuleInterface module_service;

	@EJB
	private GuiGroupInterface group_service;

	@PostConstruct
	public void init() {

		list_groupe = new ArrayList<>();

		list_mod = new ArrayList<>();

		listAllModules = new ArrayList<>();
		listmoduleselected = new ArrayList<>();

		listrepsubmoduleFraudConfiguration = new ArrayList<>();
		listrepsubmoduleFraudDecition = new ArrayList<>();
		listrepsubmoduleFraudWarning = new ArrayList<>();
		listrepsubmoduleUsermanagement = new ArrayList<>();

		listrepsubmoduleFraudConfigurationselected = new ArrayList<>();
		listrepsubmoduleFraudDecitionselected = new ArrayList<>();
		listrepsubmoduleFraudWarningselected = new ArrayList<>();
		listrepsubmoduleUsermanagementselected = new ArrayList<>();

		listAllModules = module_service.getAllModules();

		listmoduleselected = new ArrayList<>();

		guiGroup = new GuiGroup();
		listGroups = new ArrayList<>();
		listGroups = group_service.getAllGroups();
		selectedGroup = new GuiGroup();
		listModules = new ArrayList<>();
		listModules = module_service.getAllModules();

		root = new CheckboxTreeNode(new DocProfile(null, null, null), null);

		for (RepModule m : listModules) {
			System.out.println(m.getModuleName());
			TreeNode mod = new CheckboxTreeNode(new DocProfile(m, m.getModuleName(), "module"), root);
			listSubModules = new ArrayList<>();
			if (m.getList_sub_modules() != null) {
				listSubModules = m.getList_sub_modules();

				for (RepSubModule subm : listSubModules) {
					TreeNode submod = new CheckboxTreeNode(
							new DocProfile(subm, subm.getRepSubModuleName(), "submodule"), mod);
					listFunctions = new ArrayList<>();
					listFunctions = subm.getList_functions();

				}
			}
		}
		
		for (int i = 0; i < listAllModules.size(); i++) {

			for (int j = 0; j < listAllModules.get(i).getList_sub_modules().size(); j++) {
				System.out.println(listAllModules.get(i).getList_sub_modules().get(j).getRepSubModuleName());
			}

		}
	}

	private List<RepModule> listAllModules = new ArrayList<>();
	private List<String> listmoduleselected = new ArrayList<>();

	private List<RepSubModule> listrepsubmoduleFraudConfiguration = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleFraudDecition = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleFraudWarning = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleUsermanagement = new ArrayList<>();

	private boolean submoduleFraudConfiguration = false;
	private boolean submodulemoduleFraudDecition = false;
	private boolean submodulemoduleFraudWarning = false;
	private boolean submodulemoduleUsermanagement = false;

	private List<RepSubModule> listrepsubmoduleFraudConfigurationselected = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleFraudDecitionselected = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleFraudWarningselected = new ArrayList<>();
	private List<RepSubModule> listrepsubmoduleUsermanagementselected = new ArrayList<>();

	public void getsubmoduleforselectedmodule() {
		submoduleFraudConfiguration = false;
		submodulemoduleFraudDecition = false;
		submodulemoduleFraudWarning = false;
		submodulemoduleUsermanagement = false;

		for (int i = 0; i < listmoduleselected.size(); i++) {
			System.out.println("ajax modul select action");
			String s = listmoduleselected.get(i);
			System.out.println(s);
			if (s.contains("FRAUD CONFUGURATION")) {
				System.out.println("true");
				submoduleFraudConfiguration = true;
				for (int j = 0; j < listAllModules.size(); j++) {
					if (listAllModules.get(i).getModuleName().contains("FRAUD CONFUGURATION")) {
						listrepsubmoduleFraudConfiguration = listAllModules.get(i).getList_sub_modules();
						System.out.println(listrepsubmoduleFraudConfiguration.size());
						for (int j2 = 0; j2 < listrepsubmoduleFraudConfiguration.size(); j2++) {
							System.out.println(listrepsubmoduleFraudConfiguration.get(j2).getRepSubModuleName());
							System.out.println(listrepsubmoduleFraudConfiguration.size());
						}
					}
				}
			}
			if (s.contains("FRAUD DECISIONS MANAGEMENT")) {
				submodulemoduleFraudDecition = true;

				for (int j = 0; j < listAllModules.size(); j++) {
					if (listAllModules.get(i).getModuleName().contains("FRAUD DECISIONS MANAGEMENT")) {
						listrepsubmoduleFraudDecition = listAllModules.get(i).getList_sub_modules();
					}
				}
			}
			if (s.contains("FRAUD WARNINIGS")) {
				submodulemoduleFraudWarning = true;

				for (int j = 0; j < listAllModules.size(); j++) {
					if (listAllModules.get(i).getModuleName().contains("FRAUD WARNINIGS")) {
						listrepsubmoduleFraudWarning = listAllModules.get(i).getList_sub_modules();
					}
				}
			}
			if (s.contains("USER MANAGEMENTS")) {
				submodulemoduleUsermanagement = true;
				for (int j = 0; j < listAllModules.size(); j++) {
					if (listAllModules.get(i).getModuleName().contains("USER MANAGEMENTS")) {
						listrepsubmoduleUsermanagement = listAllModules.get(i).getList_sub_modules();
					}
				}
			}

		}

	}

	List<RepModule> list_mod = new ArrayList<>();
	RepModule fraudConf;
	RepModule fraudDecision;
	RepModule fraudwarning;
	RepModule fraudusermanager;
	List<GuiGroup> list_groupe = new ArrayList<>();

	public void displaySelectedMultiple() {
		group_service.addGroup(guiGroup);

		if (!listrepsubmoduleFraudConfigurationselected.isEmpty()) {
			fraudConf = new RepModule();
			fraudConf = listrepsubmoduleFraudConfigurationselected.get(0).getRepModule();
			fraudConf.setList_sub_modules(listrepsubmoduleFraudConfigurationselected);

		}
		if (!listrepsubmoduleFraudDecitionselected.isEmpty()) {
			fraudDecision = new RepModule();
			fraudDecision = listrepsubmoduleFraudDecitionselected.get(0).getRepModule();
			fraudDecision.setList_sub_modules(listrepsubmoduleFraudDecitionselected);
		}
		if (!listrepsubmoduleFraudWarningselected.isEmpty()) {
			fraudwarning = new RepModule();
			fraudwarning = listrepsubmoduleFraudWarningselected.get(0).getRepModule();
			fraudwarning.setList_sub_modules(listrepsubmoduleFraudWarningselected);
		}
		if (!listrepsubmoduleUsermanagementselected.isEmpty()) {
			fraudusermanager = new RepModule();
			fraudusermanager = listrepsubmoduleUsermanagementselected.get(0).getRepModule();
			fraudusermanager.setList_sub_modules(listrepsubmoduleUsermanagementselected);
		}

		List<RepSubModule> listallsubmodules = new ArrayList<>();
		listallsubmodules.addAll(listrepsubmoduleFraudConfigurationselected);
		listallsubmodules.addAll(listrepsubmoduleFraudDecitionselected);
		listallsubmodules.addAll(listrepsubmoduleFraudWarningselected);
		listallsubmodules.addAll(listrepsubmoduleUsermanagementselected);

		guiGroup.setSub_module_groups(listallsubmodules);

		/*
		 * list_groupe.add(guiGroup);
		 * 
		 * fraudConf.setGroup_module(list_groupe);
		 * fraudDecision.setGroup_module(list_groupe);
		 * fraudwarning.setGroup_module(list_groupe);
		 * fraudusermanager.setGroup_module(list_groupe);
		 */

		if (fraudConf != null) {
			list_mod.add(fraudConf);
		}
		if (fraudDecision != null) {
			list_mod.add(fraudDecision);
		}
		if (fraudwarning != null) {
			list_mod.add(fraudwarning);
		}
		if (fraudusermanager != null) {
			list_mod.add(fraudusermanager);
		}

		guiGroup.setModule_groups(list_mod);

		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		guiGroup.setDateCreation(currentTimestamp);
		group_service.updateGroup(guiGroup);
		init();
		guiGroup = new GuiGroup();
	}

	public void updateGroup(TreeNode[] nodes) {
		List<RepModule> list_mod = new ArrayList<>();
		List<RepSubModule> list_submod = new ArrayList<>();
		List<Function> list_func = new ArrayList<>();
		if (nodes != null && nodes.length > 0) {
			for (TreeNode node : nodes) {
				FacesMessage message = null;

				if (((DocProfile) node.getData()).getType().equals("module")) {
					RepModule m = (RepModule) ((DocProfile) node.getData()).getData();
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", m.getModuleName());
					// guiGroup.getModule_groups().add(m);
					list_mod.add(m);
				}
				if (((DocProfile) node.getData()).getType().equals("submodule")) {
					RepSubModule subm = (RepSubModule) ((DocProfile) node.getData()).getData();
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", subm.getRepSubModuleName());
					// guiGroup.getSub_module_groups().add(subm);
					list_submod.add(subm);
				}
				if (((DocProfile) node.getData()).getType().equals("function")) {
					Function f = (Function) ((DocProfile) node.getData()).getData();
					message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", f.getFunctionName());
					// guiGroup.getFunction_groups().add(f);
					list_func.add(f);
				}

				FacesContext.getCurrentInstance().addMessage(null, message);
			}
			selectedGroup.setModule_groups(list_mod);
			selectedGroup.setSub_module_groups(list_submod);
			selectedGroup.setFunction_groups(list_func);
			System.out.println(selectedGroup.getGName());
			group_service.updateGroup(selectedGroup);
			init();
			selectedGroup = new GuiGroup();
		}
	}

	public void preEdit() {
		// GuiGroup g = (GuiGroup) event.getObject();
		// group_service.updateGroup(g);
		// RequestContext context = RequestContext.getCurrentInstance();
		// context.update(":ListGroups");
	}

	public GuiGroup getGuiGroup() {
		return guiGroup;
	}

	public void setGuiGroup(GuiGroup guiGroup) {
		this.guiGroup = guiGroup;
	}

	public List<RepModule> getListModules() {
		return listModules;
	}

	public void setListModules(List<RepModule> listModules) {
		this.listModules = listModules;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<RepSubModule> getListSubModules() {
		return listSubModules;
	}

	public List<Function> getListFunctions() {
		return listFunctions;
	}

	public TreeNode[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setListSubModules(List<RepSubModule> listSubModules) {
		this.listSubModules = listSubModules;
	}

	public void setListFunctions(List<Function> listFunctions) {
		this.listFunctions = listFunctions;
	}

	public void setSelectedNodes(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public List<GuiGroup> getListGroups() {
		return listGroups;
	}

	public void setListGroups(List<GuiGroup> listGroups) {
		this.listGroups = listGroups;
	}

	public GuiGroup getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(GuiGroup selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public List<RepSubModule> getListrepsubmoduleFraudConfiguration() {
		return listrepsubmoduleFraudConfiguration;
	}

	public void setListrepsubmoduleFraudConfiguration(List<RepSubModule> listrepsubmoduleFraudConfiguration) {
		this.listrepsubmoduleFraudConfiguration = listrepsubmoduleFraudConfiguration;
	}

	public List<RepSubModule> getListrepsubmoduleFraudDecition() {
		return listrepsubmoduleFraudDecition;
	}

	public void setListrepsubmoduleFraudDecition(List<RepSubModule> listrepsubmoduleFraudDecition) {
		this.listrepsubmoduleFraudDecition = listrepsubmoduleFraudDecition;
	}

	public List<RepSubModule> getListrepsubmoduleFraudWarning() {
		return listrepsubmoduleFraudWarning;
	}

	public void setListrepsubmoduleFraudWarning(List<RepSubModule> listrepsubmoduleFraudWarning) {
		this.listrepsubmoduleFraudWarning = listrepsubmoduleFraudWarning;
	}

	public List<RepSubModule> getListrepsubmoduleUsermanagement() {
		return listrepsubmoduleUsermanagement;
	}

	public void setListrepsubmoduleUsermanagement(List<RepSubModule> listrepsubmoduleUsermanagement) {
		this.listrepsubmoduleUsermanagement = listrepsubmoduleUsermanagement;
	}

	public List<RepModule> getListAllModules() {
		return listAllModules;
	}

	public void setListAllModules(List<RepModule> listAllModules) {
		this.listAllModules = listAllModules;
	}

	public List<String> getListmoduleselected() {
		return listmoduleselected;
	}

	public void setListmoduleselected(List<String> listmoduleselected) {
		this.listmoduleselected = listmoduleselected;
	}

	public boolean isSubmoduleFraudConfiguration() {
		return submoduleFraudConfiguration;
	}

	public void setSubmoduleFraudConfiguration(boolean submoduleFraudConfiguration) {
		this.submoduleFraudConfiguration = submoduleFraudConfiguration;
	}

	public boolean isSubmodulemoduleFraudDecition() {
		return submodulemoduleFraudDecition;
	}

	public void setSubmodulemoduleFraudDecition(boolean submodulemoduleFraudDecition) {
		this.submodulemoduleFraudDecition = submodulemoduleFraudDecition;
	}

	public boolean isSubmodulemoduleFraudWarning() {
		return submodulemoduleFraudWarning;
	}

	public void setSubmodulemoduleFraudWarning(boolean submodulemoduleFraudWarning) {
		this.submodulemoduleFraudWarning = submodulemoduleFraudWarning;
	}

	public boolean isSubmodulemoduleUsermanagement() {
		return submodulemoduleUsermanagement;
	}

	public void setSubmodulemoduleUsermanagement(boolean submodulemoduleUsermanagement) {
		this.submodulemoduleUsermanagement = submodulemoduleUsermanagement;
	}

	public List<RepSubModule> getListrepsubmoduleFraudConfigurationselected() {
		return listrepsubmoduleFraudConfigurationselected;
	}

	public void setListrepsubmoduleFraudConfigurationselected(
			List<RepSubModule> listrepsubmoduleFraudConfigurationselected) {
		this.listrepsubmoduleFraudConfigurationselected = listrepsubmoduleFraudConfigurationselected;
	}

	public List<RepSubModule> getListrepsubmoduleFraudDecitionselected() {
		return listrepsubmoduleFraudDecitionselected;
	}

	public void setListrepsubmoduleFraudDecitionselected(List<RepSubModule> listrepsubmoduleFraudDecitionselected) {
		this.listrepsubmoduleFraudDecitionselected = listrepsubmoduleFraudDecitionselected;
	}

	public List<RepSubModule> getListrepsubmoduleFraudWarningselected() {
		return listrepsubmoduleFraudWarningselected;
	}

	public void setListrepsubmoduleFraudWarningselected(List<RepSubModule> listrepsubmoduleFraudWarningselected) {
		this.listrepsubmoduleFraudWarningselected = listrepsubmoduleFraudWarningselected;
	}

	public List<RepSubModule> getListrepsubmoduleUsermanagementselected() {
		return listrepsubmoduleUsermanagementselected;
	}

	public void setListrepsubmoduleUsermanagementselected(List<RepSubModule> listrepsubmoduleUsermanagementselected) {
		this.listrepsubmoduleUsermanagementselected = listrepsubmoduleUsermanagementselected;
	}

}
