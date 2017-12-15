package com.fresenius.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.fresenius.jdbc.EnergyData;
import com.fresenius.jdbc.EnergyDataDbHandler;
/**
 * Controller class
 * @author Max
 * @author Kevin
 */
@ManagedBean
@SessionScoped

public class EnergyDataController {

	private List<EnergyData> arraydata;
	private EnergyDataDbHandler energyDataDbHandler;
	private Logger logger = Logger.getLogger(getClass().getName());

	public EnergyDataController() throws Exception {
		arraydata = new ArrayList<>();

		energyDataDbHandler = EnergyDataDbHandler.getInstance();
	}

	public List<EnergyData> getEnergyData() {
		return arraydata;
	}

	/**
	 * Loads up all the energy-data on start of the view
	 * @param theEData
	 */
	public void loadEnergyData(EnergyData theEData) {
		
		logger.info("Loading energy data");

		arraydata.clear();
		try {
			// get all data from database
			arraydata = energyDataDbHandler.getEnergyData(theEData);

		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading energy data", exc);

			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	/**
	 * Clicked row gets an edit choice, based on primefaces
	 * @param event
	 */
	public void onRowEdit(RowEditEvent event) {
		//Gets the specified row
		Object theEData = new EnergyData();
		theEData = event.getObject();
		int eDataId = ((EnergyData) theEData).getElectricityId();
		
		logger.info("loading data: " + eDataId);
		
		try {
			//Primefaces message for action
	        FacesMessage msg = new FacesMessage("Edit success", ((EnergyData) event.getObject()).msgId());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	        
	        //Casts to EnergyData
	        updateEnergyData((EnergyData) theEData);
	        
		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error loading data ID: " + eDataId, exc);

			addErrorMessage(exc);

		}
		
	}
	/**
	 * Adds new energy data when commandButton is used in modal form
	 * @param theEData
	 * @return
	 */
	public String addEnergyData(EnergyData theEData) {
		
		logger.info("Adding energy data: " + theEData);

		try {

			// Add data to the database
			energyDataDbHandler.addEnergyData(theEData);


		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error adding data", exc);

			// Add error message for JSF page
			addErrorMessage(exc);

			return null;
		}

		return "test-list?faces-redirect=true";
	}
	/**
	 * Updates specified row
	 * @param theEData
	 */
	public void updateEnergyData(EnergyData theEData) {
		logger.info("updating data: " + theEData);

		try {

			// Update data in the database
			energyDataDbHandler.updateEnergyData(theEData);
			
		} catch (Exception exc) {
			// Send this to server logs
			logger.log(Level.SEVERE, "Error updating data: " + theEData, exc);

			// Add error message for JSF page
			addErrorMessage(exc);

		}

	}

	/**
	 * Filter's out the table based on searchquerys value
	 * @param theEData
	 * @return
	 */
	public String searchEnergyData(EnergyData theEData) {

		logger.info("Searching data:"+ theEData) ;

		try {
			//Changes the SQL injection in energy data based on searchquery's value
			energyDataDbHandler.searchEnergyData(theEData);

		} catch (Exception exc) {
			//Send this to server logs
			logger.log(Level.SEVERE, "Error deleting data: " + exc);

			addErrorMessage(exc);
			return null;
		}
		return "test-list.xhtml";
	}
	/**
	 * Deletes a row
	 * @param eDataId
	 * @return
	 */
	
	public String deleteEnergyData(int eDataId) {
		logger.info("Deleting data id:" + eDataId);

		try {
			//Delete's a row in the database
			energyDataDbHandler.deleteEnergyData(eDataId);

		} catch (Exception exc) {
			//Send this to the server logs
			logger.log(Level.SEVERE, "Error deleting data: " + eDataId, exc);

			addErrorMessage(exc);
			return null;
		}
		return "test-list.xhtml";
	}

	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}