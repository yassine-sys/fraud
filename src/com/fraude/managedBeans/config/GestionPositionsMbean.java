package com.fraude.managedBeans.config;

import java.sql.Timestamp;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.Cell;
import com.fraude.entities.Util;
import com.fraude.interfaces.CellRemote;
import com.fraude.interfaces.FraudeHotlistRemote;
import com.fraude.services.CellService;
@Path("/fraud")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes(MediaType.APPLICATION_JSON)
@ManagedBean(name = "manage_position")
@ViewScoped
public class GestionPositionsMbean {

	@EJB
	private CellRemote cell_service;

	@EJB
	private FraudeHotlistRemote hotlist_service;

	private List<Cell> liste_cell = new ArrayList<>();
	private Cell cell;
	private Cell cell1;

	@PostConstruct
	public void init() {
		liste_cell = cell_service.getAllCell();
		cell = new Cell();
		cell1 = new Cell();

	}
	
	@GET
	@Path("/getAllCell")
	public List<Cell> getAllCell() {
		return cell_service.getAllCell();
	}

	public void ajouter() {
		cell.setNomUtilisateur(Util.getUser().getUName());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		cell.setDateModif(currentTimestamp);
		cell_service.addCell(cell);
		liste_cell = cell_service.getAllCell();
		cell = new Cell();
	}
	@POST
	@Path("/AddCell")
	@Produces(MediaType.APPLICATION_JSON)
	public void ajouter(Cell cell) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			cell.setDateModif(currentTimestamp);
			cell_service.addCell(cell);
		//	return Response.status(Response.Status.CREATED).entity("catfraud added successfully").build();
		} catch (Exception e) {
			//return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				//	.entity("Error adding service: " + e.getMessage()).build();
		}
	}
	public void delete() {
		cell_service.deleteCell(cell1);
		liste_cell = cell_service.getAllCell();
	}
	
	@DELETE
	@Path("/deleteCell/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCell(@PathParam("id") Integer id) {
		try {
			cell_service.deleteCell(id); // Delete the entity
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to delete Repertoire").build();
		}
	}


	public void modifier() {
		cell1.setNomUtilisateur(Util.getUser().getNomUtilisateur());
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
		cell1.setDateModif(currentTimestamp);
		cell_service.updateCell(cell1);
		liste_cell = cell_service.getAllCell();
	}
	@PUT
	@Path("/updateCell/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCell(@PathParam("id") Integer id, Cell updateRequest) {
		try {
			Calendar calendar = Calendar.getInstance();
			Timestamp currentTimestamp = new java.sql.Timestamp(calendar.getTime().getTime());
			updateRequest.setDateModif(currentTimestamp);
			cell_service.updateCell(id, updateRequest); // Call the service to update the Repertoire

			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			// Handle exceptions or validation errors here
			return Response.status(Response.Status.BAD_REQUEST).entity("Failed to update categorie").build();
		}
	}

	public List<Cell> getListe_cell() {
		return liste_cell;
	}

	public void setListe_cell(List<Cell> liste_cell) {
		this.liste_cell = liste_cell;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public Cell getCell1() {
		return cell1;
	}

	public void setCell1(Cell cell1) {
		this.cell1 = cell1;
	}

}
