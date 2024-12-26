package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class AccountDao {
    public Account insertAccount(Account acc) {
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.executeUpdate();

            ResultSet pk = ps.getGeneratedKeys();
            if(pk.next()) {
                acc.setAccount_id(pk.getInt(1));
                return acc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean usernameUsed(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            return rs.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Account getAccount(Account acc) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT account_id FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                acc.setAccount_id(rs.getInt(1));
                return acc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Account getAccount(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT username, password FROM account WHERE account_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return new Account(id, rs.getString(1), rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
