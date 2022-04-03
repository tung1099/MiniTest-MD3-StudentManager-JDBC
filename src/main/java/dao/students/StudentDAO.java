package dao.students;

import model.Classes;
import model.Students;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/studentmanagement?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students (id,name,address,class_id) VALUES (?, ?, ?, ?);";
    private static final String SELECT_STUDENT_BY_ID = "select * from students where id =?";
    private static final String SELECT_ALL_STUDENT = "select * from students";
    private static final String DELETE_STUDENT_SQL = "delete from students where id = ?;";
    private static final String UPDATE_STUDENT_SQL = "update students set name = ?, address = ?, class_id = ? where id = ?;";

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
    public void insertStudent(Students students) throws SQLException {

        System.out.println(INSERT_STUDENT_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL)) {
            preparedStatement.setString(1, students.getName());
            preparedStatement.setString(2, students.getAddress());
            preparedStatement.setInt(3, students.getClass_id());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    @Override
    public Students selectStudent(int id) {

        Students students = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                int class_id = rs.getInt("class_id");
                students = new Students(id, name, address, class_id);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return students;
    }

    @Override
    public List<Students> selectAllStudent() {
        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Students> students = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENT);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int class_id = rs.getInt("class_id");

                students.add(new Students(id, name, address, class_id));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return students;
    }

    @Override
    public boolean deleteStudent(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateStudent(Students students) throws SQLException {

        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_SQL);) {
            statement.setString(1, students.getName());
            statement.setString(2, students.getAddress());
            statement.setInt(3, students.getClass_id());
            statement.setInt(4, students.getId());

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
