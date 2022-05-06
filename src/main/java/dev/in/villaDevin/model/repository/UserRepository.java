package dev.in.villaDevin.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import dev.in.villaDevin.model.User;

@Repository
public class UserRepository {
    
    
    
    private final String TABLE_NAME = "\"user\"";
    private final Connection dbConnection;

    public UserRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public User save (User newUser) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO " + TABLE_NAME + " (resident_id, email, password) VALUES(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        pStmt.setLong(1, newUser.getResidentId());
        pStmt.setString(2, newUser.getEmail());
        pStmt.setString(3, newUser.getPassword());
        pStmt.execute();

        ResultSet resultSet = pStmt.getGeneratedKeys();
       
        while (resultSet.next()) {
        	newUser.setId(resultSet.getLong("id")); 

        }

        return newUser;
    }

    public Optional<User> findOneByEmail(String email) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE email = ?");
        pStmt.setString(1, email);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        User user = null;
        while (resultSet.next()) {

            user = new User(
                resultSet.getLong("id"),
                resultSet.getLong("resident_id"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
        }

        return Optional.ofNullable(user);
    }

    public Optional<User> findOneByResidentId(Long residentId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE resident_id = ?");
        pStmt.setLong(1, residentId);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        User user = null;
        while (resultSet.next()) {

            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getLong("resident_id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }

        return Optional.ofNullable(user);
    }

    public void updatePassword(Long userId, String newPassword) throws SQLException{
        PreparedStatement pStmt = dbConnection.prepareStatement("UPDATE " + TABLE_NAME + " SET password = ? WHERE id = ?");
        pStmt.setString(1, newPassword);
        pStmt.setLong(2, userId);
        pStmt.execute();
    }

    public void deleteByResidentId(Long residentId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE resident_id = ?");
        pStmt.setLong(1, residentId);
        pStmt.execute();
    }

	
}
