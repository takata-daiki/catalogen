//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.ems.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ems.dao.UserDao;
import com.ems.model.User;

public class UserController extends HttpServlet {
	
	// commons logging references
	private static Logger log = Logger.getLogger(UserController.class.getName());
	
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private UserDao dao;


    

	public UserController(Connection conn) throws ClassNotFoundException {
    	super();
    	log.trace("START");
		dao = new UserDao(conn);
	
    	log.trace("END");
    }
    
    
	public UserController() throws ClassNotFoundException {
    	super();
    	log.trace("START");
		dao = new UserDao();
    	log.trace("END");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("START");
    	String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteUser(id);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());    
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            User user = dao.getUserById(id);
            request.setAttribute("user", user);
        } else if (action.equalsIgnoreCase("listUser")){
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        } else {
            forward = INSERT_OR_EDIT;
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    	log.trace("END");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("START");
    	User user = new User(request.getParameter("fname"), 
    			request.getParameter("lname"), 
    			request.getParameter("date_of_birth"), 
    			request.getParameter("password"),
    			request.getParameter("email"),
    			request.getParameter("role"));
        if(request.getParameter("id") == null || request.getParameter("id").isEmpty())
        {
            dao.addUser(user);
        }
        else
        {
            user.setId(Integer.parseInt(request.getParameter("id")));
            dao.updateUser(user);
        }
        
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
    	log.trace("END");
    }
    
    
}