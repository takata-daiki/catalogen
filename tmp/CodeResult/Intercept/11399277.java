/*
 * Copyright 2002-2009 the original author or authors.
 *
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
 */

package org.spice.web.components.interceptors;

import java.lang.annotation.*;

/**
 * Intercept annotation used for the intercepting the request which will be annotated with the @Before or @After annotation. This annotation is used to annotate the class and tells the compiler that this class is ready for interception.
 *
 * @author Karthik Rajkumar
 * @version included from the RED-CHILLIES version.
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Intercept {
}
