package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MessageDao {
    public Message insertMessage(Message newMsg) {
        Connection connection = ConnectionUtil.getConnection();

        return null;
    }
}
