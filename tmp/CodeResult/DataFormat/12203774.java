/*
 * This file is part of Cadmium.
 * Copyright (C) 2007-2010 Xavier Clerc.
 *
 * Cadmium is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cadmium is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.x9c.cadmium.kernel;

/**
 * This interface defines data format of serialized values.
 *
 * @author <a href="mailto:cadmium@x9c.fr">Xavier Clerc</a>
 * @version 1.0
 * @since 1.0
 */
public interface DataFormat {

    /** Magic number of serialized values. */
    int MAGIC_NUMBER = 0x8495A6BE;

    /** Base prefix for small block. */
    int PREFIX_SMALL_BLOCK = 0x80;

    /** Base prefix for small integer. */
    int PREFIX_SMALL_INT = 0x40;

    /** Base prefix for small string. */
    int PREFIX_SMALL_STRING = 0x20;

    /** Code of signed 8-bit integer. */
    int CODE_INT8 = 0x00;

    /** Code of signed 16-bit integer. */
    int CODE_INT16 = 0x01;

    /** Code of signed 32-bit integer. */
    int CODE_INT32 = 0x02;

    /** Code of signed 64-bit integer. */
    int CODE_INT64 = 0x03;

    /** Code for shared block referenced by unsigned 8-bit offset. */
    int CODE_SHARED8 = 0x04;

    /** Code for shared block referenced by unsigned 16-bit offset. */
    int CODE_SHARED16 = 0x05;

    /** Code for shared block referenced by unsigned 32-bit offset. */
    int CODE_SHARED32 = 0x06;

    /** Code for block (size given by unsigned 32-bit integer). */
    int CODE_BLOCK32 = 0x08;

    /** Code for block (size given by unsigned 64-bit integer). */
    int CODE_BLOCK64 = 0x13;

    /** Code for short string (length is given by unsigned 8-bit integer). */
    int CODE_STRING8 = 0x09;

    /** Code for short string (length is given by unsigned 32-bit integer). */
    int CODE_STRING32 = 0x0A;

    /** Code for double (big endian). */
    int CODE_DOUBLE_BIG = 0x0B;

    /** Code for double (little endian). */
    int CODE_DOUBLE_LITTLE = 0x0C;

    /**
     * Code for double array
     * (length given by unsigned 8-bit integer / big endian).
     */
    int CODE_DOUBLE_ARRAY8_BIG = 0x0D;

    /**
     * Code for double array
     * (length given by unsigned 8-bit integer / little endian).
     */
    int CODE_DOUBLE_ARRAY8_LITTLE = 0x0E;

    /**
     * Code for double array
     * (length given by unsigned 32-bit integer / big endian).
     */
    int CODE_DOUBLE_ARRAY32_BIG = 0x0F;

    /**
     * Code for double array
     * (length given by unsigned 32-bit integer / little endian).
     */
    int CODE_DOUBLE_ARRAY32_LITTLE = 0x07;

    /** Code for code pointer (unsigned 32-bit integer). */
    int CODE_CODEPOINTER = 0x10;

    /** Code for infix pointer (offset as unsigned 32-bit integer). */
    int CODE_INFIXPOINTER = 0x11;

    /** Code for custom block. */
    int CODE_CUSTOM = 0x12;

    /**
     * Endianness of float values. <br/>
     * This value is always the same, whether the underlying platform is big or
     * little endian, as the JVM is inherently a big-endian platform.
     */
    int ARCH_FLOAT_ENDIANNESS = 0x76543210;

    /**
     * Native double format. <br/>
     * It is equal to either <tt>CODE_DOUBLE_BIG</tt> or <tt>CODE_DOUBLE_LITTLE</tt>,
     * according to the underlying platform. It is used to marshal (externalize)
     * values in a platform-consistent way.
     */
    int CODE_DOUBLE_NATIVE =
        Misc.isBigEndianPlatform()
        ? DataFormat.CODE_DOUBLE_BIG
        : DataFormat.CODE_DOUBLE_LITTLE;

    /**
     * Native double format (for short array). <br/>
     * It is equal to either <tt>CODE_DOUBLE_ARRAY8_BIG</tt> or <tt>CODE_DOUBLE_ARRAY8_LITTLE</tt>,
     * according to the underlying platform. It is used to marshal (externalize)
     * values in a platform-consistent way.
     */
    int CODE_DOUBLE_ARRAY8_NATIVE =
        Misc.isBigEndianPlatform()
        ? DataFormat.CODE_DOUBLE_ARRAY8_BIG
        : DataFormat.CODE_DOUBLE_ARRAY8_LITTLE;

    /**
     * Native double format (for long array). <br/>
     * It is equal to either <tt>CODE_DOUBLE_ARRAY32_BIG</tt> or <tt>CODE_DOUBLE_ARRAY32_LITTLE</tt>,
     * according to the underlying platform. It is used to marshal (externalize)
     * values in a platform-consistent way.
     */
    int CODE_DOUBLE_ARRAY32_NATIVE =
        Misc.isBigEndianPlatform()
        ? DataFormat.CODE_DOUBLE_ARRAY32_BIG
        : DataFormat.CODE_DOUBLE_ARRAY32_LITTLE;

} // end interface 'DataFormat'
