package com.fraude.managedBeans.config;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

import com.fraude.entities.Cell;
import com.fraude.entities.ListAppelant;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListCellid;
import com.fraude.entities.ListDetailsAppelant;
import com.fraude.entities.ListDetailsAppele;
import com.fraude.entities.ListDetailsCellid;
import com.fraude.entities.ListDetailsImei;
import com.fraude.entities.ListImei;
import com.fraude.entities.Util;
import com.fraude.interfaces.FraudeHotlistRemote;

@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "manage_hotlist")
@ViewScoped
public class HotListMbean {

	@EJB
	private FraudeHotlistRemote hotlist_service;

	private ListAppele appele;
	private ListAppele appele1;
	private List<ListAppele> liste_appele;

	private List<ListDetailsAppele> list_det_appele = new ArrayList<>();

	private ListAppelant appelant;
	private ListAppelant appelant1;
	private List<ListAppelant> liste_appelant;

	private List<ListDetailsAppelant> list_det_appelant = new ArrayList<>();

	private ListImei imei;
	private ListImei imei1;
	private List<ListImei> liste_imei;

	private List<ListDetailsImei> list_det_imei = new ArrayList<>();
	private ListCellid cellid;
	private ListCellid cellid1;
	private List<ListCellid> liste_cellid;

	private List<ListDetailsCellid> list_det_cellid = new ArrayList<>();

	public List<ListDetailsAppele> getList_det_appele() {
		return list_det_appele;
	}

	public void setList_det_appele(List<ListDetailsAppele> list_det_appele) {
		this.list_det_appele = list_det_appele;
	}

	private ListDetailsAppele detailsappele;
	private ListDetailsAppele detailsappele1;
	private ListDetailsCellid detailscellid;
	private ListDetailsCellid detailscellid1;
	private ListDetailsImei detailsimei;
	private ListDetailsImei detailsimei1;
	private ListDetailsAppelant detailsappelant;
	private ListDetailsAppelant detailsappelant1;

	@PostConstruct
	public void init() {
//		liste_appele = hotlist_service.getAllAppele();
//		liste_appelant = hotlist_service.getAllAppelant();
//		liste_cellid = hotlist_service.getAllCellId();
//		liste_imei = hotlist_service.getAllImei();
//		appele = new ListAppele();
//		appele1 = new ListAppele();
//		detailsappele = new ListDetailsAppele();
//		detailsappele1 = new ListDetailsAppele();
//		appelant = new ListAppelant();
//		appelant1 = new ListAppelant();
//		detailsappelant = new ListDetailsAppelant();
//		detailsappelant1 = new ListDetailsAppelant();
//		cellid = new ListCellid();
//		cellid1 = new ListCellid();
//		detailscellid = new ListDetailsCellid();
//		detailscellid1 = new ListDetailsCellid();
//		imei = new ListImei();
//		imei1 = new ListImei();
//		detailsimei = new ListDetailsImei();
//		detailsimei1 = new ListDetailsImei();

	}

	@POST
	@Path("/Addappele")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListAppele appele) {
		try {

			appele.setDateModif(new Timestamp(System.currentTimeMillis()));
			hotlist_service.addAppele(appele);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAllAppele")
	public List<ListAppele> getAllAppele() {
		return hotlist_service.getAllAppele();
	}

	@PUT
	@Path("/updateAppele/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAppele(@PathParam("id") Integer id, ListAppele updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateAppele(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteAppele/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAppele(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteAppele(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	public void ajoutAppele() {
		appele.setNomUtilisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		appele.setDateModif(currentTimestamp);
		hotlist_service.addAppele(appele);
		liste_appele = hotlist_service.getAllAppele();
		appele = new ListAppele();

	}

	public void modifAppele() {
		hotlist_service.updateAppele(appele1);
		liste_appele = hotlist_service.getAllAppele();

	}

	public void deletAppele() {
		hotlist_service.deleteAppele(appele1);
		liste_appele = hotlist_service.getAllAppele();

	}

	@POST
	@Path("/Addappelant")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListAppelant appelant) {
		try {

			appelant.setDateModif(new Timestamp(System.currentTimeMillis()));
			hotlist_service.addAppelant(appelant);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAllAppelant")
	public List<ListAppelant> getAllAppelant() {
		return hotlist_service.getAllAppelant();
	}

	@PUT
	@Path("/updateAppelant/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAppelant(@PathParam("id") Integer id, ListAppelant updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateAppelant(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteAppelant/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAppelant(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteAppelant(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	public void addAppelant() {
		appelant.setNomUitlisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		appelant.setDateModif(currentTimestamp);
		hotlist_service.addAppelant(appelant);
		liste_appelant = hotlist_service.getAllAppelant();
		appelant = new ListAppelant();

	}

	public void deleteAppelant() {
		hotlist_service.deleteAppelant(appelant1);
		liste_appelant = hotlist_service.getAllAppelant();

	}

	public void modifAppelant() {
		hotlist_service.updateAppelant(appelant1);
		liste_appelant = hotlist_service.getAllAppelant();

	}

	public void addImei() {
		imei.setNomUtilisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		imei.setDateModif(currentTimestamp);
		hotlist_service.addImei(imei);
		liste_imei = hotlist_service.getAllImei();
		imei = new ListImei();

	}

	public void deleteImei() {
		hotlist_service.deleteImei(imei1);
		liste_imei = hotlist_service.getAllImei();

	}

	public void modifImei() {
		hotlist_service.updateImei(imei1);
		liste_imei = hotlist_service.getAllImei();

	}

	@POST
	@Path("/Imei")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListImei Imei) {
		try {

			Imei.setDateModif(new Timestamp(System.currentTimeMillis()));
			hotlist_service.addImei(Imei);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAllImei")
	public List<ListImei> getAllImei() {
		return hotlist_service.getAllImei();
	}

	@PUT
	@Path("/updateImei/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateImei(@PathParam("id") Integer id, ListImei updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateImei(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update ").build();
		}
	}

	@DELETE
	@Path("/deleteImei/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteImei(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteImei(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	@POST
	@Path("/cellid")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListCellid cellid) {
		try {

			cellid.setDateModif(new Timestamp(System.currentTimeMillis()));
			hotlist_service.addCellId(cellid);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAllcellid")
	public List<ListCellid> getAllcellid() {
		return hotlist_service.getAllCellId();
	}

	@PUT
	@Path("/updateCellid/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCellid(@PathParam("id") Integer id, ListCellid updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateCellid(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteCellid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCellid(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteCellid(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	public void addCellid() {
		cellid.setNomUtilisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		cellid.setDateModif(currentTimestamp);
		hotlist_service.addCellId(cellid);
		liste_cellid = hotlist_service.getAllCellId();
		cellid = new ListCellid();

	}

	public void deleteCellId() {
		hotlist_service.deleteCellId(cellid1);
		liste_cellid = hotlist_service.getAllCellId();

	}

	public void modifCellId() {
		hotlist_service.updateCellId(cellid1);
		liste_cellid = hotlist_service.getAllCellId();

	}

	public void loadDetailsAppelant() {
		list_det_appelant = hotlist_service.getListDetailsAppelant(appelant1);

	}

	public void loadDetailsCell() {
		list_det_cellid = hotlist_service.getListDetailsCellId(cellid1);

	}

	public void loadDetailsImei() {
		list_det_imei = hotlist_service.getListDetailsImei(imei1);

	}

	public void loadDetails() {
		list_det_appele = hotlist_service.getListDetailsAppeles(appele1);

	}

	@POST
	@Path("/detailsappelant")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListDetailsAppelant appelant) {
		try {

			hotlist_service.addDetailAppelant(appelant);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAlldetailsappelant/{appelant_id}")
	public List<ListDetailsAppelant> getAlldetailsappelant(@PathParam("appelant_id") Integer appelant_id) {
		return hotlist_service.getAlldetailsappelant(appelant_id);
	}

	@PUT
	@Path("/updateListDetailsAppelant/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateListDetailsAppelant(@PathParam("id") Integer id, ListDetailsAppelant updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateListDetailsAppelant(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteListDetailsAppelant/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteListDetailsAppelant(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteListDetailsAppelant(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	@POST
	@Path("/detailsappele")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListDetailsAppele appele) {
		try {

			hotlist_service.addDetailAppele(appele);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAlldetailsappele/{appele_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ListDetailsAppele> getAlldetailsappele(@PathParam("appele_id") Integer appele_id) {
		return hotlist_service.getAlldetailsappele(appele_id);
	}

	@PUT
	@Path("/updateListDetailsAppele/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateListDetailsAppele(@PathParam("id") Integer id, ListDetailsAppele updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateListDetailsAppele(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteListDetailsAppele/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteListDetailsAppele(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteListDetailsAppele(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	@POST
	@Path("/detailscellid")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListDetailsCellid cellid) {
		try {

			hotlist_service.addDetailCellId(cellid);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAlldetailscellid/{cell_id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ListDetailsCellid> getAlldetailscellid(@PathParam("cell_id") Integer cell_id) {

		return hotlist_service.getAlldetailsCellid(cell_id);
	}

	@PUT
	@Path("/updateListDetailscellid/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateListDetailscellid(@PathParam("id") Integer id, ListDetailsCellid updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateListDetailsCellid(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteListDetailsCellid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteListDetailsCellid(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteListDetailsCellid(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}

	@POST
	@Path("/detailsimei")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(ListDetailsImei imei) {
		try {

			hotlist_service.addDetailImei(imei);
			// return Response.status(Response.Status.CREATED).entity("catfraud added
			// successfully").build();
		} catch (Exception e) {
			// return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			// .entity("Error adding service: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/getAlldetailsimei/{imei_id}")
	public List<ListDetailsImei> getAlldetailsimei(@PathParam("imei_id") Integer imei_id) {
		return hotlist_service.getAllListDetailsImei(imei_id);
	}

	@PUT
	@Path("/updateListDetailsimei/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateListDetailsimei(@PathParam("id") Integer id, ListDetailsImei updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			hotlist_service.updateListDetailsImei(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	@DELETE
	@Path("/deleteListDetailsimei/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteListDetailsimei(@PathParam("id") Integer id) {
		try {
			hotlist_service.deleteListDetailsImei(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}
	
	@GET
	@Path("/getAppeleByid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ListAppele> getAppeleByid(@PathParam("id") Integer id) {
		
			return hotlist_service.getAppeleByid(id); 
			
	}
	
	
	@GET
	@Path("/getAppelantByid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ListAppelant> getAppelantByid(@PathParam("id") Integer id) {
		
			return hotlist_service.getAppelantByid(id); 
			
	}
	
	
	@GET
	@Path("/getCellidByid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ListCellid> getCellidByid(@PathParam("id") Integer id) {
		
			return hotlist_service.getCellidByid(id); 
			
	}
	
	@GET
	@Path("/getImeiByid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ListImei> getImeiByid(@PathParam("id") Integer id) {
		
			return hotlist_service.getImeiByid(id); 
			
	}

	public void addDetailAppelant() {
		// if(hotlist_service.findDetailsAppelant(detailsappelant.getHotlistnumber())==null){
		detailsappelant.setNomUtilisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		detailsappelant.setDateModif(currentTimestamp);
		detailsappelant.setAppelant(appelant1);
		hotlist_service.addDetailAppelant(detailsappelant);
		loadDetailsAppelant();
		detailsappelant = new ListDetailsAppelant();
		// }

	}

	public void modifDetailAppelant() {
		hotlist_service.updateDetailAppelant(detailsappelant1);
		loadDetailsAppelant();

	}

	public void deleteDetailAppelant() {
		hotlist_service.deleteDetailAppelant(detailsappelant1);
		loadDetailsAppelant();
	}

	public void addDetailCellId() {
		if (hotlist_service.findDetailsCellid(detailscellid.getHotlistnumber()) == null) {
			detailscellid.setNomUtlisateur(Util.getUser().getUName());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			detailscellid.setDateModif(currentTimestamp);
			detailscellid.setCellId(cellid1);
			hotlist_service.addDetailCellId(detailscellid);
			loadDetailsCell();
			detailscellid = new ListDetailsCellid();
		} else {
			FacesContext.getCurrentInstance().addMessage("@form", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Deja existe Dans Hotlist :" + cellid1.getNom(), "Veuillez R�ssayer"));

		}

	}

	public void modifDetailCellId() {
		hotlist_service.updateDetailCellId(detailscellid1);
		loadDetailsCell();

	}

	public void deleteDetailCellId() {
		hotlist_service.deleteDetailCellId(detailscellid1);
		loadDetailsCell();

	}

	public void addDetailImei() {
		if (hotlist_service.findDetailsImei(detailsimei.getHotlistnumber()) == null) {
			detailsimei.setNomUtilisateur(Util.getUser().getUName());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			detailsimei.setDateModif(currentTimestamp);
			detailsimei.setImei(imei1);
			hotlist_service.addDetailImei(detailsimei);
			loadDetailsImei();
			detailsimei = new ListDetailsImei();
		} else {
			FacesContext.getCurrentInstance().addMessage("@form",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Plage deja existe", "Veuillez R�ssayer"));

		}

	}

	public void deleteDetailImei() {
		hotlist_service.deleteDetailImei(detailsimei1);
		loadDetailsImei();
	}

	public void modifDetailImei() {
		hotlist_service.updateDetailImei(detailsimei1);
		loadDetailsImei();
	}

	public void addDetailsAppele() {
		if (hotlist_service.findDetailsAppele(detailsappele.getHotlistnumber()) == null) {
			detailsappele.setNomUtilisateur(Util.getUser().getUName());
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			detailsappele.setDateModif(currentTimestamp);

			detailsappele.setAppele(appele1);
			hotlist_service.addDetailAppele(detailsappele);
			list_det_appele = hotlist_service.getListDetailsAppeles(appele1);
			detailsappele = new ListDetailsAppele();
		} else {
			FacesContext.getCurrentInstance().addMessage("@form",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Plage deja existe", "Veuillez R�ssayer"));

		}

	}

	public void modifierDetailsAppele() {

		hotlist_service.updateDetailAppele(detailsappele1);
		list_det_appele = hotlist_service.getListDetailsAppeles(appele1);

	}

	public void suppDetailsAppele() {

		hotlist_service.deleteDetailAppele(detailsappele1);
		list_det_appele = hotlist_service.getListDetailsAppeles(appele1);

	}

	public ListAppele getAppele() {
		return appele;
	}

	public void setAppele(ListAppele appele) {
		this.appele = appele;
	}

	public ListAppele getAppele1() {
		return appele1;
	}

	public void setAppele1(ListAppele appele1) {
		this.appele1 = appele1;
	}

	public List<ListAppele> getListe_appele() {
		return liste_appele;
	}

	public void setListe_appele(List<ListAppele> liste_appele) {
		this.liste_appele = liste_appele;
	}

	public ListDetailsAppele getDetailsappele() {
		return detailsappele;
	}

	public void setDetailsappele(ListDetailsAppele detailsappele) {
		this.detailsappele = detailsappele;
	}

	public ListDetailsAppele getDetailsappele1() {
		return detailsappele1;
	}

	public void setDetailsappele1(ListDetailsAppele detailsappele1) {
		this.detailsappele1 = detailsappele1;
	}

	public ListAppelant getAppelant() {
		return appelant;
	}

	public void setAppelant(ListAppelant appelant) {
		this.appelant = appelant;
	}

	public ListAppelant getAppelant1() {
		return appelant1;
	}

	public void setAppelant1(ListAppelant appelant1) {
		this.appelant1 = appelant1;
	}

	public List<ListAppelant> getListe_appelant() {
		return liste_appelant;
	}

	public void setListe_appelant(List<ListAppelant> liste_appelant) {
		this.liste_appelant = liste_appelant;
	}

	public List<ListDetailsAppelant> getList_det_appelant() {
		return list_det_appelant;
	}

	public void setList_det_appelant(List<ListDetailsAppelant> list_det_appelant) {
		this.list_det_appelant = list_det_appelant;
	}

	public ListImei getImei() {
		return imei;
	}

	public void setImei(ListImei imei) {
		this.imei = imei;
	}

	public ListImei getImei1() {
		return imei1;
	}

	public void setImei1(ListImei imei1) {
		this.imei1 = imei1;
	}

	public List<ListImei> getListe_imei() {
		return liste_imei;
	}

	public void setListe_imei(List<ListImei> liste_imei) {
		this.liste_imei = liste_imei;
	}

	public List<ListDetailsImei> getList_det_imei() {
		return list_det_imei;
	}

	public void setList_det_imei(List<ListDetailsImei> list_det_imei) {
		this.list_det_imei = list_det_imei;
	}

	public ListCellid getCellid() {
		return cellid;
	}

	public void setCellid(ListCellid cellid) {
		this.cellid = cellid;
	}

	public ListCellid getCellid1() {
		return cellid1;
	}

	public void setCellid1(ListCellid cellid1) {
		this.cellid1 = cellid1;
	}

	public List<ListCellid> getListe_cellid() {
		return liste_cellid;
	}

	public void setListe_cellid(List<ListCellid> liste_cellid) {
		this.liste_cellid = liste_cellid;
	}

	public List<ListDetailsCellid> getList_det_cellid() {
		return list_det_cellid;
	}

	public void setList_det_cellid(List<ListDetailsCellid> list_det_cellid) {
		this.list_det_cellid = list_det_cellid;
	}

	public ListDetailsCellid getDetailscellid() {
		return detailscellid;
	}

	public void setDetailscellid(ListDetailsCellid detailscellid) {
		this.detailscellid = detailscellid;
	}

	public ListDetailsCellid getDetailscellid1() {
		return detailscellid1;
	}

	public void setDetailscellid1(ListDetailsCellid detailscellid1) {
		this.detailscellid1 = detailscellid1;
	}

	public ListDetailsImei getDetailsimei() {
		return detailsimei;
	}

	public void setDetailsimei(ListDetailsImei detailsimei) {
		this.detailsimei = detailsimei;
	}

	public ListDetailsImei getDetailsimei1() {
		return detailsimei1;
	}

	public void setDetailsimei1(ListDetailsImei detailsimei1) {
		this.detailsimei1 = detailsimei1;
	}

	public ListDetailsAppelant getDetailsappelant() {
		return detailsappelant;
	}

	public void setDetailsappelant(ListDetailsAppelant detailsappelant) {
		this.detailsappelant = detailsappelant;
	}

	public ListDetailsAppelant getDetailsappelant1() {
		return detailsappelant1;
	}

	public void setDetailsappelant1(ListDetailsAppelant detailsappelant1) {
		this.detailsappelant1 = detailsappelant1;
	}

	

}
