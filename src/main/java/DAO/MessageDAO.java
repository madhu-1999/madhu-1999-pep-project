package DAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    private final Connection connection;

    public MessageDAO() {
        this.connection = ConnectionUtil.getConnection();
    }

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
}
