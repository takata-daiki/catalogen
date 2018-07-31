/**
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */

package org.mobicents.ssf.flow.engine.builder.template;

import java.util.ArrayList;
import java.util.List;


public class ActionTemplate extends AbstractAnnotatedTemplate {
    private String type;
    
    private List<PropertyTemplate> properties;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<PropertyTemplate> getProperties() {
        return properties;
    }
    public void setProperties(List<PropertyTemplate> properties) {
        this.properties = properties;
    }
    
    public void addProperty(PropertyTemplate property) {
        if(this.properties == null) {
            this.properties = new ArrayList<PropertyTemplate>();
        }
        this.properties.add(property);
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("ActionTemplate:");
    	sb.append(super.toString());
    	sb.append("[type=" + type + "]");
    	if(properties != null) {
    		sb.append("[properties=");
    		for(PropertyTemplate p: properties) {
    			sb.append(p);
    		}
    		sb.append("]");
    	}
    	return sb.toString();
    }
    
    @Override
    public Object clone() {
    	ActionTemplate template = (ActionTemplate)super.clone();
    	for(PropertyTemplate p:this.properties) {
    		template.addProperty(p);
    	}
    	template.type = this.type;
    	return template;
    }
}
