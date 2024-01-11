package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;
import com.fraude.entities.TypeDestination;

@Local
public interface TypeDestinationRemote {

	public List<TypeDestination> getAllTypeDest();

	public List<String> getAllStringTypeDest();

	public void addTypeDest(TypeDestination td);

	public void deleteTypeDest(TypeDestination td);

	public void updateTypeDest(TypeDestination td);

}
