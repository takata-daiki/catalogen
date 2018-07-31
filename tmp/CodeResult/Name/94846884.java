/**
 * Name        : com.malcolm.RestSimplAppInitializer.java
 * Author      : Malcolm
 * Created on  : Jun 16, 2014
 *
 * Description : Rest Simple App Initializer 
 */
package com.malcolm;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

//import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Rest Simple App Initializer
 * 
 * @author Malcolm
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestSimpleAppInitializer implements WebApplicationInitializer {

	/**
	 * Servlet Name
	 */
	public static final String SERVLET_NAME = "restSimpleServlet";
	
	/**
	 * Rest Resources Path
	 */
	public static final String RESOURCE_PATH = "/resources/*";
	
	/**
	 * Resources configurations
	 */
	public static final String RS_APPLICATION = "javax.ws.rs.Application";
	
	@Override
	public void onStartup(ServletContext ctx) throws ServletException {
		ctx.addListener(ContextLoaderListener.class);
		ctx.setInitParameter(ContextLoader.CONTEXT_CLASS_PARAM,AnnotationConfigWebApplicationContext.class.getName());
		ctx.setInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, RestSimpleAppConfig.class.getName());
		ServletRegistration.Dynamic servletRegistration = ctx.addServlet(SERVLET_NAME,ServletContainer.class.getName());
		servletRegistration.addMapping(RESOURCE_PATH);
		servletRegistration.setLoadOnStartup(1);
		servletRegistration.setInitParameter(RS_APPLICATION, com.malcolm.resources.Resources.class.getName());
	}
}
