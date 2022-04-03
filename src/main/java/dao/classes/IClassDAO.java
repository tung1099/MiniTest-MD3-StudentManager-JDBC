package dao.classes;

import model.Classes;

import java.sql.SQLException;
import java.util.List;

public interface IClassDAO {
    public void insertClass(Classes classes) throws SQLException;

    public Classes selectClass(int id);

    public List<Classes> selectAllClass();

    public boolean deleteClass(int id) throws SQLException;

    public boolean updateClass(Classes classes) throws SQLException;
}
