/**
 * Name        : com.malcolm.resources.JerseyTestTests.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Jersey Tests Based on Grizly 
 */
package com.malcolm.resources;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malcolm.RestSimpleAppConfig;
import com.malcolm.model.Employee;
import com.malcolm.model.Project;
import com.malcolm.model.Role;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly2.web.GrizzlyWebTestContainerFactory;

/**
 * Jersey Tests Based on Grizly
 * 
 * @author Malcolm
 */
@Transactional
public class JerseyTestTests extends JerseyTest{
	
	/**
	 * Servlet Resources
	 */
	public static final String SERVLET_RESOURCES = "com.malcolm.resources";
	
	/**
	 * Rest Resources Path
	 */
	public static final String RESOURCE_PATH = "/resources/";
	
	/**
	 * Resources configurations
	 */
	public static final String RS_APPLICATION = "javax.ws.rs.Application";
		
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder(SERVLET_RESOURCES)
			.contextParam(ContextLoader.CONTEXT_CLASS_PARAM,AnnotationConfigWebApplicationContext.class.getName())
			.contextParam(ContextLoader.CONFIG_LOCATION_PARAM, RestSimpleAppConfig.class.getName())
			.contextListenerClass(ContextLoaderListener.class)
			.servletClass(ServletContainer.class)
			.servletPath(RESOURCE_PATH)
			.initParam(RS_APPLICATION, com.malcolm.resources.Resources.class.getName())
			.requestListenerClass(RequestContextListener.class)
			.build();
	}
	
	@Override
	public TestContainerFactory getTestContainerFactory() {
	     return new GrizzlyWebTestContainerFactory();
	}
	
	@Test
    public void getAllEmployeesSimpleJava() {
		List<Employee> employeeList = resource().path("/Employee/getAllEmployeesSimple").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
	}
	
	@Test
    public void getAllEmployeesSimpleXML()  {
		List<Employee> employeeList = resource().path("/Employee/getAllEmployeesSimple").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
	}
	
	@Test
    public void getAllEmployeesSimpleJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getAllEmployeesSimple").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
	}
	
	@Test
    public void getAllEmployeesJava() {
		List<Employee> employeeList = resource().path("/Employee/getAllEmployee").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getAllEmployeesXML()  {
		List<Employee> employeeList = resource().path("/Employee/getAllEmployee").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getAllEmployeesJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getAllEmployee").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByProjectIdJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByProjectId").queryParam("projectID", "1").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(10));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByProjectIdXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByProjectId").queryParam("projectID", "1").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByProjectIdJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByProjectId").queryParam("projectID", "1").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByProjectNameJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByProjectName").queryParam("projectName", "Apricot-DEV").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(10));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByProjectNameXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByProjectName").queryParam("projectName", "Apricot-DEV").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByProjectNameJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByProjectName").queryParam("projectName", "Apricot-DEV").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByRoleIdJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByRoleId").queryParam("roleId", "5").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByRoleIdXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByRoleId").queryParam("roleId", "5").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByRoleIdJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByRoleId").queryParam("roleId", "5").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByRoleNameJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByRoleName").queryParam("roleName", "BUSINESS ANALYST").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByRoleNameXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByRoleName").queryParam("roleName", "BUSINESS ANALYST").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByRoleNameJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByRoleName").queryParam("roleName", "BUSINESS ANALYST").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
        assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(15));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByDepartmentIdJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByDepartmentId").queryParam("departmentId", "5").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByDepartmentIdXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByDepartmentId").queryParam("departmentId", "5").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByDepartmentIdJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByDepartmentId").queryParam("departmentId", "5").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByDepartmentNameJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByDepartmentName").queryParam("departmentName", "DEVELOPMENT").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByDepartmentNameXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByDepartmentName").queryParam("departmentName", "DEVELOPMENT").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByDepartmentNameJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByDepartmentName").queryParam("departmentName", "DEVELOPMENT").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(51));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByManagerIdJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByManagerId").queryParam("managerId", "24").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(4));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByManagerIdXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByManagerId").queryParam("managerId", "24").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(4));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByManagerIdJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByManagerId").queryParam("managerId", "24").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(4));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByFirstNameJava() {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByFirstName").queryParam("employeeName", "a develo").get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(9));
		validateEmployeeList(employeeList);
    }
	
	@Test
    public void getEmployeesByFirstNameXML()  {
		List<Employee> employeeList = resource().path("/Employee/getEmployeesByFirstName").queryParam("employeeName", "a develo").accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<List<Employee>>(){});
		assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(9));
		validateEmployeeList(employeeList);
	}
	
	@Test
    public void getEmployeesByFirstNameJSON() throws JsonParseException, JsonMappingException, IOException {
		String jsonString = resource().path("/Employee/getEmployeesByFirstName").queryParam("employeeName", "a develo").accept(MediaType.APPLICATION_JSON).get(String.class);
        ObjectMapper   mapper 		= new ObjectMapper();
        List<Employee> employeeList = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
        assertNotNull("Employee List not fetched ",employeeList);
		assertThat(employeeList, notNullValue());
		assertThat(employeeList.size(), not(0));
		assertThat(employeeList.size(), greaterThan(1));
		assertThat(employeeList.size(), equalTo(9));
		validateEmployeeList(employeeList);
    }
	
	/**
	 * Method to validate Employee List from REST call
	 * 
	 * @param employeeList
	 */
	private void validateEmployeeList(List<Employee> employeeList) {
		for (Employee employee : employeeList) {
			if(employee.getEmployeeDepartment() != null ){
				assertNotNull("Employee Department Name not valid ",employee.getEmployeeDepartment().getDepartmentName());
				assertNotNull("Employee Department Desc not valid ",employee.getEmployeeDepartment().getDepartmentDesc());
			}else{
				assertThat(employee.getEmployeeDesignation().getDesignationID(), equalTo(12));
			}
			if(employee.getEmployeeManager() != null ){
				assertNotNull("Employee Manager First  Name not valid ",employee.getEmployeeManager().getEmployeeFirstName());
				assertNotNull("Employee Manager Last Name not valid ",employee.getEmployeeManager().getEmployeeLastName());
			}else{
				assertThat(employee.getEmployeeDesignation().getDesignationID(), equalTo(12));
			}
			if(employee.getEmployeeDesignation() != null ){
				assertNotNull("Employee Designation Name not valid ",employee.getEmployeeDesignation().getDesignationName());
			}else{
				assertThat(employee.getEmployeeDesignation().getDesignationID(), equalTo(12));
			}
			if(employee.getEmployeeProjects() != null ){
				List<Project> projectAssnList = employee.getEmployeeProjects();
				for (Project project : projectAssnList) {
					if(project != null){
						assertNotNull("Employee Project  Name not valid ",project.getProjectName());
						assertNotNull("Employee Project Desc not valid ",project.getProjectDesc());
					}
				}
			}else{
				assertThat(employee.getEmployeeDesignation().getDesignationID(), equalTo(12));
			}
			if(employee.getEmployeeRoles() != null ){
				List<Role> roleAssnList = employee.getEmployeeRoles();
				for (Role role : roleAssnList) {
					if(role != null){
						assertNotNull("Employee Role Name not valid ",role.getRoleName());
						assertNotNull("Employee Role Desc not valid ",role.getRoleDesc());
					}else{
						System.out.println("GOT INVALID ROLE - NO ASSOCIATION ");
					}
					
				}
			}
		}
	}
}	
