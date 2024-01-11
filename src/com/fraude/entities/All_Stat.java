package com.fraude.entities;

import java.util.List;

public class All_Stat {

	private Flow etlflowRecanother;
	private EtlProcFlow epf ;
	private List<EtlFieldFlow> list_Groupe_champsanother;
	private List<EtlFieldFlow> list_agg_champsanother;
	
	
	private List<EtlFieldProc> etlFieldProcs_agg_another ;
	private List<EtlFieldProc> etlFieldProcs_groupe_another ;
	
	public Flow getEtlflowRecanother() {
		return etlflowRecanother;
	}
	public void setEtlflowRecanother(Flow etlflowRecanother) {
		this.etlflowRecanother = etlflowRecanother;
	}
	public List<EtlFieldFlow> getList_Groupe_champsanother() {
		return list_Groupe_champsanother;
	}
	public void setList_Groupe_champsanother(List<EtlFieldFlow> list_Groupe_champsanother) {
		this.list_Groupe_champsanother = list_Groupe_champsanother;
	}
	public List<EtlFieldFlow> getList_agg_champsanother() {
		return list_agg_champsanother;
	}
	public void setList_agg_champsanother(List<EtlFieldFlow> list_agg_champsanother) {
		this.list_agg_champsanother = list_agg_champsanother;
	}
	public EtlProcFlow getEpf() {
		return epf;
	}
	public void setEpf(EtlProcFlow epf) {
		this.epf = epf;
	}
	public List<EtlFieldProc> getEtlFieldProcs_agg_another() {
		return etlFieldProcs_agg_another;
	}
	public void setEtlFieldProcs_agg_another(List<EtlFieldProc> etlFieldProcs_agg_another) {
		this.etlFieldProcs_agg_another = etlFieldProcs_agg_another;
	}
	public List<EtlFieldProc> getEtlFieldProcs_groupe_another() {
		return etlFieldProcs_groupe_another;
	}
	public void setEtlFieldProcs_groupe_another(List<EtlFieldProc> etlFieldProcs_groupe_another) {
		this.etlFieldProcs_groupe_another = etlFieldProcs_groupe_another;
	}
	

}
