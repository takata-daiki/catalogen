//Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/

package com.ems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ems.model.Event;


/**
* EventDao is the class that performs actions on the Event of the database
* Code inspired by http://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/
* @author Luca Barazzuol
*/
public class EventDao {
	
	// commons logging references
	static Logger log = Logger.getLogger(EventDao.class.getName());

    /**
	 * @uml.property  name="connection"
	 */
    private Connection connection;

    /**
     * Constructor with a Connection as a parameter
     * used by JUnit
     * 
     * @param  c A connection object used to access database by test units
     */
    public EventDao(Connection c){
    	connection = c;
    }
    
    /**
     * Constructor with no parameters
     * It initializes the connection to the database
     * 
     */
    public EventDao() {
        Context initialContext;
		try {
			initialContext = new InitialContext();
	        Context envContext;
			try {
				envContext = (Context)initialContext.lookup("java:/comp/env");
		        DataSource ds = (DataSource)envContext.lookup("jdbc/ems");
		        connection = ds.getConnection();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Add a record to the table
     * 
     * @param aRecord A record
     */
    public void addRecord(Event aRecord) {
    	log.trace("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into event(id_manager,name,description,start,end,enrollment_start,enrollment_end) values (?, ?, ?, ?, ?, ?, ? )");
            preparedStatement.setInt(1, aRecord.getId_manager());
            preparedStatement.setString(2, aRecord.getName());
            preparedStatement.setString(3, aRecord.getDescription());
            preparedStatement.setString(4, aRecord.getStart());
            preparedStatement.setString(5, aRecord.getEnd());
            preparedStatement.setString(6, aRecord.getEnrollment_start());
            preparedStatement.setString(7, aRecord.getEnrollment_end());            
        	log.debug("add record");
            preparedStatement.executeUpdate();
            
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Delete a record from table using its id
     * 
     * @param id It is the id of the record to delete
     */
    public void deleteRecord(int id) {
    	log.trace("START");
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from event where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Update the fields of a record
     * 
     * @param aRecord The record to update
     */
    public void updateRecord(Event aRecord) {
    	log.trace("START");
    	log.debug(aRecord.toString());
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update event set id_manager=?, name=?, description=?,start=?, end=?, enrollment_start=?, enrollment_end=? " +
                            "where id=?");
            
            preparedStatement.setInt(1, aRecord.getId_manager());
            preparedStatement.setString(2, aRecord.getName());
            preparedStatement.setString(3, aRecord.getDescription());
            preparedStatement.setString(4, aRecord.getStart());
            preparedStatement.setString(5, aRecord.getEnd());
            preparedStatement.setString(6, aRecord.getEnrollment_start());
            preparedStatement.setString(7, aRecord.getEnrollment_end()); 
            
            preparedStatement.setInt(8, aRecord.getId());
            preparedStatement.executeUpdate();
        	log.debug("update done");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    }

    /**
     * Returns the list of all record stored in the table Event
     * 
     * @return List<Event> List of objects Event
     */
    public List<Event> getAllRecords() {
        log.trace("START");
    	List<Event> list = new ArrayList<Event>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from event");
            while (rs.next()) {
                Event aRecord = new Event();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_manager(rs.getInt("id_manager"));
                aRecord.setName(rs.getString("name"));
                aRecord.setDescription(rs.getString("description"));
                aRecord.setStart(rs.getString("start"));
                aRecord.setEnd(rs.getString("end"));
                aRecord.setEnrollment_start(rs.getString("enrollment_start"));
                aRecord.setEnrollment_end(rs.getString("enrollment_end"));
                list.add(aRecord);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return list;
    }
    
    /**
     * Returns the list of all record stored in the table Event and associated with an event_mng
     * 
     * @param id_event_mng Id of a event manager
     * @return List<Event> List of objects Event
     */
    public List<Event> getRecordsById_event_mng(int id_event_mng) {
        log.trace("START");
    	List<Event> list = new ArrayList<Event>();
        try {
            
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from event where id_manager=?");
            preparedStatement.setInt(1, id_event_mng);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Event aRecord = new Event();
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_manager(rs.getInt("id_manager"));
                aRecord.setName(rs.getString("name"));
                aRecord.setDescription(rs.getString("description"));
                aRecord.setStart(rs.getString("start"));
                aRecord.setEnd(rs.getString("end"));
                aRecord.setEnrollment_start(rs.getString("enrollment_start"));
                aRecord.setEnrollment_end(rs.getString("enrollment_end"));
                list.add(aRecord);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return list;
    }
 
    
    
    /**
     * Returns the record passing its id
     * 
     * @param id Identifier of the record to get
     * @return event An object Event
     */
    public Event getRecordById(int id) {
    	log.trace("START");
        Event aRecord = new Event();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from event where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_manager(rs.getInt("id_manager"));
                aRecord.setName(rs.getString("name"));
                aRecord.setDescription(rs.getString("description"));
                aRecord.setStart(rs.getString("start"));
                aRecord.setEnd(rs.getString("end"));
                aRecord.setEnrollment_start(rs.getString("enrollment_start"));
                aRecord.setEnrollment_end(rs.getString("enrollment_end"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return aRecord;
    }
    
  
    /**
     * Returns the record passing an id_group
     * 
     * @param id Identifier of the id_group 
     * @return event An object Event
     */
    public Event getRecordById_group(int id_group) {
    	log.trace("START");
        Event aRecord = new Event();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * " +
                    				" from event, ems.group" +
                    				" where event.id = ems.group.id_event" +
                    				" and ems.group.id=?");
            preparedStatement.setInt(1, id_group);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                aRecord.setId(rs.getInt("id"));
                aRecord.setId_manager(rs.getInt("id_manager"));
                aRecord.setName(rs.getString("name"));
                aRecord.setDescription(rs.getString("description"));
                aRecord.setStart(rs.getString("start"));
                aRecord.setEnd(rs.getString("end"));
                aRecord.setEnrollment_start(rs.getString("enrollment_start"));
                aRecord.setEnrollment_end(rs.getString("enrollment_end"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
        return aRecord;
    }    
    
 
    
    /**
     * Return a list of user that can modify the record identified by the passed id 
     * 
     * @param anId_participant an id of a participant
     * @return List of id that can change the Event
     */
    public List<Integer>  canBeChangedBy(int anId_event) {
    	log.trace("START");
        List<Integer> listOfId = new ArrayList<Integer>();
        try {
        	
        	//look for group_referent
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT id_manager " +
                    					" FROM ems.event" +
                    					" WHERE id = ?");
            preparedStatement.setInt(1, anId_event);
            log.debug(preparedStatement.toString());            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	log.debug("add to list id: " + rs.getInt("id_manager"));
                listOfId.add(rs.getInt("id_manager"));
            }

            //look for admins
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("SELECT * " +
                    					" FROM ems.user, ems.user_role" +
                    					" WHERE ems.user.email = ems.user_role.email" +
                    					" AND ems.user_role.ROLE_NAME = 'admin'");
            log.debug(preparedStatement2.toString());
            rs = preparedStatement2.executeQuery();
            while (rs.next()) {
            	log.debug("add to list admin id: " + rs.getInt("id") + rs.getString("fname"));
                listOfId.add(rs.getInt("id"));
            }
            
            for (int  i = 0; i < listOfId.size(); i++){
            	log.debug("listOfId: " + listOfId.get(i));
            }
            rs.close();
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	log.trace("END");
    	return listOfId;
    }  
}
