package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    private final Connection connection;

    public AccountDAO() {
        connection = ConnectionUtil.getConnection();
    }
    /**
     * Inserts new account information into the database
     * @param account
     * @return true if successfully inserted, else false
     */
    public boolean registerUser(Account account){
        String sql = "INSERT INTO Account(username, password) VALUES(?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if account with given username already exists
     * @param username
     * @return true if account with given username exists, else false
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT username FROM Account WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.isBeforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Fetches account details for given username
     * @param username
     * @return Account of user with given username
     */
    public Account getAccount(String username) {
        String sql = "SELECT account_id, username, password FROM Account WHERE username = ?";
        Account account = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                account = new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
 }
