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

package org.mobicents.ssf.flow.engine.builder.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.mobicents.ssf.flow.engine.builder.FlowTemplateBuilder;
import org.mobicents.ssf.flow.engine.builder.template.AbstractAnnotatedTemplate;
import org.mobicents.ssf.flow.engine.builder.template.AbstractStateTemplate;
import org.mobicents.ssf.flow.engine.builder.template.ActionListTemplate;
import org.mobicents.ssf.flow.engine.builder.template.ActionStateTemplate;
import org.mobicents.ssf.flow.engine.builder.template.ActionTemplate;
import org.mobicents.ssf.flow.engine.builder.template.AttributeTemplate;
import org.mobicents.ssf.flow.engine.builder.template.EndStateTemplate;
import org.mobicents.ssf.flow.engine.builder.template.EvaluateStateTemplate;
import org.mobicents.ssf.flow.engine.builder.template.EvaluateTemplate;
import org.mobicents.ssf.flow.engine.builder.template.FlowTemplate;
import org.mobicents.ssf.flow.engine.builder.template.PropertyTemplate;
import org.mobicents.ssf.flow.engine.builder.template.Template;
import org.mobicents.ssf.flow.engine.builder.template.TransitionTemplate;
import org.mobicents.ssf.flow.engine.builder.template.TransitionableStateTemplate;
import org.mobicents.ssf.flow.internal.SipFlowResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


@SuppressWarnings("unchecked")
public class XmlFlowTemplateBuilder implements TemplateBuilder, FlowTemplateBuilder {
    private DocumentLoader loader = new DocumentLoader();
    private Logger logger = LoggerFactory.getLogger(XmlFlowTemplateBuilder.class);
    
    private Document document;
    
    private Resource resource;
    
    private FlowTemplate flow;
    
    public XmlFlowTemplateBuilder(Resource resource) {
        this.resource = resource;
    }
    
    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.TempateBuilder#init()
     */
    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.FlowTemplateBuilder#init()
     */
    public void init() {
        try {
            // resource???????
        	logger.info(SipFlowResourceMessage.getMessage(106, resource.getURL()));
            this.document = loader.loadDocument(resource);
        } catch (Exception e) {
            resolveException(SipFlowResourceMessage.getMessage(104, resource), e);
        }
    }
    
    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.TempateBuilder#buildTemplate()
     */
    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.FlowTemplateBuilder#buildTemplate()
     */
    synchronized public void buildTemplate() {
        // XML??????FlowTemplate?????
        // ???????
        // WebFlow??Model????ArtifactFactory??????????
        // ??????????????Model??????????????????
        if(this.document == null) {
            return;
        }
        Element element = this.document.getDocumentElement();
        
        try {
            this.flow = parseFlowTemplate(element);
        } catch (Exception e) {
            resolveException(SipFlowResourceMessage.getMessage(105, element), e);
        }
    }
    
    private void resolveException(String message, Exception e) {
        if(e instanceof TemplateBuilderException) {
            throw (TemplateBuilderException)e;
        }
        throw new TemplateBuilderException(message, e);
    }
    
    private FlowTemplate parseFlowTemplate(Element element) throws IOException, ParserConfigurationException, SAXException {
        FlowTemplate flow = new FlowTemplate();
        // start-state?ID???
        flow.setStartStateId(element.getAttribute("start-state"));
        
        // state???
        // TODO ??????XML?????state??????????????????????
        List<AbstractStateTemplate> stateList = parseStateTemplateList(element);
        
        // ???????????????
        Set<AbstractStateTemplate> externalStateSet = parseExternalFile(element);
        stateList.addAll(externalStateSet);
        
        flow.setStateTemplateList(stateList);
        
        this.parseAnnotatedTemplate(flow, element);
        
        this.setupExtendsState(flow);
        
        return flow;
    }
    
    private void setupExtendsState(FlowTemplate flow) {
    	// FlowTemplate????????State????extends?????????????setup????
    	List<AbstractStateTemplate> stateList = flow.getStateTemplateList();
    	for(AbstractStateTemplate state: stateList) {
    		String extendsId = state.getExtendsId();
    		if(StringUtils.hasLength(extendsId)) {
    			
    			AbstractStateTemplate astate = flow.getStateTemplate(extendsId);
    			if(astate != null && !StringUtils.hasLength(astate.getExtendsId())) {
    				AbstractStateTemplate targetState =
    					(AbstractStateTemplate)astate.clone();
    				state.update(targetState);
    			} else {
    				throw new TemplateBuilderException("Cannot extend state.[state=" + state.getId() + "],[extends=" + extendsId + "]");
    			}
    		}
    	}
    }
    
    private Set<AbstractStateTemplate> parseExternalFile(Element element) throws IOException, ParserConfigurationException, SAXException {
        Set<AbstractStateTemplate> rv = new HashSet<AbstractStateTemplate>();
        List<Element> locations = DomUtils.getChildElementsByTagName(element, new String[] {
                "state-location"
        });
        
        Set<StateLocation> locationSet = new TreeSet<StateLocation>();
        
        // ???????????????path?priority??????????
        for(Element locationElement: locations) {
            try {
                // ???????????????
                String path = locationElement.getAttribute("path");
                // ??????????
                String s = locationElement.getAttribute("priority");
                int priority = 5;
                if(s != null && s.length() > 0) {
                    priority = Integer.parseInt(s);
                }
                
                locationSet.add(new StateLocation(path, priority));
                
            } catch (Exception e) {
                resolveException(SipFlowResourceMessage.getMessage(107, locationElement), e);
            }
        }
        
        for(StateLocation location: locationSet) {
            try {
                // ???????????????????
                logger.info(SipFlowResourceMessage.getMessage(108, location.getPath(), location.getPriority()));
                
                String path = location.getPath();
                // ??????????????????
                Resource externalResource = this.resource.createRelative(path);
                // ????????Document???????????
                Document externalDocument = this.loader.loadDocument(externalResource);
                // ???????????????
                Element externalElement = externalDocument.getDocumentElement();
                rv.addAll(parseStateTemplateList(externalElement));
            } catch (Exception e) {
                resolveException(SipFlowResourceMessage.getMessage(109, location.getPath()), e);
            }
        }
        
        return rv;
    }
    
    private static class StateLocation implements Comparable<StateLocation> {
        
        private static final int DEFAULT_PRIORITY = 5;
        private int priority = DEFAULT_PRIORITY;
        private String path;

        public StateLocation(String path, int priority) {
            this.path = path;
            this.priority = priority;
        }
        
        public String getPath() {
            return this.path;
        }
        
        public int getPriority() {
            return this.priority;
        }

        public int compareTo(StateLocation o) {
            //return this.priority - o.priority;
            int rv = o.priority - this.priority;
            if(rv == 0) {
                rv = this.path.compareTo(o.path);
            }
            return rv;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((path == null) ? 0 : path.hashCode());
            result = prime * result + priority;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final StateLocation other = (StateLocation) obj;
            if (path == null) {
                if (other.path != null)
                    return false;
            } else if (!path.equals(other.path))
                return false;
            if (priority != other.priority)
                return false;
            return true;
        }
        
        public String toString() {
            return "path=" + path + ", priority=" + priority;
        }
    }

    private List<AbstractStateTemplate> parseStateTemplateList(Element element) {
        List<Element> stateElements = DomUtils.getChildElementsByTagName(element, new String[] {
                "evaluate-state", "action-state", "end-state"
                });
        
        ArrayList<AbstractStateTemplate> list = new ArrayList<AbstractStateTemplate>();
        for(Element stateElement: stateElements) {
            try {
                AbstractStateTemplate template = parseStateTemplate(stateElement);
                if(template != null) {
                    list.add(template);
                }
            } catch (Exception e) {
                resolveException(SipFlowResourceMessage.getMessage(110, stateElement), e);
            }
        }
        return list;
        
    }

    private AbstractStateTemplate parseStateTemplate(Element element) {
    	AbstractStateTemplate state = null;
        if(DomUtils.nodeNameEquals(element, "evaluate-state")) {
        	state = parseEvaluateStateTemplate(element);
        } else if(DomUtils.nodeNameEquals(element, "action-state")) {
            state = parseActionStateTemplate(element);
        } else if(DomUtils.nodeNameEquals(element, "end-state")) {
            state = parseEndStateTemplate(element);
        }
        String extendsId = element.getAttribute("extends");
        if(extendsId != null) {
        	state.setExtendsId(extendsId);
        }

        if(state == null) {
        	logger.error(SipFlowResourceMessage.getMessage(111, element));
        	return null;
        } else {
        	return state;
        }
    }

    private AbstractStateTemplate parseEndStateTemplate(Element element) {
        EndStateTemplate state = new EndStateTemplate();
        
        // ????
        parseAnnotatedTemplate(state, element);
        
        //
        // ????Action??
        // 
        parseActionList(state, element);
        
        return state;
    }

    private AbstractStateTemplate parseActionStateTemplate(Element element) {
        ActionStateTemplate state = new ActionStateTemplate();
        
        // ????
        parseAnnotatedTemplate(state, element);
        
        // transition????
        parseTransitions(state, element);
        
        // action-list????
        parseActionList(state, element);

        return state;
    }

    private void parseActionList(AbstractStateTemplate state, Element element) {
        ActionListTemplate actionList = new ActionListTemplate();
        Element actionListElement = DomUtils.getChildElementByTagName(element, "action-list");
        
        if(actionListElement != null) {
        	this.parseAnnotatedTemplate(actionList, actionListElement);
            List<Element> actionElements = DomUtils.getChildElementsByTagName(actionListElement, "action");
            for(Element actionElement:actionElements) {
                actionList.addAction(parseAction(actionElement));
            }
            if(state instanceof ActionStateTemplate) {
            	((ActionStateTemplate)state).setActionList(actionList);
            } else if(state instanceof EndStateTemplate) {
                ((EndStateTemplate)state).setActionList(actionList);
            }
        }

        actionListElement = DomUtils.getChildElementByTagName(element, "entry-action-list");
        
        if(actionListElement != null) {
        	actionList = new ActionListTemplate();
        	this.parseAnnotatedTemplate(actionList, actionListElement);
            List<Element> actionElements = DomUtils.getChildElementsByTagName(actionListElement, "action");
            for(Element actionElement:actionElements) {
                actionList.addAction(parseAction(actionElement));
            }
            
            state.setEntryActionList(actionList);
        }

        List<Element> actionListElements = DomUtils.getChildElementsByTagName(element, "event-action-list");
        
        for(Element e: actionListElements) {
        	actionList = new ActionListTemplate();
        	this.parseAnnotatedTemplate(actionList, e);
            List<Element> actionElements = DomUtils.getChildElementsByTagName(e, "action");
            for(Element actionElement:actionElements) {
                actionList.addAction(parseAction(actionElement));
            }
                        
            state.addEventActionList(actionList);
        }

        if(state instanceof TransitionableStateTemplate) {
        	actionListElements = DomUtils.getChildElementsByTagName(element, "exit-action-list");
        	
        	for(Element e: actionListElements) {
        		actionList = new ActionListTemplate();
        		this.parseAnnotatedTemplate(actionList, e);
        		List<Element> actionElements = DomUtils.getChildElementsByTagName(e, "action");
        		for(Element actionElement:actionElements) {
        			actionList.addAction(parseAction(actionElement));
        		}
        		
        		((TransitionableStateTemplate)state).addExitActionList(actionList);
        	}
        }

    }

    private ActionTemplate parseAction(Element element) {
        ActionTemplate action = new ActionTemplate();
        action.setType(element.getAttribute("type"));
        List<Element> propertyElements = DomUtils.getChildElementsByTagName(element, "property");
        for(Element propertyElement:propertyElements) {
            action.addProperty(parsePropertyTemplate(propertyElement));
        }
        return action;
    }

    private AbstractStateTemplate parseEvaluateStateTemplate(Element element) {
        EvaluateStateTemplate state = new EvaluateStateTemplate();
        
        // ????
        parseAnnotatedTemplate(state, element);
        
        // transition????
        parseTransitions(state, element);
        
        // evaluate????
        parseEvaluateList(state, element);
        
        // entry-action-list
        parseActionList(state, element);
        
        return state;
    }
    
    private void parseEvaluateList(EvaluateStateTemplate state, Element element) {
        List<Element> evaluateElements = DomUtils.getChildElementsByTagName(element, "evaluate");
        
        if(evaluateElements.isEmpty()) {
            return;
        }
        for(Element evaluateElement:evaluateElements) {
            state.addEvaluate(parseEvaluate(evaluateElement));
        }
        
    }

    private EvaluateTemplate parseEvaluate(Element element) {
        EvaluateTemplate evaluate = new EvaluateTemplate();
        evaluate.setType(element.getAttribute("type"));
        String sessionName = element.getAttribute("session-name");
        if(sessionName != null) {
            PropertyTemplate prop = new PropertyTemplate();
            prop.setName("session-name");
            prop.setValue(sessionName);
            evaluate.addProperty(prop);
        }
        List<Element> propertyElements = DomUtils.getChildElementsByTagName(element, "property");
        for(Element propertyElement:propertyElements) {
            evaluate.addProperty(parsePropertyTemplate(propertyElement));
        }
        
        return evaluate;
    }

    private PropertyTemplate parsePropertyTemplate(Element element) {
        PropertyTemplate property = new PropertyTemplate();
        property.setName(element.getAttribute("name"));
        property.setType(element.getAttribute("type"));
        String value = element.getAttribute("value");
        if(value == null || value.trim().length() == 0) {
        	property.setValue(DomUtils.getTextValue(element));
        } else {
        	property.setValue(value);
        }
        return property;
    }

    private void parseTransitions(TransitionableStateTemplate state, Element element) {
        List<Element> transitionElements = DomUtils.getChildElementsByTagName(element, "transition");
        if(transitionElements.isEmpty()) {
            return;
        }
        
        // ????????
        for(Element transitionElement: transitionElements) {
            state.addTransition(parseTransitionTemplate(transitionElement));
        }
    }
    
    private TransitionTemplate parseTransitionTemplate(Element element) {
        TransitionTemplate template = new TransitionTemplate();
        template.setOn(element.getAttribute("on"));
        template.setTo(element.getAttribute("to"));
        template.setAction(element.getAttribute("action"));
        template.setAttributes(parseAttributes(element));
        
        return template;
    }

    private void parseAnnotatedTemplate(AbstractAnnotatedTemplate template, Element element) {
        template.setId(element.getAttribute("id"));
        template.setName(DomUtils.getChildElementValueByTagName(element, "name"));
        
        template.setDescription(DomUtils.getChildElementValueByTagName(element, "description"));
        
        template.setAttributes(parseAttributes(element));
    }

	private List<AttributeTemplate> parseAttributes(Element element) {
        List<Element> attributeElements = DomUtils.getChildElementsByTagName(element, "attribute");
        if(attributeElements.isEmpty()) {
            return null;
        }
        
        ArrayList<AttributeTemplate> attributes = new ArrayList<AttributeTemplate>();
        for(Element attr: attributeElements) {
            attributes.add(parseAttribute(attr));
        }
        return attributes;
    }
    
    private AttributeTemplate parseAttribute(Element element) {
        // ???????
        AttributeTemplate attribute = new AttributeTemplate();
        attribute.setName(element.getAttribute("name"));
        attribute.setValue(parseAttributeValue(element));
        attribute.setType(element.getAttribute("type"));
        return attribute;
    }
    
    private String parseAttributeValue(Element element) {
        if(element.hasAttribute("value")) {
            return element.getAttribute("value");
        } else {
            Element valueElement = DomUtils.getChildElementByTagName(element, "value");
            if(valueElement != null) {
                return DomUtils.getTextValue(valueElement);
            } else {
                return null;
            }
        }
    }
    
    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.FlowTemplateBuilder#getTemplate()
     */
    public Template getTemplate() {
        return createFlowTemplate(true);
    }

    /* (? Javadoc)
     * @see com.oki.sip.sf.flow.engine.builder.xml.FlowTemplateBuilder#createFlow()
     */
    public synchronized FlowTemplate createFlowTemplate(boolean isForce) {
        if(this.flow == null || isForce) {
            buildTemplate();
        }
        return this.flow;
    }
}
