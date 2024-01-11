package com.backend.mbean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

import com.fraude.entities.GuiGroup;
import com.fraude.entities.GuiUser;
import com.fraude.interfaces.GuiGroupInterface;
import com.fraude.interfaces.GuiUserInterface;


@ManagedBean(name="User_managed_bean")
@ViewScoped
public class UserMBean {

	private GuiGroup guiGroup;
	
	private GuiUser guiUser;
	
	private GuiUser selectedRow ;
	
	private List<GuiUser> listUsers;
	
	private List<GuiGroup> listGroups;

	@EJB
	private GuiUserInterface user_service;
	
	@EJB
	private GuiGroupInterface group_service;
	
	private String UPwd;
	
	
	
	
	
	@PostConstruct
	public void init() {
	
		guiUser = new GuiUser();
		listUsers = new ArrayList<>();
		listUsers = user_service.getAllUsers();	
		
		//selectedRow = new GuiUser();
		
		listGroups = new ArrayList<>();
		listGroups = group_service.getAllGroups();
	}
	public void ajaxLoginControl(){
		
		 MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(guiUser.getULogin().getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addUser(){
		
		
		guiUser.setUser_group(guiGroup);
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		guiUser.setDateCreation(currentTimestamp);
		guiUser.setEtat("A");
		 MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(UPwd.getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
			guiUser.setUPwd(myHash);


		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		    
				
		user_service.addUser(guiUser);
		init();
		guiGroup = new GuiGroup();
		guiUser = new GuiUser();
	}
	
	public void onSelectUser(){
		
	}
	public void onEditUser(){
		
		guiUser.setUser_group(guiGroup);
		user_service.updateUser(guiUser);

		init();
		guiGroup = new GuiGroup();
		guiUser = new GuiUser();
		/*RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dlgEditUser').show();");*/
		//selectedRow.setUser_group(guiGroup);
		/*user_service.updateUser(selectedRow);
		//selectedRow = new GuiUser();
		init();*/
	}
	
	public void onDelete(){
		
		guiUser.setEtat("D");
		user_service.updateUser(guiUser);
		
		init();
		guiUser = new GuiUser();
	}
	
	public GuiGroup getGuiGroup() {
		return guiGroup;
	}

	public void setGuiGroup(GuiGroup guiGroup) {
		this.guiGroup = guiGroup;
	}

	
	public List<GuiUser> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<GuiUser> listUsers) {
		this.listUsers = listUsers;
	}

	public GuiUser getGuiUser() {
		return guiUser;
	}

	public void setGuiUser(GuiUser guiUser) {
		this.guiUser = guiUser;
	}



	public List<GuiGroup> getListGroups() {
		return listGroups;
	}



	public void setListGroups(List<GuiGroup> listGroups) {
		this.listGroups = listGroups;
	}

	public GuiUser getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(GuiUser selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getUPwd() {
		return UPwd;
	}

	public void setUPwd(String uPwd) {
		UPwd = uPwd;
	}
}
