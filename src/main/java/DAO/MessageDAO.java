package DAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    private final Connection connection;

    public MessageDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

    /**
     * Inserts a blog post record
     * @param message
     * @return blog post id if insert successful, else return -1;
     */
    public int createMessage(Message message) {
        String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
        int message_id = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"message_id"});
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()) {
                        message_id = rs.getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message_id;
    }

    /**
     * Fetches a blog post by its id
     * @param message_id
     * @return Message object of blog post with given message_id, else null
     */
    public Message getMessage(int message_id) {
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?";
        Message message = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                message = new Message(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getLong(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Fetches all messages created by all accounts
     * @return List of all messages created by all accounts if any, else empty list
     */
    public List<Message> getAllMessages() {
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message";
        List<Message> allMessages = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getLong(4));
                allMessages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMessages;
    }

    /**
     * Fetches all messages created by given account_id
     * @return List of all messages created by account_id if present, else empty list
     */
    public List<Message> getAllMessagesByAccountId(int account_id) {
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE posted_by = ?";
        List<Message> allMessages = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getLong(4));
                allMessages.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMessages;
    }

    /**
     * Fetch a message with given message_id
     * @param message_id
     * @return Message object if message exists, else null
     */
    public Message getMessageById(int message_id) {
        String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?";
        Message message = null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                message = new Message(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getLong(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
