/*******************************************************************************
 * IOUtils.java
 *  
 *  Copyright (c) 2013, Oliver Tena. All rights reserved.
 * 
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 * 
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA 02110-1301  USA
 ******************************************************************************/
package com.parser.widgets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Utilidades para leer properties.
 *
 */
public class IOUtils {
	
	
	
	public static Properties readPropertiesFile(String file) throws IOException {
		return readPropertiesFile(new File(file));
	}
	
	
	public static Properties readPropertiesFile(File file) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try{
			fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
			
		}finally{
			if (null!=fis){
				fis.close();
			}
		}
		return prop;		
	}

}
