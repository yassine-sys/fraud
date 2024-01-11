package com.fraude.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.Config_ASCII;
import com.fraude.entities.Config_ASN1;
import com.fraude.entities.Convertisseur;
import com.fraude.entities.EtlBinType;
import com.fraude.entities.EtlDataType;
import com.fraude.entities.EtlFieldFlow;
import com.fraude.entities.EtlFieldProc;
import com.fraude.entities.EtlFieldService;
import com.fraude.entities.EtlPrincipalFields;
import com.fraude.entities.EtlProc;
import com.fraude.entities.EtlProcFlow;
import com.fraude.entities.EtlServiceConverter;
import com.fraude.entities.EtlTypeFlow;
import com.fraude.entities.Flow;
import com.fraude.entities.Noeud;
import com.fraude.entities.Repertoire;
import com.fraude.entities.Service;
import com.fraude.interfaces.ConverterInterface;
import com.fraude.interfaces.EtlPrincipalFieldsInterface;
import com.fraude.interfaces.FlowInterface;
import com.fraude.interfaces.NoeudInterface;
import com.fraude.interfaces.RepositoryInterface;
import com.fraude.interfaces.ServiceInterface;
import com.fraude.managedBeans.config.ContextMenuViewM;
import com.fraude.managedBeans.config.StatMbean;



@Stateless
public class FlowService implements FlowInterface, NoeudInterface, RepositoryInterface, ServiceInterface,
		ConverterInterface, EtlPrincipalFieldsInterface {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<EtlPrincipalFields> getPrincipalFieldsByFlow(Flow f) {
		// TODO Auto-generated method stub
		return em
				.createQuery(
						"select pf  from  EtlPrincipalFields pf where pf.flow.id = " + f.getId() + "and pf.used=false")
				.getResultList();
	}

	@Override
	public void updateListPrincipalFields(Flow f) {
		// TODO Auto-generated method stub

		em.createQuery("update EtlPrincipalFields pf set pf.used='false' where pf.flow.id= " + f.getId())
				.executeUpdate();
	}

	@Override
	public void deletePrincipalField(EtlPrincipalFields principal_field) {
		// TODO Auto-generated method stub
		em.remove(principal_field);
	}

	@Override
	public void updatePrincipalField(EtlPrincipalFields principal_field) {
		// TODO Auto-generated method stub
		em.merge(principal_field);
	}

	@Override
	public List<Flow> getAllFlows() {
		// TODO Auto-generated method stub
		return em.createQuery("Select f from Flow f where f.flow_type != 3").getResultList();
	}

	@Override
	public Flow getFlowByName(String nameFlow) {
		// TODO Auto-generated method stub
		return (Flow) em.createQuery("Select f from Flow f where f.name= " + nameFlow);
	}

	@Override
	public List<EtlProcFlow> getAllprocflowbyidprocs(Long id_proc) {
		return em.createQuery("select u from EtlProcFlow u where id_proc=" + id_proc).getResultList();
	}

	@Override
	public List<EtlProc> getAllproc() {
		return em.createQuery("select u from EtlProc u where u.proc_type='REC'").getResultList();
	}

	@Override
	public List<EtlFieldFlow> getAllChamps(Flow f) {
		// TODO Auto-generated method stub
		return em.createQuery("Select n from EtlFieldFlow n where id_flow = " + f.getId()).getResultList();
	}

	@Override
	public List<EtlFieldFlow> getAllChamps() {
		// TODO Auto-generated method stub
		return em.createQuery("Select n from EtlFieldFlow n ").getResultList();
	}

	@Override
	public List<Noeud> getAllNoeud() {
		// TODO Auto-generated method stub
		return em.createQuery("Select n from Noeud n").getResultList();
	}

	@Override
	public void addNoeud(Noeud n) {
		// TODO Auto-generated method stub
		em.persist(n);
	}

	@Override
	public void deleteNoeud(Noeud n) {
		// TODO Auto-generated method stub
		em.remove(n);
	}

	@Override
	public List<Flow> getAllFlow() {
		// TODO Auto-generated method stub
		return em.createQuery("Select f from Flow f").getResultList();
	}

	@Override
	public void addFlow(Flow f) {
		// TODO Auto-generated method stub
		em.persist(f);
	}

	@Override
	public void deleteFlow(Flow f) {
		// TODO Auto-generated method stub
		em.remove(f);
	}

	@Override
	public List<EtlFieldFlow> getAllChampsByFlow(Flow f) {
		// TODO Auto-generated method stub
		return em.createQuery("select c  from  EtlFieldFlow c where c.flow.id = " + f.getId()).getResultList();
	}

	@Override
	public List<Noeud> getAllNoeudByConvertisseur(Convertisseur f) {
		// TODO Auto-generated method stub
		return em.createQuery("Select n From Noeud n where n.converter.id=" + f.getId()).getResultList();
	}

	@Override
	public void addRepository(Repertoire p) {
		// TODO Auto-generated method stub
		em.persist(p);
	}

	@Override
	public List<Repertoire> getRepertoireByNoeud(Noeud n) {
		// TODO Auto-generated method stub
		return em.createQuery("Select n From Repertoire n where n.rep_noeud.id=" + n.getId()).getResultList();
	}

	@Override
	public List<Service> getAllServices() {
		// TODO Auto-generated method stub
		return em.createQuery("Select s From Service s ").getResultList();
	}

	@Override
	public void addService(Service s) {
		// TODO Auto-generated method stub
		em.persist(s);
	}

	@Override
	public void updateFlow(Flow f) {
		// TODO Auto-generated method stub
		em.merge(f);
	}

	@Override
	public void deleteService(Service s) {
		// TODO Auto-generated method stub
		em.remove(s);
	}

	@Override
	public void updateService(Service s) {
		// TODO Auto-generated method stub
		em.merge(s);
	}

	@Override
	public List<Convertisseur> getAllConverters() {
		// TODO Auto-generated method stub
		return em.createQuery("Select p from Convertisseur p  ").getResultList();
	}

	@Override
	public void addConverter(Convertisseur c) {
		// TODO Auto-generated method stub
		em.persist(c);
	}

	@Override
	public void deleteConverter(Convertisseur c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConfigAsn1(Config_ASN1 c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConfigASCII(Config_ASCII c) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Convertisseur> getConvertisseursByTypeConfig(String type) {
		// TODO Auto-generated method stub
		return em.createQuery("Select c From Convertisseur c ").getResultList();
	}

	@Override
	public List<Convertisseur> getAllConvertisseurByFlow(Flow f) {
		// TODO Auto-generated method stub
		return em.createQuery("Select n From Convertisseur n where n.flow.id=" + f.getId()).getResultList();
	}

	@Override
	public void addService_Convertisseur(EtlServiceConverter s) {
		// TODO Auto-generated method stub
		em.persist(s);

	}

	@Override
	public void deleteService_Convertisseur(EtlServiceConverter s) {
		// TODO Auto-generated method stub
		em.remove(s);
	}

	@Override
	public void updateService_Convertisseur(EtlServiceConverter s) {
		// TODO Auto-generated method stub
		em.merge(s);
	}

	@Override
	public List<EtlServiceConverter> getAllServiceConvertisseurByConv(Convertisseur c) {
		// TODO Auto-generated method stub
		return em.createQuery("Select s From  EtlServiceConverter s where s.convertisseur.id=" + c.getId())
				.getResultList();
	}

	@Override
	public List<EtlServiceConverter> getAllServiceConvertisseurByFlow(Flow f) {
		// TODO Auto-generated method stub
		return em
				.createQuery("Select distinct s From  EtlServiceConverter s where s.convertisseur.flow.id=" + f.getId())
				.getResultList();
	}

	@Override
	public List<EtlFieldService> getAllChampsByService(EtlServiceConverter c) {
		// TODO Auto-generated method stub
		return em.createQuery("Select  s From  EtlFieldService s where s.service.id=" + c.getId()).getResultList();
	}

	@Override
	public void createTable(List<EtlFieldFlow> list, String table) {
		// TODO Auto-generated method stub
		String champs = list.get(0).getLibelle() + " " + list.get(0).getData_type().getType_data();
		for (int i = 1; i < list.size(); i++) {
			champs = champs + " ," + list.get(i).getLibelle() + " " + list.get(i).getData_type().getType_data();
		}

		em.createNativeQuery("DROP TABLE IF EXISTS " + table).executeUpdate();
		em.createNativeQuery("CREATE UNLOGGED TABLE " + table + "(" + champs + " );").executeUpdate();

	}

	@Override
	public void alterTable(List<EtlFieldFlow> list) {
		// TODO Auto-generated method stub
		String flow = list.get(0).getFlow().getFlowName();
		List<Convertisseur> convertisseurs = list.get(0).getFlow().getListe_convertisseurs();
		List<Noeud> noeuds = new ArrayList<>();
		for (Convertisseur c : convertisseurs) {
			noeuds.addAll(c.getList_noeud());
		}
		String champs = list.get(0).getLibelle() + " " + list.get(0).getData_type();
		for (int i = 1; i < list.size(); i++) {
			champs = champs + " ," + list.get(i).getLibelle() + " " + list.get(i).getData_type();
		}

		for (Noeud n : noeuds) {
			em.createNativeQuery("DROP IF EXISTS tables.cdrs" + flow.toLowerCase() + "_" + n.getId()).executeUpdate();
			em.createNativeQuery(
					"CREATE TABLE  tables.cdrs" + flow.toLowerCase() + "_" + n.getId() + "(" + champs + " );")
					.executeUpdate();
		}

	}

	@Override
	public void createTableProcess(Flow f) {
		// TODO Auto-generated method stub

		em.createNativeQuery("DROP TABLE IF EXISTS " + f.getFlowName().toLowerCase() + "_process").executeUpdate();
		em.createNativeQuery("CREATE TABLE " + f.getFlowName().toLowerCase()
				+ "_process(  filename character varying ,date_reception timestamp with time zone,statut character varying ,nb_record numeric(20),date_min character varying ,"
				+ "date_max character varying ,id_node bigint );").executeUpdate();
		em.createNativeQuery("DROP TABLE IF EXISTS " + f.getFlowName().toLowerCase() + "_encours").executeUpdate();
		em.createNativeQuery("CREATE TABLE " + f.getFlowName().toLowerCase()
				+ "_encours(  filename character varying ,date_reception timestamp with time zone,statut character varying ,nb_record numeric(20),date_min character varying ,"
				+ "date_max character varying ,id_node bigint );").executeUpdate();

	}

	@Override
	public List<EtlBinType> getAllBinType() {
		// TODO Auto-generated method stub
		return em.createQuery("Select e From EtlBinType e ").getResultList();
	}

	@Override
	public List<EtlTypeFlow> getAllTypeFlow() {
		// TODO Auto-generated method stub
		return em.createQuery("Select e From EtlTypeFlow e where (id!=3 and id!=4)").getResultList();
	}

	@Override
	public List<EtlDataType> getAllDataType() {
		// TODO Auto-generated method stub
		return em.createQuery("Select e From EtlDataType e ").getResultList();
	}

	@Override
	public void createTableProcessNodeChild(Flow f, Noeud n, Integer fils) {
		// TODO Auto-generated method stub
		em.createNativeQuery(
				"DROP TABLE IF EXISTS " + f.getFlowName().toLowerCase() + "_process_" + n.getId() + "_" + fils)
				.executeUpdate();
		em.createNativeQuery("CREATE TABLE " + f.getFlowName().toLowerCase() + "_process_" + n.getId() + "_" + fils
				+ "(  filename character varying ,date_reception timestamp with time zone,statut character varying ,nb_record numeric(20),date_min character varying ,"
				+ "date_max character varying ,id_node bigint );").executeUpdate();
	}

	@Override
	public void addProc(EtlProc proc) {
		// TODO Auto-generated method stub
		em.merge(proc);
	}

	@Override
	public void updateProc(EtlProc proc) {
		// TODO Auto-generated method stub
		em.merge(proc);
	}

	@Override
	public void addEtlProcFlow(EtlProcFlow epf, long id_flow, long id_proc) {
		// TODO Auto-generated method stub
		// em.persist(epf);
		em.createNativeQuery("INSERT INTO test_etl.etl_proc_flow (version, query_filters, id_flow, id_proc) VALUES (0,"
				+ epf.getQuery_filters() + "," + id_flow + "," + id_proc + ");").executeUpdate();
	}

	@Override
	public void addEtlProcFlow(EtlProcFlow epf) {
		// TODO Auto-generated method stub
		try {
			em.persist(epf);
			em.persist(epf);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("id epf " + epf.getId());
		}

	}

	public void addfieldFlow(EtlFieldFlow eff) {
		em.persist(eff);
	}

	@Override
	public void updateEtlProcFlow(EtlProcFlow epf) {
		// TODO Auto-generated method stub
		try {
			em.merge(epf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(epf.getId());
		}
	}

	@Override
	public void addEtlFieldProc(EtlFieldProc efp) {
		// TODO Auto-generated method stub
		em.persist(efp);

	}

	@Override
	public void updateNoeud(Noeud n) {
		// TODO Auto-generated method stub
		em.merge(n);
	}

	@Override
	public List<EtlPrincipalFields> getAllPrincipalFields() {
		// TODO Auto-generated method stub
		return em.createQuery("Select e From EtlPrincipalFields e ").getResultList();
	}

	@Override
	public EtlPrincipalFields getPrincipalFieldByName(String principal_field_name) {
		// TODO Auto-generated method stub
		return (EtlPrincipalFields) em
				.createQuery("Select p From EtlPrincipalFields p where p.fieldname = " + principal_field_name);
	}

	@Override
	public void updateEtlPrincipalFields(EtlPrincipalFields principalField) {
		// TODO Auto-generated method stub
		em.merge(principalField);
	}

	@Override
	public List<Repertoire> getAllRep() {
		// TODO Auto-generated method stub
		return em.createQuery("Select e From Repertoire e ").getResultList();
	}

	int c = 0;

	@Override
	public void insertlistfieldRec(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {

		EtlFieldProc efp;
		List<BigInteger> list = new ArrayList<>();
		list = em.createNativeQuery("select u.id from etl.etl_field_flow u where u.id_flow=" + f.getId())
				.getResultList();
		for (int i = 0; i < ContextMenuViewM.listeRenameField.size(); i++) {
			for (int j = 0; j < ContextMenuViewM.list_etldield_proc.size(); j++) {
				System.out.println("id /_" + ContextMenuViewM.list_etldield_proc.get(j).getField().getId() + "id /_"
						+ ContextMenuViewM.listeRenameField.get(i).getId());
				if (ContextMenuViewM.list_etldield_proc.get(j).getField().getId() == ContextMenuViewM.listeRenameField
						.get(i).getId()) {
					System.out.println(" inside the if condition");
					efp = new EtlFieldProc();
					// efp.setType_field("Filtre");
					System.out.println("after new etl" + list.get(c));
					efp.setField(ContextMenuViewM.listeRenameField.get(i));
					System.out.println("after new etl 2" + list.get(c).longValueExact());
					efp.setId_field_dest(list.get(c).longValueExact());
					System.out.println(" inside 2 /" + ContextMenuViewM.listeRenameField.get(i).getLibelle());
					System.out.println(" inside 3 /" + ContextMenuViewM.list_etldield_proc.get(j).getFunction());
					efp.setFunction(ContextMenuViewM.list_etldield_proc.get(j).getFunction());
					System.out.println(" inside 4----" + epf.getId());
					efp.setProc_flow(epf);
					System.out.println(" adding");
					em.persist(efp);
					efp = new EtlFieldProc();
					c++;
				}

			}
			System.out.println("native query  ok");
		}
	}

	@Override
	public void insertlistfieldRec2(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {

		EtlFieldProc efp;
		List<BigInteger> list = new ArrayList<>();
		list = em.createNativeQuery("select u.id from etl.etl_field_flow u where u.id_flow=" + f.getId())
				.getResultList();
		for (int i = 0; i < ContextMenuViewM.listeRenameField.size(); i++) {
			for (int j = 0; j < ContextMenuViewM.list_etldield_proc2.size(); j++) {
				System.out.println("id /_" + ContextMenuViewM.list_etldield_proc2.get(j).getField().getId() + "id /_"
						+ ContextMenuViewM.listeRenameField.get(i).getId());
				if (ContextMenuViewM.list_etldield_proc2.get(j).getField().getId() == ContextMenuViewM.listeRenameField
						.get(i).getId()) {
					System.out.println(" inside the if condition");
					efp = new EtlFieldProc();
					// efp.setType_field("Filtre");
					System.out.println("after new etl" + list.get(c));
					efp.setField(ContextMenuViewM.listeRenameField.get(i));
					System.out.println("after new etl 2" + list.get(c).longValueExact());
					efp.setId_field_dest(list.get(c).longValueExact());
					System.out.println(" inside 2 /" + ContextMenuViewM.listeRenameField.get(i).getLibelle());
					System.out.println(" inside 3 /" + ContextMenuViewM.list_etldield_proc2.get(j).getFunction());
					efp.setFunction(ContextMenuViewM.list_etldield_proc.get(j).getFunction());
					System.out.println(" inside 4----" + epf.getId());
					efp.setProc_flow(epf);
					System.out.println(" adding");
					em.persist(efp);
					efp = new EtlFieldProc();
					c++;
				}

			}
			System.out.println("native query  ok");
		}
	}

	int b = 0;

	@Override
	public void insertlistfieldSTAT_Groupe(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {
		EtlFieldProc efp;
		List<BigInteger> list = new ArrayList<>();

		list = em.createNativeQuery("select u.id from etl.etl_field_flow u where u.id_flow=" + f.getId()).getResultList();

		for (int i = 0; i < StatMbean.list_etldield_proc3.size(); i++) {
			System.out.println(" inside the if condition etl field proc ");
			efp = new EtlFieldProc();
			efp.setType_field("Group By");
			System.out.println("b=" + b);
			System.out.println("after new etl" + list.get(b));
			efp.setField(StatMbean.list_etldield_proc3.get(i).getField());
			System.out.println("after new etl 2" + list.get(b).longValueExact());
			efp.setId_field_dest(list.get(b).longValueExact());
			System.out.println(" inside 2 /" + StatMbean.list_etldield_proc3.get(i).getField().getLibelle());
			System.out.println(" inside 3 /" + StatMbean.list_etldield_proc3.get(i).getFunction());
			efp.setFunction(StatMbean.list_etldield_proc3.get(i).getFunction());
			System.out.println(" inside 4----" + epf.getId());
			efp.setProc_flow(epf);
			System.out.println("adding");
			em.persist(efp);
			efp = new EtlFieldProc();
			b++;

			System.out.println("native query  ok");
		}
		b = 0;
	}


	@Override
	public void insertlistfieldSTAT_agg(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {

		EtlFieldProc efp;
		List<BigInteger> list = new ArrayList<>();
		List<EtlFieldFlow> listetl = new ArrayList<>();
		list = em.createNativeQuery("select u.id from etl.etl_field_flow u where u.id_flow=" + f.getId()).getResultList();

		for (int i = 0; i < StatMbean.list_etldield_proc2.size(); i++) {
			System.out.println(" inside the if condition etl field proc 2 ");
			efp = new EtlFieldProc();
			efp.setType_field("Aggregation");
			// System.out.println("after new etl" + list.get(b));
			efp.setField(StatMbean.list_etldield_proc2.get(i).getField());
			System.out.println("after new etl 2" + list.get(b).longValueExact());
			efp.setId_field_dest(list.get(b).longValueExact());
			System.out.println(" inside 2 /" + StatMbean.list_etldield_proc2.get(i).getField().getLibelle());
			System.out.println(" inside 3 /" + StatMbean.list_etldield_proc2.get(i).getFunction());
			efp.setFunction(StatMbean.list_etldield_proc2.get(i).getFunction());
			System.out.println(" inside 4----" + epf.getId());
			efp.setProc_flow(epf);
			System.out.println("adding");
			em.persist(efp);
			efp = new EtlFieldProc();
			b++;
		}
		System.out.println("native query  ok");
	}

	public void insertflowstat(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {
		// List<EtlFieldFlow> list_id= getAllChamps();
		BigInteger idB = (BigInteger) em.createNativeQuery("SELECT id FROM etl.etl_field_flow ORDER BY id DESC LIMIT 1")
				.getSingleResult();
		System.out.println("this is id==" + idB);
		long idL = idB.longValueExact();

		for (int j = 0; j < etlfieldflow.size(); j++) {
			idL++;
			if (etlfieldflow.get(j).getPrincipalField() == null) {
				em.createNativeQuery(
						"INSERT INTO etl.etl_field_flow(id,version ,format,length_data,name_base,mapping, precision_data, id_data_type, id_flow, is_date )"
								+ " VALUES (" + idL + ",0,'" + etlfieldflow.get(j).getFormat() + "','"
								+ etlfieldflow.get(j).getLength_data() + "','" + etlfieldflow.get(j).getLibelle()
								+ "','" + etlfieldflow.get(j).getMapping() + "','"
								+ etlfieldflow.get(j).getPrecision_data() + "',"
								+ etlfieldflow.get(j).getData_type().getId() + "," + f.getId() + ","
								+ etlfieldflow.get(j).isIsdate() + ");")
						.executeUpdate();
			} else {
				em.createNativeQuery(
						"INSERT INTO etl.etl_field_flow(id,version ,format,length_data,name_base,mapping,precision_data, id_data_type, id_flow ,id_principal_field, is_date)"
								+ " VALUES (" + idL + ",0,'" + etlfieldflow.get(j).getFormat() + "','"
								+ etlfieldflow.get(j).getLength_data() + "','" + etlfieldflow.get(j).getLibelle()
								+ "','" + etlfieldflow.get(j).getMapping() + "','"
								+ etlfieldflow.get(j).getPrecision_data() + "',"
								+ etlfieldflow.get(j).getData_type().getId() + "," + f.getId() + ","
								+ etlfieldflow.get(j).getPrincipalField().getId() + "," + etlfieldflow.get(j).isIsdate()
								+ ");")
						.executeUpdate();
			}

		}

	}

	@Override
	public void insertlistfieldSTAT(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf) {
		int b = 0;
		EtlFieldProc efp;
		List<BigInteger> list = new ArrayList<>();
		list = em.createNativeQuery("select u.id from etl.etl_field_flow u where u.id_flow=" + f.getId())
				.getResultList();

		for (int i = 0; i < StatMbean.listeRenameField.size(); i++) {
			for (int j = 0; j < StatMbean.list_etldield_proc.size(); j++) {
				System.out.println("id /_" + StatMbean.list_etldield_proc.get(j).getField().getId() + "id /_"
						+ StatMbean.listeRenameField.get(i).getId());
				if (StatMbean.list_etldield_proc.get(j).getField().getId() == StatMbean.listeRenameField.get(i)
						.getId()) {
					System.out.println(" inside the if condition etl field proc ");
					efp = new EtlFieldProc();
					efp.setType_field("Filtre");
					System.out.println(b + "---- and list is ----" + list.size());

					System.out.println("after new etl" + list.get(b).longValueExact());
					efp.setField(StatMbean.listeRenameField.get(i));
					System.out.println("after new etl 2" + list.get(b).longValueExact());
					efp.setId_field_dest(list.get(b).longValueExact());
					System.out.println(" inside 2 /" + StatMbean.listeRenameField.get(i).getLibelle());
					System.out.println(" inside 3 /" + StatMbean.list_etldield_proc.get(j).getFunction());
					// efp.setFunction(StatMbean.list_etldield_proc.get(j).getFunction());
					System.out.println(" inside 4----" + epf.getId());
					efp.setProc_flow(epf);
					System.out.println("adding");
					em.persist(efp);
					efp = new EtlFieldProc();
					b++;
				}

			}
			System.out.println("native query  ok");
		}
	}

	@Override
	public void addFlowI(Flow f) {
		try {
			// TODO Auto-generated method stub
			em.createNativeQuery("INSERT INTO etl.etl_flows(version, name, table_name, id_type_flow)" + "VALUES (0,'"
					+ f.getFlowName() + "','" + f.getTable_name() + "'," + 3 + ");").executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public List<BigInteger> getprocflowbyidprocs(long id) {

		return em.createNativeQuery("SELECT id_flow FROM etl.Etl_Proc_Flow  where id_proc=" + id).getResultList();

	}

	@Override
	public void deleteetlprocs(EtlProc etp) {
		// TODO Auto-generated method stub
		em.remove(em.merge(etp));
	}

	@Override
	public Flow getFlowbyid(BigInteger id) {
		// TODO Auto-generated method stub
		return (Flow) em.createQuery("select u from Flow u where u.id=" + id).getSingleResult();
	}

	@Override
	public EtlProcFlow get_query_filtre(long id_proc, long id_flow) {
		return (EtlProcFlow) em
				.createQuery("select u from  EtlProcFlow u where id_proc=" + id_proc + "and id_flow=" + id_flow)
				.getSingleResult();
	}

	@Override
	public EtlTypeFlow getTypeflow() {
		return (EtlTypeFlow) em.createQuery("select u from EtlTypeFlow u where u.id=" + 3).getSingleResult();

	}

	@Override
	public List<EtlProc> getAllprocStat() {
		return em.createQuery("select p from EtlProc p where p.proc_type='STAT'").getResultList();
	}

	@Override
	public List<EtlFieldProc> getallfieldprocbyprocflow(Long id) {
		// TODO Auto-generated method stub
		return em.createQuery("select u from EtlFieldProc u where id_proc_flow=" + id).getResultList();
	}

	@Override
	public void updateConvertisseur(Convertisseur c) {
		// TODO Auto-generated method stub
		em.merge(c);
	}

	@Override
	public void updateRepository(Repertoire rep) {
		// TODO Auto-generated method stub
		em.merge(rep);
	}

	@Override
	public List<EtlFieldFlow> getflowbyid(long id) {
		return em.createQuery("select f from EtlFieldFlow f where id_flow=" + id).getResultList();
	}

	@Override
	public EtlFieldFlow getfieldflowbyid(long id) {
		return (EtlFieldFlow) em.createQuery("select f from EtlFieldFlow f where f.id=" + id).getSingleResult();
	}

	public List<EtlProcFlow> getEtl_procflow() {
		return em.createQuery("select e from EtlProcFlow e").getResultList();
	}

}
