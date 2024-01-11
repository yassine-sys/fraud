package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.Function;
import com.fraude.entities.RepModule;
import com.fraude.entities.RepSubModule;
import com.fraude.interfaces.FunctionInterface;
import com.fraude.interfaces.ModuleInterface;
import com.fraude.interfaces.SubModuleInterface;


@Stateless
public class ModuleService implements ModuleInterface , SubModuleInterface , FunctionInterface{

	@PersistenceContext
	EntityManager em;

	@Override
	public List<RepModule> getAllModules() {
		// TODO Auto-generated method stub
		
		return em.createQuery("select m from RepModule m").getResultList();
	}

	@Override
	public void addModule(RepModule m) {
		// TODO Auto-generated method stub
		em.persist(m);
	}

	@Override
	public void deleteModule(RepModule m) {
		// TODO Auto-generated method stub
		em.remove(m);
	}

	@Override
	public void updateModule(RepModule m) {
		// TODO Auto-generated method stub
		System.out.println("((((( updupdupd ))))))");
		em.merge(m);
		//em.createNativeQuery("update etl.etl_rep_module set nom_module="+m.getModuleName()+" where id="+m.getId());
	}

	@Override
	public List<RepSubModule> getAllSubModules() {
		// TODO Auto-generated method stub
		return  em.createQuery("select subm from RepSubModule subm").getResultList();
	}

	@Override
	public void addSubModule(RepSubModule subm) {
		// TODO Auto-generated method stub
		em.persist(subm);
	}

	@Override
	public void deleteSubModule(RepSubModule subm) {
		// TODO Auto-generated method stub
		em.remove(subm);
	}

	@Override
	public void updateSubModule(RepSubModule subm) {
		// TODO Auto-generated method stub
		em.merge(subm);
	}

	@Override
	public List<Function> getAllFunctions() {
		// TODO Auto-generated method stub
		return  em.createQuery("select func from Function func").getResultList();
	}

	@Override
	public void addFunction(Function f) {
		// TODO Auto-generated method stub
		em.persist(f);
	}

	@Override
	public void deleteFunction(Function f) {
		// TODO Auto-generated method stub
		em.remove(f);
	}

	@Override
	public void updateFunction(Function f) {
		// TODO Auto-generated method stub
		em.merge(f);
	}
}
