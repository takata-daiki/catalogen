/*
 * Copyright (c) 2012.
 * This is part of the project "PrometaJava"
 * from Sven Ruppert for infotraX GmbH, please contact chef@sven-ruppert.de
 */

package org.rapidpm.data.table.annotation;

import java.lang.annotation.*;

/**
 * Sven Ruppert
 *
 * @author svenruppert
 * @version This Source Code is part of the www.svenruppert.de project.
 *          please contact sven.ruppert@me.com
 * @since 02.11.2009
 *        Time: 17:56:02
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellFormatter {

    Class value();

}
