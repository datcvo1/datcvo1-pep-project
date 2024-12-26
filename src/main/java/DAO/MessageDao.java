package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class MessageDao {
    public Message insertMessage(Message newMsg) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, newMsg.getPosted_by());
            ps.setString(2, newMsg.getMessage_text());
            ps.setLong(3, newMsg.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet pk = ps.getGeneratedKeys();
            if(pk.next()) {
                newMsg.setMessage_id(pk.getInt(1));
                return newMsg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while(rs.next()) {
                messages.add(new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
