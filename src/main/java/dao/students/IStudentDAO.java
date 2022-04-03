package dao.students;

import model.Classes;
import model.Students;

import java.sql.SQLException;
import java.util.List;

public interface IStudentDAO {

    public void insertStudent(Students students) throws SQLException;

    public Students selectStudent(int id);

    public List<Students> selectAllStudent();

    public boolean deleteStudent(int id) throws SQLException;

    public boolean updateStudent(Students students) throws SQLException;
}
