package com.kazopidis.piesshop.models.dao;

import com.kazopidis.piesshop.models.model.Area;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {

    public static Area prepareAreaObject(ResultSet resultSet) throws SQLException {
        Area area = new Area();

        area.setId(resultSet.getInt("id"));
        area.setDescription(resultSet.getString("description"));

        return area;
    }

    public static Area getAreaById(int id) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM area WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            Area area = prepareAreaObject(resultSet);

            statement.close();
            resultSet.close();
            connection.close();

            return  area;
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Area> getAreas() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM area";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            List<Area> areas = new ArrayList<>();

            while(resultSet.next()) {
                Area area = prepareAreaObject(resultSet);
                areas.add(area);
            }

            statement.close();
            resultSet.close();
            connection.close();

            return areas;

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
