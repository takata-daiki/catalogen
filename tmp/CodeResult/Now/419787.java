/* =================================================================
Copyright (C) 2009 ADV/web-engineering All rights reserved.

This file is part of Mozart.

Mozart is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Mozart is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

Mozart
http://www.mozartcms.ru
================================================================= */
// -*- java -*-
// File: Now.java
//
// Created: Mon Jan 13 12:02:19 2003
//
// $Id: Now.java 1106 2009-06-03 07:32:17Z vic $
// $Name:  $
//


package ru.adv.db.filter;

import java.util.Date;

/**
 * ?????????? ??????? ?????
 *
 * @version $Revision: 1.2 $
 */
public class Now {

    public Object perform(Object value, FilterParams params) {
        return new Date();
    }

}
