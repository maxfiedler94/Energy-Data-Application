package com.fresenius.jdbc;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
/**
 * Contains all the beans
 * @author Max
 * @author Kevin
 */
@ManagedBean
public class EnergyData {

	private int electricityId;
	private String zone;
	private String levelOneConsumer;
	private String levelTwoConsumer;
	private String levelThreeConsumer;
	private String searchQuery;
	private Date inputDate;
	private EnergyData selectedData;
	
	private double connInstalledPower;
	private double utilOfConsumer;
	private double anuallyHours;
	private double sum;

	// No-args constructor
	public EnergyData() {

	}

	// Args constructor
	public EnergyData(int electricityId, String zone, String levelOneConsumer, String levelTwoConsumer,
			String levelThreeConsumer, Date inputDate, double connInstalledPower
			,double utilOfConsumer,double anuallyHours,double sum) {
		this.electricityId = electricityId;
		this.zone = zone;
		this.levelOneConsumer = levelOneConsumer;
		this.levelTwoConsumer = levelTwoConsumer;
		this.levelThreeConsumer = levelThreeConsumer;
		this.inputDate = inputDate;
		
		this.connInstalledPower = connInstalledPower;
		this.utilOfConsumer = utilOfConsumer;
		this.anuallyHours = anuallyHours;
		this.sum = sum;
	}
	/**
	 * Converter for ID from int to String
	 * @return
	 */
	public String msgId(){
		String msgId = String.valueOf(getElectricityId());
		return msgId;
	}
	
	/**
	 * Cancels update/edit
	 * @param event
	 */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((EnergyData) event.getObject()).msgId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Resets the search
     */
	public void resetSearch() {
		searchQuery = null;
	}

	// Getters and setters for instance variables
	public int getElectricityId() {
		return electricityId;
	}

	public void setElectricityId(int electricityId) {
		this.electricityId = electricityId;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getLevelOneConsumer() {
		return levelOneConsumer;
	}

	public void setLevelOneConsumer(String levelOneConsumer) {
		this.levelOneConsumer = levelOneConsumer;
	}

	public String getLevelTwoConsumer() {
		return levelTwoConsumer;
	}

	public void setLevelTwoConsumer(String levelTwoConsumer) {
		this.levelTwoConsumer = levelTwoConsumer;
	}

	public String getLevelThreeConsumer() {
		return levelThreeConsumer;
	}

	public void setLevelThreeConsumer(String levelThreeConsumer) {
		this.levelThreeConsumer = levelThreeConsumer;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public double getConnInstalledPower() {
		return connInstalledPower;
	}

	public void setConnInstalledPower(double connInstalledPower) {
		this.connInstalledPower = connInstalledPower;
	}

	public double getUtilOfConsumer() {
		return utilOfConsumer;
	}

	public void setUtilOfConsumer(double utilOfConsumer) {
		this.utilOfConsumer = utilOfConsumer;
	}

	public double getAnuallyHours() {
		return anuallyHours;
	}

	public void setAnuallyHours(double anuallyHours) {
		this.anuallyHours = anuallyHours;
	}
	public EnergyData getSelectedData() {
		return selectedData;
	}

	public void setSelectedData(EnergyData selectedData) {
		this.selectedData = selectedData;
	}
	
	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	// To-string method for console
	@Override
	public String toString() {
		return "Data [id=" + electricityId + ", Zone= " + zone + ", Level-1-consumer= " + levelOneConsumer
				+ ", Level-2-consumer= " + levelTwoConsumer + ", Level-3-consumer= " + levelThreeConsumer
				+ ", Input date=" + inputDate +", Connected Installed Power" + connInstalledPower + ", Utilization of Consumer" +
				utilOfConsumer + ", Anually Hours" + anuallyHours + ", Sum" + sum +"]";
	}
}