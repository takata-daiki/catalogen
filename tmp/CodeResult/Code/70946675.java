//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.ems.prototype;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ems.prototype.Entity;

//import com.daniel.util.DbUtil;

public class EntityDao {
	
	// commons logging references
	static Logger log = Logger.getLogger(EntityDao.class.getName());

    /**
	 * @uml.property  name="connection"
	 */
    private Connection connection;

    //used to load the connection in JUnit tests
    public EntityDao(Connection c){
    	connection = c;
    }
    
    public EntityDao() throws NamingException, SQLException {
        Context initialContext = new InitialContext();
        Context envContext  = (Context)initialContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup("jdbc/ems");
        connection = ds.getConnection();
    }

    public void addRecord(Entity record) {
    	log.trace("START");
        try {
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into user(fname,lname,date_of_birth,email,password,role) values (?, ?, ?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, record.getFname());
            preparedStatement.setString(2, record.getLname());
            preparedStatement.setString(3, record.getDate_of_birth());
            preparedStatement.setString(4, record.getEmail());
            preparedStatement.setString(5, record.getPassword());
            preparedStatement.setString(6, record.getRole());
        	log.debug("addUser Execute Update");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    public void deleteRecord(int id) {
    	log.trace("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from user where id=?");
            // Parameters start with 1
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    public void updateRecord(Entity record) {
    	log.debug("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update user set fname=?, lname=?, date_of_birth=?,email=?, password=?, role=? " +
                            "where id=?");
            // Parameters start with 1
            preparedStatement.setString(1, record.getFname());
            preparedStatement.setString(2, record.getLname());
            preparedStatement.setString(3, record.getDate_of_birth());
            preparedStatement.setString(4, record.getEmail());
            preparedStatement.setString(5, record.getPassword());
            preparedStatement.setString(6, record.getRole());
            preparedStatement.setInt(7, record.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    public List<Entity> getAllRecords() {
        log.trace("START");
    	List<Entity> records = new ArrayList<Entity>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from user");
            while (rs.next()) {
                Entity record = new Entity();
                record.setId(rs.getInt("id"));
                record.setFname(rs.getString("fname"));
                record.setLname(rs.getString("lname"));
                record.setDate_of_birth(rs.getString("date_of_birth"));
                record.setPassword(rs.getString("password"));
                record.setEmail(rs.getString("email"));
                record.setRole(rs.getString("role"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return records;
    }

    public Entity getRecordById(int id) {
    	log.trace("START");
        Entity record = new Entity();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from user where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	record.setId(rs.getInt("id"));
            	record.setFname(rs.getString("fname"));
            	record.setLname(rs.getString("lname"));
                record.setDate_of_birth(rs.getString("date_of_birth"));
                record.setPassword(rs.getString("password"));
                record.setEmail(rs.getString("email"));
                record.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return record;
    }
}
