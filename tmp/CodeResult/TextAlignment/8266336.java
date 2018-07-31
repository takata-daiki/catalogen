/*
 * Copyright Miroslav Pokorny
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package rocket.widget.client;

/**
 * An enum class that holds all possible text aligment values.
 * 
 * @author Miroslav Pokorny
 */
public class TextAlignment {

	/**
	 * Center the text.
	 */
	public static final TextAlignment CENTER = new TextAlignment("center");

	/**
	 * Justify the text.
	 */
	public static final TextAlignment JUSTIFY = new TextAlignment("justify");

	/**
	 * Align the text to the left edge.
	 */
	public static final TextAlignment LEFT = new TextAlignment("left");

	/**
	 * Align the text to the right.
	 */
	public static final TextAlignment RIGHT = new TextAlignment("right");

	private TextAlignment(String value) {
		this.setValue(value);
	}

	private String value;

	public String getValue() {
		return value;
	}

	void setValue(final String value) {
		this.value = value;
	}
}
