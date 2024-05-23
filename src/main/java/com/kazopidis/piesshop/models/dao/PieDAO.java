package com.kazopidis.piesshop.models.dao;

import com.kazopidis.piesshop.models.model.Pie;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PieDAO {

    public static Pie preparePieObject(ResultSet resultSet) throws SQLException {
        Pie pie = new Pie();
        pie.setId(resultSet.getInt("id"));
        pie.setName(resultSet.getString("name"));
        pie.setPrice(resultSet.getDouble("price"));
        pie.setFileName(resultSet.getString("filename"));
        pie.setIngredients(resultSet.getString("ingredients"));
        pie.setDescription(resultSet.getString("description"));

        return pie;
    }

    public static Pie getPieById(int id) {

        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection =  ds.getConnection();

            String query = "SELECT * FROM pie WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            Pie pie = preparePieObject(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

            return pie;

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Pie> getPies()  {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection =  ds.getConnection();

            String query = "SELECT * FROM pie";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Pie> pies = new ArrayList<>();
            while(resultSet.next()) {
                Pie pie =  preparePieObject(resultSet);
                pies.add(pie);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return pies;

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
