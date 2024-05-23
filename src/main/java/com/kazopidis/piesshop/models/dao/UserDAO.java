package com.kazopidis.piesshop.models.dao;

import com.kazopidis.piesshop.crypto.Crypto;
import com.kazopidis.piesshop.forms.form.FormRegister;
import com.kazopidis.piesshop.models.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserDAO {

    public static User prepareUserObject(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setFullName(resultSet.getString("fullname"));
        user.setEmail(resultSet.getString("email"));
        user.setTel(resultSet.getString("tel"));
        user.setStatus(resultSet.getString("status"));
        user.setCode(resultSet.getString("code"));

        return user;
    }

    public static User getUserById(int id) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM piesshopdb.user WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            User user = UserDAO.prepareUserObject(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println(user);

            return user;
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserBySession(String session) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM user WHERE session = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,session);

            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = prepareUserObject(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return user;

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserByUsername(String username) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);

            ResultSet resultSet =  statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = prepareUserObject(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println(user);

            return user;

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static User getUserByEmail(String email) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,email);

            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = prepareUserObject(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println(user);

            return user;

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Saving the user as unverified first
    public static int storeUserUnverified(FormRegister formRegister) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection =  ds.getConnection();

            //prepare password (salt + hash)
            String salt = Crypto.salt();
            String hashedPassword = Crypto.hash(formRegister.getPassword(),salt);


            // Store user
            Random r = new Random();
            String code = String.valueOf(r.nextInt(100000));

            String query = "INSERT INTO piesshopdb.user (username, password, fullname, email, tel, status, code, salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, formRegister.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, formRegister.getFullName());
            statement.setString(4, formRegister.getEmail());
            statement.setString(5, formRegister.getTel());
            statement.setString(6, "unverified");
            statement.setString(7, code);
            statement.setString(8, salt);

            statement.executeUpdate();

            ResultSet genKeys = statement.getGeneratedKeys();

            genKeys.next();

            int userId = genKeys.getInt(1);

            statement.close();
            genKeys.close();

            //Store user role
            query = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setInt(2, 2);

            statement.executeUpdate();

            statement.close();

            connection.close();

            return userId;

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean verifyUser(String code) {

        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT * FROM user WHERE code = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            boolean result = false;

            if (resultSet.next()) {
                User user = prepareUserObject(resultSet);

                if (user.getStatus().equals("unverified")) {
                    query = "UPDATE piesshopdb.user SET status = 'verified' WHERE id = ?";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, user.getId());

                    System.out.println("userID: "+user.getId());

                    statement.executeUpdate();

                    statement.close();
                    result = true;
                }
            }

            resultSet.close();
            statement.close();
            connection.close();

            return  result;


        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }

    }

    public static void deleteUnverifiedUsers() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "DELETE FROM user WHERE status = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,"unverified");

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUnverifiedUsersCode() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "UPDATE user SET code = NULL WHERE status = ? AND code IS NOT NULL";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,"verified");

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Integer> adminStats() {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT COUNT(*) AS cnt FROM user WHERE status = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,"unverified");

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            int cntVerified = resultSet.getInt("cnt");

            resultSet.close();
            statement.close();

            query = "SELECT COUNT(*) AS cnt FROM user WHERE status = ? AND code IS NOT NULL";

            statement = connection.prepareStatement(query);
            statement.setString(1,"verified");

           resultSet = statement.executeQuery();

           resultSet.next();

           int cntVerifiedCodes = resultSet.getInt("cnt");

           resultSet.close();
           statement.close();

            List<Integer> adminStats = new ArrayList<>();
            adminStats.add(cntVerified);
            adminStats.add(cntVerifiedCodes);

            connection.close();

            return adminStats;
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSalt(String username) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            //prepare password (salt + hash)
            String query = "SELECT salt FROM user WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);

            ResultSet resultSet = statement.executeQuery();

            String salt = null;
            if (resultSet.next()) {
                salt = resultSet.getString("salt");
            }

            resultSet.close();
            statement.close();
            connection.close();

            return salt;
        } catch (SQLException | NamingException e) {
        throw new RuntimeException(e);
        }
    }

    public static User login(String username, String password) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            //prepare password (salt + hash)
            String salt = getSalt(username);
            String hashedPassword = Crypto.hash(password, salt);

            // get stored username + password
            String query = "SELECT * FROM user WHERE username = ? AND password = ? AND status = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,username);
            statement.setString(2,hashedPassword);
            statement.setString(3,"verified");

            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = prepareUserObject(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            System.out.println(user);

            return user;

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUserResetPassword(String email, int code) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "UPDATE user SET code = ?, status = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,code);
            statement.setString(2,"reset");
            statement.setString(3,email);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUserNewPassword(String password, int code) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            // Get salt from the database based on the code
            String querySalt = "SELECT salt FROM user WHERE code = ?";
            PreparedStatement statementSalt = connection.prepareStatement(querySalt);
            statementSalt.setInt(1, code);
            ResultSet resultSetSalt = statementSalt.executeQuery();

            String salt = null;
            if (resultSetSalt.next()) {
                salt = resultSetSalt.getString("salt");
            }

            resultSetSalt.close();
            statementSalt.close();

            if (salt != null) {
                // Hash the new password
                String hashedPassword = Crypto.hash(password, salt);

                // Update user information in the database
                String queryUpdate = "UPDATE user SET status = ?, code = NULL, password = ? WHERE code = ?";
                PreparedStatement statementUpdate = connection.prepareStatement(queryUpdate);
                statementUpdate.setString(1, "verified");
                statementUpdate.setString(2, hashedPassword);
                statementUpdate.setInt(3, code);

                statementUpdate.executeUpdate();

                statementUpdate.close();
            }

            connection.close();

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void storeSession(String username, String session) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "UPDATE user SET session = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,session);
            statement.setString(2,username);

            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRole(String session) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT r.description AS role " +
                    "FROM user u " +
                    "JOIN user_role ur ON ur.user_id = u.id " +
                    "JOIN role r ON ur.role_id = r.role_id " +
                    "WHERE u.session = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, session);

            ResultSet resultSet = statement.executeQuery();

            String role = null;
            if (resultSet.next())
                role = resultSet.getString("role");

            resultSet.close();
            statement.close();
            connection.close();

            return role;

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean logout(String session) {
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/piesshopdb");
            Connection connection = ds.getConnection();

            String query = "SELECT username FROM user WHERE session = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,session);

            ResultSet resultSet = statement.executeQuery();

            String username = null;
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }

            resultSet.close();
            statement.close();

            if (username != null) {
                query = "UPDATE user SET session = NULL WHERE username = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1,username);
                statement.executeUpdate();

                statement.close();
            }

            connection.close();

            return username != null;
            /*
            * Returns a boolean indicating whether the logout was successful.
            * If a username was found, the method returns true, indicating a successful logout; otherwise, it returns false.*/

        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

}























