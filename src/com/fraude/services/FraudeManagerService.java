package com.fraude.services;

import java.sql.ResultSet;

import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.poi.util.SystemOutLogger;
import org.hibernate.annotations.Filters;

import com.fraude.entities.FiltresReglesFraude;
import com.fraude.entities.Flow;
import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.DecisionFraude;
import com.fraude.entities.DetailsMail;
import com.fraude.entities.DetailsSms;
import com.fraude.entities.FiltresFraude;
import com.fraude.entities.FluxCdr;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListImei;
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
import com.fraude.interfaces.FraudeManagerRemote;
import javax.persistence.Tuple;
import javax.persistence.Query;



@Stateless
public class FraudeManagerService implements FraudeManagerRemote {

	@PersistenceContext
	EntityManager emRep;

	@Override
	public List<CategoriesFraude> getAllCategories() {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select c From CategoriesFraude c").getResultList();
	}

	@Override
	public List<Flow> getAllParamperflow(Long idflow) {
		// TODO Auto-generated method stub

		return emRep.createQuery(
				"SELECT f FROM Flow f WHERE f.flow_type = 6 AND f.proc.id IN (SELECT e.proc.id FROM EtlProcFlow e WHERE e.flow.id = :idflow)")
				.setParameter("idflow", idflow).getResultList();
	}

	@Override
	public List<Flow> getAllFlux() {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select f From Flow f where f.flow_type=1").getResultList();
	}

	@Override
	public List<Flow> getAllFlux2() {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select f From Flow f where f.flow_type=1").getResultList();
	}

	@Override
	public List<ParametresFraude> getParametresFraude(String Where) {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select Distinct p.parametre From ParametresCategory p where " + Where)
				.getResultList();
	}

	@Override
	public List<Flow> getParametresFraude2(String Where) {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select Distinct f From Flow f where " + Where).getResultList();
	}

	@Override
	public List<FiltresFraude> getFilterFiltresFraude(String where) {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select Distinct f.filtre From FiltresFlux f where " + where).getResultList();
	}

	@Override
	public List<FiltresFraude> getFilterFiltresFraude2() {
		// TODO Auto-generated method stub

		return emRep.createQuery("Select Distinct f From FiltresFraude f").getResultList();
	}

	@Override
	public void updateRegle(ReglesFraude rf) {
		// TODO Auto-generated method stub
		emRep.merge(rf);
	}

	@Override
	public void addRegle(ReglesFraude rf) {
		// TODO Auto-generated method stub
		emRep.persist(rf);

	}

	@Override
	public void deleteRegle(ReglesFraude rf) {
		// TODO Auto-generated method stub

	}

	@Override
	public FiltresFraude getFilterByID(Integer id) {
		// TODO Auto-generated method stub
		return emRep.find(FiltresFraude.class, id);
	}

	@Override
	public FluxCdr getFluxByID(Integer id) {
		// TODO Auto-generated method stub
		return emRep.find(FluxCdr.class, id);
	}

	@Override
	public ParametresFraude getParametreByID(Integer id) {
		// TODO Auto-generated method stub
		return emRep.find(ParametresFraude.class, id);
	}

	@Override
	public CategoriesFraude getCategoryByID(Integer id) {
		// TODO Auto-generated method stub
		return emRep.find(CategoriesFraude.class, id);
	}

	@Override
	public List<ReglesFraude> getAllFraudes(String Where) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select r From ReglesFraude r " + Where).getResultList();
	}

	@Override
	public void addParametre(ParametresReglesFraude pr) {
		// TODO Auto-generated method stub
		emRep.persist(pr);

	}

	@Override
	public void updateParametre(ParametresReglesFraude pr) {
		// TODO Auto-generated method stub
		emRep.merge(pr);

	}

	@Override
	public void deleteParametre(ParametresReglesFraude pr) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE   FROM ParametresReglesFraude f WHERE f.id=" + pr.getId()).executeUpdate();
	}

	@Override
	public void addFiltre(FiltresReglesFraude fr) {
		// TODO Auto-generated method stub
		emRep.persist(fr);
	}

	@Override
	public void updateFiltre(FiltresReglesFraude fr) {
		// TODO Auto-generated method stub
		emRep.merge(fr);
	}

	@Override
	public void deleteFiltre(FiltresReglesFraude fr) {
		// TODO Auto-generated method stub

		emRep.createQuery("DELETE   FROM FiltresReglesFraude f WHERE f.id=" + fr.getId()).executeUpdate();
	}

	@Override
	public List<ParametresReglesFraude> getParametresReglesFraudeByRegle(ReglesFraude rf) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select f From ParametresReglesFraude f where f.regle.id = " + rf.getId())
				.getResultList();
	}

	@Override
	public List<FiltresReglesFraude> getFiltresReglesFraudeByRegle(ReglesFraude rf) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select f From FiltresReglesFraude f where f.regle.id = " + rf.getId())
				.getResultList();
	}

	@Override
	public List<Object[]> getAlerteFraudeByRegle(ReglesFraude rg) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				" Select id_regle,msisdn,to_timestamp(min(date_debut), 'YYMMDDHH24MISS') as date_debut,to_timestamp(max(date_fin), 'YYMMDDHH24MISS')as date_fin,max(date_detection) as date_detection,count(*)as nb_occurance ,min(date_debut) from stat.alerte_fraude_seq where id_regle ="
						+ rg.getId() + "  group by id_regle,msisdn")
				.getResultList();
	}

	@Override
	public List<Object[]> getAlerteHURByRegle(ReglesFraude rg, String date) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				" Select id_regle, msisdn,to_date(date_appel,'YYMMDD'), nb_occurance,date_appel  from stat.alerte_fraude where id_regle ="
						+ rg.getId() + " and to_date(date_appel,'YYMMDD')=to_date('" + date
						+ "','DD/MM/YYYY')  group by id_regle,msisdn,date_appel,nb_occurance")
				.getResultList();
	}

	@Override
	public List<Object[]> getParametres(ReglesFraude prg, String msisdn) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				"SELECT etl.etl_flows.id, tbl.valeur ,to_timestamp(tbl.date_debut, 'YYMMDDHH24MISS') as date_debut, to_timestamp(tbl.date_fin, 'YYMMDDHH24MISS') as date_fin   FROM (select * from stat.regle_parametres_valeur_seq where id_regle = "
						+ prg.getId() + " and stat.regle_parametres_valeur_seq.msisdn = '" + msisdn
						+ "'  )  as tbl, etl.etl_flows  where tbl.id_parametre = etl.etl_flows.id ")
				.getResultList();

	}

	@Override
	public Object[] getDecisions(String msisdn) {
		// TODO Auto-generated method stub
		try {
			return (Object[]) emRep.createNativeQuery("Select * from  tables.parc_gpto_act where id='"
					+ msisdn.substring(3, msisdn.length()) + "' LIMIT 1").getSingleResult();

		} catch (Exception ex) {
			return new Object[20];
		}

	}

	// @Override
	// public StatFraudeMsisdn getInfo(String msisdn) {
	// // TODO Auto-generated method stub
	// try{
	// return (StatFraudeMsisdn) emRep.createQuery("Select s from
	// StatFraudeMsisdn s where msisdn='"+msisdn+"' ").getSingleResult();
	//
	// }catch(Exception ex){
	// System.out.println(ex.getMessage());
	// return new StatFraudeMsisdn();
	// }
	//
	// }

	@Override
	public List<Object[]> getCDRByTypeRegle(String date, String where) {
		// TODO Auto-generated method stub
		try {
			return emRep.createNativeQuery("Select * From cdrs_archives.cdrs_" + date + " " + where).getResultList();
		} catch (PersistenceException exp) {
			return null;
		}

	}

	@Override
	public boolean testifexists(String table) {
		return (Boolean) emRep.createNativeQuery(
				"SELECT EXISTS (SELECT 1 FROM   information_schema.tables  WHERE  table_schema = 'cdrs_archives' AND    table_name = '"
						+ table + "')")
				.getSingleResult();
	}

	@Override
	public List<Object[]> getCDRBActivatonyTypeRegle(String date, String where) {
		// TODO Auto-generated method stub
		try {
			return emRep.createNativeQuery("Select * From cdrs_archives.cdrs_in_act_" + date + " " + where)
					.getResultList();
		} catch (PersistenceException exp) {
			return null;
		}
	}

	@Override
	public List<Object[]> getCDRRechargeByTypeRegle(String date, String where) {

		try {
			return emRep.createNativeQuery("Select * From cdrs_archives.cdrs_in_recharge" + date + " " + where)
					.getResultList();
		} catch (PersistenceException exp) {
			return null;
		}
	}

	@Override
	public List<Object[]> getCDRTransfertByTypeRegle(String date, String where) {
		// TODO Auto-generated method stub

		try {
			return emRep.createNativeQuery("Select * From cdrs_archives.cdrs_in_mgr_" + date + " " + where)
					.getResultList();
		} catch (PersistenceException exp) {
			return null;
		}
	}

	@Override
	public void decision(DecisionFraude df, String msisdn) {
		// TODO Auto-generated method stub
		emRep.persist(df);
		emRep.createNativeQuery("Delete FROM stat.alerte_fraude_seq  where msisdn = '" + msisdn + "'").executeUpdate();

	}
	
	@Override
	public void decisionnonfraudilentseq(Non_Frodulent_seq df, String msisdn) {
		// TODO Auto-generated method stub
		emRep.persist(df);
		emRep.createNativeQuery("Delete FROM stat.alerte_fraude_seq  where msisdn = '" + msisdn + "'").executeUpdate();

	}

	@Override
	public void updateDecision(DecisionFraude df) {
		// TODO Auto-generated method stub
		emRep.merge(df);
	}

	@Override
	public void addDecision(DecisionFraude df) {
		// TODO Auto-generated method stub
		emRep.persist(df);
	}

	@Override
	public List<DecisionFraude> getDecisionFraudes(String where) {

		return emRep.createQuery("Select d From DecisionFraude d  " + where).getResultList();
	}

	@Override
	public List<Object[]> getMSISDNMorethantwo() {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				"select msisdn,count(*) from (select msisdn,id_regle,count(*) as nombre from stat.alerte_fraude_seq group by msisdn,id_regle) a group by msisdn having count(*)>1")
				.getResultList();
	}

	@Override
	public ReglesFraude getRegleById(Integer id) {
		return emRep.find(ReglesFraude.class, id);
	}

	@Override
	public List<Object[]> getReglesByMSISDN(String msisdn) {
		return emRep.createNativeQuery(
				"select a.nom,a.id,b.date_debut,b.date_fin,b.date_detection,b.nb_occurance,b.datee from( select id_regle,msisdn,to_timestamp(min(date_debut), 'YYMMDDHH24MISS') as date_debut,to_timestamp(max(date_fin), 'YYMMDDHH24MISS')as date_fin,max(date_detection) as date_detection,count(*)as nb_occurance ,min(date_debut) as datee from stat.alerte_fraude_seq  where msisdn='"
						+ msisdn
						+ "' group by id_regle,msisdn ) as b,tableref.regles_fraudes as a where a.id=b.id_regle")
				.getResultList();

	}
//	@Override
//	public List<Object[]> getReglesByMSISDN(String msisdn) {
//		// TODO Auto-generated method stub
//		return emRep.createNativeQuery(
//				"select msisdn,count(*) from (select msisdn,id_regle,count(*) as nombre from stat.alerte_fraude_seq group by msisdn,id_regle) a group by msisdn having count(*)>1")
//				.getResultList();
//	}

//	@Override
//	public List<Object[]> getMSISDNsByRegle(Long regleId) {
//		return emRep.createNativeQuery("SELECT" + "        id_regle," + "        msisdn,"
//				+ "        to_timestamp(min(substr(date_debut,1,8))," + "        'YYMMDDHH24MISS') as date_debut,"
//				+ "        to_timestamp(max(substr(date_fin,1,8))," + "        'YYMMDDHH24MISS') as date_fin,"
//				+ "        max(date_detection) as date_detection," + "        count(*) as nb_occurance,"
//				+ "        min(substr(date_debut,1,8))" + "    FROM" + "        stat.alerte_fraude_seq " + "WHERE "
//				+ "id_regle = :regleId " + "GROUP BY " + "id_regle, " + "msisdn " + "ORDER BY " + "date_detection ASC")
//				.setParameter("regleId", regleId).getResultList();
//	}

	 
	@Override
	public List<Object[]> getMSISDNsByRegle(Long regleId) {
	    try {
	        String subQueryString = "(SELECT max(valeur) FROM stat.regle_parametres_valeur_seq as b "
	                + "WHERE a.msisdn= b.msisdn and  id_regle = " + regleId + " GROUP BY id_parametre , id_regle)";
	        System.out.println(subQueryString);

	        List<Object[]> maxvalue = (List<Object[]>) emRep.createNativeQuery("SELECT max(valeur) FROM stat.regle_parametres_valeur_seq "
							+ "WHERE  id_regle = " + regleId + " GROUP BY id_parametre , id_regle")
					.getResultList();
	        
	        if (maxvalue != null && maxvalue.size() <= 1) {
	            return emRep.createNativeQuery("SELECT"
	                    + "    id_regle," + "    msisdn,"
	                    + "    to_timestamp(min(substr(date_debut,1,8)), 'YYMMDDHH24MISS') as date_debut,"
	                    + "    to_timestamp(max(substr(date_fin,1,8)), 'YYMMDDHH24MISS') as date_fin,"
	                    + "    max(date_detection) as date_detection," + "    count(*) as nb_occurance,"
	                    + "    min(substr(date_debut,1,8)) as min_date_debut," + "    " + subQueryString
	                    + " as maxValeur " + "FROM stat.alerte_fraude_seq a " + "WHERE id_regle = :regleId "
	                    + "GROUP BY id_regle, msisdn " + "ORDER BY date_detection ASC")
	                    .setParameter("regleId", regleId)
	                    .getResultList();
	        } else {
	            return emRep.createNativeQuery("SELECT"
	                    + "    id_regle," + "    msisdn,"
	                    + "    to_timestamp(min(substr(date_debut,1,8)), 'YYMMDDHH24MISS') as date_debut,"
	                    + "    to_timestamp(max(substr(date_fin,1,8)), 'YYMMDDHH24MISS') as date_fin,"
	                    + "    max(date_detection) as date_detection," + "    count(*) as nb_occurance,"
	                    + "    min(substr(date_debut,1,8)) as min_date_debut" + " FROM stat.alerte_fraude_seq a "
	                    + "WHERE id_regle = :regleId " + "GROUP BY id_regle, msisdn " + "ORDER BY date_detection ASC")
	                    .setParameter("regleId", regleId)
	                    .getResultList();
	        }
	    } catch (Exception e) {
	        // Log or handle the exception as needed
	        e.printStackTrace();
	        // You might want to throw a custom exception or handle the error in a way suitable for your application
	        throw new RuntimeException("Error in getMSISDNsByRegle", e);
	    }
	}


	@Override
	public FiltresFraude getFiltreById(String id) {
		// TODO Auto-generated method stub
		return (FiltresFraude) emRep.createQuery("Select f From FiltresFraude f where f.filtre='" + id + "'")
				.getSingleResult();
	}

	@Override
	public List<Object[]> getLocationDuree(String msisdn) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select location,sum(duree),sum(nbAppel) from StatFraudeMsisdn  where msisdn='"
				+ msisdn + "' group by location").getResultList();
	}

	@Override
	public List<DecisionFraude> FindDecisionByMsisdn(String msisdn) {
		List<DecisionFraude> d = new ArrayList<DecisionFraude>();
		try {
			d = emRep.createQuery("Select d From DecisionFraude d where d.msisdn='" + msisdn + "' ").getResultList();

		} catch (javax.persistence.NoResultException exp) {
			return d;
		}
		return d;
	}

	@Override
	public Integer FindDecisionsByMsisdn(String msisdn) {

		return (Integer) emRep.createQuery("Select count(*) From DecisionFraude d where d.msisdn='" + msisdn + "' ")
				.getSingleResult();

	}

	@Override
	public void updateDecision1(String msisdn) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object[]> getAllDecisionFraudes(String string) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				"Select id_regle,msisdn,to_timestamp(min(date_debut), 'YYMMDDHH24MISS') as date_debut,to_timestamp(max(date_fin), 'YYMMDDHH24MISS')as date_fin,max(date_detection) as date_detection,count(*)as nb_occurance ,min(date_debut) From stat.alerte_fraude_seq   "
						+ string)
				.getResultList();
	}

	@Override
	public List<Object[]> getAllDecisionFraudesBY(String string) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery(
				"select a.nom,a.id,b.date_debut,b.date_fin,b.date_detection,b.nb_occurance from( Select id_regle,msisdn,to_timestamp(min(date_debut), 'YYMMDDHH24MISS') as date_debut,to_timestamp(max(date_fin), 'YYMMDDHH24MISS')as date_fin,max(date_detection) as date_detection,count(*)as nb_occurance ,min(date_debut) From stat.alerte_fraude_seq   "
						+ string + ") as b,tableref.regles_fraudes as a where a.id=b.id_regle")
				.getResultList();
	}

	@Override
	public StatFraudeMsisdn getInfo(String msisdn) {
		// TODO Auto-generated method stub
		try {
			return (StatFraudeMsisdn) emRep
					.createQuery("Select s from  StatFraudeMsisdn s where msisdn='" + msisdn + "' ").getSingleResult();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new StatFraudeMsisdn();
		}
	}

	@Override
	public List<DetailsMail> getAlerteFraudeByRegleMail(ReglesFraude rg) {
		// TODO Auto-generated method stub
		// return emRep.createNativeQuery(" Select id,
		// id_regle,adresse_mail,type from tableref.details_mail where id_regle
		// ="+rg.getId()).getResultList();

		return emRep.createQuery(" Select s from DetailsMail s where s.Id_Reg =" + rg.getId()).getResultList();

	}

	@Override
	public void addAlerteMail(DetailsMail dt) {
		emRep.persist(dt);
	}

	@Override
	public void addSMS(DetailsSms sms) {
		emRep.persist(sms);
	}

	@Override
	public List<DetailsSms> getAlerteFraudeByRegleSms(ReglesFraude rg) {
		// TODO Auto-generated method stub
		return emRep.createQuery(" Select d from DetailsSms d where d.Id_Reg =" + rg.getId()).getResultList();

	}

	@Override
	public void addAlerteSms(DetailsSms dt, Integer id) {
		// TODO Auto-generated method stub
		System.out.println("INSERT INTO tableref.details_sms (msisdn,type,id_regle) values('" + dt.getMsisdn() + "','"
				+ dt.getType() + "'," + id + ")");
		emRep.createNativeQuery("INSERT INTO tableref.details_sms (msisdn,type,id_regle) values('" + dt.getMsisdn()
				+ "','" + dt.getType() + "'," + id + ")").executeUpdate();

	}

	@Override
	public void ModifierAlerteMail(DetailsMail dt) {
		// TODO Auto-generated method stub
		emRep.merge(dt);
	}

	@Override
	public List<DetailsMail> getAlerteFraudeByRegleMailselect(DetailsMail rg) {
		// TODO Auto-generated method stub
		// return emRep.createNativeQuery(" Select id,adresse_mail,type,id_regle
		// from tableref.details_mail where id ="+rg.getId()).getResultList();

		return emRep.createQuery("select s from DetailsMail s where s.id=" + rg.getId()).getResultList();

	}

	@Override
	public void deleteDetailMails(DetailsMail t) {
		// TODO Auto-generated method stub

		// emRep.createNativeQuery(" delete from tableref.details_mail where id
		// ="+t.getId()).executeUpdate();

		emRep.remove(emRep.contains(t) ? t : emRep.merge(t));
	}

	@Override
	public void deleteDetailSms(DetailsSms dt) {
		// TODO Auto-generated method stub
		// emRep.createNativeQuery(" delete from tableref.details_sms where
		// id_regle ="+dt.getId()).executeUpdate();
		emRep.remove(emRep.contains(dt) ? dt : emRep.merge(dt));
	}

	@Override
	public List<Object[]> getAllParametre(Object[] obj) {
		Query jpql = emRep.createNativeQuery(
				"SELECT name, msisdn, to_timestamp(date_debut, 'YYMMDDHH24MISS') as debut, to_timestamp(date_fin, 'YYMMDDHH24MISS') as fin, valeur, id_parametre FROM stat.regle_parametres_valeur_seq as a left JOIN  etl.etl_flows as b on ( a.id_parametre = b.id ) where "
						+ " msisdn= '" + obj[1].toString() + "'");
		return jpql.getResultList();

	}

	@Override
	public List<Object[]> getLocationMsisdn(String msisdn, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> GetMo(Object[] obj, String date) {

		Query jpql = emRep.createNativeQuery(
				"select DISTINCT name_base from etl.etl_field_flow where id_flow in (select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5] + "' )) AND show=true");

		Query jpql2 = emRep.createNativeQuery(
				"select name_base from etl.etl_field_flow WHERE id_principal_field= (select id from etl.etl_principal_fields where flow_id in (select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5]
						+ "' )) and fraude= true and (fieldname='calling_number'or fieldname='imsi')) AND id_flow =(select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5] + "' )) Limit 1");

		Query nameTable = emRep.createNativeQuery("select table_name from etl.etl_flows "
				+ " id in ( select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id='"
				+ obj[5] + "'))");

		Object obj3 = nameTable.getSingleResult();
		System.out.println(obj3.toString());
		Object obj2 = jpql2.getSingleResult();
		System.out.println(obj2.toString());

		String query = "Select ";
		int i = 0;

		List<Object> results = jpql.getResultList();

		for (Object result : results) {
			i++;
			System.out.println(result.toString());
			if (i == results.size()) {
				query += result.toString() + " from cdrs_archives." + obj3.toString() + date + " where "
						+ obj2.toString() + " = '" + obj[1] + "' limit 50 ";
			} else
				query += result.toString() + " ,";

		}
		System.out.println(query);
		Query res = emRep.createNativeQuery(query);
		List<Object[]> cdrs = res.getResultList();
		for (Object cdr : cdrs) {
			int j = 0;
			System.out.println(cdr.toString());
		}

		return res.getResultList();
	}

	@Override
	public List<Object[]> GetMT(Object[] obj, String date) {
		Query jpql = emRep.createNativeQuery(
				"select DISTINCT name_base from etl.etl_field_flow where id_flow in (select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5] + "' )) AND show = true");

		Query jpql2 = emRep.createNativeQuery(
				"select name_base from etl.etl_field_flow WHERE id_principal_field= (select id from etl.etl_principal_fields where flow_id in (select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5]
						+ "' )) and fraude= true and (fieldname='called_number'or fieldname='imsi')) AND id_flow =(select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5] + "' )) Limit 1");

		Query nameTable = emRep.createNativeQuery(
				"select table_name from etl.etl_flows where id in ( select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id='"
						+ obj[5] + "'))");

		Object obj3 = nameTable.getSingleResult();
		System.out.println(obj3.toString());
		Object obj2 = jpql2.getSingleResult();
		System.out.println(obj2.toString());

		String query = "Select ";
		int i = 0;

		List<Object> results = jpql.getResultList();

		for (Object result : results) {
			i++;
			System.out.println(result.toString());
			if (i == results.size()) {
				query += result.toString() + " from cdrs_archives." + obj3.toString() + date + " where "
						+ obj2.toString() + " = '" + obj[1] + "' limit 50 ";
			} else
				query += result.toString() + " ,";

		}
		System.out.println(query);
		Query res = emRep.createNativeQuery(query);
		List<Object[]> cdrs = res.getResultList();
		for (Object cdr : cdrs) {
			int j = 0;
			System.out.println(cdr.toString());
		}

		return res.getResultList();
	}

	@Override
	public List<String> GetNameField(Object[] obj) {

		Query jpql = emRep.createNativeQuery(
				"select DISTINCT name_base from etl.etl_field_flow where id_flow in (select id_flow from etl.etl_proc_flow where id_proc in (select id_proc from etl.etl_flows where id= '"
						+ obj[5] + "' )) AND show=true ");
		List<String> results = jpql.getResultList();
		return results;
	}

	@Override
	public List<OperatorDeployment> getoperator() {
		// TODO Auto-generated method stub
		return emRep.createQuery("select o from OperatorDeployment o").getResultList();
	}

	@Override
	public void addlog(TblLog log) {
		// TODO Auto-generated method stub
		emRep.persist(log);
	}

	@Override
	public List<TypeDestination> getAllTypedest() {
		return emRep.createQuery("Select f From TypeDestination f").getResultList();
	}

	@Override
	public List<PlanTarifaire> getAllPlanTarifaire() {
		return emRep.createQuery("Select f From PlanTarifaire f").getResultList();
	}

	@Override
	public List<Offre> getAllOffre() {
		return emRep.createQuery("Select f From Offre f").getResultList();
	}

	@Override
	public List<ReglesFraude> getAllRules() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select r From ReglesFraude r where r.id<>0 ORDER BY r.nom").getResultList();
	}
	
	@Override
	public List<Object[]> getAllRulesWithMaxDateDetection() {
	    String query = "SELECT" + 
	    		"    r.id,r.nom," + 
	    		"    (SELECT max (a.date_detection ) FROM stat.alerte_fraude_seq a WHERE a.id_regle = r.id ) as max_date_detection" + 
	    		"    FROM" + 
	    		"    tableref.regles_fraudes r" + 
	    		"    WHERE" + 
	    		"    r.id <> 0" + 
	    		"    ORDER BY" + 
	    		"    r.nom" ;

	    return emRep.createNativeQuery(query).getResultList();
	}







	@Override
	public void addReglewithparamandfiltre(YourDataWrapper dataWrapper) {
		try {
			ReglesFraude regle = dataWrapper.getRegle();
			List<ParametresReglesFraude> params = dataWrapper.getParams();
			List<FiltresReglesFraude> filters = dataWrapper.getFilters();
			regle.setEtat("A");
			regle.setDateModif(new Timestamp(new Date().getTime()));

			dataWrapper.setRegle(regle);

			// Step 1: Persist the ReglesFraude entity to generate an ID
			emRep.persist(regle);

			// Now, regle will have a generated ID

			// Step 2: Set the ReglesFraude instance for each ParametresReglesFraude
			if (params != null && !params.isEmpty()) {
				params.forEach(param -> param.setRegle(regle));

				// Step 3: Persist the associated ParametresReglesFraude
				params.forEach(emRep::persist);
			}

			// Step 4: Set the ReglesFraude instance for each FiltresReglesFraude if filters
			// are provided
			if (filters != null && !filters.isEmpty()) {
				filters.forEach(filter -> filter.setRegle(regle));

				// Step 5: Persist the associated FiltresReglesFraude
				filters.forEach(emRep::persist);
			}
			// Optionally, you can set other properties of regle here

			// Finally, you can persist the updated ReglesFraude instance
			emRep.persist(regle);
		} catch (Exception e) {
			// Handle the exception and possibly log or throw a custom exception.
		}
	}

//	public void addReglewithparamandfiltre(YourDataWrapper dataWrapper) {
//	    try {
//	        ReglesFraude regle = dataWrapper.getRegle();
//	        List<ParametresReglesFraude> params = dataWrapper.getParams();
//	        List<FiltresReglesFraude> filters = dataWrapper.getFilters();
//
//	       
//	        // Set the ReglesFraude instance for each ParametresReglesFraude
//	        params.stream().peek(param -> param.setRegle(regle)).forEach(emRep::persist);
//
//	        // Set the ReglesFraude instance for each FiltresReglesFraude
//	        filters.stream().peek(filter -> filter.setRegle(regle)).forEach(emRep::persist);
//
//	        regle.setListe_parameters(params);
//	        regle.setListe_filters(filters);
//	        regle.setEtat("Activate");
//	       
//	        
//
//	        // Persist the ReglesFraude instance
//	        emRep.persist(regle);
//
//	    } catch (Exception e) {
//	        // Handle the exception and possibly log or throw a custom exception.
//	    }
//	}

	@Override
	public void updaterule(Integer id, ReglesFraude regle) {
		ReglesFraude reglesFraude = emRep.find(ReglesFraude.class, id);
		if (reglesFraude != null) {
			// Toggle the "etat" value
			if ("A".equals(reglesFraude.getEtat())) {
				reglesFraude.setEtat("D");
			} else if ("D".equals(reglesFraude.getEtat())) {
				reglesFraude.setEtat("A");
			}
			emRep.merge(reglesFraude);

		}

	}

//	@Override
//	public void updateregle(Integer id, ReglesFraude updaterequest, Integer id_flow) {
//		ReglesFraude existingregle = emRep.find(ReglesFraude.class, id);
//		if (existingregle != null) {
//
////			CategoriesFraude updatedCategorie = (CategoriesFraude) emRep
////					.createQuery("select c from CategoriesFraude c where c.id=" + updaterequest.getCategorie().getId())
////					.getSingleResult();
//
//			// existingregle.setCategorie(updatedCategorie);
//			Flow updatedFlux = (Flow) emRep.createQuery("select f from  Flow f where f.id=" + id_flow)
//					.getSingleResult();
//
//			// System.out.println(updatedFlux.getFlowName());
//			existingregle.setFlux(updatedFlux);
//			existingregle.setNom(updaterequest.getNom());
//
//			emRep.merge(existingregle);
//
//		}
//	}

	@Override
	public void updateblok(Integer id, ReglesFraude regle) {
		ReglesFraude reglesFraude = emRep.find(ReglesFraude.class, id);
		if (reglesFraude != null) {
			// Toggle the "etat" value
			if ("1".equals(reglesFraude.getType())) {
				reglesFraude.setType("0");
			} else if ("0".equals(reglesFraude.getType())) {
				reglesFraude.setType("1");
			}
			emRep.merge(reglesFraude);

		}

	}

	@Override
	public void updatefilter(Integer id, FiltresReglesFraude updateRequest) {

		FiltresReglesFraude existingFilter = emRep.find(FiltresReglesFraude.class, id);

		if (existingFilter != null) {
//			existingFilter.setFiltreFraude(updateRequest.getFiltreFraude());
			existingFilter.setInegal(updateRequest.getInegal());
			existingFilter.setNomUtlisateur(updateRequest.getNomUtlisateur());
			existingFilter.setVdef(updateRequest.getVdef());
			existingFilter.setVegal(updateRequest.getVegal());
			existingFilter.setVlike(updateRequest.getVlike());
			existingFilter.setVnotlike(updateRequest.getVnotlike());

			emRep.merge(existingFilter);
		}

	}

	@Override
	public void updateparam(Long id, ParametresReglesFraude updateRequest) {

		ParametresReglesFraude existingParam = emRep.find(ParametresReglesFraude.class, id);

		if (existingParam != null) {

			existingParam.setVegal(updateRequest.getVegal());
			existingParam.setNomUtilisateur(updateRequest.getNomUtilisateur());
			existingParam.setVmax(updateRequest.getVmax());
			existingParam.setVmin(updateRequest.getVmin());

			emRep.merge(existingParam);
		}

	}

	@Override
	public void editregle(Integer id, ReglesFraude updaterequest) {
		ReglesFraude existingregle = emRep.find(ReglesFraude.class, id);
		if (existingregle != null) {

			CategoriesFraude categorie = emRep.find(CategoriesFraude.class, updaterequest.getCategorie().getId());
			// Flow flux = emRep.find(Flow.class, updaterequest.getFlux().getId());

			existingregle.setCategorie(categorie);

			// System.out.println(updatedFlux.getFlowName());
			// existingregle.setFlux(flux);
			existingregle.setNom(updaterequest.getNom());
			existingregle.setDescription(updaterequest.getDescription());

			emRep.merge(existingregle);

		}
	}

	@Override
	public List<DetailsSms> getSMSByRegleId(Integer idRegle) {
		TypedQuery<DetailsSms> query = emRep.createQuery(
				"SELECT epf FROM DetailsSms epf JOIN FETCH epf.Id_Reg WHERE epf.Id_Reg.id = :idRegle",
				DetailsSms.class);
		query.setParameter("idRegle", idRegle);
		return query.getResultList();
	}

	@Override
	public List<DetailsMail> getMailsByRegleId(Integer idRegle) {
		TypedQuery<DetailsMail> query = emRep.createQuery(
				"SELECT epf FROM DetailsMail epf JOIN FETCH epf.Id_Reg WHERE epf.Id_Reg.id = :idRegle",
				DetailsMail.class);
		query.setParameter("idRegle", idRegle);
		return query.getResultList();
	}

	@Override
	public List<ParametresReglesFraude> getParamsByRegleId(Integer idRegle) {
		TypedQuery<ParametresReglesFraude> query = emRep.createQuery(
				"SELECT epf FROM ParametresReglesFraude epf JOIN FETCH epf.regle WHERE epf.regle.id =:idRegle",
				ParametresReglesFraude.class);
		query.setParameter("idRegle", idRegle);
		return query.getResultList();
	}

	@Override
	@Transactional
	public List<FiltresReglesFraude> getFiltersByRegleId(Integer idRegle) {
		TypedQuery<FiltresReglesFraude> query = emRep.createQuery(
				"SELECT epf FROM FiltresReglesFraude epf JOIN FETCH epf.regle WHERE epf.regle.id =:idRegle",
				FiltresReglesFraude.class);
		query.setParameter("idRegle", idRegle);
		return query.getResultList();
	}

	@Override
	public void addparam(ParametresReglesFraude param) {
		// TODO Auto-generated method stub
		emRep.persist(param);
	}

	@Override
	public void addfilter(FiltresReglesFraude filter) {
		emRep.persist(filter);

	}

	@Override
	public List<ReglesFraude> getRegleByid(Integer id) {
		TypedQuery<ReglesFraude> query = emRep.createQuery("SELECT epf FROM ReglesFraude epf WHERE epf.id = :id",
				ReglesFraude.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public void deleteFiltre(Integer id) {
		FiltresReglesFraude filter = emRep.find(FiltresReglesFraude.class, id);
		if (filter != null) {
			filter.setFiltreFraude(null);
			filter.setRegle(null);
			emRep.remove(filter);
		}
	}

	@Override
	public void deleteMail(Integer id) {
		DetailsMail mail = emRep.find(DetailsMail.class, id);
		if (mail != null) {

			emRep.remove(mail);
		}
	}

	@Override
	public void deleteSMS(Integer id) {
		DetailsSms sms = emRep.find(DetailsSms.class, id);
		if (sms != null) {
			// filter.setFiltreFraude(null);
			// filter.setRegle(null);
			emRep.remove(sms);
		}
	}

	@Override
	public void deleteParam(Long id) {
		ParametresReglesFraude param = emRep.find(ParametresReglesFraude.class, id);
		if (param != null) {
			ReglesFraude regle = param.getRegle();
			if (regle != null) {
				regle.getListe_parameters().remove(param);
			}

			emRep.remove(param);
		}

	}

	@Override
	public List<DecisionFraude> getAllDecisionFraudes() {

		return emRep.createQuery("SELECT d From DecisionFraude d").getResultList();
	}

	@Override
	public List<Object[]> getMsisdnAndRegleCount() {
		String jpql = "select msisdn,count(*) from (select msisdn,id_regle,count(*) as nombre from stat.alerte_fraude_seq group by msisdn,id_regle) a group by msisdn having count(*)>1";
		TypedQuery<Object[]> query = (TypedQuery<Object[]>) emRep.createNativeQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<DecisionFraude> findDecisionsInDateRange(java.sql.Date startDate, java.sql.Date endDate) {
		TypedQuery<DecisionFraude> query = emRep.createQuery(
				"SELECT d FROM DecisionFraude d WHERE d.dateDecision BETWEEN :startDate AND :endDate",
				DecisionFraude.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	@Override
	public List<Object[]> getDetailsWarning(String msisdn, Integer id_regle) {
		String queryString = "SELECT max(name) as maxName, max(msisdn) as maxMsisdn, "
				+ "min(date_debut) as debut, max(date_fin) as fin, "
				+ "avg(valeur) as avgValeur, min(valeur) as minValeur, "
				+ "max(valeur) as maxValeur, id_parametre,id_regle " + "FROM stat.regle_parametres_valeur_seq as a "
				+ "LEFT JOIN etl.etl_flows as b ON a.id_parametre = b.id " + "WHERE msisdn = :msisdn AND id_regle ="
				+ id_regle + " " + "GROUP BY id_parametre,id_regle";

		TypedQuery<Object[]> typedQuery = (TypedQuery<Object[]>) emRep.createNativeQuery(queryString);
		typedQuery.setParameter("msisdn", msisdn);

		return typedQuery.getResultList();

	}

//	@Override
//	public List<Object[]> chartbyregle(List<Long> idRegles, Timestamp startDate, Timestamp endDate) {
//		List<String> idStrings = idRegles.stream().map(String::valueOf).collect(Collectors.toList());
//
//		// Join the List<String> into a comma-separated string
//		String commaSeparatedIds = String.join(",", idStrings);
//
//		String queryString = "SELECT COUNT(DISTINCT msisdn) as msisdnCount, t2.nom "
//				+ "FROM stat.alerte_fraude_seq AS t1, tableref.regles_fraudes AS t2 " + "WHERE t1.id_regle IN ("
//				+ commaSeparatedIds + ") " + "AND t1.date_detection BETWEEN"
//				+ " date_detection Between to_timestamp(':startDate 000000','yymmdd hh24miss') And to_timestamp(':endDate 235959','yymmdd hh24miss')  AND  "
//				+ "GROUP BY t2.nom";
//
//		Query query = emRep.createNativeQuery(queryString);
//		query.setParameter("startDate", startDate);
//		query.setParameter("endDate", endDate);
//
//		return query.getResultList();
//	}

	@Override
	public List<Object[]> chartbyregle(String idRegles, String startDate, String endDate) {

		String queryString = "SELECT COUNT(DISTINCT msisdn) as msisdnCount, t2.nom "
				+ "FROM stat.alerte_fraude_seq AS t1, tableref.regles_fraudes AS t2 " + "WHERE t1.id_regle = t2.ID "
				+ "        AND t1.id_regle  IN (" + idRegles + ") " + "AND t1.date_detection BETWEEN to_timestamp('"
				+ startDate + " 000000', 'yymmdd hh24miss') AND to_timestamp('" + endDate
				+ " 235959', 'yymmdd hh24miss') " + "GROUP BY t2.nom";
		TypedQuery<Object[]> query = (TypedQuery<Object[]>) emRep.createNativeQuery(queryString);

//	    Query query = emRep.createNativeQuery(queryString);

		return query.getResultList();
	}

}
