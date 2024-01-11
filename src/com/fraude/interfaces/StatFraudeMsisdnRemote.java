package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

@Local
public interface StatFraudeMsisdnRemote {

	public List<Object[]> getStatFraude(String x,String y ,List<String> where);
	public List<Object[]> getStatFraudeParOperateur(String x,String y ,List<String> where);
	List<Object[]> getStatFraudeAlertPie(String x, String y, List<Integer> lst_cat, List<String> Where);
	List<Object[]> getStatFraudeAlert(String x, String y, List<Integer> lst_cat, List<String> Where);
	List<Object[]> getStatFraude2(String x, String y, List<String> Where);
	List<Object[]> getStatFraud(String x, String y, List<String> Where);
	List<Object[]> getLoct(String loc);
	List<Object[]> getLoctMssdin2(float att, float lng);
	List<Object[]> getLoctMssdin(float att, float lng);
	List<Object[]> getStatFraudeMsisdn(String x, String y, List<String> Where, List<String> Where2);
	List<Object[]> getStatFraudeMsisdnLine(String x, String y, List<String> Where, List<String> Where2);
	
}
