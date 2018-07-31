/*
 * InLab Software Hikari Framework
 *
 * Copyright (c) 2009, InLab Software, LLC. All rights reserved.
 * Use is subject to license terms.
 *
 * http://www.inlabsoft.com/products/hikari/license
 */
package com.inlabsoft.hikari.bcf.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.inlabsoft.hikari.bcf.Entity;

/**
 * The <code>Indirect</code> annotation is a marker that used to specify attribute of the
 * {@link Entity} that points on another {@link Entity} and requires property initialization
 * through indirect call. In other words such attribute will be populated in case when accessing it
 * transparently for application code.
 * <p>
 * The handler of <code>Indirect</code> annotation will works when only annotated attribute of
 * {@link Entity} type. Also annotated attribute must be populated and contains entity with only
 * populated entity key. Otherwise this annotation will be ignored.
 * <p>
 * Only applicable to interfaces that extended from {@link Entity}.
 *
 * @author  Andrey Ochirov
 * @version 1.0
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Indirect {

    /**
     *
     * @return
     */
    Class<?> entity() default Entity.class;

}