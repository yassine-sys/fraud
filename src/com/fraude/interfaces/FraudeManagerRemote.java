package com.fraude.interfaces;

import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.fraude.entities.FiltresReglesFraude;
import com.fraude.entities.Flow;
import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.DetailsMail;
import com.fraude.entities.DetailsSms;
import com.fraude.entities.FiltresFraude;
import com.fraude.entities.FluxCdr;
import com.fraude.entities.Non_Frodulent_seq;
import com.fraude.entities.Offre;
import com.fraude.entities.OperatorDeployment;
import com.fraude.entities.ParametresFraude;
import com.fraude.entities.ParametresReglesFraude;
import com.fraude.entities.PlanTarifaire;
import com.fraude.entities.ReglesFraude;
import com.fraude.entities.StatFraudeMsisdn;
import com.fraude.entities.TblLog;
import com.fraude.entities.TypeDestination;
import com.fraude.entities.YourDataWrapper;

@Local
public interface FraudeManagerRemote {

	public Integer FindDecisionsByMsisdn(String msisdn);

	public void addDecision(DecisionFraude df);

	public boolean testifexists(String table);

	// public StatFraudeMsisdn getInfo(String msisdn);
	public ReglesFraude getRegleById(Integer id);

	public List<DecisionFraude> getDecisionFraudes(String where);

	public List<DecisionFraude> FindDecisionByMsisdn(String msisdn);

	public void updateDecision(DecisionFraude df);

	public void updateDecision1(String msisdn);

	public void decision(DecisionFraude df, String msisdn);

	public List<Object[]> getCDRBActivatonyTypeRegle(String date, String where);

	public List<Object[]> getLocationDuree(String msisdn);

	public List<Object[]> getCDRRechargeByTypeRegle(String date, String where);

	public List<Object[]> getCDRTransfertByTypeRegle(String date, String where);

	public List<Object[]> getCDRByTypeRegle(String date, String where);

	public List<Object[]> getParametres(ReglesFraude prg, String msisdn);

	public Object[] getDecisions(String msisdn);

	public List<Object[]> getAlerteFraudeByRegle(ReglesFraude rg);

	public List<Object[]> getLocationMsisdn(String msisdn, Integer id);

	public FiltresFraude getFiltreById(String id);

	public List<Object[]> getReglesByMSISDN(String msisdn);

	public List<Object[]> getMSISDNMorethantwo();

	public List<Object[]> getAlerteHURByRegle(ReglesFraude rg, String date);

	public List<CategoriesFraude> getAllCategories();

	public List<Flow> getAllFlux();

	public List<Flow> getAllFlux2();

	public List<ReglesFraude> getAllFraudes(String Where);

	public List<ParametresReglesFraude> getParametresReglesFraudeByRegle(ReglesFraude rf);

	public List<FiltresReglesFraude> getFiltresReglesFraudeByRegle(ReglesFraude rf);

	public List<ParametresFraude> getParametresFraude(String Where);

	public List<Flow> getParametresFraude2(String Where);

	public List<FiltresFraude> getFilterFiltresFraude(String where);

	public List<FiltresFraude> getFilterFiltresFraude2();

	public void updateRegle(ReglesFraude rf);

	public void addParametre(ParametresReglesFraude pr);

	public void updateParametre(ParametresReglesFraude pr);

	public void deleteParametre(ParametresReglesFraude pr);

	public void addFiltre(FiltresReglesFraude fr);

	public void updateFiltre(FiltresReglesFraude fr);

	public void deleteFiltre(FiltresReglesFraude fr);

	public void addRegle(ReglesFraude rf);

	public void deleteRegle(ReglesFraude rf);

	public FiltresFraude getFilterByID(Integer id);

	public FluxCdr getFluxByID(Integer id);

	public ParametresFraude getParametreByID(Integer id);

	public CategoriesFraude getCategoryByID(Integer id);

	public List<Object[]> getAllDecisionFraudes(String string);

	public List<Object[]> getAllDecisionFraudesBY(String string);

	public StatFraudeMsisdn getInfo(String msisdn);

	public List<DetailsMail> getAlerteFraudeByRegleMail(ReglesFraude selected_regle);

	public List<DetailsMail> getAlerteFraudeByRegleMailselect(DetailsMail rg);

	public List<DetailsSms> getAlerteFraudeByRegleSms(ReglesFraude rg);

	public void addAlerteMail(DetailsMail dt);

	public void ModifierAlerteMail(DetailsMail dt);

	public void deleteDetailMails(DetailsMail dt);

	public void addAlerteSms(DetailsSms dt, Integer id);

	public void deleteDetailSms(DetailsSms dt);

	public List<Object[]> getAllParametre(Object[] obj);

	public List<Object[]> GetMo(Object[] obj, String date);

	public List<Object[]> GetMT(Object[] obj, String date);

	public List<String> GetNameField(Object[] obj);

	public List<OperatorDeployment> getoperator();

	public void addlog(TblLog log);

	public List<Flow> getAllParamperflow(Long idflow);

	public List<TypeDestination> getAllTypedest();

	public List<PlanTarifaire> getAllPlanTarifaire();

	public List<Offre> getAllOffre();

//	public void addReglewithparamandfiltre(ReglesFraude regle,ParametresReglesFraude param,FiltresReglesFraude filter);

	public void addReglewithparamandfiltre(YourDataWrapper dataWrapper);

	List<ReglesFraude> getAllRules();

	public void updaterule(Integer id, ReglesFraude regle);

	public void updateblok(Integer id, ReglesFraude regle);

	public void updatefilter(Integer id, FiltresReglesFraude filter);

	public void updateparam(Long id, ParametresReglesFraude param);

	// public void updateregle(Integer id, ReglesFraude regle, Integer id_flow);

	public void editregle(Integer id, ReglesFraude updaterequest);

	public List<ParametresReglesFraude> getParamsByRegleId(Integer idRegle);

	public List<FiltresReglesFraude> getFiltersByRegleId(Integer idRegle);

	public void addparam(ParametresReglesFraude param);

	public void addfilter(FiltresReglesFraude filter);

	public List<ReglesFraude> getRegleByid(Integer id);

	public void deleteFiltre(Integer id);

	public void deleteParam(Long id);

	public List<DecisionFraude> getAllDecisionFraudes();

	public List<Object[]> getMsisdnAndRegleCount();

	public List<DecisionFraude> findDecisionsInDateRange(Date startDate, Date endDate);

	List<Object[]> getMSISDNsByRegle(Long regleId);

	List<Object[]> getDetailsWarning(String msisdn, Integer id_regle);

	void addSMS(DetailsSms sms);

	List<DetailsMail> getMailsByRegleId(Integer idRegle);

	List<DetailsSms> getSMSByRegleId(Integer idRegle);

	void deleteMail(Integer id);

	void deleteSMS(Integer id);

	public List<Object[]> chartbyregle(String idRegles, String startDate, String endDate);

	void decisionnonfraudilentseq(Non_Frodulent_seq df, String msisdn);


	List<Object[]> getAllRulesWithMaxDateDetection();
}
