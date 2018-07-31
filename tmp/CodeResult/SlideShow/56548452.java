/**
 * 
 */
package org.photostory.service;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * @author asmirnov
 *
 */
public class SlideShow extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		return Collections.<Class<?>>singleton(AlbumService.class);
	}

}
