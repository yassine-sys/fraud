package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the gui_groups database table.
 * 
 */
@Entity
@Table(name = "gui_groups", schema = "tableref")
@NamedQuery(name = "GuiGroup.findAll", query = "SELECT g FROM GuiGroup g")
public class GuiGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gui_groups_g_id_seq")
	@SequenceGenerator(name="gui_groups_g_id_seq", sequenceName = "tableref.gui_groups_g_id_seq")
	@Column(name = "g_id")
	private Long gId;

	/*
	 * @ManyToMany(cascade=CascadeType.MERGE)
	 * 
	 * @JoinTable(name="module_groups",schema="tableref")
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE) private List<GuiModulesGroup>
	 * module_groups;
	 */

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "module_groups")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RepModule> module_groups;

	@OneToMany(mappedBy = "user_group", cascade = CascadeType.ALL)
	private List<GuiUser> groupUsers;

	
	public Long getgId() {
		return gId;
	}
	public void setgId(Long gId) {
		this.gId = gId;
	}
	
	/*
	 * @OneToMany(mappedBy="group",cascade=CascadeType.ALL,orphanRemoval=true)
	 * 
	 * @LazyCollection(LazyCollectionOption.FALSE) private
	 * List<SubGroupTabSelected> liste_sub_groups;
	 * 
	 * public List<SubGroupTabSelected> getListe_sub_groups() { return
	 * liste_sub_groups; }
	 * 
	 * public void setListe_sub_groups(List<SubGroupTabSelected>
	 * liste_sub_groups) { this.liste_sub_groups = liste_sub_groups; }
	 */

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "submodule_groups")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RepSubModule> sub_module_groups;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "function_groups")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Function> function_groups;

	/*
	 * public List<GuiModulesGroup> getTab_groups() { return module_groups; }
	 * public void setTab_groups(List<GuiModulesGroup> module_groups) {
	 * this.module_groups = module_groups; }
	 */
	
	@Column(name = "date_creation")
	private Timestamp dateCreation;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	@Column(name = "g_description")
	private String gDescription;

	public List<RepModule> getModule_groups() {
		return module_groups;
	}

	public void setModule_groups(List<RepModule> module_groups) {
		this.module_groups = module_groups;
	}

	@Column(name = "g_name")
	private String gName;

	@Column(name = "id_createur")
	private Long idCreateur;

	@Column(name = "nom_utilisateur")
	private String nomUtilisateur;

	private String etat;

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public GuiGroup() {
	}

	public Long getGId() {
		return this.gId;
	}

	public void setGId(Long gId) {
		this.gId = gId;
	}

	public Timestamp getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public String getGDescription() {
		return this.gDescription;
	}

	public void setGDescription(String gDescription) {
		this.gDescription = gDescription;
	}

	public String getGName() {
		return this.gName;
	}

	public void setGName(String gName) {
		this.gName = gName;
	}

	public Long getIdCreateur() {
		return this.idCreateur;
	}

	public void setIdCreateur(Long idCreateur) {
		this.idCreateur = idCreateur;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public List<RepSubModule> getSub_module_groups() {
		return sub_module_groups;
	}

	public void setSub_module_groups(List<RepSubModule> sub_module_groups) {
		this.sub_module_groups = sub_module_groups;
	}

	public List<Function> getFunction_groups() {
		return function_groups;
	}

	public void setFunction_groups(List<Function> function_groups) {
		this.function_groups = function_groups;
	}

	public List<GuiUser> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(List<GuiUser> groupUsers) {
		this.groupUsers = groupUsers;
	}
}