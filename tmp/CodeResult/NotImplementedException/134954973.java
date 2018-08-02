//Copyright (C) 2015  Novabit Informationssysteme GmbH
//
//This file is part of Nuclos.
//
//Nuclos is free software: you can redistribute it and/or modify
//it under the terms of the GNU Affero General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//Nuclos is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU Affero General Public License for more details.
//
//You should have received a copy of the GNU Affero General Public License
//along with Nuclos.  If not, see <http://www.gnu.org/licenses/>.
package org.nuclos.server.communication;

import java.util.Set;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.nuclos.api.communication.CommunicationPort;
import org.nuclos.api.communication.CommunicationPortKey;
import org.nuclos.api.context.communication.CommunicationContext;
import org.nuclos.api.context.communication.GenericNotificationContext;
import org.nuclos.api.context.communication.GenericRequestContext;
import org.nuclos.api.context.communication.InstantiableContext;
import org.nuclos.api.context.communication.NotificationContext;
import org.nuclos.api.context.communication.PhoneCallNotificationContext;
import org.nuclos.api.context.communication.PhoneCallRequestContext;
import org.nuclos.api.context.communication.RequestContext;
import org.nuclos.api.exception.BusinessException;
import org.nuclos.api.service.CommunicationService;
import org.nuclos.common.UID;
import org.nuclos.server.communication.context.impl.GenericNotificationContextImpl;
import org.nuclos.server.communication.context.impl.GenericRequestContextImpl;
import org.nuclos.server.communication.context.impl.PhoneCallNotificationContextImpl;
import org.nuclos.server.communication.context.impl.PhoneCallRequestContextImpl;
import org.nuclos.server.security.CommunicationServiceExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommunicationServiceBean implements CommunicationService {
	
	@Autowired
	private CommunicationInterfaceLocal comInterface;
	
	@Override
	public <T extends InstantiableContext> T newContextInstance(CommunicationPortKey portKey, Class<T> contextClass) throws BusinessException {
		return newContextInstance(new UID(portKey.getId()), contextClass);
	}
	
	@Override
	public <T extends InstantiableContext> T newContextInstance(CommunicationPort port, Class<T> contextClass) throws BusinessException {
		return newContextInstance(port.getId(), contextClass);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends InstantiableContext> T newContextInstance(org.nuclos.api.UID portUID, Class<T> contextClass) throws BusinessException {
		CommunicationPort port = comInterface.getPort((UID) portUID);
		if (RequestContext.class.isAssignableFrom(contextClass)) {
			Set<Class<? extends RequestContext<?>>> supportedClasses = comInterface.getSupportedRequestContextClasses((UID) portUID);
			if (!supportedClasses.contains(contextClass)) {
				throw new IllegalArgumentException("The port does not support the request context class " + contextClass.getCanonicalName());
			}
		}
		final String logClassname = port.getClass().getCanonicalName();
		if (contextClass.equals(GenericNotificationContext.class)) {
			return (T) new GenericNotificationContextImpl(port, logClassname);
		} 
		if (contextClass.equals(GenericRequestContext.class)) {
			return (T) new GenericRequestContextImpl(port, logClassname);
		} 
		
		if (contextClass.equals(PhoneCallNotificationContext.class)) {
			return (T) new PhoneCallNotificationContextImpl(port, logClassname);
		} 
		if (contextClass.equals(PhoneCallRequestContext.class)) {
			return (T) new PhoneCallRequestContextImpl(port, logClassname);
		} 
		
		throw new NotImplementedException("Uups, instantiation of context class " + contextClass + " is not implemented yet");
	}

	@Override
	public void handleNotification(NotificationContext context) throws BusinessException {
		handleCommunicationContext(context);
	}

	@Override
	public void handleResponse(RequestContext<?> context) throws BusinessException {
		if (!context.isResponse()) {
			throw new IllegalArgumentException("Context does not contain a response");
		}
		handleCommunicationContext(context);
	}
	
	@Override
	public void executeRequest(RequestContext<?> context) throws BusinessException {
		handleCommunicationContext(context);
		context.getPort().handleRequest(context);
	}
	
	private void handleCommunicationContext(CommunicationContext context) throws BusinessException {
		new CommunicationServiceExecuter(context).execute();
	}

}
