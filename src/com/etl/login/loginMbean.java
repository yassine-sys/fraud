package com.etl.login;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.fraude.entities.GuiGroup;
import com.fraude.entities.GuiUser;
import com.fraude.entities.RepModule;
import com.fraude.entities.RepSubModule;
import com.fraude.interfaces.UserLoginInterface;

@ManagedBean(name = "loginbean")
@SessionScoped
public class loginMbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5765352152292295535L;

	private String login;
	private String passwd;
	private String myHash = "";
	private GuiUser guser;
	private String nameuser;

	@EJB
	private UserLoginInterface userRemote;

	@PostConstruct
	public void init() {
		guser = new GuiUser();
		listmodule = new ArrayList<>();
	}

	boolean loggedIn = false;
	private GuiGroup group;

	private List<RepModule> listmodule = new ArrayList<>();
	private List<RepSubModule> listsubmodule = new ArrayList<>();

	public String Dologin()
			throws IOException, AttributeNotFoundException, InstanceNotFoundException, MalformedObjectNameException,
			MBeanException, ReflectionException, InterruptedException, NoSuchAlgorithmException {
		setGroup(new GuiGroup());
		String navigateTo = null;
		myHash = "";
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md.update(passwd.getBytes());
		byte[] digest = md.digest();
		myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		guser = userRemote.getconnectuser(login, myHash);

		if (guser != null) {

			loggedIn = true;

			HttpSession session = Util.getSession();
			session.setAttribute("user.account", guser);
			session.setAttribute("login.bean", this);
			session.setAttribute("username", guser.getNomUtilisateur());

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/welcome.jsf");

			System.out.println(Util.getUser().getULogin());

			listmodule = Util.getUser().getUser_group().getModule_groups();

			listsubmodule = Util.getUser().getUser_group().getSub_module_groups();
			for (int i = 0; i < listmodule.size(); i++) {
				System.out.println(listmodule.get(i).getModuleName());
				List<RepSubModule> lsm = new ArrayList<>();
				for (int j = 0; j < listsubmodule.size(); j++) {
					System.out.println(listsubmodule.get(j).getRepSubModuleName());

					if (listsubmodule.get(j).getRepModule().equals(listmodule.get(i))) {
						lsm.add(listsubmodule.get(j));
					}
				}
				listmodule.get(i).setList_sub_modules(lsm);
			}

			System.out.println(listmodule.size());

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, " Incorrect login or password ", null));

		}

		return navigateTo;

	}

	public void DoLogout()
			throws IOException, AttributeNotFoundException, InstanceNotFoundException, MalformedObjectNameException,
			MBeanException, ReflectionException, InterruptedException, NoSuchAlgorithmException {
		HttpSession session = Util.getSession();
		session.invalidate();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/login.jsf");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public GuiUser getGuser() {
		return guser;
	}

	public void setGuser(GuiUser guser) {
		this.guser = guser;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getNameuser() {
		return nameuser;
	}

	public void setNameuser(String nameuser) {
		this.nameuser = nameuser;
	}

	public GuiGroup getGroup() {
		return group;
	}

	public void setGroup(GuiGroup group) {
		this.group = group;
	}

	public List<RepModule> getListmodule() {
		return listmodule;
	}

	public void setListmodule(List<RepModule> listmodule) {
		this.listmodule = listmodule;
	}

	public List<RepSubModule> getListsubmodule() {
		return listsubmodule;
	}

	public void setListsubmodule(List<RepSubModule> listsubmodule) {
		this.listsubmodule = listsubmodule;
	}
	private String emdpc = "";
	private String nmdp = "";
	private String nmdpconfirm = "";
	private String emdps;

	public void changerpass() throws NoSuchAlgorithmException {
		emdpc = Util.getUser().getUPwd();
		String login = Util.getUser().getULogin();
		String navigateTo = null;
		myHash = "";
		MessageDigest md;
		md = MessageDigest.getInstance("MD5");
		md.update(emdps.getBytes());
		byte[] digest = md.digest();
		myHash = DatatypeConverter.printHexBinary(digest);

		if (myHash.contains(emdpc) && (nmdp.contains(nmdpconfirm))) {
			System.out.println("entient mdp et nmdp correcte ");
			try {
				GuiUser found = userRemote.getconnectuser(login, emdpc);
				if (found != null) {
					String myHash = "";
					MessageDigest mdn;
					mdn = MessageDigest.getInstance("MD5");
					mdn.update(nmdp.getBytes());
					byte[] digestn = mdn.digest();
					myHash = DatatypeConverter.printHexBinary(digestn);
					found.setUPwd(myHash);
					userRemote.updateUser(found);
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null,
							new FacesMessage("utilisateur votre mot de passe a été changer avec succes", " "));
					DoLogout();
				} else if (found == null) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null,
							new FacesMessage("vous devez saisire entient mot de passe correcte ", " "));
				}

			} catch (Exception e) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("vous devez saisire entient mot de passe correcte ", " "));
				System.out.println("problem change passe word ");
			}
		}
		emdpc = "";
		emdps = "";
		nmdp = "";

	}
	public void change_password() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + "/change.jsf");
	}
	public String getNmdpconfirm() {
		return nmdpconfirm;
	}

	public void setNmdpconfirm(String nmdpconfirm) {
		this.nmdpconfirm = nmdpconfirm;
	}

	public String getEmdpc() {
		return emdpc;
	}

	public void setEmdpc(String emdpc) {
		this.emdpc = emdpc;
	}

	public String getNmdp() {
		return nmdp;
	}

	public void setNmdp(String nmdp) {
		this.nmdp = nmdp;
	}

	public String getEmdps() {
		return emdps;
	}

	public void setEmdps(String emdps) {
		this.emdps = emdps;
	}
}
