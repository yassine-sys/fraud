package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.interfaces.StatFraudeMsisdnRemote;

@Stateless
public class StatFraudeMsisdnService implements StatFraudeMsisdnRemote {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Object[]> getStatFraude(String x, String y, List<String> Where) {
		// TODO Auto-generated method stub
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		System.out.println("Select " + x + " ," + y + " from StatFraudeMsisdn where" + where + " group by " + x
				+ " order by " + x);
		return em.createQuery(
				"Select " + x + " ," + y + " from StatFraudeMsisdn where" + where + " group by " + x + " order by " + x)
				.getResultList();
	}

	@Override
	public List<Object[]> getStatFraudeParOperateur(String x, String y, List<String> Where) {
		// TODO Auto-generated method stub
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		return em.createNativeQuery(
				"Select 'Sotelma' as deuxG, count(distinct msisdn) as nb_msisdn1  FROM stat.stat_fraude_msisdn   where  (msisdn like '2236%' or msisdn like '22385%' or msisdn like '22386%' or msisdn like '22386%' or msisdn like '22387%' or msisdn like '22388%' or msisdn like '22389%' or msisdn like '22395%' or msisdn like '22396%' or msisdn like '22397%' or msisdn like '22398%' or msisdn like '22399%') and "
						+ where
						+ " UNION Select 'Orange Mali' as troisG ,count(distinct msisdn) as nb_msisdn1  From stat.stat_fraude_msisdn  where (msisdn like '2237%' or msisdn like '22380%' or msisdn like '22381%' or msisdn like '22382%' or msisdn like '22383%' or msisdn like '22384%' or msisdn like '22384%' or msisdn like '22390%' or msisdn like '22391%' or msisdn like '22392%' or msisdn like '22393%' or msisdn like '22394%') and "
						+ where
						+ " UNION Select 'ATEL' as autres ,count(distinct msisdn)  as nb_msisdn1  From stat.stat_fraude_msisdn where msisdn like '2235%' and "
						+ where + " ;")
				.getResultList();

	}
	
	@Override
	public List<Object[]> getStatFraudeAlert(String x, String y, List<Integer> lst_cat, List<String> Where) {
		// TODO Auto-generated method stub
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}

		String where1 = lst_cat.get(0).toString();
		if (lst_cat.size() >= 2) {
			for (int i = 1; i < lst_cat.size(); i++) {
				where1 = where1 + " , " + lst_cat.get(i).toString();
			}
		}

		return em.createNativeQuery("Select " + x + " ," + y
				+ " from stat.alerte_fraude_seq where id_regle in ( select id FROM tableref.regles_fraudes where id_categorie in ( "
				+ where1 + ")) and  " + where + " Group by " + x).getResultList();
	}

	@Override
	public List<Object[]> getStatFraudeAlertPie(String x, String y, List<Integer> lst_cat, List<String> Where) {
		// TODO Auto-generated method stub
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}

		String where1 = lst_cat.get(0).toString();
		if (lst_cat.size() >= 2) {
			for (int i = 1; i < lst_cat.size(); i++) {
				where1 = where1 + " , " + lst_cat.get(i).toString();
			}
		}

		return em.createNativeQuery("SELECT COUNT\r\n" + "	(\r\n" + "	DISTINCT ( msisdn )),\r\n"
				+ "	t3.nom_categorie \r\n" + "FROM\r\n" + "	stat.alerte_fraude_seq AS t1,\r\n"
				+ "	tableref.regles_fraudes AS t2,\r\n" + "	tableref.categories_fraudes t3 \r\n" + "WHERE\r\n"
				+ "	t1.id_regle = t2.ID \r\n" + "	AND t2.id_categorie = t3.ID \r\n" + "	AND t3.ID IN (" + where1
				+ ") AND " + where + " Group by 	t3.nom_categorie " + x).getResultList();
	}
	
	
	@Override
	public List<Object[]> getStatFraude2(String x, String y, List<String> Where) {
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		System.out.println("Select coalesce (" + x + ", '0') as locat," + y + " from stat.stat_fraude_msisdn t1 ,tableref.destination t2 where" + where
				+ " and t2.dest is not null and t2.dest=t1.id group by " + x + " order by " + x);
		return em.createNativeQuery(
				"Select coalesce (" + x + ", '0') as locat," + y + " from stat.stat_fraude_msisdn t1 ,tableref.destination t2 where" + where
				+ " and t2.dest is not null and t2.dest=t1.id group by " + x + " order by " + x)
				.getResultList();
	}

	@Override
	public List<Object[]> getStatFraud(String x, String y, List<String> Where) {
		// TODO Auto-generated method stub
		String where = Where.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		System.out.println("Select coalesce( (" + x + ", '0')) as locat," + y + " from StatFraudeMsisdn where" + where
				+ "   group by " + x + " order by " + x );
		return em.createQuery(
				"Select coalesce(" + x + ", '0') as locat," + y + " from StatFraudeMsisdn where " + where + "  group by " + x + " order by " + y +" DESC").setMaxResults(10).getResultList();
	
	}
	
	
	@Override
	public List<Object[]> getLoct(String loc) {
		
		
		
		System.out.println("************************************************************");
		return em.createNativeQuery("select cell_nom, altitude,latitude from tableref.cell where to_number (lac,'999999')||'-'||to_number (cell_id,'99999999')= '"+loc+"' and ( altitude is not null and latitude is not null )").getResultList();
	}


	@Override
	public List<Object[]> getLoctMssdin(float att,float lng) {
		return em.createNativeQuery("select msisdn,sum(nb_appel),sum(duree) as d from stat.stat_fraude_msisdn where location in (select to_number (lac,'999999')||'-'||to_number (cell_id,'99999999') from tableref.cell WHERE(round(latitude\\:\\:numeric,4) =round ( "+att+",4) or round(latitude\\:\\:numeric,4) =round ( "+att+",4)-0.0001 or round(latitude\\:\\:numeric,4) =round ( "+att+",4)+0.0001) AND (round(altitude\\:\\:numeric,4) = round ("+lng+",4) or round(altitude\\:\\:numeric,4) = round ("+lng+",4)-0.0001 or round(altitude\\:\\:numeric,4) = round ("+lng+",4)+0.0001 ))group by msisdn order by d DESC" + "").getResultList();
	}


	@Override
	public List<Object[]> getLoctMssdin2(float att, float lng) {
		return em.createNativeQuery("select msisdn,sum(nb_appel),sum(duree) as d from stat.stat_fraude_msisdn where location in (select to_number (lac,'999999')||'-'||to_number (cell_id,'99999999') from tableref.cell WHERE(round(latitude\\:\\:numeric,4) =round ( "+att+",4) or round(latitude\\:\\:numeric,4) =round ( "+att+",4)-0.0001 or round(latitude\\:\\:numeric,4) =round ( "+att+",4)+0.0001) AND (round(altitude\\:\\:numeric,4) = round ("+lng+",4) or round(altitude\\:\\:numeric,4) = round ("+lng+",4)-0.0001 or round(altitude\\:\\:numeric,4) = round ("+lng+",4)+0.0001 ))group by msisdn order by d DESC" + "").getResultList();
	}
	
	
	@Override
	public List<Object[]> getStatFraudeMsisdn(String x, String y, List<String> Where, List<String> Where2) {
		String where = Where.get(0);
		String where2 = Where2.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		if (Where2.size() >= 2) {
			for (int i = 1; i < Where2.size(); i++) {
				where2 = where2 + " AND " + Where2.get(i);
			}
		}
		System.out.println("Select coalesce (" + x + ", '0') as locat," + y + " from StatFraudeMsisdn where" + where
				+ " and location is notnull group by " + x + " order by " + x);
		return em 
				.createNativeQuery("Select " + x + " ," + y + " from stat.stat_fraude_msisdn where" + where
						+ " group by " + x + " Union select " + x + ", 0, 0 from stat.decision_fraude where " + where2
						+ " and " + x + " not in (select msisdn from stat.stat_fraude_msisdn) group by " + x)
				.getResultList();
	}

	@Override
	public List<Object[]> getStatFraudeMsisdnLine(String x, String y, List<String> Where, List<String> Where2) {
		String where = Where.get(0);
		String where2 = Where2.get(0);
		if (Where.size() >= 2) {
			for (int i = 1; i < Where.size(); i++) {
				where = where + " AND " + Where.get(i);
			}
		}
		if (Where2.size() >= 2) {
			for (int i = 1; i < Where2.size(); i++) {
				where2 = where2 + " AND " + Where2.get(i);
			}
		}
		System.out.println("select date_detection,count (distinct (msisdn)) from (Select " + x + " ," + y
				+ " from stat.decision_fraude where msisdn in ( select distinct (msisdn) from stat.deblocage WHERE retour = 1 ) "
				+ "and decision = 'D'and nom_utilisateur='sys' and " + where2
				+ "  group by msisdn) t1 GROUP BY date_detection");
		return em.createNativeQuery("select date_detection,count (distinct (msisdn)) from (Select " + x + " ," + y
				+ " from stat.decision_fraude where msisdn in ( select distinct (msisdn) from stat.deblocage WHERE retour = 1 ) "
				+ "and decision = 'D'and nom_utilisateur='sys' and " + where2
				+ "  group by msisdn ) t1 GROUP BY date_detection").getResultList();
	}


}
