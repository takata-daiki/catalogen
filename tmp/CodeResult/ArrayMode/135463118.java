/*-
 * #%L
 * metamodel
 * %%
 * Copyright (C) 2010 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.meta;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация пространства метаданных
 * 
 * @author Mitronov Stas
 * 
 */
public class MetaSpaceImpl implements MetaSpace {

	public static final String DESCRIPTION_ELEMENT_NAME = "description";

	public static String getFullName(Element element) {
		if ((element == null) || (element.elementType == null))
			return null;
		String name = getFullName(element.parent);
		if (name != null)
			name = name + "." + element.name;
		else
			name = element.name;

		return name;
	}

	public MetaSpaceImpl doGetSpace() {
		return this;
	}

	public Element doGetSpaceElement(String name) throws MetaException {
		Element element = doGetSpace().doGetElementN(name);
		if ((element == null) && (modelResolver != null)) {
			modelResolver.resolveObject(doGetSpace(), name);
			element = doGetSpace().doGetElement(name);
		}
		return element;
	}

	protected class ProcessElementAction {

		protected Element element;

		protected boolean processedSelfElement = false;

		protected boolean cloneParentedElements = false;

		protected ArrayList<ElementLink> parentLinks;

		protected HashMap<String, Element> processedElements;

		public ProcessElementAction(Element element) {
			this.element = element;
		}

		public ArrayList<ElementLink> getParentLinks() {
			if (parentLinks == null) {
				parentLinks = new ArrayList<ElementLink>();
			}
			return parentLinks;
		}

		public boolean hasParentLinks() {
			return (parentLinks != null) && (!parentLinks.isEmpty());
		}

		public void addParentLink(ElementLink link) {
			getParentLinks().add(link);
		}

		public void addParentLink(Element element, String fullName) {
			addParentLink(new ElementLink(element, fullName));
		}

		public void clearParentLinks() {
			if (hasParentLinks())  parentLinks.clear();
		}

		public HashMap<String, Element> getProcessedElements() {
			if (processedElements == null) {
				processedElements = new HashMap<String, Element>();
			}
			return processedElements;
		}

		public Element getElement() {
			return element;
		}

		public void processElement(Element element) throws MetaException {
			addLog("Process " + element.getFullName());
		}

		public void execute() throws MetaException {
			ElementProcess action = new ElementProcess(element, this, processedSelfElement, cloneParentedElements);
			action.execute();
		}

	}

	protected class ElementProcess extends ProcessElementAction {

		protected ProcessElementAction action;

		public ElementProcess(Element element, ProcessElementAction action, boolean processedSelfElement,
				boolean cloneParentedElements) {
			super(element);
			this.action = action;
			this.processedSelfElement = processedSelfElement;
			this.cloneParentedElements = cloneParentedElements;
		}

		public void processElement(Element element, String refName) throws MetaException {

			// addLog("refName " + refName);

			if (element.hasParentedElements()) {

				Iterator<Element> it = cloneParentedElements ? (new ArrayList<Element>(element.getParentedElements()))
						.iterator() : element.getParentedElements().iterator();
				Element curr;
				if (refName.equals("")) {
					while (it.hasNext()) {
						curr = it.next();
						if (!getProcessedElements().containsValue(curr)) {
							action.processElement(curr);
							getProcessedElements().put(curr.getFullName(), curr);
							processElement(curr, "");
						}
					}

				} else {
					Element owner2;
					while (it.hasNext()) {
						curr = it.next();
						owner2 = curr.doGetElement(refName.split("\\."));
						if (owner2 == null) {
							addLog("Process - not found - " + curr.getFullName() + "." + refName);
						} else {
							if (!getProcessedElements().containsValue(owner2)) {
								action.processElement(owner2);
								getProcessedElements().put(owner2.getFullName(), owner2);
								processElement(owner2, "");
							}
						}
					}
				}

			}

			if (element.hasParent()) {

				if (refName.equals("")) {
					processElement(element.doGetParent(), element.getName());
				} else
					processElement(element.doGetParent(), element.getName() + "." + refName);

			}

		}

		public void execute() throws MetaException {
			if (processedSelfElement)
				action.processElement(element);
			processElement(element, "");
		}

	}

	protected class RemoveElementAction extends ProcessElementAction {

		public RemoveElementAction(Element element) {
			super(element);
			this.processedSelfElement = true;
		}

		public void removeElement(Element element) throws MetaException {
			if (element.doHasElements()) {
				Iterator<Element> it = element.doGetElements().iterator();
				while (it.hasNext()) {
					removeElement(it.next());
				}
			}
			element.isRemoveElement1 = true;
			addParentLink(element, element.getFullName());
		}

		public void processElement(Element element) throws MetaException {
			removeElement(element);
		}

		public void execute() throws MetaException {
			super.execute();
			if (hasParentLinks()) {

				Iterator<ElementLink> it = getParentLinks().iterator();
				while (it.hasNext()) {
					ElementLink link = it.next();
					Element element = link.element;

					addLog("RemoveElement - " + link.name);

					if (element.doHasElements())
						element.doGetElements().clear();

					if (element.hasType()) {
						element.type.removeParentedElement(element);
						element.type = null;
					}
					if (element.parent != null) {
						if (element.parent.doHasElements())
							element.parent.doGetElements().remove(element);
						element.parent = null;
					}

				}
				getParentLinks().clear();
			}

		}

	}

	protected class AddElementAction extends ProcessElementAction {

		public AddElementAction(Element element) {
			super(element);
		}

		public void processElement(Element owner, Element element, String refName) throws MetaException {
			String refName2 = refName;

			if (owner.hasParentedElements()) {
				Iterator<Element> it = owner.getParentedElements().iterator();
				Element curr;
				if (element.parent == owner) {
					while (it.hasNext()) {
						curr = it.next();
						if (!getProcessedElements().containsValue(curr) && curr.checkInheritedElement(element)) {
							Element value = createElement(curr, element.name, element.elementType, element.type,
									element.section);

							curr.doGetElements().add(value);
							value.inheritedMode = 1;
							value.doConnectParent();

							if (value.elementType == MetaElementType.FIELD) {
								((ElementField) value).isInheritedValue = true;
								((ElementField) value).value = ((ElementField) element).value;
								((ElementField) value).isArray = ((ElementField) element).isArray;
								((ElementField) value).isReference = ((ElementField) element).isReference;
							}

							getProcessedElements().put(curr.getFullName(), curr);

							processElement(curr, value, "");

						}
					}

				} else {
					Element owner2;
					Element value;
					while (it.hasNext()) {
						curr = it.next();

						owner2 = curr.doGetElement(refName2.split("\\."));

						if ((owner2 != null) && !getProcessedElements().containsValue(owner2)
								&& owner2.checkInheritedElement(element)) {

							if (element.hasType() && element.type.isParent(owner)) {

								String s = convertFullName(element.type.getFullName(), owner.getFullName(), curr
										.getFullName());
								Element parent2 = doGetSpace().doGetElement(s);

								value = createElement(owner2, element.name, element.elementType, parent2,
										element.section);

							} else {
								value = createElement(owner2, element.name, element.elementType, element.type,
										element.section);
							}

							owner2.doGetElements().add(value);

							value.inheritedMode = 1;
							value.doConnectParent();

							if (value.elementType == MetaElementType.FIELD) {
								((ElementField) value).isInheritedValue = true;
								((ElementField) value).value = ((ElementField) element).value;
								((ElementField) value).isArray = ((ElementField) element).isArray;
								((ElementField) value).isReference = ((ElementField) element).isReference;
							}

							getProcessedElements().put(owner2.getFullName(), owner2);

							processElement(owner2, value, "");

						}
					}
				}

			}

			if (owner.hasParent()) {
				if ((refName2 == null) || (refName2.equals(""))) {

				} else
					refName2 = "." + refName2;

				processElement(owner.doGetParent(), element, owner.getName() + refName2);

			}

		}

		public void execute() throws MetaException {
			if (element.hasParent()) {
				processElement(element.parent, element, "");
			}
		}

	}

	protected class ConnectParentAction extends ProcessElementAction {

		public ConnectParentAction(Element element) {
			super(element);
		}

		public void processElement(Element source, Element element) {
			if (source.doHasElements()) {
				Iterator<Element> it = source.doGetElements().iterator();
				Element curr;
				while (it.hasNext()) {
					curr = it.next();

					if (element.checkInheritedElement(curr)) {

						Element value = element.doGetElement(curr.name);

						if (value == null) {
							if (curr.hasType() && curr.type.isParent(this.element.type)) {

								value = createElement(element, curr.name, curr.elementType, null, curr.section);
								addParentLink(value, convertFullName(curr.type.getFullName(), this.element.type
										.getFullName(), this.element.getFullName()));

							} else {

								value = createElement(element, curr.name, curr.elementType, curr.type, curr.section);

							}

							value.inheritedMode = 1;

							element.doGetElements().add(value);
						} else {
							if (curr.hasType() && !value.hasType()) {
								if (curr.type.isParent(this.element.type)) {
									addParentLink(value, convertFullName(curr.type.getFullName(), this.element.type
											.getFullName(), this.element.getFullName()));

								} else {

									if (value.type == null) {
										value.type = curr.type;

									}

								}
							}

							value.inheritedMode = 1;

						}

						if (value.hasType()) {
							value.type.addParentedElement(value);
							value.parentConnected = true;
						}

						if (curr.elementType == MetaElementType.FIELD) {
							((ElementField) value).isInheritedValue = true;
							((ElementField) value).value = ((ElementField) curr).value;
							((ElementField) value).isArray = ((ElementField) curr).isArray;
							((ElementField) value).isReference = ((ElementField) curr).isReference;
						}

						processElement(curr, value);

					}
				}
			}
		}

		public void execute() throws MetaException {
			processElement(element.type, element);
			if (hasParentLinks()) {

				Iterator<ElementLink> it = getParentLinks().iterator();
				while (it.hasNext()) {
					ElementLink link = it.next();

					addLog("ConnectParent - " + link.element.getFullName() + " - " + link.name);
					link.element.type = doGetSpace().doGetElement(link.name);
					link.element.type.addParentedElement(link.element);
					link.element.parentConnected = true;
				}
			}
		}
	}

	protected class SetValueFieldAction extends ProcessElementAction {

		protected ElementValue oldValue;

		public SetValueFieldAction(Element element, Object value) {
			super(element);
			ElementField field = (ElementField) element;
			this.oldValue = field.value;
			addLog("*SetElementValueAction " + field.getFullName() + " " + field.getInfoText());
			field.value = new ElementValue(field, value);
			field.isInheritedValue = false;
			addLog("	value = \"" + field.value.value + "\"");
		}

		public void processElement(Element element) {
			ElementField field = (ElementField) element;
			addLog("SetElementValueAction " + field.getFullName() + " " + field.getInfoText());

			if (field.value == oldValue) {

				field.value = ((ElementField) this.element).value;
				if (field.value == null)
					addLog("	value = null");
				else
					addLog("	value = \"" + field.value.value + "\" (" + field.value.owner.getFullName() + ")");
			}
		}

	}

	protected class SetArrayModeFieldAction extends ProcessElementAction {

		protected boolean arrayMode;

		public SetArrayModeFieldAction(Element element, boolean arrayMode) {
			super(element);
			processedSelfElement = true;
			this.arrayMode = arrayMode;
		}

		public void processElement(Element element) {
			ElementField field = (ElementField) element;
			field.isArray = arrayMode;
			if ((field.value != null) && (field.value.owner == field) && (field.value.value != null)) {
				if (arrayMode) {
					field.value.value = new Object[] { field.value.value };
				} else {
					if (((Object[]) field.value.value).length > 0) {
						field.value.value = ((Object[]) field.value.value)[0];
					} else {
						field.value.value = null;
					}

				}
			}
		}

	}

	protected class SetReferenceModeFieldAction extends ProcessElementAction {

		protected boolean isReference;

		public SetReferenceModeFieldAction(Element element, boolean isReference) {
			super(element);
			processedSelfElement = true;
			this.isReference = isReference;
		}

		public void processElement(Element element) {
			ElementField field = (ElementField) element;
			field.isReference = isReference;
			if ((field.value != null) && (field.value.owner == field) && (field.value.value != null)) {
				if (isReference) {
					// field.value.value = new Object[] { field.value.value };
				} else {
					// if (((Object[]) field.value.value).length > 0) {
					// field.value.value = ((Object[]) field.value.value)[0];
					// } else {
					// field.value.value = null;
					// }

				}
			}
		}

	}

	protected class SetTypeAction extends ProcessElementAction {

		protected Element typeElement;

		public SetTypeAction(Element element, Element typeElement) {
			super(element);
			this.typeElement = typeElement;
			this.processedSelfElement = true;

			Element whileParent = element.doGetParent();
			String typeName = typeElement.getFullName();
			while (whileParent != null) {
				String ownerName = whileParent.getFullName();
				if ((ownerName != null) && (ownerName.equals(typeName))) {
					this.cloneParentedElements = true;
					break;
				}
				whileParent = whileParent.doGetParent();
			}

		}

		public void processElement(Element element) throws MetaException {
			addLog("SetParentAction2 " + element.getFullName());

			if (element.hasType()) {
				addLog("element.hasParent() - " + element.type.getFullName());
				element.parentConnected = false;
				element.doConnectParent();
			} else {
				element.type = typeElement;
				if (element.hasType()) {

					element.type.addParentedElement(element);
					element.doConnectParent();
				} else
					addLog("parent = null ");

			}
		}

	}

	protected class LoadModelAction extends ProcessElementAction {

		protected org.dom4j.Element node;

		protected List<ElementNodeLink> valueLinks;

		public LoadModelAction(Element element, org.dom4j.Element node) {
			super(element);
			this.node = node;
		}

		public List<ElementNodeLink> getValueLinks() {
			if (valueLinks == null) {
				valueLinks = new ArrayList<ElementNodeLink>();
			}
			return valueLinks;
		}

		public boolean hasValueLinks() {
			return (valueLinks != null) && (!valueLinks.isEmpty());
		}

		public void addValueLink(Element element, org.dom4j.Element node) {
			getValueLinks().add(new ElementNodeLink(element, node));
		}

		public void processElement(Element owner, org.dom4j.Element ownerNode) throws MetaException {
			Element element;
			Element elementParent;
			Iterator<MetaElementType> iet = MetaElementType.getTypes().iterator();
			while (iet.hasNext()) {
				MetaElementType elementType = iet.next();
				if (owner.elementType.checkElementType(elementType)) {

					if ((owner.elementType == MetaElementType.FIELD) && (elementType == MetaElementType.FIELD))
						continue;

					Iterator it = ownerNode.elementIterator(elementType.getTemplate().toLowerCase());
					while (it.hasNext()) {
						org.dom4j.Element node = (org.dom4j.Element) it.next();

						String name = node.attributeValue("name");
						if (name == null) {
							throw new MetaException("Error node.name == null");
						}

						String nameParent = node.attributeValue("type");
						element = owner.doGetElement(name);

						String nameSection = node.attributeValue("section");
						MetaElementSection section = (nameSection == null) ? null : MetaElementSection
								.valueOf(nameSection.toUpperCase());

						int isArray = -1;
						int isReference = -1;

						if (element == null) {

							if (nameParent != null) {
								if (nameParent.endsWith("[]")) {
									isArray = 1;
									nameParent = nameParent.substring(0, nameParent.length() - 2);
								} else
									isArray = 0;

								if (nameParent.endsWith("^")) {
									isReference = 1;
									nameParent = nameParent.substring(0, nameParent.length() - 1);
								} else
									isReference = 0;

								elementParent = doGetSpace().root.doGetElement(nameParent.split("\\."));
							} else
								elementParent = null;

							element = createElement(owner, name, elementType, elementParent, section);

							owner.doGetElements().add(element);

							AddElementAction action = new AddElementAction(element);
							action.execute();

							if (element.hasType())
								element.doConnectParent();
							else {
								if (nameParent != null)
									addParentLink(element, nameParent);
							}

						} else {
							if (section != null)
								element.section = section;
						}

						if (element.elementType == MetaElementType.FIELD) {
							if (isArray != -1) {
								((ElementField) element).setArrayMode(isArray == 1);
							}
							if (isReference != -1) {
								((ElementField) element).setIsReference(isReference == 1);
							}

						}
						if (((node.attributeValue("value") != null) && (node.attributeValue("value").length() > 0))
								|| node.elementIterator("field").hasNext() || node.elementIterator("value").hasNext())
							addValueLink(element, node);

						if (element.elementType == MetaElementType.OBJECT) {

							String defaultFieldName = node.attributeValue("default");
							if ((defaultFieldName != null) && (defaultFieldName.length() > 0)) {
								((ElementObject) element).defaultFieldName = defaultFieldName;
								// System.out.println(defaultFieldName);
							}
						}
						processElement(element, node);
					}
				}
			}
		}

		public void execute() throws MetaException {
			String name = node.attributeValue("name");
			String nameParent = node.attributeValue("type");

			element = createElement(doGetSpace().root, name, MetaElementType.MODEL, null, null);

			doGetSpace().root.doGetElements().add(element);

			if (nameParent != null) {
				addLog("ModelParent - " + element.getFullName() + " = " + nameParent);
				Element parent = doGetSpace().doGetElementN(nameParent);
				if ((parent == null) && (modelResolver != null)) {
					modelResolver.resolveObject(doGetSpace(), nameParent);
					parent = doGetSpace().doGetElement(nameParent);
				}

				element.type = parent;
				element.doConnectParent();
			}

			processElement(element, node);

			if (hasParentLinks()) {

				Iterator<ElementLink> it = getParentLinks().iterator();
				while (it.hasNext()) {
					ElementLink link = it.next();
					addLog("ParentLink - " + link.element.getFullName() + " = " + link.name);

					Element parent = doGetSpaceElement(link.name);

					SetTypeAction action = new SetTypeAction(link.element, parent);
					action.execute();
				}
			}
			if (hasValueLinks()) {

				Iterator<ElementNodeLink> it = getValueLinks().iterator();
				while (it.hasNext()) {
					ElementNodeLink link = it.next();
					if (link.element.elementType == MetaElementType.FIELD) {

						((ElementField) link.element).setValue(loadElementValue((ElementField) link.element, link.node,
								((ElementField) link.element).isArray()));
					} else {
						if (link.element instanceof MetaFields) {
							MetaFields fields = (MetaFields) link.element;
							if (fields.hasDefaultField()
									&& !(fields.getDefaultField().isArray() || fields.getDefaultField().isObject())) {
								String text = link.node.attributeValue("value");
								if ((text != null) && (text.length() > 0))
									fields.getDefaultField().setValue(text);
							}

						}

					}
				}
			}
		}

		protected Object loadElementValue(ElementField field, org.dom4j.Element node, boolean isArray)
				throws MetaException {
			Object value = null;
			if (isArray) {
				Iterator it = node.elementIterator("value");
				if (it.hasNext()) {
					ArrayList<Object> values = new ArrayList<Object>();
					while (it.hasNext()) {
						org.dom4j.Element valueNode = (org.dom4j.Element) it.next();
						values.add(loadElementValue(field, valueNode, false));
					}
					value = values.toArray();
				}
			} else {
				Object object;
				if (field.isReference())
					object = new String("");
				else
					object = field.createValue();
				if (object instanceof ElementObjectValue) {

					ElementObjectValue element = (ElementObjectValue) object;
					if (element.hasDefaultField()
							&& !(element.getDefaultField().isArray() || element.getDefaultField().isObject())) {
						String text = node.attributeValue("value");
						if ((text != null) && (text.length() > 0))
							element.getDefaultField().setValue(text);
					}

					Iterator it = node.elementIterator("field");
					if (it.hasNext()) {
						while (it.hasNext()) {
							org.dom4j.Element fieldNode = (org.dom4j.Element) it.next();
							try {
								ElementField objectField = (ElementField) ((ElementObjectValue) object)
										.getField(fieldNode.attributeValue("name"));
								objectField.setValue(loadElementValue(objectField, fieldNode, objectField.isArray()));
							} catch (MetaException e) {
								e.printStackTrace();
							}
						}
					}
					value = object;
				} else {
					String text;
					if (node.getName().equalsIgnoreCase("value"))
						text = node.getText();
					else
						text = node.attributeValue("value");

					if (text != null) {
						if (object instanceof String) {
							value = text;
						} else if (object instanceof Integer) {
							value = Integer.valueOf(text);
						} else if (object instanceof Long) {
							value = Long.valueOf(text);
						} else if (object instanceof Float) {
							value = Float.valueOf(text);
						} else if (object instanceof Double) {
							value = Double.valueOf(text);
						} else if (object instanceof Date) {
							try {
								value = DateFormat.getInstance().parse(text);
							} catch (ParseException e) {

							}
						} else if (object instanceof Boolean) {
							value = Boolean.valueOf(text);
						}
					}
				}
			}
			return value;
		}

	}

	protected class ElementValueNodeLoad {

		protected ElementField field;

		protected org.dom4j.Element node;

		protected int nodeMode;

		protected boolean hasValue;

		public ElementValueNodeLoad(ElementField field, org.dom4j.Element node, int nodeMode) {
			this.field = field;
			this.node = node;
			this.nodeMode = nodeMode;
		}

		public void load() throws MetaException {
			hasValue = false;
			Object value = loadElementValue(field, node, field.isArray());
			if (hasValue)
				field.setValue(value);
		}

		protected Object loadElementValue(ElementField field, org.dom4j.Element node, boolean isArray)
				throws MetaException {
			Object value = null;
			int v = 0;

			if (isArray) {
				if (node.elements().size() == 0) {
					String text = node.getText();
					if ((text != null) && (text.length() > 0)) {
						v++;
						Object[] values = new Object[] { text };
						value = values;
					}

				} else {

					Iterator it = node.elementIterator("value");
					if (it.hasNext()) {
						ArrayList<Object> values = new ArrayList<Object>();
						while (it.hasNext()) {
							org.dom4j.Element valueNode = (org.dom4j.Element) it.next();
							hasValue = false;
							Object value2 = loadElementValue(field, valueNode, false);
							if (hasValue) {
								v++;
								values.add(value2);
							}
						}
						if (v > 0)
							value = values.toArray();
					}

				}
			} else {
				Object object;
				if (field.isReference())
					object = new String("");
				else
					object = field.createValue();
				if (object instanceof ElementObjectValue) {

					ElementObjectValue element = (ElementObjectValue) object;
					if (element.hasDefaultField()
							&& !(element.getDefaultField().isArray() || element.getDefaultField().isObject())) {
						String text = null;
						org.dom4j.Attribute attribute = node.attribute("value");
						if (attribute != null)
							text = attribute.getText();
						else if (node.elements().size() == 0)
							text = node.getText();
						else {
							Iterator it = node.elementIterator("value");
							while (it.hasNext()) {
								org.dom4j.Element valueNode = (org.dom4j.Element) it.next();
								text = valueNode.getText();
								break;
							}
						}
						if ((text != null) && (text.length() > 0)) {
							element.getDefaultField().setValue(text);
							v++;
						}
					}

					Iterator ait = node.attributeIterator();
					while (ait.hasNext()) {
						org.dom4j.Attribute attribute = (org.dom4j.Attribute) ait.next();
						if (!checkStdAttributeName(attribute.getName(), nodeMode)) {

							// System.out.println("attribute " +
							// link.element.getFullName() +
							// " - "
							// + attribute.getName());

							ElementField objectField = (ElementField) element.findField(attribute.getName());

							if (objectField == null)
								continue;

							if (objectField.isReference()) {
								objectField.setValue(doGetSpaceElement(attribute.getText()));
							} else {
								if (objectField.isObject()) {
									Object object2 = field.createValue();
									if (((MetaObject) object2).hasDefaultField()) {
										((MetaObject) object2).getDefaultField().setValue(attribute.getText());
										objectField.setValue(object2);
									}
								} else
									objectField.setValue(attribute.getText());
							}
							v++;
						}
					}

					Iterator it = node.elementIterator();
					if (it.hasNext()) {
						while (it.hasNext()) {
							org.dom4j.Element fieldNode = (org.dom4j.Element) it.next();

							try {
								ElementField objectField;
								if (fieldNode.getName().equals("field")) {
									objectField = (ElementField) element.getField(fieldNode.attributeValue("name"));
								} else {
									objectField = (ElementField) element.findField(fieldNode.getName());
								}
								if (objectField == null)
									continue;
								hasValue = false;
								Object value2 = loadElementValue(objectField, fieldNode, objectField.isArray());
								if (hasValue) {
									v++;
									objectField.setValue(value2);
								}

							} catch (MetaException e) {
								e.printStackTrace();
							}

						}
					}
					if (v > 0)
						value = object;
				} else {
					String text = null;
					org.dom4j.Attribute attribute = node.attribute("value");
					if (attribute != null)
						text = attribute.getText();
					else if (node.elements().size() == 0)
						text = node.getText();
					else {
						Iterator it = node.elementIterator("value");
						while (it.hasNext()) {
							org.dom4j.Element valueNode = (org.dom4j.Element) it.next();
							text = valueNode.getText();
							break;
						}
					}

					if ((text != null) && (text.length() > 0)) {
						v++;
						value = text;
					}
				}
			}
			hasValue = v > 0;
			return value;
		}
	}

	protected class LoadModelAction1 extends ProcessElementAction {

		protected ArrayList<ElementNodeLink> nodeLinks;

		protected int doHasNodeLinksCount = 0;
		protected int doNodeLinksSizeNotChanges = 0;
		protected int prevNodeLinksSize = 0;

		protected int doHasParentLinksCount = 0;
		protected int doParentLinksSizeNotChanges = 0;
		protected int prevParentLinksSize = 0;
		
		public LoadModelAction1(Element element) {
			super(element);
		}

		public ArrayList<ElementNodeLink> getNodeLinks() {
			if (nodeLinks == null) {
				nodeLinks = new ArrayList<ElementNodeLink>();
			}
			return nodeLinks;
		}

		public boolean hasNodeLinks() {
			return (nodeLinks != null) && (!nodeLinks.isEmpty());
		}

		public void addNodeLink(ElementNodeLink link) {
			getNodeLinks().add(link);
		}
		
		public void addNodeLink(Element element, org.dom4j.Element node, boolean isOwner, int nodeMode) {
			addNodeLink(new ElementNodeLink(element, node, isOwner, nodeMode));
		}
		
		private void clearNodeLinks() {
			if (hasNodeLinks()) nodeLinks.clear();
		}

		public void processElement(Element owner, org.dom4j.Element ownerNode, int ownerNodeMode) throws MetaException {
			Element element;
			Element elementParent;

			Iterator it = ownerNode.elementIterator();
			while (it.hasNext()) {

				org.dom4j.Element node = (org.dom4j.Element) it.next();

				MetaElementType elementType = getNodeElementType(node);

				element = null;
				int isArray = -1;
				int isReference = -1;
				int nodeMode = 0;

				if (elementType == null) {
					// System.out.println("elementType == null " +
					// owner.getFullName() + " " + owner.elementType.name()
					// + " - " + node.getName() + " " +
					// getNodeElementName(node));

					if ((owner.elementType == MetaElementType.FIELD)
							&& !((node.getNamespace() != null) && (node.getNamespace().getURI().equals("annotation"))))
						continue;

					element = owner.doGetElement(getNodeElementName(node));
					if (element == null) {
						// System.out.println("addNodeLink owner0 " +
						// owner.getFullName() + " - " + node.getName() + " "
						// + getNodeElementName(node));

						addNodeLink(owner, node, true, ownerNodeMode);
						continue;
					} else {
						elementType = element.elementType;
						nodeMode = 1;
					}
				}

				if (owner.elementType.checkElementType(elementType)) {

					String nameSection = node.attributeValue("section");
					MetaElementSection section = (nameSection == null) ? null : MetaElementSection.valueOf(nameSection
							.toUpperCase());

					if (element == null) {

						String name = node.attributeValue("name");
						if ((name == null) || (name.length() == 0)) {
							throw new MetaException("Error node.name == null");
						}

						String nameParent = node.attributeValue("type");
						element = owner.doGetElement(name);

						if (element == null) {

							if (nameParent != null) {
								if (nameParent.endsWith("[]")) {
									isArray = 1;
									nameParent = nameParent.substring(0, nameParent.length() - 2);
								} else
									isArray = 0;

								if (nameParent.endsWith("^")) {
									isReference = 1;
									nameParent = nameParent.substring(0, nameParent.length() - 1);
								} else
									isReference = 0;

								elementParent = doGetSpace().root.doGetElement(nameParent.split("\\."));
								if (elementParent != null) {
									Element whileOwner = owner;
									String parentName = elementParent.getFullName();
									while (whileOwner != null) {
										String ownerName = whileOwner.getFullName();
										if ((ownerName != null) && (ownerName.equals(parentName))) {
											elementParent = null;
											break;
										}
										whileOwner = whileOwner.doGetParent();
									}

								}
							} else
								elementParent = null;

							element = createElement(owner, name, elementType, elementParent, section);

							owner.doGetElements().add(element);

							AddElementAction action = new AddElementAction(element);
							action.execute();

							if (element.hasType())
								element.doConnectParent();
							else {
								if (nameParent != null)
									addParentLink(element, nameParent);
							}

						}
					} else {
						if (section != null)
							element.section = section;
					}

					if (element.elementType == MetaElementType.FIELD) {
						if (isArray != -1) {
							((ElementField) element).setArrayMode(isArray == 1);
						}
						if (isReference != -1) {
							((ElementField) element).setIsReference(isReference == 1);
						}

					}

					if (element.elementType == MetaElementType.OBJECT) {

						String defaultFieldName = node.attributeValue("default");
						if ((defaultFieldName != null) && (defaultFieldName.length() > 0)) {
							((ElementObject) element).defaultFieldName = defaultFieldName;
							// System.out.println(defaultFieldName);
						}
					}

					addNodeLink(element, node, false, nodeMode);

					processElement(element, node, nodeMode);
				}
			}
		}

		private String getNodeElementName(org.dom4j.Element node) {
			String name = node.getName();
			if ((node.getNamespace() != null) && (node.getNamespace().getURI().equals("annotation")))
				name = "@" + name;
			return name;
		}

		private MetaElementType getNodeElementType(org.dom4j.Element node) {
			if (checkStdElementNodeName(node.getName()))
				return MetaElementType.valueOf(node.getName().toUpperCase());
			else
				return null;
		}
		
		public Element processModelNode(org.dom4j.Element node) throws MetaException {
			Element element;
			
			String name = node.attributeValue("name");
			String nameParent = node.attributeValue("type");
			int nodeMode = 0;
			element = createElement(doGetSpace().root, name, MetaElementType.MODEL, null, null);

			doGetSpace().root.doGetElements().add(element);

			if (nameParent != null) {
				addLog("ModelParent - " + element.getFullName() + " = " + nameParent);
				Element parent = doGetSpace().doGetElementN(nameParent);
				if ((parent == null) && (modelResolver != null)) {
					modelResolver.resolveObject(doGetSpace(), nameParent);
					parent = doGetSpace().doGetElement(nameParent);
				}

				element.type = parent;
				element.doConnectParent();
			}

			processElement(element, node, nodeMode);
			
			return element;
		}
		
		public void processLinks() throws MetaException {
			
			if (hasParentLinks()) {
				doHasParentLinksCount++;
				if (getParentLinks().size() == prevParentLinksSize)
					doParentLinksSizeNotChanges++;
				prevParentLinksSize = getParentLinks().size();
				if (doParentLinksSizeNotChanges > 100)
					throw new MetaException("Can not resolve Parent references - " + prevParentLinksSize);

//				System.out.println("hasParentLinks() " + getParentLinks().size());

				Iterator<ElementLink> it = ((ArrayList<ElementLink>) getParentLinks().clone()).iterator();
				clearParentLinks();
				while (it.hasNext()) {
					ElementLink link = it.next();
					addLog("ParentLink - " + link.element.getFullName() + " = " + link.name);

					Element parent = doGetSpace().doGetElementN(link.name);

					if ((parent == null) && (modelResolver != null)) {
						modelResolver.resolveObject(doGetSpace(), link.name);
						parent = doGetSpace().doGetElement(link.name);
					}

					if (parent == null) {
						addParentLink(link);
					} else {
						SetTypeAction action = new SetTypeAction(link.element, parent);
						action.execute();
					}
				}
			}
			if (hasNodeLinks()) {
				doHasNodeLinksCount++;
				if (getNodeLinks().size() == prevNodeLinksSize)
					doNodeLinksSizeNotChanges++;
				prevNodeLinksSize = getNodeLinks().size();
				if (doNodeLinksSizeNotChanges > 100)
					throw new MetaException("Can not resolve Node references - " + prevNodeLinksSize + ": " + getNodeLinks());

//				System.out.println("hasNodeLinks() " + getNodeLinks().size());
				Iterator<ElementNodeLink> it = ((ArrayList<ElementNodeLink>) getNodeLinks().clone()).iterator();
				clearNodeLinks();
				while (it.hasNext()) {
					ElementNodeLink link = it.next();
					if (link.isOwner) {

						Element element1 = link.element.doGetElement(getNodeElementName(link.node));
						if (element1 == null) {
//							System.out.println("addNodeLink 1 owner " + link.element.getFullName() + " - "
//									+ link.node.getName() + " " + getNodeElementName(link.node));
							addNodeLink(link.element, link.node, true, link.nodeMode);
							
						} else {
//							System.out.println("addNodeLink 2 " + element1.getFullName() + " - " + link.node.getName()
//									+ " " + getNodeElementName(link.node));
							addNodeLink(element1, link.node, false, 1);
							processElement(element1, link.node, 1);
						}

					} else {
						if ((link.element.elementType == MetaElementType.FIELD) && (!link.element.hasType())) {
//							System.out.println("addNodeLink 3 " + link.element.getFullName() + " - " + link.node.getName()
//									+ " " + getNodeElementName(link.node));
							addNodeLink(link);
						} else if ((link.element.elementType == MetaElementType.ANNOTATION)
								&& (!link.element.hasType())) {
//							System.out.println("addNodeLink 4 " + link.element.getFullName() + " - " + link.node.getName()
//									+ " " + getNodeElementName(link.node));
							addNodeLink(link);
						} else if (link.element.elementType == MetaElementType.FIELD) {
							(new ElementValueNodeLoad(((ElementField) link.element), link.node, link.nodeMode)).load();
						} else if (link.element instanceof MetaFields) {
							MetaFields fields = (MetaFields) link.element;
							Iterator ait = link.node.attributeIterator();
							boolean b = false;
							while (ait.hasNext() && !b) {
								org.dom4j.Attribute attribute = (org.dom4j.Attribute) ait.next();
								if ((attribute.getNamespace() != null)
										&& (attribute.getNamespace().getURI().equals("annotation"))) {
									MetaAnnotable annotable = (MetaAnnotable) link.element;
									if (!annotable.hasAnnotationName("@" + attribute.getName())) {
										b = true;
									}
								} else if (!checkStdAttributeName(attribute.getName(), link.nodeMode)) {
									if (!fields.hasFieldName(attribute.getName())) {
										b = true;
									}
								}
							}
							if (b) {
								addNodeLink(link);
								continue;
							}
							ait = link.node.attributeIterator();
							while (ait.hasNext()) {
								org.dom4j.Attribute attribute = (org.dom4j.Attribute) ait.next();
								if ((attribute.getNamespace() != null)
										&& (attribute.getNamespace().getURI().equals("annotation"))) {
									MetaAnnotable annotable = (MetaAnnotable) link.element;
									MetaAnnotation annotation = annotable.getAnnotation("@" + attribute.getName());
									if (annotation.hasDefaultField())
										annotation.getDefaultField().setValue(attribute.getText());

								} else if (!checkStdAttributeName(attribute.getName(), link.nodeMode)) {

//									System.out.println("attribute " + link.element.getFullName() + " - "
//											+ attribute.getName());

									MetaField field = fields.getField(attribute.getName());

									if (field.isReference())
										field.setValue(doGetSpaceElement(attribute.getText()));
									else {
										if (field.isObject()) {
											Object object = field.createValue();
											if (((MetaObject) object).hasDefaultField()) {
												((MetaObject) object).getDefaultField().setValue(attribute.getText());
												field.setValue(object);
											}
										} else
											field.setValue(attribute.getText());
									}
								}
							}

							if (fields.hasDefaultField()
									&& !(fields.getDefaultField().isArray() || fields.getDefaultField().isObject())) {

								String text = null;
								org.dom4j.Attribute attribute = link.node.attribute("value");
								if (attribute != null)
									text = attribute.getText();
								else if (link.node.elements().size() == 0)
									text = link.node.getText();

								if ((text != null) && (text.length() > 0))
									fields.getDefaultField().setValue(text);
							}
						}
					}
				}
			}
		}

		public void execute() throws MetaException {
			
			while (hasParentLinks() || hasNodeLinks()) {
				processLinks();
			}
		}

	}

	protected class ElementLink {

		protected Element element;

		protected String name;

		public ElementLink(Element element, String name) {
			this.element = element;
			this.name = name;
		}
	}

	protected class ElementNodeLink {

		protected Element element;

		protected boolean isOwner;

		protected org.dom4j.Element node;

		protected int nodeMode;

		public ElementNodeLink(Element element, org.dom4j.Element node) {
			this(element, node, false, 0);
		}

		public ElementNodeLink(Element element, org.dom4j.Element node, boolean isOwner, int nodeMode) {
			this.element = element;
			this.isOwner = isOwner;
			this.node = node;
			this.nodeMode = nodeMode;
		}

		@Override
		public String toString() {
			return "xml:" + node + ";element:" + element + ";isOwner:" + isOwner + ";nodeMode:" + nodeMode;
		}
		
	}

	protected class ElementValue {

		protected ElementField owner;

		protected Object value;

		public ElementValue(ElementField owner, Object value) {
			this.owner = owner;
			this.value = value;
		}

	}

	protected class ElementList<E extends MetaElement> extends ArrayList {

		private static final long serialVersionUID = 6888421885645888187L;

		@SuppressWarnings("unchecked")
		public ElementList(Element owner, MetaElementType elementType) {
			super();
			if (owner.doHasElements()) {
				Iterator<Element> it = owner.doGetElements().iterator();
				if (elementType != null) {
					while (it.hasNext()) {
						Element value = it.next();
						if (value.elementType == elementType)
							add((E) value);
					}
				} else {
					while (it.hasNext()) {
						add((E) it.next());
					}
				}
			}
		}

	}

	protected class ElementChangeInfo implements MetaChangeInfo, MetaLogInfo {

		protected Element element;

		protected MetaChangeType action;

		protected Object value;

		protected Object oldValue;

		public ElementChangeInfo(Element element, MetaChangeType action, Object value, Object oldValue) {
			this.element = element;
			this.action = action;
			this.value = value;
			this.oldValue = oldValue;
		}

		public MetaChangeType getAction() {
			return action;
		}

		public MetaElement getElement() {
			return element;
		}

		public Object getValue() {
			return value;
		}

		public void processListeners() {
			Element curr = element;
			while (curr != null) {
				if (curr.hasListeners()) {
					Iterator<MetaChangeListener> it = curr.getListeners().iterator();
					while (it.hasNext()) {
						MetaChangeListener listener = it.next();
						if (curr == element) {
							listener.elementChanged(this);
						} else {
							if (listener.hasChildChanges())
								listener.elementChanged(this);
						}
					}

				}
				curr = curr.parent;
			}

		}

		public Object getOldValue() {

			return oldValue;
		}

		public String getInfoText() {
			String text = element.getFullName();

			if (root == element)
				text = "Space";

			if (action.hasValue()) {

				text = text + " - " + action.getCaption() + " " + getValueInfoText(value);

				if (action.hasOldValue())
					text = text + " old value " + getValueInfoText(oldValue);

			} else {

				text = action.getCaption() + " - " + text;
			}

			return text;
		}

		private String getValueInfoText(Object value) {
			if (value == null)
				return "null";
			else {
				if (value instanceof String)
					return "\"" + value.toString() + "\"";
				else if (value instanceof Element)
					return ((Element) value).getFullName();
			}
			return "unknown";
		}

		public String getInfoText(int level) {

			return getInfoText();
		}

	}

	protected class ElementChanges {

		private int changeLevel = 0;

		protected List<ElementChangeInfo> items = new ArrayList<ElementChangeInfo>();

	}

	protected class ElementListeners {

		protected List<MetaChangeListener> items = new ArrayList<MetaChangeListener>();

	}

	protected class Element implements MetaElement, MetaLogInfo {

		protected Element parent;

		protected Element type;

		protected String name;

		protected MetaElementType elementType;

		protected MetaElementSection section = MetaElementSection.PUBLIC;

		protected ArrayList<Element> elements;

		protected ArrayList<Element> parentedElements;

		protected boolean parentConnected = false;

		protected int inheritedMode = 0;

		protected boolean isRemoveElement1 = false;

		protected ElementListeners listeners;

		public Element() {
			doGetSpace().loadDefinitions(this);
		}

		public Element(Element owner, String name, Element type, MetaElementSection section) {
			this.parent = owner;
			this.name = name;
			this.type = type;
			if (section != null)
				this.section = section;
		}

		protected void clear() {
			parent = null;
			type = null;
			listeners = null;
			parentedElements = null;
			if (elements != null) {
				Iterator<Element> it = elements.iterator();
				while (it.hasNext()) {
					Element value = it.next();
					value.clear();
				}
			}
			elements = null;
		}

		public boolean hasSpace() {
			return doGetSpace() != null;
		}

		public Element doGetParent() {
			return parent;
		}

		public String getFullName() {
			return MetaSpaceImpl.getFullName(this);
		}

		public void doSetParent(Element parent) throws MetaException {
			if (this.parent == null)
				this.parent = parent;
			else if (this.parent != parent)
				throw new MetaException("Error doSetParent(" + ")");
		}

		public boolean hasParent() {
			return (parent != null) && (parent.elementType != null);
		}

		public boolean hasType() {
			return (type != null);
		}

		public boolean hasName() {
			return (name != null) && (!name.equals(""));
		}

		public String getName() {
			return name;
		}

		public void doSetName(String name) throws MetaException {
			notifyChange(MetaChangeType.NAME, name, this.name);
			this.name = name;
		}

		public void setName(String name) throws MetaException {
			if (!this.name.equals(name)) {
				if ((name == null) || name.equals(""))
					throw new MetaException("Error setName() name = null");
				if (doGetParent().hasElementName(name))
					throw new MetaException("Error setName(" + name + ")");
				doSetName(name);
			}
		}

		public Element doGetType() {
			return type;
		}

		public void doSetType(String typeName) throws MetaException {
			doSetType(doGetSpace().doGetElement(typeName));
		}

		public void doSetType(Element type) throws MetaException {
			doDisconnectParent();
			notifyChange(MetaChangeType.TYPE, type, this.type);
			this.type = type;
			doConnectParent();
		}

		public void doConnectParent() throws MetaException {
			if ((this.type != null) && (!parentConnected)) {
				this.type.addParentedElement(this);
				ConnectParentAction action = new ConnectParentAction(this);
				action.execute();
				parentConnected = true;
			}
		}

		public boolean checkInheritedElement(Element element) {
			return element.section != MetaElementSection.PRIVATE;
		}

		public void doDisconnectParent() {
			if (this.type != null) {
				this.type.removeParentedElement(this);

			}
			parentConnected = false;
		}

		public void removeParentedElement(Element value) {
			if (hasParentedElements())
				getParentedElements().remove(value);
		}

		public void addParentedElement(Element value) {
			if (!getParentedElements().contains(value))
				getParentedElements().add(value);
		}

		public boolean hasParentedElements() {
			return (parentedElements != null) && (!parentedElements.isEmpty());
		}

		public List<Element> getParentedElements() {
			if (parentedElements == null) {
				parentedElements = new ArrayList<Element>();
			}
			return parentedElements;
		}

		public boolean doHasElements() {
			return (elements != null) && (!elements.isEmpty());
		}

		public boolean doHasElements(MetaElementType elementType) {

			if (doHasElements()) {
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).elementType == elementType)
						return true;
				}
			}

			return false;
		}

		public List<Element> doGetElements() {
			if (elements == null) {
				elements = new ArrayList<Element>();
			}
			return elements;
		}

		protected Element doGetElement(String[] names) {
			if (!doHasElements())
				return null;

			Element curr = this;
			Element value = null;
			for (int i = 0; i < names.length; i++) {
				value = curr.doGetElement(names[i]);
				if (value == null)
					break;
				curr = value;
			}
			return value;
		}

		protected Element doGetElement(String[] names, MetaElementType elementType) {
			Element value = doGetElement(names);
			if ((value != null) && (value.elementType == elementType))
				return value;
			else
				return null;
		}

		protected Element doGetElement(String name) {
			if (!doHasElements())
				return null;
			Iterator<Element> it = doGetElements().iterator();
			while (it.hasNext()) {
				Element value = it.next();
				if (value.getName().equals(name))
					return value;
			}
			return null;
		}

		protected Element getElementP(String name) throws MetaException {
			Element value = doGetElement(name.split("\\."));
			if (value != null)
				return value;
			else
				throw new MetaException("Error getElementP(" + name + ")");

		}

		protected Element getElementP(String name, MetaElementType elementType) throws MetaException {
			Element value = doGetElement(name.split("\\."));
			if ((value != null) && (value.elementType == elementType))
				return value;
			else {
//				throw new MetaException("Error getElementP(" + name + "," + elementType.name() + ") " + name.split("\\.").toString() + ((value != null)?" " + value.elementType:" !"));
				throw new MetaException("Error getElementP(" + name + "," + elementType.name() + ")");
			}	

		}

		public boolean hasElementName(String name) {
			return hasElementName(name, null);
		}

		public boolean hasElementName(String name, MetaElementType elementType) {
			Element element = doGetElement(name.split("\\."));
			if (element == null)
				return false;
			if ((elementType != null) && (element.elementType != elementType))
				return false;
			if (element.isInherited() && (element.section == MetaElementSection.PRIVATE))
				return false;
			if ((this.elementType == MetaElementType.FIELD) && (element.section != MetaElementSection.PUBLIC))
				return false;
			return true;
		}

		public String generateName(String template) {
			String name;
			for (int i = 1; i < 100000; i++) {
				name = template + i;
				if (!hasElementName(name))
					return name;
			}
			throw new RuntimeException("Error generateName(" + template + ")");
		}

		public String generateName(MetaElementType elementType) {
			return generateName(elementType.getTemplate());
		}

		protected Element doAddElement(Element value) throws MetaException {
			doGetElements().add(value);
			value.doConnectParent();

			AddElementAction action = new AddElementAction(value);
			action.execute();

			notifyChange(MetaChangeType.ADD, value, null);

			return value;
		}

		protected void doRemoveElement(Element value) throws MetaException {
			RemoveElementAction action = new RemoveElementAction(value);
			action.execute();
			notifyChange(MetaChangeType.REMOVE, value, null);
		}

		public Element doAddElement(String name, MetaElementType elementType, Element type, MetaElementSection section)
				throws MetaException {

			if (((elementType == MetaElementType.ANNOTATION) || (elementType == MetaElementType.FIELD))
					&& (type == null))
				throw new MetaException("Error doAddElement(" + name + "," + elementType.getTemplate() + ")");

			String elementName;
			if ((name != null) && (!name.equals(""))) {
				checkElementName(name);
				if (doGetElement(name) != null)
					throw new MetaException("Error doAddElement(" + name + "," + elementType.getTemplate() + ")");

				elementName = name;
			} else {
				if (elementType == MetaElementType.ANNOTATION)
					elementName = "@" + type.getName();
				else
					elementName = generateName(elementType);
			}

			return doAddElement(createElement(this, elementName, elementType, type, section));
		}

		public void removeElement(Element value) throws MetaException {
			if (value.parent == this)
				doRemoveElement(value);
			else
				throw new MetaException("Error removeElement(" + value.name + ")");
		}

		public boolean isParent(Element value) {
			if (hasParent()) {
				if (doGetParent() == value)
					return true;
				else
					return doGetParent().isParent(value);
			} else
				return false;
		}

		public void doSetSection(MetaElementSection section) {
			notifyChange(MetaChangeType.SECTION, section, this.section);
			this.section = section;
		}

		public void setSection(MetaElementSection section) {
			if (this.section != section)
				doSetSection(section);
		}

		public MetaElementSection getSection() {
			return section;
		}

		public MetaSpace getSpace() {
			return doGetSpace();
		}

		public MetaElement addElement(MetaElement value) throws MetaException {
			// TODO addElement(MetaElement value)
			return value;
		}

		public MetaElement addElement(String name, MetaElementType elementType, MetaElement type,
				MetaElementSection section) throws MetaException {
			if (type == null)
				return doAddElement(name, elementType, null, section);
			else
				return doAddElement(name, elementType, doGetSpace().doGetElement(type.getFullName()), section);
		}

		public MetaElement getElement(String name) throws MetaException {
			return getElementP(name);
		}

		@SuppressWarnings("unchecked")
		public List<MetaElement> getElements() {
			return new ElementList<MetaElement>(this, null);
		}

		public MetaElement getParent() {
			if (hasParent())
				return doGetParent();
			else
				return null;
		}

		public MetaElementType getElementType() {
			return elementType;
		}

		public boolean hasElements() {
			return doHasElements();
		}

		public void removeElement(MetaElement value) throws MetaException {
			removeElement(value.getName());
		}

		public void removeElement(String name) throws MetaException {
			Element value = doGetElement(name);
			if (value != null)
				doRemoveElement(value);
			else
				throw new MetaException("Error removeElement(" + name + ")");
		}

		public void setParent(MetaElement owner) throws MetaException {
			doSetParent(doGetSpace().doGetElement(owner.getFullName()));
		}

		public MetaAnnotation addAnnotation(MetaAnnotation value) throws MetaException {
			return null;
		}

		public MetaAnnotation addAnnotation(MetaObject type, MetaElementSection section) throws MetaException {
			return (MetaAnnotation) doAddElement(null, MetaElementType.ANNOTATION, doGetSpace().doGetElement(
					type.getFullName()), section);
		}

		public MetaAnnotation addAnnotation(MetaObject type) throws MetaException {
			return addAnnotation(type, null);
		}

		public MetaAnnotation getAnnotation(String name) throws MetaException {
			return (MetaAnnotation) getElementP(name, MetaElementType.ANNOTATION);
		}

		@SuppressWarnings("unchecked")
		public List<MetaAnnotation> getAnnotations() {
			return new ElementList<MetaAnnotation>(this, MetaElementType.ANNOTATION);
		}

		public boolean hasAnnotationName(String name) {
			return hasElementName(name, MetaElementType.ANNOTATION);
		}

		public boolean hasAnnotations() {
			return doHasElements(MetaElementType.ANNOTATION);
		}

		public void removeAnnotation(MetaAnnotation value) throws MetaException {
			removeAnnotation(value.getName());
		}

		public void removeAnnotation(String name) throws MetaException {
			doRemoveElement(getElementP(name, MetaElementType.ANNOTATION));
		}

		public boolean isInherited() {
			return inheritedMode > 0;
		}

		public void addListener(MetaChangeListener listener) {
			if (listeners == null)
				listeners = new ElementListeners();
			if (!listeners.items.contains(listener))
				listeners.items.add(listener);
		}

		public List<MetaChangeListener> getListeners() {
			if (hasListeners()) {
				return new ArrayList<MetaChangeListener>(listeners.items);
			} else
				return new ArrayList<MetaChangeListener>();
		}

		public boolean hasListeners() {
			return (listeners != null) && (!listeners.items.isEmpty());
		}

		public boolean removeListener(MetaChangeListener listener) {
			if (hasListeners()) {
				return listeners.items.remove(listener);
			} else
				return false;
		}

		protected void notifyChange(MetaChangeType action, Object value, Object oldValue) {
			beginChanges();
			try {
				addChange(new ElementChangeInfo(this, action, value, oldValue));
			} finally {
				endChanges();
			}
		}

		public String getInfoText() {

			String state = section.name() + " ";
			if ((parentConnected) || (type == null))
				state = state + "PC = true";
			else
				state = state + "PC = false";
			if (inheritedMode > 0) {
				if (inheritedMode == 1)
					state = state + " INH = true";
				else
					state = state + " INH = REF";
			} else
				state = state + " INH = false";

			if (hasParentedElements())
				state = state + " PCount = " + getParentedElements().size();
			else
				state = state + " PCount = 0";

			return state;

		}

		public String getInfoText(int level) {

			return getInfoText();
		}

		public String getDescription() {
			MetaAnnotation element = doGetDescriptionAnnotation();
			if (element != null) {
				Object value;
				value = element.getDefaultField().getValue();
				if (value != null) {
					return value.toString();
				}
			}
			return null;
		}

		protected ElementAnnotation doGetDescriptionAnnotation() {
			return (ElementAnnotation) doGetElement(DESCRIPTION_ELEMENT_NAME);
		}

		public MetaAnnotation getDescriptionAnnotation() {
			return doGetDescriptionAnnotation();
		}

		public boolean hasDescription() {
			String value = getDescription();
			return (value != null) && (value.length() > 0);
		}

		public void setDescription(String text) {
			if ((text != null) && (text.length() > 0)) {
				MetaAnnotation element = getDescriptionAnnotation();
				try {

					if (element == null) {
						element = (MetaAnnotation) doAddElement(createElement(this, DESCRIPTION_ELEMENT_NAME,
								MetaElementType.ANNOTATION, getDescriptionObjectType(), MetaElementSection.PRIVATE));
					}
					element.getDefaultField().setValue(text);
				} catch (MetaException e) {
					throw new RuntimeException(e);
				}
			} else
				clearDescription();
		}

		public void setDescriptionAnnotation(MetaAnnotation description) {
			clearDescription();
			if (description != null) {

			}
		}

		public void clearDescription() {
			// TODO Auto-generated method stub

		}

		@SuppressWarnings("unchecked")
		public List<MetaElement> getElements(MetaElementType elementType) {
			return new ElementList<MetaElement>(this, elementType);
		}

		public String generateName() {
			return generateName("Name");
		}

		public MetaElement addElement(String name, MetaElementType elementType, String typeName,
				MetaElementSection section) throws MetaException {
			if (typeName == null)
				return doAddElement(name, elementType, null, section);
			else
				return doAddElement(name, elementType, doGetSpace().doGetElement(typeName), section);
		}

		public MetaElement getType() {
			return doGetType();
		}

		public String getTypeFullName() {
			if (hasType()) {
				return type.getFullName();
			} else
				return "";
		}

		public void setType(MetaElement value) throws MetaException {
			doSetType(value.getFullName());
		}

		public void setTypeFullName(String name) throws MetaException {
			doSetType(name);
		}

		public MetaElement findElement(String name) {
			return doGetElement(name.split("\\."));
		}

		protected boolean doInheritsFrom(Element type) {
			if (type != null) {
				Element value;
				if (this instanceof ElementObject)
					value = this;
				else
					value = this.type;

				while (value != null) {
					if (value.getFullName().equals(type.getFullName()))
						return true;
					value = value.type;
				}
			}
			return false;
		}

		public boolean inheritsFrom(MetaElement type) {
			return doInheritsFrom(doGetSpace().doGetElementN(type.getFullName()));
		}

		public boolean inheritsFrom(String typeName) {
			return doInheritsFrom(doGetSpace().doGetElementN(typeName));
		}

		public MetaAnnotation findAnnotation(String name) {
			return (MetaAnnotation) doGetElement(name.split("\\."), MetaElementType.ANNOTATION);
		}

		@Override
		public String toString() {
			return getFullName();
		}
	}

	protected class ElementObject extends Element implements MetaObject {

		protected String defaultFieldName;

		public ElementObject(Element owner, String name, Element type, MetaElementSection section) {
			super(owner, name, type, section);
			elementType = MetaElementType.OBJECT;
		}

		protected boolean doInheritsFrom(Element type) {
			if (type == element_o) {
				return true;
			}
			return super.doInheritsFrom(type);
		}

		public MetaField addField(MetaField value) throws MetaException {
			MetaField field = addField(value.getName(), (MetaObject) value.getType());
			if (!value.isInheritedValue())
				field.setValue(value.getValue());
			return field;
		}

		public MetaField addField(String name, MetaObject type) throws MetaException {
			return (MetaField) doAddElement(name, MetaElementType.FIELD, doGetSpace().doGetElement(type.getFullName()),
					null);
		}

		public MetaField addField(String name, MetaObject type, MetaElementSection section) throws MetaException {
			return (MetaField) doAddElement(name, MetaElementType.FIELD, doGetSpace().doGetElement(type.getFullName()),
					section);
		}

		public MetaField getField(String name) throws MetaException {
			return (MetaField) getElementP(name, MetaElementType.FIELD);
		}

		@SuppressWarnings("unchecked")
		public List<MetaField> getFields() {
			return new ElementList<MetaField>(this, MetaElementType.FIELD);
		}

		public void removeField(MetaField value) throws MetaException {
			removeField(value.getName());
		}

		public void removeField(String name) throws MetaException {
			doRemoveElement(getElementP(name, MetaElementType.FIELD));
		}

		public boolean hasFieldName(String name) {
			return hasElementName(name, MetaElementType.FIELD);
		}

		public boolean hasFields() {
			return doHasElements(MetaElementType.FIELD);
		}

		public MetaObject getObject(String name) throws MetaException {
			return (MetaObject) getElementP(name, MetaElementType.OBJECT);
		}

		@SuppressWarnings("unchecked")
		public List<MetaObject> getObjects() {
			return new ElementList<MetaObject>(this, MetaElementType.OBJECT);
		}

		public boolean hasObjectName(String name) {
			return hasElementName(name, MetaElementType.OBJECT);
		}

		public boolean hasObjects() {
			return doHasElements(MetaElementType.OBJECT);
		}

		public MetaObject addObject(MetaObject value) throws MetaException {
			// TODO Auto-generated method stub
			return null;
		}

		public MetaObject addObject() throws MetaException {
			return (MetaObject) doAddElement(null, MetaElementType.OBJECT, null, null);
		}

		public MetaObject addObject(String name) throws MetaException {
			return (MetaObject) doAddElement(name, MetaElementType.OBJECT, null, null);
		}

		public MetaObject addObject(String name, MetaObject type) throws MetaException {
			if (type == null)
				return (MetaObject) doAddElement(name, MetaElementType.OBJECT, null, null);
			else
				return (MetaObject) doAddElement(name, MetaElementType.OBJECT, doGetSpace().doGetElement(
						type.getFullName()), null);
		}

		public MetaObject addObject(String name, MetaObject type, MetaElementSection section) throws MetaException {
			if (type == null)
				return (MetaObject) doAddElement(name, MetaElementType.OBJECT, null, section);
			else
				return (MetaObject) doAddElement(name, MetaElementType.OBJECT, doGetSpace().doGetElement(
						type.getFullName()), section);
		}

		public void removeObject(MetaObject value) throws MetaException {
			removeObject(value.getName());
		}

		public void removeObject(String name) throws MetaException {
			doRemoveElement(getElementP(name, MetaElementType.OBJECT));
		}

		public Object createValue() throws MetaException {

			if (isObject()) {
				ElementObjectValue value = new ElementObjectValue(this, 0);
				value.loadValue(this);
				return (MetaObject) value;
			}

			if (getName().compareToIgnoreCase("string") == 0)
				return new String();

			if (getName().compareToIgnoreCase("integer") == 0)
				return new Integer(0);

			if (getName().compareToIgnoreCase("long") == 0)
				return new Long(0);

			if (getName().compareToIgnoreCase("float") == 0)
				return new Float(0);

			if (getName().compareToIgnoreCase("double") == 0)
				return new Double(0);

			if (getName().compareToIgnoreCase("datetime") == 0)
				return new Date();

			if (getName().compareToIgnoreCase("boolean") == 0)
				return new Boolean(true);
			
			if (getName().compareToIgnoreCase("byte") == 0)
				return new Byte((byte)0);

			return new String();
		}

		public boolean isValue() {
			return false;
		}

		public boolean isObject() {
			if (hasElements()) {
				Iterator<Element> it = doGetElements().iterator();
				while (it.hasNext()) {
					Element field = it.next();
					if ((field.getElementType() == MetaElementType.FIELD)
							&& (field.getSection() == MetaElementSection.PUBLIC)) {
						return true;
					}
				}
			}
			return false;
		}

		public Class<?> doGetValueClass(boolean isArray) {
			if (isArray) {
				if (isObject()) {
					return MetaObject[].class;
				}

				if (getName().compareToIgnoreCase("string") == 0)
					return String[].class;

				if (getName().compareToIgnoreCase("integer") == 0)
					return Integer[].class;

				if (getName().compareToIgnoreCase("long") == 0)
					return Long[].class;

				if (getName().compareToIgnoreCase("float") == 0)
					return Float[].class;

				if (getName().compareToIgnoreCase("double") == 0)
					return Double[].class;

				if (getName().compareToIgnoreCase("datetime") == 0)
					return Date[].class;

				if (getName().compareToIgnoreCase("boolean") == 0)
					return Boolean[].class;
				
				if (getName().compareToIgnoreCase("byte") == 0)
					return Byte[].class;
			} else {
				if (isObject()) {
					return MetaObject.class;
				}

				if (getName().compareToIgnoreCase("string") == 0)
					return String.class;

				if (getName().compareToIgnoreCase("integer") == 0)
					return Integer.class;

				if (getName().compareToIgnoreCase("long") == 0)
					return Long.class;

				if (getName().compareToIgnoreCase("float") == 0)
					return Float.class;

				if (getName().compareToIgnoreCase("double") == 0)
					return Double.class;

				if (getName().compareToIgnoreCase("datetime") == 0)
					return Date.class;

				if (getName().compareToIgnoreCase("boolean") == 0)
					return Boolean.class;
				
				if (getName().compareToIgnoreCase("byte") == 0)
					return Byte.class;				
			}
			return String.class;
		}

		public Class<?> getValueClass() {
			return doGetValueClass(false);
		}

		public MetaField getDefaultField() {
			if (hasDefaultField())
				try {
					return getField(getDefaultFieldName());
				} catch (MetaException e) {
					e.printStackTrace();
				}
			return null;
		}

		public boolean hasDefaultField() {
			return (defaultFieldName != null) && (defaultFieldName.length() > 0);
		}

		public void setDefaultField(MetaField field) throws MetaException {
			setDefaultField(field.getName());
		}

		public String getDefaultFieldName() {
			return defaultFieldName;
		}

		public void setDefaultField(String name) throws MetaException {
			if (getElementP(name, MetaElementType.FIELD) != null)
				defaultFieldName = name;
		}

		public MetaField findField(String name) {
			return (MetaField) doGetElement(name.split("\\."), MetaElementType.FIELD);
		}

		public MetaObject findObject(String name) {
			return (MetaObject) doGetElement(name.split("\\."), MetaElementType.OBJECT);
		}

		public boolean checkInheritedElement(Element element) {
			boolean result = super.checkInheritedElement(element);
			if (result && (element.elementType == MetaElementType.OBJECT) && hasType()) {
				Element whileParent = doGetParent();
				String typeName = doGetType().getFullName();
				while (whileParent != null) {
					String ownerName = whileParent.getFullName();
					if ((ownerName != null) && (ownerName.equals(typeName))) {
						result = false;
						break;
					}
					whileParent = whileParent.doGetParent();
				}
			}
			return result;
		}
	}

	protected class ElementField extends Element implements MetaField {

		protected boolean isInheritedValue = false;

		protected ElementValue value;

		protected boolean isArray = false;

		protected boolean isReference = false;

		public ElementField(Element owner, String name, Element type, MetaElementSection section) {
			super(owner, name, type, section);
			elementType = MetaElementType.FIELD;
			if (type == element_o) {
				isReference = true;
			}
		}

		public boolean checkInheritedElement(Element element) {
			return (element.elementType == MetaElementType.ANNOTATION)
					&& (element.section == MetaElementSection.PUBLIC);
		}

		public void setInheritedValue() {
			// TODO Auto-generated method stub
		}

		public boolean isNull() {
			return (doGetValue() == null);
		}

		public void setValue(Object value) throws MetaException {

			value = convertValue(value);

			if (compareValue(doGetValue(), value))
				return;
			if (value != null) {
				if (isArray()) {
					if (value.getClass().isArray()) {
						Object[] values = ((Object[]) value);
						Object[] newValues = new Object[values.length];
						for (int i = 0; i < values.length; i++) {
							newValues[i] = cloneValue(values[i], getCloneMode());
						}
						doSetValue(newValues);
					} else {
						Object[] newValues = new Object[1];
						newValues[0] = cloneValue(value, getCloneMode());
						doSetValue(newValues);
					}

				} else {
					Object newValue = cloneValue(value, getCloneMode());
					doSetValue(newValue);
				}
			} else
				doSetValue(value);
		}

		protected int getCloneMode() {
			return 1;
		}

		protected boolean compareValue(Object oldValue, Object newValue) {

			if ((oldValue == null) && (newValue == null))
				return true;

			return false;
		}

		protected Object cloneValue(Object object, int cloneMode) throws MetaException {
			if (object == null) {
				return null;
			}
			if (object instanceof MetaObject) {
				ElementObjectValue objectValue = new ElementObjectValue((ElementObject) type, cloneMode);
				objectValue.loadValue((MetaObject) object);
				return objectValue;
			} else if (object instanceof String) {
				return object;
			} else if (object instanceof Integer) {
				return object;
			} else if (object instanceof Long) {
				return object;
			} else if (object instanceof Float) {
				return object;
			} else if (object instanceof Double) {
				return object;
			} else if (object instanceof Date) {
				return object;
			} else if (object instanceof Boolean) {
				return object;
			}
			return object;
		}

		protected Object doGetValue() {
			if (value == null)
				return null;
			else
				return value.value;
		}

		public Object getValue() {
			try {
				if (isReference())
					return getRefToObject(doGetValue());
				else
					return cloneValue(doGetValue(), 0);
			} catch (MetaException e) {
				throw new RuntimeException(e);
			}
		}

		protected Object getRefToObject(Object value) throws MetaException {
			if (value != null) {
				if (isArray()) {
					if (value.getClass().isArray()) {
						Object[] values = ((Object[]) value);
						MetaObject[] newValues = new MetaObject[values.length];
						for (int i = 0; i < values.length; i++) {
							newValues[i] = doRefToObject(values[i]);
						}
						return newValues;
					} else {
						return doRefToObject(value);
					}
				} else {
					return doRefToObject(value);
				}
			}
			return null;
		}

		protected MetaObject doRefToObject(Object value) throws MetaException {
			if (value == null) return null;
			if (!(value instanceof String))
				throw new MetaException("doRefToObject (!(value instanceof String))");
			return (MetaObject) doGetSpace().getElement((String) value);
		}

		private Object convertValue(Object value) throws MetaException {
			return convertValue(value, isArray());
		}

		private Object convertValue(Object value, boolean isArray) throws MetaException {
			if (value != null) {
				if (isArray) {
					if (value.getClass().isArray()) {
						Object[] values = ((Object[]) value);
						Object[] newValues = new Object[values.length];
						for (int i = 0; i < values.length; i++) {
							newValues[i] = convertValue(values[i], false);
						}
						return newValues;
					} else {
						return new Object[] { convertValue(value, false) };
					}
				} else {
					if (isObject()) {

					} else if (isReference()) {
						value = doObjectToRef(value);
					} else {

						Object object = createValue();
						if (value instanceof String) {
							String text = (String) value;
							if (object instanceof Integer) {
								value = Integer.valueOf(text);
							} else if (object instanceof Long) {
								value = Long.valueOf(text);
							} else if (object instanceof Float) {
								value = Float.valueOf(text);
							} else if (object instanceof Double) {
								value = Double.valueOf(text);
							} else if (object instanceof Date) {
								try {
									value = DateFormat.getInstance().parse(text);
								} catch (ParseException e) {
									value = null;
								}
							} else if (object instanceof Boolean) {
								value = Boolean.valueOf(text);
							}
						}
					}

				}

			}
			return value;
		}

		protected String doObjectToRef(Object value) throws MetaException {
			if (value == null) return null;
			ElementObject object = null;
			if (value instanceof MetaObject) {
				object = (ElementObject) doGetSpace().getElement(((MetaObject) value).getFullName());
			} else if (value instanceof String) {
				if (((String) value).equals("")) return null;	
				object = (ElementObject) doGetSpace().getElement((String) value);
			}
			if (object == null)
				throw new MetaException("doObjectToRef (object == null)");

			if (!object.doInheritsFrom(doGetType()))
				throw new MetaException("doObjectToRef (!object.doInheritsFrom(doGetType()))");

			return object.getFullName();
		}

		public boolean isArray() {
			return isArray;
		}

		public boolean isInheritedValue() {
			return isInheritedValue;
		}

		protected void doSetValue(Object value) throws MetaException {

			if ((isInheritedValue) || (this.value == null)) {

				if (this.value == null)
					notifyChange(MetaChangeType.VALUE, value, null);
				else
					notifyChange(MetaChangeType.VALUE, value, this.value.value);

				SetValueFieldAction action = new SetValueFieldAction(this, value);
				action.execute();
			} else {

				notifyChange(MetaChangeType.VALUE, value, this.value.value);
				clearValue(this.value.value);
				this.value.value = value;
			}
		}

		protected void clearValue() {
			clearValue(doGetValue());
		}

		protected void clearValue(Object value) {
			if (value == null)
				return;

			if (value.getClass().isArray()) {
				Object[] values = ((Object[]) value);
				for (int i = 0; i < values.length; i++) {
					clearValue(values[i]);
				}
			} else {
				if (value instanceof ElementObjectValue) {
					((ElementObjectValue) value).clearValue();
				}
			}
		}

		public String getInfoText() {

			String state = super.getInfoText();

			if (isInheritedValue)
				state = state + " INHV = true";
			else
				state = state + " INHV = false";

			if (isArray()) {
				state = state + " A = true";
			} else
				state = state + " A = false";

			if (value == null)
				state = state + " value = null";
			else {
				state = state + " value = " + getValueText();
				if (value.owner != this)
					state = state + " (" + value.owner.getFullName() + ")";
			}

			return state;
		}

		public void setArrayMode(boolean isArray) {
			try {
				if (this.isArray == isArray)
					return;
				if (isInherited())
					throw new MetaException("Is inherited field");

				notifyChange(MetaChangeType.ARRAY_MODE, isArray, this.isArray);

				SetArrayModeFieldAction action = new SetArrayModeFieldAction(this, isArray);
				action.execute();
			} catch (MetaException e) {
				throw new RuntimeException(e);
			}

		}

		public String getValueText() {
			return doGetValueText(doGetValue());
		}

		protected String doGetValueText(Object value) {
			if (value == null) {
				if (isArray())
					return "[]";
				else
					return "";
			}
			String text = "";
			if (value.getClass().isArray()) {
				Object[] values = ((Object[]) value);
				for (int i = 0; i < values.length; i++) {
					String valueText = doGetValueText(values[i]);
					if ((valueText != null) && !valueText.equals(""))
						text = text + ((text.length() > 0) ? "," : "") + valueText;
				}
				text = "[" + text + "]";
			} else {
				if (value instanceof ElementObject) {
					ElementObject object = (ElementObject) value;
					if (object.isValue()) {
						Iterator<Element> it = object.doGetElements().iterator();

						while (it.hasNext()) {
							Element field = it.next();
							if ((field.getElementType() == MetaElementType.FIELD)
									&& (field.getSection() == MetaElementSection.PUBLIC)) {
								String valueText = ((ElementField) field).getValueText();
								if ((valueText != null) && !valueText.equals(""))
									text = text + ((text.length() > 0) ? "," : "") + valueText;
							}
						}
					}
					text = object.getFullName();

					text = "{" + text + "}";
				} else if (value instanceof String) {
					text = "\"" + value + "\"";
				} else if (value instanceof Date) {
					text = "{" + value + "}";
				} else
					text = value.toString();
			}
			return text;
		}

		public boolean isObject() {
			return ((ElementObject) doGetType()).isObject();
		}

		public boolean isValue() {
			return (parent instanceof ElementObjectValue);
		}

		public int getArrayLength() {
			if (isArray() && !isNull()) {
				Object[] values = (Object[]) doGetValue();
				return values.length;
			}
			return 0;
		}

		public Class<?> getValueClass() {
			return ((ElementObject) doGetType()).doGetValueClass(isArray());
		}

		public boolean isDefault() {
			if (doGetParent() instanceof ElementObject) {
				return name.equals(((ElementObject) doGetParent()).getDefaultFieldName());
			} else if (doGetParent() instanceof ElementAnnotation) {
				return name.equals(((ElementAnnotation) doGetParent()).getDefaultFieldName());
			}
			return false;
		}

		public void setIsDefault(boolean isDefault) {
			if (doGetParent() instanceof ElementObject) {
				try {
					((ElementObject) doGetParent()).setDefaultField(name);
				} catch (MetaException e) {
					throw new RuntimeException(e);
				}
			}
		}

		public void addValue(Object value) throws MetaException {
			if (isArray()) {
				Object[] values = (Object[]) doGetValue();
				if (values != null) {
					Object[] newValues = new Object[values.length + 1];
					for (int i = 0; i < values.length; i++) {
						newValues[i] = values[i];
					}
					newValues[newValues.length - 1] = isReference() ? doObjectToRef(value) : cloneValue(value,
							getCloneMode());
					doSetValue(newValues);
				} else {
					Object[] newValues = new Object[1];
					newValues[0] = isReference() ? doObjectToRef(value) : cloneValue(value, getCloneMode());
					doSetValue(newValues);
				}

			} else
				setValue(value);

		}

		public Object createValue() throws MetaException {
			if (isReference())
				return null;
			else	
			    return ((ElementObject) doGetType()).createValue();
		}

		public boolean isReference() {
			if (type == element_o)
				return true;
			return isReference;
		}

		public void setIsReference(boolean isReference) {
			try {
				if (type == element_o)
					isReference = true;

				if (this.isReference == isReference)
					return;
				if (isInherited())
					throw new MetaException("Is inherited field");

				notifyChange(MetaChangeType.REFERENCE_MODE, isReference, this.isReference);

				SetReferenceModeFieldAction action = new SetReferenceModeFieldAction(this, isReference);
				action.execute();

			} catch (MetaException e) {
				throw new RuntimeException(e);
			}

		}
	}

	protected class ElementObjectValue extends ElementObject {

		protected ElementObject object;

		protected int cloneMode;

		public ElementObjectValue(ElementObject object, int cloneMode) {
			super(null, "Value", object, null);
			this.object = object;
			this.cloneMode = cloneMode;

			if (cloneMode == 1)
				object.addParentedElement(this);

			Iterator<Element> it = object.doGetElements().iterator();
			while (it.hasNext()) {
				Element field = it.next();
				if ((field.getElementType() == MetaElementType.FIELD)
						&& (field.getSection() == MetaElementSection.PUBLIC)) {
					ElementFieldValue valueField = new ElementFieldValue(this, (ElementField) field);
					doAddField(valueField);
				}
			}

		}

		public void clearValue() {

			if (cloneMode == 1)
				object.removeParentedElement(this);

			Iterator<Element> it = doGetElements().iterator();
			while (it.hasNext()) {
				Element field = it.next();
				if (field.getElementType() == MetaElementType.FIELD) {
					((ElementFieldValue) field).clearValue();
				}
			}
		}

		public void loadValue(MetaObject object) throws MetaException {
			Iterator<MetaField> it = object.getFields().iterator();
			while (it.hasNext()) {
				MetaField field = it.next();
				if ((field.getSection() == MetaElementSection.PUBLIC)) {
					getField(field.getName()).setValue(field.getValue());
				}
			}
		}

		protected void doAddField(ElementFieldValue value) {
			doGetElements().add(value);
		}

		public Object createValue() throws MetaException {
			return object.createValue();
		}

		protected Element doAddElement(Element value) throws MetaException {
			return value;

		}

		protected void doRemoveElement(Element value) throws MetaException {

		}

		public void doSetName(String name) throws MetaException {

		}

		public void doSetParent(Element owner) throws MetaException {

		}

		public void doSetType(Element type) throws MetaException {

		}

		public void doSetSection(MetaElementSection section) {

		}

		public boolean isValue() {
			return true;
		}

		public boolean isInherited() {
			return true;
		}

		public boolean hasDefaultField() {
			return ((ElementObject) doGetType()).hasDefaultField();
		}

		public void setDefaultField(MetaField field) throws MetaException {

		}

		public String getDefaultFieldName() {
			return ((ElementObject) doGetType()).getDefaultFieldName();
		}

		public void setDefaultField(String name) throws MetaException {

		}

	}

	protected class ElementFieldValue extends ElementField {

		protected ElementObjectValue object;

		protected ElementField field;

		protected Object value;

		public ElementFieldValue(ElementObjectValue object, ElementField field) {
			super(object, field.name, field.type, null);
			this.object = object;
			this.field = field;
			if (object.cloneMode == 1)
				field.type.addParentedElement(this);
		}

		protected int getCloneMode() {
			return object.cloneMode;
		}

		protected Object doGetValue() {
			return value;
		}

		protected void doSetValue(Object value) throws MetaException {
			this.value = value;
		}

		protected void clearValue() {
			if (object.cloneMode == 1)
				field.type.removeParentedElement(this);
			clearValue(doGetValue());
		}

		public boolean isArray() {
			return field.isArray();
		}

		public boolean isInheritedValue() {
			return field.isInheritedValue();
		}

		public boolean isInherited() {
			return true;
		}

		protected Element doAddElement(Element value) throws MetaException {
			return value;
		}

		protected void doRemoveElement(Element value) throws MetaException {

		}

		public void doSetName(String name) throws MetaException {

		}

		public void doSetParent(Element owner) throws MetaException {

		}

		public void doSetType(Element type) throws MetaException {

		}

		public void doSetSection(MetaElementSection section) {

		}

		public void setIsDefault(boolean isDefault) {

		}

		public void setArrayMode(boolean isArray) {

		}

		public void setIsReference(boolean isReference) {

		}
	}

	protected class ElementAnnotation extends Element implements MetaAnnotation {

		public ElementAnnotation(Element owner, String name, Element type, MetaElementSection section) {
			super(owner, name, type, section);
			elementType = MetaElementType.ANNOTATION;
		}

		public boolean checkConnectElement(Element element) {
			return ((element.elementType == MetaElementType.ANNOTATION) || (element.elementType == MetaElementType.FIELD))
					&& (element.section == MetaElementSection.PUBLIC);
		}

		public MetaField getField(String name) throws MetaException {
			return (MetaField) getElementP(name, MetaElementType.FIELD);
		}

		@SuppressWarnings("unchecked")
		public List<MetaField> getFields() {
			return new ElementList<MetaField>(this, MetaElementType.FIELD);
		}

		public boolean hasFieldName(String name) {
			return hasElementName(name, MetaElementType.FIELD);
		}

		public boolean hasFields() {
			return doHasElements(MetaElementType.FIELD);
		}

		public MetaField getDefaultField() {
			if (hasDefaultField())
				return (MetaField) doGetElement(((ElementObject) doGetType()).getDefaultFieldName());
			else
				return null;
		}

		public boolean hasDefaultField() {
			return ((ElementObject) doGetType()).hasDefaultField();
		}

		public String getDefaultFieldName() {

			return ((ElementObject) doGetType()).getDefaultFieldName();
		}

		public MetaField findField(String name) {
			return (MetaField) doGetElement(name.split("\\."), MetaElementType.FIELD);
		}

	}

	protected class ElementModel extends ElementObject implements MetaModel {

		public ElementModel(Element owner, String name, Element type, MetaElementSection section) {
			super(owner, name, type, section);
			elementType = MetaElementType.MODEL;
		}

		public MetaModel addModel(MetaModel value) throws MetaException {
			// TODO Auto-generated method stub
			return null;
		}

		public MetaModel addModel() throws MetaException {
			return (MetaModel) doAddElement(null, MetaElementType.MODEL, null, null);
		}

		public MetaModel addModel(String name) throws MetaException {
			return (MetaModel) doAddElement(name, MetaElementType.MODEL, null, null);
		}

		public MetaModel addModel(String name, MetaObject type) throws MetaException {
			if (type == null)
				return (MetaModel) doAddElement(name, MetaElementType.MODEL, null, null);
			else
				return (MetaModel) doAddElement(name, MetaElementType.MODEL, doGetSpace().doGetElement(
						type.getFullName()), null);
		}

		public MetaModel getModel(String name) throws MetaException {
			return (MetaModel) getElementP(name, MetaElementType.MODEL);
		}

		@SuppressWarnings("unchecked")
		public List<MetaModel> getModels() {
			return new ElementList<MetaModel>(this, MetaElementType.MODEL);
		}

		public boolean hasModelName(String name) {
			return hasElementName(name, MetaElementType.MODEL);
		}

		public boolean hasModels() {
			return doHasElements(MetaElementType.MODEL);
		}

		public void removeModel(MetaModel value) throws MetaException {
			removeModel(value.getName());
		}

		public void removeModel(String name) throws MetaException {
			doRemoveElement(getElementP(name, MetaElementType.MODEL));
		}

		public MetaModel findModel(String name) {
			return (MetaModel) doGetElement(name.split("\\."), MetaElementType.MODEL);
		}

	}

	protected ArrayList<ElementValue> values = new ArrayList<ElementValue>();

	protected Element root = new Element();

	protected Element element_o;

	protected PrintStream logStream;

	protected MetaModelResolver modelResolver;

	protected ElementChanges changes;

	private int saveMode = 2; // 0 1 2

	private int loadMode = 1; // 0 1

	private LoadModelAction1 loadModelAction;

	public void setLogStream(PrintStream logStream) {
		this.logStream = logStream;
	}

	public Element getDescriptionObjectType() {
		return root.doGetElement(DESCRIPTION_ELEMENT_NAME);
	}

	protected Element doGetElement(String name) throws MetaException {
		return root.getElementP(name);
	}

	protected Element doGetElementN(String name) {
		return root.doGetElement(name.split("\\."));
	}

	public void addLog(String text) {
		if (logStream != null) {
			logStream.println(text);
		}
	}

	public String convertFullName(String parentName, String parentOwnerName, String ownerName) {

		return ownerName + parentName.substring(parentOwnerName.length());
	}

	protected void doGetNames(Element element, List<String> list, String path) {
		if (element.hasElements()) {
			Iterator<Element> it = element.doGetElements().iterator();
			while (it.hasNext()) {
				Element value = it.next();
				list.add(path + value.getName());
				doGetNames(value, list, path + value.getName() + ".");
			}

		}
	}

	public List<String> getNames() {
		List<String> list = new ArrayList<String>();
		doGetNames(root, list, "");
		return list;

	}

	public MetaElement getElement(String name) throws MetaException {
		return doGetElement(name);
	}

	protected void doGetElements(Element element, List<MetaElement> list, MetaElementType elementType) {
		if (element.hasElements()) {
			Iterator<Element> it = element.doGetElements().iterator();
			while (it.hasNext()) {
				Element value = it.next();
				if ((elementType == null) || (value.elementType == elementType))
					list.add(value);
				doGetElements(value, list, elementType);
			}

		}
	}

	public List<MetaElement> getElements() {
		return getElements(null);
	}

	public List<MetaElement> getElements(MetaElementType elementType) {
		List<MetaElement> list = new ArrayList<MetaElement>();
		doGetElements(root, list, elementType);
		return list;
	}

	public boolean hasElementName(String name) {
		return root.hasElementName(name);
	}

	public boolean hasElements() {
		return root.hasElements();
	}

	public MetaModel addModel(MetaModel value) throws MetaException {
		// TODO Auto-generated method stub
		return null;
	}

	public MetaModel addModel() throws MetaException {
		return (MetaModel) root.doAddElement(null, MetaElementType.MODEL, null, null);
	}

	public MetaModel addModel(String name) throws MetaException {
		return (MetaModel) root.doAddElement(name, MetaElementType.MODEL, null, null);
	}

	public MetaModel addModel(String name, MetaObject type) throws MetaException {
		if (type == null)
			return (MetaModel) root.doAddElement(name, MetaElementType.MODEL, null, null);
		else
			return (MetaModel) root.doAddElement(name, MetaElementType.MODEL, doGetElement(type.getFullName()), null);
	}

	public void removeModel(String name) throws MetaException {
		// TODO Auto-generated method stub
		root.removeElement(name);
	}

	public void removeModel(MetaModel value) throws MetaException {
		// TODO Auto-generated method stub
		root.removeElement(doGetElement(value.getFullName()));
	}

	public MetaModel getModel(String name) throws MetaException {
		return (MetaModel) root.getElementP(name, MetaElementType.MODEL);
	}

	@SuppressWarnings("unchecked")
	public List<MetaModel> getModels() {
		return new ElementList<MetaModel>(root, MetaElementType.MODEL);
	}

	public boolean hasModelName(String name) {
		return root.hasElementName(name, MetaElementType.MODEL);
	}

	public boolean hasModels() {
		return root.doHasElements();
	}

	private boolean checkStdAttributeName(String name, int parentNodeMode) {
		if (parentNodeMode == 1)
			return name.equals("value") || name.equals("default") || name.equals("section");
		return name.equals("name") || name.equals("type") || name.equals("value") || name.equals("default")
				|| name.equals("section");
	}

	private boolean checkStdElementNodeName(String name) {
		return name.equals("model") || name.equals("object") || name.equals("annotation") || name.equals("field");
	}

	public void saveModel(MetaModel model, java.io.OutputStream stream) throws MetaException {
		CSMWriter writer = new CSMWriter();
		writer.setSaveMode(saveMode);
		writer.saveModel(model, stream);
	}

	public void saveModel(MetaModel model, File file) throws MetaException, FileNotFoundException {
		saveModel(model, new FileOutputStream(file));
	}

	public String saveModel(MetaModel model) throws MetaException {
		CSMWriter writer = new CSMWriter();
		writer.setSaveMode(saveMode);
		return writer.saveModel(model);
	}

	private MetaModel loadModel(org.xml.sax.InputSource source) throws MetaException {
		org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
		org.dom4j.Document document;
		try {
			document = reader.read(source);
			org.dom4j.Element node = document.getRootElement();

			String name = node.attributeValue("name");

			if (name == null)
				throw new MetaException("Error node.name == null");

			if (root.doGetElement(name.split("\\.")) != null)
				throw new MetaException("Error loadModel doGetElement(" + name + ") != null");

			if (modelResolver == null)
				modelResolver = new MetaModelResolverImpl();
			if (loadMode == 0) {
				ProcessElementAction action;
				action = new LoadModelAction(this.root, node);
				action.execute();
				return (MetaModel) action.getElement();
			} else {
				boolean b = false;
				try {
					if (loadModelAction == null) { 
					  loadModelAction = new LoadModelAction1(this.root);
					  b = true;
					}
					Element element = loadModelAction.processModelNode(node);
					
					if (b) {
						loadModelAction.execute();
					} else
						loadModelAction.processLinks();
					
					return (MetaModel) element;
				} finally {
					if (b) {
						loadModelAction = null;
					}
					
				}
			}	
		} catch (org.dom4j.DocumentException e) {
			throw new MetaException(e);
		}
	}

	public MetaModel loadModel(String text) throws MetaException {
		return loadModel(new org.xml.sax.InputSource(new StringReader(text)));
	}

	public MetaModel loadModel(InputStream stream) throws MetaException {
		return loadModel(new org.xml.sax.InputSource(stream));
	}

	public MetaModel loadModel(File file) throws MetaException, FileNotFoundException {
		return loadModel(new FileInputStream(file));
	}

	protected void loadDefinitions(Element root) {
		element_o = createElement(root, "object", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element_o);

		Element element_s = createElement(root, "string", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element_s);

		Element element = createElement(root, "datetime", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = createElement(root, "integer", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = createElement(root, "double", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = createElement(root, "float", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = createElement(root, "long", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = createElement(root, "boolean", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);
		
		element = createElement(root, "byte", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		element = (ElementObject) createElement(root, "description", MetaElementType.OBJECT, null, null);
		root.doGetElements().add(element);

		ElementField element_f = (ElementField) createElement(element, "text", MetaElementType.FIELD, element_s, null);
		element.doGetElements().add(element_f);
		element_f.setIsDefault(true);

	}

	public Element createElement(Element owner, String name, MetaElementType elementType, Element type,
			MetaElementSection section) {
		switch (elementType) {
		case ANNOTATION:
			return new ElementAnnotation(owner, name, type, section);
		case FIELD:
			if ((owner != null) && (owner instanceof ElementObjectValue))
				return new ElementFieldValue((ElementObjectValue) owner, (ElementField) owner.type.doGetElement(name));
			else
				return new ElementField(owner, name, type, section);
		case MODEL:
			return new ElementModel(owner, name, type, section);
		case OBJECT:
			return new ElementObject(owner, name, type, section);
		default:
			return null;
		}
	}

	public MetaModelResolver getModelResolver() {

		return modelResolver;
	}

	public void setModelResolver(MetaModelResolver modelResolver) {
		this.modelResolver = modelResolver;

	}

	public void endChanges() {
		if (changes != null) {
			changes.changeLevel--;
			if (changes.changeLevel == 0) {
				processChanges();
				changes.items.clear();
				changes = null;
			}
		}
	}

	private void processChanges() {

		Iterator<ElementChangeInfo> it = changes.items.iterator();
		while (it.hasNext()) {
			ElementChangeInfo info = it.next();
			info.processListeners();
		}

	}

	public void addChange(ElementChangeInfo info) {
		changes.items.add(info);

	}

	public void beginChanges() {
		if (changes == null)
			changes = new ElementChanges();
		changes.changeLevel++;
	}

	public void addListener(MetaChangeListener listener) {
		root.addListener(listener);

	}

	public List<MetaChangeListener> getListeners() {

		return root.getListeners();
	}

	public boolean hasListeners() {

		return root.hasListeners();
	}

	public boolean removeListener(MetaChangeListener listener) {

		return root.removeListener(listener);
	}

	public void printElement(MetaElement element, String printSpace) throws MetaException {
		doPrintElement(doGetElement(element.getFullName()), printSpace);
	}

	public void doPrintValue(Object value, String printSpace, String promt) throws MetaException {
		if (value == null) {
			System.out.println(printSpace + promt + "null");
			return;
		}
		if (value.getClass().isArray()) {
			Object[] values = (Object[]) value;
			System.out.println(printSpace + promt + "array[" + values.length + "]");
			for (int i = 0; i < values.length; i++) {
				doPrintValue(values[i], printSpace + "  ", "");
			}
		} else {
			if (value instanceof MetaObject) {
				MetaObject object = (MetaObject) value;
				if (object.isValue()) {
					System.out.println(printSpace + promt + "Object - " + object.getType().getFullName());

					Iterator<MetaField> it = object.getFields().iterator();

					while (it.hasNext()) {
						MetaField field = it.next();
						System.out.println(printSpace + "  Field - " + field.getName());
						if (!field.isNull()) {
							doPrintValue(field.getValue(), printSpace + "    ", "");
						}
					}
				} else
					System.out.println(printSpace + promt + "Object - " + object.getFullName());
			} else {
				System.out.println(printSpace + promt + value.getClass().getSimpleName() + " = "
						+ ((value instanceof String) ? ("\"" + value + "\"") : value));
			}
		}
	}

	public void doPrintElement(Element element, String printSpace) throws MetaException {
		String inh = "";
		String tl = "";

		if (element.isInherited()) {
			inh = "inherited ";
		}

		tl = " (" + ((MetaLogInfo) element).getInfoText() + ")";

		if (element.hasType())
			System.out.println(printSpace + inh + element.getElementType().getTemplate() + " \"" + element.getName()
					+ "\" : " + element.getType().getFullName()
					+ ((element instanceof ElementField) && (((ElementField) element).isArray()) ? "[]" : "") + tl);
		else
			System.out.println(printSpace + inh + element.getElementType().getTemplate() + " \"" + element.getName()
					+ "\"" + tl);

		if (element.getElementType() == MetaElementType.FIELD) {
			MetaField field = (MetaField) element;
			if (!field.isNull()) {
				if (field.isInheritedValue())
					inh = "inherited ";
				else
					inh = "";

				// System.out.println(printSpace + "  " + inh + "Value \"" +
				// field.getValue() + "\"");
				doPrintValue(field.getValue(), printSpace + "  ", inh + "Value: ");

			}
		}

		if (element.hasElements()) {
			if (element.getElements().size() > 2)
				System.out.println(printSpace + "    count - " + element.getElements().size());

			Iterator<Element> et = element.doGetElements().iterator();

			List<String> strl = new ArrayList<String>();
			while (et.hasNext()) {
				strl.add(et.next().getName());
			}

			Collections.sort(strl);

			Iterator<String> ets = strl.iterator();

			while (ets.hasNext()) {
				printElement(element.doGetElement(ets.next()), printSpace + "    ");
			}

		}
	}

	public void checkElementName(String name) throws MetaException {
		if ((name == null) || (name.equals("")))
			throw new MetaException("Name is empty");
		Integer pos = checkElementNameI(name);
		if (!(pos < 0))
			throw new MetaException("Error name = [" + name + "] pos " + pos.toString());
	}

	public boolean checkElementNameB(String name) {
		return checkElementNameI(name) < 0;
	}

	public int checkElementNameI(String name) {

		if ((name == null) || (name.equals("")))
			return 0;

		Pattern p = Pattern.compile("[a-z][a-z\\d\\u005F]*");
		name = name.toLowerCase();
		Matcher m = p.matcher(name);
		if (m.matches())
			return -1;
		p = Pattern.compile("[a-z]");
		m = p.matcher(name.substring(0, 1));
		if (!m.matches())
			return 0;

		p = Pattern.compile("[a-z\\d\\u005F]");
		for (int i = 1; i < name.length(); i++) {
			m = p.matcher(name.substring(i, i + 1));
			if (!m.matches())
				return i;
		}

		return 0;
	}

	public String generateName() {
		return root.generateName();
	}

	public String generateName(String template) {
		return root.generateName(template);
	}

	public String generateName(MetaElementType elementType) {
		return root.generateName(elementType);
	}

	public MetaElement findElement(String name) {
		return root.doGetElement(name.split("\\."));
	}

	public MetaModel findModel(String name) {
		return (MetaModel) root.doGetElement(name.split("\\."), MetaElementType.MODEL);
	}

	public int getSaveMode() {

		return saveMode;
	}

	public void setSaveMode(int mode) {
		saveMode = mode;

	}

	public void clear() {
		Element rootOld = root;
		root = new Element();
		root.listeners = rootOld.listeners;
		rootOld.clear();
	}

}
