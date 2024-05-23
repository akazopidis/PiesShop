package com.kazopidis.piesshop.models.dao;

import com.kazopidis.piesshop.models.model.OrderItem;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {
    public static void storeOrderItem(OrderItem orderItem) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "INSERT INTO piesshopdb.order_item (order_id, pie_id, quantity)"+
                    " VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1,orderItem.getOrderId());
            statement.setInt(2,orderItem.getPieId());
            statement.setInt(3,orderItem.getQuantity());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
