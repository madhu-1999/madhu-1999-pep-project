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
        int rowsAffected = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected > 0;
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
     * @return Account of user with given username, if exists else null
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

     /**
     * Checks if account exists for given account id
     * @param username
     * @return true if account exists, else false
     */
    public boolean isAccountExists(int account_id) {
        String sql = "SELECT account_id, username, password FROM Account WHERE account_id = ?";
        boolean exists = false;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            exists = ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
 }
