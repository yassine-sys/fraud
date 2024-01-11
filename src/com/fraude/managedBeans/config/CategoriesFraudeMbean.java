package com.fraude.managedBeans.config;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.fraude.entities.CategoriesFraude;
import com.fraude.interfaces.CategoriesFraudeRemote;
import com.fraude.services.CategoriesFraudeService;

@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "cate_fraud")
@ViewScoped
public class CategoriesFraudeMbean {

	@EJB
	private CategoriesFraudeRemote CatFraudService;

	private CategoriesFraude CatFraud;

	private CategoriesFraude CatFraudSelection;

	private List<CategoriesFraude> listCatFraude;

	@PostConstruct
	public void init() {

		listCatFraude = CatFraudService.getAllCatFraude();
		CatFraud = new CategoriesFraude();
		CatFraudSelection = new CategoriesFraude();

	}

	public CategoriesFraude getCatFraud() {
		return CatFraud;
	}

	public void setCatFraud(CategoriesFraude catFraud) {
		CatFraud = catFraud;
	}

	public CategoriesFraude getCatFraudSelection() {
		return CatFraudSelection;
	}

	public void setCatFraudSelection(CategoriesFraude catFraudSelection) {
		CatFraudSelection = catFraudSelection;
	}

	public List<CategoriesFraude> getListCatFraude() {
		return listCatFraude;
	}

	public void setListCatFraude(List<CategoriesFraude> listCatFraude) {
		this.listCatFraude = listCatFraude;
	}

	@POST
	@Path("/AddCategoriesFraude")
	@Produces(MediaType.APPLICATION_JSON)
	public void AddCategoriesFraude(CategoriesFraude catfraud) {
		try {
			CatFraudService.AjouterCatFraude(catfraud);
		//	return Response.status(Response.Status.CREATED).entity("catfraud added successfully").build();
		} catch (Exception e) {
			//return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				//	.entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAllCategoriesFraude")
	public List<CategoriesFraude> getAllCategoriesFraude() {
		return CatFraudService.getAllCatFraude();
	}

	@PUT
	@Path("/updateCategoriesFraude/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCategoriesFraude(@PathParam("id") Integer id, CategoriesFraude updateRequest) {
		try {
			CatFraudService.updateCategoriesFraude(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteCatFraud/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCatFraud(@PathParam("id") Integer id) {
		try {
			CatFraudService.deleteCatFraud(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	public void AddCategoriesFraude() {

		CatFraudService.AjouterCatFraude(CatFraud);

		listCatFraude = CatFraudService.getAllCatFraude();
		CatFraud = new CategoriesFraude();
	}

	public void UpdateCategoriesFraude() {

		CatFraudService.ModifierCatFraud(CatFraudSelection);

		listCatFraude = CatFraudService.getAllCatFraude();
		CatFraudSelection = new CategoriesFraude();
	}

	public void DeleteCategriesFraude() {

		CatFraudService.SuprimerCatFraude(CatFraudSelection);

		listCatFraude = CatFraudService.getAllCatFraude();
		CatFraudSelection = new CategoriesFraude();
	}
}
