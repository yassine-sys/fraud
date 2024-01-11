package com.fraude.managedBeans.decisions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.fraude.entities.Util;
import com.fraude.managedBeans.decisions.BlocageMbeanUrl.Nmro;
import com.fraude.entities.DecisionFraude;
import com.fraude.interfaces.FraudeManagerRemote;

@ManagedBean(name = "fraude_block")
@ViewScoped
public class BlocageOffnetMbean implements Serializable {

	private static final long serialVersionUID = -5185502145262813875L;
	private Integer one_num;
	private String choix;
	private String type_lancement;

	private List<Nmro> list_deja_block;
	private List<Nmro> list_block;
	private List<Nmro> list_erreur;

	public void execCommande() {
		System.out.println("numero "+Nmro);
		String getfirstTwonumber = "";
		getfirstTwonumber = Nmro.substring(0, 2);
		if (getfirstTwonumber.contains("85") || getfirstTwonumber.contains("86") || getfirstTwonumber.contains("87")
				|| getfirstTwonumber.contains("88") || getfirstTwonumber.contains("89")
				|| getfirstTwonumber.contains("95") || getfirstTwonumber.contains("96")
				|| getfirstTwonumber.contains("97") || getfirstTwonumber.contains("98")
				|| getfirstTwonumber.contains("99") || getfirstTwonumber.substring(0, 1).toString().contains("6")) {

			try {
				Process proc;
				if (this.getChoix().equals("blocage")) {
					proc = Runtime.getRuntime().exec(new String[] { "bash", "-c", ". /home/webuser/.profile;/mediation/bin/blkonnet  "+ Nmro});
				} else {
					proc = Runtime.getRuntime().exec(new String[] { "bash", "-c", ". /home/webuser/.profile;/mediation/bin/dblkonnet  " + Nmro });
				}

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				String s = null;
				boolean add = false;
				// read the output from the command
				FacesContext context = FacesContext.getCurrentInstance();
				System.out.println("Here is the standard output of the command:\n");

				while ((s = stdInput.readLine()) != null) {
					add = true;
					System.out.println(s);

				}
				if (add) {
					DecisionFraude df = new DecisionFraude();
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					df.setDateModif(currentTimestamp);
					df.setNomUtilisateur(Util.getUser().getUName());
					df.setMsisdn("223" + Nmro);

					if (this.getChoix().equals("blocage")) {
						df.setDecision("D");
					} else {
						df.setDecision("w");
					}
					df.setIdRegle(0);
					decision_service.updateDecision(df);
					context.addMessage(null, new FacesMessage("Successful", "numero bloqué avec succès "));
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				String m="";
				while ((s = stdError.readLine()) != null) {

					  m+=s;
					System.out.println(s);
				}
				if(!m.isEmpty()){
					context.addMessage("somekey", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (getfirstTwonumber.contains("80") || getfirstTwonumber.contains("81") || getfirstTwonumber.contains("82")
				|| getfirstTwonumber.contains("83") || getfirstTwonumber.contains("84")
				|| getfirstTwonumber.contains("90") || getfirstTwonumber.contains("91")
				|| getfirstTwonumber.contains("92") || getfirstTwonumber.contains("93")
				|| getfirstTwonumber.substring(0, 1).toString().contains("7")) {
			/*
			 * try { Process proc = Runtime.getRuntime() .exec(new String[] {
			 * "bash", "-c", "/mediation/bin/blkoffnetmsc1 " + Nmro });
			 * 
			 * BufferedReader stdInput = new BufferedReader(new
			 * InputStreamReader(proc.getInputStream()));
			 * 
			 * BufferedReader stdError = new BufferedReader(new
			 * InputStreamReader(proc.getErrorStream())); String s = null;
			 * 
			 * // read the output from the command System.out.println(
			 * "Here is the standard output of the command:\n"); while ((s =
			 * stdInput.readLine()) != null) { System.out.println(s);
			 * FacesContext context = FacesContext.getCurrentInstance();
			 * 
			 * context.addMessage(null, new FacesMessage("Successful",
			 * "OutPut :" + s)); }
			 * 
			 * // read any errors from the attempted command System.out.println(
			 * "Here is the standard error of the command (if any):\n"); while
			 * ((s = stdError.readLine()) != null) { FacesContext context =
			 * FacesContext.getCurrentInstance();
			 * 
			 * context.addMessage(null, new FacesMessage("Successful",
			 * "erreur :" + s)); System.out.println(s); }
			 * 
			 * } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}
		if (getfirstTwonumber.substring(0, 1).toString().contains("5")) {
			
			try {
				Process proc;
				if (this.getChoix().equals("blocage")) {

					proc = Runtime.getRuntime().exec(new String[] { "bash", "-c", ". /home/webuser/.profile;/mediation/bin/blkoffnetmsc1 " + Nmro });
					
				} else {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", ". /home/webuser/.profile;/mediation/bin/dblkoffnetmsc1 " + Nmro });
				}

				BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				String s = null;
				boolean add = false;
				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				FacesContext context = FacesContext.getCurrentInstance();
				while ((s = stdInput.readLine()) != null) {
					add = true;
					System.out.println(s);

				}
				if (add) {
					DecisionFraude df = new DecisionFraude();
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					df.setDateModif(currentTimestamp);
					df.setNomUtilisateur(Util.getUser().getUName());
					df.setMsisdn("223" + Nmro);

					if (this.getChoix().equals("blocage")) {
						df.setDecision("D");
					} else {
						df.setDecision("w");
					}
					df.setIdRegle(0);
					decision_service.updateDecision(df);
					context.addMessage(null, new FacesMessage("Successful", "numero bloqué avec succès "));
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				String m="";
				while ((s = stdError.readLine()) != null) {

					  m+=s;
					System.out.println(s);
				}
				if(!m.isEmpty()){
					context.addMessage("somekey", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<Nmro> getList_block() {
		return list_block;
	}

	public void setList_block(List<Nmro> list_block) {
		this.list_block = list_block;
	}

	public void setList_deja_block(List<Nmro> list_deja_block) {
		this.list_deja_block = list_deja_block;
	}

	public List<Nmro> getlstNum() {
		return lstNum;
	}

	public void setlstNum(List<Nmro> lstNum) {
		this.lstNum = lstNum;
	}

	public String getType_lancement() {
		return type_lancement;
	}

	public void setType_lancement(String type_lancement) {
		this.type_lancement = type_lancement;
	}

	private String Nmro;

	public String getNmro() {
		return Nmro;
	}

	public void setNmro(String Nmro) {
		this.Nmro = Nmro;
	}

	private List<Nmro> lstNum;

	public List<Nmro> getLstNum() {
		return lstNum;
	}

	public void setLstNum(List<Nmro> lstNum) {
		this.lstNum = lstNum;
	}

	public Integer getOne_num() {
		return one_num;
	}

	public void setOne_num(Integer one_num) {
		this.one_num = one_num;
	}

	public String getChoix() {
		return choix;
	}

	private boolean manuel = true;
	private boolean parFichier = false;

	public boolean isParFichier() {
		return parFichier;
	}

	public void setParFichier(boolean parFichier) {
		this.parFichier = parFichier;
	}

	public boolean isManuel() {
		return manuel;
	}

	public void setManuel(boolean manuel) {
		this.manuel = manuel;
	}

	@PostConstruct
	public void init() {
		lstNum = new ArrayList<Nmro>();

	}

	private UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void changeType() {
		if (type_lancement.equals("manuel")) {
			manuel = true;
			parFichier = false;
		} else {
			parFichier = true;
			manuel = false;
		}
	}

	private boolean completed = false;

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

//	public void NmroFromFichier(FileUploadEvent event) throws IOException, InvalidFormatException {
//		file = event.getFile();
//		if (file != null) {
//			InputStream streamFile = file.getInputstream();
//			Workbook workbook = WorkbookFactory.create(streamFile);
//			Sheet sheet = workbook.getSheetAt(0);
//			Iterator rows = sheet.rowIterator();
//			int nb = 0;
//			while (nb <= sheet.getLastRowNum() && rows.hasNext()) {
//				Row row = sheet.getRow(nb);
//				Cell cell = row.getCell(0);
//				switch (cell.getCellType()) {
//				case Cell.CELL_TYPE_NUMERIC:
//
//					BigDecimal bg = new BigDecimal(cell.getNumericCellValue());
//					Nmro n = new Nmro();
//					n.setIndex(nb);
//					n.setNum(bg + "");
//					lstNum.add(n);
//
//					System.out.println(cell.getNumericCellValue() + "");
//					break;
//				case Cell.CELL_TYPE_STRING:
//					Nmro n1 = new Nmro();
//					n1.setIndex(nb);
//					n1.setNum(cell.getStringCellValue());
//					lstNum.add(n1);
//					System.out.println(cell.getStringCellValue());
//					break;
//
//				}
//
//				rows.next();
//				nb++;
//
//			}
//			System.out.println(lstNum.size());
//
//			completed = true;
//
//		}
//	}

	private int nb_fait = 0;

	public int getNb_fait() {
		return nb_fait;
	}

	public void setNb_fait(int nb_fait) {
		this.nb_fait = nb_fait;
	}

	private List<Nmro> list_deblocage = new ArrayList<>();

	public List<Nmro> getList_deblocage() {
		return list_deblocage;
	}

	public void setList_deblocage(List<Nmro> list_deblocage) {
		this.list_deblocage = list_deblocage;
	}

	private String num_encours;

	public String getNum_encours() {
		return num_encours;
	}

	public void setNum_encours(String num_encours) {
		this.num_encours = num_encours;
	}

	private List<Nmro> list_white_liste = new ArrayList<>();

	public List<Nmro> getList_white_liste() {
		return list_white_liste;
	}

	public void setList_white_liste(List<Nmro> list_white_liste) {
		this.list_white_liste = list_white_liste;
	}

	private boolean showdata = false;

	public boolean isShowdata() {
		return showdata;
	}

	public void setShowdata(boolean showdata) {
		this.showdata = showdata;
	}

	@EJB
	FraudeManagerRemote decision_service;

	public void ValidateBlockOne() throws IOException {
		try {
			boolean complete = false;
			boolean etatcomplete = false;
			boolean success = false;
			blkoffnet telnet = new blkoffnet("myserver", "userId", "Password");
			if (this.getChoix().equals("blocage")) {

				telnet.sendCommand("SET BUREAU:ID=20;");
				if (telnet.sendCommandblk(Nmro)) {
					success = true;
				}

				DecisionFraude decision;

				List<DecisionFraude> decisions = new ArrayList<>();
				if (decision_service.FindDecisionByMsisdn("222" + Nmro).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("222" + Nmro);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						// dec.setNomUtilisater(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("222" + Nmro);
						dec.setDecision("D");
						dec.setIdRegle(0);

						decision_service.updateDecision(dec);

					}

				} else {
					decision = new DecisionFraude();
					System.out.println("else");
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					decision.setDateModif(currentTimestamp);
					decision.setDateDecision(currentTimestamp);
					// decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					decision.setMsisdn("222" + Nmro + " ");
					decision.setDecision("D");
					decision.setIdRegle(0);
					decision_service.addDecision(decision);

				}
			} else {
				telnet.sendCommand("SET BUREAU:ID=20;");
				if (telnet.senddeblkcmd(Nmro)) {
					success = true;
				}
				DecisionFraude decision;

				List<DecisionFraude> decisions = new ArrayList<>();
				if (decision_service.FindDecisionByMsisdn("222" + Nmro).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("222" + Nmro);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("222" + Nmro);
						dec.setDecision("W");
						dec.setIdRegle(0);

						decision_service.updateDecision(dec);

					}

				} else {
					decision = new DecisionFraude();
					System.out.println("else");
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					decision.setDateModif(currentTimestamp);
					decision.setDateDecision(currentTimestamp);
					// decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					decision.setMsisdn("222" + Nmro + " ");
					decision.setDecision("W");
					decision.setIdRegle(0);
					decision_service.addDecision(decision);

				}

			}
			telnet.sendCommandSYN();
			complete = true;
			etatcomplete = true;

			if (success) {
				FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Action sur  nï¿½" + Nmro + "  effectuï¿½ avec succes", Nmro));

			} else {
				FacesContext.getCurrentInstance().addMessage("@form",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error With  nï¿½" + Nmro + "  ", Nmro));

			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error With  nï¿½" + Nmro + "  ", "Please Try Again"));

		}

	}

	public void ValiderBlock() throws IOException, JSchException {

		blkoffnet telnet = new blkoffnet("myserver", "userId", "Password");
		for (int i = 0; i < lstNum.size(); i++) {

			telnet.sendCommand("SET BUREAU:ID=20;");
			telnet.sendCommandblk(lstNum.get(i).getNum());

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("222" + lstNum.get(i).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("222" + lstNum.get(i).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("222" + lstNum.get(i).getNum());
					dec.setDecision("W");
					dec.setIdRegle(0);

					decision_service.updateDecision(dec);
					Nmro n = new Nmro();
					n.setIndex(lstNum.get(i).getIndex());
					n.setNum(lstNum.get(i).getNum());
					list_white_liste.add(n);
				}

			} else {
				decision = new DecisionFraude();
				System.out.println("else");
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				decision.setDateModif(currentTimestamp);
				decision.setDateDecision(currentTimestamp);
				// decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
				decision.setMsisdn("222" + lstNum.get(i).getNum());
				decision.setDecision("W");
				decision.setIdRegle(0);
				decision_service.addDecision(decision);
				Nmro n = new Nmro();
				n.setIndex(lstNum.get(i).getIndex());
				n.setNum(lstNum.get(i).getNum());
				list_white_liste.add(n);
			}

		}

		telnet.sendCommandSYN();
		lstNum = new ArrayList<>();
		showdata = true;
		FacesContext.getCurrentInstance().addMessage("@form",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros Bloquï¿½s Avec succes", "Succes"));

		completed = false;
	}

	public void DeBlockFile() throws IOException, JSchException {
		blkoffnet telnet = new blkoffnet("myserver", "userId", "Password");
		for (int i = 0; i < lstNum.size(); i++) {

			telnet.sendCommand("SET BUREAU:ID=20;");
			telnet.senddeblkcmd(lstNum.get(i).getNum());

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("222" + lstNum.get(i).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("222" + lstNum.get(i).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("222" + lstNum.get(i).getNum());
					dec.setDecision("W");
					dec.setIdRegle(0);
					decision_service.updateDecision(dec);
					Nmro n = new Nmro();
					n.setIndex(lstNum.get(i).getIndex());
					n.setNum(lstNum.get(i).getNum());
					list_white_liste.add(n);
				}

			} else {
				decision = new DecisionFraude();
				System.out.println("else");
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				decision.setDateModif(currentTimestamp);
				decision.setDateDecision(currentTimestamp);
				// decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
				decision.setMsisdn("222" + lstNum.get(i).getNum());
				decision.setDecision("W");
				decision.setIdRegle(0);
				decision_service.addDecision(decision);
				Nmro n = new Nmro();
				n.setIndex(lstNum.get(i).getIndex());
				n.setNum(lstNum.get(i).getNum());
				list_white_liste.add(n);
			}

			telnet.sendCommandSYN();

		}

		lstNum = new ArrayList<>();
		FacesContext.getCurrentInstance().addMessage("@form",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros  debloquï¿½s", "Success"));

		completed = false;
	}

	public class Nmro {
		private int index;
		private String num;

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

	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = ">";
	private String host = "129.20.129.1";
	private int port = 23;
	private String login = "offnetsimbox";
	private String pwd = "chinguitel2016";

}
