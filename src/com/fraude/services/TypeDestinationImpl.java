package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.TypeDestination;
import com.fraude.interfaces.TypeDestinationRemote;

@Stateless
public class TypeDestinationImpl implements TypeDestinationRemote {
	
	@PersistenceContext
	EntityManager emRep;

	@Override
	public List<TypeDestination> getAllTypeDest() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select t from TypeDestination t").getResultList();
	}
	@Override
	public List<String> getAllStringTypeDest() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select distinct typeDest from TypeDestination t").getResultList();
	}
	@Override
	public void addTypeDest(TypeDestination td) {
		// TODO Auto-generated method stub
		emRep.persist(td);
	}
	@Override
	public void deleteTypeDest(TypeDestination td) {
		// TODO Auto-generated method stub
		emRep.remove(emRep.contains(td) ? td : emRep.merge(td));
		
		
	}
	@Override
	public void updateTypeDest(TypeDestination td) {
		// TODO Auto-generated method stub
		emRep.merge(td);
	}

}
