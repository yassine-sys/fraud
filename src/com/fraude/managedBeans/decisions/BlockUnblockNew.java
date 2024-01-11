package com.fraude.managedBeans.decisions;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.fraude.entities.Util;
import com.fraude.entities.DecisionFraude;
import com.fraude.interfaces.FraudeManagerRemote;

@ManagedBean(name = "block_new")
@ViewScoped
public class BlockUnblockNew {

	private String numero;
	private String choix;

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@EJB
	FraudeManagerRemote decision_service;

	public void validate() {
		try {
			Document d = loadXMLFromString(getResultURL("/opt/csw/bin/curl", "-X", "GET",
					"http://172.17.10.62:22022/openapi/v1/tokens-stub/get?login=VOIP_FRAUD&password=cn1igfSU"));
			Node el = d.getFirstChild();
			//String session_token = el.getTextContent();
			Node session_token = el.getFirstChild();
			String result = getResultURL("/opt/csw/bin/curl", "-X", "GET",
					"http://172.17.10.62:22022/openapi/v1/subscribers/msisdn:" + numero + "?fields=subscriberId", "-H",
					"authtoken:" + session_token, "-H", "content-type: application/json");

			JSONObject obj = new JSONObject(result);
			Object sub = obj.get("subscriberId");
			String subs_id = sub.toString();
			if (choix.equals("block")) {

				System.out.println(getResultURL("/opt/csw/bin/curl", "-X", "POST",
						"http://172.17.10.62:22022/openapi/v1/subscribers/" + subs_id + "/block", "-H",
						"authtoken:" + session_token, "-H", "content-type: application/json"));
				DecisionFraude decision;
				String decis = "D";
				List<DecisionFraude> decisions = new ArrayList<>();
				if (decision_service.FindDecisionByMsisdn("222" + numero).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("222" + numero);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("222" + numero);
						dec.setDecision(decis);
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
					decision.setMsisdn("222" + numero + " ");
					decision.setDecision(decis);
					decision.setIdRegle(0);
					decision_service.addDecision(decision);

				}
			} else if (choix.equals("block White")) {

				System.out.println(getResultURL("/opt/csw/bin/curl", "-X", "POST",
						"http://172.17.10.62:22022/openapi/v1/subscribers/" + subs_id + "/block", "-H",
						"authtoken:" + session_token, "-H", "content-type: application/json"));
				DecisionFraude decision;
				String decis = "W";
				List<DecisionFraude> decisions = new ArrayList<>();
				if (decision_service.FindDecisionByMsisdn("222" + numero).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("222" + numero);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("222" + numero);
						dec.setDecision(decis);
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
					decision.setMsisdn("222" + numero + " ");
					decision.setDecision(decis);
					decision.setIdRegle(0);
					decision_service.addDecision(decision);

				}
			} else if (choix.equals("unblock")) {
				System.out.println(getResultURL("/opt/csw/bin/curl", "-X", "POST",
						"http://172.17.10.62:22022/openapi/v1/subscribers/" + subs_id + "/unblock", "-H",
						"authtoken:" + session_token, "-H", " content-type: application/json"));
				DecisionFraude decision;
				String decis = "W";
				List<DecisionFraude> decisions = new ArrayList<>();
				if (decision_service.FindDecisionByMsisdn("222" + numero).size() > 0) {
					decisions = decision_service.FindDecisionByMsisdn("222" + numero);
					for (DecisionFraude dec : decisions) {
						Calendar calendar = Calendar.getInstance();
						Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
						dec.setDateModif(currentTimestamp);
						//dec.setNomUtilisateur(Util.getUser().getNomUtilisateur());
						dec.setMsisdn("222" + numero);
						dec.setDecision(decis);
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
					decision.setMsisdn("222" + numero + " ");
					decision.setDecision(decis);
					decision.setIdRegle(0);
					decision_service.addDecision(decision);

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResultURL(String... text) {
		String result = "";
		try {
			ProcessBuilder pb = new ProcessBuilder(text);
			System.out.println(text.toString());
			Process process = pb.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				result = result + line;

			}
			process.waitFor();
			process.getInputStream().close();

			process.getOutputStream().close();

			process.getErrorStream().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}

	public Document loadXMLFromString(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

}
