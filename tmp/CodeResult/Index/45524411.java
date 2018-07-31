/*
 * Index.java
 * 
 * Copyright (c) 2012, insign gmbh.  
 * www.insign.ch 
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package controllers;

import play.Play;
import play.mvc.Controller;
import play.mvc.Result;


public class Index extends Controller {
	
	/** 
	 * Action shows main page of a site with necessary information.  
	 * @return Result
	 */
	public static Result main( ) {
		
		return ok( views.html.index.main.render() );
	}
	
	/** 
	 * Action shows usage page with install guide.  
	 * @return Result
	 */
	public static Result usage( ) {
		String smtpHost = Play.application().configuration().getString("smtp.host");
		System.out.println("host: " + smtpHost);
		return ok( views.html.index.usage.render() );
	}
	
}
