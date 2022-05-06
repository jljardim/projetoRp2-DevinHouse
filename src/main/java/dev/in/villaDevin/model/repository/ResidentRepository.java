package dev.in.villaDevin.model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dev.in.villaDevin.model.Resident;

@Repository
public class ResidentRepository {

	private static final String TABLE_NAME = "resident";

	private final Connection dbConnection;

	public ResidentRepository(Connection dbConnection) {
		super();
		this.dbConnection = dbConnection;
	}

	public List<Resident> findByName(String name) throws SQLException {
		PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE name ilike ?");
		pStmt.setString(1, "%" + name + "%");
		pStmt.execute();

		ResultSet resultSet = pStmt.getResultSet();

		List<Resident> residents = new ArrayList<>();
		while (resultSet.next()) {
			final Resident resident = new Resident(resultSet.getLong("id"), resultSet.getString("uuid"),
					resultSet.getString("name"), resultSet.getString("lastname"),
					resultSet.getDate("datenasc").toLocalDate(), resultSet.getBigDecimal("income"),
					resultSet.getString("cpf")

			);
			residents.add(resident);
		}
		return residents;
	}

	public Resident save(Resident resident) throws SQLException {
		PreparedStatement pStmt = dbConnection.prepareStatement(
				"INSERT INTO " + TABLE_NAME + " (uuid, name, lastname, datenasc, income, cpf) VALUES(?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		pStmt.setString(1, resident.getUuid());
		pStmt.setString(2, resident.getName());
		pStmt.setString(3, resident.getLastName());
		pStmt.setDate(4, Date.valueOf(resident.getDateNasc()));
		pStmt.setBigDecimal(5, resident.getIncome());
		pStmt.setString(6, resident.getCpf());
		pStmt.execute();

		ResultSet resultSet = pStmt.getGeneratedKeys();

		while (resultSet.next()) {
			resident.setId(resultSet.getLong(1));

		}
		return resident;
	}

	public List<Resident> findAll() throws SQLException {
		Statement stmt = dbConnection.createStatement();
		stmt.execute("SELECT * FROM " + TABLE_NAME);

		ResultSet resultSet = stmt.getResultSet();

		List<Resident> residents = new ArrayList<>();
		while (resultSet.next()) {
			final Resident resident = new Resident(resultSet.getLong("id"), resultSet.getString("uuid"),
					resultSet.getString("name"), resultSet.getString("lastname"),
					resultSet.getDate("datenasc").toLocalDate(), resultSet.getBigDecimal("income"),
					resultSet.getString("cpf")

			);
			residents.add(resident);
		}
		return residents;
	}

//	
	public Resident findAllById(Long id) throws SQLException {
		PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
		pStmt.setLong(1, id);
		pStmt.execute();

		ResultSet resultSet = pStmt.getResultSet();

		Resident resident = null;
		while (resultSet.next()) {
			resident = new Resident(resultSet.getLong("id"), resultSet.getString("uuid"), resultSet.getString("name"),
					resultSet.getString("lastname"), resultSet.getDate("datenasc").toLocalDate(),
					resultSet.getBigDecimal("income"), resultSet.getString("cpf")

			);
		}
		return resident;
	}

	public void deleteById(Long residentId) throws SQLException {
		PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
		pStmt.setLong(1, residentId);
		pStmt.execute();
	}

}
