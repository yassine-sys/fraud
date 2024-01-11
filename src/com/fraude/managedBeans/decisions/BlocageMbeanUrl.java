package com.fraude.managedBeans.decisions;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.fraude.entities.DecisionFraude;
import com.fraude.entities.Util;
import com.fraude.interfaces.FraudeManagerRemote;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@ManagedBean(name = "fraude_block_url")
@ViewScoped
public class BlocageMbeanUrl {

	private Integer one_num;
	private String choix;
	private String type_lancement;

	private List<Nmro> list_deja_block;
	private List<Nmro> list_block;
	private List<Nmro> list_erreur;

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
	public void init(){
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
//				rows.next();
//				nb++;
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

	public void ValidateBlockOne() throws IOException, JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession("root", "192.168.102.109", 22);
		session.setPassword("Pass1234");
		// It must not be recommended, but if you want to skip host-key check,
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		boolean complete = false;
		boolean etatcomplete = false;

		if (this.getChoix().equals("blocage")) {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("curl \"http://192.168.2.36:1986/?blk=1&mdn=222" + Nmro + "\";");
			channel.setInputStream(null);

			// channel.setOutputStream(System.out);

			// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			// ((ChannelExec)channel).setErrStream(fos);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();
			String result = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					result = result + new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();

			System.out.println("Begin");

			if (result.contains("Failed")) {
				System.out.println("Nmro d�ja coup�");
				complete = true;
				etatcomplete = false;
			} else if (result.contains("Success")) {
				System.out.println("Nmro  coup�");
				complete = true;
				etatcomplete = true;
			}

		} else {
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("curl \"http://192.168.2.36:1986/?blk=0&mdn=222" + Nmro + "\";");
			// ((ChannelExec)channel).setCommand("curl
			// \"http://192.168.2.36:1986/?blk=0&mdn=222"+Nmro+" "+"\";");
			channel.setInputStream(null);

			// channel.setOutputStream(System.out);

			// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			// ((ChannelExec)channel).setErrStream(fos);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();
			String result = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					result = result + new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();

			if (result.contains("Failed")) {
				System.out.println("Nmro d�ja uncoup�");
				complete = true;
				etatcomplete = false;
			} else if (result.contains("Success")) {
				System.out.println("Nmro  unbloqu�");
				complete = true;
				etatcomplete = true;
			}

			/////////////////////////////////////////////
			// DecisionFraude decision;
			// decision = new DecisionFraude();
			// System.out.println("else");
			// Calendar calendar = Calendar.getInstance();
			// Timestamp currentTimestamp = new
			// java.sql.Timestamp(calendar.getTime().getTime());
			// decision.setDateModif(currentTimestamp);
			// decision.setDateDecision(currentTimestamp);
			// decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
			// //decision.setMsisdn("222"+Nmro);
			// decision.setMsisdn("222"+Nmro);
			// System.out.println("222"+Nmro);
			// //decision.setDecision("D");
			// decision.setDecision("W");
			// decision.setIdRegle(0);
			// //decision_service.addDecision(decision);
			// decision_service.updateDecision(decision);
			// System.out.println(decision);

			////////////////////////////////////////////

		}

		DecisionFraude decision;

		List<DecisionFraude> decisions = new ArrayList<>();
		if (decision_service.FindDecisionByMsisdn("222" + Nmro).size() > 0) {
			decisions = decision_service.FindDecisionByMsisdn("222" + Nmro);
			for (DecisionFraude dec : decisions) {
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				dec.setDateModif(currentTimestamp);
				//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
				dec.setMsisdn("222" + Nmro);
				// dec.setDecision("D");
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
			//decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
			decision.setMsisdn("222" + Nmro);
			// decision.setDecision("D");
			decision.setDecision("W");
			decision.setIdRegle(0);
			decision_service.addDecision(decision);
			// decision_service.updateDecision(decision);

		}
		FacesContext.getCurrentInstance().addMessage("@form",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Action sur  n�" + Nmro + "  effectu� avec succes", Nmro));

	}

	public void ValiderBlock() throws IOException, JSchException {

		for (int j = 0; j < lstNum.size(); j++) {
			JSch jsch = new JSch();
			Session session = jsch.getSession("root", "192.168.102.109", 22);
			session.setPassword("Pass1234");
			// It must not be recommended, but if you want to skip host-key
			// check,
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel)
					.setCommand("curl \"http://192.168.2.36:1986/?blk=1&mdn=222" + lstNum.get(j).getNum() + "\";");
			channel.setInputStream(null);

			// channel.setOutputStream(System.out);

			// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			// ((ChannelExec)channel).setErrStream(fos);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();
			String result = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					result = result + new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			if (result.contains("Failed")) {
				System.out.println("Nmro d�ja bloqu�");
			} else if (result.contains("Success")) {
				System.out.println("Nmro  bloqu�");

			}

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("222" + lstNum.get(j).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("222" + lstNum.get(j).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("222" + lstNum.get(j).getNum());
					dec.setDecision("D");
					dec.setIdRegle(0);

					decision_service.updateDecision(dec);
					Nmro n = new Nmro();
					n.setIndex(lstNum.get(j).getIndex());
					n.setNum(lstNum.get(j).getNum());
					list_white_liste.add(n);
				}

			} else {
				decision = new DecisionFraude();
				System.out.println("else");
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				decision.setDateModif(currentTimestamp);
				decision.setDateDecision(currentTimestamp);
				//decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
				decision.setMsisdn("222" + lstNum.get(j).getNum());
				decision.setDecision("D");
				decision.setIdRegle(0);
				decision_service.addDecision(decision);
				Nmro n = new Nmro();
				n.setIndex(lstNum.get(j).getIndex());
				n.setNum(lstNum.get(j).getNum());
				list_white_liste.add(n);
			}

		}

		lstNum = new ArrayList<>();
		showdata = true;
		FacesContext.getCurrentInstance().addMessage("@form",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros Bloqu�s Avec succes", "Succes"));

		completed = false;
	}

	public void DeBlockFile() throws IOException, JSchException {
		for (int j = 0; j < lstNum.size(); j++) {
			JSch jsch = new JSch();
			Session session = jsch.getSession("root", "192.168.102.109", 22);
			session.setPassword("Pass1234");
			// It must not be recommended, but if you want to skip host-key
			// check,
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel)
					.setCommand("curl \"http://192.168.2.36:1986/?blk=0&mdn=222" + lstNum.get(j).getNum() + "\";");
			channel.setInputStream(null);

			// channel.setOutputStream(System.out);

			// FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			// ((ChannelExec)channel).setErrStream(fos);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();
			String result = "";
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
					result = result + new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
			if (result.contains("Failed")) {
				System.out.println("Nmro d�ja unbloqu�");
			} else if (result.contains("Success")) {
				System.out.println("Nmro  unbloqu�");

			}

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("222" + lstNum.get(j).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("222" + lstNum.get(j).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("222" + lstNum.get(j).getNum());
					dec.setDecision("W");
					dec.setIdRegle(0);
					decision_service.updateDecision(dec);
					Nmro n = new Nmro();
					n.setIndex(lstNum.get(j).getIndex());
					n.setNum(lstNum.get(j).getNum());
					list_white_liste.add(n);
				}

			} else {
				decision = new DecisionFraude();
				System.out.println("else");
				Calendar calendar = Calendar.getInstance();
				Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
				decision.setDateModif(currentTimestamp);
				decision.setDateDecision(currentTimestamp);
				//decision.setNomUtilisateur(Util.getUser().getNomUtilisateur());
				decision.setMsisdn("222" + lstNum.get(j).getNum());
				decision.setDecision("W");
				decision.setIdRegle(0);
				decision_service.addDecision(decision);
				Nmro n = new Nmro();
				n.setIndex(lstNum.get(j).getIndex());
				n.setNum(lstNum.get(j).getNum());
				list_white_liste.add(n);
			}

		}

		lstNum = new ArrayList<>();
		FacesContext.getCurrentInstance().addMessage("@form",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros  debloqu�s", "Success"));

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

}
