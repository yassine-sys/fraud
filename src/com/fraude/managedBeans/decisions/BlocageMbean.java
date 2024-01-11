package com.fraude.managedBeans.decisions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import com.fraude.entities.Util;
import com.fraude.managedBeans.decisions.BlocageMbeanUrl.Nmro;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.TblLog;
import com.fraude.interfaces.FraudeManagerRemote;

@ManagedBean(name = "fraude_block_offnet")
@ViewScoped
@RequestScoped
public class BlocageMbean implements Serializable {

	private static final long serialVersionUID = -5185502145262813875L;
	private Integer one_num;
	private String choix;
	private String choix2;
	private String type_lancement;

	private List<Nmro> list_deja_block;
	private List<Nmro> list_block;
	private List<Nmro> list_erreur;
	private List<Nmro> tabNum;
	private List<String> sdtinputs;

	public List<String> getSdtinputs() {
		return sdtinputs;
	}

	public void setSdtinputs(List<String> sdtinputs) {
		this.sdtinputs = sdtinputs;
	}

	public List<Nmro> getTabNum() {
		return tabNum;
	}

	public void setTabNum(List<Nmro> tabNum) {
		this.tabNum = tabNum;
	}

	
//	public void handleFileUpload() {
//		if (uploadedFile != null) {
//			try {
//				// Define the directory where you want to save the uploaded file
//				String uploadDirectory = "/mediation/webuser/fileUpload";
//
//				// Get the input stream from the uploaded file
//				InputStream input = uploadedFile.getInputStream();
//
//				// Generate a unique file name (you can adjust as needed)
//				String fileName = "uploaded_file_" + System.currentTimeMillis() + ".txt";
//
//				// Create the target path
//				Path filePath = Files.createTempFile(uploadDirectory, fileName);
//
//				// Copy the uploaded file to the target directory
//				Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
//
//				this.file = new File(filePath.toString());
//				System.out.println("path is : " + filePath);
//				System.out.println("file name is : " + this.file.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("fileuploaded");
//	}
//	public void handleFileUpload(FileUploadEvent event) {
//		try {
//			InputStream inputFile = event.getFile().getInputStream();
//			Path folder = Paths.get("/mediation/webuser/fileUpload/");
//			String filename = FilenameUtils.getBaseName(event.getFile().getFileName());
//			String extension = FilenameUtils.getExtension(event.getFile().getFileName());
//			Path filePath = Files.createTempFile(folder, filename + "-", "." + extension);
//
//			Files.copy(inputFile, filePath, StandardCopyOption.REPLACE_EXISTING);
//			this.file = new File(filePath.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
//		FacesContext.getCurrentInstance().addMessage(null, msg);
//	}

	private String typeBlocage = "";

	public void execCommande() {
		System.out.println("numero " + Nmro);
		String getfirstTwonumber = "";
		// getfirstTwonumber = Nmro.substring(0, 1);
		FacesContext context = FacesContext.getCurrentInstance();

		Process proc = null;
		// if (getfirstTwonumber.contains("1")) {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<BlocageMbean.Nmro>();
		textofnum.trim();
		textofnum.replaceAll("\\s", "");

		if (textofnum.contains(",") || textofnum.contains(" ") || textofnum.contains("\n")) {

			List<String> listnumber = Arrays.asList(textofnum.split("[,\\s\\n]+"));

			System.out.println("Blocage !");

			for (int i = 0; i < listnumber.size(); i++) {
				Nmro = listnumber.get(i).toString();
				Nmro nmro = new Nmro();
				try {
					String typeB = null;
					if (this.getChoix().equals("blocage")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blkonnet  " + Nmro });
						proc.waitFor();
						typeB = "blocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						TblLog log = new TblLog();
						log.setAction("blocage num : " + Nmro);
						log.setUsername(Util.getUser().getULogin());
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);

					} else if (this.getChoix().equals("deblocage")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblkonnet  " + Nmro });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						TblLog log = new TblLog();
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						log.setAction("deblocage num : " + Nmro);
						log.setUsername(Util.getUser().getULogin());
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					} else if (this.getChoix().equals("WhiteList")) {

						DecisionFraude df = new DecisionFraude();
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						df.setDateModif(currentTimestamp);
						df.setNomUtilisateur(Util.getUser().getUName());
						df.setMsisdn(numberprefix + Nmro);
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						df.setDecision("W");
						df.setIdRegle(0);
						decision_service.addDecision(df);
						TblLog log = new TblLog();
						log.setAction(" WhiteList : " + Nmro);
						log.setUsername(Util.getUser().getULogin());
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
						context.addMessage(null, new FacesMessage("Successful", "Number has been added to whiteList "));
					} else if (this.getChoix().equals("deblocageSMS")) {
						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + Nmro });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);
						TblLog log = new TblLog();
						log.setAction("deblocage num : " + Nmro);
						log.setUsername(Util.getUser().getULogin());
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					} else if (this.getChoix().equals("blocageSMS")) {
						proc = Runtime.getRuntime().exec(
								new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + "  " + Nmro });
						proc.waitFor();
						typeB = "blocage";
						System.out.println(proc.exitValue());
						nmro.setIndex(i);
						nmro.setNum(Nmro);

						TblLog log = new TblLog();
						log.setAction("blocage num : " + Nmro);
						log.setUsername(Util.getUser().getULogin());
						log.setDateAction(new Timestamp(System.currentTimeMillis()));
						decision_service.addlog(log);
					}
					if (proc != null) {
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

						BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
						String s = null;
						boolean add = false;
						// read the output from the command
						System.out.println("Here is the standard output of the command:\n");
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;

							} else {
								add = false;
							}
							System.out.println(s);

						}
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(Util.getUser().getUName());
							df.setMsisdn(numberprefix + Nmro);

							if (this.getChoix().equals("blocage")) {
								df.setDecision("D");
							} else {
								df.setDecision("W");
							}
							df.setIdRegle(0);
							decision_service.updateDecision(df);
							if (typeB.equals("blocage")) {
								if (outputCode.contains("1"))
									nmro.setAction("Number has been blocke");
//								context.addMessage(null, new FacesMessage("Successful", "Number has been blocked "));
								if (outputCode.contains("2"))
									nmro.setAction("Number is already blocked ");
//								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
//										"Number is already blocked "));
							}
							if (typeB.equals("deblocage")) {
								if (outputCode.contains("1"))
									nmro.setAction("Number has been unblocked ");
//								context.addMessage(null, new FacesMessage("Successful", "Number has been unblocked "));
								if (outputCode.contains("2"))
									nmro.setAction("Number is already unblocked");
//								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
//										"Number is already unblocked "));
							}

						} else {
							if (typeB.equals("blocage")) {
//								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
//										"Error while blocking number "));
								nmro.setAction("Error while blocking number");

							}
							if (typeB.equals("deblocage")) {
								nmro.setAction("Error while unblocking number ");
//								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
//										"Error while unblocking number "));
							}
						}

						// read any errors from the attempted command
						System.out.println("Here is the standard error of the command (if any):\n");
						String m = "";
						while ((s = stdError.readLine()) != null) {

							m += s;
							System.out.println(s);
						}
						if (!m.isEmpty()) {
							context.addMessage("somekey",
									new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tabNum.add(nmro);
			}
		} else {
			Nmro = textofnum;
			try {
				String typeB = null;
				if (this.getChoix().equals("blocage")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blkonnet  " + Nmro });
					proc.waitFor();
					typeB = "blocage";
					System.out.println(proc.exitValue());

					TblLog log = new TblLog();
					log.setAction("blocage num : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (this.getChoix().equals("deblocage")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblkonnet  " + Nmro });
					proc.waitFor();
					typeB = "deblocage";
					System.out.println(proc.exitValue());
					TblLog log = new TblLog();
					log.setAction("deblocage num : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (this.getChoix().equals("WhiteList")) {

					DecisionFraude df = new DecisionFraude();
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					df.setDateModif(currentTimestamp);
					df.setNomUtilisateur(Util.getUser().getUName());
					df.setMsisdn(numberprefix + Nmro);
					df.setDecision("W");
					df.setIdRegle(0);
					decision_service.addDecision(df);
					TblLog log = new TblLog();
					log.setAction(" WhiteList : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
					context.addMessage(null, new FacesMessage("Successful", "Number has been added to whiteList "));
				} else if (this.getChoix().equals("deblocageSMS")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + Nmro });
					proc.waitFor();
					typeB = "deblocage";
					System.out.println(proc.exitValue());
					TblLog log = new TblLog();
					log.setAction("deblocage num : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				} else if (this.getChoix().equals("blocageSMS")) {
					proc = Runtime.getRuntime()
							.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms  " + "  " + Nmro });
					proc.waitFor();
					typeB = "blocage";
					System.out.println(proc.exitValue());

					TblLog log = new TblLog();
					log.setAction("blocage num : " + Nmro);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				}
				if (proc != null) {
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

					BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
					String s = null;
					boolean add = false;
					// read the output from the command
					System.out.println("Here is the standard output of the command:\n");
					String outputCode = "0";
					while ((s = stdInput.readLine()) != null) {
						if (s.contains("1") || s.contains("2")) {
							add = true;
							outputCode = s;

						} else {
							add = false;
						}
						System.out.println(s);

					}
					if (add) {
						DecisionFraude df = new DecisionFraude();
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						df.setDateModif(currentTimestamp);
						df.setNomUtilisateur(Util.getUser().getUName());
						df.setMsisdn(numberprefix + Nmro);

						if (this.getChoix().equals("blocage")) {
							df.setDecision("D");
						} else {
							df.setDecision("W");
						}
						df.setIdRegle(0);
						decision_service.updateDecision(df);
						if (typeB.equals("blocage")) {
							if (outputCode.contains("1"))
								context.addMessage(null, new FacesMessage("Successful", "Number has been blocked "));
							if (outputCode.contains("2"))
								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
										"Number is already blocked "));
						}
						if (typeB.equals("deblocage")) {
							if (outputCode.contains("1"))
								context.addMessage(null, new FacesMessage("Successful", "Number has been unblocked "));
							if (outputCode.contains("2"))
								context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!",
										"Number is already unblocked "));
						}

					} else {
						if (typeB.equals("blocage")) {
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
									"Error while blocking number "));
						}
						if (typeB.equals("deblocage")) {
							context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
									"Error while unblocking number "));
						}
					}

					// read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					String m = "";
					while ((s = stdError.readLine()) != null) {

						m += s;
						System.out.println(s);
					}
					if (!m.isEmpty()) {
						context.addMessage("somekey",
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error : " + m));
					}
				}
			} catch (Exception e) {
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

	public String getChoix2() {
		return choix2;
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
		choix = "";
		lstNum = new ArrayList<Nmro>();
		tabNum = new ArrayList<Nmro>();
		sdtinputs = new ArrayList<String>();

	}

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
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

	public void setChoix2(String choix2) {
		this.choix2 = choix2;
	}

	private String textofnum = "";

	public String gettextofnum() {
		return textofnum;
	}

	public void settextofnum(String listofnum) {
		this.textofnum = listofnum;
	}

	public void bloquer() {

		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;
		List<String> listnumber = Arrays.asList(textofnum.split(","));
		System.out.println("Blocage !");
		for (int i = 0; i < listnumber.size(); i++) {

			try {

//			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
//			String line = bufferedReader.readLine();
				String line = listnumber.get(i).toString();
				while (line != null) {
					if (line != "") {
						// if (line.substring(0, 1).contains("1")) {
						Process proc;

						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blkonnet " + line });
						proc.waitFor();
						typeB = "blocage";
						// System.out.println(proc.exitValue());
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;

							} else {
								add = false;
							}
							// System.out.println(s);

						}

						Nmro num = new Nmro();
						System.out.println(
								"***************" + outputCode + "**********************************************");
						if (outputCode.contains("1"))
							num.Action = "Number has been blocked";

						if (outputCode.contains("2"))
							num.Action = "Number is already blocked";

						if (outputCode.contains("0"))
							num.Action = "ERROR";

						num.num = line;
						System.out.println(line + " = " + num.Action); // ici code ligne
						tabNum.add(num);
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(Util.getUser().getUName());
							df.setMsisdn(numberprefix + line);
							df.setDecision("D");
							df.setIdRegle(0);
							decision_service.updateDecision(df);

							TblLog log = new TblLog();
							log.setAction("blocage num : " + Nmro);
							log.setUsername(Util.getUser().getULogin());
							log.setDateAction(new Timestamp(System.currentTimeMillis()));
							decision_service.addlog(log);

						}

					}

				}

				// line = bufferedReader.readLine();
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void bloquersms() {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;

		List<String> listnumber = Arrays.asList(textofnum.split(","));
		System.out.println("Deblocage sms!");
		for (int i = 0; i < listnumber.size(); i++) {
			String line = listnumber.get(i).toString();
			try {

				// bufferedReader = new BufferedReader(new InputStreamReader(new
				// FileInputStream(this.file), "UTF-8"));
				// String line = bufferedReader.readLine();

				while (line != null) {
					if (line != "") {
						// if (line.substring(0, 1).contains("1")) {
						Process proc;

						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/blksms " + line });
						proc.waitFor();
						typeB = "blocage";
						// System.out.println(proc.exitValue());
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;

							} else {
								add = false;
							}
							// System.out.println(s);

						}

						Nmro num = new Nmro();
						System.out.println(
								"***************" + outputCode + "**********************************************");
						if (outputCode.contains("1"))
							num.Action = "Number has been blocked";

						if (outputCode.contains("2"))
							num.Action = "Number is already blocked";

						if (outputCode.contains("0"))
							num.Action = "ERROR";

						num.num = line;
						System.out.println(line + " = " + num.Action); // ici code ligne
						tabNum.add(num);
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(Util.getUser().getUName());
							df.setMsisdn(numberprefix + line);
							df.setDecision("D");
							df.setIdRegle(0);
							decision_service.updateDecision(df);

							TblLog log = new TblLog();
							log.setAction("blocage num : " + Nmro);
							log.setUsername(Util.getUser().getULogin());
							log.setDateAction(new Timestamp(System.currentTimeMillis()));
							decision_service.addlog(log);

						}

					}

				}

				// line = bufferedReader.readLine();
				// }

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void debloquer() {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;
		List<String> listnumber = Arrays.asList(textofnum.split(","));
		System.out.println("Deblocage !");
		for (int i = 0; i < listnumber.size(); i++) {
			String line = listnumber.get(i).toString();
			try {

				// bufferedReader = new BufferedReader(new InputStreamReader(new
				// FileInputStream(this.file), "UTF-8"));
				// String line = bufferedReader.readLine();
				while (line != null) {
					if (line != "") {
						// if (line.substring(0, 1).contains("1")) {
						Process proc;

						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblkonnet " + line });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;

							} else {
								add = false;
							}

						}
						Nmro num = new Nmro();
						System.out.println(
								"***************" + outputCode + "**********************************************");

						if (outputCode.contains("1"))
							num.Action = "Number has been unblocked";

						if (outputCode.contains("2"))
							num.Action = "Number is already unblocked";

						if (outputCode.contains("0"))
							num.Action = "ERROR";

						num.num = line;
						System.out.println(line + " = " + outputCode); // ici code ligne
						tabNum.add(num);
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(Util.getUser().getUName());
							df.setMsisdn(numberprefix + line);
							df.setDecision("W");
							df.setIdRegle(0);
							decision_service.updateDecision(df);
							TblLog log = new TblLog();
							log.setAction("deblocage num : " + line);
							log.setUsername(Util.getUser().getULogin());
							log.setDateAction(new Timestamp(System.currentTimeMillis()));
							decision_service.addlog(log);
						}

						System.out.println(line); // ici code ligne

						// line = bufferedReader.readLine();
					}
				}
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void debloquersms() {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;
		List<String> listnumber = Arrays.asList(textofnum.split(","));
		System.out.println("Deblocage !");
		for (int i = 0; i < listnumber.size(); i++) {
			String line = listnumber.get(i).toString();
			try {

				// bufferedReader = new BufferedReader(new InputStreamReader(new
				// FileInputStream(this.file), "UTF-8"));
				// String line = bufferedReader.readLine();
				while (line != null) {
					if (line != "") {
						// if (line.substring(0, 1).contains("1")) {
						Process proc;

						proc = Runtime.getRuntime()
								.exec(new String[] { "bash", "-c", cmdprefix + " /mediation/bin/dblksms " + line });
						proc.waitFor();
						typeB = "deblocage";
						System.out.println(proc.exitValue());
						BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						String outputCode = "0";
						while ((s = stdInput.readLine()) != null) {
							if (s.contains("1") || s.contains("2")) {
								add = true;
								outputCode = s;

							} else {
								add = false;
							}

						}
						Nmro num = new Nmro();
						System.out.println(
								"***************" + outputCode + "**********************************************");

						if (outputCode.contains("1"))
							num.Action = "Number has been unblocked";

						if (outputCode.contains("2"))
							num.Action = "Number is already unblocked";

						if (outputCode.contains("0"))
							num.Action = "ERROR";

						num.num = line;
						System.out.println(line + " = " + outputCode); // ici code ligne
						tabNum.add(num);
						if (add) {
							DecisionFraude df = new DecisionFraude();
							Calendar calendar = Calendar.getInstance();
							Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
							df.setDateModif(currentTimestamp);
							df.setNomUtilisateur(Util.getUser().getUName());
							df.setMsisdn(numberprefix + line);
							df.setDecision("W");
							df.setIdRegle(0);
							decision_service.updateDecision(df);
							TblLog log = new TblLog();
							log.setAction("deblocage num : " + line);
							log.setUsername(Util.getUser().getULogin());
							log.setDateAction(new Timestamp(System.currentTimeMillis()));
							decision_service.addlog(log);
						}

						System.out.println(line); // ici code ligne

						// line = bufferedReader.readLine();
						// }
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void whiteList() {
		String numberprefix = "";
		String cmdprefix = "";
		String operator = decision_service.getoperator().get(0).getName_operator();
		if (operator.contains("sotelma")) {
			numberprefix = "223";
			cmdprefix = " ssh collect@172.20.19.50";
		}
		if (operator.contains("expresso")) {
			numberprefix = "221";
		}
		if (operator.contains("sudatel")) {
			numberprefix = "249";
		}
		if (operator.contains("moovtogo")) {
			numberprefix = "228";
		}
		if (operator.contains("chinguitel")) {
			numberprefix = "222";
		}
		if (operator.contains("mauritel")) {
			numberprefix = "222";
		}
		if (operator.contains("mattel")) {
			numberprefix = "222";
		}
		if (operator.contains("tchad")) {
			numberprefix = "235";

		}
		tabNum = new ArrayList<Nmro>();
		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;
		List<String> listnumber = Arrays.asList(textofnum.split(","));
		System.out.println("Deblocage !");
		for (int i = 0; i < listnumber.size(); i++) {
			String line = listnumber.get(i).toString();
			try {

//			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
//			String line = bufferedReader.readLine();
				while (line != null) {
					// if (line.substring(0, 1).contains("1")) {
					Nmro num = new Nmro();
					num.num = line;
					num.Action = "whiteList";
					tabNum.add(num);
					DecisionFraude df = new DecisionFraude();
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					df.setDateModif(currentTimestamp);
					df.setNomUtilisateur(Util.getUser().getUName());
					df.setMsisdn(numberprefix + line);
					df.setDecision("W");
					df.setIdRegle(0);
					decision_service.addDecision(df);
					TblLog log = new TblLog();
					log.setAction("whiteList num : " + line);
					log.setUsername(Util.getUser().getULogin());
					log.setDateAction(new Timestamp(System.currentTimeMillis()));
					decision_service.addlog(log);
				}
				System.out.println(line); // ici code ligne

				// line = bufferedReader.readLine();
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void NmroFromFichier(FileUploadEvent event) throws IOException, InvalidFormatException {

		BufferedReader bufferedReader;
		String typeB = null;
		String s = null;
		boolean add = false;
		if (getChoix().equals("blocage")) {
			System.out.println("Blocage !");
			/*
			 * try { file = event.getFile(); bufferedReader = new BufferedReader(new
			 * InputStreamReader(file.getInputstream(),"UTF-8")); String line =
			 * bufferedReader.readLine(); while (line != null) { if ( line.substring(0,
			 * 2).contains("70")) { Process proc;
			 * 
			 * proc = Runtime.getRuntime().exec(new String[] { "bash", "-c",
			 * ". /mediation/webuser/.bash_profile;/mediation/bin/blkonnet  "+ line});
			 * proc.waitFor(); typeB="blocage"; //System.out.println(proc.exitValue());
			 * BufferedReader stdInput = new BufferedReader(new
			 * InputStreamReader(proc.getInputStream())); String outputCode = "0" ; while
			 * ((s = stdInput.readLine()) != null) { if(s.contains("1") || s.contains("2"))
			 * { add = true; outputCode = s ;
			 * 
			 * } else { add = false ; } //System.out.println(s);
			 * 
			 * } if(outputCode.contains("1")) sdtinputs.add("Number has been blocked");
			 * 
			 * if(outputCode.contains("2")) sdtinputs.add("Number is already blocked");
			 * 
			 * else sdtinputs.add("ERROR");
			 * 
			 * tabNum.add(line); System.out.println(line+" = "+outputCode); // ici code
			 * ligne
			 * 
			 * if(add){ DecisionFraude df = new DecisionFraude(); Calendar calendar =
			 * Calendar.getInstance(); Timestamp currentTimestamp = new
			 * java.sql.Timestamp(calendar.getTime().getTime());
			 * df.setDateModif(currentTimestamp);
			 * df.setNomUtilisateur(Util.getUser().getUName()); df.setMsisdn("221" + line);
			 * df.setDecision("D"); df.setIdRegle(0); decision_service.updateDecision(df); }
			 * 
			 * 
			 * }
			 * 
			 * line = bufferedReader.readLine(); }
			 * 
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */

		} else {
			System.out.println("D�locage !");

			/*
			 * try { file = event.getFile(); bufferedReader = new BufferedReader(new
			 * InputStreamReader(file.getInputstream(),"UTF-8")); String line =
			 * bufferedReader.readLine(); while (line != null) { if ( line.substring(0,
			 * 2).contains("70")) { Process proc;
			 * 
			 * proc = Runtime.getRuntime().exec(new String[] { "bash", "-c",
			 * ". /mediation/webuser/.bash_profile;/mediation/bin/dblkonnet  "+ line});
			 * proc.waitFor(); typeB="deblocage"; System.out.println(proc.exitValue());
			 * BufferedReader stdInput = new BufferedReader(new
			 * InputStreamReader(proc.getInputStream())); String outputCode = "0" ; while
			 * ((s = stdInput.readLine()) != null) { if(s.contains("1") || s.contains("2"))
			 * { add = true; outputCode = s ;
			 * 
			 * } else { add = false ; }
			 * 
			 * } if(outputCode.contains("1")) sdtinputs.add("Number has been unblocked");
			 * 
			 * if(outputCode.contains("2")) sdtinputs.add("Number is already unblocked");
			 * 
			 * else sdtinputs.add("ERROR");
			 * 
			 * tabNum.add(line); System.out.println(line+" = "+outputCode); // ici code
			 * ligne
			 * 
			 * if(add){ DecisionFraude df = new DecisionFraude(); Calendar calendar =
			 * Calendar.getInstance(); Timestamp currentTimestamp = new
			 * java.sql.Timestamp(calendar.getTime().getTime());
			 * df.setDateModif(currentTimestamp);
			 * df.setNomUtilisateur(Util.getUser().getUName()); df.setMsisdn("221" + line);
			 * df.setDecision("W"); df.setIdRegle(0); decision_service.updateDecision(df); }
			 * 
			 * tabNum.add(line); System.out.println(line); // ici code ligne
			 * 
			 * 
			 * line = bufferedReader.readLine(); } } catch (Exception e) {
			 * e.printStackTrace(); }
			 */

		}

	}

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
				if (decision_service.FindDecisionByMsisdn("249" + Nmro).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("249" + Nmro);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						// dec.setNomUtilisater(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("249" + Nmro);
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
					decision.setMsisdn("249" + Nmro + " ");
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
				if (decision_service.FindDecisionByMsisdn("249" + Nmro).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("249" + Nmro);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("249" + Nmro);
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
				FacesContext.getCurrentInstance().addMessage("@form",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "action on number " + Nmro + "   succes", Nmro));

			} else {
				FacesContext.getCurrentInstance().addMessage("@form",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error With  number" + Nmro + "  ", Nmro));

			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error With  number" + Nmro + "  ", "Please Try Again"));

		}

	}

	public void ValiderBlock() throws IOException, JSchException {

		blkoffnet telnet = new blkoffnet("myserver", "userId", "Password");
		for (int i = 0; i < lstNum.size(); i++) {

			telnet.sendCommand("SET BUREAU:ID=20;");
			telnet.sendCommandblk(lstNum.get(i).getNum());

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("249" + lstNum.get(i).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("249" + lstNum.get(i).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("249" + lstNum.get(i).getNum());
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
				decision.setMsisdn("249" + lstNum.get(i).getNum());
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
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros Bloqu�s Avec succes", "Succes"));

		completed = false;
	}

	public void choixAction() {

		if (choix2.equals("blocage"))
			choix2 = "deblocage";
		else
			choix2 = "blocage";
		System.out.println(choix2);
	}

	public void DeBlockFile() throws IOException, JSchException {
		blkoffnet telnet = new blkoffnet("myserver", "userId", "Password");
		for (int i = 0; i < lstNum.size(); i++) {

			telnet.sendCommand("SET BUREAU:ID=20;");
			telnet.senddeblkcmd(lstNum.get(i).getNum());

			DecisionFraude decision;

			List<DecisionFraude> decisions = new ArrayList<>();
			if (decision_service.FindDecisionByMsisdn("249" + lstNum.get(i).getNum()).size() > 0) {
				decisions = decision_service.FindDecisionByMsisdn("249" + lstNum.get(i).getNum());
				for (DecisionFraude dec : decisions) {
					Calendar calendar = Calendar.getInstance();
					Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
					dec.setDateModif(currentTimestamp);
					// dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
					dec.setMsisdn("249" + lstNum.get(i).getNum());
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
				decision.setMsisdn("249" + lstNum.get(i).getNum());
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
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Numeros  debloqu�s", "Success"));

		completed = false;
	}

	public String getTypeBlocage() {
		return typeBlocage;
	}

	public void setTypeBlocage(String typeBlocage) {
		this.typeBlocage = typeBlocage;
	}

	public class Nmro {
		private int index;
		private String num;
		private String Action;

		public String getAction() {
			return Action;
		}

		public void setAction(String action) {
			Action = action;
		}

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
