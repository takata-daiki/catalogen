/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.metadata.parser.jsp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.as.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.as.metadata.parser.util.MetaDataElementParser;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.web.spec.FunctionMetaData;
import org.jboss.metadata.web.spec.TldExtensionMetaData;

/**
 * @author Remy Maucherat
 */
public class FunctionMetaDataParser extends MetaDataElementParser {

    public static FunctionMetaData parse(XMLStreamReader reader) throws XMLStreamException {
        FunctionMetaData function = new FunctionMetaData();

        // Handle attributes
        final int count = reader.getAttributeCount();
        for (int i = 0; i < count; i ++) {
            final String value = reader.getAttributeValue(i);
            if (reader.getAttributeNamespace(i) != null) {
                continue;
            }
            final Attribute attribute = Attribute.forName(reader.getAttributeLocalName(i));
            switch (attribute) {
                case ID: {
                    function.setId(value);
                    break;
                }
                default: throw unexpectedAttribute(reader, i);
            }
        }

        DescriptionGroupMetaData descriptionGroup = new DescriptionGroupMetaData();
        // Handle elements
        while (reader.hasNext() && reader.nextTag() != END_ELEMENT) {
            if (DescriptionGroupMetaDataParser.parse(reader, descriptionGroup)) {
                if (function.getDescriptionGroup() == null) {
                    function.setDescriptionGroup(descriptionGroup);
                }
                continue;
            }
            final Element element = Element.forName(reader.getLocalName());
            switch (element) {
                case NAME:
                    function.setName(reader.getElementText());
                    break;
                case FUNCTION_CLASS:
                    function.setFunctionClass(reader.getElementText());
                    break;
                case FUNCTION_SIGNATURE:
                    function.setFunctionSignature(reader.getElementText());
                    break;
                case EXAMPLE:
                    List<String> examples = function.getExamples();
                    if (examples == null) {
                        examples = new ArrayList<String>();
                        function.setExamples(examples);
                    }
                    examples.add(reader.getElementText());
                    break;
                case FUNCTION_EXTENSION:
                    List<TldExtensionMetaData> extensions = function.getFunctionExtensions();
                    if (extensions == null) {
                        extensions = new ArrayList<TldExtensionMetaData>();
                        function.setFunctionExtensions(extensions);
                    }
                    extensions.add(TldExtensionMetaDataParser.parse(reader));
                    break;
                default: throw unexpectedElement(reader);
            }
        }

        return function;
    }

}
