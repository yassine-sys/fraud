package com.fraude.interfaces;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.Convertisseur;
import com.fraude.entities.EtlBinType;
import com.fraude.entities.EtlDataType;
import com.fraude.entities.EtlFieldFlow;
import com.fraude.entities.EtlFieldProc;
import com.fraude.entities.EtlPrincipalFields;
import com.fraude.entities.EtlProc;
import com.fraude.entities.EtlProcFlow;
import com.fraude.entities.EtlServiceConverter;
import com.fraude.entities.EtlTypeFlow;
import com.fraude.entities.Flow;
import com.fraude.entities.Noeud;




@Local
public interface FlowInterface {
	
	public void addEtlProcFlow(EtlProcFlow epf, long id_flow, long id_proc);
	public void addFlowI(Flow f);
	public List<Flow> getAllFlow();
	public List<EtlTypeFlow> getAllTypeFlow();
	public List<EtlBinType> getAllBinType();
	public List<EtlDataType> getAllDataType();
	public void addFlow(Flow f );
	public EtlTypeFlow getTypeflow();
	public void createTable(List<EtlFieldFlow> list,String n);
	public void createTableProcess(Flow f);
	public void createTableProcessNodeChild(Flow f,Noeud n ,Integer fils);
	public void addProc(EtlProc proc);
	public void updateProc(EtlProc proc);
	public void addEtlProcFlow(EtlProcFlow epf);
	public void updateEtlProcFlow(EtlProcFlow epf);
	public void addEtlFieldProc(EtlFieldProc efp);
	public void alterTable(List<EtlFieldFlow> list);
	public void updateFlow(Flow f);
	public void deleteFlow(Flow f);
	public List<EtlFieldFlow> getAllChampsByFlow(Flow f);
	public List<Noeud> getAllNoeudByConvertisseur(Convertisseur f);
	public List<Convertisseur> getAllConvertisseurByFlow(Flow f);
	public List<EtlServiceConverter> getAllServiceConvertisseurByFlow(Flow f);
	public List<EtlPrincipalFields> getAllPrincipalFields();
	public List<EtlFieldFlow> getAllChamps(Flow f);
	public void insertlistfieldRec(List<EtlFieldFlow> etlfieldflow , Flow f,EtlProcFlow epf);
	public void insertlistfieldSTAT(List<EtlFieldFlow> etlfieldflow , Flow f,EtlProcFlow epf);
	public void insertlistfieldSTAT_agg(List<EtlFieldFlow> etlfieldflow , Flow f,EtlProcFlow epf);
	public void insertlistfieldSTAT_Groupe(List<EtlFieldFlow> etlfieldflow , Flow f,EtlProcFlow epf);
	public List<EtlProc> getAllproc();
	public List<EtlProcFlow> getAllprocflowbyidprocs(Long id_proc);
	public void deleteetlprocs(EtlProc etp);
	public List<BigInteger> getprocflowbyidprocs(long id);
	public Flow getFlowbyid(BigInteger  l);
	public EtlProcFlow get_query_filtre(long id_proc,long id_flow);
	public List<EtlProc> getAllprocStat();
	public void insertflowstat(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf);
	public List<EtlFieldProc> getallfieldprocbyprocflow(Long id);
	public List<EtlFieldFlow>getflowbyid(long id);
	public List<EtlProcFlow> getEtl_procflow();
	
	
	public List<Flow> getAllFlows();
	public Flow getFlowByName(String nameFlow);
	public List<EtlPrincipalFields> getPrincipalFieldsByFlow(Flow f);
	public void deletePrincipalField(EtlPrincipalFields principal_field);
	public void updatePrincipalField(EtlPrincipalFields principal_field);
	public void updateListPrincipalFields(Flow f);
	public void insertlistfieldRec2(List<EtlFieldFlow> etlfieldflow, Flow f, EtlProcFlow epf);
	public List<EtlFieldFlow> getAllChamps();
	public EtlFieldFlow getfieldflowbyid(long id);
}
