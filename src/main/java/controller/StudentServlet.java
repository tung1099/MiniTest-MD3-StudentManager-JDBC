package controller;

import dao.classes.ClassDAO;
import dao.students.StudentDAO;
import model.Classes;
import model.Students;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "/students")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClassDAO classDAO;
    private StudentDAO studentDAO;

    public void init() {
        classDAO = new ClassDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "createStudent":
                    showFormAddStudent(request, response);
                    break;
                case "editStudent":
                    showEditForm(request, response);
                    break;
                case "deleteStudent":
                    deleteStudent(request, response);
                    break;
                case "listClass":
                    listClass(request, response);
                    break;
                case "createClass":
                    showFormAddClass(request, response);
                    break;
                case "editClass":
                    showFormEditClass(request, response);
                    break;
                case "deleteClass":
                    deleteClass(request, response);
                    break;
                default:
                    listStudent(request,response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "createStudent":
                    insertStudent(request, response);
                    break;
                case "editStudent":
                    updateStudent(request, response);
                    break;
                case "createClass":
                    insertClass(request,response);
                    break;
                case "editClass":
                    updateClass(request,response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    private void listStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Students> listStudent = studentDAO.selectAllStudent();
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/list.jsp");
        dispatcher.forward(request, response);
    }
    private void listClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Classes> listClass = classDAO.selectAllClass();
        request.setAttribute("listClass", listClass);
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/list.jsp");
        dispatcher.forward(request, response);
    }
    private void showFormAddStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/create.jsp");
        dispatcher.forward(request, response);
    }
    private void showFormAddClass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/create.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Students existingStudent = studentDAO.selectStudent(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/edit.jsp");
        request.setAttribute("student", existingStudent);
        dispatcher.forward(request, response);

    }
    private void showFormEditClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Classes existingClass = classDAO.selectClass(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/edit.jsp");
        request.setAttribute("class", existingClass);
        dispatcher.forward(request, response);

    }
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        int class_id = Integer.parseInt(request.getParameter("class_id"));
        Students newStudent = new Students(name, address, class_id);
        studentDAO.insertStudent(newStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/create.jsp");
        dispatcher.forward(request, response);
    }
    private void insertClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Classes newClass = new Classes(name, description);
        classDAO.insertClass(newClass);
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/create.jsp");
        dispatcher.forward(request, response);
    }
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        int class_id = Integer.parseInt(request.getParameter("class_id"));

        Students book = new Students(id, name, address, class_id);
        studentDAO.updateStudent(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/edit.jsp");
        dispatcher.forward(request, response);
    }
    private void updateClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Classes book = new Classes(id, name, description);
        classDAO.updateClass(book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);

        List<Students> listStudent = studentDAO.selectAllStudent();
        request.setAttribute("listStudent", listStudent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("student/list.jsp");
        dispatcher.forward(request, response);
    }
    private void deleteClass(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        classDAO.deleteClass(id);

        List<Classes> listClass = classDAO.selectAllClass();
        request.setAttribute("listClass", listClass);
        RequestDispatcher dispatcher = request.getRequestDispatcher("class/list.jsp");
        dispatcher.forward(request, response);
    }
}


