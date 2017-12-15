package com.fresenius.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.fresenius.jdbc.EnergyData;
import com.fresenius.jdbc.EnergyDataDbHandler;

/**
 * Communicates with the database, the jdbc class
 * 
 * @author Max
 * @author Kevin
 */
public class EnergyDataDbHandler {

	private static EnergyDataDbHandler instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/energy_data";

	public static EnergyDataDbHandler getInstance() throws Exception {
		if (instance == null) {
			instance = new EnergyDataDbHandler();
		}

		return instance;
	}

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	private EnergyDataDbHandler() throws Exception {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();

		DataSource theDataSource = (DataSource) context.lookup(jndiName);

		return theDataSource;
	}

	/**
	 * Swap between two SQL injections, one being filtered out with search and
	 * the other being a full list of the table
	 * 
	 * @param theEData
	 * @return
	 * @throws Exception
	 */
	public String searchEnergyData(EnergyData theEData) throws Exception {
		String sql;
		String searchQuery = theEData.getSearchQuery();

		if (searchQuery != null) {
			sql = "select * from energy_aspect_electricity" + " where zone like '%" + searchQuery + "%'";
			return sql;
		} else {
			sql = "select * from energy_aspect_electricity order by zone";
			return sql;
		}
	}

	/**
	 * Full list/or narrowed down list due to searchQuery
	 * 
	 * @param theEData
	 * @return
	 * @throws Exception
	 */
	public List<EnergyData> getEnergyData(EnergyData theEData) throws Exception {

		List<EnergyData> arraydata = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();

			// SQL defined based on searchQuery's value
			sql = searchEnergyData(theEData);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			// Process result set
			while (rs.next()) {

				// Retrieve data from result set row
				int electricityId = rs.getInt("electricity_id");
				String zone = rs.getString("zone");
				String levelOneConsumer = rs.getString("level_1_consumer");
				String levelTwoConsumer = rs.getString("level_2_consumer");
				String levelThreeConsumer = rs.getString("level_3_consumer");
				Date dateInput = rs.getDate("input_date");
				double connInstalledPower = rs.getDouble("connected_installed_power");
				double utilOfConsumer = rs.getDouble("utilization_of_consumer");
				double anuallyHours = rs.getDouble("anually_hours");
				double sum = connInstalledPower + utilOfConsumer;

				// Create new energydata object
				EnergyData tempData = new EnergyData(electricityId, zone, levelOneConsumer, levelTwoConsumer,
						levelThreeConsumer, dateInput, connInstalledPower, utilOfConsumer, anuallyHours, sum);

				// Add it to the list of data
				arraydata.add(tempData);
			}

			return arraydata;
		} finally {
			close(conn, stmt, rs);
		}
	}

	/**
	 * Add a new row to the table
	 * 
	 * @param theEData
	 * @throws Exception
	 */
	public void addEnergyData(EnergyData theEData) throws Exception {

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();

			// Insert into table
			String sql = "insert into energy_aspect_electricity"
					+ " (zone,level_1_consumer, level_2_consumer,level_3_consumer"
					+ ",input_date,connected_installed_power,utilization_of_consumer," + "anually_hours)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = conn.prepareStatement(sql);

			// Set parameters
			stmt.setString(1, theEData.getZone());
			stmt.setString(2, theEData.getLevelOneConsumer());
			stmt.setString(3, theEData.getLevelTwoConsumer());
			stmt.setString(4, theEData.getLevelThreeConsumer());
			stmt.setDate(5, new java.sql.Date(theEData.getInputDate().getTime()));
			stmt.setDouble(6, theEData.getConnInstalledPower());
			stmt.setDouble(7, theEData.getUtilOfConsumer());
			stmt.setDouble(8, theEData.getAnuallyHours());

			stmt.execute();
		} finally {
			close(conn, stmt);
		}

	}

	/**
	 * Method to get specified row in the table
	 * 
	 * @param eDataId
	 *            The ID of the row
	 * @return The row
	 * @throws Exception
	 */
	public EnergyData getEnergyData(int eDataId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select * from energy_aspect_electricity" + " where electricity_id=?";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, eDataId);
			rs = stmt.executeQuery();

			EnergyData theEData = null;
			// Process result set
			if (rs.next()) {
				// Get values from db
				int electricityId = rs.getInt("electricity_id");
				String zone = rs.getString("zone");
				String levelOneConsumer = rs.getString("level_1_consumer");
				String levelTwoConsumer = rs.getString("level_2_consumer");
				String levelThreeConsumer = rs.getString("level_3_consumer");
				Date inputDate = rs.getDate("input_date");
				double connInstalledPower = rs.getDouble("connected_installed_power");
				double utilOfConsumer = rs.getDouble("utilization_of_consumer");
				double anuallyHours = rs.getDouble("anually_hours");

				// Create new data object
				double sum = connInstalledPower + utilOfConsumer;
				theEData = new EnergyData(electricityId, zone, levelOneConsumer, levelTwoConsumer, levelThreeConsumer,
						inputDate, connInstalledPower, utilOfConsumer, anuallyHours, sum);

			} else {
				throw new Exception("Could not find Energy Data id: " + eDataId);
			}
			return theEData;
		} finally {
			close(conn, stmt, rs);
		}
	}

	/**
	 * Update specified row in table
	 * 
	 * @param theEData
	 *            Current value in the specified row
	 * @throws Exception
	 */
	public void updateEnergyData(EnergyData theEData) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();

			String sql = "update energy_aspect_electricity " + " set zone=?, level_1_consumer=?, level_2_consumer=?,"
					+ "level_3_consumer=?,input_date=?,connected_installed_power=?,"
					+ "utilization_of_consumer=?,anually_hours=?" + "where electricity_id=?";

			stmt = conn.prepareStatement(sql);

			// Set parameters
			stmt.setString(1, theEData.getZone());
			stmt.setString(2, theEData.getLevelOneConsumer());
			stmt.setString(3, theEData.getLevelTwoConsumer());
			stmt.setString(4, theEData.getLevelThreeConsumer());
			stmt.setDate(5, new java.sql.Date(theEData.getInputDate().getTime()));

			stmt.setDouble(6, theEData.getConnInstalledPower());
			stmt.setDouble(7, theEData.getUtilOfConsumer());
			stmt.setDouble(8, theEData.getAnuallyHours());
			stmt.setInt(9, theEData.getElectricityId());

			stmt.execute();
		} finally {
			close(conn, stmt);
		}

	}

	/**
	 * Delete specified row in table
	 * 
	 * @param eDataId
	 *            ID of specified row
	 * @throws Exception
	 */
	public void deleteEnergyData(int eDataId) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();

			String sql = "delete from energy_aspect_electricity" + " where electricity_id=?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, eDataId);
			stmt.execute();

		} finally {
			close(conn, stmt);
		}
	}

	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();

		return theConn;
	}

	/**
	 * Close for methods where Connection and statement is used
	 * 
	 * @param theConn
	 * @param theStmt
	 */
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}

	/**
	 * Close for methods where Connection,Statement and ResultSet is used
	 * 
	 * @param theConn
	 * @param theStmt
	 * @param theRs
	 */
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}