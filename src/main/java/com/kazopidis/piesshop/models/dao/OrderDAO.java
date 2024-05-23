package com.kazopidis.piesshop.models.dao;

import com.kazopidis.piesshop.forms.form.FormOrder;
import com.kazopidis.piesshop.models.model.OrderItem;
import com.kazopidis.piesshop.models.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    // Nested Class
    public static class RecentOrderHistoryItem {
        private String stamp; // stamp of Order page
        private Map<String, Integer> orderItems = new HashMap<>(); // a map of order items (orderItems), where the keys are pie names and the values are quantities.

        public RecentOrderHistoryItem() {
        }

        public RecentOrderHistoryItem(String stamp, Map<String, Integer> orderItems) {
            this.stamp = stamp;
            this.orderItems = orderItems;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        public Map<String, Integer> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(Map<String, Integer> orderItems) {
            this.orderItems = orderItems;
        }

        @Override
        public String toString() {
            return "RecentOrderHistoryItem{" +
                    "stamp=" + stamp +
                    ", orderItems=" + orderItems +
                    '}';
        }
    }

    /*
    I dont need a wrapper for Order. This is the use of FormOrder
     */

    public static void storeOrder(FormOrder formOrder, User user) {

        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "INSERT INTO piesshopdb.order (fullname, address, area_id, email, tel, comments, offer, payment, stamp, user_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, formOrder.getFullName());
            statement.setString(2, formOrder.getAddress());
            statement.setInt(3, formOrder.getAreaId());
            statement.setString(4, formOrder.getEmail());
            statement.setString(5, formOrder.getTel());
            statement.setString(6, formOrder.getComments());
            statement.setBoolean(7, formOrder.isOffer());
            statement.setString(8, formOrder.getPayment());
            statement.setTimestamp(9, java.sql.Timestamp.valueOf(formOrder.getTimestamp()));
            statement.setInt(10, user.getId());

            statement.executeUpdate();

            // Get the id, to use it later
            ResultSet genKeys = statement.getGeneratedKeys();

            int orderId = -1;

            if (genKeys.next()) {
                orderId = genKeys.getInt(1);
            }

            System.out.println("key: " + orderId);

            statement.close();
            genKeys.close();
            connection.close();

            // Updating OrderItems Table
            // relation of the order id with the order_id from order_items
            if (orderId != -1) {
                for (OrderItem orderItem : formOrder.getOrderItems()) {
                    orderItem.setOrderId(orderId);
                    OrderItemDAO.storeOrderItem(orderItem);
                }
            }
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // This method retrieves the recent order history of a specific user.
    //It takes a User object and an integer n as parameters, where n represents the number of recent orders to retrieve.
    public static List<RecentOrderHistoryItem> recentOrdersOfUser(User user, int n) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            // Constructs a SQL query to fetch recent orders of the specified user.
            String query = "SELECT o.stamp AS stamp, p.name AS pie, oi.quantity AS quantity\n" +
                    "FROM order_item oi\n" +
                    "\tJOIN pie p ON oi.pie_id = p.id\n" +
                    "    JOIN piesshopdb.order o ON oi.order_id = o.id\n" +
                    "    JOIN piesshopdb.user u ON o.user_id = u.id\n" +
                    "WHERE u.id = ?\n" +
                    "ORDER BY stamp DESC, p.id\n" +
                    "LIMIT ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            statement.setInt(2, getNumberOfPies() * n);

            ResultSet resultSet = statement.executeQuery();

            List<RecentOrderHistoryItem> orders = new ArrayList<>();
            LocalDateTime previous = null;

            while (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString("quantity")) > 0) {
                    LocalDateTime stamp = resultSet.getTimestamp("stamp").toLocalDateTime();
                    if (!stamp.equals(previous)) {
                        orders.add(new RecentOrderHistoryItem(
                                stamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy(HH:mm:ss)")),
                                new HashMap<>()
                        ));
                        orders.get(orders.size() - 1).getOrderItems().put(resultSet.getString("pie"), resultSet.getInt("quantity"));
                        previous = stamp; //Updates the previous timestamp variable with the current timestamp (stamp).
                    } else {
                        orders.get(orders.size() - 1).getOrderItems().put(resultSet.getString("pie"), resultSet.getInt("quantity"));
                    }
                }
            }

            resultSet.close();
            statement.close();
            connection.close();

            return orders;
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getNumberOfPies() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT COUNT(*) AS pie_counter FROM pie";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            int numberOfPies = 0;
            if (resultSet.next()) {
                numberOfPies = resultSet.getInt("pie_counter");
            }

            resultSet.close();
            statement.close();
            connection.close();

            return numberOfPies;

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
