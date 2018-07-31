/**
 * Copyright 2011 Henric Persson (henric.persson@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package burrito.client.crud.generic.fields;

import burrito.client.crud.generic.CrudField;
/**
 * A {@link CrudField} representating a {@link Long}
 * @author henper
 *
 */
@SuppressWarnings("serial")
public class LongField extends CrudField {

	private Long value;
	
	public LongField() {
		// default constructor
		super();
	}
	
	public LongField(Long value) {
		this.value = value;
	}



	@Override
	public Class<?> getType() {
		return Long.class;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public void setValue(Object value) {
		this.value = (Long) value;
	}

}
