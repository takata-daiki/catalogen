/*
 * BEGIN_HEADER - DO NOT EDIT
 * 
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://open-esb.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * https://open-esb.dev.java.net/public/CDDLv1.0.html.
 * If applicable add the following below this CDDL HEADER,
 * with the fields enclosed by brackets "[]" replaced with
 * your own identifying information: Portions Copyright
 * [year] [name of copyright owner]
 */

/*
 * @(#)Intercept.java - Last published on 3/13/2008
 *
 * Copyright 2008 Sun Microsystems, Inc. All Rights Reserved.
 * 
 *
 * END_HEADER - DO NOT EDIT
 */

package com.sun.jbi.interceptors;


/**
 * Annotation for message and message exchange interception.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercept      
{
    /**
     * Speciying the name property allows for a user defined interceptor name
     * @return
     */
    String name() default "";
    
    /**
     * Speciying the consumer property allows you to intercept exchanges
     * based on the consuming component's name.
     * @return
     */
    String consumer() default "";
    /**
     * Speciying the provider property allows you to intercept exchanges
     * based on the providing component's name.
     * @return
     */
    String provider() default "";
    /**
     * Speciying the message property allows you to intercept specific messages
     * within an exchange, e.g. {"in", "out", "fault"}
     * @return
     */
    String message()  default "";
    
    /**
     * Specifying the service property allows you to intercept exchanges
     * for a specific service.
     * @return
     */
    String service() default "";
    
    /**
     * Specifying the endpoint property allows you to intercept exchanges
     * for a specific endpoint.
     * @return
     */
    String endpoint() default "";    
    
    /**
     * Specifying the service property allows you to intercept exchanges
     * with a specific status.
     * @return
     */
    String status() default "";
    
    /**
     * Priority allows you to order multiple, related interceptors into an 
     * ordered chain.
     * @return
     */
    int priority() default 100;
    
    /**
     * Interceptor type, this is an optional parameter, useful for creating 
     * aspects.
     */
     String type() default "";
}
