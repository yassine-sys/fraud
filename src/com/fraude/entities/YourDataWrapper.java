package com.fraude.entities;

import java.util.List;


public class YourDataWrapper {
	
	
    private ReglesFraude regle;
    
    private List<ParametresReglesFraude> params; // Change to List<ParametresReglesFraude>
    private List<FiltresReglesFraude> filters; // Change to List<FiltresReglesFraude>
    private CategoriesFraude categorie;
    private Flow flux;
   
    public CategoriesFraude getCategorie() {
		return categorie;
	}
	public void setCategorie(CategoriesFraude categorie) {
		this.categorie = categorie;
	}
	public Flow getFlux() {
		return flux;
	}
	public void setFlux(Flow flux) {
		this.flux = flux;
	}
	
	public ReglesFraude getRegle() {
		return regle;
	}
	public void setRegle(ReglesFraude regle) {
		this.regle = regle;
	}
	public List<ParametresReglesFraude> getParams() {
		return params;
	}
	public void setParams(List<ParametresReglesFraude> params) {
		this.params = params;
	}
	public List<FiltresReglesFraude> getFilters() {
		return filters;
	}
	public void setFilters(List<FiltresReglesFraude> filters) {
		this.filters = filters;
	}
	public YourDataWrapper(ReglesFraude regle, List<ParametresReglesFraude> params, List<FiltresReglesFraude> filters) {
		
		this.regle = regle;
		this.params = params;
		this.filters = filters;
	}
	public YourDataWrapper() {
		
	}

   
}
