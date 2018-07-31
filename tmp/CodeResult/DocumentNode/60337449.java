/* Copyright (c) 2011, RedIRIS. All Rights Reserved.
 * Code authored by
 * 	 Antonio David Prez Morales <antonio.perez@rediris.es>
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * This file is licensed to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. ĘYou may obtain a copy of the License at
 * 
 * ĘĘĘhttp://www.apache.org/licenses/LICENSE-2.0
 * 
 * Software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.geant.gembus.security.api.ws.trust;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.geant.gembus.security.core.util.JAXBUtil;
import net.geant.gembus.security.core.ws.trust.BaseRequestSecurityToken;
import net.geant.gembus.security.core.ws.trust.BaseRequestSecurityTokenResponse;
import net.geant.gembus.security.core.ws.trust.RequestSecurityToken;
import net.geant.gembus.security.core.ws.trust.RequestSecurityTokenResponse;
import net.geant.gembus.security.core.ws.trust.RequestSecurityTokenResponseCollection;
import net.geant.gembus.security.ws.trust.ObjectFactory;
import net.geant.gembus.security.ws.trust.RequestSecurityTokenResponseType;
import net.geant.gembus.security.ws.trust.RequestSecurityTokenType;
import net.geant.gembus.security.ws.wss.secext.BinarySecurityTokenType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * <p>
 * This factory implements utility methods for converting between JAXB model
 * objects and XML source.
 * </p>
 * 
 * @author <a href="mailto:antonio.perez@rediris.es">Antonio David Prez
 *         Morales</a>
 */
public class WSTrustJAXBFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(WSTrustJAXBFactory.class);

	private static final WSTrustJAXBFactory instance = new WSTrustJAXBFactory();

	private final JAXBContext jaxbcontext;

	private Marshaller marshaller;

	private Unmarshaller unmarshaller;

	private final ObjectFactory objectFactory;

	/**
	 * <p>
	 * Creates the {@code WSTrustJAXBFactory} singleton instance
	 * </p>
	 */
	private WSTrustJAXBFactory() {

		try {
			this.jaxbcontext = JAXBUtil.getJAXBContext(this.getPackages());
			this.marshaller = this.jaxbcontext.createMarshaller();
			this.marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.FALSE);
			this.unmarshaller = this.jaxbcontext.createUnmarshaller();
			this.objectFactory = new ObjectFactory();
		} catch (JAXBException jbe) {
			throw new RuntimeException(jbe.getMessage(), jbe);
		}

	}

	/**
	 * <p>
	 * Gets a reference to the singleton instance.
	 * </p>
	 * 
	 * @return a reference to the {@code WSTrustJAXBFactory} instance.
	 */
	public static WSTrustJAXBFactory getInstance() {
		return instance;
	}

	/**
	 * <p>
	 * Get the WS packages for JAXB Context
	 * </p>
	 * 
	 * @return a {@code String} containing the packages
	 */
	private String getPackages() {
		StringBuffer packages = new StringBuffer();
		packages.append("net.geant.gembus.security.ws.addressing");
		packages.append(":net.geant.gembus.security.ws.policy");
		packages.append(":net.geant.gembus.security.ws.trust");
		packages.append(":net.geant.gembus.security.ws.wss.secext");
		packages.append(":net.geant.gembus.security.ws.wss.utility");
		return packages.toString();
	}

	/**
	 * <p>
	 * Creates a {@code BaseRequestSecurityToken} from the specified XML source
	 * </p>
	 * 
	 * @param request
	 *            the XML source containing the request security token message.
	 * @return the {@code BaseRequestSecurityToken} instance. It will be an
	 *         instance of {@code RequestSecurityToken} (the message contains a
	 *         single token request), or an instance of
	 *         {@code RequestSecurityTokenCollection} (the message contains
	 *         multiple token requests)
	 */
	@SuppressWarnings("unchecked")
	public BaseRequestSecurityToken parseRequestSecurityToken(Source request) {
		/**
		 * TODO Check if request is a request security token collection or a
		 * simple request security token and parse it
		 * 
		 * Currently, only parse a simple request security token
		 */
		Node documentNode = ((DOMSource) request).getNode();
		Element documentElement = documentNode instanceof Element ? (Element) documentNode
				: documentNode instanceof Document ? (Element) ((Document) documentNode)
						.getDocumentElement() : (Element) documentNode;
		/*
		 * if the request contains a validate, cancel, or renew target, we must
		 * preserve it from JAXB unmarshalling. if the request contains a
		 * Assertion element in RequestSecurityToken extension element, we must
		 * preserve it from unmarshalling too
		 */

		/*
		 * For this, create the RequestSecurityToken object and check if exists
		 * any of those elements and replace them in the RST object by the
		 * element in the RST document
		 */
		JAXBElement<RequestSecurityTokenType> jaxbRST = null;
		try {
			jaxbRST = (JAXBElement<RequestSecurityTokenType>) this.unmarshaller
					.unmarshal(documentElement);
		} catch (JAXBException e) {
			// throw new
			// RuntimeException("Failed to unmarshall request security token",
			// e);
			throw new WSTrustException("The request was invalid or malformed",
					"wst:InvalidRequest");
		}

		RequestSecurityTokenType rstt = jaxbRST.getValue();
		RequestSecurityToken rst = new RequestSecurityToken(rstt);

		/* Check if ValidateTarget exists */
		if (rst.getValidateTarget() != null
				&& rst.getValidateTarget().getAny() != null) {
			Node n = this.findNodeByNameNS(documentElement, "ValidateTarget",
					WSTrustConstants.BASE_NAMESPACE);
			if (!(rst.getValidateTarget().getAny() instanceof JAXBElement)) {
				Element newElementNode = this.transformElement((Element) n
						.getFirstChild());
				rst.getValidateTarget().setAny(newElementNode);
			}
			documentElement.removeChild(n);
		}

		/* Check if RenewTarget exists */
		if (rst.getRenewTarget() != null
				&& rst.getRenewTarget().getAny() != null) {
			Node n = this.findNodeByNameNS(documentElement, "RenewTarget",
					WSTrustConstants.BASE_NAMESPACE);
			Element newElementNode = this.transformElement((Element) n
					.getFirstChild());
			rst.getRenewTarget().setAny(newElementNode);
			documentElement.removeChild(n);
		}

		/* Check if CancelTarget exists */
		if (rst.getCancelTarget() != null
				&& rst.getCancelTarget().getAny() != null) {
			Node n = this.findNodeByNameNS(documentElement, "CancelTarget",
					WSTrustConstants.BASE_NAMESPACE);
			Element newElementNode = this.transformElement((Element) n
					.getFirstChild());
			rst.getCancelTarget().setAny(newElementNode);
			documentElement.removeChild(n);
		}

		List<Object> extensionElements = rst.getExtensionElements();

		int length = extensionElements.size();
		Element element = null;
		Object tempElement = null;
		/* Set the extension elements */
		for (int i = 0; i < length; i++) {
			tempElement = extensionElements.get(i);
			if (!(tempElement instanceof JAXBElement<?>)) {
				// element = (Element) extensionElements.get(i);
				element = (Element) tempElement;
				extensionElements.remove(i);
				Node node = this.findNodeByNameNS(documentElement,
						element.getLocalName(), element.getNamespaceURI());
				Element newElementNode = this.transformElement((Element) node);
				documentElement.removeChild(node);
				extensionElements.add(newElementNode);
			}
		}

		return rst;
	}

	/**
	 * <p>
	 * Creates a {@code org.w3c.dom.Document} from the specified request object
	 * </p>
	 * 
	 * @param rst
	 *            a {@code RequestSecurityToken} representing the object model
	 *            of the request security token.
	 * @return the constructed {@code org.w3c.dom.Document} instance.
	 */
	public Document marshallRequestSecurityToken(RequestSecurityToken rst) {
		Element targetElement = null;
		/*
		 * if the request contains a validate, cancel, or renew target, we must
		 * preserve it from JAXB marshalling. if the request contains a
		 * Assertion element in RequestSecurityToken extension element, we must
		 * preserve it from marshalling too
		 */

		/*
		 * For this, create the RequestSecurityToken document and check if
		 * exists any of those elements and replace them in the RST document by
		 * the element in the RST object
		 */
		String requestType = rst.getRequestType().toString();
		if (requestType.equalsIgnoreCase(WSTrustConstants.VALIDATE_REQUEST)) {
			targetElement = (Element) rst.getValidateTarget().getAny();
			rst.getValidateTarget().setAny(null);
		} else if (requestType.equalsIgnoreCase(WSTrustConstants.RENEW_REQUEST)) {
			targetElement = (Element) rst.getRenewTarget().getAny();
			rst.getRenewTarget().setAny(null);
		} else if (requestType
				.equalsIgnoreCase(WSTrustConstants.CANCEL_REQUEST)) {
			targetElement = (Element) rst.getCancelTarget().getAny();
			rst.getCancelTarget().setAny(null);
		}

		List<Object> extensionElements = rst.getExtensionElements();
		Document result = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setNamespaceAware(true);
			factory.setXIncludeAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			result = builder.newDocument();
			this.marshaller.marshal(this.objectFactory
					.createRequestSecurityToken(rst.getDelegated()), result);

			// insert the original target in the appropriate element.
			if (targetElement != null) {
				Node node = null;
				if (requestType
						.equalsIgnoreCase(WSTrustConstants.VALIDATE_REQUEST))
					node = this.findNodeByNameNS(result.getDocumentElement(),
							"ValidateTarget", WSTrustConstants.BASE_NAMESPACE);
				else if (requestType
						.equalsIgnoreCase(WSTrustConstants.RENEW_REQUEST))
					node = this.findNodeByNameNS(result.getDocumentElement(),
							"RenewTarget", WSTrustConstants.BASE_NAMESPACE);
				else if (requestType
						.equalsIgnoreCase(WSTrustConstants.CANCEL_REQUEST))
					node = this.findNodeByNameNS(result.getDocumentElement(),
							"CancelTarget", WSTrustConstants.BASE_NAMESPACE);

				node.appendChild(result.importNode(targetElement, true));

			}

			/* set the extension elements */
			int length = extensionElements.size();

			/*
			 * Delete the nodes contained in extensionElements from the RST
			 * Document
			 */
			for (int i = 0; i < length; i++) {
				Element element = (Element) extensionElements.get(i);
				Node node = this.findNodeByNameNS(result.getDocumentElement(),
						element.getLocalName(), element.getNamespaceURI());
				result.removeChild(node);
			}

			/* Add the nodes contained in extensionElements in the RST Document */
			for (int i = 0; i < length; i++) {
				Element element = (Element) extensionElements.get(i);
				result.appendChild(result.adoptNode(element));
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Failed to marshall security token request", e);
		}

		return result;
	}

	/**
	 * <p>
	 * Creates a {@code javax.xml.transform.Source} from the specified request
	 * object
	 * </p>
	 * 
	 * @param rst
	 *            a {@code RequestSecurityToken} representing the object model
	 *            of the request security token.
	 * @return the constructed {@code javax.xml.transform.Source} instance.
	 */
	public Source marshallRequestSecurityTokenToSource(RequestSecurityToken rst) {
		Document documentRST = this.marshallRequestSecurityToken(rst);
		return new DOMSource(documentRST);
	}

	/**
	 * <p>
	 * Creates a {@code org.w3c.dom.Document} from the specified request object
	 * </p>
	 * 
	 * @param rstc
	 *            a {@code RequestSecurityTokenCollection} representing the
	 *            object model of the request security token collection.
	 * @return the constructed {@code org.w3c.dom.Document} instance.
	 */
	public Document marshallRequestSecurityTokenCollection(
			RequestSecurityToken rstc) {
		return null;
	}

	/**
	 * <p>
	 * Creates a {@code javax.xml.transform.Source} from the specified request
	 * object
	 * </p>
	 * 
	 * @param rst
	 *            a {@code RequestSecurityTokenCollection} representing the
	 *            object model of the request security token collection.
	 * @return the constructed {@code javax.xml.transform.Source} instance.
	 */
	public Source marshallRequestSecurityTokenCollectionToSource(
			RequestSecurityToken rstc) {
		return null;
	}

	/**
	 * <p>
	 * Creates a {@code BaseRequestSecurityToken} from the specified XML source
	 * </p>
	 * 
	 * @param response
	 *            the XML source containing the request security token message.
	 * @return the {@code BaseRequestSecurityToken} instance. It will be an
	 *         instance of {@code RequestSecurityTokenResponse} (the message
	 *         contains a single token response), or an instance of
	 *         {@code RequestSecurityTokenResponseCollection} (the message
	 *         contains multiple token response)
	 */
	@SuppressWarnings("unchecked")
	public BaseRequestSecurityTokenResponse parseRequestSecurityTokenResponse(
			Source response) {
		/**
		 * TODO Check if request is a request security token response collection
		 * or a simple request security token response and parse it
		 * 
		 * Currently, only parse a simple request security token response
		 */
		Node documentNode = ((DOMSource) response).getNode();
		Element documentElement = documentNode instanceof Element ? (Element) documentNode
				: documentNode instanceof Document ? (Element) ((Document) documentNode)
						.getDocumentElement() : (Element) documentNode;
		/*
		 * if the response contains an issued token, we must preserve it from
		 * the JAXB unmarshalling.
		 * 
		 * For this, create the RequestSecurityTokenResponse object and check if
		 * exists the RequestedSecurityToken element and replace it in the RSTR
		 * object by the element in the RSTR document
		 */
		Element tokenElement = null;

		JAXBElement<RequestSecurityTokenResponseType> jaxbRSTR = null;
		try {
			jaxbRSTR = (JAXBElement<RequestSecurityTokenResponseType>) this.unmarshaller
					.unmarshal(documentElement);
		} catch (JAXBException e) {
			throw new RuntimeException(
					"Failed to unmarshall request security token response", e);
		}

		RequestSecurityTokenResponseType rstrt = jaxbRSTR.getValue();
		RequestSecurityTokenResponse rstr = new RequestSecurityTokenResponse(
				rstrt);

		Node requestedTokenNode = this.findNodeByNameNS(documentElement,
				"RequestedSecurityToken", WSTrustConstants.BASE_NAMESPACE);
		if (requestedTokenNode != null)
			tokenElement = (Element) requestedTokenNode.getFirstChild();

		// insert the security token in the parsed response.
		if (tokenElement != null) {
			rstr.getRequestedSecurityToken().setAny(tokenElement);
		}

		return rstr;
	}

/**
	 * <p> Creates a {@code org.w3c.dom.Document} from the specified response
	 * object</p>
	 * 
	 * @param response
	 *            a {@code RequestSecurityTokenResponse representing
	 *            the object model of the security token response.
	 * @return the constructed {@code org.w3c.dom.Document} instance.
	 */
	public Document marshallRequestSecurityTokenResponse(
			RequestSecurityTokenResponse rstr) {

		// if the response contains an issued token, we must preserve it from
		// the JAXB marshaling.
		Element tokenElement = null;

		if (rstr.getRequestedSecurityToken() != null) {
			tokenElement = (Element) rstr.getRequestedSecurityToken().getAny();

			// Don't marshall any token - it will be inserted in the
			// DOM document later.
			rstr.getRequestedSecurityToken().setAny(null);
		}

		Document result = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			result = dbf.newDocumentBuilder().newDocument();
			// marshall the response to a document and insert the issued token
			// on the resultant document.

			this.marshaller.marshal(this.objectFactory
					.createRequestSecurityTokenResponse(rstr.getDelegated()),
					result);

			// Insert the token in the appropriate element of the response
			// document
			if (tokenElement != null) {
				Node node = this.findNodeByNameNS(result.getDocumentElement(),
						"RequestedSecurityToken",
						WSTrustConstants.BASE_NAMESPACE);
				node.appendChild(result.importNode(tokenElement, true));
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Failed to marshall request security token response", e);
		}
		return result;
	}

/**
	 * <p> Creates a {@code javax.xml.transform.Source} from the specified response
	 * object</p>
	 * 
	 * @param rstr
	 *            a {@code RequestSecurityTokenResponse representing
	 *            the object model of the security token response.
	 * @return the constructed {@code javax.xml.transform.Source} instance.
	 */
	public Source marshallRequestSecurityTokenResponseToSource(
			RequestSecurityTokenResponse rstr) {
		Document documentRSTR = this.marshallRequestSecurityTokenResponse(rstr);
		return new DOMSource(documentRSTR);
	}

	/**
	 * <p>
	 * Creates a {@code org.w3c.dom.Document} from the specified request object
	 * </p>
	 * 
	 * @param rstc
	 *            a {@code RequestSecurityTokenResponseCollection} representing
	 *            the object model of the request security token collection.
	 * @return the constructed {@code org.w3c.dom.Document} instance.
	 */
	public Document marshallRequestSecurityTokenResponseCollection(
			RequestSecurityTokenResponseCollection rstrc) {
		return null;
	}

	/**
	 * <p>
	 * Creates a {@code javax.xml.transform.Source} from the specified request
	 * object
	 * </p>
	 * 
	 * @param rst
	 *            a {@code RequestSecurityTokenResponseCollection} representing
	 *            the object model of the request security token collection.
	 * @return the constructed {@code javax.xml.transform.Source} instance.
	 */
	public Source marshallRequestSecurityTokenResponseCollectionToSource(
			RequestSecurityTokenResponseCollection rstrc) {
		return null;
	}

	/**
	 * <p>
	 * Finds a node that matches the specified name and namespace in the
	 * specified document
	 * </p>
	 * 
	 * @param documentNode
	 *            the {@code Node} instance representing the document upon which
	 *            the search is made.
	 * @param localName
	 *            a {@code String} containing the local name of the searched
	 *            node.
	 * @param namespace
	 *            a {@code String} containing the namespace of the searched
	 *            node.
	 * 
	 * @return a {@code Node} representing the searched node. If more than one
	 *         node is found, the first one will be returned. If no nodes were
	 *         found according to the search parameters, then {@code null} is
	 *         returned.
	 */
	private Node findNodeByNameNS(Node documentNode, String localName,
			String namespace) {

		if (documentNode instanceof Element) {
			Element element = (Element) documentNode;
			NodeList nodeList = element.getElementsByTagNameNS(namespace,
					localName);
			if (nodeList == null || nodeList.getLength() == 0) {
				return null;
			}

			return nodeList.item(0);
		}

		else {

			NodeList nodeList = documentNode.getChildNodes();
			int length = nodeList.getLength();
			for (int i = 0; i < length; i++) {
				Node n = nodeList.item(i);
				if (n.getLocalName().equals(localName)
						&& n.getNamespaceURI().equals(namespace))
					return n;
			}
		}

		return null;
	}

	/**
	 * <p>
	 * Transforms between XML libraries with different object models
	 * (com.sun.xml.messaging.saaj.soap.impl.ElementImpl and
	 * org.apache.xerces.dom.DeferredElementNSImpl).
	 * </p>
	 * <p>
	 * To do this, this method writes the XML out to a string from one model and
	 * read it back in to the other model.
	 * </p>
	 * 
	 * @param element
	 *            a {@code org.w3c.dom.Element} to transform
	 * @return the transformed {@code org.w3c.dom.Element}
	 */
	private Element transformElement(Element element) {
		/* Transform Element to String */
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		try {
			transformer.transform(new DOMSource(element), new StreamResult(
					buffer));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		String stringElement = buffer.toString();

		/* Transform String to Element */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		InputSource is = new InputSource(new StringReader(stringElement));
		Document document = null;
		try {
			document = builder.parse(is);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document.getDocumentElement();
	}

}