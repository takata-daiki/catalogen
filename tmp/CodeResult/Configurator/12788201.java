/*
 * JaCoLiB
 * Copyright (C) 2008 Alessandro Serra
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package it.gashale.jacolib.main.configurator;

import it.gashale.jacolib.core.JacolibError;
import it.gashale.jacolib.main.Jacolib;


public abstract class Configurator {
	private Environment m_env;
	private String[] m_prefixes;
	
	protected Configurator(Environment env) {
		m_env=env;
		m_prefixes=null;
	}
	protected Configurator() {
		m_env=null;
		m_prefixes=null;
	}
	public Environment getEnvironment() {
		return m_env;
	}
	public void setEnvironment(Environment env) {
		m_env=env;
	}
	public String[] getPrefixes() {
		return m_prefixes;
	}
	public void setPrefixes(String[] prefix) {
		m_prefixes=prefix;
	}
	public String getValue(String names[], String defaultval) throws JacolibError {
		String r=null;
		if(m_env!=null)
			r=m_env.getValue(m_prefixes, names, defaultval);
		return r;
	}
	public String getGlobalValue(String names[], String defaultval) throws JacolibError {
		String r=null;
		if(m_env!=null)
			r=m_env.getValue(names, defaultval);
		return r;
	}
	
	abstract public void configure(Jacolib jacolib) throws JacolibError;
	
	public static Configurator newConfigurator(String configurator_class, Environment env) throws JacolibError {
		Configurator configurator=null;
		if(configurator_class!=null) {
			try {
				Class<?> c = Class.forName(configurator_class);
				configurator=(Configurator)c.newInstance();
				configurator.setEnvironment(env);
			} catch (ClassNotFoundException e) {
				throw new JacolibError("Configurator not found: "+e.getMessage());
			} catch (InstantiationException e) {
				throw new JacolibError("Configurator exception: "+configurator_class+": "+e.getMessage());
			} catch (IllegalAccessException e) {
				throw new JacolibError("Configurator exception: "+e.getMessage());
			}
		} else {
			throw new JacolibError("Undefine Configurator.");
		}
		return configurator;
	}
}
