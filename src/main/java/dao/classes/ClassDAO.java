package dao.classes;

import model.Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO implements IClassDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/studentmanagement?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String INSERT_CLASS_SQL = "INSERT INTO classes (id,name,description) VALUES (?, ?, ?);";
    private static final String SELECT_CLASS_BY_ID = "select * from classes where id =?";
    private static final String SELECT_ALL_CLASS = "select * from classes;";
    private static final String DELETE_CLASS_SQL = "delete from classes where id = ?;";
    private static final String UPDATE_CLASS_SQL = "update classes set name = ?,description= ? where id = ?;";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertClass(Classes classes) throws SQLException {

        System.out.println(INSERT_CLASS_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLASS_SQL)) {
            preparedStatement.setString(1, classes.getName());
            preparedStatement.setString(2, classes.getDescription());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Classes selectClass(int id) {
        Classes classes = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASS_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                classes = new Classes(id, name, description);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return classes;
    }

    @Override
    public List<Classes> selectAllClass() {
        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Classes> classes = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLASS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                classes.add(new Classes(id, name, description));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return classes;
    }

    @Override
    public boolean deleteClass(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_CLASS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateClass(Classes classes) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_CLASS_SQL);) {
            statement.setString(1, classes.getName());
            statement.setString(2, classes.getDescription());
            statement.setInt(3, classes.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}



